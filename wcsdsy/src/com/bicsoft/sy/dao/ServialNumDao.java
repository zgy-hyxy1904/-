package com.bicsoft.sy.dao;

import java.io.Serializable;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.ServialNum;

public interface ServialNumDao extends IDao<ServialNum, Serializable>{
	public String getServialNum(String bizType);
}