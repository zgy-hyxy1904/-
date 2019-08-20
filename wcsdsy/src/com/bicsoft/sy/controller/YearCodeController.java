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

import com.bicsoft.sy.entity.YearCode;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.YearCodeModel;
import com.bicsoft.sy.service.YearCodeService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * 年度管理
 * @author 高华
 * @date 2015-09-14
 */

@Controller
@RequestMapping("/year/")
public class YearCodeController {
	private static final Logger log = LoggerFactory.getLogger(YearCodeController.class);
	
	@Autowired
	private YearCodeService yearCodeService;
	
	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(HttpServletRequest request, Model model, Integer page, Integer pageSize, String yearName, String flag)
	{
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		if(flag != null && flag.equals("2")){
			pageModel = this.yearCodeService.queryForPageModel(pageModel, yearName);
		}
		
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("yearName", yearName);
		return new ModelAndView("yearcode/yearcodeList");
	 }
	
	@RequestMapping({"/addInit"})
	@ResponseBody
	public ModelAndView list(Model model){
		return new ModelAndView("/yearcode/yearcodeAdd");
	}
	
	@RequestMapping({"/save"})
	@ResponseBody
	public JsonResult addCompany(@RequestBody YearCodeModel yearCodeModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		yearCodeModel.setCreateDate(baseModel.getCreateDate());
		yearCodeModel.setCreateUserId(baseModel.getCreateUserId());
		yearCodeModel.setUpdateDate(baseModel.getUpdateDate());
		yearCodeModel.setUpdateUserId(baseModel.getUpdateUserId());
		
		String yearCode = yearCodeModel.getYearCode();
		YearCode yearEntity = this.yearCodeService.getYearCode(yearCode);
		if(yearEntity != null && yearEntity.getYearCode() != null){
			return new JsonResult(false, "该年度已添加！");
		}
		this.yearCodeService.save(yearCodeModel);
		return new JsonResult(true);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request)
	{
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.yearCodeService.logicDelete(YearCode.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
}
