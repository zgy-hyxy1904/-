package com.bicsoft.sy.service;

import java.util.List;

import com.bicsoft.sy.entity.Product;
import com.bicsoft.sy.model.PageModel;

public interface ProductService{
	public void save(Product product);
	
	public Product getProduct(String companyCode, String productCode);
	
	public void logicDelete(Class<Product> clazz, int id);
	
	public List<?> getProductTypeList(String companyCode);
	
	public List<?> getProductTypeList(String companyCode, String typeCode);
	
	public List<?> getProductTypeList(String companyCode, String typeCode, String classCode);
	
	public PageModel queryForPageModel(PageModel pageModel, String companyCode, String productName);
	
}