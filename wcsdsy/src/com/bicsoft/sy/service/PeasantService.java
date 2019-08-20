package com.bicsoft.sy.service;

import com.bicsoft.sy.entity.Peasant;

public interface PeasantService{
	
	public void save(Peasant peasant);
	
	public Peasant getByContractorID(String contractorIDType, String contractorID);
	
	public Peasant getByContractorID(String contractorType, String contractorIDType, String contractorID);
	
}