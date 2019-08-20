package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.List;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.RoleFunction;
import com.bicsoft.sy.entity.UserRole;

public interface UserRoleDao extends IDao<UserRole, Serializable>{
	public List<UserRole> getUserRoleList(String userId);
}