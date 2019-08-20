package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.RoleFunctionDao;
import com.bicsoft.sy.entity.RoleFunction;
import com.bicsoft.sy.entity.Role;

@Repository
public class RoleFunctionDaoImpl extends BaseDaoImpl<RoleFunction, Serializable> implements RoleFunctionDao {
	
	public List<RoleFunction> getFunListForRole(String roleCode){
		String hql = " from RoleFunction where deleteFlag=? and roleCode = ?";
		Query query = currentSession().createQuery(hql);
		query.setString(0, "N");
		query.setString(1,  roleCode);
		return query.list();
	}
}