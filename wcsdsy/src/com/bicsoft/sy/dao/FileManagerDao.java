package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.List;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.Mfile;

public interface FileManagerDao extends IDao<Mfile, Serializable>{
	public List<Mfile> getFileList(String bizType, String bizCode);
	
	public Mfile getFile(String bizType, String bizCode, String filePath);
	
	public Mfile getFileByPath(String bizType, String bizCode, String filePath);
}