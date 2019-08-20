<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>   
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">
		<fieldset id="queryBlock" class="fieldset_common_style">
			<form id="landpicform" name="landpicform" method="post" action="${ctx}/landPic/list?flag=2">
			<table class="table_common_style">
				<tr>
					<td class="table_common_td_label_query_style">年度：</td>
					<td>
						<simple:select id="year" name="year" entityName="yearcode" value="${year}" width="170" canEdit="false"/>
						<input type='hidden' id="pageTotal" name="pageTotal" value="${pageModel.totalCount}" />
						<input type="hidden" id="page" name="page" value="${pageModel.page}">
						<input type="hidden" id="pageSize" name="pageSize" value="${pageModel.pageSize}">
					</td>
					<td class="table_common_td_label_query_style">企业名称：</td>
					<td nowrap>
						<simple:select id="companyCode" name="companyCode" value="${companyCode}" entityName="company" width="200" hasPleaseSelectOption="${!sessionScope.isCompanyUser}" readOnly="${sessionScope.isCompanyUser}" canEdit="${!sessionScope.isCompanyUser}" />
					</td>
					<td align="right" valign="bottom">
                       	<a href="#" class="easyui-linkbutton" onclick="onSubmit()">
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
						<a href="#" class="easyui-linkbutton" onclick="viewLandPic();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-view l-btn-icon-left">查看</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="addLandPic();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-add l-btn-icon-left">添加</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="editLandPic();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-edit l-btn-icon-left">修改</span>
							</span>
						</a>
						<a href="#" class="easyui-linkbutton" onclick="deleteLandPic();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-remove l-btn-icon-left">删除</span>
							</span>
						</a>
					</td>
				</tr>
			</table>	
		</fieldset>
		<table id="data" class="easyui-datagrid" striped="true" checkbox="true" singleSelect="false" singleSelect="true" pageSize="${pageModel.pageSize}" pageNumber="${pageModel.page}">
			<thead>
				<tr>
					<th field="id" width="140" checkbox="true"  align="center"></th>
					<th field="cityName" width="140"  align="center">市 </th>
					<th field="townName" width="140" align="center">乡镇</th>
					<th field="picInfo" width="140"  align="center">图片说明</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="landPic" items="${pageModel.result}" varStatus="status">
				<tr>
					<td height="30" align="center" nowrap>${landPic.ID}</td>
					<td height="30" align="center" nowrap>${landPic.CityName}</td>
					<td height="30" align="center" nowrap>${landPic.TownName}</td>
					<td height="30" align="center" nowrap>${landPic.PicInfo}</td>
				</tr>
				</c:forEach>		
			</tbody>
		</table>
	</div>
	<div id="addDialog"></div>
<script type="text/javascript">
	$(document).ready(function(){
		var winHeight = $(window).height();
	    var queryBlockHeight = $("#queryBlock").height();
	    var toolBlock = $("#toolBlock").height();
		$("#data").datagrid({ 
			height:winHeight -queryBlockHeight -toolBlock-48,
			pagination: true,
	        rownumbers: true,
	        fitColumns: true,
	        //fit: true,
	        pageList: [15,20,25],
	        pagePosition: "top"
	});
	
	var pagger = $('#data').datagrid('getPager');  
	$(pagger).pagination({
		total: $("#pageTotal").val(),
		pageNumber: parseInt($("#page").val()),
		showPageList: true,
		showRefresh: false,
	    onSelectPage: function(pageNumber, pageSize){
	    	$('#page').val(pageNumber);
	    	$('#pageSize').val(pageSize);
	    	onSubmit();
	    }
	});
});
	
function addLandPic(){
	var year = $("#year").val();
	var companyCode = $("#companyCode").val();
	$('#addDialog').dialog({
	    title: '添加土地图片',
	    width: 800,
	    height: 380,
	    closed: false,
	    cache: false,
	    href: '${ctx}/landPic/addPic?year='+year+"&companyCode="+companyCode,
	    modal: true
	});
}

function viewLandPic(){
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length == 0){
		$.messager.alert('警告','请选择一条记录。','warning');
		return false;
	}
	if(length > 1){
		$.messager.alert('警告','只能选择一条记录。','warning');
		return false;
	}
	var id = rows[0].id;
	$('#addDialog').dialog({
	    title: '查看土地图片',
	    width: 800,
	    height: 380,
	    closed: false,
	    cache: false,
	    href: '${ctx}/landPic/viewPic?id='+id,
	    modal: true
	});
}

function editLandPic(){
	var year = $("#year").val();
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	if(length == 0){
		$.messager.alert('警告','请选择一条记录。','warning');
		return false;
	}
	if(length > 1){
		$.messager.alert('警告','只能选择一条记录。','warning');
		return false;
	}
	var id = rows[0].id;
	$('#addDialog').dialog({
	    title: '修改土地图片',
	    width: 800,
	    height: 380,
	    closed: false,
	    cache: false,
	    href: '${ctx}/landPic/editPicInit?id='+id+"&year="+year,
	    modal: true
	});
}

function deleteLandPic(){
	var ids = [];
	var rows = $('#data').datagrid('getSelections');
	var length = rows.length;
	for(var i=0; i<rows.length; i++) ids.push(rows[i].id);
	if(ids.length == 0){
		$.messager.alert('警告','至少选择一条记录。','warning');
		return false;
	}
	
	$.messager.confirm("确认", "您确认删除选定的记录吗？", function (deleteAction) {
		if (deleteAction) {
			showLoading();
			Public.ajaxGet('${ctx}/landPic/delete', {ids : ids}, function(e) {
				hideLoading();
				if (200 == e.status) {
					Public.tips({
						content : "成功！"
					});
					$('#landpicform').submit();
				} else
					Public.tips({
						type : 1,
						content : "失败！" + e.msg
					});
			});
		}
	});
}

function onSubmit(){
	showLoading();
	$('#landpicform').submit();
}
</script>
</body>
</html>