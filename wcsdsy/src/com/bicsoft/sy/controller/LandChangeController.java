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

import com.bicsoft.sy.entity.GeneLandDetail;
import com.bicsoft.sy.entity.GeneLandReg;
import com.bicsoft.sy.entity.GeneLandRegD;
import com.bicsoft.sy.entity.LandRegChange;
import com.bicsoft.sy.entity.SpecLandDetail;
import com.bicsoft.sy.entity.SpecLandReg;
import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.LandRegChangeModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.FileManagerService;
import com.bicsoft.sy.service.GeneLandDetailService;
import com.bicsoft.sy.service.GeneLandRegDService;
import com.bicsoft.sy.service.GeneLandRegService;
import com.bicsoft.sy.service.GraiEvalService;
import com.bicsoft.sy.service.LandRegChangeService;
import com.bicsoft.sy.service.ServialNumService;
import com.bicsoft.sy.service.SpecLandDetailService;
import com.bicsoft.sy.service.SpecLandRegService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.GetSession;
import com.bicsoft.sy.util.JsonResult;

/**
 * 土地变更
 * @author 
 * @date 2015-08-26
 */
@Controller
@RequestMapping("/landChange")
public class LandChangeController {
	private static final Logger log = LoggerFactory.getLogger(LandChangeController.class);
	
	@Autowired
	private LandRegChangeService landRegChangeService;
	
	@Autowired
	private GeneLandRegService geneLandRegService;
	
	@Autowired
	private GeneLandRegDService geneLandRegDService;
	
	@Autowired
	private GeneLandDetailService geneLandDetailService;
	
	@Autowired
	private SpecLandRegService specLandRegService;
	
	@Autowired
	private SpecLandDetailService specLandDetailService;
	
	@Autowired
	private GraiEvalService graiEvalService;
	
	@Autowired
	private FileManagerService fileManagerService;
	
	@Autowired
	private ServialNumService servialNumService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");                            
		for (String id : idArray) {                                                   
			this.landRegChangeService.logicDelete(LandRegChange.class, Integer.parseInt(id));                                  
		}                                                    
		JsonResult jr = new JsonResult(true);                                    
		return jr;                                                           
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody LandRegChangeModel landRegChangeModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);       
		landRegChangeModel.setCreateDate(baseModel.getCreateDate());                                 
		landRegChangeModel.setCreateUserId(baseModel.getCreateUserId());                                  
		landRegChangeModel.setUpdateDate(baseModel.getUpdateDate());                                  
		landRegChangeModel.setUpdateUserId(baseModel.getUpdateUserId());                              
		landRegChangeModel.setApplicantDate(baseModel.getCreateDate());
		if(landRegChangeModel.getSpecRegistType()!=null&&landRegChangeModel.getSpecRegistType().equals("on")){
			landRegChangeModel.setSpecRegistType("1");
		}else{
			landRegChangeModel.setSpecRegistType("0");
		}
		if(landRegChangeModel.getGeneRegistType()!=null&&landRegChangeModel.getGeneRegistType().equals("on")){
			landRegChangeModel.setGeneRegistType("1");
		}else{
			landRegChangeModel.setGeneRegistType("0");
		}
		this.landRegChangeService.save(landRegChangeModel);
		CommonUtil.saveMfile(fileManagerService, "20", landRegChangeModel.getId() + "", request);
		                                                                                            
		return new JsonResult(true);                     
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public ModelAndView view(Model model, Integer id, Integer gotoPage, Integer page, Integer pageSize, LandRegChangeModel landRegChangeModel){
		model.addAttribute("year", landRegChangeModel.getYear() );
		model.addAttribute("companyCode", landRegChangeModel.getCompanyCode() );
		model.addAttribute("beginDate", landRegChangeModel.getBeginDate());
		model.addAttribute("endDate", landRegChangeModel.getEndDate());
		model.addAttribute("status", landRegChangeModel.getStatus());
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("gotoPage", gotoPage);
		
		if( id != null && id > 0 ){
			LandRegChange landChange = this.landRegChangeService.getLandRegChange(id);
			model.addAttribute("landRegChange", landChange);
		}else{ 
			
			SpecLandReg specLandReg = new SpecLandReg();
			specLandReg.setOperatorDate( DateTimeUtil.getCurrentDate() );
			
			model.addAttribute("specLandReg", specLandReg);
		}
		
		return new ModelAndView("/landchange/landChangeView");                                                                            
	}
	@RequestMapping("/operatorFlow")
	@ResponseBody
	public ModelAndView operatorFlow(Model model, Integer id){
		
		return new ModelAndView("/genelandreg/geneLandRegFlowList");
	}
	
	@RequestMapping("/submitAudit")
	@ResponseBody
	public JsonResult submitAudit(HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		String[] idArray = request.getParameterValues("ids[]");

		this.landRegChangeService.updateStatus( idArray, baseModel );

		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	/**
	 * 添加、编辑、审核
	 * @param model
	 * @param id
	 * @param flag: audit审核;
	 * @return
	 */
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id, Integer page, Integer pageSize, LandRegChangeModel landRegChangeModel, String flag, String retFlag, HttpServletRequest request){
		String bizType = "04";
		CommonUtil.removeTmpFileList( bizType, request );   //清空未保存文件
		
		if( id != null && id > 0 ){
			//主表信息
			LandRegChange landRegChange = this.landRegChangeService.getLandRegChange(id);
			model.addAttribute("landRegChange", landRegChange);
			model.addAttribute("idType", landRegChange.getIdType());
			model.addAttribute("companyCode", landRegChange.getCompanyCode());
			model.addAttribute("companyCode1", landRegChange.getCompanyCode());
			model.addAttribute("readOnlyFlag", 1);
			model.addAttribute("year", landRegChange.getYear());
		}else{ 
			LandRegChange landRegChange = new LandRegChange();
			landRegChange.setApplyBatchNo( servialNumService.getServialNum(Constants.BIZ_TYPE_BG));
			landRegChange.setApplicantDate(DateTimeUtil.getCurrentDate());
			model.addAttribute("landRegChange", landRegChange);
			User user = GetSession.getSessionEntity(request);
			model.addAttribute("companyCode", user.getCompanyCode());
			model.addAttribute("companyCode1", user.getCompanyCode());
			model.addAttribute("applicantDate", DateTimeUtil.getCurrentDateByPattern("yyyy-MM-dd"));
			model.addAttribute("idType", "01");   //证件类型默认为身份证
		}
		//根据当前年度和企业名称查询
		if( StringUtils.isEmpty( flag ) ){
			flag = "edit";
		}
		if( id != null ){
			model.addAttribute("id", id);
		}
		model.addAttribute("flag", flag);
		model.addAttribute("retFlag", retFlag);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("landRegChangeModel", landRegChangeModel);
		
		if(flag != null && "audit".equals( flag ) ){
			return new ModelAndView("/landchange/landChangeAudit");
		}else{
			return new ModelAndView("/landchange/landChangeEdit");
		}
	}

	/**
	 * 驳回
	 * @param model
	 * @param id
	 * @param 
	 * @return
	 */
	@RequestMapping("/auditBhInput")
	@ResponseBody
	public ModelAndView auditBhInput(Model model, Integer id, HttpServletRequest request){
		if( id != null && id > 0 ){
			LandRegChange landRegChange  = this.landRegChangeService.getLandRegChange(id);
			
			BaseModel baseModel = CommonUtil.getBaseModel(request);
			landRegChange.setUpdateDate(baseModel.getUpdateDate());
			landRegChange.setUpdateUserId(baseModel.getUpdateUserId());
			landRegChange.setAuditor( baseModel.getUpdateUserId() );
			landRegChange.setAuditDate( baseModel.getUpdateDate() );
			
			//this.specLandRegService.save(specLandRegModel);
		}
		model.addAttribute("id", id);
		return new ModelAndView("/landchange/landChangeEditReason");
	}
	
	@RequestMapping("/saveReason")
	@ResponseBody
	public JsonResult saveReason(@RequestBody LandRegChangeModel landRegChangeModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		landRegChangeModel.setUpdateDate(baseModel.getUpdateDate());
		landRegChangeModel.setUpdateUserId(baseModel.getUpdateUserId());
		landRegChangeModel.setUpdateUserName(baseModel.getUpdateUserName());
		
		this.landRegChangeService.saveReason(landRegChangeModel);
		 LandRegChange landRegChange = this.landRegChangeService.getLandRegChange(landRegChangeModel.getId());
		//审核通过，业务数据逻辑删除
		if(landRegChange.getStatus().equals("03")){
			if(landRegChange.getSpecRegistType()!=null&&landRegChange.getSpecRegistType().equals("1")){
				List<SpecLandReg> results =this.specLandRegService.querySpecLandRegInfoList(landRegChange.getCompanyCode(), landRegChange.getYear(), landRegChange.getIdType(), landRegChange.getContractorID());
			    if(results!=null){
					for(SpecLandReg temp :results){
						this.specLandRegService.logicDelete(SpecLandReg.class,temp.getId());
						 List<SpecLandDetail> result = this.specLandDetailService.getSpecLandDetailList(temp.getId());
						 for(SpecLandDetail temp1 :result){
							 this.specLandDetailService.logicDelete(SpecLandDetail.class,temp1.getId());
						 }
				    }
			    }
			}
			if(landRegChange.getGeneRegistType()!=null&&landRegChange.getGeneRegistType().equals("1")){
				List<Map<String,Object>> results = this.geneLandRegDService.queryGeneLandRegDInfoList(landRegChange.getCompanyCode(), landRegChange.getYear(), landRegChange.getIdType(), landRegChange.getContractorID());
                
				//List<GeneLandRegD> results =this.geneLandRegDService.queryGeneLandRegDInfoList(landRegChange.getCompanyCode(), landRegChange.getYear(), landRegChange.getIdType(), landRegChange.getContractorID());
			    if(results!=null){
					for(Map<String,Object> temp :results){
						this.geneLandRegDService.logicDelete(GeneLandRegD.class,Integer.parseInt(temp.get("id").toString()));
						
						GeneLandReg result = this.geneLandRegService.getGeneLandReg(Integer.parseInt(temp.get("hid").toString()));
						this.geneLandRegService.logicDelete(GeneLandReg.class, result.getId());
						
						List<GeneLandDetail> resultList = this.geneLandDetailService.getGeneLandDetailList(Integer.parseInt(temp.get("id").toString())+"");
						for(GeneLandDetail temp1 :resultList){
							this.geneLandDetailService.logicDelete(GeneLandDetail.class, temp1.getId());
						}
				    }
			    }
			}
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, LandRegChangeModel landRegChangeModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		if(landRegChangeModel!=null){
			model.addAttribute("pageModel", pageModel);
			model.addAttribute("companyCode", landRegChangeModel.getCompanyCode());
			model.addAttribute("year", landRegChangeModel.getYear());
			model.addAttribute("beginDate", landRegChangeModel.getBeginDate());
			model.addAttribute("endDate", landRegChangeModel.getEndDate());
			model.addAttribute("status", landRegChangeModel.getStatus());
			
		}
		return new ModelAndView("/landchange/landChangeList");
	  }
	
	@RequestMapping({"/listSearch"})
	@ResponseBody
	public ModelAndView listSearch(Model model, Integer page, Integer pageSize, LandRegChangeModel landRegChangeModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( landRegChangeModel.getYear() )){ 
			params.put("year", landRegChangeModel.getYear() );
		}
		if(StringUtils.isNotEmpty( landRegChangeModel.getCompanyCode() )){ 
			params.put("companyCode", landRegChangeModel.getCompanyCode() );
		}
		if(StringUtils.isNotEmpty( landRegChangeModel.getBeginDate() )){ 
			params.put("beginDate", landRegChangeModel.getBeginDate() );
		}
		if(StringUtils.isNotEmpty( landRegChangeModel.getEndDate() )){ 
			params.put("endDate", landRegChangeModel.getEndDate() );
		}
		if(StringUtils.isNotEmpty( landRegChangeModel.getStatus() )){ 
			params.put("status", landRegChangeModel.getStatus() );
		}
		pageModel = this.landRegChangeService.queryForPageModel("LandRegChange", params, pageModel);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("companyCode", landRegChangeModel.getCompanyCode());
		model.addAttribute("year", landRegChangeModel.getYear());
		model.addAttribute("beginDate", landRegChangeModel.getBeginDate());
		model.addAttribute("endDate", landRegChangeModel.getEndDate());
		model.addAttribute("status", landRegChangeModel.getStatus());
		
		return new ModelAndView("/landchange/landChangeList");
	 }
	
	@RequestMapping({"/listquery"})
	@ResponseBody
	public ModelAndView listquery(Model model, Integer page, Integer pageSize, LandRegChangeModel landRegChangeModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		if(landRegChangeModel!=null){
			model.addAttribute("pageModel", pageModel);
			model.addAttribute("year", landRegChangeModel.getYear());
			model.addAttribute("beginDate", landRegChangeModel.getBeginDate());
			model.addAttribute("endDate", landRegChangeModel.getEndDate());
			model.addAttribute("status", landRegChangeModel.getStatus());
			
		}

		return new ModelAndView("/landchange/landChangeListQuery");
	 }
	
	@RequestMapping({"/listQuerySearch"})
	@ResponseBody
	public ModelAndView listQuerySearch(Model model, Integer page, Integer pageSize, LandRegChangeModel landRegChangeModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( landRegChangeModel.getYear() )){ 
			params.put("year", landRegChangeModel.getYear() );
		}
		if(StringUtils.isNotEmpty( landRegChangeModel.getCompanyCode() )){ 
			params.put("companyCode", landRegChangeModel.getCompanyCode() );
		}
		if(StringUtils.isNotEmpty( landRegChangeModel.getBeginDate() )){ 
			params.put("beginDate", landRegChangeModel.getBeginDate() );
		}
		if(StringUtils.isNotEmpty( landRegChangeModel.getEndDate() )){ 
			params.put("endDate", landRegChangeModel.getEndDate() );
		}
		if(StringUtils.isNotEmpty( landRegChangeModel.getStatus() )){ 
			params.put("status", landRegChangeModel.getStatus() );
		}
		pageModel = this.landRegChangeService.queryForPageModel("LandRegChange", params, pageModel);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("companyCode", landRegChangeModel.getCompanyCode());
		model.addAttribute("year", landRegChangeModel.getYear());
		model.addAttribute("beginDate", landRegChangeModel.getBeginDate());
		model.addAttribute("endDate", landRegChangeModel.getEndDate());
		model.addAttribute("status", landRegChangeModel.getStatus());
		
		return new ModelAndView("/landchange/landChangeListQuery");
	 }	

	@RequestMapping("/queryLandRegInfosCount")
	@ResponseBody
	public JsonResult queryGeneLandRegInfosCount(String companyCode, String yearCode , String idType, String contractorID){
		int geneResult = this.geneLandRegService.queryGeneLandRegInfosCount(yearCode , idType, contractorID);
		int specResult = this.specLandRegService.querySpecLandRegInfosCount(yearCode , idType, contractorID);
		int grainResult = this.graiEvalService.queryGrainRegInfoListCount( yearCode ,  contractorID);
		Map<String, String> data = new HashMap<String, String>();
		data.put("geneResultCnt", geneResult+"");
		data.put("specResultCnt", specResult+"");
		data.put("grainResultCnt", grainResult+"");
		JsonResult js = new JsonResult(true);
		js.setData(data);
		return js;
	}
	
}
