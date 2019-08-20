package com.bicsoft.sy.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bicsoft.sy.entity.AreaDevision;
import com.bicsoft.sy.entity.CommonData;
import com.bicsoft.sy.entity.Company;
import com.bicsoft.sy.entity.Contract;
import com.bicsoft.sy.entity.GraiEval;
import com.bicsoft.sy.entity.Peasant;
import com.bicsoft.sy.entity.Product;
import com.bicsoft.sy.entity.Quality;
import com.bicsoft.sy.entity.Sample;
import com.bicsoft.sy.entity.SecurityCode;
import com.bicsoft.sy.entity.SecurityCodeDetail;
import com.bicsoft.sy.entity.YYSyncLog;
import com.bicsoft.sy.model.GeneLandRegModel;
import com.bicsoft.sy.service.AreaDevisionService;
import com.bicsoft.sy.service.CommonDataService;
import com.bicsoft.sy.service.CompanyService;
import com.bicsoft.sy.service.ContractService;
import com.bicsoft.sy.service.GeneLandRegDService;
import com.bicsoft.sy.service.GraiEvalService;
import com.bicsoft.sy.service.GraiRegService;
import com.bicsoft.sy.service.InputRegService;
import com.bicsoft.sy.service.PeasantService;
import com.bicsoft.sy.service.ProcMoniService;
import com.bicsoft.sy.service.ProductService;
import com.bicsoft.sy.service.QualityService;
import com.bicsoft.sy.service.RestApiService;
import com.bicsoft.sy.service.SampleService;
import com.bicsoft.sy.service.SecurityCodeDetailService;
import com.bicsoft.sy.service.SecurityCodeService;
import com.bicsoft.sy.service.YYSyncLogService;
import com.bicsoft.sy.util.ApiResult;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.FTPUtil;
import com.bicsoft.sy.util.HttpUtil;
import com.bicsoft.sy.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 接口管理
 * @author 高华
 * @date 2015-08-18
 */

@Controller
@RequestMapping("/api/")
public class RestApiController {
	private static final Logger log = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	private CompanyService companyService;
	@Autowired
	private YYSyncLogService yySyncLogService;
	@Autowired
	private PeasantService peasantService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private SampleService sampleService;
	@Autowired
	private QualityService qualityService;
	@Autowired
	private AreaDevisionService areaDevisionService;
	@Autowired
	private RestApiService restApiService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SecurityCodeService securityCodeService;
	@Autowired
	private SecurityCodeDetailService securityCodeDetailService;
	@Autowired
	private CommonDataService commonDataService;
	@Autowired
	private GeneLandRegDService geneLandRegDService;
	@Autowired
	private GraiEvalService graiEvalService;
	@Autowired
	private GraiRegService graiRegService;
	@Autowired
	private InputRegService inputRegService;
	@Autowired
	ProcMoniService procMoniService;
	
	//private final String yyRestApi = "http://bocode.com.cn/httpjson/api/";
	
	/**
	 * 01    企业档案同步
	 * 02　土地信息同步
	 * 03　投放物数据接口
	 * 04　过程监 控
	 * 05   公司年产量
	 * 06 博码备案申请
	 */
	
	@RequestMapping("/companyList")
	@ResponseBody
	//企业信息同步
	public ApiResult compalyList(HttpServletRequest request) throws Exception{
		//签名较验　
		String bizType = "01";
		
		Date lastSyncDate = null;
		List<?> complayList = restApiService.getCompanyListForRest(lastSyncDate);
		
		int total = (complayList != null) ? complayList.size() : 0;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("data", complayList);
		paramMap.put("total", total);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("status", "0");
		resultJson.put("msg", "操作成功!");
        
        boolean status = false;
        String msg = "调用失败";
        if(resultJson != null){
        	if(resultJson.getString("status").equals("0")){
        		status = true;
        		msg = resultJson.getString("msg");
        	}
        }
        //保存同步日志
        YYSyncLog syncLog = new YYSyncLog();
        syncLog.setBizType(bizType);
        syncLog.setCreateDate(new Date());
        syncLog.setSyncCount(total);
        syncLog.setDataPath(0);
        syncLog.setSyncDate(new Date());
        syncLog.setSyncStatus(status?0:1);
        yySyncLogService.save(syncLog);
        ApiResult ar = new ApiResult(status,msg);
		return ar;
	}

	@RequestMapping("/landList")
	@ResponseBody
	//土地信息数据同步
	public ApiResult landList(HttpServletRequest request) throws Exception{
		String bizType = "02";
		
		String rootPath = request.getSession().getServletContext().getRealPath("/upload/") + "\\";
		List<String> fileList = new ArrayList<String>();
		String zipFileName = DateTimeUtil.getStringFromDate("yyyyMMddHHmmss", new Date());
		
		List<Map> langList = (List<Map>)restApiService.getCompanyLandStats();
		int total = (langList != null) ? langList.size() : 0;
		for(Map map :langList){
			String cityCode = (String)map.get("CityCode");
			String townCode = (String)map.get("TownCode");
			String companyCode = (String)map.get("CompanyCode");
			Company company = companyService.getCompany(companyCode);
			if(company != null){
				map.put("CompanyName", company.getCompanyName());
			}
			AreaDevision areaDevision = areaDevisionService.getAreaDevision(cityCode, townCode);
			if(areaDevision != null){
				map.put("CityName", areaDevision.getCityName());
				map.put("TownName", areaDevision.getTownName());
			}
			//取图片
			List<Map> imgList = (List<Map>)map.get("Img");
			for(Map imgMap : imgList){
				String imageUrl = (String)imgMap.get("ImageUrl");
				StringBuffer path = new StringBuffer(rootPath);
				path.append(imageUrl);
				fileList.add(path.toString());
			}
		}
		
		if(fileList.size() > 0){
			//压缩图片文件
			FTPUtil ftpClient = new FTPUtil();
			ftpClient.createZipFile(fileList, rootPath+zipFileName);
			//上传图片文件
			ftpClient.upload(rootPath+zipFileName+".zip");
			ftpClient.disconnect();
			//删除临时文件
			File zipFile = new File(rootPath+zipFileName+".zip");
			if(zipFile.isFile() && zipFile.exists()){
				zipFile.delete();  
			}
		}
		
		zipFileName = (fileList.size() == 0) ? "" : zipFileName; 
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("data", langList);
		paramMap.put("ZipFileName", zipFileName);
		paramMap.put("total", total);
		
		
		JSONObject resultJson = new JSONObject();
		
		JSONObject[] contracts = new JSONObject[6];
		JSONObject contract1 = new JSONObject();
        contract1.put("id", 2713);
        contract1.put("contractorCode", "230184206200000001");
        contract1.put("graphCode", "4974.00-42599.50");
        contract1.put("landCode", "02887");
        contract1.put("landName", "老于坟地南北垄");
        contract1.put("contractArea", 1.44);
        contract1.put("measurementMu", 1.44);
        contract1.put("eastTo", "刘有");
        contract1.put("westTo", "刘长清");
        contract1.put("southTo", "刘长江");
        contract1.put("northTo", "沟渠");
        contract1.put("landLevel", "二等地");
        contract1.put("landType", "02");
        contract1.put("isBaseLand", "是");
        contract1.put("ownership", "");
        contract1.put("landClass", null);
        contract1.put("disputeReason", null);
        contract1.put("landPurpose", "种植业");
        contract1.put("contractStartDate", null);
        contract1.put("contractYear", null);
        contract1.put("contractEndDate", null);

        JSONObject contract2 = new JSONObject();
        contract2.put("id", 2714);
        contract2.put("contractorCode", "230184206200000001");
        contract2.put("graphCode", "4973.50-42599.00");
        contract2.put("landCode", "03616");
        contract2.put("landName", "四河门南北垄");
        contract2.put("contractArea", 2.14);
        contract2.put("measurementMu", 2.14);
        contract2.put("eastTo", "于忠孝");
        contract2.put("westTo", "关玉忠");
        contract2.put("southTo", "道路");
        contract2.put("northTo", "道路");
        contract2.put("landLevel", "三等地");
        contract2.put("landType", "02");
        contract2.put("isBaseLand", "是");
        contract2.put("ownership", "");
        contract2.put("landClass", null);
        contract2.put("disputeReason", null);
        contract2.put("landPurpose", "种植业");
        contract2.put("contractStartDate", null);
        contract2.put("contractYear", null);
        contract2.put("contractEndDate", null);

		JSONObject contract3 = new JSONObject();
		contract3.put("id", 2715);
		contract3.put("contractorCode", "230184206200000001");
		contract3.put("graphCode", "4974.00-42600.50");
		contract3.put("landCode", "02974");
		contract3.put("landName", "房东南北垄");
		contract3.put("contractArea", 3.97);
		contract3.put("measurementMu", 3.97);
		contract3.put("eastTo", "宁学彬");
		contract3.put("westTo", "王占江2");
		contract3.put("southTo", "道路");
		contract3.put("northTo", "道路");
		contract3.put("landLevel", "一等地");
		contract3.put("landType", "02");
		contract3.put("isBaseLand", "是");
		contract3.put("ownership", "");
		contract3.put("landClass", null);
		contract3.put("disputeReason", null);
		contract3.put("landPurpose", "种植业");
		contract3.put("contractStartDate", null);
		contract3.put("contractYear", null);
		contract3.put("contractEndDate", null);

		JSONObject contract4 = new JSONObject();
		contract4.put("id", 2716);
		contract4.put("contractorCode", "230184206200000001");
		contract4.put("graphCode", "4974.00-42599.50");
		contract4.put("landCode", "03046");
		contract4.put("landName", "坝下稻地");
		contract4.put("contractArea", 0.39);
		contract4.put("measurementMu", 0.39);
		contract4.put("eastTo", "沟渠");
		contract4.put("westTo", "沟渠");
		contract4.put("southTo", "道路");
		contract4.put("northTo", "沟渠");
		contract4.put("landLevel", "一等地");
		contract4.put("landType", "01");
		contract4.put("isBaseLand", "否");
		contract4.put("ownership", "");
		contract4.put("landClass", null);
		contract4.put("disputeReason", null);
		contract4.put("landPurpose", "种植业");
		contract4.put("contractStartDate", null);
		contract4.put("contractYear", null);
		contract4.put("contractEndDate", null);

		JSONObject contract5 = new JSONObject();
		contract5.put("id", 2717);
		contract5.put("contractorCode", "230184206200000001");
		contract5.put("graphCode", "4974.50-42600.00");
		contract5.put("landCode", "02373");
		contract5.put("landName", "豪北大地");
		contract5.put("contractArea", 1.64);
		contract5.put("measurementMu", 1.64);
		contract5.put("eastTo", "道路");
		contract5.put("westTo", "道路");
		contract5.put("southTo", "杜才");
		contract5.put("northTo", "刘长清");
		contract5.put("landLevel", "二等地");
		contract5.put("landType", "02");
		contract5.put("isBaseLand", "是");
		contract5.put("ownership", "");
		contract5.put("landClass", null);
		contract5.put("disputeReason", null);
		contract5.put("landPurpose", "种植业");
		contract5.put("contractStartDate", null);
		contract5.put("contractYear", null);
		contract5.put("contractEndDate", null);

		JSONObject contract6 = new JSONObject();
		contract6.put("id", 2718);
		contract6.put("contractorCode", "230184206200000001");
		contract6.put("graphCode", "4975.00-42600.00");
		contract6.put("landCode", "02040");
		contract6.put("landName", "岗上");
		contract6.put("contractArea", 0.12);
		contract6.put("measurementMu", 0.97);
		contract6.put("eastTo", "王富");
		contract6.put("westTo", "沟渠");
		contract6.put("southTo", "刘长山");
		contract6.put("northTo", "朱和");
		contract6.put("landLevel", "一等地");
		contract6.put("landType", "02");
		contract6.put("isBaseLand", "是");
		contract6.put("ownership", "");
		contract6.put("landClass", null);
		contract6.put("disputeReason", null);
		contract6.put("landPurpose", "种植业");
		contract6.put("contractStartDate", null);
		contract6.put("contractYear", null);
		contract6.put("contractEndDate", null);

		contracts[0] = contract1;
		contracts[1] = contract2;
		contracts[2] = contract3;
		contracts[3] = contract4;
		contracts[4] = contract5;
		contracts[5] = contract6;
		
		
		JSONObject peasant = new JSONObject();
		peasant.put("id", 1);
		peasant.put("contractorCode", "230184206200000001");
		peasant.put("contractorName", "周玉忠");
		peasant.put("contractorID", "232103196211031736");
		peasant.put("contractorIDType", "01");
		peasant.put("contractorAge", 53);
		peasant.put("contractorSex", "男");
		peasant.put("contractorBirth", "1962-11-03");
		peasant.put("contractorTel", "13234990099");
		peasant.put("cityCode", "230184");
		peasant.put("townCode", "206");
		peasant.put("countryCode", "200");
		peasant.put("groupName", "民意乡九合村八里屯");
		peasant.put("contractorZipcode", "150207");
		peasant.put("contractorhouseholdType", "农业户口");
		peasant.put("contractorType", "01");
		peasant.put("contractId", null);
		peasant.put("contractorNation", "汉族");
		peasant.put("rightId", null);
		peasant.put("attestor", null);
		peasant.put("attestMechanism", null);
		peasant.put("attestDate", null);
		peasant.put("attestNo", null);
		peasant.put("landPurpose", "种植业");
		peasant.put("rightGetWay", "家庭承包");
		peasant.put("contractStartDate", 883584000000L);
		peasant.put("contractYear", 30);
		peasant.put("contractEndDate", 1830182400000L);
		peasant.put("getLandPersonCount", null);
		peasant.put("familyPersonCount", null);
		peasant.put("surveyDate", null);
		peasant.put("surveyMemo", null);
		
		JSONObject datas = new JSONObject();
		datas.put("contract", contracts);
		datas.put("peasant", peasant);
		
		resultJson.put("status", "0");
		resultJson.put("msg", "success");
		resultJson.put("data", datas);
		
        
        boolean status = false;
        String msg = "调用失败";
        if(resultJson != null){
        	if(resultJson.getString("status").equals("0")){
        		status = true;
        		msg = resultJson.getString("msg");
        	}
        }
        //保存同步日志
        YYSyncLog syncLog = new YYSyncLog();
        syncLog.setBizType(bizType);
        syncLog.setCreateDate(new Date());
        syncLog.setSyncCount(total);
        syncLog.setDataPath(0);
        syncLog.setSyncDate(new Date());
        syncLog.setSyncStatus(status?0:1);
        yySyncLogService.save(syncLog);
        ApiResult ar = new ApiResult(status,msg);
		return ar;
	}
	
	//投入品数据接口  溯源->亿阳
	@RequestMapping("/inputList")
	@ResponseBody
	public ApiResult inputList(HttpServletRequest request) throws Exception{
		String bizType = "03";
		
		List<Map> inputList = (List<Map>)restApiService.getInputRegListForRest();
		int total = (inputList != null) ? inputList.size() : 0;
		
		for(Map<String,String> map : inputList){
			String companyCode = (String)map.get("CompanyCode");
			Company company = companyService.getCompany(companyCode);
			if(company != null){
				map.put("CompanyName", company.getCompanyName());
			}
		}
		
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("data", inputList);
		paramMap.put("total", total);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("status", "0");
		resultJson.put("msg", "操作成功!");
	     
	    boolean status = false;
	    String msg = "调用失败";
	    if(resultJson != null){
	    	if(resultJson.getString("status").equals("0")){
	    		status = true;
	    		msg = resultJson.getString("msg");
	    	}
	    }
	    //保存同步日志
	    YYSyncLog syncLog = new YYSyncLog();
	    syncLog.setBizType(bizType);
	    syncLog.setCreateDate(new Date());
	    syncLog.setSyncCount(total);
	    syncLog.setDataPath(0);
	    syncLog.setSyncDate(new Date());
	    syncLog.setSyncStatus(status?0:1);
	    yySyncLogService.save(syncLog);
	  		
	    ApiResult ar = new ApiResult(status, msg);
		return ar;
	}

	//过程监控数据接口  溯源->亿阳
	@RequestMapping("/sourceList")
	@ResponseBody
	public ApiResult sourceList(HttpServletRequest request) throws Exception{
		String bizType = "04";
		
		Date lastSyncDate = null;
		String rootPath = request.getSession().getServletContext().getRealPath("/upload/") + "\\";
		List<String> fileList = new ArrayList<String>();
		String zipFileName = DateTimeUtil.getStringFromDate("yyyyMMddHHmmss", new Date());
		
		YYSyncLog yySyncLog = yySyncLogService.getLastSyncLog(bizType);
		//if(yySyncLog != null) lastSyncDate = yySyncLog.getSyncDate();
		//格式化数据
		List<Map> moniList = (List<Map>)restApiService.getProcMoniListForRest(lastSyncDate);
		for(Map map : moniList){
			String companyCode = (String )map.get("CompanyCode");
			Company company = companyService.getCompany(companyCode);
			if(company != null){
				map.put("CompanyName", (String)company.getCompanyName());
			}
			//取图片
			List<Map> imgList = (List<Map>)map.get("data");
			for(Map imgMap : imgList){
				String type = (String)imgMap.get("Type");
				String url = (String)imgMap.get("Url");
				if(type.equals("0")){//图片
					StringBuffer path = new StringBuffer(rootPath);
					path.append(url);
					fileList.add(path.toString());
				}
			}
		}
		int total = (moniList != null) ? moniList.size() : 0;
		 
		if(fileList.size() > 0){
			//压缩图片文件
			FTPUtil ftpClient = new FTPUtil();
			ftpClient.createZipFile(fileList, rootPath+zipFileName);
			//上传图片文件
			ftpClient.upload(rootPath+zipFileName+".zip");
			ftpClient.disconnect();
			//删除临时文件
			File zipFile = new File(rootPath+zipFileName+".zip");
			if(zipFile.isFile() && zipFile.exists()){
				zipFile.delete();  
			}
		}
		
		zipFileName = (fileList.size() == 0) ? "" : zipFileName;
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("data", moniList);
		paramMap.put("ZipFileName", zipFileName);
		paramMap.put("total", total);
		
		String data = JSONObject.fromObject(paramMap).toString();
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("status", "0");
		resultJson.put("msg", "操作成功!");
         
        boolean status = false;
        String msg = "调用失败";
        if(resultJson != null){
        	if(resultJson.getString("status").equals("0")){
        		status = true;
        		msg = resultJson.getString("msg");
        	}
        }
        //保存同步日志
  		YYSyncLog syncLog = new YYSyncLog();
  		syncLog.setBizType(bizType);
  		syncLog.setCreateDate(new Date());
  		syncLog.setSyncCount(total);
  		syncLog.setDataPath(0);
  		syncLog.setSyncDate(new Date());
  		syncLog.setSyncStatus(status?0:1);
  		yySyncLogService.save(syncLog);
  		ApiResult ar = new ApiResult(status,msg);
		return ar;
	}
	
	//公司年产量
	//投放物数据接口  溯源->亿阳
	@RequestMapping("/yieldList")
	@ResponseBody
	public ApiResult yieldList(HttpServletRequest request) throws Exception{
		String bizType = "05";
		//评估产量
		List<Map> yieldEvalList = (List<Map>)restApiService.getYieldEvalForRest();
		for(Map map :yieldEvalList){
			String companyCode = (String)map.get("CompanyCode");			
			Company company = companyService.getCompany(companyCode);
			map.put("CompanyName", company.getCompanyName());
		}
		
		//预测产量
		List<Map> yieldPredictList = (List<Map>)restApiService.getYieldPredictStatsForRest();
		for(Map map :yieldPredictList){
			String companyCode = (String)map.get("CompanyCode");			
			Company company = companyService.getCompany(companyCode);
			map.put("CompanyName", company.getCompanyName());
		}
		
		int total = (yieldPredictList != null) ? yieldPredictList.size() : 0;
		
		//收粮产量
		List<Map> yieldGrainRegList =  (List<Map>)restApiService.getYieldGrainRegForRest();
		for(Map map :yieldGrainRegList){
			String companyCode = (String)map.get("CompanyCode");			
			Company company = companyService.getCompany(companyCode);
			map.put("CompanyName", company.getCompanyName());
		}
		
		if(yieldEvalList != null) total = total + yieldEvalList.size();
		if(yieldGrainRegList != null) total = total + yieldGrainRegList.size();
		
		yieldEvalList.addAll(yieldPredictList);
		yieldEvalList.addAll(yieldEvalList);
		
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("data", yieldEvalList);
		paramMap.put("total", total);
		
		String data = JSONObject.fromObject(paramMap).toString();	
		JSONObject resultJson = new JSONObject();
		resultJson.put("status", "0");
		resultJson.put("msg", "操作成功!");
		
        boolean status = false;
        String msg = "调用失败";
        if(resultJson != null){
        	if(resultJson.getString("status").equals("0")){
        		status = true;
        		msg = resultJson.getString("msg");
        	}
        }
        //保存同步日志
  		YYSyncLog syncLog = new YYSyncLog();
  		syncLog.setBizType(bizType);
  		syncLog.setCreateDate(new Date());
  		syncLog.setSyncCount(total);
  		syncLog.setDataPath(0);
  		syncLog.setSyncDate(new Date());
  		syncLog.setSyncStatus(status?1:0);
  		yySyncLogService.save(syncLog);
  		ApiResult ar = new ApiResult(status,msg);
		return ar;
	}
	
	//博码申请备案同步接口  溯源->亿阳
	@RequestMapping("/codeRegList")
	@ResponseBody
	public ApiResult codeRegList(HttpServletRequest request) throws Exception{
		String bizType = "06";
		
		Date lastSyncDate = null;
		YYSyncLog yySyncLog = yySyncLogService.getLastSyncLog(bizType);
		//if(yySyncLog != null) lastSyncDate = yySyncLog.getSyncDate();
		List<Map> yieldPredictList = (List<Map>)restApiService.getYieldPredict(lastSyncDate);
		for(Map map : yieldPredictList){
			String productCode = (String)map.get("ProductCode");
			String companyCode = (String)map.get("CompanyCode");
			Product product = productService.getProduct(companyCode, productCode);
			if(product != null){
				map.put("ProductName", product.getProductName());
				map.put("NetWeight", product.getWeight());
				map.put("Unit", product.getUnit());
			}
		}
		
		int total = (yieldPredictList != null) ? yieldPredictList.size() : 0;
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("data", yieldPredictList);
		paramMap.put("total", total);
		
		String data = JSONObject.fromObject(paramMap).toString();	
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("status", "0");
		resultJson.put("msg", "操作成功!");
         
        boolean status = false;
        String msg = "调用失败";
        if(resultJson != null){
        	if(resultJson.getString("status").equals("0")){
        		status = true;
        		msg = resultJson.getString("msg");
        	}
        }
        //保存同步日志
  		YYSyncLog syncLog = new YYSyncLog();
  		syncLog.setBizType(bizType);
  		syncLog.setCreateDate(new Date());
  		syncLog.setSyncCount(total);
  		syncLog.setDataPath(0);
  		syncLog.setSyncDate(new Date());
  		syncLog.setSyncStatus(status?1:0);
  		yySyncLogService.save(syncLog);
  		ApiResult ar = new ApiResult(status,msg);
		return ar;
	}
		
	@RequestMapping("/getContratorInfo")
	@ResponseBody
	//取承包人信息
	public ApiResult getContratorInfo(String contractorIDType, String contratorId, String year) throws Exception{
		String _year = year;
		if( StringUtils.isEmpty( _year ) ) {
			_year = DateTimeUtil.getCurrentYear();
		}
		if(StringUtil.isNullOrEmpty(contractorIDType) || StringUtil.isNullOrEmpty(contratorId)){
			return new ApiResult(false, "参数错误！");
		}
		Peasant peasant = peasantService.getByContractorID(contractorIDType, contratorId);
		if(peasant == null){
			return new ApiResult(false, "土地确权失败，无该证件号的土地确权信息。");
		}
		List<Contract> contractList = contractService.getContractList(contractorIDType, contratorId);
		
		Map<String, Object> data = new HashMap<String, Object>();
		String codeKey = "PlowlandType";
		//Float zmj = 0.0f;
		BigDecimal zmj = new BigDecimal("0.0");
		for( Contract contract: contractList ){
			//取土地类型和类别信息
			CommonData _data = commonDataService.getCommonData(codeKey, contract.getLandType());
			if( _data != null ){
				contract.setLandTypeName( _data.getCodeValue() == null ? "" : _data.getCodeValue() );
			}
			_data = commonDataService.getCommonData("PlowlandClass", contract.getLandClass());
			if( _data != null ){
				contract.setLandClassName( _data.getCodeValue() == null ? "" : _data.getCodeValue() );
			}
			
			zmj = zmj.add(new BigDecimal(Float.toString( contract.getMeasurementMu()) ) );
		}
		Float yba = this.geneLandRegDService.queryBAmj( contratorId, _year );
		data.put("peasant", peasant);
		data.put("contract", contractList);
		data.put("zmj", zmj);    //总面积
		data.put("yba", yba);
		data.put("kba", zmj.subtract( new BigDecimal(yba.toString()) ) );
		//根据年度和身份证号获取已卖出粮食
		Float ymcls = this.graiRegService.getYmcls(DateTimeUtil.getCurrentYear(), contratorId);
		data.put("ymcls", ymcls);
		
		ApiResult ar = new ApiResult(true,"success");
		ar.setData(data);
		return ar;
	}
	
	@RequestMapping("/checkGeneReg")
	@ResponseBody
	//土地备案自动审核
	public ApiResult checkGeneReg(Integer regId) throws Exception{
		if(regId == null){
			return new ApiResult(false, "参数错误！");
		}
		int checkStatus = restApiService.checkGeneReg(regId);
		
		boolean status = (checkStatus == 0);
		String msg = null;
		if(checkStatus == 1){
			msg =  "超出最大备案面积";
		}else if(checkStatus == 2){
			msg = "没有相关备案记录";
		}
		ApiResult ar = new ApiResult(status, msg);
		return ar;
	}
	
	@RequestMapping("/checkGeneRegExt")
	@ResponseBody
	//土地备案自动审核
	public ApiResult checkGeneRegExt(@RequestBody GeneLandRegModel geneLandRegModel, String year) throws Exception{
		if(geneLandRegModel == null || year == null){
			return new ApiResult(false, "参数错误！");
		}
		int checkStatus = restApiService.checkGeneRegExt(geneLandRegModel, year);
		
		boolean status = (checkStatus == 0);
		String msg = null;
		if(checkStatus == 1){
			msg =  "超出最大备案面积";
		}else if(checkStatus == 2){
			msg = "没有相关备案记录";
		}
		ApiResult ar = new ApiResult(status, msg);
		return ar;
	}	
	
	//根据批次号防伪码激活
	@RequestMapping("/codeActivate")
	@ResponseBody
	public ApiResult codeActivate(String batchNo, String productCode) throws Exception{
		if(batchNo == null){
			return new ApiResult(false, "批次号不能为空！");
		}
		if(productCode == null){
			return new ApiResult(false, "品类不能为空！");
		}

		List<HashMap> dataList = new ArrayList<HashMap>();
		List<Sample> sampleList = sampleService.getSampleByBatchNo(batchNo, productCode);
		if(sampleList == null)  return new ApiResult(false, "没有找到该批次信息");
		for(Sample sample : sampleList){
			HashMap item = new HashMap();
			item.put("Codes", sample.getSecurityCode());
			dataList.add(item);
		}
		
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("data", dataList);
		paramMap.put("total", dataList.size());
		
		String data = JSONObject.fromObject(paramMap).toString();
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("status", "0");
		resultJson.put("msg", "操作成功!"); 
         
        boolean status = false;
        String msg = "调用失败";
        if(resultJson != null){
        	if(resultJson.getString("status").equals("0")){
        		status = true;
        		msg = resultJson.getString("msg");
        	}
        }
        ApiResult ar = new ApiResult(status, msg);
		return ar;
	}
	
	//防伪码召回
	@RequestMapping("/recall")
	@ResponseBody
	public ApiResult recall(String batchNo, String productCode) throws Exception {
		if(batchNo == null){
			return new ApiResult(false, "批次号不能为空！");
		}
		if(productCode == null){
			return new ApiResult(false, "品类不能为空！");
		}
		
		List<Sample> sampleList = sampleService.getSampleByBatchNo(batchNo, productCode);
		if(sampleList == null) {
			return new ApiResult(false, "没有找到该批次信息");
		}
		
		List<HashMap> dataList = new ArrayList<HashMap>();
		for(Sample sample : sampleList){
			HashMap item = new HashMap();
			item.put("Codes", sample.getSecurityCode());
			dataList.add(item);
		}
		
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("data", dataList);
		paramMap.put("total", dataList.size());
		
		String data = JSONObject.fromObject(paramMap).toString();
		
		log.debug("recall test:");
		log.debug(data);
		
		JSONObject resultJson = new JSONObject();
		resultJson.put("status", "0");
		resultJson.put("msg", "操作成功!");
        boolean status = false;
        String msg = "调用失败";
        if(resultJson != null){
        	if(resultJson.getString("status").equals("0")){
        		status = true;
        		msg = resultJson.getString("msg");
        		//激活防伪码数量处理
        		//激活状态处理
        	}
        }
        ApiResult ar = new ApiResult(status, msg);
		return ar;
	}
	
	//产品档案数据同步接口
	@RequestMapping("/productList")
	@ResponseBody
	public ApiResult recall(HttpServletRequest request) {
		String data = request.getParameter("result");
		if(StringUtil.isNullOrEmpty(data)){
			ApiResult ar = new ApiResult(false, "参数错误");
			return ar;
		}
		log.debug("productList test:");
		log.debug(data);
		//data = "[{\"CompanyCode\": \"GS001\",\"CompanyName\":\"五常市长盛种业有限公司\",\"Details\": [{\"ProductCode\":\"2014124144233\",\"ProductName\":\"测试一号大米\",\"Specifications\": 0,\"Status\": \"0\",\"Unit\": \"袋\"}]}]";
		JSONArray jsonArray = JSONArray.fromObject(data);
		for(int i=0; i<jsonArray.size();i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String companyCode = jsonObject.getString("CompanyCode");
			//企业较验
			Company company = companyService.getCompany(companyCode);
			if(company == null){
				return new ApiResult(false, "企业数据错误");
			}			
			String details =  jsonObject.getString("Details");
			JSONArray detailArray = JSONArray.fromObject(details);
			for(int j=0; j<detailArray.size();j++) {
				JSONObject detailObject = detailArray.getJSONObject(j);
				String productCode = detailObject.getString("ProductCode");
				String productName = detailObject.getString("ProductName");
				float weight = Float.valueOf(detailObject.getString("Specifications"));
				String unit = detailObject.getString("Unit");
				String status = detailObject.getString("Status");
				if(status.equals("0")){ //新增
					//检查该产品是否存在
					Product product = productService.getProduct(companyCode, productCode);
					if(product == null){
						product = new Product();
						product.setCompanyCode(companyCode);
						product.setProductCode(productCode);
						product.setProductName(productName);
						product.setUnit(unit);
						product.setWeight(weight);
						product.setCreateDate(new Date());
						product.setCreateUserId("admin");
						product.setUpdateDate(new Date());
						product.setUpdateUserId("admin");
						productService.save(product);
					}else{
						product.setProductName(productName);
						product.setUnit(unit);
						product.setWeight(weight);
						product.setUpdateDate(new Date());
						product.setUpdateUserId("admin");
						productService.save(product);
					}
				}else if(status.equals("1")){ //修改
					Product product = productService.getProduct(companyCode, productCode);
					if(product != null){
						product.setProductName(productName);
						product.setUnit(unit);
						product.setWeight(weight);
						product.setUpdateDate(new Date());
						product.setUpdateUserId("admin");
						productService.save(product);
					}
				}else if(status.equals("2")){ //删除
					Product product = productService.getProduct(companyCode, productCode);
					if(product != null){
						productService.logicDelete(Product.class, product.getId());
					}
				}
			}
		}
        ApiResult ar = new ApiResult(true);
		return ar;
	}
	
	//防伪码申请记录接口
	@RequestMapping("/SecurityCode")
	@ResponseBody
	public ApiResult SecurityCode(HttpServletRequest request) {
		String data = request.getParameter("result");
		if(StringUtil.isNullOrEmpty(data)){
			ApiResult ar = new ApiResult(false, "参数错误");
			return ar;
		}
		log.debug("SecurityCode test:");
		log.debug(data);
		//data = "[ { \"CompanyCode\": \"GS001\", \"CompanyName\":\"五常市长盛种业有限公司\",\"Details\": [{\"ApplyDate\": \"2014-03-31 09:41:44\",\"Qty\": \"10\"}]}]";
		JSONArray jsonArray = JSONArray.fromObject(data);
		for(int i=0; i<jsonArray.size();i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String companyCode = jsonObject.getString("CompanyCode");
			String companyName = jsonObject.getString("CompanyName");
			String details =  jsonObject.getString("Details");
			
			//二级结点
			JSONArray detailArray = JSONArray.fromObject(details);
			for(int j=0; j<detailArray.size();j++) {
				JSONObject detailObject = detailArray.getJSONObject(j);
				String applyDate =  detailObject.getString("ApplyDate");
				int qty = Integer.valueOf(detailObject.getString("Qty"));
				
				String year = DateTimeUtil.getCurrentYear();
				if(!StringUtil.isNullOrEmpty(applyDate)){
					year = applyDate.substring(0, 4);
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				SecurityCode securityCode = securityCodeService.getSecurityCode(year, companyCode);
				if(securityCode != null){
					SecurityCodeDetail securityCodeDetail = new SecurityCodeDetail();
					securityCodeDetail.setYear(year);
					securityCodeDetail.setCompanyCode(companyCode);
					securityCodeDetail.setCompanyName(companyName);
					securityCodeDetail.setQty(qty);
					try{
						Date date = sdf.parse(applyDate);
						securityCodeDetail.setApplyDate(date);
					}catch(Exception e){
						return new ApiResult(false, "申请日期格式错误！");
					}
					securityCodeDetail.setCreateDate(new Date());
					securityCodeDetail.setCreateUserId("admin");
					securityCodeDetail.setUpdateDate(new Date());
					securityCodeDetail.setUpdateUserId("admin");
					securityCodeDetailService.save(securityCodeDetail);
					
					//根据公司年度统计累计申请数量
					int applyTotalNum = securityCode.getApplyTotalNum();
					securityCode.setApplyTotalNum(applyTotalNum+qty);
					securityCodeService.save(securityCode);
				}else{
					securityCode = new SecurityCode();
					securityCode.setCompanyCode(companyCode);
					securityCode.setYear(year);
					securityCode.setApplyTotalNum(qty);
					securityCode.setActivationTotalNum(0);
					securityCode.setCreateDate(new Date());
					securityCode.setCreateUserId("admin");
					securityCode.setUpdateDate(new Date());
					securityCode.setUpdateUserId("admin");
					securityCodeService.save(securityCode);
					
					SecurityCodeDetail securityCodeDetail = new SecurityCodeDetail();
					securityCodeDetail.setCompanyCode(companyCode);
					securityCodeDetail.setCompanyName(companyName);
					securityCodeDetail.setYear(year);
					securityCodeDetail.setQty(qty);
					try{
						Date date = sdf.parse(applyDate);
						securityCodeDetail.setApplyDate(date);
					}catch(Exception e){
						return new ApiResult(false, "申请日期格式错误！");
					}
					securityCodeDetail.setCreateDate(new Date());
					securityCodeDetail.setCreateUserId("admin");
					securityCodeDetail.setUpdateDate(new Date());
					securityCodeDetail.setUpdateUserId("admin");
					securityCodeDetailService.save(securityCodeDetail);
				}
			}
		}
        ApiResult ar = new ApiResult(true);
		return ar;
	}
	
	//抽检样品记录接收接口
	@RequestMapping("/Sampling")
	@ResponseBody
	public ApiResult Sampling(HttpServletRequest request) throws Exception{
		String result = request.getParameter("result");
		if(StringUtil.isNullOrEmpty(result)){
			 ApiResult ar = new ApiResult(false, "参数错误");
			return ar;
		}
		log.debug("Sampling test:");
		log.debug(result);
		//result = "[{\"CompanyCode\": \"GS001\",\"CompanyName\": \"五常市长盛种业有限公司\",\"Details\": [{\"CheckCode\": \"WCQSE000110201508280001\",\"Details\": [{\"BatchCreateDate\": \"2015-04-20 16:42:36.0\",\"BatchNo\": \"201504201642\",\"ProName\": \"五常大米2.0\",\"ProType\": \"大米\",\"ProductCode\": \"166689474\",\"ProductNum\": \"67\",\"SampleDatetime\": \"2015-08-27 14:06:00\",\"SecurityCode\": \"1234567890123456\"}],\"SampleDate\": \"2015-08-27 14:06:00\",\"SamplePerson\": \"1\"}]}]";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		JSONArray jsonArray = JSONArray.fromObject(result);
		for(int i=0; i<jsonArray.size();i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String companyCode = jsonObject.getString("CompanyCode");
			String details = jsonObject.getString("Details");
			
			JSONArray detailsArray = JSONArray.fromObject(details);
			for(int j=0; j<detailsArray.size();j++) {
				JSONObject detailObject = detailsArray.getJSONObject(j);
				String checkCode = detailObject.getString("CheckCode");
				String sampleDate = detailObject.getString("SampleDate");
				String samplePerson = detailObject.getString("SamplePerson");
				String secondDetail = detailObject.getString("Details");
				
				String year = sampleDate.substring(0, 4);
				//三级结点
				JSONArray secondDetailArray = JSONArray.fromObject(secondDetail);
				for(int k=0; k<secondDetailArray.size(); k++) {
					JSONObject secondObject = secondDetailArray.getJSONObject(k);
					String batchNo = secondObject.getString("BatchNo");
					String productCode = secondObject.getString("ProductCode");
					int productNum = secondObject.getInt("ProductNum");
					String securityCode = secondObject.getString("SecurityCode");
					String produceDate = secondObject.getString("BatchCreateDate");
					
					Sample  sample = new Sample();
					sample.setYear(year);
					sample.setCompanyCode(companyCode);
					sample.setCheckCode(checkCode);
					Date date = sdf.parse(sampleDate);
					sample.setSampleDate(date);
					sample.setSamplePerson(samplePerson);
					sample.setBatchNo(batchNo);
					sample.setProductCode(productCode);
					sample.setProductNum(productNum);
					sample.setSecurityCode(securityCode);
					sample.setInspectStatus("01");
					sample.setProduceDate(DateTimeUtil.getDateFromString(produceDate, "yyyy-MM-dd HH:mm:ss"));
					sample.setCreateDate(new Date());
					sample.setCreateUserId("admin");
					sample.setUpdateDate(new Date());
					sample.setUpdateUserId("admin");
					sampleService.save(sample);
				}
			}
			
		}
		ApiResult ar = new ApiResult(true);
		return ar;
	}
	
	//抽检结果记录接收接口
	@RequestMapping("/SamplingResult")
	@ResponseBody
	public ApiResult SamplingResult(HttpServletRequest request) throws Exception{
		String result = request.getParameter("result");
		if(StringUtil.isNullOrEmpty(result)){
			 ApiResult ar = new ApiResult(false, "参数错误");
			return ar;
		}
		log.debug("SamplingResult test:");
		log.debug(result);
		//result = "[ {\"CompanyCode\": \"GS001\",\"CompanyName\": \"五常市长盛种业有限公司\",\"Details\": [{\"CheckCode\": \"WCQSE000110201508280001\",\"CheckDate\": \"2015-08-28 20:19:09\",\"CheckPerson\": \"李恒远\",\"Details\": [{\"BatchCreate\": \"2015-04-20 16:42:36\",\"BatchNo\": \"201504201642\",\"ProName\": \"五常大米2.0\",\"ProType\": \"大米\",\"ProductCode\": \"166689474\",\"ProductNum\": \"67\",\"SampleDatetime\": \"aa\",\"SecurityCode\": \"1234567890123456\"}],\"HandoverDate\": \"2015-08-27 20:16:37\",\"HandoverPerson\": \"李四\",\"Path\": \"reports/2015/05/08\",\"Remark\": \"检测通过\",\"ResultStatus\": \"1\",\"SendDate\": \"2015-08-27 20:16:25\",\"SendPerson\": \"张三\"}]}]";
		ApiResult ar = new ApiResult(true);
		JSONArray jsonArray = JSONArray.fromObject(result);
		for(int i=0; i<jsonArray.size();i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String companyCode = jsonObject.getString("CompanyCode");
			
			String details = jsonObject.getString("Details");
			JSONArray detailsArray = JSONArray.fromObject(details);
			for(int j=0; j<detailsArray.size();j++) {
				JSONObject detailObject = detailsArray.getJSONObject(j);
				String checkCode = detailObject.getString("CheckCode");
				String deliveryDate = detailObject.getString("SendDate");
				String deliveryPerson = detailObject.getString("SendPerson");
				String handoverTime = detailObject.getString("HandoverDate");
				String handoverPerson = detailObject.getString("HandoverPerson");
				String inspectdateDate = detailObject.getString("CheckDate");
				String inspectPerson = detailObject.getString("CheckPerson");
				String inspectStatus = detailObject.getString("ResultStatus");
				//String remoteFilepath = detailObject.getString("Path");
				String remark = detailObject.getString("Remark");
				
				String remoteFilepath = "/checkPicUrl/"+ checkCode;
				log.debug("quality path!");
				log.debug(remoteFilepath);
				//下载质检报告
				String fileSavePath = request.getSession().getServletContext().getRealPath("/upload/report/") + "/";
				FTPUtil ftpUtil = new FTPUtil();
				List<String> reportFileList = ftpUtil.downFtpFile(remoteFilepath, fileSavePath);
				ftpUtil.disconnect();
				
				String localPath = "";
				if(reportFileList.size() > 0){
					localPath = StringUtils.join(reportFileList.toArray(), ";");
				}
				
				String year = inspectdateDate.substring(0, 4);
				Quality quality = new Quality();
				quality.setYear(year);
				quality.setCompanyCode(companyCode);
				quality.setCheckCode(checkCode);
				if(!StringUtil.isNullOrEmpty(deliveryDate)){
					quality.setDeliveryDate(DateTimeUtil.getDateFromString(deliveryDate, "yyyy-MM-dd HH:mm:ss"));
				}
				if(!StringUtil.isNullOrEmpty(inspectdateDate)){
					quality.setInspectDate(DateTimeUtil.getDateFromString(inspectdateDate, "yyyy-MM-dd HH:mm:ss"));
				}
				quality.setDeliveryPerson(deliveryPerson);
				quality.setHandoverPerson(handoverPerson);
				if(!StringUtil.isNullOrEmpty(handoverTime)){
					quality.setHandoverTime(DateTimeUtil.getDateFromString(handoverTime,"yyyy-MM-dd HH:mm:ss"));
				}
				quality.setInspectPerson(inspectPerson);
				inspectStatus = inspectStatus.equals("1") ? "02" : "03";
				quality.setInspectStatus(inspectStatus);
				quality.setPath(localPath);
				quality.setRemark(remark);
				quality.setCreateDate(new Date());
				quality.setCreateUserId("admin");
				quality.setUpdateDate(new Date());
				quality.setUpdateUserId("admin");
				qualityService.save(quality);
				
				List<HashMap> codeList = new ArrayList<HashMap>();
				//更新抽检状态
				JSONArray secondDetailArray = detailObject.getJSONArray("Details");
				for(int k=0; k<secondDetailArray.size(); k++) {
					JSONObject secondObject = secondDetailArray.getJSONObject(k);
					String batchNo = secondObject.getString("BatchNo");
					String productCode = secondObject.getString("ProductCode");
					List<Sample> sampleList = sampleService.getSampleByBatchNo(batchNo, productCode);
					if(sampleList != null){
						for(Sample sample : sampleList){
							sample.setInspectStatus(inspectStatus);
							sample.setDeliveryPerson(deliveryPerson);
							sample.setDeliveryDate(DateTimeUtil.getDateFromString(deliveryDate, "yyyy-MM-dd HH:mm:ss"));
							sampleService.save(sample);
						}
					}

					HashMap<String, String> item = new HashMap<String, String>();
					item.put("Codes", secondObject.getString("SecurityCode"));
					codeList.add(item);
				}
				
				boolean iscanActivate = true; //根据总量控制判断本次质检能否激活
				if(inspectStatus.equals("02")){
					//进行总量控制
					//已激活产量 < 总产量 评估产量+收粮产量
					GraiEval gEval = graiEvalService.getGraiEvalByYear(year);
					double sumAreas = graiEvalService.getLandsArea(companyCode, year);
					double maxlYield = sumAreas * gEval.getMaxYield() * gEval.getMilledriceRate();
					double grainRegYield = graiEvalService.getTotalWeight(year, companyCode) * gEval.getMilledriceRate();
					
					//总量控制
					double sampleYield = sampleService.getSampleYield(year, companyCode);
					if(sampleYield > (maxlYield+grainRegYield)){ //超出总量
						iscanActivate = false;
					}
					
					//检测是否有投入品备案
					if(!inputRegService.hasInputReg(year, companyCode)){
						iscanActivate = false;
					}
					//检查是否有过程监控数据
					if(!procMoniService.hasProcMoniFile(year, companyCode)){
						iscanActivate = false;
					}
				}
				
				if(iscanActivate && codeList.size() > 0){
					Map<String, Object> paramMap = new HashMap();
					paramMap.put("data", codeList);
					paramMap.put("total", codeList.size());
					
					String codeStr = JSONObject.fromObject(paramMap).toString();
					
					log.debug("SamplingResult activate test:");
					log.debug(codeStr);
					
					JSONObject resultJson = new JSONObject();
					resultJson.put("status", "0");
					resultJson.put("msg", "操作成功!");
					
			        boolean status = false;
			        String msg = "调用失败";
			        if(resultJson != null){
			        	//激活成功
			        	if(resultJson.getString("status").equals("0")){
			        		//更新激活的防伪码总量
			        		for(Map codeItem : codeList){
			        			String scode = (String)codeItem.get("Codes");
			        			Sample sample = sampleService.getSampleBySecurityCode(scode);
				        		SecurityCode securityCode = securityCodeService.getSecurityCode(year, companyCode);
				        		if(sample != null && securityCode != null){
				        			securityCode.setActivationTotalNum(securityCode.getActivationTotalNum()+sample.getProductNum());
				        			securityCodeService.save(securityCode);
				        		}
			        		}
			        		status = true;
			        		msg = resultJson.getString("msg");
			        	}
			        }
			        ar = new ApiResult(status, msg);
				}
			}
		}
		return ar;
	}
}
