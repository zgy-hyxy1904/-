package com.bicsoft.sy.service;

import java.util.Map;

import com.bicsoft.sy.entity.BlackListD;
import com.bicsoft.sy.model.BlackListDModel;
import com.bicsoft.sy.model.PageModel;

/**
 * 黑名单子表
 * @author 创新中软
 * @date 2015-08-26
 */
public interface BlackListDService {
	public BlackListD getBlackListD(int id);
	
	public void save(BlackListDModel blackListDModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<BlackListD> clazz, int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
}
