package com.bicsoft.sy.service;

import com.bicsoft.sy.entity.Quality;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.QualityModel;

public interface QualityService{
	
	public void save(Quality quality);
	
	public Quality getQualityByBatchNo(String batchNo);
	
	public Quality getQuality(Integer id);
	
	public PageModel queryForPageModel(PageModel pageModel, QualityModel qualityModel);
}