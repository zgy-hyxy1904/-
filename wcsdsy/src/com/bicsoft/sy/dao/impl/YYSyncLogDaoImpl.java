package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.YYSyncLogDao;
import com.bicsoft.sy.entity.YYSyncLog;
import com.bicsoft.sy.model.PageModel;

@Repository
public class YYSyncLogDaoImpl extends BaseDaoImpl<YYSyncLog, Serializable> implements YYSyncLogDao {
	
	public YYSyncLog getLastSyncLog(String bizType){
		String hql = "from YYSyncLog where bizType = ? and SyncStatus=0 order by SyncDate desc";
		Query query = currentSession().createQuery(hql);
		query.setString(0, bizType);
		query.setMaxResults(1);
		
		List<?> list = query.list();
		if(list != null && list.size() > 0) return (YYSyncLog)list.get(0);
		else return null;
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
}