package com.bicsoft.sy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bicsoft.sy.dao.AreaDivisionDao;
import com.bicsoft.sy.entity.AreaDevision;
import com.bicsoft.sy.service.AreaDevisionService;

@Service
@Transactional
public class AreaDevisionServiceImpl implements  AreaDevisionService
{
	private static final Logger log = LoggerFactory.getLogger(AreaDevisionServiceImpl.class);
	
	@Autowired
	protected AreaDivisionDao areaDivisionDao;
  
	//通过证件id查询农户信息
	public AreaDevision getAreaDevisionByName(String townName, String countryName){
		return areaDivisionDao.getAreaDevisionByName(townName, countryName);
	}
	
	public List<AreaDevision> queryCitys(){
		return (List<AreaDevision>) areaDivisionDao.queryBySql("select distinct ma.cityCode,ma.cityName,'' as townCode,'' as townName,'' as countryCode,'' as countryName from m_areadivision ma", null);
	}
	
	public List<AreaDevision> queryTownsByCityCode(String cityCode){
		
		return (List<AreaDevision>) areaDivisionDao.queryBySql("select distinct ma.townCode,ma.townName,'' as cityCode,'' as cityName,'' as countryCode,'' as countryName from m_areadivision ma where ma.cityCode='"+cityCode+"'", null);
	}
	
	public List<AreaDevision> queryCountrysByCityAndTownCode(String cityCode, String townCode){
		return (List<AreaDevision>) areaDivisionDao.queryBySql("select ma.countryCode,ma.countryName,'' as cityCode,'' as cityName,'' as townCode,'' as townName from m_areadivision ma where ma.cityCode='"+cityCode+"' and townCode='"+townCode+"'", null);
	}

	@Override
	public AreaDevision getAreaDevision(String cityCode, String townCode, String countryCode) {
		String hql = "from AreaDevision where cityCode=:cityCode and townCode=:townCode and countryCode=:countryCode";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("cityCode", cityCode);
		params.put("townCode", townCode);
		params.put("countryCode", countryCode);
		List<AreaDevision> areas = areaDivisionDao.queryByHQL(hql, params);
		return (areas==null||areas.size()==0)?new AreaDevision():areas.get(0);
	}
	
	@Override
	public AreaDevision getAreaDevision(String cityCode, String townCode) {
		String hql = "from AreaDevision where cityCode=:cityCode and townCode=:townCode";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("cityCode", cityCode);
		params.put("townCode", townCode);
		List<AreaDevision> areas = areaDivisionDao.queryByHQL(hql, params);
		return (areas==null||areas.size()==0)?new AreaDevision():areas.get(0);
	}
	
	@Override
	public String getAreaNameByCode( String codeKey,String code) {
		String sql = "SELECT DISTINCT "+codeKey+"Name, "+codeKey+"Code FROM m_areadivision WHERE "+codeKey+"Code='"+code+"'";
		List<Object[]> name = areaDivisionDao.queryBySQL(sql, null);
		return (name==null||name.size()==0)?"":name.get(0)[0]+"";
	}
	
	public String getCountryNameByCode( String codeKey,String code){
		if(code.indexOf(",")<0){
			return getAreaNameByCode(codeKey, code);
		}else if(code.trim().length()>1 && code.indexOf(",")>-1){
			String[] codes = code.split(",");
			String sql = "SELECT DISTINCT "+codeKey+"Name, "+codeKey+"Code FROM m_areadivision WHERE townCode='"+codes[0].trim()+"' and countryCode='"+codes[1].trim()+"'";
			List<Object[]> name = areaDivisionDao.queryBySQL(sql, null);
			return (name==null||name.size()==0)?"":name.get(0)[0]+"";
		}
		return "";
	}
}
