package com.bicsoft.sy.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
	public static String getCurrentDateByPattern(String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String date = sdf.format(new Date());
		
		return date;
	}
	
	public static Date getCurrentDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		
		Date d = new Date();
		try {
			d = sdf.parse( date );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return d;
	}
	
	//取当前时间
	public static String getCurrentDateTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		return date;
	}
	
	//计算拉黑截止日期
	public static Date computerBlackListEndDate( Date date, String blackListTimeLimit ){
		//获取日历实例并赋予当前时间
        Calendar cNow = Calendar.getInstance();
        cNow.setTime(date);
        if( "01".equalsIgnoreCase( blackListTimeLimit ) ){  //永久
        	cNow.add(Calendar.YEAR, 100);
        }else if( "02".equalsIgnoreCase( blackListTimeLimit ) ){  //5年
        	cNow.add(Calendar.YEAR, 5);
        }else if( "03".equalsIgnoreCase( blackListTimeLimit ) ){  //3年
        	cNow.add(Calendar.YEAR, 3);
        }else if( "04".equalsIgnoreCase( blackListTimeLimit ) ){  //1年
        	cNow.add(Calendar.YEAR, 1);
        }else if( "05".equalsIgnoreCase( blackListTimeLimit ) ){  //6个月
        	cNow.add(Calendar.MONTH, 6);
        }else if( "06".equalsIgnoreCase( blackListTimeLimit ) ){  //3个月
        	cNow.add(Calendar.MONTH, 3);
        }
		
		return cNow.getTime();
	}
	
	public static String getCurrentYear(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(new Date());
		
		return year;
	}
	
	public static String getStringFromDate(String pattern, Date date){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		
		String szDate = sdf.format(date);
		
		return szDate;
	}
	
	public static Date getDateFromString(String szDate, String pattern) throws Exception{
		DateFormat fmt =  new SimpleDateFormat(pattern);
		return fmt.parse(szDate);
	}
	
	public static Date formatStartDate(Date startDate) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer date = new StringBuffer(sdf.format(startDate));
		date.append(" 00:00:00");
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(date.toString());
	}
	
	public static Date formatEndDate(Date endDate) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer date = new StringBuffer(sdf.format(endDate));
		date.append(" 23:59:59");
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(date.toString());
	}
}
