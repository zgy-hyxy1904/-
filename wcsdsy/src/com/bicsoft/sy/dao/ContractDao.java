package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.List;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.Contract;

public interface ContractDao extends IDao<Contract, Serializable>{
	
	public List<Contract> getContractList(String contractorCode);
	
	public List<Contract> getContractList(String contractorIDType, String contratorId);
}