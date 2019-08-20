package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.SampleDao;
import com.bicsoft.sy.entity.Quality;
import com.bicsoft.sy.entity.Sample;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SampleModel;
import com.bicsoft.sy.util.StringUtil;

@Repository
public class SampleDaoImpl extends BaseDaoImpl<Sample, Serializable> implements SampleDao {
	
	public List<Sample> getSampleByBatchNo(String batchNo, String productCode){
		String hql = "from Sample where batchNo = ? and productCode = ?";
		Query query = currentSession().createQuery(hql);
		query.setString(0, batchNo);
		query.setString(1, productCode);
		return query.list();
	}
	
	public PageModel queryForPageModel(PageModel pageModel, SampleModel sampleModel){
		StringBuffer sqlTotal = new StringBuffer(" select * from b_Samplings where 1 = 1 ");
		
		String beginDate = sampleModel.getBeginDate();
		if(!StringUtil.isNullOrEmpty(beginDate)) beginDate = beginDate + " 00:00:00";
		String endDate = sampleModel.getEndDate();
		if(!StringUtil.isNullOrEmpty(endDate)) endDate = endDate+" 23:59:59";
		
		if(!StringUtil.isNullOrEmpty(sampleModel.getYear())) sqlTotal.append(" and year = ? ");
		if(!StringUtil.isNullOrEmpty(sampleModel.getCompanyCode())) sqlTotal.append(" and companyCode = ? ");
		if(!StringUtil.isNullOrEmpty(beginDate)) sqlTotal.append(" and date_format(deliveryDate, '%Y-%m-%d') >= ? ");
		if(!StringUtil.isNullOrEmpty(endDate)) sqlTotal.append(" and date_format(deliveryDate, '%Y-%m-%d') <= ? ");
		if(!StringUtil.isNullOrEmpty(sampleModel.getBatchNo())) sqlTotal.append(" and batchNo = ? ");
		if(!StringUtil.isNullOrEmpty(sampleModel.getQualityStatus())) sqlTotal.append(" and InspectStatus = ? ");
		
		int i=0;
		Query queryTotal = currentSession().createSQLQuery(sqlTotal.toString());
		if(!StringUtil.isNullOrEmpty(sampleModel.getYear())) queryTotal.setString(i++, sampleModel.getYear());
		if(!StringUtil.isNullOrEmpty(sampleModel.getCompanyCode())) queryTotal.setString(i++, sampleModel.getCompanyCode());
		if(!StringUtil.isNullOrEmpty(beginDate))queryTotal.setString(i++, beginDate);
		if(!StringUtil.isNullOrEmpty(endDate)) queryTotal.setString(i++, endDate);
		if(!StringUtil.isNullOrEmpty(sampleModel.getBatchNo())) queryTotal.setString(i++, sampleModel.getBatchNo());
		if(!StringUtil.isNullOrEmpty(sampleModel.getQualityStatus())) queryTotal.setString(i++, sampleModel.getQualityStatus());
		
		int total = queryTotal.list().size();
		pageModel.setTotalCount(total);
		
		i=0;
		sqlTotal.append(" order by DeliveryDate desc ");
		Query query2 = currentSession().createSQLQuery(sqlTotal.toString());
		if(!StringUtil.isNullOrEmpty(sampleModel.getYear())) query2.setString(i++, sampleModel.getYear());
		if(!StringUtil.isNullOrEmpty(sampleModel.getCompanyCode())) query2.setString(i++, sampleModel.getCompanyCode());
		if(!StringUtil.isNullOrEmpty(beginDate))query2.setString(i++, beginDate);
		if(!StringUtil.isNullOrEmpty(endDate)) query2.setString(i++, endDate);
		if(!StringUtil.isNullOrEmpty(sampleModel.getBatchNo())) query2.setString(i++, sampleModel.getBatchNo());
		if(!StringUtil.isNullOrEmpty(sampleModel.getQualityStatus())) query2.setString(i++, sampleModel.getQualityStatus());
		query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
		query2.setMaxResults(pageModel.getPageSize());
		
		pageModel.setResult(query2.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
		return pageModel;
	}
	
	//已经激活的产量
	public double getActivateSampleYield(String year, String companyCode){
		double yield = 0;
		StringBuffer sql = new StringBuffer("select sum(ProductNum * m_product.Weight) * 2 activeYield from b_Samplings left join m_product on m_product.productCode = b_Samplings.productCode");
		sql.append(" where b_Samplings.InspectStatus='02' and b_Samplings.year=? and b_Samplings.companyCode=? and m_product.companyCode = ?");
		Query query = currentSession().createSQLQuery(sql.toString());
		query.setString(0, year);
		query.setString(1, companyCode);
		query.setString(2, companyCode);
		Object result = query.uniqueResult();
		if(result != null) yield = (double)result;
		return yield;
	}
	
	//已申请产量
	public double getSampleYield(String year, String companyCode){
		double yield = 0;
		StringBuffer sql = new StringBuffer("select sum(ProductNum * m_product.Weight) * 2 activeYield from b_Samplings left join m_product on m_product.productCode = b_Samplings.productCode");
		sql.append(" where b_Samplings.year=? and b_Samplings.companyCode=? and m_product.companyCode = ?");
		Query query = currentSession().createSQLQuery(sql.toString());
		query.setString(0, year);
		query.setString(1, companyCode);
		query.setString(2, companyCode);
		Object result = query.uniqueResult();
		if(result != null) yield = (double)result;
		return yield;
	}
	
	public Sample getSampleBySecurityCode(String securityCode){
		String hql = "from Sample where securityCode=? ";
		Query query = currentSession().createQuery(hql);
		query.setString(0, securityCode);
		query.setMaxResults(1);
		List<Sample> list = query.list();
		if(list != null && list.size() > 0) return list.get(0);
		else return null;
	}
	
}