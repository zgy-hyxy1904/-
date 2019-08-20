<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
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
		<fieldset class="fieldset_common_style">
			<form id="inputForm" name="inputForm" method="get" action="../graiReg/list">
			<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
			<input type="hidden" id="page" name="page" value="${pageModel.page}">
			<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
			
			<table class="table_common_style">
				<tr>
					<td class="table_common_td_label_query_style">年度：</td>
					<td class="table_common_td_txt_query_style">
						<input name="year" id="year" value="${year}" class="easyui-textbox" style="width:200px;height:30px">
					</td>
					<td class="table_common_td_label_query_style">企业：</td>
					<td class="table_common_td_txt_query_style">
						<%@ include file="../../companyComm.jsp" %>
					</td>
					<td class="table_common_td_label_query_style">处理日期：</td>
					<td class="table_common_td_txt_query_style">
						<input class="easyui-datebox" name="beginDate" value="${beginDate }"
            				data-options="required:false,showSeconds:false" style="width:100px" editable="false"/>
						~
						<input class="easyui-datebox" name="endDate" value="${endDate }"
            				data-options="required:false,showSeconds:false" style="width:100px" editable="false"/>
					</td>
					<td align="right" valign="bottom">
                       	<a href="#" class="easyui-linkbutton" onclick="return form_check();"><span class="l-btn-left">
                       		<span class="l-btn-text icon-search l-btn-icon-left">查询</span></span>
                       	</a>
             		</td>
				</tr>
			</table>	
			</form>
		</fieldset>
		<fieldset class="fieldset_common_style">
			<table class="table_common_style">
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
		<table id="data" class="easyui-datagrid" striped="true" singleSelect="false" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="id" width="140" align="center" checkbox="true"></th>
					<th field="ApplyBatchNo" width="140" align="center">流水号</th>
					<th field="year" width="120" align="center">年度</th>
					<th field="companyName" width="120" align="center">企业</th>
					<th field="inputName" width="120" align="center">农户姓名</th>
					<th field="inpuDealer" width="120" align="center">身份证号码</th>
					<th field="purchaseQuantity" width="120" align="center">收粮重量</th>
					<th field="purchasingAgent" width="120" align="center">处理人</th>
					<th field="purchaseDate" width="120" align="center">处理日期</th>
					<th field="view" width="120" align="center">凭证查看</th>
					<th field="CreateUserId" width="120" align="center">登记人</th>
					<th field="CreateDate" width="120" align="center">登记时间</th> 
				</tr>
			</thead>
			<tbody>
				<c:forEach var="inputReg" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${inputReg.id}</td>
					<td height="30" align="center" nowrap>${inputReg.applyBatchNo}</td>
					<td height="30" align="center" nowrap>${inputReg.year}</td>
					<td height="30" align="center" nowrap>${inputReg.companyName}</td>
					<td height="30" align="center" nowrap>${inputReg.name}</td>
					<td height="30" align="center" nowrap>${inputReg.idNumber}</td>
					<td height="30" align="center" nowrap>${inputReg.weight}</td>
					<td height="30" align="center" nowrap>${inputReg.operatorName}</td>
					<td height="30" align="center" nowrap>${inputReg.operatorDate}</td>
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
        fitColumns: false,
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
	
	var ids = [];
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length < 1){
		alert('请至少选择一条记录！');
		return false;
	}
	if(!confirm("您确定要删除选中的记录吗？")) return false;
	for(var i=0; i<rows.length; i++) ids.push(rows[i].id);
	Public.ajaxGet('delete', {ids : ids}, function(e) {
		if (200 == e.status) {
			Public.tips({
				content : "成功！"
			});
			$('#userform').submit();
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
	$('#inputForm').submit();
}
</script>
</body>
</html>