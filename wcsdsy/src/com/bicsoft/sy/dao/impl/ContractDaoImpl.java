package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.ContractDao;
import com.bicsoft.sy.entity.Contract;

@Repository
public class ContractDaoImpl extends BaseDaoImpl<Contract, Serializable> implements ContractDao {
	
	public List<Contract> getContractList(String contractorCode){
		String sql = " from Contract where contractorCode=? and landType=? ";
		Query query = currentSession().createQuery(sql);
		query.setString(0, contractorCode);
		query.setString(1, "01");
		List<Contract> list = query.list();
		return list;
	}
	
	public List<Contract> getContractList(String contractorIDType, String contratorId){
		String sql = " from Contract where landType=? and contractorCode in (select contractorCode from Peasant where contractorIDType=? and contractorID=?)";
		Query query = currentSession().createQuery(sql);
		query.setString(0, "01");
		query.setString(1, contractorIDType);
		query.setString(2, contratorId);
		List<Contract> list = query.list();
		return list;
	}
}