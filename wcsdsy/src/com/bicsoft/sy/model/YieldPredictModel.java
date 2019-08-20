package com.bicsoft.sy.model;

public class YieldPredictModel
{
	//年度
	private String year;
	//企业编码
	private String companyCode;
	//预报日期
	private String predictDate;
	
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
	
	public String getPredictDate() {
		return predictDate;
	}
	
	public void setPredictDate(String predictDate) {
		this.predictDate = predictDate;
	}
}
