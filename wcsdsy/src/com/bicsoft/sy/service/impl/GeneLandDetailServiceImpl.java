package com.bicsoft.sy.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.GeneLandDetailDao;
import com.bicsoft.sy.entity.GeneLandDetail;
import com.bicsoft.sy.entity.SpecLandDetail;
import com.bicsoft.sy.model.GeneLandDetailModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.GeneLandDetailService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class GeneLandDetailServiceImpl implements GeneLandDetailService {
	private static final Logger log = LoggerFactory.getLogger(ProvEvalServiceImpl.class);
	
	@Autowired
	private GeneLandDetailDao geneLandDetailDao;
	
	@Override
	public GeneLandDetail getGeneLandDetail(int id) {
		return this.geneLandDetailDao.queryById(GeneLandDetail.class, id);
	}

	@Override
	public void save(GeneLandDetailModel geneLandDetailModel) {
		try{
			GeneLandDetail geneLandDetail = null;
			if(geneLandDetailModel.getId() == null){
				geneLandDetail = (GeneLandDetail) POVOConvertUtil.convert(geneLandDetailModel, "com.bicsoft.sy.entity.geneLandDetail");
				this.geneLandDetailDao.save(geneLandDetail);
			}else{
				geneLandDetail = this.geneLandDetailDao.queryById(GeneLandDetail.class, geneLandDetailModel.getId());
				geneLandDetail.setUpdateDate(geneLandDetailModel.getUpdateDate());
				geneLandDetail.setUpdateUserId(geneLandDetailModel.getUpdateUserId());
			}
			this.geneLandDetailDao.save(geneLandDetail);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:",e);
		}
	}

	@Override
	public void delete(int id) {
		this.geneLandDetailDao.delete(GeneLandDetail.class, id);
	}

	@Override
	public void logicDelete(Class<GeneLandDetail> clazz, int id) {
		this.geneLandDetailDao.logicDelete(clazz, id);
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.geneLandDetailDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public GeneLandDetail getGeneLandDetail(String hid) {
		GeneLandDetail geneLandDetail = null;
		List<GeneLandDetail> results = this.geneLandDetailDao.queryByHQL("from GeneLandDetail where deleteFlag='N' and hid='" + hid + "'" , null);
		if(results==null || results.size()<1){
			try {
				throw new RuntimeException("数据库中不存在SpecLandDetail="+hid);
			} catch (RuntimeException e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				geneLandDetail = new GeneLandDetail();
			}
		}else{
			geneLandDetail = results.get(0);
		}
		return geneLandDetail;
	}

	@Override
	public List<GeneLandDetail> getGeneLandDetailList(String hid) {
		List<GeneLandDetail> results = this.geneLandDetailDao.queryByHQL("from GeneLandDetail where deleteFlag='N' and hid='" + hid + "'" , null);
		
		return results;
	}

}
