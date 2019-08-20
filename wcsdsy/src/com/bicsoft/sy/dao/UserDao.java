package com.bicsoft.sy.dao;

import java.io.Serializable;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.model.PageModel;

public interface UserDao extends IDao<User, Serializable>{
	public User login(User user);
	
	User getUserByUserId(String userId);
	
	public PageModel queryForPageModel(PageModel pageModel, String userId, String userName, String userType);
}