package com.bicsoft.sy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.PeasantDao;
import com.bicsoft.sy.entity.Peasant;
import com.bicsoft.sy.service.PeasantService;

@Service
@Transactional
public class PeasantServiceImpl implements  PeasantService
{
	private static final Logger log = LoggerFactory.getLogger(PeasantServiceImpl.class);
	
	@Autowired
	protected PeasantDao peasantDao;
  
	//通过证件id查询农户信息
	public Peasant getByContractorID(String contractorType, String contractorIDType, String contratorId){
		return peasantDao.getByContractorID(contractorType, contractorIDType, contratorId);
	}
	
	public Peasant getByContractorID(String contractorIDType, String contractorID){
		return peasantDao.getByContractorID(contractorIDType, contractorID);
	}
	
	public void save(Peasant peasant){
		peasantDao.save(peasant);
	}
}
