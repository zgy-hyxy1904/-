package com.bicsoft.sy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.CompanyDao;
import com.bicsoft.sy.entity.Company;
import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.model.CompanyModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.CompanyService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
	private static final Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	@Autowired
	private CompanyDao companyDao;
	
	@Override
	public Company getCompany(int id) {
		return this.companyDao.queryById(Company.class, id);
	}

	@Override
	public void save(Company company) {
		this.companyDao.save(company);
	}

	@Override
	public void delete(int id) {
		this.companyDao.delete(Company.class, id);
	}

	@Override
	public void logicDelete(Class<Company> clazz, int id) {
		this.companyDao.logicDelete(clazz, id);
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.companyDao.queryForPageModel(entityName, params, pageModel);
	}

	/**
	 * 数据字典可用
	 */
	@Override
	public Company getCompany(String companyCode) {
		Company company = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("companyCode", companyCode);
		List<Company> results = this.companyDao.queryByHQL("from Company where deleteFlag='N' and companyCode=:companyCode", params);
		if(results==null || results.size()<1){
			try {
				throw new RuntimeException("数据库中不存在CompanyCode="+companyCode+"的企业");
			} catch (RuntimeException e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				company = new Company();
			}
		}else{
			company = results.get(0);
		}
		return company;
	}
	
	/**
	 * 数据字典可用
	 */
	@Override
	public List<Company> getCompanyListByCompanyType(String companyType) {
		Map<String,Object> params = null;
		String strHQL = "from Company where deleteFlag='N' ";
		if(companyType!=null && !"".equals(companyType.trim())){
			strHQL += " and companyType=:companyType";
			params = new HashMap<String,Object>();
			params.put("companyType", companyType);
		}
		return this.companyDao.queryByHQL(strHQL, params);
	}
	
	public PageModel queryForPageModel(PageModel pageModel, String companyCode, String companyName, String companyType){
		return this.companyDao.queryForPageModel(pageModel, companyCode, companyName, companyType);
	}
	
	public void save(CompanyModel companyModel){
		try{
			if(companyModel.getId() == null){
				Company company = (Company) POVOConvertUtil.convert(companyModel, "com.bicsoft.sy.entity.Company");
				this.companyDao.save(company);
			}else{
				Company company = this.companyDao.queryById(Company.class, companyModel.getId());
				company.setUpdateDate(companyModel.getUpdateDate());
				company.setLegalPerson(companyModel.getLegalPerson());
				company.setLegalPersonID(companyModel.getLegalPersonID());
				company.setAddress(companyModel.getAddress());
				company.setRegisterDate(companyModel.getRegisterDate());
				company.setConnectName(companyModel.getConnectName());
				company.setConnectPhone(companyModel.getConnectPhone());
				company.setCompanyType(companyModel.getCompanyType());
				company.setRemark(companyModel.getRemark());
				this.companyDao.save(company);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:",e);
		}
		
	}
}
