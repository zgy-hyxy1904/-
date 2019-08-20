package com.bicsoft.sy.service;

import java.util.List;

import com.bicsoft.sy.entity.Sample;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SampleModel;

public interface SampleService{
	
	public List<Sample> getSampleByBatchNo(String batchNo, String productCode);
	
	public Sample getSampleBySecurityCode(String securityCode);
	
	public void logicDelete(Class<Sample> clazz , int id);
	
	public PageModel queryForPageModel(PageModel pageModel, SampleModel sampleModel);
	
	public void save(Sample sample);
	
	public double getSampleYield(String year, String companyCode);
	
	public double getActivateSampleYield(String year, String companyCode);
}