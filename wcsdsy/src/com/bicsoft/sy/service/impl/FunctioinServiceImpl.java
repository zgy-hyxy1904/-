package com.bicsoft.sy.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.FunctionDao;
import com.bicsoft.sy.entity.Function;
import com.bicsoft.sy.service.FunctionService;

@Service
@Transactional
public class FunctioinServiceImpl implements  FunctionService
{
	private static final Logger log = LoggerFactory.getLogger(FunctioinServiceImpl.class);
	
	@Autowired
	protected FunctionDao functionDao;
	
	public List<Function> getFunctionList(){
		return this.functionDao.getFunctionList();
	}
	
	public List<Map> getUserFunctionList(String userId){
		return this.functionDao.getUserFunctionList(userId);
	}
}
