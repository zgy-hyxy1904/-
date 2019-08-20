package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.InputReg;
import com.bicsoft.sy.model.InputRegModel;
import com.bicsoft.sy.model.PageModel;

public interface InputRegService {
	public InputReg getInputReg(int id);
	
	public void save(InputRegModel inputRegModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<InputReg> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public List<InputReg> getInputRegList();
	
	public boolean hasInputReg(String year, String companyCode);
}
