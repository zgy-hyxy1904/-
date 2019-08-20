package com.bicsoft.sy.controller;

import java.math.BigDecimal;
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

import com.bicsoft.sy.entity.GraiEval;
import com.bicsoft.sy.entity.Product;
import com.bicsoft.sy.entity.SecurityCode;
import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.SecurityCodeModel;
import com.bicsoft.sy.service.GraiEvalService;
import com.bicsoft.sy.service.ProductService;
import com.bicsoft.sy.service.SampleService;
import com.bicsoft.sy.service.SecurityCodeDetailService;
import com.bicsoft.sy.service.SecurityCodeService;
import com.bicsoft.sy.service.YieldPredictService;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.GetSession;
import com.bicsoft.sy.util.StringUtil;

/**
 * 防伪码管理
 * @author 高华
 * @date 2015-08-20
 */

@Controller
@RequestMapping("/securityCode/")
public class SecurityCodeController {
	private static final Logger log = LoggerFactory.getLogger(SecurityCodeController.class);

	@Autowired
	private SecurityCodeService securityCodeService;
	@Autowired
	private SecurityCodeDetailService securityCodeDetailService;
	@Autowired
	private YieldPredictService yieldPredictService;
	@Autowired
	private GraiEvalService graiEvalService;
	@Autowired
	private SampleService sampleService;
	@Autowired
	private ProductService productService;

	@RequestMapping({"/applyList"})
	@ResponseBody
	public ModelAndView applyList(HttpServletRequest request, String flag, String year, String companyCode, Integer page, Integer pageSize){
		ModelAndView returnView = new ModelAndView("securitycode/applyList");
		page = (page != null) ? page : 1;
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);

		if(flag != null && flag.equals("2")){
			pageModel = this.securityCodeDetailService.queryForPageModel(pageModel, year, companyCode);
		}
		User user = GetSession.getSessionEntity(request);
		if(user.getUserType().equals(Constants.USER_TYPE_COMPANY)){
			companyCode = user.getCompanyCode();
		}
		returnView.addObject("year", year);
		returnView.addObject("companyCode", companyCode);
		returnView.addObject("pageModel", pageModel);
		return returnView;
	}

	@RequestMapping({"/seclist"})
	@ResponseBody
	public ModelAndView seclist(HttpServletRequest request, SecurityCodeModel securityCodeModel, String flag, Integer page, Integer pageSize){
		ModelAndView returnView = new ModelAndView("securitycode/seclist");
		page = (page != null) ? page : 1;
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);

		BigDecimal totalYield = new BigDecimal("0.0");
		BigDecimal predictYield = new BigDecimal("0.0");
		BigDecimal sampleActYield = new BigDecimal("0.0");
		BigDecimal noApplyCodeCount = new BigDecimal("0.0");
		int applyTotalNum = 0;
		int activationTotalNum = 0;
		if(!StringUtil.isNullOrEmpty(flag)){
			pageModel = this.securityCodeService.queryForPageModel(pageModel, securityCodeModel);
			List<Map> result = (List<Map>)pageModel.getResult();
			for(Map map : result){
				String companyCode = (String)map.get("CompanyCode");
				String productCode = (String)map.get("ProductCode");
				String inspectStatus = (String)map.get("InspectStatus");
				Object productNum = map.get("ProductNum");
				Product product = productService.getProduct(companyCode, productCode);
				if(product != null){
					double total = 0.0;
					if(inspectStatus.equals("02")){
						total =  Double.parseDouble(productNum.toString()) * product.getWeight() * 2;
					}
					map.put("TotalWeight", total);
					map.put("ProductName", product.getProductName());
				}
			}

			//统计
			double sumAreas = graiEvalService.getLandsArea(securityCodeModel.getCompanyCode(), securityCodeModel.getYear());
			GraiEval gEval = graiEvalService.getGraiEvalByYear(securityCodeModel.getYear());
			if( gEval.getMilledriceRate() == null ){
				gEval.setMilledriceRate(0.0f);
			}
			if( gEval.getMaxYield() == null ){
				gEval.setMaxYield( 0.0f );
			}
			BigDecimal evalYield = new BigDecimal(String.valueOf(sumAreas))
		            .multiply(new BigDecimal(gEval.getMaxYield().toString()))
		            .multiply(new BigDecimal(gEval.getMilledriceRate().toString()));

			double dGrainRegYield = graiEvalService.getTotalWeight(securityCodeModel.getYear(), securityCodeModel.getCompanyCode());
			BigDecimal grainRegYield = new BigDecimal(String.valueOf(dGrainRegYield));
			double dPredictYield = yieldPredictService.getYieldRegistedTotalValue(securityCodeModel.getCompanyCode(), securityCodeModel.getYear());
			predictYield = new BigDecimal(String.valueOf(dPredictYield));

			SecurityCode securityCode = securityCodeService.getSecurityCode(securityCodeModel.getYear(), securityCodeModel.getCompanyCode());
			if(securityCode != null){
				applyTotalNum = securityCode.getApplyTotalNum();
				activationTotalNum = securityCode.getActivationTotalNum();
			}

			//通过质检激活产量
			double dSampleActYield = sampleService.getActivateSampleYield(securityCodeModel.getYear(), securityCodeModel.getCompanyCode());
			sampleActYield = new BigDecimal(String.valueOf(dSampleActYield));
			//通过质检申请的产量
			double dSampleYield = sampleService.getSampleYield(securityCodeModel.getYear(), securityCodeModel.getCompanyCode());
			BigDecimal sampleYield = new BigDecimal(String.valueOf(dSampleYield));

			totalYield = evalYield.add(grainRegYield);

			noApplyCodeCount = totalYield.subtract(sampleYield);
		}
		
		String companyCode = securityCodeModel.getCompanyCode();
		User user = GetSession.getSessionEntity(request);
		if(user.getUserType().equals(Constants.USER_TYPE_COMPANY)){
			companyCode = user.getCompanyCode();
		}
		
		returnView.addObject("evalYield", StringUtil.formatYield(totalYield));
		returnView.addObject("predictedYield", StringUtil.formatYield(predictYield));
		returnView.addObject("sampleActYield", StringUtil.formatYield(sampleActYield));
		returnView.addObject("noApplyCodeCount", StringUtil.formatYield(noApplyCodeCount));
		returnView.addObject("applyCodeCount", applyTotalNum);
		returnView.addObject("activateCodeCount", activationTotalNum);
		returnView.addObject("securityCodeModel", securityCodeModel);
		returnView.addObject("pageModel", pageModel);
		returnView.addObject("companyCode", companyCode);
		return returnView;
	}
}
