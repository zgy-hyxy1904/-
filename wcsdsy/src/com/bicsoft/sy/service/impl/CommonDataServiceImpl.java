package com.bicsoft.sy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.CommonDataDao;
import com.bicsoft.sy.entity.CommonData;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.CommonDataService;

@Service
@Transactional
public class CommonDataServiceImpl implements CommonDataService {
	private static final Logger log = LoggerFactory.getLogger(CommonDataServiceImpl.class);
	
	@Autowired
	private CommonDataDao commonDataDao;
	
	@Override
	public CommonData getCommonData(int id) {
		return this.commonDataDao.queryById(CommonData.class, id);
	}

	@Override
	public void save(CommonData commonData) {
		this.commonDataDao.save(commonData);
	}

	@Override
	public void delete(int id) {
		this.commonDataDao.delete(CommonData.class, id);
	}

	@Override
	public void logicDelete(Class<CommonData> clazz, int id) {
		this.commonDataDao.logicDelete(clazz, id);
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.commonDataDao.queryForPageModel(entityName, params, pageModel);
	}
	
	
	/**
	 * 数据字典可用
	 */
	@Override
	public CommonData getCommonData(String codeKey, String codeCode) {
		CommonData commonData = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("codeKey", codeKey);
		params.put("codeCode", codeCode == null ? "" : codeCode);
		List<CommonData> results = this.commonDataDao.queryByHQL("from CommonData where codeKey=:codeKey and codeCode=:codeCode", params);
		if(results==null || results.size()<1){
			try {
				//throw new RuntimeException("数据库中不存在CodeKey="+codeKey+":CodeCode="+codeCode+"的数据");
				commonData = new CommonData();
			} catch (RuntimeException e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				commonData = new CommonData();
			}
		}else{
			commonData = results.get(0);
		}
		return commonData;
	}

	/**
	 *数据字典可用
	 */
	@Override
	public List<CommonData> getCommonDataListByCodeKey(String codeKey) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("codeKey", codeKey);
		return this.commonDataDao.queryByHQL("from CommonData where codeKey=:codeKey order by codeSort asc", params);
	}
}
