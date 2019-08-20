<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
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

</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">	
		<fieldset id="queryBlock" class="fieldset_common_style">
			<form id="inputForm" name="inputRegForm" method="get" action="../blackList/search">
				<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
				<input type="hidden" id="page" name="page" value="${pageModel.page}">
				<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
				
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_query_style">企业：</td>
						<td class="table_common_td_txt_query_style">
							<%@ include file="../../companyComm.jsp" %>
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
		<table id="data" class="easyui-datagrid" striped="true" singleSelect="false" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="companyName" width="140" align="center">企业名称</th>
					<th field="inputName" width="120" align="center">处理日期</th>
					<th field="inpuDealer" width="120" align="center">拉黑时长</th>
					<th field="purchaseQuantity" width="80" align="center">拉黑截至日期</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="black" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>
						<simple:showName entityName="company" value="${black.companyCode }"></simple:showName>
					</td>
					<td height="30" align="center" nowrap>
						<fmt:formatDate value="${black.settleDate}" pattern="yyyy-MM-dd"/>
					</td>
					<td height="30" align="center" nowrap>
						<simple:showName entityName="commonData" codeKey="BlackListDuration" value="${black.blackListTimeLimit }"></simple:showName>
					</td>
					<td height="30" align="center" nowrap>
						<fmt:formatDate value="${black.blackListEndDate}" pattern="yyyy-MM-dd"/>
					</td>
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
        fitColumns: true,
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
	    	showLoading();
	    	$('#inputForm').submit();
	    }
	});
});

function deleteRecord(id){
	$.messager.confirm("确认", "您确定要删除选中的记录吗？", function (deleteAction) {
	    if (deleteAction) {
	    	var ids = [];
	    	var rows = $('#data').datagrid('getSelections');
	    	var length = rows.length;
	    	for(var i=0; i<rows.length; i++) ids.push(rows[i].id);
	    	Public.ajaxGet('delete', {ids : ids}, function(e) {
	    		if (200 == e.status) {
	    			$.messager.alert('提示','删除成功。','info');
	    			form_check();
	    		} else
	    			$.messager.alert('错误','删除失败！'+ e.msg,'error');
	    	});
	    }
	});

}

function edit(id){
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length == 0){
		$.messager.alert('警告','请选择一条记录。','warning');
		return false;
	}
	if(length > 1){
		$.messager.alert('警告','只能选择一条记录。','warning');
		return false;
	}
	var id = rows[0].id;
	
	/*$('#addDialog').dialog({
	    title: '编辑投入品备案',
	    width: 500,
	    height: 400,
	    closed: false,
	    cache: false,
	    href: 'editInput?id='+ id,
	    modal: true
	});
	*/
	document.location.href = "${ctx}/inputReg/editInput?id=" + id;
}
function add(id){
	/*
	$('#addDialog').dialog({
	    title: '添加投入品备案',
	    width: 500,
	    height: 400,
	    closed: false,
	    cache: false,
	    href: 'editInput',
	    modal: true
	});
	*/
	document.location.href = "${ctx}/inputReg/editInput";
}

function closeEdiDialog(){
	$('#provEvalEditDlg').dialog('close');
}
function view(){
	
}
function form_check(){
	showLoading();
	$('#inputForm').submit();
}
</script>
</body>
</html>