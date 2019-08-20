<%@ page language="java" contentType="text/html; charset=UTF-8" import="java.util.*"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片浏览控件使用指南</title>
<link rel="stylesheet" type="text/css" href="${ctx}/style/index.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/color.css">
<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript" src="${ctx}/js/form2js.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:0 10px;">	
		<H2>图片浏览控件的标签使用说明:</H2>
		<p>
			1.在页面中引入如下标签代码:<BR>
			<div style='background-color:#999999'>
			<code>
				&lt;%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%&gt;
			</code>
			</div>
			<BR>
			2.在需要使用图片浏览控件的位置引入如下组件代码示例:<BR>
			<div style='background-color:#999999'>
			<code>
				&lt;simple:imgView 
					outerDivId="outerDiv1"
					innerDivId="innerDiv1"
					imgInfoMaps="imgPathAndInfoMaps" 
					divId="showDiv1" 
					width="300"
					height="400"
					time="2000" 
					speed="500"
					autoScrooll="true"
					arrowControl="true"
					numberControl="false""&gt;&lt;/simple:imgView &gt;
			</code>
			</div>
			3.如果所浏览的图片需要Ajax重新载入路径,此时无法将数据组织在request的Attribute中,则可以通过标签中封装的javascript:reloadImageView方法完成图片的重加载:<BR>
			<div style='background-color:#999999'>
			<code>
			/**<BR>
			 *本方法实现了参数个数递增式重载,调用时可传任意个数的参数,关于reloadImageView方法的11个参数说明:<BR>
			 *1.[outerDivId],String类型,代表图片外部的容器DivId,当一个页面中有个图片浏览组件时用于标识不同的组件<BR>
			 *2.[innerDivId],String类型,代表外部的容器内部的DivId,用于掌控内部Div的样式与js绑定<BR>
			 *3.[imgSrcPaths],Array类型,用于标识的图片的路径集合,可不填写,如不填写则表示无图片路径,即显示默认图片images/nothing.png<BR>
			 *4.[imgInfos],Array类型,用于标识的图片的描述,可不填写,如不填写则表示无图片描述,即描述为空<BR>
			 *5.[width],int类型,控件宽度,单位为px,可不填写,默认宽度300<BR>
			 *6.[height],int类型,控件高度,单位为px,可不填写,默认宽度400<BR>
			 *7.[autoScrooll],boolean类型,是否轮播,可不填写默认true<BR>
			 *8.[time],int类型,轮播间隔时间,单位毫秒,可不填写,默认2000ms<BR>
			 *9.[arrowControl],boolean类型,是否显示左右箭头,可不填写,默认true<BR>
			 *10.[speed],int类型,图片切换速度,单位毫秒,可不填写,默认500<BR>
			 *11.[numberControl],boolean类型,是否显示图片序号列表,可不填写,默认false<BR>
			 */<BR>
				&lt;button onclick='reloadImageView('outerDiv1','innerDiv1',['$\{ctx \}/test/pic/2_b.jpg', '$\{ctx \}/test/pic/3_b.jpg', '$\{ctx \}/test/pic/5_b.jpg'], ['描述1','描述2','描述3'])'&gt;
					重新载入图片
				&lt;/button&gt;<BR>
			</code>
			</div>
			<BR>

		<HR>
		<H2>示例展示(自己Copy源码改):</H2>
		<HR>
		图片路径存在List容器中的示例:<FONT COLOR="RED">当然,本人并不推荐在JSP页面中书写如下代码,本示例仅在于演示使用方法</FONT>
		<div style='background-color:#999999'>
		<CODE>
		&lt;%<BR>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Map&lt;String,String&gt; imgProperties = new HashMap&lt;String,String&gt;();<BR>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;imgProperties.put(request.getContextPath()+"/test/pic/1_b.jpg","第1个图片的描述");<BR>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;imgProperties.put(request.getContextPath()+"/test/pic/2_b.jpg","第2个图片的描述");<BR>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;imgProperties.put(request.getContextPath()+"/test/pic/3_b.jpg","");<BR>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;imgProperties.put(request.getContextPath()+"/test/pic/4_b.jpg","第4个图片的描述,上一个图片就没有描述,下一个也没有");<BR>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;imgProperties.put(request.getContextPath()+"/test/pic/5_b.jpg",null);<BR>
		<BR>
		request.setAttribute("imgPathAndInfoMaps", imgProperties);<BR>
		%&gt;<BR><BR>
		
		&lt;simple:imgView 
					imgInfoMaps="imgPathAndInfoMaps" 
					width="300"
					height="400"
					time="2000" 
					speed="500"
					autoScrooll="true"
					arrowControl="true"
					numberControl="false""&gt;&lt;/simple:imgView&gt;
		</CODE>
		</div>
<%
		Map<String,String> imgProperties = new HashMap<String,String>();
		imgProperties.put(request.getContextPath()+"/test/pic/1_b.jpg","第1个图片的描述");
		imgProperties.put(request.getContextPath()+"/test/pic/2_b.jpg","第2个图片的描述");
		imgProperties.put(request.getContextPath()+"/test/pic/3_b.jpg","");
		imgProperties.put(request.getContextPath()+"/test/pic/4_b.jpg","第4个图片的描述,上一个图片就没有描述,下一个也没有");
		imgProperties.put(request.getContextPath()+"/test/pic/5_b.jpg",null);
		
		request.setAttribute("imgPathAndInfoMaps", imgProperties);
%>
		<HR>
		示例效果:
		<table border=1 bordercolor='green' width=95% align=center>
			<tr>
				<td width="50%">
					<simple:imgView outerDivId="outerDiv1" innerDivId="innerDiv1" imgInfoMaps="imgPathAndInfoMaps" width="450" height="600"></simple:imgView>
					<script>
						var paths = ['${ctx }/test/pic/2_b.jpg', '${ctx }/test/pic/3_b.jpg', '${ctx }/test/pic/5_b.jpg'];
					</script>
					<button onclick="reloadImageView('outerDiv1','innerDiv1',paths, ['描述1','描述2','描述3'], 450, 600)">换一批图</button>
				</td>
				<td>
					<simple:imgView outerDivId="outerDiv2" innerDivId="innerDiv2" imgInfoMaps="imgPathAndInfoMaps" width="300" height="400"></simple:imgView>
					<script>
						['${ctx }/test/pic/2_b.jpg', '${ctx }/test/pic/3_b.jpg', '${ctx }/test/pic/5_b.jpg'];
					</script>
					<button onclick="reloadImageView('outerDiv2','innerDiv2',['${ctx }/test/pic/1_b.jpg', '${ctx }/test/pic/2_b.jpg', '${ctx }/test/pic/4_b.jpg'], ['描述111','描述2222','描述333333'])">换一批图</button>
				</td>
			</tr>
		</table>
		
		
		
		<HR>
			图片浏览控件的属性说明(以下属性名称在使用时需严格区分大小写):<BR>
			  <TABLE border=1 bordercolor='green' width=95% align=center>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">imgInfoMaps:</FONT></TD>
				<TD>图片路径和描述的Map集合名称,非必填项,如果不填写,则默认显示images/nothing.png图片.通过request.setAttribute("imgPathAndInfoMaps", imgProperties)方式设置此Map的名称,本例中<FONT COLOR="RED">imgSrcPaths属性需要填写的内容为"imgPathAndInfoMaps"</FONT>,<FONT COLOR="BLUE">imgProperties是一个Map&lt;String,String&gt;类型图片信息集合,其中key是图片路径,value是图片描述,图片描述如果没有则设value为""或null</FONT></TD>
			  </TR>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">outerDivId:</FONT></TD>
				<TD>用于代表图片外部的容器DivId,当一个页面中有个图片浏览组件时用于标识不同的组件</TD>
			  </TR>
			  <TR>
				<TD width="5%"><FONT COLOR="RED">innerDivId:</FONT></TD>
				<TD>用于代表外部的容器内部的DivId,用于掌控内部Div的样式与js绑定</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">width:</FONT></TD>
				<TD>控件宽度设定,单位为px,非必填项,默认为300,px单位是写死的,如果有需要的话跟我说,我可以修改源码为占百分比形式的相对宽度,目前没必要</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">height:</FONT></TD>
				<TD>控件高度设定,单位为px,非必填项,默认为400,px单位是写死的,如果有需要的话跟我说,我可以修改源码为占百分比形式的相对宽度,目前没必要</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">autoScrooll:</FONT></TD>
				<TD>是否自动轮播,非必填项,默认为true(轮播)</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">time:</FONT></TD>
				<TD>图片自动轮播时间间隔设定,单位为毫秒,非必填项,默认为2000</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">arrowControl:</FONT></TD>
				<TD>是否显示左右箭头,非必填项,默认为true(显示)</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">speed:</FONT></TD>
				<TD>图片切换速度设定,单位为毫秒,非必填项,默认为500</TD>
			  </TR>
			  <TR>
				<TD><FONT COLOR="RED">numberControl:</FONT></TD>
				<TD>是否显示图片右下角的轮播图片序号链接,非必填项,默认为false(不显示)</TD>
			  </TR>
			 
			  </TABLE>
	</div>
</body>
</html>