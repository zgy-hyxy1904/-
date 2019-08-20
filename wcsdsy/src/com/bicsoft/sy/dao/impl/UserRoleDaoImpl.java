package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.UserRoleDao;
import com.bicsoft.sy.entity.UserRole;

@Repository
public class UserRoleDaoImpl extends BaseDaoImpl<UserRole, Serializable> implements UserRoleDao {
	
	public List<UserRole> getUserRoleList(String userId){
		String hql = " from UserRole where deleteFlag=? and userID = ?";
		Query query = currentSession().createQuery(hql);
		query.setString(0, "N");
		query.setString(1,  userId);
		return query.list();
	}
}