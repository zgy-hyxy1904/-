package com.bicsoft.sy.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.GraiForeDDao;
import com.bicsoft.sy.entity.BlackList;
import com.bicsoft.sy.entity.GraiForeD;
import com.bicsoft.sy.entity.GraiRegDetail;
import com.bicsoft.sy.model.GraiForeDModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.GraiForeDService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class GraiForeDServiceImpl implements GraiForeDService {
	private static final Logger log = LoggerFactory.getLogger(GraiForeDServiceImpl.class);
	
	@Autowired
	private GraiForeDDao graiForeDDao;
	
	@Override
	public GraiForeD getGraiForeD(int id) {
		return this.graiForeDDao.queryById(GraiForeD.class, id);
	}

	@Override
	public void save(GraiForeDModel graiForeDModel) {
		try{
			GraiForeD graiForeD = null;
			if(graiForeDModel.getId() == null){
				graiForeD = (GraiForeD) POVOConvertUtil.convert(graiForeDModel, "com.bicsoft.sy.entity.GraiForeD");
				this.graiForeDDao.save(graiForeD);
			}else{
				graiForeD = this.graiForeDDao.queryById(GraiForeD.class, graiForeDModel.getId());
				graiForeD.setUpdateDate(graiForeDModel.getUpdateDate());
				graiForeD.setUpdateUserId(graiForeDModel.getUpdateUserId());
				
			}
			this.graiForeDDao.save(graiForeD);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:", e);
		}
	}

	@Override
	public void delete(int id) {
		this.graiForeDDao.delete(GraiForeD.class, id);

	}

	@Override
	public void logicDelete(Class<GraiForeD> clazz, int id) {
		this.graiForeDDao.logicDelete(clazz, id);

	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.graiForeDDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public GraiForeD getGraiForeDByHid(int hid){
		GraiForeD graiForeD = null;
		List<GraiForeD> results = this.graiForeDDao.queryByHQL("from GraiForeD where deleteFlag='N' and hid='" + hid + "'" , null);
		if(results==null || results.size()<1){
			try {
				throw new RuntimeException("数据库中不存在SpecLandDetail="+hid);
			} catch (RuntimeException e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				graiForeD = new GraiForeD();
			}
		}else{
			graiForeD = results.get(0);
		}
		return graiForeD;
	}

	@Override
	public List<GraiForeD> getGraiForeDListByHid(int hid){
		List<GraiForeD> results = this.graiForeDDao.queryByHQL("from GraiForeD where deleteFlag='N' and hid='" + hid + "'" , null);
		
		return results;
	}

}
