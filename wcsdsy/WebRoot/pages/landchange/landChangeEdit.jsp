<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<c:set var="companyCode" value="${landRegChange.companyCode }" />
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
	<script type="text/javascript" src="${ctx}/js/upload.js"></script>

</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">
		<form id="editForm" name="editForm" method="post" action="../landChange/list">
			<fieldset class="fieldset_common_style">
				<input type="hidden" name="id" id="id" value="${landRegChange.id}"/>
				<input type="hidden" name="status" id="statusEdit" value="01"/>
				<table style="width: 100%" align="center">
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
							<a href="#" class="easyui-linkbutton"   onclick="return closeDialog();">
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
				<table style="width: 100%" align="left">
					<tr>
						<td class="table_common_td_label_style">申请批次号：</td>
						<td class="table_common_td_txt_style">
							<input type="hidden" id="applyBatchNo" name="applyBatchNo" value="${landRegChange.applyBatchNo}">
							${landRegChange.applyBatchNo}
						</td>
					</tr>
					<tr> 
						<td class="table_common_td_label_style">年度：</td>
						
					<c:if test="${ readOnlyFlag == 1 }">
	    				<td class="table_common_td_txt_style">
	    					<simple:select id="year" name="year" value="${year}" entityName="YearCode" width="187" canEdit="false" readOnly="true"/>
	    				</td>
	    			</c:if>
	    			<c:if test="${ readOnlyFlag != 1 }">
	    				<td class="table_common_td_txt_style editableFalse">
	    			    	<simple:select id="year" name="year" value="${year}" entityName="YearCode" width="187" canEdit="false"/>
	    					<span class="span_common_mustinput_style">*</span>
	    				</td>
	    			</c:if>
						
						<td class="table_common_td_label_style">企业：</td>
						<td class="table_common_td_txt_style editableFalse">
							<c:if test="${sessionScope.isCompanyUser}">
								<simple:select id="companyCode1" name="companyCode" entityName="Company" width="187" value="${sessionScope.user.companyCode}" 
								hasPleaseSelectOption="${!sessionScope.isCompanyUser}"
								readOnly="${sessionScope.isCompanyUser}" 
								canEdit="${!sessionScope.isCompanyUser}"/>
							</c:if>
							<c:if test="${!sessionScope.isCompanyUser}">
								<simple:select id="companyCode1" name="companyCode" hasPleaseSelectOption="true" value="${companyCode1}" entityName="Company" width="187" canEdit="true"/>
							</c:if>
							<span class="span_common_mustinput_style">*</span>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">证件类型：</td>
					<td class="table_common_td_txt_style editableFalse">
						<%@include file="../../idTypeComm.jsp" %>
						<span class="span_common_mustinput_style">*</span>
					</td>
						<td class="table_common_td_label_style">证件类型：</td>
						<td class="table_common_td_txt_style editableFalse">
							<input class="easyui-textbox" type="text" id="contractorID" name="contractorID" value="${landRegChange.contractorID }" style="width:189px;"></input>
						    <span class="span_common_mustinput_style">*</span>
						</td>
						<td align="right" valign="bottom">
	                       	<a href="javascript:void(0)" class="easyui-linkbutton" onclick="return expandInfo();">
	                       		<span class="l-btn-left"><span class="l-btn-text icon-search l-btn-icon-left">查询</span></span>
	                       	</a>
	             		</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<table style="width: 100%" align="left">
					<tr>
						<td class="table_common_td_label_style">承包方：</td>
						<td class="table_common_td_txt_style">
							<input class="easyui-textbox" type="text" id="contractorName" name="contractorName" value="${landRegChange.contractorName }" style="width:188px;" maxlength="10" readOnly></input>
						</td>
						<td class="table_common_td_label_style">联系方式：</td>
						<td class="table_common_td_txt_style">
							<input class="easyui-textbox" type="text" id="contractorTel" name="contractorTel" value="${landRegChange.contractorTel }" style="width:189px;" readOnly></input>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">住址：</td>
						<td colspan="3" class="table_common_td_txt_style">
							<span style='display:none'>
							<select class="easyui-combobox" id="cityCode" name="cityCode">
								<option value="230184">五常市</option>
							</select>
							</span>
							<select class="easyui-combobox" id="townCodeView" name="townCodeView" data-options="editable:false" style="width:187px;" disabled>
								<option value="${landRegChange.townCode }" selected><simple:showName entityName="areadevision" codeKey="town" value="${landRegChange.townCode }"></simple:showName></option>
							</select>
							<input type="hidden" id="townCode" name="townCode" value="${landRegChange.townCode }">
	
							<select class="easyui-combobox" id="countryCodeView" name="countryCodeView" data-options='editable:false' style="width:187px;" disabled>
								<option value="${landRegChange.countryCode }" selected><simple:showName entityName="areadevision" codeKey="country" value="${landRegChange.townCode },${landRegChange.countryCode }"></simple:showName></option>
							</select>
							<input type="hidden" id="countryCode" name="countryCode" value="${landRegChange.countryCode }">
	
							<input type="text" class="easyui-textbox" id="address" name="address" value="${landRegChange.address }" readonly="readonly" style="width:440px;">
						</td>
						
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<table style="width: 100%" align="left">
					<!--<tr>
						<td align="right" width="100px">类型选择：</td>
						<td colspan="5">
							<input type="checkbox" id="geneRegistType" name="geneRegistType" disabled="true" >普通土地
							&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;
							<input  type="checkbox" id="specRegistType" name="specRegistType" disabled="true" onchange="">特殊土地
						</td>
					</tr>	-->	
					<tr>
						<td class="table_common_td_label_style">类型选择：</td>
						<td colspan="5" class="table_common_td_txt_style editableFalse">
							<c:if test="${landRegChange.geneRegistType == '1' }">
								<input type="checkbox" id="geneRegistType" name="geneRegistType" checked disabled="true" >普通备案
								&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;
							</c:if>			
							<c:if test="${landRegChange.geneRegistType != '1' }">
								<input type="checkbox" id="geneRegistType" name="geneRegistType" disabled="true" >普通备案
								&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;
							</c:if>				
							<c:if test="${landRegChange.specRegistType == '1' }">
                                 <input  type="checkbox" id="specRegistType" name="specRegistType" checked  disabled="true">特殊备案
							</c:if>	
							<c:if test="${landRegChange.specRegistType != '1' }">
                                 <input  type="checkbox" id="specRegistType" name="specRegistType" disabled="true">特殊备案
							</c:if>	
							<span class="span_common_mustinput_style">*</span>
						</td>
					</tr>			
					<tr>
						<td class="table_common_td_label_comment_style">变更原因：</td>
						<td colspan="5" class="table_common_td_txt_style">
							<textarea class="easyui-textbox" rows="3" id="changeReason" name="changeReason" cols="60" style="font-size:12px;height:100px" data-options="multiline:true">${landRegChange.changeReason }</textarea>
						   <span class="span_common_mustinput_style">*</span>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_comment_style">申请人：</td>
						<td class="table_common_td_txt_style">
						
						<c:if test="${landRegChange.applicant != null }">
						    <input class="easyui-textbox" type="text" id="applicant" name="applicant" value="${landRegChange.applicant}" style="width:100px;"></input>
						</c:if>	
						<c:if test="${landRegChange.applicant == null }">
						    <input class="easyui-textbox" type="text" id="applicant" name="applicant" value="${user.userName}" style="width:100px;"></input>
						</c:if>	
						    <span class="span_common_mustinput_style">*</span>
						</td>
						<td class="table_common_td_label_comment_style">联系方式：</td>
						<td class="table_common_td_txt_style">
							<input class="easyui-textbox" type="text" id="applicantTel" name="applicantTel" value="${landRegChange.applicantTel }" style="width:189px;"></input>
						    <span class="span_common_mustinput_style">*</span>
						</td>
						<td colspan="2">
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<legend>申请资料列表</legend>
				<table style="width: 100%" align="center">
					<tr>
						<td align="left">
							<a href="#" class="easyui-linkbutton" onclick="javascript:showUploadDialog('20', '${landRegChange.id}', '举证资料', 'imgPriviewOuter', 'imgPriviewInner');">
								<span class="l-btn-left"><span class="l-btn-text icon-docupload l-btn-icon-left">上传申请凭证</span></span>
							</a>
						</td>
					</tr>
					<tr>
						<td>
							<table id="imgList" height="152px" class="easyui-datagrid" striped="true" singleSelect="true">
								<thead>
									<tr>
										<th field="originalName" width="50%" align="center">申请凭证</td>
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
				<legend>申请资料预览</legend>
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
    var lastQcIdNumber = "";
	$(document).ready(function(){
		var id = $("#id").val();
		if(id != ""){
			showFileList("20", id, '申请资料', 'imgPriviewOuter', 'imgPriviewInner' );
		}
		
		if($('#contractorID').val()!=""){
			var params = {'companyCode': $("#companyCode1").combobox('getValue'), 'yearCode':$('#year').combobox('getValue'), 'idType':$("#idType").combobox('getValue'), 'contractorID':$('#contractorID').val()};
			Public.ajaxGet('../landChange/queryLandRegInfosCount', params, function(e) {
				if (200 == e.status) {
					 if(parseInt(e.data.geneResultCnt)==0){
						 $("#geneRegistType").prop("disabled", true);
					 }else{
						 $("#geneRegistType").prop("disabled", false);
					 }
					 if(parseInt(e.data.specResultCnt)==0){
						 $("#specRegistType").prop("disabled", true);
					 }else{
						 $("#specRegistType").prop("disabled", false);
					 }
				} else {
					$.messager.alert('错误','操作失败！' + e.msg, 'error');
				}
			});
		}
	});
	
	//动态调整预览图片位置
	var width = $("#fileDiv").width();
	var picWidth = $("#imgPriviewInner").width();
	var paddingLeft = (width - picWidth)/2 + "px";
	$("#fileDiv").css("padding-left", paddingLeft);

	function closeEditDialog(){
		$('#addDialog').dialog('close');
	}

	function save(){
		if(!check()) return ;
		var retFlag = '${retFlag}';
		var a = $('#editForm').toObject();
		Public.ajaxPost('save', JSON.stringify(a), function(e) {
			hideLoading();
			if (200 == e.status) {
				$.messager.alert('提示','操作成功。','info',function(){
					if(retFlag == '1'){
						//retList();
					    document.location.href = '${ctx}/landChange/listSearch?year=${year}&companyCode=${companyCode}&beginDate=${beginDate}&endDate=${endDate}&status=${status}&page=${page}&pageSize=${pageSize}';
					}else if("${flag}" == "audit"){
					    document.location.href = '${ctx}/landChange/listQuerySearch?year=${year}&companyCode=${companyCode}&beginDate=${beginDate}&endDate=${endDate}&status=${status}&page=${page}&pageSize=${pageSize}';
					} else {
						//document.location.href = '${ctx}/landChange/listSearch?year=${year}&companyCode=${companyCode}&beginDate=${beginDate}&endDate=${endDate}&status=${status}&page=${page}&pageSize=${pageSize}';
					    document.location.href = '${ctx}/landChange/editInput';
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
		    href: '../file/uploadInit?bizType=landRegChange',
		    modal: true
		});
	}
	//确权查询方法
	var userName='';
	function expandInfo(){ 
		userName='${user.userName}';
		if($("#companyCode1").combobox('getValue')==""){
			$.messager.alert('警告','请选择企业！','warning');
			return false;
		}
		//----只根据证件类型和证件号码查询
		if($("#idType").combobox('getValue')==""){
			$.messager.alert('警告','请选择证件类型！','warning');
			return false;
		}
		if($('#contractorID').val()==""){
			$.messager.alert('警告','请输入证件号码！','warning');
			return false;
		}
		if($("#idType").combobox('getValue')=="01" && $('#contractorID').val()!=""){
			var strError = checkIdNumber($('#contractorID').val());
			if(strError.length>1){
				$.messager.alert('警告',strError,'warning');
				return false;
			}
		}
		var contractorId = $("#contractorID").val();
		var idType = $("#idType").combobox('getValue');
		showLoading();
		lastQcIdNumber = contractorId;
		Public.ajaxGet('../api/getContratorInfo?contratorId=' + contractorId + "&contractorIDType=" + idType, {}, function(e) {
			hideLoading();
			if (0 == e.status) {
				clearDatasTable();
				initInterfaceInfo( e.data );
			} else {
				$.messager.alert('错误', e.msg, 'error');
				//承包方
				$("#contractorName").textbox('setValue', '');
				//联系方式
				$("#contractorTel").textbox('setValue', '');
				//乡
				$("#townCodeView").combobox('setValue', '');
				//村
				$("#countryCodeView").combobox('setValue', '');
				//乡
				$("#townCode").val('');
				//村
				$("#countryCode").val('');
				//屯
				$("#address").textbox('setValue', '');
				
				$("#geneRegistType").prop("disabled", true);
				$("#specRegistType").prop("disabled", true);
				$("#geneRegistType").prop("checked", false);
				$("#specRegistType").prop("checked", false);
				//情况说明
				$("#changeReason").textbox('setValue', '');
				//承包方
				$("#applicant").textbox('setValue', '');
				//联系方式
				$("#applicantTel").textbox('setValue', '');
				clearDatasTable();
			}
		});
	}
	function clearDatasTable(){
		$('#imgList').datagrid('loadData', { total: 0, rows: [] });
	}
	//初始化信息
	function initInterfaceInfo( data ){
		//基本信息
		var contratorInfo = data.peasant;
		//承包方
		$("#contractorName").textbox('setValue', contratorInfo.contractorName );
		//联系方式
		$("#contractorTel").textbox('setValue', contratorInfo.contractorTel );
		var params = {'cityCode': $('#cityCode').combobox('getValue'), 'townCode':contratorInfo.townCode, 'countryCode':contratorInfo.countryCode};
		Public.ajaxGet('../areaDevision/getAreaDevisions', params, function(e) {
			if (200 == e.status) {
				 addTownAndCountryOptions(JSON.parse(e.data));
			} else {
				$.messager.alert('错误','操作失败！' + e.msg, 'error');
			}
		});
		//屯
		$("#address").textbox('setValue', contratorInfo.groupName );
		var params = {'companyCode': $("#companyCode1").combobox('getValue'), 'yearCode':$('#year').combobox('getValue'), 'idType':$("#idType").combobox('getValue'), 'contractorID':$('#contractorID').val()};
		Public.ajaxGet('../landChange/queryLandRegInfosCount', params, function(e) {
			if (200 == e.status) {
				 if(parseInt(e.data.geneResultCnt)==0&&parseInt(e.data.specResultCnt)==0){
						$.messager.alert('警告','该证件号码不存在备案信息，请重新输入。','warning');
						return false;
				 }
				 if(parseInt(e.data.grainResultCnt)>0){
						$.messager.alert('警告','该农户本年度存在收粮信息，不能变更。请重新输入证件号码。','warning');
						return false;
				 }
				 if(parseInt(e.data.geneResultCnt)==0){
					 $("#geneRegistType").prop("disabled", true);
				 }else{
					 $("#geneRegistType").prop("disabled", false);
				 }
				 if(parseInt(e.data.specResultCnt)==0){
					 $("#specRegistType").prop("disabled", true);
				 }else{
					 $("#specRegistType").prop("disabled", false);
				 }
			} else {
				$.messager.alert('错误','操作失败！' + e.msg, 'error');
			}
		});
		
		//情况说明
		$("#changeReason").textbox('setValue', '');
		//承包方
		$("#applicant").textbox('setValue', userName);
		//联系方式
		$("#applicantTel").textbox('setValue', '');
		clearDatasTable();
		
	}
	function addTownAndCountryOptions(obj){
		$('#townCodeView').combobox({
			valueField:'id',
			textField:'text',
			onChange:function(){return false;}
		});
		$('#countryCodeView').combobox({
			valueField:'id',
			textField:'text',
			onChange:function(){return false;}
		});
		$('#townCodeView').combobox('clear');
		$('#countryCodeView').combobox('clear');
		$('#townCodeView').combobox('loadData',[{'id':obj[0].id,'text': obj[0].text}]);
		$('#countryCodeView').combobox('loadData',[{'id':obj[1].id,'text': obj[1].text}]);
		$('#townCodeView').combobox('setValue',obj[0].id);
		$('#countryCodeView').combobox('setValue',obj[1].id);
		$('#townCode').val(obj[0].id);
		$('#countryCode').val(obj[1].id);
	}
function auditOpt( status ){
	if( status == "0" ){   //通过,改变状态
		$("#statusEdit").val( "03" );
		save();
	}else if( status == -1 ){  //驳回,弹窗
		var id = $("#id").val();
		$('#bhDialog').dialog({
		    title: '驳回',
		    width: 500,
		    height: 300,
		    closed: false,
		    cache: false,
		    href: '${ctx}/landChange/auditBhInput?id='+id,
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
	var url = "${ctx}/landChange/listSearch";
	url += "?year=${landRegChangeModel.year}";
	url += "&page=${page}";
	url += "&pageSize=${pageSize}";
	url += "&companyCode=${landRegChangeModel.companyCode}";
	url += "&beginDate=${landRegChangeModel.beginDate}";
	url += "&endDate=${landRegChangeModel.endDate}";
	url += "&status=${landRegChangeModel.status}";

	document.location.href = url;
}
function check(){
	if($("#companyCode1").combobox('getValue')==""){
		$.messager.alert('警告','请选择企业！','warning');
		return false;
	}
	if($("#idType").combobox('getValue')==""){
		$.messager.alert('警告','请选择证件类型！','warning');
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
	
	if (!$('#geneRegistType').attr('checked')&&!$('#specRegistType').attr('checked')){
		$.messager.alert('警告','请选择土地备案类型！','warning');
		return false;
	}
	if($("#changeReason").val()==""){
		$.messager.alert('警告','请输入变更原因！','warning');
		return false;
	}
	if($("input[name='applicant']").val()==""){
		$.messager.alert('警告','请填写申请人！','warning');
		return false;
	}
	if(($("input[name='applicant']").val()+"").length>10){
		$.messager.alert('警告','您输入的申请人姓名长度超出系统限制！','warning');
		return false;
	}
	
	if($("input[name='applicantTel']").val()==""){
		$.messager.alert('警告','请填写申请人电话！','warning');
		return false;
	}
	if(!isTel($("input[name='applicantTel']").val())){
		$.messager.alert('警告','请填写正确的申请人电话号码或者手机号！','warning');
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
