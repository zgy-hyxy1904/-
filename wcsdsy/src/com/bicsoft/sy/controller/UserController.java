package com.bicsoft.sy.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bicsoft.sy.entity.AreaDevision;
import com.bicsoft.sy.entity.Peasant;
import com.bicsoft.sy.entity.Role;
import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.entity.UserRole;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.RoleComboModel;
import com.bicsoft.sy.model.ToDoListModel;
import com.bicsoft.sy.model.UserModel;
import com.bicsoft.sy.service.AreaDevisionService;
import com.bicsoft.sy.service.ContractService;
import com.bicsoft.sy.service.PeasantService;
import com.bicsoft.sy.service.RoleService;
import com.bicsoft.sy.service.ServialNumService;
import com.bicsoft.sy.service.UserRoleService;
import com.bicsoft.sy.service.UserService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.GetSession;
import com.bicsoft.sy.util.JsonResult;
import com.bicsoft.sy.util.MD5Util;

import net.sf.json.JSONArray;

/**
 * 用户管理
 * @author 高华
 * @date 2015-08-18
 */

@Controller
@RequestMapping("/user/")
public class UserController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private PeasantService peasantService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private AreaDevisionService areaDevisionService;
	@Autowired
	private ServialNumService servialNumService;
	
	@RequestMapping("/login")
	public ModelAndView login(){
		log.info("system login info");
		return new ModelAndView("login");
	}
	
	@RequestMapping("/addUserInit")
	public ModelAndView addUserInit(Model model,HttpServletRequest request){
		List<Role> roleList = roleService.getRoleList();
		
		JSONArray jsonArr = new JSONArray();
		for(Role role : roleList){
			//不显示系统管理组
			if(role.getRoleCode().equals(Constants.ADMIN_ROLE_CODE)) continue;
			RoleComboModel comboModel = new RoleComboModel();
			comboModel.setRoleCode(role.getRoleCode());
			comboModel.setText(role.getRoleName());
			comboModel.setDesc(role.getRoleName());
			jsonArr.add(comboModel);
		}
		
		String companyCode = "";
		User user = GetSession.getSessionEntity(request);
		if(user.getUserType().equals(Constants.USER_TYPE_COMPANY)){
			companyCode = user.getCompanyCode();
		}
		
		model.addAttribute("companyCode", companyCode);
		model.addAttribute("rolelist", jsonArr.toString());
		return new ModelAndView("/user/addUser");
	}
	
	@RequestMapping("/addUser")
	@ResponseBody
	public JsonResult addUser(@RequestBody UserModel userModel, HttpServletRequest request){
		
		User user = userService.getUserByUserId(userModel.getUserID());
		if(user != null){
			return new JsonResult(false, "该用户名已经被使用，请重新输入！");
		}
		
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		userModel.setCreateDate(baseModel.getCreateDate());
		userModel.setCreateUserId(baseModel.getCreateUserId());
		userModel.setUpdateDate(baseModel.getUpdateDate());
		userModel.setUpdateUserId(baseModel.getUpdateUserId());
		
		//密码加密保存
		userModel.setPassword(MD5Util.getMD5Code(userModel.getPassword()));
		this.userService.save(userModel);
		
		String role = userModel.getRole();
		if(role != null){
			String[] roleList = role.split(",");
			for(String roleCode : roleList){
				UserRole userRole = new UserRole();
				userRole.setUserID(userModel.getUserID());
				userRole.setRoleCode(roleCode);
				
				userRole.setCreateDate(baseModel.getCreateDate());
				userRole.setCreateUserId(baseModel.getCreateUserId());
				userRole.setUpdateDate(baseModel.getUpdateDate());
				userRole.setUpdateUserId(baseModel.getUpdateUserId());
				userRoleService.save(userRole);
			}
		}
		
		return new JsonResult(true);
	}
	
	@RequestMapping("/detail")
	public ModelAndView detail(Model model, Integer id){
		User user = this.userService.getUser(id);
		model.addAttribute("user", user);
		HashMap<String, Integer> userRoleMap = new HashMap<String, Integer>();
		List<UserRole> userRoleList = userRoleService.getUserRoleList(user.getUserID());
		for(UserRole userRole : userRoleList){
			userRoleMap.put(userRole.getRoleCode(), 1);
		}
		
		List<Role> roleList = roleService.getRoleList();
		JSONArray jsonArr = new JSONArray();
		for(Role role : roleList){
			RoleComboModel comboModel = new RoleComboModel();
			comboModel.setRoleCode(role.getRoleCode());
			comboModel.setText(role.getRoleName());
			comboModel.setDesc(role.getRoleName());
			if(userRoleMap.get(role.getRoleCode()) != null){
				comboModel.setSelected(true);
			}
			jsonArr.add(comboModel);
		}
		model.addAttribute("rolelist", jsonArr.toString());
		return new ModelAndView("/user/detail");
	}
	
	@RequestMapping("/editUserInit")
	public ModelAndView editUserInit(Model model, Integer id){
		User user = this.userService.getUser(id);
		
		HashMap<String, Integer> userRoleMap = new HashMap<String, Integer>();
		List<UserRole> userRoleList = userRoleService.getUserRoleList(user.getUserID());
		for(UserRole userRole : userRoleList){
			userRoleMap.put(userRole.getRoleCode(), 1);
		}
		
		List<Role> roleList = roleService.getRoleList();
		JSONArray jsonArr = new JSONArray();
		for(Role role : roleList){
			if(role.getRoleCode().equals(Constants.ADMIN_ROLE_CODE)) continue;
			RoleComboModel comboModel = new RoleComboModel();
			comboModel.setRoleCode(role.getRoleCode());
			comboModel.setText(role.getRoleName());
			comboModel.setDesc(role.getRoleName());
			if(userRoleMap.get(role.getRoleCode()) != null){
				comboModel.setSelected(true);
			}
			jsonArr.add(comboModel);
		}
		System.out.println(jsonArr.toString());
		model.addAttribute("rolelist", jsonArr.toString());
		
		model.addAttribute("user", user);
		return new ModelAndView("/user/editUser");
	}
	
	@RequestMapping("/editUser")
	@ResponseBody
	public JsonResult editUser(@RequestBody UserModel userModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		userModel.setUpdateDate(baseModel.getUpdateDate());
		userModel.setUpdateUserId(baseModel.getUpdateUserId());
		
		userRoleService.deleteUserRoles(userModel.getUserID());
		
		//保存角色信息 先删再插插入
		String role = userModel.getRole();
		if(role != null){
			String[] roleList = role.split(",");
			for(String roleCode : roleList){
				UserRole userRole = new UserRole();
				userRole.setUserID(userModel.getUserID());
				userRole.setRoleCode(roleCode);
				
				userRole.setCreateDate(baseModel.getCreateDate());
				userRole.setCreateUserId(baseModel.getCreateUserId());
				userRole.setUpdateDate(baseModel.getUpdateDate());
				userRole.setUpdateUserId(baseModel.getUpdateUserId());
				userRoleService.save(userRole);
			}
		}
		
		
		this.userService.save(userModel);
		return new JsonResult(true);
	}
	
	@RequestMapping("/editPassInit")
	public ModelAndView editPassInit(Model model, Integer id){
		User user = this.userService.getUser(id);
		model.addAttribute("user", user);
		return new ModelAndView("/user/editPass");
	}
	
	/**
	 * 修改密码
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatePwdInit")
	public ModelAndView updatePwdInit(Model model, HttpServletRequest request){
		User user = GetSession.getSessionEntity(request);
		model.addAttribute("user", user);
		return new ModelAndView("/user/updatePwd");
	}
	
	/**
	 * 个人基本信息维护
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/baseInfoEditInit")
	public ModelAndView baseInfoEditInit(Model model, HttpServletRequest request){
		User user = GetSession.getSessionEntity(request);
		User _user = this.userService.getUser(user.getId());
		model.addAttribute("user", _user);
		
		return new ModelAndView("/user/baseInfoEdit");
	}
	
	/**
	 * 个人基本信息维护
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/baseInfoEdit")
	@ResponseBody
	public JsonResult baseInfoEdit(@RequestBody UserModel model, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		User curUser = GetSession.getSessionEntity(request);
		User _user = this.userService.getUser( curUser.getId() );
		_user.setUserName( model.getUserName() );
		_user.setTel(model.getTel());
		_user.setEmail(model.getEmail());
		_user.setUpdateDate(baseModel.getUpdateDate());
		_user.setUpdateUserId(baseModel.getUpdateUserId());
		this.userService.save(_user);
		
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	/**
	 * 
	 * 用户自已修改密码
	 * @param modle
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatePwd")
	@ResponseBody
	public JsonResult updatePwd(@RequestBody UserModel model, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		User curUser = GetSession.getSessionEntity(request);
		User _user = this.userService.getUser( curUser.getId() );
		
		JsonResult jr = new JsonResult(true);
		if( _user != null && _user.getPassword() != null &&
				_user.getPassword().equals( MD5Util.getMD5Code( model.getOldPwd() ) )){
			_user.setPassword(MD5Util.getMD5Code(model.getPassword()));
			_user.setUpdateDate(baseModel.getUpdateDate());
			_user.setUpdateUserId(baseModel.getUpdateUserId());
			
			this.userService.save(_user);
		}else{
			jr = new JsonResult( false );
			Map<String, String> map = new HashMap<String, String>();
			map.put("msg", "原始密码不正确!");
			jr.setData( map );
		}
		
		return jr;
	}
	
	@RequestMapping("/editPass")
	@ResponseBody
	public JsonResult editPass(@RequestBody UserModel modle, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		User user = this.userService.getUser(modle.getId());
		user.setPassword(MD5Util.getMD5Code(modle.getPassword()));
		user.setUpdateDate(baseModel.getUpdateDate());
		user.setUpdateUserId(baseModel.getUpdateUserId());
		this.userService.save(user);
		return new JsonResult(true);
	}
	
	@RequestMapping("/logindo")
	@ResponseBody
	public JsonResult logindo(@RequestBody User user, HttpServletRequest request,HttpServletResponse response)
	{
	    User entity = this.userService.login(user);
	    if (entity != null)
	    {
	    	if(entity.getLoginStatus().equals("02")){
		    	 return new JsonResult(false, "您已经被禁止登陆！");
		    }
	    	entity.setLastSignTime(new Date());
	    	this.userService.save(entity);;
	    	
	    	GetSession.setSessionEntity(entity, request);
	    	JsonResult jr = new JsonResult(true);
	    	jr.setData(entity);
	    	return jr;
	    }
	    return new JsonResult(false, "账号或密码错误！");
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request)
	{
		String[] idArray = request.getParameterValues("ids[]");
		if( idArray != null ){
			for (String id : idArray) {
				this.userService.logicDelete(User.class, Integer.parseInt(id));
			}
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, String userID, String userName, String type, String flag)
	{
		//String servialNum = servialNumService.getServialNum("PT");
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		if(flag != null && flag.equals("2")){
			pageModel = this.userService.queryForPageModel(pageModel, userID, userName, type);
		}
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("userID", userID);
		model.addAttribute("userName", userName);
		model.addAttribute("type", type);
		return new ModelAndView("user/userList");
	  }
	
	@RequestMapping("/setLoginStatus")
	@ResponseBody
	public JsonResult setLoginStatus(Integer id, String loginStatus)
	{
	    User user = this.userService.getUser(id);
	    if(loginStatus.equals("01") && user.getLoginStatus().equals("01")){
	    	return new JsonResult(false,"该用户已经启用了。");
	    }else if(loginStatus.equals("02") && user.getLoginStatus().equals("02")){
	    	return new JsonResult(false,"该用户已经禁用了。");
	    }
	    user.setLoginStatus(loginStatus);
	    this.userService.save(user);
	    return new JsonResult(true);
	}
	
	//导入土地数据　临时测试用
	@RequestMapping("/importData")
	@ResponseBody
	public JsonResult importData() throws Exception
	{
		String cityCode = "230184";
		String path = "D:\\wuchang\\Bicsoft-WCSD\\dev\\suyuan\\doc\\peasant.txt";
		//String path = "D:\\wuchang\\Bicsoft-WCSD\\dev\\suyuan\\doc\\contract1.txt";
		String  line = null;
		FileReader fr = new FileReader(path);
		BufferedReader bf = new BufferedReader(fr); 
		 while((line = bf.readLine())!=null){

			 String[] cols = line.split(",");
			 
			 /*Contract contract = new Contract();
			 contract.setGraphCode(cols[0]);
			 contract.setContractorCode(cols[1]);
			 contract.setLandName(cols[2]);
			 contract.setLandCode(cols[3]);
			 contract.setContractArea(Float.valueOf(cols[4]));
			 contract.setEastTo(cols[5]);
			 contract.setMeasurementMu(Float.valueOf(cols[6]));
			 contract.setSouthTo(cols[7]);
			 contract.setWestTo(cols[8]);
			 contract.setNorthTo(cols[9]);
			 contract.setLandPurpose(cols[10]);
			 if(cols[11].equals("水田")){
				 contract.setLandType("01");
			 }else if(cols[11].equals("旱地")){
				 contract.setLandType("02");
			 }else if(cols[11].equals("其它")){
				 contract.setLandType("03");
			 }
			 contract.setLandLevel(cols[12]);
			 if(cols[13].equals("机动地")){
				 contract.setLandClass("03");
			 }else if(cols[13].equals("开荒地")){
				 contract.setLandClass("04");
			 }else if(cols[13].equals("其它集体土地")){
				 contract.setLandClass("05");
			 }
			 contract.setLandClass("05");
			 
			 contract.setIsBaseLand(cols[14]);
			 contract.setOwnership(cols[15]);
			 contractService.save(contract);*/

			 //contact
			 /*Contract contract = new Contract();
			 contract.setContractorCode(cols[1]);
			 contract.setGraphCode(cols[2]);
			 contract.setLandCode(cols[3]);
			 contract.setLandName(cols[4]);
			 contract.setEastTo(cols[5]);
			 contract.setContractArea(Float.valueOf(cols[6]));
			 contract.setMeasurementMu(Float.valueOf(cols[7]));
			 contract.setWestTo(cols[8]);
			 contract.setSouthTo(cols[9]);
			 contract.setNorthTo(cols[10]);
			 contract.setLandPurpose(cols[11]);
			 contract.setLandLevel(cols[12]);
			 if(cols[13].equals("水田")){
				 contract.setLandType("01");
			 }else if(cols[13].equals("旱地")){
				 contract.setLandType("02");
			 }else if(cols[13].equals("其它")){
				 contract.setLandType("03");
			 }
			 contract.setIsBaseLand(cols[14]);
			 contract.setOwnership(cols[15]);
			 if(cols[27].equals("机动地")){
				 contract.setLandClass("03");
			 }else if(cols[27].equals("开荒地")){
				 contract.setLandClass("04");
			 }else if(cols[27].equals("其它集体土地")){
				 contract.setLandClass("05");
			 }

			 DateFormat fmt =  new SimpleDateFormat("yyyy-MM-dd");
			 Date startDate = fmt.parse(cols[38]);
			 Date endDate = fmt.parse(cols[39]);
			 contract.setContractStartDate(startDate);
			 contract.setContractEndDate(endDate);
			 contract.setContractYear(Integer.valueOf(cols[40]));
			 contractService.save(contract);*/

			 
			 //农户信息
			 Peasant peasant = new Peasant();
			 peasant.setContractorCode(cols[0]);
			 peasant.setContractorName(cols[1]);
			 peasant.setContractorID(cols[2]);
			 if(cols[3].equals("居民身份证")){
				 peasant.setContractorIDType("01");
			 }
			 if(cols[4] != null && !cols[4].equals("")){
				 peasant.setContractorAge(Integer.valueOf(cols[4])); 
			 }
			 peasant.setContractorSex(cols[5]);
			 peasant.setContractorBirth(cols[6]);
			 peasant.setContractorTel(cols[7]);
			 peasant.setGroupName(cols[8]);
			 peasant.setCityCode(cityCode);
			 
			 Pattern pa = Pattern.compile("(.*?)(乡|镇)(.*?)(村)");
			 Matcher ma = pa.matcher(peasant.getGroupName());  
			 if (ma.find()){
				 String townName = ma.group(1)+ma.group(2);
				 String countryName = ma.group(3)+ma.group(4);
				//取乡镇村信息
				 AreaDevision areaDevision =  areaDevisionService.getAreaDevisionByName(townName, countryName);
				 if(areaDevision != null){
					 peasant.setTownCode(areaDevision.getTownCode());
					 peasant.setCountryCode(areaDevision.getCountryCode());
				 }
			 }
			 
			 peasant.setContractorZipcode(cols[9]);
			 peasant.setContractorhouseholdType(cols[10]);
			 if(cols[11].equals("农户")){
				 peasant.setContractorType("01");
			 }else if(cols[11].equals("个人")){
				 peasant.setContractorType("02");
			 }
			 peasant.setContractorNation(cols[13]);
			 peasant.setLandPurpose(cols[19]);
			 peasant.setRightGetWay(cols[20]);
			 
			 DateFormat fmt =  new SimpleDateFormat("yyyy-MM-dd");
			 Date startDate = fmt.parse(cols[21]);
			 Date endDate = fmt.parse(cols[23]);
			 
			 peasant.setContractStartDate(startDate);
			 if(cols[22] != null && !cols[22].equals("")) peasant.setContractYear(Integer.valueOf(cols[22]));
			 peasant.setContractEndDate(endDate);
			 
			 peasantService.save(peasant);
		 }
		
		return new JsonResult(true);
	}
	/**
	 * 待办事项
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/myTask")
	public ModelAndView myTask(Model model, HttpServletRequest request){
		User user = GetSession.getSessionEntity(request);
		model.addAttribute("user", user);
		
		return new ModelAndView("/user/myTask");
	}
	
	//退出登陆
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request){
		//移除sessioin
		GetSession.removeSession(request);
		return new ModelAndView("/login");
	}
	
	@RequestMapping("/toDoList")
	@ResponseBody
	public ModelAndView view(Model model, HttpServletRequest request){
		String companyCode = null;
		User user = GetSession.getSessionEntity(request);
		if(user.getUserType().equals(Constants.USER_TYPE_COMPANY)){
			companyCode = user.getCompanyCode();
		}
		List<ToDoListModel> toDoLists = userService.queryToDoLists(companyCode);
		model.addAttribute("toDoLists", toDoLists);
		return new ModelAndView("/user/toDoView");
	}
}
