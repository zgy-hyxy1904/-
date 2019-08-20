package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.GraiRegDetail;
import com.bicsoft.sy.model.GraiRegDetailModel;
import com.bicsoft.sy.model.PageModel;

/**
 * 收粮备案  --详情表
 * @author 创新中软
 * @date 2015-08-21
 */
public interface GraiRegDetailService {
	public GraiRegDetail getGraiRegDetail(int id);
	
	public GraiRegDetail getGraiRegDetailByHid(int hid);
	
	public List<GraiRegDetail> getGraiRegDetailList(int hid);
	
	public void save(GraiRegDetailModel graiRegDetailModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<GraiRegDetail> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
}
