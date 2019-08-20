package com.bicsoft.sy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.SecurityCodeDetailDao;
import com.bicsoft.sy.entity.SecurityCodeDetail;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.SecurityCodeDetailService;

@Service
@Transactional
public class SecurityCodeDetailServiceImpl implements  SecurityCodeDetailService
{
	private static final Logger log = LoggerFactory.getLogger(SecurityCodeDetailServiceImpl.class);
	
	@Autowired
	protected SecurityCodeDetailDao securityDetailCodeDao;
	
	public void save(SecurityCodeDetail securityCodeDetail){
		this.securityDetailCodeDao.save(securityCodeDetail);
	}
	
	public PageModel queryForPageModel(PageModel pageModel, String year, String companyCode){
		return this.securityDetailCodeDao.queryForPageModel(pageModel, year, companyCode);
	}
}
