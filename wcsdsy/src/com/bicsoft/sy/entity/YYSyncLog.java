package com.bicsoft.sy.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 数据同步日志Entity
 * 
 * @author 创新中软
 * @date 2015-08-18
 */
@Entity
@Table(name = "YY_SYNC_LOG")
public class YYSyncLog {
	private Integer id;
	//业务类型
	private String bizType;
	//同步时间
	private Date syncDate;
	//同步状态
	private Integer syncStatus;
	//同步数量
	private Integer syncCount;
	//数据流向
	private Integer dataPath;
	//处理时间
	private Date createDate;
	
	/** default constructor */
	public YYSyncLog() {
	}
  
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId()
	{
		return this.id;
	}
	  
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "BizType")
	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	
	@Column(name = "SyncDate")
	public Date getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(Date syncDate) {
		this.syncDate = syncDate;
	}

	@Column(name = "SyncStatus")
	public Integer getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}

	@Column(name = "SyncCount")
	public Integer getSyncCount() {
		return syncCount;
	}

	public void setSyncCount(Integer syncCount) {
		this.syncCount = syncCount;
	}

	@Column(name = "DataPath")
	public Integer getDataPath() {
		return dataPath;
	}

	public void setDataPath(Integer dataPath) {
		this.dataPath = dataPath;
	}

	@Column(name = "CreateDate")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	
}
