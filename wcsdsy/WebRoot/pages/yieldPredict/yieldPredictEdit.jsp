<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<title>添加预报-五常优质水稻溯源监管平台</title>
<link rel="stylesheet" type="text/css" href="${ctx}/style/index.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/color.css">
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript" src="${ctx}/js/form2js.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">	
		<form id="addUserFrom" class="easyui-form" method="post" data-options="novalidate:true">
			<fieldset id="queryBlock" class="fieldset_common_style">
				<table class="table_common_style">
					<tr>
		    			<td class="table_common_td_label_style">产品名称：</td>
		    			<td class="table_common_td_txt_style">
		    				<input type="hidden" id="companyCode" value="${companyCode}" />
		    				<input type="hidden" id="rowIndex" value="${rowIndex}" />
							<select id="productCode" name="productCode" class="easyui-combobox" editable="false" style="width:200px;height:25px">
			    				<option value="">请选择产品</option>
			    				<c:forEach var="product" items="${products}" varStatus="status">
			    					<option value="${product.productCode}|${product.weight}|${product.unit}" <c:if test="${product.productCode == productCode}">selected</c:if>  >${product.productName}</option>
			    				</c:forEach>
			    			</select>
						</td>
	    			</tr>
	    			<tr>
		    			<td class="table_common_td_label_style">数量：</td>
		    			<td class="table_common_td_txt_style">
							<input class="easyui-numberbox" type="text" id="num" name="num" style="width:200px;height:25px" data-options="min:0,max:99999999999" ></input>
		    				<span class="span_common_mustinput_style">*</span>
		    			</td>
	    			</tr>
	    			<tr height="50">
		    			<td colspan="2" align="center">
			    			<a href="#" class="easyui-linkbutton" onclick="save()" data-options="iconCls:'icon-save'">保存</a>
			    			<a href="#" style="margin-left:50px;" class="easyui-linkbutton" style="margin-left:15px;" onclick="closeialog()" data-options="iconCls:'icon-cancel'">关闭</a>
		    			</td>
	    			</tr>
				</table>
			</fieldset>
		</form>
	</div>
<script type="text/javascript">
$(document).ready(function(){
    var rowIndex = $("#rowIndex").val();
    var row = $('#data').datagrid('getData').rows[rowIndex];
    $('#num').textbox({"value":row.num});
});

function closeialog()
{
	$('#addDialog').dialog('close');
}

function save()
{
	var productCode = $('#productCode').combobox('getValue');
	var num = $.trim($("#num").val());
	if(productCode == '') {
	  $.messager.alert('警告','请选择产品。','warning');
		return false;
	}
    if(num == ''){
	    $.messager.alert('警告','请输入数量。','warning');
	    return false;
    }	
	if(!isInt(num)){
	  $.messager.alert('警告','数量只能输入整数。','warning');
		return false;
	}

	var productName = $('#productCode').combobox('getText');
	var prtArr = productCode.split("|");
	productCode = prtArr[0];
	var unitWeight = parseFloat(prtArr[1]);
	var weight = parseInt(num) * unitWeight * 2;
	var unit = prtArr[2];
	
	var rowIndex = parseInt($("#rowIndex").val());
	$('#data').datagrid('updateRow', {
		index: rowIndex,
		row: {
			productName: productName,
			productCode : productCode,
			unitWeight: unitWeight,
			unit :unit,
			num : num,
			weight : weight
		}
	});
	updateCurrentWeight();
	$.messager.alert('提示','修改成功。','info');
	closeDialog();
}
</script>
</body>
</html>