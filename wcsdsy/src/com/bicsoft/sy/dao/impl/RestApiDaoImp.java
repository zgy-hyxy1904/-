package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.RestApiRegDao;
import com.bicsoft.sy.entity.GeneLandRegD;
import com.bicsoft.sy.entity.GraiEval;
import com.bicsoft.sy.model.GeneLandRegModel;
import com.bicsoft.sy.util.DictUtil;
import com.bicsoft.sy.util.StringUtil;

@Repository
public class RestApiDaoImp extends BaseDaoImpl<Object, Serializable> implements RestApiRegDao {
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	//企业信息同步
	public List<?> getCompanyListForRest(Date lastSyncDate){
		StringBuffer SQL = new StringBuffer("select a.CompanyCode, a.CompanyName, a.LegalPerson as Corporation, IF(a.DeleteFlag='N','0','1') as DeleteFlag, ");
		SQL.append(" a.LegalPersonID, a.ConnectName as Contacts,a.ConnectPhone as Phone, DATE_FORMAT(a.RegisterDate,'%Y-%m-%d') as RegisterDate, ");
		SQL.append(" a.CompanyType as Type,IF(b.BlackListCount>0,'1','0') as Blacklist,IF(b.BlackListEndDate>now(),'1','0') as BlacklistStatus ");
		SQL.append(" from M_COMPANY as a left join b_BlackListManageH as b ");
		SQL.append(" on a.CompanyCode = b.CompanyCode ");
		if(lastSyncDate != null) SQL.append(" where a.UpdateDate > ? ");
		Query query = currentSession().createSQLQuery(SQL.toString());
		if(lastSyncDate != null) {
			query.setDate(0, lastSyncDate);
		}	
		List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	
	//土地备案同步
	public List<?> getCompanyLandStats(){
		List<Map> resultList = new ArrayList();
		StringBuffer companySQL = new StringBuffer("select b_GeneralLandRegH.Year,b_GeneralLandRegH.CompanyCode,CityCode,TownCode from b_GeneralLandRegD ")
				.append(" left join b_GeneralLandRegH on b_GeneralLandRegD.hid = b_GeneralLandRegH.id")
				.append(" group by b_GeneralLandRegH.Year,b_GeneralLandRegH.CompanyCode,CityCode,TownCode")
				.append(" union select Year,CompanyCode,CityCode, TownCode from b_SpecialLandReg group by  Year,CompanyCode,CityCode, TownCode");
		Query query = currentSession().createSQLQuery(companySQL.toString());
		List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		for(Map map : list){
			String year = (String)map.get("Year");
			String companyCode = (String)map.get("CompanyCode");
			String cityCode = (String)map.get("CityCode");
			String townCode = (String)map.get("TownCode");
			
			//普通土地备案面积统计
			String detailSQL = "select sum(ArchiveAcreage) from b_GeneralLandRegD where CityCode=? and TownCode=? and hid in (select id from b_GeneralLandRegH where CompanyCode=? and Year=? and Status=?) ";
			Query query2 = currentSession().createSQLQuery(detailSQL);
			query2.setString(0, cityCode);
			query2.setString(1, townCode);
			query2.setString(2, companyCode);
			query2.setString(3, year);
			query2.setString(4, "02");
			Object result = query2.uniqueResult();
			double totalArea = 0;
			if(result != null) totalArea = (double)result;
			
			//特殊土地备案面积统计
			detailSQL = "select sum(ArchiveAcreage) from b_SpecialLandReg where Year=? and CompanyCode=? and  CityCode=? and TownCode=? and Status=?";
			query2 = currentSession().createSQLQuery(detailSQL);
			query2.setString(0, year);
			query2.setString(1, companyCode);
			query2.setString(2, cityCode);
			query2.setString(3, townCode);
			query2.setString(4, "02");
			result = query2.uniqueResult();
			if(result != null) totalArea = totalArea + (double)result;
			
			
			//取乡镇图片
			String landPicSQL = "select PicInfo as ImageDesc, PicUrl as ImageUrl from b_land_picture where Year=? and CompanyCode=? and CityCode=? and TownCode=? ";
			query2 = currentSession().createSQLQuery(landPicSQL);
			query2.setString(0, year);
			query2.setString(1, companyCode);
			query2.setString(2, cityCode);
			query2.setString(3, townCode);
			List<Map> landPicList = query2.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			
			Map<String, Object> dataMap = new HashMap();
			dataMap.put("Year", year);
			dataMap.put("CompanyCode", companyCode);
			dataMap.put("RecordArea", totalArea);
			dataMap.put("CityCode", cityCode);
			dataMap.put("TownCode", townCode);
			dataMap.put("Img", landPicList);
			resultList.add(dataMap);
		}
		return resultList;
	}
	
	//投入品数据同步
	public List<?> getInputRegListForRest(){
		String hql = "select Year, CompanyCode,InputGoodsName as InputName,InputGoodsUnit as Unit,sum(PurchaseQuantity) as InputNumber "
			+" from b_InputReg group by Year, CompanyCode, InputGoodsName, InputGoodsUnit";
		
		Query query = currentSession().createSQLQuery(hql);
		List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	
	//过程监控
	public List<?> getProcMoniListForRest(Date lastSyncDate){
		StringBuffer sql = new StringBuffer("select Year,CompanyCode,BizType from b_Process_Monitoring ");
		if(lastSyncDate != null) sql.append(" where UpdateDate>?");
		sql.append(" group by Year,CompanyCode,BizType");
		
		Query query = currentSession().createSQLQuery(sql.toString());
		if(lastSyncDate != null) query.setDate(0, lastSyncDate);
		List<Map> procList = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		for(Map map : procList){
			String year = (String)map.get("Year");
			String companyCode = (String)map.get("CompanyCode");
			String bizType = (String)map.get("BizType");
			String imgBizType = DictUtil.getImgBizType(bizType);
			sql = new StringBuffer("select IF(FileType='01','0','1') as Type,FilePath as Url,FileInfo as `Describe` from tb_file where BizType=? and BizCode in(select BizCode from b_Process_Monitoring where Year=? and CompanyCode=? and BizType=?)");
			if(lastSyncDate != null) sql.append(" and UpdateDate>?");
			Query query2 = currentSession().createSQLQuery(sql.toString());
			query2.setString(0, imgBizType);
			query2.setString(1, year);
			query2.setString(2, companyCode);
			query2.setString(3, bizType);
			if(lastSyncDate != null) query2.setDate(4, lastSyncDate);
			
			List<Map> imgList = query2.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			if(imgList != null && imgList.size()>0) map.put("data", imgList);
			else map.put("data", new ArrayList<Map>());
		}
		return procList;
	}
	
	//预报产量
	public List<Map> getYieldPredictStatsForRest(){
		String hql = "select Year, CompanyCode, ROUND(sum(YieldPredictionValue),2) as Yield, '1' as YieldType, '斤' as Unit from b_YieldPredictionH group by Year, CompanyCode";
			Query query = currentSession().createSQLQuery(hql);
			List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
			return list;
	}
	
	//评估产量
	public List<?> getYieldEvalForRest(){		
		List<Map> resultList = new ArrayList();
		StringBuffer companySQL = new StringBuffer("select b_GeneralLandRegH.Year,b_GeneralLandRegH.CompanyCode from b_GeneralLandRegD ")
				.append(" left join b_GeneralLandRegH on b_GeneralLandRegD.hid = b_GeneralLandRegH.id")
				.append(" group by b_GeneralLandRegH.Year,b_GeneralLandRegH.CompanyCode")
				.append(" union select Year,CompanyCode from b_SpecialLandReg group by  Year,CompanyCode");
		Query query = currentSession().createSQLQuery(companySQL.toString());
		List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		
		for(Map map : list){
			String year = (String)map.get("Year");
			String companyCode = (String)map.get("CompanyCode");
			
			//当年的产量评估参数
			String evalSQL = "from GraiEval where year = ?";
			Query query2 = currentSession().createQuery(evalSQL);
			query2.setString(0, year);
			List<?> evalList = query2.list();
			GraiEval graiEval = null;
			float maxYield = 0;
			float milledriceRate = 0;
			if(evalList != null && evalList.size() >0 ) {
				graiEval = (GraiEval)evalList.get(0);
				maxYield = graiEval.getMaxYield();
				milledriceRate = graiEval.getMilledriceRate();
			}
			
			//普通土地备案面积统计
			String detailSQL = "select sum(ArchiveAcreage) from b_GeneralLandRegD where hid in (select id from b_GeneralLandRegH where CompanyCode=? and Year=? and Status=?) ";
			query2 = currentSession().createSQLQuery(detailSQL);
			query2.setString(0, companyCode);
			query2.setString(1, year);
			query2.setString(2, "01");
			Object result = query2.uniqueResult();
			double totalArea = 0;
			if(result != null) totalArea = (double)result;
			
			//特殊土地备案面积统计
			detailSQL = "select sum(ArchiveAcreage) from b_SpecialLandReg where CompanyCode=? and Year=? and Status=?";
			query2 = currentSession().createSQLQuery(detailSQL);
			query2.setString(0, companyCode);
			query2.setString(1, year);
			query2.setString(2, "01");
			result = query2.uniqueResult();
			if(result != null) totalArea = totalArea + (double)result;
			
			Map<String, Object> dataMap = new HashMap();
			dataMap.put("Year", year);
			dataMap.put("CompanyCode", companyCode);
			dataMap.put("Yield", StringUtil.formatYieldToDouble(totalArea * maxYield * milledriceRate));
			dataMap.put("YieldType", "0");
			dataMap.put("Unit", "斤");
			resultList.add(dataMap);
		}
		return resultList;
	}
	
	//收粮产量
	public List<?> getYieldGrainRegForRest(){
		String hql = "select Year, CompanyCode, ROUND(sum(ThisWeight),2) as Yield, '2' as YieldType, '斤' as Unit from b_GrainReg group by Year, CompanyCode";
		Query query = currentSession().createSQLQuery(hql);
		List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
	
	public int checkGeneReg(Integer id){
		int result = 0;
		//统计每个承包人的本次备案面积
		StringBuffer sql = new StringBuffer("select IDType, ContractorID,sum(ArchiveAcreage) as ArchiveAcreage from b_GeneralLandRegD where HID = ? group by IDType, ContractorID");
		Query query = currentSession().createSQLQuery(sql.toString());
		query.setInteger(0, id);
		List<Map> regList = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		if(regList != null && regList.size() > 0){
			for(Map map : regList){
				String IDType = (String)map.get("IDType");
				String ContractorID = (String)map.get("ContractorID");
				Float thisArea = (Float)map.get("ArchiveAcreage");
				
				//查当前承包人已备案面积
				StringBuffer regedSql = new StringBuffer("select sum(ArchiveAcreage) from b_GeneralLandRegD where HID in(select id from b_GeneralLandRegH where IDType=? and ContractorID=? and Status=? and DeleteFlag=? )");
				Query query2 = currentSession().createSQLQuery(regedSql.toString());
				query2.setString(0, IDType);
				query2.setString(1, ContractorID);
				query2.setString(2, "02");
				query2.setString(3, "N");
				Object sumResult = query2.uniqueResult();
				double regedArea = 0;
				if(sumResult != null) regedArea = (double)sumResult;
				
				//取当前承包人所有承包面积
				StringBuffer totalSql = new  StringBuffer("select sum(MeasurementMu) from M_Contract where ContractorCode in(select ContractorCode from M_Peasant where ContractorIDType=? and ContractorID=?) and LandType=?");
				Query query3 = currentSession().createSQLQuery(totalSql.toString());
				query3.setString(0, IDType);
				query3.setString(1, ContractorID);
				query3.setString(2, "01");//水田
				sumResult = query3.uniqueResult();
				double totalArea = 0;
				if(sumResult != null) totalArea = (double)sumResult;
				
				//本次面积大于剩余备案面各
				if(thisArea > (totalArea - regedArea)) return 1;
			}
		}else{
			return 2;
		}
		return result;
	}
	
	public int checkGeneRegExt(GeneLandRegModel geneLandRegModel, String year){
		int result = 0;
		
		List<GeneLandRegD> list = geneLandRegModel.getList();
		if(list == null || list.size() == 0) return 2;

		//统计每个承包商本次备案面积
		HashMap<String, GeneLandRegD> areaMap = new HashMap<String, GeneLandRegD>();
		for( GeneLandRegD geneLandRegD : list  ){
			String key = geneLandRegD.getIdType() + geneLandRegD.getContractorID();
			if(areaMap.get(key) == null){
				areaMap.put(key, geneLandRegD);
			}else{
				GeneLandRegD geneLandRegTmp = areaMap.get(key);
				geneLandRegTmp.setArchiveAcreage(geneLandRegTmp.getArchiveAcreage() + geneLandRegD.getArchiveAcreage());
			}
		}
		
		for (Entry<String, GeneLandRegD> entry : areaMap.entrySet()) {
			GeneLandRegD geneLandRegD = entry.getValue();
			String idType = geneLandRegD.getIdType();
			String contractorID = geneLandRegD.getContractorID();
			Float thisArea =  geneLandRegD.getArchiveAcreage();
			
			//查当前承包人已备案面积
			StringBuffer regedSql = new StringBuffer("select sum(ArchiveAcreage) from b_GeneralLandRegD where HID in(select id from b_GeneralLandRegH where year=? and IDType=? and ContractorID=? and Status=? and DeleteFlag=? )");
			Query query2 = currentSession().createSQLQuery(regedSql.toString());
			query2.setString(0, year);
			query2.setString(1, idType);
			query2.setString(2, contractorID);
			query2.setString(3, "02"); //审核状态
			query2.setString(4, "N"); //删除标识
			Object sumResult = query2.uniqueResult();
			float regedArea = 0;
			if(sumResult != null) regedArea = Float.valueOf(sumResult.toString());
			
			//取当前承包人所有承包面积
			StringBuffer totalSql = new  StringBuffer("select sum(MeasurementMu) from M_Contract where ContractorCode in(select ContractorCode from M_Peasant where ContractorIDType=? and ContractorID=?) and LandType=? ");
			Query query3 = currentSession().createSQLQuery(totalSql.toString());
			query3.setString(0, idType);
			query3.setString(1, contractorID);
			query3.setString(2, "01"); //水田
			sumResult = query3.uniqueResult();
			float totalArea = 0;
			if(sumResult != null) totalArea = Float.valueOf(sumResult.toString());
			
			//本次面积大于剩余备案面各
			if(thisArea > (totalArea - regedArea)) return 1;
		}
		return result;
	}
	
	//预报流水记录
	public List<?> getYieldPredict(Date lastSyncDate){
		StringBuffer SQL = new StringBuffer("select b.Year as Year,b.CompanyCode as CompanyCode,b.CompanyName as CompanyName,a.ProductCode as ProductCode,");
		SQL.append(" a.qty as Number,a.weight as TotalWeight,DATE_FORMAT(b.PredictionDate,'%Y-%m-%d') as ApplicationTime from b_YieldPredictionD as a");
		SQL.append(" left join b_YieldPredictionH as b on a.hid = b.id where b.DeleteFlag=? ");
		if(lastSyncDate != null) SQL.append(" and b.UpdateDate > ? ");
		Query query = currentSession().createSQLQuery(SQL.toString());
		query.setString(0, "N");
		if(lastSyncDate != null) {
			query.setDate(1, lastSyncDate);
		}	
		List<Map> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return list;
	}
}
