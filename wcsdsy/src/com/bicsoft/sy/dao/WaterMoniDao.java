package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.List;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.WaterMoni;
import com.bicsoft.sy.model.PageModel;

/**
 * 水质监测
 * @author 创新中软
 * @date 2015-08-17
 */
public interface WaterMoniDao extends IDao<WaterMoni, Serializable>{
	public WaterMoni getWaterMoni(int id);
	
	public void save(WaterMoni waterMoni);
	
	public void delete(int id);
	
	public PageModel findPage(PageModel page, String name);
	
	public List<WaterMoni> getWaterMoniList();
}
