package com.bicsoft.sy.util;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
	}

	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie;
		}
		return null;
	}

	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map cookieMap = new HashMap();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			Cookie[] arrayOfCookie1;
			int j = (arrayOfCookie1 = cookies).length;
			for (int i = 0; i < j; i++) {
				Cookie cookie = arrayOfCookie1[i];
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
