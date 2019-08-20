package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.List;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.Product;
import com.bicsoft.sy.model.PageModel;

public interface ProductDao extends IDao<Product, Serializable>{
	public Product getProduct(String companyCode, String productCode);
	public List<?> getProductTypeList(String companyCode);
	public List<?> getProductTypeList(String companyCode, String typeCode);
	public List<?> getProductTypeList(String companyCode, String typeCode, String classCode);
	public PageModel queryForPageModel(PageModel pageModel, String companyCode, String productName);
}