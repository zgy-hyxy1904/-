package com.bicsoft.sy.service;

import java.util.List;

public interface PackingService{
	
	public List<?> getPackingTypeList(String companyCode);
	
	public List<?> getPackingClassList(String companyCode, String typeCode);
	
	public List<?> getPackingSpectList(String companyCode, String typeCode, String classCode);
	
}