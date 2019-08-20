package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.List;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.Sample;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SampleModel;

public interface SampleDao extends IDao<Sample, Serializable>{
	
	public List<Sample> getSampleByBatchNo(String batchNo, String productCode);
	
	public Sample getSampleBySecurityCode(String securityCode);
	
	public PageModel queryForPageModel(PageModel pageModel, SampleModel sampleModel);
	
	public double getSampleYield(String year, String companyCode);
	
	public double getActivateSampleYield(String year, String companyCode);
}