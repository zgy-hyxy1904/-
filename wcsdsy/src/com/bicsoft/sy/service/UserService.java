package com.bicsoft.sy.service;

import com.bicsoft.sy.model.UserModel;

import java.util.List;

import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.ToDoListModel;

public interface UserService{
	
	public User login(User user);
	
	public User getUser(int id);
	
	public void save(User user);
	
	public void save(UserModel user);
	
	public void delete(int id);
	
	public User getUserByUserId(String userId);
	
	public void logicDelete(Class<User> clazz , int id);
	
	public PageModel queryForPageModel(PageModel pageModel, String userId, String userName, String userType);
	
	public List<ToDoListModel> queryToDoLists(String companyCode);
}