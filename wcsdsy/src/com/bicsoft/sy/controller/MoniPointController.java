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

import com.bicsoft.sy.entity.MoniPoint;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.GridModel;
import com.bicsoft.sy.model.MoniPointModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.MoniPointService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * 监测点
 * @author 创新中软
 * @date 2015-08-23
 */
@Controller
@RequestMapping("/moniPoint/")
public class MoniPointController {
private static final Logger log = LoggerFactory.getLogger(AirMoniController.class);
	
	@Autowired
	private MoniPointService moniPointService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.moniPointService.logicDelete(MoniPoint.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody MoniPointModel moniPointModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		moniPointModel.setCreateDate(baseModel.getCreateDate());
		moniPointModel.setCreateUserId(baseModel.getCreateUserId());
		moniPointModel.setUpdateDate(baseModel.getUpdateDate());
		moniPointModel.setUpdateUserId(baseModel.getUpdateUserId());
		this.moniPointService.save(moniPointModel);
		
		return new JsonResult(true);
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public ModelAndView view(Model model, Integer id){
		if( id != null && id > 0 ){
			MoniPoint moniPoint = this.moniPointService.getMoniPoint(id);
			model.addAttribute("moniPoint", moniPoint);
			
		}
		
		return new ModelAndView("/monipoint/moniPointView");
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id){
		if( id != null && id > 0 ){
			MoniPoint moniPoint = this.moniPointService.getMoniPoint(id);
			model.addAttribute("moniPoint", moniPoint);
			
		}
		
		return new ModelAndView("/monipoint/moniPointEdit");
	}

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, MoniPointModel moniPointModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( moniPointModel.getMonitorPointType() )){ 
			params.put("monitorPointType", moniPointModel.getMonitorPointType() );
		}
		if( StringUtils.isNotEmpty( moniPointModel.getMonitorPointType() ) ){
			pageModel = this.moniPointService.queryForPageModel("MoniPoint", params, pageModel);
		}
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("monitorPointType", moniPointModel.getMonitorPointType());
		
		return new ModelAndView("/monipoint/moniPointList");
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
