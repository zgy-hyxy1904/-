package com.bicsoft.sy.model;

import java.util.List;

import com.bicsoft.sy.entity.Mfile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcMoniModel extends BaseModel {
	private Integer id;
	private String year;
	private String companyCode;
	private String companyName;
	private String bizType;
	private String bizCode;
	private List<Mfile> list;
	
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
	public List<Mfile> getList() {
		return list;
	}
	public void setList(List<Mfile> list) {
		this.list = list;
	}
}
