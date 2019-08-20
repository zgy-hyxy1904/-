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
<script>
	var root = "${ctx}";
</script>
<script type="text/javascript" src="${ctx}/js/upload.js"></script>

</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">
		<fieldset id="queryBlock" class="fieldset_common_style">
			<form id="inputForm" name="inputForm" method="get" action="../specLandReg/listquery">
				<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
				<input type="hidden" id="page" name="page" value="${pageModel.page}">
				<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">	
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_query_style">年度：</td> 
						<td class="table_common_td_txt_query_style">
							<simple:select id="year" name="year" value="${year}" entityName="YearCode" width="211"/>
						</td>
						<td class="table_common_td_label_query_style">企业：</td>
						<td colspan="3" class="table_common_td_txt_query_style">
							<%@ include file="../../companyComm.jsp" %>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_query_style">申请日期：</td>
						<td class="table_common_td_txt_query_style editableFalse">
							<input class="easyui-datebox" id="beginDate" name="beginDate" value="${beginDate }"
	            				data-options="required:false,showSeconds:false" style="width:100px" editable="false"/>
							~
							<input class="easyui-datebox" id="endDate" name="endDate" value="${endDate }"
	            				data-options="required:false,showSeconds:false" style="width:100px" editable="false"/>
						</td>
						<td class="table_common_td_label_query_style">状态：</td>
						<td class="table_common_td_txt_query_style">
							<simple:select id='status' name="status" value="${status}" codeKey="SpecialRegistStatus" entityName="commonData" hasPleaseSelectOption="true" width="187"/>
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
						<a href="#" class="easyui-linkbutton" onclick="return auditInput();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-audit l-btn-icon-left">审核</span>
							</span>
						</a>
					</td>
				</tr>
			</table>	
		</fieldset>
		<table id="data" class="easyui-datagrid" striped="true" singleSelect="true" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="id" width="140" align="center" checkbox="true"></th>
					<th field="ApplyBatchNo" width="140" align="center">申请批次号</th>
					<th field="year" width="70" align="center">年度</th>
					<th field="CompanyName" width="218" align="center">企业</th>
					<th field="date1" width="120" align="center">申请日期</th>
					<th field="InputName" width="100" align="center">申请人</th>
					<th field="InpuDealer" width="120" align="center">本次备案总面积(亩)</th>
					<th field="PurchaseQuantity" width="120" align="center">状态</th>
					<th field="PurchasingAgent" width="120" align="center">驳回原因</th>
					<th field="PurchaseDate" width="80" align="center">审核人</th>
					<th field="view" width="140" align="center">审核时间</th>
					<th field="spstatus" width="0" align="center" hidden='true'></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="specLandReg" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${specLandReg.id}</td>
					<td height="30" align="center" nowrap><a href="javascript:void(0)" onclick="detail('${specLandReg.applyBatchNo}');">${specLandReg.applyBatchNo}</a></td>
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
					<td height="30" align="center" nowrap>
						<fmt:formatDate value="${specLandReg.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td height="30" align="center" nowrap>${specLandReg.status}</td>
				</tr>
				</c:forEach>
				<tr>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td>合计：</td>
					<td>${pageModel.data}</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</tr>	
			</tbody>
		</table>
	</div>
	<div id="addDialog"></div>
	<div id="flowDialog"></div>
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
	        checkOnSelect:false,
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
	
	
	function closeEdiDialog()
	{
		$('#userEditDlg').dialog('close');
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
		var year = $('#year').combobox('getValue');
		var companyCode = $('#companyCode').combobox('getValue');
		var status = $('#status').combobox('getValue');
		var beginDate = $('#beginDate').val();
		var endDate = $('#endDate').val();
		
		var url = "${ctx}/specLandReg/view?id=" + id + "&gotoPage=2";
		url += "&year=" + year;
		url += "&companyCode=" + companyCode;
		url += "&status=" + status;
		url += "&beginDate=" + beginDate;
		url += "&endDate=" + endDate;
		 
		document.location.href = url;
	}
	function auditInput(){
		var rows = $('#data').datagrid('getSelections');
		var length = rows.length;
		if(length != 1 ){
			$.messager.alert('警告','请选择一条记录！','warning');
			return false;
		}
		if(rows[0].spstatus=='01'){
			$.messager.alert('警告','该土地备案信息尚未提交审核申请！','warning');
			return false;
		}
		if(rows[0].spstatus=='03'){
			$.messager.alert('警告','已审核通过的备案土地不可被再次审核！','warning');
			return false;
		}
		if(rows[0].spstatus=='04'){
			$.messager.alert('警告','已驳回申请的备案土地不可被再次审核！','warning');
			return false;
		}
		var id = rows[0].id;
		
	
		/*$('#addDialog').dialog({
		    title: '特殊土地备案审核',
		    width: 700,
		    height: 520,
		    closed: false,
		    cache: false,
		    href: '${ctx}/specLandReg/editInput?flag=audit&id='+id + "&t=" + (new Date()).getTime(),
		    modal: true
		}); */
		var url = "${ctx}/specLandReg/editInput?flag=audit&id="+id + "&t=" + (new Date()).getTime();
		url += getQueryCond();
		document.location.href = url;
	}
	
	function getQueryCond(){
		var page = $("#page").val();
		var pageSize = $("#pageSize").val();
		var year = $("#year").combobox("getValue");
		var companyCode = $("#companyCode").combobox("getValue");
		var beginDate = $("#beginDate").val();
		var endDate = $("#endDate").val();
		var status = $("#status").val();
		
		var str = "";
		str += "&page=" + page;
		str += "&pageSize=" + pageSize;
		str += "&year=" + year;
		str += "&companyCode=" + companyCode;
		str += "&beginDate=" + beginDate;
		str += "&endDate=" + endDate;
		str += "&status=" + status;
		
		return str;
	}
	
	function form_check(){
		var beginDate =$.trim($("#beginDate").datebox('getValue'));
		var endDate =$.trim($("#endDate").datebox('getValue'));
		if(!dateCompare(beginDate,endDate)){
			$.messager.alert('警告','申请日期开始日不能大于结束日！','warning');
			return false;
		}
		showLoading();
		$('#inputForm').submit();
	}
	
	/**
	* 点击流水号显示操作日志
	*/
	function detail( batchNo ){
		$('#flowDialog').dialog({
		    title: '操作记录',
		    width: 600,
		    height: 400,
		    closed: false,
		    cache: false,
		    href: '${ctx}/landLog/list' + "?landLogType=SPEC&batchNo=" + batchNo,
		    modal: true
		});
	}
</script>
</body>
</html>
