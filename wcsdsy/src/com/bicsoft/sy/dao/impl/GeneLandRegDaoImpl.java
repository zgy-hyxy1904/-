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
import com.bicsoft.sy.dao.GeneLandRegDao;
import com.bicsoft.sy.entity.GeneLandReg;
import com.bicsoft.sy.entity.SoilMoni;
import com.bicsoft.sy.entity.SpecLandDetail;
import com.bicsoft.sy.model.PageModel;

@Repository
public class GeneLandRegDaoImpl extends BaseDaoImpl<GeneLandReg, Serializable> implements GeneLandRegDao {
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<SoilMoni> getSoilMoniList() {
		return null;
	}
	
	public PageModel queryForPageModel(String entityName,
		Map<String, Object> params, PageModel pageModel) {
		String sqlTotal = " FROM " + entityName + " WHERE deleteFlag=? ";
		StringBuffer whereStr = new StringBuffer("");
		if( params.get("year") != null && StringUtils.isNotEmpty( (String)params.get("year") ) ){
			whereStr.append(" AND year=?");
		}
		if( params.get("companyCode") != null && StringUtils.isNotEmpty( (String)params.get("companyCode") ) ){
			whereStr.append(" AND companyCode=?");
		}
		if( params.get("beginDate") != null && StringUtils.isNotEmpty( (String)params.get("beginDate") ) ){
			whereStr.append(" AND date_format(createDate, '%Y-%m-%d') >=?");
		}
		if( params.get("endDate") != null && StringUtils.isNotEmpty( (String)params.get("endDate") ) ){
			whereStr.append(" AND date_format(createDate, '%Y-%m-%d') <=?");
		}
		if( params.get("status") != null && StringUtils.isNotEmpty( (String)params.get("status") ) ){
			whereStr.append(" AND status=?");
		}
		
		//统计数据
		int i=0;
		StringBuffer sumSQL = new StringBuffer("select sum(ArchiveAcreage) from b_GeneralLandRegD where ");
		sumSQL.append("hid in(select id from b_GeneralLandRegH WHERE deleteFlag=?");
		sumSQL.append(whereStr);
		sumSQL.append(")");
		Query query2 = currentSession().createSQLQuery(sumSQL.toString());
		query2.setString(i++, "N");
		if( params.get("year") != null && StringUtils.isNotEmpty( (String)params.get("year") ) ){
			query2.setString(i++, (String)params.get("year"));
		}
		if( params.get("companyCode") != null && StringUtils.isNotEmpty( (String)params.get("companyCode") ) ){
			query2.setString(i++, (String)params.get("companyCode"));
		}
		if( params.get("beginDate") != null && StringUtils.isNotEmpty( (String)params.get("beginDate") ) ){
			query2.setString(i++, (String)params.get("beginDate"));
		}
		if( params.get("endDate") != null && StringUtils.isNotEmpty( (String)params.get("endDate") ) ){
			query2.setString(i++, (String)params.get("endDate"));
		}
		if( params.get("status") != null && StringUtils.isNotEmpty( (String)params.get("status") ) ){
			query2.setString(i++, (String)params.get("status"));
		}
		
		Object result = query2.uniqueResult();
		double totalArea = 0;
		if(result != null) totalArea = (double)result;
		pageModel.setData(totalArea);
		
		whereStr.append(" order by createDate desc ");
		sqlTotal += whereStr.toString();
		
		i=0;
		Query queryTotal = currentSession().createQuery(sqlTotal);
		queryTotal.setString(i++, "N");
		if( params.get("year") != null && StringUtils.isNotEmpty( (String)params.get("year") ) ){
			queryTotal.setString(i++, (String)params.get("year"));
		}
		if( params.get("companyCode") != null && StringUtils.isNotEmpty( (String)params.get("companyCode") ) ){
			queryTotal.setString(i++, (String)params.get("companyCode"));
		}
		if( params.get("beginDate") != null && StringUtils.isNotEmpty( (String)params.get("beginDate") ) ){
			queryTotal.setString(i++, (String)params.get("beginDate"));
		}
		if( params.get("endDate") != null && StringUtils.isNotEmpty( (String)params.get("endDate") ) ){
			queryTotal.setString(i++, (String)params.get("endDate"));
		}
		if( params.get("status") != null && StringUtils.isNotEmpty( (String)params.get("status") ) ){
			queryTotal.setString(i++, (String)params.get("status"));
		}
		
		int total = queryTotal.list().size();
		pageModel.setTotalCount(total);
		
		i=0;
		query2 = currentSession().createQuery(sqlTotal);
		query2.setString(i++, "N");
		if( params.get("year") != null && StringUtils.isNotEmpty( (String)params.get("year") ) ){
			query2.setString(i++, (String)params.get("year"));
		}
		if( params.get("companyCode") != null && StringUtils.isNotEmpty( (String)params.get("companyCode") ) ){
			query2.setString(i++, (String)params.get("companyCode"));
		}
		if( params.get("beginDate") != null && StringUtils.isNotEmpty( (String)params.get("beginDate") ) ){
			query2.setString(i++, (String)params.get("beginDate"));
		}
		if( params.get("endDate") != null && StringUtils.isNotEmpty( (String)params.get("endDate") ) ){
			query2.setString(i++, (String)params.get("endDate"));
		}
		if( params.get("status") != null && StringUtils.isNotEmpty( (String)params.get("status") ) ){
			query2.setString(i++, (String)params.get("status"));
		}
		
		query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
		query2.setMaxResults(pageModel.getPageSize());
		
		pageModel.setResult(query2.list());
		
		return pageModel;
	}

	@Override
	public SpecLandDetail getGeneLandDetail(String hid) {
		
		return null;
	}

	@Override
	public List<SpecLandDetail> getGeneLandDetailList(String hid) {
		
		return null;
	}	
}
