package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.PackingDao;
import com.bicsoft.sy.entity.Packing;

@Repository
public class PackingDaoImpl extends BaseDaoImpl<Packing, Serializable> implements PackingDao {
	
	public List<?> getPackingTypeList(String companyCode){
		String sql = " select TypeCode,TypeName from M_PACKING where CompanyCode=? group by TypeCode,TypeName";
		Query query = currentSession().createSQLQuery(sql);
		query.setString(0, companyCode);
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	public List<?> getPackingClassList(String companyCode, String typeCode){
		String sql = " select ClassCode,ClassName from M_PACKING where CompanyCode=? and TypeCode = ? group by ClassCode,ClassName";
		Query query = currentSession().createSQLQuery(sql);
		query.setString(0, companyCode);
		query.setString(1, typeCode);

		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	public List<?> getPackingSpectList(String companyCode, String typeCode, String classCode){
		String sql = " select SpecCode,SpecName,SpecWeight from M_PACKING where CompanyCode=? and TypeCode = ? and ClassCode = ? group by SpecCode,SpecName,SpecWeight";
		Query query = currentSession().createSQLQuery(sql);
		query.setString(0, companyCode);
		query.setString(1, typeCode);
		query.setString(2, classCode);

		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
}