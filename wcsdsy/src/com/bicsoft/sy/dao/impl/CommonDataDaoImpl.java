package com.bicsoft.sy.dao.impl;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.CommonDataDao;
import com.bicsoft.sy.entity.CommonData;

@Repository
public class CommonDataDaoImpl  extends BaseDaoImpl<CommonData, Serializable> implements CommonDataDao{
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
