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

import com.bicsoft.sy.dao.AirMoniDao;
import com.bicsoft.sy.entity.AirMoni;
import com.bicsoft.sy.model.AirMoniModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.AirMoniService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class AirMoniServiceImpl implements AirMoniService {
	private static final Logger log = LoggerFactory.getLogger(ProvEvalServiceImpl.class);
	
	@Autowired
	private AirMoniDao airMoniDao;
	
	@Override
	public AirMoni getAirMoni(int id) {
		return this.airMoniDao.getAirMoni(id);
	}

	@Override
	public void save(AirMoniModel airMoniModel) {
		try{
			AirMoni airMoni = null;
			if(airMoniModel.getId() == null){
				airMoni = (AirMoni) POVOConvertUtil.convert(airMoniModel, "com.bicsoft.sy.entity.AirMoni");
				this.airMoniDao.save(airMoni);
			}else{
				airMoni = this.airMoniDao.queryById(AirMoni.class, airMoniModel.getId());
				airMoni.setUpdateDate(airMoniModel.getUpdateDate());
				airMoni.setUpdateUserId(airMoniModel.getUpdateUserId());
				
				airMoni.setMonitorDate( airMoniModel.getMonitorDate() );
				airMoni.setMonitorPointCode( airMoniModel.getMonitorPointCode() );
				airMoni.setNo2( airMoniModel.getNo2() );
				airMoni.setSo2( airMoniModel.getSo2() );
				airMoni.setTsp( airMoniModel.getTsp() );
			}
			this.airMoniDao.save(airMoni);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:", e);
		}
	}

	@Override
	public void delete(int id) {
		this.airMoniDao.delete(id);
	}


	@Override
	public List<AirMoni> getAirMoniList() {
		return this.airMoniDao.getAirMoniList();
	}

	@Override
	public void logicDelete(Class<AirMoni> clazz, int id) {
		this.airMoniDao.logicDelete(clazz, id);
		
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.airMoniDao.queryForPageModel(entityName, params, pageModel);
	}
	
	@Override
	public List<String> queryYears(){
		String sql = "SELECT DISTINCT DATE_FORMAT(MonitorDate,'%Y') AS Y,YEAR(MonitorDate) as YearCode From B_AirMonitoring WHERE DeleteFlag='N' ORDER BY YearCode";
		List<Object[]> results = this.airMoniDao.queryBySQL(sql, null);
		List<String> yearCodes = new ArrayList<String>();
		for(Object[] obj : results){
			yearCodes.add(String.valueOf(obj[0]));
		}
		return yearCodes;
	}
	
	@Override
	public List<String> queryMonths(String yearCode){
		String sql = "SELECT DISTINCT DATE_FORMAT(MonitorDate,'%c') AS M,MONTH(MonitorDate) AS MonthCode From B_AirMonitoring WHERE DeleteFlag='N' AND YEAR(MonitorDate)='"+yearCode+"' ORDER BY MonthCode";
		List<Object[]> results = this.airMoniDao.queryBySQL(sql, null);
		List<String> monthCodes = new ArrayList<String>();
		for(Object[] obj : results){
			monthCodes.add(String.valueOf(obj[0]));
		}
		return monthCodes;
	}
	
	@Override
	public List<String> queryDays(String yearCode, String monthCode){
		String sql = "SELECT DISTINCT DATE_FORMAT(MonitorDate,'%d') AS D,DAY(MonitorDate) AS DayCode From B_AirMonitoring WHERE DeleteFlag='N' AND YEAR(MonitorDate)='"+yearCode+"' AND MONTH(MonitorDate)='"+monthCode+"' ORDER BY DayCode";
		List<Object[]> results = this.airMoniDao.queryBySQL(sql, null);
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
		sql.append("	AIR.TSP, AIR.SO2, AIR.NO2,DATE_FORMAT(AIR.MonitorDate,'%Y-%c-%d') AS MonitorDate ");
		sql.append("	FROM B_airmonitoring AIR ");
		sql.append("	LEFT JOIN M_monitorpoint MONITOR ");
		sql.append("	ON AIR.MonitorPointCode=MONITOR.MonitorPointCode ");
		sql.append("	WHERE AIR.DeleteFlag='N' AND MONITOR.MonitorPointType='01' AND YEAR(AIR.MonitorDate)='"+yearCode+"' ");
		if(monthCode!=null &!"".equals(monthCode)){
			sql.append(" AND MONTH(AIR.MonitorDate)='"+monthCode+"'  ");
			if(dayCode!=null &!"".equals(dayCode)){
				sql.append(" AND DAY(AIR.MonitorDate)='"+dayCode+"'  ");
			}
		}
		
		List<Object[]> results = this.airMoniDao.queryBySQL(sql.toString(), null);
		List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
		for(Object[] objs : results){
			int index=0;
			Map<String,String> oneData = new HashMap<String,String>();
			oneData.put("MonitorPointType", String.valueOf(objs[index++]));
			oneData.put("MonitorPointCode", String.valueOf(objs[index++]));
			oneData.put("MonitorPointName", String.valueOf(objs[index++]));
			oneData.put("Longitude", String.valueOf(objs[index++]));
			oneData.put("Latitude", String.valueOf(objs[index++]));
			oneData.put("TSP", String.valueOf(objs[index++]));
			oneData.put("SO2", String.valueOf(objs[index++]));
			oneData.put("NO2", String.valueOf(objs[index++]));
			oneData.put("MonitorDate", String.valueOf(objs[index++]));
			datas.add(oneData);
		}
		return datas;
	}


	@Override
	public List<Map<String,String>> queryNearestGisDatas(){
		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT MONITOR.MonitorPointType,MONITOR.MonitorPointCode,MONITOR.MonitorPointName,MONITOR.Longitude, MONITOR.Latitude, ");
//		sql.append("	AIR.TSP, AIR.SO2, AIR.NO2,DATE_FORMAT(AIR.MonitorDate,'%Y-%c-%d') AS MonitorDate ");
//		sql.append("	FROM B_airmonitoring AIR ");
//		sql.append("	LEFT JOIN M_monitorpoint MONITOR ");
//		sql.append("	ON AIR.MonitorPointCode=MONITOR.MonitorPointCode ");
//		sql.append("	WHERE AIR.DeleteFlag='N' AND monitor.MonitorPointType='01' AND AIR.MonitorDate=(SELECT MAX(A.MonitorDate) FROM B_airmonitoring A)  ORDER BY AIR.MonitorDate ASC ");
		sql.append("SELECT MONITOR.MonitorPointType,MONITOR.MonitorPointCode,MONITOR.MonitorPointName,MONITOR.Longitude, MONITOR.Latitude, 	AIR.TSP, AIR.SO2,  ");
		sql.append("	AIR.NO2,DATE_FORMAT(AIR.MonitorDate,'%Y-%c-%d') AS MonitorDate  ");
		sql.append("	FROM M_monitorpoint MONITOR 	LEFT JOIN  B_airmonitoring AIR	ON AIR.MonitorPointCode=MONITOR.MonitorPointCode and AIR.DeleteFlag='N'	AND  ");
		sql.append("	AIR.MonitorDate=(SELECT MAX(A.MonitorDate) FROM B_airmonitoring A) ");
		sql.append("	WHERE MONITOR.DeleteFlag='N' AND MONITOR.MonitorPointType='01'   ORDER BY AIR.MonitorDate ASC  ");
		
		List<Object[]> results = this.airMoniDao.queryBySQL(sql.toString(), null);
		List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
		for(Object[] objs : results){
			int index=0;
			Map<String,String> oneData = new HashMap<String,String>();
			oneData.put("MonitorPointType", String.valueOf(objs[index++]));
			oneData.put("MonitorPointCode", String.valueOf(objs[index++]));
			oneData.put("MonitorPointName", String.valueOf(objs[index++]));
			oneData.put("Longitude", String.valueOf(objs[index++]));
			oneData.put("Latitude", String.valueOf(objs[index++]));
			oneData.put("TSP", String.valueOf(objs[index++]));
			oneData.put("SO2", String.valueOf(objs[index++]));
			oneData.put("NO2", String.valueOf(objs[index++]));
			oneData.put("MonitorDate", String.valueOf(objs[index++]));
			datas.add(oneData);
		}
		return datas;
	}
	
	@Override
	public List<Map<String,String>> queryGisDatasByDate(String date){
		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT MONITOR.MonitorPointType,MONITOR.MonitorPointCode,MONITOR.MonitorPointName,MONITOR.Longitude, MONITOR.Latitude, ");
//		sql.append("	AIR.TSP, AIR.SO2, AIR.NO2,DATE_FORMAT(AIR.MonitorDate,'%Y-%c-%d') AS MonitorDate ");
//		sql.append("	FROM B_airmonitoring AIR ");
//		sql.append("	LEFT JOIN M_monitorpoint MONITOR ");
//		sql.append("	ON AIR.MonitorPointCode=MONITOR.MonitorPointCode ");
//		sql.append("	WHERE AIR.DeleteFlag='N' AND monitor.MonitorPointType='01' AND DATE_FORMAT(AIR.MonitorDate,'%Y-%c-%d')='"+date+"'   ORDER BY AIR.MonitorDate ASC ");
		sql.append("SELECT MONITOR.MonitorPointType,MONITOR.MonitorPointCode,MONITOR.MonitorPointName,MONITOR.Longitude, MONITOR.Latitude, 	AIR.TSP, AIR.SO2,  ");
		sql.append("	AIR.NO2,DATE_FORMAT(AIR.MonitorDate,'%Y-%c-%d') AS MonitorDate  ");
		sql.append("	FROM M_monitorpoint MONITOR 	LEFT JOIN B_airmonitoring AIR 	ON AIR.MonitorPointCode=MONITOR.MonitorPointCode AND AIR.DeleteFlag='N' ");
		sql.append("	AND DATE_FORMAT(AIR.MonitorDate,'%Y-%c-%d')='"+date+"'  ");
		sql.append("	WHERE MONITOR.DeleteFlag='N' AND MONITOR.MonitorPointType='01'   ORDER BY AIR.MonitorDate ASC  ");
		
		List<Object[]> results = this.airMoniDao.queryBySQL(sql.toString(), null);
		List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
		for(Object[] objs : results){
			int index=0;
			Map<String,String> oneData = new HashMap<String,String>();
			oneData.put("MonitorPointType", String.valueOf(objs[index++]));
			oneData.put("MonitorPointCode", String.valueOf(objs[index++]));
			oneData.put("MonitorPointName", String.valueOf(objs[index++]));
			oneData.put("Longitude", String.valueOf(objs[index++]));
			oneData.put("Latitude", String.valueOf(objs[index++]));
			oneData.put("TSP", String.valueOf(objs[index++]));
			oneData.put("SO2", String.valueOf(objs[index++]));
			oneData.put("NO2", String.valueOf(objs[index++]));
			oneData.put("MonitorDate", String.valueOf(objs[index++]));
			datas.add(oneData);
		}
		return datas;
	}
	
	@Override
	public List<String> queryDates(String yearCode) {
		String sql = "SELECT DISTINCT DATE_FORMAT(MonitorDate,'%Y-%c-%d') AS D,DATE_FORMAT(MonitorDate,'%Y-%c-%d') AS Temp From B_AirMonitoring WHERE DeleteFlag='N' AND YEAR(MonitorDate)='"+yearCode+"' ORDER BY D";
		List<Object[]> results = this.airMoniDao.queryBySQL(sql, null);
		List<String> dayCodes = new ArrayList<String>();
		for(Object[] obj : results){
			dayCodes.add(String.valueOf(obj[0]));
		}
		return dayCodes;
	}
	

}
