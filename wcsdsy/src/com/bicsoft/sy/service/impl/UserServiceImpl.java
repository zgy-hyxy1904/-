package com.bicsoft.sy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.UserDao;
import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.ToDoListModel;
import com.bicsoft.sy.model.UserModel;
import com.bicsoft.sy.service.UserService;
import com.bicsoft.sy.util.POVOConvertUtil;
import com.bicsoft.sy.util.StringUtil;
import com.mysql.jdbc.StringUtils;

@Service
@Transactional
public class UserServiceImpl implements  UserService
{
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	protected UserDao userDao;
  
	public User login(User user){
		return this.userDao.login(user);
	}
  
	public User getUser(int id){
		return this.userDao.queryById(User.class, id);
	}
  
	public void save(UserModel userModel){
		try{
			if(userModel.getId() == null){
				//企业为空的判断
				if(StringUtil.isNullOrEmpty(userModel.getCompanyCode())){
					userModel.setCompanyName("");
				}
				User user = (User) POVOConvertUtil.convert(userModel, "com.bicsoft.sy.entity.User");
				this.userDao.save(user);
			}else{
				User user = this.userDao.queryById(User.class, userModel.getId());
				user.setUpdateDate(userModel.getUpdateDate());
				user.setUpdateUserId(userModel.getUpdateUserId());
				user.setTel(userModel.getTel());
				user.setRemark(userModel.getRemark());
				user.setEmail(userModel.getEmail());
				this.userDao.save(user);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:",e);
		}
	}
  
	public void save(User user){
		this.userDao.save(user);
	}
	
	public void delete(int id){
		this.userDao.delete(User.class,id);
	}
  
	public void logicDelete(Class<User> clazz , int id){
		this.userDao.logicDelete(clazz, id);
	}
	
	public PageModel queryForPageModel(PageModel pageModel, String userId, String userName, String userType){
		return this.userDao.queryForPageModel(pageModel, userId, userName, userType);
	}
	
	public User getUserByUserId(String userId){
		return this.userDao.getUserByUserId(userId);
	}
	
	@Override
	public List<ToDoListModel> queryToDoLists(String companyCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT");
		sql.append("		'普通土地备案信息待提交申请数量' AS ToDoList, COUNT(*) AS ToDoCount ");
		sql.append("	FROM b_generallandregh ");
		sql.append("WHERE DeleteFlag = 'N' AND Status = '01' ");
		if(!StringUtils.isNullOrEmpty(companyCode)){
			sql.append(" AND companyCode='"+companyCode+"'");
		}
		sql.append("	UNION ALL ");
		sql.append("	SELECT ");
		sql.append("		CASE Status ");
		sql.append("			WHEN '01' THEN '特殊土地备案信息待提交申请数量' ");
		sql.append("			WHEN '02' THEN '特殊土地备案信息待审核请求数量' ");
		sql.append("			WHEN '04' THEN '特殊土地备案信息已驳回请求数量' ");
		sql.append("			ELSE '其它待办事项' ");
		sql.append("			END AS ToDoList, ");
		sql.append(" 	COUNT(*) AS ToDoCount ");
		sql.append("	FROM b_speciallandreg ");
		sql.append("WHERE DeleteFlag = 'N' AND Status <> '03' ");
		if(!StringUtils.isNullOrEmpty(companyCode)){
			sql.append(" AND companyCode='"+companyCode+"'");
		}
		sql.append("GROUP BY Status ");
		
		List<Object[]> results = userDao.queryBySQL(sql.toString(), null);
		List<ToDoListModel> models = new ArrayList<ToDoListModel>();
		for(Object[] result : results){
			ToDoListModel model = new ToDoListModel();
			model.setToDoList(String.valueOf(result[0]));
			model.setToDoCount(String.valueOf(result[1]));
			models.add(model);
		}
		return models;
	}
}
