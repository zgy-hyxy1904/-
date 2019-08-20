package com.bicsoft.sy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.WaterMoniDao;
import com.bicsoft.sy.entity.WaterMoni;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.WaterMoniModel;
import com.bicsoft.sy.service.WaterMoniService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class WaterMoniServiceImpl implements WaterMoniService {
	private static final Logger log = LoggerFactory.getLogger(ProvEvalServiceImpl.class);
	
	@Autowired
	private WaterMoniDao waterMoniDao;
	
	@Override
	public WaterMoni getWaterMoni(int id) {
		return this.waterMoniDao.getWaterMoni(id);
	}

	@Override
	public void save(WaterMoniModel waterMoniModel) {
		try{
			WaterMoni waterMoni = null;
			if(waterMoniModel.getId() == null){
				waterMoni = (WaterMoni) POVOConvertUtil.convert(waterMoniModel, "com.bicsoft.sy.entity.WaterMoni");
				this.waterMoniDao.save(waterMoni);
			}else{
				waterMoni = this.waterMoniDao.queryById(WaterMoni.class, waterMoniModel.getId());
				
				waterMoni.setBod5( waterMoniModel.getBod5() );
				waterMoni.setCodcr( waterMoniModel.getCodcr() );
				waterMoni.setCodmn( waterMoniModel.getCodmn() );
				waterMoni.setDoValue( waterMoniModel.getDoValue() );
				waterMoni.setMonitorPointCode( waterMoniModel.getMonitorPointCode() );
				waterMoni.setNh3n( waterMoniModel.getNh3n() );
				waterMoni.setPh( waterMoniModel.getPh() );
				waterMoni.setUpdateDate(waterMoniModel.getUpdateDate());
				waterMoni.setUpdateUserId(waterMoniModel.getUpdateUserId());
			}
			this.waterMoniDao.save(waterMoni);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:",e);
		}
	}

	@Override
	public List<WaterMoni> getWaterMoniList() {
		return this.waterMoniDao.getWaterMoniList();
	}

	@Override
	public void delete(int id) {
		this.waterMoniDao.delete(id);
	}

	@Override
	public void logicDelete(Class<WaterMoni> clazz , int id) {
		this.waterMoniDao.logicDelete(clazz, id);
		
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.waterMoniDao.queryForPageModel(entityName, params, pageModel);
	}
	
	
	@Override
	public List<String> queryYears(){
		String sql = "SELECT DISTINCT DATE_FORMAT(MonitorDate,'%Y') AS Y,YEAR(MonitorDate) as YearCode From B_Watermonitoring WHERE DeleteFlag='N' ORDER BY YearCode";
		List<Object[]> results = this.waterMoniDao.queryBySQL(sql, null);
		List<String> yearCodes = new ArrayList<String>();
		for(Object[] obj : results){
			yearCodes.add(String.valueOf(obj[0]));
		}
		return yearCodes;
	}
	
	@Override
	public List<String> queryMonths(String yearCode){
		String sql = "SELECT DISTINCT DATE_FORMAT(MonitorDate,'%c') AS M,MONTH(MonitorDate) AS MonthCode From B_Watermonitoring WHERE DeleteFlag='N' AND YEAR(MonitorDate)='"+yearCode+"' ORDER BY MonthCode";
		List<Object[]> results = this.waterMoniDao.queryBySQL(sql, null);
		List<String> monthCodes = new ArrayList<String>();
		for(Object[] obj : results){
			monthCodes.add(String.valueOf(obj[0]));
		}
		return monthCodes;
	}
	
	@Override
	public List<String> queryDays(String yearCode, String monthCode){
		String sql = "SELECT DISTINCT DATE_FORMAT(MonitorDate,'%d') AS D,DAY(MonitorDate) AS DayCode From B_Watermonitoring WHERE DeleteFlag='N' AND YEAR(MonitorDate)='"+yearCode+"' AND MONTH(MonitorDate)='"+monthCode+"' ORDER BY DayCode";
		List<Object[]> results = this.waterMoniDao.queryBySQL(sql, null);
		List<String> dayCodes = new ArrayList<String>();
		for(Object[] obj : results){
			dayCodes.add(String.valueOf(obj[0]));
		}
		return dayCodes;
	}
	
	@Override
	public List<Map<String,String>> queryGisDatas(String yearCode){
		return this.queryGisDatas(yearCode, null);
	}
	
	@Override
	public List<Map<String,String>> queryGisDatas(String yearCode,String monthCode){
		return this.queryGisDatas(yearCode, monthCode, null);
	}
	
	@Override
	public List<Map<String,String>> queryGisDatas(String yearCode,String monthCode, String dayCode){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT MONITOR.MonitorPointType,MONITOR.MonitorPointCode,MONITOR.MonitorPointName,MONITOR.Longitude, MONITOR.Latitude, ");
		sql.append("	WATER.PH, WATER.DOValue, WATER.CODMn, WATER.BOD5, WATER.NH3N, WATER.TP, WATER.CODCr,DATE_FORMAT(WATER.MonitorDate,'%Y-%c-%d') as MonitorDate ");
		sql.append("	FROM B_Watermonitoring WATER  ");
		sql.append("	LEFT JOIN M_monitorpoint MONITOR  ");
		sql.append("	ON WATER.MonitorPointCode=MONITOR.MonitorPointCode  ");
		sql.append("	WHERE WATER.DeleteFlag='N' AND MONITOR.MonitorPointType='02' AND YEAR(WATER.MonitorDate)='"+yearCode+"' ");
		if(monthCode!=null &!"".equals(monthCode)){
			sql.append(" AND MONTH(WATER.MonitorDate)='"+monthCode+"'  ");
			if(dayCode!=null &!"".equals(dayCode)){
				sql.append(" AND DAY(WATER.MonitorDate)='"+dayCode+"'  ");
			}
		}
		
		List<Object[]> results = this.waterMoniDao.queryBySQL(sql.toString(), null);
		List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
		for(Object[] objs : results){
			int index=0;
			Map<String,String> oneData = new HashMap<String,String>();
			oneData.put("MonitorPointType", String.valueOf(objs[index++]));
			oneData.put("MonitorPointCode", String.valueOf(objs[index++]));
			oneData.put("MonitorPointName", String.valueOf(objs[index++]));
			oneData.put("Longitude", String.valueOf(objs[index++]));
			oneData.put("Latitude", String.valueOf(objs[index++]));
			oneData.put("PH", String.valueOf(objs[index++]));
			oneData.put("DOValue", String.valueOf(objs[index++]));
			oneData.put("CODMn", String.valueOf(objs[index++]));
			oneData.put("BOD5", String.valueOf(objs[index++]));
			oneData.put("NH3N", String.valueOf(objs[index++]));
			oneData.put("TP", String.valueOf(objs[index++]));
			oneData.put("CODCr", String.valueOf(objs[index++]));
			oneData.put("MonitorDate", String.valueOf(objs[index++]));
			datas.add(oneData);
		}
		return datas;
	}
	

	@Override
	public List<Map<String,String>> queryNearestGisDatas(){
		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT MONITOR.MonitorPointType,MONITOR.MonitorPointCode,MONITOR.MonitorPointName,MONITOR.Longitude, MONITOR.Latitude, ");
//		sql.append("	WATER.PH, WATER.DOValue, WATER.CODMn, WATER.BOD5, WATER.NH3N, WATER.TP, WATER.CODCr,DATE_FORMAT(WATER.MonitorDate,'%Y-%c-%d') as MonitorDate ");
//		sql.append("	FROM B_Watermonitoring WATER  ");
//		sql.append("	LEFT JOIN M_monitorpoint MONITOR  ");
//		sql.append("	ON WATER.MonitorPointCode=MONITOR.MonitorPointCode  ");
//		sql.append("	WHERE WATER.DeleteFlag='N' AND MONITOR.MonitorPointType='02' AND WATER.MonitorDate=(SELECT MAX(w.MonitorDate) FROM B_Watermonitoring w)  ORDER BY WATER.MonitorDate ASC ");
		sql.append("SELECT MONITOR.MonitorPointType,MONITOR.MonitorPointCode,MONITOR.MonitorPointName,MONITOR.Longitude, MONITOR.Latitude, 	WATER.PH, WATER.DOValue, WATER.CODMn,  ");
		sql.append("	WATER.BOD5, WATER.NH3N, WATER.TP, WATER.CODCr,DATE_FORMAT(WATER.MonitorDate,'%Y-%c-%d') as MonitorDate ");
		sql.append("	FROM M_monitorpoint MONITOR  ");
		sql.append("	LEFT JOIN B_Watermonitoring WATER ON WATER.MonitorPointCode=MONITOR.MonitorPointCode AND WATER.DeleteFlag='N'  ");
		sql.append("	AND WATER.MonitorDate=(SELECT MAX(w.MonitorDate) FROM B_Watermonitoring w)  ");
		sql.append("	WHERE MONITOR.DeleteFlag='N' AND MONITOR.MonitorPointType='02'  ORDER BY WATER.MonitorDate ASC  ");
		
		List<Object[]> results = this.waterMoniDao.queryBySQL(sql.toString(), null);
		List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
		for(Object[] objs : results){
			int index=0;
			Map<String,String> oneData = new HashMap<String,String>();
			oneData.put("MonitorPointType", String.valueOf(objs[index++]));
			oneData.put("MonitorPointCode", String.valueOf(objs[index++]));
			oneData.put("MonitorPointName", String.valueOf(objs[index++]));
			oneData.put("Longitude", String.valueOf(objs[index++]));
			oneData.put("Latitude", String.valueOf(objs[index++]));
			oneData.put("PH", String.valueOf(objs[index++]));
			oneData.put("DOValue", String.valueOf(objs[index++]));
			oneData.put("CODMn", String.valueOf(objs[index++]));
			oneData.put("BOD5", String.valueOf(objs[index++]));
			oneData.put("NH3N", String.valueOf(objs[index++]));
			oneData.put("TP", String.valueOf(objs[index++]));
			oneData.put("CODCr", String.valueOf(objs[index++]));
			oneData.put("MonitorDate", String.valueOf(objs[index++]));
			datas.add(oneData);
		}
		return datas;
	}
	
	@Override
	public List<Map<String,String>> queryGisDatasByDate(String date){
		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT MONITOR.MonitorPointType,MONITOR.MonitorPointCode,MONITOR.MonitorPointName,MONITOR.Longitude, MONITOR.Latitude, ");
//		sql.append("	WATER.PH, WATER.DOValue, WATER.CODMn, WATER.BOD5, WATER.NH3N, WATER.TP, WATER.CODCr,DATE_FORMAT(WATER.MonitorDate,'%Y-%c-%d') as MonitorDate ");
//		sql.append("	FROM B_Watermonitoring WATER  ");
//		sql.append("	LEFT JOIN M_monitorpoint MONITOR  ");
//		sql.append("	ON WATER.MonitorPointCode=MONITOR.MonitorPointCode  ");
//		sql.append("	WHERE WATER.DeleteFlag='N' AND MONITOR.MonitorPointType='02' AND DATE_FORMAT(WATER.MonitorDate,'%Y-%c-%d')='"+date+"' ORDER BY WATER.MonitorDate ASC  ");
		sql.append("SELECT MONITOR.MonitorPointType,MONITOR.MonitorPointCode,MONITOR.MonitorPointName,MONITOR.Longitude, MONITOR.Latitude, 	WATER.PH, WATER.DOValue, WATER.CODMn,  ");
		sql.append("	WATER.BOD5, WATER.NH3N, WATER.TP, WATER.CODCr,DATE_FORMAT(WATER.MonitorDate,'%Y-%c-%d') as MonitorDate ");
		sql.append("	FROM M_monitorpoint MONITOR  ");
		sql.append("	LEFT JOIN B_Watermonitoring WATER ON WATER.MonitorPointCode=MONITOR.MonitorPointCode AND WATER.DeleteFlag='N'  ");
		sql.append("	AND DATE_FORMAT(WATER.MonitorDate,'%Y-%c-%d')='"+date+"'  ");
		sql.append("	WHERE MONITOR.DeleteFlag='N' AND MONITOR.MonitorPointType='02'  ORDER BY WATER.MonitorDate ASC  ");
			
		List<Object[]> results = this.waterMoniDao.queryBySQL(sql.toString(), null);
		List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
		for(Object[] objs : results){
			int index=0;
			Map<String,String> oneData = new HashMap<String,String>();
			oneData.put("MonitorPointType", String.valueOf(objs[index++]));
			oneData.put("MonitorPointCode", String.valueOf(objs[index++]));
			oneData.put("MonitorPointName", String.valueOf(objs[index++]));
			oneData.put("Longitude", String.valueOf(objs[index++]));
			oneData.put("Latitude", String.valueOf(objs[index++]));
			oneData.put("PH", String.valueOf(objs[index++]));
			oneData.put("DOValue", String.valueOf(objs[index++]));
			oneData.put("CODMn", String.valueOf(objs[index++]));
			oneData.put("BOD5", String.valueOf(objs[index++]));
			oneData.put("NH3N", String.valueOf(objs[index++]));
			oneData.put("TP", String.valueOf(objs[index++]));
			oneData.put("CODCr", String.valueOf(objs[index++]));
			oneData.put("MonitorDate", String.valueOf(objs[index++]));
			datas.add(oneData);
		}
		return datas;
	}
	
	
	@Override
	public List<String> queryDates(String yearCode) {
		String sql = "SELECT DISTINCT DATE_FORMAT(MonitorDate,'%Y-%c-%d') AS D,DATE_FORMAT(MonitorDate,'%Y-%c-%d') AS Temp From B_Watermonitoring WHERE DeleteFlag='N' AND YEAR(MonitorDate)='"+yearCode+"' ORDER BY D";
		List<Object[]> results = this.waterMoniDao.queryBySQL(sql, null);
		List<String> dayCodes = new ArrayList<String>();
		for(Object[] obj : results){
			dayCodes.add(String.valueOf(obj[0]));
		}
		return dayCodes;
	}

}
