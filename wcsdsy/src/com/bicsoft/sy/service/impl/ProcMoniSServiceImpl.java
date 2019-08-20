package com.bicsoft.sy.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.ProcMoniSDao;
import com.bicsoft.sy.entity.ProcMoniS;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.ProcMoniSModel;
import com.bicsoft.sy.service.ProcMoniSService;

@Service
@Transactional
public class ProcMoniSServiceImpl implements ProcMoniSService {
	private static final Logger log = LoggerFactory.getLogger(ProcMoniServiceImpl.class);
	
	@Autowired
	private ProcMoniSDao procMoniSDao;
	
	@Override
	public ProcMoniS getProcMoniS(int id) {
		return this.procMoniSDao.queryById(ProcMoniS.class,id);
	}

	@Override
	public void save(ProcMoniSModel ProcMoniSModel) {
		
	}

	@Override
	public void delete(int id) {
		this.procMoniSDao.delete(ProcMoniS.class, id);
	}

	@Override
	public void logicDelete(Class<ProcMoniS> clazz, int id) {
		this.procMoniSDao.logicDelete(clazz, id);
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.procMoniSDao.queryForPageModel(entityName, params, pageModel);
	}
}
