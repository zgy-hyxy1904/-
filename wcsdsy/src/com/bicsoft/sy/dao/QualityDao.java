package com.bicsoft.sy.dao;

import java.io.Serializable;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.Quality;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.QualityModel;

public interface QualityDao extends IDao<Quality, Serializable>{
	
	public Quality getQualityByBatchNo(String batchNo);
	
	public PageModel queryForPageModel(PageModel pageModel, QualityModel qualityModel);
}