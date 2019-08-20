package com.bicsoft.sy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.YearCodeDao;
import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.entity.YearCode;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.YearCodeModel;
import com.bicsoft.sy.service.YearCodeService;
import com.bicsoft.sy.util.POVOConvertUtil;
import com.bicsoft.sy.util.StringUtil;

@Service
@Transactional
public class YearCodeServiceImpl implements YearCodeService {
	private static final Logger log = LoggerFactory.getLogger(YearCodeServiceImpl.class);
	
	@Autowired
	private YearCodeDao yearCodeDao;
	
	@Override
	public YearCode getYearCode(int id) {
		return this.yearCodeDao.queryById(YearCode.class, id);
	}

	@Override
	public void save(YearCode yearCode) {
		this.yearCodeDao.save(yearCode);
	}

	@Override
	public void delete(int id) {
		this.yearCodeDao.delete(YearCode.class, id);
	}

	@Override
	public void logicDelete(Class<YearCode> clazz, int id) {
		this.yearCodeDao.logicDelete(clazz, id);
	}

	@Override
	public PageModel queryForPageModel(PageModel pageModel, String yearName){
		return this.yearCodeDao.queryForPageModel(pageModel, yearName);
	}

	/**
	 * 数据字典可用
	 */
	@Override
	public YearCode getYearCode(String yearCode) {
		YearCode yearEntity = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("yearCode", yearCode);
		List<YearCode> results = this.yearCodeDao.queryByHQL("from YearCode where deleteFlag='N' and yearCode=:yearCode", params);
		if(results==null || results.size()<1){
			try {
				throw new RuntimeException("数据库中不存在YearCode="+yearCode+"的年份");
			} catch (RuntimeException e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				yearEntity = new YearCode();
			}
		}else{
			yearEntity = results.get(0);
		}
		return yearEntity;
	}
	
	/**
	 * 数据字典可用
	 */
	@Override
	public List<YearCode> getYearCodeList() {
		return this.yearCodeDao.queryByHQL("from YearCode where deleteFlag='N' ", null);
	}
	
	public void save(YearCodeModel yearModel){
		//企业为空的判断
		try{
			YearCode yearCode = (YearCode) POVOConvertUtil.convert(yearModel, "com.bicsoft.sy.entity.YearCode");
			this.yearCodeDao.save(yearCode);
		}catch(Exception e){
			//e.printStackTrace();
		}
		
	}
}
