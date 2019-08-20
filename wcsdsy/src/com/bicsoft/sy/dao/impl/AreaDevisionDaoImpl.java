package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.AreaDivisionDao;
import com.bicsoft.sy.entity.AreaDevision;

@Repository
public class AreaDevisionDaoImpl extends BaseDaoImpl<AreaDevision, Serializable> implements AreaDivisionDao {
	
	public AreaDevision getAreaDevisionByName(String townName, String countryName){
		String sql = " from AreaDevision where deleteFlag='N' and townName=? and countryName=?";
		Query query = currentSession().createQuery(sql);
		query.setString(0, townName);
		query.setString(1, countryName);
		List<?> result = query.list();
		if (result != null)
			return (AreaDevision) result.get(0);
		else
			return null;
	}

	@Override
	public List<AreaDevision> queryBySql(String sql, Map<String, Object> params) {
		SQLQuery query = currentSession().createSQLQuery(sql);
		if(params!=null){
			query.setProperties(params);
		}
		
		return query.setResultTransformer(Transformers.aliasToBean(AreaDevision.class)).list();
		
	}
	
}