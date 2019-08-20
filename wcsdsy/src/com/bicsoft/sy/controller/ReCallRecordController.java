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

import com.bicsoft.sy.entity.Product;
import com.bicsoft.sy.entity.ReCallRecord;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.GridModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.ReCallRecordModel;
import com.bicsoft.sy.service.ProductService;
import com.bicsoft.sy.service.ReCallRecordService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * 召回
 * @author 创新中软
 * @date 2015-08-31
 */
@Controller
@RequestMapping("/recall/")
public class ReCallRecordController {
	private static final Logger log = LoggerFactory.getLogger(ReCallRecordController.class);
	
	@Autowired
	private ReCallRecordService reCallRecordService;
	@Autowired
	private ProductService productService;
	
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.reCallRecordService.logicDelete(ReCallRecord.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody ReCallRecordModel reCallRecordModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		reCallRecordModel.setCreateDate(baseModel.getCreateDate());
		reCallRecordModel.setCreateUserId(baseModel.getCreateUserId());
		reCallRecordModel.setUpdateDate(baseModel.getUpdateDate());
		reCallRecordModel.setUpdateUserId(baseModel.getUpdateUserId());
		this.reCallRecordService.save(reCallRecordModel);
		
		return new JsonResult(true);
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public JsonResult view(Model model, Integer id){
		this.reCallRecordService.delete(id);
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id){
		if( id != null && id > 0 ){
			ReCallRecord reCallRecord = this.reCallRecordService.getReCallRecord(id);
			model.addAttribute("reCallRecord", reCallRecord);
			
		}
		
		return new ModelAndView("/recall/recallEdit");
	}

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, ReCallRecordModel reCallRecordModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		if(pageModel!=null){ 
			model.addAttribute("pageModel", pageModel);
			model.addAttribute("companyCode", reCallRecordModel.getCompanyCode());
			model.addAttribute("batchNo", reCallRecordModel.getBatchNo());
			model.addAttribute("beginDate", reCallRecordModel.getBeginDate());
			model.addAttribute("endDate", reCallRecordModel.getEndDate());
		}

		return new ModelAndView("/recall/recallList");
	  }
	
	@RequestMapping({"/search"})
	@ResponseBody
	public ModelAndView search(Model model, Integer page, Integer pageSize, ReCallRecordModel reCallRecordModel){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty( reCallRecordModel.getCompanyCode() )){ 
			params.put("companyCode", reCallRecordModel.getCompanyCode() );
		}
		if(StringUtils.isNotEmpty( reCallRecordModel.getBatchNo())){ 
			params.put("batchNo", reCallRecordModel.getBatchNo() );
		}
		if(StringUtils.isNotEmpty( reCallRecordModel.getBeginDate() )){ 
			params.put("beginDate", reCallRecordModel.getBeginDate() );
		}
		if(StringUtils.isNotEmpty( reCallRecordModel.getEndDate() )){ 
			params.put("endDate", reCallRecordModel.getEndDate() );
		}
		
		pageModel = this.reCallRecordService.queryForPageModel("ReCallRecord", params, pageModel);
		List<ReCallRecord> list = (List<ReCallRecord>)pageModel.getResult();
		if( list != null ){
			for(ReCallRecord r : list ){
				Product _p = this.productService.getProduct(r.getCompanyCode(), r.getProductCode());
				if( _p != null ){
					r.setProductName( _p.getProductName() );
				}
			}
		}
		
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("companyCode", reCallRecordModel.getCompanyCode());
		model.addAttribute("batchNo", reCallRecordModel.getBatchNo());
		model.addAttribute("beginDate", reCallRecordModel.getBeginDate());
		model.addAttribute("endDate", reCallRecordModel.getEndDate());
		
		return new ModelAndView("/recall/recallList");
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
