package com.bicsoft.sy.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.GraiRegDetailDao;
import com.bicsoft.sy.entity.GraiRegDetail;
import com.bicsoft.sy.model.GraiRegDetailModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.GraiRegDetailService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class GraiRegDetailServiceImpl implements GraiRegDetailService {
	private static final Logger log = LoggerFactory.getLogger(GraiRegDetailServiceImpl.class);

	@Autowired
	private GraiRegDetailDao graiRegDetailDao;
	
	@Override
	public GraiRegDetail getGraiRegDetail(int id) {
		return this.graiRegDetailDao.queryById(GraiRegDetail.class, id);
	}

	@Override
	public void save(GraiRegDetailModel graiRegDetailModel) {
		try{
			GraiRegDetail graiRegDetail = null;
			if(graiRegDetailModel.getId() == null){
				graiRegDetail = (GraiRegDetail) POVOConvertUtil.convert(graiRegDetailModel, "com.bicsoft.sy.entity.GraiEval");
				this.graiRegDetailDao.save(graiRegDetail);
			}else{
				graiRegDetail = this.graiRegDetailDao.queryById(GraiRegDetail.class, graiRegDetailModel.getId());
				graiRegDetail.setUpdateDate(graiRegDetailModel.getUpdateDate());
				graiRegDetail.setUpdateUserId(graiRegDetailModel.getUpdateUserId());
			}
			this.graiRegDetailDao.save(graiRegDetail);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:",e);
		}
	}

	@Override
	public void delete(int id) {
		this.graiRegDetailDao.delete(GraiRegDetail.class, id);
	}

	@Override
	public void logicDelete(Class<GraiRegDetail> clazz, int id) {
		this.graiRegDetailDao.logicDelete(clazz, id);
		
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.graiRegDetailDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public GraiRegDetail getGraiRegDetailByHid(int hid) {
		GraiRegDetail graiRegDetail = null;
		List<GraiRegDetail> results = this.graiRegDetailDao.queryByHQL("from GraiRegDetail where deleteFlag='N' and hid='" + hid + "'" , null);
		if(results==null || results.size()<1){
			try {
				throw new RuntimeException("数据库中不存在SpecLandDetail="+hid);
			} catch (RuntimeException e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				graiRegDetail = new GraiRegDetail();
			}
		}else{
			graiRegDetail = results.get(0);
		}
		return graiRegDetail;
	}

	@Override
	public List<GraiRegDetail> getGraiRegDetailList(int hid) {
		
		return this.graiRegDetailDao.queryByHQL("from GraiRegDetail where deleteFlag='N' and hid='" + hid + "'" , null);
	}

}
