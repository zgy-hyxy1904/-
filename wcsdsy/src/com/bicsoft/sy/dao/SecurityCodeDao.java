package com.bicsoft.sy.dao;

import java.io.Serializable;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.SecurityCode;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.QualityModel;
import com.bicsoft.sy.model.SecurityCodeModel;

public interface SecurityCodeDao extends IDao<SecurityCode, Serializable>{
	public SecurityCode getSecurityCode(String year, String companyCode);
	public PageModel queryForPageModel(PageModel pageModel, SecurityCodeModel securityCodeModel);
}