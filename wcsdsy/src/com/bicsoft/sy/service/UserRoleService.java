package com.bicsoft.sy.service;

import java.util.List;

import com.bicsoft.sy.entity.UserRole;

public interface UserRoleService{
	
	public void save(UserRole userRole);
	
	public void delete(int id);
	
	public void logicDelete(Class<UserRole> clazz , int id);
	
	public List<UserRole> getUserRoleList(String userId);
	
	public void deleteUserRoles(String userId);
}