package com.bicsoft.sy.core.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bicsoft.sy.model.PageModel;

public interface IDao<T, PK extends Serializable>{
	public void save(T entity);
	public void update(T entity);
	public void delete(T entity);
	public void delete(Class<T> clazz, int id);
	public void logicDelete(Class<T> clazz, int id);
	public T queryById(Class<T> clazz, Serializable PK);
	
	public List<T> queryByHQL(String strHQL, Map<String,Object> params);
	public List<T> queryForPage(String strHQL, Map<String, Object> params, PageModel pageModel);
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	public int queryCount(String strHQL, Map<String, Object> params);
	public List<Object[]> queryBySQL(String strSQL, Map<String,Object> params);
	
	public T merge(T entity);
	
	public void delete(String sql, Object obj);
}
