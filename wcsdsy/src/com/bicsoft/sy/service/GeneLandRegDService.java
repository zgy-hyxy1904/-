package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.GeneLandDetail;
import com.bicsoft.sy.entity.GeneLandRegD;
import com.bicsoft.sy.model.GeneLandRegDModel;
import com.bicsoft.sy.model.PageModel;

public interface GeneLandRegDService {
	public GeneLandRegD getGeneLandRegD(int id);
	
	public void save(GeneLandRegDModel geneLandRegDModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<GeneLandRegD> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	public GeneLandRegD getGeneLandRegD(String hid);
	
	public List<GeneLandRegD> getGeneLandRegDList(String hid);
	
	//查询已备案面积
	public Float queryBAmj(String idNumber, String year);
	
	//根据企业和年度查询数据
	public List<GeneLandRegD> getGeneLandRegDList(String year, String companyCode);
	
	//public List<GeneLandRegD> queryGeneLandRegDInfoList(String companyCode, String yearCode,String idType,String contractorID);
	public List<Map<String,Object>> queryGeneLandRegDInfoList(String companyCode, String yearCode,String idType,String contractorID);
}
