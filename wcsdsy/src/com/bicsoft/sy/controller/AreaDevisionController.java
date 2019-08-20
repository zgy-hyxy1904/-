package com.bicsoft.sy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bicsoft.sy.entity.AreaDevision;
import com.bicsoft.sy.model.ComboModel;
import com.bicsoft.sy.service.AreaDevisionService;
import com.bicsoft.sy.util.JsonResult;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/areaDevision/")
public class AreaDevisionController {
	private static final Logger log = LoggerFactory.getLogger(AreaDevisionController.class);
	
	@Autowired
	private AreaDevisionService areaDevisionService;
	
	@RequestMapping("/getAreaDevisions")
	@ResponseBody
	public JsonResult getAreaDevisions(String cityCode, String townCode, String countryCode){
		AreaDevision area = areaDevisionService.getAreaDevision(cityCode, townCode, countryCode);
		
		JSONArray jsonArr = new JSONArray();
		ComboModel model1 = new ComboModel();
		model1.setId(area.getTownCode());
		model1.setText(area.getTownName());
		ComboModel model2 = new ComboModel();
		model2.setId(area.getCountryCode());
		model2.setText(area.getCountryName());
		jsonArr.add(model1);
		jsonArr.add(model2);
		
		JsonResult js = new JsonResult(true);
		js.setData(jsonArr.toString());
		return js;
	}
	
	@RequestMapping("/getCitys")
	public ModelAndView getCitys(Model model){
		List<AreaDevision> areaDevisions = areaDevisionService.queryCitys();
		model.addAttribute("citys", areaDevisions);
		return new ModelAndView("/test/testArea");
	}
	
	@RequestMapping("/getTownsByCityCode")
	@ResponseBody
	public JsonResult getTownsByCityCode(String cityCode){
		List<AreaDevision> towns = (List<AreaDevision>)areaDevisionService.queryTownsByCityCode(cityCode);
		
		JSONArray jsonArr = new JSONArray();
		
		for(AreaDevision town : towns){
			ComboModel model = new ComboModel();
			model.setId(town.getTownCode());
			model.setText(town.getTownName());
			jsonArr.add(model);
		};
		
		JsonResult js = new JsonResult(true);
		js.setData(jsonArr.toString());
		return js;
	}
	
	@RequestMapping("/getCountrysByCityAndTownCode")
	@ResponseBody
	public JsonResult getCountrysByCityAndTownCode(String cityCode, String townCode){
		List<AreaDevision> countrys = (List<AreaDevision>)areaDevisionService.queryCountrysByCityAndTownCode(cityCode, townCode);
		
		JSONArray jsonArr = new JSONArray();
		
		for(AreaDevision country : countrys){
			ComboModel model = new ComboModel();
			model.setId(country.getCountryCode());
			model.setText(country.getCountryName());
			jsonArr.add(model);
		};
		
		JsonResult js = new JsonResult(true);
		js.setData(jsonArr.toString());
		return js;
	}
	
}
