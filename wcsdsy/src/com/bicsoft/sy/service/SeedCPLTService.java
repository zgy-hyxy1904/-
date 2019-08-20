package com.bicsoft.sy.service;

import java.util.Map;

import com.bicsoft.sy.entity.SeedCPLT;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SeedCPLTModel;

/**
 * 种子投诉
 * @author 创新中软
 * @date 2015-08-26
 */
public interface SeedCPLTService {
	public SeedCPLT getSeedCPLT(int id);
	
	public void save(SeedCPLTModel seedCPLTModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<SeedCPLT> clazz, int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
}
