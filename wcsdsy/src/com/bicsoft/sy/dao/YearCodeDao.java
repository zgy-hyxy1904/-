package com.bicsoft.sy.dao;

import java.io.Serializable;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.YearCode;
import com.bicsoft.sy.model.PageModel;

public interface YearCodeDao  extends IDao<YearCode, Serializable>{
	public PageModel queryForPageModel(PageModel pageModel, String yearCode);
}
