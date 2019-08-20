<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
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
			<form id="inputRegForm" name="inputRegForm" method="get" action="../geneLandReg/list">
				<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
				<input type="hidden" id="page" name="page" value="${pageModel.page}">
				<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_query_style">年度：</td>
						<td class="table_common_td_txt_query_style">
							<simple:select id="year" name="year" value="${year}" entityName="YearCode" width="211"/>
						</td>
						<td class="table_common_td_label_query_style">企业：</td>
						<td class="table_common_td_txt_query_style">
							<%@ include file="../../companyComm.jsp" %>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_query_style">申请日期：</td>
						<td class="table_common_td_txt_query_style editableFalse">
							<input class="easyui-datebox" id="beginDate" name="beginDate" value="${beginDate }"
	            				data-options="required:false,showSeconds:false" style="width:100px" editable="false"/>
							~
							<input class="easyui-datebox" id="endDate" name="endDate" value="${endDate }"
	            				data-options="required:false,showSeconds:false" style="width:100px" editable="false"/>
						</td>
						<td class="table_common_td_label_query_style">状态：</td>
						</td>
						<td class="table_common_td_txt_query_style">
							<simple:select id='status' name="status" value="${status}" codeKey="GeneralRegistStatus" entityName="commonData" hasPleaseSelectOption="true" width="187"/><BR>
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
		<fieldset id="toolBlock" class="fieldset_common_style">
			<table>
			<tr>
				<td>
					<a href="#" class="easyui-linkbutton" onclick="return view();">
						<span class="l-btn-left">
							<span class="l-btn-text icon-view l-btn-icon-left">查看</span>
						</span>
					</a>
					<a href="#" class="easyui-linkbutton" onclick="return add();">
						<span class="l-btn-left">
							<span class="l-btn-text icon-add l-btn-icon-left">添加</span>
						</span>
					</a>
					<a href="#" class="easyui-linkbutton" onclick="return edit();">
						<span class="l-btn-left">
							<span class="l-btn-text icon-edit l-btn-icon-left">修改</span>
						</span>
					</a>
					<a href="#" class="easyui-linkbutton" onclick="return deleteRecord();">
						<span class="l-btn-left">
							<span class="l-btn-text icon-remove l-btn-icon-left">删除</span>
						</span>
					</a>
				</td>
			</tr>
			</table>
		</fieldset>	
		<table id="data" class="easyui-datagrid"  pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="id" width="140" align="center" checkbox="true"></th>
					<th field="applyBatchNo" width="130" align="center">流水号</th>
					<th field="year" width="75" align="center">年度</th>
					<th field="companyName" width="218" align="center">企业</th>
					<th field="createDate" width="100" align="center">申请日期</th>
					<th field="createUserId" width="100" align="center">申请人</th>
					<th field="InpuDealer" width="120" align="center">本次备案总面积<br/>(亩)</th>
					<th field="PurchaseQuantity" width="80" align="center">状态</th>
					<th field="PurchasingAgent" width="100" align="center">原因</th>
					<th field="PurchaseDate" width="80" align="center">审核人</th>
					<th field="view" width="130" align="center">审核时间</th>
					<th field="spstatus" width="0" align="center" hidden='true'></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="geneLandReg" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${geneLandReg.id}</td>
					<td height="30" align="center" nowrap>
						<a href="javascript:void(0)" onclick="detail('${geneLandReg.applyBatchNo}');">${geneLandReg.applyBatchNo}</a>
						<%--${geneLandReg.applyBatchNo} --%>
					</td>
					<td height="30" align="center" nowrap>${geneLandReg.year}</td>
					<td height="30" align="center" nowrap>${geneLandReg.companyName}</td>
					<td height="30" align="center" nowrap>
						<fmt:formatDate value="${geneLandReg.createDate}" pattern="yyyy-MM-dd"/>
					</td>
					<td height="30" align="center" nowrap>${geneLandReg.createUserId}</td>
					<td height="30" align="center" nowrap>
						<fmt:formatNumber type="number" value="${geneLandReg.mjsum }" pattern="0.00" maxFractionDigits="2"/>
					</td>
					<td height="30" align="center" nowrap><simple:showName entityName="commonData" codeKey="GeneralRegistStatus" value="${geneLandReg.status}"></simple:showName></td>
					<td height="30" align="center" nowrap>${geneLandReg.reason}</td>
					<td height="30" align="center" nowrap>${geneLandReg.auditor}</td>
					<td height="30" align="center" nowrap><fmt:formatDate value="${geneLandReg.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td height="30" align="center" nowrap>${geneLandReg.status}</td>
				</tr>
				</c:forEach>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>合计：</td>
					<td>${pageModel.data}</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>		
			</tbody>
		</table>
	</div>
	
	<div id="flowDialog"></div>
	
<script type="text/javascript">
$(document).ready(function(){
	 var winHeight = $(window).height();
     var queryBlockHeight = $("#queryBlock").height();
     var toolBlock = $("#toolBlock").height();
	$("#data").datagrid({
		onLoadSuccess:function(data){
			$(".datagrid-cell-check")[data.rows.length-1].innerHTML = "";
	    },
	    onSelectAll:function(rows){
	    	 $('#data').datagrid('unselectRow',rows.length-1);
	    },
	    onSelect:function(index,row){
	    	var rows  = $('#data').datagrid("getRows");
	    	if(index == rows.length-1){
	    		$('#data').datagrid('unselectRow', index);
	    	}
	    },
		height:winHeight -queryBlockHeight -toolBlock - 50,
		pagination: true,
        rownumbers: true,
        fitColumns: false,
        checkOnSelect:false,
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
	    	$('#inputRegForm').submit();
	    }
	});
});


function closeEdiDialog(){
	$('#userEditDlg').dialog('close');
}

function view(){
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length == 0 ){
		$.messager.alert('警告','请选择一条记录！','warning');
		return false;
	}
	if(length > 1){
		$.messager.alert('警告','只能选择一条记录！','warning');
		return false;
	}
		
	var id = rows[0].id;
	var year = $('#year').combobox('getValue');
	var companyCode = $('#companyCode').combobox('getValue');
	var status = $('#status').combobox('getValue');
	var beginDate = $('#beginDate').val();
	var endDate = $('#endDate').val();
	
	var url = "${ctx}/geneLandReg/view?id=" + id;
	url += "&year=" + year;
	url += "&companyCode=" + companyCode;
	url += "&status=" + status;
	url += "&beginDate=" + beginDate;
	url += "&endDate=" + endDate;
	 
	document.location.href = url;
}

function deleteRecord(){
	var ids = [];
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length < 1){
		$.messager.alert('警告','请至少选择一条记录！','warning');
		return false; 
	}
	for(var i=0; i<rows.length; i++) {
		if(rows[i].spstatus=='02'){
			$.messager.alert('警告','您所选择的记录中包含已审核通过的备案土地,请重新选择！','warning');
			return false;
		}
	}
	
  	$.messager.confirm("确认", "您确认删除选定的记录吗？", function (deleteAction) {
	    if (!deleteAction) {
	    	return false;
	    }
	    for(var i=0; i<rows.length; i++) 
		ids.push(rows[i].id);
	    showLoading();
		Public.ajaxGet('delete', {ids : ids}, function(e) {
			hideLoading();
			if (200 == e.status) {
				form_check();
			} else
				$.messager.alert('错误','删除失败！' + e.msg,'error');
		});
	});
  	
}
function add(){
	var url = "${ctx}/geneLandReg/editInput?retFlag=1";
	url += getQueryCond();
	
	document.location.href = url;
}
function getQueryCond(){
	var year = $('#year').combobox('getValue');
	var companyCode = $('#companyCode').combobox('getValue');
	var status = $('#status').combobox('getValue');
	var beginDate = $('#beginDate').val();
	var endDate = $('#endDate').val();
	
	var str = "";
	str += "&year=" + year;
	str += "&companyCode=" + companyCode;
	str += "&status=" + status;
	str += "&beginDate=" + beginDate;
	str += "&endDate=" + endDate;
	
	return str;
}

function edit(){
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length == 0 ){
		$.messager.alert('警告','请选择一条记录！','warning');
		return false;
	}
	if(length > 1){
		$.messager.alert('警告','只能选择一条记录！','warning');
		return false;
	}
	if(rows[0].spstatus=='02'){
		$.messager.alert('警告','已审核通过的备案土地不可再做此操作！','warning');
		return false;
	}
	var id = rows[0].id;
	
	var url = "${ctx}/geneLandReg/editInput?retFlag=1&id=" + id;
	url += getQueryCond();
	
	document.location.href = url;
}

function form_check(){
	var beginDate =$.trim($("#beginDate").datebox('getValue'));
	var endDate =$.trim($("#endDate").datebox('getValue'));
	if(!dateCompare(beginDate,endDate)){
		$.messager.alert('警告','申请日期开始日不能大于结束日！','warning');
		return false;
	}
	showLoading();
	$('#inputRegForm').submit();
}

/**
* 点击流水号显示操作日志
*/
function detail( batchNo ){
	$('#flowDialog').dialog({
	    title: '操作记录',
	    width: 600,
	    height: 400,
	    closed: false,
	    cache: false,
	    href: '${ctx}/landLog/list' + "?landLogType=GENE&batchNo=" + batchNo,
	    modal: true
	});
}
</script>
</body>
</html>