package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.LandLogDao;
import com.bicsoft.sy.entity.LandLog;
import com.bicsoft.sy.model.PageModel;

@Repository
public class LandLogDaoImpl  extends BaseDaoImpl<LandLog, Serializable> implements LandLogDao{
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params, PageModel pageModel) {
			String sqlTotal = " FROM " + entityName + " WHERE deleteFlag=? ";
			StringBuffer whereStr = new StringBuffer("");
			if( params.get("batchNo") != null && StringUtils.isNotEmpty( (String)params.get("batchNo") ) ){
				whereStr.append(" AND batchNo=?");
			}
			whereStr.append(" order by createDate desc ");
			sqlTotal += whereStr.toString();
			
			int i=0;
			Query queryTotal = currentSession().createQuery(sqlTotal);
			queryTotal.setString(i++, "N");
			if( params.get("batchNo") != null && StringUtils.isNotEmpty( (String)params.get("batchNo") ) ){
				queryTotal.setString(i++, (String)params.get("batchNo"));
			}
			
			int total = queryTotal.list().size();
			pageModel.setTotalCount(total);
			i=0;
			Query query2 = currentSession().createQuery(sqlTotal);
			query2.setString(i++, "N");
			if( params.get("batchNo") != null && StringUtils.isNotEmpty( (String)params.get("batchNo") ) ){
				query2.setString(i++, (String)params.get("batchNo"));
			}
			query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
			query2.setMaxResults(pageModel.getPageSize());
			
			pageModel.setResult(query2.list());
			
			return pageModel;
		}
}
