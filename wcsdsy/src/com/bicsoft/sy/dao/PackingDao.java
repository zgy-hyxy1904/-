package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.List;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.Packing;

public interface PackingDao extends IDao<Packing, Serializable>{
	
	public List<?> getPackingTypeList(String companyCode);
	public List<?> getPackingClassList(String companyCode, String typeCode);
	public List<?> getPackingSpectList(String companyCode, String typeCode, String classCode);
	
}