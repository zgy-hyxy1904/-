package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.FunctionDao;
import com.bicsoft.sy.entity.Function;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function, Serializable> implements FunctionDao {
	
	public List<Function> getFunctionList(){
		String sqlTotal = " from Function where deleteFlag=? order by moduleDispSeq,functionDispSeq";
		Query query = currentSession().createQuery(sqlTotal);
		query.setString(0, "N");
		return query.list();
	}
	
	public List<Map> getUserFunctionList(String userId){
		StringBuffer sql = new StringBuffer("select ModuleCode,ModuleName,FunctionCode,FunctionName,FunctionURL,FunctionIcon from M_Function where FunctionCode in ");
		sql.append(" (select FunctionCode from M_RoleFunctionMap where RoleCode in ");
		sql.append(" (select RoleCode from M_UserRoleMap where userId=?)) order by ModuleDispSeq,FunctionDispSeq");
		
		Query query = currentSession().createSQLQuery(sql.toString());
		query.setString(0, userId);
		List<Map> funList = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return funList;
	}
}