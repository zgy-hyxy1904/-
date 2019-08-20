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
<script type="text/javascript" src="${ctx}/js/upload.js"></script>

</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">	
		<fieldset class="fieldset_common_style">
			<form id="inputForm" name="inputForm" method="get" action="${ctx}/procMoni/list">
			<table class="table_common_style">
				<tr>
					<td class="table_common_td_label_style">年度：</td>
					<td class="table_common_td_txt_style">
						<simple:select id="year" name="year" value="${year}" entityName="YearCode" width="170" onChange="initInfo();"/>
					</td>
					<td class="table_common_td_label_style">企业名称：</td>
					<td class="table_common_td_txt_style">
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
					</td>
					<td align="right" valign="bottom">
                       	<a href="#" class="easyui-linkbutton" onclick="return form_check();">
                       		<span class="l-btn-left"><span class="l-btn-text icon-search l-btn-icon-left">查询
                       		</span>
                       		</span>
                       	</a>
             		</td>
				</tr>
			</table>	
			</form>
		</fieldset>
		<div class="processMonitor">
			<ul class="menuct">
				<li class="on"><a href="javascript:void(0)">浸种催芽</a></li>
				<li><a href="javascript:void(0)">育秧环节</a></li>
				<li><a href="javascript:void(0)">插秧环节</a></li>
				<li><a href="javascript:void(0)">植保环节</a></li>
				<li><a href="javascript:void(0)">收割环节</a></li>
				<li><a href="javascript:void(0)">物流环节</a></li>
				<li><a href="javascript:void(0)">仓储环节</a></li>
				<li><a href="javascript:void(0)">加工环节</a></li>
			</ul>
			<div class="processMonitorC">
				<div class="processMonitorSon" id="monitor_01" style="display:block;">
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th><span><i>过程图片</i></span></th>
							<th><span><i>过程视频</i></span></th>
						</tr>
						<tr>
							<td class="imgC" align="center">
							<div class="imgList" id="imglist01">
								<ul>
									<c:forEach var="mfile" items="${fileList.f_0101}" varStatus="status">
										<li><img src="/suyuan/upload/${mfile.filePath}" title="${mfile.fileInfo}"/></li>
									</c:forEach>
								</ul>
								<a href="javascript:void(0)" class="imglist_arrow imglist_l"></a>
								<a href="javascript:void(0)" class="imglist_arrow imglist_r"></a>
							</div>
							</td>
							<td>
								<div class="video" id="video01">
									<a href="javascript:void(0)" class="video_arrow video_l"></a>
									<a href="javascript:void(0)" class="video_arrow video_r"></a>
									<ul>
										<c:forEach var="mfile" items="${fileList.f_0102}" varStatus="status">
											<li>
												<!-- <embed src="${mfile.filePath}" allowFullScreen="true" quality="high" width="320" height="250" align="middle" allowScriptAccess="always" type="application/x-shockwave-flash"></embed> -->
												<param name="wmode" value="opaque" />
									             <embed runat="server" id="embedLnk" style="z-index:0;" src="${mfile.filePath}" 
									             type="application/x-shockwave-flash" 
									             allowscriptaccess="always" wmode="opaque"
									             allowfullscreen="true" width="320" height="250">
									             </embed>
											</li>
										</c:forEach>
									</ul>
								</div>
							</td>
						</tr>
						<tr>
							<td align="center" id="imgbtn01">
								<!-- <a href="javascript:void(0)" class="btn btn1"></a> -->
								<!-- <a href="javascript:void(0)" class="btn btn2"></a> -->
								<a href="javascript:void(0)" class="btn btn3"></a>
								<!-- <a href="javascript:void(0)" class="btn btn4"></a> -->
								<a href="javascript:void(0)" class="btn btn5" target="_blank"></a>
							</td>
						</tr>
						<tr valign="top">
							<td class="description" rel="pic">
								<c:forEach var="mfile" items="${fileList.f_0101}" varStatus="status">
									<span>简介: ${mfile.fileInfo}</span>
								</c:forEach>
							</td>
							<td class="description" rel="video">
								<c:forEach var="mfile" items="${fileList.f_0102}" varStatus="status">
									<span>简介: ${mfile.fileInfo}</span>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
				<div class="processMonitorSon" id="monitor_02">
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th><span><i>过程图片</i></span></th>
							<th><span><i>过程视频</i></span></th>
						</tr>
						<tr>
							<td class="imgC" align="center">
							<div class="imgList" id="imglist02">
								<ul>
									<c:forEach var="mfile" items="${fileList.f_0201}" varStatus="status">
										<li><img src="/suyuan/upload/${mfile.filePath}" title="${mfile.fileInfo}"/></li>
									</c:forEach>
								</ul>
								<a href="javascript:void(0)" class="imglist_arrow imglist_l"></a>
								<a href="javascript:void(0)" class="imglist_arrow imglist_r"></a>
							</div>
							</td>
							<td>
								<div class="video" id="video02">
									<ul>
										<c:forEach var="mfile" items="${fileList.f_0202}" varStatus="status">
											<li>
												<param name="wmode" value="opaque" />
									             <embed runat="server" id="embedLnk" style="z-index:0;" src="${mfile.filePath}" 
									             type="application/x-shockwave-flash" 
									             allowscriptaccess="always" wmode="opaque"
									             allowfullscreen="true" width="320" height="250">
									             </embed>
											</li>
										</c:forEach>
									</ul>
									<a href="javascript:void(0)" class="video_arrow video_l"></a>
									<a href="javascript:void(0)" class="video_arrow video_r"></a>
								</div>
							</td>
						</tr>
						<tr>
							<td align="center" id="imgbtn02">
								<!-- <a href="javascript:void(0)" class="btn btn1"></a> -->
								<!-- <a href="javascript:void(0)" class="btn btn2"></a> -->
								<a href="javascript:void(0)" class="btn btn3"></a>
								<!-- <a href="javascript:void(0)" class="btn btn4"></a> -->
								<a href="javascript:void(0)" class="btn btn5" target="_blank"></a>
							</td>
						</tr>
						<tr valign="top">
							<td class="description" rel="pic">
								<c:forEach var="mfile" items="${fileList.f_0201}" varStatus="status">
									<span>简介: ${mfile.fileInfo}</span>
								</c:forEach>
							</td>
							<td class="description" rel="video">
								<c:forEach var="mfile" items="${fileList.f_0202}" varStatus="status">
									<span>简介: ${mfile.fileInfo}</span>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
				<div class="processMonitorSon" id="monitor_03">
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th><span><i>过程图片</i></span></th>
							<th><span><i>过程视频</i></span></th>
						</tr>
						<tr>
							<td class="imgC" align="center">
							<div class="imgList" id="imglist03">
								<ul>
									<c:forEach var="mfile" items="${fileList.f_0301}" varStatus="status">
										<li><img src="/suyuan/upload/${mfile.filePath}" title="${mfile.fileInfo}"/></li>
									</c:forEach>
								</ul>
								<a href="javascript:void(0)" class="imglist_arrow imglist_l"></a>
								<a href="javascript:void(0)" class="imglist_arrow imglist_r"></a>
							</div>
							</td>
							<td>
								<div class="video" id="video03">
									<ul>
										<c:forEach var="mfile" items="${fileList.f_0302}" varStatus="status">
											<li>
												<param name="wmode" value="opaque" />
									             <embed runat="server" id="embedLnk" style="z-index:0;" src="${mfile.filePath}" 
									             type="application/x-shockwave-flash" 
									             allowscriptaccess="always" wmode="opaque"
									             allowfullscreen="true" width="320" height="250">
									             </embed>
											</li>
										</c:forEach>
									</ul>
									<a href="javascript:void(0)" class="video_arrow video_l"></a>
									<a href="javascript:void(0)" class="video_arrow video_r"></a>
								</div>
							</td>
						</tr>
						<tr>
							<td align="center" id="imgbtn03">
								<!-- <a href="javascript:void(0)" class="btn btn1"></a> -->
								<!-- <a href="javascript:void(0)" class="btn btn2"></a> -->
								<a href="javascript:void(0)" class="btn btn3"></a>
								<!-- <a href="javascript:void(0)" class="btn btn4"></a> -->
								<a href="javascript:void(0)" class="btn btn5" target="_blank"></a>
							</td>
						</tr>
						<tr valign="top">
							<td class="description" rel="pic">
								<c:forEach var="mfile" items="${fileList.f_0301}" varStatus="status">
									<span>简介: ${mfile.fileInfo}</span>
								</c:forEach>
							</td>
							<td class="description" rel="video">
								<c:forEach var="mfile" items="${fileList.f_0302}" varStatus="status">
									<span>简介: ${mfile.fileInfo}</span>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
				<div class="processMonitorSon" id="monitor_04">
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th><span><i>过程图片</i></span></th>
							<th><span><i>过程视频</i></span></th>
						</tr>
						<tr>
							<td class="imgC" align="center">
							<div class="imgList" id="imglist04">
								<ul>
									<c:forEach var="mfile" items="${fileList.f_0401}" varStatus="status">
										<li><img src="/suyuan/upload/${mfile.filePath}" title="${mfile.fileInfo}"/></li>
									</c:forEach>
								</ul>
								<a href="javascript:void(0)" class="imglist_arrow imglist_l"></a>
								<a href="javascript:void(0)" class="imglist_arrow imglist_r"></a>
							</div>
							</td>
							<td>
								<div class="video" id="video04">
									<ul>
										<c:forEach var="mfile" items="${fileList.f_0402}" varStatus="status">
											<li>
												<param name="wmode" value="opaque" />
									             <embed runat="server" id="embedLnk" style="z-index:0;" src="${mfile.filePath}" 
									             type="application/x-shockwave-flash" 
									             allowscriptaccess="always" wmode="opaque"
									             allowfullscreen="true" width="320" height="250">
									             </embed>
											</li>
										</c:forEach>
									</ul>
									<a href="javascript:void(0)" class="video_arrow video_l"></a>
									<a href="javascript:void(0)" class="video_arrow video_r"></a>
								</div>
							</td>
						</tr>
						<tr>
							<td align="center" id="imgbtn04">
								<!-- <a href="javascript:void(0)" class="btn btn1"></a> -->
								<!-- <a href="javascript:void(0)" class="btn btn2"></a> -->
								<a href="javascript:void(0)" class="btn btn3"></a>
								<!-- <a href="javascript:void(0)" class="btn btn4"></a> -->
								<a href="javascript:void(0)" class="btn btn5" target="_blank"></a>
							</td>
						</tr>
						<tr valign="top">
							<td class="description" rel="pic">
								<c:forEach var="mfile" items="${fileList.f_0401}" varStatus="status">
									<span>简介: ${mfile.fileInfo}</span>
								</c:forEach>
							</td>
							<td class="description" rel="video">
								<c:forEach var="mfile" items="${fileList.f_0402}" varStatus="status">
									<span>简介: ${mfile.fileInfo}</span>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
				<div class="processMonitorSon" id="monitor_05">
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th><span><i>过程图片</i></span></th>
							<th><span><i>过程视频</i></span></th>
						</tr>
						<tr>
							<td class="imgC" align="center">
							<div class="imgList" id="imglist05">
								<ul>
									<c:forEach var="mfile" items="${fileList.f_0501}" varStatus="status">
										<li><img src="/suyuan/upload/${mfile.filePath}" title="${mfile.fileInfo}"/></li>
									</c:forEach>
								</ul>
								<a href="javascript:void(0)" class="imglist_arrow imglist_l"></a>
								<a href="javascript:void(0)" class="imglist_arrow imglist_r"></a>
							</div>
							</td>
							<td>
								<div class="video" id="video05">
									<ul>
										<c:forEach var="mfile" items="${fileList.f_0502}" varStatus="status">
											<li>
												<param name="wmode" value="opaque" />
									             <embed runat="server" id="embedLnk" style="z-index:0;" src="${mfile.filePath}" 
									             type="application/x-shockwave-flash" 
									             allowscriptaccess="always" wmode="opaque"
									             allowfullscreen="true" width="320" height="250">
									             </embed>
											</li>
										</c:forEach>
									</ul>
									<a href="javascript:void(0)" class="video_arrow video_l"></a>
									<a href="javascript:void(0)" class="video_arrow video_r"></a>
								</div>
							</td>
						</tr>
						<tr>
							<td align="center" id="imgbtn05">
								<!-- <a href="javascript:void(0)" class="btn btn1"></a> -->
								<!-- <a href="javascript:void(0)" class="btn btn2"></a> -->
								<a href="javascript:void(0)" class="btn btn3"></a>
								<!-- <a href="javascript:void(0)" class="btn btn4"></a> -->
								<a href="javascript:void(0)" class="btn btn5" target="_blank"></a>
							</td>
						</tr>
						<tr valign="top">
							<td class="description" rel="pic">
								<c:forEach var="mfile" items="${fileList.f_0501}" varStatus="status">
									<span>简介: ${mfile.fileInfo}</span>
								</c:forEach>
							</td>
							<td class="description" rel="video">
								<c:forEach var="mfile" items="${fileList.f_0502}" varStatus="status">
									<span>简介: ${mfile.fileInfo}</span>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
				<div class="processMonitorSon" id="monitor_06">
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th><span><i>过程图片</i></span></th>
							<th><span><i>过程视频</i></span></th>
						</tr>
						<tr>
							<td class="imgC" align="center">
							<div class="imgList" id="imglist06">
								<ul>
									<c:forEach var="mfile" items="${fileList.f_0601}" varStatus="status">
										<li><img src="/suyuan/upload/${mfile.filePath}" title="${mfile.fileInfo}"/></li>
									</c:forEach>
								</ul>
								<a href="javascript:void(0)" class="imglist_arrow imglist_l"></a>
								<a href="javascript:void(0)" class="imglist_arrow imglist_r"></a>
							</div>
							</td>
							<td>
								<div class="video" id="video06">
									<ul>
										<c:forEach var="mfile" items="${fileList.f_0602}" varStatus="status">
											<li>
												<param name="wmode" value="opaque" />
									             <embed runat="server" id="embedLnk" style="z-index:0;" src="${mfile.filePath}" 
									             type="application/x-shockwave-flash" 
									             allowscriptaccess="always" wmode="opaque"
									             allowfullscreen="true" width="320" height="250">
									             </embed>
											</li>
										</c:forEach>
									</ul>
									<a href="javascript:void(0)" class="video_arrow video_l"></a>
									<a href="javascript:void(0)" class="video_arrow video_r"></a>
								</div>
							</td>
						</tr>
						<tr>
							<td align="center" id="imgbtn06">
								<!-- <a href="javascript:void(0)" class="btn btn1"></a> -->
								<!-- <a href="javascript:void(0)" class="btn btn2"></a> -->
								<a href="javascript:void(0)" class="btn btn3"></a>
								<!-- <a href="javascript:void(0)" class="btn btn4"></a> -->
								<a href="javascript:void(0)" class="btn btn5" target="_blank"></a>
							</td>
						</tr>
						<tr valign="top">
							<td class="description" rel="pic">
								<c:forEach var="mfile" items="${fileList.f_0601}" varStatus="status">
									<span>简介: ${mfile.fileInfo}</span>
								</c:forEach>
							</td>
							<td class="description" rel="video">
								<c:forEach var="mfile" items="${fileList.f_0602}" varStatus="status">
									<span>简介: ${mfile.fileInfo}</span>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
				<div class="processMonitorSon" id="monitor_07">
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th><span><i>过程图片</i></span></th>
							<th><span><i>过程视频</i></span></th>
						</tr>
						<tr>
							<td class="imgC" align="center">
							<div class="imgList" id="imglist07">
								<ul>
									<c:forEach var="mfile" items="${fileList.f_0701}" varStatus="status">
										<li><img src="/suyuan/upload/${mfile.filePath}" title="${mfile.fileInfo}"/></li>
									</c:forEach>
								</ul>
								<a href="javascript:void(0)" class="imglist_arrow imglist_l"></a>
								<a href="javascript:void(0)" class="imglist_arrow imglist_r"></a>
							</div>
							</td>
							<td>
								<div class="video" id="video07">
									<ul>
										<c:forEach var="mfile" items="${fileList.f_0702}" varStatus="status">
											<li>
												<param name="wmode" value="opaque" />
									             <embed runat="server" id="embedLnk" style="z-index:0;" src="${mfile.filePath}" 
									             type="application/x-shockwave-flash" 
									             allowscriptaccess="always" wmode="opaque"
									             allowfullscreen="true" width="320" height="250">
									             </embed>
											</li>
										</c:forEach>
									</ul>
									<a href="javascript:void(0)" class="video_arrow video_l"></a>
									<a href="javascript:void(0)" class="video_arrow video_r"></a>
								</div>
							</td>
						</tr>
						<tr>
							<td align="center" id="imgbtn07">
								<!-- <a href="javascript:void(0)" class="btn btn1"></a> -->
								<!-- <a href="javascript:void(0)" class="btn btn2"></a> -->
								<a href="javascript:void(0)" class="btn btn3"></a>
								<!-- <a href="javascript:void(0)" class="btn btn4"></a> -->
								<a href="javascript:void(0)" class="btn btn5" target="_blank"></a>
							</td>
						</tr>
						<tr valign="top">
							<td class="description" rel="pic">
								<c:forEach var="mfile" items="${fileList.f_0701}" varStatus="status">
									<span>简介: ${mfile.fileInfo}</span>
								</c:forEach>
							</td>
							<td class="description" rel="video">
								<c:forEach var="mfile" items="${fileList.f_0702}" varStatus="status">
									<span>简介: ${mfile.fileInfo}</span>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
				<div class="processMonitorSon" id="monitor_08">
					<table width="100%" cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th><span><i>过程图片</i></span></th>
							<th><span><i>过程视频</i></span></th>
						</tr>
						<tr>
							<td class="imgC" align="center">
							<div class="imgList" id="imglist08">
								<ul>
									<c:forEach var="mfile" items="${fileList.f_0801}" varStatus="status">
										<li><img src="/suyuan/upload/${mfile.filePath}" title="${mfile.fileInfo}"/></li>
									</c:forEach>
								</ul>
								<a href="javascript:void(0)" class="imglist_arrow imglist_l"></a>
								<a href="javascript:void(0)" class="imglist_arrow imglist_r"></a>
							</div>
							</td>
							<td>
								<div class="video" id="video08">
									<ul>
										<c:forEach var="mfile" items="${fileList.f_0802}" varStatus="status">
											<li>
												<param name="wmode" value="opaque" />
									             <embed runat="server" id="embedLnk" style="z-index:0;" src="${mfile.filePath}" 
									             type="application/x-shockwave-flash" 
									             allowscriptaccess="always" wmode="opaque"
									             allowfullscreen="true" width="320" height="250">
									             </embed>
											</li>
										</c:forEach>
									</ul>
									<a href="javascript:void(0)" class="video_arrow video_l"></a>
									<a href="javascript:void(0)" class="video_arrow video_r"></a>
								</div>
							</td>
						</tr>
						<tr>
							<td align="center" id="imgbtn08">
								<!-- <a href="javascript:void(0)" class="btn btn1"></a> -->
								<!-- <a href="javascript:void(0)" class="btn btn2"></a> -->
								<a href="javascript:void(0)" class="btn btn3"></a>
								<!-- <a href="javascript:void(0)" class="btn btn4"></a> -->
								<a href="javascript:void(0)" class="btn btn5" target="_blank"></a>
							</td>
						</tr>
						<tr valign="top">
							<td class="description" rel="pic">
								<c:forEach var="mfile" items="${fileList.f_0801}" varStatus="status">
									<span>简介: ${mfile.fileInfo}</span>
								</c:forEach>
							</td>
							<td class="description" rel="video">
								<c:forEach var="mfile" items="${fileList.f_0802}" varStatus="status">
									<span>简介: ${mfile.fileInfo}</span>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="addDialog"></div>
	<div class="imgDetailBG"></div>
	<div class="imgDetail">
		<img src="" />
		<div class="imgDetailBtn">
			<a href="javascript:void(0)" class="btn btn1"></a>
			<a href="javascript:void(0)" class="btn btn2"></a>
			<!-- <a href="javascript:void(0)" class="btn btn3"></a> -->
			<a href="javascript:void(0)" class="btn btn4"></a>
			<!-- <a href="javascript:void(0)" class="btn btn5"></a> -->
		</div>
	</div>
<script type="text/javascript">
	$(document).ready(function(){
		var year = $("#year").combobox('getValue');
		var companyCode  = $("#companyCode").combobox('getValue');
	});

	function initInfo( ){
		var year = $("#year").combobox('getValue');
		var companyCode  = $("#companyCode").combobox('getValue');
		if( year != "" && companyCode != "" ){
			document.location.href = "${ctx}/procMoni/list?year=" + year + "&companyCode=" + companyCode;
		}
	}

	function form_check(){
		$('#inputForm').submit();
	}

	function tabc(){
		var li = $(".processMonitor .menuct li");
		var son = $(".processMonitorSon");

		li.click(function(){
			var index = $(this).index();
			li.removeClass("on");
			$(this).addClass("on");
			son.hide();
			son.eq(index).show();
		})
	}
	tabc();

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
				leftbtn.hide();
				rightbtn.hide();
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
			leftbtn.click(function(){
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
			rightbtn.click(function(){
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
			imgDesc.hide();
			imgDesc.eq(i).css("display","block");
		}

		var init = function(){
			if(imgCount == 0){
				checkNULL();
			}else{
				elementSize();
				initleftbtn();
				initrightbtn();
				initImgDetailBtn();
				initImgListBtn();
				initYuan();
				initDesc();
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
				leftbtn.hide();
				rightbtn.hide();
			}
		}

		var elementSize = function(){
			videolistul.width(videolistliWidth * videoCount);
		}

		var initleftbtn = function(){
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
	
	function form_check(){
		if( $('#companyCode').combobox('getValue') == "" ){
			$.messager.alert('警告','请选择企业。','warning');
			return false;
		}
		showLoading();
		$('#inputForm').submit();
	}
	imgList("#imglist01", "#imgbtn01");
	videoList("#video01");
	imgList("#imglist02", "#imgbtn02");
	videoList("#video02");
	imgList("#imglist03", "#imgbtn03");
	videoList("#video03");
	imgList("#imglist04", "#imgbtn04");
	videoList("#video04");
	imgList("#imglist05", "#imgbtn05");
	videoList("#video05");
	imgList("#imglist06", "#imgbtn06");
	videoList("#video06");
	imgList("#imglist07", "#imgbtn07");
	videoList("#video07");
	imgList("#imglist08", "#imgbtn08");
	videoList("#video08");
</script>
</body>
</html>