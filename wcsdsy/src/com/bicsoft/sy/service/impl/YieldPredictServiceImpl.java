package com.bicsoft.sy.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.YieldPredictDao;
import com.bicsoft.sy.dao.YieldPredictDetailDao;
import com.bicsoft.sy.entity.YieldPredict;
import com.bicsoft.sy.entity.YieldPredictDetail;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.YieldPredictModel;
import com.bicsoft.sy.service.YieldPredictService;

@Service
@Transactional
public class YieldPredictServiceImpl implements  YieldPredictService
{
	private static final Logger log = LoggerFactory.getLogger(YieldPredictServiceImpl.class);
	
	@Autowired
	protected YieldPredictDao yieldPredictDDao;
	
	@Autowired
	protected YieldPredictDao yieldPredictDao;
	
	public void logicDelete(Class<YieldPredict> clazz , int id)
	{
		this.yieldPredictDao.logicDelete(clazz, id);
	}
	
	public PageModel getYieldPredictList(PageModel pageModel, YieldPredictModel yieldPredictModel){
		return this.yieldPredictDao.getYieldPredictList(pageModel, yieldPredictModel);
	}
	
	public void save(YieldPredict yieldPredict){
		this.yieldPredictDao.save(yieldPredict);
	}
	
	public double getYieldRegistedTotalValue(String companyCode, String year){
		return this.yieldPredictDao.getYieldRegistedTotalValue(companyCode, year);
	}
}
