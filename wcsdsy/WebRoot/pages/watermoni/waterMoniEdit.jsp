<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@page import="com.bicsoft.sy.entity.MoniPoint,java.util.List" %>
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
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:0 10px;">	
		<form id="addFrom" class="easyui-form" method="post" data-options="novalidate:true">
			<input name="id" value="${waterMoni.id}" type="hidden">
			<fieldset id="queryBlock" class="fieldset_common_style">
				<table class="table_common_style">
					<tr>
		    			<td class="table_common_td_label_style">监测日期：</td>
		    			<td class="table_common_td_txt_style">
		    				<input class="easyui-datebox" name="monitorDate"  value="${waterMoni.monitorDate}"
	            data-options="required:true,showSeconds:false" style="width:170px" editable="false">
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">断面名称：</td>
		    			<td class="table_common_td_txt_style">
		    				<%@include file="../../moniPoint.jsp" %>
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">PH值：</td>
		    			<td class="table_common_td_txt_style">
			    			<input class="easyui-textbox" type="text" name="ph" value="${waterMoni.ph }" data-options="min:0,max:999999999.99,precision:2,groupSeparator:','" style="width:170px;"></input>
			    			<span class="span_common_mustinput_style">*</span>
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">DO(mg/L)：</td>
		    			<td class="table_common_td_txt_style">
		    				<input class="easyui-textbox" type="text" name="doValue" value="${waterMoni.doValue }"  data-options="min:0,max:999999999.99,precision:2,groupSeparator:','" style="width:170px;"></input>
		    				<span class="span_common_mustinput_style">*</span>
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">COD Mn(mg/L)：</td>
		    			<td class="table_common_td_txt_style">
		    				<input class="easyui-textbox" type="text" name="codmn" value="${waterMoni.codmn }"  data-options="min:0,max:999999999.99,precision:2,groupSeparator:','" style="width:170px;"></input>
		    				<span class="span_common_mustinput_style">*</span>
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">BOD5(mg/L)：</td>
		    			<td class="table_common_td_txt_style">
		    				<input class="easyui-textbox" type="text" name="bod5" value="${waterMoni.bod5 }"  data-options="min:0,max:999999999.99,precision:2,groupSeparator:','" style="width:170px;"></input>
		    				<span class="span_common_mustinput_style">*</span>
		    			</td>
	    			</tr>
	
	    			<tr>
		    			<td class="table_common_td_label_style">氨氮(mg/L)：</td>
		    			<td class="table_common_td_txt_style">
		    				<input class="easyui-textbox" type="text" name="nh3n" value="${waterMoni.nh3n }"  data-options="min:0,max:999999999.99,precision:3,groupSeparator:','" style="width:170px;"></input>
		    				<span class="span_common_mustinput_style">*</span>
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">总磷(mg/L)：</td>
		    			<td class="table_common_td_txt_style">
		    				<input class="easyui-textbox" type="text" name="tp" value="${waterMoni.tp }"  data-options="min:0,max:999999999.99,precision:3,groupSeparator:','" style="width:170px;"></input>
		    				<span class="span_common_mustinput_style">*</span>
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">COD Cr(mg/L)：</td>
		    			<td class="table_common_td_txt_style">
		    				<input class="easyui-textbox" type="text" name="codcr" value="${waterMoni.codcr }"  data-options="min:0,max:999999999.99,precision:2,groupSeparator:','" style="width:170px;"></input>
		    				<span class="span_common_mustinput_style">*</span>
		    			</td>
	    			</tr>
	    			<tr height="80">
		    			<td colspan="2" align="center">
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
	if(!check()) return ;
	var a = $('#addFrom').toObject();
	Public.ajaxPost('save', JSON.stringify(a), function(e) {
		if (200 == e.status) {
			$.messager.alert('提示','保存成功。','info');
			closeEdiDialog();
			$('#inputForm').submit();
		} else {
			$.messager.alert('错误','保存失败','error');
		}
	});
}

function closeEdiDialog(){
	$('#addDialog').dialog('close');
}

function check(){
	if($("input[name='ph']").val()==""){
		$.messager.alert('警告','请填写PH值！','warning');
		return false;
	}else if (!isNumber($("input[name='ph']").val())){
		$.messager.alert('警告','PH值请输入数字！','warning');
		return false;
	}
	if($("input[name='doValue']").val()==""){
		$.messager.alert('警告','请填写DO值！','warning');
		return false;
	}else if (!isNumber($("input[name='doValue']").val())){
		$.messager.alert('警告','DO值请输入数字！','warning');
		return false;
	}
	if($("input[name='codmn']").val()==""){
		$.messager.alert('警告','请填写COD Mn值！','warning');
		return false;
	}else if (!isNumber($("input[name='codmn']").val())){
		$.messager.alert('警告','请填写COD Mn值！','warning');
		return false;
	}
	if($("input[name='bod5']").val()==""){
		$.messager.alert('警告','请填写BOD5值！','warning');
		return false;
	}else if (!isNumber($("input[name='bod5']").val())){
		$.messager.alert('警告','BOD5值请输入数字！','warning');
		return false;
	}
	if($("input[name='nh3n']").val()==""){
		$.messager.alert('警告','请填写氨氮值！','warning');
		return false;
	}else if (!isNumber($("input[name='nh3n']").val())){
		$.messager.alert('警告','氨氮值请输入数字！','warning');
		return false;
	}
	if($("input[name='tp']").val()==""){
		$.messager.alert('警告','请填写总磷值！','warning');
		return false;
	}else if (!isNumber($("input[name='tp']").val())){
		$.messager.alert('警告','总磷值请输入数字！','warning');
		return false;
	}
	if($("input[name='codcr']").val()==""){
		$.messager.alert('警告','请填写COD Cr值！','warning');
		return false;
	}else if (!isNumber($("input[name='codcr']").val())){
		$.messager.alert('警告','COD Cr值请输入数字！','warning');
		return false;
	}
	return true;
}
</script>
</body>
</html>