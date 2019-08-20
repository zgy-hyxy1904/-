package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.SoilMoni;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SoilMoniModel;

/**
 * 土壤监测
 * @author 创新中软
 * @date 2015-08-17
 */
public interface SoilMoniService {
	public SoilMoni getSoilMoni(int id);
	
	public void save(SoilMoniModel soilMoniModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<SoilMoni> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public List<SoilMoni> getSoilMoniList();
	
	public List<String> queryYears();
	
	public List<String> queryMonths(String yearCode);
	
	public List<String> queryDays(String yearCode, String monthCode);
	
	public List<Map<String,String>> queryGisDatas(String yearCode);
	
	public List<Map<String,String>> queryGisDatas(String yearCode,String monthCode);
	
	public List<Map<String,String>> queryGisDatas(String yearCode,String monthCode, String dayCode);
	
	public List<String> queryDates(String yearCode);
	
	public List<Map<String,String>> queryNearestGisDatas();
	
	public List<Map<String,String>> queryGisDatasByDate(String date);
}
