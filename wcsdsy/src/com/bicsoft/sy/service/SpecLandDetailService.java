package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.SpecLandDetail;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SpecLandDetailModel;

public interface SpecLandDetailService {
	public SpecLandDetail getSpecLandRegDetail(int id);
	
	public void save(SpecLandDetailModel specLandRegDetailModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<SpecLandDetail> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public SpecLandDetail getSpecLandDetail(String hid);
	
	public List<SpecLandDetail> getSpecLandDetailList(int hid);
}
