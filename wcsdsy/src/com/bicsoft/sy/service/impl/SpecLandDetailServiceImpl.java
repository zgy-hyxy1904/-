package com.bicsoft.sy.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.SpecLandDetailDao;
import com.bicsoft.sy.entity.SpecLandDetail;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SpecLandDetailModel;
import com.bicsoft.sy.service.SpecLandDetailService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class SpecLandDetailServiceImpl implements SpecLandDetailService {
	private static final Logger log = LoggerFactory.getLogger(SpecLandDetailServiceImpl.class);

	@Autowired
	private SpecLandDetailDao specLandDetailDao;
	
	@Override
	public SpecLandDetail getSpecLandRegDetail(int id) {
		return this.specLandDetailDao.queryById(SpecLandDetail.class,id);
	}

	@Override
	public void save(SpecLandDetailModel specLandRegDetailModel) {
		try{
			SpecLandDetail entity = null;
			if(specLandRegDetailModel.getId() == null){
				entity = (SpecLandDetail) POVOConvertUtil.convert(specLandRegDetailModel, "com.bicsoft.sy.entity.ProvEval");
				entity.setRemark("remark");
				this.specLandDetailDao.save(entity);
			}else{
				entity = this.specLandDetailDao.queryById(SpecLandDetail.class, specLandRegDetailModel.getId());
				entity.setUpdateDate(specLandRegDetailModel.getUpdateDate());
				entity.setUpdateUserId(specLandRegDetailModel.getUpdateUserId());
			}
			this.specLandDetailDao.save(entity);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:",e);
		}
	}

	@Override
	public void delete(int id) {
		this.specLandDetailDao.delete(SpecLandDetail.class, id);

	}

	@Override
	public void logicDelete(Class<SpecLandDetail> clazz, int id) {
		this.specLandDetailDao.logicDelete(clazz, id);
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.specLandDetailDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public SpecLandDetail getSpecLandDetail(String hid) {
		SpecLandDetail specLandDetail = null;
		List<SpecLandDetail> results = this.specLandDetailDao.queryByHQL("from SpecLandDetail where deleteFlag='N' and hid='" + hid + "'" , null);
		if(results==null || results.size()<1){
			try {
				throw new RuntimeException("数据库中不存在SpecLandDetail="+hid);
			} catch (RuntimeException e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				specLandDetail = new SpecLandDetail();
			}
		}else{
			specLandDetail = results.get(0);
		}
		return specLandDetail;
	}

	@Override
	public List<SpecLandDetail> getSpecLandDetailList(int hid) {
		List<SpecLandDetail> results = this.specLandDetailDao.queryByHQL("from SpecLandDetail where deleteFlag='N' and hid='" + hid + "'" , null);
		return results;
	}
}
