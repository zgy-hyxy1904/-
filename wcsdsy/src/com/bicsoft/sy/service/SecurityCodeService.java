package com.bicsoft.sy.service;

import com.bicsoft.sy.entity.SecurityCode;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.QualityModel;
import com.bicsoft.sy.model.SecurityCodeModel;

public interface SecurityCodeService{
	
	public SecurityCode getSecurityCode(String year, String companyCode);
	
	public void logicDelete(Class<SecurityCode> clazz , int id);
	
	public PageModel queryForPageModel(PageModel pageModel, SecurityCodeModel securityCodeModel);
	
	public void save(SecurityCode securityCode);
}