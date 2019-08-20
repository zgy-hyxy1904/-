<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<title>企业产量预报-五常优质水稻溯源监管平台</title>
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
			<form id="inputFrom" name="inputFrom" method="get" action="./">
			<table class="table_common_style">
				<tr>
					<td>
						<a href="#" class="easyui-linkbutton" onclick="add()">添加</a>
						<a href="#" class="easyui-linkbutton" onclick="edit()">修改</a>
						<a href="#" class="easyui-linkbutton" onclick="delete()">删除</a>
						<a href="#" class="easyui-linkbutton" onclick="save()">保存</a>
					</td>
				</tr>
				<tr>
					<td width="100" height="20" align="right" nowrap >年度：</td>
					<td nowrap>
						<simple:select id="year" name="year" entityName="yearcode" value="${yieldPredictModel.year}" width="100"/>
					</td>
					<td width="100" height="20" align="right" nowrap >企业：</td>
					<td nowrap>
						<input name="companyCode" value="${companyCode}" class="easyui-textbox" style="width:150px;height:25px">
					</td>
				</tr>
			</table>
			</form>
		</fieldset>
		
		<table id="data" class="easyui-datagrid">
			<thead>
				<tr>
					<th field="id" width="140" align="center" checkbox="true"></th>
					<th field="ApplyBatchNo" width="140" align="center">承包方类型</th>
					<th field="year" width="120" align="center">证件类型</th>
					<th field="companyName" width="120" align="center">证件号码</th>
					<th field="inputName" width="120" align="center">承包方</th>
					<th field="inpuDealer" width="120" align="center">住址</th>
					<th field="purchaseQuantity" width="120" align="center">土地类别</th>
					<th field="purchasingAgent" width="120" align="center">地块位置</th>
					<th field="purchaseDate" width="120" align="center">承包总面积<br />（亩）</th>
					<th field="view" width="120" align="center">已备案面积<br />（亩）</th>
					<th field="CreateUserId" width="120" align="center">可备案面积<br />（亩）</th>
					<th field="CreateDate1" width="120" align="center">本次备案面积<br />（亩）</th>
					<th field="CreateDate2" width="120" align="center">经办人</th>
					<th field="CreateDate3" width="120" align="center">经办日期</th> 
				</tr>
			</thead>
			<tbody>
				<c:forEach var="inputReg" items="${pageModel.result}" varStatus="status">
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

/**
* 页面保存按钮功能
*/
function save(id){
	var a = $('#inputFrom').toObject();
	Public.ajaxPost('save', JSON.stringify(a), function(e) {
		if (200 == e.status) {
			alert("保存成功！");
		} else {
			parent.parent.Public.tips({
				type : 1,
				content : "失败！" + e.msg
			});
		}
	});
}
/**
* 提交申请操作
*/
function apply(){
	
}

function closeEdiDialog(){
	$('#provEvalEditDlg').dialog('close');
}
//增加地块
function addLandRegD(){
	$('#addDialog').dialog({
	    title: '添加地块信息',
	    width: 950,
	    height: 450,
	    closed: false,
	    cache: false,
	    href: '${ctx}/geneLandRegD/editInput',
	    modal: true
	});
}
</script>
</body>
</html>