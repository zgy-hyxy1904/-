package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.Company;
import com.bicsoft.sy.model.CompanyModel;
import com.bicsoft.sy.model.PageModel;

public interface CompanyService {
	public Company getCompany(int id);
	
	public void save(Company company);
	
	public void save(CompanyModel companyModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<Company> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public Company getCompany(String companyCode);
	
	public List<Company> getCompanyListByCompanyType(String companyType);
	
	public PageModel queryForPageModel(PageModel pageModel, String companyCode, String companyName, String companyType);
}
