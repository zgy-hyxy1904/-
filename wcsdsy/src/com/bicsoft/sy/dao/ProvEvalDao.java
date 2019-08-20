package com.bicsoft.sy.dao;

import java.io.Serializable;
import java.util.Map;

import com.bicsoft.sy.core.dao.IDao;
import com.bicsoft.sy.entity.ProvEval;
import com.bicsoft.sy.model.PageModel;



/**
 * 种源评估
 * @author 创新中软
 * @date 2015-08-17
 */
public interface ProvEvalDao extends IDao<ProvEval, Serializable> {
	public PageModel queryLandInfoList(Map<String, Object> params,PageModel pageModel);
}
