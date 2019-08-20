<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<%--c:set var="companyCode" value="${landRegChange.companyCode }" />
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
		<form id="editForm" name="editForm" method="post" action="../landChange/list">
			<fieldset class="fieldset_common_style">
				<input type="hidden" name="id" id="id" value="${landRegChange.id}"/>
				<input type="hidden" name="status" id="statusEdit" value="01"/>
				<input type="hidden" name="detailId" id="detailId" value="${detailId}"/>
				<table style="width: 100%" align="center">
					<tr width="100%" >
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
				<table border="0"  width="100%">
					<tr>
						<td class="table_common_td_label_style">申请批次号：</td>
						<td colspan="3" class="table_common_td_txt_style">
							<input type="hidden" name="applyBatchNo" value="${landRegChange.applyBatchNo}">
							${landRegChange.applyBatchNo}
						</td>
					</tr>
					<tr> 
						<td class="table_common_td_label_style">年度：</td>
						<td class="table_common_td_txt_style">
							${landRegChange.year }
						</td>
						<td class="table_common_td_label_style">企业：</td>
						<td class="table_common_td_txt_style">
							${landRegChange.companyName }
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">证件类型：</td>
						<td class="table_common_td_txt_style">
							<simple:showName entityName="commonData" codeKey="idType" value="${landRegChange.idType}"></simple:showName>
						</td>
						<td class="table_common_td_label_style">证件号码：</td>
						<td class="table_common_td_txt_style">
							${landRegChange.contractorID }
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<table border="0"  width="100%">
					<tr>
						<td class="table_common_td_label_style">承包方：</td>
						<td class="table_common_td_txt_style">
							${landRegChange.contractorName }
						</td>
						<td class="table_common_td_label_style">联系方式：</td>
						<td class="table_common_td_txt_style">
							${landRegChange.contractorTel }
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">住址：</td>
						<td colspan="3" class="table_common_td_txt_style">
							<simple:showName entityName="areadevision" codeKey="town" value="${landRegChange.townCode}"></simple:showName>
							<simple:showName entityName="areadevision" codeKey="country" value="${landRegChange.townCode},${landRegChange.countryCode}"></simple:showName>
							${landRegChange.address }
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<table style="width: 100%" align="center">
					<tr>
						<td class="table_common_td_label_style">类型选择：</td>
						<td colspan="5" class="table_common_td_txt_style">
							<c:if test="${landRegChange.geneRegistType == '1' }">
								<input type="checkbox" id="geneLandReg" checked disabled="true" >普通备案
								&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;
							</c:if>			
							<c:if test="${landRegChange.geneRegistType == '0' }">
								<input type="checkbox" id="geneLandReg" disabled="true" >普通备案
								&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;
							</c:if>				
							<c:if test="${landRegChange.specRegistType == '1' }">
                                 <input  type="checkbox" id="specLandReg" checked  disabled="true">特殊备案
							</c:if>	
							<c:if test="${landRegChange.specRegistType == '0' }">
                                 <input  type="checkbox" id="specLandReg" disabled="true">特殊备案
							</c:if>	
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">申请人：</td>
						<td class="table_common_td_txt_style">
							${landRegChange.applicant }
						</td>
						<td class="table_common_td_label_style">联系方式：</td>
						<td class="table_common_td_txt_style">
							${landRegChange.applicantTel}
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_comment_style">变更原因：</td>
						<td colspan="5" class="table_common_td_txt_style">
							<textarea class="easyui-textbox" rows="3" id="description" name="description" cols="47" disabled style="font-size:12px;height:80px" data-options="multiline:true">${landRegChange.changeReason }</textarea>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<table style="width: 100%" align="center">
					<tr>
						<td width="10%" align="left">举证资料：</td>
						<td colspan="3" align="left">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="4" valign="top">
							<table id="imgList" height="230px" class="easyui-datagrid" striped="true" singleSelect="true">
								<thead>
									<tr>
										<th field="originalName" width="50%" align="center">采购凭证</td>
										<th field="fileInfo" width="30%" align="center">说明</td>
										<!-- th field="op" width="20%" align="center">操作</td -->
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<legend>举证资料预览</legend>
				<div style="width: 100%;margin-left:25%" align="center">
					<simple:imgView outerDivId="imgPriviewOuter" innerDivId="imgPriviewInner" imgInfoMaps="imgPathAndInfoMaps" width="450" height="500"></simple:imgView>
				</div>
			</fieldset>
		</form>
	</div>
	<div id="bhDialog"></div>
	<div id="uploadDialog"></div>
	
<script type="text/javascript">
	$(document).ready(function(){
		var id = $("#id").val();
		if(id != ""){
			showFileList("20", id, '申请资料', 'imgPriviewOuter', 'imgPriviewInner' );
		}
	});

	function retList(){
		if('${gotoPage}' == 1){
			document.location.href = "${ctx}/landChange/listSearch?year=${year}&companyCode=${companyCode}&beginDate=${beginDate}&endDate=${endDate}&status=${status}&page=${page}&pageSize=${pageSize}";
		}else if('${gotoPage}' == 2){
			document.location.href = "${ctx}/landChange/listQuerySearch?year=${year}&companyCode=${companyCode}&beginDate=${beginDate}&endDate=${endDate}&status=${status}&page=${page}&pageSize=${pageSize}";
		}
	}
</script>
</body>
</html>