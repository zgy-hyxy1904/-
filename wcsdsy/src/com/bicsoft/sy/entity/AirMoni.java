package com.bicsoft.sy.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 大气监测
 * @author 创新中软
 * @date 2015-08-17
 */
@Entity
@Table(name="b_AirMonitoring")
public class AirMoni extends BaseEntity {
	
	private Integer id;
	//监测日期
	private Date monitorDate;
	//监测点位
	private String monitorPointCode;
	private String monitorPointName;
	//tsp
	private Float tsp;
	//SO2
	private Float so2;
	//NO2
	private Float no2;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getMonitorDate() {
		return monitorDate;
	}
	public void setMonitorDate(Date monitorDate) {
		this.monitorDate = monitorDate;
	}
	public String getMonitorPointCode() {
		return monitorPointCode;
	}
	public void setMonitorPointCode(String monitorPointCode) {
		this.monitorPointCode = monitorPointCode;
	}
	public Float getTsp() {
		return tsp;
	}
	public void setTsp(Float tsp) {
		this.tsp = tsp;
	}
	public Float getSo2() {
		return so2;
	}
	public void setSo2(Float so2) {
		this.so2 = so2;
	}
	public Float getNo2() {
		return no2;
	}
	public void setNo2(Float no2) {
		this.no2 = no2;
	}
	@Transient
	public String getMonitorPointName() {
		return monitorPointName;
	}
	public void setMonitorPointName(String monitorPointName) {
		this.monitorPointName = monitorPointName;
	}
}
