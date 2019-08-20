package com.bicsoft.sy.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 土地备案变更申请明细表
 * @author 
 * @date 2015-09-11
 *
 */
@Entity
@Table(name="b_LandRegChangeDetails")
public class LandRegChangeDetail extends BaseEntity {
	//土地备案变更申请明细表主键
	private Integer id;
	//土地备案变更表主键
	private Integer hid;
	//土地备案主表主键
	private Float LandRegHID;
	//土地备案子表主键
	private String LandRegSID;
	//土地面积
	private String ArchiveAcreage;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getHid() {
		return hid;
	}
	public void setHid(Integer hid) {
		this.hid = hid;
	}
	public Float getLandRegHID() {
		return LandRegHID;
	}
	public void setLandRegHID(Float landRegHID) {
		LandRegHID = landRegHID;
	}
	public String getLandRegSID() {
		return LandRegSID;
	}
	public void setLandRegSID(String landRegSID) {
		LandRegSID = landRegSID;
	}
	public String getArchiveAcreage() {
		return ArchiveAcreage;
	}
	public void setArchiveAcreage(String archiveAcreage) {
		ArchiveAcreage = archiveAcreage;
	}
	
}
