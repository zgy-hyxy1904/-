package com.bicsoft.sy.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.GraiEvalDao;
import com.bicsoft.sy.dao.GraiForeDDao;
import com.bicsoft.sy.dao.GraiForeDao;
import com.bicsoft.sy.entity.CommonData;
import com.bicsoft.sy.entity.Contract;
import com.bicsoft.sy.entity.GraiFore;
import com.bicsoft.sy.entity.GraiForeD;
import com.bicsoft.sy.entity.Peasant;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.GraiExcelModel;
import com.bicsoft.sy.model.GraiForeModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.ContractService;
import com.bicsoft.sy.service.GraiForeService;
import com.bicsoft.sy.service.PeasantService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class GraiForeServiceImpl implements GraiForeService {
	private static final Logger log = LoggerFactory.getLogger(GraiForeServiceImpl.class);
	
	@Autowired
	private GraiForeDao graiForeDao;
	
	@Autowired
	private GraiForeDDao graiForeDDao;
	
	@Autowired
	private GraiEvalDao graiEvalDao;
	
	@Autowired
	private PeasantService peasantService;
	
	@Autowired
	private ContractService contractService;
	
	@Override
	public GraiFore getGraiFore(int id) {
		return this.graiForeDao.queryById(GraiFore.class, id);
	}

	@Override
	public void save(GraiForeModel graiForeModel) {
		try{
			GraiFore graiFore = null;
			if(graiForeModel.getId() == null){
				graiFore = (GraiFore) POVOConvertUtil.convert(graiForeModel, "com.bicsoft.sy.entity.GraiFore");
				this.graiForeDao.save(graiFore);
			}else{
				graiFore = this.graiForeDao.queryById(GraiFore.class, graiForeModel.getId());
				graiFore.setUpdateDate(graiForeModel.getUpdateDate());
				graiFore.setUpdateUserId(graiForeModel.getUpdateUserId());
				
			}
			this.graiForeDao.save(graiFore);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:", e);
		}
	}

	@Override
	public void delete(int id) {
		this.graiForeDao.delete(GraiFore.class, id);

	}

	@Override
	public void logicDelete(Class<GraiFore> clazz, int id) {
		this.graiForeDao.logicDelete(clazz, id);

	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.graiForeDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public void saveImportData(String year, String companyCode, String companyName,
			List<GraiExcelModel> list, BaseModel baseModel) {
		/**
		 * 数据导入,先保存主表,再保存子表
		 */
		try{
			if( list != null && list.size() > 0 ){
				Map<String, Float> map = this.graiEvalDao.getInfo( year );  //获取参数
				for( GraiExcelModel model : list ){
					GraiFore graiFore = new GraiFore();
					graiFore.setCompanyCode( companyCode );
					graiFore.setCompanyName( companyName );
					graiFore.setForecastDate( new Date() );
					graiFore.setFarmerName( model.getName() );
					graiFore.setIdNumber( model.getIdNumber() );
					graiFore.setYear( year );
					graiFore.setCreateDate( baseModel.getCreateDate() );
					graiFore.setCreateUserId( baseModel.getCreateUserId() );
					graiFore.setUpdateDate( baseModel.getUpdateDate() );
					graiFore.setUpdateUserId( baseModel.getUpdateUserId() );
					
					List<Contract> cList = getMuInfo(  model.getIdNumber(),"01", "01", map );  //获取面积和产量
					if( cList != null ){   //存在信息时调用
						graiFore.setActualMu( map.get("htMj") );
						graiFore.setMeasurementMu( map.get("scMj") );
						graiFore.setMinEstimateTotalYield( map.get("htMj") * map.get("min") );
						graiFore.setMaxEstimateTotalYield( map.get("htMj") * map.get("max") );
						this.graiForeDao.save( graiFore );   //保存主表信息
						/**
						 * 保存子表数据
						 */
						for( Contract c : cList ){
							GraiForeD d = new GraiForeD();
							d.setHid( graiFore.getId() );
							d.setActualMu( c.getContractArea() );
							d.setMeasurementMu( c.getMeasurementMu() );
							d.setLandClass( c.getLandClass() == null ? "": c.getLandClass());
							d.setLandType( c.getLandType() );
							d.setLandName( c.getLandName() );
							d.setCreateDate( baseModel.getCreateDate() );
							d.setCreateUserId( baseModel.getCreateUserId() );
							d.setUpdateDate( baseModel.getUpdateDate() );
							d.setUpdateUserId( baseModel.getUpdateUserId() );
							
							this.graiForeDDao.save( d );
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:", e);
		}
	}

	private List<Contract> getMuInfo(String idNumber, String idType, String cType, Map<String, Float> map){
		Peasant peasant = peasantService.getByContractorID( idType, idNumber);
		
		if( peasant != null ) {
			List<Contract> contractList = this.contractService.getContractList( peasant.getContractorIDType(), peasant.getContractorID());
			if( contractList != null && contractList.size() > 0 ){
				Float htMj = 0.0f;
				Float scMj = 0.0f;
				for( Contract contract : contractList){
					htMj += contract.getContractArea();
					scMj += contract.getMeasurementMu();
				}
				map.put("htMj"  , htMj);
				map.put("scMj"  , scMj);
			}else{
				map.put("htMj"  , 0.0f);
				map.put("scMj"  , 0.0f);
			}
			
			return contractList;
		}else{
			return null;
		}
	}

	@Override
	public int getZyInfo(String companyCode, String idNumber, String year) {
		StringBuffer sql = new StringBuffer("select count(id) as COUNT, count(id) as COUNT1 from b_grainforecasth WHERE DeleteFlag='N' ");
		sql.append(" AND year='");
		sql.append( year );
		sql.append( "' AND companyCode!='" );
		sql.append( companyCode );
		sql.append("' AND idNumber='");
		sql.append( idNumber );
		sql.append("'");
		
		List<Object[]> results = this.graiForeDDao.queryBySQL(sql.toString(), null);
		Object count = (Object)results.get(0)[0];
		return Integer.valueOf( count.toString() );
	}
	
	@Override
	public GraiFore getGraiForeByIdNumer(String companyCode, String idNumber, String year) {
		GraiFore gf = null;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("companyCode", companyCode);
		params.put("idNumber", idNumber);
		params.put("year", year);
		List<GraiFore> results = this.graiForeDao.queryByHQL("from GraiFore where companyCode=:companyCode and idNumber=:idNumber and year=:year and DeleteFlag='N'", params);
		
		if(results!=null && results.size()>0){
			gf = results.get(0);
		}
		return gf;
	}
	
}
