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
			<form id="inputForm" name="inputForm" method="get" action="../graiFore/listquery">
				<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
				<input type="hidden" id="page" name="page" value="${pageModel.page}">
				<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
				
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_query_style">年度：</td>
						<td class="table_common_td_txt_query_style editableFalse">
							<%@include file="../../yearComm.jsp" %>
						</td>
						<td class="table_common_td_label_query_style">企业：</td>
						<td class="table_common_td_txt_query_style">
							<%@ include file="../../companyCommExt.jsp" %>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_query_style">预报日期：</td>
						<td colspan="2" class="table_common_td_txt_query_style editableFalse">
							<input class="easyui-datebox" name="beginDate" id="beginDate" value="${beginDate }"
	            				data-options="required:false,showSeconds:false" style="width:100px" editable="false"/>
							~
							<input class="easyui-datebox" id="endDate" name="endDate" value="${endDate }"
	            				data-options="required:false,showSeconds:false" style="width:100px" editable="false"/>
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
						<a href="#" class="easyui-linkbutton" onclick="return deleteRecord();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-remove l-btn-icon-left">删除</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="return viewReport();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-export l-btn-icon-left">预报报表导出</span>
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
					<th field="year" width="60" align="center">年度</th>
					<th field="companyName" width="140" align="center">企业</th>
					<th field="name" width="100" align="center">农户姓名</th>
					<th field="idNumber" width="130" align="center">身份证号</th>
					<th field="inputName" width="120" align="center">实（亩）合计</th>
					<th field="inpuDealer" width="120" align="center">测量（亩）合计</th>
					<th field="purchaseQuantity" width="120" align="center">预估总产量（斤）</th>
					<th field="purchasingAgent" width="120" align="center">预报日期</th>
					<th field="zy" width="120" align="center">争议地块</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="graiFore" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${graiFore.id}</td>
					<td height="30" align="center" nowrap>${graiFore.year}</td>
					<td height="30" align="center" nowrap>${graiFore.companyName}</td>
					<td height="30" align="center" nowrap>${graiFore.farmerName}</td>
					<td height="30" align="center" nowrap>${graiFore.idNumber}</td>
					<td height="30" align="center" nowrap>${graiFore.actualMu}</td>
					<td height="30" align="center" nowrap>${graiFore.measurementMu}</td>
					<td height="30" align="center" nowrap>${graiFore.minEstimateTotalYield} <br/>
						${graiFore.maxEstimateTotalYield}
					</td>
					<td height="30" align="center" nowrap>
						<fmt:formatDate value="${graiFore.forecastDate}" pattern="yyyy-MM-dd"/>
					</td>
					<td height="30" align="center" nowrap>
						${graiFore.zyLand }
					</td>
				</tr>
				</c:forEach>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>合计：</td>
					<td>${pageModel.data.actualMu}</td>
					<td>${pageModel.data.measurementMu}</td>
					<td>${pageModel.data.minEstimateTotalYield}</br>${pageModel.data.maxEstimateTotalYield}</td>
					<td></td>
				</tr>	
			</tbody>
		</table>
	</div>
	<div id="addDialog"></div>
	<div id="expDialog"></div>
	
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
		height:winHeight -queryBlockHeight -toolBlock-50,
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
	    	$('#inputForm').submit();
	    }
	});
});

function deleteRecord(id){
	var ids = [];
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length < 1){
		$.messager.alert('警告','请至少选择一条记录。','warning');
		return false;
	}
	$.messager.confirm("确认", "您确认删除选定的记录吗？", function (deleteAction) {
	    if (!deleteAction) {
			return false;
	    }
		showLoading();
		for(var i=0; i<rows.length; i++) ids.push(rows[i].id);
		Public.ajaxGet('delete', {ids : ids}, function(e) {
			if (200 == e.status) {
				form_check();
			} else{
				hideLoading();
				$.messager.alert('错误','删除失败！' + e.msg,'error');
			}
		});
	});
}

function edit(id){
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
	
	document.location.href = "${ctx}/graiReg/editInput?id=" + id;
}
function add(id){
	document.location.href = "${ctx}/graiReg/editInput";
}

function closeEdiDialog(){
	$('#provEvalEditDlg').dialog('close');
}

function viewReport(){
	var year = $("#year").val();
	var companyCode = $("#companyCode").combobox('getValue');
	var companyName = $("#companyCode").combobox('getText');
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	
	var param = 'year='+year + "&companyCode=" + companyCode + "&companyName=" + companyName + "&t=" + (new Date).getTime();
	param += "&beginDate=" + beginDate;
	param += "&endDate=" + endDate;
	
	Public.ajaxGet("checkExcel?"+param, {},
		function(e) {
		if (200 == e.status) {
			if(e.data > 0){
				window.open("expExcel?"+param);
			}else{
				$.messager.alert('提示','没有可导出的数据。','info');
			}
		} else {
			$.messager.alert('提示','导出失败。' + e.msg,'info');
		}
	});
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
	
	$('#addDialog').dialog({
	    title: '查看',
	    width: 650,
	    height: 410,
	    closed: false,
	    cache: false,
	    href: 'view?id=' + id + "&t=" + (new Date).getTime(),
	    modal: true
	});
}
function form_check(){
	var beginDate =$.trim($("#beginDate").datebox('getValue'));
	var endDate =$.trim($("#endDate").datebox('getValue'));
	if(!dateCompare(beginDate,endDate)){
		$.messager.alert('警告','预报日期开始日不能大于结束日。','warning');	
		return false;
	}
	showLoading();
	$('#inputForm').submit();
}
</script>
</body>
</html>