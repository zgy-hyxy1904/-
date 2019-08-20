package com.bicsoft.sy.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpecLandRegModel extends BaseModel{
	private Integer id;
	private Integer detailId;
	//年度
	private String year;
	//企业ID
	private String companyCode;
	//企业名称
	private String companyName;
	//申请批次号
	private String applyBatchNo;
	//承包方类型
	private String contractorType;
	//承包方证件类型
	private String idType;
	//承包方证件号码
	private String contractorID;
	//承包方姓名
	private String contractorName;
	//承包方联系方式
	private String contractorTel;
	//所在市编码 默认为五常市
	private String cityCode;
	//所在乡镇编码
	private String townCode;
	//所在村编码
	private String countryCode;
	//所在屯名称
	private String groupName;
	//本次备案面积
	private Float archiveAcreage;
	//经办人
	private String operatorName;
	//经办日期
	private Date operatorDate;
	//情况说明
	private String description;
	//审核人
	private String auditor;
	//审核时间
	private Date auditTime;
	//状态
	private String status;
	
	//驳回原因
	@JsonIgnoreProperties(ignoreUnknown = true)
	private String reason;
	
	//子表属性
	//实(亩)
	private Float actualMu;
	//土地类型
	@JsonIgnoreProperties(ignoreUnknown = true)
	private String landType;
	//土地类别
	@JsonIgnoreProperties(ignoreUnknown = true)
	private String landClass;
	
	//cityCode市
	@JsonIgnoreProperties(ignoreUnknown = true)
	private String cityCodeDetail;
	//所在乡镇编码
	@JsonIgnoreProperties(ignoreUnknown = true)
	private String townCodeDetail;
	//所在村编码
	@JsonIgnoreProperties(ignoreUnknown = true)
	private String countryCodeDetail;
	//所在屯名称
	@JsonIgnoreProperties(ignoreUnknown = true)
	private String groupNameDetail;
	
	//查询条件
	private String beginDate;
	private String endDate;
	
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
	public String getApplyBatchNo() {
		return applyBatchNo;
	}
	public void setApplyBatchNo(String applyBatchNo) {
		this.applyBatchNo = applyBatchNo;
	}
	public String getContractorType() {
		return contractorType;
	}
	public void setContractorType(String contractorType) {
		this.contractorType = contractorType;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getContractorID() {
		return contractorID;
	}
	public void setContractorID(String contractorID) {
		this.contractorID = contractorID;
	}
	public String getContractorName() {
		return contractorName;
	}
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
	public String getContractorTel() {
		return contractorTel;
	}
	public void setContractorTel(String contractorTel) {
		this.contractorTel = contractorTel;
	}
	public Float getArchiveAcreage() {
		return archiveAcreage;
	}
	public void setArchiveAcreage(Float archiveAcreage) {
		this.archiveAcreage = archiveAcreage;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Date getOperatorDate() {
		return operatorDate;
	}
	public void setOperatorDate(Date operatorDate) {
		this.operatorDate = operatorDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@JsonIgnoreProperties(ignoreUnknown = true)
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
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
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getTownCodeDetail() {
		return townCodeDetail;
	}
	public void setTownCodeDetail(String townCodeDetail) {
		this.townCodeDetail = townCodeDetail;
	}
	public String getCountryCodeDetail() {
		return countryCodeDetail;
	}
	public void setCountryCodeDetail(String countryCodeDetail) {
		this.countryCodeDetail = countryCodeDetail;
	}
	public String getGroupNameDetail() {
		return groupNameDetail;
	}
	public void setGroupNameDetail(String groupNameDetail) {
		this.groupNameDetail = groupNameDetail;
	}
	public Integer getDetailId() {
		return detailId;
	}
	public void setDetailId(Integer detailId) {
		this.detailId = detailId;
	}
	public String getCityCodeDetail() {
		return cityCodeDetail;
	}
	public void setCityCodeDetail(String cityCodeDetail) {
		this.cityCodeDetail = cityCodeDetail;
	}
}
