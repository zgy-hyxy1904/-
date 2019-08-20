<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>根据Code显示名称的标签使用示例</title>
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
		<H2>根据Code显示名称的标签使用说明:</H2>
		<p>
			1.在页面中引入如下标签代码:<BR>
			<div style='background-color:#999999'>
			<code>
				&lt;%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%&gt;
			</code>
			</div>
			<BR>
			2.在需要使用下拉列表根据Code显示名称的位置引入如下组件代码示例:<BR>
			<div style='background-color:#999999'>
			<code>
				&lt;simple:showName
					entityName="commonData"
					codeKey="IDType" 
					value="01"
					"&gt;&lt;/simple:showName&gt;
			</code>
			</div>
			<BR>

		<H2>示例展示(自己Copy源码改):</H2>
		<HR>
		commonData普通示例:
		<BR>
		<simple:showName entityName="commonData" codeKey="IDType" value="01"></simple:showName><BR>
		<HR>
		company普通示例:
		<BR>
		<simple:showName entityName="company" value="jfly"></simple:showName><BR>
		<HR>
		yearCode普通示例:
		<BR>
		<simple:showName entityName="yearcode" value="2015"></simple:showName><BR>
		<HR>
		seedVariety普通示例:
		<BR>
		<simple:showName entityName="seedVariety" value="01"></simple:showName><BR>
		
		
		
		<HR>
			根据Code显示名称属性说明(以下属性名称在使用时需严格区分大小写):<BR>
			  <TABLE border=1 bordercolor='green' width=95% align=center>
			  <TR>
				<TD><FONT COLOR="RED">entityName:</FONT></TD>
				<TD>业务Entity名称,目前涉及根据Code显示名称的业务的Entity有 CommonData,YearCode,Company,SeedVariety 不区分大小写,如不填写则默认为CommonData</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">codeKey:</FONT></TD>
				<TD>
					1.<FONT COLOR="RED">当entityName为CommonData时,此属性表示代码主档标识</FONT>,则必须填写此属性,区分大小写<BR>
					2.<FONT COLOR="RED">当entityName为Company,YearCode,SeedVariety时此属性无用,就当它不存在</FONT>
				</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">value:</FONT></TD>
				<TD><FONT COLOR="RED">必填项.</FONT>此属性用于接收业务编码.</TD>
			  </TR>
		 </TABLE>
	</div>
</body>
</html>