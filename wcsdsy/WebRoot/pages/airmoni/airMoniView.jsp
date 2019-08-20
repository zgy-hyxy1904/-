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
<script type="text/javascript" src="${ctx}/js/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:0 4px;">	
		<form id="addFrom" class="easyui-form" method="post" data-options="novalidate:true">
			<input name="id" value="${airMoni.id}" type="hidden">
		
			<table cellpadding="8" style="text-align:center;" align="center" border="0">
				<tr>
	    			<td width="30%" align="right">监测日期：</td>
	    			<td  align="left">
	    				<input class="easyui-datebox" name="monitorDate"  value="${airMoni.monitorDate}"
            data-options="required:true,showSeconds:false" style="width:170px" editable="false" readonly>
	    			</td>
    			</tr>
    			<tr>
	    			<td width="30%" align="right">监测点位：</td>
	    			<td align="left">
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
	    			<td width="30%" align="right">TSP：</td>
	    			<td align="left" nowrap><input class="easyui-textbox" type="text" id="tsp" name="tsp" value="${airMoni.tsp }" style="width:170px;" readonly></input>mg/m³
	    			</td>
    			</tr>
    			<tr>
	    			<td width="30%" align="right">SO2：</td>
	    			<td align="left" nowrap>
	    				<input class="easyui-textbox" type="text" name="so2" id="so2" value="${airMoni.so2 }"  style="width:170px;" readonly></input>mg/m³
	    			</td>
    			</tr>
    			<tr>
	    			<td width="30%" align="right">NO2：</td>
	    			<td align="left" nowrap>
	    				<input class="easyui-textbox" type="text" name="no2" id="no2" value="${airMoni.no2 }"  style="width:170px;" readonly></input>mg/m³
	    			</td>
    			</tr>
    			<tr height="80">
	    			<td colspan="2" align="center">
	    			<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:15px;" onclick="closeEdiDialog()" data-options="iconCls:'icon-cancel'">关闭</a>
	    			</td>
    			</tr>
			</table>
		</form>
	</div>
<script type="text/javascript">

function closeEdiDialog(){
	$('#addDialog').dialog('close');
}

</script>
</body>
</html>