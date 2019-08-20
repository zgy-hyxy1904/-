package com.bicsoft.sy.service;

import java.util.List;
import java.util.Map;

import com.bicsoft.sy.entity.GraiFore;
import com.bicsoft.sy.model.BaseModel;
import com.bicsoft.sy.model.GraiExcelModel;
import com.bicsoft.sy.model.GraiForeModel;
import com.bicsoft.sy.model.PageModel;

/**
 * 收粮预报
 * @author 创新中软
 * @date 2015-08-26
 */
public interface GraiForeService {
	public GraiFore getGraiFore(int id);
	
	public void save(GraiForeModel graiForeModel);
	
	public void saveImportData( String year, String companyCode, String companyName, List<GraiExcelModel> list, BaseModel baseModel );
	
	public void delete(int id);
	
	public void logicDelete(Class<GraiFore> clazz, int id);
	
	public PageModel queryForPageModel(String entityName, Map<String, Object> params,PageModel pageModel);
	
	/**
	 * 判断是否争议地块
	 * @return
	 */
	public int getZyInfo(String companyCode, String idNumber, String year);
	public GraiFore getGraiForeByIdNumer(String companyCode, String idNumber, String year);
}
