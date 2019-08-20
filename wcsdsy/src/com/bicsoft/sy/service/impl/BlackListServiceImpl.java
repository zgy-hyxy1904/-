package com.bicsoft.sy.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.BlackListDao;
import com.bicsoft.sy.entity.BlackList;
import com.bicsoft.sy.entity.GraiRegDetail;
import com.bicsoft.sy.model.BlackListModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.BlackListService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class BlackListServiceImpl implements BlackListService {
	private static final Logger log = LoggerFactory.getLogger(BlackListServiceImpl.class);
	
	@Autowired
	private BlackListDao blackListDao;
	
	@Override
	public BlackList getBlackList(int id) {
		return this.blackListDao.queryById(BlackList.class  , id);
	}

	@Override
	public void save(BlackListModel blackListModel) {
		try{
			BlackList blackList = null;
			if(blackListModel.getId() == null){
				blackList = (BlackList) POVOConvertUtil.convert(blackListModel, "com.bicsoft.sy.entity.AirMoni");
				this.blackListDao.save(blackList);
			}else{
				blackList = this.blackListDao.queryById(BlackList.class, blackListModel.getId());
				blackList.setUpdateDate(blackListModel.getUpdateDate());
				blackList.setUpdateUserId(blackListModel.getUpdateUserId());
				
			}
			this.blackListDao.save(blackList);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:", e);
		}

	}

	@Override
	public void delete(int id) {
		this.blackListDao.delete(BlackList.class, id);
	}

	@Override
	public void logicDelete(Class<BlackList> clazz, int id) {
		this.blackListDao.logicDelete(clazz, id);

	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		
		return this.blackListDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public BlackList getBlackListByCompanyCod(String companyCode) {
		BlackList blackList = null;
		List<BlackList> results = this.blackListDao.queryByHQL("from BlackList where deleteFlag='N' and companyCode='" + companyCode + "'" , null);
		if(results == null || results.size() < 1){
			try {
				blackList = new BlackList();
				//throw new RuntimeException("数据库中不存在SpecLandDetail=" + companyCode);
			} catch (RuntimeException e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				blackList = new BlackList();
			}
		}else{
			blackList = results.get(0);
		}
		
		return blackList;
	}
}
