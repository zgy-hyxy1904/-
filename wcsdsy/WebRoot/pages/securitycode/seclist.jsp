<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>   
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>防伪码管理-五常优质水稻溯源监管平台</title>
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
			<form id="seclistform" name="seclistform" method="post" action="${ctx}/securityCode/seclist?flag=2">
			<table class="table_common_style">
				<tr>
					<td class="table_common_td_label_query_style">年度：</td>
					<td class="table_common_td_txt_query_style">
						<simple:select id="year" name="year" entityName="yearcode" value="${securityCodeModel.year}" width="170" canEdit="false"/>
						<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
						<input type="hidden" id="page" name="page" value="${pageModel.page}">
						<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
					</td>
					<td class="table_common_td_label_query_style">企业名称：</td>
					<td class="table_common_td_txt_query_style">
						<simple:select id="companyCode" name="companyCode" value="${companyCode}" codeKey="02" entityName="company" width="200" hasPleaseSelectOption="${!sessionScope.isCompanyUser}" readOnly="${sessionScope.isCompanyUser}" canEdit="${!sessionScope.isCompanyUser}"/>
					</td>
				</tr>
				<tr>
					<td class="table_common_td_label_query_style">生产日期：</td>
             		<td colspan="2" class="table_common_td_txt_query_style">
             			<input name="produceDate" id="produceDate" class="easyui-datebox" value="${securityCodeModel.produceDate}" data-options="required:false,showSeconds:false" style="width:120px" editable="false"/>
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
			<table class="table_common_style">
				<tr>
					<td class="table_common_td_label_style">总产量（斤）：</td>
					<td class="table_common_td_txt_style">
						<input id="evalYield" value="${evalYield}" class="easyui-textbox" style="width:100px;height:25px" readonly></td>
					<td class="table_common_td_label_style">已预报产量（斤）：</td>
					<td class="table_common_td_txt_style">
						<input id="predictedYield" value="${predictedYield}" class="easyui-textbox" style="width:100px;height:25px" readonly></td>
					<td class="table_common_td_label_style">已激活产量（斤）：</td>
					<td class="table_common_td_txt_style">
						<input id=sameleYield value="${sampleActYield}" class="easyui-textbox" style="width:100px;height:25px" readonly></td>
				</tr>
				<tr>
					<td class="table_common_td_label_style">未申请产量（斤）：</td>
					<td class="table_common_td_txt_style">
						<input id="noApplyCodeCount" value="${noApplyCodeCount}" class="easyui-textbox" style="width:100px;height:25px" readonly></td>
					<td class="table_common_td_label_style">已申请数量：</td>
					<td class="table_common_td_txt_style">
						<input id="applyCodeCount" value="${applyCodeCount}" class="easyui-textbox" style="width:100px;height:25px" readonly></td>
					<td class="table_common_td_label_style">已激活数量：</td>
					<td class="table_common_td_txt_style">
						<input id="activateCodeCount" value="${activateCodeCount}" class="easyui-textbox" style="width:100px;height:25px" readonly></td>
				</tr>
			</table>
		</fieldset>
		<table id="data" class="easyui-datagrid" striped="true" checkbox="true" singleSelect="true" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="year" width="70" align="center">年度</th>
					<th field="companyCode" width="300" align="center">企业名称</th>
					<th field="batchNo" width="120" align="center">批次</th>
					<th field="productName" width="200"  align="center">产品名称</th>
					<th field="productNum" width="80" align="center">产品数量</th>
					<th field="weight" width="120" align="center">激活产量（斤）</th>
					<th field="useDate" width="150" align="center">生产日期</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="quality" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${quality.Year}</td>
					<td height="30" align="center" nowrap><simple:showName entityName="company" value="${quality.CompanyCode}"></simple:showName></td>
					<td height="30" align="center" nowrap>${quality.BatchNo}</td>
					<td height="30" align="center" nowrap>${quality.ProductName}</td>
					<td height="30" align="center" nowrap>${quality.ProductNum}</td>
					<td height="30" align="center" nowrap>${quality.TotalWeight}</td>
					<td height="30" align="center" nowrap><fmt:formatDate value="${quality.ProduceDate}" pattern="yyyy-MM-dd"/></td>
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
  var toolBlockHeight =$("#toolBlock").height();
	$("#data").datagrid({ 
	  height:winHeight -queryBlockHeight - toolBlockHeight -50,
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
	    	$('#seclistform').submit();
	    }
	});
	
});

function form_check()
{

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
	$('#seclistform').submit();
}
</script>
</body>
</html>