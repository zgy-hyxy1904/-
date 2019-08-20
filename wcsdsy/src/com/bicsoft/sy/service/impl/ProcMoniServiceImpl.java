package com.bicsoft.sy.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.FileManagerDao;
import com.bicsoft.sy.dao.ProcMoniDao;
import com.bicsoft.sy.entity.AirMoni;
import com.bicsoft.sy.entity.Mfile;
import com.bicsoft.sy.entity.ProcMoni;
import com.bicsoft.sy.entity.ProvEval;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.ProcMoniModel;
import com.bicsoft.sy.service.ProcMoniService;
import com.bicsoft.sy.util.DictUtil;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class ProcMoniServiceImpl implements ProcMoniService {
	private static final Logger log = LoggerFactory.getLogger(ProcMoniServiceImpl.class);
	
	@Autowired
	private ProcMoniDao procMoniDao;
	
	@Autowired
	protected FileManagerDao fileManagerDao;
	
	@Override
	public ProcMoni getProcMoni(int id) {
		return this.procMoniDao.queryById(ProcMoni.class,id);
	}

	@Override
	public void save(ProcMoniModel procMoniModel) {
		try{
			ProcMoni procMoni = null;
			if(procMoniModel.getId() == null){
				procMoni = (ProcMoni) POVOConvertUtil.convert(procMoniModel, "com.bicsoft.sy.entity.ProcMoni");
				procMoni.setCompanyCode( procMoniModel.getCompanyCode() );
				procMoni.setCompanyName( procMoniModel.getCompanyName() == null ? "" : procMoniModel.getCompanyName() );
				procMoni.setBizCode( procMoniModel.getBizCode() == null ? "" :  procMoniModel.getBizCode());
				procMoni.setBizType( procMoniModel.getBizType() == null ? "" :  procMoniModel.getBizType());
				procMoni.setYear( procMoniModel.getYear() );
				
				procMoni.setCreateDate( procMoniModel.getCreateDate() );
				procMoni.setCreateUserId( procMoniModel.getCreateUserId() );
				procMoni.setUpdateDate( procMoniModel.getUpdateDate() );
				procMoni.setUpdateUserId( procMoniModel.getUpdateUserId() );
				
				this.procMoniDao.save(procMoni);
				
				procMoniModel.setId( procMoni.getId() );
			}else{
				procMoni = this.procMoniDao.queryById(ProcMoni.class, procMoniModel.getId());
				procMoni.setUpdateDate(procMoniModel.getUpdateDate());
				procMoni.setUpdateUserId(procMoniModel.getUpdateUserId());
				procMoni.setBizType( procMoniModel.getBizType() == null ? "" :  procMoniModel.getBizType());
				procMoni.setYear( procMoniModel.getYear() );
				procMoni.setCompanyCode( procMoniModel.getCompanyCode() );
				
				this.procMoniDao.save(procMoni);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:",e);
		}
	}

	@Override
	public void delete(int id) {
		this.procMoniDao.delete(ProcMoni.class, id);
	}

	@Override
	public void logicDelete(Class<ProcMoni> clazz, int id) {
		this.procMoniDao.logicDelete(clazz, id);
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.procMoniDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public List<ProvEval> getProcMoniList() {
		return null;
	}

	@Override
	public ProcMoni getProcMoni(String year, String bizType,
			String companyCode) {
		ProcMoni procMoni = null;
		List<ProcMoni> results = this.procMoniDao.queryByHQL("from ProcMoni where deleteFlag='N' and year='" + year + "' and bizType='" + bizType + "' and companyCode='" + companyCode + "'" , null);
		if(results == null || results.size() < 1){
			try {
				throw new RuntimeException("数据库中不存在ProcMoni=" + companyCode);
			} catch (RuntimeException e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				procMoni = new ProcMoni();
			}
		}else{
			procMoni = results.get(0);
		}
		
		return procMoni;
	}

	public Map getProcMoniList(String year, String companyCode){
		return this.procMoniDao.getProcMoniList(year, companyCode);
	}
	
	public boolean hasProcMoniFile(String year, String companyCode){
		return this.procMoniDao.hasProcMoniFile(year, companyCode);
	}
}
