package com.bicsoft.sy.dao;

import java.io.Serializable;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.YYSyncLog;
import com.bicsoft.sy.model.PageModel;

public interface YYSyncLogDao extends IDao<YYSyncLog, Serializable>{
	
	public YYSyncLog getLastSyncLog(String bizType);
	
	public PageModel queryForPageModel(PageModel pageModel, String userId, String userName, String userType);
}