package com.bicsoft.sy.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.RoleDao;
import com.bicsoft.sy.entity.Role;
import com.bicsoft.sy.model.PageModel;
import com.bicsoft.sy.model.RoleModel;
import com.bicsoft.sy.service.RoleService;
import com.bicsoft.sy.util.POVOConvertUtil;

@Service
@Transactional
public class RoleServiceImpl implements  RoleService
{
	private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	protected RoleDao roleDao;
	
	public void save(RoleModel roleModel){
		if(roleModel.getId() == null){
			try{
				Role role = (Role)POVOConvertUtil.convert(roleModel, "com.bicsoft.sy.entity.Role");
				this.roleDao.save(role);
			}catch (Exception e) {
				e.printStackTrace();
				log.error("RoleService saveObject ServiceException:",e);
			}
		}else{
			Role role = this.roleDao.queryById(Role.class, roleModel.getId());
			role.setUpdateDate(roleModel.getUpdateDate());
			role.setUpdateUserId(roleModel.getUpdateUserId());
			role.setRoleName(roleModel.getRoleName());
			role.setRemark(roleModel.getRemark());
			this.roleDao.save(role);
		}
	}
	
	public void logicDelete(Class<Role> clazz , int id)
	{
		this.roleDao.logicDelete(clazz, id);
	}
	
	public PageModel queryForPageModel(PageModel pageModel, RoleModel roleModel)
	{
		return this.roleDao.queryForPageModel(pageModel, roleModel);
	}
	
	public Role getRole(int id){
		return this.roleDao.queryById(Role.class, id);
	}
	
	public void delete(Role role){
		this.roleDao.delete(role);
	}
	
	public List<Role> getRoleList(){
		return this.roleDao.getRoleList();
	}
	
	public Role getRoleByRoleCode(String roleCode){
		return this.roleDao.getRoleByRoleCode(roleCode);
	}
}
