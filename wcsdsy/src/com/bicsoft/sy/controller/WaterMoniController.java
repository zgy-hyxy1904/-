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

import com.bicsoft.sy.entity.MoniPoint;
import com.bicsoft.sy.entity.WaterMoni;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.GridModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.WaterMoniModel;
import com.bicsoft.sy.service.MoniPointService;
import com.bicsoft.sy.service.WaterMoniService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * 水质监测
 * @author 创新中软
 * @date 2015-08-17
 */
@Controller
@RequestMapping("/waterMoni/")
public class WaterMoniController {
	private static final Logger log = LoggerFactory.getLogger(WaterMoniController.class);
	
	@Autowired
	private WaterMoniService waterMoniService;
	
	@Autowired
	private MoniPointService moniPointService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.waterMoniService.logicDelete(WaterMoni.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody WaterMoniModel waterMoniModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		waterMoniModel.setCreateDate(baseModel.getCreateDate());
		waterMoniModel.setCreateUserId(baseModel.getCreateUserId());
		waterMoniModel.setUpdateDate(baseModel.getUpdateDate());
		waterMoniModel.setUpdateUserId(baseModel.getUpdateUserId());
		this.waterMoniService.save(waterMoniModel);
		
		return new JsonResult(true);
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public ModelAndView view(Model model, Integer id){
		if( id != null && id > 0 ){
			WaterMoni waterMoni = this.waterMoniService.getWaterMoni(id);
			model.addAttribute("waterMoni", waterMoni);
			 
			model.addAttribute("monitorPointCode", waterMoni.getMonitorPointCode());
		}else{
			WaterMoni waterMoni = new WaterMoni();
			waterMoni.setMonitorDate(DateTimeUtil.getCurrentDate());
			model.addAttribute("waterMoni", waterMoni);
			
		}
		List<MoniPoint> pointList = this.moniPointService.getMoniPointList( Constants.MONI_POINT_02 );
		model.addAttribute("pointList", pointList);
		
		return new ModelAndView("/watermoni/waterMoniView");
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id){
		if( id != null && id > 0 ){
			WaterMoni waterMoni = this.waterMoniService.getWaterMoni(id);
			model.addAttribute("waterMoni", waterMoni);
			 
			model.addAttribute("monitorPointCode", waterMoni.getMonitorPointCode());
		}else{
			WaterMoni waterMoni = new WaterMoni();
			waterMoni.setMonitorDate(DateTimeUtil.getCurrentDate());
			model.addAttribute("waterMoni", waterMoni);
			
		}
		List<MoniPoint> pointList = this.moniPointService.getMoniPointList( Constants.MONI_POINT_02 );
		model.addAttribute("pointList", pointList);
		
		return new ModelAndView("/watermoni/waterMoniEdit");
	}

	@RequestMapping({"/search"})
	@ResponseBody
	public ModelAndView search(Model model, Integer page, Integer pageSize, WaterMoniModel waterMoniModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( waterMoniModel.getBeginDate() )){ 
			params.put("beginDate", waterMoniModel.getBeginDate() );
		}
		if(StringUtils.isNotEmpty( waterMoniModel.getEndDate() )){ 
			params.put("endDate", waterMoniModel.getEndDate() );
		}
		pageModel = this.waterMoniService.queryForPageModel("WaterMoni", params, pageModel);
		List<WaterMoni> list = (List<WaterMoni>)pageModel.getResult();
		for(WaterMoni waterMoni : list){
			waterMoni.setMonitorPointName( this.moniPointService.getMoniPoint(waterMoni.getMonitorPointCode()).getMonitorPointName() );
		}
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("beginDate", waterMoniModel.getBeginDate());
		model.addAttribute("endDate", waterMoniModel.getEndDate());
		
		return new ModelAndView("/watermoni/waterMoniList");
	  }
	
	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, WaterMoniModel waterMoniModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(waterMoniModel!=null ){ 
			model.addAttribute("pageModel", pageModel);
			model.addAttribute("beginDate", waterMoniModel.getBeginDate());
			model.addAttribute("endDate", waterMoniModel.getEndDate());
		}
		
		return new ModelAndView("/watermoni/waterMoniList");
	  }
	
	@RequestMapping({"/listquery"})
	@ResponseBody
	public GridModel listquery(Model model, Integer page, Integer pageSize, String userName){
		page = 1;
		pageSize = 10;
		userName = "";
		PageModel pageModel = new PageModel(page, pageSize);
		//pageModel = this.waterMoniService.findPage(pageModel, userName);
	    return new GridModel(pageModel);
	 }
}
