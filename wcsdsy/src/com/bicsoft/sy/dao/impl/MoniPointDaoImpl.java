package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.MoniPointDao;
import com.bicsoft.sy.entity.MoniPoint;
import com.bicsoft.sy.model.PageModel;

@Repository
public class MoniPointDaoImpl extends BaseDaoImpl<MoniPoint, Serializable> implements MoniPointDao  {
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		String sqlTotal = " FROM " + entityName + " WHERE deleteFlag=? ";
		StringBuffer whereStr = new StringBuffer("");
		if( params.get("monitorPointType") != null && StringUtils.isNotEmpty( (String)params.get("monitorPointType") ) ){
			whereStr.append(" AND monitorPointType = ?");
		}
		
		sqlTotal += whereStr.toString();
		sqlTotal  +="order by updateDate desc";
		
		int i=0;
		Query queryTotal = currentSession().createQuery(sqlTotal);
		queryTotal.setString(i++, "N");
		if( params.get("monitorPointType") != null && StringUtils.isNotEmpty( (String)params.get("monitorPointType") ) ){
			queryTotal.setString(i++, (String)params.get("monitorPointType"));
		}
		
		int total = queryTotal.list().size();
		pageModel.setTotalCount(total);
		
		i=0;
		Query query2 = currentSession().createQuery(sqlTotal);
		query2.setString(i++, "N");
		if( params.get("monitorPointType") != null && StringUtils.isNotEmpty( (String)params.get("monitorPointType") ) ){
			query2.setString(i++, (String)params.get("monitorPointType"));
		}
		
		query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
		query2.setMaxResults(pageModel.getPageSize());
		
		pageModel.setResult(query2.list());
		
		return pageModel;
	}
}
