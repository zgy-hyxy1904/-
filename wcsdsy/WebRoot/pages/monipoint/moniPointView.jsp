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
			<input name="id" value="${moniPoint.id}" type="hidden"readonly>
			<fieldset class="fieldset_common_style">
				<table class="table_common_style">
					<tr>
		    			<td class="table_common_td_label_style">监测点编号：</td>
		    			<td class="table_common_td_txt_style">
		    				${moniPoint.monitorPointCode}
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">监测点名称：</td>
		    			<td class="table_common_td_txt_style">
		    				${moniPoint.monitorPointName}
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">监测点类型：</td>
		    			<td class="table_common_td_txt_style">
		    				<simple:showName entityName="commonData" codeKey="MonitorPointType" value="${moniPoint.monitorPointType}"></simple:showName>
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">监测点地址：</td>
		    			<td class="table_common_td_txt_style">
		    				${moniPoint.monitorPointAddress }
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">监测点描述：</td>
		    			<td class="table_common_td_txt_style">
		    				${moniPoint.sectionDescription}
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">经度：</td>
		    			<td class="table_common_td_txt_style">
		    				${moniPoint.longitude}
		    			</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">纬度：</td>
		    			<td class="table_common_td_txt_style">
		    				${moniPoint.latitude}
		    			</td>
	    			</tr>
	    			<tr height="30">
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