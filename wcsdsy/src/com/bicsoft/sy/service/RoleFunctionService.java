package com.bicsoft.sy.service;

import java.util.List;

import com.bicsoft.sy.entity.RoleFunction;
import com.bicsoft.sy.model.BaseModel;

public interface RoleFunctionService{
	public void delete(RoleFunction roleFunction);
	public void saveRoleFunList(BaseModel baseModel, String roleCode, String[] funIds);
	public List<RoleFunction> getFunListForRole(String roleCode);
}