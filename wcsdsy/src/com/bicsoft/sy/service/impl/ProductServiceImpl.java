package com.bicsoft.sy.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.ProductDao;
import com.bicsoft.sy.entity.Product;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements  ProductService
{
	private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	protected ProductDao productDao;
  
	public void save(Product product){
		this.productDao.save(product);
	}
	
	public void logicDelete(Class<Product> clazz, int id) {
		this.productDao.logicDelete(clazz, id);
	}
	
	public Product getProduct(String companyCode, String productCode){
		return this.productDao.getProduct(companyCode, productCode);
	}
	
	public List<?> getProductTypeList(String companyCode){
		return this.productDao.getProductTypeList(companyCode);
	}
	
	public List<?> getProductTypeList(String companyCode, String typeCode){
		return this.productDao.getProductTypeList(companyCode, typeCode);
	}
	
	public List<?> getProductTypeList(String companyCode, String typeCode, String classCode){
		return this.productDao.getProductTypeList(companyCode, typeCode, classCode);
	}
	
	public PageModel queryForPageModel(PageModel pageModel, String companyCode, String productName){
		return this.productDao.queryForPageModel(pageModel, companyCode, productName);
	}
}
