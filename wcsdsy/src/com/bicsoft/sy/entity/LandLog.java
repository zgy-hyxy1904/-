package com.bicsoft.sy.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 土地日志
 * 
 * @author 创新中软
 * @date 2015-08-17
 */
@Entity
@Table(name = "B_LandLog")
public class LandLog extends BaseEntity {

	// 自增长主键
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	// 日志类型 GENE=普通	SPEC=特殊
	@Column(name = "LandLogType")
	private String landLogType;
	// 批次号普通PT开头,特殊TX开头
	@Column(name = "BatchNo")
	private String batchNo;
	// 操作说明
	@Column(name = "ActionInfo")
	private String actionInfo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLandLogType() {
		return landLogType;
	}

	public void setLandLogType(String landLogType) {
		this.landLogType = landLogType;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getActionInfo() {
		return actionInfo;
	}

	public void setActionInfo(String actionInfo) {
		this.actionInfo = actionInfo;
	}

}
