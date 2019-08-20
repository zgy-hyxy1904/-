package com.bicsoft.sy.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bicsoft.sy.entity.Quality;
import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.QualityModel;
import com.bicsoft.sy.service.QualityService;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.GetSession;
import com.bicsoft.sy.util.StringUtil;

/**
 * 质检管理
 * @author 高华
 * @date 2015-08-20
 */

@Controller
@RequestMapping("/quality/")
public class QualityController {
	private static final Logger log = LoggerFactory.getLogger(QualityController.class);
	
	@Autowired
	private QualityService qualityService;
	
	//质检记录列表
	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView qualityList(HttpServletRequest request, QualityModel qualityModel, String flag, Integer page, Integer pageSize){
		ModelAndView returnView = new ModelAndView("quality/list");   
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		if(flag != null && flag.equals("2")){
			pageModel = this.qualityService.queryForPageModel(pageModel, qualityModel);
		}
		returnView.addObject("pageModel", pageModel);
		returnView.addObject("qualityModel", qualityModel);
		
		User user = GetSession.getSessionEntity(request);
		if(user.getUserType().equals(Constants.USER_TYPE_COMPANY)){
			qualityModel.setCompanyCode(user.getCompanyCode());
		}
		
		return returnView;
	}
	
	//查看质检报告
	@RequestMapping({"/report"})
	@ResponseBody
	public ModelAndView report(HttpServletRequest request, Integer id){
		ModelAndView returnView = new ModelAndView("quality/qualityReport");   
		Quality quality = qualityService.getQuality(id);
		String rootPath = request.getContextPath();
		
		Map<String,String> imgProperties = new HashMap<String,String>();
		if(!StringUtil.isNullOrEmpty(quality.getPath())){
			String[] reports = quality.getPath().split(";");
			for(String path : reports){
				StringBuffer reportPath = new StringBuffer();
				reportPath.append(rootPath).append("/upload/report/").append(path);
				imgProperties.put(reportPath.toString(),"质检报告");
			}
		}
        request.setAttribute("imgPathAndInfoMaps", imgProperties);
		return returnView;
	}
}
