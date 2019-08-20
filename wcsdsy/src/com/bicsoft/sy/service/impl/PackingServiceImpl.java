package com.bicsoft.sy.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.PackingDao;
import com.bicsoft.sy.service.PackingService;

@Service
@Transactional
public class PackingServiceImpl implements  PackingService
{
	private static final Logger log = LoggerFactory.getLogger(PackingServiceImpl.class);
	
	@Autowired
	protected PackingDao packingDao;
  
	public List<?> getPackingTypeList(String companyCode){
		return this.packingDao.getPackingTypeList(companyCode);
	}
	
	public List<?> getPackingClassList(String companyCode, String typeCode){
		return this.packingDao.getPackingClassList(companyCode, typeCode);
	}
	
	public List<?> getPackingSpectList(String companyCode, String typeCode, String classCode){
		return this.packingDao.getPackingSpectList(companyCode, typeCode, classCode);
	}
}
