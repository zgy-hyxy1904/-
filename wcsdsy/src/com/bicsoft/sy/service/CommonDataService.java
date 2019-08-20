package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.CommonData;
import com.bicsoft.sy.model.PageModel;

public interface CommonDataService {
	public CommonData getCommonData(int id);
	
	public CommonData getCommonData(String codeKey, String codeValue);
	
	public void save(CommonData commonData);
	
	public void delete(int id);
	
	public void logicDelete(Class<CommonData> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public List<CommonData> getCommonDataListByCodeKey(String codeKey);
}
