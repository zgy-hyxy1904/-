package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.Function;

public interface FunctionService{

	public List<Function> getFunctionList();
	
	public List<Map> getUserFunctionList(String userId);
}