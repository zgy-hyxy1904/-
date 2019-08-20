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

import com.bicsoft.sy.dao.PmaqiMoniDao;
import com.bicsoft.sy.entity.PmaqiMoni;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.PmaqiMoniModel;
import com.bicsoft.sy.service.PmaqiMoniService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class PmiaqiMoniServiceImpl implements PmaqiMoniService {
	private static final Logger log = LoggerFactory.getLogger(PmiaqiMoniServiceImpl.class);
	
	@Autowired
	private PmaqiMoniDao pmaqiMoniDao;
	
	@Override
	public PmaqiMoni getPmaqiMoni(int id) {
		return pmaqiMoniDao.queryById(PmaqiMoni.class, id);
	}

	@Override
	public void save(PmaqiMoniModel pmaqiMoniModel) {
		try{
			PmaqiMoni pmaqiMoni = null;
			if(pmaqiMoniModel.getId() == null){
				pmaqiMoni = (PmaqiMoni) POVOConvertUtil.convert(pmaqiMoniModel, "com.bicsoft.sy.entity.PmaqiMoni");
				this.pmaqiMoniDao.save(pmaqiMoni);
			}else{
				pmaqiMoni = this.pmaqiMoniDao.queryById(PmaqiMoni.class, pmaqiMoniModel.getId());
				pmaqiMoni.setUpdateDate(pmaqiMoniModel.getUpdateDate());
				pmaqiMoni.setUpdateUserId(pmaqiMoniModel.getUpdateUserId());
				pmaqiMoni.setMonitorPointCode( pmaqiMoniModel.getMonitorPointCode() );
				pmaqiMoni.setAqiName( pmaqiMoniModel.getAqiName() );
				pmaqiMoni.setAqi( pmaqiMoniModel.getAqi() );
				pmaqiMoni.setCo( pmaqiMoniModel.getCo() );
				pmaqiMoni.setNo2( pmaqiMoniModel.getNo2() );
				pmaqiMoni.setO3( pmaqiMoniModel.getO3() );
				pmaqiMoni.setPm10( pmaqiMoniModel.getPm10() );
				pmaqiMoni.setPm2_5( pmaqiMoniModel.getPm2_5() );
			}
			this.pmaqiMoniDao.save(pmaqiMoni);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:", e);
		}
	}

	@Override
	public void delete(int id) {
		this.pmaqiMoniDao.delete(PmaqiMoni.class, id);

	}

	@Override
	public void logicDelete(Class<PmaqiMoni> clazz, int id) {
		this.pmaqiMoniDao.logicDelete(clazz, id);

	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.pmaqiMoniDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public List<PmaqiMoni> getPmaqiMoniList() {
		return this.pmaqiMoniDao.queryByHQL("from PmaqiMoni where deleteFlag='N'", null);
	}
	
	@Override
	public List<String> queryYears(){
		String sql = "SELECT DISTINCT DATE_FORMAT(MonitorDate,'%Y') AS Y,YEAR(MonitorDate) as YearCode From B_Pmaqimonitoring WHERE DeleteFlag='N' ORDER BY YearCode";
		List<Object[]> results = this.pmaqiMoniDao.queryBySQL(sql, null);
		List<String> yearCodes = new ArrayList<String>();
		for(Object[] obj : results){
			yearCodes.add(String.valueOf(obj[0]));
		}
		return yearCodes;
	}
	
	@Override
	public List<String> queryMonths(String yearCode){
		String sql = "SELECT DISTINCT DATE_FORMAT(MonitorDate,'%c') AS M,MONTH(MonitorDate) AS MonthCode From B_Pmaqimonitoring WHERE DeleteFlag='N' AND YEAR(MonitorDate)='"+yearCode+"' ORDER BY MonthCode";
		List<Object[]> results = this.pmaqiMoniDao.queryBySQL(sql, null);
		List<String> monthCodes = new ArrayList<String>();
		for(Object[] obj : results){
			monthCodes.add(String.valueOf(obj[0]));
		}
		return monthCodes;
	}
	
	@Override
	public List<String> queryDays(String yearCode, String monthCode){
		String sql = "SELECT DISTINCT DATE_FORMAT(MonitorDate,'%d') AS D,DAY(MonitorDate) AS DayCode From B_Pmaqimonitoring WHERE DeleteFlag='N' AND YEAR(MonitorDate)='"+yearCode+"' AND MONTH(MonitorDate)='"+monthCode+"' ORDER BY DayCode";
		List<Object[]> results = this.pmaqiMoniDao.queryBySQL(sql, null);
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
		sql.append("	PMAQI.AQI, PMAQI.AQIName, PMAQI.PM2_5, PMAQI.PM10, PMAQI.CO, PMAQI.NO2, PMAQI.O3, PMAQI.SO2,DATE_FORMAT(PMAQI.MonitorDate,'%Y-%c-%d') as MonitorDate ");
		sql.append("	FROM B_Pmaqimonitoring PMAQI  ");
		sql.append("	LEFT JOIN M_monitorpoint MONITOR ");
		sql.append("	ON PMAQI.MonitorPointCode=monitor.MonitorPointCode  ");
		sql.append("	WHERE PMAQI.DeleteFlag='N' AND monitor.MonitorPointType='04' AND YEAR(PMAQI.MonitorDate)='"+yearCode+"' ");
		if(monthCode!=null &!"".equals(monthCode)){
			sql.append(" AND MONTH(PMAQI.MonitorDate)='"+monthCode+"'  ");
			if(dayCode!=null &!"".equals(dayCode)){
				sql.append(" AND DAY(PMAQI.MonitorDate)='"+dayCode+"'  ");
			}
		}
		
		List<Object[]> results = this.pmaqiMoniDao.queryBySQL(sql.toString(), null);
		List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
		for(Object[] objs : results){
			int index=0;
			Map<String,String> oneData = new HashMap<String,String>();
			oneData.put("MonitorPointType", String.valueOf(objs[index++]));
			oneData.put("MonitorPointCode", String.valueOf(objs[index++]));
			oneData.put("MonitorPointName", String.valueOf(objs[index++]));
			oneData.put("Longitude", String.valueOf(objs[index++]));
			oneData.put("Latitude", String.valueOf(objs[index++]));
			oneData.put("AQI", String.valueOf(objs[index++]));
			oneData.put("AQIName", String.valueOf(objs[index++]));
			oneData.put("PM2_5", String.valueOf(objs[index++]));
			oneData.put("PM10", String.valueOf(objs[index++]));
			oneData.put("CO", String.valueOf(objs[index++]));
			oneData.put("NO2", String.valueOf(objs[index++]));
			oneData.put("O3", String.valueOf(objs[index++]));
			oneData.put("SO2", String.valueOf(objs[index++]));
			oneData.put("MonitorDate", String.valueOf(objs[index++]));
			datas.add(oneData);
		}
		return datas;
	}
	
	@Override
	public List<Map<String,String>> queryNearestGisDatas(){
		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT MONITOR.MonitorPointType,MONITOR.MonitorPointCode,MONITOR.MonitorPointName,MONITOR.Longitude, MONITOR.Latitude, ");
//		sql.append("	PMAQI.AQI, PMAQI.AQIName, PMAQI.PM2_5, PMAQI.PM10, PMAQI.CO, PMAQI.NO2, PMAQI.O3, PMAQI.SO2,DATE_FORMAT(PMAQI.MonitorDate,'%Y-%c-%d') as MonitorDate ");
//		sql.append("	FROM B_Pmaqimonitoring PMAQI  ");
//		sql.append("	LEFT JOIN M_monitorpoint MONITOR ");
//		sql.append("	ON PMAQI.MonitorPointCode=monitor.MonitorPointCode  ");
//		sql.append("	WHERE PMAQI.DeleteFlag='N' AND monitor.MonitorPointType='04' AND PMAQI.MonitorDate=(SELECT MAX(P.MonitorDate) FROM B_Pmaqimonitoring P)  ORDER BY PMAQI.MonitorDate ASC ");
		sql.append("SELECT MONITOR.MonitorPointType,MONITOR.MonitorPointCode,MONITOR.MonitorPointName,MONITOR.Longitude, MONITOR.Latitude, 	PMAQI.AQI, PMAQI.AQIName, PMAQI.PM2_5, ");
		sql.append("	PMAQI.PM10, PMAQI.CO, PMAQI.NO2, PMAQI.O3, PMAQI.SO2,DATE_FORMAT(PMAQI.MonitorDate,'%Y-%c-%d') as MonitorDate 	FROM  M_monitorpoint MONITOR  ");
		sql.append("	LEFT JOIN B_Pmaqimonitoring PMAQI ON PMAQI.MonitorPointCode= MONITOR.MonitorPointCode  ");
		sql.append("	and PMAQI.DeleteFlag='N' and PMAQI.MonitorDate=(SELECT MAX(P.MonitorDate) FROM B_Pmaqimonitoring P) ");
		sql.append("	WHERE monitor.DeleteFlag='N' AND monitor.MonitorPointType='04' ");
		sql.append("	ORDER BY PMAQI.MonitorDate ASC ");
		
		List<Object[]> results = this.pmaqiMoniDao.queryBySQL(sql.toString(), null);
		List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
		for(Object[] objs : results){
			int index=0;
			Map<String,String> oneData = new HashMap<String,String>();
			oneData.put("MonitorPointType", String.valueOf(objs[index++]));
			oneData.put("MonitorPointCode", String.valueOf(objs[index++]));
			oneData.put("MonitorPointName", String.valueOf(objs[index++]));
			oneData.put("Longitude", String.valueOf(objs[index++]));
			oneData.put("Latitude", String.valueOf(objs[index++]));
			oneData.put("AQI", String.valueOf(objs[index++]));
			oneData.put("AQIName", String.valueOf(objs[index++]));
			oneData.put("PM2_5", String.valueOf(objs[index++]));
			oneData.put("PM10", String.valueOf(objs[index++]));
			oneData.put("CO", String.valueOf(objs[index++]));
			oneData.put("NO2", String.valueOf(objs[index++]));
			oneData.put("O3", String.valueOf(objs[index++]));
			oneData.put("SO2", String.valueOf(objs[index++]));
			oneData.put("MonitorDate", String.valueOf(objs[index++]));
			datas.add(oneData);
		}
		return datas;
	}
	
	@Override
	public List<Map<String,String>> queryGisDatasByDate(String date){
		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT MONITOR.MonitorPointType,MONITOR.MonitorPointCode,MONITOR.MonitorPointName,MONITOR.Longitude, MONITOR.Latitude, ");
//		sql.append("	PMAQI.AQI, PMAQI.AQIName, PMAQI.PM2_5, PMAQI.PM10, PMAQI.CO, PMAQI.NO2, PMAQI.O3, PMAQI.SO2,DATE_FORMAT(PMAQI.MonitorDate,'%Y-%c-%d') as MonitorDate ");
//		sql.append("	FROM B_Pmaqimonitoring PMAQI  ");
//		sql.append("	LEFT JOIN M_monitorpoint MONITOR ");
//		sql.append("	ON PMAQI.MonitorPointCode=monitor.MonitorPointCode  ");
//		sql.append("	WHERE PMAQI.DeleteFlag='N' AND monitor.MonitorPointType='04' AND DATE_FORMAT(PMAQI.MonitorDate,'%Y-%c-%d')='"+date+"' ORDER BY PMAQI.MonitorDate ASC  ");
		sql.append("SELECT MONITOR.MonitorPointType,MONITOR.MonitorPointCode,MONITOR.MonitorPointName,MONITOR.Longitude, MONITOR.Latitude, 	PMAQI.AQI, PMAQI.AQIName, PMAQI.PM2_5,  ");
		sql.append("	PMAQI.PM10, PMAQI.CO, PMAQI.NO2, PMAQI.O3, PMAQI.SO2,DATE_FORMAT(PMAQI.MonitorDate,'%Y-%c-%d') as MonitorDate 	FROM M_monitorpoint MONITOR LEFT JOIN  ");
		sql.append("	B_Pmaqimonitoring PMAQI ON PMAQI.MonitorPointCode=MONITOR.MonitorPointCode  and PMAQI.DeleteFlag='N' AND DATE_FORMAT(PMAQI.MonitorDate,'%Y-%c-%d')='"+date+"'  ");
		sql.append("	WHERE  MONITOR.DeleteFlag='N' AND MONITOR.MonitorPointType='04'  ORDER BY PMAQI.MonitorDate ASC ");
		
		
		List<Object[]> results = this.pmaqiMoniDao.queryBySQL(sql.toString(), null);
		List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
		for(Object[] objs : results){
			int index=0;
			Map<String,String> oneData = new HashMap<String,String>();
			oneData.put("MonitorPointType", String.valueOf(objs[index++]));
			oneData.put("MonitorPointCode", String.valueOf(objs[index++]));
			oneData.put("MonitorPointName", String.valueOf(objs[index++]));
			oneData.put("Longitude", String.valueOf(objs[index++]));
			oneData.put("Latitude", String.valueOf(objs[index++]));
			oneData.put("AQI", String.valueOf(objs[index++]));
			oneData.put("AQIName", String.valueOf(objs[index++]));
			oneData.put("PM2_5", String.valueOf(objs[index++]));
			oneData.put("PM10", String.valueOf(objs[index++]));
			oneData.put("CO", String.valueOf(objs[index++]));
			oneData.put("NO2", String.valueOf(objs[index++]));
			oneData.put("O3", String.valueOf(objs[index++]));
			oneData.put("SO2", String.valueOf(objs[index++]));
			oneData.put("MonitorDate", String.valueOf(objs[index++]));
			datas.add(oneData);
		}
		return datas;
	}
	
	@Override
	public List<String> queryDates(String yearCode) {
		String sql = "SELECT DISTINCT DATE_FORMAT(MonitorDate,'%Y-%c-%d') AS D,DATE_FORMAT(MonitorDate,'%Y-%c-%d') AS Temp From B_Pmaqimonitoring WHERE DeleteFlag='N' AND YEAR(MonitorDate)='"+yearCode+"' ORDER BY D";
		List<Object[]> results = this.pmaqiMoniDao.queryBySQL(sql, null);
		List<String> dayCodes = new ArrayList<String>();
		for(Object[] obj : results){
			dayCodes.add(String.valueOf(obj[0]));
		}
		return dayCodes;
	}

}
