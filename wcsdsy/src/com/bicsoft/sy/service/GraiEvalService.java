package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.GraiEval;
import com.bicsoft.sy.model.GraiEvalModel;
import com.bicsoft.sy.model.PageModel;

/**
 * 粮食评估
 * @author 创新中软
 * @date 2015-08-17
 */
public interface GraiEvalService {
	public GraiEval getGraiEval(int id);
	
	public void save(GraiEvalModel graiEvalModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<GraiEval> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public List<GraiEval> getGraiEvalList();
	
	public GraiEval getGraiEvalByYear(String yearCode);
	
	public Float getLandsArea(String companyCode, String yearCode);
	
	public Float getGeneLandsArea(String companyCode, String yearCode);
	
	public Float getSpecLandsArea(String companyCode, String yearCode);
	
	public List<Map<String,Object>> queryLandInfos(String companyCode, String yearCode, int page, int pageSize);

	public int queryLandInfosCount(String companyCode, String yearCode);
	
	public GraiEval getGraiEval(String year, String seedCode);
	
	public GraiEval getGraiEval(String year);
	
	public double getTotalWeight(String year, String companyCode);
	
	public int queryGrainRegInfoListCount(String yearCode , String contractorID);
	
}
