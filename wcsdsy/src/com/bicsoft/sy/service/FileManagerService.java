package com.bicsoft.sy.service;

import java.util.List;

import com.bicsoft.sy.entity.Mfile;
import com.bicsoft.sy.model.MfileModel;

public interface FileManagerService{
	public void save(Mfile mfile);
	public List<Mfile> getFileList(String bizType, String bizCode);
	
	public void save(MfileModel mfileModel);
	
	public void deleteFile(String bizType, String bizCode, String filePath);
	
	public Mfile getFile(String bizType, String bizCode, String filePath);
}