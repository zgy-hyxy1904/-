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

import com.bicsoft.sy.entity.ProcMoniS;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.ProcMoniSModel;
import com.bicsoft.sy.model.ProvEvalModel;
import com.bicsoft.sy.service.ProcMoniSService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * 过程监控主表
 * @author 创新中软
 * @date 2015-08-20
 */
@Controller
@RequestMapping("/procMoniS/")
public class ProcMoniSController {
	private static final Logger log = LoggerFactory.getLogger(InputRegController.class);
	
	@Autowired
	private ProcMoniSService procMoniSService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.procMoniSService.logicDelete(ProcMoniS.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody ProcMoniSModel procMoniSModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		procMoniSModel.setCreateDate(baseModel.getCreateDate());
		procMoniSModel.setCreateUserId(baseModel.getCreateUserId());
		procMoniSModel.setUpdateDate(baseModel.getUpdateDate());
		procMoniSModel.setUpdateUserId(baseModel.getUpdateUserId());
		this.procMoniSService.save(procMoniSModel);
		
		return new JsonResult(true);
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public JsonResult view(Model model, Integer id){
		this.procMoniSService.delete(id);
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id){
		if( id != null && id > 0 ){
			ProcMoniS procMoniS = this.procMoniSService.getProcMoniS(id);
			model.addAttribute("procMoniS", procMoniS);
		}
		return new ModelAndView("/procmoni/procMoniSEdit");
	}

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, ProvEvalModel provEvalModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( provEvalModel.getYear() )){ 
			params.put("year", provEvalModel.getYear() );
		}
		pageModel = this.procMoniSService.queryForPageModel("ProcMoni", params, pageModel);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("provEvalModel", provEvalModel);
		model.addAttribute("year", provEvalModel.getYear());
		
		return new ModelAndView("/procmoni/procMoniSList");
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
		pageModel = this.procMoniSService.queryForPageModel("ProcMoni", params, pageModel);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("provEvalModel", provEvalModel);
		model.addAttribute("year", provEvalModel.getYear());
		
		return new ModelAndView("/proveval/provEvalListQuery");
	 }	
}
