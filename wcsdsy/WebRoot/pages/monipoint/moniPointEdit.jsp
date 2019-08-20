<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
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
<script type="text/javascript" src="${ctx}/js/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">	
		<form id="addFrom" class="easyui-form" method="post" data-options="novalidate:true">
			<input name="id" value="${moniPoint.id}" type="hidden">
			<fieldset class="fieldset_common_style">
				<table class="table_common_style">
					<tr>
		    			<td class="table_common_td_label_style">监测点编号：</td>
		    			<td class="table_common_td_txt_style">
		    				<input class="easyui-textbox" name="monitorPointCode"  value="${moniPoint.monitorPointCode}" style="width:170px;">
		    				<span class="span_common_mustinput_style">*</span>
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">监测点名称：</td>
		    			<td class="table_common_td_txt_style">
		    				<input class="easyui-textbox" name="monitorPointName"  value="${moniPoint.monitorPointName}" style="width:170px;">
		    				<span class="span_common_mustinput_style">*</span>
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">监测点类型：</td>
		    			<td class="table_common_td_txt_style">
		    				<simple:select id="monitorPointType" name="monitorPointType" codeKey="MonitorPointType" entityName="CommonData" width="170px;" value="${moniPoint.monitorPointType}"/>
		    				<span class="span_common_mustinput_style">*</span>
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">监测点地址：</td>
		    			<td class="table_common_td_txt_style">
		    				<input class="easyui-textbox" type="monitorPointAddress" name="monitorPointAddress" value="${moniPoint.monitorPointAddress }" style="width:170px;">
		    				<span class="span_common_mustinput_style">*</span>
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">监测点描述：</td>
		    			<td class="table_common_td_txt_style">
		    				<input class="easyui-textbox" type="text" name="sectionDescription" value="${moniPoint.sectionDescription}"  style="width:170px;"></input>
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">经度：</td>
		    			<td class="table_common_td_txt_style">
		    				<input class="easyui-textbox" type="text" name="longitude" value="${moniPoint.longitude}"  style="width:170px;"></input>
		    				<span class="span_common_mustinput_style">*</span>
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">纬度：</td>
		    			<td class="table_common_td_txt_style">
		    				<input class="easyui-textbox" type="text" name="latitude" value="${moniPoint.latitude}"  style="width:170px;"></input>
		    				<span class="span_common_mustinput_style">*</span>
		    			</td>
	    			</tr>
	    			<tr height="30">
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
	if($("input[name='monitorPointCode']").val()==""){
		alert("请填写监测点编号！");
		return false;
	}
	if($("input[name='monitorPointName']").val()==""){
		alert("请填写监测点名称！");
		return false;
	}
	if($("input[name='monitorPointAddress']").val()==""){
		alert("请填写监测点地址！");
		return false;
	}
	/**
	if($("input[name='sectionDescription']").val()==""){
		alert("请填写监测点描述！");
		return false;
	}
	**/
	if($("input[name='longitude']").val()==""){
		alert("请填写经度！");
		return false;
	}else if (!isNumber($("input[name='longitude']").val())){
		alert("经度请输入数字！");
		return false;
	}
	if($("input[name='latitude']").val()==""){
		alert("请填写纬度！");
		return false;
	}else if (!isNumber($("input[name='latitude']").val())){
		alert("纬度请输入数字！");
		return false;
	}
	
	return true;
}

</script>
</body>
</html>