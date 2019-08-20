package com.bicsoft.sy.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 种子公司投诉
 * @author 创新中软
 * @date 2015-08-26
 */
@Entity
@Table(name="b_SeedComplaint")
public class SeedCPLTModel extends BaseModel {
	private Integer id;
	//投诉日期
	private Date complaintDate;
	//投诉人
	private String complainant;
	//性别
	private String complainantSex;
	//投诉人电话
	private String complainantTel;
	//邮箱
	private String complainantMail;
	//投诉重量
	private Float complaintWeight;
	//内容
	private String complaintContent;
	//处理日期
	private Date settleDate;
	//处理人
	private String settlePepole;
	//处理人
	private String processor;
	//处理状态
	private String settleStatus; 
	//处理人电话
	private String processorsTel;
	//处理邮箱
	private String processorsMail;
	//处理企业编号
	private String companyCode;
	//处理企业名称
	private String companyName;
	//处理方式
	private String processMode;
	//处理人
	private String processorName;
	//处理重量
	private Float productionWeight;
	//处理结果概述
	private String processResult;
	//驳回原因
	private String rejectReason;
	//购种日期
	private Date buySeedsDate;
	//加入黑名单
	private String addBlackList;
	//产品召回
	private String recall;
	//时长
	private String blackListTimeLimit;
	//原因
	private String blackListReason;
	//拉黑截止日期
	private Date blackListEndDate;
	//召回批次号
	private String batchNo;
	//召回原因
	private String recallReason;
	
	//查询条件字段
	private String complaintBeginDate;
	private String complaintEndDate;
	private String settleBeginDate;
	private String settleEndDate;
	
	private String beginDate;
	private String endDate;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getComplaintDate() {
		return complaintDate;
	}
	public void setComplaintDate(Date complaintDate) {
		this.complaintDate = complaintDate;
	}
	public String getComplainant() {
		return complainant;
	}
	public void setComplainant(String complainant) {
		this.complainant = complainant;
	}
	public String getComplainantTel() {
		return complainantTel;
	}
	public void setComplainantTel(String complainantTel) {
		this.complainantTel = complainantTel;
	}
	public String getComplainantMail() {
		return complainantMail;
	}
	public void setComplainantMail(String complainantMail) {
		this.complainantMail = complainantMail;
	}
	public Float getComplaintWeight() {
		return complaintWeight;
	}
	public void setComplaintWeight(Float complaintWeight) {
		this.complaintWeight = complaintWeight;
	}
	public String getComplaintContent() {
		return complaintContent;
	}
	public void setComplaintContent(String complaintContent) {
		this.complaintContent = complaintContent;
	}
	public Date getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}
	public String getSettlePepole() {
		return settlePepole;
	}
	public void setSettlePepole(String settlePepole) {
		this.settlePepole = settlePepole;
	}
	public String getProcessorsTel() {
		return processorsTel;
	}
	public void setProcessorsTel(String processorsTel) {
		this.processorsTel = processorsTel;
	}
	public String getProcessorsMail() {
		return processorsMail;
	}
	public void setProcessorsMail(String processorsMail) {
		this.processorsMail = processorsMail;
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
	public String getProcessMode() {
		return processMode;
	}
	public void setProcessMode(String processMode) {
		this.processMode = processMode;
	}
	public String getProcessorName() {
		return processorName;
	}
	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}
	public Float getProductionWeight() {
		return productionWeight;
	}
	public void setProductionWeight(Float productionWeight) {
		this.productionWeight = productionWeight;
	}
	public String getProcessResult() {
		return processResult;
	}
	public void setProcessResult(String processResult) {
		this.processResult = processResult;
	}
	public String getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	public String getSettleStatus() {
		return settleStatus;
	}
	public void setSettleStatus(String settleStatus) {
		this.settleStatus = settleStatus;
	}
	public String getComplaintBeginDate() {
		return complaintBeginDate;
	}
	public void setComplaintBeginDate(String complaintBeginDate) {
		this.complaintBeginDate = complaintBeginDate;
	}
	public String getComplaintEndDate() {
		return complaintEndDate;
	}
	public void setComplaintEndDate(String complaintEndDate) {
		this.complaintEndDate = complaintEndDate;
	}
	public String getSettleBeginDate() {
		return settleBeginDate;
	}
	public void setSettleBeginDate(String settleBeginDate) {
		this.settleBeginDate = settleBeginDate;
	}
	public String getSettleEndDate() {
		return settleEndDate;
	}
	public void setSettleEndDate(String settleEndDate) {
		this.settleEndDate = settleEndDate;
	}
	public String getComplainantSex() {
		return complainantSex;
	}
	public void setComplainantSex(String complainantSex) {
		this.complainantSex = complainantSex;
	}
	public Date getBuySeedsDate() {
		return buySeedsDate;
	}
	public void setBuySeedsDate(Date buySeedsDate) {
		this.buySeedsDate = buySeedsDate;
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public String getAddBlackList() {
		return addBlackList;
	}
	public void setAddBlackList(String addBlackList) {
		this.addBlackList = addBlackList;
	}
	public String getRecall() {
		return recall;
	}
	public void setRecall(String recall) {
		this.recall = recall;
	}
	public String getBlackListTimeLimit() {
		return blackListTimeLimit;
	}
	public void setBlackListTimeLimit(String blackListTimeLimit) {
		this.blackListTimeLimit = blackListTimeLimit;
	}
	public String getBlackListReason() {
		return blackListReason;
	}
	public void setBlackListReason(String blackListReason) {
		this.blackListReason = blackListReason;
	}
	public Date getBlackListEndDate() {
		return blackListEndDate;
	}
	public void setBlackListEndDate(Date blackListEndDate) {
		this.blackListEndDate = blackListEndDate;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getRecallReason() {
		return recallReason;
	}
	public void setRecallReason(String recallReason) {
		this.recallReason = recallReason;
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
