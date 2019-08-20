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
	<div region="center" border="false" style="padding:5px;">	
		<form id="addFrom" class="easyui-form" method="post" data-options="novalidate:true">
			<input name="id" value="${pmMoni.id}" type="hidden"readonly>
			<fieldset class="fieldset_common_style">
			<table class="table_common_style">
				<tr>
	    			<td class="table_common_td_label_style">监测日期：</td>
	    			<td class="table_common_td_txt_style">
	    				${pmMoni.monitorDate}
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">监测点位：</td>
	    			<td class="table_common_td_txt_style">
	    				${pointName}
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">空气质量指数：</td>
	    			<td class="table_common_td_txt_style">   ${pmMoni.aqi }
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">质量指数类别：</td>
	    			<td class="table_common_td_txt_style">
	    				${pmMoni.aqiName }</input>
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">细颗粒物：</td>
	    			<td class="table_common_td_txt_style">
	    				${pmMoni.pm2_5 } mg/m³
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">可吸入颗粒物：</td>
	    			<td class="table_common_td_txt_style">
	    				${pmMoni.pm10 } mg/m³
	    			</td>
    			</tr> 
    			<tr>
	    			<td class="table_common_td_label_style">一氧化碳：</td>
	    			<td class="table_common_td_txt_style">
	    				${pmMoni.co }mg/m³
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">二氧化氮：</td>
	    			<td class="table_common_td_txt_style">
	    				${pmMoni.no2 } mg/m³
	    			</td>
    			</tr>
				<tr>		
	    			<td class="table_common_td_label_style">臭氧；</td>
	    			<td class="table_common_td_txt_style">
	    				${pmMoni.o3 }mg/m³
	    			</td>
    			</tr>
    			<tr>
	    			<td class="table_common_td_label_style">二氧化硫：</td>
	    			<td class="table_common_td_txt_style">
	    				${pmMoni.so2 }mg/m³
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