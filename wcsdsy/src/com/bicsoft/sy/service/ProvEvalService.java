package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.ProvEval;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.ProvEvalModel;

/**
 * 种源评估
 * @author 创新中软
 * @date 2015-08-17
 */
public interface ProvEvalService {
	public ProvEval getProvEval(int id);
	
	public void save(ProvEvalModel provEvalModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<ProvEval> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public PageModel queryLandInfoList(Map<String, Object> params,PageModel pageModel);
	
	public List<ProvEval> getProvEvalList();
	
	public ProvEval getProvEvalByYear(String yearCode);
	
	public Float getLandsArea(String companyCode, String yearCode);
	
	public Float getGeneLandsArea(String companyCode, String yearCode);
	
	public Float getSpecLandsArea(String companyCode, String yearCode);
	
	public ProvEval getProvEval(String year, String seedCode);
	
	public List<Map<String,Object>> queryLandInfos(String companyCode, String yearCode , int page, int pageSize);
	
	public int queryLandInfosCount(String companyCode, String yearCode);
}
