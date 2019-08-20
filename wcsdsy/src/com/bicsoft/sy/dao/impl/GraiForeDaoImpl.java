package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.GraiForeDao;
import com.bicsoft.sy.entity.GraiFore;
import com.bicsoft.sy.model.PageModel;

@Repository
public class GraiForeDaoImpl extends BaseDaoImpl<GraiFore, Serializable> implements GraiForeDao {
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
		if( params.get("companyCode") != null && StringUtils.isNotEmpty( (String)params.get("companyCode") ) ){
			whereStr.append(" AND companyCode=?");
		}
		if( params.get("beginDate") != null && StringUtils.isNotEmpty( (String)params.get("beginDate") ) ){
			whereStr.append(" AND date_format(forecastDate, '%Y-%m-%d') >=?");
		}
		if( params.get("endDate") != null && StringUtils.isNotEmpty( (String)params.get("endDate") ) ){
			whereStr.append(" AND date_format(forecastDate, '%Y-%m-%d') <=?");
		}
		sqlTotal += whereStr.toString();
		
		//统计数据
		int i=0;
		StringBuffer sumSQL = new StringBuffer("select IFNULL(sum(ActualMu),0) as actualMu,IFNULL(sum(MeasurementMu),0) as measurementMu,IFNULL(sum(minEstimateTotalYield),0) as minEstimateTotalYield,IFNULL(sum(maxEstimateTotalYield),0) as maxEstimateTotalYield from b_GrainForecastH where deleteFlag=? ");
		sumSQL.append(whereStr);
		Query sumQuery = currentSession().createSQLQuery(sumSQL.toString());
		sumQuery.setString(i++, "N");
		if( params.get("year") != null && StringUtils.isNotEmpty( (String)params.get("year") ) ){
			sumQuery.setString(i++, (String)params.get("year"));
		}
		if( params.get("companyCode") != null && StringUtils.isNotEmpty( (String)params.get("companyCode") ) ){
			sumQuery.setString(i++, (String)params.get("companyCode"));
		}
		if( params.get("beginDate") != null && StringUtils.isNotEmpty( (String)params.get("beginDate") ) ){
			sumQuery.setString(i++, (String)params.get("beginDate"));
		}
		if( params.get("endDate") != null && StringUtils.isNotEmpty( (String)params.get("endDate") ) ){
			sumQuery.setString(i++, (String)params.get("endDate"));
		}
		
		List<Map> sumResult = sumQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		Map sumMap = new HashMap();
		if(sumResult == null || sumResult.size()==0){
			sumMap.put("actualMu", "0");
			sumMap.put("MeasurementMu", "0");
			sumMap.put("minEstimateTotalYield", "0");
			sumMap.put("maxEstimateTotalYield", "0");
		}else{
			sumMap.put("actualMu", sumResult.get(0).get("actualMu"));
			sumMap.put("measurementMu", sumResult.get(0).get("measurementMu"));
			sumMap.put("minEstimateTotalYield", sumResult.get(0).get("minEstimateTotalYield"));
			sumMap.put("maxEstimateTotalYield", sumResult.get(0).get("maxEstimateTotalYield"));
		}
		pageModel.setData(sumMap);
		
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
		
		//是否是excel导出，如果是的话不分页，直接返回结果
		if("y".equals(params.get("expExcel"))){
			pageModel.setResult(queryTotal.list());
			return pageModel;
		}
		int total = queryTotal.list().size();
		pageModel.setTotalCount(total);
		
		i=0;
		Query query2 = currentSession().createQuery(sqlTotal);
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
		
		query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
		query2.setMaxResults(pageModel.getPageSize());
		
		pageModel.setResult(query2.list());
		
		return pageModel;
	}

}
