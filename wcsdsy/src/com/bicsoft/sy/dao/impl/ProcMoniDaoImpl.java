package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.bicsoft.sy.dao.ProcMoniDao;
import com.bicsoft.sy.entity.Mfile;
import com.bicsoft.sy.entity.ProcMoni;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.util.DictUtil;

@Repository
public class ProcMoniDaoImpl extends BaseDaoImpl<ProcMoni, Serializable>  implements ProcMoniDao {

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
		whereStr.append(" order by createDate desc ");
		sqlTotal += whereStr.toString();
		
		int i=0;
		Query queryTotal = currentSession().createQuery(sqlTotal);
		queryTotal.setString(i++, "N");
		if( params.get("year") != null && StringUtils.isNotEmpty( (String)params.get("year") ) ){
			queryTotal.setString(i++, (String)params.get("year"));
		}
		if( params.get("companyCode") != null && StringUtils.isNotEmpty( (String)params.get("companyCode") ) ){
			queryTotal.setString(i++, (String)params.get("companyCode"));
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
		
		query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
		query2.setMaxResults(pageModel.getPageSize());
		
		pageModel.setResult(query2.list());
		
		return pageModel;
	}
	
	
	public Map getProcMoniList(String year, String companyCode){
		HashMap result =  new HashMap();
		
		String sql = "select BizType,BizCode from b_process_monitoring where Year=? and CompanyCode=? ";
		Query query = currentSession().createSQLQuery(sql);
		query.setString(0 , year);
		query.setString(1, companyCode);
		List<Map> moniList = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		for(Map map : moniList){
			String bizType = (String)map.get("BizType");
			String bizCode = (String)map.get("BizCode");
			String imgBizType = DictUtil.getImgBizType(bizType);
			
			//图片
			String fileSQL = "from Mfile where bizType=? and bizCode=? and fileType=? and deleteFlag=?";
			query = currentSession().createQuery(fileSQL);
			query.setString(0 , imgBizType);
			query.setString(1, bizCode);
			query.setString(2, "01");
			query.setString(3, "N");
			List<Mfile> fileList = query.list();
			
			//视频
			String videlSQL = "from Mfile where bizType=? and bizCode=? and fileType=? and deleteFlag=?";
			query = currentSession().createQuery(videlSQL);
			query.setString(0 , imgBizType);
			query.setString(1, bizCode);
			query.setString(2, "02");
			query.setString(3, "N");
			List<Mfile> videlList = query.list();
			
			result.put("f_"+bizType+"01", fileList);
			result.put("f_"+bizType+"02", videlList);
		}
		return result;
	}
	
	public boolean hasProcMoniFile(String year, String companyCode){
		String SQL = "from Mfile where  bizCode in (select bizCode from ProcMoni where year=? and companyCode=?) and deleteFlag=? ";
		Query query = currentSession().createQuery(SQL);
		query.setString(0, year);
		query.setString(1, companyCode);
		query.setString(2, "N");
		query.setMaxResults(1);
		List<?> result = query.list();
		return (result != null && result.size()>0); 
	}
}
