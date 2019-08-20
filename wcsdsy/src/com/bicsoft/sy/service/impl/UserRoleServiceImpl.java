package com.bicsoft.sy.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.UserRoleDao;
import com.bicsoft.sy.entity.UserRole;
import com.bicsoft.sy.service.UserRoleService;

@Service
@Transactional
public class UserRoleServiceImpl implements  UserRoleService
{
	private static final Logger log = LoggerFactory.getLogger(UserRoleServiceImpl.class);
	
	@Autowired
	protected UserRoleDao userRoleDao;
	
	public List<UserRole> getUserRoleList(String userId)
	{
		return this.userRoleDao.getUserRoleList(userId);
	}
  
	public void save(UserRole userRole){
		this.userRoleDao.save(userRole);
	}
	
	public void delete(int id){
		this.userRoleDao.delete(UserRole.class,id);
	}
	
	public void logicDelete(Class<UserRole> clazz , int id){
		this.userRoleDao.logicDelete(clazz, id);
	}
	
	public void deleteUserRoles(String userId){
		List<UserRole> userRoleList =  getUserRoleList(userId);
		for(UserRole userRole : userRoleList){
			this.userRoleDao.delete(userRole);
		}
	}
}
