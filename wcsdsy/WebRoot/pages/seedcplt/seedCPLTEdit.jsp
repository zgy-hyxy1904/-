<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
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
		<form id="addFrom" class="easyui-form" method="post" data-options="novalidate:true">
		<input type="hidden" name="id" id="id" value="${inputReg.id}"/>
		<input type="hidden" name="settleStatus" id="settleStatus" value="01"/>
			<fieldset class="fieldset_common_style">
				<table class="table_common_style">
					<tr>
						<td>
						    <a href="#" class="easyui-linkbutton" onclick="return save();">
								<span class="l-btn-left">
								<span class="l-btn-text icon-save l-btn-icon-left">保存</span>
								</span>
							</a>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_style">企业名称：</td>
						<td colspan="3" class="table_common_td_txt_style">
							<simple:select id="companyCode" name="companyCode" codeKey="01" hasPleaseSelectOption="true" value="${companyCode}" entityName="Company" />
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">投诉日期：</td>
						<td class="table_common_td_txt_style editableFalse" >
							<input class="easyui-datebox" name="complaintDate"  value="${seedCPLT.complaintDate}"
	                                     data-options="required:true,showSeconds:false,editable:false" style="width:170px">
						</td>
						<td class="table_common_td_label_style">购买日期：</td>
						<td class="table_common_td_txt_style editableFalse" >
							<input class="easyui-datebox" name="buySeedsDate" id="buySeedsDate" value="${ seedCPLT.buySeedsDate}"
	                                    data-options="required:true,showSeconds:false,editable:false" style="width:170px">
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">投诉人：</td>
						<td class="table_common_td_txt_style">
							<input class="easyui-textbox"  type="text" id='complainant' name="complainant" value="${seedCPLT.complainant }" style="width:170px;"></input>
						</td>
						<td class="table_common_td_label_style">性别：</td>
						<td class="table_common_td_txt_style editableFalse">
							<simple:select id='complainantSex' name="complainantSex" value="${seedCPLT.complainantSex}" codeKey="Sex" entityName="commonData" hasPleaseSelectOption="true" canEdit="false" width="170"/><BR>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">联系电话：</td>
						<td class="table_common_td_txt_style">
							<input class="easyui-textbox" id='complainantTel' type="text" name="complainantTel" value="${seedCPLT.complainantTel }" style="width:170px;"></input>
						</td>
						<td class="table_common_td_label_style">联系邮箱：</td>
						<td class="table_common_td_txt_style">
							<input class="easyui-textbox" type="text" id="complainantMail" name="complainantMail" value="${seedCPLT.complainantMail}" style="width:170px;"></input>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_comment_style">诉投内容：</td>
						<td colspan="3" class="table_common_td_txt_style">
							<textarea class="easyui-textbox" rows="5" cols="66" id='complaintContent' name="complaintContent" style="font-size:12px;height:80px;width:560px;" data-options="multiline:true" >${seedCPLT.complaintContent}</textarea>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
	<div id="addDialog"></div>
	<div id="uploadDialog"></div>
	
<script type="text/javascript">
	function save(){
		
		if(!check()) return false;
		var a = $('#addFrom').toObject();
		showLoading();
		Public.ajaxPost('save', JSON.stringify(a), function(e) {
			hideLoading();
			if (200 == e.status) {
				$.messager.alert('提示','保存成功。','info');
				if( e.data.id != "" ){
					$("#id").val( e.data.id );
				}
				document.location.href = "${ctx}/seedCPLT/editInput";
			} else {
				$.messager.alert('错误','保存失败！'+ e.msg,'error');
			}
		});
	}
	function retList(){
		window.document.location.href = "${ctx}/inputReg/list";
	}
	function closeEdiDialog(){
		$('#addDialog').dialog('close');
	}
	
	function check(){
		var companyCode = $('#companyCode').combobox('getValue');
		if(companyCode == ''){
			$.messager.alert('警告','请选择企业。','warning');
			return false;
		}
		if($('#buySeedsDate').datebox('getValue')==""){
			$.messager.alert('警告','请输入购买日期。','warning');
			return false;
		}
		if($('#complainant').val()==""){
			$.messager.alert('警告','请输入投诉人姓名。','warning');
			return false;
		}
		if($('#complainantSex').combobox('getValue')==""){
			$.messager.alert('警告','请选择性别。','warning');
			return false;
		}
		if($('#complainantTel').val()==""){
			$.messager.alert('警告','请输入投诉人联系电话。','warning');
			return false;
		}
		if(!isTel($("#complainantTel").val())){
			$.messager.alert('警告','请填写正确的电话号码或者手机号。','warning');		
			return false;
		}
		var mailbox = $.trim($("#complainantMail").val());
		if(mailbox!=""&&mailbox!=null&&!isEmail(mailbox)){
			$.messager.alert('警告','请填写正确邮箱地址。','warning');		
			return false;
		}
		if($('#complaintContent').val()==""){
			$.messager.alert('警告','请输入投诉内容。','warning');
			return false;
		}
		return true;
	}
</script>
</body>
</html>

