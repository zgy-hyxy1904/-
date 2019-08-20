package com.bicsoft.sy.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

import com.bicsoft.sy.entity.CommonData;
import com.bicsoft.sy.entity.Contract;
import com.bicsoft.sy.entity.GeneLandDetail;
import com.bicsoft.sy.entity.GeneLandRegD;
import com.bicsoft.sy.entity.Peasant;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.GeneLandRegDModel;
import com.bicsoft.sy.model.GridModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.CommonDataService;
import com.bicsoft.sy.service.ContractService;
import com.bicsoft.sy.service.GeneLandDetailService;
import com.bicsoft.sy.service.GeneLandRegDService;
import com.bicsoft.sy.service.PeasantService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.DateTimeUtil;
import com.bicsoft.sy.util.JsonResult;

/**
 * 普通土地备案子表
 * @author 
 * @date 2015-08-18
 */
@Controller
@RequestMapping("/geneLandRegD/")
public class GeneLandRegDController {
	private static final Logger log = LoggerFactory.getLogger(GeneLandRegDController.class);
	
	@Autowired
	private GeneLandRegDService geneLandRegDService;
	@Autowired
	private GeneLandDetailService geneLandDetailService;
	@Autowired
	private CommonDataService commonDataService;
	@Autowired
	private PeasantService peasantService;
	@Autowired
	private ContractService contractService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){

		String[] idArray = request.getParameterValues("ids[]");                                                            
		for (String id : idArray) {                                                                                          
			this.geneLandRegDService.logicDelete(GeneLandRegD.class, Integer.parseInt(id));                                       
		}                                                                
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult save(@RequestBody GeneLandRegDModel geneLandRegDModel, HttpServletRequest request){

		BaseModel baseModel = CommonUtil.getBaseModel(request);                                                      
		geneLandRegDModel.setCreateDate(baseModel.getCreateDate());                                                             
		geneLandRegDModel.setCreateUserId(baseModel.getCreateUserId());                                                              
		geneLandRegDModel.setUpdateDate(baseModel.getUpdateDate());
		geneLandRegDModel.setUpdateUserId(baseModel.getUpdateUserId());                                                                      
		this.geneLandRegDService.save(geneLandRegDModel);
		                                                                                                           
		return new JsonResult(true);                                                      
	}
	
	@RequestMapping("/view")
	@ResponseBody
	public JsonResult view(Model model, Integer id){

		this.geneLandRegDService.delete(id);
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/editInput")
	@ResponseBody
	public ModelAndView editInput(Model model, Integer id, String rowIndex, String conInfo, String flag, String year){
		if( StringUtils.isEmpty( year ) ) year = DateTimeUtil.getCurrentYear();
		BigDecimal zmj = new BigDecimal("0.0");
		Float yba = 0.0f;
		String codeKey = "PlowlandType";
		List<GeneLandDetail> landList;
		model.addAttribute("flag", flag);
		model.addAttribute("rowIndex", rowIndex);
		if( id != null && id > 0 ){
			GeneLandRegD geneLandRegD = this.geneLandRegDService.getGeneLandRegD(id);
			model.addAttribute("GeneLandRegD", geneLandRegD);                             
		}else{
			GeneLandRegD geneLandRegD = new GeneLandRegD();
			//geneLandRegD.setOperatorDate(DateTimeUtil.getCurrentDateByPattern("yyyy-MM-dd"));
			model.addAttribute("GeneLandRegD", geneLandRegD);   
			model.addAttribute("operatorDate", DateTimeUtil.getCurrentDateByPattern("yyyy-MM-dd"));
			model.addAttribute("idType", "01");   //证件类型默认为身份证
		}
		if( StringUtils.isNotEmpty( conInfo ) ){
			String[] conInfoArr = conInfo.split(",");
			if( conInfoArr != null && conInfoArr.length == 12 ){//新增地块未保存时修改
				model.addAttribute("contractorType", conInfoArr[0]);  //承包方类型
				model.addAttribute("idType", conInfoArr[1]);  //证件类型
				model.addAttribute("contractorID", conInfoArr[2]);  //证件号码
				
				model.addAttribute("archiveAcreage", conInfoArr[3]);//本次备案面积
				model.addAttribute("operatorName", conInfoArr[4]);   //经办人
				model.addAttribute("operatorDate", conInfoArr[5]);   //经办日期
				model.addAttribute("contractorName", conInfoArr[6]);   //
				model.addAttribute("contractorTel", conInfoArr[7]);   //
				model.addAttribute("townCode", conInfoArr[8]);   //
				model.addAttribute("countryCode", conInfoArr[9]);   //
				model.addAttribute("groupName", conInfoArr[10]);   //
				if( StringUtils.isNotEmpty( conInfoArr[11] ) && !"0".equalsIgnoreCase( conInfoArr[11] ) ){  //有主键时
					landList = this.geneLandDetailService.getGeneLandDetailList( conInfoArr[11] );  //根据主键获取
					for( GeneLandDetail detail : landList ){
						//取土地类型和类别信息
						CommonData _data = commonDataService.getCommonData(codeKey, detail.getLandType());
						if( _data != null ){
							detail.setLandTypeName( _data.getCodeValue() );
						}
						_data = commonDataService.getCommonData("PlowlandClass", detail.getLandClass());
						if( _data != null ){
							detail.setLandClassName( _data.getCodeValue() );
						}
						zmj = zmj.add( new BigDecimal(Float.toString(detail.getMeasurementMu()) ));
					}
					yba = this.geneLandRegDService.queryBAmj( conInfoArr[2], year );
					model.addAttribute("zmj", zmj);    //总面积
					model.addAttribute("yba", yba);
					model.addAttribute("kba", zmj.subtract(new BigDecimal(yba.toString())));   //new BigDecimal(yba.toString()) ) );
					model.addAttribute("landList", landList);
				}else{
					processLandInfo( model, conInfoArr[2], conInfoArr[1], conInfoArr[0], year );
				}
			}
			if( conInfoArr != null && conInfoArr.length == 7 ){//带主键修改地块
				try{
					id = new Integer( conInfoArr[6] );  //ID
					model.addAttribute("id", id);
					model.addAttribute("contractorType", conInfoArr[0]);  //承包方类型
					model.addAttribute("idType", conInfoArr[1]);  //证件类型
					model.addAttribute("contractorID", conInfoArr[2]);  //证件号码
					
					model.addAttribute("archiveAcreage", conInfoArr[3]);//本次备案面积
					model.addAttribute("operatorName", conInfoArr[4]);   //经办人
					model.addAttribute("operatorDate", conInfoArr[5]);   //经办日期
					
					GeneLandRegD geneLandRegD = this.geneLandRegDService.getGeneLandRegD(id);
					model.addAttribute("contractorName", geneLandRegD.getContractorName());
					model.addAttribute("contractorTel", geneLandRegD.getContractorTel());
					model.addAttribute("cityCode", geneLandRegD.getCityCode());
					model.addAttribute("townCode", geneLandRegD.getTownCode());
					model.addAttribute("countryCode", geneLandRegD.getCountryCode());
					model.addAttribute("groupName", geneLandRegD.getGroupName());
					
					yba = this.geneLandRegDService.queryBAmj( geneLandRegD.getContractorID(), year );
					//土地信息列表
					landList = this.geneLandDetailService.getGeneLandDetailList( id + "" );
					for( GeneLandDetail detail : landList ){
						//取土地类型和类别信息
						CommonData _data = commonDataService.getCommonData(codeKey, detail.getLandType());
						if( _data != null ){
							detail.setLandTypeName( _data.getCodeValue() );
						}
						_data = commonDataService.getCommonData("PlowlandClass", detail.getLandClass());
						if( _data != null ){
							detail.setLandClassName( _data.getCodeValue() );
						}
						//zmj += detail.getMeasurementMu();
						zmj = zmj.add(new BigDecimal(Float.toString( detail.getMeasurementMu()) ) );
					}
					model.addAttribute("zmj", zmj);    //总面积
					model.addAttribute("yba", yba);
					model.addAttribute("kba", zmj.subtract(new BigDecimal(yba.toString())));  //zmj.subtract(new BigDecimal(yba.toString()))
					model.addAttribute("landList", landList);
				}catch(Exception e){}
			}
		}
		model.addAttribute("year", year);
		
		return new ModelAndView("/genelandreg/geneLandRegDEdit");              
	}
	
	private void processLandInfo(Model model, String idNumber, String idType, String contractorType, String year){
		Peasant peasant = peasantService.getByContractorID( idType, idNumber); //只根据二个参数查询---新需求
		if(peasant == null){
			peasant = new Peasant();
		}
		List<Contract> contractList = contractService.getContractList(peasant.getContractorIDType(), peasant.getContractorID());
		String codeKey = "PlowlandType";
		Float zmj = 0.0f;
		List<GeneLandDetail> landList = new ArrayList<GeneLandDetail>();
		for( Contract contract: contractList ){
			//取土地类型和类别信息
			CommonData _data = commonDataService.getCommonData(codeKey, contract.getLandType());
			if( _data != null ){
				contract.setLandTypeName( _data.getCodeValue() );
			}
			_data = commonDataService.getCommonData("PlowlandClass", contract.getLandClass());
			if( _data != null ){
				contract.setLandClassName( _data.getCodeValue() );
			}
			
			zmj += contract.getMeasurementMu();
			contract.setActualMu( contract.getContractArea() );
			System.out.println( contract.getLandClassName() + "," + contract.getLandTypeName() + "," + contract.getLandName() );
		}
		Float yba = this.geneLandRegDService.queryBAmj( idNumber, year );
		model.addAttribute("landList", contractList);
		model.addAttribute("zmj", zmj);    //总面积
		model.addAttribute("yba", yba);     //已备案
		model.addAttribute("kba", zmj - yba);  //可备案
	}
	
	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, String userName){
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		pageModel = this.geneLandRegDService.queryForPageModel("GeneLandRegD", null, pageModel);
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("userName", userName);
		return new ModelAndView("/genelandreg/geneLandRegDList");

	  }
	
	@RequestMapping({"/listquery"})
	@ResponseBody
	public GridModel listquery(Model model, Integer page, Integer pageSize, String userName){
		page = 1;
		pageSize = 10;
		userName = "";                                                    
		PageModel pageModel = new PageModel(page, pageSize);                                                         
		//pageModel = this.airMoniService.findPage(pageModel, userName);                                                     
	    	return new GridModel(pageModel);                                                                                 
	 }
}
