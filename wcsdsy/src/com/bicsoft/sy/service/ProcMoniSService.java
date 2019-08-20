package com.bicsoft.sy.service;

import java.util.Map;

import com.bicsoft.sy.entity.ProcMoniS;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.ProcMoniSModel;

/**
 * 过程监控
 * @author 创新中软
 * @date 2015-08-20
 */
public interface ProcMoniSService {
	public ProcMoniS getProcMoniS(int id);
	
	public void save(ProcMoniSModel ProcMoniSModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<ProcMoniS> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
}
