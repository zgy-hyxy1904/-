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
						<input class="easyui-textbox" type="text" id="legalPerson" name="legalPerson" value="${company.legalPerson}" style="width:200px;" editable="false"></input></td>
	    			</td>
    			</tr>
    			<tr>
	    			<td align="right">法人身份证：</td>
	    			<td>
						<input class="easyui-textbox" type="text" id="legalPersonID" name="legalPersonID" value="${company.legalPersonID}" style="width:200px;" editable="false"></input></td>
	    			</td>
    			</tr>
    			<tr>
	    			<td align="right">企业地址：</td>
	    			<td>
	    				<input class="easyui-textbox" type="text" id="address" name="address" value="${company.address}" style="width:200px;" editable="false"></input></td>
	    			</td>
    			</tr>
    			<tr>
	    			<td align="right">注册日期：</td>
	    			<td>
	    				<input comboname="registerDate" textboxname="registerDate" class="easyui-datebox datebox-f combo-f textbox-f" value="${company.registerDate}" data-options="required:true,showSeconds:false" style="width: 200px;" editable="false" readonly>
	    			</td>
    			</tr>
    			<tr>
	    			<td align="right">联系人：</td>
	    			<td>
	    				<input class="easyui-textbox" type="text" id="connectName" name="connectName" value="${company.connectName}" registerDate style="width:200px;" editable="false"></input></td>
	    			</td>
    			</tr>
    			<tr>
	    			<td align="right">联系人电话：</td>
	    			<td>
	    				<input class="easyui-textbox" type="text" id="connectPhone" name="connectPhone" value="${company.connectPhone}" style="width:200px;" editable="false"></input></td>
	    			</td>
	    		</tr>
    			<tr>
	    			<td align="right">企业类型：</td>
	    			<td>
						<simple:select id="companyType" name="companyType" codeKey="CompanyType" entityName="commonData" value="${company.companyType}" readOnly="true" width="200"/>
	    			</td>
	    		</tr>
    			<tr>
	    			<td align="right">资质说明：</td>
	    			<td heigth="30px" colspan="3">
	    				<textarea name="remark" rows="2" style="width:300px;" readonly>${company.remark}</textarea>
	    			</td>
    			</tr>
    			<tr height="55">
	    			<td colspan="2" align="center">
	    			<a href="javascript:void(0)" style="margin-left:50px;" class="easyui-linkbutton" style="margin-left:15px;" onclick="closeDialog()" data-options="iconCls:'icon-cancel'">取消</a>
	    			</td>
    			</tr>
			</table>
		</form>
	</div>
<script type="text/javascript">
function closeDialog(){
	$('#addDialog').dialog('close');
}
</script>
</body>
</html>