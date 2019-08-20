<%@page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>登录</title>
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/default/easyui.css">
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript" src="${ctx}/js/form2js.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
<script type="text/javascript">
if(window!=top){
	top.location.href=location.href;
}
</script>
<style type="text/css">
	 *{margin:0;padding:0;font-family:"Microsoft Yahei";}
	 ul,li{list-style:none;}
	 body{ background:url("${ctx}/images/loginbg.jpg") no-repeat top center;overflow:hidden;}
	 .loginC{width:1000px;margin:0 auto;position:relative;}
	 .login{width:256px;height:276px;background:url("${ctx}/images/loginInputBg.png") no-repeat center center;position:absolute;color:#6b6b6b;font-size:14px;top:186px;left:728px;}
	 .login ul{position:absolute;left:30px;top:70px;}
	 .login ul li{margin-bottom:25px;}
	 .login ul li input.text{background:#FFF;border:1px solid #616365;width:190px;text-indent:0px!important;font-size:14px;padding:8px 0;height:14px;padding-left:10px;color:#6b6b6b;}
	 .login ul li input.smallText{background:#FFF;border:1px solid #dfdfdf;width:60px;text-indent:10px;font-size:14px;padding:8px 0;border-radius:4px;color:#6b6b6b;}
	 .login ul li input.btn{border:0;cursor:pointer;width:200px;height:38px;background:#5aa35e;color:#FFF;font-size:16px;font-weight:bold;}
	 /*.login ul li input.btn{border:0;cursor:pointer;width:102px;height:44px;background:url("${ctx}/images/loginBtn.png") no-repeat center center;}*/
	 .login ul li.btnContain{}
</style>
</head>
<body>
	<div class="loginC">
		<div class="login">
			<form id="login-form" method="post" action="user/logindo">
				<ul>
					<li>
						<span><input name="userID" id="userID" type="text" class="text" placeholder="用户名" /></span>
					</li>
					<li>
						<span><input name="password" id="password" type="password" class="text" placeholder="密码" /></span>
					</li>
					<li class="btnContain">
						<input type="button" class="btn" value="登 陆" onclick="onSubmit()" />
					</li>
				</ul>
			</form>	
		</div>
	</div>
</body>
<script type="text/javascript">
$(function() {
	// var h = $(window).height();
	// var w = $(window).width();
	// var t = 100;
	// var l = (w - 602) / 2;
	// $("body").height(h).width(w);
	// $(".login").css({"top": t, "left": l});
	
	$('#userName').focus();
	
	$('#userID').validatebox({
        required: true,
        missingMessage: "请输入帐号！"
    });
    $('#password').validatebox({
        required: true,
        missingMessage: "请输入密码！"
    });
    
    if($.cookie('userID') != null && $.cookie('userID') != ''){
		$('#userID').val($.cookie('userID'));
	}

});

function onSubmit(){
	if ($("#userID").validatebox("isValid") && $("#password").validatebox("isValid")) {
		var a = $('#login-form').toObject();
		Public.ajaxPost('logindo?type=0', JSON.stringify(a) , function(e) {
			if (200 == e.status) {
				$.cookie('userID', a.userID);
				window.location.href = "../main";
			} else
				$.messager.alert('错误',e.msg,'error');
		});
	}
}

document.onkeydown = function(e) {
	// 兼容FF和IE和Opera
	var theEvent = e || window.event;
	var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
	var activeElementId = document.activeElement.id;//当前处于焦点的元素的id
	if (code == 13 && activeElementId == "password") {
		onSubmit();//要触发的方法
		return false;
	}
	return true;
}
</script>
</html>