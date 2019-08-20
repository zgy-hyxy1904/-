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

import com.bicsoft.sy.entity.ProvEval;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.ProvEvalModel;
import com.bicsoft.sy.service.ProvEvalService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.JsonResult;
import com.bicsoft.sy.util.StringUtil;

/**
 * 种源评估
 * @author 创新中软
 * @date 2015-08-17
 */
@Controller
@RequestMapping("/provEval/")
public class ProvEvalController {
	private static final Logger log = LoggerFactory.getLogger(InputRegController.class);
	
	@Autowired
	private ProvEvalService provEvalService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.provEvalService.logicDelete(ProvEval.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	//新增维护时按年度和品种进行校验,年度和品种不能重复
	@RequestMapping("/validateYearAndSeed")
	@ResponseBody
	public JsonResult validateYearAndSeed(HttpServletRequest request, String year, String seedCode){
		ProvEval eval = this.provEvalService.getProvEval(year, seedCode);
		Map<String, String> data = new HashMap<String, String>();
		JsonResult jr = new JsonResult(true);
		if( eval != null ){
			data.put("flag", "1");
		}else{
			data.put("flag", "0");
		}
		jr.setData( data );
		
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody ProvEvalModel provEvalModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		provEvalModel.setCreateDate(baseModel.getCreateDate());
		provEvalModel.setCreateUserId(baseModel.getCreateUserId());
		provEvalModel.setUpdateDate(baseModel.getUpdateDate());
		provEvalModel.setUpdateUserId(baseModel.getUpdateUserId());
		this.provEvalService.save(provEvalModel);
		
		return new JsonResult(true);
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public JsonResult view(Model model, Integer id){
		this.provEvalService.delete(id);
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id){
		if( id != null && id > 0 ){
			ProvEval provEval = this.provEvalService.getProvEval(id);
			model.addAttribute("provEval", provEval);
			model.addAttribute("readOnlyFlag", 1);
			model.addAttribute("year", provEval.getYear());
		}else{
			model.addAttribute("readOnlyFlag", 0);
		}
		return new ModelAndView("/proveval/provEvalEdit");
	}

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, ProvEvalModel provEvalModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		if(!StringUtil.isNullOrEmpty(provEvalModel.getYear())){
			Map<String, Object> params = new HashMap<String, Object>();
			if(StringUtils.isNotEmpty( provEvalModel.getYear() )){ 
				params.put("year", provEvalModel.getYear() );
			}else{
				provEvalModel.setYear( DateTimeUtil.getCurrentYear() );
				params.put("year", provEvalModel.getYear() );
			}
			pageModel = this.provEvalService.queryForPageModel("ProvEval", params, pageModel);
		}else{
			provEvalModel.setYear( DateTimeUtil.getCurrentYear() );
		}
		
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("provEvalModel", provEvalModel);
		model.addAttribute("year", provEvalModel.getYear());
		
		return new ModelAndView("/proveval/provEvalList");
	}
	
	@RequestMapping({"/listquery"})
	@ResponseBody
	public ModelAndView listquery(Model model, Integer page, Integer pageSize,ProvEvalModel provEvalModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		if( StringUtils.isNotEmpty( provEvalModel.getCompanyCode() ) && 
				StringUtils.isNotEmpty( provEvalModel.getYear() )){
			Map<String, Object> params = new HashMap<String, Object>();
			pageModel = this.provEvalService.queryForPageModel("ProvEval", params, pageModel);
			
			float geneLandsArea = provEvalService.getGeneLandsArea(provEvalModel.getCompanyCode(), provEvalModel.getYear());
			float sepcLandsArea = provEvalService.getSpecLandsArea(provEvalModel.getCompanyCode(), provEvalModel.getYear());
			float sumAreas = geneLandsArea + sepcLandsArea;
			
			ProvEval pEval = provEvalService.getProvEvalByYear(provEvalModel.getYear());
			
			if( pEval.getMaxYield() == null ){
				pEval.setMaxYield(0.0f);
			}
			if( pEval.getMinYield() == null ){
				pEval.setMinYield(0.0f);
			}
			float minYield = sumAreas*pEval.getMinYield();
			float maxYield = sumAreas*pEval.getMaxYield();
			
			model.addAttribute("geneLandsArea", geneLandsArea);
			model.addAttribute("sepcLandsArea", sepcLandsArea);
			model.addAttribute("sumAreas", sumAreas);
			model.addAttribute("minYield", minYield);
			model.addAttribute("maxYield", maxYield);
			
			int total = this.provEvalService.queryLandInfosCount(provEvalModel.getCompanyCode(),provEvalModel.getYear());
			pageModel.setTotalCount(total);
			
			List<Map<String, Object>>  list = this.provEvalService.queryLandInfos(provEvalModel.getCompanyCode(),
					                                                              provEvalModel.getYear(),
					                                                              (page- 1) * pageSize,
					                                                              pageSize);
			pageModel.setResult(list);
			model.addAttribute("pageModel", pageModel);
		}
		/*
		 * service中的queryLandInfos(String companyCode, String yearCode)查询土地信息的
		 * 返回的是List<Map<String,Object>>;
		 * 	map.get("contractorName");		承包人姓名
		 * 	map.get("homeAddress");			住址
		 * 	[已取消]map.get("plowlandName");	土地类别名称
		 * 	[已取消]map.get("landAddress");	地块位址
		 *  map.get("avchiveDate");			备案日期
		 * 	map.get("archiveAcreage");		备案面积(亩)
		 */
		model.addAttribute("provEvalModel", provEvalModel);
		model.addAttribute("year", provEvalModel.getYear());
		model.addAttribute("companyCode", provEvalModel.getCompanyCode());
		
		return new ModelAndView("/proveval/provEvalListQuery");
	 }
}
