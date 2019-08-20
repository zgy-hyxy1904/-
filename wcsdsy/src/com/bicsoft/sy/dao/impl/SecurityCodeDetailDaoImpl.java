package com.bicsoft.sy.dao.impl;

import java.io.Serializable;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.SecurityCodeDetailDao;
import com.bicsoft.sy.entity.SecurityCodeDetail;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.util.StringUtil;
@Repository
public class SecurityCodeDetailDaoImpl extends BaseDaoImpl<SecurityCodeDetail, Serializable> implements SecurityCodeDetailDao {

	public PageModel queryForPageModel(PageModel pageModel, String year, String companyCode){
		StringBuffer sqlTotal = new StringBuffer(" from SecurityCodeDetail where deleteFlag=? ");
		if(!StringUtil.isNullOrEmpty(year)) sqlTotal.append("and year=? ");
		if(!StringUtil.isNullOrEmpty(companyCode)) sqlTotal.append("and companyCode=? ");
		sqlTotal.append(" order by applyDate desc");
		int i=0;
		Query queryTotal = currentSession().createQuery(sqlTotal.toString());
		queryTotal.setString(i++, "N");
		if(!StringUtil.isNullOrEmpty(year)) queryTotal.setString(i++, year);
		if(!StringUtil.isNullOrEmpty(companyCode)) queryTotal.setString(i++, companyCode);
		int total = queryTotal.list().size();
		pageModel.setTotalCount(total);

		i=0;
		Query query2 = currentSession().createQuery(sqlTotal.toString());
		query2.setString(i++, "N");
		if(!StringUtil.isNullOrEmpty(year)) query2.setString(i++, year);
		if(!StringUtil.isNullOrEmpty(companyCode)) query2.setString(i++, companyCode);

		query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
		query2.setMaxResults(pageModel.getPageSize());

		pageModel.setResult(query2.list());
		return pageModel;
	}
}