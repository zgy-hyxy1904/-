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
	<div region="center" border="false" style="padding:5px;">	
		<form id="addFrom" class="easyui-form" method="post" data-options="novalidate:true">
			<input name="id" value="${soilMoni.id}" type="hidden">
			<fieldset class="fieldset_common_style">
			<table class="table_common_style">
				<tr>
	    			<td class="table_common_td_label_style">监测日期：</td>
	    			<td class="table_common_td_txt_style">
	    				<input class="easyui-datebox" name="monitorDate"  value="${soilMoni.monitorDate}" readonly
            data-options="required:true,showSeconds:false" style="width:170px" editable="false">
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">监测点位：</td>
	    			<td class="table_common_td_txt_style">
	<select name="monitorPointCode" class="easyui-combobox" style="width:170px;" data-options="editable:false" readonly>
<%
	java.util.List<MoniPoint> list = (List<MoniPoint>)request.getAttribute("pointList");
	
	String _monitorPointCode = (String)request.getAttribute("monitorPointCode");
	System.out.println("code:" + _monitorPointCode); 
	for( MoniPoint point : list ){
%>
	<option value="<%=point.getMonitorPointCode()%>" <%if( _monitorPointCode != null && _monitorPointCode.equals(point.getMonitorPointCode())) {out.println("selected=selected");}%>><%=point.getMonitorPointName()%></option>
<%		
	}
%>
	</select>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">有机质(g/Kg)：</td>
	    			<td class="table_common_td_txt_style">
	    				<input class="easyui-numberbox" type="text" name="omvalue" value="${soilMoni.omvalue }" data-options="min:0,max:999999999.99,precision:2,groupSeparator:','"  style="width:170px;"></input>
	    				<span class="span_common_mustinput_style">*</span>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">碱解氮(mg/Kg)：</td>
	    			<td class="table_common_td_txt_style">
	    				<input class="easyui-numberbox" type="text" name="alkelinen" value="${soilMoni.alkelinen }" data-options="min:0,max:999999999.99,precision:2,groupSeparator:','"  style="width:170px;"></input>
	    				<span class="span_common_mustinput_style">*</span>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">有效磷(mg/Kg)：</td>
	    			<td class="table_common_td_txt_style">
	    				<input class="easyui-numberbox" type="text" name="olsenp" value="${soilMoni.olsenp }"  data-options="min:0,max:999999999.99,precision:2,groupSeparator:','"  style="width:170px;"></input>
	    				<span class="span_common_mustinput_style">*</span>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">速效钾(mg/Kg)：</td>
	    			<td class="table_common_td_txt_style"> 
	    				<input class="easyui-numberbox" type="text" name="olsenk" value="${soilMoni.olsenk }" data-options="min:0,max:999999999.99,precision:2,groupSeparator:','"  style="width:170px;"></input>
	    				<span class="span_common_mustinput_style">*</span>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">PH值：</td>
	    			<td class="table_common_td_txt_style">
	    				<input class="easyui-numberbox" type="text" name="ph" value="${soilMoni.ph }" data-options="min:0,max:999999999.99,precision:2,groupSeparator:','"  style="width:170px;"></input>
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
	if($("input[name='omvalue']").val()==""){
		$.messager.alert('警告','请填写有机质！','warning');
		return false;
	}else if (!isNumber($("input[name='omvalue']").val())){
		$.messager.alert('警告','有机质请输入数字！','warning');
		return false;
	}
	if($("input[name='alkelinen']").val()==""){
		$.messager.alert('警告','请填写碱解氮！','warning');
		return false;
	}else if (!isNumber($("input[name='omvalue']").val())){
		$.messager.alert('警告','碱解氮请输入数字！','warning');
		return false;
	}
	if($("input[name='olsenp']").val()==""){
		$.messager.alert('警告','请填写有效磷！','warning');
		return false;
	}else if (!isNumber($("input[name='omvalue']").val())){
		$.messager.alert('警告','效磷请输入数字！','warning');
		return false;
	}
	if($("input[name='olsenk']").val()==""){
		$.messager.alert('警告','请填写速效钾！','warning');
		return false;
	}else if (!isNumber($("input[name='omvalue']").val())){
		$.messager.alert('警告','速效钾请输入数字！','warning');
		return false;
	}
	if($("input[name='ph']").val()==""){
		$.messager.alert('警告','请填写PH值！','warning');
		return false;
	}else if (!isNumber($("input[name='omvalue']").val())){
		$.messager.alert('警告','PH值请输入数字！','warning');
		return false;
	}
	return true;
}

</script>
</body>
</html>