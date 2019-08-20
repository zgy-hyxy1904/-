package com.bicsoft.sy.common.taglib;

import java.io.Writer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.bicsoft.sy.entity.AreaDevision;
import com.bicsoft.sy.service.AreaDevisionService;

public class CasCadeCityTag extends RequestContextAwareTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3484661201004988852L;
	private static final Logger log = LoggerFactory.getLogger(CasCadeCityTag.class);

	private String cityId;
	private String townId;
	private String countryId;
	private String groupId;
	private String cityName;
	private String townName;
	private String countryName;
	private String groupName;
	private String cityWidth;
	private String townWidth;
	private String countryWidth;
	private String groupWidth;
	private String height;
	private String cssClass;
	private String readOnly;
	private String canEdit;
	private String cityCode;
	private String townCode;
	private String countryCode;
	private String groupValue;
	private String showCity;
	private String showGroup;
	private String showText;
	private String textCanEdit;

	@Override
	protected int doStartTagInternal() throws Exception {
		Writer out = pageContext.getOut();
		
		AreaDevisionService areaDevisionService = (AreaDevisionService) this.getRequestContext().getWebApplicationContext().getBean("areaDevisionService");
		
		if(!isValid(cityId)){
			cityId = "cityCode";
		}
		if(!isValid(townId)){
			townId = "townCode";
		}
		if(!isValid(countryId)){
			countryId = "countryCode";
		}
		if(!isValid(groupId)){
			groupId = "groupId";
		}
		if(!isValid(cityName)){
			cityName = "cityCode";
		}
		if(!isValid(townName)){
			townName = "townCode";
		}
		if(!isValid(countryName)){
			countryName = "countryCode";
		}
		if(!isValid(groupName)){
			groupName = "groupName";
		}
		if(!isValid(countryWidth)){
			countryWidth = "150";
		}
		if(!isValid(cityWidth)){
			cityWidth = "150";
		}
		if(!isValid(townWidth)){
			townWidth = "150";
		}
		if(!isValid(groupWidth)){
			groupWidth = "150";
		}
		if(!isValid(height)){
			height = "25";
		}
		if(!isValid(cssClass)){
			cssClass = "easyui-combobox";
		}
		if(!isValid(readOnly)){
			readOnly = "false";
		}
		if(!isValid(canEdit)){
			canEdit = "false";
		}
		if(!isValid(textCanEdit)){
			textCanEdit = "true";
		}
		if(!isValid(showCity)){
			showCity = "false";
		}
		if(!isValid(groupValue)){
			groupValue = "";
		}
		if(!isValid(cityCode)&&!isTrue(showCity)&&isTrue(showGroup)){
			cityCode = "230184";
		}
		if(!isValid(showGroup)){
			showGroup = "true";
		}
		if(!isValid(showText)){
			showText = "true";
		}
		if("true".equalsIgnoreCase(readOnly)&&!isValid(cityCode)&&!isValid(townCode)&&!isValid(countryCode)){
			return EVAL_PAGE;
		}
		
		StringBuffer buffer = new StringBuffer("\n");

		if(!"true".equalsIgnoreCase(readOnly)){
			List<AreaDevision> areas = areaDevisionService.queryCitys();
			
			if(!isTrue(showCity)){
				buffer.append("<span style='display:none'>\n"); 
				buffer.append((isTrue(showText)?"市/地区:":"")+"<select id='"+cityId+"' name='"+cityName+"' class='"+cssClass+"' style='width:" + cityWidth + "px;height:" + height + "px' data-options='editable:" + canEdit + "'>\n"); 
				for(AreaDevision area : areas){
					if(cityCode.equals(area.getCityCode())){
						buffer.append("		<option value='"+area.getCityCode()+"'>"+area.getCityName()+"</option>\n");
						break;
					}
				}
				buffer.append("</select> \n"); 
				buffer.append("</span>\n"); 
				List<AreaDevision> towns = areaDevisionService.queryTownsByCityCode(cityCode);
				buffer.append((isTrue(showText)?"镇/乡:":"")+"<span id='townSpan'><select id='"+townId+"' name='"+townName+"' class='"+cssClass+"' style='width:" + townWidth + "px;height:" + height + "px' data-options='editable:" + canEdit + ",required:true'>");
				buffer.append("	<option value=''  "+(isValid(townCode)?"":"selected")+">-=请选择=-</option>\n"); 
				for(AreaDevision town : towns){
					buffer.append("		<option value='"+town.getTownCode()+"' "+(isValid(townCode)&&townCode.equals(town.getTownCode())?"selected":"")+">"+town.getTownName()+"</option>\n"); 
				}
				buffer.append("</select></span>\n");
			}else{
				buffer.append("<span  id='citySpan'>\n"); 
				buffer.append((isTrue(showText)?"市/地区:":"")+"<select id='"+cityId+"' name='"+cityName+"' class='"+cssClass+"' style='width:" + cityWidth + "px;height:" + height + "px' data-options='editable:" + canEdit + ",required:true'>\n"); 
				buffer.append("	<option value='' "+(isValid(cityCode)?"":"selected")+">-=请选择=-</option>\n"); 
				for(AreaDevision area : areas){
					buffer.append("		<option value='"+area.getCityCode()+"' "+(isValid(cityCode)&&cityCode.equals(area.getCityCode())?"selected":"")+">"+area.getCityName()+"</option>\n"); 
				}
				buffer.append("</select> \n"); 
				buffer.append("</span>\n"); 
				buffer.append((isTrue(showText)?"镇/乡:":"")+"<span id='townSpan'><select id='"+townId+"' name='"+townName+"' class='"+cssClass+"' style='width:" + townWidth + "px;height:" + height + "px' data-options='editable:" + canEdit + "'>");
				if(isValid(townCode)){
					List<AreaDevision> towns = areaDevisionService.queryTownsByCityCode(cityCode);
					buffer.append("	<option value='' "+(isValid(townCode)?"":"selected")+">-=请选择=-</option>\n"); 
					for(AreaDevision town : towns){
						buffer.append("		<option value='"+town.getTownCode()+"' "+(isValid(townCode)&&townCode.equals(town.getTownCode())?"selected":"")+">"+town.getTownName()+"</option>\n"); 
					}
				}
				buffer.append("</select></span>\n");
			}
			
			buffer.append((isTrue(showText)?"村:":"")+"<span id='countrySpan'><select id='"+countryId+"' name='"+countryName+"' class='"+cssClass+"' style='width:" + countryWidth + "px;height:" + height + "px' data-options='editable:" + canEdit + "'>");
			if(isValid(countryCode)){
				List<AreaDevision> countrys = areaDevisionService.queryCountrysByCityAndTownCode(cityCode, townCode);
				buffer.append("	<option value='' "+(isValid(countryCode)?"":"selected")+">-=请选择=-</option>\n"); 
				for(AreaDevision country : countrys){
					buffer.append("		<option value='"+country.getCountryCode()+"' "+(isValid(countryCode)&&countryCode.equals(country.getCountryCode())?"selected":"")+">"+country.getCountryName()+"</option>\n"); 
				}
			}
			buffer.append("</select></span>\n");
			if("true".equalsIgnoreCase(showGroup)){
				buffer.append("<span id='groupSpan'>"+(isTrue(showText)?"屯":"")+"<input class='easyui-textbox' type='text' id='"+groupId+"' name='"+groupName+"' "+setTextReadOnly(textCanEdit)+" value='"+(isValid(groupValue)?groupValue:"")+"' style='width:" + groupWidth + "px;height:" + height + "px'></select></span>\n");
			}else{
				buffer.append("<span id='groupSpan' style='display:none'>"+(isTrue(showText)?"屯":"")+"<input class='easyui-textbox' type='text' id='"+groupId+"' name='"+groupName+"' "+setTextReadOnly(textCanEdit)+" value="+(isValid(groupValue)?groupValue:"")+" style='width:" + groupWidth + "px;height:" + height + "px'></select></span>\n");
			}
			buffer.append("<script>\n"); 
			buffer.append("$(document).ready(function(){\n"); 
			buffer.append("	$('#"+cityId+"').combobox({\n"); 
			buffer.append("		valueField:'id',\n"); 
			buffer.append("		textField:'text',\n"); 
			buffer.append("		onChange : function(){\n"); 
			buffer.append("			var cityCode = $('#"+cityId+"').combobox('getValue');\n"); 
			buffer.append("			Public.ajaxGet('"+this.getRequestContext().getContextPath()+"/areaDevision/getTownsByCityCode', {cityCode:cityCode}, function(e) {\n"); 
			buffer.append("				if (200 == e.status) {\n"); 
			buffer.append("					$('#"+townId+"').combobox('loadData', JSON.parse(e.data));\n"); 
			buffer.append("				} else {\n"); 
			buffer.append("					parent.parent.Public.tips({\n"); 
			buffer.append("						type : 1,\n"); 
			buffer.append("						content : '失败！' + e.msg\n"); 
			buffer.append("					});\n"); 
			buffer.append("				}\n"); 
			buffer.append("			});\n"); 
			buffer.append("		}\n"); 
			buffer.append("	});\n"); 
			buffer.append("    $('#"+townId+"').combobox({\n"); 
			buffer.append("        valueField:'id',\n"); 
			buffer.append("        textField:'text',\n"); 
			buffer.append("        onChange: function(){\n"); 
			buffer.append("			var cityCode = $('#"+cityId+"').combobox('getValue');\n"); 
			buffer.append("			var townCode = $('#"+townId+"').combobox('getValue');\n"); 
			buffer.append("			Public.ajaxGet('"+this.getRequestContext().getContextPath()+"/areaDevision/getCountrysByCityAndTownCode', {cityCode:cityCode,townCode:townCode}, function(e) {\n"); 
			buffer.append("				if (200 == e.status) {\n"); 
			buffer.append("					$('#"+countryId+"').combobox({valueField:'id',textField:'text'});\n"); 
			buffer.append("					$('#"+countryId+"').combobox('loadData', JSON.parse(e.data));\n"); 
			buffer.append("				} else {\n"); 
			buffer.append("					parent.parent.Public.tips({\n"); 
			buffer.append("						type : 1,\n"); 
			buffer.append("						content : '失败！' + e.msg\n"); 
			buffer.append("					});\n"); 
			buffer.append("				}\n"); 
			buffer.append("			});\n"); 
			buffer.append("        }\n"); 
			buffer.append("    });\n"); 
			buffer.append("});\n"); 
			buffer.append("</script>\n"); 
		}else{
			AreaDevision area = areaDevisionService.getAreaDevision(cityCode, townCode, countryCode);
			if("true".equalsIgnoreCase(showCity)){
				buffer.append((isTrue(showText)?"市/地区:":"")+area.getCityName()); 
			}
			buffer.append((isTrue(showText)?"镇/乡:":"")+area.getTownName()); 
			buffer.append("</select> \n"); 
			buffer.append((isTrue(showText)?"村:":"")+area.getCountryName()); 
			buffer.append("</select> \n"); 
			if(isTrue(showGroup)){
				buffer.append((isTrue(showText)?"屯:":"")+groupValue);
			}
		}
		
		out.write(buffer.toString());
		return EVAL_PAGE;
	}

	private boolean isValid(String str){
		return str!=null && !"".equals(str.trim());
	}
	
	private boolean isTrue(String str){
		return Boolean.parseBoolean(str);
	}

	private String setTextReadOnly(String canEdit){
		if(!isTrue(canEdit)){
			return "readonly='readOnly'";
		}
		return "";
	}
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getTownId() {
		return townId;
	}

	public void setTownId(String townId) {
		this.townId = townId;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getTownCode() {
		return townCode;
	}

	public void setTownCode(String townCode) {
		this.townCode = townCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(String canEdit) {
		this.canEdit = canEdit;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getShowCity() {
		return showCity;
	}

	public void setShowCity(String showCity) {
		this.showCity = showCity;
	}

	public String getShowGroup() {
		return showGroup;
	}

	public void setShowGroup(String showGroup) {
		this.showGroup = showGroup;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupValue() {
		return groupValue;
	}

	public void setGroupValue(String groupValue) {
		this.groupValue = groupValue;
	}

	public String getCityWidth() {
		return cityWidth;
	}

	public void setCityWidth(String cityWidth) {
		this.cityWidth = cityWidth;
	}

	public String getTownWidth() {
		return townWidth;
	}

	public void setTownWidth(String townWidth) {
		this.townWidth = townWidth;
	}

	public String getCountryWidth() {
		return countryWidth;
	}

	public void setCountryWidth(String countryWidth) {
		this.countryWidth = countryWidth;
	}

	public String getGroupWidth() {
		return groupWidth;
	}

	public void setGroupWidth(String groupWidth) {
		this.groupWidth = groupWidth;
	}

	public String getShowText() {
		return showText;
	}

	public void setShowText(String showText) {
		this.showText = showText;
	}

	public String getTextCanEdit() {
		return textCanEdit;
	}

	public void setTextCanEdit(String textCanEdit) {
		this.textCanEdit = textCanEdit;
	}
	
	
}
