<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
	<title>文件上传-五常优质水稻溯源监管平台</title>
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
</head>
<body>
	<div region="center" border="false" style="padding:0 10px;">
		<form id="addFrom" class="easyui-form" method="post" data-options="novalidate:true">
		<input type="hidden" name="id" id="id" value="${companyCPLT.id}"/>
		<input type="hidden" name="companyCode" id="companyCode" value="${companyCPLT.companyCode}"/>
		
		<table class="n_input" border="0" align="center" width="95%">
			<tr>
				<td>
					<fieldset class="fieldset_common_style">
						<a href="#" class="easyui-linkbutton" onclick="return save();">
							<span class="l-btn-left">
							<span class="l-btn-text icon-save l-btn-icon-left">保存</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="return closeEdiDialog();">
							<span class="l-btn-left">
							<span class="l-btn-text icon-cancel l-btn-icon-left">关闭</span>
							</span>
						</a>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td valign="top">
					<fieldset class="fieldset_common_style">
					<table width="100%" border="0">
						<tr>
							<td align="right" width="10%">投诉日期：</td>
							<td width="25%">
								<input class="easyui-datebox" name="complaintDate"  value="${companyCPLT.complaintDate}"
                                     data-options="required:true,showSeconds:false" style="width:170px" readOnly>
							</td>
							<td align="right" width="10%">投诉人：</td>
							<td align="left" width="25%">
								<input class="easyui-textbox"  type="text" name="complainant" value="${companyCPLT.complainant }" style="width:170px; " readOnly></input>
							</td>
						</tr>
						<tr>
							<td align="right" width="10%">联系电话：</td>
							<td>
								<input class="easyui-textbox"  type="text" id="complainantTel" name="complainantTel" value="${companyCPLT.complainantTel }" style="width:170px;" readOnly></input>
							</td>
							<td align="right" width="10%">联系邮箱：</td>
							<td>
								<input class="easyui-textbox" type="text" id="complainantMail" name="complainantMail" value="${companyCPLT.complainantMail}" style="width:170px;" readOnly></input>
							</td>
						</tr>
						<tr>
							<td align="right" width="10%">诉投内容：</td>
							<td colspan="3">
								<textarea class="easyui-textbox" rows="2" style="width:480px;height:60px" data-options="multiline:true" readOnly>${companyCPLT.complaintContent}</textarea>
							</td>
						</tr>
					</table>
					</fieldset>
				</td>
			</tr>
			<tr>
				<td>
					<fieldset class="fieldset_common_style">
					<table width="100%" border="0">
						<tr>
							<td align="right" width="10%">处理企业：</td>
							<td width="25%">
								<simple:select id="companyCode1" name="companyCode" codeKey="02" hasPleaseSelectOption="true" entityName="Company" width="170" value="${companyCode}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="10%">处理方式：</td>
							<td align="left" width="25%">
								<fieldset class="fieldset_common_style">
									<input type="checkbox" name="processMode" id="processMode" value="01"/>警告<input type="checkbox" name="processMode" value="02"/>责令停产停业
									<input type="checkbox" name="processMode" value="03"/>罚款   <br />
									<input type="checkbox" name="processMode" id="processMode" value="04"/>暂扣或者吊销许可证、暂扣或者吊销执照
									<input type="checkbox" name="processMode" value="05"/>其它   <br />
								</fieldset>
							</td>
						</tr>
						<tr>
							<td align="right" width="10%">处理结果：</td>
							<td>
								<textarea class="easyui-textbox" rows="2" id="processResult" name="processResult" style="width:500px;height:60px" data-options="multiline:true">${companyCPLT.processResult}</textarea>
							</td>
						</tr>
						<tr>
							<td align="right" width="10%">相关附件：</td>
							<td>
								<a href="javascript:void(0)" class="easyui-linkbutton" style="margin-left:1px; width:100px; height:30px;" onclick="javascript:uploadInfo();">上传附件</a>
							</td>
						</tr>
						<tr>
							<td></td>
							<td>
								<table id="pzlist" width="100%" class="easyui-datagrid" striped="true" singleSelect="true" style="height:100px;">
									<thead>
										<tr>
											<th field="originalName" width="40%"  align="center">处理凭证</th>
											<th field="fileInfo" width="40%" align="center">说明</th>
											<th field="op" width="20%" align="center">操作</th>
										</tr>
									</thead>
								</table>
							</td>
						</tr>
						<tr>
							<td align="right" width="10%" nowrap="nowrap">生产批次</td>
							<td><input type="text" class="easyui-textbox" id="batchNo" name="batchNo" value="${batchNo }"/>
							&nbsp;&nbsp;产品名称：
							 <select id="productCode" name="productCode" style="width:170px;">
							</select>
							</td>
						</tr>
						<tr>
							<td align="right" width="10%" nowrap="nowrap">
							<input type="checkbox" value="1" onclick="javascript:process(this);" name="addBlackList" id="addBlackList"/>加入黑名单</td>
							<td>
							拉黑时长：<simple:select id='blackListTimeLimit' name="blackListTimeLimit" value="${blackListTimeLimit}" codeKey="BlackListDuration" entityName="commonData" hasPleaseSelectOption="false" width="170"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="10%">
							<input type="checkbox" onclick="processRecall(this);" value="1" name="recall" id="recall"/>产品召回
							</td>
							<td valign="top">
							&nbsp;&nbsp;召回原因：<textarea cols="30" rows="2" id="recallReason" name="recallReason">${recallReason }</textarea>
							</td>
						</tr>
						<tr>
							<td align="right" width="10%">处理日期：</td>
							<td>
								<input class="easyui-datebox" name="settleDate"  value="${companyCPLT.settleDate}" data-options="required:true,showSeconds:false,editable:false" style="width:170px">
            					&nbsp;&nbsp;&nbsp;&nbsp;处理人：<input type="text" name="processor" id="processor" value="${companyCPLT.processor}"/>
							</td>
						</tr>
						<tr>
							<td align="right" width="10%">处理人电话：</td>
							<td>
								<input type="text" name="processorsTel" id="processorsTel" value="${companyCPLT.processorsTel }" style="width:168px"/>
								&nbsp;&nbsp;&nbsp;处理人邮箱：<input type="text" name="processorsMail" id="processorsMail" value="${companyCPLT.processorsMail }"/>
							</td>
						</tr>
					</table>
					</fieldset>
				</td>
			</tr>
		</table>
		</form>
	</div>
<script type="text/javascript">
	$(document).ready(function(){
		//加入黑名单
		var addBlackList = "${addBlackList}";
		if( addBlackList == "1" ){
			document.getElementById("addBlackList").checked = true;
		}
		//召回
		var recall = "${recall}";
		if( recall == "1" ){
			document.getElementById("recall").checked = true;
		}
		
		var qx = $("input[name='processMode']");
		//初始化处理方式
		var processorMode = "${companyCPLT.processMode}";
		for( var i = 0 ; i < qx.length; i++ ){
			if( processorMode.indexOf( qx[i].value ) != -1 ){
				qx[i].checked = true;
			}
		}  
		
		$("#companyCode1").combobox({
			onChange: function (n,o) {
				initProduct();
			}
		});
		initProduct();
		process( document.getElementById("addBlackList") );
		processRecall( $("#recall") );
		//初始化附件
		showFileList("18", $("#id").val(), '附件');
		
		setTimeout(function(){
			  $('#blackListTimeLimit').combobox('disable'); 
			},0);
	});
	
	function initProduct(){
		var companyCode = $("#companyCode1").combobox('getValue');
		Public.ajaxPost('getProductInfo?companyCode=' + companyCode, [], function(e) {
			if (200 == e.status) {
				initPro( e.data );
			} else {
				$.messager.alert('错误','失败！'+ e.msg,'error');
			}
		});
	}
	
	function initPro( data ){
		col_clear();
		var pList = data.product;
		for( var d in pList ){
			col_add( pList[d].productName, pList[d].productCode );
		}
	}
	
	function save(){
		var a = $('#addFrom').toObject();
		var qx = $("input[name='processMode']:checked").map(function () {
	        return $(this).val();
	    }).get().join(',');
	    if( qx == "" ){
	    	$.messager.alert('警告','必须选择至少一项处理方式。','warning');
	    	return false;
	    }
	    a.processMode = qx;
	    if( $("#addBlackList").attr('checked') ){//加入黑名单
	    	if( $("#blackListTimeLimit").combobox("getValue") == "" ){
	    		$.messager.alert('警告','拉黑时长不能为空。','warning');
	    		return false;
	    	}
	    }
	    if( $("#settleDate").val() == "" ){
	    	$.messager.alert('警告','处理日期不能为空。','warning');
	    	return false;
	    }
	    if( $("#processor").val() == "" ){
	    	$.messager.alert('警告','处理人不能为空。','warning');
	    	return false;
	    }
	    if( $("#processorsTel").val() == "" ){
	    	$.messager.alert('警告','处理人电话不能为空。','warning');
	    	return false;
	    }
		if(!isTel($("#processorsTel").val())){
			$.messager.alert('警告','请填写正确的处理人电话号码。','warning');			
			return false;
		}
		var processorsmailbox = $.trim($("#processorsMail").val());
		if(processorsmailbox!=""&&processorsmailbox!=null&&!isEmail(processorsmailbox)){
			$.messager.alert('警告','请填写正确处理人邮箱地址。','warning');			
			return false;
		}
        if( $("#recall").attr('checked') ){  //召回
	    	if(!$("#addBlackList").attr('checked')){
	    		$.messager.alert('警告','有召回产品业务的企业必须加入企业黑名单。','warning');
                return false;
	    	}
	    	if( $("#batchNo").val() == "" ){
	    		$.messager.alert('警告','召回产品的生产批次不能为空。','warning');
	    		return false;
	    	}
	    	if( $("#recallReason").val() == "" ){
	    		$.messager.alert('警告','召回原因不能为空。','warning');
	    		return false;
	    	}
	    }
        showLoading();
		Public.ajaxPost('save', JSON.stringify(a), function(e) {
			hideLoading();
			if (200 == e.status) {
				$.messager.alert('提示','保存成功。','info');
				closeEdiDialog();
				form_check();
			} else {
				$.messager.alert('错误','保存失败！'+ e.msg,'error');
			}
		});
	}
	// 清空
	function col_clear() {
 		var selOpt = $("#productCode option");
 		selOpt.remove();
	}
	// 添加
	function col_add( text, value ) {
 		var selObj = $("#productCode");
 		selObj.append("<option value='"+value+"'>"+text+"</option>");
	}
	function process(obj){
		if( obj.checked ){
			$("#blackListTimeLimit").combobox('enable');
		}else{
			$("#blackListTimeLimit").combobox('disable');
		}
	}
	function processRecall(obj){
		if( obj.checked ){
			$("#addBlackList").attr('checked', true);
			$("#blackListTimeLimit").combobox('enable');
			$("#recallReason").removeAttr('disabled');
		}else{
			$("#recallReason").attr('disabled',"disabled");
		}
	}
	//上传资料相关
	function uploadInfo(){
		showUploadDialog('18', '${companyCPLT.id}', '资料');
	}
	function showUploadDialog(bizType, bizCode, title){
		$('#uploadDialog').dialog({
		    title: '文件上传',
		    width: 540,
		    height: 400,
		    closed: false,
		    cache: false,
		    href: '${ctx}/file/uploadInit?bizType='+bizType,
		    modal: true,
		    onClose : function(){
		    	showFileList( bizType, bizCode, title );
		    }
		});
	}
	
	function showFileList( bizType, bizCode, title ){
		$.ajax({
			url: "${ctx}/file/fileList?bizType="+bizType+"&bizCode=",
			type: 'post',
			dataType: 'text',
			contentType : 'text/html',
			error: function (result){
				$.messager.alert('错误','获得文件列表失败。','error');
			},
		    async: true,
			success: function (result){
				var obj = JSON.parse(result);
				$('#pzlist').datagrid('loadData', { total: 0, rows: [] });
				for(var k in obj.data){
					var mfile = obj.data[k];			
					var opHtml = "<a href='javascript:void(0);return false;' onclick='delFile(\""+ bizType +"\",\""+ mfile.bizCode+ "\",\"" +  mfile.filePath + "\")'>删除</a>";
					$('#pzlist').datagrid('appendRow', {
						originalName: mfile.originalName,
						fileInfo : mfile.fileInfo,
						op : opHtml
					});
				}
			}
	    });
	}
	//bizCode如果没有就传空串
	function delFile(bizType, bizCode, filePath){
		if(!confirm("您确定要删除吗？")) return false;
		//bizCode 为空时，传空串
		Public.ajaxGet('${ctx}/file/deleteFile', {bizType : bizType, bizCode : bizCode, filePath : filePath},
			function(e) {
			if (200 == e.status) {
				//重新更新本地列表
				$.messager.alert('提示','删除成功','info');
				showFileList( bizType, bizCode, '资料' );
			} else {
				$.messager.alert('错误','删除失败！' + e.msg,'error');
			}
		});
	}
	function closeEdiDialog()
	{
		$('#addDialog').dialog('close');
	}
</script>
<div id="uploadDialog"></div>
</body>
</html>