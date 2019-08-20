package com.bicsoft.sy.service;

import java.util.Map;

import com.bicsoft.sy.entity.GraiReg;
import com.bicsoft.sy.model.GraiRegModel;
import com.bicsoft.sy.model.PageModel;

/**
 * 收粮备案
 * @author 创新中软
 * @date 2015-08-17
 */
public interface GraiRegService {
	public GraiReg getGraiReg(int id);
	
	public void save(GraiRegModel graiRegModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<GraiReg> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public Float getYmcls(String year, String idNumber);
	/**
	 * 查询已卖出粮食,不包含自已本条信息
	 * @param year
	 * @param idNumber
	 * @return
	 */
	public Float getYmcls(String year, String idNumber, Integer id);
}
