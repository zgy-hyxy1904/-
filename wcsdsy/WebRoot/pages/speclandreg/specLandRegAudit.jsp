<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<c:set var="companyCode" value="${specLandReg.companyCode }" />
<c:set var="yearCode" value="${YearCode }" />
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
				<table class="table_common_style">
					<tr>
						<td>
							<c:if test="${flag == 'audit' }">
							<a href="#" class="easyui-linkbutton"  onclick="javascript:auditOpt(0);">
								<span class="l-btn-left">
									<span class="l-btn-text icon-ok l-btn-icon-left">审核通过
									</span></span>
							</a>
							<a href="#" class="easyui-linkbutton"   onclick="javascript:auditOpt(-1);">
								<span class="l-btn-left">
									<span class="l-btn-text icon-back l-btn-icon-left">驳回</span></span>
							</a>
							<a href="#" class="easyui-linkbutton"   onclick="return retList();">
								<span class="l-btn-left">
									<span class="l-btn-text icon-cancel l-btn-icon-left">返回</span></span>
							</a>
						</c:if>
						<c:if test="${flag == 'edit' }">
							<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="return save();">
								保存
							</a>
							<a href="#" class="easyui-linkbutton" onclick="return submitAudit();">
								<span class="l-btn-left">
									<span class="l-btn-text icon-ok l-btn-icon-left">提交申请</span></span>
							</a>
						</c:if>
						<c:if test="${retFlag == 1 }">
							<a href="#" class="easyui-linkbutton"  onclick="return retList();">
								<span class="l-btn-left">
								<span class="l-btn-text icon-cancel l-btn-icon-left">返回</span></span>
							</a>
						</c:if>
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
							<%@include file="../../yearComm.jsp"%>
						</td>
						<td class="table_common_td_label_style">企业：</td>
						<td class="table_common_td_txt_style">
							<c:if test="${sessionScope.isCompanyUser}">
								<simple:select id="companyCode1" name="companyCode" entityName="Company" width="187" value="${sessionScope.user.companyCode}" 
								hasPleaseSelectOption="${!sessionScope.isCompanyUser}"
								readOnly="${sessionScope.isCompanyUser}" 
								canEdit="${!sessionScope.isCompanyUser}"/>
							</c:if>
							<c:if test="${!sessionScope.isCompanyUser}">
								<simple:select id="companyCode1" name="companyCode" hasPleaseSelectOption="true" value="${companyCode1}" entityName="Company" width="187" canEdit="true"/>
							</c:if>
							
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">承包方类型：</td>
						<td class="table_common_td_txt_style">
							<%@include file="../../contractorTypeComm.jsp" %>
						</td>
						<td class="table_common_td_label_style">证件类型：</td>
						<td class="table_common_td_txt_style">
							<%@include file="../../idTypeComm.jsp" %>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">承包方：</td>
						<td class="table_common_td_txt_style">
							<input class="easyui-textbox" type="text" readonly="readonly" name="contractorName" value="${specLandReg.contractorName }" style="width:188px;" maxlength="10"></input>
						</td>
						<td class="table_common_td_label_style">证件号码：</td>
						<td class="table_common_td_txt_style">
							<input class="easyui-textbox" type="text" readonly="readonly" name="contractorID" value="${specLandReg.contractorID }" style="width:189px;"></input>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">住址：</td>
						<td class="table_common_td_txt_style" colspan="3">
							<simple:casCity  countryWidth="140" cityWidth="140" groupWidth="260" cityId="cityCode" cityName="cityCode" cityCode="${specLandReg.cityCode}" townCode="${specLandReg.townCode }" countryCode="${specLandReg.countryCode }" groupValue="${specLandReg.groupName }" townId="townCode" townName="townCode" countryId="countryCode" countryName="countryCode" groupName="groupName" groupId="groupName" showCity="false" showText="false" canEdit="false" showGroup="true"/>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_style">土地类型：</td>
						<td class="table_common_td_txt_style">
							<%@include file="../../landTypeComm.jsp" %>		
						</td>
						<td class="table_common_td_label_style">测量（亩）：</td>
						<td class="table_common_td_txt_style">
							<input readonly="readonly" class="easyui-numberbox" precision="2" min="0.00" max="9999999.99"  name="actualMu" value="${actualMu }" style="width:80px;"></input>
						</td>
						<td class="table_common_td_label_style">土地类别：</td>
						<td class="table_common_td_txt_style">
							<%@include file="../../landClassComm.jsp" %>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">地块位置：</td>
						<td colspan="5" class="table_common_td_txt_style">
							<simple:casCity countryWidth="146" cityWidth="146" groupWidth="265" cityId="cityCodeDetail" cityName="cityCodeDetail" cityCode="${cityCodeDetail}" townCode="${townCodeDetail }" countryCode="${countryCodeDetail }" groupValue="${groupNameDetail }" townId="townCodeDetail" townName="townCodeDetail" countryId="countryCodeDetail" countryName="countryCodeDetail" groupName="groupNameDetail" groupId="groupNameDetail" showText="false" showCity="false" showGroup="true"/>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_style">本次备案面积（亩）：</td>
						<td class="table_common_td_txt_style">
							<input readonly="readonly" class="easyui-numberbox" precision="2" min="0.00" max="9999999.99" type="text" name="archiveAcreage" value="<f:formatNumber type="number" value="${specLandReg.archiveAcreage }" pattern="0.00" maxFractionDigits="2"/>" style="width:70px;"></input>
						</td>
						<td class="table_common_td_label_style">经办人：</td>
						<td class="table_common_td_txt_style">
							<input readonly="readonly" class="easyui-textbox" type="text" name="operatorName" value="${specLandReg.operatorName }" style="width:70px;"></input>
						</td>
						<td class="table_common_td_label_style">经办日期：</td>
						<td class="table_common_td_txt_style">
							<input readonly="readonly" class="easyui-datebox" name="operatorDate" id="operatorDate" value="${ specLandReg.operatorDate}"
       data-options="required:true,showSeconds:false" style="width:120px" editable="false" >
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_comment_style">情况说明：</td>
						<td colspan="5" class="table_common_td_txt_style">
							<textarea readonly="readonly" class="easyui-textbox" rows="3" id="description" name="description" cols="60" style="width:500px;height:100px" data-options="multiline:true">${specLandReg.description }</textarea>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<legend>举证资料列表</legend>
				<table class="table_common_style">
					<tr>
						<td>
							<table id="imgList" height="152px" class="easyui-datagrid" striped="true" singleSelect="true">
								<thead>
									<tr>
										<th field="originalName" width="50%" align="center">举证凭证</td>
										<th field="fileInfo" width="30%" align="center">说明</td>
										<th field="op" width="20%" align="center">操作</td>
									</tr>
								</thead>
							</table>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<legend>举证资料预览</legend>
				<table id="fileDiv" style="width: 100%;" align="center">
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
		
		$('input,textarea',$('form[name="editForm"]')).attr('readonly',true);
		$('select',$('form[name="editForm"]')).attr('disabled','disabled');
		setTimeout(function(){
			  $('#companyCode1').combobox('disable'); 
			  $('#contractorType').combobox('disable'); 
			  $('#idType').combobox('disable'); 
			  $('#landType').combobox('disable'); 
			  $('#landClass').combobox('disable'); 
			  $('#townCode').combobox('disable'); 
			  $('#countryCode').combobox('disable'); 
			  $('#townCodeDetail').combobox('disable'); 
			  $('#countryCodeDetail').combobox('disable'); 
			},0);
	});

	function closeEditDialog(){
		$('#addDialog').dialog('close');
	}

	function save(){
		if(!check()) return ;
		var retFlag = '${retFlag}';
		var a = $('#editForm').toObject();
		showLoading();
		Public.ajaxPost('save', JSON.stringify(a), function(e) {
			hideLoading();
			if (200 == e.status) {
				$.messager.alert('提示','操作成功。','info',function(){
					if(retFlag == '1'){
					    document.location.href = '${ctx}/specLandReg/list?year=${year}&companyCode=${companyCode}&beginDate=${beginDate}&endDate=${endDate}&status=${status}&page=${page}&pageSize=${pageSize}';
					}else if("${flag}" == "audit"){
					    retList();
					    //document.location.href = '${ctx}/specLandReg/listquery?year=${year}&companyCode=${companyCode}&beginDate=${beginDate}&endDate=${endDate}&status=${status}&page=${page}&pageSize=${pageSize}';
					} else {
					    document.location.href = '${ctx}/specLandReg/editInput';
					}
				});
			} else {
				$.messager.alert('错误','保存失败！' + e.msg,'error');
			}
		});
	}
	
	function closeEdiDialog(){
		$('#addDialog').dialog('close');
	}
	
	function fileUpload(){
		$('#addDialog').dialog({
		    title: '文件上传',
		    width: 550,
		    height: 400,
		    closed: false,
		    cache: false,
		    href: '../file/uploadInit?bizType=specLandReg',
		    modal: true
		});
	}

function auditOpt( status ){
	if( status == "0" ){   //通过,改变状态
		$("#statusEdit").val( "03" );
		//save();
		showLoading();
		var a = {};
		a.id = "${specLandReg.id}";
		a.status = "03";
		a.reason = "";
		Public.ajaxPost('saveReason', JSON.stringify(a), function(e) {
			hideLoading();
			if (200 == e.status) {
				$.messager.alert('警告','操作成功！','warning');
				retList();
			} else {
				$.messager.alert('错误','保存失败！' + e.msg,'error');
			}
		});
	}else if( status == -1 ){  //驳回,弹窗
		var id = $("#id").val();
		$('#bhDialog').dialog({
		    title: '驳回',
		    width: 500,
		    height: 300,
		    closed: false,
		    cache: false,
		    href: '${ctx}/specLandReg/auditBhInput?id='+id,
		    modal: true
		});
	}
}

//提交申请,只改变状态
function submitAudit(){
	$("#statusEdit").val( "02" );
	save();
}

function retList(){
	var url = "${ctx}/specLandReg/listquery";
	url += "?year=${specLandRegModel.year}";
	url += "&page=${page}";
	url += "&pageSize=${pageSize}";
	url += "&companyCode=${specLandRegModel.companyCode}";
	url += "&beginDate=${specLandRegModel.beginDate}";
	url += "&endDate=${specLandRegModel.endDate}";
	url += "&status=${specLandRegModel.status}";
	
	document.location.href = url;
}
function check(){
	if($("#companyCode1").combobox('getValue')==""){
		$.messager.alert('警告','请选择企业！','warning');
		return false;
	}
	if($("#contractorType").combobox('getValue')==""){
		$.messager.alert('警告','请选择承包方类型！','warning');
		return false;
	}
	if($("#idType").combobox('getValue')==""){
		$.messager.alert('警告','请选择证件类型！','warning');
		return false;
	}
	if($("input[name='contractorName']").val()==""){
		$.messager.alert('警告','请填写承包方！','warning');
		return false;
	}
	if($("input[name='contractorID']").val()==""){
		$.messager.alert('警告','请填写承包人证件号码！','warning');
		return false;
	}
	if($("#idType").combobox('getValue')=="01" && $("input[name='contractorID']").val()!=""){
		var strError = checkIdNumber($("input[name='contractorID']").val());
		if(strError.length>1){
			$.messager.alert('警告',strError,'warning');
			return false;
		}
	}
	if(($("input[name='contractorID']").val()+"").length>18){
		$.messager.alert('警告','您输入的证件号码长度超出系统限制！','warning');
		return false;
	}
	if($("#countryCode").combobox('getValue')==""||$("#townCode").combobox('getValue')==""||$("#groupName").val()==""){
		$.messager.alert('警告','请填写住址！','warning');
		return false;
	}
	if($("#landType").combobox('getValue')==""){
		$.messager.alert('警告','请选择土地类型！','warning');
		return false;
	}
	if($("input[name='actualMu']").val()==""){
		$.messager.alert('警告','请填写测量亩！','warning');
		return false;
	}else if (!isNumber($("input[name='actualMu']").val())){
		$.messager.alert('警告','测量亩请输入数字！','warning');
		return false;
	}
	if(($("input[name='actualMu']").val()+"").length>14){
		$.messager.alert('警告','您输入的测量亩数字长度超出系统限制！','warning');
		return false;
	}
	if($("#landClass").combobox('getValue')==""){
		$.messager.alert('警告','请选择土地类别！','warning');
		return false;
	}	
	if($("#countryCodeDetail").combobox('getValue')==""||$("#townCodeDetail").combobox('getValue')==""||$("#groupNameDetail").val()==""){
		$.messager.alert('警告','请选择地块位置！','warning');
		return false;
	}
	if($("input[name='archiveAcreage']").val()==""){
		$.messager.alert('警告','请填写本次备案面积！','warning');
		return false;
	}else if (!isNumber($("input[name='archiveAcreage']").val())){
		$.messager.alert('警告','本次备案面积请输入数字！','warning');
		return false;
	}
	if(($("input[name='archiveAcreage']").val()+"").length>14){
		$.messager.alert('警告','您输入的本次备案面积数字长度超出系统限制！','warning');
		return false;
	}
	if($("input[name='operatorName']").val()==""){
		$.messager.alert('警告','请填写经办人！','warning');
		return false;
	}
	if(($("input[name='operatorName']").val()+"").length>10){
		$.messager.alert('警告','您输入的经办人名称长度超出系统限制！','warning');
		return false;
	}
	if($("#description").val()==""){
		$.messager.alert('警告','请输入本次备案的情况说明！','warning');
		return false;
	}
	return true;
}

function showVideoList(o, d){}
function closeDialog(){
	$("#addDialog").dialog('close');
}
</script>
</body>
</html>