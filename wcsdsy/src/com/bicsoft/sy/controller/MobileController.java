package com.bicsoft.sy.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bicsoft.sy.entity.Company;
import com.bicsoft.sy.entity.Mfile;
import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.model.GraiRegModel;
import com.bicsoft.sy.model.InputRegModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.ProcMoniModel;
import com.bicsoft.sy.service.CommonDataService;
import com.bicsoft.sy.service.CompanyService;
import com.bicsoft.sy.service.FileManagerService;
import com.bicsoft.sy.service.GraiRegService;
import com.bicsoft.sy.service.InputRegService;
import com.bicsoft.sy.service.ProcMoniService;
import com.bicsoft.sy.service.ServialNumService;
import com.bicsoft.sy.service.UserService;
import com.bicsoft.sy.service.YearCodeService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.DictUtil;
import com.bicsoft.sy.util.JsonResult;
import com.bicsoft.sy.util.StringUtil;

/**
 * 手机端接口
 * @author 高华
 * @date 2015-09-16
 */

@Controller
@RequestMapping("/mobile/")
public class MobileController {
	private static final Logger log = LoggerFactory.getLogger(MobileController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private InputRegService inputRegService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private GraiRegService graiRegService;
	@Autowired
	private YearCodeService yearCodeService;
	@Autowired
	private CommonDataService commonDataService;
	@Autowired
	private ServialNumService servialNumService;
	@Autowired
	private ProcMoniService procMoniService;
	@Autowired
	private FileManagerService fileManagerService;
	
	
	@RequestMapping("/logindo")
	@ResponseBody
	public JsonResult logindo(HttpServletRequest request)
	{
		String userID = request.getParameter("userID");
		String password = request.getParameter("password");
		User loginUser = new User();
		loginUser.setUserID(userID);
		loginUser.setPassword(password);
		if(StringUtil.isNullOrEmpty(userID)){
			 return new JsonResult(false, "用户名不能为空！");
		}
		if(StringUtil.isNullOrEmpty( password)){
			 return new JsonResult(false, "密码不能为空！");
		}
	    User entity = this.userService.login(loginUser);
	    if (entity != null)
	    {
	    	if(entity.getLoginStatus().equals("02")){
		    	 return new JsonResult(false, "您已经被禁止登陆！");
		    }
	    	entity.setLastSignTime(new Date());
	    	this.userService.save(entity);;
	    	
	    	JsonResult jr = new JsonResult(true);
	    	
	    	List yearCodeDict = yearCodeService.getYearCodeList();
	    	List inputUnitDict = commonDataService.getCommonDataListByCodeKey("InputMaterialUnit");
	    	List processClassDict = commonDataService.getCommonDataListByCodeKey("ProcessMonitoringClass");
	    	HashMap dataMap = new HashMap();
	    	dataMap.put("user", entity);
	    	dataMap.put("yearCodeDict", yearCodeDict);
	    	dataMap.put("inputUnitDict", inputUnitDict);
	    	dataMap.put("processClassDict", processClassDict);
	    	
	    	//推送年度、投入品单位、过程监控类别
	    	jr.setData(dataMap);
	    	return jr;
	    }
	    return new JsonResult(false, "账号或密码错误！");
	}

	@RequestMapping("/inputList")
	@ResponseBody
	public JsonResult inputList(Integer page, Integer pageSize, InputRegModel inputRegModel)
	{
		if(StringUtil.isNullOrEmpty( inputRegModel.getCompanyCode() )){ 
			return new JsonResult(false, "请选择企业。");
		}
		if(StringUtil.isNullOrEmpty( inputRegModel.getYear() )){ 
			return new JsonResult(false, "请选择年度。");
		}
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(!StringUtil.isNullOrEmpty( inputRegModel.getYear() )){ 
			params.put("year", inputRegModel.getYear() );
		}
		if(!StringUtil.isNullOrEmpty( inputRegModel.getCompanyCode() )){ 
			params.put("companyCode", inputRegModel.getCompanyCode() );
		}
		if(!StringUtil.isNullOrEmpty( inputRegModel.getBeginDate() )){ 
			params.put("beginDate", inputRegModel.getBeginDate() );
		}
		if(!StringUtil.isNullOrEmpty( inputRegModel.getEndDate() )){ 
			params.put("endDate", inputRegModel.getEndDate() );
		}
		if(!StringUtil.isNullOrEmpty( inputRegModel.getYear() ) ){
			pageModel = this.inputRegService.queryForPageModel("InputReg", params, pageModel);
		}
		JsonResult js  = new JsonResult(true);
		js.setData(pageModel);
	    return js;
	}
	
	@RequestMapping("/inputSave")
	@ResponseBody
	public JsonResult inputSave(HttpServletRequest request)
	{
		InputRegModel inputRegModel = new InputRegModel();
		inputRegModel.setApplyBatchNo(servialNumService.getServialNum(Constants.BIZ_TYPE_SL));
		inputRegModel.setYear(request.getParameter("year"));
		inputRegModel.setCompanyCode(request.getParameter("companyCode"));
		inputRegModel.setInputGoodsName(request.getParameter("inputGoodsName"));
		inputRegModel.setPurchaseQuantity(Float.valueOf(request.getParameter("purchaseQuantity")));
		inputRegModel.setInputGoodsUnit(request.getParameter("inputGoodsUnit"));
		inputRegModel.setInputGoodsSupplier(request.getParameter("inputGoodsSupplier"));
		inputRegModel.setPurchasePerson(request.getParameter("purchasePerson"));
		
		if(StringUtil.isNullOrEmpty(inputRegModel.getYear())){ 
			return new JsonResult(false, "请选择年度。");
		}
		if(StringUtil.isNullOrEmpty(inputRegModel.getInputGoodsName())){ 
			return new JsonResult(false, "投入品名称不能为空。");
		}
		if(StringUtil.isNullOrEmpty(inputRegModel.getPurchaseQuantity())){ 
			return new JsonResult(false, "采购量不能为空。");
		}
		if(StringUtil.isNullOrEmpty(inputRegModel.getInputGoodsSupplier())){ 
			return new JsonResult(false, "投入品经销商不能为空。");
		}
		if(StringUtil.isNullOrEmpty(inputRegModel.getPurchasePerson())){ 
			return new JsonResult(false, "采购人不能为空。");
		}
		if(StringUtil.isNullOrEmpty(inputRegModel.getPurchaseDate().toString())){ 
			return new JsonResult(false, "采购日期不能为空。");
		}
		Company company = companyService.getCompany(inputRegModel.getCompanyCode());
		if(company == null){ 
			return new JsonResult(false, "企业名称有误。");
		}
		inputRegModel.setCompanyName(company.getCompanyName());
		try{
			String dzChaseDate = request.getParameter("purchaseDate");
			Date purchaseDate =  DateTimeUtil.getDateFromString(dzChaseDate, "yyy-MM-dd");
			inputRegModel.setPurchaseDate(purchaseDate);
		}catch(Exception e){
			return new JsonResult(false, "采购日期格式错误！");
		}
		
		String userId = request.getParameter("userId");
		inputRegModel.setCreateDate(new Date());
		inputRegModel.setUpdateDate(new Date());
		inputRegModel.setCreateUserId(userId);
		inputRegModel.setUpdateUserId(userId);
		this.inputRegService.save(inputRegModel);
		
		//保存举证资料
		String [] files = request.getParameterValues("files[]");
		for(String file : files){
			String[] fields = file.split("|");
			Mfile mfile = new Mfile();
			mfile.setBizType("02");
			mfile.setBizCode(inputRegModel.getId()+"");
			mfile.setFileType("01");
			mfile.setOriginalName(fields[0]);
			mfile.setFilePath(fields[1]);
			mfile.setFileInfo(fields[2]);
			mfile.setCreateUserId(userId);
			mfile.setCreateDate(new Date());
			mfile.setUpdateUserId(userId);
			mfile.setUpdateDate(new Date());
			
			fileManagerService.save(mfile);
			
			//从临时目录移至上传目录
			String srcPath = request.getSession().getServletContext().getRealPath("/uploadtmp/");
			srcPath += "\\" + mfile.getFilePath();
			
			String dstPath = request.getSession().getServletContext().getRealPath("/upload/");
			dstPath += "\\" + mfile.getFilePath();
			
			File afile = new File(srcPath);
            afile.renameTo(new File(dstPath));
		}
		
		JsonResult js  = new JsonResult(true);
	    return js;
	}

	@RequestMapping({"/grainRegList"})
	@ResponseBody
	public JsonResult grainRegList(Model model, Integer page, Integer pageSize, GraiRegModel graiRegModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		pageModel.setData("0.00");
		Map<String, Object> params = new HashMap<String, Object>();
		if(!StringUtil.isNullOrEmpty( graiRegModel.getYear() )){ 
			params.put("year", graiRegModel.getYear() );
		}
		if(!StringUtil.isNullOrEmpty( graiRegModel.getCompanyCode() )){ 
			params.put("companyCode", graiRegModel.getCompanyCode() );
		}
		if(!StringUtil.isNullOrEmpty( graiRegModel.getBeginDate())){ 
			params.put("beginDate", graiRegModel.getBeginDate() );
		}
		if(!StringUtil.isNullOrEmpty( graiRegModel.getEndDate())){ 
			params.put("endDate", graiRegModel.getEndDate() );
		}
		if(!StringUtil.isNullOrEmpty(graiRegModel.getYear() ) ){
			pageModel = this.graiRegService.queryForPageModel("GraiReg", params, pageModel);
		}
		JsonResult js  = new JsonResult(true);
		js.setData(pageModel);
	    return js;
	}
	
	@RequestMapping({"/grainRegSave"})
	@ResponseBody
	public JsonResult grainRegSave(HttpServletRequest request){
		GraiRegModel graiRegModel = new GraiRegModel();
		graiRegModel.setYear(request.getParameter("year"));
		graiRegModel.setCompanyCode(request.getParameter("companyCode"));
		
		Company company = companyService.getCompany(graiRegModel.getCompanyCode());
		graiRegModel.setCompanyName(company.getCompanyName());
		
		graiRegModel.setFarmerName(request.getParameter("farmerName"));
		graiRegModel.setIdNumber(request.getParameter("idNumber"));
		graiRegModel.setCityCode(request.getParameter("cityCode"));
		graiRegModel.setTownCode(request.getParameter("townCode"));
		graiRegModel.setCountryCode(request.getParameter("countryCode"));
		graiRegModel.setGroupName(request.getParameter("groupName"));
		graiRegModel.setThisWeight(Float.valueOf(request.getParameter("thisWeight")));
		graiRegModel.setOperatorName(request.getParameter("operatorName"));
		try{
			String szOperateDate = request.getParameter("operatorDate");
			Date operateDate =  DateTimeUtil.getDateFromString(szOperateDate, "yyy-MM-dd");
			graiRegModel.setOperatorDate(operateDate);
		}catch(Exception e){
			return new JsonResult(false, "采购日期格式错误！");
		}
		graiRegModel.setActualMu(Float.valueOf(request.getParameter("actualMu")));
		graiRegModel.setMeasurementMu(Float.valueOf(request.getParameter("measurementMu")));
		graiRegModel.setEstimateTotalYield(Float.valueOf(request.getParameter("estimateTotalYield")));
		graiRegModel.setSeedPurchaseTotal(Float.valueOf(request.getParameter("seedPurchaseTotal")));
		graiRegModel.setSoldYield(Float.valueOf(request.getParameter("soldYield")));
		graiRegModel.setSurplusYield(Float.valueOf(request.getParameter("surplusYield")));
		graiRegModel.setRegisteredTotalYield(Float.valueOf(request.getParameter("registeredTotalYield")));
		graiRegModel.setGrainTotalYield(Float.valueOf(request.getParameter("grainTotalYield")));
		
		String userId = request.getParameter("userId");
		graiRegModel.setCreateDate(new Date());
		graiRegModel.setUpdateDate(new Date());
		graiRegModel.setCreateUserId(userId);
		graiRegModel.setUpdateUserId(userId);
		this.graiRegService.save(graiRegModel);
		
		//保存举证资料
		String [] files = request.getParameterValues("files[]");
		for(String file : files){
			String[] fields = file.split("|");
			Mfile mfile = new Mfile();
			mfile.setBizType(fields[0]);
			mfile.setBizCode(graiRegModel.getId()+"");
			mfile.setFileType("01");
			mfile.setOriginalName(fields[1]);
			mfile.setFilePath(fields[2]);
			mfile.setFileInfo(fields[3]);
			mfile.setCreateUserId(userId);
			mfile.setCreateDate(new Date());
			mfile.setUpdateUserId(userId);
			mfile.setUpdateDate(new Date());
			
			fileManagerService.save(mfile);
			
			//从临时目录移至上传目录
			String srcPath = request.getSession().getServletContext().getRealPath("/uploadtmp/");
			srcPath += "\\" + mfile.getFilePath();
			
			String dstPath = request.getSession().getServletContext().getRealPath("/upload/");
			dstPath += "\\" + mfile.getFilePath();
			
			File afile = new File(srcPath);
			
            afile.renameTo(new File(dstPath));
		}
		JsonResult js  = new JsonResult(true);
	    return js;
	}
	
	@RequestMapping({"/procMoniSave"})
	@ResponseBody
	public JsonResult processSave(HttpServletRequest request){
		ProcMoniModel procMoniModel = new ProcMoniModel();
		procMoniModel.setYear(request.getParameter("year"));
		procMoniModel.setCompanyCode(request.getParameter("companyCode"));
		
		String userId = request.getParameter("userId");
		procMoniModel.setCreateDate(new Date());
		procMoniModel.setUpdateDate(new Date());
		procMoniModel.setCreateUserId(userId);
		procMoniModel.setUpdateUserId(userId);
		
		if( procMoniModel.getId() == null ){
			procMoniModel.setBizCode( procMoniModel.getCompanyCode() + procMoniModel.getYear());
		}
		
		this.procMoniService.save(procMoniModel);
	
		//保存图片资料
		String [] files = request.getParameterValues("files[]");
		for(String file : files){
			String[] fields = file.split("|");
			Mfile mfile = new Mfile();
			mfile.setBizType(DictUtil.getImgBizType(procMoniModel.getBizType()));
			mfile.setBizCode(procMoniModel.getBizCode());
			mfile.setExtField1("");
			mfile.setExtField2("");
			mfile.setFileType("01");
			mfile.setOriginalName(fields[1]);
			mfile.setFilePath(fields[2]);
			mfile.setFileInfo(fields[3]);
			mfile.setCreateUserId(userId);
			mfile.setCreateDate(new Date());
			mfile.setUpdateUserId(userId);
			mfile.setUpdateDate(new Date());
			
			fileManagerService.save(mfile);
			
			//从临时目录移至上传目录
			String srcPath = request.getSession().getServletContext().getRealPath("/uploadtmp/");
			srcPath += "\\" + mfile.getFilePath();
			
			String dstPath = request.getSession().getServletContext().getRealPath("/upload/");
			dstPath += "\\" + mfile.getFilePath();
			
			File afile = new File(srcPath);
			
            afile.renameTo(new File(dstPath));
		}
	
		//保存视频资料
		String [] videos = request.getParameterValues("videos[]");
		for(String video : videos){
			String[] fields = video.split("|");
			Mfile mfile = new Mfile();
			mfile.setBizType(DictUtil.getImgBizType(procMoniModel.getBizType()));
			mfile.setBizCode(procMoniModel.getBizCode());
			mfile.setFileInfo(fields[0]);
			mfile.setFilePath(fields[1]);
			mfile.setFileInfo("02");
			mfile.setCreateUserId(userId);
			mfile.setCreateDate(new Date());
			mfile.setUpdateUserId(userId);
			mfile.setUpdateDate(new Date());
			
			fileManagerService.save(mfile);
		}
	    JsonResult js = new JsonResult(true);
	    return js;
	}
	
	@RequestMapping({"/fileUpload"})
	@ResponseBody
	public JsonResult fileUpload(HttpServletRequest request){
		String path = request.getSession().getServletContext().getRealPath("/upload/");
	    String imgStr = request.getParameter("imgStr");
	    String imgName  = request.getParameter("imgName");
	    
	    int index = imgName.lastIndexOf(".");
		String extName = imgName.substring(index+1);
	    String newName = CommonUtil.getUUID() + "." + extName;
	    String filePath = path + newName;
	    boolean isSuccess = CommonUtil.string2Image(imgStr, filePath);
	    JsonResult js = new JsonResult(true);
	    if(!isSuccess){
	    	js = new JsonResult(false, "上传失败！");
	    }
    	js.setData(filePath);
	    return js;
	}
	
	@RequestMapping({"/getFileList"})
	@ResponseBody
	public JsonResult getFileList(HttpServletRequest request){
		String bizType = request.getParameter("bizType");
		String bizCode = request.getParameter("bizCode");
		if(StringUtil.isNullOrEmpty(bizType)){ 
			return new JsonResult(false, "bizType不能为空。");
		}
		if(StringUtil.isNullOrEmpty(bizCode)){ 
			return new JsonResult(false, "bizCode不能为空。");
		}
		List<Mfile> fileList = this.fileManagerService.getFileList(bizType, bizCode);
		JsonResult js = new JsonResult(true);
		js.setData(fileList);
	    return js;
	}
}
