<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
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
		<fieldset id="queryBlock" class="fieldset_common_style">
			<form id="inputForm" name="inputForm" method="get" action="../soilMoni/search">
				<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
				<input type="hidden" id="page" name="page" value="${pageModel.page}">
				<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
				
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_query_style">监测日期：</td>
						<td class="table_common_td_txt_query_style">
							<input class="easyui-datebox" id="beginDate" name="beginDate" value="${beginDate }"
		           				data-options="required:false,showSeconds:false" style="width:150px" editable="false"/>
							~
							<input class="easyui-datebox" id="endDate" name="endDate" value="${endDate }"
		           				data-options="required:false,showSeconds:false" style="width:150px" editable="false"/>
						</td>
						<td align="right" valign="bottom">
	                      	<a href="#" class="easyui-linkbutton" onclick="return form_check();">
	                      		<span class="l-btn-left">
	                      			<span class="l-btn-text icon-search l-btn-icon-left">查询</span>
	                      		</span>
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
					<th field="year" width="120" align="center">监测日期</th>
					<th field="code" width="160" align="center">监测点位</th>
					<th field="minYield" width="120" align="center">有机质(g/kg)</th>
					<th field="maxYield" width="120" align="center">碱解氮(mg/kg)</th>
					<th field="milledriceRate" width="120" align="center">有效磷(mg/kg)</th>
					<th field="milledriceRate1" width="120" align="center">速效钾(mg/kg)</th>
					<th field="milledriceRate2" width="120" align="center">PH值</th>
					<th field="milledriceRate3" width="120" align="center">创建人</th>
					<th field="milledriceRate4" width="120" align="center">创建日期</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="graiEval" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${graiEval.id}</td>
					<td height="30" align="center" nowrap>
						<fmt:formatDate value="${graiEval.monitorDate}" pattern="yyyy-MM-dd"/>
					</td>
					<td height="30" align="center" nowrap>${graiEval.monitorPointName}</td>
					<td height="30" align="center" nowrap>${graiEval.omvalue}</td>
					<td height="30" align="center" nowrap>${graiEval.alkelinen}</td>
					<td height="30" align="center" nowrap>${graiEval.olsenp}</td>
					<td height="30" align="center" nowrap>${graiEval.olsenk}</td>
					<td height="30" align="center" nowrap>${graiEval.ph}</td>
					<td height="30" align="center" nowrap>${graiEval.createUserId}</td>
					<td height="30" align="center" nowrap>
						<fmt:formatDate value="${graiEval.createDate}" pattern="yyyy-MM-dd"/>
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
	       // fit: true,
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
		    	showLoading();
		    	$('#inputForm').submit();
		    }
		});
	});

function deleteRecord(id){
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

function edit(id){
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if (length == 0 ) {
		$.messager.alert('警告','必须选择一条记录！','warning');
		return false;
	}
	if (length > 1) {
		$.messager.alert('警告','只能选择一条记录！','warning');
		return false;
	}
	var id = rows[0].id;
	$('#addDialog').dialog({
	    title: '编辑土壤监测数据',
	    width: 380,
	    height: 328,
	    closed: false,
	    cache: false,
	    href: 'editInput?id='+ id,
	    modal: true
	});
}

function view(){
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length == 0 || length > 1){
		$.messager.alert('警告','必须且只能一条记录！','warning');
		return false;
	}
	var id = rows[0].id;
	$('#addDialog').dialog({
	    title: '查看土壤监测数据',
	    width: 350,
	    height: 328,
	    closed: false,
	    cache: false,
	    href: 'view?id='+ id,
	    modal: true
	});
}
function add(){
	$('#addDialog').dialog({
	    title: '添加土壤监测数据',
	    width: 380,
	    height: 328,
	    closed: false,
	    cache: false,
	    href: 'add',
	    modal: true
	});
}

function closeEdiDialog(){
	$('#graiEvalEditDlg').dialog('close');
}

function form_check(){
	var beginDate =$.trim($("#beginDate").datebox('getValue'));
	var endDate =$.trim($("#endDate").datebox('getValue'));
	if(!dateCompare(beginDate,endDate)){
		$.messager.alert('警告','监测日期开始日不能大于结束日！。','warning');
		return false;
	}
	showLoading();
	$('#inputForm').submit();
}
</script>
</body>
</html>