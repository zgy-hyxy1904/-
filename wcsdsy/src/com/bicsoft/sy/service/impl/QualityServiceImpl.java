package com.bicsoft.sy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.QualityDao;
import com.bicsoft.sy.entity.Quality;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.QualityModel;
import com.bicsoft.sy.service.QualityService;

@Service
@Transactional
public class QualityServiceImpl implements  QualityService
{
	private static final Logger log = LoggerFactory.getLogger(QualityServiceImpl.class);
	
	@Autowired
	protected QualityDao qualityDao;
	
	public void save(Quality quality){
		this.qualityDao.save(quality);
	}
	
	public Quality getQualityByBatchNo(String batchNo){
		return this.qualityDao.getQualityByBatchNo(batchNo);
	}
	
	public PageModel queryForPageModel(PageModel pageModel, QualityModel qualityModel)
	{
		return this.qualityDao.queryForPageModel(pageModel, qualityModel);
	}
	
	public Quality getQuality(Integer id){
		return this.qualityDao.queryById(Quality.class, id);
	}
}
