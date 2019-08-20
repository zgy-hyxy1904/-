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
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>

</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">	
		<%-- fieldset class="fieldset_common_style">
			<form id="inputForm" name="inputForm" method="get" action="../graiFore/listquery">
			<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
			<input type="hidden" id="page" name="page" value="${pageModel.page}">
			<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
			
			<!-- table class="n_input">
				<tr>
					<td width="100" height="20" align="right" nowrap >年度：</td>
					<td nowrap>
						<%@include file="../../yearComm.jsp" %>
					</td>
					<td width="100" height="20" align="right" nowrap >企业：</td>
					<td nowrap>
						<%@ include file="../../companyComm.jsp" %>
					</td>
					<td width="100" height="20" align="right" nowrap >预报日期：</td>
					<td nowrap>
						<input class="easyui-datebox" name="beginDate" value="${beginDate }"
            				data-options="required:false,showSeconds:false" style="width:100px" />
						~
						<input class="easyui-datebox" name="endDate" value="${endDate }"
            				data-options="required:false,showSeconds:false" style="width:100px" />
					</td>
					<td height="32" colspan="10" align="center" nowrap="nowrap">
                       	<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:15px; width:80px; height:30px;" onclick="return form_check();">查询</a>
             		</td>
				</tr>
				<tr>
					<td colspan="10">
					<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:15px; width:80px; height:30px;" onclick="return view();">查看</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:15px; width:80px; height:30px;" onclick="return importData();">导入</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:15px; width:80px; height:30px;" onclick="return add();">提交预报</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:15px; width:80px; height:30px;" onclick="return add();">删除</a>
					</td>
				</tr>
			</table -->	
			</form>
		</fieldset --%>
		
		<table id="data" class="easyui-datagrid" striped="true" singleSelect="false" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="id" width="140" align="center" checkbox="true"></th>
					<th field="year" width="60" align="center">年度</th>
					<th field="companyName" width="120" align="center">农户姓名</th>
					<th field="idNumber" width="120" align="center">身份证号</th>
					<th field="inputName" width="120" align="center">实（亩）合计</th>
					<th field="inpuDealer" width="120" align="center">测量（亩）合计</th>
					<th field="purchaseQuantity" width="120" align="center">预估总产量（斤）</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="graiForeD" items="${list}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${graiForeD.year}</td>
					<td height="30" align="center" nowrap>${graiForeD.farmerName}</td>
					<td height="30" align="center" nowrap>${graiForeD.idNumber}</td>
					<td height="30" align="center" nowrap>${graiForeD.measurementMu}</td>
					<td height="30" align="center" nowrap>${graiForeD.actualMu}</td>
					<td height="30" align="center" nowrap>${graiForeD.measurementMu}</td>
					<td height="30" align="center" nowrap>
						${graiForeD.estimateTotalYield}
					</td>
				</tr>
				</c:forEach>		
			</tbody>
		</table>
	</div>
	<div id="addDialog"></div>
	<div id="importDialog"></div>
	
<script type="text/javascript">
$(document).ready(function(){
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
	
	document.location.href = "${ctx}/graiReg/editInput?id=" + id;
}
function add(id){
	document.location.href = "${ctx}/graiReg/editInput";
}

function closeEdiDialog(){
	$('#provEvalEditDlg').dialog('close');
}
function view(){
	
}
function form_check(){
	$('#inputForm').submit();
}
function importData(){
	var year = $("#year").val();
	var companyCode = $("#companyCode").val();
	var companyName = $("#companyName").val();
	
	$('#importDialog').dialog({
	    title: '导入收粮预报',
	    width: 500,
	    height: 400,
	    closed: false,
	    cache: false,
	    href: 'importInput?year='+year + "&companyCode=" + companyCode + "&companyName=" + companyName + "&t=" + (new Date).getTime(),
	    modal: true
	});
}
</script>
</body>
</html>