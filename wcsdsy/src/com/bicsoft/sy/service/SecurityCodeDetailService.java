package com.bicsoft.sy.service;

import com.bicsoft.sy.entity.SecurityCodeDetail;
import com.bicsoft.sy.model.PageModel;

public interface SecurityCodeDetailService{
	
	public void save(SecurityCodeDetail securityCodeDetail);
	
	public PageModel queryForPageModel(PageModel pageModel, String year, String companyCode);

}