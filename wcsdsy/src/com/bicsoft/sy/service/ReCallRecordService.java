package com.bicsoft.sy.service;

import java.util.Map;

import com.bicsoft.sy.entity.ReCallRecord;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.ReCallRecordModel;

/**
 * 召回管理
 * @author 创新中软
 * @date 2015-08-26
 */
public interface ReCallRecordService {
	public ReCallRecord getReCallRecord(int id);
	
	public void save(ReCallRecordModel reCallRecordModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<ReCallRecord> clazz, int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public ReCallRecord getReCallRecordByCompanyCod(String companyCode);
}
