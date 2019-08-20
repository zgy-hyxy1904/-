package com.bicsoft.sy.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.ProvEvalDao;
import com.bicsoft.sy.entity.ProvEval;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.ProvEvalModel;
import com.bicsoft.sy.service.ProvEvalService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class ProvEvalServiceImpl implements ProvEvalService {
	private static final Logger log = LoggerFactory.getLogger(ProvEvalServiceImpl.class);
	
	@Autowired
	private ProvEvalDao provEvalDao;

	@Override
	public ProvEval getProvEval(int id) {
		return this.provEvalDao.queryById(ProvEval.class,id);
	}

	@Override
	public void save(ProvEvalModel provEvalModel) {
		try{
			ProvEval provEval = null;
			if(provEvalModel.getId() == null){
				provEval = (ProvEval) POVOConvertUtil.convert(provEvalModel, "com.bicsoft.sy.entity.ProvEval");
				this.provEvalDao.save(provEval);
			}else{
				provEval = this.provEvalDao.queryById(ProvEval.class, provEvalModel.getId());
				provEval.setUpdateDate(provEvalModel.getUpdateDate());
				provEval.setUpdateUserId(provEvalModel.getUpdateUserId());
				
				provEval.setSeedCode( provEvalModel.getSeedCode() );
				provEval.setMaxYield( provEvalModel.getMaxYield() );
				provEval.setMinYield(provEvalModel.getMinYield());
			}
			this.provEvalDao.save(provEval);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:",e);
		}
	}

	@Override
	public void delete(int id) {
		this.provEvalDao.delete(ProvEval.class, id);
	}

	@Override
	public void logicDelete(Class<ProvEval> clazz, int id) {
		this.provEvalDao.logicDelete(clazz, id);
		
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.provEvalDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public List<ProvEval> getProvEvalList() {
		return null;
	}

	public ProvEval getProvEvalByYear(String yearCode){
		ProvEval provEval = null;
		String hql = "from ProvEval where year=:yearCode AND deleteFlag='N'";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("yearCode", yearCode);
		List<ProvEval> results = provEvalDao.queryByHQL(hql, params);
		if(results==null || results.size()==0){
			provEval = new ProvEval();
		}else{
			provEval = results.get(0);
		}
		return provEval;
	}
	
	@Override
	public Float getLandsArea(String companyCode, String yearCode) {
		/*String sql = "SELECT SUM(IFNULL(A.AREAS,0)) AS AREASUM,CAST(SUM(IFNULL(A.AREAS,0)) AS CHAR) AS CAREASUM FROM ("+
			" SELECT SUM(GD.ArchiveAcreage) AS AREAS FROM B_GeneralLandRegD GD WHERE GD.deleteFlag='N' AND GD.HID IN (SELECT ID FROM b_GeneralLandRegH GH WHERE deleteFlag='N' AND Status='02' AND GH.CompanyCode='"+companyCode+"' AND GH.Year='"+yearCode+"')"+
			" UNION ALL"+
			" SELECT SUM(S.ArchiveAcreage) AS AREAS FROM B_SpecialLandReg S WHERE S.deleteFlag='N' AND S.Status='03' AND S.CompanyCode='"+companyCode+"' AND S.Year='"+yearCode+"') AS A";
		*/
		
		String sql = 
				" SELECT " +
				" 	SUM(IFNULL(T.ArchiveAcreage, 0)) AS AREASUM, " +
				" 	CAST( " +
				" 		SUM(IFNULL(T.ArchiveAcreage, 0)) AS CHAR " +
				" 	) AS CAREASUMFROM " +
				" FROM " +
				" 	( " +
				" 		SELECT " +
				" 			GRD.ContractorName, " +
				" 			CONCAT( " +
				" 				AREAGRD.townName, " +
				" 				AREAGRD.countryName, " +
				" 				GRD.GroupName " +
				" 			) AS HomeAddress, " +
				" 			DATE_FORMAT(GRD.CreateDate, '%Y-%m-%d') AS ArchiveDate, " +
				" 			GRD.ArchiveAcreage " +
				" 		FROM " +
				" 			b_GeneralLandRegH GRH " +
				" 		LEFT JOIN b_GeneralLandRegD GRD ON GRH.ID = GRD.HID " +
				" 		LEFT JOIN m_areadivision AREAGRD ON GRD.cityCode = AREAGRD.cityCode " +
				" 		AND GRD.townCode = AREAGRD.townCode " +
				" 		AND GRD.countryCode = AREAGRD.countryCode " +
				" 		WHERE " +
				" 			GRH.deleteFlag = 'N' " +
				" 		AND GRD.deleteFlag = 'N' " +
				" 		AND GRH.companyCode = '"+companyCode+"' " +
				" 		AND GRH. YEAR = '"+yearCode+"' " +
				"       AND GRH.status='02'"+
				" 		UNION ALL " +
				" 			SELECT " +
				" 				SR.ContractorName, " +
				" 				CONCAT( " +
				" 					AREASR.townName, " +
				" 					AREASR.countryName, " +
				" 					SR.GroupName " +
				" 				) AS HomeAddress, " +
				" 				DATE_FORMAT(SR.CreateDate, '%Y-%m-%d') AS ArchiveDate, " +
				" 				SR.ArchiveAcreage " +
				" 			FROM " +
				" 				b_SpecialLandReg SR " +
				" 			LEFT JOIN m_areadivision AREASR ON SR.cityCode = AREASR.cityCode " +
				" 			AND SR.townCode = AREASR.townCode " +
				" 			AND SR.countryCode = AREASR.countryCode " +
				" 			WHERE " +
				" 				SR.deleteFlag = 'N' " +
				" 			AND SR.companyCode = '"+companyCode+"' " +
				" 			AND SR. YEAR = '"+yearCode+"' " +
				"           AND SR.status='03'"+
				" 	) T ";
		List<Object[]> results = provEvalDao.queryBySQL(sql, null);
		
		if(results == null || results.size()==0 || results.get(0)[0]==null){
			return Float.parseFloat("0");
		}
		return Float.parseFloat(results.get(0)[0] + "");
	
	}
	
	@Override
	//普通土地备案面积
	public Float getGeneLandsArea(String companyCode, String year) {
		String sql = "SELECT SUM(ArchiveAcreage) FROM B_GeneralLandRegD GD WHERE GD.deleteFlag='N' AND GD.HID IN (SELECT ID FROM b_GeneralLandRegH GH WHERE GH.deleteFlag='N' AND GH.Status='02' AND GH.CompanyCode='"+companyCode+"' AND GH.Year='"+year+"')";
		List<Object[]> results = provEvalDao.queryBySQL(sql, null);
		if(results == null || results.size()==0 || results.get(0) == null){
			return Float.parseFloat("0");
		}
		return Float.valueOf(results.get(0)+"");
	}
	
	@Override
	//特殊土地备案面积
	public Float getSpecLandsArea(String companyCode, String year) {
		String sql = "SELECT SUM(ArchiveAcreage) FROM b_SpecialLandReg WHERE CompanyCode='"+companyCode+"' AND Year='"+year+"'" + " and DeleteFlag='N' and Status='03'" ;
		List<Object[]> results = provEvalDao.queryBySQL(sql, null);
		if(results == null || results.size()==0 || results.get(0) == null){
			return Float.parseFloat("0");
		}
		return Float.valueOf(results.get(0)+"");
	}

	@Override
	public PageModel queryLandInfoList(Map<String, Object> params,
			PageModel pageModel) {
		return this.provEvalDao.queryLandInfoList(params, pageModel);
	}
	

	@Override
	public List<Map<String,Object>> queryLandInfos(String companyCode, String yearCode, int page, int pageSize){
		StringBuffer buffer = new StringBuffer();
//		buffer.append("SELECT GRD.ContractorName, AREAGRD.townName||AREAGRD.countryName||GRD.GroupName as HomeAddress,CD.CodeValue,AREAGD.townName||AREAGD.countryName||GD.GroupName as LandAddress,GRD.ArchiveAcreage ");
//		buffer.append("FROM b_GeneralLandRegH GRH ");
//		buffer.append("LEFT JOIN b_GeneralLandRegD GRD ");
//		buffer.append("	ON GRH.ID = GRD.HID ");
//		buffer.append("LEFT JOIN b_GeneralLandDetails GD ");
//		buffer.append("	ON GRH.ID=GD.HID ");
//		buffer.append("LEFT JOIN m_areadivision AREAGRD ");
//		buffer.append("	ON GRD.cityCode=AREAGRD.cityCode AND GRD.townCode=AREAGRD.townCode AND GRD.countryCode=AREAGRD.countryCode ");
//		buffer.append("LEFT JOIN m_areadivision AREAGD ");
//		buffer.append("	ON GD.cityCode=AREAGD.cityCode AND GD.townCode=AREAGD.townCode AND GD.countryCode=AREAGD.countryCode ");
//		buffer.append("LEFT JOIN CommonData CD ");
//		buffer.append("	ON GD.LandClass=CD.CodeCode  AND CD.CodeKey='PlowlandClass' ");
//		buffer.append("WHERE GRH.deleteFlag='N' AND GRD.deleteFlag='N' AND GD.deleteFlag='N' AND GRH.companyCode='"+companyCode+"' AND GRH.Year='"+yearCode+"' ");
//		buffer.append("UNION ALL ");
//		buffer.append("SELECT SR.ContractorName, AREASR.townName||AREASR.countryName||SR.GroupName as HomeAddress,CD.CodeValue as PlowlandName,ARESRD.townName||ARESRD.countryName||SRD.GroupName as LandAddress,SR.ArchiveAcreage  ");
//		buffer.append("FROM b_SpecialLandReg SR ");
//		buffer.append("LEFT JOIN b_SpecialLandRegDetails SRD ");
//		buffer.append("	ON SR.ID=SRD.HID ");
//		buffer.append("LEFT JOIN m_areadivision AREASR ");
//		buffer.append("	ON SR.cityCode=AREASR.cityCode AND SR.townCode=AREASR.townCode AND SR.countryCode=AREASR.countryCode ");
//		buffer.append("LEFT JOIN m_areadivision ARESRD ");
//		buffer.append("	ON SRD.cityCode=ARESRD.cityCode AND SRD.townCode=ARESRD.townCode AND SRD.countryCode=ARESRD.countryCode ");
//		buffer.append("LEFT JOIN CommonData CD ");
//		buffer.append("	ON SRD.LandClass=CD.CodeCode  AND CD.CodeKey='PlowlandClass' ");
//		buffer.append("WHERE SRD.deleteFlag='N' AND SR.deleteFlag='N' AND SR.companyCode='"+companyCode+"' AND SR.Year='"+yearCode+"' ");
		
		buffer.append("SELECT GRD.ContractorName, CONCAT(AREAGRD.townName,AREAGRD.countryName,GRD.GroupName) AS HomeAddress,DATE_FORMAT(GRD.CreateDate,'%Y-%m-%d') AS ArchiveDate,FORMAT(GRD.ArchiveAcreage,2),'普通备案' as landType ");
		buffer.append("FROM b_GeneralLandRegH GRH ");
		buffer.append("LEFT JOIN b_GeneralLandRegD GRD ");
		buffer.append("	ON GRH.ID = GRD.HID ");
		buffer.append("LEFT JOIN m_areadivision AREAGRD ");
		buffer.append("	ON GRD.cityCode=AREAGRD.cityCode AND GRD.townCode=AREAGRD.townCode AND GRD.countryCode=AREAGRD.countryCode ");
		buffer.append("WHERE GRH.status='02' AND GRH.deleteFlag='N' AND GRD.deleteFlag='N' AND GRH.companyCode='"+companyCode+"' AND GRH.Year='"+yearCode+"' ");
		buffer.append("UNION ALL ");
		buffer.append("SELECT SR.ContractorName, CONCAT(AREASR.townName,AREASR.countryName,SR.GroupName) as HomeAddress,DATE_FORMAT(SR.CreateDate,'%Y-%m-%d') AS ArchiveDate,FORMAT(SR.ArchiveAcreage,2),'特殊备案' as landType ");
		buffer.append("FROM b_SpecialLandReg SR ");
		buffer.append("LEFT JOIN m_areadivision AREASR ");
		buffer.append("	ON SR.cityCode=AREASR.cityCode AND SR.townCode=AREASR.townCode AND SR.countryCode=AREASR.countryCode ");
		buffer.append("WHERE SR.status='03' AND SR.deleteFlag='N' AND SR.companyCode='"+companyCode+"' AND SR.Year='"+yearCode+"' ");
		
		buffer.append("LIMIT "+page+" , "+pageSize);
		
		List<Object[]> results = provEvalDao.queryBySQL(buffer.toString(), null);
		List<Map<String,Object>> landInfos = new ArrayList<Map<String,Object>>();
		for(Object[] objs : results){
			Map<String,Object> landInfo = new HashMap<String,Object>();
			landInfo.put("contractorName", objs[0]);
			landInfo.put("homeAddress", objs[1]);
//			landInfo.put("plowlandName", objs[2]);
//			landInfo.put("landAddress", objs[3]);
			landInfo.put("archiveDate", objs[2]);
			landInfo.put("archiveAcreage", objs[3]);
			landInfo.put("landType", objs[4]);
			landInfos.add(landInfo);
		}
		return landInfos;
	}

	@Override
	public int queryLandInfosCount(String companyCode, String yearCode){
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT GRD.ContractorName, CONCAT(AREAGRD.townName,AREAGRD.countryName,GRD.GroupName) AS HomeAddress,DATE_FORMAT(GRD.CreateDate,'%Y-%m-%d') AS ArchiveDate,GRD.ArchiveAcreage ");
		buffer.append("FROM b_GeneralLandRegH GRH ");
		buffer.append("LEFT JOIN b_GeneralLandRegD GRD ");
		buffer.append("	ON GRH.ID = GRD.HID ");
		buffer.append("LEFT JOIN m_areadivision AREAGRD ");
		buffer.append("	ON GRD.cityCode=AREAGRD.cityCode AND GRD.townCode=AREAGRD.townCode AND GRD.countryCode=AREAGRD.countryCode ");
		buffer.append("WHERE GRH.status='02' AND GRH.deleteFlag='N' AND GRD.deleteFlag='N' AND GRH.companyCode='"+companyCode+"' AND GRH.Year='"+yearCode+"' ");
		buffer.append("UNION ALL ");
		buffer.append("SELECT SR.ContractorName, CONCAT(AREASR.townName,AREASR.countryName,SR.GroupName) as HomeAddress,DATE_FORMAT(SR.CreateDate,'%Y-%m-%d') AS ArchiveDate,SR.ArchiveAcreage  ");
		buffer.append("FROM b_SpecialLandReg SR ");
		buffer.append("LEFT JOIN m_areadivision AREASR ");
		buffer.append("	ON SR.cityCode=AREASR.cityCode AND SR.townCode=AREASR.townCode AND SR.countryCode=AREASR.countryCode ");
		buffer.append("WHERE SR.status='03' AND SR.deleteFlag='N' AND SR.companyCode='"+companyCode+"' AND SR.Year='"+yearCode+"' ");
		
		List<Object[]> results = provEvalDao.queryBySQL(buffer.toString(), null);
		if(results!=null){
			return results.size();
		}else{
			return 0;
		}
	}
	
	@Override
	public ProvEval getProvEval(String year, String seedCode) {
		String hql = " from ProvEval WHERE deleteFlag='N' AND year='" + year + "' AND seedCode='" + seedCode + "'";
		List<ProvEval> list = (List<ProvEval>)this.provEvalDao.queryByHQL(hql, null);
		if( list != null && list.size() > 0 ){
			return list.get(0);
		}else{
			return null;
		}
	}
}
