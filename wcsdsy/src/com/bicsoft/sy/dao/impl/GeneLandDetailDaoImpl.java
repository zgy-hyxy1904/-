package com.bicsoft.sy.dao.impl;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.GeneLandDetailDao;
import com.bicsoft.sy.entity.GeneLandDetail;

@Repository
public class GeneLandDetailDaoImpl extends BaseDaoImpl<GeneLandDetail, Serializable>  implements GeneLandDetailDao {
	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
