package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.GeneLandDetail;
import com.bicsoft.sy.entity.GeneLandReg;
import com.bicsoft.sy.entity.GeneLandRegD;
import com.bicsoft.sy.model.GeneLandRegModel;
import com.bicsoft.sy.model.PageModel;


public interface GeneLandRegService {
	public GeneLandReg getGeneLandReg(int id);
	
	public void save(GeneLandRegModel geneLandRegModel);
	
	public void delete(int id);
	
	public void logicDelete(Class<GeneLandReg> clazz , int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);

	public Float getThisWeightSum(String id);
	
	public List<GeneLandDetail> getGeneLandDetailList( Integer id );

	public int queryGeneLandRegInfosCount(String yearCode , String idType, String contractorID);


}
