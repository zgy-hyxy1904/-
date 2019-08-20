package com.bicsoft.sy.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="b_Samplings")
public class Sample extends BaseEntity
{
	private Integer id;
	//年度
	private String year;
	//企业编码
	private String companyCode;
	//质检编号
	private String checkCode;
	//抽样时间
	private Date sampleDate;
	//抽样作业人
	private String samplePerson;
	//批次
	private String batchNo;
	//产品编码
	private String productCode;
	//产品数量
	private Integer productNum;
	//生产日期
	private Date produceDate;
	//防伪码
	private String securityCode;
	//抽检状态
	private String inspectStatus;
	//企业送检人
	private String deliveryPerson;
	//送检日期
	private Date deliveryDate;

	/** default constructor */
	public Sample() {
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

	@Column(name = "SampleDate")
	public Date getSampleDate() {
		return sampleDate;
	}

	public void setSampleDate(Date sampleDate) {
		this.sampleDate = sampleDate;
	}

	@Column(name = "SamplePerson")
	public String getSamplePerson() {
		return samplePerson;
	}

	public void setSamplePerson(String samplePerson) {
		this.samplePerson = samplePerson;
	}

	@Column(name = "BatchNo")
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name = "ProductCode")
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@Column(name = "ProductNum")
	public Integer getProductNum() {
		return productNum;
	}

	public void setProductNum(Integer productNum) {
		this.productNum = productNum;
	}
	
	@Column(name = "ProduceDate")
	public Date getProduceDate() {
		return produceDate;
	}

	public void setProduceDate(Date produceDate) {
		this.produceDate = produceDate;
	}

	@Column(name = "SecurityCode")
	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	@Column(name = "InspectStatus")
	public String getInspectStatus() {
		return inspectStatus;
	}

	public void setInspectStatus(String inspectStatus) {
		this.inspectStatus = inspectStatus;
	}

	@Column(name = "DeliveryPerson")
	public String getDeliveryPerson() {
		return deliveryPerson;
	}

	public void setDeliveryPerson(String deliveryPerson) {
		this.deliveryPerson = deliveryPerson;
	}

	@Column(name = "DeliveryDate")
	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
}
