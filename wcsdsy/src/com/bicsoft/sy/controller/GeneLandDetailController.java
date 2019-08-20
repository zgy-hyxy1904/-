package com.bicsoft.sy.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bicsoft.sy.entity.GeneLandDetail;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.GeneLandDetailModel;
import com.bicsoft.sy.model.GridModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.GeneLandDetailService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * 普通土地备案--地块详细信息
 * @author 
 * @date 2015-08-19
 */
@Controller
@RequestMapping("/geneLandDetail/")
public class GeneLandDetailController {
	private static final Logger log = LoggerFactory.getLogger(AirMoniController.class);
	
	@Autowired
	private GeneLandDetailService geneLandDetailService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.geneLandDetailService.logicDelete(GeneLandDetail.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody GeneLandDetailModel geneLandDetailModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		geneLandDetailModel.setCreateDate(baseModel.getCreateDate());
		geneLandDetailModel.setCreateUserId(baseModel.getCreateUserId());
		geneLandDetailModel.setUpdateDate(baseModel.getUpdateDate());
		geneLandDetailModel.setUpdateUserId(baseModel.getUpdateUserId());
		
		this.geneLandDetailService.save(geneLandDetailModel);
		
		return new JsonResult(true);
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public JsonResult view(Model model, Integer id){
		this.geneLandDetailService.delete(id);
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	@RequestMapping("/operatorFlow")
	@ResponseBody
	public ModelAndView operatorFlow(Model model, Integer id){
		
		return new ModelAndView("/genelandreg/geneLandRegFlowList");
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id){
		if( id != null && id > 0 ){
			GeneLandDetail geneLandDetail = this.geneLandDetailService.getGeneLandDetail(id);
			model.addAttribute("geneLandDetail", geneLandDetail);
		}
		//根据当前年度和企业名称查询
		String year = "2015";
		String companyCode = "五常市";
		
		
		return new ModelAndView("/genelandreg/geneLandRegEdit");
	}

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, String userName){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		pageModel = this.geneLandDetailService.queryForPageModel("GeneLandReg", null, pageModel);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("userName", userName);
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
