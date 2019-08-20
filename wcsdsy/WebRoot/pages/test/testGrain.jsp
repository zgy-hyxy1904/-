<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
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
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:0 10px;">	
		<fieldset class="fieldset_common_style">
			<form id="inputForm" name="inputRegForm" method="get" action="../graiEval/listquery">
			<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
			<input type="hidden" id="page" name="page" value="${pageModel.page}">
			<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
			
			<table class="n_input">
				<tr>
					<td width="100" height="20" align="right" nowrap >企业名称：</td>
					<td nowrap>
						<%@ include file="../../companyComm.jsp" %>
					</td>
					<td width="100" height="20" align="right" nowrap >年度：</td>
					<td nowrap>
						<%@ include file="../../yearComm.jsp" %>
					</td>
					<td height="32" colspan="10" align="center" nowrap="nowrap">
                       	<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:15px; width:80px; height:30px;" onclick="return form_check();">查询</a>
             		</td>
				</tr>
				<tr>
					<td width="100" height="20" align="right" nowrap>地块面积：</td>
					<td ><input type="text" style="background-color: #8B8B7A" value="${sumAreas}" class="easyui-textbox"/>亩</td>
					<td width="100" height="20" align="right" nowrap> &nbsp;</td>
					<td> &nbsp;</td>
				</tr>
				<tr>
					<td width="100" height="20" align="right" nowrap>粮食评估产量：</td>
					<td colspan="10">
						<input type="text" value="${maxYield }" class="easyui-textbox"/>斤
					</td>
					 
				</tr>
			</table>	
			</form>
		</fieldset>
		
		<table>
			<tr>
				<td>地块信息：</td>
			</tr>
		</table>
		<table id="data" class="t1" style="table-layout:fixed;border-collapse: collapse;">
			<thead>
				<tr>
					<th field="name" width="120" align="center">承包方</th>
					<th field="address" width="120" align="center">住址</th>
					<th field="landClass" width="120" align="center">土地类别</th>
					<th field="location" width="120" align="center">地块位置</th>
					<th field="mj" width="120" align="center">备案面积（亩）</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="provEval" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${provEval.contractorName}</td>
					<td height="30" align="center" nowrap>
						${provEval.homeAddress}
					</td>
					<td height="30" align="center" nowrap>${provEval.plowlandName}</td>
					<td height="30" align="center" nowrap>${provEval.landAddress}</td>
					<td height="30" align="center" nowrap>${provEval.archiveAcreage}</td>
				</tr>
				</c:forEach>		
			</tbody>
		</table>
		<!-- table>
			<tr>
				<td>购种信息: </td>
			</tr>
		</table>
		<table id="data1" class="t1" style="table-layout:fixed;border-collapse: collapse;">
			<thead>
				<tr>
					<th field="name" width="120" align="center">凭证编号</th>
					<th field="address" width="120" align="center">购种日期</th>
					<th field="landClass" width="120" align="center">种子销售公司</th>
					<th field="location" width="120" align="center">重量（斤）</th>
					<th field="mj" width="120" align="center">凭证原图</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="provEval" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${provEval.year}</td>
					<td height="30" align="center" nowrap>
						<simple:showName entityName="seedVariety" value="${provEval.seedCode}"></simple:showName>
					</td>
					<td height="30" align="center" nowrap>${provEval.minYield}</td>
					<td height="30" align="center" nowrap>${provEval.maxYield}</td>
					<td height="30" align="center" nowrap>${provEval.maxYield}</td>
				</tr>
				</c:forEach>		
			</tbody>
		</table -->
		
	</div>
	<div id="addDialog"></div>
	
<script type="text/javascript">
$(document).ready(function(){
	
});

function deleteProvEval(id){
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
	$('#addDialog').dialog({
	    title: '编辑种源评估参数',
	    width: 500,
	    height: 400,
	    closed: false,
	    cache: false,
	    href: 'editInput?id='+ id,
	    modal: true
	});
}
function add(id){
	$('#addDialog').dialog({
	    title: '添加种源评估参数',
	    width: 500,
	    height: 400,
	    closed: false,
	    cache: false,
	    href: 'editInput',
	    modal: true
	});
}

function closeEdiDialog(){
	$('#provEvalEditDlg').dialog('close');
}

function form_check(){
	$('#inputForm').submit();
}
</script>
</body>
</html>