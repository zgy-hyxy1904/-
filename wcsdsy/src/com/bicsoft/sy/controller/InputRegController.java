package com.bicsoft.sy.controller;

import java.util.HashMap;
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

import com.bicsoft.sy.entity.InputReg;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.GridModel;
import com.bicsoft.sy.model.InputRegModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.FileManagerService;
import com.bicsoft.sy.service.InputRegService;
import com.bicsoft.sy.service.ServialNumService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * 投入品备案
 * @author 
 * @date 2015-08-17
 */
@Controller
@RequestMapping("/inputReg/")
public class InputRegController {
	private static final Logger log = LoggerFactory.getLogger(InputRegController.class);
	
	@Autowired
	private InputRegService inputRegService;
	@Autowired
	private FileManagerService fileManagerService;
	@Autowired
	private ServialNumService servialNumService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.inputRegService.logicDelete(InputReg.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody InputRegModel inputRegModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		inputRegModel.setCreateDate(baseModel.getCreateDate());
		inputRegModel.setCreateUserId(baseModel.getCreateUserId());
		inputRegModel.setUpdateDate(baseModel.getUpdateDate());
		inputRegModel.setUpdateUserId(baseModel.getUpdateUserId());
		this.inputRegService.save(inputRegModel);
		CommonUtil.saveMfile(fileManagerService, "02", inputRegModel.getId() + "", request);
		
		return new JsonResult(true);
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public ModelAndView view(Model model, Integer id){
		if( id != null && id > 0 ){
			InputReg inputReg = this.inputRegService.getInputReg(id);
			model.addAttribute("inputReg", inputReg);
		}else{ 
			InputReg inputReg = new InputReg();
			inputReg.setApplyBatchNo( servialNumService.getServialNum(Constants.BIZ_TYPE_TR) );
			inputReg.setPurchaseDate(DateTimeUtil.getCurrentDate());
			model.addAttribute("inputReg", inputReg);
		}
		
		
		return new ModelAndView("/inputreg/inputRegView");
	}
	
	@RequestMapping("/viewInfo")
	@ResponseBody
	public ModelAndView viewInfo(Model model, Integer id, Integer page, Integer pageSize, InputRegModel inputRegModel){
		model.addAttribute("year", inputRegModel.getYear() );
		model.addAttribute("companyCode", inputRegModel.getCompanyCode() );
		model.addAttribute("beginDate", inputRegModel.getBeginDate());
		model.addAttribute("endDate", inputRegModel.getEndDate());
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		
		if( id != null && id > 0 ){
			InputReg inputReg = this.inputRegService.getInputReg(id);
			model.addAttribute("inputReg", inputReg);
		}else{ 
			InputReg inputReg = new InputReg();
			inputReg.setApplyBatchNo( servialNumService.getServialNum(Constants.BIZ_TYPE_TR) );
			inputReg.setPurchaseDate(DateTimeUtil.getCurrentDate());
			model.addAttribute("inputReg", inputReg);
		}
		
		return new ModelAndView("/inputreg/inputRegViewInfo");
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id, Integer page, Integer pageSize, InputRegModel inputRegModel, String retFlag, HttpServletRequest request){
		String bizType = "02";
		CommonUtil.removeTmpFileList( bizType, request );   //清空未保存文件
		if( id != null && id > 0 ){
			InputReg inputReg = this.inputRegService.getInputReg(id);
			model.addAttribute("inputReg", inputReg);
			if( inputReg != null ){
				model.addAttribute("companyCode", inputReg.getCompanyCode());
				model.addAttribute("year", inputReg.getYear());
			}
		}else{ 
			InputReg inputReg = new InputReg();
			inputReg.setApplyBatchNo( servialNumService.getServialNum(Constants.BIZ_TYPE_TR) );
			inputReg.setPurchaseDate(DateTimeUtil.getCurrentDate());
			model.addAttribute("inputReg", inputReg);
			model.addAttribute("year", inputReg.getYear());
		}
		model.addAttribute("retFlag", retFlag);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("inputRegModel", inputRegModel);
		
		return new ModelAndView("/inputreg/inputRegEdit");
	}

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, InputRegModel inputRegModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( inputRegModel.getYear() )){ 
			params.put("year", inputRegModel.getYear() );
		}
		if(StringUtils.isNotEmpty( inputRegModel.getCompanyCode() )){ 
			params.put("companyCode", inputRegModel.getCompanyCode() );
		}
		if(StringUtils.isNotEmpty( inputRegModel.getBeginDate() )){ 
			params.put("beginDate", inputRegModel.getBeginDate() );
		}
		if(StringUtils.isNotEmpty( inputRegModel.getEndDate() )){ 
			params.put("endDate", inputRegModel.getEndDate() );
		}
		if( StringUtils.isNotEmpty( inputRegModel.getYear() ) ){
			pageModel = this.inputRegService.queryForPageModel("InputReg", params, pageModel);
		}
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("provEvalModel", inputRegModel);
		model.addAttribute("year", inputRegModel.getYear());
		model.addAttribute("companyCode", inputRegModel.getCompanyCode());
		model.addAttribute("beginDate", inputRegModel.getBeginDate());
		model.addAttribute("endDate", inputRegModel.getEndDate());
		
		return new ModelAndView("/inputreg/inputRegList");
	  }
	
	@RequestMapping({"/listquery"})
	@ResponseBody
	public GridModel listquery(Model model, Integer page, Integer pageSize, InputRegModel inputRegModel){
		page = 1;
		pageSize = 10;
		PageModel pageModel = new PageModel(page, pageSize);
		//pageModel = this.inputRegService.findPage(pageModel, inputRegModel);
	    return new GridModel(pageModel);
	 }
}
