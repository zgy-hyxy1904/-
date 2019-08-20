package com.bicsoft.sy.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.LandRegChangeDao;
import com.bicsoft.sy.entity.GraiEval;
import com.bicsoft.sy.entity.LandRegChange;
import com.bicsoft.sy.entity.LandRegChangeDetail;
import com.bicsoft.sy.entity.SpecLandReg;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.LandRegChangeModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SpecLandRegModel;
import com.bicsoft.sy.service.LandRegChangeService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class LandRegChangeServiceImpl implements LandRegChangeService {
	private static final Logger log = LoggerFactory.getLogger(ProvEvalServiceImpl.class);
	
	@Autowired
	private LandRegChangeDao landRegChangeDao;

	@Override
	public LandRegChange getLandRegChange(int id) {
		return this.landRegChangeDao.queryById(LandRegChange.class, id);
	}

	@Override
	public void save(LandRegChangeModel landChangeModel) {
		try{
			LandRegChange landRegChange = null;
			if(landChangeModel.getId() == null){
				landRegChange = (LandRegChange) POVOConvertUtil.convert(landChangeModel, "com.bicsoft.sy.entity.LandRegChange");
				this.landRegChangeDao.save(landRegChange);
			}else{
				landRegChange = this.landRegChangeDao.queryById(LandRegChange.class, landChangeModel.getId());
				landRegChange.setContractorID(landChangeModel.getContractorID());
				landRegChange.setGeneRegistType(landChangeModel.getGeneRegistType());
				landRegChange.setSpecRegistType(landChangeModel.getSpecRegistType());
				landRegChange.setChangeReason(landChangeModel.getChangeReason());
				landRegChange.setApplicant(landChangeModel.getApplicant());
				landRegChange.setApplicantDate(landChangeModel.getApplicantDate());
				landRegChange.setApplicantTel(landChangeModel.getApplicantTel());
				landRegChange.setStatus(landChangeModel.getStatus());
				landRegChange.setUpdateDate(landChangeModel.getUpdateDate());
				landRegChange.setUpdateUserId(landChangeModel.getUpdateUserId());
				this.landRegChangeDao.save(landRegChange);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:",e);
		}
	}

	@Override
	public void delete(int id) {
		this.landRegChangeDao.delete(LandRegChange.class, id);
		
	}

	@Override
	public void logicDelete(Class<LandRegChange> clazz, int id) {
		this.landRegChangeDao.logicDelete(clazz, id);
		
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.landRegChangeDao.queryForPageModel(entityName, params, pageModel);
	}
	
	@Override
	public void saveReason(LandRegChangeModel landChangeModel) {
		try{
			LandRegChange landRegChange = null;
			if(landChangeModel.getId() != null){
				landRegChange = this.landRegChangeDao.queryById(LandRegChange.class, landChangeModel.getId());
				landRegChange.setStatus( landChangeModel.getStatus() );
				landRegChange.setAuditDate( new Date() );
				landRegChange.setAuditor(landChangeModel.getUpdateUserName());
				landRegChange.setRejectReason( landChangeModel.getRejectReason() );
				
				this.landRegChangeDao.save(landRegChange);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:", e);
		}
	}
	
	@Override
	public List<LandRegChangeModel> updateStatus(String[] ids, BaseModel baseModel) {
		List<LandRegChangeModel> sModels = null;
		if( ids != null && ids.length > 0 ){
			sModels = new ArrayList<LandRegChangeModel>();
			for( String id : ids ){
				try{
					LandRegChange landRegChange = null;
					landRegChange = this.landRegChangeDao.queryById(LandRegChange.class, Integer.valueOf( id ));
					landRegChange.setStatus( "02" );
					landRegChange.setUpdateDate( new Date() );
					landRegChange.setUpdateUserId( baseModel.getUpdateUserId() );
					
					this.landRegChangeDao.save(landRegChange);
					
					//为日志而生
					LandRegChangeModel model = new LandRegChangeModel();
					model.setId(landRegChange.getId());
					model.setApplyBatchNo(landRegChange.getApplyBatchNo());
					model.setStatus(landRegChange.getStatus());
					model.setCreateUserId(landRegChange.getCreateUserId());
					model.setCreateDate(landRegChange.getCreateDate());
					model.setUpdateUserId(landRegChange.getUpdateUserId());
					model.setUpdateDate(landRegChange.getUpdateDate());
					sModels.add(model);
				}catch (Exception e) {
					e.printStackTrace();
					log.error("UserService saveObject ServiceException:", e);
				}
			}
		}
		return sModels;
	}
}
