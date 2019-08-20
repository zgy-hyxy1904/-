package com.bicsoft.sy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="M_PACKING")
public class Packing
{
	private Integer id;
	//企业编号
	private String companyCode;
	//类型编号
	private String typeCode;
	//类型名称
	private String typeName;
	//品类编号
	private String classCode;
	//品类名称
	private float className;
	//规格编号
	private float specCode;
	//规格名称
	private String specName;
	//规格重量
	private float specWeight;
		
	/** default constructor */
	public Packing() {
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

	@Column(name = "CompanyCode")
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Column(name = "TypeCode")
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Column(name = "TypeName")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Column(name = "ClassCode")
	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	@Column(name = "ClassName")
	public float getClassName() {
		return className;
	}

	public void setClassName(float className) {
		this.className = className;
	}

	@Column(name = "SpecCode")
	public float getSpecCode() {
		return specCode;
	}

	public void setSpecCode(float specCode) {
		this.specCode = specCode;
	}
	
	@Column(name = "SpecName")
	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	@Column(name = "SpecWeight")
	public float getSpecWeight() {
		return specWeight;
	}

	public void setSpecWeight(float specWeight) {
		this.specWeight = specWeight;
	}
}
