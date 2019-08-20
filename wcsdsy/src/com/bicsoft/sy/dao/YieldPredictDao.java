package com.bicsoft.sy.dao;

import java.io.Serializable;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.YieldPredict;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.YieldPredictModel;

public interface YieldPredictDao extends IDao<YieldPredict, Serializable>{
	
	public double getYieldRegistedTotalValue(String companyCode, String year);
	
	public PageModel getYieldPredictList(PageModel pageModel, YieldPredictModel yieldPredictModel);
}