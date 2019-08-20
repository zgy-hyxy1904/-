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
@Table(name = "M_SEEDVARIETY")
public class SeedVariety extends BaseEntity {
	// 自增长主键
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	// 企业名称
	@Column(name = "SeedCode")
	private String seedCode;
	// 企业法人
	@Column(name = "SeedName")
	private String seedName;

	public String getSeedCode() {
		return seedCode;
	}

	public void setSeedCode(String seedCode) {
		this.seedCode = seedCode;
	}

	public String getSeedName() {
		return seedName;
	}

	public void setSeedName(String seedName) {
		this.seedName = seedName;
	}

}
