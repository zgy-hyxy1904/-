package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.List;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.RoleFunction;

public interface RoleFunctionDao extends IDao<RoleFunction, Serializable>{
	public List<RoleFunction> getFunListForRole(String roleCode);
}