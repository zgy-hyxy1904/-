package com.bicsoft.sy.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="b_Quality")
public class Quality extends BaseEntity
{
	private Integer id;
	//年度
	private String year;
	//企业编码
	private String companyCode;
	//质检编号
	private String checkCode;
	//送检日期
	private Date deliveryDate;
	//质检日期
	private Date inspectDate;
	//企业送检人
	private String deliveryPerson;
	//质检中心交接人
	private String handoverPerson;
	//质检中心交接时间
	private Date handoverTime;
	//质检员
	private String inspectPerson;
	//质检结论
	private String inspectStatus;
	//质检报告
	private String path;
	
	/** default constructor */
	public Quality() {
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

	@Column(name = "Year")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@Column(name = "CompanyCode")
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Column(name = "CheckCode")
	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	@Column(name = "DeliveryDate")
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@Column(name = "InspectDate")
	public Date getInspectDate() {
		return inspectDate;
	}

	public void setInspectDate(Date inspectDate) {
		this.inspectDate = inspectDate;
	}

	@Column(name = "DeliveryPerson")
	public String getDeliveryPerson() {
		return deliveryPerson;
	}

	public void setDeliveryPerson(String deliveryPerson) {
		this.deliveryPerson = deliveryPerson;
	}

	@Column(name = "HandoverPerson")
	public String getHandoverPerson() {
		return handoverPerson;
	}

	public void setHandoverPerson(String handoverPerson) {
		this.handoverPerson = handoverPerson;
	}

	@Column(name = "HandoverTime")
	public Date getHandoverTime() {
		return handoverTime;
	}

	public void setHandoverTime(Date handoverTime) {
		this.handoverTime = handoverTime;
	}

	@Column(name = "InspectPerson")
	public String getInspectPerson() {
		return inspectPerson;
	}

	public void setInspectPerson(String inspectPerson) {
		this.inspectPerson = inspectPerson;
	}

	@Column(name = "InspectStatus")
	public String getInspectStatus() {
		return inspectStatus;
	}

	public void setInspectStatus(String inspectStatus) {
		this.inspectStatus = inspectStatus;
	}

	@Column(name = "Path")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
