package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.WaterMoni;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.WaterMoniModel;

/**
 * 水质监测
 * @author 创新中软
 * @date 2015-08-17
 */
public interface WaterMoniService {
	public WaterMoni getWaterMoni(int id);
	
	public void save(WaterMoniModel waterMoniModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<WaterMoni> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public List<WaterMoni> getWaterMoniList();
	
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
