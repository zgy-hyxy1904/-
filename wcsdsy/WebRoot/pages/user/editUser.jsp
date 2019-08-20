<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
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
	    			<td class="table_common_td_label_style">用户名：</td>
	    			<td class="table_common_td_txt_style">
		    			<input class="easyui-textbox" type="text" name="userID" style="width:150px;" value="${user.userID}" readonly></input>
		    			<input type="hidden" name="id" style="width:150px;" value="${user.id}"></input>
		    			<span class="span_common_mustinput_style">*</span>
		    		</td>
		    		<td class="table_common_td_label_style">密码：</td>
	    			<td class="table_common_td_txt_style">
	    				<input type="password" class="easyui-textbox" type="text" name="password" style="width:150px;" value="${user.password}" readonly></input>
	    				<span class="span_common_mustinput_style">*</span>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">姓名：</td>
	    			<td class="table_common_td_txt_style">
	    				<input class="easyui-textbox" type="text" name="userName" style="width:150px;" value="${user.userName}" readonly></input>
	    				<span class="span_common_mustinput_style">*</span>
	    			</td>
	    			<td class="table_common_td_label_style">用户类型：</td>
	    			<td class="table_common_td_txt_style">
						<simple:select id="userType" name="userType" codeKey="UserType" entityName="commonData" value="${user.userType}" width="150" readOnly="true" canEdit="false"/>
	    				<span class="span_common_mustinput_style">*</span>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">单位名称：</td>
	    			<td class="table_common_td_txt_style">
						<simple:select id="companyCode" name="companyCode" entityName="company" value="${user.companyCode}" width="150" readOnly="true" canEdit="false"/>
	    			</td>
	    			<td class="table_common_td_label_style">联系方式：</td>
	    			<td class="table_common_td_txt_style">
						<input class="easyui-textbox" type="text" id="tel" name="tel" style="width:150px;" value="${user.tel}"></input>
	    				<span class="span_common_mustinput_style">*</span>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">邮箱：</td>
	    			<td class="table_common_td_txt_style">
						<input class="easyui-textbox" type="text" id="email" name="email" style="width:150px;" value="${user.email}"></input>
	    				<span class="span_common_mustinput_style">*</span>
	    			</td>
	    			<td class="table_common_td_label_style">角色：
	    			</td>
	    			<td class="table_common_td_txt_style">
    				   <input class="easyui-combobox" id="role" name="role" style="width:150px" url=''
							data-options='valueField:"roleCode",textField:"text",data:${rolelist},multiple:true,panelHeight:"auto"' >
	    				<span class="span_common_mustinput_style">*</span>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_comment_style">备注：</td>
	    			<td colspan="3" class="table_common_td_txt_style">
	    				<textarea  class="easyui-textbox" rows="2" id="remark" name="remark" style="width:400px;height:60px" data-options="multiline:true" >${user.remark}</textarea>
	    			</td>
    			</tr>
    			<tr height="30">
	    			<td colspan="4" align="center">
	    			<a href="#" class="easyui-linkbutton" onclick="formCheck()" data-options="iconCls:'icon-save'">保存</a>
	    			<a href="#" class="easyui-linkbutton" style="margin-left:15px;" onclick="closeEdiDialog()" data-options="iconCls:'icon-cancel'">取消</a>
	    			</td>
    			</tr>
			</table>
			</fieldset>
		</form>
	</div>
<script type="text/javascript">
function formCheck(){
	var tel = $.trim($("#tel").val());
	var email = $.trim($("#email").val());
	if(tel == ''){
		$.messager.alert('警告','联系方式不能为空。','warning');
		return false;	
	}
	if(!isTel(tel)){
		$.messager.alert('警告','请填写正确的联系方式。','warning');
		return false;
	}
	if(!isEmail(email)){
		$.messager.alert('警告','请填写正确的邮箱。','warning');
		return false;
	}
	var a = $('#editUserFrom').toObject();
	a.role = $('#role').combobox('getValues').toString();
	showLoading();
	Public.ajaxPost('editUser', JSON.stringify(a), function(e) {
		hideLoading();
		if (200 == e.status) {
			$.messager.alert('提示','修改成功。','info');
			$('#addDialog').window('close');
			$('#userform').submit();
		} else {
			parent.parent.Public.tips({
				type : 1,
				content : "失败！" + e.msg
			});
		}
	});
}

function closeEdiDialog(){
	$('#addDialog').dialog('close');
}
</script>
</body>
</html>