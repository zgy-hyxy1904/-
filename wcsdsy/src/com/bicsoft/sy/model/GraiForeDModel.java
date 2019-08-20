package com.bicsoft.sy.model;

public class GraiForeDModel extends BaseModel {
	private Integer id;
	private Integer hid;
	private String landType;
	//实亩
	private Float ActualMu;
	//测量亩
	private Float measurementMu;
	private String landClass;
	private String landName;
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
	public String getLandClass() {
		return landClass;
	}
	public void setLandClass(String landClass) {
		this.landClass = landClass;
	}
	public String getLandName() {
		return landName;
	}
	public void setLandName(String landName) {
		this.landName = landName;
	}
}
