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
<script type="text/javascript" src="${ctx}/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">
		<form id="addFrom" class="easyui-form" method="post" data-options="novalidate:true">
			<input type="hidden" name="id" value="${id}">
			<input type="hidden" name="status" value="04">
			
			<table class="table_common_style">
				<tr>
	    			<td class="table_common_td_label_comment_style">驳回原因
	    			</td>
	    			<td class="table_common_td_txt_style">
	    				<textarea class="easyui-textbox" rows="3" id="description" name="reason" cols="42" style="font-size:12px;height:180px" data-options="multiline:true"></textarea>
	    				<span class="span_common_mustinput_style">*</span>
	    			</td>
    			</tr>
    			<tr height="30">
	    			<td colspan="2" align="center">
	    			<a href="#" class="easyui-linkbutton" onclick="saveReason()" data-options="iconCls:'icon-save'">保存</a>
	    			<a href="#" class="easyui-linkbutton" style="margin-left:15px;" onclick="closeEdiDialog()" data-options="iconCls:'icon-cancel'">取消</a>
	    			</td>
    			</tr>
			</table>
		</form>
	</div>
<script type="text/javascript">
function saveReason(){
	var a = $('#addFrom').toObject();
	showLoading();
	Public.ajaxPost('saveReason', JSON.stringify(a), function(e) {
		hideLoading();
		if (200 == e.status) {
			$.messager.alert('警告','保存成功！','warning');
			closeEdiDialog();
			showLoading();
			retList();
		} else {
			$.messager.alert('错误','保存失败！' + e.msg,'error');
		}
	});
}

function closeEdiDialog(){
	$('#bhDialog').dialog('close');
}


</script>
</body>
</html>