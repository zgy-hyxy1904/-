package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.YieldPredict;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.YieldPredictModel;

public interface YieldPredictService{
	
	public void logicDelete(Class<YieldPredict> clazz , int id);
	
	public void save(YieldPredict yieldPredict);
	
	public PageModel getYieldPredictList(PageModel pageModel, YieldPredictModel yieldPredictModel);
	
	public double getYieldRegistedTotalValue(String companyCode, String year);
}