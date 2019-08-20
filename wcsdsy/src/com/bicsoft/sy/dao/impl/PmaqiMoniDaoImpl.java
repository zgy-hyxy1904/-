package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.PmaqiMoniDao;
import com.bicsoft.sy.entity.PmaqiMoni;
import com.bicsoft.sy.model.PageModel;

@Repository
public class PmaqiMoniDaoImpl extends BaseDaoImpl<PmaqiMoni, Serializable>  implements PmaqiMoniDao {
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		String sqlTotal = " FROM " + entityName + " WHERE deleteFlag=? ";
		StringBuffer whereStr = new StringBuffer("");
		if( params.get("beginDate") != null && StringUtils.isNotEmpty( (String)params.get("beginDate") ) ){
			whereStr.append(" AND  date_format(monitorDate, '%Y-%m-%d') >=?");
		}
		if( params.get("endDate") != null && StringUtils.isNotEmpty( (String)params.get("endDate") ) ){
			whereStr.append(" AND  date_format(monitorDate, '%Y-%m-%d') <=?");
		}
		
		sqlTotal += whereStr.toString();
		sqlTotal +="order by monitorDate desc";//排序
		
		int i=0;
		Query queryTotal = currentSession().createQuery(sqlTotal);
		queryTotal.setString(i++, "N");
		if( params.get("beginDate") != null && StringUtils.isNotEmpty( (String)params.get("beginDate") ) ){
			queryTotal.setString(i++, (String)params.get("beginDate"));
		}
		if( params.get("endDate") != null && StringUtils.isNotEmpty( (String)params.get("endDate") ) ){
			queryTotal.setString(i++, (String)params.get("endDate"));
		}
		
		int total = queryTotal.list().size();
		pageModel.setTotalCount(total);
		
		i=0;
		Query query2 = currentSession().createQuery(sqlTotal);
		query2.setString(i++, "N");
		if( params.get("beginDate") != null && StringUtils.isNotEmpty( (String)params.get("beginDate") ) ){
			query2.setString(i++, (String)params.get("beginDate"));
		}
		if( params.get("endDate") != null && StringUtils.isNotEmpty( (String)params.get("endDate") ) ){
			query2.setString(i++, (String)params.get("endDate"));
		}
		
		query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
		query2.setMaxResults(pageModel.getPageSize());
		
		pageModel.setResult(query2.list());
		
		return pageModel;
	}

}
