package com.bicsoft.sy.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bicsoft.sy.entity.User;
import com.bicsoft.sy.util.CookieUtil;

/**
 * @处理器拦截 处理登陆控制
 * @Author    Gaohua
 * @Version   2015/08/16
 */
public class CommonInterceptor implements HandlerInterceptor {
	public String[] allowUrls;

	public void setAllowUrls(String[] allowUrls) {
		this.allowUrls = allowUrls;
	}

	/** 
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在 
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在 
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返 
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。 
     */ 
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
		if ((this.allowUrls != null) && (this.allowUrls.length >= 1)) {
			String[] arrayOfString;
			int j = (arrayOfString = this.allowUrls).length;
			for (int i = 0; i < j; i++) {
				String url = arrayOfString[i];
				if (requestUrl.contains(url)) {
					return true;
				}
			}
		}
		User str = (User) request.getSession().getAttribute("user");
		if (str != null) {
			Cookie cookie = CookieUtil.getCookieByName(request, "user");
			String value = "";
			if (cookie != null) {
				value = cookie.getValue();
				CookieUtil.addCookie(response, "user", value, 1200);
			}
			return true;
		}
		String loginUrl = request.getContextPath() + "/user/login";
		response.sendRedirect(loginUrl);

		return false;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
