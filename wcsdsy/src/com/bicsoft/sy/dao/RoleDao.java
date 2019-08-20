package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.List;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.Role;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.RoleModel;

public interface RoleDao extends IDao<Role, Serializable>{
	public List<Role> getRoleList();
	
	public Role getRoleByRoleCode(String roleCode);
	
	public PageModel queryForPageModel(PageModel pageModel, RoleModel roleModel);
}