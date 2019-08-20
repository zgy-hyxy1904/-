<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>根据Code显示名称的标签使用示例</title>
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
    <form id="form1">  
		<select id="cityCode" name="cityCode" class="easyui-combobox" data-options="required:true,width:150"> 
 				<option selected>-=请选择=-</option>
 				<c:forEach var="city" items="${citys}" varStatus="status">
 					<option value="${city.cityCode}">${city.cityName}</option>
 				</c:forEach>
		</select>市/地区 
		<span id="townSpan"><select id="townCode" name="townCode" class="easyui-combobox" data-options="width:150"></select>镇/乡</span>
		<span id="countrySpan"><select id="countryCode" name="countryCode" class="easyui-combobox" data-options="width:150"></select>村/屯 </span>
    </form>  
</body> 
</HTML>

<script>

$(document).ready(function(){
	/*
	$("#cityCode").combobox({
		valueField:"id",
		textField:"text",
		onChange : function(){
			var cityCode = $('#cityCode').combobox('getValue');
			Public.ajaxGet('${ctx}/areaDevision/getTownsByCityCode', {cityCode:cityCode}, function(e) {
				if (200 == e.status) {
					$("#townCode").combobox("loadData", JSON.parse(e.data));
				} else {
					parent.parent.Public.tips({
						type : 1,
						content : "失败！" + e.msg
					});
				}
			});
		}
	});
	*/
	$("#cityCode").combobox({
		valueField:"id",
		textField:"text",
		onChange : function(){
			var cityCode = $('#cityCode').combobox('getValue');
			alert(cityCode);
			Public.ajaxGet('/suyuan/areaDevision/getTownsByCityCode', {cityCode:cityCode}, function(e) {
				if (200 == e.status) {
					$("#townCode").combobox("loadData", JSON.parse(e.data));
				} else {
					parent.parent.Public.tips({
						type : 1,
						content : "失败！" + e.msg
					});
				}
			});
		}
	});
    $('#townCode').combobox({
        valueField:'id',
        textField:'text',
        onChange: function(){
			var cityCode = $('#cityCode').combobox('getValue');
			var townCode = $('#townCode').combobox('getValue');
			Public.ajaxGet('${ctx}/areaDevision/getCountrysByCityAndTownCode', {cityCode:cityCode,townCode:townCode}, function(e) {
				if (200 == e.status) {
					$("#countryCode").combobox("loadData", JSON.parse(e.data));
				} else {
					parent.parent.Public.tips({
						type : 1,
						content : "失败！" + e.msg
					});
				}
			});
        }
    });
});
</script>
