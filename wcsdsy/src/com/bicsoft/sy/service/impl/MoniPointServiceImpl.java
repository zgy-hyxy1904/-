package com.bicsoft.sy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.impl.MoniPointDaoImpl;
import com.bicsoft.sy.entity.MoniPoint;
import com.bicsoft.sy.model.MoniPointModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.MoniPointService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class MoniPointServiceImpl implements MoniPointService {
	private static final Logger log = LoggerFactory.getLogger(YearCodeServiceImpl.class);
	
	@Autowired
	private MoniPointDaoImpl moniPointDao;


	@Override
	public void save(MoniPointModel moniPointModel) {
		try{
			MoniPoint moniPoint = null;
			if(moniPointModel.getId() == null){
				moniPoint = (MoniPoint) POVOConvertUtil.convert(moniPointModel, "com.bicsoft.sy.entity.MoniPoint");
				moniPoint.setCreateDate( moniPointModel.getCreateDate() );
				moniPoint.setCreateUserId( moniPointModel.getCreateUserId() );
				moniPoint.setUpdateDate( moniPointModel.getUpdateDate() );
				moniPoint.setUpdateUserId( moniPointModel.getUpdateUserId() );
				
				this.moniPointDao.save(moniPoint);
			}else{
				moniPoint = this.moniPointDao.queryById(MoniPoint.class, moniPointModel.getId());
				moniPoint.setUpdateDate(moniPointModel.getUpdateDate());
				moniPoint.setUpdateUserId(moniPointModel.getUpdateUserId());
				
				moniPoint.setMonitorPointCode( moniPointModel.getMonitorPointCode() );
				moniPoint.setLatitude( moniPointModel.getLatitude() );
				moniPoint.setLongitude( moniPointModel.getLongitude() );
				moniPoint.setSectionDescription( moniPointModel.getSectionDescription() );
				moniPoint.setMonitorPointAddress( moniPointModel.getMonitorPointAddress() );
				moniPoint.setMonitorPointCode( moniPointModel.getMonitorPointCode() );
				moniPoint.setMonitorPointName(moniPointModel.getMonitorPointName() );
				moniPoint.setMonitorPointType(moniPointModel.getMonitorPointType() );
				this.moniPointDao.save(moniPoint);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:", e);
		}
	}

	@Override
	public void delete(int id) {
		this.moniPointDao.delete(MoniPoint.class, id);
		
	}

	@Override
	public void logicDelete(Class<MoniPoint> clazz, int id) {
		this.moniPointDao.delete(MoniPoint.class, id);
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.moniPointDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public MoniPoint getMoniPoint(String moniPointCode) {
		MoniPoint moniPoint = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("monitorPointCode", moniPointCode);
		List<MoniPoint> results = this.moniPointDao.queryByHQL("from MoniPoint where deleteFlag='N' and monitorPointCode=:monitorPointCode", params);
		if(results==null || results.size()<1){
			try {
				throw new RuntimeException("数据库中不存在monitorPointCode="+moniPointCode+"的监测点");
			} catch (RuntimeException e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				moniPoint = new MoniPoint();
			}
		}else{
			moniPoint = results.get(0);
		}
		return moniPoint;
	}

	@Override
	public MoniPoint getMoniPoint(int id) {
		return this.moniPointDao.queryById(MoniPoint.class, id);
	}

	@Override
	public List<MoniPoint> getMoniPointList() {
		return this.moniPointDao.queryByHQL("from MoniPoint where deleteFlag='N' ", null);
	}

	@Override
	public List<MoniPoint> getMoniPointList(String moniPointType) {
		return this.moniPointDao.queryByHQL("from MoniPoint where deleteFlag='N' AND monitorPointType='" + moniPointType + "'", null);
	}
}
