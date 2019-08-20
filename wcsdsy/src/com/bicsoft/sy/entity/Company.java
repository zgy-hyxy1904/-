package com.bicsoft.sy.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 数据字典Entity
 * 
 * @author 创新中软
 * @date 2015-08-18
 */
@Entity
@Table(name = "M_Company")
public class Company extends BaseEntity{
	// 自增长主键
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	// 企业编码
	@Column(name = "CompanyCode")
	private String companyCode;
	// 企业名称
	@Column(name = "CompanyName")
	private String companyName;
	// 企业法人
	@Column(name = "LegalPerson")
	private String legalPerson;
	// 企业法人身份证
	@Column(name = "LegalPersonID")
	private String legalPersonID;
	//企业地址
	@Column(name = "Address")
	private String address;
	//注册日期
	@Column(name = "RegisterDate")
	private Date registerDate;
	//联系人
	@Column(name = "ConnectName")
	private String connectName;
	//联系电话
	@Column(name = "ConnectPhone")
	private String connectPhone;
	//企业类型 01-育种，02-非育种
	@Column(name = "CompanyType")
	private String companyType;

	/** default constructor */
	public Company(){
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getConnectPhone() {
		return connectPhone;
	}

	public void setConnectPhone(String connectPhone) {
		this.connectPhone = connectPhone;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getLegalPersonID() {
		return legalPersonID;
	}

	public void setLegalPersonID(String legalPersonID) {
		this.legalPersonID = legalPersonID;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getConnectName() {
		return connectName;
	}

	public void setConnectName(String connectName) {
		this.connectName = connectName;
	}
}
