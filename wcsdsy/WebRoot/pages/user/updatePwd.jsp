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
			<table class="table_common_style" border="0">
				<tr>
	    			<td class="table_common_td_label_style" width="50%">原始密码:</td>
	    			<td class="table_common_td_txt_style">
		    			<input class="easyui-textbox" type="password" id="oldPwd" name="oldPwd" style="width:200px;" value=""></input>
		    			<input type="hidden" name="id" style="width:200px;" value="${user.id}"></input>
		    			<span class="span_common_mustinput_style">*</span>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">新密码:</td>
	    			<td class="table_common_td_txt_style">
	    				<input class="easyui-textbox" id="password" type="password" name="password" style="width:200px;" value=""></input>
	    				<span class="span_common_mustinput_style">*</span>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">确认新密码:</td>
	    			<td class="table_common_td_txt_style">
	    				<input class="easyui-textbox" id="newPassword" type="password" name="newPassword" style="width:200px;" value=""></input>
	    				<span class="span_common_mustinput_style">*</span>
	    			</td>
    			</tr>
    			<tr height="60">
	    			<td colspan="2" align="center">
	    				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="formCheck()" data-options="iconCls:'icon-save'">保存</a>
	    			</td>
    			</tr>
			</table>
		</form>
	</div>
<script type="text/javascript">
function formCheck(){
	if( $("#oldPwd").textbox('getValue') == '' ){
		$.messager.alert('警告','原始密码不能为空！','warning');
		return false;
	}
	if( $("#password").textbox('getValue') == '' ){
		$.messager.alert('警告','新密码不能为空!','warning');
		return false;
	}
	if( $("#newPassword").textbox('getValue') == '' ){
		$.messager.alert('警告','确认新密码不能为空!','warning');
		return false;
	}
	if( $("#password").textbox('getValue') != $("#newPassword").textbox('getValue') ){
		$.messager.alert('警告','新密码和确认新密码输入不一致!','warning');
		return false;
	}
	if($("#password").textbox('getValue').length < 6){
		$.messager.alert('警告','新密码至少6位！','warning');
		return false;
	}
	var a = $('#editUserFrom').toObject();
	Public.ajaxPost('${ctx}/user/updatePwd', JSON.stringify(a), function(e) {
		if (200 == e.status) {
			$("#oldPwd").textbox('setValue', "");
			$("#password").textbox('setValue', "");
			$("#newPassword").textbox('setValue', "");
			
			$.messager.alert('警告','操作成功','warning');
		}else{
			$.messager.alert('错误','操作失败:' + e.data.msg,'error');
		}
	});
}

</script>
</body>
</html>