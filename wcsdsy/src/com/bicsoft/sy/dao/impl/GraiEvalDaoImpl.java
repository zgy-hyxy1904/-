package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.GraiEvalDao;
import com.bicsoft.sy.entity.GraiEval;
import com.bicsoft.sy.model.PageModel;

@Repository
public class GraiEvalDaoImpl extends BaseDaoImpl<GraiEval, Serializable>  implements GraiEvalDao {
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		String sqlTotal = " FROM " + entityName + " WHERE deleteFlag=? ";
		StringBuffer whereStr = new StringBuffer("");
		if( params.get("year") != null && StringUtils.isNotEmpty( (String)params.get("year") ) ){
			whereStr.append(" AND year=?");
		}
		sqlTotal += whereStr.toString();
		
		int i=0;
		Query queryTotal = currentSession().createQuery(sqlTotal);
		queryTotal.setString(i++, "N");
		if( params.get("year") != null && StringUtils.isNotEmpty( (String)params.get("year") ) ){
			queryTotal.setString(i++, (String)params.get("year"));
		}
		
		int total = queryTotal.list().size();
		pageModel.setTotalCount(total);
		
		i=0;
		Query query2 = currentSession().createQuery(sqlTotal);
		query2.setString(i++, "N");
		if( params.get("year") != null && StringUtils.isNotEmpty( (String)params.get("year") ) ){
			query2.setString(i++, (String)params.get("year"));
		}
		
		query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
		query2.setMaxResults(pageModel.getPageSize());
		
		pageModel.setResult(query2.list());
		
		return pageModel;
	}

	@Override
	public Map<String, Float> getInfo(String year) {
		GraiEval graiEval = null;
		List<GraiEval> results = this.queryByHQL("from GraiEval where deleteFlag='N' and year='" + year + "'" , null);
		if(results == null || results.size() < 1){
			try {
				graiEval = new GraiEval();
			} catch (RuntimeException e) {
				e.printStackTrace();
				graiEval = new GraiEval();
			}
		}else{
			graiEval = results.get(0);
		}
		Map<String, Float> map = new HashMap<String, Float>();
		map.put("min", graiEval.getMinYield() == null ? 0 : graiEval.getMinYield());
		map.put("max", graiEval.getMaxYield() == null ? 0 : graiEval.getMaxYield());
		map.put("rate", graiEval.getMilledriceRate()==null ? 0 : graiEval.getMilledriceRate());
		
		return map;
	}
	
	public double getTotalWeight(String year, String companyCode){
		String sql = "select sum(ThisWeight) as totalWeight from b_GrainReg where deleteFlag='N' and Year=? and CompanyCode=?";
		Query query = currentSession().createSQLQuery(sql);
		query.setString(0, year);
		query.setString(1, companyCode);
		Object result = query.uniqueResult();
		double totalWeight = 0;
		if(result != null) totalWeight = (double)result;
		return totalWeight;
	}
}
