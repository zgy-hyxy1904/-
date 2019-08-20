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
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">
		<form id="addFrom" class="easyui-form" method="post" data-options="novalidate:true">
			<input type="hidden" name="id" id="id" value="${companyCPLT.id}"/>
			<fieldset class="fieldset_common_style">
			<table id="pzlist" width="100%" class="easyui-datagrid" striped="true" singleSelect="true" style="height:100px;">
				<thead>
					<tr>
						<th field="originalName" width="50%"  align="center">处理凭证</th>
						<th field="fileInfo" width="50%" align="center">说明</th>
					</tr>
				</thead>
			</table>
		</fieldset>
		<fieldset class="fieldset_common_style">
			<table id="fileDiv" class="table_common_style">
				<tr>
					<td>
						<simple:imgView outerDivId="imgPriviewOuter" innerDivId="imgPriviewInner" imgInfoMaps="imgPathAndInfoMaps" width="450" height="450"></simple:imgView>
					</td>
				</tr>
			</table>
		</fieldset>
		</form>
	</div>
	<script type="text/javascript">
		$(document).ready(function(){
			var id = $("#id").val(); 
			if(id != ""){
				showFileList("18", id, '处理凭证', 'imgPriviewOuter', 'imgPriviewInner' );
				
			}
			//动态调整预览图片位置
			var width = $("#fileDiv").width();
			var picWidth = $("#imgPriviewInner").width();
			var paddingLeft = (width - picWidth)/2 + "px";
			$("#fileDiv").css("padding-left", paddingLeft);
		});
		function showFileList(bizType, bizCode, title, viewDivIdOuter, viewDivIdInner){
			$.ajax({
				url: "${ctx}/file/fileList?bizType="+bizType+"&bizCode="+bizCode,
				type: 'post',
				dataType: 'text',
				contentType : 'text/html',
				error: function (result){
			        $.messager.alert('错误','获得文件列表失败。','error');
				},
			    async: true,
				success: function (result){
					var obj = JSON.parse(result);
					//预览数组
					var imgPathArray = new Array();
					var imgDescArray = new Array();
					var i = 0;
					for(var k in obj.data){
						var mfile = obj.data[k]; 
						if( mfile.fileType == "01" || mfile.fileType == "" ){
							$('#pzlist').datagrid('appendRow', {
								originalName: mfile.originalName,
								fileInfo : mfile.fileInfo
							});
							
							var fileID  = mfile.id;
							if( fileID == 0 || fileID < 1 ){
								imgPathArray[i] = "${ctx}/uploadtmp/" + mfile.filePath;
							}else{
								imgPathArray[i] = "${ctx}/upload/" + mfile.filePath;
							}
							imgDescArray[i] = mfile.fileInfo;
							i++;
						}
					}
					if( imgPathArray.length > 0 ){
						reloadImageView( viewDivIdOuter, viewDivIdInner, imgPathArray, imgDescArray, 450, 450);
					}
				}
		    });
		}
	</script>
</body>
</html>
