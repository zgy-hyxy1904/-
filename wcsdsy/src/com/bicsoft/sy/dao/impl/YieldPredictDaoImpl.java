package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.YieldPredictDao;
import com.bicsoft.sy.entity.YieldPredict;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.YieldPredictModel;
import com.bicsoft.sy.util.StringUtil;

@Repository
public class YieldPredictDaoImpl extends BaseDaoImpl<YieldPredict, Serializable> implements YieldPredictDao {

	public PageModel getYieldPredictList(PageModel pageModel, YieldPredictModel yieldPredictModel){
		String sqlTotal = " from YieldPredict where deleteFlag=? ";
		if(!StringUtil.isNullOrEmpty(yieldPredictModel.getCompanyCode())) sqlTotal += " and companyCode = ? ";
		if(!StringUtil.isNullOrEmpty(yieldPredictModel.getYear())) sqlTotal += " and year = ? ";

		sqlTotal +=" order by predictionDate desc";
		int i=0;
		Query queryTotal = currentSession().createQuery(sqlTotal);
		queryTotal.setString(i++, "N");
		if(!StringUtil.isNullOrEmpty(yieldPredictModel.getCompanyCode())) queryTotal.setString(i++, yieldPredictModel.getCompanyCode());
		if(!StringUtil.isNullOrEmpty(yieldPredictModel.getYear())) queryTotal.setString(i++, yieldPredictModel.getYear());
		int total = queryTotal.list().size();
		pageModel.setTotalCount(total);

		i=0;
		Query query2 = currentSession().createQuery(sqlTotal);
		query2.setString(i++, "N");
		if(!StringUtil.isNullOrEmpty(yieldPredictModel.getCompanyCode())) query2.setString(i++, yieldPredictModel.getCompanyCode());
		if(!StringUtil.isNullOrEmpty(yieldPredictModel.getYear())) query2.setString(i++, yieldPredictModel.getYear());
		query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
		query2.setMaxResults(pageModel.getPageSize());

		pageModel.setResult(query2.list());
		return pageModel;
	}

	public double getYieldRegistedTotalValue(String companyCode, String year){
		double total = 0;
		String sql = "select sum(YieldPredictionValue) from b_YieldPredictionH where companyCode=? and year=? and deleteFlag='N'";
		Query query = currentSession().createSQLQuery(sql);
		query.setString(0, companyCode);
		query.setString(1,  year);
		Object result = query.uniqueResult();
		if(result != null){
			total = (double)result;
		}
		return total;
	}
}