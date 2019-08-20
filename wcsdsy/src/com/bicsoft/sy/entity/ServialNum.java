package com.bicsoft.sy.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 流水号生成
 * @author 创新中软
 * @date 2015-08-17
 */
@Entity
@Table(name="b_servial_num")
public class ServialNum {
	
	private Integer id;
	//普通土地系列号
	private Integer pTNo;
	//特殊土地系列号
	private Integer tXNo;
	//收粮上报流水号
	private Integer sLNo;
	//投入品流水号
	private Integer tRNo;
	//土地变更流水号
	private Integer bgNo;
		
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "PTNo")
	public Integer getpTNo() {
		return pTNo;
	}

	public void setpTNo(Integer pTNo) {
		this.pTNo = pTNo;
	}

	@Column(name = "TXNo")
	public Integer gettXNo() {
		return tXNo;
	}

	public void settXNo(Integer tXNo) {
		this.tXNo = tXNo;
	}

	@Column(name = "SLNo")
	public Integer getsLNo() {
		return sLNo;
	}

	public void setsLNo(Integer sLNo) {
		this.sLNo = sLNo;
	}

	@Column(name = "TRNo")
	public Integer gettRNo() {
		return tRNo;
	}

	public void settRNo(Integer tRNo) {
		this.tRNo = tRNo;
	}

	@Column(name = "BGNo")
	public Integer getBgNo() {
		return bgNo;
	}

	public void setBgNo(Integer bgNo) {
		this.bgNo = bgNo;
	}
}
