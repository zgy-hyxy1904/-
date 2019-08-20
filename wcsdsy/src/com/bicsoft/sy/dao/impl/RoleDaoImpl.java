package com.bicsoft.sy.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.bicsoft.sy.core.dao.BaseDaoImpl;
import com.bicsoft.sy.dao.RoleDao;
import com.bicsoft.sy.entity.Role;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.RoleModel;
import com.bicsoft.sy.util.StringUtil;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role, Serializable> implements RoleDao {
	
	public List<Role> getRoleList(){
		String hql = " from Role where deleteFlag=? ";
		Query query = currentSession().createQuery(hql);
		query.setString(0, "N");
		return query.list();
	}
	
	public PageModel queryForPageModel(PageModel pageModel, RoleModel roleModel){
		String sqlTotal = " from Role where deleteFlag=? ";
		if(!StringUtil.isNullOrEmpty(roleModel.getRoleCode())) sqlTotal += " and roleCode like ? ";
		if(!StringUtil.isNullOrEmpty(roleModel.getRoleName())) sqlTotal += " and roleName like ? ";
		
		int i=0;
		Query queryTotal = currentSession().createQuery(sqlTotal);
		queryTotal.setString(i++, "N");
		if(!StringUtil.isNullOrEmpty(roleModel.getRoleCode())) queryTotal.setString(i++, "%"+roleModel.getRoleCode()+"%");
		if(!StringUtil.isNullOrEmpty(roleModel.getRoleName())) queryTotal.setString(i++, "%"+roleModel.getRoleName()
		+"%");
		int total = queryTotal.list().size();
		pageModel.setTotalCount(total);
		
		i=0;
		sqlTotal += " order by createDate desc "; 
		Query query2 = currentSession().createQuery(sqlTotal);
		query2.setString(i++, "N");
		if(!StringUtil.isNullOrEmpty(roleModel.getRoleCode())) query2.setString(i++, "%"+roleModel.getRoleCode()+"%");
		if(!StringUtil.isNullOrEmpty(roleModel.getRoleName())) query2.setString(i++, "%"+roleModel.getRoleName()+"%");
		query2.setFirstResult((pageModel.getPage() - 1) * pageModel.getPageSize());
		query2.setMaxResults(pageModel.getPageSize());
		
		pageModel.setResult(query2.list());
		return pageModel;
	}
	
	public Role getRoleByRoleCode(String roleCode){
		String hql = " from Role where roleCode=? ";
		Query query = currentSession().createQuery(hql);
		query.setString(0, roleCode);
		List<Role> list = query.list();
		if(list != null && list.size()>0) return list.get(0);
		else return null;
	}
}