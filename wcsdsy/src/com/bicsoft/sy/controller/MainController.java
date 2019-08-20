package com.bicsoft.sy.controller;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.service.FunctionService;
import com.bicsoft.sy.util.GetSession;

@Controller
public class MainController {

	@Autowired
	private FunctionService functionService;
	
	@RequestMapping({"/main"})
	public ModelAndView main(HttpServletRequest request){
		User user = GetSession.getSessionEntity(request);
		String userId = user.getUserID();
		List<Map> funlist = functionService.getUserFunctionList(userId);
		Map mouduleMap = new HashMap();
		List<Map> menuList = new ArrayList<Map>();
		for(Map fmap : funlist){
			String moduleCode = (String)fmap.get("ModuleCode");
			String moduleName = (String)fmap.get("ModuleName");
			if(mouduleMap.get(moduleCode) == null){
				List<Map> subMenu = new ArrayList<Map>();
				subMenu.add(fmap);
				
				Map menu = new HashMap();
				menu.put("parent", fmap);
				menu.put("child", subMenu);
				mouduleMap.put(moduleCode, menu);
				menuList.add(menu);
			}else{
				Map menu = (Map)mouduleMap.get(moduleCode);
				List<Map> subMenu = (List<Map>)menu.get("child");
				subMenu.add(fmap);
			}
		}
		ModelAndView view = new ModelAndView("main");
		view.addObject("menu", menuList);
		return view;
	}
}
