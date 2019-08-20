<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<%--c:set var="companyCode" value="${specLandReg.companyCode }" />
<c:set var="yearCode" value="${YearCode }" / --%>
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
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
	<script>
		var root = "${ctx}";
	</script>
	<script type="text/javascript" src="${ctx}/js/upload_view.js"></script>

</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">
		<form id="editForm" name="editForm" method="post" action="../specLandReg/list">
			<fieldset class="fieldset_common_style">
				<input type="hidden" name="id" id="id" value="${specLandReg.id}"/>
				<input type="hidden" name="status" id="statusEdit" value="01"/>
				<input type="hidden" name="detailId" id="detailId" value="${detailId}"/>
				<table>
					<tr>
						<td>
							<a href="#" class="easyui-linkbutton"  onclick="return retList();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-cancel l-btn-icon-left">返回</span></span>
							</a>
						</td>
					</tr>
				</table>	
			</fieldset>
			<fieldset class="fieldset_common_style">
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_style">申请批次号：</td>
						<td colspan="3" class="table_common_td_txt_style">
							<input type="hidden" name="applyBatchNo" value="${specLandReg.applyBatchNo}">
							${specLandReg.applyBatchNo}
						</td>
					</tr>
					<tr> 
						<td class="table_common_td_label_style">年度：</td>
						<td class="table_common_td_txt_style">
							${specLandReg.year }
						</td>
						<td class="table_common_td_label_style">企业：</td>
						<td class="table_common_td_txt_style">
							${specLandReg.companyName }
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">承包方类型：</td>
						<td class="table_common_td_txt_style">
							<simple:showName entityName="commonData" codeKey="ContractorType" value="${specLandReg.contractorType}"></simple:showName>
						</td>
						<td class="table_common_td_label_style">证件类型：</td>
						<td class="table_common_td_txt_style">
							<simple:showName entityName="commonData" codeKey="IDType" value="${specLandReg.idType}"></simple:showName>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">承包方：</td>
						<td class="table_common_td_txt_style">
							${specLandReg.contractorName }
						</td>
						<td class="table_common_td_label_style">证件号码：</td>
						<td class="table_common_td_txt_style">
							${specLandReg.contractorID }
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">住址：</td>
						<td colspan="3" class="table_common_td_txt_style">
							<simple:showName entityName="areadevision" codeKey="town" value="${specLandReg.townCode}"></simple:showName>
							<simple:showName entityName="areadevision" codeKey="country" value="${specLandReg.townCode},${specLandReg.countryCode}"></simple:showName>
							${specLandReg.groupName }
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_style">土地类型：</td>
						<td class="table_common_td_txt_style">
							<simple:showName entityName="commonData" codeKey="PlowlandType" value="${landType}"></simple:showName>
						</td>
						<td class="table_common_td_label_style">测量（亩）：</td>
						<td class="table_common_td_txt_style">
							<f:formatNumber type="number" value="${actualMu}" pattern="0.00" maxFractionDigits="2"/>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">土地类别：</td>
						<td class="table_common_td_txt_style">
							<simple:showName entityName="commonData" codeKey="PlowlandClass" value="${landClass}"></simple:showName>
						</td>
						<td class="table_common_td_label_style">地块位置：</td>
						<td class="table_common_td_txt_style">
							<simple:showName entityName="areadevision" codeKey="town" value="${townCodeDetail}"></simple:showName>
							<simple:showName entityName="areadevision" codeKey="country" value="${townCodeDetail},${countryCodeDetail}"></simple:showName>
							${groupNameDetail }
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_style">本次备案面积（亩）：</td>
						<td class="table_common_td_txt_style">
							<f:formatNumber type="number" value="${specLandReg.archiveAcreage}" pattern="0.00" maxFractionDigits="2"/>
						</td>
						<td class="table_common_td_label_style">经办人：</td>
						<td class="table_common_td_txt_style">
							${specLandReg.operatorName }
						</td>
						<td class="table_common_td_label_style">经办日期：</td>
						<td class="table_common_td_txt_style">
							<fmt:formatDate value="${specLandReg.operatorDate}" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_comment_style">情况说明：</td>
						<td colspan="5" class="table_common_td_txt_style">
							<textarea class="easyui-textbox" rows="3" id="description" name="description" cols="47" disabled style="font-size:12px;height:80px" data-options="multiline:true">${specLandReg.description }</textarea>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<legend>举证资料列表</legend>
				<table class="table_common_style">
					<tr>
						<td>
							<table id="imgList" height="230px" class="easyui-datagrid" striped="true" singleSelect="true">
								<thead>
									<tr>
										<th field="originalName" width="50%" align="center">举证凭证</td>
										<th field="fileInfo" width="30%" align="center">说明</td>
									</tr>
								</thead>
							</table>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<legend>举证资料预览</legend>
				<table id="fileDiv" class="table_common_style">
					<tr>
						<td>
							<simple:imgView outerDivId="imgPriviewOuter" innerDivId="imgPriviewInner" imgInfoMaps="imgPathAndInfoMaps" width="420" height="500"></simple:imgView>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
	<div id="bhDialog"></div>
	<div id="uploadDialog"></div>
	
<script type="text/javascript">
	$(document).ready(function(){
		var id = $("#id").val();
		if(id != ""){
			showFileList("04", id, '举证资料', 'imgPriviewOuter', 'imgPriviewInner' );
		}
		
		//动态调整预览图片位置
		var width = $("#fileDiv").width();
		var picWidth = $("#imgPriviewInner").width();
		var paddingLeft = (width - picWidth)/2 + "px";
		$("#fileDiv").css("padding-left", paddingLeft);
	});

	function retList(){
		if('${gotoPage}' == 1){
			document.location.href = "${ctx}/specLandReg/list?year=${year}&companyCode=${companyCode}&beginDate=${beginDate}&endDate=${endDate}&status=${status}&page=${page}&pageSize=${pageSize}";
		}else if('${gotoPage}' == 2){
			document.location.href = "${ctx}/specLandReg/listquery?year=${year}&companyCode=${companyCode}&beginDate=${beginDate}&endDate=${endDate}&status=${status}&page=${page}&pageSize=${pageSize}";
		}
	}
	function showVideoList(o, d){}
</script>
</body>
</html>