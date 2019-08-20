package com.bicsoft.sy.model;

import java.util.Date;

public class BlackListModel extends BaseModel{
	private Integer id;
	//企业ID
	private String companyCode;
	//企业名称
	private String companyName;
	//企业类型
	private String companyType;
	//拉黑次数
	private Integer blackListCount;
	//拉黑截止日期
	private Date blackListEndDate;
	//最后一次拉黑原因
	private String blackListReason;
	
	private String beginDate;
	private String endDate;
	
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
	public Integer getBlackListCount() {
		return blackListCount;
	}
	public void setBlackListCount(Integer blackListCount) {
		this.blackListCount = blackListCount;
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
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
