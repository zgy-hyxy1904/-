package com.bicsoft.sy.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.ProductService;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.GetSession;
/**
 * 主品主档管理
 * @author 高华
 * @date 2015-09-14
 */

@Controller
@RequestMapping("/product/")
public class productController {
	private static final Logger log = LoggerFactory.getLogger(productController.class);
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(HttpServletRequest request, Model model, Integer page, Integer pageSize, String companyCode, String productName, String flag)
	{
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		if(flag != null && flag.equals("2")){
			pageModel = this.productService.queryForPageModel(pageModel, companyCode, productName);
		}
		
		User user = GetSession.getSessionEntity(request);
		if(user.getUserType().equals(Constants.USER_TYPE_COMPANY)){
			companyCode = user.getCompanyCode();
		}
		
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("companyCode", companyCode);
		model.addAttribute("productName", productName);
		return new ModelAndView("product/productList");
	 }
}
