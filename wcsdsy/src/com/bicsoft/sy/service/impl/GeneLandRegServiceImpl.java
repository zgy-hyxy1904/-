package com.bicsoft.sy.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.GeneLandDetailDao;
import com.bicsoft.sy.dao.GeneLandRegDDao;
import com.bicsoft.sy.dao.GeneLandRegDao;
import com.bicsoft.sy.entity.Contract;
import com.bicsoft.sy.entity.GeneLandDetail;
import com.bicsoft.sy.entity.GeneLandReg;
import com.bicsoft.sy.entity.GeneLandRegD;
import com.bicsoft.sy.entity.Peasant;
import com.bicsoft.sy.entity.SpecLandReg;
import com.bicsoft.sy.model.GeneLandRegModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.ContractService;
import com.bicsoft.sy.service.GeneLandRegService;
import com.bicsoft.sy.service.PeasantService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class GeneLandRegServiceImpl implements GeneLandRegService {
	private static final Logger log = LoggerFactory.getLogger(ProvEvalServiceImpl.class);
	
	@Autowired
	private GeneLandRegDao geneLandRegDao;
	
	@Autowired
	private GeneLandRegDDao geneLandRegDDao;
	
	@Autowired
	private GeneLandDetailDao geneLandDetailDao;
	
	@Autowired
	private PeasantService peasantService;
	
	@Autowired
	private ContractService contractService;
	
	@Override
	public GeneLandReg getGeneLandReg(int id) {
		return this.geneLandRegDao.queryById(GeneLandReg.class, id);
	}

	@Override
	public void save(GeneLandRegModel geneLandRegModel) {
		try{
			GeneLandReg entity = null;
			if(geneLandRegModel.getId() == null){
				entity = (GeneLandReg) POVOConvertUtil.convert(geneLandRegModel, "com.bicsoft.sy.entity.GeneLandReg");
				if( "02".equals( geneLandRegModel.getStatus() ) ){
					entity.setAuditor( geneLandRegModel.getCreateUserId() );
					entity.setAuditTime( geneLandRegModel.getCreateDate() );
				}
				entity.setCreateDate( geneLandRegModel.getCreateDate() );
				entity.setCreateUserId( geneLandRegModel.getCreateUserId());
				entity.setUpdateDate( geneLandRegModel.getUpdateDate() );
				entity.setUpdateUserId( geneLandRegModel.getUpdateUserId() );
				this.geneLandRegDao.save(entity);
				geneLandRegModel.setId( entity.getId() );
				/**
				 * 这里保存子表的信息
				 */
				List<GeneLandRegD> list = geneLandRegModel.getList();
				if( list != null && list.size() > 0 ){
					for( GeneLandRegD geneLandRegD : list  ){
						geneLandRegD.setHid( entity.getId() );
						geneLandRegD.setCityCode( "230184" );
						geneLandRegD.setCreateDate( geneLandRegModel.getCreateDate() );
						geneLandRegD.setCreateUserId( geneLandRegModel.getCreateUserId());
						geneLandRegD.setUpdateDate( geneLandRegModel.getUpdateDate() );
						geneLandRegD.setUpdateUserId( geneLandRegModel.getUpdateUserId() );
						
						this.geneLandRegDDao.save( geneLandRegD );
						
						Integer hid = geneLandRegD.getId();
						//保存子表信息
						insertDetail( hid, geneLandRegD );
					}
				}
			}else{   //修改
				entity = this.geneLandRegDao.queryById(GeneLandReg.class, geneLandRegModel.getId());
				entity.setStatus(geneLandRegModel.getStatus());
				entity.setUpdateDate(geneLandRegModel.getUpdateDate());
				entity.setUpdateUserId(geneLandRegModel.getUpdateUserId());
				
				if( "02".equals( geneLandRegModel.getStatus() ) ){
					entity.setAuditor( geneLandRegModel.getCreateUserId() );
					entity.setAuditTime( geneLandRegModel.getCreateDate() );
				}
				this.geneLandRegDao.save(entity);
				/**
				 * 这里保存子表的信息,先删除再保存
				 */
				List<GeneLandRegD> list = geneLandRegModel.getList();
				if( list != null && list.size() > 0 ){
					//删除子表数据
					this.geneLandRegDDao.delete("delete from b_generallandregd WHERE hid=" + entity.getId(), null);
					for( GeneLandRegD geneLandRegD : list  ){
						geneLandRegD.setHid( entity.getId() );
						geneLandRegD.setCityCode( "230184" );  //五常市
						geneLandRegD.setCreateDate( geneLandRegModel.getCreateDate() );
						geneLandRegD.setCreateUserId( geneLandRegModel.getCreateUserId());
						geneLandRegD.setUpdateDate( geneLandRegModel.getUpdateDate() );
						geneLandRegD.setUpdateUserId( geneLandRegModel.getUpdateUserId() );
						
						this.geneLandRegDDao.save( geneLandRegD );
						
						Integer hid = geneLandRegD.getId();
						//保存子表信息
						insertDetail( hid, geneLandRegD );
					}
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:",e);
		}
	}
	
	private boolean insertDetail( Integer hid, GeneLandRegD geneLandRegD ){
		Peasant peasant = peasantService.getByContractorID( geneLandRegD.getIdType(), geneLandRegD.getContractorID());
		if(peasant == null){
			return false;
		}
		List<Contract> contractList = contractService.getContractList( peasant.getContractorIDType(), peasant.getContractorID());
		this.geneLandDetailDao.delete("delete from b_GeneralLandDetails where hid=" + geneLandRegD.getId(), null);
		if( contractList != null && contractList.size() > 0 ){
			for( Contract con : contractList ){
				GeneLandDetail detail = new GeneLandDetail();
				detail.setHid( hid );
				detail.setActualMu( con.getContractArea() );
				detail.setMeasurementMu( con.getMeasurementMu() );
				detail.setLandType( con.getLandType() );
				detail.setLandClass( con.getLandClass() == null ? "" : con.getLandClass() );
				detail.setCityCode( geneLandRegD.getCityCode() );
				detail.setCountryCode( geneLandRegD.getCountryCode() );
				detail.setTownCode( geneLandRegD.getTownCode() );
				detail.setGroupName( geneLandRegD.getGroupName() );
				detail.setLandName( con.getLandName() );
				
				detail.setCreateDate( geneLandRegD.getCreateDate() );
				detail.setCreateUserId( geneLandRegD.getCreateUserId() );
				detail.setUpdateDate( geneLandRegD.getUpdateDate() );
				detail.setUpdateUserId( geneLandRegD.getUpdateUserId() );
				
				this.geneLandDetailDao.save( detail );
			}
		}
		
		return true;
	}

	@Override
	public void delete(int id) {
		this.geneLandRegDao.delete(GeneLandReg.class, id);
	}

	@Override
	public void logicDelete(Class<GeneLandReg> clazz, int id) {
		this.geneLandRegDao.logicDelete(clazz, id);
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.geneLandRegDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public Float getThisWeightSum(String id) {
		String sql = "select ifnull(sum(ArchiveAcreage), 0) as mjsum,ifnull(sum(ArchiveAcreage), 0) as mjsum1 from b_generallandregd ";
		sql += " where deleteFlag='N' and hid='" + id + "'";		
				
		List<Object[]> results = geneLandRegDDao.queryBySQL(sql, null);
		
		return Float.parseFloat(results.get(0)[0] + "");
	}

	@Override
	public List<GeneLandDetail> getGeneLandDetailList(Integer id) {
		List<GeneLandDetail> results = this.geneLandDetailDao.queryByHQL("from GeneLandDetail where deleteFlag='N' and hid='" + id + "'" , null);
		
		return results;
	}

	@Override
	public int queryGeneLandRegInfosCount(String yearCode,String idType,String contractorID){
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT detail.id ");
		buffer.append(" from b_generallandregh head,b_generallandregd detail,b_generallanddetails details ");
		buffer.append("WHERE head.id=detail.hid and detail.id=details.hid and head.deleteFlag='N' and detail.deleteFlag='N' and details.deleteFlag='N' ");
		buffer.append(" and head.year='"+yearCode+"' and detail.iDType='"+idType+ "' and detail.contractorID='"+contractorID+ "'");
		List<Object[]> results = this.geneLandDetailDao.queryBySQL(buffer.toString(), null);
        if(results!=null){
        	return results.size();
        }else{
        	return 0;
        }
	}
	

}
