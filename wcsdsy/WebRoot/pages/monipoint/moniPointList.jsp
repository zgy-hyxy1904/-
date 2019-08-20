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
			<form id="inputForm" name="inputForm" method="get" action="../moniPoint/list">
				<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
				<input type="hidden" id="page" name="page" value="${pageModel.page}">
				<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_query_style">监测点类型：</td>
						<td class="table_common_td_txt_query_style">
							<simple:select id="monitorPointType" name="monitorPointType" codeKey="MonitorPointType" entityName="CommonData" width="170px;" value="${monitorPointType}"/>
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
						<a href="#" class="easyui-linkbutton" onclick="view();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-view l-btn-icon-left">查看</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="add();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-add l-btn-icon-left">添加</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="edit();">
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
					<th field="monitorDate" width="120" align="center">监测点类型</th>
					<th field="monitorPoint" width="120" align="center">监测点编号</th>
					<th field="tsp" width="120" align="center">监测点名称</th>
					<th field="so2" width="120" align="center">经度</th>
					<th field="no21" width="120" align="center">纬度</th>
					<th field="no22" width="120" align="center">监测点地址</th>
					<th field="no23" width="120" align="center">监测点描述</th>
					<th field="createDate" width="120" align="center">修改人</th>
					<th field="createUserId" width="120" align="center">修改日期</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="moniPoint" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${moniPoint.id}</td>
					<td height="30" align="center" nowrap>
						<simple:showName entityName="commonData" codeKey="MonitorPointType" value="${moniPoint.monitorPointType}"></simple:showName>
					</td>
					<td height="30" align="center" nowrap>${moniPoint.monitorPointCode}</td>
					<td height="30" align="center" nowrap>${moniPoint.monitorPointName}</td>
					<td height="30" align="center" nowrap>${moniPoint.longitude}</td>
					<td height="30" align="center" nowrap>${moniPoint.latitude}</td>
					<td height="30" align="center" nowrap>${moniPoint.monitorPointAddress}</td>
					<td height="30" align="center" nowrap>${moniPoint.sectionDescription}</td>
					<td height="30" align="center" nowrap>${moniPoint.updateUserId}</td>
					<td height="30" align="center" nowrap>
						<fmt:formatDate value="${moniPoint.updateDate}" pattern="yyyy-MM-dd"/>
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
	    var toolBlock = $("#toolBlock").height();
	    height:winHeight -queryBlockHeight -toolBlock-28,
		$("#data").datagrid({ 
			height:winHeight -queryBlockHeight -toolBlock-46,
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
		    onSelectPage: function(pageNumber, pageSize){
		    	$('#page').val(pageNumber);
		    	$('#pageSize').val(pageSize);
		    	$('#inputForm').submit();
		    }
		});
});

function view(id){
		var rows = $('#data').datagrid('getSelections');
		var length = rows.length;
		if(length == 0 || length > 1){
			$.messager.alert('警告','请至少选择一条记录！','warning');
			return false;
		}
		var id = rows[0].id;
		$('#addDialog').dialog({
		    title: '查看监测点数据',
		    width: 330,
		    height: 290,
		    closed: false,
		    cache: false,
		    href: 'view?id='+ id,
		    modal: true
		});
}
	
function edit(id){
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length == 0 || length > 1){
		$.messager.alert('警告','请至少选择一条记录！','warning');
		return false;
	}
	var id = rows[0].id;
	$('#addDialog').dialog({
	    title: '编辑监测点数据',
	    width: 350,
	    height: 330,
	    closed: false,
	    cache: false,
	    href: 'editInput?id='+ id,
	    modal: true
	});
}

function deleteRecord(){
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length < 1){
		$.messager.alert('警告','请至少选择一条记录！','warning');
		return false;
	}
	  $.messager.confirm("确认", "您确定要删除选中的记录吗？", function (deleteAction) {
		    if (deleteAction) {
				var ids = [];
				var rows = $('#data').datagrid('getSelections');
				var length = rows.length;
				for (var i = 0; i < rows.length; i++)
					ids.push(rows[i].id);
				Public.ajaxGet('delete', {
					ids : ids
				}, function(e) {
					if (200 == e.status) {

						form_check();
					} else{
						$.messager.alert('错误','删除失败！' + e.msg,'error');
					}
					
				});
		    }
		});
}

function add(){
	$('#addDialog').dialog({
	    title: '添加监测点数据',
	    width: 350,
	    height: 330,
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
	$('#inputForm').submit();
}
</script>
</body>
</html>