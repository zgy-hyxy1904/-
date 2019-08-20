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
	<div region="center" border="false" style="padding: 5px;">
		<form id="addFrom" class="easyui-form" method="post" data-options="novalidate:true">
			<input name="id" value="${waterMoni.id}" type="hidden">
			<fieldset id="queryBlock" class="fieldset_common_style">
			<table class="table_common_style">
				<tr>
	    			<td class="table_common_td_label_style">监测日期：</td>
	    			<td class="table_common_td_txt_style">
	    				<input class="easyui-datebox" name="monitorDate"  value="${waterMoni.monitorDate}"
            data-options="required:true,showSeconds:false" style="width:170px" editable="false" readonly>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">断面名称：</td>
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
	    			<td class="table_common_td_label_style">PH值：</td>
	    			<td class="table_common_td_txt_style"><input class="easyui-textbox" type="text" name="ph" value="${waterMoni.ph }" style="width:170px;" readonly></input></td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">DO：</td>
	    			<td class="table_common_td_txt_style">
	    				<input class="easyui-textbox" type="text" name="doValue" value="${waterMoni.doValue }"  style="width:170px;" readonly>mg/L
	    				</input>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">COD Mn：</td>
	    			<td class="table_common_td_txt_style">
	    				<input class="easyui-textbox" type="text" name="codmn" value="${waterMoni.codmn }"  style="width:170px;" readonly>mg/L
	    				</input>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">BOD5：</td>
	    			<td class="table_common_td_txt_style">
	    				<input class="easyui-textbox" type="text" name="bod5" value="${waterMoni.bod5 }"  style="width:170px;" readonly>BOD5
	    				</input>
	    			</td>
    			</tr>

    			<tr>
	    			<td class="table_common_td_label_style">氨氮：</td>
	    			<td class="table_common_td_txt_style">
	    				<input class="easyui-textbox" type="text" name="nh3n" value="${waterMoni.nh3n }"  style="width:170px;" readonly>mg/L
	    				</input>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">总磷：</td>
	    			<td class="table_common_td_txt_style">
	    				<input class="easyui-textbox" type="text" name="tp" value="${waterMoni.tp }"  style="width:170px;" readonly>mg/L
	    				</input>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">COD Cr：</td>
	    			<td class="table_common_td_txt_style">
	    				<input class="easyui-textbox" type="text" name="codcr" value="${waterMoni.codcr }"  style="width:170px;" readonly>mg/L
	    				</input>
	    			</td>
    			</tr>
    			<tr height="80">
	    			<td colspan="2" align="center">
	    			<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:15px;" onclick="closeEdiDialog()" data-options="iconCls:'icon-cancel'">关闭</a>
	    			</td>
    			</tr>
			</table>
			</fieldset>
		</form>
	</div>
<script type="text/javascript">
function closeEdiDialog(){
	$('#addDialog').dialog('close');
}

</script>
</body>
</html>