package com.bicsoft.sy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.SeedVarietyDao;
import com.bicsoft.sy.entity.SeedVariety;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.SeedVarietyService;

@Service
@Transactional
public class SeedVarietyServiceImpl implements SeedVarietyService {
	private static final Logger log = LoggerFactory.getLogger(SeedVarietyServiceImpl.class);
	
	@Autowired
	private SeedVarietyDao seedVarietyDao;
	
	@Override
	public SeedVariety getSeedVariety(int id) {
		return this.seedVarietyDao.queryById(SeedVariety.class, id);
	}

	@Override
	public void save(SeedVariety seedVariety) {
		this.seedVarietyDao.save(seedVariety);
	}

	@Override
	public void delete(int id) {
		this.seedVarietyDao.delete(SeedVariety.class, id);
	}

	@Override
	public void logicDelete(Class<SeedVariety> clazz, int id) {
		this.seedVarietyDao.logicDelete(clazz, id);
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.seedVarietyDao.queryForPageModel(entityName, params, pageModel);
	}
	
	
	/**
	 * 数据字典可用
	 */
	@Override
	public SeedVariety getSeedVariety(String seedCode) {
		SeedVariety seedVariety = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("seedCode", seedCode);
		List<SeedVariety> results = this.seedVarietyDao.queryByHQL("from SeedVariety where seedCode=:seedCode", params);
		if(results==null || results.size()<1){
			try {
				throw new RuntimeException("数据库中不存在SeedCode="+seedCode+"的数据");
			} catch (RuntimeException e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				seedVariety = new SeedVariety();
			}
		}else{
			seedVariety = results.get(0);
		}
		return seedVariety;
	}

	/**
	 *数据字典可用
	 */
	@Override
	public List<SeedVariety> getSeedVarietyList() {
		return this.seedVarietyDao.queryByHQL("from SeedVariety",null);
	}
}
