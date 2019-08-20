package com.bicsoft.sy.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.SampleDao;
import com.bicsoft.sy.entity.Sample;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SampleModel;
import com.bicsoft.sy.service.SampleService;

@Service
@Transactional
public class SampleServiceImpl implements  SampleService
{
	private static final Logger log = LoggerFactory.getLogger(SampleServiceImpl.class);
	
	@Autowired
	protected SampleDao sampleDao;
	
	public List<Sample> getSampleByBatchNo(String batchNo, String productCode){
		return this.sampleDao.getSampleByBatchNo(batchNo, productCode);
	}
	
	public void logicDelete(Class<Sample> clazz , int id){
		this.sampleDao.logicDelete(clazz, id);
	}
	
	public PageModel queryForPageModel(PageModel pageModel, SampleModel sampleModel){
		return this.sampleDao.queryForPageModel(pageModel, sampleModel);
	}
	
	public void save(Sample sample){
		this.sampleDao.save(sample);
	}
	
	public double getSampleYield(String year, String companyCode){
		return this.sampleDao.getSampleYield(year, companyCode);
	}
	
	public double getActivateSampleYield(String year, String companyCode){
		return this.sampleDao.getActivateSampleYield(year, companyCode);
	}
	
	public Sample getSampleBySecurityCode(String securityCode){
		return this.sampleDao.getSampleBySecurityCode(securityCode);
	}
}
