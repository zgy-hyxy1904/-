package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.Map;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.ProcMoni;

public interface ProcMoniDao extends IDao<ProcMoni, Serializable>{
	public Map getProcMoniList(String year, String companyCode);
	
	public boolean hasProcMoniFile(String year, String companyCode);
}
