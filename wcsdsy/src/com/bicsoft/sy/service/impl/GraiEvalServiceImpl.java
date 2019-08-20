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

import com.bicsoft.sy.dao.GraiEvalDao;
import com.bicsoft.sy.entity.GraiEval;
import com.bicsoft.sy.entity.SpecLandReg;
import com.bicsoft.sy.model.GraiEvalModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.GraiEvalService;
import com.bicsoft.sy.util.POVOConvertUtil;
import com.bicsoft.sy.util.StringUtil;

@Service
@Transactional
public class GraiEvalServiceImpl implements GraiEvalService {
	private static final Logger log = LoggerFactory.getLogger(GraiEvalServiceImpl.class);
	
	@Autowired
	private GraiEvalDao graiEvalDao;
	
	@Override
	public GraiEval getGraiEval(int id) {
		return this.graiEvalDao.queryById(GraiEval.class, id);
	}

	@Override
	public void save(GraiEvalModel graiEvalModel) {
		try{
			GraiEval graiEval = null;
			if(graiEvalModel.getId() == null){
				graiEval = (GraiEval) POVOConvertUtil.convert(graiEvalModel, "com.bicsoft.sy.entity.GraiEval");
				this.graiEvalDao.save(graiEval);
			}else{
				graiEval = this.graiEvalDao.queryById(GraiEval.class, graiEvalModel.getId());
				graiEval.setUpdateDate(graiEvalModel.getUpdateDate());
				graiEval.setUpdateUserId(graiEvalModel.getUpdateUserId());
				
				graiEval.setSeedCode( graiEvalModel.getSeedCode() );
				graiEval.setMaxYield( graiEvalModel.getMaxYield() );
				graiEval.setMinYield(graiEvalModel.getMinYield());
				graiEval.setMilledriceRate(graiEvalModel.getMilledriceRate() );
			}
			this.graiEvalDao.save(graiEval);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:",e);
		}
	}

	@Override
	public void delete(int id) {
		this.graiEvalDao.delete(GraiEval.class, id);
	}


	@Override
	public void logicDelete(Class<GraiEval> clazz, int id) {
		this.graiEvalDao.logicDelete(clazz, id);
		
	}

	@Override
	public PageModel queryForPageModel(String entityName,
			Map<String, Object> params, PageModel pageModel) {
		return this.graiEvalDao.queryForPageModel(entityName, params, pageModel);
	}

	@Override
	public List<GraiEval> getGraiEvalList() {
		return null;
	}
	
	public GraiEval getGraiEvalByYear(String yearCode){
		GraiEval graiEval = null;
		String hql = "from GraiEval where year=:yearCode AND deleteFlag='N'";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("yearCode", yearCode);
		List<GraiEval> results = graiEvalDao.queryByHQL(hql, params);
		if(results==null || results.size()==0){
			graiEval = new GraiEval();
		}else{
			graiEval = results.get(0);
		}
		return graiEval;
	}
	
	@Override
	public Float getLandsArea(String companyCode, String yearCode) {
//		String sql = "SELECT SUM(IFNULL(A.AREAS,0)) AS AREASUM,CAST(SUM(IFNULL(A.AREAS,0)) AS CHAR) AS CAREASUMFROM FROM("+
//			" SELECT SUM(GD.ArchiveAcreage) AS AREAS FROM B_GeneralLandRegD GD WHERE GD.deleteFlag='N' AND GD.HID IN (SELECT ID FROM b_GeneralLandRegH GH WHERE GH.deleteFlag='N' AND GH.Status='02' AND GH.CompanyCode='"+companyCode+"' AND GH.Year='"+yearCode+"')"+
//			" UNION ALL"+
//			" SELECT SUM(S.ArchiveAcreage) AS AREAS FROM B_SpecialLandReg S WHERE S.deleteFlag='N' AND S.Status='03' AND S.CompanyCode='"+companyCode+"' AND S.Year='"+yearCode+"') AS A";
//		
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
		
		List<Object[]> results = graiEvalDao.queryBySQL(sql, null);
		
		if(results == null || results.size()==0 || results.get(0)[0]==null){
			return Float.parseFloat("0");
		}
		return Float.parseFloat(results.get(0)[0] + "");
	}
	
	@Override
	//普通土地备案面积
	public Float getGeneLandsArea(String companyCode, String year) {
		String sql = "SELECT SUM(ArchiveAcreage) FROM B_GeneralLandRegD GD WHERE GD.deleteFlag='N' AND GD.HID IN (SELECT ID FROM b_GeneralLandRegH GH WHERE GH.deleteFlag='N' AND GH.Status='02' AND GH.CompanyCode='"+companyCode+"' AND GH.Year='"+year+"')";
		List<Object[]> results = graiEvalDao.queryBySQL(sql, null);
		if(results == null || results.size()==0 || results.get(0) == null){
			return Float.parseFloat("0");
		}
		return Float.valueOf(results.get(0)+"");
	}
	
	@Override
	//特殊土地备案面积
	public Float getSpecLandsArea(String companyCode, String year) {
		String sql = "SELECT SUM(ArchiveAcreage) FROM b_SpecialLandReg WHERE CompanyCode='"+companyCode+"' AND Year='"+year+"'" + " and DeleteFlag='N' and Status='03'" ;
		List<Object[]> results = graiEvalDao.queryBySQL(sql, null);
		if(results == null || results.size()==0 || results.get(0) == null){
			return Float.parseFloat("0");
		}
		return Float.valueOf(results.get(0)+"");
	}

	@Override
	public List<Map<String,Object>> queryLandInfos(String companyCode, String yearCode, int page , int pageSize){
		StringBuffer buffer = new StringBuffer();
		//没有地块单独的面积,这个SQL不适用,换成下面那个简单的
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
//		buffer.append("WHERE SR.deleteFlag='N' AND SRD.deleteFlag='N' AND SR.companyCode='"+companyCode+"' AND SR.Year='"+yearCode+"' ");
		
		buffer.append("SELECT GRD.ContractorName, CONCAT(AREAGRD.townName,AREAGRD.countryName,GRD.GroupName) AS HomeAddress,DATE_FORMAT(GRD.CreateDate,'%Y-%m-%d') AS ArchiveDate,FORMAT(GRD.ArchiveAcreage,2),'普通备案' as landType ");
		buffer.append("FROM b_GeneralLandRegH GRH ");
		buffer.append("LEFT JOIN b_GeneralLandRegD GRD ");
		buffer.append("	ON GRH.ID = GRD.HID ");
		buffer.append("LEFT JOIN m_areadivision AREAGRD ");
		buffer.append("	ON GRD.cityCode=AREAGRD.cityCode AND GRD.townCode=AREAGRD.townCode AND GRD.countryCode=AREAGRD.countryCode ");
		buffer.append("WHERE GRH.status='02' AND GRH.deleteFlag='N' AND GRD.deleteFlag='N' AND GRH.companyCode='"+companyCode+"' AND GRH.Year='"+yearCode+"' AND GRH.status!='04'");
		buffer.append("UNION ALL ");
		buffer.append("SELECT SR.ContractorName, CONCAT(AREASR.townName,AREASR.countryName,SR.GroupName) as HomeAddress,DATE_FORMAT(SR.CreateDate,'%Y-%m-%d') AS ArchiveDate,FORMAT(SR.ArchiveAcreage,2),'特殊备案' as landType  ");
		buffer.append("FROM b_SpecialLandReg SR ");
		buffer.append("LEFT JOIN m_areadivision AREASR ");
		buffer.append("	ON SR.cityCode=AREASR.cityCode AND SR.townCode=AREASR.townCode AND SR.countryCode=AREASR.countryCode ");
		buffer.append("WHERE SR.status='03' AND SR.deleteFlag='N' AND SR.companyCode='"+companyCode+"' AND SR.Year='"+yearCode+"' AND SR.status!='04'" );
		buffer.append("LIMIT "+page+" , "+pageSize);
		List<Object[]> results = graiEvalDao.queryBySQL(buffer.toString(), null);
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
		buffer.append("WHERE GRH.status='02' AND GRH.deleteFlag='N' AND GRD.deleteFlag='N' AND GRH.companyCode='"+companyCode+"' AND GRH.Year='"+yearCode+"' AND GRH.status!='04'");
		buffer.append("UNION ALL ");
		buffer.append("SELECT SR.ContractorName, CONCAT(AREASR.townName,AREASR.countryName,SR.GroupName) as HomeAddress,DATE_FORMAT(SR.CreateDate,'%Y-%m-%d') AS ArchiveDate,SR.ArchiveAcreage  ");
		buffer.append("FROM b_SpecialLandReg SR ");
		buffer.append("LEFT JOIN m_areadivision AREASR ");
		buffer.append("	ON SR.cityCode=AREASR.cityCode AND SR.townCode=AREASR.townCode AND SR.countryCode=AREASR.countryCode ");
		buffer.append("WHERE SR.status='03' AND SR.deleteFlag='N' AND SR.companyCode='"+companyCode+"' AND SR.Year='"+yearCode+"' AND SR.status!='04'" );
		
		List<Object[]> results = graiEvalDao.queryBySQL(buffer.toString(), null);
        if(results!=null){
        	return results.size();
        }else{
        	return 0;
        }
	}

	@Override
	public GraiEval getGraiEval(String year, String seedCode) {
		String hql = " from GraiEval WHERE deleteFlag='N' AND year='" + year + "' AND seedCode='" + seedCode + "'";
		List<GraiEval> list = (List<GraiEval>)this.graiEvalDao.queryByHQL(hql, null);
		if( list != null && list.size() > 0 ){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public GraiEval getGraiEval(String year) {
		String hql = " from GraiEval WHERE deleteFlag='N' AND year='" + year + "'";
		List<GraiEval> list = (List<GraiEval>)this.graiEvalDao.queryByHQL(hql, null);
		if( list != null && list.size() > 0 ){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public double getTotalWeight(String year, String companyCode){
		return this.graiEvalDao.getTotalWeight(year, companyCode);
	}
	
	@Override
	public int queryGrainRegInfoListCount(String yearCode,String contractorID){
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT ID ");
		buffer.append("FROM b_grainreg ");
		buffer.append("WHERE deleteFlag='N' AND year='"+yearCode+"' AND IDNumber='"+contractorID+ "'");
		List<Object[]> results = graiEvalDao.queryBySQL(buffer.toString(), null);
        if(results!=null){
        	return results.size();
        }else{
        	return 0;
        }
	}
}
