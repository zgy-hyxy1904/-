package com.bicsoft.sy.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 黑名单管理--子表
 * @author 创新中软
 * @date 2015-08-17
 */
@Entity
@Table(name="b_BlackListManageD")
public class BlackListD extends BaseEntity {
	private Integer id;
	//企业ID
	private String companyCode;
	//拉黑截止日期
	private Date blackListEndDate;
	//原因
	private String blackListReason;
	//处理人
	private String processor;
	//时长
	private String blackListTimeLimit;
	//处理日期
	private Date settleDate;
	
	@Id
	@GeneratedValue
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
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	public String getBlackListTimeLimit() {
		return blackListTimeLimit;
	}
	public void setBlackListTimeLimit(String blackListTimeLimit) {
		this.blackListTimeLimit = blackListTimeLimit;
	}
	public Date getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}
}