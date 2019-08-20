package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.GraiForeD;
import com.bicsoft.sy.entity.GraiRegDetail;
import com.bicsoft.sy.model.GraiForeDModel;
import com.bicsoft.sy.model.PageModel;

/**
 * 收粮预报--子表
 * @author 创新中软
 * @date 2015-08-26
 */
public interface GraiForeDService {
	public  GraiForeD getGraiForeD(int id);
	
	public GraiForeD getGraiForeDByHid(int hid);
	
	public List<GraiForeD> getGraiForeDListByHid(int hid);
	
	public void save( GraiForeDModel blackListModel);
	
	public void delete(int id);
	
	public void logicDelete(Class< GraiForeD> clazz, int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
}
