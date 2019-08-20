package com.bicsoft.sy.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bicsoft.sy.entity.Product;
import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SampleModel;
import com.bicsoft.sy.service.ProductService;
import com.bicsoft.sy.service.SampleService;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.GetSession;

/**
 * 质检管理
 * @author 高华
 * @date 2015-08-20
 */

@Controller
@RequestMapping("/sample/")
public class SampleController {
	private static final Logger log = LoggerFactory.getLogger(SampleController.class);
	
	@Autowired
	private SampleService sampleService;
	@Autowired
	private ProductService productService;
	
	//抽检记录列表
	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView sampleList(HttpServletRequest request, SampleModel sampleModel, String flag, Integer page, Integer pageSize) throws Exception{
		ModelAndView returnView = new ModelAndView("sample/list");   
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		if(flag != null && flag.equals("2")){
			pageModel = this.sampleService.queryForPageModel(pageModel, sampleModel);
			List<Map> result = (List<Map>)pageModel.getResult();
			for(Map map : result){
				String companyCode = (String)map.get("CompanyCode");
				String productCode = (String)map.get("ProductCode");
				Product product = productService.getProduct(companyCode, productCode);
				if(product != null) {
					map.put("UnitWeight", product.getWeight());
					map.put("ProductName", product.getProductName());
				}
			}
		}
		
		User user = GetSession.getSessionEntity(request);
		if(user.getUserType().equals(Constants.USER_TYPE_COMPANY)){
			sampleModel.setCompanyCode(user.getCompanyCode());
		}
		
		returnView.addObject("pageModel", pageModel);
		returnView.addObject("sampleModel", sampleModel);
		return returnView;
	  }
}
