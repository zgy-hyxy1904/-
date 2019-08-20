package com.bicsoft.sy.service;

import java.util.Map;

import com.bicsoft.sy.entity.LandRegChangeDetail;
import com.bicsoft.sy.model.LandRegChangeDetailModel;
import com.bicsoft.sy.model.PageModel;

public interface LandRegChangeDetailService {
	public LandRegChangeDetail getLandRegChangeDetail(int id);
	
	public void save(LandRegChangeDetailModel landRegChangeDetailModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<LandRegChangeDetail> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
}
