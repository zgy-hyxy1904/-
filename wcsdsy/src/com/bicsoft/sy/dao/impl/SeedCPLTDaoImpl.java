package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.SeedCPLTDao;
import com.bicsoft.sy.entity.SeedCPLT;
import com.bicsoft.sy.model.PageModel;

@Repository
public class SeedCPLTDaoImpl extends BaseDaoImpl<SeedCPLT, Serializable> implements SeedCPLTDao {
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		String sqlTotal = " FROM " + entityName + " WHERE deleteFlag=? ";
		StringBuffer whereStr = new StringBuffer("");
		if( params.get("companyCode") != null && StringUtils.isNotEmpty( (String)params.get("companyCode") ) ){
			whereStr.append(" AND companyCode =?");
		}
		if( params.get("settleStatus") != null && StringUtils.isNotEmpty( (String)params.get("settleStatus") ) ){
			whereStr.append(" AND settleStatus =?");
		}
		if( params.get("complaintBeginDate") != null && StringUtils.isNotEmpty( (String)params.get("complaintBeginDate") ) ){
			whereStr.append(" AND date_format(complaintDate, '%Y-%m-%d') >=?");
		}
		if( params.get("complaintEndDate") != null && StringUtils.isNotEmpty( (String)params.get("complaintEndDate") ) ){
			whereStr.append(" AND date_format(complaintDate, '%Y-%m-%d') <=?");
		}
		if( params.get("settleBeginDate") != null && StringUtils.isNotEmpty( (String)params.get("settleBeginDate") ) ){
			whereStr.append(" AND date_format(settleDate, '%Y-%m-%d') >=?");
		}
		if( params.get("settleEndDate") != null && StringUtils.isNotEmpty( (String)params.get("settleEndDate") ) ){
			whereStr.append(" AND date_format(settleDate, '%Y-%m-%d') <=?");
		}
		if( params.get("beginDate") != null && StringUtils.isNotEmpty( (String)params.get("beginDate") ) ){
			whereStr.append(" AND date_format(BlackListEndDate, '%Y-%m-%d') >=?");
		}
		if( params.get("endDate") != null && StringUtils.isNotEmpty( (String)params.get("endDate") ) ){
			whereStr.append(" AND date_format(BlackListEndDate, '%Y-%m-%d') <=?");
		}
		sqlTotal += whereStr.toString();
		sqlTotal += " order by complaintDate desc,settleDate desc,BlackListEndDate desc";
		
		int i=0;
		Query queryTotal = currentSession().createQuery(sqlTotal);
		queryTotal.setString(i++, "N");
		if( params.get("companyCode") != null && StringUtils.isNotEmpty( (String)params.get("companyCode") ) ){
			queryTotal.setString(i++, (String)params.get("companyCode"));
		}
		if( params.get("settleStatus") != null && StringUtils.isNotEmpty( (String)params.get("settleStatus") ) ){
			queryTotal.setString(i++, (String)params.get("settleStatus"));
		}
		if( params.get("complaintBeginDate") != null && StringUtils.isNotEmpty( (String)params.get("complaintBeginDate") ) ){
			queryTotal.setString(i++, (String)params.get("complaintBeginDate"));
		}
		if( params.get("complaintEndDate") != null && StringUtils.isNotEmpty( (String)params.get("complaintEndDate") ) ){
			queryTotal.setString(i++, (String)params.get("complaintEndDate"));
		}
		if( params.get("settleBeginDate") != null && StringUtils.isNotEmpty( (String)params.get("settleBeginDate") ) ){
			queryTotal.setString(i++, (String)params.get("settleBeginDate"));
		}
		if( params.get("settleEndDate") != null && StringUtils.isNotEmpty( (String)params.get("settleEndDate") ) ){
			queryTotal.setString(i++, (String)params.get("settleEndDate"));
		}
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
		if( params.get("companyCode") != null && StringUtils.isNotEmpty( (String)params.get("companyCode") ) ){
			query2.setString(i++, (String)params.get("companyCode"));
		}
		if( params.get("settleStatus") != null && StringUtils.isNotEmpty( (String)params.get("settleStatus") ) ){
			query2.setString(i++, (String)params.get("settleStatus"));
		}
		if( params.get("complaintBeginDate") != null && StringUtils.isNotEmpty( (String)params.get("complaintBeginDate") ) ){
			query2.setString(i++, (String)params.get("complaintBeginDate"));
		}
		if( params.get("complaintEndDate") != null && StringUtils.isNotEmpty( (String)params.get("complaintEndDate") ) ){
			query2.setString(i++, (String)params.get("complaintEndDate"));
		}
		if( params.get("settleBeginDate") != null && StringUtils.isNotEmpty( (String)params.get("settleBeginDate") ) ){
			query2.setString(i++, (String)params.get("settleBeginDate"));
		}
		if( params.get("settleEndDate") != null && StringUtils.isNotEmpty( (String)params.get("settleEndDate") ) ){
			query2.setString(i++, (String)params.get("settleEndDate"));
		}
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
