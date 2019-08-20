package com.bicsoft.sy.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bicsoft.sy.model.ComboModel;
import com.bicsoft.sy.service.AirMoniService;
import com.bicsoft.sy.service.MoniPointService;
import com.bicsoft.sy.service.PmaqiMoniService;
import com.bicsoft.sy.service.SoilMoniService;
import com.bicsoft.sy.service.WaterMoniService;
import com.bicsoft.sy.util.JsonResult;

import net.sf.json.JSONArray;

/**
 * Gis地图Controller
 * @author WolfSoul
 * @date 2015-08-27
 */
@Controller
@RequestMapping("/gisMap/")
public class GisMapController {
	private static final Logger log = LoggerFactory.getLogger(GisMapController.class);

	@Autowired
	private AirMoniService airMoniService;
	@Autowired
	private WaterMoniService waterMoniService;
	@Autowired
	private SoilMoniService soilMoniService;
	@Autowired
	private PmaqiMoniService pmaqiMoniService;
	
	@RequestMapping("/view")
	@ResponseBody
	public ModelAndView view(Model model, Integer id){
		return new ModelAndView("/gis/gisView");
	}
	
	@RequestMapping("/getYearCodes")
	@ResponseBody
	public JsonResult getYearCodes(String moniBizType){
		List<String> yearCodes = null;
		if(moniBizType!=null && !"".equals(moniBizType) && "air".equals(moniBizType)){
			yearCodes = airMoniService.queryYears();
		}else if(moniBizType!=null && !"".equals(moniBizType) && "water".equals(moniBizType)){
			yearCodes = waterMoniService.queryYears();
		}else if(moniBizType!=null && !"".equals(moniBizType) && "soil".equals(moniBizType)){
			yearCodes = soilMoniService.queryYears();
		}else if(moniBizType!=null && !"".equals(moniBizType) && "pmaqi".equals(moniBizType)){
			yearCodes = pmaqiMoniService.queryYears();
		}else{
			yearCodes = new ArrayList<String>();
		}
		
		JSONArray jsonArr = new JSONArray();
		
		for(String yearCode : yearCodes){
			ComboModel model = new ComboModel();
			model.setId(yearCode);
			model.setText(yearCode);
			jsonArr.add(model);
		};
		
		JsonResult js = new JsonResult(true);
		js.setData(jsonArr.toString());
		return js;
	}
	
	@RequestMapping("/getDateCodes")
	@ResponseBody
	public JsonResult getDateCodes(String moniBizType,String yearCode){
		List<String> dateCodes = null;
		if(moniBizType!=null && !"".equals(moniBizType) && "air".equals(moniBizType)){
			dateCodes = airMoniService.queryDates(yearCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "water".equals(moniBizType)){
			dateCodes = waterMoniService.queryDates(yearCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "soil".equals(moniBizType)){
			dateCodes = soilMoniService.queryDates(yearCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "pmaqi".equals(moniBizType)){
			dateCodes = pmaqiMoniService.queryDates(yearCode);
		}else{
			dateCodes = new ArrayList<String>();
		}
		
		JSONArray jsonArr = new JSONArray();
		
		for(String dateCode : dateCodes){
			ComboModel model = new ComboModel();
			model.setId(dateCode);
			model.setText(dateCode.replace(yearCode+"-", ""));
			jsonArr.add(model);
		};
		
		JsonResult js = new JsonResult(true);
		js.setData(jsonArr.toString());
		return js;
	}

	
//	@RequestMapping("/getGisDatasByYear")
//	@ResponseBody
//	public JsonResult getGisDatasByYear(String moniBizType, String yearCode){
//		List<Map<String,String>> datas = null;
//		if(moniBizType!=null && !"".equals(moniBizType) && "air".equalsIgnoreCase(moniBizType)){
//			datas = airMoniService.queryGisDatas(yearCode);
//		}else if(moniBizType!=null && !"".equals(moniBizType) && "water".equalsIgnoreCase(moniBizType)){
//			datas = waterMoniService.queryGisDatas(yearCode);
//		}else if(moniBizType!=null && !"".equals(moniBizType) && "soil".equalsIgnoreCase(moniBizType)){
//			datas = soilMoniService.queryGisDatas(yearCode);
//		}else if(moniBizType!=null && !"".equals(moniBizType) && "pmaqi".equalsIgnoreCase(moniBizType)){
//			datas = pmaqiMoniService.queryGisDatas(yearCode);
//		}else{
//			datas = new ArrayList<Map<String,String>>();
//		}
//		
//		JSONArray jsonArr = new JSONArray();
//		
//		for(Map<String,String> data : datas){
//			jsonArr.add(data);
//		};
//		
//		JsonResult js = new JsonResult(true);
//		js.setData(jsonArr.toString());
//		return js;
//	}
	
	@RequestMapping("/getGisDatasByDate")
	@ResponseBody
	public JsonResult getGisDatasByDate(String moniBizType, String dateCode){
		List<Map<String,String>> datas = null;
		if(moniBizType!=null && !"".equals(moniBizType) && "air".equalsIgnoreCase(moniBizType)){
			datas = airMoniService.queryGisDatasByDate(dateCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "water".equalsIgnoreCase(moniBizType)){
			datas = waterMoniService.queryGisDatasByDate(dateCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "soil".equalsIgnoreCase(moniBizType)){
			datas = soilMoniService.queryGisDatasByDate(dateCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "pmaqi".equalsIgnoreCase(moniBizType)){
			datas = pmaqiMoniService.queryGisDatasByDate(dateCode);
		}else{
			datas = new ArrayList<Map<String,String>>();
		}
		
		JSONArray jsonArr = new JSONArray();
		
		for(Map<String,String> data : datas){
			jsonArr.add(data);
		};
		
		JsonResult js = new JsonResult(true);
		js.setData(jsonArr.toString());
		return js;
	}
	
	@RequestMapping("/getNearestGisDatas")
	@ResponseBody
	public JsonResult getNearestGisDatas(String moniBizType){
		List<Map<String,String>> datas = null;
		if(moniBizType!=null && !"".equals(moniBizType) && "air".equalsIgnoreCase(moniBizType)){
			datas = airMoniService.queryNearestGisDatas();
		}else if(moniBizType!=null && !"".equals(moniBizType) && "water".equalsIgnoreCase(moniBizType)){
			datas = waterMoniService.queryNearestGisDatas();
		}else if(moniBizType!=null && !"".equals(moniBizType) && "soil".equalsIgnoreCase(moniBizType)){
			datas = soilMoniService.queryNearestGisDatas();
		}else if(moniBizType!=null && !"".equals(moniBizType) && "pmaqi".equalsIgnoreCase(moniBizType)){
			datas = pmaqiMoniService.queryNearestGisDatas();
		}else{
			datas = new ArrayList<Map<String,String>>();
		}
		
		JSONArray jsonArr = new JSONArray();
		
		for(Map<String,String> data : datas){
			jsonArr.add(data);
		};
		
		JsonResult js = new JsonResult(true);
		js.setData(jsonArr.toString());
		return js;
	}
	
	/*
	@RequestMapping("/getMonthCodes")
	@ResponseBody
	public JsonResult getMonthCodes(String moniBizType, String yearCode){
		List<String> monthCodes = null;
		if(moniBizType!=null && !"".equals(moniBizType) && "air".equals(moniBizType)){
			monthCodes = airMoniService.queryMonths(yearCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "water".equals(moniBizType)){
			monthCodes = waterMoniService.queryMonths(yearCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "soil".equals(moniBizType)){
			monthCodes = soilMoniService.queryMonths(yearCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "pmaqi".equals(moniBizType)){
			monthCodes = pmaqiMoniService.queryMonths(yearCode);
		}else{
			monthCodes = new ArrayList<String>();
		}
		
		JSONArray jsonArr = new JSONArray();
		
		for(String monthCode : monthCodes){
			ComboModel model = new ComboModel();
			model.setId(monthCode);
			model.setText(monthCode);
			jsonArr.add(model);
		};
		
		JsonResult js = new JsonResult(true);
		js.setData(jsonArr.toString());
		return js;
	}
	
	@RequestMapping("/getDayCodes")
	@ResponseBody
	public JsonResult getDayCodes(String moniBizType, String yearCode, String monthCode){
		List<String> dayCodes = null;
		if(moniBizType!=null && !"".equals(moniBizType) && "air".equalsIgnoreCase(moniBizType)){
			dayCodes = airMoniService.queryDays(yearCode, monthCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "water".equalsIgnoreCase(moniBizType)){
			dayCodes = waterMoniService.queryDays(yearCode, monthCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "soil".equalsIgnoreCase(moniBizType)){
			dayCodes = soilMoniService.queryDays(yearCode, monthCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "pmaqi".equalsIgnoreCase(moniBizType)){
			dayCodes = pmaqiMoniService.queryDays(yearCode, monthCode);
		}else{
			dayCodes = new ArrayList<String>();
		}
		
		JSONArray jsonArr = new JSONArray();
		
		for(String dayCode : dayCodes){
			ComboModel model = new ComboModel();
			model.setId(dayCode);
			model.setText(dayCode);
			jsonArr.add(model);
		};
		
		JsonResult js = new JsonResult(true);
		js.setData(jsonArr.toString());
		return js;
	}*/
	
	/*
	@RequestMapping("/getGisDatasByYearMonth")
	@ResponseBody
	public JsonResult getGisDatasByYearMonth(String moniBizType, String yearCode, String monthCode){
		List<Map<String,String>> datas = null;
		if(moniBizType!=null && !"".equals(moniBizType) && "air".equalsIgnoreCase(moniBizType)){
			datas = airMoniService.queryGisDatas(yearCode, monthCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "water".equalsIgnoreCase(moniBizType)){
			datas = waterMoniService.queryGisDatas(yearCode, monthCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "soil".equalsIgnoreCase(moniBizType)){
			datas = soilMoniService.queryGisDatas(yearCode, monthCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "pmaqi".equalsIgnoreCase(moniBizType)){
			datas = pmaqiMoniService.queryGisDatas(yearCode, monthCode);
		}else{
			datas = new ArrayList<Map<String,String>>();
		}
		
		JSONArray jsonArr = new JSONArray();
		
		for(Map<String,String> data : datas){
			jsonArr.add(data);
		};
		
		JsonResult js = new JsonResult(true);
		js.setData(jsonArr.toString());
		return js;
	}
	
	@RequestMapping("/getGisDatasByYearMonthDay")
	@ResponseBody
	public JsonResult getGisDatasByYearMonthDay(String moniBizType, String yearCode, String monthCode, String dayCode){
		List<Map<String,String>> datas = null;
		if(moniBizType!=null && !"".equals(moniBizType) && "air".equalsIgnoreCase(moniBizType)){
			datas = airMoniService.queryGisDatas(yearCode, monthCode, dayCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "water".equalsIgnoreCase(moniBizType)){
			datas = waterMoniService.queryGisDatas(yearCode, monthCode, dayCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "soil".equalsIgnoreCase(moniBizType)){
			datas = soilMoniService.queryGisDatas(yearCode, monthCode, dayCode);
		}else if(moniBizType!=null && !"".equals(moniBizType) && "pmaqi".equalsIgnoreCase(moniBizType)){
			datas = pmaqiMoniService.queryGisDatas(yearCode, monthCode, dayCode);
		}else{
			datas = new ArrayList<Map<String,String>>();
		}
		
		JSONArray jsonArr = new JSONArray();
		
		for(Map<String,String> data : datas){
			jsonArr.add(data);
		};
		
		JsonResult js = new JsonResult(true);
		js.setData(jsonArr.toString());
		return js;
	}
	*/
	
}
