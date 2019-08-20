package com.bicsoft.sy.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.SeedVariety;
import com.bicsoft.sy.model.PageModel;

public interface SeedVarietyService {
	public SeedVariety getSeedVariety(int id);
	
	public void save(SeedVariety seedVariety);
	
	public void delete(int id);
	
	public void logicDelete(Class<SeedVariety> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public SeedVariety getSeedVariety(String seedCode);
	
	public List<SeedVariety> getSeedVarietyList();
}
