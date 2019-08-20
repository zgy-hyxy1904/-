package com.bicsoft.sy.dao;

import java.io.Serializable;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.LandPicture;
import com.bicsoft.sy.model.PageModel;

public interface LandPictureDao extends IDao<LandPicture, Serializable>{
	public LandPicture getLandPictureByTownCode(String companyCode, String cityCode, String townCode);
	public PageModel queryForPageModel(PageModel pageModel, String year, String companyCode);
}