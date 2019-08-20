package com.bicsoft.sy.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TB_FILE")
public class Mfile extends BaseEntity
{
	private Integer id;
	private String bizType;
	private String bizCode;
	private String extField1;
	private String extField2;
	private String fileType;
	private String originalName;
	private String filePath;
	private String fileInfo;
	
	/** default constructor */
	public Mfile() {
	
	}

	/**
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the bizType
	 */
	@Column(name = "BizType")
	public String getBizType() {
		return bizType;
	}

	/**
	 * @param bizType the bizType to set
	 */
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	/**
	 * @return the bizCode
	 */
	@Column(name = "BizCode")
	public String getBizCode() {
		return bizCode;
	}

	/**
	 * @param bizCode the bizCode to set
	 */
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	/**
	 * @return the extField1
	 */
	@Column(name = "ExtField1")
	public String getExtField1() {
		return extField1;
	}

	/**
	 * @param extField1 the extField1 to set
	 */
	public void setExtField1(String extField1) {
		this.extField1 = extField1;
	}

	/**
	 * @return the extField2
	 */
	@Column(name = "ExtField2")
	public String getExtField2() {
		return extField2;
	}

	/**
	 * @param extField2 the extField2 to set
	 */
	public void setExtField2(String extField2) {
		this.extField2 = extField2;
	}

	/**
	 * @return the fileType
	 */
	@Column(name = "FileType")
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	/**
	 * @return the originalName
	 */
	@Column(name = "OriginalName")
	public String getOriginalName() {
		return originalName;
	}

	/**
	 * @param originalName the originalName to set
	 */
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	/**
	 * @return the filePath
	 */
	@Column(name = "FilePath")
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the fileInfo
	 */
	@Column(name = "FileInfo")
	public String getFileInfo() {
		return fileInfo;
	}

	/**
	 * @param fileInfo the fileInfo to set
	 */
	public void setFileInfo(String fileInfo) {
		this.fileInfo = fileInfo;
	}
}
