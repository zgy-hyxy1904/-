package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.Map;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.GraiEval;

/**
 * 粮食评估
 * @author 创新中软
 * @date 2015-08-17
 */
public interface GraiEvalDao extends IDao<GraiEval, Serializable>{
	public Map<String, Float> getInfo(String year);
	public double getTotalWeight(String year, String companyCode);
}
