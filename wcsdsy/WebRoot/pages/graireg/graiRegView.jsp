<%@page contentType="text/html;charset=UTF-8"%>
<%@include file="/common.jsp" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %> 
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
	$(document).ready(function() {
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
</script>
	<script>
		var root = "${ctx}";
	</script>
	<script type="text/javascript" src="${ctx}/js/grainreg.js"></script>

</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:5px;">	
		<form id="editForm" name="editForm" method="post" action="../graiReg/list">
			<fieldset class="fieldset_common_style">
				<input type="hidden" name="id" id="id" value="${graiReg.id}"/>
				<input type="hidden" name="actualMu" id="actualMu" value="${actualMu}"/>
				<input type="hidden" name="measurementMu" id="measurementMu" value="${zmj }"/>
				<input type="hidden" name="estimateTotalYield" id="estimateTotalYield" value=""/>
				<input type="hidden" name="soldYield" id="soldYield" value=""/>	
				<table class="table_common_style">
					<tr>
						<td>
							<a href="#" class="easyui-linkbutton"  onclick="return retList();">
							<span class="l-btn-left">
								<span class="l-btn-text icon-cancel l-btn-icon-left">返回</span></span>
							</a>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_style">年度：</td>
						<td class="table_common_td_txt_style">
							${year }							
						</td>
						<td class="table_common_td_label_style">企业：</td>
						<td class="table_common_td_txt_style">${graiReg.companyName }</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<legend>地块信息</legend>
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_style">姓名：</td>
						<td class="table_common_td_txt_style">
							${graiReg.farmerName }
						</td>
						<td class="table_common_td_label_style">身份证号：</td>
						<td colspan="3" class="table_common_td_txt_style">
							${graiReg.idNumber }
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">地址：</td>
						<td colspan="5" class="table_common_td_txt_style">
							<simple:showName entityName="areadevision" codeKey="town" value="${graiReg.townCode }"></simple:showName>
							<simple:showName entityName="areadevision" codeKey="country" value="${graiReg.townCode },${graiReg.countryCode }"></simple:showName>
							${graiReg.groupName }
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">
							地块总面积(亩)：
						</td>
						<td class="table_common_td_txt_style">
							${zmj }
						</td>
						<td class="table_common_td_label_style">
							已备案地块面积(亩)：
						</td>
						<td class="table_common_td_txt_style">
							${registeredTotalYield}
						</td>
						<td class="table_common_td_label_style">
							收粮面积(亩)：
						</td>
						<td class="table_common_td_txt_style">
							${grainTotalYield }
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<table class="t1" id="datas" width="100%" style="table-layout:fixed;border-collapse: collapse;font-size:14px;">
								<tr>
									<th width="20%" height="20" align="center" nowrap >土地类型</td>
									<th width="15%" height="20" align="center" nowrap>实（亩）</td>
									<th width="15%" height="20" align="center" nowrap>测量（亩）</td>
									<th width="20%" height="20" align="center" nowrap>土地类别</td>
									<th width="30%" height="20" align="center" nowrap>地块名称</td>
								</tr>
								<c:forEach var="landInfo" items="${landList}" varStatus="status">
									<tr>
										<td align="center" height="20">
											<simple:showName entityName="commonData" codeKey="PlowlandType" value="${landInfo.landType }"></simple:showName>
										</td>
										<td align="center" height="20">${landInfo.contractArea }</td>
										<td align="center" height="20">${landInfo.measurementMu }</td>
										<td align="center" height="20">
											<simple:showName entityName="commonData" codeKey="PlowlandClass" value="${landInfo.landClass }"></simple:showName>
										</td>
										<td align="center" height="20">${landInfo.landName }</td>
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<legend>地块举证材料</legend>
				<table class="table_common_style">
					<tr>
						<td>
							<table height="100px;" id="imgList" class="easyui-datagrid" striped="true" singleSelect="true">
								<thead>
									<tr>
										<th field="originalName" width="70%" align="center">举证凭证</td>
										<th field="fileInfo" width="30%" align="center">说明</td>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<legend>购种信息</legend>
				<table>
					<tr>
						<td class="table_common_td_label_style">
							手工登记购种重量（斤）：
						</td>
						<td class="table_common_td_txt_style">
							<f:formatNumber type="number" value="${seedPurchaseTotal }" pattern="0.00" maxFractionDigits="2"/>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<legend>购种举证材料</legend>
				<table class="table_common_style">
					<tr>
						<td>
							<table height="100px;" id="imgList1" class="easyui-datagrid" striped="true" singleSelect="true">
								<thead>
									<tr>
										<th field="originalName" width="70%" align="center">举证凭证</td>
										<th field="fileInfo" width="30%" align="center">说明</td>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<legend>收粮信息</legend>
				<table class="table_common_style">
					<tr>
						<td class="table_common_td_label_style">预估产量（斤）： <span id="ygDiv">${estimateTotalYield}</span></td>
						<td class="table_common_td_label_style">
							已卖出重量（斤）： <span id="ymcDiv">${soldYield}</span>
						</td>
						<td colspan="2" class="table_common_td_label_style">可卖重量（斤）： <span id="kmDiv">${surplusYield}</span></td>
					</tr>
					<tr>
						<td class="table_common_td_label_style">
							<strong>本次卖出重量（斤）：</strong>
						</td>
						<td colspan="3" class="table_common_td_txt_style">
							<f:formatNumber type="number" value="${graiReg.thisWeight }" pattern="0.00" maxFractionDigits="2"/>
						</td>
					</tr>
					<tr>
						<td class="table_common_td_label_style"><strong>处理人：</strong></td>
						<td class="table_common_td_txt_style">
							${graiReg.operatorName}
						</td>
						<td class="table_common_td_label_style"><strong>处理日期：</strong></td>
						<td class="table_common_td_txt_style">
							<fmt:formatDate value="${graiReg.operatorDate }" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
				</table>
			</fieldset>
			<fieldset class="fieldset_common_style">
				<legend>超产举证材料</legend>
				<table class="table_common_style">
					<tr>
						<td> 
							<table height="100px;" id="imgList2" class="easyui-datagrid" striped="true" singleSelect="true">
								<thead>
									<tr>
										<th field="originalName" width="70%" align="center">举证凭证</td>
										<th field="fileInfo" width="30%" align="center">说明</td>
									</tr>
								</thead>
							</table>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
	<div id="bhDialog"></div>
	<div id="uploadDialog"></div>
	
<script type="text/javascript">
	var zmj = parseFloat("${zmj}");
	var maxYield = parseFloat("${maxYield}");  //最大亩产
	var ymcls = parseFloat("${ymcls}");        //已卖出粮食
	var ygcl = 0;                  //预估产量
	var kmzl = 0;                  //可卖重量
	$(document).ready(function(){
		//初始化文件
		showFileList('14', '${graiReg.id}', '土地举证材料', 'imgPriviewOuter', 'imgPriviewInner', '', 'imgList');
		showFileList('15', '${graiReg.id}', '购种举证材料', 'imgPriviewOuter', 'imgPriviewInner', '', 'imgList1');
		showFileList('16', '${graiReg.id}', '超产举证材料', 'imgPriviewOuter', 'imgPriviewInner', '', 'imgList2');
	});


	
	var aMu = 0;   //合同亩
	var sMu = 0;   //测亩
	//调用接口查询土地信息
	function queryLand(){
		var contractorType = "01";   //默认为农户
		var contractorId = $("#idNumber").val();   //身份证号
		var idType = "01";   //默认为身份证--新需求只根据二个参数取数据:证件类型和证件号码
			
		Public.ajaxGet('../api/getContratorInfo?contratorId=' + contractorId + "&contractorIDType=" + idType + "&year=" + $("#year").combobox('getValue'), {}, function(e) {
			if (0 == e.status) {
				clearDatasTable();
				initInterfaceInfo( e.data );
			} else{
				$.messager.alert('错误', e.msg, 'error');
				//承包方
				$("#farmerName").textbox('setValue', '' );
				//联系方式
				//$("#tmp_contractorTel").textbox('setValue', contratorInfo.contractorTel );
				//乡
				$("#townCode").val('');
				//村--乡赋值后有延时,所以放后面
				$("#countryCode").val('');
				//屯
				$("#groupName").textbox('setValue', '' );
				//乡
				$("#tmp_townCodeView").combobox('setValue', '');
				//村
				$("#tmp_countryCodeView").combobox('setValue', '');
				
				//设值面积
				$("#actualMu").val('');
				$("#measurementMu").val('');
				$("#ygDiv").html( '0.00' );
				$("#kmDiv").html( '0.00' );
				$("#ymcDiv").html( '0.00' );
				
				//$("#townCode").combobox({disabled: true});
				//$("#countryCode").combobox({disabled: true});
				clearDatasTable();
			}
		});
	}
	//初始化信息
	function initInterfaceInfo( data ){
		//基本信息
		var contratorInfo = data.peasant;
		//承包方
		$("#farmerName").textbox('setValue', contratorInfo.contractorName );
		$("#groupName").textbox('setValue', contratorInfo.groupName );
		var params = {'cityCode': $('#cityCode').combobox('getValue'), 'townCode':contratorInfo.townCode, 'countryCode':contratorInfo.countryCode};
		Public.ajaxGet('../areaDevision/getAreaDevisions', params, function(e) {
			if (200 == e.status) {
				 addTownAndCountryOptions(JSON.parse(e.data));
			} else {
				Public.tips({
					type : 1,
					content : "失败！" + e.msg
				});
			}
		});
		//土地列表信息
		var landInfo = data.contract;
		for( var contract in landInfo ){
			//类型
			var landType = landInfo[contract].landTypeName==null?'':landInfo[contract].landTypeName;
			//合同面积
			var actuMu = landInfo[contract].contractArea;
			aMu += actuMu;
			//实测面积
			var measurementMu = landInfo[contract].measurementMu;
			sMu += measurementMu;
			//类别
			var landClass = landInfo[contract].landClassName==null?'':landInfo[contract].landClassName;
			//alert( landClass );
			//位置
			var landlocation = landInfo[contract].landName;
			
			addRow("datas", landType, actuMu, measurementMu, landClass, landlocation );
		}
		
		//设值面积
		$("#actualMu").val( aMu );
		$("#measurementMu").val( sMu );
		
		zmj = parseFloat(data.zmj);
		ymcls = parseFloat(data.ymcls);
		comValue($('#seedPurchaseTotal').val());
	}
	//添加行--土地信息
	function addRow(tableId, landTypeText, actuMu, measurementMu, landClassText, landlocation ){
		var testTbl = document.getElementById(tableId);
		var maxRowNum =  testTbl.rows.length;  //获取最大行数
		//添加一行
		var newTr = testTbl.insertRow( maxRowNum );
		//添加5列
		var landType = newTr.insertCell(0);
		landType.setAttribute("align", "center");
		landType.setAttribute("height", "20");
		landType.innerHTML = landTypeText;
		var sm = newTr.insertCell(1);
		sm.setAttribute("align", "center");
		sm.innerHTML= actuMu;
		var cm = newTr.insertCell(2);
		cm.setAttribute("align", "center");
		cm.innerHTML= measurementMu;
		var landClass = newTr.insertCell(3);
		landClass.setAttribute("align", "center");
		landClass.innerHTML = landClassText;
		var landLocation = newTr.insertCell(4);
		landLocation.setAttribute("align", "center");
		landLocation.innerHTML = landlocation;
	}
	function comValue( num ){
		if(num==null||num==''||num==0){
			return;
		}
		if(zmj==undefined || zmj==null || zmj=='' || zmj==0){
			alert('土地面积为0,请先确权!');
			return false;
		}
		//计算值 :测量亩累加值  购种重量/10 取最小的  * 最大亩产    =   预估产量 
		if( num / 10 < zmj ){
			ygcl = maxYield * (num /10);
		}else{
			ygcl = maxYield * zmj;
		}
		$("#ygDiv").html( numberDecimalDigits(ygcl,2) );
		kmzl = ygcl - ymcls;
		$("#kmDiv").html(numberDecimalDigits( kmzl,2) );
		$("#ymcDiv").html( numberDecimalDigits(ymcls,2) );
	}
	

	function clearDatasTable(){
		$("#datas").html("<tr><th width=100 height=20 align=center nowrap >土地类型</th><th width=100 height=20 align=center nowrap>实（亩）</th><th width=100 height=20 align=center nowrap>测量（亩）</th><th width=100 height=20 align=center nowrap>土地类别</th><th width=120 height=20 align=center nowrap>地块名称</th></tr>");
	}
	function check(){
		if( $("#companyCode").combobox('getValue') == "" ){
			alert("请选择企业！");
			return false;
		}
		if($("#farmerName").val()==""){
			alert("请填写姓名！");
			return false;
		}
		if($("#idNumber").val()==""){
			alert("请填写身份证号！");
			return false;
		}
		if($("#countryCode").val()==""||$("#townCode").val()==""||$("#groupName").val()==""){
			alert("请选择地址！");
			return false;
		}
		if($("#seedPurchaseTotal").val()==""){
			alert("请填写手工登记购种数量！");
			return false;
		}else if (!isNumber($("#seedPurchaseTotal").val())){
			alert("手工登记购种数量请输入数字！");
			return false;
		}			
		if($("#thisWeight").val()==""){
			alert("请填写本次卖出重量！");
			return false;
		}else if (!isNumber($("#thisWeight").val())){
			alert("本次卖出重量请输入数字！");
			return false;
		}			
		if($("#operatorName").val()==""){
			alert("请填写处理人！");
			return false;
		}
		return true;
	}	
	
	function addTownAndCountryOptions(obj){
		$('#tmp_townCodeView').combobox({
			valueField:'id',
			textField:'text',
			onChange:function(){return false;}
		});
		$('#tmp_countryCodeView').combobox({
			valueField:'id',
			textField:'text',
			onChange:function(){return false;}
		});
		$('#tmp_townCodeView').combobox('clear');
		$('#tmp_countryCodeView').combobox('clear');
		$('#tmp_townCodeView').combobox('loadData',[{'id':obj[0].id,'text': obj[0].text}]);
		$('#tmp_countryCodeView').combobox('loadData',[{'id':obj[1].id,'text': obj[1].text}]);
		$('#tmp_townCodeView').combobox('setValue',obj[0].id);
		$('#tmp_countryCodeView').combobox('setValue',obj[1].id);
		$('#townCode').val(obj[0].id);
		$('#countryCode').val(obj[1].id);
	}
	function retList(){
		window.document.location.href = "${ctx}/graiReg/list?year=${year}&companyCode=${companyCode}&beginDate=${beginDate}&endDate=${endDate}&status=${status}&page=${page}&pageSize=${pageSize}";
	}
</script>
</body>
</html>