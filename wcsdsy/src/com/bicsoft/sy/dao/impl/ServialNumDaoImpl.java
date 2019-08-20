package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.ServialNumDao;
import com.bicsoft.sy.entity.ServialNum;

@Repository
public class ServialNumDaoImpl extends BaseDaoImpl<ServialNum, Serializable> implements ServialNumDao {
	
	public String getServialNum(String bizType){
		String result = "";
		try{
			Connection  conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			CallableStatement  callStat = conn.prepareCall("{call generate_servial_num(?,?)}");
			callStat.setString(1, bizType);
			callStat.registerOutParameter(2, Types.VARCHAR) ;
			callStat.execute();
			result = callStat.getString(2);
			callStat.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}