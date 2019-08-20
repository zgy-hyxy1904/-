<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
	<title>文件上传-五常优质水稻溯源监管平台</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/style/index.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/color.css">
	<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/js/form2js.js"></script>
	<script type="text/javascript" src="${ctx}/js/json2.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
</head>
<body>
	<div region="center" border="false" style="padding:5px;">
		<form>
			<fieldset class="fieldset_common_style">
				<input type="hidden" id="bizType" name="bizType" value="${bizType}" />
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_style">选择要导入的Excel文件：</td>
						<td class="table_common_td_txt_style">
							<input type="file" id="file" name="file" size="25"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<div id="pbar" align="center" style="width:300px;height:30px; margin-top:10px;"></div>
						</td>
					</tr>
					<tr height="30">
						<td colspan="2" align="center">
							<a href="#" id="uploadDiv" class="easyui-linkbutton" style="height:30px; width:80px;" onclick="upload()">上传</a>
							<a href="#" class="easyui-linkbutton" style="margin-left:40px;height:30px; width:80px;" onclick="closeUploadDialog()">关闭</a>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
		<fieldset class="fieldset_common_style">
		<legend>错误信息</legend>
		<table id="excelImportData" class="easyui-datagrid" striped="true" singleSelect="true" style="height:290px;">
			<thead>
				<tr>
					<th field="rowNo" width="20%"  align="center">行号</th>
					<th field="idCard" width="40%" align="center">身份证</th>
					<th field="errorMsg" width="40%"  align="center">错误信息</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		</fieldset>
		<div id="uploadDiv" style="100%" align="center"></div>
    </div>
<script type="text/javascript">
var timer;
	$(document).ready(function(){

	});
	function upload(){
		var file = $("#file").val();
		if(file == ''){
			alert('请先选择文件！');
			return false;
		}
		var year = "${year}";
		var companyCode = "${companyCode}";
		var companyName = "${companyName}";
		$("#pbar").html("正在努力导入中，请稍候...");
		//showLoading();
		$.ajaxFileUpload({
			url:"${ctx}/graiFore/uploadExcel?year="+year+"&companyCode=" + companyCode + "&companyName=" + companyName,
			secureuri:false,
			fileElementId:'file',
			dataType: 'text',
			success: function (data, status){
				//如果格式错误显示错误列表
				var jsonObj = JSON.parse(data);
				if(jsonObj.msg == 'formatError'){
					if(jsonObj.data.errorFlag == 2){
						alert(jsonObj.data.errorMsg);
					}
					$('#excelImportData').datagrid('loadData', { total: 0, rows: [] });
					for(var k in jsonObj.data.errorList){
						var error = jsonObj.data.errorList[k];
						$('#excelImportData').datagrid('appendRow', {
							rowNo: error.rowNo,
							idCard : error.idNum,
							errorMsg: error.msg
						});
					}
					$("#pbar").html("");
					return false;
				}
				$.messager.alert("提示", jsonObj.msg, "info", function () {  
					if(data.status == 200){
						$("#pbar").html("");
				        $('#importDialog').dialog('close'); 
						form_check();						
					} else {
						$("#pbar").html("");
					}
		        });
			},
			error: function (data, status){ 
				$("#pbar").html("");
				var jsonObj = JSON.parse(data);
	        	$.messager.alert('错误','导入失败！\n' + jsonObj.msg, 'error');
			}
		});
	}
	function closeUploadDialog(){
		$('#importDialog').dialog('close');
	}
</script>
</body>
</html>