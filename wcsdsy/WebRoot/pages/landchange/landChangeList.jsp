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
	<div region="center" border="false" style="padding:0 10px;">	
		
			<form id="inputForm" name="inputForm" method="get" action="../landChange/listSearch">
			<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
			<input type="hidden" id="page" name="page" value="${pageModel.page}">
			<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
		<fieldset id="queryBlock" class="fieldset_common_style">	
				<table style="width: 100%" align="center">
					<tr>
						<td width="100" height="20" align="right" nowrap >年度：</td> 
						<td nowrap>
							<simple:select id="year" name="year" value="${year}" entityName="YearCode" width="211"/>
						</td>
						<td width="100" height="20" align="right" nowrap >企业：</td>
						<td nowrap>
							<%@ include file="../../companyComm.jsp" %>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_query_style" width="100" height="20" align="right">申请日期：</td>
						<td class="table_common_td_txt_query_style editableFalse">
							<input class="easyui-datebox" id="beginDate" name="beginDate" value="${beginDate }"
	            				data-options="required:false,showSeconds:false" style="width:100px" editable="false"/>
							~
							<input class="easyui-datebox" id="endDate" name="endDate" value="${endDate }"
	            				data-options="required:false,showSeconds:false" style="width:100px" editable="false"/>
						</td>
						<td width="100" height="20" align="right" nowrap >状态：</td>
						<td nowrap>
							<simple:select id='status' name="status" value="${status}" codeKey="SpecialRegistStatus" entityName="commonData" hasPleaseSelectOption="true" width="187"/>
						</td>
						<td align="right" valign="bottom">
	                       	<a href="#" class="easyui-linkbutton" onclick="return form_check();">
	                       		<span class="l-btn-left"><span class="l-btn-text icon-search l-btn-icon-left">查询</span></span>
	                       	</a>
	             		</td>
					</tr>
				</table>
			</fieldset>	
			<fieldset id="toolBlock" class="fieldset_common_style">
			<table class="n_input">	
				<tr>
					<td colspan="10">
					<a href="#" class="easyui-linkbutton" onclick="return view();">
						<span class="l-btn-left">
							<span class="l-btn-text icon-view l-btn-icon-left">查看</span>
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
					<a href="#" class="easyui-linkbutton" onclick="return submitAudit();">
						<span class="l-btn-left">
							<span class="l-btn-text icon-ok l-btn-icon-left">提交申请</span>
						</span>
					</a>
					</td>
				</tr>
			</table>
			</fieldset>		
			</form>
		
		<table id="data" class="easyui-datagrid" striped="true" singleSelect="false" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="id" width="140" align="center" checkbox="true"></th>
					<th field="applyBatchNo" width="100" align="center">申请批次号</th>
					<th field="year" width="60" align="center">年度</th>
					<th field="CompanyName" width="150" align="center">企业</th>
					<th field="date1" width="80" align="center">申请日期</th>
					<th field="applicant" width="80" align="center">申请人</th>
					<th field="applicantTel" width="150" align="center">联系方式</th>
					<th field="contractorName" width="80" align="center">承包方</th>
					<th field="idType" width="60" align="center">证件类型</th>
					<th field="contractorID" width="150" align="center">证件号码</th>
					<th field="changeReason" width="200" align="center">变更情况说明</th>
					<th field="status" width="80" align="center">状态</th>
					<th field="PurchasingAgent" width="120" align="center">驳回原因</th>
					<th field="PurchaseDate" width="80" align="center">审核人</th>
					<th field="view" width="160" align="center">审核时间</th>
					<th field="spstatus" width="0" align="center" hidden='true'></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="landRegChange" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${landRegChange.id}</td>
					<td height="30" align="center" nowrap>${landRegChange.applyBatchNo}</td>
					<td height="30" align="center" nowrap>${landRegChange.year}</td>
					<td height="30" align="center" nowrap>${landRegChange.companyName}</td>
					<td height="30" align="center" nowrap>
						<fmt:formatDate value="${landRegChange.applicantDate}" pattern="yyyy-MM-dd"/>
					</td>
					<td height="30" align="center" nowrap>${landRegChange.applicant}</td>
					<td height="30" align="center" nowrap>${landRegChange.applicantTel}</td>
					<td height="30" align="center" nowrap>${landRegChange.contractorName}</td>
					<td height="30" align="center" nowrap>
						<simple:showName entityName="commonData" codeKey="idType" value="${landRegChange.idType}"></simple:showName>
			
					<td height="30" align="center" nowrap>${landRegChange.contractorID}</td>
					<td height="30" align="center" nowrap>${landRegChange.changeReason}</td>
					<td height="30" align="center" nowrap>
						<simple:showName entityName="commonData" codeKey="SpecialRegistStatus" value="${landRegChange.status}"></simple:showName>
					</td>
					<td height="30" align="center" nowrap>${landRegChange.rejectReason}</td>
					<td height="30" align="center" nowrap>${landRegChange.auditor}</td>
					<td height="30" align="center" nowrap>
						<fmt:formatDate value="${landRegChange.auditDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td height="30" align="center" nowrap>${landRegChange.status}</td>
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
	var toolBlockHeight = $("#toolBlock").height();
	$("#data").datagrid({ 
		height:winHeight -queryBlockHeight -toolBlockHeight -45,
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
	    	showLoading();
	    	$('#inputForm').submit();
	    }
	});
});

function deleteRecord(){
	 
	var ids = [];
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length < 1){
		$.messager.alert('警告','请至少选择一条记录。','warning');
		return false;
	}
	for(var i=0; i<rows.length; i++) {
		if(rows[i].spstatus=='03'){
			$.messager.alert('警告','您所选择的记录中包含已审核通过的备案变更申请,请重新选择！','warning');
			return false;
		}
		if(rows[i].spstatus=='02'){
			$.messager.alert('警告','您所选择的记录中包含待审核的备案变更申请,请重新选择！','warning');
			return false;
		}
		ids.push(rows[i].id);
	}
	$.messager.confirm("确认", "您确定要删除选中的记录吗？", function (deleteAction) {
	    if (deleteAction) {
			showLoading();
	    	Public.ajaxGet('delete', {ids : ids}, function(e) {
	    		hideLoading();
				if (200 == e.status) {
					$('#inputForm').submit();
				} else
					$.messager.alert('错误','删除失败！'+ e.msg,'error');
			});
	    }
	});
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
		$.messager.alert('警告','待审核状态的土地备案变更申请信息不可被编辑！','warning');
		return false;
	}
	if(rows[0].spstatus=='03'){
		$.messager.alert('警告','已审核通过的土地备案变更申请信息不可被编辑！','warning');
		return false;
	}
	var id = rows[0].id;
	var url = "${ctx}/landChange/editInput?retFlag=1&id=" + id;
	var year = $('#year').combobox('getValue');
	var companyCode = $('#companyCode').combobox('getValue');
	var status = $('#status').combobox('getValue');
	var beginDate = $('#beginDate').val();
	var endDate = $('#endDate').val();
	url += "&year=" + year;
	url += "&companyCode=" + companyCode;
	url += "&status=" + status;
	url += "&beginDate=" + beginDate;
	url += "&endDate=" + endDate;
	document.location.href = url;
	
}

function add(id){
	document.location.href = "${ctx}/specLandReg/editInput";
}

function closeEdiDialog(){
	$('#userEditDlg').dialog('close');
}
//查看
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
	var url = "${ctx}/landChange/view?id=" + id + "&gotoPage=1";
	url += "&year=" + year;
	url += "&companyCode=" + companyCode;
	url += "&status=" + status;
	url += "&beginDate=" + beginDate;
	url += "&endDate=" + endDate;
	
	document.location.href = url;
}
//提交申请
function submitAudit(){
	var ids = [];
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
	if(rows[0].spstatus!='01'){
		$.messager.alert('警告','只有待申请的记录允许提交申请，请重新选择！','warning');
		return false;
	}
	$.messager.confirm("确认", "您确定要将选中的记录提交申请吗？", function (deleteAction) {
	    if (deleteAction) {
	    	ids.push(rows[0].id);
	    	showLoading();
	    	Public.ajaxGet('submitAudit', {ids : ids}, function(e) {
	    		hideLoading();
	    		if (200 == e.status) {
	    			form_check();
	    		} else
	    		    $.messager.alert('错误','提交失败！'+ e.msg,'error');
	    	});
	    }
	});
}

function form_check(){
	var beginDate =$.trim($("#beginDate").datebox('getValue'));
	var endDate =$.trim($("#endDate").datebox('getValue'));
	if(!dateCompare(beginDate,endDate)){
		$.messager.alert('警告','申请日期开始日不能大于结束日。','warning');
		return false;
	}
	showLoading();
	$('#inputForm').submit();
}
</script>
</body>
</html>
