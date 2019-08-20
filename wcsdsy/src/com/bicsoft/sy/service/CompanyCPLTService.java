package com.bicsoft.sy.service;

import java.util.Map;

import com.bicsoft.sy.entity.CompanyCPLT;
import com.bicsoft.sy.model.CompanyCPLTModel;
import com.bicsoft.sy.model.PageModel;

/**
 * 企业投诉
 * @author 创新中软
 * @date 2015-08-26
 */
public interface CompanyCPLTService {
	public CompanyCPLT getCompanyCPLT(int id);
	
	public void save(CompanyCPLTModel companyCPLTModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<CompanyCPLT> clazz, int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
}
