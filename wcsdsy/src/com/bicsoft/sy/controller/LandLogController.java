package com.bicsoft.sy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bicsoft.sy.entity.LandLog;
import com.bicsoft.sy.model.GraiEvalModel;
import com.bicsoft.sy.model.LandLogModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.LandLogService;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.StringUtil;

@Controller
@RequestMapping("/landLog/")
public class LandLogController {
	
	@Autowired
	private LandLogService landLogService;
	
	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, LandLogModel landLogModel){
//		page = (page != null) ? page : 1; 
//		pageSize = (pageSize != null) ? pageSize : 15;
//		PageModel pageModel = new PageModel(page, pageSize);
		
//		Map<String, Object> params = new HashMap<String, Object>();
//		if(!StringUtil.isNullOrEmpty(landLogModel.getBatchNo())){
//			params.put("batchNo", landLogModel.getBatchNo() );
//		}
//		pageModel = this.landLogService.queryForPageModel("LandLog", params, pageModel);
//		model.addAttribute("pageModel", pageModel);
		List<LandLog> landLogs = this.landLogService.queryByBatchNo(landLogModel.getBatchNo() );
		
		model.addAttribute("landLogs", landLogs);
		model.addAttribute("batchNo", landLogModel.getBatchNo());
		model.addAttribute("landLogType", landLogModel.getLandLogType());
		
		return new ModelAndView("landlog/landRegLogList");
	  }
}
