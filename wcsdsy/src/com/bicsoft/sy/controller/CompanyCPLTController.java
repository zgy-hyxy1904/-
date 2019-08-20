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
import com.bicsoft.sy.entity.Product;
import com.bicsoft.sy.entity.ReCallRecord;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.CompanyCPLTModel;
import com.bicsoft.sy.model.GridModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.BlackListDService;
import com.bicsoft.sy.service.BlackListService;
import com.bicsoft.sy.service.CompanyCPLTService;
import com.bicsoft.sy.service.FileManagerService;
import com.bicsoft.sy.service.ProductService;
import com.bicsoft.sy.service.ReCallRecordService;
import com.bicsoft.sy.service.ServialNumService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * 公司投诉
 * @author 创新中软
 * @date 2015-08-26
 */
@Controller
@RequestMapping("/companyCPLT/")
public class CompanyCPLTController {
	private static final Logger log = LoggerFactory.getLogger(BlackListController.class);
	
	@Autowired
	private CompanyCPLTService companyCPLTService;
	
	@Autowired
	private BlackListDService blackListDService;
	
	@Autowired
	private BlackListService blackListService;
	
	@Autowired
	private ReCallRecordService reCallRecordService;
	@Autowired
	private FileManagerService fileManagerService;
	
	@Autowired
	private ServialNumService servialNumService;
	
	//产品服务
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(Model model, HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		JsonResult jr = new JsonResult(true);
		boolean dealflag = false;
		Map<String, String> data = new HashMap<String, String>();
		for (String id : idArray) {
			CompanyCPLT companyCPLT = this.companyCPLTService.getCompanyCPLT(Integer.parseInt(id));
			companyCPLT.setSettleDate( DateTimeUtil.getCurrentDate() );
			model.addAttribute("companyCPLT", companyCPLT);
			if(companyCPLT.getSettleStatus().equals("02")){
				dealflag =true;
			}
		}
		if(!dealflag){
			for (String id : idArray) {
				this.companyCPLTService.logicDelete(CompanyCPLT.class, Integer.parseInt(id));
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
	public JsonResult save(@RequestBody CompanyCPLTModel companyCPLTModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		companyCPLTModel.setCreateDate(baseModel.getCreateDate());
		companyCPLTModel.setCreateUserId(baseModel.getCreateUserId());
		companyCPLTModel.setUpdateDate(baseModel.getUpdateDate());
		companyCPLTModel.setUpdateUserId(baseModel.getUpdateUserId());
		this.companyCPLTService.save(companyCPLTModel);
		CommonUtil.saveMfile(fileManagerService, "18", companyCPLTModel.getId() + "", request);
		
		JsonResult jr = new JsonResult(true);
		Map<String, String> data = new HashMap<String, String>();
		data.put("id", companyCPLTModel.getId()+"");
		jr.setData( data );
		
		return jr;
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public ModelAndView view(Model model, Integer id){
		if( id != null && id > 0 ){
			CompanyCPLT companyCPLT = this.companyCPLTService.getCompanyCPLT(id);
			companyCPLT.setSettleDate( DateTimeUtil.getCurrentDate() );
			model.addAttribute("companyCPLT", companyCPLT);
		}else{
			CompanyCPLT companyCPLT = new CompanyCPLT();
			companyCPLT.setComplaintDate( DateTimeUtil.getCurrentDate() );
			model.addAttribute("companyCPLT", companyCPLT);
		}
		
		return new ModelAndView("/companycplt/companyCPLTView");
	}
	
	@RequestMapping("/viewInfo")
	@ResponseBody
	public ModelAndView viewInfo(Model model, Integer id){
		if( id != null && id > 0 ){
			CompanyCPLT companyCPLT = this.companyCPLTService.getCompanyCPLT(id);

			StringBuffer processMode = new StringBuffer("");
			if( companyCPLT.getProcessMode() != null && companyCPLT.getProcessMode().contains("01") ){
				if( processMode.length() > 0 ){
					processMode.append(",");
				}
				processMode.append("警告");
			}
			if( companyCPLT.getProcessMode() != null && companyCPLT.getProcessMode().contains("02") ){
				if( processMode.length() > 0 ){
					processMode.append(",");
				}
				processMode.append("责令停产停业");
			}
			if( companyCPLT.getProcessMode() != null && companyCPLT.getProcessMode().contains("03") ){
				if( processMode.length() > 0 ){
					processMode.append(",");
				}
				processMode.append("罚款");
			}
			if( companyCPLT.getProcessMode() != null && companyCPLT.getProcessMode().contains("04") ){
				if( processMode.length() > 0 ){
					processMode.append(",");
				}
				processMode.append("暂扣或者吊销许可证、暂扣或者吊销执照");
			}
			if( companyCPLT.getProcessMode() != null && companyCPLT.getProcessMode().contains("05") ){
				if( processMode.length() > 0 ){
					processMode.append(",");
				}
				processMode.append("其它");
			}
			companyCPLT.setProcessMode( processMode.toString() );
			
			String settleStatus = "";
			if( companyCPLT.getSettleStatus() != null && companyCPLT.getSettleStatus().contains("01") ){
				settleStatus = "未处理";
			}
			if( companyCPLT.getSettleStatus() != null && companyCPLT.getSettleStatus().contains("02") ){
				settleStatus = "已处理";
			}
			companyCPLT.setSettleStatus( settleStatus );
			
			model.addAttribute("companyCPLT", companyCPLT);
		}else{ 
			CompanyCPLT companyCPLT = new CompanyCPLT();
			companyCPLT.setComplaintDate(DateTimeUtil.getCurrentDate());
			model.addAttribute("companyCPLT", companyCPLT);
		}
		return new ModelAndView("/companycplt/companyCPLTViewInfo");
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id){
		if( id != null && id > 0 ){
			CompanyCPLT companyCPLT = this.companyCPLTService.getCompanyCPLT(id);
			companyCPLT.setSettleDate( DateTimeUtil.getCurrentDate() );
			model.addAttribute("companyCPLT", companyCPLT);
		}else{
			CompanyCPLT companyCPLT = new CompanyCPLT();
			companyCPLT.setComplaintDate( DateTimeUtil.getCurrentDate() );
			model.addAttribute("companyCPLT", companyCPLT);
		}
		
		return new ModelAndView("/companycplt/companyCPLTEdit");
	}

	@RequestMapping("/settleValidate")
	@ResponseBody
	public JsonResult settleValidate(Model model, Integer id){
		JsonResult jr = new JsonResult(true);
		Map<String, String> data = new HashMap<String, String>();
		if( id != null && id > 0 ){
			CompanyCPLT companyCPLT = this.companyCPLTService.getCompanyCPLT(id);
			companyCPLT.setSettleDate( DateTimeUtil.getCurrentDate() );
			model.addAttribute("companyCPLT", companyCPLT);
			
			data.put("status", companyCPLT.getSettleStatus());
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
			CompanyCPLT companyCPLT = this.companyCPLTService.getCompanyCPLT(id);
			if( companyCPLT == null ){
				companyCPLT = new CompanyCPLT();
			}
			companyCPLT.setSettleDate( DateTimeUtil.getCurrentDate() );
			model.addAttribute("companyCPLT", companyCPLT);
			
			BlackList bl = this.blackListService.getBlackListByCompanyCod( companyCPLT.getCompanyCode() );
			ReCallRecord rcr = this.reCallRecordService.getReCallRecordByCompanyCod(companyCPLT.getCompanyCode());
			
			if( bl != null && bl.getId() != null && bl.getId() > 0 ){
				model.addAttribute("addBlackList", "1");
				model.addAttribute("blackListTimeLimit", bl.getBlackListTimeLimit());
			}else{
				model.addAttribute("addBlackList", "0");
			}
			if( rcr != null &&  rcr.getId() != null && rcr.getId() > 0 ){
				model.addAttribute("recall", "1");
				model.addAttribute("batchNo", rcr.getBatchNo()); //批次
				model.addAttribute("recallReason", rcr.getRecallReason());
				model.addAttribute("productCode", rcr.getProductCode());
			}else{
				model.addAttribute("recall", "0");
			}
		}
		
		return new ModelAndView("/companycplt/settleEdit");
	}
	
	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, CompanyCPLTModel companyCPLTModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( companyCPLTModel.getCompanyCode() )){ 
			params.put("companyCode", companyCPLTModel.getCompanyCode() );
		}
		if(StringUtils.isNotEmpty( companyCPLTModel.getSettleStatus() )  ){
			params.put("settleStatus", companyCPLTModel.getSettleStatus() );
		}
		if(StringUtils.isNotEmpty( companyCPLTModel.getComplaintBeginDate() )  ){
			params.put("complaintBeginDate", companyCPLTModel.getComplaintBeginDate() );
		}
		if(StringUtils.isNotEmpty( companyCPLTModel.getComplaintEndDate() )  ){
			params.put("complaintEndDate", companyCPLTModel.getComplaintEndDate() );
		}
		if(StringUtils.isNotEmpty( companyCPLTModel.getSettleBeginDate())  ){
			params.put("settleBeginDate", companyCPLTModel.getSettleBeginDate() );
		}
		if(StringUtils.isNotEmpty( companyCPLTModel.getSettleEndDate())  ){
			params.put("settleEndDate", companyCPLTModel.getSettleEndDate() );
		}
		if( companyCPLTModel.getCompanyCode() != null ){
			pageModel = this.companyCPLTService.queryForPageModel("CompanyCPLT", params, pageModel);
		}
		List<CompanyCPLT> list = (List<CompanyCPLT>)pageModel.getResult();
		if( list != null ) {
			for( CompanyCPLT cplt : list ){
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
		model.addAttribute("companyCode", companyCPLTModel.getCompanyCode());
		model.addAttribute("complaintBeginDate", companyCPLTModel.getComplaintBeginDate());
		model.addAttribute("complaintEndDate", companyCPLTModel.getComplaintEndDate());
		model.addAttribute("settleBeginDate", companyCPLTModel.getSettleBeginDate());
		model.addAttribute("settleEndDate", companyCPLTModel.getSettleEndDate());
		model.addAttribute("settleStatus", companyCPLTModel.getSettleStatus());
		
		return new ModelAndView("/companycplt/companyCPLTList");
	  }
	
	@RequestMapping({"/getProductInfo"})
	@ResponseBody
	public JsonResult getProductInfoByCompanyCode(String companyCode){
		JsonResult jr = new JsonResult(true);
		List<Product> list = (List<Product>)productService.getProductTypeList(companyCode);
		Map<String, Object> so = new HashMap<String, Object>();
		so.put("product", list);
		jr.setData( so );
		
		return jr;
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
