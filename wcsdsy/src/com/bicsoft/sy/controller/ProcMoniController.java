package com.bicsoft.sy.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bicsoft.sy.entity.ProcMoni;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.ProcMoniModel;
import com.bicsoft.sy.model.ProvEvalModel;
import com.bicsoft.sy.service.FileManagerService;
import com.bicsoft.sy.service.ProcMoniService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.DictUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * 过程监控主表 --子表
 * @author 创新中软
 * @date 2015-08-20
 */
@Controller
@RequestMapping("/procMoni/")
public class ProcMoniController {
	private static final Logger log = LoggerFactory.getLogger(InputRegController.class);
	
	@Autowired
	private ProcMoniService procMoniService;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.procMoniService.logicDelete(ProcMoni.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/getInfo")
	@ResponseBody
	public JsonResult getInfo(HttpServletRequest request,String year, String companyCode, String bizType){
		ProcMoni procMoni = new ProcMoni();
		if( StringUtils.isNotEmpty( year ) && StringUtils.isNotEmpty( companyCode ) && StringUtils.isNotEmpty( bizType ) ){
			procMoni = this.procMoniService.getProcMoni(year, bizType, companyCode);
			procMoni.setBizType( DictUtil.getImgBizType(procMoni.getBizType()) );
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("procMoni", procMoni);
		
		JsonResult jr = new JsonResult(true);
		if( procMoni.getBizCode() == null )   procMoni.setBizCode("");
		jr.setData( data );
		
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody ProcMoniModel procMoniModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		procMoniModel.setCreateDate(baseModel.getCreateDate());
		procMoniModel.setCreateUserId(baseModel.getCreateUserId());
		procMoniModel.setUpdateDate(baseModel.getUpdateDate());
		procMoniModel.setUpdateUserId(baseModel.getUpdateUserId());
		if( procMoniModel.getId() == null ){
			procMoniModel.setBizCode( procMoniModel.getCompanyCode() + procMoniModel.getYear());
		}
		
		this.procMoniService.save(procMoniModel);
		CommonUtil.saveMfile(fileManagerService, DictUtil.getImgBizType( procMoniModel.getBizType() ), procMoniModel.getBizCode() + "", request);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("procMoniModel", procMoniModel);
		JsonResult jr = new JsonResult(true);
		jr.setData( data );
		
		return jr;
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public JsonResult view(Model model, Integer id){
		this.procMoniService.delete(id);
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id, HttpServletRequest request){
		String bizType = "06";
		CommonUtil.removeTmpFileList( bizType, request );   //清空未保存文件
		CommonUtil.removeTmpFileList( "07", request );   //清空未保存文件
		CommonUtil.removeTmpFileList( "08", request );   //清空未保存文件
		CommonUtil.removeTmpFileList( "09", request );   //清空未保存文件
		CommonUtil.removeTmpFileList( "10", request );   //清空未保存文件
		CommonUtil.removeTmpFileList( "11", request );   //清空未保存文件
		CommonUtil.removeTmpFileList( "12", request );   //清空未保存文件
		CommonUtil.removeTmpFileList( "13", request );   //清空未保存文件
		
		if( id != null && id > 0 ){
			ProcMoni procMoni = this.procMoniService.getProcMoni(id);
			model.addAttribute("procMoni", procMoni);
		}
		
		return new ModelAndView("/procmoni/procMoniEdit");
	}

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, ProcMoniModel procMoniModel){
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( procMoniModel.getYear() )){ 
			params.put("year", procMoniModel.getYear() );
		}
		if(StringUtils.isNotEmpty( procMoniModel.getCompanyCode() )){ 
			params.put("companyCode", procMoniModel.getCompanyCode() );
		}
		if( procMoniModel.getYear() != null  &&  procMoniModel.getCompanyCode() != null ){
			Map fileList = this.procMoniService.getProcMoniList(procMoniModel.getYear(), procMoniModel.getCompanyCode());
			model.addAttribute("fileList", fileList);
		}
		model.addAttribute("procMoniModel", procMoniModel);
		model.addAttribute("year", procMoniModel.getYear());
		model.addAttribute("companyCode", procMoniModel.getCompanyCode());
		return new ModelAndView("/procmoni/procMoniList");
	}
	
	@RequestMapping({"/listquery"})
	@ResponseBody
	public ModelAndView listquery(Model model, Integer page, Integer pageSize,ProvEvalModel provEvalModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deleteFlag", "Y");
		if(StringUtils.isNotEmpty( provEvalModel.getYear() )){ 
			params.put("year", provEvalModel.getYear() );
		}
		pageModel = this.procMoniService.queryForPageModel("ProcMoni", params, pageModel);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("provEvalModel", provEvalModel);
		model.addAttribute("year", provEvalModel.getYear());
		
		return new ModelAndView("/proveval/provEvalListQuery");
	 }	
}
