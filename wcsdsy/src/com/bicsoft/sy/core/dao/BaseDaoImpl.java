package com.bicsoft.sy.core.dao;

import java.util.List;
import java.util.Map;
import java.io.Serializable;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.entity.BaseEntity;
import com.bicsoft.sy.model.PageModel;

/**
 * @处理器拦截     通用dao实现
 * @Author   Gaohua
 * @Version  2015/08/16
 */

@Repository
public class BaseDaoImpl<T, PK extends Serializable> extends HibernateDaoSupport implements IDao<T, PK> {

	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public Session currentSession() {
		return super.getSessionFactory().getCurrentSession();
	}
	
	/**
	 * 保存一条数据
	 */
	public void save(T entity) {
		currentSession().saveOrUpdate(entity);
	}

	/**
	 * 修改一条数据
	 */
	public void update(T entity) {
		currentSession().update(entity);
	}

	/**
	 * 删除一条数据
	 */
	public void delete(T entity) {
		currentSession().delete(entity);
	}
	
	/**
	 * 根据id物理删除一条数据
	 */
	public void delete(Class<T> clazz, int id) {
		Object obj = currentSession().get(clazz, id);
		currentSession().delete(obj);
	}
	
	/**
	 * 根据id逻辑一条数据
	 */
	public void logicDelete(Class<T> clazz, int id) {
		BaseEntity entity =  (BaseEntity)currentSession().get(clazz, id);
		entity.setDeleteFlag("Y");
		currentSession().saveOrUpdate(entity);
	}
	
	/**
	 * 融合带有关联关系的数据
	 */
	public T merge(T entity){
		//update(object)方法运行后, object是持久化状态
		//而Object obj = merge(object),方法运行后,object是脱管状态,obj是持久化状态
		//在这里可以有效的解决two sessions的问题,因为不需要使用两个Session去管理两个持久化的对象了
		return (T)currentSession().merge(entity);
	}
	
	/**
	 * 根HQL查询结果列表
	 */
	public List<T> queryByHQL(String strHQL, Map<String, Object> params) {
		Query query = currentSession().createQuery(strHQL);
		if(params != null){
			query.setProperties(params);
		}
		return query.list();
	}

	/**
	 * 根据ID查一条
	 */
	public T queryById(Class<T> clazz, Serializable PK) {
		return (T)currentSession().get(clazz, PK);
	}

	/**
	 * 查记录总数
	 */
	public int queryCount(String strHQL, Map<String, Object> params) {
		Query query = currentSession().createQuery(strHQL);
		if(params != null){
			query.setProperties(params);
		}
		return query.list().size();
	}
	
	public List<Object[]> queryBySQL(String strSql, Map<String,Object> params){
		SQLQuery query = currentSession().createSQLQuery(strSql);
		if(params!=null){
			query.setProperties(params);
		}
		return query.list();
	}

	/**
	 * 根据HQL做分页查询
	 */
	public List<T> queryForPage(String strHQL, Map<String, Object> params,PageModel page) {
		Query query = currentSession().createQuery(strHQL);
		query.setFirstResult((page.getPage() - 1) * page.getPageSize());
		query.setMaxResults(page.getPageSize());
		if(params != null){
			query.setProperties(params);
		}
		return query.list();
	}
	
	/**
	 * 根据HQL做分页查询
	 */
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel) {
		String strHQL = "FROM " + entityName;
		Query query = currentSession().createQuery(strHQL);
		if(params != null){
			query.setProperties(params);
		}
		int total = query.list().size();
		pageModel.setTotalCount(total);
		
		Query query2 = currentSession().createSQLQuery(strHQL);
		query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
		query2.setMaxResults(pageModel.getPageSize());
		if(params != null){
			query.setProperties(params);
		}
		pageModel.setResult(query.list());
		return pageModel;
	}

	@Override
	public void delete(String sql, Object obj) {
		SQLQuery query = this.currentSession().createSQLQuery( sql );
		query.executeUpdate();   
	}
}
