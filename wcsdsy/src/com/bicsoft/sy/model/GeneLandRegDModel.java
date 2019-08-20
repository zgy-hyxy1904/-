package com.bicsoft.sy.model;

import java.util.Date;

public class GeneLandRegDModel extends BaseModel {
	private String id;
	//主表主键
	private String hid;
	//承包方类型
	private String contractorType;
	//承包方证件类型
	private String idType;
	//承包方证件号码
	private String contractorID;
	//承包方姓名
	private String contractorName;
	//承包方联系方式
	private String contractorTel;
	//所在市编码 默认为五常市
	private String cityCode;
	//所在乡镇编码
	private String townCode;
	//所在村编码
	private String countryCode;
	//所在屯名称
	private String groupName;
	//本次备案面积
	private Float archiveAcreage;
	//经办人
	private String operatorName;
	//经办日期
	private Date operatorDate;
	
	//经办日期字符串
	private String operatorDateStr;
	
	//承包总面积
	private Float contractTotalYield;
	//已备案面积--本次备案时的已备案面积
	private Float registeredTotalYield;
	
	private String townName;      //乡镇名称
	private String countryName;   //村屯名称
	private String idName;        //证件类型名称
	private String contractorTypeName;  //承包方类型名称
	
	private Float zmj;//总面积            
	private Float yba; //已备案;   
	private Float kba; //可备案         
	
	private String thismj;  //本次备案面积
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHid() {
		return hid;
	}
	public void setHid(String hid) {
		this.hid = hid;
	}
	public String getContractorType() {
		return contractorType;
	}
	public void setContractorType(String contractorType) {
		this.contractorType = contractorType;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getContractorID() {
		return contractorID;
	}
	public void setContractorID(String contractorID) {
		this.contractorID = contractorID;
	}
	public String getContractorName() {
		return contractorName;
	}
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
	public String getContractorTel() {
		return contractorTel;
	}
	public void setContractorTel(String contractorTel) {
		this.contractorTel = contractorTel;
	}
	
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getTownCode() {
		return townCode;
	}
	public void setTownCode(String townCode) {
		this.townCode = townCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Float getArchiveAcreage() {
		return archiveAcreage;
	}
	public void setArchiveAcreage(Float archiveAcreage) {
		this.archiveAcreage = archiveAcreage;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Date getOperatorDate() {
		return operatorDate;
	}
	public void setOperatorDate(Date operatorDate) {
		this.operatorDate = operatorDate;
	}
	public Float getContractTotalYield() {
		return contractTotalYield;
	}
	public void setContractTotalYield(Float contractTotalYield) {
		this.contractTotalYield = contractTotalYield;
	}
	public Float getRegisteredTotalYield() {
		return registeredTotalYield;
	}
	public void setRegisteredTotalYield(Float registeredTotalYield) {
		this.registeredTotalYield = registeredTotalYield;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getIdName() {
		return idName;
	}
	public void setIdName(String idName) {
		this.idName = idName;
	}
	public String getContractorTypeName() {
		return contractorTypeName;
	}
	public void setContractorTypeName(String contractorTypeName) {
		this.contractorTypeName = contractorTypeName;
	}
	public Float getZmj() {
		return zmj;
	}
	public void setZmj(Float zmj) {
		this.zmj = zmj;
	}
	public Float getYba() {
		return yba;
	}
	public void setYba(Float yba) {
		this.yba = yba;
	}
	public Float getKba() {
		return kba;
	}
	public void setKba(Float kba) {
		this.kba = kba;
	}
	public String getOperatorDateStr() {
		return operatorDateStr;
	}
	public void setOperatorDateStr(String operatorDateStr) {
		this.operatorDateStr = operatorDateStr;
	}
	public String getThismj() {
		return thismj;
	}
	public void setThismj(String thismj) {
		this.thismj = thismj;
	}
}
