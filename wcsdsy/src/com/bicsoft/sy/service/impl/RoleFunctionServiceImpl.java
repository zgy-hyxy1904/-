package com.bicsoft.sy.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.RoleFunctionDao;
import com.bicsoft.sy.entity.RoleFunction;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.service.RoleFunctionService;

@Service
@Transactional
public class RoleFunctionServiceImpl implements  RoleFunctionService
{
	private static final Logger log = LoggerFactory.getLogger(RoleFunctionServiceImpl.class);
	
	@Autowired
	protected RoleFunctionDao roleFunctionDao;
	
	public List<RoleFunction> getFunListForRole(String roleCode){
		return this.roleFunctionDao.getFunListForRole(roleCode);
	}
	
	public void saveRoleFunList(BaseModel baseModel, String roleCode, String[] funIds){
		for(String funId : funIds){
			RoleFunction roleFunction = new RoleFunction();
			roleFunction.setFunctionCode(funId);
			roleFunction.setRoleCode(roleCode);
			roleFunction.setCreateDate(baseModel.getCreateDate());
			roleFunction.setCreateUserId(baseModel.getCreateUserId());
			roleFunction.setUpdateDate(baseModel.getUpdateDate());
			roleFunction.setUpdateUserId(baseModel.getUpdateUserId());
			this.roleFunctionDao.save(roleFunction);
		}
	}
	
	public void delete(RoleFunction roleFunction){
		this.roleFunctionDao.delete(roleFunction);
	}
}
