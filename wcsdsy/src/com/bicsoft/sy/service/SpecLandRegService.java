package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.SpecLandReg;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SpecLandRegModel;

public interface SpecLandRegService {
	public SpecLandReg getSpecLandReg(int id);
	
	public void save(SpecLandRegModel specLandModel);
	
	public void save(SpecLandReg specLand);
	
	public void saveReason(SpecLandRegModel specLandRegModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<SpecLandReg> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public List<SpecLandRegModel> updateStatus(String[] ids, BaseModel baseModel);
	
	public List<SpecLandRegModel> updateStatus(String[] ids, String status, BaseModel baseModel);
	
	public int querySpecLandRegInfosCount(String yearCode , String idType, String contractorID);
	
	public List<SpecLandReg> querySpecLandRegInfoList(String companyCode, String yearCode,String idType,String contractorID);
}
