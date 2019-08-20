package com.bicsoft.sy.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.core.dao.ReCallRecordDao;
import com.bicsoft.sy.entity.ReCallRecord;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.ReCallRecordModel;
import com.bicsoft.sy.service.ReCallRecordService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class ReCallRecordServiceImpl implements ReCallRecordService {
	private static final Logger log = LoggerFactory.getLogger(ReCallRecordServiceImpl.class);
	
	@Autowired
	private ReCallRecordDao reCallRecordDao;
	
	@Override
	public ReCallRecord getReCallRecord(int id) {
		return this.reCallRecordDao.queryById(ReCallRecord.class, id);
	}

	@Override
	public void save(ReCallRecordModel reCallRecordModel) {
		try{
			ReCallRecord reCallRecord = null;
			if(reCallRecordModel.getId() == null){
				reCallRecord = (ReCallRecord) POVOConvertUtil.convert(reCallRecordModel, "com.bicsoft.sy.entity.ReCallRecord");
				this.reCallRecordDao.save(reCallRecord);
			}else{
				reCallRecord = this.reCallRecordDao.queryById(ReCallRecord.class, reCallRecordModel.getId());
				reCallRecord.setUpdateDate(reCallRecordModel.getUpdateDate());
				reCallRecord.setUpdateUserId(reCallRecordModel.getUpdateUserId());
				
			}
			this.reCallRecordDao.save(reCallRecord);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:", e);
		}

	}

	@Override
	public void delete(int id) {
		this.reCallRecordDao.delete(ReCallRecord.class, id);
	}

	@Override
	public void logicDelete(Class<ReCallRecord> clazz, int id) {
		this.reCallRecordDao.logicDelete(clazz, id);
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.reCallRecordDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public ReCallRecord getReCallRecordByCompanyCod(String companyCode) {
		ReCallRecord reCallRecord = null;
		List<ReCallRecord> results = this.reCallRecordDao.queryByHQL("from ReCallRecord where deleteFlag='N' and companyCode='" + companyCode + "'" , null);
		if(results==null || results.size()<1){
			try {
				reCallRecord = new ReCallRecord();
				//throw new RuntimeException("数据库中不存在SpecLandDetail=" + companyCode);
			} catch (RuntimeException e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				reCallRecord = new ReCallRecord();
			}
		}else{
			reCallRecord = results.get(0);
		}
		return reCallRecord;
		
	}
}
