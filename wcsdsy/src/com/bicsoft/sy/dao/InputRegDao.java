package com.bicsoft.sy.dao;

import java.io.Serializable;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.InputReg;

/**
 * 投入品备案
 * @author 创新中软
 * @date 2015-08-17
 */
public interface InputRegDao extends IDao<InputReg, Serializable>{
	public boolean hasInputReg(String year, String companyCode);
}
