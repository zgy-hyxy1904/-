package com.bicsoft.sy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.YieldPredictDetailDao;
import com.bicsoft.sy.entity.YieldPredictDetail;
import com.bicsoft.sy.service.YieldPredictDetailService;

@Service
@Transactional
public class YieldPredictDetailServiceImpl implements  YieldPredictDetailService
{
	private static final Logger log = LoggerFactory.getLogger(YieldPredictDetailServiceImpl.class);
	
	@Autowired
	protected YieldPredictDetailDao yieldPredictDetailDao;
	
	public void save(YieldPredictDetail yieldPredictDetail){
		this.yieldPredictDetailDao.save(yieldPredictDetail);
	}
}
