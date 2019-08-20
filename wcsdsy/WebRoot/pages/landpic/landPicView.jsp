<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<title>查看土地图片-五常优质水稻溯源监管平台</title>
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
			<table class="n_input" style="width:100%;">
				<tr>
					<td width="50%" valign="top">
						<fieldset style="border: 1px solid #99BBE8; height:300px;">
						<input type="hidden" id="cc" value="${landPicture.townCode}" />
						<table>
							<tr height="70">
							<td width="20%" align="right">请选择市：</td>
				    			<td width="30%">
				    				<select id="cityCode" name="cityCode" class="easyui-combobox" editable="false" style="width:180px;height:25px" data-options='required:true' readonly>
					    				<option value="">请选择市</option>
					    				<c:forEach var="city" items="${cities}" varStatus="status">
					    					<option value="${city.cityCode}"  <c:if test="${city.cityCode==landPicture.cityCode}">selected</c:if> >${city.cityName}</option>
					    				</c:forEach>
					    			</select>
								</td>
			    			</tr>
			    			<tr height="70">
				    			<td width="60" align="right">请选择乡镇：</td>
				    			<td width="200">
				    				<select id="townCode" name="townCode" class="easyui-combobox" editable="false" style="width:180px;height:25px" data-options='required:true' readonly>
					    				<option value="">请选择乡镇</option>
					    				<c:forEach var="town" items="${towns}" varStatus="status">
					    					<option value="${town.townCode}"  <c:if test="${town.townCode==landPicture.townCode}">selected="true"</c:if> >${town.townName}</option>
					    				</c:forEach>
					    			</select>
								</td>
			    			</tr>
			    			<tr height="70">
				    			<td width="60" align="right">图片描述：</td>
				    			<td width="200">
				    				<input class="easyui-textbox" id="fileInfo" value="${landPicture.picInfo}" style="width:180px; height:25px;" readonly>
								</td>
			    			</tr>
			    			<tr height="80">
				    			<td colspan="2" align="center">
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
							<td><img src="${ctx}/upload/${landPicture.picUrl}" style="width:360px;height:300px;" border="0"/></td>
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
		textField:"text"
	});
	$('#townCode').combobox({
        valueField:'id',
        textField:'text'
    });
});

function closeDialog(){
	$('#addDialog').dialog('close');
}

</script>
</body>
</html>