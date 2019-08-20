package com.bicsoft.sy.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 粮食评估
 * @author 创新中软
 * @date 2015-08-17
 */
@Entity
@Table(name="b_GrainEvaluation")
public class GraiEval extends BaseEntity {
	
	private Integer id;
	//年度
	private String year;
	//种子品种编号
	private String seedCode;
	//最小亩产量
	private Float minYield;
	//最大亩产量
	private Float maxYield;
	//出米率
	private Float milledriceRate;
	
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
	public void setMinYield(Float minYield) {
		this.minYield = minYield;
	}
	public Float getMaxYield() {
		return maxYield;
	}
	public void setMaxYield(Float maxYield) {
		this.maxYield = maxYield;
	}
	public Float getMilledriceRate() {
		return milledriceRate;
	}
	public void setMilledriceRate(Float milledriceRate) {
		this.milledriceRate = milledriceRate;
	}
}
