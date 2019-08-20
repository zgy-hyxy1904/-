package com.bicsoft.sy.model;

public class GraiRegDetailModel extends BaseModel {
	private Integer id;
	private Integer hid;
	//土地类型
	private String landType;
	//实(亩)
	private Float actualMu;
	//测量(亩)
		private Float measurementMu;
	//土地类别
	private String landClass;
	//位置
	private String location;
	//预估总产量
	private Float estimateTotalYield;
	//购种重量合计
	private Float seedPurchaseTotal;
	//已卖出
	private Float soldYield;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getHid() {
		return hid;
	}
	public void setHid(Integer hid) {
		this.hid = hid;
	}
	public String getLandType() {
		return landType;
	}
	public void setLandType(String landType) {
		this.landType = landType;
	}
	public Float getActualMu() {
		return actualMu;
	}
	public void setActualMu(Float actualMu) {
		this.actualMu = actualMu;
	}
	public String getLandClass() {
		return landClass;
	}
	public void setLandClass(String landClass) {
		this.landClass = landClass;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
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
}
