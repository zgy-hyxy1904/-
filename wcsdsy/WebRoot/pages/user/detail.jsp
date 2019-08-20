<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<form id="addUserFrom" class="easyui-form" method="post" data-options="novalidate:true">
			<table cellpadding="0" cellspacing="0" class="table_ct_detail">
				<tr>
	    			<th width="20%" align="right">用户名:</th>
	    			<td width="30%" align="left" colspan="3">${user.userID}</td>
    			</tr>
    			<tr>
	    			<th width="20%" align="right">姓名:</th>
	    			<td width="30%" align="left">${user.userName}</td>
	    			<th width="20%" align="right">用户类型:</th>
	    			<td width="30%" align="left">
	    				<simple:showName entityName="commonData" codeKey="UserType" value="${user.userType}"></simple:showName>
	    			</td>
    			</tr>
    			<tr>
	    			<th align="right">单位名称:</th>
	    			<td align="left">
	    			<simple:showName entityName="company" value="${user.companyCode}"></simple:showName>
						
	    			</td>
	    			<th align="right">联系方式:</th>
	    			<td align="left">
						${user.tel}
	    			</td>
    			</tr>
    			<tr>
	    			<th align="right">邮箱:</th>
	    			<td align="left">
						${user.email}
	    			</td>
	    			<th align="right">角色:
	    			</th>
	    			<td align="left" id="role">
	    			</td>
    			</tr>
    			<tr>
	    			<th align="right">备注:</th>
	    			<td colspan="3" align="left">
	    				${user.remark}
	    			</td>
    			</tr>
    			<tr>
	    			<td colspan="4" align="center">
	    			<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:15px;" onclick="closeDialog()" data-options="iconCls:'icon-cancel'">关闭</a>
	    			</td>
    			</tr>
			</table>
		</form>
	</div>
<script type="text/javascript">
$(function(){
	var str ='${rolelist}';
	$('#role').text(getRoleTypeName(str));
});
function closeDialog(){
	$('#addDialog').dialog('close');
}

function getRoleTypeName(str){
	  eval("var json = " + str);
	  var roleType = "";
	  for(var i=0;i<json.length;i++){
	    if(json[i].selected == true || json[i].selected == 'true'){
	        if(roleType == ""){
	          roleType = json[i].desc;
	        } else {
	          roleType = roleType + ","+json[i].desc;
	        }
	    } 
	  }
	  return roleType;
	}
</script>
</body>
</html>