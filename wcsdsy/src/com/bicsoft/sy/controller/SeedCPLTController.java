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

import com.bicsoft.sy.entity.BlackList;
import com.bicsoft.sy.entity.CompanyCPLT;
import com.bicsoft.sy.entity.ReCallRecord;
import com.bicsoft.sy.entity.SeedCPLT;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.GridModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SeedCPLTModel;
import com.bicsoft.sy.service.BlackListDService;
import com.bicsoft.sy.service.BlackListService;
import com.bicsoft.sy.service.FileManagerService;
import com.bicsoft.sy.service.ReCallRecordService;
import com.bicsoft.sy.service.SeedCPLTService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * 种子投诉
 * @author 创新中软
 * @date 2015-08-26
 */
@Controller
@RequestMapping("/seedCPLT/")
public class SeedCPLTController {
	private static final Logger log = LoggerFactory.getLogger(BlackListController.class);
	
	@Autowired
	private SeedCPLTService seedCPLTService;
	@Autowired
	private BlackListDService blackListDService;
	
	@Autowired
	private BlackListService blackListService;
	
	@Autowired
	private ReCallRecordService reCallRecordService;
	@Autowired
	private FileManagerService fileManagerService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(Model model, HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		JsonResult jr = new JsonResult(true);
		boolean dealflag = false;
		Map<String, String> data = new HashMap<String, String>();
		for (String id : idArray) {
			    SeedCPLT seedCPLT = this.seedCPLTService.getSeedCPLT(Integer.parseInt(id));
				seedCPLT.setSettleDate( DateTimeUtil.getCurrentDate() );
				model.addAttribute("seedCPLT", seedCPLT);
				if(seedCPLT.getSettleStatus().equals("02")){
					dealflag =true;
				}
		}
		
		if(!dealflag){
			for (String id : idArray) {
				this.seedCPLTService.logicDelete(SeedCPLT.class, Integer.parseInt(id));
			}
			data.put("status", "01");
		}else{
			data.put("status", "02");
		}
		jr.setData( data );
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody SeedCPLTModel seedCPLTModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		seedCPLTModel.setCreateDate(baseModel.getCreateDate());
		seedCPLTModel.setCreateUserId(baseModel.getCreateUserId());
		seedCPLTModel.setUpdateDate(baseModel.getUpdateDate());
		seedCPLTModel.setUpdateUserId(baseModel.getUpdateUserId());
		this.seedCPLTService.save(seedCPLTModel);
		CommonUtil.saveMfile(fileManagerService, "19", seedCPLTModel.getId() + "", request);
		
		JsonResult jr = new JsonResult(true);
		Map<String, String> data = new HashMap<String, String>();
		data.put("id", seedCPLTModel.getId()+"");
		jr.setData( data );
		
		return jr;
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public ModelAndView view(Model model, Integer id){
		if( id != null && id > 0 ){
			SeedCPLT seedCPLT = this.seedCPLTService.getSeedCPLT(id);
			model.addAttribute("seedCPLT", seedCPLT);
		}else{
			SeedCPLT seedCPLT = new SeedCPLT();
			seedCPLT.setComplaintDate( DateTimeUtil.getCurrentDate() );
			model.addAttribute("seedCPLT", seedCPLT);
		}
		
		return new ModelAndView("/seedcplt/seedCPLTView");
	}
	
	@RequestMapping("/viewInfo")
	@ResponseBody
	public ModelAndView viewInfo(Model model, Integer id){
		if( id != null && id > 0 ){
			SeedCPLT seedCPLT = this.seedCPLTService.getSeedCPLT(id);
			
			StringBuffer processMode = new StringBuffer("");
			if( seedCPLT.getProcessMode() != null && seedCPLT.getProcessMode().contains("01") ){
				if( processMode.length() > 0 ){
					processMode.append(",");
				}
				processMode.append("警告");
			}
			if( seedCPLT.getProcessMode() != null && seedCPLT.getProcessMode().contains("02") ){
				if( processMode.length() > 0 ){
					processMode.append(",");
				}
				processMode.append("责令停产停业");
			}
			if( seedCPLT.getProcessMode() != null && seedCPLT.getProcessMode().contains("03") ){
				if( processMode.length() > 0 ){
					processMode.append(",");
				}
				processMode.append("罚款");
			}
			if( seedCPLT.getProcessMode() != null && seedCPLT.getProcessMode().contains("04") ){
				if( processMode.length() > 0 ){
					processMode.append(",");
				}
				processMode.append("暂扣或者吊销许可证、暂扣或者吊销执照");
			}
			if( seedCPLT.getProcessMode() != null && seedCPLT.getProcessMode().contains("05") ){
				if( processMode.length() > 0 ){
					processMode.append(",");
				}
				processMode.append("其它");
			}
			seedCPLT.setProcessMode( processMode.toString() );
			
			String settleStatus = "";
			if( seedCPLT.getSettleStatus() != null && seedCPLT.getSettleStatus().contains("01") ){
				settleStatus = "未处理";
			}
			if( seedCPLT.getSettleStatus() != null && seedCPLT.getSettleStatus().contains("02") ){
				settleStatus = "已处理";
			}
			seedCPLT.setSettleStatus( settleStatus );
			
			model.addAttribute("seedCPLT", seedCPLT);
		}else{
			SeedCPLT seedCPLT = new SeedCPLT();
			seedCPLT.setComplaintDate( DateTimeUtil.getCurrentDate() );
			model.addAttribute("seedCPLT", seedCPLT);
		}
		return new ModelAndView("/seedcplt/seedCPLTViewInfo");
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id){
		if( id != null && id > 0 ){
			SeedCPLT seedCPLT = this.seedCPLTService.getSeedCPLT(id);
			model.addAttribute("seedCPLT", seedCPLT);
		}else{
			SeedCPLT seedCPLT = new SeedCPLT();
			seedCPLT.setComplaintDate( DateTimeUtil.getCurrentDate() );
			model.addAttribute("seedCPLT", seedCPLT);
		}
		
		return new ModelAndView("/seedcplt/seedCPLTEdit");
	}
	
	@RequestMapping("/settleValidate")
	@ResponseBody
	public JsonResult settleValidate(Model model, Integer id){
		JsonResult jr = new JsonResult(true);
		Map<String, String> data = new HashMap<String, String>();
		if( id != null && id > 0 ){
			SeedCPLT seedCPLT = this.seedCPLTService.getSeedCPLT(id);
			seedCPLT.setSettleDate( DateTimeUtil.getCurrentDate() );
			model.addAttribute("seedCPLT", seedCPLT);
			
			data.put("status", seedCPLT.getSettleStatus());
		}else{
			data.put("status", "01");
		}
		jr.setData( data );
		
		return jr;
	}
	
	@RequestMapping("/settleInput")
	@ResponseBody
	public ModelAndView settleInput(Model model, Integer id){
		if( id != null && id > 0 ){
			SeedCPLT seedCPLT = this.seedCPLTService.getSeedCPLT(id);
			seedCPLT.setSettleDate( DateTimeUtil.getCurrentDate() );
			model.addAttribute("seedCPLT", seedCPLT);
			
//			BlackList bl = this.blackListService.getBlackListByCompanyCod( seedCPLT.getCompanyCode() );
//			ReCallRecord rcr = this.reCallRecordService.getReCallRecordByCompanyCod(seedCPLT.getCompanyCode());
//			
//			if( bl != null && bl.getId() != null && bl.getId() > 0 ){
//				model.addAttribute("addBlackList", "1");
//				model.addAttribute("blackListTimeLimit", bl.getBlackListTimeLimit());
//			}else{
//				model.addAttribute("addBlackList", "0");
//			}
//			if( rcr != null &&  rcr.getId() != null && rcr.getId() > 0 ){
//				model.addAttribute("recall", "1");
//				model.addAttribute("batchNo", rcr.getBatchNo()); //批次
//				model.addAttribute("recallReason", rcr.getRecallReason());
//			}else{
//				model.addAttribute("recall", "0");
//			}
		}
		
		return new ModelAndView("/seedcplt/settleEdit");
	}

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, SeedCPLTModel seedCPLTModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		if( pageModel != null){
			model.addAttribute("pageModel", pageModel);
			model.addAttribute("complaintBeginDate", seedCPLTModel.getComplaintBeginDate());
			model.addAttribute("complaintEndDate", seedCPLTModel.getComplaintEndDate());
			model.addAttribute("settleBeginDate", seedCPLTModel.getSettleBeginDate());
			model.addAttribute("settleEndDate", seedCPLTModel.getSettleEndDate());
			model.addAttribute("beginDate", seedCPLTModel.getBeginDate());
			model.addAttribute("endDate", seedCPLTModel.getEndDate());
			model.addAttribute("settleStatus", seedCPLTModel.getSettleStatus());
			model.addAttribute("companyCode", seedCPLTModel.getCompanyCode());
		}
		
		return new ModelAndView("/seedcplt/seedCPLTList");
	  }
	
	@RequestMapping({"/search"})
	@ResponseBody
	public ModelAndView search(Model model, Integer page, Integer pageSize, SeedCPLTModel seedCPLTModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		
		if(StringUtils.isNotEmpty( seedCPLTModel.getCompanyCode() )){ 
			params.put("companyCode", seedCPLTModel.getCompanyCode() );
		}
		if(StringUtils.isNotEmpty( seedCPLTModel.getSettleStatus() )){ 
			params.put("settleStatus", seedCPLTModel.getSettleStatus() );
		}
		if(StringUtils.isNotEmpty( seedCPLTModel.getComplaintBeginDate() )){ 
			params.put("complaintBeginDate", seedCPLTModel.getComplaintBeginDate() );
		}
		if(StringUtils.isNotEmpty( seedCPLTModel.getComplaintEndDate() )){ 
			params.put("complaintEndDate", seedCPLTModel.getComplaintEndDate() );
		}
		if(StringUtils.isNotEmpty( seedCPLTModel.getSettleBeginDate() )){ 
			params.put("settleBeginDate", seedCPLTModel.getSettleBeginDate() );
		}
		if(StringUtils.isNotEmpty( seedCPLTModel.getComplaintEndDate() )){ 
			params.put("settleEndDate", seedCPLTModel.getSettleEndDate() );
		}
		if(StringUtils.isNotEmpty( seedCPLTModel.getBeginDate() )){ 
			params.put("beginDate", seedCPLTModel.getBeginDate() );
		}
		if(StringUtils.isNotEmpty( seedCPLTModel.getEndDate() )){ 
			params.put("endDate", seedCPLTModel.getEndDate() );
		}
		pageModel = this.seedCPLTService.queryForPageModel("SeedCPLT", params, pageModel);
		List<SeedCPLT> list = (List<SeedCPLT>)pageModel.getResult();
		if( list != null ) {
			for( SeedCPLT cplt : list ){
				StringBuffer processMode = new StringBuffer("");
				if( cplt.getProcessMode() != null && cplt.getProcessMode().contains("01") ){
					if( processMode.length() > 0 ){
						processMode.append(",");
					}
					processMode.append("警告");
				}
				if( cplt.getProcessMode() != null && cplt.getProcessMode().contains("02") ){
					if( processMode.length() > 0 ){
						processMode.append(",");
					}
					processMode.append("责令停产停业");
				}
				if( cplt.getProcessMode() != null && cplt.getProcessMode().contains("03") ){
					if( processMode.length() > 0 ){
						processMode.append(",");
					}
					processMode.append("罚款");
				}
				if( cplt.getProcessMode() != null && cplt.getProcessMode().contains("04") ){
					if( processMode.length() > 0 ){
						processMode.append(",");
					}
					processMode.append("暂扣或者吊销许可证、暂扣或者吊销执照");
				}
				if( cplt.getProcessMode() != null && cplt.getProcessMode().contains("05") ){
					if( processMode.length() > 0 ){
						processMode.append(",");
					}
					processMode.append("其它");
				}
				
				cplt.setProcessMode( processMode.toString() );
			}
		}
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("complaintBeginDate", seedCPLTModel.getComplaintBeginDate());
		model.addAttribute("complaintEndDate", seedCPLTModel.getComplaintEndDate());
		model.addAttribute("settleBeginDate", seedCPLTModel.getSettleBeginDate());
		model.addAttribute("settleEndDate", seedCPLTModel.getSettleEndDate());
		model.addAttribute("beginDate", seedCPLTModel.getBeginDate());
		model.addAttribute("endDate", seedCPLTModel.getEndDate());
		model.addAttribute("settleStatus", seedCPLTModel.getSettleStatus());
		model.addAttribute("companyCode", seedCPLTModel.getCompanyCode());
		
		return new ModelAndView("/seedcplt/seedCPLTList");
	  }
	
	@RequestMapping({"/listquery"})
	@ResponseBody
	public GridModel listquery(Model model, Integer page, Integer pageSize, String userName){
		page = 1;
		pageSize = 10;
		userName = "";
		PageModel pageModel = new PageModel(page, pageSize);
		
	    return new GridModel(pageModel);
	 }
}
