package com.bicsoft.sy.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

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

import com.bicsoft.sy.entity.AreaDevision;
import com.bicsoft.sy.entity.CompanyCPLT;
import com.bicsoft.sy.entity.SpecLandDetail;
import com.bicsoft.sy.entity.SpecLandReg;
import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.ComboModel;
import com.bicsoft.sy.model.InputRegModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SpecLandRegModel;
import com.bicsoft.sy.service.FileManagerService;
import com.bicsoft.sy.service.ServialNumService;
import com.bicsoft.sy.service.SpecLandDetailService;
import com.bicsoft.sy.service.SpecLandRegService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.GetSession;
import com.bicsoft.sy.util.JsonResult;

/**
 * 特殊土地备案
 * @author 
 * @date 2015-08-18
 */
@Controller
@RequestMapping("/specLandReg/")
public class SpecLandRegController {
	private static final Logger log = LoggerFactory.getLogger(AirMoniController.class);
	
	@Autowired
	private SpecLandRegService specLandRegService;
	
	@Autowired
	private SpecLandDetailService specLandDetailService;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	@Autowired
	private ServialNumService servialNumService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		if( idArray != null ) {
			for (String id : idArray) {
				this.specLandRegService.logicDelete(SpecLandReg.class, Integer.parseInt(id));
			}
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody SpecLandRegModel specLandRegModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		if( specLandRegModel.getId() == null || specLandRegModel.getId() < 1 ){
			specLandRegModel.setCreateDate(baseModel.getCreateDate());
			specLandRegModel.setCreateUserId(baseModel.getCreateUserId());
		}
		specLandRegModel.setUpdateDate(baseModel.getUpdateDate());
		specLandRegModel.setUpdateUserId(baseModel.getUpdateUserId());
		
		this.specLandRegService.save(specLandRegModel);
		CommonUtil.saveMfile(fileManagerService, "04", specLandRegModel.getId() + "", request);
		
		return new JsonResult(true);
	}
	
	@RequestMapping("/saveReason")
	@ResponseBody
	public JsonResult saveReason(@RequestBody SpecLandRegModel specLandRegModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		specLandRegModel.setUpdateDate(baseModel.getUpdateDate());
		specLandRegModel.setUpdateUserId(baseModel.getUpdateUserId());
		specLandRegModel.setUpdateUserName(baseModel.getUpdateUserName());
		
		this.specLandRegService.saveReason(specLandRegModel);
		
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public ModelAndView view(Model model, Integer id, Integer gotoPage, Integer page, Integer pageSize, SpecLandRegModel specLandRegModel){
		model.addAttribute("year", specLandRegModel.getYear() );
		model.addAttribute("companyCode", specLandRegModel.getCompanyCode() );
		model.addAttribute("beginDate", specLandRegModel.getBeginDate());
		model.addAttribute("endDate", specLandRegModel.getEndDate());
		model.addAttribute("status", specLandRegModel.getStatus());
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("gotoPage", gotoPage);
		
		if( id != null && id > 0 ){
			//主表信息
			SpecLandReg specLandReg = this.specLandRegService.getSpecLandReg(id);
			model.addAttribute("specLandReg", specLandReg);
			
			model.addAttribute("contractorType", specLandReg.getContractorType());
			model.addAttribute("idType", specLandReg.getIdType());
			//model.addAttribute("companyCode", specLandReg.getCompanyCode());
			//model.addAttribute("companyCode1", specLandReg.getCompanyCode());
			//子表信息
			SpecLandDetail specLandDetail = this.specLandDetailService.getSpecLandDetail(id+"");
			if( specLandDetail != null ){
				model.addAttribute("landType", specLandDetail.getLandType());
				model.addAttribute("landClass", specLandDetail.getLandClass());
				model.addAttribute("actualMu", specLandDetail.getActualMu());
				model.addAttribute("townCodeDetail", specLandDetail.getTownCode());
				model.addAttribute("countryCodeDetail", specLandDetail.getCountryCode());
				model.addAttribute("groupNameDetail", specLandDetail.getGroupName());
				model.addAttribute("detailId", specLandDetail.getId());
			}
		}
		if( id != null ){
			model.addAttribute("id", id);
		}
		return new ModelAndView("/speclandreg/specLandRegView");
	}
	
	@RequestMapping("/operatorFlow")
	@ResponseBody
	public ModelAndView operatorFlow(Model model, Integer id){
		
		return new ModelAndView("/genelandreg/geneLandRegFlowList");
	}
	
	@RequestMapping("/submitAudit")
	@ResponseBody
	public JsonResult submitAudit(HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		String[] idArray = request.getParameterValues("ids[]");

		this.specLandRegService.updateStatus( idArray, baseModel );
		
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	/**
	 * 添加、编辑、审核
	 * @param model
	 * @param id
	 * @param flag: audit审核;
	 * @return
	 */
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id, Integer page, Integer pageSize, SpecLandRegModel specLandRegModel, String flag, String retFlag, HttpServletRequest request){
		String bizType = "04";
		CommonUtil.removeTmpFileList( bizType, request );   //清空未保存文件
		
		if( id != null && id > 0 ){
			//主表信息
			SpecLandReg specLandReg = this.specLandRegService.getSpecLandReg(id);
			model.addAttribute("specLandReg", specLandReg);
			
			model.addAttribute("contractorType", specLandReg.getContractorType());
			model.addAttribute("idType", specLandReg.getIdType());
			model.addAttribute("year", specLandReg.getYear());
			model.addAttribute("companyCode", specLandReg.getCompanyCode());
			model.addAttribute("companyCode1", specLandReg.getCompanyCode());
			//子表信息
			SpecLandDetail specLandDetail = this.specLandDetailService.getSpecLandDetail(id+"");
			if( specLandDetail != null ){
				model.addAttribute("landType", specLandDetail.getLandType());
				model.addAttribute("landClass", specLandDetail.getLandClass());
				model.addAttribute("actualMu", specLandDetail.getActualMu());
				model.addAttribute("townCodeDetail", specLandDetail.getTownCode());
				model.addAttribute("countryCodeDetail", specLandDetail.getCountryCode());
				model.addAttribute("groupNameDetail", specLandDetail.getGroupName());
				model.addAttribute("detailId", specLandDetail.getId());
			}
		}else{ 
			SpecLandReg specLandReg = new SpecLandReg();
			specLandReg.setApplyBatchNo( servialNumService.getServialNum(Constants.BIZ_TYPE_TX));
			specLandReg.setOperatorDate(DateTimeUtil.getCurrentDate());
			model.addAttribute("specLandReg", specLandReg);
			User user = GetSession.getSessionEntity(request);
			model.addAttribute("contractorType", "01");
			model.addAttribute("idType", "01");
			model.addAttribute("companyCode", user.getCompanyCode());
			model.addAttribute("companyCode1", user.getCompanyCode());
		}
		//根据当前年度和企业名称查询
		if( StringUtils.isEmpty( flag ) ){
			flag = "edit";
		}
		if( id != null ){
			model.addAttribute("id", id);
		}
		model.addAttribute("flag", flag);
		model.addAttribute("retFlag", retFlag);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("specLandRegModel", specLandRegModel);
		
		if(flag != null && "audit".equals( flag ) ){
			return new ModelAndView("/speclandreg/specLandRegAudit");
		}else{
			return new ModelAndView("/speclandreg/specLandRegEdit");
		}
	}

	/**
	 * 驳回
	 * @param model
	 * @param id
	 * @param 
	 * @return
	 */
	@RequestMapping("/auditBhInput")
	@ResponseBody
	public ModelAndView auditBhInput(Model model, Integer id, HttpServletRequest request){
		if( id != null && id > 0 ){
			SpecLandReg specLandReg = this.specLandRegService.getSpecLandReg(id);
			
			BaseModel baseModel = CommonUtil.getBaseModel(request);
			specLandReg.setUpdateDate(baseModel.getUpdateDate());
			specLandReg.setUpdateUserId(baseModel.getUpdateUserId());
			specLandReg.setAuditor( baseModel.getUpdateUserId() );
			specLandReg.setAuditTime( baseModel.getUpdateDate() );
			
			//this.specLandRegService.save(specLandRegModel);
		}
		model.addAttribute("id", id);
		return new ModelAndView("/speclandreg/specLandRegEditReason");
	}
	
	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, SpecLandRegModel specLandRegModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		pageModel.setData("0.00");
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( specLandRegModel.getYear() )){ 
			params.put("year", specLandRegModel.getYear() );
		}
		if(StringUtils.isNotEmpty( specLandRegModel.getCompanyCode() )){ 
			params.put("companyCode", specLandRegModel.getCompanyCode() );
		}
		if(StringUtils.isNotEmpty( specLandRegModel.getBeginDate() )){ 
			params.put("beginDate", specLandRegModel.getBeginDate() );
		}
		if(StringUtils.isNotEmpty( specLandRegModel.getEndDate() )){ 
			params.put("endDate", specLandRegModel.getEndDate() );
		}
		if(StringUtils.isNotEmpty( specLandRegModel.getStatus() )){ 
			params.put("status", specLandRegModel.getStatus() );
		}
		if( StringUtils.isNotEmpty( specLandRegModel.getYear() ) ){
			pageModel = this.specLandRegService.queryForPageModel("SpecLandReg", params, pageModel);
		}
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("companyCode", specLandRegModel.getCompanyCode());
		model.addAttribute("year", specLandRegModel.getYear());
		model.addAttribute("beginDate", specLandRegModel.getBeginDate());
		model.addAttribute("endDate", specLandRegModel.getEndDate());
		model.addAttribute("status", specLandRegModel.getStatus());
		
		return new ModelAndView("/speclandreg/specLandRegList");
	  }
	
	/**
	 * 备案审核列表页
	 * @param model
	 * @param page
	 * @param pageSize
	 * @param specLandRegModel
	 * @return
	 */
	@RequestMapping({"/listquery"})
	@ResponseBody
	public ModelAndView listquery(Model model, Integer page, Integer pageSize, SpecLandRegModel specLandRegModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		pageModel.setData("0.00");
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( specLandRegModel.getYear() )){ 
			params.put("year", specLandRegModel.getYear() );
		}
		if(StringUtils.isNotEmpty( specLandRegModel.getCompanyCode() )){ 
			params.put("companyCode", specLandRegModel.getCompanyCode() );
		}
		if(StringUtils.isNotEmpty( specLandRegModel.getBeginDate() )){ 
			params.put("beginDate", specLandRegModel.getBeginDate() );
		}
		if(StringUtils.isNotEmpty( specLandRegModel.getEndDate() )){ 
			params.put("endDate", specLandRegModel.getEndDate() );
		}
		if(StringUtils.isNotEmpty( specLandRegModel.getStatus() )){ 
			params.put("status", specLandRegModel.getStatus() );
		}
		
		if( StringUtils.isNotEmpty( specLandRegModel.getYear() ) ){
			pageModel = this.specLandRegService.queryForPageModel("SpecLandReg", params, pageModel);
		}
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("year", specLandRegModel.getYear());
		model.addAttribute("companyCode", specLandRegModel.getCompanyCode());
		model.addAttribute("beginDate", specLandRegModel.getBeginDate());
		model.addAttribute("endDate", specLandRegModel.getEndDate());
		model.addAttribute("status", specLandRegModel.getStatus());
		
		return new ModelAndView("/speclandreg/specLandRegListQuery");
	 }

}
