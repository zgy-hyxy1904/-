package com.bicsoft.sy.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bicsoft.sy.entity.CommonData;
import com.bicsoft.sy.entity.Contract;
import com.bicsoft.sy.entity.GeneLandReg;
import com.bicsoft.sy.entity.GeneLandRegD;
import com.bicsoft.sy.entity.Peasant;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.ErrorModel;
import com.bicsoft.sy.model.GeneLandRegDModel;
import com.bicsoft.sy.model.GeneLandRegModel;
import com.bicsoft.sy.model.GridModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.AreaDevisionService;
import com.bicsoft.sy.service.CommonDataService;
import com.bicsoft.sy.service.CompanyService;
import com.bicsoft.sy.service.ContractService;
import com.bicsoft.sy.service.GeneLandDetailService;
import com.bicsoft.sy.service.GeneLandRegDService;
import com.bicsoft.sy.service.GeneLandRegService;
import com.bicsoft.sy.service.PeasantService;
import com.bicsoft.sy.service.RestApiService;
import com.bicsoft.sy.service.ServialNumService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.DictUtil;
import com.bicsoft.sy.util.JsonResult;
import com.bicsoft.sy.util.LandExcelUtil;

/**
 * 普通土地备案
 * @author 
 * @date 2015-08-18
 */
@Controller
@RequestMapping("/geneLandReg/")
public class GeneLandRegController {
	private static final Logger log = LoggerFactory.getLogger(AirMoniController.class);
	
	@Autowired
	private GeneLandRegService geneLandRegService;
	
	@Autowired
	private GeneLandDetailService geneLandDetailService;
	
	@Autowired
	private GeneLandRegDService geneLandRegDService;
	
	@Autowired
	private ServialNumService servialNumService;
	
	@Autowired
	private RestApiService restApiService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private AreaDevisionService areaDevisionService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private PeasantService peasantService;
	@Autowired
	private CommonDataService commonDataService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");                            
		for (String id : idArray) {                                                   
			this.geneLandRegService.logicDelete(GeneLandReg.class, Integer.parseInt(id));                                  
		}                                                    
		JsonResult jr = new JsonResult(true);                                    
		return jr;                                                           
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody GeneLandRegModel geneLandRegModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);       
		geneLandRegModel.setCreateDate(baseModel.getCreateDate());                                 
		geneLandRegModel.setCreateUserId(baseModel.getCreateUserId());                                  
		geneLandRegModel.setUpdateDate(baseModel.getUpdateDate());                                  
		geneLandRegModel.setUpdateUserId(baseModel.getUpdateUserId());                              
		//二次确权接口
		
		this.geneLandRegService.save(geneLandRegModel);                                                                  
		JsonResult jr = new JsonResult(true);
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", geneLandRegModel.getId()+"");
		jr.setData( map );
		
		return jr;
	}
	
	@RequestMapping("/copyData")
	@ResponseBody
	public JsonResult copyData(HttpServletRequest request){
		String year = request.getParameter("year");
		String companyCode = request.getParameter("companyCode");
		JsonResult jr = new JsonResult(true);
		List<GeneLandRegD> list = this.geneLandRegDService.getGeneLandRegDList(year, companyCode);
		if( list == null || list.size() == 0 ){
			jr = new JsonResult(false, "未找到土地备案信息。");
		}else{
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("landList", list);
			data.put("operatorDate", DateTimeUtil.getCurrentDateByPattern("yyyy-MM-dd"));
			jr.setData( data );
		}
		
		return jr;
	}
	
	@RequestMapping("/importInput")
	@ResponseBody
	public ModelAndView importInput(Model model, String year, String companyCode, String companyName){
		model.addAttribute("year", year);
		model.addAttribute("companyCode", companyCode);
		model.addAttribute("companyName", companyName);
		
		return new ModelAndView("/genelandreg/importEdit");
	}
	
	/**
	 * @param apkFile
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/uploadExcel")
	@ResponseBody
	public String uploadExcel(@RequestParam(value = "file") MultipartFile apkFile, HttpServletRequest request, HttpServletResponse response){
		  BaseModel baseModel = CommonUtil.getBaseModel(request);
		  MultipartFile multipartFile = apkFile;
		  String sourceName = multipartFile.getOriginalFilename(); // 原始文件名
		  String year = request.getParameter("year");
		  /*int successCnt = 0;
		  int idErrCnt = 0;
		  int chongfuCnt = 0;*/
		  String base = request.getSession().getServletContext().getRealPath("/") + "graiexcel" + File.separator;
		  File file = new File(base);
		  if(!file.exists()){
		       file.mkdirs();
		  }
		  try{
			  String path = base + File.separator + sourceName;
		   
			  multipartFile.transferTo(new File(path));
			  //上传成功后读取Excel表格里面的数据
			  List<GeneLandRegDModel> importList = LandExcelUtil.readExcelFile( path );
			  if( importList == null || importList.size() == 0 ){
				  JsonResult jr = new JsonResult( false, "导入模板格式不对或Excel数据为空！" );
			      JSONObject json = JSONObject.fromObject(jr);
			      response.getWriter().print(json.toString());
			      log.debug(json.toString() );
			      return null;
			  }else{
				  /**
				   * 校验身份证号是否符合规范
				   */
				  for( GeneLandRegDModel model : importList){
					  if( !CommonUtil.isIdType( model.getIdName() ) ){
						  JsonResult jr = new JsonResult( false, "[" + model.getIdType() + "]证件类型格式错误！" );
					      JSONObject json = JSONObject.fromObject(jr);
					      response.getWriter().print(json.toString());
					      log.debug(json.toString() );
					      return null;
					  }
					  if( !CommonUtil.isCardNo( model.getContractorID() ) ){
					      JsonResult jr = new JsonResult( false, "[" + model.getContractorID() + "]身份证格式错误！" );
					      JSONObject json = JSONObject.fromObject(jr);
					      response.getWriter().print(json.toString());
					      log.debug(json.toString() );
					      return null;
					  }
					  if( !CommonUtil.isPhone( model.getContractorTel() ) && !CommonUtil.isPhoneNumber( model.getContractorTel() ) ){
					      JsonResult jr = new JsonResult( false, "[" + model.getContractorTel() + "]联系方式格式错误！" );
					      JSONObject json = JSONObject.fromObject(jr);
					      response.getWriter().print(json.toString());
					      log.debug(json.toString() );
					      return null;
					  }
					  
					  if( !CommonUtil.isPositiveInteger(model.getThismj() ) && !CommonUtil.isPositiveDecimal(model.getThismj() ) ){
					      JsonResult jr = new JsonResult( false, "[" + model.getThismj() + "]面积格式错误！" );
					      JSONObject json = JSONObject.fromObject(jr);
					      response.getWriter().print(json.toString());
					      log.debug(json.toString() );
					      return null;
					  }else{
						  if( model.getThismj().indexOf(".") > 0 ){
							  String xswLength = model.getThismj().substring( model.getThismj().indexOf(".") );
							  if( xswLength != null && xswLength.length() > 3 ){
								  JsonResult jr = new JsonResult( false, "[" + model.getThismj() + "]面积格式错误！" );
							      JSONObject json = JSONObject.fromObject(jr);
							      response.getWriter().print(json.toString());
							      log.debug(json.toString() );
							      return null;
							  }
						  }
					  }
					  if( !CommonUtil.isDate( model.getOperatorDateStr() ) ){
					      JsonResult jr = new JsonResult( false, "[" + model.getOperatorDateStr() + "]日期格式错误！" );
					      JSONObject json = JSONObject.fromObject(jr);
					      response.getWriter().print(json.toString());
					      log.debug(json.toString() );
					      return null;
					  }
				  }
				  String retMsg = "";
				  List<GeneLandRegDModel> iList = new ArrayList<GeneLandRegDModel>();
				  List<ErrorModel> errorList = new ArrayList<ErrorModel>();
				  for( GeneLandRegDModel model : importList){
					  model.setIdType( DictUtil.getIdType( model.getIdName() ) );
					  Peasant peasant = peasantService.getByContractorID(model.getIdType(), model.getContractorID());
					  if(peasant == null){
					      ErrorModel m = new ErrorModel();
					      m.setIdNumber( model.getContractorID() );
					      m.setInfo("无该身份证的土地确权信息！");
					      
					      errorList.add( m );
					      continue;
					  }
					  model.setContractorType( peasant.getContractorType() );
					  CommonData cData = this.commonDataService.getCommonData("ContractorType", model.getContractorType()); 
					  model.setContractorTypeName( cData.getCodeValue() );
					  model.setTownCode(peasant.getTownCode() );
					  model.setCountryCode( peasant.getCountryCode() );
					  model.setTownName( this.areaDevisionService.getAreaNameByCode("town", model.getTownCode()) );
					  model.setCountryName( this.areaDevisionService.getCountryNameByCode("country", model.getTownCode() + "," + model.getCountryCode()) );
					  model.setGroupName( peasant.getGroupName() );
					  model.setArchiveAcreage( new BigDecimal( model.getThismj() ).floatValue() );
					  
					  List<Contract> contractList = contractService.getContractList(model.getIdType(), model.getContractorID());
					  BigDecimal zmj = new BigDecimal("0.00");
					  for( Contract contract: contractList ){
						  zmj = zmj.add(new BigDecimal(Float.toString( contract.getMeasurementMu()) ) );
					  }
					  model.setContractTotalYield( zmj.floatValue() );
					  model.setRegisteredTotalYield( this.geneLandRegDService.queryBAmj( model.getContractorID(), year ) );
					  BigDecimal kba = new BigDecimal("0.00");
					  //BigDecimal contractTotalYield = new BigDecimal("0.00");
					  //contractTotalYield = new BigDecimal( Float.toString(model.getContractTotalYield() ) );
					  kba = new BigDecimal( Float.toString(model.getContractTotalYield())).subtract( new BigDecimal(Float.toString(model.getRegisteredTotalYield())) );
					  model.setKba( kba.floatValue() );
					  
					  if( model.getArchiveAcreage() > model.getKba() ){
						  ErrorModel m = new ErrorModel();
					      m.setIdNumber( model.getContractorID() );
					      m.setInfo("超过可备案面积！");
					      
					      errorList.add( m );
					      continue;
					  }
					  
					  iList.add( model );
				  }
				  for( GeneLandRegDModel model : importList){
					  log.debug("idNumber:" + model.getContractorID() + "-" + model.getArchiveAcreage()
							  + "-" + model.getOperatorDate());
				  }
				  
				  String msg = "成功导入"+ iList.size() +"条。";
				  JsonResult jr = new JsonResult( true, msg);
				  HashMap<String, Object> data = new HashMap<String, Object>();
				  data.put("data", iList);
				  data.put("errorData", errorList);
			      jr.setData( data );
				  JSONObject json = JSONObject.fromObject(jr);
			      response.getWriter().print(json.toString());
			      log.debug(json.toString() );
			      
			      return null;
			  }
		  }catch (Exception e) {
			   try {
				   JsonResult jr = new JsonResult( false, "未知原因！" );
			       JSONObject json = JSONObject.fromObject(jr);
			       response.getWriter().print(json.toString());
			       log.debug(json.toString() );
			       return null;
			   } catch (IOException e1) {
				   e1.printStackTrace();
			   }
		  }
		  
		  return null;
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public ModelAndView view(Model model, Integer id, Integer page, Integer pageSize, GeneLandRegModel geneLandRegModel){
		model.addAttribute("year", geneLandRegModel.getYear() );
		model.addAttribute("companyCode", geneLandRegModel.getCompanyCode() );
		model.addAttribute("beginDate", geneLandRegModel.getBeginDate());
		model.addAttribute("endDate", geneLandRegModel.getEndDate());
		model.addAttribute("status", geneLandRegModel.getStatus());
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		
		if( id != null && id > 0 ){
			GeneLandReg geneLandReg = this.geneLandRegService.getGeneLandReg(id);
			model.addAttribute("geneLandReg", geneLandReg);
			
			List<GeneLandRegD> list = this.geneLandRegDService.getGeneLandRegDList(id + "");
			for( GeneLandRegD d : list ){
				Float zmj = d.getContractTotalYield();
				/*List<GeneLandDetail> detailList = this.geneLandRegService.getGeneLandDetailList(d.getId());
				for( GeneLandDetail detail : detailList ){
					zmj += detail.getMeasurementMu();  
				}*/
				zmj = (zmj == null ? 0.0f : zmj);
				d.setZmj(zmj);//总面积
				Float yba = d.getRegisteredTotalYield(); //this.geneLandRegDService.queryBAmj( d.getContractorID() );
				yba = (yba == null ? 0.0f : yba);
				d.setYba( yba );
				d.setKba( zmj - yba );
			}
			
			model.addAttribute("list", list);
		}
		return new ModelAndView("/genelandreg/geneLandRegView");                                                                          
	}
	
	@RequestMapping("/operatorFlow")
	@ResponseBody
	public ModelAndView operatorFlow(Model model, Integer id){
		
		return new ModelAndView("/genelandreg/geneLandRegFlowList");
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id, Integer page, Integer pageSize, String year, GeneLandRegModel geneLandRegModel, String retFlag){
		if( id != null && id > 0 ){
			GeneLandReg geneLandReg = this.geneLandRegService.getGeneLandReg(id);
			model.addAttribute("year", geneLandReg.getYear());
			model.addAttribute("companyCode", geneLandReg.getCompanyCode());
			model.addAttribute("geneLandReg", geneLandReg);
			
			List<GeneLandRegD> list = this.geneLandRegDService.getGeneLandRegDList(id + "");
			for( GeneLandRegD d : list ){
				Float zmj = d.getContractTotalYield();
				/*List<GeneLandDetail> detailList = this.geneLandRegService.getGeneLandDetailList(d.getId());
				for( GeneLandDetail detail : detailList ){
					zmj += detail.getMeasurementMu();  
				}*/
				d.setZmj( zmj );//总面积
				Float yba = d.getRegisteredTotalYield(); //this.geneLandRegDService.queryBAmj( d.getContractorID() );
				d.setYba( yba );
				d.setKba( zmj - yba );
			}
			
			model.addAttribute("list", list);
		}else{
			GeneLandReg geneLandReg = new GeneLandReg();
			geneLandReg.setApplyBatchNo( servialNumService.getServialNum(Constants.BIZ_TYPE_PT) );
			
			model.addAttribute("geneLandReg", geneLandReg);
		}
		
		model.addAttribute("retFlag", retFlag == null ? "" : retFlag);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("geneLandRegModel", geneLandRegModel); 
		model.addAttribute("year", year);
		
		return new ModelAndView("/genelandreg/geneLandRegEdit");                                                
	}

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, GeneLandRegModel geneLandRegModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		pageModel.setData("0.00");
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( geneLandRegModel.getYear() )){ 
			params.put("year", geneLandRegModel.getYear() );
		}
		if(StringUtils.isNotEmpty( geneLandRegModel.getCompanyCode() )){ 
			params.put("companyCode", geneLandRegModel.getCompanyCode() );
		}
		if(StringUtils.isNotEmpty( geneLandRegModel.getBeginDate() )){ 
			params.put("beginDate", geneLandRegModel.getBeginDate() );
		}
		if(StringUtils.isNotEmpty( geneLandRegModel.getEndDate() )){ 
			params.put("endDate", geneLandRegModel.getEndDate() );
		}
		if(StringUtils.isNotEmpty( geneLandRegModel.getStatus() )){ 
			params.put("status", geneLandRegModel.getStatus() );
		}
		if( StringUtils.isNotEmpty( geneLandRegModel.getYear() ) ){
			pageModel = this.geneLandRegService.queryForPageModel("GeneLandReg", params, pageModel);
			//获取本次备案面积
			List<GeneLandReg> list = (List<GeneLandReg>)pageModel.getResult();
			for( GeneLandReg reg : list ){
				reg.setMjsum( geneLandRegService.getThisWeightSum(reg.getId()+"") );
			}
		}
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("year",geneLandRegModel.getYear() );
		model.addAttribute("companyCode",geneLandRegModel.getCompanyCode() );
		model.addAttribute("beginDate", geneLandRegModel.getBeginDate());
		model.addAttribute("endDate", geneLandRegModel.getEndDate());
		model.addAttribute("status", geneLandRegModel.getStatus());
		
		return new ModelAndView("/genelandreg/geneLandRegList");
	  }
	
	@RequestMapping({"/listquery"})
	@ResponseBody
	public GridModel listquery(Model model, Integer page, Integer pageSize, String userName){
		page = 1;
		pageSize = 10;
		userName = "";
		PageModel pageModel = new PageModel(page, pageSize);
		//pageModel = this.airMoniService.findPage(pageModel, userName);
	    return new GridModel(pageModel);
	 }
	
}
