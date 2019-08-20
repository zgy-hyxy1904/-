package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.ProductDao;
import com.bicsoft.sy.entity.Product;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.util.StringUtil;

@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product, Serializable> implements ProductDao {
	
	public List<?> getProductTypeList(String companyCode){
		String sql = "from Product where CompanyCode=? and DeleteFlag=?";
		Query query = currentSession().createQuery(sql);
		query.setString(0, companyCode);
		query.setString(1, "N");
		return query.list();
	}
	
	public List<?> getProductTypeList(String companyCode, String typeCode){
		String sql = " select ClassCode,ClassName from M_PACKING where CompanyCode=? and TypeCode = ? group by ClassCode,ClassName";
		Query query = currentSession().createSQLQuery(sql);
		query.setString(0, companyCode);
		query.setString(1, typeCode);

		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	public List<?> getProductTypeList(String companyCode, String typeCode, String classCode){
		String sql = " select SpecCode,SpecName,SpecWeight from M_PACKING where CompanyCode=? and TypeCode = ? and ClassCode = ? group by SpecCode,SpecName,SpecWeight";
		Query query = currentSession().createSQLQuery(sql);
		query.setString(0, companyCode);
		query.setString(1, typeCode);
		query.setString(2, classCode);

		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	public Product getProduct(String companyCode, String productCode){
		String sql = "from Product where companyCode=? and productCode=?";
		Query query = currentSession().createQuery(sql);
		query.setString(0, companyCode);
		query.setString(1, productCode);
		List<?> list = query.list();
		if(list != null && list.size()>0 ) return (Product)list.get(0);
		else return null;
	}
	
	public PageModel queryForPageModel(PageModel pageModel, String companyCode, String productName){
		String sqlTotal = " from Product where deleteFlag=? ";
		if(!StringUtil.isNullOrEmpty(companyCode)) sqlTotal += " and companyCode=? ";
		if(!StringUtil.isNullOrEmpty(productName)) sqlTotal += " and productName like ? ";
		
		int i=0;
		Query queryTotal = currentSession().createQuery(sqlTotal);
		queryTotal.setString(i++, "N");
		if(!StringUtil.isNullOrEmpty(companyCode)) queryTotal.setString(i++, companyCode);
		if(!StringUtil.isNullOrEmpty(productName)) queryTotal.setString(i++, "%"+productName+"%");
		int total = queryTotal.list().size();
		pageModel.setTotalCount(total);
		
		i=0;
		sqlTotal += " order by createDate desc "; 
		Query query2 = currentSession().createQuery(sqlTotal);
		query2.setString(i++, "N");
		if(!StringUtil.isNullOrEmpty(companyCode)) query2.setString(i++, companyCode);
		if(!StringUtil.isNullOrEmpty(productName)) query2.setString(i++, "%"+productName+"%");
		query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
		query2.setMaxResults(pageModel.getPageSize());
		
		pageModel.setResult(query2.list());
		return pageModel;
	}
}