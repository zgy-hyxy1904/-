package com.bicsoft.sy.dao.impl;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.SpecLandDetailDao;
import com.bicsoft.sy.entity.SpecLandDetail;

@Repository
public class SpecLandDetailDaoImpl extends BaseDaoImpl<SpecLandDetail, Serializable> implements SpecLandDetailDao {

	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

}
