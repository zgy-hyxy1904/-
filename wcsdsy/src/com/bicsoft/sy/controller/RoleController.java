package com.bicsoft.sy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bicsoft.sy.entity.Function;
import com.bicsoft.sy.entity.Role;
import com.bicsoft.sy.entity.RoleFunction;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.RoleModel;
import com.bicsoft.sy.model.TreeModel;
import com.bicsoft.sy.service.FunctionService;
import com.bicsoft.sy.service.RoleFunctionService;
import com.bicsoft.sy.service.RoleService;
import com.bicsoft.sy.util.CommonUtil;
import com.bicsoft.sy.util.Constants;
import com.bicsoft.sy.util.JsonResult;

import net.sf.json.JSONArray;

/**
 * 用户管理
 * @author 高华
 * @date 2015-08-19
 */

@Controller
@RequestMapping("/role/")
public class RoleController {
	private static final Logger log = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleService roleService;
	@Autowired
	private FunctionService functionService;
	@Autowired
	private RoleFunctionService roleFunctionService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult delete(HttpServletRequest request)
	{
		String[] idArray = request.getParameterValues("ids[]");
		for (String id : idArray) {
			Role role = this.roleService.getRole(Integer.parseInt(id));
			List<RoleFunction> funList = roleFunctionService.getFunListForRole(role.getRoleCode());
			for(RoleFunction fun : funList){
				roleFunctionService.delete(fun);
			}
			 this.roleService.delete(role);	
		}
		JsonResult jr = new JsonResult(true);
		return jr;
	}
	
	@RequestMapping("/addRoleInit")
	public ModelAndView addUserInit(){
		return new ModelAndView("/role/addRole");
	}
	
	@RequestMapping("/addRole")
	@ResponseBody
	public JsonResult addUser(@RequestBody RoleModel roleModel, HttpServletRequest request){
		Role role = this.roleService.getRoleByRoleCode(roleModel.getRoleCode());
		if(role != null){
			return new JsonResult(false, "该角色名已经存在，请重新输入！");
		}
		
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		roleModel.setCreateDate(baseModel.getCreateDate());
		roleModel.setCreateUserId(baseModel.getCreateUserId());
		roleModel.setUpdateDate(baseModel.getUpdateDate());
		roleModel.setUpdateUserId(baseModel.getUpdateUserId());
		//密码加密保存
		this.roleService.save(roleModel);
		return new JsonResult(true);
	}
	
	@RequestMapping("/editRoleInit")
	public ModelAndView editRoleInit(Model model, Integer id){
		Role role = roleService.getRole(id);
		
		model.addAttribute("role", role);
		return new ModelAndView("/role/editRole");
	}
	
	@RequestMapping("/editRole")
	@ResponseBody
	public JsonResult editRole(@RequestBody RoleModel roleModel, HttpServletRequest request){
		BaseModel baseModel = CommonUtil.getBaseModel(request);
		roleModel.setCreateDate(baseModel.getCreateDate());
		roleModel.setCreateUserId(baseModel.getCreateUserId());
		roleModel.setUpdateDate(baseModel.getUpdateDate());
		roleModel.setUpdateUserId(baseModel.getUpdateUserId());
		//密码加密保存
		this.roleService.save(roleModel);
		return new JsonResult(true);
	}
	
	@RequestMapping("/detail")
	public ModelAndView detail(Model model, Integer id){
		Role role = this.roleService.getRole(id);
		model.addAttribute("role", role);
		return new ModelAndView("/role/detail");
	}
	
	@RequestMapping("/roleAuthInit")
	public ModelAndView roleAuthInit(Model model, Integer id){
		Role role = this.roleService.getRole(id);
		
		List<Function> flist = this.functionService.getFunctionList();

		Map roleAuthMap = new HashMap();
		List<RoleFunction> roleAuthList = this.roleFunctionService.getFunListForRole(role.getRoleCode());
		for(RoleFunction fun : roleAuthList){
			roleAuthMap.put(fun.getFunctionCode(), 1);
		}
		
		LinkedHashMap<String, Function> moduleMap = new LinkedHashMap<String, Function>();
		LinkedHashMap<String, ArrayList> functionMap = new LinkedHashMap<String, ArrayList>();
		for(Function function:flist){
			if(function.getModuleCode().equals(Constants.AUTH_MODULE_CODE)) continue;
			String moduleCode = function.getModuleCode();
			if(functionMap.get(moduleCode) == null){
				ArrayList<Function> funlist = new ArrayList<Function>();
				funlist.add(function);
				functionMap.put(moduleCode, funlist);
				moduleMap.put(moduleCode, function);
			}else{
				List<Function> funlist = functionMap.get(moduleCode);
				funlist.add(function);
			}
		}
		
		JSONArray jsonArr = new JSONArray();
		Iterator iter = moduleMap.keySet().iterator(); 
		while (iter.hasNext()) { 
			String moduleCode =  (String)iter.next(); 
			Function Module = moduleMap.get(moduleCode);
			
			TreeModel parentNode = new TreeModel();
			parentNode.setId(Module.getModuleCode());
			parentNode.setText(Module.getModuleName());
			
			List<Function> funlist = functionMap.get(moduleCode);
			
			List<TreeModel> childList = new ArrayList<TreeModel>();
			for(Function function:funlist){
				TreeModel childNode = new TreeModel();
				childNode.setId(function.getFunctionCode());
				childNode.setText(function.getFunctionName());
				if(roleAuthMap.get(function.getFunctionCode()) != null)
					childNode.setChecked("true");
				childList.add(childNode);
			}
			
			parentNode.setChildren(childList);
			jsonArr.add(parentNode);
		}
		
		model.addAttribute("role", role);
		model.addAttribute("jsonData", jsonArr.toString());
		return new ModelAndView("/role/roleAuth");
	}
	
	@RequestMapping("/saveRoleAuth")
	@ResponseBody
	public JsonResult saveRoleAuth(HttpServletRequest request)
	{
		String roleCode = request.getParameter("roleCode");
		String[] funIds = request.getParameterValues("funIds[]");
		
		//物理删除角色功能
		List<RoleFunction> roleAuthList = this.roleFunctionService.getFunListForRole(roleCode);
		for(RoleFunction roleFunction:roleAuthList){
			this.roleFunctionService.delete(roleFunction);
		}
		
		if(funIds != null && funIds.length > 0){
			BaseModel baseModel = CommonUtil.getBaseModel(request);
			this.roleFunctionService.saveRoleFunList(baseModel, roleCode, funIds);
		}
		
		JsonResult jr = new JsonResult(true);
		return jr;
	}

	@RequestMapping({"/list"})
	@ResponseBody
	public ModelAndView list(Model model, Integer page, Integer pageSize, RoleModel roleModel, String flag)
	{
		page = (page != null) ? page : 1; 
		pageSize = (pageSize != null) ? pageSize : 15;
		PageModel pageModel = new PageModel(page, pageSize);
		if(flag != null && flag.equals("2")){
			pageModel = this.roleService.queryForPageModel(pageModel, roleModel);
		}
		model.addAttribute("pageModel", pageModel);
		model.addAttribute("roleModel", roleModel);
		return new ModelAndView("role/roleList");
	  }
}
