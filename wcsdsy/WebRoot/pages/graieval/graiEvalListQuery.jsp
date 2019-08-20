<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
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
	<div region="center" border="false" style="padding:5px;">	
		<fieldset id="queryBlock" class="fieldset_common_style">
			<form id="inputForm" name="inputRegForm" method="get" action="../graiEval/listquery">
				<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
				<input type="hidden" id="page" name="page" value="${pageModel.page}">
				<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
				
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_query_style">年度：</td>
						<td class="table_common_td_txt_query_style">
							<simple:select id="year" name="year" value="${year}" entityName="YearCode" width="187" canEdit="true" hasPleaseSelectOption="false"/>
						</td>
						<td class="table_common_td_label_query_style">企业名称：</td>
						<td class="table_common_td_txt_query_style">
							<simple:select id="companyCode" name="companyCode" value="${companyCode}" codeKey="02" entityName="company" width="200" hasPleaseSelectOption="${!sessionScope.isCompanyUser}" readOnly="${sessionScope.isCompanyUser}" canEdit="${!sessionScope.isCompanyUser}" />
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
		<fieldset id="infoBlock" class="fieldset_common_style">
			<table class="table_common_style">
				<tr>
					<td class="table_common_td_label_style">普通土地备案面积（亩）：</td>
					<td class="table_common_td_txt_style">
						<input type="text" style="background-color: #8B8B7A" value='<fmt:formatNumber type="number" value="${geneLandsArea}" pattern="0.00" maxFractionDigits="2"/>' class="easyui-textbox" size="25" editable="false"/>
					</td>
					<td class="table_common_td_label_style">特殊土地备案面积（亩）：</td>
					<td class="table_common_td_txt_style">
						<input type="text" style="background-color: #8B8B7A" value='<fmt:formatNumber type="number" value="${sepcLandsArea}" pattern="0.00" maxFractionDigits="2"/>' class="easyui-textbox" size="25" editable="false"/>
					</td>
				</tr>
				<tr>
					<td class="table_common_td_label_style">地块总面积（亩）：</td>
					<td colspan="3" class="table_common_td_txt_style">
						<input type="text" style="background-color: #8B8B7A" value='<fmt:formatNumber type="number" value="${sumAreas}" pattern="0.00" maxFractionDigits="2"/>' class="easyui-textbox" size="25" editable="false"/>
					</td>
				</tr>
				<tr>
					<td class="table_common_td_label_style">粮食评估产量（斤）：</td>
					<td  colspan="3" class="table_common_td_txt_style">
						<input type="text" readonly="readonly" value='<fmt:formatNumber type="number" value="${minYield}" pattern="0.00" maxFractionDigits="2"/>' class="easyui-textbox"  size="25"/>
						~
						<input type="text" readonly="readonly" value='<fmt:formatNumber type="number" value="${maxYield}" pattern="0.00" maxFractionDigits="2"/>' class="easyui-textbox"  size="25"/>
					</td>
				</tr>
			</table>	
		</fieldset>
		<table id="data" class="easyui-datagrid" striped="true"  singleSelect="false" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="contractorName" width="120" align="center">承包方</th>
					<th field="homeAddress" width="240" align="center">住址</th>
					<th field="archiveDate" width="150" align="center">备案时间</th>
					<th field="archiveAcreage" width="120" align="center">备案面积（亩）</th>
					<th field="landType" width="120" align="center">备案类型</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach var="graiEval" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${graiEval.contractorName}</td>
					<td height="30" align="center" nowrap>${graiEval.homeAddress}</td>
					<td height="30" align="center" nowrap>${graiEval.archiveDate}</td>
					<td height="30" align="center" nowrap>${graiEval.archiveAcreage}</td>
					<td height="30" align="center" nowrap>${graiEval.landType}</td>
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
	var infoBlock = $("#infoBlock").height();
	    
	$("#data").datagrid({ 
		height:winHeight -queryBlockHeight -infoBlock-50,
		pagination: true,
        rownumbers: true,
        fitColumns: true,
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

function deleteProvEval(id){
	  
	var ids = [];
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	
	if(length < 1){
		$.messager.alert('警告','请至少选择一条记录。','warning');
		return false;
	}
	  $.messager.confirm("确认", "您确认删除选定的记录吗？", function (deleteAction) {
		    if (deleteAction) {
		alert("你选择了OK");
		    }
		});
	//if(!confirm("您确定要删除选中的记录吗？")) return false;
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
		$.messager.alert('警告','必须且只能选择一条记录！','warning');
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
	var yearCode = $('#year').combobox('getValue');
	if(yearCode == ''){
		$.messager.alert('警告','请选择年度。','warning');
		return false;
	}
	
	var companyCode = $('#companyCode').combobox('getValue');
	if(companyCode == ''){
		$.messager.alert('警告','请选择企业。','warning');
		return false;
	}
	showLoading();
	$('#inputForm').submit();
}
</script>
</body>
</html>