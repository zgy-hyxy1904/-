package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.Company;
import com.bicsoft.sy.model.PageModel;

public interface CompanyDao  extends IDao<Company, Serializable>{
	public PageModel queryForPageModel(PageModel pageModel, String companyCode, String companyName, String companyType);
}
