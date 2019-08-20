package com.bicsoft.sy.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.LandPictureDao;
import com.bicsoft.sy.entity.LandPicture;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.LandPictureService;

@Service
@Transactional
public class LandPictureServiceImpl implements  LandPictureService
{
	private static final Logger log = LoggerFactory.getLogger(LandPictureServiceImpl.class);
	
	@Autowired
	protected LandPictureDao landPictureDao;
  
	public PageModel queryForPageModel(PageModel pageModel, String year, String companyCode){
		return this.landPictureDao.queryForPageModel(pageModel, year, companyCode);
	}
	
	public void save(LandPicture landPicture){
		this.landPictureDao.save(landPicture);
	}
	
	public void logicDelete(Class<LandPicture> clazz , int id){
		this.landPictureDao.logicDelete(clazz, id);
	}
	
	public LandPicture queryById(Class<LandPicture> clazz , int id){
		return this.landPictureDao.queryById(clazz, id);
	}
	
	public LandPicture getLandPictureByTownCode(String companyCode, String cityCode, String townCode){
		return this.landPictureDao.getLandPictureByTownCode(companyCode, cityCode, townCode);
	}
}
