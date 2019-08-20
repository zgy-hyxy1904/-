<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传组件使用示例</title>
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
<script type="text/javascript" src="${ctx}/js/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:0 10px;">	
		<H2>上传组件使用说明:</H2>
		<p>
			<a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px; height:30px;" onclick="showUploadDialog();">上传</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" style="width:100px; height:30px;" onclick="showFileList();">查看已上传文件</a>
		</p>
		<H2>bizType说明</H2>
			01：  购种凭证上传</br>
			02：  投入品凭证上传</br>
			03: 收粮凭证上传</br>
			04: 特殊土地备案凭证上传</br>
			05: 特殊土地变更凭证上传</br>
			06: 浸种催芽图片上传</br>
			07: 育秧环节图片上传</br>
			08: 插秧环节图片上传</br>
			09: 植保环节图片上传</br>
			10: 收割环节图片上传</br>
			11: 物流环节图片上传</br>
			12: 仓储环节图片上传</br>
			13: 加工环节图片上传</br>
		<p>
		<H2>bizCode说明</H2>
		<p>
			该图片关联的业务id，如业务数据未保存时取文件列表和删除临时文件时，传空串
		<p>
		<div id="imgList"></div>
		<div id="uploadDialog"></div>
	</div>
<script type="text/javascript">
	var bizType = '01';
	//uploadDlgId: 上传对话框的id(如果id=uploadDialog可不传) 
	function showUploadDialog(){
		$('#uploadDialog').dialog({
		    title: '文件上传',
		    width: 540,
		    height: 400,
		    closed: false,
		    cache: false,
		    href: '${ctx}/file/uploadInit?bizType='+bizType+"&uploadDlgId=uploadDialog",
		    modal: true,
		    onClose : function(){
		    	showFileList();
		    }
		});
	}
	
	function showFileList(){
		$.ajax({
			url: "${ctx}/file/fileList?bizType="+bizType+"&bizCode=",
			type: 'post',
			dataType: 'text',
			contentType : 'text/html',
			error: function (result){
		        alert('获取文件列表失败');
			},
		    async: true,
			success: function (result){
				var obj = JSON.parse(result);
				var html = '<table class="tab_upload" style="width:100%; margin-top:10px;">';
				html += '<tr><td>文件名称</td><td>文件说明</td><td>操作</td></tr>';
				for(var k in obj.data){
					var mfile = obj.data[k];
					html += "<tr><td>" + mfile.originalName + "</td>";
					html += "<td>" + mfile.fileInfo + "</td>";
					html += "<td><a href='javascript:void(0);return false;' onclick='delFile(\""+ mfile.bizCode+ "\",\"" +  mfile.filePath + "\")'>删除</a></td></tr>";
				}
				html += "</table>";
				$("#imgList").html(html);
			}
	    });
	}
	
	//bizCode如果没有就传空串
	function delFile(bizCode, filePath){
		//bizCode 为空时，传空串
		Public.ajaxGet('${ctx}/file/deleteFile', {bizType : bizType, bizCode : bizCode, filePath : filePath},
			function(e) {
			if (200 == e.status) {
				//重新更新本地列表
				alert('删除成功');
				showFileList();
			} else {
				parent.parent.Public.tips({
					type : 1,
					content : "失败！" + e.msg
				});
			}
		});
	}
</script>
</body>
</html>