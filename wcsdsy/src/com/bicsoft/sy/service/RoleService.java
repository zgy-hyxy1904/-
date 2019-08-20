package com.bicsoft.sy.service;

import java.util.List;

import com.bicsoft.sy.entity.Function;
import com.bicsoft.sy.entity.Role;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.RoleModel;

public interface RoleService{
	
	public Role getRole(int id);
	
	public void save(RoleModel role);
	
	public void delete(Role role);
	
	public void logicDelete(Class<Role> clazz , int id);
	
	public List<Role> getRoleList(); 
	
	public Role getRoleByRoleCode(String roleCode);
	
	public PageModel queryForPageModel(PageModel pageModel, RoleModel roleModel);
}