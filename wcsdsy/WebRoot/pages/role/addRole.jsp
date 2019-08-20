<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<title>添加角色-五常优质水稻溯源监管平台</title>
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
			<form id="addroleform" action="${ctx}/role/addRole" class="easyui-form" method="post" data-options="novalidate:true">
				<table class="table_common_style">
					<tr>
		    			<td class="table_common_td_label_style">角色编号:</td>
		    			<td class="table_common_td_txt_style">
		    				<input class="easyui-textbox" type="text" id="roleCode" name="roleCode" style="width:200px;"></input>
		    				<span class="span_common_mustinput_style">*</span>
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">角色名:</td>
		    			<td class="table_common_td_txt_style">
	    					<input class="easyui-textbox" type="text" id="roleName" name="roleName" style="width:200px;"></input>
	    					<span class="span_common_mustinput_style">*</span>
	    				</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_comment_style">备注:</td>
		    			<td class="table_common_td_txt_style">
		    				<textarea  class="easyui-textbox" rows="2" id="remark" name="remark" style="width:200px;height:60px" data-options="multiline:true" ></textarea>
		    			</td>
	    			</tr>
	    			<tr height="50">
		    			<td colspan="2" align="center">
		    			<a href="#" class="easyui-linkbutton" onclick="addForm_check()" data-options="iconCls:'icon-save'">保存</a>
		    			<a href="#" class="easyui-linkbutton" style="margin-left:40px;" onclick="closeDialog()" data-options="iconCls:'icon-cancel'">取消</a>
		    			</td>
	    			</tr>
				</table>
			</form>
		</fieldset>
	</div>
<script type="text/javascript">
function addForm_check()
{
	var roleCode = $.trim($("#roleCode").val());
	var roleName = $.trim($("#roleName").val());
	
	if(roleCode == '') {
		$.messager.alert('警告','角色编号不能为空。','warning');
		return false;
	}
	if(roleName == ''){
		$.messager.alert('警告','角色名不能为空。','warning');
		return false;
	}
	var a = $('#addroleform').toObject();
	showLoading();
	Public.ajaxPost('addRole', JSON.stringify(a), function(e) {
		hideLoading();
		if (200 == e.status) {
			$.messager.alert('提示','操作成功。','info');
			$('#addDialog').window('close');
			$('#roleform').submit();
		} else {
			parent.parent.Public.tips({
				type : 1,
				content : "失败！" + e.msg
			});
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