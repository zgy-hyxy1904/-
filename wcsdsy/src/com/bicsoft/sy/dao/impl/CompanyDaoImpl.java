package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.CompanyDao;
import com.bicsoft.sy.entity.Company;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.util.StringUtil;

@Repository
public class CompanyDaoImpl  extends BaseDaoImpl<Company, Serializable> implements CompanyDao{
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public PageModel queryForPageModel(PageModel pageModel, String companyCode, String companyName, String companyType){
		String sqlTotal = " select ID,CompanyCode,CompanyName,Address,LegalPerson,ConnectName,ConnectPhone,CompanyType from M_COMPANY where DeleteFlag=? ";
		if(!StringUtil.isNullOrEmpty(companyCode)) sqlTotal += " and CompanyCode like ? ";
		if(!StringUtil.isNullOrEmpty(companyName)) sqlTotal += " and CompanyName like ? ";
		if(!StringUtil.isNullOrEmpty(companyType)) sqlTotal += " and companyType = ? ";
		
		int i=0;
		Query queryTotal = currentSession().createSQLQuery(sqlTotal);
		queryTotal.setString(i++, "N");
		if(!StringUtil.isNullOrEmpty(companyCode)) queryTotal.setString(i++, "%"+companyCode+"%");
		if(!StringUtil.isNullOrEmpty(companyName)) queryTotal.setString(i++, "%"+companyName+"%");
		if(!StringUtil.isNullOrEmpty(companyType)) queryTotal.setString(i++, companyType);
		int total = queryTotal.list().size();
		pageModel.setTotalCount(total);
		
		i=0;
		sqlTotal += " order by CreateDate desc "; 
		Query query2 = currentSession().createSQLQuery(sqlTotal);
		query2.setString(i++, "N");
		if(!StringUtil.isNullOrEmpty(companyCode)) query2.setString(i++, "%"+companyCode+"%");
		if(!StringUtil.isNullOrEmpty(companyName)) query2.setString(i++, "%"+companyName+"%");
		if(!StringUtil.isNullOrEmpty(companyType)) query2.setString(i++, companyType);
		query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
		query2.setMaxResults(pageModel.getPageSize());
		
		pageModel.setResult(query2.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
		return pageModel;
	}
}
