package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.LandRegChange;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.LandRegChangeModel;
import com.bicsoft.sy.model.PageModel;

public interface LandRegChangeService {
	public LandRegChange getLandRegChange(int id);
	
	public void save(LandRegChangeModel landRegChangeModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<LandRegChange> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public void saveReason(LandRegChangeModel landRegChangeModel);
	
	public List<LandRegChangeModel> updateStatus(String[] ids, BaseModel baseModel);
	
}
