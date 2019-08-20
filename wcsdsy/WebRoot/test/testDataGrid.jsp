<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据字典的标签使用示例</title>
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
		<H2>数据字典的标签使用说明:</H2>
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
				&lt;simple:select
					id="idType" 
					name="idType" 
					codeKey="IDType" 
					entityName="commonData"
					cssClass="easyui-combobox"
					value="01"
					readOnly="false"
					hasPleaseSelectOption="TRUE"
					width="150"
					height="20"
					canEdit="true"
					onChange="javascript:alert(this.options.length);"&gt;&lt;/simple:select&gt;
			</code>
			</div>
			<B>3.如果数据字典是企业信息,则在Controller中可以通过以下代码获取企业名称:</B><BR>
			<div style='background-color:#999999'>
			<code>
				request.getParameter("companyName");
			</code>
			</div>
			<BR>

		<H2>示例展示(自己Copy源码改):</H2>
		<HR>
		普通示例:<simple:select id='idType1' name="idType" codeKey="IDType" entityName="commonData" hasPleaseSelectOption="true"/><BR>
		某选项需默认被选中示例:<simple:select id="idType2" name="idType" codeKey="IDType" entityName="CommonData" value="02"/><BR>
		只读示例:<simple:select id="idType3" name="idType" codeKey="IDType" entityName="commondata" value="02" readOnly="true"/><BR>
		内容可被编辑示例:<simple:select id='idType4' name="idType" codeKey="IDType" entityName="COMMONDATA" canEdit="true"/><BR>
		样式设置示例:<simple:select id="idType5" name="idType" codeKey="IDType" entityName="commonDATA" width="500" height="30" cssClass="easyui-combobox"/><BR>
		onChange事件示例:<simple:select id="idType6" name="idType" codeKey="IDType" entityName="COMMONdata" onChange="javascript:window.alert('easyUI的下拉列表onChange事件有BUG,请使用$(#id).combobox({onChange:function(newValue,oldValue){}});方式解决,自己去API中查');"/><BR>
		<HR>
		企业信息普通示例:<simple:select id="company1" name="companyCode" entityName="company"/><BR>
		根据企业类型查看企业信息示例:<simple:select id="company2" name="companyCode" codeKey="01" entityName="Company"/><BR>
		某企业需默认被选中示例:<simple:select id="company3" name="companyCode" entityName="COMPANY" value="jfly"/><BR>
		只读示例:<simple:select id="company4" name="companyCode" entityName="company" value="jfly" readOnly="true"/><BR>
		内容可被编辑示例:<simple:select id='company5' name="companyCode" codeKey="02" entityName="company" canEdit="true"/><BR>
		<HR>
		年份信息普通示例:<simple:select id="yearCode1" name="yearCode" entityName="YearCode"/><BR>
		某年份需默认被选中示例:<simple:select id="yearCode2" name="yearCode" entityName="yearcode" value="2015"/><BR>
		只读示例:<simple:select id='yearCode3' name="yearCode" entityName="yearCode" value="2015" readOnly="true"/><BR>
		内容可被编辑示例:<simple:select id='yearCode4' name="yearCode" entityName="yearcode" canEdit="true"/><BR>
		<HR>
		种子品种普通示例:<simple:select id="seedCode1" name="seedCode" entityName="SeedVariety"/><BR>
		某种子品种需默认被选中示例:<simple:select id="seedCode2" name="seedCode" entityName="seedvariety" value="01"/><BR>
		只读示例:<simple:select id='seedCode3' name="seedCode" entityName="seedVariety" value="01" readOnly="true"/><BR>
		内容可被编辑示例:<simple:select id='seedCode4' name="seedCode" entityName="SeedVariety" canEdit="true"/><BR>
		
		
		

		<HR>
			下拉列表形式的数据字典属性说明(以下属性名称在使用时需严格区分大小写):<BR>
			  <TABLE border=1 bordercolor='green' width=95% align=center>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">id:</FONT></TD>
				<TD><FONT COLOR="RED">必填项</FONT>,用法同表单元素id</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">name:</FONT></TD>
				<TD><FONT COLOR="RED">必填项</FONT>,用法同表单元素name</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">entityName:</FONT></TD>
				<TD>业务Entity名称,目前涉及数据字典的业务的Entity有 CommonData,Company,YearCode,SeedVariety 不区分大小写,如不填写则默认为CommonData</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">codeKey:</FONT></TD>
				<TD>
					1.<FONT COLOR="RED">当entityName为CommonData时,此属性表示代码主档标识</FONT>,区分大小写,则必须填写此属性,否则抛出RuntimeException但不影响程序执行.此属性的取值参见commonData表中CodeKey字段<BR>
					2.<FONT COLOR="RED">当entityName为Company时</FONT>,此属性表示企业类型,可不填写,如不填写则默认取全部企业信息,此属性的取值参见CommonData表中CodeKey=CompanyType时的CodeCode字段
					3.<FONT COLOR="RED">当entityName为YearCode时,此属性无用,就当它不存在</FONT>
					4.<FONT COLOR="RED">当entityName为SeedVariety时,此属性无用,就当它不存在</FONT>
				</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">cssClass:</FONT></TD>
				<TD>easyUI的样式类名,非必填项,默认为easyui-combobox</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">value:</FONT></TD>
				<TD>option的value设置,非必填项.<BR>
					1.<FONT COLOR="RED">如果只有value属性被设置,则此value对应的选项是默认被选中状态</FONT><BR>
					2.<FONT COLOR="RED">如果同时设置了readOnly属性,则下拉框中只显示一个符合value的选项,间接实现只读</FONT>
				</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">readOnly:</FONT></TD>
				<TD>
					是否只读,默认为false,非必填项,不区分大小写.<BR>
					<FONT COLOR="RED">如果需要设置只读,则必须填写value属性,否则只读无效,同时不要设置canEdit属性的取值为true</FONT>
				</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">hasPleaseSelectOption:</FONT></TD>
				<TD>是否具有"-=请选择=-"的选项,该选项的取值为"",默认为false,非必填项,不区分大小写.</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">width:</FONT></TD>
				<TD>宽度设定,单位为px,非必填项,默认为200,px单位是写死的,如果有需要的话跟我说,我可以修改源码为占百分比形式的相对宽度,目前没必要</TD>
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
				<TD><FONT COLOR="RED">onChange:</FONT></TD>
				<TD>当下拉列表框取值发生变化时触发脚本,非必填项</TD>
			  </TR>
			  </TABLE>
	</div>
</body>
</html>