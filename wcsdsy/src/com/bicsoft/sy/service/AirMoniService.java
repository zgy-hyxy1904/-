package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.AirMoni;
import com.bicsoft.sy.model.AirMoniModel;
import com.bicsoft.sy.model.PageModel;

/**
 * 大气监测
 * @author 创新中软
 * @date 2015-08-17
 */
public interface AirMoniService {
	public AirMoni getAirMoni(int id);
	
	public void save(AirMoniModel airMoniModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<AirMoni> clazz, int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public List<AirMoni> getAirMoniList();
	
	public List<String> queryYears();
	
	public List<String> queryMonths(String yearCode);
	
	public List<String> queryDays(String yearCode, String monthCode);
	
	public List<String> queryDates(String yearCode);
	
	public List<Map<String,String>> queryGisDatas(String yearCode);
	
	public List<Map<String,String>> queryGisDatas(String yearCode,String monthCode);
	
	public List<Map<String,String>> queryGisDatas(String yearCode,String monthCode, String dayCode);
	
	public List<Map<String,String>> queryNearestGisDatas();
	
	public List<Map<String,String>> queryGisDatasByDate(String date);
}
