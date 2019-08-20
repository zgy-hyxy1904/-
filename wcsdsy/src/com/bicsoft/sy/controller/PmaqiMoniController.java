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
import com.bicsoft.sy.entity.PmaqiMoni;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.GridModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.PmaqiMoniModel;
import com.bicsoft.sy.service.MoniPointService;
import com.bicsoft.sy.service.PmaqiMoniService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * pm2.5监测
 * @author 创新中软
 * @date 2015-08-24
 */
@Controller
@RequestMapping("/pmMoni/")
public class PmaqiMoniController {
	private static final Logger log = LoggerFactory.getLogger(AirMoniController.class);
	
	@Autowired
	private PmaqiMoniService pmaqiMoniService;
	
	@Autowired
	private MoniPointService moniPointService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.pmaqiMoniService.logicDelete(PmaqiMoni.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody PmaqiMoniModel pmaqiMoni, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		pmaqiMoni.setCreateDate(baseModel.getCreateDate());
		pmaqiMoni.setCreateUserId(baseModel.getCreateUserId());
		pmaqiMoni.setUpdateDate(baseModel.getUpdateDate());
		pmaqiMoni.setUpdateUserId(baseModel.getUpdateUserId());
		this.pmaqiMoniService.save(pmaqiMoni);
		
		return new JsonResult(true);
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public ModelAndView view(Model model, Integer id){
		
		String monitorPointCode = null;
		if( id != null && id > 0 ){
			PmaqiMoni pmaqiMoni = this.pmaqiMoniService.getPmaqiMoni(id);
			model.addAttribute("pmMoni", pmaqiMoni);
			
			model.addAttribute("monitorPointCode", pmaqiMoni.getMonitorPointCode());
			monitorPointCode = pmaqiMoni.getMonitorPointCode();
		}else{
			PmaqiMoni pmaqiMoni = new PmaqiMoni();
			pmaqiMoni.setMonitorDate(DateTimeUtil.getCurrentDate());
			model.addAttribute("pmMoni", pmaqiMoni); 
			
		}
		List<MoniPoint> pointList = this.moniPointService.getMoniPointList(Constants.MONI_POINT_04);
		model.addAttribute("pointList", pointList);
		String pointName = "";
		
		if(monitorPointCode != null) {
			for( MoniPoint point : pointList ){
				if(monitorPointCode.equals(point.getMonitorPointCode())){
					pointName = point.getMonitorPointName();
					break;
				}
			}
		}
		model.addAttribute("pointName", pointName);
		
		return new ModelAndView("/pmmoni/pmMoniView");
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id){
		if( id != null && id > 0 ){
			PmaqiMoni pmaqiMoni = this.pmaqiMoniService.getPmaqiMoni(id);
			model.addAttribute("pmMoni", pmaqiMoni);
			
			model.addAttribute("monitorPointCode", pmaqiMoni.getMonitorPointCode());
		}else{
			PmaqiMoni pmaqiMoni = new PmaqiMoni();
			pmaqiMoni.setMonitorDate(DateTimeUtil.getCurrentDate());
			model.addAttribute("pmMoni", pmaqiMoni); 
			
		}
		List<MoniPoint> pointList = this.moniPointService.getMoniPointList(Constants.MONI_POINT_04);
		model.addAttribute("pointList", pointList);
		
		return new ModelAndView("/pmmoni/pmMoniEdit");
	}

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, PmaqiMoniModel pmaqiMoniModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		if(pageModel!=null){ 
			model.addAttribute("pageModel", pageModel);
			model.addAttribute("beginDate", pmaqiMoniModel.getBeginDate());
			model.addAttribute("endDate", pmaqiMoniModel.getEndDate());
		}
		return new ModelAndView("/pmmoni/pmMoniList");
	  }
	
	@RequestMapping({"/search"})
	@ResponseBody
	public ModelAndView search(Model model, Integer page, Integer pageSize, PmaqiMoniModel pmaqiMoniModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( pmaqiMoniModel.getBeginDate() )){ 
			params.put("beginDate", pmaqiMoniModel.getBeginDate() );
		}
		if(StringUtils.isNotEmpty( pmaqiMoniModel.getEndDate() )){ 
			params.put("endDate", pmaqiMoniModel.getEndDate() );
		}
		pageModel = this.pmaqiMoniService.queryForPageModel("PmaqiMoni", params, pageModel);
		List<PmaqiMoni> list = (List<PmaqiMoni>)pageModel.getResult();
		for(PmaqiMoni pmaqiMoni : list){
			pmaqiMoni.setMonitorPointName( this.moniPointService.getMoniPoint(pmaqiMoni.getMonitorPointCode()).getMonitorPointName() );
		}
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("beginDate", pmaqiMoniModel.getBeginDate());
		model.addAttribute("endDate", pmaqiMoniModel.getEndDate());
		
		return new ModelAndView("/pmmoni/pmMoniList");
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
