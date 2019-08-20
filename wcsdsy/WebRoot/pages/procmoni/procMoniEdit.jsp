<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<title>过程监控资料管理-五常优质水稻溯源监管平台</title>
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
	<script type="text/javascript" src="${ctx}/js/upload_proc.js"></script>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">
		<form id="addFrom" class="easyui-form" method="post" data-options="novalidate:true">
		<input name="id" type="hidden" id="id" value="${id}"/>
		<input name="bizCode" type="hidden" id="bizCode" value="${bizCode}"/>
		<fieldset class="fieldset_common_style">
			<table>
				<tr>
					<td align="left">
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="return save();">保存</a>
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset class="fieldset_common_style">
			<table class="table_common_style">
				<tr>
					<td align="right">年度：</td>
					<td>
						<simple:select id="year" name="year" value="${year}" entityName="YearCode" width="170" onChange="initInfo();"/>
						<span class="span_common_mustinput_style">*</span>
					</td>
					<td align="right">企业名称：</td>
					<td>
						<!-- 实现不要默认选中 -->
						<%--simple:select id="companyCode" name="companyCode" value="${companyCode}" entityName="Company" width="170" onChange="initInfo();"/ --%>
						<c:if test="${sessionScope.isCompanyUser}">
							<simple:select id="companyCode" name="companyCode" entityName="Company" width="187" value="${sessionScope.user.companyCode}" 
							hasPleaseSelectOption="${!sessionScope.isCompanyUser}"
							readOnly="${sessionScope.isCompanyUser}" 
							canEdit="${!sessionScope.isCompanyUser}"/>
						</c:if>
						<c:if test="${!sessionScope.isCompanyUser}">
							<simple:select id="companyCode" name="companyCode" hasPleaseSelectOption="true" value="${companyCode}" entityName="Company" width="170" canEdit="true"/>
						</c:if>
						<span class="span_common_mustinput_style">*</span>
					</td>
				</tr>
				<tr>
					<td align="right">过程分类：</td>
					<td>
						<simple:select id='bizType' name="bizType" value="${bizType}" codeKey="ProcessMonitoringClass" onChange="initInfo();" entityName="commonData" hasPleaseSelectOption="true" width="170"/>
						<span class="span_common_mustinput_style">*</span>
					</td>
				</tr>
			</table>
		</fieldset>
		<fieldset class="fieldset_common_style">
			<legend>过程图片上传</legend>
			<table class="table_common_style"> 
				<tr>
					<td>
						<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:procImgUpload();">
							<span class="l-btn-left"><span class="l-btn-text icon-docupload l-btn-icon-left">上传过程图片</span>
						</a>
					</td>
					<td align="center" width="50%">过程图片预览：</td>
				</tr>
			<tr>
				<td>
					<table id="imgList" height="250" class="easyui-datagrid" striped="true" singleSelect="true">
						<thead>
							<tr>
								<th field="originalName" width="50%" align="center">过程图片</td>
								<th field="fileInfo" width="30%" align="center">过程图片描述</td>
								<th field="op" width="20%" align="center">操作</td>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</td>
				<td valign="top" align="center">
					<table class="table_common_style">
						<tr>
							<td class="imgC" align="center">
							<div class="imgList" id="imglist01">
								<ul id="imgUl">
								</ul>
								<a href="javascript:void(0)" class="imglist_arrow imglist_l"></a>
								<a href="javascript:void(0)" class="imglist_arrow imglist_r"></a>
							</div>
							</td>
						</tr>
						<tr>
							<td align="center" id="imgbtn01">
								<a href="javascript:void(0)" class="btn btn3"></a>
								<a href="javascript:void(0)" class="btn btn5" target="_blank"></a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</fieldset>
		<fieldset class="fieldset_common_style">
			<legend>过程视频上传</legend>
			<table class="table_common_style"> 
				<tr>
					<td class="table_common_td_label_style">
						过程视频URL：
					</td>
					<td class="table_common_td_txt_style">
						<input type="text" class="easyui-textbox" id="vUrl" name="vUrl" style="width:200px;">
						<span class="span_common_mustinput_style">*</span>
					</td>
					<td align="center" width="50%">过程视频预览：</td>
				</tr>
				<tr>
					<td class="table_common_td_label_style">	
						过程视频描述：
					</td>
					<td class="table_common_td_txt_style">
						<input type="text" class="easyui-textbox" id="desc" name="desc" style="width:200px;">
						<span class="span_common_mustinput_style">*</span>
					</td>
					<td rowspan="3" height="230px" align="center">
						<div class="video" id="video01">
							<a href="javascript:void(0)" class="video_arrow video_l"></a>
							<a href="javascript:void(0)" class="video_arrow video_r"></a>
							<ul id="videoUl">
							</ul>
						</div>
					</td>
				</tr>
				<tr>
					<td align="right" colspan="2">
						<a href="#" class="easyui-linkbutton" onclick="return add();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-add l-btn-icon-left">添加</span>
							</span>
						</a>
					</td>
				</tr>
				<tr>
					<td colspan="2" valign="top">
						<table height="200" id="videoList" class="easyui-datagrid" striped="true" singleSelect="true">
							<thead>
								<tr>
									<th field="videoURL" width="50%" align="center">过程视频URL</td>
									<th field="urlDesc" width="30%" align="center">过程视频描述</td>
									<th field="op" width="20%" align="center">操作</td>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		</fieldset>
		</form>
	</div>
	<div id="addDialog"></div>
	<div id="uploadDialog"></div>
<script type="text/javascript">
	$(document).ready(function(){
		$("#bizType").combobox({
			onChange: function (n,o) {
				initInfo();
			}
		});
		initInfo();
	});

	function save(){
		if( checkForm() ){
			var a = $('#addFrom').toObject();
			delete a["videoUrl"];  
			var jObj = [];
			//var videoUrl = $("input[name='videoUrl']");
			var rows = $("#videoList").datagrid("getRows");
			for( var i = 0; i < rows.length; i++ ){
				var d = {};
				d.filePath = rows[i].videoURL;
				d.fileInfo = rows[i].urlDesc;
				
				jObj.push( d );
			}
			//return;
			a.list = jObj;
			showLoading();
			Public.ajaxPost('save', JSON.stringify(a), function(e) {
				if (200 == e.status) {
					$.messager.alert('提示','保存成功。','info');
					hideLoading();
					$("#id").val(e.data.procMoniModel.id );
					$("#bizCode").val( e.data.procMoniModel.bizCode );
				} else {
					hideLoading();
					$.messager.alert('错误','操作失败！' + e.msg,'error');
				}
			});
		}
	}
	function initInfo( ){
		var year = $("#year").combobox('getValue');
		var bizType = $("#bizType").combobox('getValue');
		var companyCode  = $("#companyCode").combobox('getValue');
		if( year != "" && bizType != "" && companyCode != "" ){
			Public.ajaxGet('getInfo?year=' + year + "&bizType=" + bizType + "&companyCode=" + companyCode, {}, function(e) {
				if (200 == e.status) {
					initValue( e.data );
				} else
					$.messager.alert('错误','操作失败！' + e.msg,'error');
			});
		}
	}
	function initValue(data){
		var id = data.procMoni.id;
		var bizCode = data.procMoni.bizCode;
		var bizType = data.procMoni.bizType;
		if( id != null && id != "" && id != "null" && bizCode != "" ){
			$("#id").val( id );
			$("#bizCode").val( bizCode );
			showFileList(bizType, bizCode, '过程图片', 'imgPriviewOuter', 'imgPriviewInner' );
		}else{
			$("#id").val( "" );
			$("#bizCode").val( "" );
			clearDatasTable();//清除文件列表信息
			$('#videoList').datagrid('loadData', { total: 0, rows: [] });  //清除视频列表
		}
	}

	function clearDatasTable(){
		$('#imgList').datagrid('loadData', { total: 0, rows: [] });
	}
	
	function procImgUpload(){
		if( checkForm() ){
			if( $('#bizType').combobox('getValue') != "" ){
				var bizType = $('#bizType').combobox('getValue');
				bizType = getImgBizType(bizType);
				showUploadDialog(bizType, $('#bizCode').val(), '过程图片','imgPriviewOuter','imgPriviewInner', '');
			}else{
				$.messager.alert('警告','请选择过程分类。','warning');
				return false;
			}
		}
	}
	
	function checkForm(){
		if( $('#bizType').combobox('getValue') == "" ){
			$.messager.alert('警告','请选择过程分类。','warning');
			return false;
		}
		if( $('#year').combobox('getValue') == "" ){
			$.messager.alert('警告','请选择年度。','warning');
			return false;
		}
		if( $('#companyCode').combobox('getValue') == "" ){
			$.messager.alert('警告','请选择企业。','warning');
			return false;
		}
		
		return true;
	}
	
	//添加视频地址
	function add(){
		var vUrl = $("#vUrl").val();
		var desc = $("#desc").val();
		if( vUrl == "" ){
			$.messager.alert('警告','过程视频URL不能为空。','warning');
			return;
		}
		if( vUrl.length > 100 ){
			$.messager.alert('警告','过程视频URL长度不能超过100。','warning');
			return;
		}
		if( desc.length > 100 ){
			$.messager.alert('警告','过程描述长度不能超过100。','warning');
			return;
		}
		var index = vUrl.lastIndexOf(".");
		if(index == -1){
			$.messager.alert('警告','只支持SWF格式的视频地址。','warning');
			return false;
		}
		var extName = vUrl.substring(index+1, index + 4).toUpperCase();
		var extName1 = vUrl.substring(index+1, index + 5).toUpperCase();
		if(extName != 'SWF' && extName != 'SWF?'){
			$.messager.alert('警告','只支持SWF格式的视频地址。','warning');
			return false;
		}
		var bizCode = $('#bizCode').val();
		var bizType = $("#bizType").combobox('getValue');
		bizType = getImgBizType(bizType);
		Public.ajaxGet(root + '/file/addVideo?t=' + (new Date).getTime(), {bizType:bizType,bizCode:bizCode,filePath:vUrl,fileInfo:desc},
			function(e) {
			if (200 == e.status) {
				//重新更新本地列表
				$.messager.alert('提示','添加成功。','info');
				var bizCode = $('#bizCode').val();
				showFileList(bizType, bizCode);
			} else {
				$.messager.alert('提示','删除失败。' + e.msg,'info');
			}
		});
			
		$("#vUrl").textbox('setValue','');
		$("#desc").textbox('setValue','');
	}
	
	function getImgBizType(bizType){
		if( bizType == "01" )  bizType = "06";
		else if( bizType == "02" )  bizType = "07";
		else if( bizType == "03" )  bizType = "08";
		else if( bizType == "04" )  bizType = "09";
		else if( bizType == "05" )  bizType = "10";
		else if( bizType == "06" )  bizType = "11";
		else if( bizType == "07" )  bizType = "12";
		else if( bizType == "08" )  bizType = "13";
		return bizType;
	}
	
	//删除行
	/*function delVideo( index ){
		alert(index);
		$('#videoList').datagrid('deleteRow', index);
		$('#videoList').datagrid('reload');//删除后重新加载下
	}
	
	function rowformater( value,row,index ){
		var opHtml = "<a href='javascript:void(0);return false;' onclick='delVideo(\"" + index + "\")'>删除</a>";
		return opHtml;
	}*/
	
	function imgList(imglistid, imgbtnid){
		var imglist = $(imglistid);
		var imgbtn = $(imgbtnid);
		var imglistraw = imglist.find("li");
		var imgCount = imglistraw.length;
		var imglistul = imglist.find("ul");
		var imglistliWidth = imglist.find("li").eq(0).width();
		var i = 0;
		var leftbtn = imglist.find(".imglist_l");
		var rightbtn = imglist.find(".imglist_r");
		var imgDetailBG = $(".imgDetailBG");
		var imgDetail = $(".imgDetail");
		var w = $(window).width();
		var h = $(window).height();
		var imgDetailBtn = $(".imgDetailBtn");
		var leftrotate = imgDetailBtn.find('.btn1');
		var rightrotate = imgDetailBtn.find('.btn2');
		var zoomIn = imgDetailBtn.find(".btn4");
		var ri = 0;
		var zoomOut = imgbtn.find(".btn3");
		var yuan = imgbtn.find(".btn5");
		var imgSrc;
		var imgDesc = imglist.parents("table").find(".description[rel='pic']").find("span");

		var checkNULL = function(){
			if(imgCount == 0){
				imglistul.html("<img src='/suyuan/images/nothing.png' width='100%' height='100%'/>");
				// leftbtn.hide();
				// rightbtn.hide();
			}
		}

		var elementSize = function(){
			imglistul.width(imglistliWidth * imgCount);
			imgDetailBG.width(w).height(h);
			imgDetail.width(w * 0.8).height(0.8 * h).css({
				"left": 0.1 * w,
				"top": 0.1 * h
			});
			imgDetail.find("img").height(0.8 * h - 100);
		}

		var initleftbtn = function(){
			leftbtn.unbind("click");
			leftbtn.bind("click",function(){
				if(i == 0){
					i = imgCount -1;
				}else{
					i--;
				}
				initYuan();
				imglistul.animate({
					left: - imglistliWidth * i
				}, 300);
				initDesc();
			})
		}

		var initrightbtn = function(){
			rightbtn.unbind("click");
			rightbtn.bind("click", function(){
				if(i == imgCount - 1){
					i = 0;
				}else{
					i++;
				}
				initYuan();
				imglistul.animate({
					left: - imglistliWidth * i
				}, 300);
				initDesc();
			})
		}

		var initImgDetailBtn = function(){
			leftrotate.click(function(){
				ri--;
				imgDetail.find("img").css("transform","rotate(" + 90 * ri + "deg)");
				imgDetail.find("img").css("-ms-transform","rotate(" + 90 * ri + "deg)");
				imgDetail.find("img").css("-moz-transform","rotate(" + 90 * ri + "deg)");
			})
			rightrotate.click(function(){
				ri++;
				imgDetail.find("img").css("transform","rotate(" + 90 * ri + "deg)");
				imgDetail.find("img").css("-ms-transform","rotate(" + 90 * ri + "deg)");
				imgDetail.find("img").css("-moz-transform","rotate(" + 90 * ri + "deg)");
			})
			zoomIn.click(function(){
				imgDetail.hide();
				imgDetailBG.slideUp();
			})
		}

		var initImgListBtn = function(){
			zoomOut.click(function(){
				imgDetailBG.slideDown(300, function(){
					imgDetail.find("img").attr("src", imgSrc);
					imgDetail.show();
					ri = 0;
				});
			})
		}

		var initYuan = function(){
			imgSrc = imglistraw.eq(i).find("img").attr("src");
			yuan.attr("href", imgSrc);
		}

		var initDesc = function(){
			// imgDesc.hide();
			// imgDesc.eq(i).css("display","block");
		}

		var init = function(){
			if(imgCount == 0){
				checkNULL();
			}else{
				elementSize();
				initleftbtn();
				initrightbtn();
				// initImgDetailBtn();
				// initImgListBtn();
				// initYuan();
				// initDesc();
			}
		}
		init();
	}
	
	function videoList(videoId){

		var videolist = $(videoId);
		var videolistraw = videolist.find("li");
		var videoCount = videolistraw.length;
		var videolistul = videolist.find("ul");
		var videolistliWidth = videolist.find("li").eq(0).width();
		var i = 0;
		var leftbtn = videolist.find(".video_l");
		var rightbtn = videolist.find(".video_r");
		var w = $(window).width();
		var h = $(window).height();
		var videoDesc = videolist.parents("table").find(".description[rel='video']").find("span");

		var checkNULL = function(){
			if(videoCount == 0){
				videolistul.html("<img src='/suyuan/images/nothing1.png' width='100%' height='100%'/>");
				// leftbtn.hide();
				// rightbtn.hide();
			}
		}

		var elementSize = function(){
			videolistul.width(videolistliWidth * videoCount);
		}

		var initleftbtn = function(){
			leftbtn.unbind("click");
			leftbtn.click(function(){
				if(i == 0){
					i = videoCount - 1;
				}else{
					i--;
				}
				videolistul.animate({
					left: - videolistliWidth * i
				}, 300);
				initDesc();
			})
		}

		var initrightbtn = function(){
			rightbtn.unbind("click");
			rightbtn.click(function(){
				if(i == videoCount - 1){
					i = 0;
				}else{
					i++;
				}
				videolistul.animate({
					left: - videolistliWidth * i
				}, 300);
				initDesc();
			})
		}

		var initDesc = function(){
			videoDesc.hide();
			videoDesc.eq(i).css("display","block");
		}

		var init = function(){
			if(videoCount == 0){
				checkNULL();
			}else{
				elementSize();
				initleftbtn();
				initrightbtn();
				initDesc();
			}
		}
		init();
	}
	
	imgList("#imglist01", "#imgbtn01");
	videoList("#video01");
	
</script>
</body>
</html>

