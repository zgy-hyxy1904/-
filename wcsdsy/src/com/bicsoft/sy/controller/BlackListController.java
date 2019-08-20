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

import com.bicsoft.sy.entity.BlackList;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.BlackListModel;
import com.bicsoft.sy.model.GridModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.BlackListService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * 黑名单管理
 * @author 创新中软
 * @date 2015-08-26
 */
@Controller
@RequestMapping("/blackList/")
public class BlackListController {
	private static final Logger log = LoggerFactory.getLogger(BlackListController.class);
	
	@Autowired
	private BlackListService blackListService;
	
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.blackListService.logicDelete(BlackList.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody BlackListModel BlackListModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		BlackListModel.setCreateDate(baseModel.getCreateDate());
		BlackListModel.setCreateUserId(baseModel.getCreateUserId());
		BlackListModel.setUpdateDate(baseModel.getUpdateDate());
		BlackListModel.setUpdateUserId(baseModel.getUpdateUserId());
		this.blackListService.save(BlackListModel);
		
		return new JsonResult(true);
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public JsonResult view(Model model, Integer id){
		this.blackListService.delete(id);
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id){
		if( id != null && id > 0 ){
			BlackList blackList = this.blackListService.getBlackList(id);
			model.addAttribute("blackList", blackList);
			
		}
		
		return new ModelAndView("/blacklist/blackListEdit");
	}

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, BlackListModel blackListModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(pageModel!=null ){ 
			model.addAttribute("pageModel", pageModel);
			model.addAttribute("companyCode", blackListModel.getCompanyCode());
			model.addAttribute("beginDate", blackListModel.getBeginDate());
			model.addAttribute("endDate", blackListModel.getEndDate());
		}
		
		return new ModelAndView("/blacklist/blackList");
	  }
	
	@RequestMapping({"/search"})
	@ResponseBody
	public ModelAndView search(Model model, Integer page, Integer pageSize, BlackListModel blackListModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( blackListModel.getCompanyCode() )){ 
			params.put("companyCode", blackListModel.getCompanyCode() );
		}
		if(StringUtils.isNotEmpty( blackListModel.getBeginDate() )){ 
			params.put("beginDate", blackListModel.getBeginDate() );
		}
		if(StringUtils.isNotEmpty( blackListModel.getEndDate() )){ 
			params.put("endDate", blackListModel.getEndDate() );
		}
		
		pageModel = this.blackListService.queryForPageModel("BlackList", params, pageModel);
		
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("companyCode", blackListModel.getCompanyCode());
		model.addAttribute("beginDate", blackListModel.getBeginDate());
		model.addAttribute("endDate", blackListModel.getEndDate());
		
		return new ModelAndView("/blacklist/blackList");
	  }
	
	@RequestMapping({"/listquery"})
	@ResponseBody
	public GridModel listquery(Model model, Integer page, Integer pageSize, String userName){
		page = 1;
		pageSize = 10;
		userName = "";
		PageModel pageModel = new PageModel(page, pageSize);
		
	    return new GridModel(pageModel);
	 }
}
