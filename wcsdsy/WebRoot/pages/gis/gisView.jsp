<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="width:100%;height:100%;overflow:hidden">
<head>
	<title>五常农业物联网监测平台</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="${ctx}/style/index.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/js/themes/color.css">
	
	<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/js/form2js.js"></script>
	<script type="text/javascript" src="${ctx}/js/json2.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
    <style type="text/css">
        html
        {
            height: 100%;
        }
        body
        {
            height: 100%;
            margin: 0px;
            padding: 0px;
        }
        #container
        {
            height: 100%;
        }
		/* 隐藏百度Logo */
		.anchorBL{ 
			display:none; 
		} 
    </style>
	<!-- 此处的ak值需要在系统中的配置文件中配置 -->
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=XdsxuG3CGVNXmkbSNAw1aMDb">        
    </script>
</head>
<body>
<!-- 	<div id="topName" -->
<!-- 			style="background-color:#4B4B4B;position:absolute;left:0%;top:0%;width:100%;height:4.9%;z-index:5000;text-align:center"> -->
<!-- 			<font color="#FFFFFF" size="5em">五常农业物联网监测平台</font> -->
<!-- 	</div> -->
	<div id="dateSelectDiv"
			style="position:absolute;left:2%;top:5.5%;width:40%;height:4%;z-index:5000;text-align:left;display:none">
			<input type="hidden" id="moniBizType" name="moniBizType">
			<font color="#00CC99" size="2.5em">年度:</font>
			<select id="yearCode" name="yearCode" class="easyui-combobox" style="width:60px;height:25px" data-options="editable:false"></select>
			<font color="#00CC99" size="2.5em">日期:</font>
			<select id="dateCode" name="dateCode" class="easyui-combobox" style="width:50px;height:25px" data-options="editable:false"></select>
	</div>
	<div id="airdiv"
		style='display:"";position:absolute;left:1%;top:10%;width:6%;height:12%;z-index:7000;background-image:url("${ctx}/images/airgene.png");background-size: 100% auto;background-repeat:no-repeat'
		onclick="initPage('air');"></div>
	<div id="waterdiv"
		style='display:"";position:absolute;left:1%;top:32%;width:6%;height:12%;z-index:7000;background-image:url("${ctx}/images/watergene.png");background-size: 100% auto;background-repeat:no-repeat'
		onclick="initPage('water');"></div>
	<div id="soildiv"
		style='display:"";position:absolute;left:1%;top:54%;width:6%;height:12%;z-index:7000;background-image:url("${ctx}/images/soilgene.png");background-size: 100% auto;background-repeat:no-repeat'
		onclick="initPage('soil');"></div>
	<div id="pmaqidiv"
		style='display:"";position:absolute;left:1%;top:76%;width:6%;height:12%;z-index:7000;background-image:url("${ctx}/images/pmaqigene.png");background-size: 100% auto;background-repeat:no-repeat'
		onclick="initPage('pmaqi');"></div>
    <!--用隐藏控件的方式记录数据集对象-->
    <input type='hidden' id='hid_Points' />
    <div id="container">
    </div>
    <img src="http://www.baidu.com/img/baidu_sylogo1.gif" style="display:none" onerror="imgLoadError();" />
<!-- 	<p></p> -->
<!-- 	<input type="button" value=" 添 加 点 " onclick="AddMarker();" style="margin-left:20px;" /> -->
    <script type="text/javascript">

        //表示当前未联网
        function imgLoadError() {
            window.external.AlertJSMsg('您的电脑暂未联网，地图加载失败！');
        }

		// 百度地图API功能
		var map = new BMap.Map("container"); // 创建Map实例
		map.centerAndZoom(new BMap.Point(127.362228, 45.032235), 13); // 初始化地图,设置中心点坐标和地图级别
		//map.addControl(new BMap.MapTypeControl()); //添加地图类型控件
        //map.centerAndZoom(point, 13);                  // 初始化地图，设置中心点坐标和地图级别
        map.addControl(new BMap.NavigationControl({ type: BMAP_NAVIGATION_CONTROL_LARGE,anchor: BMAP_ANCHOR_TOP_RIGHT }));  // 添加平移缩放控件，PC端默认在左上方
        //map.addControl(new BMap.OverviewMapControl({isOpen:true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT})); // 添加缩略地图控件
        map.addControl(new BMap.ScaleControl());       // 比例尺控件
		map.setCenter("五常市");     
		map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
		map.setMapStyle({style:'grassgreen'}); 
		var bdary = new BMap.Boundary();
		bdary.get("五常市", function (rs) {       //获取行政区域       
			map.clearOverlays();        //清除地图覆盖物   

			//添加遮罩层
			//思路：利用行政区划点的集合与外围自定义东南西北形成一个环形遮罩层
			//1.获取选中行政区划边框点的集合  rs.boundaries[0]
			var strs = new Array();
			strs = rs.boundaries[0].split(";");
			var EN = "";    //行政区划东北段点的集合
			var NW = ""; //行政区划西北段点的集合
			var WS = ""; //行政区划西南段点的集合
			var SE = ""; //行政区划东南段点的集合
			var pt_e = strs[0]; //行政区划最东边点的经纬度
			var pt_n = strs[0]; //行政区划最北边点的经纬度
			var pt_w = strs[0]; //行政区划最西边点的经纬度
			var pt_s = strs[0]; //行政区划最南边点的经纬度
			var n1 = ""; //行政区划最东边点在点集合中的索引位置
			var n2 = ""; //行政区划最北边点在点集合中的索引位置
			var n3 = ""; //行政区划最西边点在点集合中的索引位置
			var n4 = ""; //行政区划最南边点在点集合中的索引位置

			//2.循环行政区划边框点集合找出最东南西北四个点的经纬度以及索引位置
			for (var n = 0; n < strs.length; n++) {
				var pt_e_f = parseFloat(pt_e.split(",")[0]);
				var pt_n_f = parseFloat(pt_n.split(",")[1]);
				var pt_w_f = parseFloat(pt_w.split(",")[0]);
				var pt_s_f = parseFloat(pt_s.split(",")[1]);

				var sPt = new Array();
				try {
					sPt = strs[n].split(",");
					var spt_j = parseFloat(sPt[0]);
					var spt_w = parseFloat(sPt[1]);
					if (pt_e_f < spt_j) {   //东
						pt_e = strs[n];
						pt_e_f = spt_j;
						n1 = n;
					}
					if (pt_n_f < spt_w) {  //北
						pt_n_f = spt_w;
						pt_n = strs[n];
						n2 = n;
					}

					if (pt_w_f > spt_j) {   //西
						pt_w_f = spt_j;
						pt_w = strs[n];
						n3 = n;
					}
					if (pt_s_f > spt_w) {   //南
						pt_s_f = spt_w;
						pt_s = strs[n];
						n4 = n;
					}
				}
				catch (err) {
					alert(err);
				}
			}
			//3.得出东北、西北、西南、东南四段行政区划的边框点的集合
			if (n1 < n2) {     //第一种情况 最东边点在索引前面
				for (var o = n1; o <= n2; o++) {
					EN += strs[o] + ";"
				}
				for (var o = n2; o <= n3; o++) {
					NW += strs[o] + ";"
				}
				for (var o = n3; o <= n4; o++) {
					WS += strs[o] + ";"
				}
				for (var o = n4; o < strs.length; o++) {
					SE += strs[o] + ";"
				}
				for (var o = 0; o <= n1; o++) {
					SE += strs[o] + ";"
				}
			}
			else {   //第二种情况 最东边点在索引后面
				for (var o = n1; o < strs.length; o++) {
					EN += strs[o] + ";"
				}
				for (var o = 0; o <= n2; o++) {
					EN += strs[o] + ";"
				}
				for (var o = n2; o <= n3; o++) {
					NW += strs[o] + ";"
				}
				for (var o = n3; o <= n4; o++) {
					WS += strs[o] + ";"
				}
				for (var o = n4; o <= n1; o++) {
					SE += strs[o] + ";"
				}
			}
			//4.自定义外围边框点的集合
			var E_JW = "170.672126, 39.623555;";            //东
			var EN_JW = "170.672126, 81.291804;";       //东北角
			var N_JW = "105.913641, 81.291804;";        //北
			var NW_JW = "-169.604276,  81.291804;";     //西北角
			var W_JW = "-169.604276, 38.244136;";       //西
			var WS_JW = "-169.604276, -68.045308;";     //西南角
			var S_JW = "114.15563, -68.045308;";            //南
			var SE_JW = "170.672126, -68.045308 ;";         //东南角
			//4.添加环形遮罩层
			ply1 = new BMap.Polygon(EN + NW + WS + SE + E_JW + SE_JW + S_JW + WS_JW + W_JW + NW_JW + EN_JW + E_JW, { strokeColor: "none", fillColor: "rgb(213,234,217)", strokeOpacity: 0 }); //建立多边形覆盖物
			ply1.setFillOpacity(1);
			map.addOverlay(ply1);  //遮罩物是半透明的，如果需要纯色可以多添加几层
			//5. 给目标行政区划添加边框，其实就是给目标行政区划添加一个没有填充物的遮罩层
			ply = new BMap.Polygon(rs.boundaries[0], { strokeWeight: 4, strokeColor: "#4a8f73",fillColor: "" });
			map.addOverlay(ply); 
			map.setViewport(ply.getPath());    //调整视野
			
		});
    
    	$(document).ready(function(){
			$('#yearCode').combobox({
				valueField:'id', 
				textField:'text',
				onChange : function(){ 
					var moniBizType = $('#moniBizType').val();
					var yearCode = $('#yearCode').combobox('getValue'); 
					Public.ajaxGet('${ctx}/gisMap/getDateCodes', {'moniBizType':moniBizType,'yearCode':yearCode}, function(e) { 
						if (200 == e.status) { 
							$('#dateCode').combobox("loadData", JSON.parse(e.data)); 
						} else { 
							parent.parent.Public.tips({ 
								type : 1, 
								content : "失败！" + e.msg 
							}); 
						} 
					}); 
				} 
			});
			
			$('#dateCode').combobox({
				valueField:'id', 
				textField:'text',
				onChange : function(){ 
					var moniBizType = $('#moniBizType').val();
					var dateCode = $('#dateCode').combobox('getValue'); 
					reloadMap(moniBizType, dateCode);
				} 
			});
    	});
    	
    	
    	function initPage(moniBizType){
       		$('#dateSelectDiv').show();
    		$('#moniBizType').val(moniBizType);
    		reloadMap(moniBizType);
    		$('#yearCode').combobox("clear");
    		$('#dateCode').combobox("clear");
    		showLoading();
			Public.ajaxGet('${ctx}/gisMap/getYearCodes', {'moniBizType':moniBizType}, function(e) {
				if (200 == e.status) { 
					$('#yearCode').combobox("loadData", JSON.parse(e.data)); 
				} else { 
					hideLoading();
					parent.parent.Public.tips({ 
						type : 1, 
						content : "失败！" + e.msg 
					}); 
				} 
			}); 
    	}
    	
//     	function changeBtnImgs(){
//     	}
//     	function showPoints(){
//     		var params = {};
//     		var actionName = '';
//     		if(arguments.length==1){
//     			params['moniBizType'] = arguments[0];
//     			actionName = 'getNearestGisDatas';
//     		}else if(arguments.length==2){
//     			params['moniBizType'] = arguments[0];
//     			params['dateCode'] = arguments[1]; 
//     			actionName = 'getGisDatasByDate';
//     		}else{
//     			return;
//     		}
// 			Public.ajaxGet('${ctx}/gisMap/'+actionName, params, function(e) { 
// 				if (200 == e.status) { 
// 					AddMarker(JSON.parse(e.data));
// 				} else { 
// 					parent.parent.Public.tips({ 
// 						type : 1, 
// 						content : "失败！" + e.msg 
// 					}); 
// 				} 
// 			}); 
//     	}
    	
    	/*测试数据
		//声明对象集合
		var Points = [];
		//大气
		var p1 = {
			MonitorPointType:'01',
			MonitorPointCode:'M1001',
			MonitorPointName:'马家围子屯',
			Longitude:'127.252462',
			Latitude:'44.953581',
			TSP:'0.21',
			SO2:'0.013',
			NO2:'0.009',
			MonitorDate:'2015-08-15'
		};
		//水质
		var p2 = {
			MonitorPointType:'02',
			MonitorPointCode:'M1002',
			MonitorPointName:'龙凤山水库',
			Longitude:'127.217177',
			Latitude:'44.916547',
			PH:'7.05',
			DOValue:'10.2',
			CODMn:'3.3',
			BOD5:'24',
			NH3N:'2.4',
			TP:'0.574',
			CODCr:'16',
			MonitorDate:'2015-08-15'
		};
		//土壤
		var p3 = {
			MonitorPointType:'03',
			MonitorPointCode:'M1003',
			MonitorPointName:'红星',
			Longitude:'127.288179',
			Latitude:'44.870571',
			OMValue:'19.8',
			AlkelineN:'85',
			OlsenP:'18',
			OlsenK:'69',
			PH:'5.7',
			MonitorDate:'2015-08-15'
		};
		//PM2.5
		var p4 = {
			MonitorPointType:'04',
			MonitorPointCode:'M1004',
			MonitorPointName:'双泉村',
			Longitude:'127.245347',
			Latitude:'44.877725',
			AQI:'12',
			AQIName:'优',
			PM2_5:'8',
			PM10:'12',
			CO:'0.5',
			NO2:'24',
			O3:'32',
			SO2:'2',
			MonitorDate:'2015-08-15'
		};
		//将对象添加至集合中
		Points.push(p1);
		Points.push(p2);
		Points.push(p3);
		Points.push(p4);
		*/
		
		//向地图中添加点对象
		function AddMarker(points){
			//设置图标的路径
			var iconURL = '${ctx}/images/UnitIns.png';
			for(var i = 0;i < points.length;i++){
				var obj = points[i];
				//实例化百度的Point对象
				var p = new BMap.Point(obj.Longitude,obj.Latitude);
				//实例化百度的Marker对象
				var marker = new BMap.Marker(p, { icon: new BMap.Icon(iconURL, new BMap.Size(32, 32)) });
				//给点对象添加Label
				AddLable(p, 0, obj.MonitorPointName, 'black');
				//将点对象添加到地图中
			    map.addOverlay(marker);
				//给点对象添加Click事件 实现弹窗效果
				marker.addEventListener("mouseover", PointClick(obj,marker));
			}
		}
        //向正常节点添加单击事件
        function PointClick(obj,marker) {
            return function () {
                var infor1 = PointAddClick(obj);
                marker.openInfoWindow(infor1);
            }
        }
		//组装弹窗内的显示信息
        function PointAddClick(obj) {
		    var sContent = ''; 
			switch(obj.MonitorPointType){
				//01 = 大气
				case '01':
					if(obj.TSP==null&&obj.SO2==null&&obj.NO2==null){
						sContent = "<div style='font-size:12px; line-height:1.5; font-family:微软雅黑; font-weight:500;'>" +
						  "监测点位：" + obj.MonitorPointName +
						  "<br />该点位无监测信息"
						  "</div>";
					}else{
						sContent = "<div style='font-size:12px; line-height:1.5; font-family:微软雅黑; font-weight:500;'>" +
						  "监测日期：" + obj.MonitorDate +
						  "<br />监测点位：" + obj.MonitorPointName +
						  "<br />TSP(mg/m³)：" + obj.TSP  +
						  "<br />SO2(mg/m³)：" + obj.SO2 +
						  "<br />NO2(mg/m³)：" + obj.NO2 +
						  "</div>";
					}
	
					break;
				//02 = 水质
				case '02':
					if(obj.PH==null&&obj.DOValue==null&&obj.CODMn==null
							&&obj.BOD5==null&&obj.NH3N==null
							&&obj.TP==null&&obj.CODCr==null){
						sContent = "<div style='font-size:12px; line-height:1.5; font-family:微软雅黑; font-weight:500;'>" +
						  "断面名称：" + obj.MonitorPointName +
						  "<br />该点位无监测信息"
						  "</div>";	
					}else{
						sContent = "<div style='font-size:12px; line-height:1.5; font-family:微软雅黑; font-weight:500;'>" +
						  "监测日期：" + obj.MonitorDate +
						  "<br />断面名称：" + obj.MonitorPointName +
						  "<br />PH：" + obj.PH +
						  "<br />DO(mg/l)：" + obj.DOValue +
						  "<br />CODMn(mg/l)：" + obj.CODMn +
						  "<br />BOD5(mg/l)：" + obj.BOD5 +
						  "<br />NH3N(mg/l)：" + obj.NH3N +
						  "<br />TP(mg/l)：" + obj.TP +
						  "<br />CODCr(mg/l)：" + obj.CODCr +
						  "</div>";	
					}

					break;
				//03 = 土壤
				case '03':
					if(obj.OMValue==null&&obj.AlkelineN==null&&obj.OlsenP==null
							&&obj.OlsenK==null&&obj.PH==null){
						sContent = "<div style='font-size:12px; line-height:1.5; font-family:微软雅黑; font-weight:500;'>" +
						  "监测点位：" + obj.MonitorPointName +
						  "<br />该点位无监测信息"
						  "</div>";
					}else{
						sContent = "<div style='font-size:12px; line-height:1.5; font-family:微软雅黑; font-weight:500;'>" +
						  "监测日期：" + obj.MonitorDate +
						  "<br />监测点位：" + obj.MonitorPointName +
						  "<br />有机质(g/kg)：" + obj.OMValue  +
						  "<br />碱解氮(mg/kg)：" + obj.AlkelineN +
						  "<br />有效磷(mg/kg)：" + obj.OlsenP +
						  "<br />速效钾(mg/kg)：" + obj.OlsenK +
						  "<br />PH值：" + obj.PH +
						  "</div>";
					}
					break;
				//04 = PM2.5
				case '04':
					if(obj.AQI==null&&obj.AQIName==null
							&&obj.PM2_5==null&&obj.PM10==null&&obj.CO==null
							&&obj.NO2==null&&obj.O3==null&&obj.SO2==null){
						sContent = "<div style='font-size:12px; line-height:1.5; font-family:微软雅黑; font-weight:500;'>" +
						  "监测点位：" + obj.MonitorPointName +
						  "<br />该点位无监测信息"
						  "</div>";
					}else{
 						sContent = "<div style='font-size:12px; line-height:1.5; font-family:微软雅黑; font-weight:500;'>" +
						  "监测日期：" + obj.MonitorDate +
						  "<br />监测点位：" + obj.MonitorPointName +
						  "<br />空气质量指数：" + obj.AQI  +
						  "<br />空气质量指数类别：" + obj.AQIName +
						  "<br />细颗粒物(μg/m3)：" + obj.PM2_5 +
						  "<br />可吸入颗粒物(μg/m3)：" + obj.PM10 +
						  "<br />一氧化碳(mg/m3)：" + obj.CO +
						  "<br />二氧化氮(μg/m3)：" + obj.NO2 +
						  "<br />臭氧(mg/m3)：" + obj.O3 +
						  "<br />二氧化硫(mg/m3)：" + obj.SO2 +
						  "</div>";
					}

				  break;
				default:
				  break;
			}
            var infoWindow = new BMap.InfoWindow(sContent);  // 创建信息窗口对象
            return infoWindow;
        }

		//添加普通文本
        function AddLable(p, id, text, cr) {
            var opts = {
                position: p,    // 指定文本标注所在的地理位置
                offset: new BMap.Size(0, 0)    //设置文本偏移量
            }
            var label = new BMap.Label(text, opts, cr);  // 创建文本标注对象
            label.setStyle({
                color: cr,
                fontSize: "12px",
                height: "20px",
                lineHeight: "20px",
                fontFamily: "微软雅黑"
            });
			//将文本对象添加至地图
            map.addOverlay(label);
        }
		
		

        // 百度地图API功能
		// map = new BMap.Map("container"); // 创建Map实例
		// map.centerAndZoom(new BMap.Point(127.362228, 45.032235), 13); // 初始化地图,设置中心点坐标和地图级别
		// //map.addControl(new BMap.MapTypeControl()); //添加地图类型控件
  //       map.addControl(new BMap.NavigationControl({ type: BMAP_NAVIGATION_CONTROL_LARGE,anchor: BMAP_ANCHOR_TOP_RIGHT }));  // 添加平移缩放控件，PC端默认在左上方
  //       //map.addControl(new BMap.OverviewMapControl({isOpen:true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT})); // 添加缩略地图控件
  //       map.addControl(new BMap.ScaleControl());       // 比例尺控件
		// map.setCenter("五常市");     
		// map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
  //       //map.addControl(new BMap.NavigationControl({ type: BMAP_NAVIGATION_CONTROL_LARGE }));  // 添加平移缩放控件，PC端默认在左上方
  //       //map.addControl(new BMap.OverviewMapControl()); // 添加缩略地图控件
  //       map.addControl(new BMap.ScaleControl());       // 比例尺控件
		// map.setMapStyle({style:'grassgreen'}); 
		// bdary = new BMap.Boundary();


		function reloadMap(){
    		var params = {};
    		var actionName = '';
    		if(arguments.length==1){
    			params['moniBizType'] = arguments[0];
    			actionName = 'getNearestGisDatas';
    		}else if(arguments.length==2){
    			params['moniBizType'] = arguments[0];
    			params['dateCode'] = arguments[1]; 
    			actionName = 'getGisDatasByDate';
    		}else{
    			return;
    		}
			
			
			bdary.get("五常市", function (rs) {       //获取行政区域       
				map.clearOverlays();        //清除地图覆盖物   

				//添加遮罩层
				//思路：利用行政区划点的集合与外围自定义东南西北形成一个环形遮罩层
				//1.获取选中行政区划边框点的集合  rs.boundaries[0]
				strs = new Array();
				strs = rs.boundaries[0].split(";");
				EN = "";    //行政区划东北段点的集合
				NW = ""; //行政区划西北段点的集合
				WS = ""; //行政区划西南段点的集合
				SE = ""; //行政区划东南段点的集合
				pt_e = strs[0]; //行政区划最东边点的经纬度
				pt_n = strs[0]; //行政区划最北边点的经纬度
				pt_w = strs[0]; //行政区划最西边点的经纬度
				pt_s = strs[0]; //行政区划最南边点的经纬度
				n1 = ""; //行政区划最东边点在点集合中的索引位置
				n2 = ""; //行政区划最北边点在点集合中的索引位置
				n3 = ""; //行政区划最西边点在点集合中的索引位置
				n4 = ""; //行政区划最南边点在点集合中的索引位置

				//2.循环行政区划边框点集合找出最东南西北四个点的经纬度以及索引位置
				for (var n = 0; n < strs.length; n++) {
					var pt_e_f = parseFloat(pt_e.split(",")[0]);
					var pt_n_f = parseFloat(pt_n.split(",")[1]);
					var pt_w_f = parseFloat(pt_w.split(",")[0]);
					var pt_s_f = parseFloat(pt_s.split(",")[1]);

					var sPt = new Array();
					try {
						sPt = strs[n].split(",");
						var spt_j = parseFloat(sPt[0]);
						var spt_w = parseFloat(sPt[1]);
						if (pt_e_f < spt_j) {   //东
							pt_e = strs[n];
							pt_e_f = spt_j;
							n1 = n;
						}
						if (pt_n_f < spt_w) {  //北
							pt_n_f = spt_w;
							pt_n = strs[n];
							n2 = n;
						}

						if (pt_w_f > spt_j) {   //西
							pt_w_f = spt_j;
							pt_w = strs[n];
							n3 = n;
						}
						if (pt_s_f > spt_w) {   //南
							pt_s_f = spt_w;
							pt_s = strs[n];
							n4 = n;
						}
					}
					catch (err) {
						alert(err);
					}
				}
				//3.得出东北、西北、西南、东南四段行政区划的边框点的集合
				if (n1 < n2) {     //第一种情况 最东边点在索引前面
					for (var o = n1; o <= n2; o++) {
						EN += strs[o] + ";"
					}
					for (var o = n2; o <= n3; o++) {
						NW += strs[o] + ";"
					}
					for (var o = n3; o <= n4; o++) {
						WS += strs[o] + ";"
					}
					for (var o = n4; o < strs.length; o++) {
						SE += strs[o] + ";"
					}
					for (var o = 0; o <= n1; o++) {
						SE += strs[o] + ";"
					}
				}
				else {   //第二种情况 最东边点在索引后面
					for (var o = n1; o < strs.length; o++) {
						EN += strs[o] + ";"
					}
					for (var o = 0; o <= n2; o++) {
						EN += strs[o] + ";"
					}
					for (var o = n2; o <= n3; o++) {
						NW += strs[o] + ";"
					}
					for (var o = n3; o <= n4; o++) {
						WS += strs[o] + ";"
					}
					for (var o = n4; o <= n1; o++) {
						SE += strs[o] + ";"
					}
				}
				//4.自定义外围边框点的集合
				E_JW = "170.672126, 39.623555;";            //东
				EN_JW = "170.672126, 81.291804;";       //东北角
				N_JW = "105.913641, 81.291804;";        //北
				NW_JW = "-169.604276,  81.291804;";     //西北角
				W_JW = "-169.604276, 38.244136;";       //西
				WS_JW = "-169.604276, -68.045308;";     //西南角
				S_JW = "114.15563, -68.045308;";            //南
				SE_JW = "170.672126, -68.045308 ;";         //东南角
				//4.添加环形遮罩层
				ply1 = new BMap.Polygon(EN + NW + WS + SE + E_JW + SE_JW + S_JW + WS_JW + W_JW + NW_JW + EN_JW + E_JW, { strokeColor: "none", fillColor: "rgb(213,234,217)", strokeOpacity: 0 }); //建立多边形覆盖物
				ply1.setFillOpacity(1);
				map.addOverlay(ply1);  //遮罩物是半透明的，如果需要纯色可以多添加几层
				//5. 给目标行政区划添加边框，其实就是给目标行政区划添加一个没有填充物的遮罩层
				ply = new BMap.Polygon(rs.boundaries[0], { strokeWeight: 4, strokeColor: "#4a8f73",fillColor: "" });
				map.addOverlay(ply); 
				map.setViewport(ply.getPath());    //调整视野
				//loadCityMapInfo(map);
				
				Public.ajaxGet('${ctx}/gisMap/'+actionName, params, function(e) { 
					hideLoading();
					if (200 == e.status) { 
						AddMarker(JSON.parse(e.data));
					} else { 
						parent.parent.Public.tips({ 
							type : 1, 
							content : "失败！" + e.msg 
						}); 
					} 
				}); 
			});
			
    		var divIdHeads = ['air','water','soil','pmaqi'];
    		for(var idx=0;idx<divIdHeads.length;idx++){
    			if($('#moniBizType').val() == divIdHeads[idx]){
    				$("#"+divIdHeads[idx]+"div").css("background-image","url('${ctx}/images/"+$('#moniBizType').val()+"click.png')");
    			}else{
    				$("#"+divIdHeads[idx]+"div").css("background-image","url('${ctx}/images/"+divIdHeads[idx]+"gene.png')");
    			}
    		}
        }
    </script>
</body>
</html>
