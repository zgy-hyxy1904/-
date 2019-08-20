<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<title>查看角色详情-五常优质水稻溯源监管平台</title>
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
	<div region="center" border="false" style="padding:5px;">
		<fieldset class="fieldset_common_style">
			<table class="table_common_style">
				<tr>
	    			<th class="table_common_td_label_style">角色编号:</th>
	    			<td class="table_common_td_txt_style">${role.roleCode}</td>
	   			</tr>
	   			<tr>
	    			<th class="table_common_td_label_style">角色名:</th>
	    			<td class="table_common_td_txt_style">${role.roleName}</td>
	   			</tr>
	   			<tr>
	    			<th class="table_common_td_label_comment_style">备注:</th>
	    			<td class="table_common_td_txt_style">
	    				${role.remark}
	    			</td>
	   			</tr>
	   				<tr height="50">
		    			<td colspan="2" align="center">
		    			<a href="#" class="easyui-linkbutton" style="margin-left:15px;" onclick="closeDialog()" data-options="iconCls:'icon-cancel'">关闭</a>
		    			</td>
	    			</tr>
			</table>
		</fieldset>
	</div>
<script type="text/javascript">
function closeDialog()
{
	$('#addDialog').dialog('close');
}
</script>
</body>
</html>