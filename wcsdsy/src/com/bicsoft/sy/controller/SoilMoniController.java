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
import com.bicsoft.sy.entity.SoilMoni;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.GridModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SoilMoniModel;
import com.bicsoft.sy.service.MoniPointService;
import com.bicsoft.sy.service.SoilMoniService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * 土壤监测
 * @author 创新中软
 * @date 2015-08-17
 */
@Controller
@RequestMapping("/soilMoni/")
public class SoilMoniController {
	private static final Logger log = LoggerFactory.getLogger(SoilMoniController.class);
	
	@Autowired
	private SoilMoniService soilMoniService;
	
	@Autowired
	private MoniPointService moniPointService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.soilMoniService.logicDelete(SoilMoni.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody SoilMoniModel soilMoniModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		soilMoniModel.setCreateDate(baseModel.getCreateDate());
		soilMoniModel.setCreateUserId(baseModel.getCreateUserId());
		soilMoniModel.setUpdateDate(baseModel.getUpdateDate());
		soilMoniModel.setUpdateUserId(baseModel.getUpdateUserId());
		this.soilMoniService.save(soilMoniModel);
		
		return new JsonResult(true);
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public ModelAndView view(Model model, Integer id){
		if( id != null && id > 0 ){
			SoilMoni soilMoni = this.soilMoniService.getSoilMoni(id);
			model.addAttribute("soilMoni", soilMoni);
			
			model.addAttribute("monitorPointCode", soilMoni.getMonitorPointCode());
		}else{ 
			SoilMoni soilMoni = new SoilMoni();
			soilMoni.setMonitorDate(DateTimeUtil.getCurrentDate());
			model.addAttribute("soilMoni", soilMoni);
			
		}
		List<MoniPoint> pointList = this.moniPointService.getMoniPointList( Constants.MONI_POINT_03 );
		model.addAttribute("pointList", pointList);
		
		return new ModelAndView("/soilmoni/soilMoniView");
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public ModelAndView add(Model model, Integer id){
		if( id != null && id > 0 ){
			SoilMoni soilMoni = this.soilMoniService.getSoilMoni(id);
			model.addAttribute("soilMoni", soilMoni);
			
			model.addAttribute("monitorPointCode", soilMoni.getMonitorPointCode());
		}else{ 
			SoilMoni soilMoni = new SoilMoni();
			soilMoni.setMonitorDate(DateTimeUtil.getCurrentDate());
			model.addAttribute("soilMoni", soilMoni);
			
		}
		List<MoniPoint> pointList = this.moniPointService.getMoniPointList( Constants.MONI_POINT_03 );
		model.addAttribute("pointList", pointList);
		
		return new ModelAndView("/soilmoni/soilMoniAdd");
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id){
		if( id != null && id > 0 ){
			SoilMoni soilMoni = this.soilMoniService.getSoilMoni(id);
			model.addAttribute("soilMoni", soilMoni);
			
			model.addAttribute("monitorPointCode", soilMoni.getMonitorPointCode());
		}else{ 
			SoilMoni soilMoni = new SoilMoni();
			soilMoni.setMonitorDate(DateTimeUtil.getCurrentDate());
			model.addAttribute("soilMoni", soilMoni);
			
		}
		List<MoniPoint> pointList = this.moniPointService.getMoniPointList( Constants.MONI_POINT_03 );
		model.addAttribute("pointList", pointList);
		
		return new ModelAndView("/soilmoni/soilMoniEdit");
	}

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, SoilMoniModel soilMoniModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		if(pageModel!=null){ 
			model.addAttribute("pageModel", pageModel);
			model.addAttribute("beginDate", soilMoniModel.getBeginDate());
			model.addAttribute("endDate", soilMoniModel.getEndDate());
		}
		
		return new ModelAndView("/soilmoni/soilMoniList");
	  }
	
	@RequestMapping({"/search"})
	@ResponseBody
	public ModelAndView search(Model model, Integer page, Integer pageSize, SoilMoniModel soilMoniModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( soilMoniModel.getBeginDate() )){ 
			params.put("beginDate", soilMoniModel.getBeginDate() );
		}
		if(StringUtils.isNotEmpty( soilMoniModel.getEndDate() )){ 
			params.put("endDate", soilMoniModel.getEndDate() );
		}
		pageModel = this.soilMoniService.queryForPageModel("SoilMoni", params, pageModel);
		List<SoilMoni> list = (List<SoilMoni>)pageModel.getResult();
		for(SoilMoni soilMoni : list){
			soilMoni.setMonitorPointName( this.moniPointService.getMoniPoint(soilMoni.getMonitorPointCode()).getMonitorPointName() );
		}
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("beginDate", soilMoniModel.getBeginDate());
		model.addAttribute("endDate", soilMoniModel.getEndDate());
		
		return new ModelAndView("/soilmoni/soilMoniList");
	  }
	
	@RequestMapping({"/listquery"})
	@ResponseBody
	public GridModel listquery(Model model, Integer page, Integer pageSize, String userName){
		page = 1;
		pageSize = 10;
		userName = "";
		PageModel pageModel = new PageModel(page, pageSize);
		//pageModel = this.soilMoniService.findPage(pageModel, userName);
	    return new GridModel(pageModel);
	 }
}