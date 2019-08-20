package com.bicsoft.sy.model;

public class ProvEvalModel extends BaseModel{
	private Integer id;
	//年度
	private String year;
	//种子品种编号
	private String seedCode;
	//最小亩产量
	private Float minYield;
	//最大亩产量
	private Float maxYield;
	
	private String companyCode;
	
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
	public String getSeedCode() {
		return seedCode;
	}
	public void setSeedCode(String seedCode) {
		this.seedCode = seedCode;
	}
	public Float getMinYield() {
		return minYield;
	}
	public void setMinYield(Float minYield) {
		this.minYield = minYield;
	}
	public Float getMaxYield() {
		return maxYield;
	}
	public void setMaxYield(Float maxYield) {
		this.maxYield = maxYield;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
}
