package com.bicsoft.sy.controller;

import java.math.BigDecimal;
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

import com.bicsoft.sy.entity.Contract;
import com.bicsoft.sy.entity.GraiEval;
import com.bicsoft.sy.entity.GraiReg;
import com.bicsoft.sy.entity.GraiRegDetail;
import com.bicsoft.sy.entity.Peasant;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.GraiRegModel;
import com.bicsoft.sy.model.InputRegModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.ContractService;
import com.bicsoft.sy.service.FileManagerService;
import com.bicsoft.sy.service.GraiEvalService;
import com.bicsoft.sy.service.GraiRegDetailService;
import com.bicsoft.sy.service.GraiRegService;
import com.bicsoft.sy.service.PeasantService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.JsonResult;
import com.bicsoft.sy.util.StringUtil;

/**
 * 收粮备案
 * @author 创新中软
 * @date 2015-08-17
 */
@Controller
@RequestMapping("/graiReg/")
public class GraiRegController {
	private static final Logger log = LoggerFactory.getLogger(GraiRegController.class);
	
	@Autowired
	private GraiRegService graiRegService;
	@Autowired
	private GraiRegDetailService graiRegDetailService;
	@Autowired
	private FileManagerService fileManagerService;
	@Autowired
	private GraiEvalService graiEvalService;
	@Autowired
	private PeasantService peasantService;
	@Autowired
	private ContractService contractService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.graiRegService.logicDelete(GraiReg.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody GraiRegModel graiRegModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		graiRegModel.setCreateDate(baseModel.getCreateDate());
		graiRegModel.setCreateUserId(baseModel.getCreateUserId());
		graiRegModel.setUpdateDate(baseModel.getUpdateDate());
		graiRegModel.setUpdateUserId(baseModel.getUpdateUserId());
		//已卖出
		Float ymcls = this.graiRegService.getYmcls(DateTimeUtil.getCurrentYear(), graiRegModel.getIdNumber());
		//BigDecimal zcl = new BigDecimal( graiRegModel.getEstimateTotalYield() );
		//BigDecimal ymc = new BigDecimal( ymcls );
		/*Float kmc = graiRegModel.getEstimateTotalYield() - ymcls;
		if( graiRegModel.getThisWeight() > kmc ){
			//卖出粮食超产,判断是否有超产凭证
			Object obj = request.getSession().getAttribute("16"+"fileList");
			if( obj == null ){
				JsonResult jr = new JsonResult(false, "本次卖出粮食超出可卖出粮食， 且未上传超产凭证！");
			}
		}*/
		
		this.graiRegService.save(graiRegModel);
		
		CommonUtil.saveMfile(fileManagerService, "14", graiRegModel.getId() + "", request);
		CommonUtil.saveMfile(fileManagerService, "15", graiRegModel.getId() + "", request);
		CommonUtil.saveMfile(fileManagerService, "16", graiRegModel.getId() + "", request);
		
		return new JsonResult(true);
	}
	
	@RequestMapping("/getYmcls")
	@ResponseBody
	public JsonResult getYmcls(@RequestBody GraiRegModel graiRegModel, HttpServletRequest request){
		JsonResult jr = new JsonResult(true);
		HashMap<String, Object> data = new HashMap<String, Object>();
		//已卖出
		Float ymcls = this.graiRegService.getYmcls(DateTimeUtil.getCurrentYear(), graiRegModel.getIdNumber());
		//更新的时候已卖出重量应该减掉本次的重量
		if("1".equals(graiRegModel.getRetFlag()) && graiRegModel.getId() !=null){
			GraiReg graiReg = this.graiRegService.getGraiReg(graiRegModel.getId());
			BigDecimal bdYmcls = new BigDecimal(ymcls.toString()).subtract(new BigDecimal(graiReg.getThisWeight().toString()));
			data.put("ymcls", StringUtil.formatYield(bdYmcls));
		} else {
			data.put("ymcls", ymcls);
		}

		jr.setData(data);
		
		return jr;
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public ModelAndView view(Model model, Integer id, Integer page, Integer pageSize, GraiRegModel graiRegModel){
		model.addAttribute("year", graiRegModel.getYear() );
		model.addAttribute("companyCode", graiRegModel.getCompanyCode() );
		model.addAttribute("beginDate", graiRegModel.getBeginDate());
		model.addAttribute("endDate", graiRegModel.getEndDate());
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		
		if( id != null && id > 0 ){
			GraiReg graiReg = this.graiRegService.getGraiReg(id);
			model.addAttribute("graiReg", graiReg);
			
			GraiRegDetail detail = this.graiRegDetailService.getGraiRegDetailByHid( id );
			
			model.addAttribute("year", graiReg.getYear());
			model.addAttribute("companyCode", graiReg.getCompanyCode());
			model.addAttribute("seedPurchaseTotal", detail.getSeedPurchaseTotal());
			model.addAttribute("grainTotalYield", detail.getGrainTotalYield());
			model.addAttribute("registeredTotalYield", detail.getRegisteredTotalYield());
			model.addAttribute("soldYield", detail.getSoldYield());
			model.addAttribute("surplusYield", detail.getSurplusYield());
			model.addAttribute("estimateTotalYield", detail.getEstimateTotalYield());
			
			getLandList(model, graiReg.getIdNumber());
			
			//已卖出
			Float ymcls = this.graiRegService.getYmcls(DateTimeUtil.getCurrentYear(), graiReg.getIdNumber());
			model.addAttribute("ymcls", ymcls);
		}else{
			GraiReg graiReg  = new GraiReg();
			graiReg.setOperatorDate( DateTimeUtil.getCurrentDate() );

			model.addAttribute("graiReg", graiReg);
			model.addAttribute("zmj", "0");  //默认为0
			model.addAttribute("ymcls", "0");
		}
		//获取最大亩产--根据年度取
		GraiEval eval = this.graiEvalService.getGraiEval( DateTimeUtil.getCurrentYear() );
		model.addAttribute("maxYield", eval.getMaxYield() == null ? 0.0f : eval.getMaxYield());
		
		//model.addAttribute("retFlag", retFlag);
		
		return new ModelAndView("/graireg/graiRegView");
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id, Integer page, Integer pageSize, GraiRegModel graiRegModel, String retFlag, HttpServletRequest request){
		CommonUtil.removeTmpFileList( "14", request );   //清空未保存文件
		CommonUtil.removeTmpFileList( "15", request );   //清空未保存文件
		CommonUtil.removeTmpFileList( "16", request );   //清空未保存文件
		
		if( id != null && id > 0 ){
			GraiReg graiReg = this.graiRegService.getGraiReg(id);
			model.addAttribute("graiReg", graiReg);
			
			GraiRegDetail detail = this.graiRegDetailService.getGraiRegDetailByHid( id );
			
			model.addAttribute("year", graiReg.getYear());
			model.addAttribute("companyCode", graiReg.getCompanyCode());
			model.addAttribute("seedPurchaseTotal", detail.getSeedPurchaseTotal());
			model.addAttribute("grainTotalYield", detail.getGrainTotalYield());
			model.addAttribute("registeredTotalYield", detail.getRegisteredTotalYield());
			model.addAttribute("surplusYield", detail.getSurplusYield());
			model.addAttribute("soldYield", detail.getSoldYield());
			model.addAttribute("estimateTotalYield", detail.getEstimateTotalYield());
			
			getLandList(model, graiReg.getIdNumber());
			
			//已卖出
			Float ymcls = this.graiRegService.getYmcls(DateTimeUtil.getCurrentYear(), graiReg.getIdNumber());
			
			BigDecimal bdYmcls = new BigDecimal(ymcls.toString()).subtract(new BigDecimal(graiReg.getThisWeight().toString()));
			
			model.addAttribute("ymcls", StringUtil.formatYield(bdYmcls));
		}else{
			GraiReg graiReg  = new GraiReg();
			graiReg.setOperatorDate( DateTimeUtil.getCurrentDate() );

			model.addAttribute("graiReg", graiReg);
			model.addAttribute("zmj", "");  //默认为0
			model.addAttribute("ymcls", "0");
		}
		//获取最大亩产--根据年度取
		GraiEval eval = this.graiEvalService.getGraiEval( DateTimeUtil.getCurrentYear() );
		if( eval == null ) {
			eval = new GraiEval();
		}
		model.addAttribute("maxYield", eval.getMaxYield() == null ? 0.0f : eval.getMaxYield());
		
		model.addAttribute("retFlag", retFlag);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("graiRegModel", graiRegModel);
		
		return new ModelAndView("/graireg/graiRegEdit");
	}
	
	private void getLandList(Model model, String idNumber){
		Peasant peasant = peasantService.getByContractorID("01", idNumber);//默认按身份证,身份证号查询
		if(peasant == null){
			peasant = new Peasant();
		}
		Float zmj = 0.0f;
		Float htMj = 0.0f;
		List<Contract> contractList = contractService.getContractList( peasant.getContractorIDType(), peasant.getContractorID());
		for( Contract contract: contractList ){
			zmj += contract.getMeasurementMu();
			htMj += contract.getContractArea();
		}
		model.addAttribute("zmj", zmj);    //总面积
		model.addAttribute("actualMu", htMj);
		model.addAttribute("landList", contractList);
	}
	
	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, GraiRegModel graiRegModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		pageModel.setData("0.00");
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( graiRegModel.getYear() )){ 
			params.put("year", graiRegModel.getYear() );
		}
		if(StringUtils.isNotEmpty( graiRegModel.getCompanyCode() )){ 
			params.put("companyCode", graiRegModel.getCompanyCode() );
		}
		if(StringUtils.isNotEmpty( graiRegModel.getBeginDate())){ 
			params.put("beginDate", graiRegModel.getBeginDate() );
		}
		if(StringUtils.isNotEmpty( graiRegModel.getEndDate())){ 
			params.put("endDate", graiRegModel.getEndDate() );
		}
		if( StringUtils.isNotEmpty(graiRegModel.getYear() ) ){
			pageModel = this.graiRegService.queryForPageModel("GraiReg", params, pageModel);
		}
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("year", graiRegModel.getYear());
		model.addAttribute("companyCode", graiRegModel.getCompanyCode());
		model.addAttribute("beginDate", graiRegModel.getBeginDate());
		model.addAttribute("endDate", graiRegModel.getEndDate());
		
		return new ModelAndView("/graireg/graiRegList");
	  }
	
	@RequestMapping({"/listquery"})
	@ResponseBody
	public ModelAndView listquery(Model model, Integer page, Integer pageSize,GraiRegModel graiRegModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deleteFlag", "Y");
		if(StringUtils.isNotEmpty( graiRegModel.getYear() )){ 
			params.put("year", graiRegModel.getYear() );
		}
		if( StringUtils.isNotEmpty(graiRegModel.getYear() ) ){
			pageModel = this.graiRegService.queryForPageModel("GraiReg", params, pageModel);
		}
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("provEvalModel", graiRegModel);
		model.addAttribute("year", graiRegModel.getYear());
		
		return new ModelAndView("/graireg/graiRegListQuery");
	 }
}
