package com.bicsoft.sy.dao.impl;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.SeedVarietyDao;
import com.bicsoft.sy.entity.SeedVariety;

@Repository
public class SeedVarietyDaoImpl  extends BaseDaoImpl<SeedVariety, Serializable> implements SeedVarietyDao{
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
