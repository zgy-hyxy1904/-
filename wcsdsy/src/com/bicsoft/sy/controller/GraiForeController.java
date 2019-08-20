package com.bicsoft.sy.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
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

import com.bicsoft.sy.entity.Company;
import com.bicsoft.sy.entity.Contract;
import com.bicsoft.sy.entity.GraiFore;
import com.bicsoft.sy.entity.GraiForeD;
import com.bicsoft.sy.entity.Peasant;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.GraiExcelModel;
import com.bicsoft.sy.model.GraiForeModel;
import com.bicsoft.sy.model.GraiRegModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.CompanyService;
import com.bicsoft.sy.service.ContractService;
import com.bicsoft.sy.service.GraiForeDService;
import com.bicsoft.sy.service.GraiForeService;
import com.bicsoft.sy.service.PeasantService;
import com.bicsoft.sy.service.RestApiService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.ExcelUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * 收粮预报
 * @author 创新中软
 * @date 2015-08-26
 */
@Controller
@RequestMapping("/graiFore/")
public class GraiForeController {
	private static final Logger log = LoggerFactory.getLogger(GraiRegController.class);
	
	@Autowired
	private GraiForeService graiForeService;
	
	@Autowired
	private GraiForeDService graiForeDService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ContractService contractService;
	
	@Autowired
	private PeasantService peasantService;
	
	@Autowired
	private RestApiService restApiService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.graiForeService.logicDelete(GraiFore.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody GraiForeModel graiForeModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		graiForeModel.setCreateDate(baseModel.getCreateDate());
		graiForeModel.setCreateUserId(baseModel.getCreateUserId());
		graiForeModel.setUpdateDate(baseModel.getUpdateDate());
		graiForeModel.setUpdateUserId(baseModel.getUpdateUserId());
		
		this.graiForeService.save(graiForeModel);
		
		return new JsonResult(true);
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public ModelAndView view(Model model, Integer id){
		if( id != null && id > 0 ){
			List<GraiForeD> list = this.graiForeDService.getGraiForeDListByHid(id);
			
			model.addAttribute("list", list);
		}
		
		return new ModelAndView("/graireg/view");
	}
	
	@RequestMapping("/viewInfo")
	@ResponseBody
	public ModelAndView viewInfo(Model model, Integer id){
		if( id != null && id > 0 ){
			List<GraiForeD> list = this.graiForeDService.getGraiForeDListByHid(id);
			model.addAttribute("list", list);
			
			//model.addAttribute("list", list);
		}
		
		return new ModelAndView("/graireg/graiForeViewList");
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id){
		if( id != null && id > 0 ){
			GraiFore graiFore = this.graiForeService.getGraiFore(id);
			model.addAttribute("graiFore", graiFore);
			
			model.addAttribute("year", graiFore.getYear());
		}
		return new ModelAndView("/graireg/graiForeEdit");
	}
	
	@RequestMapping("/importInput")
	@ResponseBody
	public ModelAndView importInput(Model model, String year, String companyCode, String companyName){
		model.addAttribute("year", year);
		model.addAttribute("companyCode", companyCode);
		model.addAttribute("companyName", companyName);
		
		return new ModelAndView("/graireg/importEdit");
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
		  String companyName = request.getParameter("companyName");
		  String companyCode = request.getParameter("companyCode");
		  Company company = companyService.getCompany(companyCode);
		  companyName = company.getCompanyName();
		  
		  int successCnt = 0;
		  int idErrCnt = 0;
		  int chongfuCnt = 0;
		  
		  String base = request.getSession().getServletContext().getRealPath("/") + "graiexcel" + File.separator;
		  File file = new File(base);
		  if(!file.exists()){
		       file.mkdirs();
		  }
		  try{
			  String path = base + File.separator + sourceName;
		   
			  multipartFile.transferTo(new File(path));
			  //上传成功后读取Excel表格里面的数据
			  //System.out.println("路径是"+path);
			  List<GraiExcelModel> importList = ExcelUtil.readExcelFile( path );
			  if( importList == null || importList.size() == 0 ){
				  JsonResult jr = new JsonResult( false, "导入模板格式不对或Excel数据为空！" );
			      JSONObject json = JSONObject.fromObject(jr);
			      response.setContentType("text/html;charset=utf-8");
			      response.getWriter().print(json.toString());
			      log.debug(json.toString() );
			      return null;
			  }else{
				  //数据较验
				  List<HashMap> errorList = new ArrayList<HashMap>();
				  List<HashMap> chongfuList = new ArrayList<HashMap>();
				  HashMap checkedMap = new HashMap(); 
				  int rowNo = 0;
				  List<GraiExcelModel> insertList = new ArrayList<GraiExcelModel>();
				  for( GraiExcelModel model : importList){
					  rowNo++;
					  if( !CommonUtil.isCardNo( model.getIdNumber() ) ){
					      HashMap map = new HashMap();
					      map.put("rowNo", rowNo);
					      map.put("idNum", model.getIdNumber());
					      map.put("msg", "身份证格式错误！");
					      errorList.add(map);
					      continue;
					  }
					  
					  //判断确权信息
					  Peasant peasant = peasantService.getByContractorID("01", model.getIdNumber());
					  if(peasant == null){
						  HashMap map = new HashMap();
					      map.put("rowNo", rowNo);
					      map.put("idNum", model.getIdNumber());
					      map.put("msg", "无该身份证的土地确权信息！");
					      errorList.add(map);
					      continue;
					  }
					  if(!peasant.getContractorName().equals(model.getName())){
						  HashMap map = new HashMap();
					      map.put("rowNo", rowNo);
					      map.put("idNum", model.getIdNumber());
					      map.put("msg", "身份证号与姓名不匹配！");
					      errorList.add(map);
					      continue;
					  }
					  
					  List<Contract> contractList = getMuInfo(model.getIdNumber(),"01");
					  if( contractList == null || contractList.size()==0 ){
					      HashMap map = new HashMap();
					      map.put("rowNo", rowNo);
					      map.put("idNum", model.getIdNumber());
					      map.put("msg", "没有注册土地面积和产量！");
					      errorList.add(map);
					      continue;
					  }
					  
					  //本次重复
					  if(checkedMap.get(model.getIdNumber()+model.getName()) != null){
						  chongfuCnt++;
						  HashMap map = new HashMap();
					      map.put("rowNo", rowNo);
					      map.put("idNum", model.getIdNumber());
					      map.put("msg", "该证件号重复！");
					      chongfuList.add(map);
					      continue;
					  }
					  
					  //判断之前是否预报过
					  GraiFore gf =this.graiForeService.getGraiForeByIdNumer(companyCode, model.getIdNumber(), year);
					  if(gf != null){
						  chongfuCnt++;
						  HashMap map = new HashMap();
					      map.put("rowNo", rowNo);
					      map.put("idNum", model.getIdNumber());
					      map.put("msg", "该农户已经预报！");
					      chongfuList.add(map);
					      continue;
					  }
					  
					  checkedMap.put(model.getIdNumber()+model.getName(), 1);
					  insertList.add(model);
				  }
				  if(errorList.size() > 0){
					  JsonResult jr = new JsonResult( false, "formatError");
					  HashMap dataMap = new HashMap();
					  dataMap.put("errorFlag", 1);
					  dataMap.put("errorList", errorList);
					  dataMap.put("errorMsg", "");
					  jr.setData(dataMap);
					  JSONObject json = JSONObject.fromObject(jr);
					  response.setContentType("text/html;charset=utf-8");
					  response.getWriter().print(json.toString());
					  return null;
				  }
				  //导入数据入库
				  this.graiForeService.saveImportData(year, companyCode, companyName, insertList, baseModel);
				  successCnt = insertList.size();
				  if(chongfuList.size() > 0){
					  errorList.addAll(chongfuList);
					  JsonResult jr = new JsonResult( false, "formatError");
					  String msg = "成功导入"+successCnt+"条。";
					  if(chongfuCnt > 0){
						  msg = msg +"(排除重复身份证"+chongfuCnt+"个)";
					  }
					  HashMap dataMap = new HashMap();
					  dataMap.put("errorFlag", 2);
					  dataMap.put("errorList", errorList);
					  dataMap.put("errorMsg", msg);
					  jr.setData(dataMap);
					  JSONObject json = JSONObject.fromObject(jr);
					  response.setContentType("text/html;charset=utf-8");
					  response.getWriter().print(json.toString());
					  return null;
				  }
			  }
		  }catch (Exception e) {
			   try {
				   JsonResult jr = new JsonResult( false, "未知原因！" );
			       JSONObject json = JSONObject.fromObject(jr);
			       response.setContentType("text/html;charset=utf-8");
			       response.getWriter().print(json.toString());
			       log.debug(json.toString() );
			       return null;
			   } catch (IOException e1) {
				   e1.printStackTrace();
			   }
		  }
		  
		  String msg = "成功导入"+successCnt+"条。";
		  if(chongfuCnt > 0){
			  msg = msg +"(排除重复身份证"+chongfuCnt+"个)";
		  }
		  JsonResult jr = new JsonResult( true , msg);
		  jr.setData("");
	      JSONObject json = JSONObject.fromObject(jr);
	      try {
	    	  response.setContentType("text/html;charset=utf-8");
			   response.getWriter().print(json.toString());
	      } catch (IOException e) {
			  e.printStackTrace();
	      }
      
	      return null;
	}
	
	@RequestMapping("/checkExcel")
	@ResponseBody
    public JsonResult checkExcel(HttpServletRequest request, HttpServletResponse response, Model model, Integer page, Integer pageSize, GraiRegModel graiRegModel) throws IOException{
		String year = request.getParameter("year");
	    String companyCode = request.getParameter("companyCode");
	    String beginDate = request.getParameter("beginDate");
	    String endDate = request.getParameter("endDate");
	    
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( year )){ 
			params.put("year", year );
		}
		if(StringUtils.isNotEmpty( companyCode )){ 
			params.put("companyCode", companyCode );
		}
		if(StringUtils.isNotEmpty( beginDate)){ 
			params.put("beginDate", beginDate );
		}
		if(StringUtils.isNotEmpty( endDate)){ 
			params.put("endDate", endDate );
		}
		
		params.put("expExcel", "y" );
		pageModel = this.graiForeService.queryForPageModel("GraiFore", params, pageModel);
		List<GraiFore> list = (List<GraiFore>)pageModel.getResult();
		
		JsonResult js = new JsonResult( true );
		if(list == null) js.setData(0);
		else js.setData(list.size());
		return js;
	}
	
	@RequestMapping("/expExcel")
	@ResponseBody
    public JsonResult expExcel(HttpServletRequest request, HttpServletResponse response, Model model, Integer page, Integer pageSize, GraiRegModel graiRegModel) throws IOException{
		String year = request.getParameter("year");
	    String companyCode = request.getParameter("companyCode");
	    String beginDate = request.getParameter("beginDate");
	    String endDate = request.getParameter("endDate");
	    
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( year )){ 
			params.put("year", year );
		}
		if(StringUtils.isNotEmpty( companyCode )){ 
			params.put("companyCode", companyCode );
		}
		if(StringUtils.isNotEmpty( beginDate)){ 
			params.put("beginDate", beginDate );
		}
		if(StringUtils.isNotEmpty( endDate)){ 
			params.put("endDate", endDate );
		}
		
		params.put("expExcel", "y" );
		pageModel = this.graiForeService.queryForPageModel("GraiFore", params, pageModel);
		List<GraiFore> list = (List<GraiFore>)pageModel.getResult();
		for( GraiFore graiFore : list ){
			if( graiForeService.getZyInfo( graiFore.getCompanyCode(), graiFore.getIdNumber(), graiFore.getYear() ) > 1 ){
				graiFore.setZyLand( "是" );
			}else{
				graiFore.setZyLand( "否" );
			}
			Company c = companyService.getCompany( graiFore.getCompanyCode() );
			graiFore.setCompanyConnectName( c.getConnectName() );
			graiFore.setCompanyConnectPhone( c.getConnectPhone() );
		}
		excelExp( (List<GraiFore>)pageModel.getResult(), request, response );
		
		return new JsonResult( true );
	}
	
	private void excelExp(List<GraiFore> list, HttpServletRequest request, HttpServletResponse response){
		String fileName="预报报表";
        
        String columnNames[] = {"年度","企业","农户姓名","身份证号","实亩(合计)","测量合计(亩)", "预估总产量斤", "预估日期", "争议地块", "联系人", "联系电话"};//列名
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(list, columnNames).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
        	ServletOutputStream out = response.getOutputStream();
        	response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        }
	}
	
	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, GraiForeModel graiForeModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		//初始化统计值
		Map sumMap = new HashMap();
		sumMap.put("actualMu", "0.00");
		sumMap.put("measurementMu", "0.00");
		sumMap.put("minEstimateTotalYield", "0.00");
		sumMap.put("maxEstimateTotalYield", "0.00");
		pageModel.setData(sumMap);
		Map<String, Object> params = new HashMap<String, Object>();
		if(  StringUtils.isNotEmpty( graiForeModel.getYear() ) ) {
			params.put("year", graiForeModel.getYear());
		}
		if(StringUtils.isNotEmpty( graiForeModel.getCompanyCode() )){ 
			params.put("companyCode", graiForeModel.getCompanyCode() );
		}
		if(StringUtils.isNotEmpty( graiForeModel.getBeginDate() )){ 
			params.put("beginDate", graiForeModel.getBeginDate() );
		}
		if(StringUtils.isNotEmpty( graiForeModel.getEndDate() )){ 
			params.put("endDate", graiForeModel.getEndDate() );
		}
		if(  graiForeModel.getYear() != null ){
			pageModel = this.graiForeService.queryForPageModel("GraiFore", params, pageModel);
		}
		
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("year", graiForeModel.getYear());
		model.addAttribute("companyCode", graiForeModel.getCompanyCode());
		model.addAttribute("beginDate", graiForeModel.getBeginDate());
		model.addAttribute("endDate", graiForeModel.getEndDate());
		
		return new ModelAndView("/graireg/graiForeList");
	  }
	
	@RequestMapping({"/listquery"})
	@ResponseBody
	public ModelAndView listquery(Model model, Integer page, Integer pageSize,GraiForeModel graiForeModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		//初始化统计值
		Map sumMap = new HashMap();
		sumMap.put("actualMu", "0.00");
		sumMap.put("measurementMu", "0.00");
		sumMap.put("minEstimateTotalYield", "0.00");
		sumMap.put("maxEstimateTotalYield", "0.00");
		pageModel.setData(sumMap);
		Map<String, Object> params = new HashMap<String, Object>();
		if(  StringUtils.isNotEmpty( graiForeModel.getYear() ) ) {
			params.put("year", graiForeModel.getYear());
		}
		if(StringUtils.isNotEmpty( graiForeModel.getCompanyCode() )){ 
			params.put("companyCode", graiForeModel.getCompanyCode() );
		}
		if(StringUtils.isNotEmpty( graiForeModel.getBeginDate() )){ 
			params.put("beginDate", graiForeModel.getBeginDate() );
		}
		if(StringUtils.isNotEmpty( graiForeModel.getEndDate() )){ 
			params.put("endDate", graiForeModel.getEndDate() );
		}
		if( graiForeModel.getYear() != null ) { 
			this.graiForeService.queryForPageModel("GraiFore", params, pageModel);
			List<GraiFore> list = (List<GraiFore>)pageModel.getResult();
			for( GraiFore graiFore : list ){
				if( graiForeService.getZyInfo( graiFore.getCompanyCode(), graiFore.getIdNumber(), graiFore.getYear() ) > 0 ){
					graiFore.setZyLand( "是" );
					Company c = companyService.getCompany( graiFore.getCompanyCode() );
					graiFore.setCompanyConnectName( c.getConnectName() );
					graiFore.setCompanyConnectPhone( c.getConnectPhone() );
				}else{
					graiFore.setZyLand( "否" );
				}
			}
		}
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("provEvalModel", graiForeModel);
		model.addAttribute("year", graiForeModel.getYear());
		model.addAttribute("companyCode", graiForeModel.getCompanyCode());
		model.addAttribute("beginDate", graiForeModel.getBeginDate());
		model.addAttribute("endDate", graiForeModel.getEndDate());
		
		return new ModelAndView("/graireg/graiForeListQuery");
	 }
	
	private List<Contract> getMuInfo(String idNumber, String idType){
		Peasant peasant = peasantService.getByContractorID( idType, idNumber);
		
		if( peasant != null ) {
			List<Contract> contractList = this.contractService.getContractList( peasant.getContractorIDType(), peasant.getContractorID());
			
			return contractList;
		}else{
			return null;
		}
	}
}
