<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>   
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企业管理-五常优质水稻溯源监管平台</title>
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
			<form id="companylistform" name="companylistform" method="post" action="${ctx}/company/list?flag=2">
			<table class="table_common_style">
				<tr>
					<td class="table_common_td_label_query_style">企业编码：</td>
					<td class="table_common_td_txt_query_style">
						<input class="easyui-textbox" type="text" name="companyCode" value="${companyCode}" style="width:200px;height:25px;"></input>
						<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
						<input type="hidden" id="page" name="page" value="${pageModel.page}">
						<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
					</td>
					<td class="table_common_td_label_query_style">企业名称：</td>
					<td class="table_common_td_txt_query_style">
						<input class="easyui-textbox" type="text" name="companyName" value="${companyName}" style="width:200px;height:25px;"></input>
					</td>
				</tr>
				<tr>
					<td class="table_common_td_label_query_style">企业类型：</td>
					<td class="table_common_td_txt_query_style">
						<simple:select id="companyType" name="companyType" codeKey="CompanyType" entityName="commonData" value="${companyType}" hasPleaseSelectOption="true" width="150" />
					</td>
					<td colspan="2" align="right" valign="bottom">
                       	<a href="#" class="easyui-linkbutton" onclick="onSubmit();">
                       		<span class="l-btn-left"><span class="l-btn-text icon-search l-btn-icon-left">查询</span></span>
                       	</a>
             		</td>
				</tr>
			</table>
			</form>
		</fieldset>
		<fieldset id="toolBlock" class="fieldset_common_style">
			<table>
				<tr>
					<td>
						<a href="#" class="easyui-linkbutton" onclick="detail();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-view l-btn-icon-left">查看</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="addCompany();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-add l-btn-icon-left">添加</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="editCompany();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-edit l-btn-icon-left">修改</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="deleteCompany();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-remove l-btn-icon-left">删除</span>
							</span>
						</a>
					</td>
				</tr>
			</table>	
		</fieldset>
		<table id="data" class="easyui-datagrid" striped="true" checkbox="true" singleSelect="false" singleSelect="true" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="id" width="140" checkbox="true"  align="center"></th>
					<th field="companyCode" width="100"  align="center">企业编码 </th>
					<th field="companyName" width="300" align="center">企业名称</th>
					<th field="address" width="300"  align="center">企业地址</th>
					<th field="LegalPerson" width="140"  align="center">企业法人</th>
					<th field="connectName" width="140"  align="center">联系人</th>
					<th field="connectPhone" width="140"  align="center">联系电话</th>
					<th field="companyType" width="140"  align="center">企业类型</th>
					<th field="isBlack" width="140"  align="center">是否加入黑名单</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="company" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${company.ID}</td>
					<td height="30" align="center" nowrap>${company.CompanyCode}</td>
					<td height="30" align="center" nowrap>${company.CompanyName}</td>
					<td height="30" align="center" nowrap>${company.Address}</td>
					<td height="30" align="center" nowrap>${company.LegalPerson}</td>
					<td height="30" align="center" nowrap>${company.ConnectName}</td>
					<td height="30" align="center" nowrap><simple:showName entityName="commonData" codeKey="BlackListType" value="${company.Black}"></simple:showName></td>
					<td height="30" align="center" nowrap><simple:showName entityName="commonData" codeKey="CompanyType" value="${company.CompanyType}"></simple:showName></td>
					<td height="30" align="center" nowrap>否</td>
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
	    var toolBlock = $("#toolBlock").height();

		$("#data").datagrid({
			height:winHeight -queryBlockHeight -toolBlock-52,
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
	
function addCompany(){
	$('#addDialog').dialog({
	    title: '添加企业',
	    width: 480,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: '${ctx}/company/addCompanyInit',
	    modal: true
	});
}

function editCompany(){
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length == 0){
		$.messager.alert('警告','请选择一家企业。','warning');
		return false;
	}
	if(length > 1){
		$.messager.alert('警告','只能选择一家企业。','warning');
		return false;
	}
	var id = rows[0].id;
	$('#addDialog').dialog({
	    title: '修改企业信息',
	    width: 480,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: '${ctx}/company/editCompanyInit?id='+id,
	    modal: true
	});
}

function detail(){
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length == 0){
		$.messager.alert('警告','请选择一家企业。','warning');
		return false;
	}
	if(length > 1){
		$.messager.alert('警告','只能选择一家企业。','warning');
		return false;
	}
	var id = rows[0].id;
	$('#addDialog').dialog({
	    title: '查看企业信息',
	    width: 480,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: '${ctx}/company/detail?id='+id,
	    modal: true
	});
}

function deleteCompany(){
	var ids = [];
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	for(var i=0; i<rows.length; i++) ids.push(rows[i].id);
	if(ids.length == 0){
		$.messager.alert('警告','至少选择一家企业。','warning');
		return false;
	}

	$.messager.confirm("确认", "您确认删除选定的记录吗？", function (deleteAction) {
		if (deleteAction) {
			Public.ajaxGet('delete', {ids : ids}, function(e) {
				if (200 == e.status) {
					$.messager.alert('提示','操作成功。','info');
					onSubmit();
				} else
					$.messager.alert('错误',e.msg,'error');
			});
		}
	});
}

function onSubmit(){
	$('#companylistform').submit();
}
</script>
</body>
</html>