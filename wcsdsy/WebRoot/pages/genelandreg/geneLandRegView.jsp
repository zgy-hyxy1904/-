<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
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
			<form id="inputFrom" name="inputFrom" method="get" action="../geneLandReg/list">
			<fieldset id="queryBlock" class="fieldset_common_style">
			<table class="table_common_style">
				<tr>
					<td class="table_common_td_label_style">年度：</td>
					<td class="table_common_td_txt_style">
						${geneLandReg.year }
					</td>
					<td class="table_common_td_label_style">企业：</td>
					<td class="table_common_td_txt_style">
						${geneLandReg.companyName }
					</td>
				</tr>
				<tr>
					<td class="table_common_td_label_style">申请批次号：</td>
					<td class="table_common_td_txt_style">
						${geneLandReg.applyBatchNo}
					</td>
					<td class="table_common_td_label_style">审核状态：</td>
					<td class="table_common_td_txt_style">
						<simple:showName entityName="commonData" codeKey="GeneralRegistStatus" value="${geneLandReg.status}"></simple:showName>
					</td>
				</tr>
				<tr>
					<td class="table_common_td_label_style">审核人：</td>
					<td class="table_common_td_txt_style">
						${geneLandReg.auditor}
					</td>
					<td class="table_common_td_label_style">审核时间：</td>
					<td class="table_common_td_txt_style">
						<fmt:formatDate value="${geneLandReg.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
			</table>
			</fieldset>	
			<fieldset id="toolBlock" class="fieldset_common_style">
			<table>
				<tr>
					<td>
						<a href="#" class="easyui-linkbutton"  onclick="return reList();">
						<span class="l-btn-left">
							<span class="l-btn-text icon-cancel l-btn-icon-left">返回</span></span>
						</a>
					</td>
				</tr>				
			</table>
		</fieldset>
		<table id="geneLandData" class="easyui-datagrid" style="table-layout:fixed;border-collapse: collapse;">
			<thead> 
				<tr> 
					<!-- Modify By WolfSoul Begin -->
					<th field="contractorType" align="center" hidden="true"></th>
					<th field="contractorText" width="120" align="center" nowrap="nowrap">承包方类型</th>
					<th field="idType" align="center" hidden="true"></th>
					<th field="idTypeText" width="120" align="center" nowrap="nowrap">证件类型</th>
					<th field="contractorID" width="150" align="center" nowrap="nowrap">证件号码</th>
					<th field="contractorName" width="120" align="center" nowrap="nowrap">承包方</th>
					<th field="contractorTel" align="center" hidden="true"></th>
					<th field="cityCode" align="center" hidden="true"></th>
					<th field="townCode" align="center" hidden="true"></th>
					<th field="townText" width="120" align="center" nowrap="nowrap">所在乡</th>
					<th field="countryCode" align="center" hidden="true"></th>
					<th field="countryText" width="120" align="center" nowrap="nowrap">所在村</th>
					<th field="groupName" width="200" align="center" nowrap="nowrap">住址</th>
					<th field="zmj" width="130" align="center" nowrap="nowrap">承包总面积（亩）</th>
					<th field="yba" width="130" align="center" nowrap="nowrap">已备案面积（亩）</th>
					<th field="kba" width="130" align="center" nowrap="nowrap">可备案面积（亩）</th>
					<th field="archiveAcreage" width="130" align="center" nowrap>本次备案面积（亩）</th>
					<th field="operatorName" width="130" align="center" nowrap="nowrap">经办人</th>
					<th field="operatorDate" width="120" align="center" nowrap="nowrap">经办日期</th> 
					<!-- Modify By WolfSoul End -->
				</tr>
			</thead>
			<tbody id="dataBody">
				<c:forEach var="geneLandRegD" items="${list}" varStatus="status">
				<%-- Modify By WolfSoul Begin --%>
				<tr>
					<td height="30" align="center" nowrap>${geneLandRegD.contractorType}</td>
					<td height="30" align="center" nowrap>
						<simple:showName entityName="commonData" codeKey="ContractorType" value="${geneLandRegD.contractorType}"></simple:showName>
					</td>
					<td height="30" align="center" nowrap>${geneLandRegD.idType}</td>
					<td height="30" align="center" nowrap>
						<simple:showName entityName="commonData" codeKey="IDType" value="${geneLandRegD.idType}"></simple:showName>
					</td>
					<td height="30" align="center" nowrap>${geneLandRegD.contractorID}</td>
					<td height="30" align="center" nowrap>${geneLandRegD.contractorName}</td>
					<td height="30" align="center" nowrap>${geneLandRegD.contractorTel}</td>
					<td height="30" align="center" nowrap>${geneLandRegD.cityCode}</td>
					<td height="30" align="center" nowrap>${geneLandRegD.townCode}</td>
					<td height="30" align="center" nowrap><simple:showName entityName="areadevision" codeKey="town" value="${geneLandRegD.townCode}"></simple:showName></td>
					<td height="30" align="center" nowrap>${geneLandRegD.countryCode}</td>
					<td height="30" align="center" nowrap><simple:showName entityName="areadevision" codeKey="country" value="${geneLandRegD.townCode},${geneLandRegD.countryCode}"></simple:showName></td>
					<td height="30" align="center" nowrap>${geneLandRegD.groupName}</td>
					<td height="30" align="center" nowrap>
						<f:formatNumber type="number" value="${geneLandRegD.zmj}" pattern="0.00" maxFractionDigits="2"/>
					</td>
					<td height="30" align="center" nowrap>
						<f:formatNumber type="number" value="${geneLandRegD.yba}" pattern="0.00" maxFractionDigits="2"/>
					</td>
					<td height="30" align="center" nowrap>
						<f:formatNumber type="number" value="${geneLandRegD.kba}" pattern="0.00" maxFractionDigits="2"/>
					</td>
					<td height="30" align="center" nowrap>
						<f:formatNumber type="number" value="${geneLandRegD.archiveAcreage}" pattern="0.00" maxFractionDigits="2"/>
					</td>
					<td height="30" align="center" nowrap>${geneLandRegD.operatorName}</td>
					<td height="30" align="center" nowrap>
						<fmt:formatDate value="${geneLandRegD.operatorDate}" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<%-- Modify By WolfSoul End --%>
				</c:forEach>		
			</tbody>
		</table>
		</form>
	</div>
	<div id="addDialog"></div>
	
<script type="text/javascript">
$(document).ready(function(){
	 var winHeight = $(window).height();
     var queryBlockHeight = $("#queryBlock").height();
     var toolBlock = $("#toolBlock").height();
	//Modify By WolfSoul Begin
	$("#geneLandData").datagrid({ 
		height:winHeight -queryBlockHeight -toolBlock - 50,
        rownumbers: true,
        fitColumns: false
	});
	//Modify By WolfSoul End
});

function reList(){
	document.location.href = "${ctx}/geneLandReg/list?year=${year}&companyCode=${companyCode}&beginDate=${beginDate}&endDate=${endDate}&status=${status}&page=${page}&pageSize=${pageSize}";
}
</script>
</body>
</html>