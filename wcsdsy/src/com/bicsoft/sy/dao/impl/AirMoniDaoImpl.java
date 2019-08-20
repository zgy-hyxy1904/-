package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.AirMoniDao;
import com.bicsoft.sy.entity.AirMoni;
import com.bicsoft.sy.model.PageModel;

@Repository
public class AirMoniDaoImpl extends BaseDaoImpl<AirMoni, Serializable>  implements AirMoniDao {
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		String sqlTotal = " FROM " + entityName + " WHERE deleteFlag=? ";
		StringBuffer whereStr = new StringBuffer("");
		if( params.get("beginDate") != null && StringUtils.isNotEmpty( (String)params.get("beginDate") ) ){
			whereStr.append(" AND  date_format(monitorDate, '%Y-%m-%d') >=?");
		}
		if( params.get("endDate") != null && StringUtils.isNotEmpty( (String)params.get("endDate") ) ){
			whereStr.append(" AND  date_format(monitorDate, '%Y-%m-%d') <=?");
		}
		
		
		sqlTotal += whereStr.toString();
		sqlTotal += " order by monitorDate desc";//排序
		
		int i=0;
		Query queryTotal = currentSession().createQuery(sqlTotal);
		queryTotal.setString(i++, "N");
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
	
	@Override
	public AirMoni getAirMoni(int id) {
		return (AirMoni) currentSession().get(AirMoni.class, Integer.valueOf(id));
	}

	@Override
	public void save(AirMoni airMoni) {
		currentSession().saveOrUpdate(airMoni);
	}

	@Override
	public void delete(int id) {
		Session session = currentSession();
		AirMoni airMoni = (AirMoni) session.get(AirMoni.class, Integer.valueOf(id));
		session.delete(airMoni);
	}

	@Override
	public PageModel findPage(PageModel page, String name) {
		String sqlTotal = " select count(*) from b_InputReg  ";
		if ((name != null) && (!"".equals(name))) {
			sqlTotal = sqlTotal + " where deleteFlag='N' and userName like ? ";
		}
		Query queryTotal = currentSession().createSQLQuery(sqlTotal);
		if ((name != null) && (!"".equals(name))) {
			queryTotal.setString(0, "%" + name + "%");
		}
		int total = ((BigInteger) queryTotal.uniqueResult()).intValue();

		int first = (page.getPage() - 1) * page.getPageSize();
		int max = page.getPageSize();
		String sql = " select * from b_InputReg ";
		if ((name != null) && (!"".equals(name))) {
			sql = sql + " where deleteFlag='N' and userName like ? ";
		}
		Query query = currentSession().createSQLQuery(sql);
		if ((name != null) && (!"".equals(name))) {
			query.setString(0, "%" + name + "%");
		}
		query.setFirstResult(first).setMaxResults(max);
		List list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		page.setResult(list);
		page.setTotalCount(total);
		
		return page;
	}

	@Override
	public List<AirMoni> getAirMoniList() {
		String sql = " from AirMoni where deleteFlag='N' ";
		Query query = currentSession().createQuery(sql);
		List list = query.list();
		
		return list;
	}

}
