package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.QualityDao;
import com.bicsoft.sy.entity.Quality;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.QualityModel;
import com.bicsoft.sy.util.StringUtil;

@Repository
public class QualityDaoImpl extends BaseDaoImpl<Quality, Serializable> implements QualityDao {
	
	public PageModel queryForPageModel(PageModel pageModel, QualityModel qualityModel){
		int i=0;
		String sqlTotal = " from Quality where 1 = 1 ";
		
		String beginDate = qualityModel.getBeginDate();
		if(!StringUtil.isNullOrEmpty(beginDate)) beginDate = beginDate + " 00:00:00";
		String endDate = qualityModel.getEndDate();
		if(!StringUtil.isNullOrEmpty(endDate)) endDate = endDate+" 23:59:59";
		
		if(!StringUtil.isNullOrEmpty(qualityModel.getYear())) sqlTotal += " and year = ? ";
		if(!StringUtil.isNullOrEmpty(qualityModel.getCompanyCode())) sqlTotal += " and companyCode = ? ";
		if(!StringUtil.isNullOrEmpty(beginDate)) sqlTotal += " and  date_format(inspectDate, '%Y-%m-%d %T') >= ? ";
		if(!StringUtil.isNullOrEmpty(endDate)) sqlTotal += " and  date_format(inspectDate, '%Y-%m-%d %T') <= ? ";
		if(!StringUtil.isNullOrEmpty(qualityModel.getQualityStatus())) sqlTotal += " and inspectStatus = ? ";
		
		Query queryTotal = currentSession().createQuery(sqlTotal);
		if(!StringUtil.isNullOrEmpty(qualityModel.getYear())) queryTotal.setString(i++, qualityModel.getYear());
		if(!StringUtil.isNullOrEmpty(qualityModel.getCompanyCode())) queryTotal.setString(i++, qualityModel.getCompanyCode());
		if(!StringUtil.isNullOrEmpty(beginDate)) queryTotal.setString(i++, beginDate);
		if(!StringUtil.isNullOrEmpty(endDate)) queryTotal.setString(i++, endDate);
		if(!StringUtil.isNullOrEmpty(qualityModel.getQualityStatus())) queryTotal.setString(i++, qualityModel.getQualityStatus());
		int total = queryTotal.list().size();
		pageModel.setTotalCount(total);
		
		i=0;
		sqlTotal += " order by inspectDate desc ";
		Query query2 = currentSession().createQuery(sqlTotal);
		if(!StringUtil.isNullOrEmpty(qualityModel.getYear())) query2.setString(i++, qualityModel.getYear());
		if(!StringUtil.isNullOrEmpty(qualityModel.getCompanyCode())) query2.setString(i++, qualityModel.getCompanyCode());
		if(!StringUtil.isNullOrEmpty(beginDate)) query2.setString(i++, beginDate);
		if(!StringUtil.isNullOrEmpty(endDate)) query2.setString(i++, endDate);
		if(!StringUtil.isNullOrEmpty(qualityModel.getQualityStatus())) query2.setString(i++, qualityModel.getQualityStatus());
		query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
		query2.setMaxResults(pageModel.getPageSize());
		
		pageModel.setResult(query2.list());
		return pageModel;
	}
	
	public Quality getQualityByBatchNo(String batchNo){
		String hql = " from Quality where productionBatch = ? ";
		Query query = currentSession().createQuery(hql);
		query.setString(0, batchNo);
		query.setMaxResults(1);
		List<Quality> list = query.list();
		if(list != null && list.size() > 0) return list.get(0);
		else return null;
	}
}