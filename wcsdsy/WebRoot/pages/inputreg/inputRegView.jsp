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
		<input type="hidden" name="id" id="id" value="${inputReg.id}"/>
			<fieldset class="fieldset_common_style">
				<legend>采购凭证列表</legend>
				<table class="table_common_style">
					<tr>	
						<td valign="top" align="center" colspan="2">
							<div id="imgList1" >
								<table class="t1" width="100%" height="30px;" style="table-layout:fixed;border-collapse: collapse;font-size:14px;">
								<tr>
									<td align="center">采购凭证</td>
									<td align="center">文件说明</td>
								</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
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
	<script type="text/javascript">
		$(document).ready(function(){
			var id = $("#id").val(); 
			if(id != ""){
				showFileList("02", id, '采购凭证', 'imgPriviewOuter', 'imgPriviewInner' );
			}
			
			//动态调整预览图片位置
			var width = $("#fileDiv").width();
			var picWidth = $("#imgPriviewInner").width();
			var paddingLeft = (width - picWidth)/2 + "px";
			$("#fileDiv").css("padding-left", paddingLeft);
		});
		function showFileList(bizType, bizCode, title, viewDivIdOuter, viewDivIdInner){
			$.ajax({
				url: root + "/file/fileList?bizType="+bizType+"&bizCode="+bizCode,
				type: 'post',
				dataType: 'text',
				contentType : 'text/html',
				error: function (result){
			        alert('获得文件列表失败');
				},
			    async: true,
				success: function (result){
					var obj = JSON.parse(result);
					var html = '<table class="t1" width="100%" height="30px;" style="margin-top:10px;table-layout:fixed;border-collapse:collapse;">';
					html += '<tr height="30px;"><td align="center">'+ title +'</td><td align="center">文件说明</td></tr>';  //回显字符串
					
					//预览数组
					var imgPathArray = new Array();
					var imgDescArray = new Array();
					var i = 0;
					//视频信息
					var videoArray = new Array();
					var j = 0;
					for(var k in obj.data){
						var mfile = obj.data[k]; 
						if( mfile.fileType == "02" || mfile.fileType == "" ){
							html += "<tr height='30px;'><td>" + mfile.originalName + "</td>";
							html += "<td>" + mfile.fileInfo + "</td>";
							//html += "<td align='center'><a href='javascript:void(0);return false;' onclick='delFile(\""+ mfile.bizCode+ "\",\"" +  mfile.filePath + "\")'>删除</a></td></tr>";
							
							var fileID  = mfile.id;
							if( fileID == 0 || fileID < 1 ){
								imgPathArray[i] = root + "/uploadtmp/" + mfile.filePath;
							}else{
								imgPathArray[i] = root + "/upload/" + mfile.filePath;
							}
							imgDescArray[i] = mfile.fileInfo;
							i++;
						}else{
							videoArray[j] = mfile.filePath;
							j++;
						}
					}
					html += "</table>";
					$("#imgList1").html(html);
					if( imgPathArray.length > 0 ){
						reloadImageView( viewDivIdOuter, viewDivIdInner, imgPathArray, imgDescArray, 450, 500 );
					}
				}
		    });
		}
	</script>
</body>
</html>
