package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.UserDao;
import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.util.MD5Util;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User, Serializable> implements UserDao {
	public User login(User user) {
		String passwod = MD5Util.getMD5Code(user.getPassword());
		String sql = " from User where userID=? and password=? and deleteFlag = ?";
		Query query = currentSession().createQuery(sql);
		query.setString(0, user.getUserID());
		query.setString(1, passwod);
		query.setString(2, "N");
		List<?> result = query.list();
		if (result != null && result.size() > 0)
			return (User) result.get(0);
		else
			return null;
	}
	
	public PageModel queryForPageModel(PageModel pageModel, String userId, String userName, String userType){
		String sqlTotal = " from User where deleteFlag=? ";
		if(userId != null && !userId.equals("")) sqlTotal += " and userId like ? ";
		if(userName != null && !userName.equals("")) sqlTotal += " and userName like ? ";
		if(userType != null && !userType.equals("")) sqlTotal += " and userType=? ";
		
		int i=0;
		Query queryTotal = currentSession().createQuery(sqlTotal);
		queryTotal.setString(i++, "N");
		if(userId != null && !userId.equals("")) queryTotal.setString(i++, "%"+userId+"%");
		if(userName != null && !userName.equals("")) queryTotal.setString(i++, "%"+userName+"%");
		if(userType != null && !userType.equals("")) queryTotal.setString(i++, userType);
		int total = queryTotal.list().size();
		pageModel.setTotalCount(total);
		
		i=0;
		sqlTotal += " order by createDate desc "; 
		Query query2 = currentSession().createQuery(sqlTotal);
		query2.setString(i++, "N");
		if(userId != null && !userId.equals("")) query2.setString(i++, "%"+userId+"%");
		if(userName != null && !userName.equals("")) query2.setString(i++, "%"+userName+"%");
		if(userType != null && !userType.equals("")) query2.setString(i++, userType);
		query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
		query2.setMaxResults(pageModel.getPageSize());
		
		pageModel.setResult(query2.list());
		return pageModel;
	}
	
	public User getUserByUserId(String userId){
		String sql = " from User where userID=?";
		Query query = currentSession().createQuery(sql);
		query.setString(0, userId);
		List<User> list = query.list();
		if(list != null && list.size() > 0) return list.get(0);
		else return null;
	}
}