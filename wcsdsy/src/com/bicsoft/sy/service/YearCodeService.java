package com.bicsoft.sy.service;

import java.util.List;

import com.bicsoft.sy.entity.YearCode;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.YearCodeModel;

public interface YearCodeService {
	public YearCode getYearCode(int id);
	
	public void save(YearCode yearCode);
	
	public void save(YearCodeModel yearModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<YearCode> clazz , int id);
	
	public PageModel queryForPageModel(PageModel pageModel, String yearName);
	
	public YearCode getYearCode(String yearCode);
	
	public List<YearCode> getYearCodeList();
}
