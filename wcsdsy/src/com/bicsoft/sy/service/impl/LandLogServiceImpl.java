package com.bicsoft.sy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.LandLogDao;
import com.bicsoft.sy.entity.LandLog;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.LandLogService;

@Service
@Transactional
public class LandLogServiceImpl implements LandLogService {
	private static final Logger log = LoggerFactory.getLogger(LandLogServiceImpl.class);
	
	@Autowired
	private LandLogDao landLogDao;
	
	@Override
	public LandLog getLandLog(int id) {
		return this.landLogDao.queryById(LandLog.class, id);
	}

	@Override
	public void save(LandLog landLog) {
		this.landLogDao.save(landLog);
	}

	@Override
	public void delete(int id) {
		this.landLogDao.delete(LandLog.class, id);
	}

	@Override
	public void logicDelete(Class<LandLog> clazz, int id) {
		this.landLogDao.logicDelete(clazz, id);
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.landLogDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public List<LandLog> queryByBatchNo(String batchNo) {
		String hql = " from LandLog where deleteFlag='N' and batchNo=:batchNo order by createDate desc ";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("batchNo", batchNo);
		return this.landLogDao.queryByHQL(hql, params);
	}
}
