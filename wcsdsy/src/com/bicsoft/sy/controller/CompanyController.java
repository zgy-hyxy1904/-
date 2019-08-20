package com.bicsoft.sy.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.bicsoft.sy.entity.Company;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.CompanyModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.BlackListService;
import com.bicsoft.sy.service.CompanyService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * 企业主档维护
 * @author 高华
 * @date 2015-08-28
 */

@Controller
@RequestMapping("/company/")
public class CompanyController {
	private static final Logger log = LoggerFactory.getLogger(CompanyController.class);
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	BlackListService blackListService;
	
	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, String companyCode, String companyName, String companyType,  String flag)
	{
		Date date = new Date();
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		if(flag != null && flag.equals("2")){
			pageModel = this.companyService.queryForPageModel(pageModel, companyCode, companyName, companyType);
			for(Map map : (List<Map>)pageModel.getResult()){
				String cpCode = (String)map.get("CompanyCode");
				BlackList blackList = blackListService.getBlackListByCompanyCod(companyCode);
				String blackFalg = "01";
				if(blackList != null && blackList.getBlackListEndDate() != null && blackList.getBlackListEndDate().getTime() > date.getTime()){
					blackFalg = "02";
				}
				map.put("Black", blackFalg);
			}
		}
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("companyCode", companyCode);
		model.addAttribute("companyName", companyName);
		model.addAttribute("companyType", companyType);
		return new ModelAndView("company/companyList");
	 }
	
	@RequestMapping({"/addCompanyInit"})
	@ResponseBody
	public ModelAndView list(Model model){
		return new ModelAndView("/company/addCompany");
	}
	
	@RequestMapping({"/addCompany"})
	@ResponseBody
	public JsonResult addCompany(@RequestBody CompanyModel companyModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		companyModel.setCreateDate(baseModel.getCreateDate());
		companyModel.setCreateUserId(baseModel.getCreateUserId());
		companyModel.setUpdateDate(baseModel.getUpdateDate());
		companyModel.setUpdateUserId(baseModel.getUpdateUserId());
		this.companyService.save(companyModel);
		return new JsonResult(true);
	}
	
	@RequestMapping({"/editCompanyInit"})
	@ResponseBody
	public ModelAndView editCompanyInit(Model model, Integer id){
		Company company = this.companyService.getCompany(id);
		model.addAttribute("company", company);
		return new ModelAndView("/company/editCompany");
	}
	
	@RequestMapping({"/editCompany"})
	@ResponseBody
	public JsonResult editCompany(@RequestBody CompanyModel companyModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		companyModel.setCreateDate(baseModel.getCreateDate());
		companyModel.setCreateUserId(baseModel.getCreateUserId());
		companyModel.setUpdateDate(baseModel.getUpdateDate());
		companyModel.setUpdateUserId(baseModel.getUpdateUserId());
		 this.companyService.save(companyModel);
		return new JsonResult(true);
	}
	
	@RequestMapping({"/detail"})
	@ResponseBody
	public ModelAndView detail(Model model, Integer id){
		Company company = this.companyService.getCompany(id);
		model.addAttribute("company", company);
		return new ModelAndView("/company/viewCompany");
	}
	
	@RequestMapping({"/delete"})
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		for (String id : idArray) {
			Company company = this.companyService.getCompany(id);
			if(company != null){
				company.setDeleteFlag("Y");
				company.setUpdateUserId(baseModel.getUpdateUserId());
				company.setUpdateDate(baseModel.getUpdateDate());
				this.companyService.save(company);
			}
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
}
