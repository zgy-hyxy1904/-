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
	
	<form id="yieldRegform" name="yieldRegform" method="post" action="${ctx}/yieldPredict/reg?flag=2">
		<fieldset id="queryBlock" class="fieldset_common_style">
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_query_style">年度：</td>
						<td class="table_common_td_txt_query_style editableFalse">
							<simple:select id="year" name="year" entityName="yearcode" value="${yieldPredictModel.year}" width="170" canEdit="false"/>
							<span class="span_common_mustinput_style">*</span>
						</td>
						<td  class="table_common_td_label_query_style">企业：</td>
						<td class="table_common_td_txt_query_style">
							<simple:select id="companyCode" name="companyCode" value="${yieldPredictModel.companyCode}" codeKey="02" entityName="company" width="200" hasPleaseSelectOption="${!sessionScope.isCompanyUser}" readOnly="${sessionScope.isCompanyUser}" canEdit="${!sessionScope.isCompanyUser}"/>
							<span class="span_common_mustinput_style">*</span>
						</td>
						<td align="right" valign="bottom">
	 	                 <a href="#" class="easyui-linkbutton" onclick="return onSubmit();">
	                       		<span class="l-btn-left"><span class="l-btn-text icon-search l-btn-icon-left">查询</span></span>
	                       	</a>                      
	             		</td>
					</tr>
				</table>
			
		</fieldset>
		<fieldset id="pingguBlock"  class="fieldset_common_style">
			<legend>统计信息</legend>
			<table class="table_common_style">
				<tr>
					<td class="table_common_td_label_style">评估总产量（斤）：</td>
					<td class="table_common_td_txt_style">
						<input id="evalYield" value="${evalYield}" class="easyui-textbox" style="width:120px;height:25px" readonly>
					</td>
					<td class="table_common_td_label_style">收粮量（斤）：</td>
					<td class="table_common_td_txt_style">
						<input id="buyValue" value="${grainEval}" class="easyui-textbox" style="width:200px;height:25px" readonly>
					</td>
				</tr>
				<tr>
					<td class="table_common_td_label_style">总产量（斤）：</td>
					<td colspan="3" class="table_common_td_txt_style">
						<input id="maxPredictYield" value="${maxPredictYield}" class="easyui-textbox" style="width:120px;height:25px" readonly>
					</td>
				</tr>
				<tr>
					<td class="table_common_td_label_style">已预报总产量（斤）：</td>
					<td class="table_common_td_txt_style">
						<input id="predictedYield" value="${predictedYield}" class="easyui-textbox" style="width:120px;height:25px" readonly>
					</td>
					<td class="table_common_td_label_style">本次可预报产量（斤）：</td>
					<td class="table_common_td_txt_style">
						<input id="restPredictYield" value="${restPredictYield}" class="easyui-textbox" style="width:200px;height:25px" readonly>
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset id="yubaoBlock"  class="fieldset_common_style">
			<legend>预报信息</legend>
			<table class="table_common_style">
				<tr>
					<td class="table_common_td_label_style">预报日期：</td>
					<td class="table_common_td_txt_style editableFalse">
						<input class="easyui-datebox" editable="false" name="predictDate" id="predictDate" value="${yieldPredictModel.predictDate}" data-options="required:false,showSeconds:false" style="width:120px" />
						<span class="span_common_mustinput_style">*</span>
					</td>
					<td class="table_common_td_label_style">本次预报产量（斤）：</td>
					<td class="table_common_td_txt_style">
						<input id="currentValue" class="easyui-textbox" style="width:120px;height:25px" value="0.00" readonly>
					</td>
				</tr>
			</table>
		</fieldset>
		</form>
		<fieldset id="toolBlock"  class="fieldset_common_style">
			<table>
				<tr>
					<td>
						<a href="#" class="easyui-linkbutton" onclick="addPredict()">
							<span class="l-btn-left">
								<span class="l-btn-text icon-add l-btn-icon-left">添加</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="editPredict()">
							<span class="l-btn-left">
								<span class="l-btn-text icon-edit l-btn-icon-left">修改</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="deletePredict()">
							<span class="l-btn-left">
								<span class="l-btn-text icon-remove l-btn-icon-left">删除</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="savePredict()">
							<span class="l-btn-left">
								<span class="l-btn-text icon-save l-btn-icon-left">保存</span>
							</span>
						</a>
					</td>
				</tr>
			</table>
		</fieldset>
		
		<table id="data" class="easyui-datagrid" striped="true" checkbox="true" singleSelect="false" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="chk" width="30" align="center" checkbox="true"></th>
					<th field="productName" width="250" align="center">产品名称</th>
					<th field="productCode" hidden="true"></th>
					<th field="unitWeight" width="150" align="center">单位净重</th>
					<th field="unit" width="150" align="center">单位</th>
					<th field="num" width="150" align="center">数量</th>
					<th field="weight" width="150" align="center">总重量（斤）</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="addDialog" style="overflow:hidden;"></div>
	
<script type="text/javascript">
$(document).ready(function(){

  var winHeight = $(window).height();
  var queryBlockHeight = $("#queryBlock").height();
  var pingguBlock = $("#pingguBlock").height();
  var yubaoBlock = $("#yubaoBlock").height();
  var toolBlock = $("toolBlock").height();
  
	$("#data").datagrid({ 
	    height:winHeight -queryBlockHeight -pingguBlock-yubaoBlock-toolBlock-112,
		pagination: true,
        rownumbers: true,
        fitColumns: false,
        //fit: true,
        pageList: [15,20,25],
        pagePosition: "top"
	});
	
	var pagger = $('#data').datagrid('getPager');  
	$(pagger).pagination({
		total:0,
		pageNumber: parseInt($("#page").val()),
		showPageList: true,
		showRefresh:false,
	    onSelectPage: function(pageNumber, pageSize){
	    	$('#page').val(pageNumber);
	    	$('#pageSize').val(pageSize);
	    }
	});
	
	$('#year').validatebox({
        required: true
    });
    $('#companyCode').validatebox({
        required: true
    });
    
});

function addPredict(){
	var companyCode = $('#companyCode').combobox('getValue');
	if(companyCode == ''){
		$.messager.alert('警告','请选择企业。','warning');
		return false;
	}
	$('#addDialog').dialog({
	    title: '添加预报',
	    width: 400,
	    height: 200,
	    closed: false,
	    cache: false,
	    href: '${ctx}/yieldPredict/add?companyCode='+companyCode,
	    modal: true
	});
}

function editPredict(){
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length == 0)
	{
		$.messager.alert('警告','至少选择一条记录。','warning');
		return false;
	}
	if(length > 1)
	{
		$.messager.alert('警告','只能选择一条记录。','warning');
		return false;
	}
	var rowIndex = $('#data').datagrid('getRowIndex',$('#data').datagrid('getSelected'));
	var companyCode = $('#companyCode').combobox('getValue');
	var row = $('#data').datagrid('getData').rows[rowIndex];
	var productCode = row.productCode;
	$('#addDialog').dialog({
	    title: '修改预报',
	    width: 400,
	    height: 200,
	    closed: false,
	    cache: false,
	    href: '${ctx}/yieldPredict/edit?rowIndex='+rowIndex+"&companyCode="+companyCode+"&productCode="+productCode,
	    modal: true
	});
}

function deletePredict(){
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length == 0){
	  $.messager.alert('警告','至少选择一条记录。','warning');
		return false;
	}

    $.messager.confirm("确认", "您确认删除选定的记录吗？", function (deleteAction) {
	    if (deleteAction) {
	      var ids = [];
	      var length = rows.length;
	      for(var i=0; i<rows.length; i++){
	          var index = $('#data').datagrid('getRowIndex',rows[i]);
	          $('#data').datagrid('deleteRow',index);
	      }
	      updateCurrentWeight();
	    }
	});
}

function savePredict(){
	var rows = $('#data').datagrid('getRows');
	var length = rows.length;
	if(length == 0){
		$.messager.alert('警告','请先添加预报。','warning');
		return false;
	}
	var params = "";
	var thisPredictWeight = 0;
	for(var i=0; i<rows.length; i++){
		params += rows[i].productCode + "," + rows[i].num + "," + rows[i].weight + ";";
		thisPredictWeight += parseFloat(rows[i].weight);
	}
	var restPredictYield = parseFloat($("#restPredictYield").val());
	if(thisPredictWeight > restPredictYield){
		$.messager.alert('警告','已超出可预报重量。','warning');
		return false;
	}
	if(params.length>0) params = params.substring(0,params.length-1);
	var year = $("#year").val();
	var companyCode = $("#companyCode").datebox('getValue');
	var predictDate = $('#predictDate').datebox('getValue');
	if(predictDate == ''){

		$.messager.alert('警告','预报日期不能为空。','warning');
		return false;
	}
	showLoading();
	Public.ajaxGet('${ctx}/yieldPredict/save', {year:year,companyCode:companyCode, predictDate:predictDate,params : params}, function(e) {
		hideLoading();
		if (200 == e.status) {
			document.location.href = "${ctx}/yieldPredict/reg";
		} else {
			$.messager.alert('错误','保存失败！'+ e.msg,'error');
		}
	});
}

function updateCurrentWeight(){
	var rows = $('#data').datagrid('getRows');
	var currentWeight = 0.00;
	for(var i=0; i<rows.length; i++){
		currentWeight += rows[i].weight;
	}
	$("#currentValue").textbox('setValue', currentWeight.toFixed(2));
}

function onSubmit(){
	var companyCode = $('#year').combobox('getValue');
	if(companyCode == ''){
		$.messager.alert('警告','请选择年度。','warning');
		return false;
	}
	
	var companyCode = $('#companyCode').combobox('getValue');
	if(companyCode == ''){
		$.messager.alert('警告','请选择企业。','warning');
		return false;
	}
	showLoading();
	$('#yieldRegform').submit();
}
</script>
</body>
</html>