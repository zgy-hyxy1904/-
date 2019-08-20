package com.bicsoft.sy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.SecurityCodeDao;
import com.bicsoft.sy.entity.SecurityCode;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SecurityCodeModel;
import com.bicsoft.sy.service.SecurityCodeService;

@Service
@Transactional
public class SecurityCodeImpl implements  SecurityCodeService
{
	private static final Logger log = LoggerFactory.getLogger(SecurityCodeImpl.class);
	
	@Autowired
	protected SecurityCodeDao securityCodeDao;
	
	public SecurityCode getSecurityCode(String year, String companyCode){
		return this.securityCodeDao.getSecurityCode(year, companyCode);
	}
	
	public void logicDelete(Class<SecurityCode> clazz , int id){
		this.securityCodeDao.logicDelete(clazz, id);
	}
	
	public PageModel queryForPageModel(PageModel pageModel, SecurityCodeModel securityCodeModel){
		return this.securityCodeDao.queryForPageModel(pageModel, securityCodeModel);
	}
	
	public void save(SecurityCode securityCode){
		this.securityCodeDao.save(securityCode);
	}
}
