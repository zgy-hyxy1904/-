package com.bicsoft.sy.service;

import java.util.Map;

import com.bicsoft.sy.entity.BlackList;
import com.bicsoft.sy.model.BlackListModel;
import com.bicsoft.sy.model.PageModel;

/**
 * 黑名单管理
 * @author 创新中软
 * @date 2015-08-26
 */
public interface BlackListService {
	public BlackList getBlackList(int id);
	
	public void save(BlackListModel blackListModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<BlackList> clazz, int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public BlackList getBlackListByCompanyCod(String companyCode);
}
