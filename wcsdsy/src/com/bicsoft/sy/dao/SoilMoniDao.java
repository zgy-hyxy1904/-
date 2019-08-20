package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.List;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.SoilMoni;
import com.bicsoft.sy.model.PageModel;

/**
 * 土壤监测
 * @author 创新中软
 * @date 2015-08-17
 */
public interface SoilMoniDao extends IDao<SoilMoni, Serializable>{
	public SoilMoni getSoilMoni(int id);
	
	public void save(SoilMoni soilMoni);
	
	public void delete(int id);
	
	public PageModel findPage(PageModel page, String name);
	
	public List<SoilMoni> getSoilMoniList();
}
