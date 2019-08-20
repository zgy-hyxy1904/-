package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.AreaDevision;

public interface AreaDivisionDao extends IDao<AreaDevision, Serializable>{
	
	public AreaDevision getAreaDevisionByName(String townName, String countryName);
	
	public List<AreaDevision> queryBySql(String sql, Map<String,Object> params);
}