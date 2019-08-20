package com.bicsoft.sy.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.BlackListDDao;
import com.bicsoft.sy.entity.BlackListD;
import com.bicsoft.sy.model.BlackListDModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.BlackListDService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class BlackListDServiceImpl implements BlackListDService {
	private static final Logger log = LoggerFactory.getLogger(ProvEvalServiceImpl.class);
	
	@Autowired
	private BlackListDDao blackListDDao;
	
	@Override
	public BlackListD getBlackListD(int id) {
		return this.blackListDDao.queryById(BlackListD.class, id);
	}

	@Override
	public void save(BlackListDModel blackListDModel) {
		try{
			BlackListD blackListD = null;
			if(blackListDModel.getId() == null){
				blackListD = (BlackListD) POVOConvertUtil.convert(blackListDModel, "com.bicsoft.sy.entity.AirMoni");
				this.blackListDDao.save(blackListD);
			}else{
				blackListD = this.blackListDDao.queryById(BlackListD.class, blackListDModel.getId());
				blackListD.setUpdateDate(blackListDModel.getUpdateDate());
				blackListD.setUpdateUserId(blackListDModel.getUpdateUserId());
				
			}
			this.blackListDDao.save(blackListD);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:", e);
		}

	}

	@Override
	public void delete(int id) {
		this.blackListDDao.delete( BlackListD.class, id );

	}

	@Override
	public void logicDelete(Class<BlackListD> clazz, int id) {
		this.blackListDDao.logicDelete(clazz, id);

	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.blackListDDao.queryForPageModel(entityName, params, pageModel);
	}

}
