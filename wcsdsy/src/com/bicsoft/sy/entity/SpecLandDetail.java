package com.bicsoft.sy.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 特殊土地备案详情表
 * @author 
 * @date 2015-08-18
 */
@Entity
@Table(name="b_SpecialLandRegDetails")
public class SpecLandDetail extends BaseEntity {
	private Integer id;
	private Integer hid;
	//实(亩)
	private Float actualMu;
	//土地类型
	private String landType;
	//土地类别
	private String landClass;
	//所在市编码 默认为五常市
	private String cityCode;
	//所在乡镇编码
	private String townCode;
	//所在村编码
	private String countryCode;
	//所在屯名称
	private String groupName;
	
	@Id
	@GeneratedValue
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
	public Float getActualMu() {
		return actualMu;
	}
	public void setActualMu(Float actualMu) {
		this.actualMu = actualMu;
	}
	public String getLandType() {
		return landType;
	}
	public void setLandType(String landType) {
		this.landType = landType;
	}
	public String getLandClass() {
		return landClass;
	}
	public void setLandClass(String landClass) {
		this.landClass = landClass;
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
}
