package com.bicsoft.sy.util;

import java.util.HashMap;

/**
 * @数据字典处理类
 * @Author    Gaohua
 * @Version   2015/08/16
 */
public class DictUtil  {
	
	//土地类型
	private static HashMap<String,String> plowlandType = new HashMap<String,String>();
		
	//土地类别
	private static HashMap<String,String> plowlandClass = new HashMap<String,String>();
	
	//投入品单位
	private static HashMap<String,String> inputMaterialUnit = new HashMap<String,String>();
	
	//图片和业务BizType映射
	private static HashMap<String,String> bizTypeMap = new HashMap<String,String>();
	//证件类型
	private static HashMap<String,String> idTypeMap = new HashMap<String,String>();
	//证件类型
		private static HashMap<String,String> idTypeNameMap = new HashMap<String,String>();
	
	static{
		
		//土地类型
		plowlandType.put("01", "耕地（水田）");
		
		//土地类别
		plowlandClass.put("01","承包地块");
		plowlandClass.put("02","自留地");
		plowlandClass.put("03","机动地");
		plowlandClass.put("04","开荒地");
		plowlandClass.put("05","其它集体土地");
		
		//投入品单位
		inputMaterialUnit.put("01", "公斤");
		inputMaterialUnit.put("02", "斤");
		inputMaterialUnit.put("03", "克");
		inputMaterialUnit.put("04", "升");
		inputMaterialUnit.put("05", "公升");
		
		bizTypeMap.put("01", "06");
		bizTypeMap.put("02", "07");
		bizTypeMap.put("03", "08");
		bizTypeMap.put("04", "09");
		bizTypeMap.put("05", "10");
		bizTypeMap.put("06", "11");
		bizTypeMap.put("07", "12");
		bizTypeMap.put("08", "13");
		
		idTypeMap.put("01", "身份证");
		idTypeMap.put("02", "军官证");
		idTypeMap.put("03", "行政、企事业单位机构代码证或法人代码证");
		idTypeMap.put("04", "户口簿");
		idTypeMap.put("05", "护照");
		idTypeMap.put("06", "其他证件");
		
		idTypeNameMap.put("身份证", "01");
		idTypeNameMap.put( "军官证", "02");
		idTypeNameMap.put( "行政、企事业单位机构代码证或法人代码证", "03");
		idTypeNameMap.put("户口簿", "04");
		idTypeNameMap.put( "护照", "05");
		idTypeNameMap.put( "其他证件", "06");
	}
	
	public static String getIdType(String key){
		return (String)idTypeNameMap.get( key );
	}
	
	public static String getPlowlandType(String key){
		return (String)plowlandType.get(key);
	}
	
	public static String getPlowlandClass(String key){
		return (String)plowlandClass.get(key);
	}
	
	public static String getInputUnit(String key){
		return (String)inputMaterialUnit.get(key);
	}
	
	public static String getImgBizType(String key){
		return (String)bizTypeMap.get(key);
	}
}
