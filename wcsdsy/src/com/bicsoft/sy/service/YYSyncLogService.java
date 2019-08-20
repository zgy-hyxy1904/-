package com.bicsoft.sy.service;

import com.bicsoft.sy.entity.YYSyncLog;
import com.bicsoft.sy.model.PageModel;

public interface YYSyncLogService{
	
	public void save(YYSyncLog yySyncLog);
	
	public YYSyncLog getLastSyncLog(String bizType);
	
	public PageModel queryForPageModel(PageModel pageModel, String userId, String userName, String userType);
}