package com.bicsoft.sy.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.FileManagerDao;
import com.bicsoft.sy.entity.Mfile;
import com.bicsoft.sy.model.MfileModel;
import com.bicsoft.sy.service.FileManagerService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class FileManagerServiceImpl implements  FileManagerService
{
	private static final Logger log = LoggerFactory.getLogger(FileManagerServiceImpl.class);
	
	@Autowired
	protected FileManagerDao fileManagerDao;
  
	public void save(Mfile mfile)
	{
		this.fileManagerDao.save(mfile);
	}
	
	public List<Mfile> getFileList(String bizType, String bizCode){
		return this.fileManagerDao.getFileList(bizType, bizCode);
	}

	@Override
	public void save(MfileModel mfileModel) {
		try{
			Mfile Mfile = null;
			if(mfileModel.getId() == null){
				Mfile = (Mfile) POVOConvertUtil.convert(mfileModel, "com.bicsoft.sy.entity.Mfile");
				this.fileManagerDao.save(Mfile);
			}else{
			}
			this.fileManagerDao.save(Mfile);
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("UserService saveObject ServiceException:", e);
		}
	}
	
	public void deleteFile(String bizType, String bizCode, String filePath){
		Mfile mfile = this.fileManagerDao.getFileByPath(bizType, bizCode, filePath);
		if(mfile != null){
			mfile.setDeleteFlag("Y");
			mfile.setUpdateDate(new Date());
			this.fileManagerDao.save(mfile);
		}
	}
	
	public Mfile getFile(String bizType, String bizCode, String filePath){
		return this.fileManagerDao.getFile(bizType, bizCode, filePath);
	}
}
