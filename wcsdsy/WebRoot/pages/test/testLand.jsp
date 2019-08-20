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
		<fieldset class="fieldset_common_style">
			<form id="inputForm" name="inputForm" method="get" action="../specLandReg/list">
			<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
			<input type="hidden" id="page" name="page" value="${pageModel.page}">
			<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
			
			<table class="n_input">
				<tr>
					<td width="100" height="20" align="right" nowrap >年度：</td>
					<td nowrap>
						<%@ include file="../../yearComm.jsp" %>
					</td>
					<td width="100" height="20" align="right" nowrap >企业：</td>
					<td nowrap>
						<%@ include file="../../companyComm.jsp" %>
					</td>
				</tr>
				<tr>
					<td width="100" height="20" align="right" nowrap >申请日期：</td>
					<td nowrap>
						<input class="easyui-datebox" name="beginDate" value="${beginDate }"
            				data-options="required:false,showSeconds:false" style="width:100px" />
						~
						<input class="easyui-datebox" name="endDate" value="${endDate }"
            				data-options="required:false,showSeconds:false" style="width:100px" />
					</td>
					<td width="100" height="20" align="right" nowrap >状态：</td>
					</td>
					<td nowrap>
						<simple:select id='status' name="status" value="${status}" codeKey="SpecialRegistStatus" entityName="commonData" hasPleaseSelectOption="true" width="80"/><BR>
					</td>
					<td height="32" colspan="10" align="center" nowrap="nowrap">
                       	<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:15px; width:80px; height:30px;" onclick="return form_check();">查询</a>
             		</td>
				</tr>
				<tr>
					<td colspan="10">
					<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:15px; width:80px; height:30px;" onclick="return view();">查看</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:15px; width:80px; height:30px;" onclick="return add();">添加</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:15px; width:80px; height:30px;" onclick="return edit();">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:15px; width:80px; height:30px;" onclick="return deleteRecord();">删除</a>
					
					<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:15px; width:80px; height:30px;" onclick="return submitAudit();">提交申请</a>
					</td>
				</tr>
			</table>	
			</form>
		</fieldset>
		
		<table id="data" class="easyui-datagrid" striped="true" singleSelect="false" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="id" width="140" align="center" checkbox="true"></th>
					<th field="ApplyBatchNo" width="140" align="center">申请批次号</th>
					<th field="year" width="70" align="center">年度</th>
					<th field="CompanyName" width="140" align="center">企业</th>
					<th field="date1" width="120" align="center">申请日期</th>
					<th field="InputName" width="100" align="center">申请人</th>
					<th field="InpuDealer" width="120" align="center">本次备案总面积<br/>(亩)</th>
					<th field="PurchaseQuantity" width="120" align="center">状态</th>
					<th field="PurchasingAgent" width="120" align="center">驳回原因</th>
					<th field="PurchaseDate" width="100" align="center">审核人</th>
					<th field="view" width="120" align="center">审核时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="specLandReg" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${specLandReg.id}</td>
					<td height="30" align="center" nowrap>${specLandReg.applyBatchNo}</td>
					<td height="30" align="center" nowrap>${specLandReg.year}</td>
					<td height="30" align="center" nowrap>${specLandReg.companyName}</td>
					<td height="30" align="center" nowrap>
						<fmt:formatDate value="${specLandReg.operatorDate}" pattern="yyyy-MM-dd"/> 
					</td>
					<td height="30" align="center" nowrap>${specLandReg.contractorName}</td>
					<td height="30" align="center" nowrap>${specLandReg.archiveAcreage}</td>
					<td height="30" align="center" nowrap>
						<simple:showName entityName="commonData" codeKey="SpecialRegistStatus" value="${specLandReg.status}"></simple:showName> 
					</td>
					<td height="30" align="center" nowrap>${specLandReg.reason}</td>
					<td height="30" align="center" nowrap>${specLandReg.auditor}</td>
					<td height="30" align="center" nowrap>${specLandReg.auditTime}</td>
				</tr>
				</c:forEach>		
			</tbody>
		</table>
	</div>
	<div id="addDialog"></div>
<script type="text/javascript">
$(document).ready(function(){
	$("#data").datagrid({ 
		pagination: true,
        rownumbers: true,
        fitColumns: true,
        fit: true,
        pageList: [15,20,25],
        pagePosition: "top"
	});
	
	var pagger = $('#data').datagrid('getPager');  
	$(pagger).pagination({
		total: $("#pageTotal").val(),
		pageNumber: $("#page").val(),
		showPageList: true,
	    onSelectPage: function(pageNumber, pageSize){
	    	$('#page').val(pageNumber);
	    	$('#pageSize').val(pageSize);
	    	$('#userform').submit();
	    }
	});
});

function deleteRecord(id){
	if(!confirm("您确定要删除选中的记录吗？")) return false;
	var ids = [];
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	for(var i=0; i<rows.length; i++) ids.push(rows[i].id);
	Public.ajaxGet('delete', {ids : ids}, function(e) {
		if (200 == e.status) {
			Public.tips({
				content : "成功！"
			});
			form_check();
		} else
			Public.tips({
				type : 1,
				content : "失败！" + e.msg
			});
	});
}

function edit(id){
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length == 0 || length > 1){
		alert('必须且只能选择一条记录！');
		return false;
	}
	var id = rows[0].id;
	
	document.location.href = "${ctx}/specLandReg/editInput?id=" + id;
}
function add(id){
	document.location.href = "${ctx}/specLandReg/editInput";
}

function closeEdiDialog(){
	$('#userEditDlg').dialog('close');
}
//查看
function view(){
	
}
//提交申请
function submitAudit(){
	if(!confirm("您确定要将选中的记录提交申请吗？")) return false;
	var ids = [];
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	for(var i=0; i<rows.length; i++) ids.push(rows[i].id);
	Public.ajaxGet('submitAudit', {ids : ids}, function(e) {
		if (200 == e.status) {
			Public.tips({
				content : "成功！"
			});
			form_check();
		} else
			Public.tips({
				type : 1,
				content : "失败！" + e.msg
			});
	});
}

function form_check()
{
	$('#inputForm').submit();
}
</script>
</body>
</html>