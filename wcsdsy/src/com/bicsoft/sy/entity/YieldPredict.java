package com.bicsoft.sy.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="b_YieldPredictionH")
public class YieldPredict extends BaseEntity
{
	private Integer id;
	//企业编码
	private String companyCode;
	//企业名称
	private String companyName;
	//年度
	private String year;
	//预报日期
	private Date predictionDate;
	//本次预报产量
	private float yieldPredictionValue;
		
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CompanyCode")
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Column(name = "CompanyName")
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "Year")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "PredictionDate")
	public Date getPredictionDate() {
		return predictionDate;
	}

	public void setPredictionDate(Date predictionDate) {
		this.predictionDate = predictionDate;
	}

	@Column(name = "YieldPredictionValue")
	public float getYieldPredictionValue() {
		return yieldPredictionValue;
	}

	public void setYieldPredictionValue(float yieldPredictionValue) {
		this.yieldPredictionValue = yieldPredictionValue;
	}
}
