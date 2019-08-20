package com.bicsoft.sy.model;

import java.util.Date;

public class BlackListDModel extends BaseModel {
	private Integer id;
	//企业ID
	private String companyCode;
	//企业名称
	private String companyName;
	//企业类型
	private String companyType;
	//拉黑开始日期
	private Date blackListStartDate;
	//拉黑截止日期
	private Date blackListEndDate;
	//原因
	private String blackListReason;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public Date getBlackListStartDate() {
		return blackListStartDate;
	}
	public void setBlackListStartDate(Date blackListStartDate) {
		this.blackListStartDate = blackListStartDate;
	}
	public Date getBlackListEndDate() {
		return blackListEndDate;
	}
	public void setBlackListEndDate(Date blackListEndDate) {
		this.blackListEndDate = blackListEndDate;
	}
	public String getBlackListReason() {
		return blackListReason;
	}
	public void setBlackListReason(String blackListReason) {
		this.blackListReason = blackListReason;
	}
}
