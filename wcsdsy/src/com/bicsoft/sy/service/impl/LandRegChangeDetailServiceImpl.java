package com.bicsoft.sy.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.LandRegChangeDetailDao;
import com.bicsoft.sy.entity.LandRegChangeDetail;
import com.bicsoft.sy.model.LandRegChangeDetailModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.LandRegChangeDetailService;

@Service
@Transactional
public class LandRegChangeDetailServiceImpl implements LandRegChangeDetailService {
	private static final Logger log = LoggerFactory.getLogger(ProvEvalServiceImpl.class);
	
	@Autowired
	private LandRegChangeDetailDao landRegChangeDetailDao;

	@Override
	public LandRegChangeDetail getLandRegChangeDetail(int id) {
		return this.landRegChangeDetailDao.queryById(LandRegChangeDetail.class, id);
	}

	@Override
	public void save(LandRegChangeDetailModel landRegChangeDetailModel) {
		 
	}

	@Override
	public void delete(int id) {
		this.landRegChangeDetailDao.delete(LandRegChangeDetail.class, id);
		
	}

	@Override
	public void logicDelete(Class<LandRegChangeDetail> clazz, int id) {
		this.landRegChangeDetailDao.logicDelete(clazz, id);
		
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.landRegChangeDetailDao.queryForPageModel(entityName, params, pageModel);
	}
}
