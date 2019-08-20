package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.GeneLandDetail;
import com.bicsoft.sy.model.GeneLandDetailModel;
import com.bicsoft.sy.model.PageModel;

public interface GeneLandDetailService {
	public GeneLandDetail getGeneLandDetail(int id);
	
	public void save(GeneLandDetailModel geneLandDetailModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<GeneLandDetail> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public GeneLandDetail getGeneLandDetail(String hid);
	
	public List<GeneLandDetail> getGeneLandDetailList(String hid);
}
