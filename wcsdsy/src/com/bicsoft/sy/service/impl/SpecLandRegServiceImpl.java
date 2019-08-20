package com.bicsoft.sy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.SpecLandDetailDao;
import com.bicsoft.sy.dao.SpecLandRegDao;
import com.bicsoft.sy.entity.GeneLandDetail;
import com.bicsoft.sy.entity.SpecLandDetail;
import com.bicsoft.sy.entity.SpecLandReg;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SpecLandRegModel;
import com.bicsoft.sy.service.SpecLandDetailService;
import com.bicsoft.sy.service.SpecLandRegService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class SpecLandRegServiceImpl implements SpecLandRegService {
	private static final Logger log = LoggerFactory.getLogger(SpecLandRegServiceImpl.class);
	
	@Autowired
	private SpecLandRegDao specLandRegDao;
	
	@Autowired
	private SpecLandDetailDao specLandDetailDao;
	
	@Autowired
	private SpecLandDetailService specLandDetailService;
	
	@Override
	public SpecLandReg getSpecLandReg(int id) {
		return this.specLandRegDao.queryById(SpecLandReg.class, id);
	}

	@Override
	public void delete(int id) {
		this.specLandRegDao.delete(SpecLandReg.class, id);
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.specLandRegDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public void save(SpecLandRegModel specLandModel) {
		try{
			SpecLandReg specLandReg = null;
			if(specLandModel.getId() == null){
				specLandReg = (SpecLandReg) POVOConvertUtil.convert(specLandModel, "com.bicsoft.sy.entity.SpecLandReg");
				specLandReg.setCompanyCode( specLandModel.getCompanyCode() );
				specLandReg.setCompanyName( specLandModel.getCompanyName() );
				specLandReg.setContractorID( specLandModel.getContractorID() );
				specLandReg.setContractorTel(specLandModel.getContractorTel());
				this.specLandRegDao.save(specLandReg);
				
				specLandModel.setId( specLandReg.getId() );
				/**
				 * 这里保存子表的信息
				 */
				SpecLandDetail specLandDetail = new SpecLandDetail();
				specLandDetail.setHid( specLandReg.getId() );
				specLandDetail.setActualMu( specLandModel.getActualMu() );
				specLandDetail.setLandClass( specLandModel.getLandClass() );
				specLandDetail.setLandType( specLandModel.getLandType() );
				
				specLandDetail.setGroupName( specLandModel.getGroupNameDetail() );
				specLandDetail.setTownCode( specLandModel.getTownCodeDetail() );
				specLandDetail.setCountryCode( specLandModel.getCountryCodeDetail() );
				specLandDetail.setCityCode( specLandModel.getCityCodeDetail() );
				specLandDetail.setCreateDate( specLandModel.getCreateDate() );
				specLandDetail.setCreateUserId( specLandModel.getCreateUserId());
				specLandDetail.setUpdateDate( specLandModel.getUpdateDate() );
				specLandDetail.setUpdateUserId( specLandModel.getUpdateUserId() );
				
				this.specLandDetailDao.save(specLandDetail);
			}else{
				specLandReg = this.specLandRegDao.queryById(SpecLandReg.class, specLandModel.getId());
				specLandReg.setStatus( specLandModel.getStatus() );
				if( "03".equals( specLandModel.getStatus() ) ){
					specLandReg.setAuditTime(specLandModel.getUpdateDate());
					specLandReg.setAuditor(specLandReg.getUpdateUserId());
				}
				specLandReg.setContractorID( specLandReg.getContractorID() );
				
				specLandReg.setUpdateDate( specLandModel.getUpdateDate() );
				specLandReg.setUpdateUserId( specLandModel.getUpdateUserId() );
				this.specLandRegDao.save(specLandReg);
				/**
				 * 这里保存子表的信息
				 */
				SpecLandDetail specLandDetail = this.specLandDetailService.getSpecLandDetail( specLandReg.getId()+"" );
				if( specLandDetail != null ){
					specLandDetail.setActualMu( specLandModel.getActualMu() );
					specLandDetail.setLandClass( specLandModel.getLandClass() );
					specLandDetail.setLandType( specLandModel.getLandType() );
					
					specLandDetail.setGroupName( specLandModel.getGroupNameDetail() );
					specLandDetail.setTownCode( specLandModel.getTownCodeDetail() );
					specLandDetail.setCountryCode( specLandModel.getCountryCodeDetail() );
					specLandDetail.setCityCode("230184");
					specLandDetail.setUpdateDate( specLandModel.getUpdateDate() );
					specLandDetail.setUpdateUserId( specLandModel.getUpdateUserId() );
					
					this.specLandDetailDao.save(specLandDetail);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:", e);
		}
	}

	@Override
	public void logicDelete(Class<SpecLandReg> clazz, int id) {
		this.specLandRegDao.logicDelete(clazz, id);
		
	}

	@Override
	public void save(SpecLandReg specLand) {
		
	}

	@Override
	public void saveReason(SpecLandRegModel specLandModel) {
		try{
			SpecLandReg specLandReg = null;
			if(specLandModel.getId() != null){
				specLandReg = this.specLandRegDao.queryById(SpecLandReg.class, specLandModel.getId());
				specLandReg.setStatus( specLandModel.getStatus() );
				specLandReg.setAuditTime( new Date() );
				specLandReg.setAuditor(specLandModel.getUpdateUserName());
				specLandReg.setReason( specLandModel.getReason() );
				
				this.specLandRegDao.save(specLandReg);
				
				specLandModel.setApplyBatchNo(specLandReg.getApplyBatchNo());
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:", e);
		}
	}

	@Override
	public List<SpecLandRegModel> updateStatus(String[] ids, BaseModel baseModel) {
		List<SpecLandRegModel> sModels = null;
		if( ids != null && ids.length > 0 ){
			sModels = new ArrayList<SpecLandRegModel>();
			for( String id : ids ){
				try{
					SpecLandReg specLandReg = null;
					specLandReg = this.specLandRegDao.queryById(SpecLandReg.class, Integer.valueOf( id ));
					specLandReg.setStatus( "02" );
					specLandReg.setUpdateDate( new Date() );
					specLandReg.setUpdateUserId( baseModel.getUpdateUserId() );
					
					this.specLandRegDao.save(specLandReg);
					
					//为日志而生
					SpecLandRegModel model = new SpecLandRegModel();
					model.setId(specLandReg.getId());
					model.setApplyBatchNo(specLandReg.getApplyBatchNo());
					model.setStatus(specLandReg.getStatus());
					model.setCreateUserId(specLandReg.getCreateUserId());
					model.setCreateDate(specLandReg.getCreateDate());
					model.setUpdateUserId(specLandReg.getUpdateUserId());
					model.setUpdateDate(specLandReg.getUpdateDate());
					sModels.add(model);
				}catch (Exception e) {
					e.printStackTrace();
					log.error("UserService saveObject ServiceException:", e);
				}
			}
		}
		return sModels;
	}
	
	@Override
	public List<SpecLandRegModel> updateStatus(String[] ids, String status, BaseModel baseModel) {
		List<SpecLandRegModel> sModels = null;
		if( ids != null && ids.length > 0 ){
			sModels = new ArrayList<SpecLandRegModel>();
			for( String id : ids ){
				try{
					SpecLandReg specLandReg = null;
					specLandReg = this.specLandRegDao.queryById(SpecLandReg.class, Integer.valueOf( id ));
					specLandReg.setStatus( status );
					specLandReg.setUpdateDate( new Date() );
					specLandReg.setUpdateUserId( baseModel.getUpdateUserId() );
					
					this.specLandRegDao.save(specLandReg);
					
					//为日志而生
					SpecLandRegModel model = new SpecLandRegModel();
					model.setId(specLandReg.getId());
					model.setApplyBatchNo(specLandReg.getApplyBatchNo());
					model.setStatus(specLandReg.getStatus());
					model.setCreateUserId(specLandReg.getCreateUserId());
					model.setCreateDate(specLandReg.getCreateDate());
					model.setUpdateUserId(specLandReg.getUpdateUserId());
					model.setUpdateDate(specLandReg.getUpdateDate());
					sModels.add(model);
				}catch (Exception e) {
					e.printStackTrace();
					log.error("UserService saveObject ServiceException:", e);
				}
			}
		}
		return sModels;
	}
	
	@Override
	public int querySpecLandRegInfosCount(String yearCode,String idType,String contractorID){
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT ID ");
		buffer.append("FROM b_SpecialLandReg ");
		buffer.append("WHERE deleteFlag='N' AND year='"+yearCode+"' AND IDType='"+idType+ "' AND contractorID='"+contractorID+ "'");
		List<Object[]> results = specLandRegDao.queryBySQL(buffer.toString(), null);
        if(results!=null){
        	return results.size();
        }else{
        	return 0;
        }
	}
	
	@Override
	public List<SpecLandReg> querySpecLandRegInfoList(String companyCode, String yearCode,String idType,String contractorID){
		List<SpecLandReg> results = this.specLandRegDao.queryByHQL("from SpecLandReg where deleteFlag='N' AND companyCode='"+companyCode+"' AND year='"+yearCode+"' AND IDType='"+idType+ "' AND contractorID='"+contractorID + "'" , null);
        return results;
	}
}
