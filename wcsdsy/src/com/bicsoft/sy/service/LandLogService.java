package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.LandLog;
import com.bicsoft.sy.model.PageModel;

public interface LandLogService {
	public LandLog getLandLog(int id);
	
	public void save(LandLog landLog);
	
	public void delete(int id);
	
	public void logicDelete(Class<LandLog> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public List<LandLog> queryByBatchNo(String batchNo);
	
}
