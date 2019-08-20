package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.LandPictureDao;
import com.bicsoft.sy.entity.LandPicture;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.util.StringUtil;

@Repository
public class LandPictureDaoImpl extends BaseDaoImpl<LandPicture, Serializable> implements LandPictureDao {
	
	public PageModel queryForPageModel(PageModel pageModel, String year, String companyCode){
		StringBuffer sql = new StringBuffer("select * from b_land_picture where DeleteFlag=? ");
		if(!StringUtil.isNullOrEmpty(year))  sql.append(" and Year=?");
		if(!StringUtil.isNullOrEmpty(companyCode))  sql.append(" and CompanyCode=?");
		int i=0;
		Query queryTotal = currentSession().createSQLQuery(sql.toString());
		queryTotal.setString(i++, "N");
		if(!StringUtil.isNullOrEmpty(year)) queryTotal.setString(i++, year);
		if(!StringUtil.isNullOrEmpty(companyCode)) queryTotal.setString(i++, companyCode);
		int total = queryTotal.list().size();
		pageModel.setTotalCount(total);
		
		i=0;
		Query query2 = currentSession().createSQLQuery(sql.toString());
		query2.setString(i++, "N");
		if(!StringUtil.isNullOrEmpty(year)) query2.setString(i++, year);
		if(!StringUtil.isNullOrEmpty(companyCode)) query2.setString(i++, companyCode);
		query2.setMaxResults(pageModel.getPageSize());
		pageModel.setResult(query2.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list());
		return pageModel;
	}
	
	public LandPicture getLandPictureByTownCode(String companyCode, String cityCode, String townCode){
		String sql = "from LandPicture where companyCode=? and cityCode=? and townCode=? and DeleteFlag=?";
		Query query = currentSession().createQuery(sql);
		query.setString(0, companyCode);
		query.setString(1, cityCode);
		query.setString(2, townCode);
		query.setString(3, "N");
		List<LandPicture> result = query.list();
		if(result != null && result.size()>0) return result.get(0);
		else return null;
	}
}