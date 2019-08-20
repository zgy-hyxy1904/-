<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>   
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理-五常优质水稻溯源监管平台</title>
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
			<form id="roleform" name="roleform" method="post" action="${ctx}/role/list">
			<table class="table_common_style">
				<tr>
					<td class="table_common_td_label_style">角色编号：</td>
					<td class="table_common_td_txt_style">
						<input name="roleCode" id="roleCode" value="${role.roleCode}" class="easyui-textbox" style="width:200px;height:25px;" readonly>
						<input type='hidden' id="jsonData" value="${jsonData}" />
					</td>
				</tr>
				<tr>
					<td class="table_common_td_label_style">角色名称：</td>
					<td class="table_common_td_txt_style">
						<input name="roleName" value="${role.roleName}" class="easyui-textbox" style="width:200px;height:25px" readonly>
					</td>
				<tr>
				<tr>
    			<td class="table_common_td_label_comment_style">备注:</td>
    			<td class="table_common_td_txt_style">
    				<textarea name="remark" rows="2" style="width:250px;" readonly>${role.remark}</textarea>
    			</td>
	   			</tr>
	   			<tr>
	   				<td class="table_common_td_label_comment_style">角色权限:</td>
	    			<td heigth="50px">
	    				<div class="easyui-panel" style="padding:5px">
						<ul id="authTree" class="easyui-tree" data-options='data:${jsonData},animate:true,checkbox:true'></ul>
						</div>	
			    	</td>
				</tr>
				<tr height="60">
					<td colspan="2" align="center">
		    			<a href="#" id="saveBt" class="easyui-linkbutton" onclick="save()" data-options="iconCls:'icon-save'">保存</a>
		    			<a href="#" id="cancalBt" class="easyui-linkbutton" style="margin-left:30px;" onclick="closeDialog()" data-options="iconCls:'icon-cancel'">取消</a>
	    			</td>
				</tr>
			</table>
			</form>
		</fieldset>
	</div>
<script type="text/javascript">
function save()
{
	var funIds = [];
	var nodes = $('#authTree').tree('getChecked');	
	for(var i=0; i< nodes.length; i++) { 
		funIds.push(nodes[i].id);
	}
	var roleCode = $("#roleCode").val();
	$("#saveBt").linkbutton("disable");
	$("#cancalBt").linkbutton("disable");
	showLoading();
	Public.ajaxGet('${ctx}/role/saveRoleAuth', {roleCode : roleCode, funIds : funIds}, function(e) {
		hideLoading();
		$("#saveBt").linkbutton("enable");
		$("#cancalBt").linkbutton("enable");
		if (200 == e.status) {
			$.messager.alert('提示','操作成功。','info');
			closeDialog();
		} else {
			$.messager.alert('错误',e.msg,'error');
		}
	});
}

function closeDialog()
{
	$('#addDialog').dialog('close');
}

</script>
</body>
</html>