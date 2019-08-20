package com.bicsoft.sy.service;

import java.util.List;

import com.bicsoft.sy.entity.Contract;

public interface ContractService{
	
	public void save(Contract contract);
	
	public List<Contract> getContractList(String contractorCode);
	
	public List<Contract> getContractList(String contractorIDType, String contratorId);
	
}