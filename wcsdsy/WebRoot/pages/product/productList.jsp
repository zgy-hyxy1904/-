<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>   
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品列表-五常优质水稻溯源监管平台</title>
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
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">	
		<fieldset id="queryBlock" class="fieldset_common_style">
			<form id="productForm" name="productForm" method="post" action="${ctx}/product/list?flag=2">
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_query_style">企业名称：</td>
						<td colspan="4">
							<simple:select id="companyCode" name="companyCode" value="${companyCode}" codeKey="02" entityName="company" width="200" hasPleaseSelectOption="${!sessionScope.isCompanyUser}" readOnly="${sessionScope.isCompanyUser}" canEdit="${!sessionScope.isCompanyUser}" />
							<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
							<input type="hidden" id="page" name="page" value="${pageModel.page}">
							<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
						</td>
						<td class="table_common_td_label_query_style">产品名称：</td>
						<td nowrap>
							<input name="productName" value="${productName}" class="easyui-textbox" style="width:200px;height:25px">
						</td>
						<td align="right" valign="bottom">
	                       	<a href="#" class="easyui-linkbutton" onclick="onSubmit()">
	                       	<span class="l-btn-left"><span class="l-btn-text icon-search l-btn-icon-left">查询</span></span></a>
	             		</td>
					</tr>
				</table>
			</form>
		</fieldset>
		<table id="data" class="easyui-datagrid" striped="true" singleSelect="true" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="companyCode" width="250"  align="center">企业名称</th>
					<th field="productCode" width="200" align="center">产品编码</th>
					<th field="productName" width="250"  align="center">产品名称</th>
					<th field="unit" width="200"  align="center">包装单位</th>
					<th field="weight" width="150" align="center">产品净重</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="product" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="120" align="center" nowrap><simple:showName entityName="company" value="${product.companyCode}"></simple:showName></td>
					<td height="30" align="center" nowrap>${product.productCode}</td>
					<td height="30" align="center" nowrap>${product.productName}</td>
					<td height="30" align="center" nowrap>${product.unit}</td>
					<td height="30" align="center" nowrap>${product.weight}</td>
				</tr>
				</c:forEach>		
			</tbody>
		</table>
	</div>
<script type="text/javascript">
	var winHeight = $(window).height();
	var queryBlockHeight = $("#queryBlock").height()

	$(document).ready(function(){
		$("#data").datagrid({
			height:winHeight -queryBlockHeight-45,
			pagination: true,
	        rownumbers: true,
	        fitColumns: false,
	        //fit: true,
	        pageList: [15,20,25],
	        pagePosition: "top"
	});
	
	var pagger = $('#data').datagrid('getPager');  
	$(pagger).pagination({
		total: $("#pageTotal").val(),
		pageNumber: parseInt($("#page").val()),
		showPageList: true,
		showRefresh: false,
	    onSelectPage: function(pageNumber, pageSize){
	    	$('#page').val(pageNumber);
	    	$('#pageSize').val(pageSize);
	    	onSubmit();
	    }
	});
});

function onSubmit(){
	showLoading();
	$('#productForm').submit();
}
</script>
</body>
</html>