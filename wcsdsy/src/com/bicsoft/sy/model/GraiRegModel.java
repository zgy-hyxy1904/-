package com.bicsoft.sy.model;

import java.util.Date;

import javax.persistence.Transient;

/**
 * 收粮备案
 * @author 创新中软
 * @date 2015-08-17
 */
public class GraiRegModel extends BaseModel {
	private Integer id;
	private String year;
	//企业ID
	private String companyCode;
	//企业名称
	private String companyName; 
	//农户姓名
	private String farmerName;
	//身份证号
	private String idNumber;
	//申请批次号
	private String cityCode;
	//所在乡镇
	private String townCode;
	//所在村
	private String countryCode;
	//所在屯
	private String groupName;
	//本次卖出重量
	private Float thisWeight;
	//经办人
	private String operatorName;
	//经办日期
	private Date operatorDate;
	//业务流水
	private String bizCodePlowland;
	private String bizCodeSeed;
	private String bizCodeYield;
	
	//查询条件
	private String beginDate;
	private String endDate;
	
	//---------------子表信息
	//实(亩)
	private Float actualMu;
	//测量(亩)
	private Float measurementMu; 
	//预估总产量
	private Float estimateTotalYield;
	//购种重量合计
	private Float seedPurchaseTotal;
	//已卖出
	private Float soldYield;
	//sheng余产量剩余产量
	private Float surplusYield;
	//已备案面积合计
	private Float registeredTotalYield;
	//可收粮面积合计GrainTotalYield
	private Float grainTotalYield;
	private Float dkzmj;
	
	private String retFlag;
	
	public String getRetFlag() {
		return retFlag;
	}
	public void setRetFlag(String retFlag) {
		this.retFlag = retFlag;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getFarmerName() {
		return farmerName;
	}
	public void setFarmerName(String farmerName) {
		this.farmerName = farmerName;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
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
	public Float getThisWeight() {
		return thisWeight;
	}
	public void setThisWeight(Float thisWeight) {
		this.thisWeight = thisWeight;
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
	public String getBizCodePlowland() {
		return bizCodePlowland;
	}
	public void setBizCodePlowland(String bizCodePlowland) {
		this.bizCodePlowland = bizCodePlowland;
	}
	public String getBizCodeSeed() {
		return bizCodeSeed;
	}
	public void setBizCodeSeed(String bizCodeSeed) {
		this.bizCodeSeed = bizCodeSeed;
	}
	public String getBizCodeYield() {
		return bizCodeYield;
	}
	public void setBizCodeYield(String bizCodeYield) {
		this.bizCodeYield = bizCodeYield;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Float getActualMu() {
		return actualMu;
	}
	public void setActualMu(Float actualMu) {
		this.actualMu = actualMu;
	}
	public Float getMeasurementMu() {
		return measurementMu;
	}
	public void setMeasurementMu(Float measurementMu) {
		this.measurementMu = measurementMu;
	}
	public Float getEstimateTotalYield() {
		return estimateTotalYield;
	}
	public void setEstimateTotalYield(Float estimateTotalYield) {
		this.estimateTotalYield = estimateTotalYield;
	}
	public Float getSeedPurchaseTotal() {
		return seedPurchaseTotal;
	}
	public void setSeedPurchaseTotal(Float seedPurchaseTotal) {
		this.seedPurchaseTotal = seedPurchaseTotal;
	}
	public Float getSoldYield() {
		return soldYield;
	}
	public void setSoldYield(Float soldYield) {
		this.soldYield = soldYield;
	}
	public Float getSurplusYield() {
		return surplusYield;
	}
	public void setSurplusYield(Float surplusYield) {
		this.surplusYield = surplusYield;
	}
	public Float getRegisteredTotalYield() {
		return registeredTotalYield;
	}
	public void setRegisteredTotalYield(Float registeredTotalYield) {
		this.registeredTotalYield = registeredTotalYield;
	}
	public Float getGrainTotalYield() {
		return grainTotalYield;
	}
	public void setGrainTotalYield(Float grainTotalYield) {
		this.grainTotalYield = grainTotalYield;
	}
	@Transient
	public Float getDkzmj() {
		return dkzmj;
	}
	public void setDkzmj(Float dkzmj) {
		this.dkzmj = dkzmj;
	}
}
