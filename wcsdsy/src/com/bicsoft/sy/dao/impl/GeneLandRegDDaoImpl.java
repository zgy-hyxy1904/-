package com.bicsoft.sy.dao.impl;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.GeneLandRegDDao;
import com.bicsoft.sy.entity.GeneLandRegD;

@Repository
public class GeneLandRegDDaoImpl extends BaseDaoImpl<GeneLandRegD, Serializable> implements GeneLandRegDDao {
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
