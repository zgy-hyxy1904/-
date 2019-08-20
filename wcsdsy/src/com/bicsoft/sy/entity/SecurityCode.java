package com.bicsoft.sy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="TB_SECURITY_H")
public class SecurityCode extends BaseEntity
{
	private Integer id;
	//企业编码
	private String companyCode;
	//年度
	private String year;
	//申请总数量
	private Integer applyTotalNum;
	//激活数量
	private Integer activationTotalNum;
	
	
	/**
	 * @return the id
	 */
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

	@Column(name = "Year")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "ApplyTotalNum")
	public Integer getApplyTotalNum() {
		return applyTotalNum;
	}

	public void setApplyTotalNum(Integer applyTotalNum) {
		this.applyTotalNum = applyTotalNum;
	}

	@Column(name = "ActivationTotalNum")
	public Integer getActivationTotalNum() {
		return activationTotalNum;
	}

	public void setActivationTotalNum(Integer activationTotalNum) {
		this.activationTotalNum = activationTotalNum;
	}
}
