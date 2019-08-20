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

import com.bicsoft.sy.entity.GraiRegDetail;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.GraiEvalModel;
import com.bicsoft.sy.model.GraiRegDetailModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.GraiRegDetailService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * 收粮备案
 * @author 创新中软
 * @date 2015-08-17
 */
@Controller
@RequestMapping("/graiRegDetail/")
public class GraiRegDetailController {
	private static final Logger log = LoggerFactory.getLogger(GraiRegController.class);
	
	@Autowired
	private GraiRegDetailService graiRegDetailService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.graiRegDetailService.logicDelete(GraiRegDetail.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody GraiRegDetailModel graiRegDetailModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		graiRegDetailModel.setCreateDate(baseModel.getCreateDate());
		graiRegDetailModel.setCreateUserId(baseModel.getCreateUserId());
		graiRegDetailModel.setUpdateDate(baseModel.getUpdateDate());
		graiRegDetailModel.setUpdateUserId(baseModel.getUpdateUserId());
		
		this.graiRegDetailService.save(graiRegDetailModel);
		
		return new JsonResult(true);
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public JsonResult view(Model model, Integer id){
		this.graiRegDetailService.delete(id);
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id){
		if( id != null && id > 0 ){
			GraiRegDetail graiRegDetail = this.graiRegDetailService.getGraiRegDetail(id);
			model.addAttribute("graiRegDetail", graiRegDetail);
			
			//model.addAttribute("year", graiRegDetail.getYear());
		}
		return new ModelAndView("/graireg/graiRegDetailEdit");
	}

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, GraiEvalModel graiEvalModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( graiEvalModel.getYear() )){ 
			params.put("year", graiEvalModel.getYear() );
		}else{
			graiEvalModel.setYear( DateTimeUtil.getCurrentYear() );
			params.put("year", graiEvalModel.getYear() );
		}
		pageModel = this.graiRegDetailService.queryForPageModel("GraiEval", params, pageModel);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("year", graiEvalModel.getYear());
		
		return new ModelAndView("/graireg/graiRegDetailList");
	  }
	
	@RequestMapping({"/listquery"})
	@ResponseBody
	public ModelAndView listquery(Model model, Integer page, Integer pageSize,GraiEvalModel graiEvalModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("deleteFlag", "Y");
		if(StringUtils.isNotEmpty( graiEvalModel.getYear() )){ 
			params.put("year", graiEvalModel.getYear() );
		}
		pageModel = this.graiRegDetailService.queryForPageModel("GraiEval", params, pageModel);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("provEvalModel", graiEvalModel);
		model.addAttribute("year", graiEvalModel.getYear());
		
		return new ModelAndView("/graieval/graiEvalListQuery");
	 }
}
