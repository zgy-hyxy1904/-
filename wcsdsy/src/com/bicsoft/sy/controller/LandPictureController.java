package com.bicsoft.sy.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bicsoft.sy.entity.AreaDevision;
import com.bicsoft.sy.entity.Company;
import com.bicsoft.sy.entity.LandPicture;
import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.ComboModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.service.AreaDevisionService;
import com.bicsoft.sy.service.LandPictureService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.GetSession;
import com.bicsoft.sy.util.JsonResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 土地图片
 * @author 高华
 * @date 2015-08-28
 */

@Controller
@RequestMapping("/landPic/")
public class LandPictureController {
	private static final Logger log = LoggerFactory.getLogger(LandPictureController.class);
	
	@Autowired
	private LandPictureService landPictureService;
	@Autowired
	private AreaDevisionService areaDevisionService;
	
	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(HttpServletRequest request, Model model, Integer page, Integer pageSize, String year, String companyCode, String flag)
	{
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		if(flag != null && flag.equals("2")){
			pageModel = this.landPictureService.queryForPageModel(pageModel, year, companyCode);
			for(Map map : (List<Map>)pageModel.getResult()){
				String cityCode = (String)map.get("CityCode");
				String townCode = (String)map.get("TownCode");
				AreaDevision area = areaDevisionService.getAreaDevision(cityCode, townCode);
				if(area != null){
					map.put("CityName", area.getCityName());
					map.put("TownName", area.getTownName());
				}
			}
		}
		User user = GetSession.getSessionEntity(request);
		if(user.getUserType().equals(Constants.USER_TYPE_COMPANY)){
			companyCode = user.getCompanyCode();
		}
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("year", year);
		model.addAttribute("companyCode", companyCode);
		return new ModelAndView("/landpic/landPicList");
	 }
	
	@RequestMapping({"/addPic"})
	@ResponseBody
	public ModelAndView list(Model model, String year, String companyCode){
		model.addAttribute("year", year);
		List<AreaDevision> cities =  areaDevisionService.queryCitys();
		model.addAttribute("cities", cities);
		model.addAttribute("year", year);
		model.addAttribute("companyCode", companyCode);
		List<AreaDevision> towns =null;
		if(cities!=null){
			towns = areaDevisionService.queryTownsByCityCode(cities.get(0).getCityCode());
		}
		model.addAttribute("towns", towns);
		
		return new ModelAndView("/landpic/landPicAdd");
	}
	
	@RequestMapping({"/upload"})
	@ResponseBody
	public JsonResult upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request, HttpServletResponse response)  throws IOException{
		String id = request.getParameter("id");
		String year = request.getParameter("year");
		String companyCode = request.getParameter("companyCode");
		String cityCode = request.getParameter("cityCode");
		String townCode = request.getParameter("townCode");
		String fileInfo = request.getParameter("fileInfo");
		fileInfo = new String(fileInfo.getBytes("ISO-8859-1"),"UTF-8");
		
		String originameName = file.getOriginalFilename();
		int index = originameName.lastIndexOf(".");
		String extName = originameName.substring(index+1);
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String newName = "17_" + sdf.format(date) + "." + extName;
		String rootPath = request.getSession().getServletContext().getRealPath("/upload/");
		rootPath += "\\" + newName;
		FileUtils.writeByteArrayToFile(new File(rootPath), file.getBytes());
		
		JsonResult jr = new JsonResult(true);
		response.setContentType("text/html");
		JSONObject json = JSONObject.fromObject(jr);
		response.getWriter().write(json.toString());
		
		LandPicture landPicture;
		if(id != null){
			landPicture = this.landPictureService.queryById(LandPicture.class, Integer.valueOf(id));
			landPicture.setPicInfo(fileInfo);
			landPicture.setPicUrl(newName);
		}else{
			landPicture = new LandPicture();
			landPicture.setYear(year);
			landPicture.setCompanyCode(companyCode);
			landPicture.setCityCode(cityCode);
			landPicture.setTownCode(townCode);
			landPicture.setPicInfo(fileInfo);
			landPicture.setPicUrl(newName);
		}
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		landPicture.setCreateDate(baseModel.getCreateDate());
		landPicture.setCreateUserId(baseModel.getCreateUserId());
		landPicture.setUpdateDate(baseModel.getUpdateDate());
		landPicture.setUpdateUserId(baseModel.getUpdateUserId());
		
		this.landPictureService.save(landPicture);
		
		return null;
	}
	
	@RequestMapping({"/viewPic"})
	@ResponseBody
	public ModelAndView viewPic(Model model, Integer id){
		List<AreaDevision> cities =  areaDevisionService.queryCitys();
		LandPicture landPicture = this.landPictureService.queryById(LandPicture.class, id);
		List<AreaDevision> towns = areaDevisionService.queryTownsByCityCode(landPicture.getCityCode());
		model.addAttribute("cities", cities);
		model.addAttribute("towns", towns);
		model.addAttribute("landPicture",landPicture);
		return new ModelAndView("/landpic/landPicView");
	}
	
	@RequestMapping({"/editPicInit"})
	@ResponseBody
	public ModelAndView editPicInit(Model model, Integer id){
		List<AreaDevision> cities =  areaDevisionService.queryCitys();
		LandPicture landPicture = this.landPictureService.queryById(LandPicture.class, id);
		List<AreaDevision> towns = areaDevisionService.queryTownsByCityCode(landPicture.getCityCode());
		model.addAttribute("cities", cities);
		model.addAttribute("towns", towns);
		model.addAttribute("landPicture",landPicture);
		return new ModelAndView("/landpic/landPicEdit");
	}
	
	@RequestMapping({"/updatePicInfo"})
	@ResponseBody
	public JsonResult updatePicInfo(Integer id, String townCode,String fileInfo) throws Exception {
		LandPicture landPicture = this.landPictureService.queryById(LandPicture.class, id);
		fileInfo = new String(fileInfo.getBytes("ISO-8859-1"),"UTF-8");
		landPicture.setPicInfo(fileInfo);
		landPicture.setTownCode(townCode);
		this.landPictureService.save(landPicture);
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/getTowns")
	@ResponseBody
	public JsonResult getClass(String cityCode, String townCode){
		List<AreaDevision> towns =  areaDevisionService.queryTownsByCityCode(cityCode);
		JSONArray jsonArr = new JSONArray();
		
		for(AreaDevision area : towns){
			ComboModel model = new ComboModel();
			model.setId(area.getTownCode());
			model.setText(area.getTownName());
			if(townCode != null && townCode.equals(area.getTownCode())){
				model.setSelected(true);
			}
			jsonArr.add(model);
		};
		
		JsonResult js = new JsonResult(true);
		js.setData(jsonArr.toString());
		return js;
	}
	
	@RequestMapping("/checkUploaded")
	@ResponseBody
	public JsonResult checkUploaded(String companyCode, String cityCode, String townCode){
		LandPicture landPicture = this.landPictureService.getLandPictureByTownCode(companyCode, cityCode, townCode);
		JsonResult js = new JsonResult(true);
		js.setData((landPicture!=null));
		return js;
	}
	
	@RequestMapping({"/delete"})
	@ResponseBody
	public JsonResult delete(HttpServletRequest request){
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			this.landPictureService.logicDelete(LandPicture.class, Integer.parseInt(id));
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
}
