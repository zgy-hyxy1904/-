package com.bicsoft.sy.dao;

import java.io.Serializable;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.SpecLandReg;

public interface SpecLandRegDao extends IDao<SpecLandReg, Serializable>{
	public void saveReason(SpecLandReg specLandReg);
}
