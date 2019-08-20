package com.bicsoft.sy.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.RestApiRegDao;
import com.bicsoft.sy.model.GeneLandRegModel;
import com.bicsoft.sy.service.RestApiService;

@Service
@Transactional
public class RestApiServiceImpl implements RestApiService {
	private static final Logger log = LoggerFactory.getLogger(ProvEvalServiceImpl.class);
	
	@Autowired
	protected RestApiRegDao restApiRegDao;
	
	public List<?> getCompanyListForRest(Date lastSyncDate){
		return this.restApiRegDao.getCompanyListForRest(lastSyncDate);
	}
	
	
	public List<?> getCompanyLandStats(){
		return this.restApiRegDao.getCompanyLandStats();
	}
	
	public List<?> getInputRegListForRest(){
		return this.restApiRegDao.getInputRegListForRest();
	}
	
	public List<Map> getYieldPredictStatsForRest(){
		return this.restApiRegDao.getYieldPredictStatsForRest();
	}
	
	public List<?> getProcMoniListForRest(Date lastSyncDate){
		return this.restApiRegDao.getProcMoniListForRest(lastSyncDate);
	}
	
	public List<?> getYieldEvalForRest(){
		return this.restApiRegDao.getYieldEvalForRest();
	}
	
	public int checkGeneReg(Integer id){
		return this.restApiRegDao.checkGeneReg(id);
	}
	
	public List<?> getYieldGrainRegForRest(){
		return this.restApiRegDao.getYieldEvalForRest();
	}
	
	public int checkGeneRegExt(GeneLandRegModel geneLandRegModel, String year){
		return this.restApiRegDao.checkGeneRegExt(geneLandRegModel, year);
	}
	
	public List<?> getYieldPredict(Date lastSyncDate){
		return this.restApiRegDao.getYieldPredict(lastSyncDate);
	}
}
