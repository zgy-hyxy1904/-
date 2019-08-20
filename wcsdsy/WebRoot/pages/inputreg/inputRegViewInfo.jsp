<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<script type="text/javascript" src="${ctx}/js/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
	<script>
		var root = "${ctx}";
	</script>
	<script type="text/javascript" src="${ctx}/js/upload_view.js"></script>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">
		<form id="addFrom" class="easyui-form" method="post" data-options="novalidate:true">
			<input type="hidden" name="id" id="id" value="${inputReg.id}"/>
			
			<fieldset class="fieldset_common_style">
				<table class="table_common_style">
					<tr>
						<td>
							<a href="#" class="easyui-linkbutton"  onclick="return retList();">
								<span class="l-btn-left">
								<span class="l-btn-text icon-cancel l-btn-icon-left">
								返回</span></span>
							</a>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_style">上报流水号：</td>
						<td class="table_common_td_txt_style">
							${inputReg.applyBatchNo }
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">年度：</td>
						<td class="table_common_td_txt_style">
							${inputReg.year}
						</td>
						<td class="table_common_td_label_style">企业名称：</td>
						<td class="table_common_td_txt_style">
							${inputReg.companyName }
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">投入品名称：</td>
						<td class="table_common_td_txt_style">
							${inputReg.inputGoodsName }
						</td>
						<td class="table_common_td_label_style">采购量：</td>
						<td class="table_common_td_txt_style">
							${inputReg.purchaseQuantity}
							<simple:showName entityName="commonData" codeKey="InputMaterialUnit" value="${inputReg.inputGoodsUnit}"></simple:showName>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">投入品经销商：</td>
						<td class="table_common_td_txt_style">
							${ inputReg.inputGoodsSupplier}
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">采购人：</td>
						<td class="table_common_td_txt_style">
							${inputReg.purchasePerson }
						</td>
						<td class="table_common_td_label_style">采购日期：</td>
						<td class="table_common_td_txt_style">
							<fmt:formatDate value="${inputReg.purchaseDate}" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<legend>采购凭证列表</legend>
				<table class="table_common_style">
					<tr>	
						<td>
							<table height="216px;" id="imgList" width="100%" class="easyui-datagrid" striped="true" singleSelect="true">
								<thead>
									<tr>
										<th field="originalName" width="50%" align="center">采购凭证</td>
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
				<legend>采购凭证预览</legend>
				<table id="fileDiv" class="table_common_style">
					<tr>
						<td>
							<simple:imgView outerDivId="imgPriviewOuter" innerDivId="imgPriviewInner" imgInfoMaps="imgPathAndInfoMaps" width="450" height="500"></simple:imgView>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
	<div id="addDialog"></div>
	<div id="uploadDialog"></div>
</body>
</html>

<script type="text/javascript">
	$(document).ready(function(){
		var id = $("#id").val(); 
		if(id != ""){
			showFileList("02", id, '采购凭证', 'imgPriviewOuter', 'imgPriviewInner' );
		}
	});
	
	//动态调整预览图片位置
	var width = $("#fileDiv").width();
	var picWidth = $("#imgPriviewInner").width();
	var paddingLeft = (width - picWidth)/2 + "px";
	$("#fileDiv").css("padding-left", paddingLeft);

	function save(){
		if(!check()) return ;
		var a = $('#addFrom').toObject();
		var retFlag = '${retFlag}';
		Public.ajaxPost('save', JSON.stringify(a), function(e) {
			if (200 == e.status) {
				$.messager.alert('提示','保存成功。','info', function(){
					if(retFlag == '1'){
						window.document.location.href = "${ctx}/inputReg/list";
					}else{
						window.document.location.href = "${ctx}/inputReg/editInput";
					}
				});
			} else {
				$.messager.alert('错误','保存失败！' + e.msg,'error');
			}
		});
	}
	function retList(){
		window.document.location.href = "${ctx}/inputReg/list?year=${year}&companyCode=${companyCode}&beginDate=${beginDate}&endDate=${endDate}&status=${status}&page=${page}&pageSize=${pageSize}";
	}
	function closeEdiDialog(){
		$('#addDialog').dialog('close');
	}
	function check(){
		if( $('#companyCode').combobox('getValue') == "" ){
			$.messager.alert('警告','请选择企业。','warning');
			return false;
		}
		if($("input[name='inputGoodsName']").val()==""){
			$.messager.alert('警告','请填写投入品名称。','warning');
			return false;
		}
		if( $("input[name='inputGoodsName']").val().length > 50 ){
			$.messager.alert('警告','投入品名称长度不能超过50。','warning');
			return false;
		}
		if( $("input[name='inputGoodsSupplier']").val().length > 100 ){
			$.messager.alert('警告','投入品经销商长度不能超过100。','warning');
			return false;
		}
		if($("input[name='purchaseQuantity']").val()==""){
			$.messager.alert('警告','请填写采购量。','warning');
			return false;
		}else if (!isNumber($("input[name='purchaseQuantity']").val())){
			$.messager.alert('警告','采购量请输入数字。','warning');
			return false;
		}
		if($("input[name='purchasePerson']").val()==""){
			$.messager.alert('警告','请填写采购人。','warning');
			return false;
		}
		if( $("input[name='purchasePerson']").val().length > 20 ){
			$.messager.alert('警告','采购人长度不能超过20。','warning');
			return false;
		}
		
		return true;
	}
	function showVideoList(o, d){}
</script>