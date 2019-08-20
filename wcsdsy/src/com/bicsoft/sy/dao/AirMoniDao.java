package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.List;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.AirMoni;
import com.bicsoft.sy.model.PageModel;

/**
 * 大气监测
 * @author 创新中软
 * @date 2015-08-17
 */
public interface AirMoniDao extends IDao<AirMoni, Serializable>{
	public AirMoni getAirMoni(int id);
	
	public void save(AirMoni airMoni);
	
	public void delete(int id);
	
	public PageModel findPage(PageModel page, String name);
	
	public List<AirMoni> getAirMoniList();
}
