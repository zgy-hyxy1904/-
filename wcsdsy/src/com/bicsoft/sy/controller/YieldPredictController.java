package com.bicsoft.sy.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bicsoft.sy.entity.Company;
import com.bicsoft.sy.entity.GraiEval;
import com.bicsoft.sy.entity.Product;
import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.entity.YieldPredict;
import com.bicsoft.sy.entity.YieldPredictDetail;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.ComboModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.YieldPredictModel;
import com.bicsoft.sy.service.CompanyService;
import com.bicsoft.sy.service.GraiEvalService;
import com.bicsoft.sy.service.ProductService;
import com.bicsoft.sy.service.YieldPredictDetailService;
import com.bicsoft.sy.service.YieldPredictService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.GetSession;
import com.bicsoft.sy.util.JsonResult;
import com.bicsoft.sy.util.StringUtil;

import net.sf.json.JSONArray;

/**
 * 产量预报
 * @author 高华
 * @date 2015-08-22
 */

@Controller
@RequestMapping("/yieldPredict/")
public class YieldPredictController {
	private static final Logger log = LoggerFactory.getLogger(YieldPredictController.class);

	@Autowired
	private YieldPredictService yieldPredictService;
	@Autowired
	private YieldPredictDetailService yieldDetailPredictService;
	@Autowired
	private ProductService productService;
	@Autowired
	private GraiEvalService graiEvalService;
	@Autowired
	private CompanyService companyService;

	@RequestMapping("/add")
	public ModelAndView addPredictInit(Model model, String companyCode){
		List<Product> products = (List<Product>)productService.getProductTypeList(companyCode);
		model.addAttribute("companyCode", companyCode);
		model.addAttribute("products", products);
		return new ModelAndView("/yieldPredict/yieldPredictAdd");
	}

	@RequestMapping("/edit")
	public ModelAndView editPredict(Model model, String rowIndex, String companyCode, String productCode){
		List<Product> products = (List<Product>)productService.getProductTypeList(companyCode);
		model.addAttribute("rowIndex", rowIndex);
		model.addAttribute("companyCode", companyCode);
		model.addAttribute("productCode", productCode);
		model.addAttribute("products", products);
		return new ModelAndView("/yieldPredict/yieldPredictEdit");
	}

	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(HttpServletRequest request) throws Exception{
		String year = request.getParameter("year");
		String companyCode = request.getParameter("companyCode");
		Company company = companyService.getCompany(companyCode);
		String predictDate = request.getParameter("predictDate");
		String params = request.getParameter("params");
		String[] items = params.split(";");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(predictDate);

		YieldPredict yieldPredict= new YieldPredict();
		yieldPredict.setCompanyCode(companyCode);
		if(company != null) yieldPredict.setCompanyName(company.getCompanyName());
		yieldPredict.setYear(year);
		yieldPredict.setPredictionDate(date);
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		yieldPredict.setCreateDate(baseModel.getCreateDate());
		yieldPredict.setCreateUserId(baseModel.getCreateUserId());
		yieldPredict.setUpdateDate(baseModel.getUpdateDate());
		yieldPredict.setUpdateUserId(baseModel.getUpdateUserId());

		yieldPredictService.save(yieldPredict);

		int i = 0;
		float weight = 0;
		for(String item:items){
			String[] subItems = item.split(",");

			YieldPredictDetail yieldPredictDetail = new YieldPredictDetail();
			yieldPredictDetail.setHid(yieldPredict.getId());
			yieldPredictDetail.setSn(String.valueOf(i));
			yieldPredictDetail.setProductCode(subItems[0]);
			yieldPredictDetail.setQty(Integer.valueOf(subItems[1]));
			yieldPredictDetail.setWeight(Float.valueOf(subItems[2]));

			yieldPredictDetail.setCreateDate(baseModel.getCreateDate());
			yieldPredictDetail.setCreateUserId(baseModel.getCreateUserId());
			yieldPredictDetail.setUpdateDate(baseModel.getUpdateDate());
			yieldPredictDetail.setUpdateUserId(baseModel.getUpdateUserId());

			yieldDetailPredictService.save(yieldPredictDetail);
			weight += Float.valueOf(subItems[2]);
			i++;
		}

		//更新重量
		yieldPredict.setYieldPredictionValue(weight);
		yieldPredictService.save(yieldPredict);

		JsonResult js = new JsonResult(true);
		return js;
	}

	@RequestMapping({"/reg"})
	@ResponseBody
	public ModelAndView reg(HttpServletRequest request, Model model, YieldPredictModel yieldPredictModel, String flag){
		User user = GetSession.getSessionEntity(request);
		if(user.getUserType().equals(Constants.USER_TYPE_COMPANY)){
			yieldPredictModel.setCompanyCode(user.getCompanyCode());
		}
		PageModel pageModel = new PageModel(1, 15);
		model.addAttribute("pageModel", pageModel);

		BigDecimal evalYield = new BigDecimal("0.0");
		BigDecimal grainRegYield = new BigDecimal("0.0");
		BigDecimal maxPredictYield = new BigDecimal("0.0");
		BigDecimal predictedYield = new BigDecimal("0.0");
		BigDecimal restPredictYield = new BigDecimal("0.0");

		if(!StringUtil.isNullOrEmpty(flag)){
		  float sumAreas = graiEvalService.getLandsArea(yieldPredictModel.getCompanyCode(), yieldPredictModel.getYear());
			GraiEval gEval = graiEvalService.getGraiEvalByYear(yieldPredictModel.getYear());
			if( gEval.getMilledriceRate() == null ){
				gEval.setMilledriceRate(0.0f);
			}
			if( gEval.getMaxYield() == null ){
				gEval.setMaxYield( 0.0f );
			}
			evalYield = new BigDecimal(String.valueOf(sumAreas))
			            .multiply(new BigDecimal(gEval.getMaxYield().toString()))
			            .multiply(new BigDecimal(gEval.getMilledriceRate().toString()));

			double dGrainRegYield = graiEvalService.getTotalWeight(yieldPredictModel.getYear(), yieldPredictModel.getCompanyCode());
			grainRegYield = new BigDecimal(String.valueOf(dGrainRegYield))
			                .multiply(new BigDecimal(gEval.getMilledriceRate().toString()));


			double dPredictedYield = yieldPredictService.getYieldRegistedTotalValue(yieldPredictModel.getCompanyCode(), yieldPredictModel.getYear());
			predictedYield = new BigDecimal(String.valueOf(dPredictedYield));

			maxPredictYield = evalYield.add(grainRegYield);
			restPredictYield = maxPredictYield.subtract(predictedYield);
		}else{
			yieldPredictModel.setPredictDate(DateTimeUtil.getCurrentDateByPattern("yyyy-MM-dd"));
		}
		model.addAttribute("evalYield", StringUtil.formatYield(evalYield));
		model.addAttribute("grainEval", StringUtil.formatYield(grainRegYield));
		model.addAttribute("maxPredictYield", StringUtil.formatYield(maxPredictYield));
		model.addAttribute("predictedYield", StringUtil.formatYield(predictedYield));
		model.addAttribute("restPredictYield", StringUtil.formatYield(restPredictYield));
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("yieldPredictModel", yieldPredictModel);
		model.addAttribute("predictDate", yieldPredictModel.getPredictDate());

		return new ModelAndView("yieldPredict/yieldPredictReg");
	}

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(HttpServletRequest request, YieldPredictModel yieldPredictModel,String flag,Model model, Integer page, Integer pageSize){
		page = (page != null) ? page : 1;
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);

		User user = GetSession.getSessionEntity(request);
		if(user.getUserType().equals(Constants.USER_TYPE_COMPANY)){
			yieldPredictModel.setCompanyCode(user.getCompanyCode());
		}

		BigDecimal evalYield = new BigDecimal("0.0");
		BigDecimal grainEval = new BigDecimal("0.0");
		BigDecimal predictedYield = new BigDecimal("0.0");
		BigDecimal restPredictYield = new BigDecimal("0.0");

		if(!StringUtil.isNullOrEmpty(flag)){
			pageModel = this.yieldPredictService.getYieldPredictList(pageModel, yieldPredictModel);
			float sumAreas = graiEvalService.getLandsArea(yieldPredictModel.getCompanyCode(), yieldPredictModel.getYear());
			GraiEval gEval = graiEvalService.getGraiEvalByYear(yieldPredictModel.getYear());
			if( gEval.getMilledriceRate() == null ){
				gEval.setMilledriceRate(0.0f);
			}
			if( gEval.getMaxYield() == null ){
				gEval.setMaxYield( 0.0f );
			}
			//evalYield = sumAreas*gEval.getMaxYield()*gEval.getMilledriceRate();
			evalYield = new BigDecimal(String.valueOf(sumAreas))
		            .multiply(new BigDecimal(gEval.getMaxYield().toString()))
		            .multiply(new BigDecimal(gEval.getMilledriceRate().toString()));
			
			double dGrainEval = graiEvalService.getTotalWeight(yieldPredictModel.getYear(), yieldPredictModel.getCompanyCode());
			grainEval = new BigDecimal(String.valueOf(dGrainEval));
			
			grainEval = grainEval.multiply(new BigDecimal(gEval.getMilledriceRate().toString()));
			
			double dPredictedYield = yieldPredictService.getYieldRegistedTotalValue(yieldPredictModel.getCompanyCode(), yieldPredictModel.getYear());
			predictedYield = new BigDecimal(String.valueOf(dPredictedYield));
			
			restPredictYield = evalYield.add(grainEval).subtract(predictedYield);
		}

		model.addAttribute("evalYield", StringUtil.formatYield(evalYield));
		model.addAttribute("grainEval", StringUtil.formatYield(grainEval));
		model.addAttribute("predictedYield", StringUtil.formatYield(predictedYield));
		model.addAttribute("restPredictYield", StringUtil.formatYield(restPredictYield));

		model.addAttribute("pageModel", pageModel);
		model.addAttribute("yieldPredictModel", yieldPredictModel);
		return new ModelAndView("yieldPredict/yieldPredictList");
	 }

	@RequestMapping({"/report"})
	@ResponseBody
	public ModelAndView report(Model model, Integer page, Integer pageSize, YieldPredictModel yieldPredictModel)
	{
		page = (page != null) ? page : 1;
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		pageModel = this.yieldPredictService.getYieldPredictList(pageModel, yieldPredictModel);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("roleModel", yieldPredictModel);
		return new ModelAndView("yieldPredict/yieldPredictReport");
	  }
}
