<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
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
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">
		<table id="toDoListData" class="easyui-datagrid" striped="true" singleSelect="false">
			<thead>
				<tr>
					<th field="toDoList" width="500" align="center">待办事项类型</th>
					<th field="toDoCount" width="200" align="center">待办事项数量</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="toDoOne" items="${toDoLists}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${toDoOne.toDoList}</td>
					<td height="30" align="center" nowrap>${toDoOne.toDoCount}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	
<script type="text/javascript">
$(document).ready(function(){
	var winHeight = $(window).height();
	$("#toDoListData").datagrid({
		height:winHeight - 50,
        rownumbers: true,
        fitColumns: false,
        checkOnSelect:false
	});
});

</script>
</body>
</html>