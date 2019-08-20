package com.bicsoft.sy.model;

public class GeneLandDetailModel extends BaseModel {
	private Integer id;
	//普通-备案-子表主键
	private Integer hid;
	//土地类型
	private String landType;
	//实(亩)
	private Float actualMu;
	//测量(亩)
	private Float measurementmMu;
	//土地类别
	private String landClass;
	//位置
	private String location;
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
	public Float getMeasurementmMu() {
		return measurementmMu;
	}
	public void setMeasurementmMu(Float measurementmMu) {
		this.measurementmMu = measurementmMu;
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
}
