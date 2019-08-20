package com.bicsoft.sy.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.InputRegDao;
import com.bicsoft.sy.entity.InputReg;
import com.bicsoft.sy.model.InputRegModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.InputRegService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class InputRegServiceImpl implements InputRegService {
	private static final Logger log = LoggerFactory.getLogger(ProvEvalServiceImpl.class);
	
	@Autowired
	private InputRegDao inputRegDao;
	
	@Override
	public InputReg getInputReg(int id) {
		return this.inputRegDao.queryById(InputReg.class, id);
	}

	@Override
	public void save(InputRegModel inputRegModel) {
		try{
			InputReg inputReg = null;
			if(inputRegModel.getId() == null){
				inputReg = (InputReg) POVOConvertUtil.convert(inputRegModel, "com.bicsoft.sy.entity.InputReg");
				inputReg.setPurchaseDate( new Date() );
				inputReg.setCompanyCode( inputRegModel.getCompanyCode() );
				inputReg.setCreateDate( inputRegModel.getCreateDate() );
				inputReg.setCreateUserId( inputRegModel.getCreateUserId() );
				inputReg.setUpdateDate( inputRegModel.getUpdateDate() );
				inputReg.setUpdateUserId( inputRegModel.getUpdateUserId() );
				
				this.inputRegDao.save(inputReg);
				
				inputRegModel.setId( inputReg.getId() );
			}else{
				inputReg = this.inputRegDao.queryById(InputReg.class, inputRegModel.getId());
				inputReg.setUpdateDate(inputRegModel.getUpdateDate());
				inputReg.setUpdateUserId(inputRegModel.getUpdateUserId());
				
				inputReg.setCompanyCode( inputRegModel.getCompanyCode() );
				inputReg.setInputGoodsName( inputRegModel.getInputGoodsName() );
				inputReg.setInputGoodsSupplier( inputRegModel.getInputGoodsSupplier() );
				inputReg.setInputGoodsUnit( inputRegModel.getInputGoodsUnit() );
				inputReg.setPurchaseDate( inputRegModel.getPurchaseDate() );
				inputReg.setPurchasePerson( inputRegModel.getPurchasePerson() );
				inputReg.setPurchaseQuantity( inputRegModel.getPurchaseQuantity() );
			}
			this.inputRegDao.save(inputReg);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:",e);
		}
	}

	@Override
	public void delete(int id) {
		this.inputRegDao.delete(InputReg.class, id);
	}

	@Override
	public void logicDelete(Class<InputReg> clazz, int id) {
		this.inputRegDao.logicDelete(clazz, id);
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.inputRegDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public List<InputReg> getInputRegList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasInputReg(String year, String companyCode){
		return this.inputRegDao.hasInputReg(year, companyCode);
	}
}
