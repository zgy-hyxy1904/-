package com.bicsoft.sy.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.GraiRegDao;
import com.bicsoft.sy.dao.GraiRegDetailDao;
import com.bicsoft.sy.entity.Contract;
import com.bicsoft.sy.entity.GraiReg;
import com.bicsoft.sy.entity.GraiRegDetail;
import com.bicsoft.sy.entity.Peasant;
import com.bicsoft.sy.model.GraiRegModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.ContractService;
import com.bicsoft.sy.service.GraiRegDetailService;
import com.bicsoft.sy.service.GraiRegService;
import com.bicsoft.sy.service.PeasantService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class GraiRegServiceImpl implements GraiRegService {
	private static final Logger log = LoggerFactory.getLogger(ProvEvalServiceImpl.class);
	
	@Autowired
	private GraiRegDao graiRegDao;
	
	@Autowired
	private GraiRegDetailDao graiRegDetailDao;
	
	@Autowired
	private PeasantService peasantService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private GraiRegDetailService graiRegDetailService;
	
	@Override
	public GraiReg getGraiReg(int id) {
		return this.graiRegDao.queryById(GraiReg.class, id);
	}
	
	@Override
	public void save(GraiRegModel graiRegModel) {
		try{
			GraiReg graiReg = null;
			if(graiRegModel.getId() == null){
				graiReg = (GraiReg) POVOConvertUtil.convert(graiRegModel, "com.bicsoft.sy.entity.GraiReg");
				this.graiRegDao.save(graiReg);
				
				graiRegModel.setId( graiReg.getId() );
				/**
				 * 这里保存子表信息
				 */
				GraiRegDetail detail = new GraiRegDetail();
				detail.setHid( graiReg.getId() );
				detail.setFarmerName( graiReg.getFarmerName() );
				detail.setIdNumber( graiReg.getIdNumber() );
				detail.setActualMu( graiRegModel.getActualMu() );
				detail.setMeasurementMu( graiRegModel.getMeasurementMu() == null ? 0.0f : graiRegModel.getMeasurementMu() );
				detail.setEstimateTotalYield( graiRegModel.getEstimateTotalYield() == null ? 0.0f : graiRegModel.getEstimateTotalYield() );
				detail.setSoldYield( graiRegModel.getSoldYield() == null ? 0.0f : graiRegModel.getSoldYield()  );
				detail.setSeedPurchaseTotal( graiRegModel.getSeedPurchaseTotal() == null ? 0.0f : graiRegModel.getSeedPurchaseTotal() );
				detail.setSurplusYield( graiRegModel.getSurplusYield() == null ? 0.0f : graiRegModel.getSurplusYield() );
				detail.setRegisteredTotalYield( graiRegModel.getRegisteredTotalYield() == null ? 0.0f : graiRegModel.getRegisteredTotalYield());
				detail.setGrainTotalYield( graiRegModel.getGrainTotalYield() == null ? 0.0f : graiRegModel.getGrainTotalYield() );
				
				detail.setCreateDate( graiRegModel.getCreateDate() );
				detail.setCreateUserId( graiRegModel.getCreateUserId() );
				detail.setUpdateDate( graiRegModel.getUpdateDate() );
				detail.setUpdateUserId( graiRegModel.getUpdateUserId() );
				
				this.graiRegDetailDao.save( detail );
			} else {
				graiReg = this.graiRegDao.queryById(GraiReg.class, graiRegModel.getId());
				graiReg.setFarmerName( graiRegModel.getFarmerName() );
				graiReg.setIdNumber( graiRegModel.getIdNumber() );
				graiReg.setCompanyCode( graiRegModel.getCompanyCode() );
				graiReg.setCompanyName( graiRegModel.getCompanyName() );
				graiReg.setYear( graiRegModel.getYear() );
				graiReg.setCityCode( graiRegModel.getCityCode() );
				graiReg.setTownCode(graiRegModel.getTownCode() );
				graiReg.setCountryCode( graiRegModel.getCountryCode() );
				graiReg.setGroupName( graiRegModel.getGroupName() );
				graiReg.setThisWeight( graiRegModel.getThisWeight() );
				graiReg.setOperatorDate( graiRegModel.getOperatorDate() );
				graiReg.setOperatorName( graiRegModel.getOperatorName() );
				graiReg.setUpdateDate(graiRegModel.getUpdateDate());
				graiReg.setUpdateUserId(graiRegModel.getUpdateUserId());
				this.graiRegDao.save(graiReg);
				
				/**
				 * 这里需要修改子表数据-先删除再新增
				 */
				GraiRegDetail detail = this.graiRegDetailService.getGraiRegDetailByHid(graiReg.getId());
				detail.setFarmerName( graiReg.getFarmerName() );
				detail.setIdNumber( graiReg.getIdNumber() );
				detail.setActualMu( graiRegModel.getActualMu() );
				detail.setMeasurementMu( graiRegModel.getMeasurementMu() == null ? 0.0f : graiRegModel.getMeasurementMu() );
				detail.setEstimateTotalYield( graiRegModel.getEstimateTotalYield() == null ? 0.0f : graiRegModel.getEstimateTotalYield() );
				detail.setSoldYield( graiRegModel.getSoldYield() == null ? 0.0f : graiRegModel.getSoldYield()  );
				detail.setSeedPurchaseTotal( graiRegModel.getSeedPurchaseTotal() == null ? 0.0f : graiRegModel.getSeedPurchaseTotal() );
				detail.setSurplusYield( graiRegModel.getSurplusYield() == null ? 0.0f : graiRegModel.getSurplusYield() );
				detail.setRegisteredTotalYield( graiRegModel.getRegisteredTotalYield() == null ? 0.0f : graiRegModel.getRegisteredTotalYield());
				detail.setGrainTotalYield( graiRegModel.getGrainTotalYield() == null ? 0.0f : graiRegModel.getGrainTotalYield() );
				
				detail.setUpdateDate( graiRegModel.getUpdateDate() );
				detail.setUpdateUserId( graiRegModel.getUpdateUserId() );
				this.graiRegDetailDao.save(detail);
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:",e);
		}
	}

	private boolean insertDetail( Integer hid, GraiRegModel graiRegModel, GraiReg graiReg){
		Peasant peasant = peasantService.getByContractorID( "01", graiReg.getIdNumber());
		if(peasant == null){
			return false;
		}
		List<Contract> contractList = contractService.getContractList( peasant.getContractorIDType(), peasant.getContractorID());
		if( contractList != null && contractList.size() > 0 ){
			for( Contract con : contractList ){
				GraiRegDetail detail = new GraiRegDetail();
				detail.setHid( hid );
				detail.setActualMu( con.getContractArea() );
				detail.setMeasurementMu( con.getMeasurementMu() );
				detail.setLandType( con.getLandType() );
				detail.setLandClass( con.getLandClass() == null ? "" : con.getLandClass() );
				detail.setFarmerName( graiReg.getFarmerName() );
				detail.setIdNumber( graiReg.getIdNumber() );
				
				detail.setCreateDate( graiReg.getCreateDate() );
				detail.setCreateUserId( graiReg.getCreateUserId() );
				detail.setUpdateDate( graiReg.getUpdateDate() );
				detail.setUpdateUserId( graiReg.getUpdateUserId() );
				
				this.graiRegDetailDao.save( detail );
			}
		}
		
		return true;
	}
	
	@Override
	public void delete(int id) {
		this.graiRegDao.delete(GraiReg.class, id);
	}

	@Override
	public void logicDelete(Class<GraiReg> clazz, int id) {
		this.graiRegDao.logicDelete(clazz, id);
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.graiRegDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public Float getYmcls(String year, String idNumber) {
		String sql = "select IFNULL(sum(ThisWeight), 0) as weight,IFNULL(sum(ThisWeight), 0) as weight1 from b_grainreg WHERE DeleteFlag='N' AND year='" + year +"'";
		sql += " AND idNumber='" + idNumber + "'";
				
		List<Object[]> results = graiRegDao.queryBySQL(sql, null);
		
		return Float.parseFloat(results.get(0)[0] + "");
	}

	@Override
	public Float getYmcls(String year, String idNumber, Integer id) {
		String sql = "select IFNULL(sum(ThisWeight), 0) as weight,IFNULL(sum(ThisWeight), 0) as weight1 from b_grainreg WHERE DeleteFlag='N' AND year='" + year +"'";
		sql += " AND idNumber='" + idNumber + "'";
		sql += " AND id != " + id;		
		
		List<Object[]> results = graiRegDao.queryBySQL(sql, null);
		
		return Float.parseFloat(results.get(0)[0] + "");
	}
}
