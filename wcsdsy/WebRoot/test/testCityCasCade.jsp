<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>市乡村三级联动的标签使用示例</title>
<link rel="stylesheet" type="text/css" href="${ctx}/style/index.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/color.css">
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript" src="${ctx}/js/form2js.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:0 10px;">	
		<H2>行政区划联动的标签使用说明:</H2>
		<p>
			1.在页面中引入如下标签代码:<BR>
			<div style='background-color:#999999'>
			<code>
				&lt;%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%&gt;
			</code>
			</div>
			<BR>
			2.在需要使用下拉列表数据字典的位置引入如下组件代码示例:<BR>
			<div style='background-color:#999999'>
			<code>
				&lt;simple:casCity
					showCity="false"
					showGroup="true"
					cityId="cityCode" 
					townId="townCode" 
					countryId="countryCode" 
					groupId="groupId" 
					cityName="cityCode" 
					townName="townCode" 
					countryName="countryCode" 
					groupName="groupName" 
					readOnly="false"
					cityCode="cityCode" 
					townCode="townCode" 
					countryCode="countryCode" 
					groupValue="马家屯" 
					cssClass="easyui-combobox"
					cityWidth="150"
					townWidth="150"
					countryWidth="150"
					groupWidth="150"
					height="25"
					canEdit="false"
					&gt;&lt;/simple:casCity&gt;
			</code>
			</div>
			<BR>
		<H2>示例展示(自己Copy源码改):注意,页面中引入多个相同标签时一定要起好不同的ID,否则JS会失效</H2>
		<HR>
		普通示例:<simple:casCity cityId="cityCode1" townId="townCode1" countryId="countryCode1" showCity="true" showGroup="true"/><input type="button" onclick="alert($('#townCode1').combobox('getValue'));" value='townCode'/><input type="button" onclick="alert($('#countryCode1').combobox('getValue'));" value='显示countryCode'/><BR>
		不显示市只显示其它三级的示例:<simple:casCity cityId="cityCode2" townId="townCode2" countryId="countryCode2" showCity="false" showGroup="true"/><BR>
		不显示屯只显示其它三级的示例:<simple:casCity cityId="cityCode3" townId="townCode3" countryId="countryCode3" showCity="true" showGroup="false"/><BR>
		不显示文本示例:<simple:casCity cityId="cityCode7" townId="townCode7" countryId="countryCode7" showCity="true" showGroup="true" showText="false"/><BR>
		单独设定宽度示例:<simple:casCity cityId="cityCode8" townId="townCode8" countryId="countryCode8" showCity="true" showGroup="true" cityWidth="80" countryWidth="150" townWidth="100" groupWidth="30" showText="false"/><BR>
		需要默认被选中示例:<simple:casCity cityId="cityCode9" townId="townCode9" countryId="countryCode9" showCity="true" showGroup="true" cityCode="230184" townCode="100" countryCode="200" groupValue="马家屯"/><BR>
		<HR>
		<b>只读示例:</b><BR><simple:casCity showCity="true" showGroup="true" cityId="cityCode4" townId="townCode4" countryId="countryCode4" readOnly="true" cityCode="230184" townCode="100" countryCode="200" groupValue="马家屯"/><BR>
		<b>只读不显示市只显示其它三级的示例:</b><BR><simple:casCity showCity="false" showGroup="true" cityId="cityCode5" cityName="cityCode5" townId="townCode5" countryId="countryCode5" countryName="countryCode5" readOnly="true" cityCode="230184" townCode="100" countryCode="200" groupValue="马家屯"/><BR>
		<b>只读不显示屯只显示其它三级的示例:</b><BR><simple:casCity showCity="true" showGroup="false" cityId="cityCode6" townId="townCode6" countryId="countryCode6" readOnly="true" cityCode="230184" townCode="100" countryCode="200" groupValue="马家屯" showText="false"/><BR>
		<br>
		
		<HR>
			下拉列表形式的数据字典属性说明(以下属性名称在使用时需严格区分大小写):<BR>
			  <TABLE border=1 bordercolor='green' width=95% align=center>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">showCity:</FONT></TD>
				<TD>是否显示市下拉框,<FONT COLOR="RED">必填项</FONT>,默认值为false(不显示)</TD>
			  </TR>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">cityId:</FONT></TD>
				<TD>是否显示屯文本框,<FONT COLOR="RED">必填项</FONT>,默认值为true(显示)</TD>
			  </TR>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">cityId:</FONT></TD>
				<TD>市下拉框的id,非必填项,默认值为cityCode,用法同表单元素id</TD>
			  </TR>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">townId:</FONT></TD>
				<TD>乡镇下拉框的id,非必填项,默认值为townCode,用法同表单元素id</TD>
			  </TR>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">countryId:</FONT></TD>
				<TD>村下拉框的id,非必填项,默认值为countryCode,用法同表单元素id</TD>
			  </TR>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">townId:</FONT></TD>
				<TD>屯文本框的id,非必填项,默认值为groupId,用法同表单元素id</TD>
			  </TR>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">cityName:</FONT></TD>
				<TD>市下拉框的name,非必填项,默认值为cityCode,用法同表单元素name</TD>
			  </TR>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">townName:</FONT></TD>
				<TD>乡镇下拉框的name,非必填项,默认值为townCode,用法同表单元素name</TD>
			  </TR>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">countryName:</FONT></TD>
				<TD>村下拉框的name,非必填项,默认值为countryCode,用法同表单元素name</TD>
			  </TR>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">groupName:</FONT></TD>
				<TD>村下拉框的name,非必填项,默认值为groupName,用法同表单元素name</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">cssClass:</FONT></TD>
				<TD>easyUI的样式类名,非必填项,默认为easyui-combobox</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">cityWidth:</FONT></TD>
				<TD>市下拉框宽度设定,单位为px,非必填项,默认为150,px单位是写死的,如果有需要的话跟我说,我可以修改源码为占百分比形式的相对宽度,目前没必要</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">townWidth:</FONT></TD>
				<TD>乡下拉框宽度设定,单位为px,非必填项,默认为150,px单位是写死的,如果有需要的话跟我说,我可以修改源码为占百分比形式的相对宽度,目前没必要</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">countryWidth:</FONT></TD>
				<TD>村下下框宽度设定,单位为px,非必填项,默认为150,px单位是写死的,如果有需要的话跟我说,我可以修改源码为占百分比形式的相对宽度,目前没必要</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">groupWidth:</FONT></TD>
				<TD>屯文本框宽度设定,单位为px,非必填项,默认为150,px单位是写死的,如果有需要的话跟我说,我可以修改源码为占百分比形式的相对宽度,目前没必要</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">height:</FONT></TD>
				<TD>高度设定,单位为px,非必填项,默认为25,px单位是写死的,如果有需要的话跟我说,我可以修改源码为占百分比形式的相对宽度,目前没必要</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">canEdit:</FONT></TD>
				<TD>是否可编辑,非必填项,默认为false,不区分大小写</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">readOnly:</FONT></TD>
				<TD>
					是否只读,表示仅用于显示,默认为false,非必填项,不区分大小写.<BR>
					<FONT COLOR="RED">如果需要设置只读,则必须填写townCode,countryCode属性,否则不显示任何内容,cityCode如果有就填,没有默认五常市</FONT>
				</TD>
			  </TR>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">cityCode:</FONT></TD>
				<TD>城市编码,用于需要某项默认被选中或只读显示下拉框时填写</TD>
			  </TR>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">townCode:</FONT></TD>
				<TD>乡镇编码,用于需要某项默认被选中或只读显示下拉框时填写</TD>
			  </TR>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">countryCode:</FONT></TD>
				<TD>村编码,用于需要某项默认被选中或只读显示下拉框时填写</TD>
			  </TR>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">groupValue:</FONT></TD>
				<TD>屯的文本框取值,用于需要某项默认被选中或只读显示时填写</TD>
			  </TR>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">showText:</FONT></TD>
				<TD>是否显示文本提示</TD>
			  </TR>
			  </TABLE>
	</div>
</body>
</html>