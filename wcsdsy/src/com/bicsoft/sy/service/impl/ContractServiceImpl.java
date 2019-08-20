package com.bicsoft.sy.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.ContractDao;
import com.bicsoft.sy.entity.Contract;
import com.bicsoft.sy.service.ContractService;

@Service
@Transactional
public class ContractServiceImpl implements  ContractService
{
	private static final Logger log = LoggerFactory.getLogger(ContractServiceImpl.class);
	
	@Autowired
	protected ContractDao contractDao;
  
	//通过承包方代码查询土地承包合同信息
	public List<Contract> getContractList(String contractorCode){
		return contractDao.getContractList(contractorCode);
	}
	
	//通过证件信息查询土地承包合同信息
	public List<Contract> getContractList(String contractorIDType, String contratorId){
		return contractDao.getContractList(contractorIDType, contratorId);
	}
	
	public void save(Contract contract){
		contractDao.save(contract);
	}
}
