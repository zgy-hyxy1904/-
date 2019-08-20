package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.model.GeneLandRegModel;

/**
 * 接口
 * @author 创新中软
 * @date 2015-08-17
 */
public interface RestApiRegDao extends IDao<Object, Serializable>{
	public List<?> getCompanyListForRest(Date lastSyncDate);
	
	public List<?> getCompanyLandStats();
	
	public List<?> getInputRegListForRest();
	
	public List<Map> getYieldPredictStatsForRest();
	
	public List<?> getProcMoniListForRest(Date lastSyncDate);
	
	public List<?> getYieldEvalForRest();
	
	public int checkGeneReg(Integer id);
	
	public int checkGeneRegExt(GeneLandRegModel geneLandRegModel, String year);
	
	public List<?> getYieldPredict(Date lastSyncDate);
}
