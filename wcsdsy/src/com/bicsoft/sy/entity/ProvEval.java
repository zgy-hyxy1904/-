package com.bicsoft.sy.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 种源评估
 * @author 创新中软
 * @date 2015-08-17
 */
@Entity
@Table(name="b_ProvenanceEvaluation")
public class ProvEval extends BaseEntity {
	private Integer id;
	//年度
	private String year;
	//种子品种编号
	private String seedCode;
	//最小亩产量
	private Float minYield;
	//最大亩产量
	private Float maxYield;
	
	private String companyCode;
	
	@Id
	@GeneratedValue
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
	public String getSeedCode() {
		return seedCode;
	}
	public void setSeedCode(String seedCode) {
		this.seedCode = seedCode;
	}
	public Float getMinYield() {
		return minYield;
	}
	public void setMinYield(Float minyield) {
		this.minYield = minyield;
	}
	public Float getMaxYield() {
		return maxYield;
	}
	public void setMaxYield(Float maxyield) {
		this.maxYield = maxyield;
	}
	@Transient
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
}
