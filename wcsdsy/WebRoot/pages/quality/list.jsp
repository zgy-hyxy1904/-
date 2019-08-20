<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>   
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
		<fieldset id="queryBlock" class="fieldset_common_style">
			<form id="qualityform" name="qualityform" method="post" action="${ctx}/quality/list?flag=2">
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_query_style">年度：</td>
						<td class="table_common_td_txt_query_style editableFalse">
							<simple:select id="year" name="year" entityName="yearcode" value="${qualityModel.year}" width="120" hasPleaseSelectOption="true" canEdit="false"/>
							<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
							<input type="hidden" id="page" name="page" value="${pageModel.page}">
							<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
						</td>
						<td class="table_common_td_label_query_style">企业名称：</td>
						<td class="table_common_td_txt_query_style">
							<simple:select id="companyCode" name="companyCode" value="${qualityModel.companyCode}" codeKey="02" entityName="company" width="200" hasPleaseSelectOption="${!sessionScope.isCompanyUser}" readOnly="${sessionScope.isCompanyUser}" canEdit="${!sessionScope.isCompanyUser}" />
						</td>
						<td class="table_common_td_label_query_style">质检状态：</td>
						<td class="table_common_td_txt_query_style">
							<simple:select id="qualityStatus" name="qualityStatus" codeKey="QCStatus" entityName="commonData" value="${qualityModel.qualityStatus}" width="120" hasPleaseSelectOption="true"/>
						</td>
					</tr>
					<tr>
						
						<td class="table_common_td_label_query_style">质检日期：</td>
						<td colspan="4" class="table_common_td_txt_query_style editableFalse">
							<input id="beginDate" comboname="beginDate" textboxname="beginDate" class="easyui-datebox datebox-f combo-f textbox-f" value="${qualityModel.beginDate}" data-options="required:false,showSeconds:false" style="width: 100px; display: none;" editable="false">
							~
							<input id="endDate" comboname="endDate" textboxname="endDate" class="easyui-datebox datebox-f combo-f textbox-f" value="${qualityModel.endDate}" data-options="required:false,showSeconds:false" style="width: 100px; display: none;" editable="false">
						</td>
						<td align="right" valign="bottom">
	                       	<a href="#" class="easyui-linkbutton" onclick="onSubmit()">
	                       		<span class="l-btn-left"><span class="l-btn-text icon-search l-btn-icon-left">查询</span></span>
	                       	</a>
	             		</td>
					</tr>
				</table>
			</form>
		</fieldset>
		<table id="data" class="easyui-datagrid" striped="true" checkbox="true" singleSelect="false" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="year" width="60" align="center">年度</th>
					<th field="companyName" width="200" align="center">企业名称</th>
					<th field="checkCode" width="180" align="center">质检编号</th>
					<th field="deliveryPerson" width="80"  align="center">企业送检人</th>
					<th field="deliveryDate" width="100"  align="center">送检日期</th>
					<th field="handoverPerson" width="80"  align="center">质检中心交接人</th>
					<th field="handoverTime" width="150"  align="center">质检中心交接时间</th>
					<th field="inspectPerson" width="80" align="center">质检员</th>
					<th field="inspectDate" width="150" align="center">质检时间</th>
					<th field="inspectStatus" width=80 align="center">质检结论</th>
					<th field="path" width="100" align="center">质检报告</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="quality" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${quality.year}</td>
					<td height="30" align="center" nowrap><simple:showName entityName="company" value="${quality.companyCode}"></simple:showName></td>
					<td height="30" align="center" nowrap>${quality.checkCode}</td>
					<td height="30" align="center" nowrap>${quality.deliveryPerson}</td>
					<td height="30" align="center" nowrap><fmt:formatDate value="${quality.deliveryDate}" pattern="yyyy-MM-dd"/></td>
					<td height="30" align="center" nowrap>${quality.handoverPerson}</td>
					<td height="30" align="center" nowrap><fmt:formatDate value="${quality.handoverTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td height="30" align="center" nowrap>${quality.inspectPerson}</td>
					<td height="30" align="center" nowrap><fmt:formatDate value="${quality.inspectDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td height="30" align="center" nowrap><simple:showName entityName="commonData" codeKey="QCStatus" value="${quality.inspectStatus}"></simple:showName></td>
					<td height="30" align="center" nowrap><a href="javascript:void(0)" onclick="showPic('${quality.id}')">查看</a></td>
				</tr>
				</c:forEach>		
			</tbody>
		</table>
		<div id="addDialog"></div>
	</div>
<script type="text/javascript">
$(document).ready(function(){
    var winHeight = $(window).height();
    var queryBlockHeight = $("#queryBlock").height();

	$("#data").datagrid({ 
		height:winHeight -queryBlockHeight -32,
		pagination: true,
        rownumbers: true,
        fitColumns: false,
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

//预览质检报告
function showPic(qualityId){
	$('#addDialog').dialog({
	    title: '查看质检报告',
	    width: 600,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: '${ctx}/quality/report?id='+qualityId,
	    modal: true
	});
}

function onSubmit(){
	var beginDate = $('#beginDate').datebox('getValue');
	var endDate = $('#endDate').datebox('getValue');
	if(beginDate!= '' && endDate != '' && beginDate > endDate){
		$.messager.alert('警告','开始日期不能大于结束日期！','warning');
		return false;
	}
	showLoading();
	$('#qualityform').submit();
}
</script>
</body>
</html>