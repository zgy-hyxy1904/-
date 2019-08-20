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

import com.bicsoft.sy.dao.SoilMoniDao;
import com.bicsoft.sy.entity.SoilMoni;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SoilMoniModel;
import com.bicsoft.sy.service.SoilMoniService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class SoilMoniServiceImpl implements SoilMoniService {
	private static final Logger log = LoggerFactory.getLogger(ProvEvalServiceImpl.class);
	
	@Autowired
	private SoilMoniDao soilMoniDao;
	
	@Override
	public SoilMoni getSoilMoni(int id) {
		return soilMoniDao.getSoilMoni(id);
	}

	@Override
	public void save(SoilMoniModel soilMoniModel) {
		try{
			SoilMoni soilMoni = null;
			if(soilMoniModel.getId() == null){
				soilMoni = (SoilMoni) POVOConvertUtil.convert(soilMoniModel, "com.bicsoft.sy.entity.SoilMoni");
				this.soilMoniDao.save(soilMoni);
			}else{
				soilMoni = this.soilMoniDao.queryById(SoilMoni.class, soilMoniModel.getId());
				
				soilMoni.setAlkelinen( soilMoniModel.getAlkelinen() );
				soilMoni.setMonitorPointCode( soilMoniModel.getMonitorPointCode() );
				soilMoni.setOlsenk( soilMoniModel.getOlsenk() );
				soilMoni.setOlsenp( soilMoniModel.getOlsenp() );
				soilMoni.setOmvalue( soilMoniModel.getOmvalue() );
				soilMoni.setPh( soilMoniModel.getPh() );
				soilMoni.setUpdateDate(soilMoniModel.getUpdateDate());
				soilMoni.setUpdateUserId(soilMoniModel.getUpdateUserId());
			}
			this.soilMoniDao.save(soilMoni);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:",e);
		}
	}

	@Override
	public void delete(int id) {
		this.soilMoniDao.delete(id);
	}

	@Override
	public List<SoilMoni> getSoilMoniList() {
		return this.soilMoniDao.getSoilMoniList();
	}

	@Override
	public void logicDelete(Class<SoilMoni> clazz, int id) {
		this.soilMoniDao.logicDelete(clazz, id);
		
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.soilMoniDao.queryForPageModel(entityName, params, pageModel);
	}
	
	
	@Override
	public List<String> queryYears(){
		String sql = "SELECT DISTINCT DATE_FORMAT(MonitorDate,'%Y') AS Y,YEAR(MonitorDate) as YearCode From B_Soilmonitoring WHERE DeleteFlag='N' ORDER BY YearCode";
		List<Object[]> results = this.soilMoniDao.queryBySQL(sql, null);
		List<String> yearCodes = new ArrayList<String>();
		for(Object[] obj : results){
			yearCodes.add(String.valueOf(obj[0]));
		}
		return yearCodes;
	}
	
	@Override
	public List<String> queryMonths(String yearCode){
		String sql = "SELECT DISTINCT DATE_FORMAT(MonitorDate,'%c') AS M,MONTH(MonitorDate) AS MonthCode From B_Soilmonitoring WHERE DeleteFlag='N' AND YEAR(MonitorDate)='"+yearCode+"' ORDER BY MonthCode";
		List<Object[]> results = this.soilMoniDao.queryBySQL(sql, null);
		List<String> monthCodes = new ArrayList<String>();
		for(Object[] obj : results){
			monthCodes.add(String.valueOf(obj[0]));
		}
		return monthCodes;
	}
	
	@Override
	public List<String> queryDays(String yearCode, String monthCode){
		String sql = "SELECT DISTINCT DATE_FORMAT(MonitorDate,'%d') AS D,DAY(MonitorDate) AS DayCode From B_Soilmonitoring WHERE DeleteFlag='N' AND YEAR(MonitorDate)='"+yearCode+"' AND MONTH(MonitorDate)='"+monthCode+"' ORDER BY DayCode";
		List<Object[]> results = this.soilMoniDao.queryBySQL(sql, null);
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
		sql.append("	SIOL.OMValue, SIOL.AlkelineN, SIOL.OlsenP, SIOL.OlsenK, SIOL.PH,DATE_FORMAT(SIOL.MonitorDate,'%Y-%c-%d') as MonitorDate ");
		sql.append("	FROM B_Soilmonitoring SIOL ");
		sql.append("	LEFT JOIN M_monitorpoint MONITOR ");
		sql.append("	ON SIOL.MonitorPointCode=monitor.MonitorPointCode  ");
		sql.append("	WHERE SIOL.DeleteFlag='N' AND monitor.MonitorPointType='03' AND YEAR(SIOL.MonitorDate)='"+yearCode+"' ");
		if(monthCode!=null &!"".equals(monthCode)){
			sql.append(" AND MONTH(SIOL.MonitorDate)='"+monthCode+"'  ");
			if(dayCode!=null &!"".equals(dayCode)){
				sql.append(" AND DAY(SIOL.MonitorDate)='"+dayCode+"'  ");
			}
		}
		
		List<Object[]> results = this.soilMoniDao.queryBySQL(sql.toString(), null);
		List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
		for(Object[] objs : results){
			int index=0;
			Map<String,String> oneData = new HashMap<String,String>();
			oneData.put("MonitorPointType", String.valueOf(objs[index++]));
			oneData.put("MonitorPointCode", String.valueOf(objs[index++]));
			oneData.put("MonitorPointName", String.valueOf(objs[index++]));
			oneData.put("Longitude", String.valueOf(objs[index++]));
			oneData.put("Latitude", String.valueOf(objs[index++]));
			oneData.put("OMValue", String.valueOf(objs[index++]));
			oneData.put("AlkelineN", String.valueOf(objs[index++]));
			oneData.put("OlsenP", String.valueOf(objs[index++]));
			oneData.put("OlsenK", String.valueOf(objs[index++]));
			oneData.put("PH", String.valueOf(objs[index++]));
			oneData.put("MonitorDate", String.valueOf(objs[index++]));
			datas.add(oneData);
		}
		return datas;
	}

	@Override
	public List<Map<String,String>> queryNearestGisDatas(){
		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT MONITOR.MonitorPointType,MONITOR.MonitorPointCode,MONITOR.MonitorPointName,MONITOR.Longitude, MONITOR.Latitude, ");
//		sql.append("	SIOL.OMValue, SIOL.AlkelineN, SIOL.OlsenP, SIOL.OlsenK, SIOL.PH,DATE_FORMAT(SIOL.MonitorDate,'%Y-%c-%d') as MonitorDate ");
//		sql.append("	FROM B_Soilmonitoring SIOL ");
//		sql.append("	LEFT JOIN M_monitorpoint MONITOR ");
//		sql.append("	ON SIOL.MonitorPointCode=monitor.MonitorPointCode  ");
//		sql.append("	WHERE SIOL.DeleteFlag='N' AND monitor.MonitorPointType='03' AND SIOL.MonitorDate=(SELECT MAX(S.MonitorDate) FROM B_Soilmonitoring S)  ORDER BY SIOL.MonitorDate ASC ");
		sql.append("SELECT MONITOR.MonitorPointType,MONITOR.MonitorPointCode,MONITOR.MonitorPointName,MONITOR.Longitude, MONITOR.Latitude, 	SIOL.OMValue, SIOL.AlkelineN, SIOL.OlsenP, ");
		sql.append("	SIOL.OlsenK, SIOL.PH,DATE_FORMAT(SIOL.MonitorDate,'%Y-%c-%d') as MonitorDate  ");
		sql.append("	FROM M_monitorpoint MONITOR ");
		sql.append("	LEFT JOIN B_Soilmonitoring SIOL	ON SIOL.MonitorPointCode=MONITOR.MonitorPointCode AND SIOL.DeleteFlag='N' ");
		sql.append("	AND SIOL.MonitorDate=(SELECT MAX(S.MonitorDate) FROM B_Soilmonitoring S)   ");
		sql.append("	WHERE MONITOR.DeleteFlag='N' AND MONITOR.MonitorPointType='03'  ORDER BY SIOL.MonitorDate ASC  ");
		
		List<Object[]> results = this.soilMoniDao.queryBySQL(sql.toString(), null);
		List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
		for(Object[] objs : results){
			int index=0;
			Map<String,String> oneData = new HashMap<String,String>();
			oneData.put("MonitorPointType", String.valueOf(objs[index++]));
			oneData.put("MonitorPointCode", String.valueOf(objs[index++]));
			oneData.put("MonitorPointName", String.valueOf(objs[index++]));
			oneData.put("Longitude", String.valueOf(objs[index++]));
			oneData.put("Latitude", String.valueOf(objs[index++]));
			oneData.put("OMValue", String.valueOf(objs[index++]));
			oneData.put("AlkelineN", String.valueOf(objs[index++]));
			oneData.put("OlsenP", String.valueOf(objs[index++]));
			oneData.put("OlsenK", String.valueOf(objs[index++]));
			oneData.put("PH", String.valueOf(objs[index++]));
			oneData.put("MonitorDate", String.valueOf(objs[index++]));
			datas.add(oneData);
		}
		return datas;
	}
	
	@Override
	public List<Map<String,String>> queryGisDatasByDate(String date){
		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT MONITOR.MonitorPointType,MONITOR.MonitorPointCode,MONITOR.MonitorPointName,MONITOR.Longitude, MONITOR.Latitude, ");
//		sql.append("	SIOL.OMValue, SIOL.AlkelineN, SIOL.OlsenP, SIOL.OlsenK, SIOL.PH,DATE_FORMAT(SIOL.MonitorDate,'%Y-%c-%d') as MonitorDate ");
//		sql.append("	FROM B_Soilmonitoring SIOL ");
//		sql.append("	LEFT JOIN M_monitorpoint MONITOR ");
//		sql.append("	ON SIOL.MonitorPointCode=monitor.MonitorPointCode  ");
//		sql.append("	WHERE SIOL.DeleteFlag='N' AND monitor.MonitorPointType='03' AND DATE_FORMAT(SIOL.MonitorDate,'%Y-%c-%d')='"+date+"' ORDER BY SIOL.MonitorDate ASC  ");
		sql.append("SELECT MONITOR.MonitorPointType,MONITOR.MonitorPointCode,MONITOR.MonitorPointName,MONITOR.Longitude, MONITOR.Latitude, 	SIOL.OMValue, SIOL.AlkelineN, SIOL.OlsenP, ");
		sql.append("	SIOL.OlsenK, SIOL.PH,DATE_FORMAT(SIOL.MonitorDate,'%Y-%c-%d') as MonitorDate ");
		sql.append("	FROM M_monitorpoint MONITOR ");
		sql.append("	LEFT JOIN B_Soilmonitoring SIOL ON SIOL.MonitorPointCode = MONITOR.MonitorPointCode  AND MONITOR.DeleteFlag='N' ");
		sql.append("	AND DATE_FORMAT(SIOL.MonitorDate,'%Y-%c-%d')='"+date+"'  ");
		sql.append("	WHERE MONITOR.DeleteFlag='N' AND MONITOR.MonitorPointType='03'  ORDER BY SIOL.MonitorDate ASC  ");
		
		List<Object[]> results = this.soilMoniDao.queryBySQL(sql.toString(), null);
		List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
		for(Object[] objs : results){
			int index=0;
			Map<String,String> oneData = new HashMap<String,String>();
			oneData.put("MonitorPointType", String.valueOf(objs[index++]));
			oneData.put("MonitorPointCode", String.valueOf(objs[index++]));
			oneData.put("MonitorPointName", String.valueOf(objs[index++]));
			oneData.put("Longitude", String.valueOf(objs[index++]));
			oneData.put("Latitude", String.valueOf(objs[index++]));
			oneData.put("OMValue", String.valueOf(objs[index++]));
			oneData.put("AlkelineN", String.valueOf(objs[index++]));
			oneData.put("OlsenP", String.valueOf(objs[index++]));
			oneData.put("OlsenK", String.valueOf(objs[index++]));
			oneData.put("PH", String.valueOf(objs[index++]));
			oneData.put("MonitorDate", String.valueOf(objs[index++]));
			datas.add(oneData);
		}
		return datas;
	}
	
	@Override
	public List<String> queryDates(String yearCode) {
		String sql = "SELECT DISTINCT DATE_FORMAT(MonitorDate,'%Y-%c-%d') AS D,DATE_FORMAT(MonitorDate,'%Y-%c-%d') AS Temp From B_Soilmonitoring WHERE DeleteFlag='N' AND YEAR(MonitorDate)='"+yearCode+"' ORDER BY D";
		List<Object[]> results = this.soilMoniDao.queryBySQL(sql, null);
		List<String> dayCodes = new ArrayList<String>();
		for(Object[] obj : results){
			dayCodes.add(String.valueOf(obj[0]));
		}
		return dayCodes;
	}
}
