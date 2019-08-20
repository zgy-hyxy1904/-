<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>   
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>抽检记录-五常优质水稻溯源监管平台</title>
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
			<form id="sampleform" name="sampleform" method="post" action="${ctx}/sample/list?flag=2">
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_query_style">年度：</td>
						<td class="table_common_td_txt_query_style editableFalse">
							<simple:select id="year" name="year" entityName="yearcode" value="${sampleModel.year}" width="120" hasPleaseSelectOption="true" canEdit="false"/>
							<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
							<input type="hidden" id="page" name="page" value="${pageModel.page}">
							<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
						</td>
						<td class="table_common_td_label_query_style">企业名称：</td>
						<td class="table_common_td_txt_query_style">
							<simple:select id="companyCode" name="companyCode" value="${sampleModel.companyCode}" codeKey="02" entityName="company" width="200" hasPleaseSelectOption="${!sessionScope.isCompanyUser}" readOnly="${sessionScope.isCompanyUser}" canEdit="${!sessionScope.isCompanyUser}" />
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_query_style">批次号：</td>
						<td class="table_common_td_txt_query_style">
							<input name="batchNo" value="${sampleModel.batchNo}" class="easyui-textbox" style="width:120px;height:25px">
						</td>
						<td class="table_common_td_label_query_style">抽检状态：</td>
						<td class="table_common_td_txt_query_style">
							<simple:select id="qualityStatus" name="qualityStatus" codeKey="SIStatus" entityName="commonData" value="${sampleModel.qualityStatus}" width="200" hasPleaseSelectOption="true"/>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_query_style">送检日期：</td>
						<td colspan="2" class="table_common_td_txt_query_style editableFalse">
							<input id="beginDate" comboname="beginDate" textboxname="beginDate" class="easyui-datebox datebox-f combo-f textbox-f" value="${sampleModel.beginDate}" data-options="required:false,showSeconds:false" style="width: 100px; display: none;" editable="false">
							~
							<input id="endDate" comboname="endDate" textboxname="endDate" class="easyui-datebox datebox-f combo-f textbox-f" value="${sampleModel.endDate}" data-options="required:false,showSeconds:false" style="width: 100px; display: none;" editable="false">
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
		<table id="data" class="easyui-datagrid" striped="true" checkbox="true" singleSelect="true" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="year" width="60" align="center">年度</th>
					<th field="companyCode" width="200" align="center">企业名称</th>
					<th field="batchNo" width="140" align="center">批次</th>
					<th field="productName" width="200"  align="center">产品名称</th>
					<th field="unitWeight" width="80" align="center">产品净重</th>
					<th field="code" width="140" align="center">抽捡防伪码</th>
					<th field="samplePerson" width="80" align="center">样品抽检人</th>
					<th field="sampeDate" width="100" align="center">样品抽检日期</th>
					<th field="deliveryPerson" width="80" align="center">送检人</th>
					<th field="deliveryDate" width="80" align="center">送检日期</th>
					<th field="samplingStatus" width="80" align="center">抽检状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="sample" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${sample.Year}</td>
					<td height="120" align="center" nowrap><simple:showName entityName="company" value="${sample.CompanyCode}"></simple:showName></td>
					<td height="30" align="center" nowrap>${sample.BatchNo}</td>
					<td height="30" align="center" nowrap>${sample.ProductName}</td>
					<td height="30" align="center" nowrap>${sample.UnitWeight}</td>
					<td height="30" align="center" nowrap>${sample.SecurityCode}</td>
					<td height="30" align="center" nowrap>${sample.SamplePerson}</td>
					<td height="30" align="center" nowrap><fmt:formatDate value="${sample.SampleDate}" pattern="yyyy-MM-dd"/></td>
					<td height="30" align="center" nowrap>${sample.DeliveryPerson}</td>
					<td height="30" align="center" nowrap><fmt:formatDate value="${sample.DeliveryDate}" pattern="yyyy-MM-dd"/></td>
					<td height="30" align="center" nowrap><simple:showName entityName="commonData" codeKey="SIStatus" value="${sample.InspectStatus}"></simple:showName></td>
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
		height:winHeight -queryBlockHeight -30,
		pagination: true,
        rownumbers: true,
        fitColumns: false,//列自动宽度
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
	var beginDate = $('#beginDate').datebox('getValue');
	var endDate = $('#endDate').datebox('getValue');
	if(beginDate!= '' && endDate != '' && beginDate > endDate){
		$.messager.alert('警告','开始日期不能大于结束日期！','warning');
		return false;
	}
	showLoading();
	$('#sampleform').submit();
}
</script>
</body>
</html>