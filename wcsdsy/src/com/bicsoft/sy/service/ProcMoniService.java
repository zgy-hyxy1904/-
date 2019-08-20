package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.ProcMoni;
import com.bicsoft.sy.entity.ProvEval;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.ProcMoniModel;

/**
 * 过程监控
 * @author 创新中软
 * @date 2015-08-20
 */
public interface ProcMoniService {
	public ProcMoni getProcMoni(int id);
	
	public void save(ProcMoniModel ProcMoniModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<ProcMoni> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public List<ProvEval> getProcMoniList();
	
	public ProcMoni getProcMoni(String year, String bizType, String companyCode);
	
	public Map getProcMoniList(String year, String companyCode);
	
	public boolean hasProcMoniFile(String year, String companyCode);
}
