<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
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
		<table id="data">
			<tr>
				<th>
					<a href="#" class="easyui-linkbutton" style="margin-left:1px; width:80px;" onclick="return sureLandRegD();">
						<span class="l-btn-left">
							<span class="l-btn-text icon-ok l-btn-icon-left">
						确定
						</span></span>
					</a>
					<a href="#" class="easyui-linkbutton" style="margin-left:1px; width:80px;" onclick="return closeEditDialog();">
						<span class="l-btn-left">
							<span class="l-btn-text icon-cancel l-btn-icon-left">
						返回</span></span>
					</a>
				</th>
			</tr>
		</table>
		<fieldset class="fieldset_common_style">
			<form id="inputForm" name="inputForm" method="get" action="../inputReg/list">
			<input type="hidden" id="tmp_id" name="tmp_id" value="${id}">
			
			<table class="table_common_style">
				<tr>
					<td class="table_common_td_label_style">证件类型：</td>
					<td class="table_common_td_txt_style editableFalse">
						<%@include file="../../idTypeComm.jsp" %>
						<span class="span_common_mustinput_style">*</span>
					</td>
					<td class="table_common_td_label_style">证件号码：</td>
					<td class="table_common_td_txt_style">
						<input name="contractorID" id="contractorID" value="${contractorID}" class="easyui-textbox" style="width:150px;">
						<span class="span_common_mustinput_style">*</span>
					</td>
					<td align="center">
						<a href="#" class="easyui-linkbutton" onclick="return expandInfo();">
							<span class="l-btn-left"><span class="l-btn-text icon-search l-btn-icon-left">确权查询</span></span>
						</a>
					</td>
				</tr>
			</table>	
			</form>
		</fieldset>
		<fieldset class="fieldset_common_style">
			<table class="table_common_style">
				<tr>
					<td class="table_common_td_label_style">承包方类型：</td>
					<td class="table_common_td_txt_style">
						<simple:select id='contractorType' name="contractorType" value="01" codeKey="ContractorType" entityName="commonData"  width="150" readOnly="true" canEdit="false"/>
					</td>
					<td class="table_common_td_label_style">承包方：</td>
					<td class="table_common_td_txt_style">
						<input class="easyui-textbox" name="tmp_contractorName" id="tmp_contractorName" readonly="readonly" value="${contractorName }"  style="width:150px;">
					</td>
					<td class="table_common_td_label_style">联系方式：</td>
					<td class="table_common_td_txt_style">
						<input class="easyui-textbox" name="tmp_contractorTel" id="tmp_contractorTel" value="${contractorTel }" style="width:150px;">
						<span class="span_common_mustinput_style">*</span>
					</td>
				</tr>
				<tr>
					<td class="table_common_td_label_style">住址：</td>
					<td colspan="5" class="table_common_td_txt_style">
						<span style='display:none'>
							<select class="easyui-combobox" id="tmp_cityCode" name="tmp_cityCode">
								<option value="230184">五常市</option>
							</select>
						</span>
						<select class="easyui-combobox" id="tmp_townCodeView" name="tmp_townCodeView" data-options="editable:false" style="width:150px;" disabled>
							<option value="${townCode }" selected><simple:showName entityName="areadevision" codeKey="town" value="${townCode }"></simple:showName></option>
						</select>
						<input type="hidden" id="tmp_townCode" name="tmp_townCode" value="${townCode }">

						<select class="easyui-combobox" id="tmp_countryCodeView" name="tmp_countryCodeView" data-options='editable:false' style="width:150px;" disabled>
							<option value="${countryCode }" selected><simple:showName entityName="areadevision" codeKey="country" value="${townCode },${countryCode }"></simple:showName></option>
						</select>
						<input type="hidden" id="tmp_countryCode" name="tmp_countryCode" value="${countryCode }">

						<input type="text" class="easyui-textbox" id="tmp_groupName" name="tmp_groupName" value="${groupName }" readonly="readonly" style="width:200px;">
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset class="fieldset_common_style">
			<table id="datas" height="120px;" width="100%" class="easyui-datagrid" striped="true" singleSelect="true">
				<thead>
				<tr>
					<th field="landType" width="20%" height="20" align="center" nowrap >土地类型</th>
					<th field="aMu" width="20%" height="20" align="center" nowrap>实（亩）</th>
					<th field="cMu" width="20%" height="20" align="center" nowrap>测量（亩）</th>
					<th field="landClass" width="20%" height="20" align="center" nowrap>土地类别</th>
					<th field="landName" width="20%" height="20" align="center" nowrap>地块名称</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="landInfo" items="${landList}" varStatus="status">
					<tr>
					<td align="center" height="20">${landInfo.landTypeName }</td>
					<td align="center" height="20">${landInfo.actualMu }</td>
					<td align="center" height="20">${landInfo.measurementMu }</td>
					<td align="center" height="20">${landInfo.landClassName }</td>
					<td align="center" height="20">${landInfo.landName }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</fieldset>
		<fieldset class="fieldset_common_style">
			<table class="table_common_style">
				<tr>
					<td class="table_common_td_label_style">承包总面积（亩）：</td>
					<td class="table_common_td_txt_style">
						<input name="zmj" id="zmj" readonly="readonly" value="<fmt:formatNumber type="number" value="${zmj }" pattern="0.00" maxFractionDigits="2"/>" class="easyui-textbox" style="background-color:#B0B0B0;width:150px;">
					</td>
					<td class="table_common_td_label_style">已备案面积（亩）：</td>
					<td class="table_common_td_txt_style">
						<input name="ybamj" id="ybamj" readonly="readonly"  value="<fmt:formatNumber type="number" value="${yba }" pattern="0.00" maxFractionDigits="2"/>" class="easyui-textbox" style="background-color:#B0B0B0;width:150px;">
					</td>
				</tr>
				<tr>
					<td class="table_common_td_label_style">可备案面积（亩）：</td>
					<td class="table_common_td_txt_style">
						<input name="kbamj" id="kbamj" readonly="readonly" value="<fmt:formatNumber type="number" value="${kba }" pattern="0.00" maxFractionDigits="2"/>" class="easyui-textbox" style="background-color:#B0B0B0;width:150px;">
					</td>
					<td class="table_common_td_label_style"><strong>本次备案面积（亩）：</strong></td>
					<td class="table_common_td_txt_style">
						<input name="tmp_archiveAcreage" id="tmp_archiveAcreage" value="${archiveAcreage}" class="easyui-numberbox" precision="2" min="0.00" max="9999999.99" value="${operatorName}" style="width:150px;">
						<span class="span_common_mustinput_style">*</span>
					</td>
				</tr>
				<tr>   
					<td class="table_common_td_label_style">经办人：</td>
					<td class="table_common_td_txt_style">
						<input name="tmp_operatorName" id="tmp_operatorName" value="${operatorName}"  type="text" class="easyui-textbox" style="width:150px;">
						<span class="span_common_mustinput_style">*</span>
					</td>
					<td class="table_common_td_label_style"><strong>经办日期：</strong></td>
					<td class="table_common_td_txt_style editableFalse">
						<input class="easyui-datebox" name="tmp_operatorDateDate" id="tmp_operatorDateDate" value="${operatorDate}"
            					data-options="required:true,showSeconds:false" style="width:150px" editable="false">
            			<span class="span_common_mustinput_style">*</span>
					</td>
				</tr>
			</table>
		</fieldset>
	</div>
	<div id="addDialog"></div>
	
<script type="text/javascript">
	var lastQcIdNumber = "";
	$(document).ready(function(){
		lastQcIdNumber = "${contractorID}";  //上次确认身份证号
	});

function deleteRecord(id){
	$.messager.confirm("确认", "您确认删除选定的记录吗？", function (deleteAction) {
	    if (!deleteAction) {
	    	return false;
	    }
	    var ids = [];
		var rows = $('#data').datagrid('getSelections');
		var length = rows.length;
		for(var i=0; i<rows.length; i++) ids.push(rows[i].id);
		showLoading();
		Public.ajaxGet('delete', {ids : ids}, function(e) {
			hideLoading();
			if (200 == e.status) {
			} else
				$.messager.alert('错误','删除失败！' + e.msg,'error');
		});
	});
}

function sureLandRegD(id){
	if(!check()) return ;
	if( lastQcIdNumber != $("#contractorID").val() ){
		$.messager.alert('警告','身份证号发生了变化，请确权后再操作！','warning');
		return false;
	}
	
	//本次备案面积
	var archiveAcreage =  $("#tmp_archiveAcreage").val(); //document.getElementById("archiveAcreage").value;a
	$("#archiveAcreage").val( archiveAcreage );
	//可备案面积
	var kbamj = $("#kbamj").val();
	if(archiveAcreage==""||archiveAcreage<=0){
		$.messager.alert('警告','请输入本次备案面积！','warning');
		return false;
	}
	if(parseFloat(archiveAcreage)>kbamj){
		$.messager.alert('警告','本次备案面积不能超出可备案面积！','warning');
		return false;
	} 
	//经办人
	var operatorName = $("#tmp_operatorName").val();
	$("#operatorName").val( operatorName );
	//经办日期
	var operatorDate = $("#tmp_operatorDateDate").textbox('getValue');
	
	$("#operatorDate").val( operatorDate );
	//市
	$("#cityCode").val( $("#cityCode").val() );
	//乡
	var townCode = $("#townCodeTmp").val();
	$("#townCode").val( townCode );
	//村
	var countryCode = $("#countryCodeTmp").val();
	$("#countryCode").val( countryCode );
	//屯
	var groupName = $("#groupNameTmp").val();
	$("#groupName").val( groupName );
	//承包方类型
	$("#contractorTypeTmp").val($("#contractorType").combobox('getValue') );
	//证件类型
	$("#idTypeTmp").val($("#idType").combobox('getValue'));
	//证件号码	
	$("#contractorIDTmp").val( $("#contractorID").val() );
	//联系方式
	$("#contractorIDTmp").val( $("#contractorID").val() );

	
	//Modify By WolfSoul Begin
	var optFlag = "${flag}";
	if( optFlag == "edit" ){
		$('#geneLandData').datagrid('updateRow', {index:parseInt('${rowIndex}'),row:{
			contractorValue: intValue(),
			contractorType: $("#contractorType").combobox('getValue'),//承包人类型列是隐藏的
			contractorText: $("#contractorType").combobox('getText'),
			idType: $("#idType").combobox('getValue'),//证件类型列是隐藏的
			idTypeText: $("#idType").combobox('getText'),
			contractorID:  $("#contractorID").val(),
			contractorName: $("#tmp_contractorName").val(),
			contractorTel: $("#tmp_contractorTel").val(),//联系人电话列是隐藏的
			cityCode: $("#cityCode").val(),//市编码列是隐藏的
			townCode: $("#tmp_townCode").val(),//镇编码列是隐藏的
			townText: $("#tmp_townCodeView").combobox('getText'),
			countryCode: $("#tmp_countryCode").val(),//村编码列是隐藏的
			countryText: $("#tmp_countryCodeView").combobox('getText'),
			groupName: $("#tmp_groupName").val(),
			zmj: $("#zmj").val(),
			yba: $("#ybamj").val(),
			kba: $("#kbamj").val(),
			archiveAcreage: $("#tmp_archiveAcreage").val(),
			operatorName: $("#tmp_operatorName").val(),
			operatorDate: $("#tmp_operatorDateDate").textbox('getValue')
	    }});
	}else{
		$('#geneLandData').datagrid('appendRow', {
			contractorValue: intValue(),
			contractorType: $("#contractorType").combobox('getValue'),//承包人类型列是隐藏的
			contractorText: $("#contractorType").combobox('getText'),
			idType: $("#idType").combobox('getValue'),//证件类型列是隐藏的
			idTypeText: $("#idType").combobox('getText'),
			contractorID:  $("#contractorID").val(),
			contractorName: $("#tmp_contractorName").val(),
			contractorTel: $("#tmp_contractorTel").val(),//联系人电话列是隐藏的
			cityCode: $("#cityCode").val(),//市编码列是隐藏的
			townCode: $("#tmp_townCode").val(),//镇编码列是隐藏的
			townText: $("#tmp_townCodeView").combobox('getText'),
			countryCode: $("#tmp_countryCode").val(),//村编码列是隐藏的
			countryText: $("#tmp_countryCodeView").combobox('getText'),
			groupName: $("#tmp_groupName").val(),
			zmj: $("#zmj").val(),
			yba: $("#ybamj").val(),
			kba: $("#kbamj").val(),
			archiveAcreage: $("#tmp_archiveAcreage").val(),
			operatorName: $("#tmp_operatorName").val(),
			operatorDate: $("#tmp_operatorDateDate").textbox('getValue')
	    });
	}
	//Modify By WolfSoul End
	closeEditDialog();
}

function intValue(){
	var value = "";
	value += $("#contractorType").combobox('getValue');
	value += ",";
	value += $("#idType").combobox('getValue');
	value += ",";
	value += $("#contractorID").val();
	value += ",";
	value += $("#tmp_archiveAcreage").val();
	value += ",";
	value += $("#tmp_operatorName").val();
	value += ",";
	value += $("#tmp_operatorDateDate").textbox('getValue');
	value += ",";
	value += $("#tmp_contractorName").textbox('getValue');
	value += ",";
	value += $("#tmp_contractorTel").textbox('getValue');
	value += ",";
	value += $("#tmp_townCode").val();
	value += ",";
	value += $("#tmp_countryCode").val();
	value += ",";
	value += $("#tmp_groupName").textbox('getValue');
	value += ",";
	if( $("#tmp_id").val() == "" ){
		value += "0";
	}else{
		value += $("#tmp_id").val();
	}
	
	return value;
}

function addRow(tableId, landTypeText, actuMu, measurementMu, landClassText, landlocation ){
	var testTbl = document.getElementById(tableId);
	var maxRowNum =  testTbl.rows.length;  //获取最大行数
	//添加一行
	var newTr = testTbl.insertRow( maxRowNum );
	//添加5列
	var landType = newTr.insertCell(0);
	landType.setAttribute("align", "center");
	landType.setAttribute("height", "20");
	landType.innerHTML = landTypeText;
	var sm = newTr.insertCell(1);
	sm.setAttribute("align", "center");
	sm.innerHTML= actuMu;
	var cm = newTr.insertCell(2);
	cm.setAttribute("align", "center");
	cm.innerHTML= measurementMu;
	var landClass = newTr.insertCell(3);
	landClass.setAttribute("align", "center");
	landClass.innerHTML = landClassText;
	var landLocation = newTr.insertCell(4);
	landLocation.setAttribute("align", "center");
	landLocation.innerHTML = landlocation;
}

function closeEditDialog(){
	$('#addDialog').dialog('close');
}
//确权查询方法
function expandInfo(){ 
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
	$("#tmp_archiveAcreage").numberbox('setValue','');
	$("#tmp_operatorName").textbox('setValue','');
	var contractorType = $("#contractorType").combobox('getValue');
	var contractorId = $("#contractorID").val();
	var idType = $("#idType").combobox('getValue');
	showLoading();
	lastQcIdNumber = contractorId;
	Public.ajaxGet('../api/getContratorInfo?contratorId=' + contractorId + "&contractorIDType=" + idType +"&year=${year}", {}, function(e) {
		hideLoading();
		if (0 == e.status) {
			clearDatasTable();
			initInterfaceInfo( e.data );
		} else {
			$.messager.alert('错误', e.msg, 'error');
			//承包方
			$("#tmp_contractorName").textbox('setValue', '');
			//联系方式
			$("#tmp_contractorTel").textbox('setValue', '');
			//乡
			$("#tmp_townCodeView").combobox('setValue', '');
			//村
			$("#tmp_countryCodeView").combobox('setValue', '');
			//乡
			$("#tmp_townCode").val('');
			//村
			$("#tmp_countryCode").val('');
			//屯
			$("#tmp_groupName").textbox('setValue', '');
			
			//总面积
			$("#zmj").textbox('setValue', '');
			//已备案面积
			$("#ybamj").textbox('setValue', '');
			//可备案面积
			$("#kbamj").textbox('setValue', '');
			clearDatasTable();
		}
	});
}

function clearDatasTable(){
	//$("#datas").html("<tr><th  width='20%' height=20 align=center nowrap >土地类型</th><th width='20%' height=20 align=center nowrap>实（亩）</th><th width='20%' height=20 align=center nowrap>测量（亩）</th><th width='20%' height=20 align=center nowrap>土地类别</th><th width='20%' height=20 align=center nowrap>地块名称</th></tr>");
	$('#datas').datagrid('loadData', { total: 0, rows: [] });
}

//初始化信息
function initInterfaceInfo( data ){
	//基本信息
	var contratorInfo = data.peasant;
	//承包方类型
	$("#contractorType").combobox('setValue', contratorInfo.contractorType);
	//承包方
	$("#tmp_contractorName").textbox('setValue', contratorInfo.contractorName );
	//联系方式
	$("#tmp_contractorTel").textbox('setValue', contratorInfo.contractorTel );
	var params = {'cityCode': $('#tmp_cityCode').combobox('getValue'), 'townCode':contratorInfo.townCode, 'countryCode':contratorInfo.countryCode};
	Public.ajaxGet('../areaDevision/getAreaDevisions', params, function(e) {
		if (200 == e.status) {
			 addTownAndCountryOptions(JSON.parse(e.data));
		} else {
			$.messager.alert('错误','操作失败！' + e.msg, 'error');
		}
	});
	alert(contratorInfo.groupName);
	//屯
	$("#tmp_groupName").textbox('setValue', contratorInfo.groupName );
	//初始化总面积、已备案、可备案面积
	//总面积
	$("#zmj").textbox('setValue', numberDecimalDigits(data.zmj,2) );
	//已备案面积
	$("#ybamj").textbox('setValue', numberDecimalDigits(data.yba,2) );
	//可备案面积
	$("#kbamj").textbox('setValue', numberDecimalDigits(data.kba,2) );
	
	//土地列表信息
	var landInfo = data.contract;
	//alert(data.contract.length);
	for( var contract in landInfo ){
		//类型
		var landType = landInfo[contract].landTypeName==null?'':landInfo[contract].landTypeName;
		//合同面积
		var actuMu = landInfo[contract].contractArea;
		//实测面积
		var measurementMu = landInfo[contract].measurementMu;
		//类别
		var landClass = landInfo[contract].landClassName==null?' ':landInfo[contract].landClassName;
		//位置
		var landlocation = landInfo[contract].landName;
		
		//addRow("datas", landType, actuMu, measurementMu, landClass, landlocation );
		$('#datas').datagrid('appendRow', {
				landType: landType,
				aMu : actuMu,
				cMu: measurementMu,
				landClass: landClass,
				landName:landlocation
			});
	}
}
	function check(){
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
		if(($('#contractorID').val()+"").length>18){
			$.messager.alert('警告','您输入的证件号码长度超出系统限制！','warning');
			return false;
		}
		if($("#tmp_contractorTel").val()==""){
			$.messager.alert('警告','承包方联系方式不能为空！','warning');
			$("#tmp_contractorTel").focus();
			return false;
		}
		if(!isTel($("#tmp_contractorTel").val())){
			$.messager.alert('警告','请填写正确的承包方电话号码！','warning');
			return false;
		}
		if(($('#tmp_contractorTel').val()+"").length>14){
			$.messager.alert('警告','您输入的电话号码长度超出系统限制！','warning');
			return false;
		}
		if($("#tmp_countryCode").val() == "" 
		|| $("#tmp_townCode").val() == "" || $("#tmp_groupName").val()==""){
			$.messager.alert('警告','请填写住址！','warning');
			return false;
		}
		if($("#tmp_archiveAcreage").val()==""){
			$.messager.alert('警告','请填写本次备案面积！','warning');
			return false;
		}else if (!isNumber($("#tmp_archiveAcreage").val())){
			$.messager.alert('警告','本次备案面积请输入数字！','warning');
			return false;
		}	
		if(($('#tmp_archiveAcreage').val()+"").length>14){
			$.messager.alert('警告','您输入的本次备案面积数字长度超出系统限制！','warning');
			return false;
		}
		if($("#tmp_operatorName").val() == ""){
			$.messager.alert('警告','请填写经办人！','warning');
			return false;
		}
		if(($("#tmp_operatorName").val()+"").length>10){
			$.messager.alert('警告','您输入的经办人名称长度超出系统限制！','warning');
			return false;
		}
		return true;
	}
	
	function addTownAndCountryOptions(obj){
		$('#tmp_townCodeView').combobox({
			valueField:'id',
			textField:'text',
			onChange:function(){return false;}
		});
		$('#tmp_countryCodeView').combobox({
			valueField:'id',
			textField:'text',
			onChange:function(){return false;}
		});
		$('#tmp_townCodeView').combobox('clear');
		$('#tmp_countryCodeView').combobox('clear');
		$('#tmp_townCodeView').combobox('loadData',[{'id':obj[0].id,'text': obj[0].text}]);
		$('#tmp_countryCodeView').combobox('loadData',[{'id':obj[1].id,'text': obj[1].text}]);
		$('#tmp_townCodeView').combobox('setValue',obj[0].id);
		$('#tmp_countryCodeView').combobox('setValue',obj[1].id);
		$('#tmp_townCode').val(obj[0].id);
		$('#tmp_countryCode').val(obj[1].id);
	}
	
	function setEasyUISelectReadOnly(selectId){
		var _tempV = $('#' + selectId).combobox('getValue');
		var _tempT = $('#' + selectId).combobox('getText');
		$('#' + selectId).combobox({
			valueField:'id',
			textField:'text',
			onChange:function(){return false;}
		});
		$('#' + selectId).combobox('clear');
		$('#' + selectId).combobox('loadData',[{'id':_tempV,'text': _tempT}]);
		$('#' + selectId).combobox('setValue',_tempV);
	}
</script>
</body>
</html>