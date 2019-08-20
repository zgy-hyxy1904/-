package com.bicsoft.sy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.ServialNumDao;
import com.bicsoft.sy.entity.ServialNum;
import com.bicsoft.sy.service.ServialNumService;

@Service
@Transactional
public class ServialNumServiceImpl implements  ServialNumService
{
	private static final Logger log = LoggerFactory.getLogger(ServialNumServiceImpl.class);
	
	@Autowired
	protected ServialNumDao servialNumDao;
  
	public String getServialNum(String bizType){
		return servialNumDao.getServialNum(bizType);
	}
	
	public void updateServialNum(){
		ServialNum servialNum = servialNumDao.queryById(ServialNum.class, 1);
		if(servialNum != null){
			servialNum.setpTNo(0);
			servialNum.settXNo(0);
			servialNum.setsLNo(0);
			servialNum.settRNo(0);
			servialNum.setBgNo(0);
			servialNumDao.save(servialNum);
		}
	}
}
