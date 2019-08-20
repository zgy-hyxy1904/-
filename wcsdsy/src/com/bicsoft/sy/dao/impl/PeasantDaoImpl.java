package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.PeasantDao;
import com.bicsoft.sy.entity.Peasant;
import com.bicsoft.sy.util.StringUtil;

@Repository
public class PeasantDaoImpl extends BaseDaoImpl<Peasant, Serializable> implements PeasantDao {
	
	public Peasant getByContractorID(String contractorType, String contractorIDType, String contractorID){
		String hql = " from Peasant where contractorType=? and contractorIDType=? and contractorID=?";
		
		Query query = currentSession().createQuery(hql);
		query.setString(0, contractorType);
		query.setString(1, contractorIDType);
		query.setString(2, contractorID);
		
		List<?> result = query.list();
		if (result != null && result.size() > 0)
			return (Peasant) result.get(0);
		else
			return null;
	}
	
	public Peasant getByContractorID(String contractorIDType, String contractorID){
		String hql = " from Peasant where contractorIDType=? and contractorID=?";
		
		Query query = currentSession().createQuery(hql);
		query.setString(0, contractorIDType);
		query.setString(1, contractorID);
		
		List<?> result = query.list();
		if (result != null && result.size() > 0)
			return (Peasant) result.get(0);
		else
			return null;
	}
}