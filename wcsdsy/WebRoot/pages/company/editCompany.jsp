<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
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
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:0 10px;">	
		<form id="editcompanyform" class="easyui-form" method="post" data-options="novalidate:true">
			<table cellpadding="6" style="text-align:center;">
				<tr>
	    			<td align="right">企业编码：</td>
	    			<td><input class="easyui-textbox" type="text" id=companyCode name="companyCode" value="${company.companyCode}" style="width:200px;" editable="false"></input></td>
	    			<input type="hidden" id="id" name="id" value="${company.id}" />
    			</tr>
    			<tr>
	    			<td align="right">企业名称：</td>
	    			<td><input class="easyui-textbox" type="text" id="companyName" name="companyName" value="${company.companyName}" style="width:200px;" editable="false"></input></td>
    			</tr>
    			<tr>
	    			<td align="right">企业法人：</td>
	    			<td>
						<input class="easyui-textbox" type="text" id="legalPerson" name="legalPerson" value="${company.legalPerson}" style="width:200px;"></input></td>
	    			</td>
    			</tr>
    			<tr>
	    			<td align="right">法人身份证：</td>
	    			<td>
						<input class="easyui-textbox" type="text" id="legalPersonID" name="legalPersonID" value="${company.legalPersonID}" style="width:200px;"></input></td>
	    			</td>
    			</tr>
    			<tr>
	    			<td align="right">企业地址：</td>
	    			<td>
	    				<input class="easyui-textbox" type="text" id="address" name="address" value="${company.address}" style="width:200px;"></input></td>
	    			</td>
    			</tr>
    			<tr>
	    			<td align="right">注册日期：</td>
	    			<td>
	    				<input type="text" id="registerDate" name="registerDate" class="easyui-datebox datebox-f combo-f textbox-f" value="${company.registerDate}" data-options="required:true,showSeconds:false" style="width: 200px;" editable="false">
	    			</td>
    			</tr>
    			<tr>
	    			<td align="right">联系人：</td>
	    			<td>
	    				<input class="easyui-textbox" type="text" id="connectName" name="connectName" value="${company.connectName}" registerDate style="width:200px;"></input></td>
	    			</td>
    			</tr>
    			<tr>
	    			<td align="right">联系人电话：</td>
	    			<td>
	    				<input class="easyui-textbox" type="text" id="connectPhone" name="connectPhone" value="${company.connectPhone}" style="width:200px;"></input></td>
	    			</td>
	    		</tr>
    			<tr>
	    			<td align="right">企业类型：</td>
	    			<td>
						<simple:select id="companyType" name="companyType" codeKey="CompanyType" entityName="commonData" value="${company.companyType}" width="200"/>
	    			</td>
	    		</tr>
    			<tr>
	    			<td align="right">资质说明：</td>
	    			<td heigth="30px" colspan="3">
	    				<textarea name="remark" rows="2" style="width:300px;">${company.remark}</textarea>
	    			</td>
    			</tr>
    			<tr height="55">
	    			<td colspan="4" align="center">
	    			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="formCheck()" data-options="iconCls:'icon-save'">保存</a>
	    			<a href="javascript:void(0)" style="margin-left:50px;" class="easyui-linkbutton" style="margin-left:15px;" onclick="closeDialog()" data-options="iconCls:'icon-cancel'">取消</a>
	    			</td>
    			</tr>
			</table>
		</form>
	</div>
<script type="text/javascript">
function formCheck(){
	var companyCode = $.trim($("#companyCode").textbox('getValue'));
	var companyName = $.trim($("#companyName").textbox('getValue'));
	var legalPerson = $.trim($("#legalPerson").textbox('getValue'));
	var legalPersonID = $.trim($("#legalPersonID").textbox('getValue'));
	var address = $.trim($("#address").textbox('getValue'));
	var registerDate = $.trim($("#registerDate").datebox('getValue'));
	var connectName = $.trim($("#connectName").textbox('getValue'));
	var connectPhone = $.trim($("#connectPhone").textbox('getValue'));
	var companyType = $('#companyType').combobox('getValue');
	var remark = $('#remark').val();
	if(legalPerson == ''){
		$.messager.alert('警告','企业法人不能为空。','warning');
		return false;	
	}else if(legalPerson.length > 20){
		$.messager.alert('警告','企业法人超出最大长度。','warning');
		return false;
	}
	if(legalPersonID == ''){
		$.messager.alert('警告','法人身份证不能为空。','warning');
		return false;	
	}else if(legalPersonID.length > 20){
		$.messager.alert('警告','法人身份证超出最大长度。','warning');
		return false;
	}
	if(address == ''){
		$.messager.alert('警告','注册地址不能为空。','warning');
		return false;	
	}
	if(connectName == ''){
		$.messager.alert('警告','联系人不能为空。','warning');
		return false;	
	}
	if(connectPhone == ''){
		$.messager.alert('警告','联系人电话不能为空。','warning');
		return false;
	}
	if(!isTel(connectPhone)){
		$.messager.alert('警告','请填写正确的联系人电话。','warning');
		return false;
	}
	if(companyType == ''){
		$.messager.alert('警告','请选择企业类型。','warning');
		return false;
	}
	var a = $('#editcompanyform').toObject();
	Public.ajaxPost('${ctx}/company/editCompany', JSON.stringify(a), function(e) {
		if (200 == e.status) {
			$.messager.alert('提示','操作成功。','info');
			$('#addDialog').window('close');
			$('#companylistform').submit();
		} else {
			parent.parent.Public.tips({
				type : 1,
				content : "失败！" + e.msg
			});
		}
	});
}

function closeDialog(){
	$('#addDialog').dialog('close');
}
</script>
</body>
</html>