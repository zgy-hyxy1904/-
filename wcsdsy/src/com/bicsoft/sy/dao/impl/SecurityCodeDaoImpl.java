package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.SecurityCodeDao;
import com.bicsoft.sy.entity.SecurityCode;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SecurityCodeModel;
import com.bicsoft.sy.util.StringUtil;

@Repository
public class SecurityCodeDaoImpl extends BaseDaoImpl<SecurityCode, Serializable> implements SecurityCodeDao {
	
	public PageModel queryForPageModel(PageModel pageModel, SecurityCodeModel securityCodeModel){
		int i=0;
		String sqlTotal = " select Year,CompanyCode,BatchNo,ProductCode,ProduceDate,sum(ProductNum) as ProductNum,InspectStatus from b_Samplings where 1=1 ";
		
		if(!StringUtil.isNullOrEmpty(securityCodeModel.getYear())) sqlTotal += " and year = ? ";
		if(!StringUtil.isNullOrEmpty(securityCodeModel.getCompanyCode())) sqlTotal += " and companyCode = ? ";
		if(!StringUtil.isNullOrEmpty(securityCodeModel.getProduceDate())) sqlTotal += " and  date_format(produceDate, '%Y-%m-%d') = ? ";
		sqlTotal += " group by Year,CompanyCode,BatchNo,ProductCode,ProduceDate,InspectStatus";
		
		Query queryTotal = currentSession().createSQLQuery(sqlTotal);
		if(!StringUtil.isNullOrEmpty(securityCodeModel.getYear())) queryTotal.setString(i++, securityCodeModel.getYear());
		if(!StringUtil.isNullOrEmpty(securityCodeModel.getCompanyCode())) queryTotal.setString(i++, securityCodeModel.getCompanyCode());
		if(!StringUtil.isNullOrEmpty(securityCodeModel.getProduceDate())) queryTotal.setString(i++, securityCodeModel.getProduceDate());
		int total = queryTotal.list().size();
		pageModel.setTotalCount(total);
		
		i=0;
		sqlTotal += " order by ProduceDate desc ";
		Query query2 = currentSession().createSQLQuery(sqlTotal);
		if(!StringUtil.isNullOrEmpty(securityCodeModel.getYear())) query2.setString(i++, securityCodeModel.getYear());
		if(!StringUtil.isNullOrEmpty(securityCodeModel.getCompanyCode())) query2.setString(i++, securityCodeModel.getCompanyCode());
		if(!StringUtil.isNullOrEmpty(securityCodeModel.getProduceDate())) query2.setString(i++, securityCodeModel.getProduceDate());
		query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
		query2.setMaxResults(pageModel.getPageSize());
		
		pageModel.setResult(query2.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
		return pageModel;
	}
	
	public SecurityCode getSecurityCode(String year, String companyCode){
		String sql = " from SecurityCode where year = ? and companyCode=?";
		Query query = currentSession().createQuery(sql);
		query.setString(0, year);
		query.setString(1, companyCode);
		List<?> list = query.list();
		if(list != null && list.size() > 0) return (SecurityCode)list.get(0);
		else return null;
	}
}