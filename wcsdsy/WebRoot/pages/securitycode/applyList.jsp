<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>   
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>防伪码管理-五常优质水稻溯源监管平台</title>
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
		<fieldset id="queryBlock" class="fieldset_common_style">
			<form id="applyform" name=""applyform"" method="post" action="${ctx}/securityCode/applyList?flag=2">
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_query_style">年度：</td>
						<td>
							<simple:select id="year" name="year" entityName="yearcode" value="${year}" width="170" canEdit="false"/>
							<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
							<input type="hidden" id="page" name="page" value="${pageModel.page}">
							<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
						</td>
						<td class="table_common_td_label_query_style">企业名称：</td>
						<td nowrap>
							<simple:select id="companyCode" name="companyCode" value="${companyCode}" codeKey="02" entityName="company" width="200" hasPleaseSelectOption="${!sessionScope.isCompanyUser}" readOnly="${sessionScope.isCompanyUser}" canEdit="${!sessionScope.isCompanyUser}"/>
						</td>
						<td align="right" valign="bottom">
	                       	<a href="#" class="easyui-linkbutton" onclick="return form_check();">
	                       		<span class="l-btn-left"><span class="l-btn-text icon-search l-btn-icon-left">查询</span></span>
	                       	</a>
	             		</td>
					</tr>
				</table>
			</form>
		</fieldset>
		<table id="data" class="easyui-datagrid" striped="true" checkbox="true" singleSelect="false" singleSelect="true" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="year" width="150"  align="center">年度</th>
					<th field="companyName" width="500"  align="center">企业名称</th>
					<th field="applyDate" width="150"  align="center">申请日期</th>
					<th field="qty" width="150" align="center">申请数量</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="securitycodeDetail" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${securitycodeDetail.year}</td>
					<td height="30" align="center" nowrap>${securitycodeDetail.companyName}</td>
				    <td height="30" align="center" nowrap><fmt:formatDate value="${securitycodeDetail.applyDate}" pattern="yyyy-MM-dd"/></td>
					<td height="30" align="center" nowrap>${securitycodeDetail.qty}</td>
				</tr>
				</c:forEach>		
			</tbody>
		</table>
	</div>
	<div id="addDialog"></div>
<script type="text/javascript">
$(document).ready(function(){

  var winHeight = $(window).height();
  var queryBlockHeight = $("#queryBlock").height();
	$("#data").datagrid({ 
	   height:winHeight -queryBlockHeight -35,
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
		showRefresh:false,
	    onSelectPage: function(pageNumber, pageSize){
	    	$('#page').val(pageNumber);
	    	$('#pageSize').val(pageSize);
	    	$('#applyform').submit();
	    }
	});
});

function form_check()
{
	var companyCode = $('#year').combobox('getValue');
	if(companyCode == ''){
		$.messager.alert('警告','请选择年度。','warning');
		return false;
	}
	
	var companyCode = $('#companyCode').combobox('getValue');
	if(companyCode == ''){
		$.messager.alert('警告','请选择企业。','warning');
		return false;
	}
	showLoading();
	$('#applyform').submit();
}
</script>
</body>
</html>