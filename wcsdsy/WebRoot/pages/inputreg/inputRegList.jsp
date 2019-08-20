<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
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
<!-- 图片播放 -->
<link rel="stylesheet" type="text/css" href="${ctx}/style/jquery.fancybox.css" media="screen" />
<link rel="stylesheet" type="text/css" href="${ctx}/style/jquery.fancybox-buttons.css" />
<script type="text/javascript" src="${ctx}/js/jquery.fancybox.js"></script>	
<script type="text/javascript" src="${ctx}/js/jquery.fancybox-buttons.js"></script>
	<script>
		var root = "${ctx}";
	</script>
	<script type="text/javascript" src="${ctx}/js/upload.js"></script>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">
		<fieldset id="queryBlock" class="fieldset_common_style">
			<form id="inputForm" name="inputForm" method="get" action="../inputReg/list">
				<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
				<input type="hidden" id="page" name="page" value="${pageModel.page}">
				<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
				
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_query_style">年度：</td>
						<td class="table_common_td_txt_query_style editableFalse">
							<%@ include file="../../yearComm.jsp" %>
						</td>
						<td class="table_common_td_label_query_style">企业：</td>
						<td class="table_common_td_txt_query_style">
							<%@ include file="../../companyComm.jsp" %>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_query_style">采购日期：</td>
						<td colspan="2" class="table_common_td_txt_query_style editableFalse">
							<input class="easyui-datebox" id="beginDate" name="beginDate" value="${beginDate }"
	            				data-options="required:false,showSeconds:false" style="width:100px" editable="false"/>
							~
							<input class="easyui-datebox" id="endDate" name="endDate" value="${endDate }"
	            				data-options="required:false,showSeconds:false" style="width:100px" editable="false"/>
						</td>
						<td align="right" valign="bottom">
	                       	<a href="#" class="easyui-linkbutton" onclick="return form_check();">
	                       		<span class="l-btn-left"><span class="l-btn-text icon-search l-btn-icon-left">查询</span></span>
	                       	</a>
	             		</td>
					</tr>
				</table>
				
			</form>
		</fieldset>
		<fieldset id="toolBlock" class="fieldset_common_style">
			<table>
				<tr>
					<td>
						<a href="#" class="easyui-linkbutton" onclick="return viewInfo();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-view l-btn-icon-left">查看</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="return add();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-add l-btn-icon-left">添加</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="return edit();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-edit l-btn-icon-left">修改</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="return deleteRecord();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-remove l-btn-icon-left">删除</span>
							</span>
						</a>
					</td>
				</tr>
			</table>
		</fieldset>	
		<table id="data" class="easyui-datagrid" striped="true" singleSelect="false" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="id" width="140" align="center" checkbox="true"></th>
					<th field="ApplyBatchNo" width="130" align="center">流水号</th>
					<th field="year" width="60" align="center">年度</th>
					<th field="companyName" width="200" align="center">企业</th>
					<th field="inputName" width="120" align="center">投入品名称</th>
					<th field="inpuDealer" width="120" align="center">投入品经销商</th>
					<th field="purchaseQuantity" width="80" align="center">采购量</th>
					<th field="purchasingAgent" width="70" align="center">采购人</th>
					<th field="purchaseDate" width="120" align="center">采购日期</th>
					<th field="view" width="80" align="center">凭证查看</th>
					<th field="CreateUserId" width="100" align="center">登记人</th>
					<th field="CreateDate" width="150" align="center">登记时间</th> 
				</tr>
			</thead>
			<tbody>
				<c:forEach var="inputReg" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${inputReg.id}</td>
					<td height="30" align="center" nowrap>${inputReg.applyBatchNo}</td>
					<td height="30" align="center" nowrap>${inputReg.year}</td>
					<td height="30" align="center" nowrap>${inputReg.companyName}</td>
					<td height="30" align="center" nowrap>${inputReg.inputGoodsName}</td>
					<td height="30" align="center" nowrap>${inputReg.inputGoodsSupplier}</td>
					<td height="30" align="center" nowrap>${inputReg.purchaseQuantity}
					<simple:showName entityName="commonData" codeKey="InputMaterialUnit" value="${inputReg.inputGoodsUnit}"></simple:showName>
					</td>
					<td height="30" align="center" nowrap>${inputReg.purchasePerson}</td>
					<td height="30" align="center" nowrap>
						<fmt:formatDate value="${inputReg.purchaseDate}" pattern="yyyy-MM-dd"/>
					</td>
					<td height="30" align="center" nowrap><a href="javascript:void(0)" onclick="return view('${inputReg.id}');">凭证查看</a></td>
					<td height="30" align="center" nowrap>${inputReg.createUserId}</td>
					<td height="30" align="center" nowrap>
						<fmt:formatDate value="${inputReg.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				</c:forEach>		
			</tbody>
		</table>
	</div>
	<div id="addDialog"></div>
	<div id="showImages" style="display:none"></div>
	
<script type="text/javascript">
$(document).ready(function(){
	var winHeight = $(window).height();
    var queryBlockHeight = $("#queryBlock").height();
    var toolBlock = $("#toolBlock").height();
	$("#data").datagrid({ 
		height:winHeight -queryBlockHeight -toolBlock-50,
		pagination: true,
        rownumbers: true,
        fitColumns: false,
        //fit: true,
        pageList: [15,20,25],
        pagePosition: "top"
	});
	
	var pagger = $('#data').datagrid('getPager');  
	$(pagger).pagination({
		total: $("#pageTotal").val(),
		pageNumber: parseInt($("#page").val()),
		showPageList: true,
		showRefresh:false,
	    onSelectPage: function(pageNumber, pageSize){
	    	$('#page').val(pageNumber);
	    	$('#pageSize').val(pageSize);
	    	$('#inputForm').submit();
	    }
	});
	
	$('.fancybox-buttons').fancybox({
		openEffect  : 'none',
		closeEffect : 'none',

		prevEffect : 'none',
		nextEffect : 'none',

		closeBtn  : true,

		helpers : {
			title : {
				type : 'inside'
			},
			buttons	: {}
		},

		afterLoad : function() {
			this.title = 'Image ' + (this.index + 1) + ' of ' + this.group.length + (this.title ? ' - ' + this.title : '');
		}
	});
});

function deleteRecord(id){
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length; 
	if(length < 1){
		$.messager.alert('警告','请至少选择一条记录。','warning');
		return false;
	}
	$.messager.confirm("确认", "您确认删除选定的记录吗？", function (deleteAction) {
	    if (!deleteAction) {
			return false;
	    }
	    showLoading();
	    var ids = [];
		var rows = $('#data').datagrid('getSelections');
		var length = rows.length;
		for(var i=0; i<rows.length; i++) ids.push(rows[i].id);
		Public.ajaxGet('delete', {ids : ids}, function(e) {
			if (200 == e.status) {
				form_check();
			} else{
				hideLoading();
				$.messager.alert('错误','保存失败！' + e.msg,'error');
			}
		});
	});
}

function edit(id){
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length == 0 || length > 1 ){
		$.messager.alert('警告','必须且只能选择一条记录。','warning');
		return false;
	}
	var id = rows[0].id;
	var page = $("#page").val();
	var pageSize = $("#pageSize").val();
	var year = $("#year").combobox("getValue");
	var companyCode = $("#companyCode").combobox("getValue");
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	var url = "${ctx}/inputReg/editInput?retFlag=1";
	url += "&id=" + id;
	url += "&page=" + page;
	url += "&pageSize=" + pageSize;
	url += "&year=" + year;
	url += "&companyCode=" + companyCode;
	url += "&beginDate=" + beginDate;
	url += "&endDate=" + endDate;
	
	document.location.href = url;
}
function add(id){
	var page = $("#page").val();
	var pageSize = $("#pageSize").val();
	var year = $("#year").combobox("getValue");
	var companyCode = $("#companyCode").combobox("getValue");
	var beginDate = $("#beginDate").val();
	var endDate = $("#endDate").val();
	
	var url = "${ctx}/inputReg/editInput?retFlag=1";
	url += "&page=" + page;
	url += "&pageSize=" + pageSize;
	url += "&year=" + year;
	url += "&companyCode=" + companyCode;
	url += "&beginDate=" + beginDate;
	url += "&endDate=" + endDate;
	
	document.location.href = url;
}
function viewInfo(){
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length == 0 || length > 1){
		$.messager.alert('警告','必须且只能选择一条记录。','warning');
		return false;
	}
	var id = rows[0].id;
	var year = $('#year').combobox('getValue');
	var companyCode = $('#companyCode').combobox('getValue');
	var beginDate = $('#beginDate').val();
	var endDate = $('#endDate').val();
	
	var url = "${ctx}/inputReg/viewInfo?id=" + id + "&retFlag=1";
	url += "&year=" + year;
	url += "&companyCode=" + companyCode;
	url += "&beginDate=" + beginDate;
	url += "&endDate=" + endDate;
	
	document.location.href = url;
}
function closeEdiDialog(){
	$('#provEvalEditDlg').dialog('close');
}

function form_check(){
	var beginDate =$.trim($("#beginDate").datebox('getValue'));
	var endDate =$.trim($("#endDate").datebox('getValue'));
	if(!dateCompare(beginDate,endDate)){
		$.messager.alert('警告','采购日期开始日不能大于结束日。','warning');	
		return false;
	}
	showLoading();
	$('#inputForm').submit();
}


//Modify By ChenQingxin 投入品凭证查看
function view( id ){
	showFileViews("02", id, '采购凭证', 'imgPriviewOuter', 'imgPriviewInner' );
}

function showFileViews(bizType, bizCode, title, viewDivIdOuter, viewDivIdInner){
	var alink;
	var showDiv = document.getElementById('showImages');
	showLoading();
	$.ajax({
		url: root + "/file/fileList?bizType="+bizType+"&bizCode="+bizCode,
		type: 'post',
		dataType: 'text',
		contentType : 'text/html',
		error: function (result){
	        $.messager.alert('错误','获得文件列表失败。','error');
		},
	    async: true,
		success: function (result){
			hideLoading();
			showDiv.innerHTML = '';
			var obj = JSON.parse(result);
			if(obj.data.length>0){
				for(var i=obj.data.length-1;i>0;i--){
					var mfile = obj.data[i]; 
					if( mfile.fileType == "02" || mfile.fileType == "" ){
						var filePath = ( mfile.id == 0 || mfile.id < 1 ) ? root + "/uploadtmp/" + mfile.filePath : root + "/upload/" + mfile.filePath;
						showDiv.innerHTML += '<a class="fancybox-buttons" data-fancybox-group="button" href="'+ filePath + '" title="'+ mfile.fileInfo +'">'+ mfile.originalName +'</a>';
					}
				}
				var fp = ( obj.data[0].id == 0 || obj.data[0].id < 1 ) ? root + "/uploadtmp/" + obj.data[0].filePath : root + "/upload/" + obj.data[0].filePath;
				showDiv.innerHTML += '<a class="fancybox-buttons" id="showImgs" data-fancybox-group="button" href="'+ fp + '" title="'+ obj.data[0].fileInfo +'">'+ obj.data[0].originalName +'</a>';
				document.getElementById('showImgs').click();
			}else{
				 $.messager.alert('警告','该投入品没有可供查看的凭证!','warning');
			}
		}
    });
}
</script>
</body>
</html>