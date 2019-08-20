package com.bicsoft.sy.util;

import java.math.BigDecimal;

/**
 * @字符串通用处理类
 * @Author    Gaohua
 * @Version   2015/08/19
 */
public class StringUtil {
	public static boolean isNullOrEmpty(Object obj){ 
		if(obj == null || obj.toString().equals("")) return true;
		else return false;
	}
	
	public static double formatYieldToDouble(double yield){
		BigDecimal bd = new BigDecimal(yield);  
		double result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}
	
	public static String formatYield(double yield){
		BigDecimal bd = new BigDecimal(yield);  
		double result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return String.valueOf(result);
	}
	
	public static String formatYield(BigDecimal bd){
		double result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return String.valueOf(result);
	}
}
