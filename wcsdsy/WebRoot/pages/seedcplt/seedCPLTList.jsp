<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<title>用户管理-五常优质水稻溯源监管平台</title>
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
<script type="text/javascript" src="${ctx}/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">
		<fieldset id="queryBlock" class="fieldset_common_style">
			<form id="inputForm" name="inputForm" method="get" action="../seedCPLT/search">
				<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
				<input type="hidden" id="page" name="page" value="${pageModel.page}">
				<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
				
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_query_style">企业名称：</td>
						<td class="table_common_td_txt_query_style">
							<simple:select id="companyCode" name="companyCode" codeKey="01" hasPleaseSelectOption="true" value="${companyCode}" entityName="Company" />
						</td>
						<td class="table_common_td_label_query_style">处理状态：</td>
						<td class="table_common_td_txt_query_style">
							<simple:select id='settleStatus' name="settleStatus" value="${settleStatus}" codeKey="SettleStatus" entityName="commonData" hasPleaseSelectOption="true" width="170"/><BR>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_query_style">投诉日期：</td>
						<td colspan="3" class="table_common_td_txt_query_style editableFalse">
							<input class="easyui-datebox" id="complaintBeginDate" name="complaintBeginDate" value="${complaintBeginDate }"
	            				data-options="required:false,showSeconds:false" style="width:187px" editable="false"/>
							~
							<input class="easyui-datebox" id="complaintEndDate" name="complaintEndDate" value="${complaintEndDate }"
	            				data-options="required:false,showSeconds:false" style="width:187px" editable="false"/>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_query_style">处理日期：</td>
						<td colspan="2" class="table_common_td_txt_query_style editableFalse">
							<input class="easyui-datebox" id="settleBeginDate" name="settleBeginDate" value="${settleBeginDate }"
	            				data-options="required:false,showSeconds:false" style="width:187px" editable="false"/>
							~
							<input class="easyui-datebox" id="settleEndDate" name="settleEndDate" value="${settleEndDate }"
	            				data-options="required:false,showSeconds:false" style="width:187px" editable="false"/>
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
						<a href="#" class="easyui-linkbutton" onclick="viewInfo();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-view l-btn-icon-left">查看</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="deleteRecord();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-remove l-btn-icon-left">删除</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="return settle();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-process l-btn-icon-left">处理</span>
							</span>
						</a>
					</td>
				</tr>
			</table>
			</fieldset>	
		<table id="data" class="easyui-datagrid" striped="true" singleSelect="false" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="id" width="140" align="center" checkbox="true"></th>
					<th field="monitorDate" width="120" align="center">企业名称</th>
					<th field="monitorPoint" width="120" align="center">投诉人</th>
					<th field="tsp" width="120" align="center">投诉日期</th>
					<th field="so2" width="120" align="center">投诉内容</th>
					<th field="createDate" width="120" align="center">处理状态</th>
					<th field="no2" width="120" align="center">处理人</th>
					<th field="field1" width="120" align="center">处理日期</th>
					<th field="field2" width="120" align="center">处理方式</th>
					<th field="field3" width="120" align="center">处理结果</th>
					<th field="field4" width="120" align="center">资料查看</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="companyCPLT" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${companyCPLT.id}</td>
					<td height="30" align="center" nowrap>
						<simple:showName entityName="company" value="${companyCPLT.companyCode}"></simple:showName>
					</td>
					<td height="30" align="center" nowrap>${companyCPLT.complainant}</td>
					<td height="30" align="center" nowrap>
						<fmt:formatDate value="${companyCPLT.complaintDate}" pattern="yyyy-MM-dd"/></td>
					<td height="30" align="center" nowrap>${companyCPLT.complaintContent}</td>
					<td height="30" align="center" nowrap>
						<simple:showName entityName="commonData" codeKey="SettleStatus" value="${companyCPLT.settleStatus}"></simple:showName>
					</td>
					<td height="30" align="center" nowrap>${companyCPLT.processor}</td>
					<td height="30" align="center" nowrap>
						<fmt:formatDate value="${companyCPLT.settleDate}" pattern="yyyy-MM-dd"/>
					</td>
					<td height="30" align="center" nowrap>${companyCPLT.processMode}</td>
					<td height="30" align="center" nowrap>${companyCPLT.processResult}</td>
					<td height="30" align="center" nowrap><a href="javascript:void(0)" onclick="return view('${companyCPLT.id}');">凭证查看</a></td>
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
		$.messager.alert('警告','请选择一条记录。','warning');
		return false;
	} 
	
	$.messager.confirm("确认", "您确定要删除选中的记录吗？", function (deleteAction) {
	    if (deleteAction) {
	    	for(var i=0; i<rows.length; i++) ids.push(rows[i].id);
	    	Public.ajaxGet('delete', {ids : ids}, function(e) {
	    		if (200 == e.status) {
	    			if( e.data.status == "02" ){
	    				$.messager.alert('警告','已处理,不能删除。','warning');
	    				return ;
	    			}
	    			$.messager.alert('提示','删除成功。','info');
	    			form_check();
	    		} else
	    			$.messager.alert('错误','删除失败！'+ e.msg,'error');
	    	});
	    }
	});

}

function view(id){
	$('#addDialog').dialog({
	    title: '查看种子公司投诉凭证',
	    width: 580,
	    height: 480,
	    closed: false,
	    cache: false,
	    href: 'view?id='+ id,
	    modal: true
	});
	
}
function viewInfo(){
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
	$('#addDialog').dialog({
	    title: '查看种子公司投诉数据',
	    width: 580,
	    height: 480,
	    closed: false,
	    cache: false,
	    href: 'viewInfo?id='+ id,
	    modal: true
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
	$('#addDialog').dialog({
	    title: '编辑粮食评估参数',
	    width: 500,
	    height: 400,
	    closed: false,
	    cache: false,
	    href: 'editInput?id='+ id,
	    modal: true
	});
}

function add(){
	$('#addDialog').dialog({
	    title: '添加大气监测数据',
	    width: 500,
	    height: 400,
	    closed: false,
	    cache: false,
	    href: 'editInput',
	    modal: true
	});
}

function closeEdiDialog(){
	$('#graiEvalEditDlg').dialog('close');
}

function form_check(){
	var complaintBeginDate =$.trim($("#complaintBeginDate").datebox('getValue'));
	var complaintEndDate =$.trim($("#complaintEndDate").datebox('getValue'));
	if(!dateCompare(complaintBeginDate,complaintEndDate)){
		$.messager.alert('警告','投诉日期开始日不能大于结束日。','warning');
		return false;
	}
	var settleBeginDate =$.trim($("#settleBeginDate").datebox('getValue'));
	var settleEndDate =$.trim($("#settleEndDate").datebox('getValue'));
	if(!dateCompare(settleBeginDate,settleEndDate)){
		$.messager.alert('警告','处理日期开始日不能大于结束日。','warning');
		return false;
	}
	showLoading();
	$('#inputForm').submit();
}
//处理
function settle(){
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
	showLoading();
	Public.ajaxPost('settleValidate?id=' + id, {}, function(e) {
		hideLoading();
		if (200 == e.status) {
			if( e.data.status == "02" ){
				$.messager.alert('警告','已处理,不能重复处理。','warning');
				return ;
			}else{
				settleData( id );
			}
		} else {
			$.messager.alert('错误','处理失败！'+ e.msg,'error');
		}
	});
} 

function settleData( id ){
	$('#addDialog').dialog({
	    title: '处理种子投诉',
	    width: 650,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: 'settleInput?id='+ id,
	    modal: true
	});
}

function save(){
	var a = $('#addFrom').toObject();
	var qx = $("input[name='processMode']:checked").map(function () {
        return $(this).val();
    }).get().join(',');
    delete a.processMode; 
    a.processModel = qx;
	Public.ajaxPost('save', JSON.stringify(a), function(e) {
		if (200 == e.status) {
			$.messager.alert('提示','保存成功。','info');
			closeEdiDialog();
			form_check();
		} else {
			$.messager.alert('错误','保存失败！'+ e.msg,'error');
		}
	});
}
</script>
</body>
</html>