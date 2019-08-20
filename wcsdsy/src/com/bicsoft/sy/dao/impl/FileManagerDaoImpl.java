package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.FileManagerDao;
import com.bicsoft.sy.entity.Mfile;

@Repository
public class FileManagerDaoImpl extends BaseDaoImpl<Mfile, Serializable> implements FileManagerDao {
	
	@Override
	public List<Mfile> getFileList(String bizType, String bizCode){
		String hql = " from Mfile where deleteFlag=? and bizType=? and bizCode=?";
		Query query = currentSession().createQuery(hql);
		query.setString(0, "N");
		query.setString(1, bizType);
		query.setString(2, bizCode);
		return query.list();
	}
	
	@Override
	public Mfile getFile(String bizType, String bizCode, String filePath){
		String hql = " from Mfile where deleteFlag=? and bizType=? and bizCode=? and filePath=?";
		Query query = currentSession().createQuery(hql);
		query.setString(0, "N");
		query.setString(1, bizType);
		query.setString(2, bizCode);
		query.setString(3, filePath);
		List<Mfile> list = query.list();
		if(list != null && list.size() > 0) return (Mfile)list.get(0);
		else return null;
	}
	
	@Override
	public Mfile getFileByPath(String bizType, String bizCode, String filePath){
		String hql = " from Mfile where deleteFlag=? and bizType=? and bizCode=? and filePath=? ";
		Query query = currentSession().createQuery(hql);
		query.setString(0, "N");
		query.setString(1, bizType);
		query.setString(2, bizCode);
		query.setString(3, filePath);
		List<Mfile> list = query.list();
		if(list != null && list.size() > 0) return list.get(0);
		else return null;
	}
}