<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EasyUI使用示例</title>
<link rel="stylesheet" type="text/css" href="${ctx}/style/index.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/color.css">
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript" src="${ctx}/js/form2js.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
<script type="text/javascript">
function confirmSample(){
  $.messager.confirm("删除确认", "您确认删除选定的记录吗？", function (deleteAction) {
    if (deleteAction) {
alert("你选择了OK");
    }
});
}
</script>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:0 10px;">	
        <fieldset id="queryBlock" class="fieldset_common_style">
          <a href="#" class="easyui-linkbutton" onclick="$.messager.alert('提示','保存成功。')">
              <span class="l-btn-left"><span class="l-btn-text ">普通弹框</span></span>
          </a>
          <a href="#" class="easyui-linkbutton" onclick="$.messager.alert('提示','操作成功。','info')">
              <span class="l-btn-left"><span class="l-btn-text ">提示弹框</span></span>
          </a>
          <a href="#" class="easyui-linkbutton" onclick="$.messager.alert('错误','处理失败。','error')">
              <span class="l-btn-left"><span class="l-btn-text ">错误弹框</span></span>
          </a>
          <a href="#" class="easyui-linkbutton" onclick="$.messager.alert('警告','请选择企业。','warning')">
              <span class="l-btn-left"><span class="l-btn-text ">警告弹框</span></span>
          </a>
          <a href="#" class="easyui-linkbutton" onclick="confirmSample();">
              <span class="l-btn-left"><span class="l-btn-text ">确认弹框</span></span>
          </a>
        </fieldset>
	</div>
</body>
</html>