package com.bicsoft.sy.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.GeneLandRegDDao;
import com.bicsoft.sy.entity.CommonData;
import com.bicsoft.sy.entity.GeneLandRegD;
import com.bicsoft.sy.model.GeneLandRegDModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.AreaDevisionService;
import com.bicsoft.sy.service.CommonDataService;
import com.bicsoft.sy.service.GeneLandRegDService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class GeneLandRegDServiceImpl implements GeneLandRegDService {
	private static final Logger log = LoggerFactory.getLogger(ProvEvalServiceImpl.class);
	
	@Autowired
	private GeneLandRegDDao geneLandRegDDao;
	
	@Autowired
	private AreaDevisionService areaDevisionService;
	@Autowired
	private CommonDataService commonDataService;
	
	@Override
	public GeneLandRegD getGeneLandRegD(int id) {
		return this.geneLandRegDDao.queryById(GeneLandRegD.class, id);
	}

	
	@Override
	public void save(GeneLandRegDModel geneLandRegDModel) {
		try{
			GeneLandRegD entity = null;                                                                                       
			if(geneLandRegDModel.getId() == null){                                                           
				entity = (GeneLandRegD) POVOConvertUtil.convert(geneLandRegDModel, "com.bicsoft.sy.entity.geneLandRegD");                                                                                         
				this.geneLandRegDDao.save(entity);                                                                
			}else{
				entity = this.geneLandRegDDao.queryById(GeneLandRegD.class, geneLandRegDModel.getId());
				entity.setUpdateDate(geneLandRegDModel.getUpdateDate());                                               
				entity.setUpdateUserId(geneLandRegDModel.getUpdateUserId());                                       
			}
			this.geneLandRegDDao.save(entity);                                                               
		}                                                
		catch (Exception e) {                                                         
			e.printStackTrace();                                                                                                         
			log.error("UserService saveObject ServiceException:",e);                                                                  
		}                                                                  
	}                                         

	@Override
	public void delete(int id) {
		this.geneLandRegDDao.delete(GeneLandRegD.class, id);

	}

	@Override
	public void logicDelete(Class<GeneLandRegD> clazz, int id) {
		this.geneLandRegDDao.logicDelete(clazz, id);

	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.geneLandRegDDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public GeneLandRegD getGeneLandRegD(String hid) {
		GeneLandRegD geneLandRegD = null;
		List<GeneLandRegD> results = this.geneLandRegDDao.queryByHQL("from GeneLandRegD where deleteFlag='N' and hid='" + hid + "'" , null);
		if(results==null || results.size()<1){
			try {
				throw new RuntimeException("数据库中不存在SpecLandDetail="+hid);
			} catch (RuntimeException e) {
				e.printStackTrace();
				log.debug(e.getMessage());
				geneLandRegD = new GeneLandRegD();
			}
		}else{
			geneLandRegD = results.get(0);
		}
		return geneLandRegD;
	}

	@Override
	public List<GeneLandRegD> getGeneLandRegDList(String hid) {
		List<GeneLandRegD> results = this.geneLandRegDDao.queryByHQL("from GeneLandRegD where deleteFlag='N' and hid='" + hid + "'" , null);
		 
		return results;
	}

	@Override
	public Float queryBAmj(String idNumber, String year) {
		String sql = "select IFNULL(sum(IFNULL(d.ArchiveAcreage, 0)), 0) as mjsum,IFNULL(sum(IFNULL(d.ArchiveAcreage, 0)), 0) as mjsum1 from b_generallandregh h, b_generallandregd d "+
				" where h.DeleteFlag='N' AND h.status='02' and h.ID = d.HID and d.ContractorID='"+ idNumber +"' and h.year = '" + year + "'";
				
			List<Object[]> results = geneLandRegDDao.queryBySQL(sql, null);
			
			return Float.parseFloat(results.get(0)[0] + "");
	}

	public static void main(String [] args){
//		List<String> key = new ArrayList<String>();
//		key.add("1");
//		key.add("1");
		
		Map<String, Integer> key = new HashMap<String, Integer>();
		key.put("1", 1);
		key.put("1", 1);
		
		System.out.println( key.size() );
	}
	
	@Override
	public List<GeneLandRegD> getGeneLandRegDList(String year,
			String companyCode) {
		List<GeneLandRegD> ld = new ArrayList<GeneLandRegD>();
		StringBuffer sql = new StringBuffer("select * from b_generallandregd where hid in (");
		sql.append("select id from b_generallandregh where year='");
		sql.append( new Integer(year).intValue() - 1 );
		sql.append("' and CompanyCode='");
		sql.append( companyCode );		
		sql.append( "' and status='02') order by CreateDate" );
		
		List<Object[]> list = this.geneLandRegDDao.queryBySQL(sql.toString(), null);
		Map<String, Integer> key = new HashMap<String, Integer>();
		if( list != null ){
			GeneLandRegD d = new GeneLandRegD();
			for( Object[] obj: list ){
				String keyVal = obj[3] + "," + obj[4]; 
				if( key.get(keyVal) == null ){
					key.put(keyVal, 1);
				}else{
					int val = key.get(keyVal);
					key.put(keyVal, val + 1);
				}
			}
		}else{
			return null;
		}
		
		if( key.size() == list.size() ){//相同表示没有重复备案记录
			for( Object[] obj: list ){
				ld.add( processData( obj ) );
			}
		}else{
			Iterator it = key.keySet().iterator();
			while( it.hasNext() ){
				String keys = (String)it.next();
				String idType = keys.split(",")[0];
				String idNumber = keys.split(",")[1];
				if( key.get( keys ) > 1 ){    //需要合并时
					GeneLandRegD dd = new GeneLandRegD(); 
					//特殊处理
					BigDecimal archiveAcreage = new BigDecimal("0.0");  //本次备案面积
					BigDecimal registeredTotalYield = new BigDecimal("0.0");  //已备案面积
					for( Object[] obj: list ){
						if( idType != null && idType.equals(obj[3] ) && idNumber != null && idNumber.equals( obj[4] ) ){
							dd = processData( obj );
							archiveAcreage = archiveAcreage.add( new BigDecimal((Float)obj[11]) );
						}
					}
					dd.setArchiveAcreage( archiveAcreage.floatValue() );
					dd.setRegisteredTotalYield( this.queryBAmj(dd.getContractorID(), year) );
					ld.add( dd );
				}else{
					for( Object[] obj: list ){
						if( idType != null && idType.equals(obj[3] ) && idNumber != null && idNumber.equals( obj[4] ) ){
							ld.add( processData( obj ) );
							break;
						}
					}
				}
			}
		}
		
		return ld;
	}
	
	private GeneLandRegD processData(Object[] obj){
		GeneLandRegD d = new GeneLandRegD();
		d.setId( (Integer)obj[0] );
		d.setHid( (Integer)obj[1] );
		d.setContractorType( (String)obj[2] );
		d.setIdType( (String)obj[3] );
		d.setContractorID( (String)obj[4] );
		d.setContractorName( (String)obj[5] );
		d.setContractorTel( (String)obj[6] );
		d.setCityCode( (String)obj[7] );
		d.setTownCode((String)obj[8] );
		d.setCountryCode( (String)obj[9] );
		d.setGroupName( (String)obj[10] );
		d.setArchiveAcreage( (Float)obj[11] );
		d.setContractTotalYield( (Float)obj[12] );
		d.setRegisteredTotalYield( (Float)obj[13] );
		d.setOperatorName( (String)obj[14] );
		d.setOperatorDate( (Date)obj[15] );
		d.setTownName( this.areaDevisionService.getAreaNameByCode("town", d.getTownCode()) );
		d.setCountryName( this.areaDevisionService.getCountryNameByCode("country", d.getTownCode() + "," + d.getCountryCode()) );
		
		CommonData cData = this.commonDataService.getCommonData("ContractorType", d.getContractorType()); 
		d.setContractorTypeName( cData.getCodeValue() );
		cData = this.commonDataService.getCommonData("IDType", d.getIdType());
		d.setIdName( cData.getCodeValue() );
		Float yba = new BigDecimal( d.getContractTotalYield().toString() ).subtract( new BigDecimal(d.getRegisteredTotalYield().toString()) ).floatValue(); 
		d.setKba( yba );
		
		return d;
	}
	
//	@Override
//	public List<GeneLandRegD> queryGeneLandRegDInfoList(String companyCode, String yearCode,String idType,String contractorID){
//		List<GeneLandRegD> results = this.geneLandRegDDao.queryByHQL(" from GeneLandReg head,GeneLandRegD detail,GeneLandDetail details where head.id=detail.hid and detail.id=details.hid and head.deleteFlag='N' and detail.deleteFlag='N' and details.deleteFlag='N' and head.year='"+yearCode+"' and detail.idType='"+idType+ "' and detail.contractorID='"+contractorID + "'" , null);
//        return results;
//	}
	
	@Override
	public List<Map<String,Object>> queryGeneLandRegDInfoList(String companyCode, String yearCode,String idType,String contractorID){
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT distinct detail.ID AS ID, detail.HID AS HID ");
		buffer.append(" from b_generallandregh head,b_generallandregd detail,b_generallanddetails details where head.id=detail.hid and detail.id=details.hid and head.deleteFlag='N' and detail.deleteFlag='N' and details.deleteFlag='N' and head.year='"+yearCode+"' and detail.idType='"+idType+ "' and detail.contractorID='"+contractorID + "'" );
		List<Object[]> results = geneLandRegDDao.queryBySQL(buffer.toString(), null);
		List<Map<String,Object>> landInfos = new ArrayList<Map<String,Object>>();
		for(Object[] objs : results){
			Map<String,Object> landInfo = new HashMap<String,Object>();
			landInfo.put("id", objs[0]);
			landInfo.put("hid", objs[1]);
			landInfos.add(landInfo);
		}
		return landInfos;
	}
}
