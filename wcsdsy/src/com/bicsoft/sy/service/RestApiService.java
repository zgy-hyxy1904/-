package com.bicsoft.sy.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bicsoft.sy.model.GeneLandRegModel;

public interface RestApiService {
	
	public List<?> getCompanyListForRest(Date lastSyncDate);
	
	public List<?> getCompanyLandStats();
	
	public List<?> getInputRegListForRest();
	
	public List<Map> getYieldPredictStatsForRest();
	
	public List<?> getProcMoniListForRest(Date lastSyncDate);
	
	public List<?> getYieldEvalForRest();
	
	public int checkGeneReg(Integer id);
	
	public int checkGeneRegExt(GeneLandRegModel geneLandRegModel, String year);
	
	public List<?> getYieldGrainRegForRest();

	public List<?> getYieldPredict(Date lastSyncDate);
}
