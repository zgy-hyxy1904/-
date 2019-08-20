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

import com.bicsoft.sy.entity.GraiEval;
import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.GraiEvalModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.GraiEvalService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.GetSession;
import com.bicsoft.sy.util.JsonResult;
import com.bicsoft.sy.util.StringUtil;

/**
 * 粮食评估
 * @author 创新中软
 * @date 2015-08-17
 */
@Controller
@RequestMapping("/graiEval/")
public class GraiEvalController {
	private static final Logger log = LoggerFactory.getLogger(GraiEvalController.class);
	
	@Autowired
	private GraiEvalService graiEvalService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.graiEvalService.logicDelete(GraiEval.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	//新增维护时按年度和品种进行校验,年度和品种不能重复
	@RequestMapping("/validateYearAndSeed")
	@ResponseBody
	public JsonResult validateYearAndSeed(HttpServletRequest request, String year, String seedCode){
		GraiEval eval = this.graiEvalService.getGraiEval(year, seedCode);
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
	public JsonResult save(@RequestBody GraiEvalModel graiEvalModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		graiEvalModel.setCreateDate(baseModel.getCreateDate());
		graiEvalModel.setCreateUserId(baseModel.getCreateUserId());
		graiEvalModel.setUpdateDate(baseModel.getUpdateDate());
		graiEvalModel.setUpdateUserId(baseModel.getUpdateUserId());
		this.graiEvalService.save(graiEvalModel);
		
		return new JsonResult(true);
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public JsonResult view(Model model, Integer id){
		this.graiEvalService.delete(id);
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id){
		if( id != null && id > 0 ){
			GraiEval graiEval = this.graiEvalService.getGraiEval(id);
			model.addAttribute("graiEval", graiEval);
			model.addAttribute("readOnlyFlag", 1);
			model.addAttribute("year", graiEval.getYear());
		}else{
			model.addAttribute("readOnlyFlag", 0);
		}
		return new ModelAndView("/graieval/graiEvalEdit");
	}

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, GraiEvalModel graiEvalModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		if(!StringUtil.isNullOrEmpty(graiEvalModel.getYear())){
			Map<String, Object> params = new HashMap<String, Object>();
			if(StringUtils.isNotEmpty( graiEvalModel.getYear() )){ 
				params.put("year", graiEvalModel.getYear() );
			}else{
				graiEvalModel.setYear( DateTimeUtil.getCurrentYear() );
				params.put("year", graiEvalModel.getYear() );
			}
			pageModel = this.graiEvalService.queryForPageModel("GraiEval", params, pageModel);
		}else{
			graiEvalModel.setYear( DateTimeUtil.getCurrentYear() );
		}
		
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("year", graiEvalModel.getYear());
		
		return new ModelAndView("/graieval/graiEvalList");
	  }
	
	@RequestMapping({"/listquery"})
	@ResponseBody
	public ModelAndView listquery(HttpServletRequest request, Model model, Integer page, Integer pageSize,GraiEvalModel graiEvalModel, String companyCode, String year){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		
		if( StringUtils.isNotEmpty( graiEvalModel.getCompanyCode() ) && 
				StringUtils.isNotEmpty( graiEvalModel.getYear() )){
			Map<String, Object> params = new HashMap<String, Object>();
			pageModel = this.graiEvalService.queryForPageModel("GraiEval", params, pageModel);
			float geneLandsArea = graiEvalService.getGeneLandsArea(companyCode, year);
			float sepcLandsArea = graiEvalService.getSpecLandsArea(companyCode, year);
			float sumAreas = geneLandsArea + sepcLandsArea;
			GraiEval gEval = graiEvalService.getGraiEvalByYear(year);
			if( gEval.getMilledriceRate() == null ){ 
				gEval.setMilledriceRate(0.0f);
			}
			if( gEval.getMinYield() == null ){
				gEval.setMinYield( 0.0f );
			}
			if( gEval.getMaxYield() == null ){
				gEval.setMaxYield( 0.0f );
			}
			Float minYield = sumAreas*gEval.getMinYield()*gEval.getMilledriceRate();
			Float maxYield = sumAreas*gEval.getMaxYield()*gEval.getMilledriceRate();
			
			model.addAttribute("geneLandsArea", geneLandsArea);
			
			model.addAttribute("sepcLandsArea", sepcLandsArea);
			
			model.addAttribute("sumAreas", sumAreas);
	
			model.addAttribute("minYield", minYield);
			
			model.addAttribute("maxYield", maxYield);
			
			int total = this.graiEvalService.queryLandInfosCount(graiEvalModel.getCompanyCode(), graiEvalModel.getYear());
			pageModel.setTotalCount(total);
			List<Map<String,Object>> list = this.graiEvalService.queryLandInfos(graiEvalModel.getCompanyCode(),
					                                                           graiEvalModel.getYear(),
					                                                           (page- 1) * pageSize,
					                                                           pageSize);
			pageModel.setResult(list);
			model.addAttribute("pageModel", pageModel);
		}
		
		/*
		 * 这个业务中涉及的购种业务需求取消了,购种信息不管了
		 * service中的queryLandInfos(String companyCode, String yearCode)查询土地信息的
		 * 返回的是List<Map<String,Object>>;
		 * 	map.get("contractorName");		承包人姓名
		 * 	map.get("homeAddress");			住址
		 * 	map.get("plowlandName");		土地类别名称(已取消)
		 * 	map.get("landAddress");			地块位址(已取消)
		 * 	map.get("avchiveDate");			备案日期
		 * 	map.get("archiveAcreage");		备案面积(亩)
		 */
		
		User user = GetSession.getSessionEntity(request);
		if(user.getUserType().equals(Constants.USER_TYPE_COMPANY)){
			companyCode = user.getCompanyCode();
		}
		
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("graiEvalModel", graiEvalModel);
		model.addAttribute("year", year);
		model.addAttribute("companyCode", companyCode);
		
		return new ModelAndView("/graieval/graiEvalListQuery");
	 }
}
