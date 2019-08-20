package com.bicsoft.sy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.YYSyncLogDao;
import com.bicsoft.sy.entity.YYSyncLog;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.YYSyncLogService;;

@Service
@Transactional
public class YYSyncLogServiceImpl implements  YYSyncLogService
{
	private static final Logger log = LoggerFactory.getLogger(YYSyncLogServiceImpl.class);
	
	@Autowired
	protected YYSyncLogDao yySyncLogDao;

	public void save(YYSyncLog yySyncLog){
		yySyncLogDao.save(yySyncLog);
	}
	
	public YYSyncLog getLastSyncLog(String bizType){
		return yySyncLogDao.getLastSyncLog(bizType);
	}
	
	public PageModel queryForPageModel(PageModel pageModel, String userId, String userName, String userType){
		return this.yySyncLogDao.queryForPageModel(pageModel, userId, userName, userType);
	}
}
