package com.bicsoft.sy.service;

import com.bicsoft.sy.entity.LandPicture;
import com.bicsoft.sy.model.PageModel;

public interface LandPictureService{
	public void save(LandPicture landPicture);
	public void logicDelete(Class<LandPicture> clazz , int id);
	public LandPicture queryById(Class<LandPicture> clazz , int id);
	public LandPicture getLandPictureByTownCode(String companyCode, String cityCode, String townCode);
	public PageModel queryForPageModel(PageModel pageModel, String year, String companyCode);
}