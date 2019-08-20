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

import com.bicsoft.sy.entity.AirMoni;
import com.bicsoft.sy.entity.MoniPoint;
import com.bicsoft.sy.model.AirMoniModel;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.GridModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.AirMoniService;
import com.bicsoft.sy.service.MoniPointService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * 大气监测
 * @author 创新中软
 * @date 2015-08-17
 */
@Controller
@RequestMapping("/airMoni/")
public class AirMoniController {
	private static final Logger log = LoggerFactory.getLogger(AirMoniController.class);
	
	@Autowired
	private AirMoniService airMoniService;
	
	@Autowired
	private MoniPointService moniPointService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.airMoniService.logicDelete(AirMoni.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody AirMoniModel airMoniModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		airMoniModel.setCreateDate(baseModel.getCreateDate());
		airMoniModel.setCreateUserId(baseModel.getCreateUserId());
		airMoniModel.setUpdateDate(baseModel.getUpdateDate());
		airMoniModel.setUpdateUserId(baseModel.getUpdateUserId());
		this.airMoniService.save(airMoniModel);
		
		return new JsonResult(true);
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public ModelAndView view(Model model, Integer id){
		if( id != null && id > 0 ){
			AirMoni airMoni = this.airMoniService.getAirMoni(id);
			model.addAttribute("airMoni", airMoni);
			
			model.addAttribute("monitorPointCode", airMoni.getMonitorPointCode());
		}else{ 
			AirMoni airMoni = new AirMoni();
			airMoni.setMonitorDate(DateTimeUtil.getCurrentDate());
			model.addAttribute("airMoni", airMoni);
		}
		
		List<MoniPoint> pointList = this.moniPointService.getMoniPointList( Constants.MONI_POINT_01 );
		model.addAttribute("pointList", pointList);
		
		return new ModelAndView("/airmoni/airMoniView");
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public ModelAndView add(Model model, Integer id){
		if( id != null && id > 0 ){
			AirMoni airMoni = this.airMoniService.getAirMoni(id);
			model.addAttribute("airMoni", airMoni);
			
			model.addAttribute("monitorPointCode", airMoni.getMonitorPointCode());
		}else{ 
			AirMoni airMoni = new AirMoni();
			airMoni.setMonitorDate(DateTimeUtil.getCurrentDate());
			model.addAttribute("airMoni", airMoni);
		}
		
		List<MoniPoint> pointList = this.moniPointService.getMoniPointList( Constants.MONI_POINT_01 );
		model.addAttribute("pointList", pointList);
		
		return new ModelAndView("/airmoni/airMoniAdd");
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id){
		if( id != null && id > 0 ){
			AirMoni airMoni = this.airMoniService.getAirMoni(id);
			model.addAttribute("airMoni", airMoni);
			
			model.addAttribute("monitorPointCode", airMoni.getMonitorPointCode());
		}else{ 
			AirMoni airMoni = new AirMoni();
			airMoni.setMonitorDate(DateTimeUtil.getCurrentDate());
			model.addAttribute("airMoni", airMoni);
		}
		
		List<MoniPoint> pointList = this.moniPointService.getMoniPointList( Constants.MONI_POINT_01 );
		model.addAttribute("pointList", pointList);
		
		return new ModelAndView("/airmoni/airMoniEdit");
	}

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, AirMoniModel airMoniModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(airMoniModel!=null){
			model.addAttribute("pageModel", pageModel);
			model.addAttribute("beginDate", airMoniModel.getBeginDate());
			model.addAttribute("endDate", airMoniModel.getEndDate());
		}
		
		return new ModelAndView("/airmoni/airMoniList");
	  }
	
	@RequestMapping({"/search"})
	@ResponseBody
	public ModelAndView search(Model model, Integer page, Integer pageSize, AirMoniModel airMoniModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(airMoniModel!=null){
			if(StringUtils.isNotEmpty( airMoniModel.getBeginDate() )){ 
				params.put("beginDate", airMoniModel.getBeginDate() );
			}
			if(StringUtils.isNotEmpty( airMoniModel.getEndDate() )){ 
				params.put("endDate", airMoniModel.getEndDate() );
			}
			pageModel = this.airMoniService.queryForPageModel("AirMoni", params, pageModel);
			List<AirMoni> list = (List<AirMoni>)pageModel.getResult();
			for(AirMoni airMoni : list){
				airMoni.setMonitorPointName( this.moniPointService.getMoniPoint(airMoni.getMonitorPointCode()).getMonitorPointName() );
			}
			
			model.addAttribute("pageModel", pageModel);
			model.addAttribute("beginDate", airMoniModel.getBeginDate());
			model.addAttribute("endDate", airMoniModel.getEndDate());
		}
		
		return new ModelAndView("/airmoni/airMoniList");
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
