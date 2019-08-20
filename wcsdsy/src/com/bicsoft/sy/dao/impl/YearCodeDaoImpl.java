package com.bicsoft.sy.dao.impl;

import java.io.Serializable;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.YearCodeDao;
import com.bicsoft.sy.entity.YearCode;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.util.StringUtil;

@Repository
public class YearCodeDaoImpl   extends BaseDaoImpl<YearCode, Serializable> implements YearCodeDao{
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public PageModel queryForPageModel(PageModel pageModel, String yearName){
		String sqlTotal = " from YearCode where deleteFlag=? ";
		if(!StringUtil.isNullOrEmpty(yearName)) sqlTotal += " and yearName like ? ";
		
		int i=0;
		Query queryTotal = currentSession().createQuery(sqlTotal);
		queryTotal.setString(i++, "N");
		if(!StringUtil.isNullOrEmpty(yearName)) queryTotal.setString(i++, "%"+yearName+"%");
		int total = queryTotal.list().size();
		pageModel.setTotalCount(total);
		
		i=0;
		sqlTotal += " order by createDate desc "; 
		Query query2 = currentSession().createQuery(sqlTotal);
		query2.setString(i++, "N");
		if(!StringUtil.isNullOrEmpty(yearName)) query2.setString(i++, "%"+yearName+"%");
		query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
		query2.setMaxResults(pageModel.getPageSize());
		
		pageModel.setResult(query2.list());
		return pageModel;
	}
}
