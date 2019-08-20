package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.List;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.GeneLandReg;
import com.bicsoft.sy.entity.SoilMoni;
import com.bicsoft.sy.entity.SpecLandDetail;

public interface GeneLandRegDao extends IDao<GeneLandReg, Serializable> {
	public List<SoilMoni> getSoilMoniList();
	
	public SpecLandDetail getGeneLandDetail(String hid);
	
	public List<SpecLandDetail> getGeneLandDetailList(String hid);
}
