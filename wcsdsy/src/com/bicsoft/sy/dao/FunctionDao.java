package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.Function;

public interface FunctionDao extends IDao<Function, Serializable>{
	public List<Function> getFunctionList();
	public List<Map> getUserFunctionList(String userId);
}