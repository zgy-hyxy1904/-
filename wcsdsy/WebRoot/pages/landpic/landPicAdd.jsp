<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<title>添加土地图片-五常优质水稻溯源监管平台</title>
<link rel="stylesheet" type="text/css" href="${ctx}/style/index.css">
<link rel="stylesheet" type="text/css" href="${ctx}/style/table.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/color.css">
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript" src="${ctx}/js/form2js.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:10px 10px;">	
		<form id="addUserFrom" class="easyui-form" method="post" data-options="novalidate:true">
			<table class="n_input" style="width:100%">
				<tr>
					<td width="50%" valign="top">
						<fieldset style="border: 1px solid #99BBE8; height:300px;">
						<input type="hidden" id="year" value="${year}" />
						<input type="hidden" id="companyCode" value="${companyCode}" />
						<table>
							<tr height="50">
							<td width="20%" align="right">请选择市：</td>
				    			<td width="30%">
				    				<select id="cityCode" name="cityCode" class="easyui-combobox" editable="false" style="width:180px;height:25px" data-options='required:true'>
					    				<c:forEach var="city" items="${cities}" varStatus="status">
					    					<option value="${city.cityCode}">${city.cityName}</option>
					    				</c:forEach>
					    			</select>
								</td>
			    			</tr>
			    			<tr height="50">
				    			<td width="60" align="right">请选择乡镇：</td>
				    			<td width="200">
				    				<select id="townCode" name="townCode" class="easyui-combobox" editable="false" style="width:180px;height:25px" data-options='required:true'>
					    				<option value="">-=请选择=-</option>
					    				<c:forEach var="town" items="${towns}" varStatus="status">
					    					<option value="${town.townCode}">${town.townName}</option>
					    				</c:forEach>
					    			</select>
								</td>
			    			</tr>
			    			<tr height="50">
				    			<td width="60" align="right">图片描述：</td>
				    			<td width="200">
				    				<input class="easyui-textbox" id="fileInfo" style="width:180px; height:25px;">
								</td>
			    			</tr>
			    			<tr height="50">
				    			<td width="60" align="right">选择文件:</td>
				    			<td width="200">
				    				<input type="file" id="file" name="file" size="10"/>
								</td>
			    			</tr>
			    			<tr height="70">
				    			<td colspan="2" align="center">
				    			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="save()" data-options="iconCls:'icon-save'">保存</a>
				    			<a href="javascript:void(0)" style="margin-left:30px;" class="easyui-linkbutton" style="margin-left:15px;" onclick="closeDialog()" data-options="iconCls:'icon-cancel'">关闭</a>
				    			</td>
			    			</tr>
						</table>
						</fieldset>
					</td>
	    			<td width="50%">
	    				<fieldset style="border: 1px solid #99BBE8;height:300px;">
	    				<table border="0">
						<tr>
							<td><img src="${ctx}/images/nothing.png" style="width:360px;height:300px;" border="0"/></td>
						</tr>
						</table>
						</fieldset>
	    			</td>
			</table>
		</form>
	</div>
<script type="text/javascript">
$(document).ready(function(){
	$("#cityCode").combobox({
		valueField:"id",
		textField:"text",
		onChange : getTowns
	});
	$('#townCode').combobox({
        valueField:'id',
        textField:'text',
        onChange : checkUploaded
    });
});

function getTowns(){
	var cityCode = $('#cityCode').combobox('getValue');
	showLoading();
	Public.ajaxGet('${ctx}/landPic/getTowns', {cityCode:cityCode}, function(e) {
		hideLoading();
		if (200 == e.status) {
			$("#townCode").combobox("loadData", JSON.parse(e.data));
			$("#townCode").append("<option value='-=请选择=-'>-=请选择=-</option>");
		} else {
			$.messager.alert('错误',e.msg,'error');
		}
	});
}

function checkUploaded(){
	var companyCode = $("#companyCode").val();
	var cityCode = $('#cityCode').combobox('getValue');
	var townCode = $('#townCode').combobox('getValue');
	showLoading();
	Public.ajaxGet('${ctx}/landPic/checkUploaded', {companyCode:companyCode,cityCode:cityCode,townCode:townCode}, function(e) {
		hideLoading();
		if (200 == e.status) {
			if(e.data){
				$.messager.alert('警告','该乡镇已上传过图片。','warning');
			}
		} else {
			$.messager.alert('错误',e.msg,'error');
		}
	});
}

function closeDialog(){
	$('#addDialog').dialog('close');
}

function save(){
	var cityCode = $('#cityCode').combobox('getValue');
	var townCode = $('#townCode').combobox('getValue');
	var fileInfo = $("#fileInfo").val();
	var file = $("#file").val();
	var year = $("#year").val();
	var companyCode = $("#companyCode").val();
	if(cityCode == ''){
		$.messager.alert('警告','请选择城市。','warning');
		return false;
	}
	if(townCode == ''){
		$.messager.alert('警告','请选择乡镇。','warning');
		return false;
	}
	if(fileInfo == '') {
		$.messager.alert('警告','请填写图片说明。','warning');
		return false;
	}
	if(file == ''){
		$.messager.alert('警告','请选择文件。','warning');
		return false;
	}
	fileInfo = encodeURIComponent(fileInfo);
	showLoading();
	$.ajaxFileUpload({
		url:"${ctx}/landPic/upload?&cityCode="+cityCode+"&townCode="+townCode+"&fileInfo="+fileInfo+"&year="+year+"&companyCode="+companyCode,
		secureuri:false,
		fileElementId:'file',
		dataType: 'text',
		success: function (data, status){
			hideLoading();
			$.messager.alert('提示','操作成功。','info');
			$('#addDialog').dialog('close');
			$('#landpicform').submit();
		},
		error: function (data, status, e){
			hideLoading();
        	$.messager.alert('错误','上传失败。','error');
		}
	});
}
</script>
</body>
</html>