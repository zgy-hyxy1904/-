package com.bicsoft.sy.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "m_monitorpoint")
public class MoniPoint extends BaseEntity{
	// 自增长主键
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	private String monitorPointCode;
	private String monitorPointType;
	private String monitorPointName;
	private String monitorPointAddress;
	private String sectionDescription;
	private String longitude;  //经度
	private String latitude;    //纬度;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMonitorPointCode() {
		return monitorPointCode;
	}
	public void setMonitorPointCode(String monitorPointCode) {
		this.monitorPointCode = monitorPointCode;
	}
	public String getMonitorPointType() {
		return monitorPointType;
	}
	public void setMonitorPointType(String monitorPointType) {
		this.monitorPointType = monitorPointType;
	}
	public String getMonitorPointName() {
		return monitorPointName;
	}
	public void setMonitorPointName(String monitorPointName) {
		this.monitorPointName = monitorPointName;
	}
	public String getMonitorPointAddress() {
		return monitorPointAddress;
	}
	public void setMonitorPointAddress(String monitorPointAddress) {
		this.monitorPointAddress = monitorPointAddress;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getSectionDescription() {
		return sectionDescription;
	}
	public void setSectionDescription(String sectionDescription) {
		this.sectionDescription = sectionDescription;
	}
}