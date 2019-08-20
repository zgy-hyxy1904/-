package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.MoniPoint;
import com.bicsoft.sy.model.MoniPointModel;
import com.bicsoft.sy.model.PageModel;

public interface MoniPointService {
	public MoniPoint getMoniPoint(int id);
	
	public void save(MoniPointModel moniPointModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<MoniPoint> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public MoniPoint getMoniPoint(String moniPointCode);
	
	public List<MoniPoint> getMoniPointList();
	
	public List<MoniPoint> getMoniPointList(String moniPointType);
}
