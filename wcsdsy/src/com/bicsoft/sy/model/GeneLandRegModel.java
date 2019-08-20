package com.bicsoft.sy.model;

import java.util.Date;
import java.util.List;

import com.bicsoft.sy.entity.GeneLandRegD;

public class GeneLandRegModel extends BaseModel {
	private Integer id;
	//年度
	private String year;
	//企业ID
	private String companyCode;
	//企业名称
	private String companyName;
	//申请批次号
	private String applyBatchNo;
	//审核人
	private String auditor;
	//审核时间
	private Date auditTime;
	//状态
	private String status;
	//驳回原因
	private String reason;
	
	/**
	 * 子表相关属性
	 */
	//本次备案面积
	private Float archiveAcreage;
	//经办人
	private String operatorName;
	//经办日期
	private Date operatorDate;
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
	
	//
	private String contractorTypeHidden;
	private String idTypeHidden;
	private String contractorIDHidden;
	private String contractorTelHidden;
	private String cityCodeHidden;
	private String countryCodeHidden;
	private String groupNameHidden;
	private String zmjHidden;
	private String ybamjHidden;
	private String kbamjHidden;
	private String archiveAcreageHidden;
	private String operatorNameHidden;
	private String operatorDateHidden;
	private String contractorNameHidden;
	private String townCodeHidden;
	private String contractorValue;
	
	//查询条件
	private String beginDate;
	private String endDate;
	
	//数据信息
	private List<GeneLandRegD> list;
	
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
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
	public List<GeneLandRegD> getList() {
		return list;
	}
	public void setList(List<GeneLandRegD> list) {
		this.list = list;
	}
	public String getContractorTypeHidden() {
		return contractorTypeHidden;
	}
	public void setContractorTypeHidden(String contractorTypeHidden) {
		this.contractorTypeHidden = contractorTypeHidden;
	}
	public String getIdTypeHidden() {
		return idTypeHidden;
	}
	public void setIdTypeHidden(String idTypeHidden) {
		this.idTypeHidden = idTypeHidden;
	}
	public String getContractorIDHidden() {
		return contractorIDHidden;
	}
	public void setContractorIDHidden(String contractorIDHidden) {
		this.contractorIDHidden = contractorIDHidden;
	}
	public String getContractorTelHidden() {
		return contractorTelHidden;
	}
	public void setContractorTelHidden(String contractorTelHidden) {
		this.contractorTelHidden = contractorTelHidden;
	}
	public String getCityCodeHidden() {
		return cityCodeHidden;
	}
	public void setCityCodeHidden(String cityCodeHidden) {
		this.cityCodeHidden = cityCodeHidden;
	}
	public String getCountryCodeHidden() {
		return countryCodeHidden;
	}
	public void setCountryCodeHidden(String countryCodeHidden) {
		this.countryCodeHidden = countryCodeHidden;
	}
	public String getGroupNameHidden() {
		return groupNameHidden;
	}
	public void setGroupNameHidden(String groupNameHidden) {
		this.groupNameHidden = groupNameHidden;
	}
	public String getZmjHidden() {
		return zmjHidden;
	}
	public void setZmjHidden(String zmjHidden) {
		this.zmjHidden = zmjHidden;
	}
	public String getYbamjHidden() {
		return ybamjHidden;
	}
	public void setYbamjHidden(String ybamjHidden) {
		this.ybamjHidden = ybamjHidden;
	}
	public String getKbamjHidden() {
		return kbamjHidden;
	}
	public void setKbamjHidden(String kbamjHidden) {
		this.kbamjHidden = kbamjHidden;
	}
	public String getArchiveAcreageHidden() {
		return archiveAcreageHidden;
	}
	public void setArchiveAcreageHidden(String archiveAcreageHidden) {
		this.archiveAcreageHidden = archiveAcreageHidden;
	}
	public String getOperatorNameHidden() {
		return operatorNameHidden;
	}
	public void setOperatorNameHidden(String operatorNameHidden) {
		this.operatorNameHidden = operatorNameHidden;
	}
	public String getOperatorDateHidden() {
		return operatorDateHidden;
	}
	public void setOperatorDateHidden(String operatorDateHidden) {
		this.operatorDateHidden = operatorDateHidden;
	}
	public String getContractorNameHidden() {
		return contractorNameHidden;
	}
	public void setContractorNameHidden(String contractorNameHidden) {
		this.contractorNameHidden = contractorNameHidden;
	}
	public String getTownCodeHidden() {
		return townCodeHidden;
	}
	public void setTownCodeHidden(String townCodeHidden) {
		this.townCodeHidden = townCodeHidden;
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
	public String getContractorValue() {
		return contractorValue;
	}
	public void setContractorValue(String contractorValue) {
		this.contractorValue = contractorValue;
	}
	
}
