package com.bicsoft.sy.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 土壤监测
 * @author 创新中软
 * @date 2015-08-17
 */
@Entity
@Table(name="b_SoilMonitoring")
public class SoilMoni extends BaseEntity {
	private Integer id;
	//监测日期
	private Date monitorDate;
	//监测点位
	private String monitorPointCode;
	private String monitorPointName;
	//有机质
	private Float omvalue;
	//碱解氮
	private Float alkelinen;
	//有效磷
	private Float olsenp;
	//速效钾
	private Float olsenk;
	//PH值
	private Float ph;
	
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
	public Float getOmvalue() {
		return omvalue;
	}
	public void setOmvalue(Float omvalue) {
		this.omvalue = omvalue;
	}
	public Float getAlkelinen() {
		return alkelinen;
	}
	public void setAlkelinen(Float alkelinen) {
		this.alkelinen = alkelinen;
	}
	public Float getOlsenp() {
		return olsenp;
	}
	public void setOlsenp(Float olsenp) {
		this.olsenp = olsenp;
	}
	public Float getOlsenk() {
		return olsenk;
	}
	public void setOlsenk(Float olsenk) {
		this.olsenk = olsenk;
	}
	public Float getPh() {
		return ph;
	}
	public void setPh(Float ph) {
		this.ph = ph;
	}
	@Transient
	public String getMonitorPointName() {
		return monitorPointName;
	}
	public void setMonitorPointName(String monitorPointName) {
		this.monitorPointName = monitorPointName;
	}
	
}
