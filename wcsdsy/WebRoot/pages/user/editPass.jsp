<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
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
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">	
		<form id="editUserFrom" class="easyui-form" method="post" data-options="novalidate:true">
			<fieldset class="fieldset_common_style">
			<table class="table_common_style">
				<tr>
	    			<td class="table_common_td_label_style">用户名:</td>
	    			<td class="table_common_td_txt_style">
	    			<input class="easyui-textbox" type="text" name="userID" style="width:200px;" value="${user.userID}" readonly></input>
	    			<input type="hidden" name="id" style="width:200px;" value="${user.id}"></input>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">密码:</td>
	    			<td class="table_common_td_txt_style">
	    				<input class="easyui-textbox" type="password" name="password" style="width:200px;" value="${user.password}"></input>
	    				<span class="span_common_mustinput_style">*</span>
	    			</td>
    			</tr>
    			<tr height="50">
	    			<td colspan="2" align="center">
	    			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="formCheck()" data-options="iconCls:'icon-save'">保存</a>
	    			<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:30px;" onclick="closeDialog()" data-options="iconCls:'icon-cancel'">取消</a>
	    			</td>
    			</tr>
			</table>
			</fieldset>
		</form>
	</div>
<script type="text/javascript">
function formCheck(){
	var a = $('#editUserFrom').toObject();
	if(a.password == ''){
		$.messager.alert('警告','密码不能为空。','warning');
		return false;
	}else if(a.password.length < 6){
		$.messager.alert('警告','密码至少6位。','warning');
		return false;
	}
	Public.ajaxPost('editPass', JSON.stringify(a), function(e) {
		if (200 == e.status) {
			$.messager.alert('提示','操作成功。','info');
			closeDialog();
		}
	});
}

function closeDialog(){
	$('#addDialog').dialog('close');
}

function form_check(){
	$('#userform').submit();
}
</script>
</body>
</html>