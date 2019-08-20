<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<title>用户管理-五常优质水稻溯源监管平台</title>
<link rel="stylesheet" type="text/css" href="${ctx}/style/index.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/color.css">
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript" src="${ctx}/js/form2js.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>

</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">
		<fieldset class="fieldset_common_style">	
		<table id="onedata" height="300px" class="easyui-datagrid" striped="true" singleSelect="false" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="year" width="100" align="center">土地类型</th>
					<th field="companyName" width="140" align="center">实（亩）</th>
					<th field="name" width="100" align="center">测量亩</th>
					<th field="idNumber" width="130" align="center">地块类别</th>
					<th field="idNumber" width="150" align="center">地块名称</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="graiForeD" items="${list}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>
						<simple:showName entityName="commonData" codeKey="PlowlandType" value="${graiForeD.landType }"></simple:showName>
					</td>
					<td height="30" align="center" nowrap>${graiForeD.actualMu}</td>
					<td height="30" align="center" nowrap>${graiForeD.measurementMu}</td>
					<td height="30" align="center" nowrap>
						<simple:showName entityName="commonData" codeKey="PlowlandClass" value="${graiForeD.landClass }"></simple:showName>
					</td>
					<td height="30" align="center" nowrap>${graiForeD.landName}</td>
				</tr>
				</c:forEach>		
			</tbody>	
		</table>
		<div style="padding:5px;" align="center">
	    	<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:15px;" onclick="closeDialog()" data-options="iconCls:'icon-cancel'">关闭</a>
		</div>
		</fieldset>
	</div>
	
	<div id="addDialog"></div>
	<div id="importDialog"></div>
	
<script type="text/javascript">

function closeDialog()
{
	$('#addDialog').dialog('close');
}
</script>
</body>
</html>