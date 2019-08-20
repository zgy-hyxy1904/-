package com.bicsoft.sy.dao;

import java.io.Serializable;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.SecurityCodeDetail;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.QualityModel;
import com.bicsoft.sy.model.SecurityCodeModel;

public interface SecurityCodeDetailDao extends IDao<SecurityCodeDetail, Serializable>{
	public PageModel queryForPageModel(PageModel pageModel, String year, String companyCode);
}