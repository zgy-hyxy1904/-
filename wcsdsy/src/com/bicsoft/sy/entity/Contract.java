package com.bicsoft.sy.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="M_Contract")
public class Contract
{
	private Integer id;
	//承包方代码
	private String contractorCode;
	//图幅编码
	private String graphCode;
	//地块编码
	private String landCode;
	//地块名称
	private String landName;
	//合同面积(亩)
	private float contractArea;
	//测量面积(亩)
	private float measurementMu;
	//东至
	private String eastTo;
	//西至
	private String westTo;
	//南至
	private String southTo;
	//北至
	private String northTo;
	//等级
	private String landLevel;
	//土地类型 
	private String landType;
	//是否基本农田
	private String isBaseLand;
	//所有制性质
	private String ownership;
	//土地类别
	private String landClass;
	//争议原因
	private String disputeReason;
	//承包土地用途
	private String landPurpose;
	//承包开始日期
	private Date contractStartDate;
	//承包期限
	private Integer contractYear;
	//承包终止日期
	private Date contractEndDate;
		
	//土地类型
	private String landTypeName;
	//土地类别
	private String landClassName;
	//总面积
	private Float zmj;
	//实亩
	private Float actualMu;
	
	/** default constructor */
	public Contract() {
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

	@Column(name = "ContractorCode")
	public String getContractorCode() {
		return contractorCode;
	}

	public void setContractorCode(String contractorCode) {
		this.contractorCode = contractorCode;
	}

	@Column(name = "GraphCode")
	public String getGraphCode() {
		return graphCode;
	}

	public void setGraphCode(String graphCode) {
		this.graphCode = graphCode;
	}

	@Column(name = "LandCode")
	public String getLandCode() {
		return landCode;
	}

	public void setLandCode(String landCode) {
		this.landCode = landCode;
	}

	@Column(name = "LandName")
	public String getLandName() {
		return landName;
	}

	public void setLandName(String landName) {
		this.landName = landName;
	}

	@Column(name = "ContractArea")
	public float getContractArea() {
		return contractArea;
	}

	public void setContractArea(float contractArea) {
		this.contractArea = contractArea;
	}

	@Column(name = "MeasurementMu")
	public float getMeasurementMu() {
		return measurementMu;
	}

	public void setMeasurementMu(float measurementMu) {
		this.measurementMu = measurementMu;
	}

	@Column(name = "EastTo")
	public String getEastTo() {
		return eastTo;
	}

	public void setEastTo(String eastTo) {
		this.eastTo = eastTo;
	}

	@Column(name = "WestTo")
	public String getWestTo() {
		return westTo;
	}

	public void setWestTo(String westTo) {
		this.westTo = westTo;
	}

	@Column(name = "SouthTo")
	public String getSouthTo() {
		return southTo;
	}

	public void setSouthTo(String southTo) {
		this.southTo = southTo;
	}

	@Column(name = "NorthTo")
	public String getNorthTo() {
		return northTo;
	}

	public void setNorthTo(String northTo) {
		this.northTo = northTo;
	}

	@Column(name = "LandLevel")
	public String getLandLevel() {
		return landLevel;
	}

	public void setLandLevel(String landLevel) {
		this.landLevel = landLevel;
	}

	@Column(name = "LandType")
	public String getLandType() {
		return landType;
	}

	public void setLandType(String landType) {
		this.landType = landType;
	}

	@Column(name = "IsBaseLand")
	public String getIsBaseLand() {
		return isBaseLand;
	}

	public void setIsBaseLand(String isBaseLand) {
		this.isBaseLand = isBaseLand;
	}

	@Column(name = "Ownership")
	public String getOwnership() {
		return ownership;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	@Column(name = "LandClass")
	public String getLandClass() {
		return landClass;
	}

	public void setLandClass(String landClass) {
		this.landClass = landClass;
	}

	@Column(name = "DisputeReason")
	public String getDisputeReason() {
		return disputeReason;
	}

	public void setDisputeReason(String disputeReason) {
		this.disputeReason = disputeReason;
	}

	@Column(name = "LandPurpose")
	public String getLandPurpose() {
		return landPurpose;
	}

	public void setLandPurpose(String landPurpose) {
		this.landPurpose = landPurpose;
	}

	@Column(name = "ContractStartDate")
	public Date getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	@Column(name = "ContractYear")
	public Integer getContractYear() {
		return contractYear;
	}

	public void setContractYear(Integer contractYear) {
		this.contractYear = contractYear;
	}

	@Column(name = "ContractEndDate")
	public Date getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	@Transient
	public String getLandTypeName() {
		return landTypeName;
	}

	public void setLandTypeName(String landTypeName) {
		this.landTypeName = landTypeName;
	}
	@Transient
	public String getLandClassName() {
		return landClassName;
	}

	public void setLandClassName(String landClassName) {
		this.landClassName = landClassName;
	}

	@Transient
	public Float getZmj() {
		return zmj;
	}

	public void setZmj(Float zmj) {
		this.zmj = zmj;
	}

	@Transient
	public Float getActualMu() {
		return actualMu;
	}

	public void setActualMu(Float actualMu) {
		this.actualMu = actualMu;
	}
	
}
