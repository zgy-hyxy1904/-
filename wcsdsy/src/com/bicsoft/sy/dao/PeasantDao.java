package com.bicsoft.sy.dao;

import java.io.Serializable;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.Peasant;

public interface PeasantDao extends IDao<Peasant, Serializable>{
	
	public Peasant getByContractorID(String contractorIDType, String contractorID);
	
	public Peasant getByContractorID(String contractorType, String contractorIDType, String contratorId);
	
}