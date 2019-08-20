package com.bicsoft.sy.model;

import java.util.Date;

public class GraiForeModel extends BaseModel {
	private Integer id;
	private String year;
	//企业ID
	private String companyCode;
	//企业名称
	private String companyName;
	//预报日期
	private Date forecastDate;
	//农户姓名
	private String farmerName;
	//身份证号
	private String idNumber;
	//实亩合计
	private Float ActualMu;
	//测量亩合计
	private Float measurementMu;
	//预估总产量
	private Float estimateTotalYield;
	
	private String beginDate;
	private String endDate;
	
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
	public Date getForecastDate() {
		return forecastDate;
	}
	public void setForecastDate(Date forecastDate) {
		this.forecastDate = forecastDate;
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
	public Float getActualMu() {
		return ActualMu;
	}
	public void setActualMu(Float actualMu) {
		ActualMu = actualMu;
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
}
