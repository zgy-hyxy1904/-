package com.bicsoft.sy.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="M_Peasant")
public class Peasant
{
	private Integer id;
	//承包方代码
	private String contractorCode;
	//承包方姓名
	private String contractorName;
	//承包方证件号码
	private String contractorID;
	//承包方证件类型
	private String contractorIDType;
	//年龄
	private Integer contractorAge;
	//性别
	private String contractorSex;
	//生日
	private String contractorBirth;
	//联系电话
	private String contractorTel;
	//所在市编码
	private String cityCode;
	//所在乡编码
	private String townCode;
	//所在村编码
	private String countryCode;
	//所在村编码
	private String groupName;
	//邮政编码
	private String contractorZipcode;
	//户口性质
	private String contractorhouseholdType;
	//承包方类型
	private String contractorType;
	//承包合同编号
	private String contractId;
	//民族
	private String contractorNation;
	//经营权证编号
	private String rightId;
	//经营权证编号
	private String attestor;
	//鉴证机关
	private String attestMechanism;
	//鉴证日期
	private String attestDate;
	//鉴证编号
	private String attestNo;
	//承包土地用途
	private String landPurpose;
	//经营权取得方式
	private String rightGetWay;
	//承包开始日期
	private Date contractStartDate;
	//承包期限
	private Integer contractYear;
	//承包结束日期
	private Date contractEndDate;
	//98年分地成员总数
	private Integer getLandPersonCount;
	//家庭人口数
	private Integer familyPersonCount;
	//调查日期
	private Date surveyDate;
	//调查记事
	private String surveyMemo;
		
	/** default constructor */
	public Peasant() {
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

	@Column(name = "ContractorName")
	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	@Column(name = "ContractorID")
	public String getContractorID() {
		return contractorID;
	}

	public void setContractorID(String contractorID) {
		this.contractorID = contractorID;
	}

	@Column(name = "ContractorIDType")
	public String getContractorIDType() {
		return contractorIDType;
	}

	public void setContractorIDType(String contractorIDType) {
		this.contractorIDType = contractorIDType;
	}

	@Column(name = "ContractorAge")
	public Integer getContractorAge() {
		return contractorAge;
	}

	public void setContractorAge(Integer contractorAge) {
		this.contractorAge = contractorAge;
	}

	@Column(name = "ContractorSex")
	public String getContractorSex() {
		return contractorSex;
	}

	public void setContractorSex(String contractorSex) {
		this.contractorSex = contractorSex;
	}

	@Column(name = "ContractorBirth")
	public String getContractorBirth() {
		return contractorBirth;
	}

	public void setContractorBirth(String contractorBirth) {
		this.contractorBirth = contractorBirth;
	}

	@Column(name = "ContractorTel")
	public String getContractorTel() {
		return contractorTel;
	}

	public void setContractorTel(String contractorTel) {
		this.contractorTel = contractorTel;
	}

	@Column(name = "CityCode")
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@Column(name = "TownCode")
	public String getTownCode() {
		return townCode;
	}

	public void setTownCode(String townCode) {
		this.townCode = townCode;
	}

	@Column(name = "CountryCode")
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Column(name = "GroupName")
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "ContractorZipcode")
	public String getContractorZipcode() {
		return contractorZipcode;
	}

	public void setContractorZipcode(String contractorZipcode) {
		this.contractorZipcode = contractorZipcode;
	}

	@Column(name = "ContractorhouseholdType")
	public String getContractorhouseholdType() {
		return contractorhouseholdType;
	}

	public void setContractorhouseholdType(String contractorhouseholdType) {
		this.contractorhouseholdType = contractorhouseholdType;
	}

	@Column(name = "ContractorType")
	public String getContractorType() {
		return contractorType;
	}

	public void setContractorType(String contractorType) {
		this.contractorType = contractorType;
	}

	@Column(name = "ContractId")
	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	@Column(name = "ContractorNation")
	public String getContractorNation() {
		return contractorNation;
	}

	public void setContractorNation(String contractorNation) {
		this.contractorNation = contractorNation;
	}

	@Column(name = "RightId")
	public String getRightId() {
		return rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	@Column(name = "Attestor")
	public String getAttestor() {
		return attestor;
	}

	public void setAttestor(String attestor) {
		this.attestor = attestor;
	}

	@Column(name = "AttestMechanism")
	public String getAttestMechanism() {
		return attestMechanism;
	}

	public void setAttestMechanism(String attestMechanism) {
		this.attestMechanism = attestMechanism;
	}

	@Column(name = "AttestDate")
	public String getAttestDate() {
		return attestDate;
	}

	public void setAttestDate(String attestDate) {
		this.attestDate = attestDate;
	}

	@Column(name = "AttestNo")
	public String getAttestNo() {
		return attestNo;
	}

	public void setAttestNo(String attestNo) {
		this.attestNo = attestNo;
	}

	@Column(name = "LandPurpose")
	public String getLandPurpose() {
		return landPurpose;
	}

	public void setLandPurpose(String landPurpose) {
		this.landPurpose = landPurpose;
	}

	@Column(name = "RightGetWay")
	public String getRightGetWay() {
		return rightGetWay;
	}

	public void setRightGetWay(String rightGetWay) {
		this.rightGetWay = rightGetWay;
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

	@Column(name = "GetLandPersonCount")
	public Integer getGetLandPersonCount() {
		return getLandPersonCount;
	}

	public void setGetLandPersonCount(Integer getLandPersonCount) {
		this.getLandPersonCount = getLandPersonCount;
	}

	@Column(name = "FamilyPersonCount")
	public Integer getFamilyPersonCount() {
		return familyPersonCount;
	}

	public void setFamilyPersonCount(Integer familyPersonCount) {
		this.familyPersonCount = familyPersonCount;
	}

	@Column(name = "SurveyDate")
	public Date getSurveyDate() {
		return surveyDate;
	}

	public void setSurveyDate(Date surveyDate) {
		this.surveyDate = surveyDate;
	}

	@Column(name = "SurveyMemo")
	public String getSurveyMemo() {
		return surveyMemo;
	}

	public void setSurveyMemo(String surveyMemo) {
		this.surveyMemo = surveyMemo;
	}
}
