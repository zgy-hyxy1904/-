package com.bicsoft.sy.entity;

import static javax.persistence.GenerationType.IDENTITY;

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
@Table(name = "CommonData")
public class CommonData {
	// 自增长主键
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	// 代码主档标识名称
	@Column(name = "CodeName")
	private String codeName;
	// 代码主档标识
	@Column(name = "CodeKey")
	private String codeKey;
	// 代码
	@Column(name = "CodeCode")
	private String codeCode;
	// 显示顺序
	@Column(name = "CodeSort")
	private Integer codeSort;
	// 代码含义
	@Column(name = "CodeValue")
	private String codeValue;
	// 备注
	@Column(name = "Remark")
	private String remark;

	/** default constructor */
	public CommonData() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeKey() {
		return codeKey;
	}

	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}

	public String getCodeCode() {
		return codeCode;
	}

	public void setCodeCode(String codeCode) {
		this.codeCode = codeCode;
	}

	public Integer getCodeSort() {
		return codeSort;
	}

	public void setCodeSort(Integer codeSort) {
		this.codeSort = codeSort;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

}
