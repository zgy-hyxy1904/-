package com.bicsoft.sy.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 过程监控
 * @author 创新中软
 * @date 2015-08-20
 */
@Entity
@Table(name="b_process_monitoring")
public class ProcMoni extends BaseEntity {
	private Integer id;
	private String year;
	private String companyCode;
	private String companyName;
	private String bizType;
	private String bizCode;
	private List<Mfile> list;
	
	@Id
	@GeneratedValue
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
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public String getBizCode() {
		return bizCode;
	}
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	@Transient
	public List<Mfile> getList() {
		return list;
	}
	public void setList(List<Mfile> list) {
		this.list = list;
	}
}
