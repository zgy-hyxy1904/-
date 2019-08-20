<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="simple" uri="http://www.spsoft.com/tags/simple"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公共接口说明</title>
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
</head>
<body class="easyui-layout">
	<div region="center" border="false" style="padding:0 10px;">	
		<H2>土地认证接口:</H2>
		<p>
			参数:contractorType(承包方类型)、contractorIDType(证件类型)、contractorID(证件ID)
		</p>
		<p>
			返回json
		</p>
		<p>调用试例：</>
		<p>
			http://localhost:8080/suyuan/api/getContratorInfo?contractorType=01&contractorIDType=01&contractorID=232103196211031736
		</p>
		<p>数据格式：</p>
		<p>
		{
    "status": 0,
    "msg": "success",
    "data": {
        "contract": [
            {
                "id": 2713,
                "contractorCode": "230184206200000001",
                "graphCode": "4974.00-42599.50",
                "landCode": "02887",
                "landName": "老于坟地南北垄",
                "contractArea": 1.44,
                "measurementMu": 1.44,
                "eastTo": "刘有",
                "westTo": "刘长清",
                "southTo": "刘长江",
                "northTo": "沟渠",
                "landLevel": "二等地",
                "landType": "02",
                "isBaseLand": "是",
                "ownership": "",
                "landClass": null,
                "disputeReason": null,
                "landPurpose": "种植业",
                "contractStartDate": null,
                "contractYear": null,
                "contractEndDate": null
            },
            {
                "id": 2714,
                "contractorCode": "230184206200000001",
                "graphCode": "4973.50-42599.00",
                "landCode": "03616",
                "landName": "四河门南北垄",
                "contractArea": 2.14,
                "measurementMu": 2.14,
                "eastTo": "于忠孝",
                "westTo": "关玉忠",
                "southTo": "道路",
                "northTo": "道路",
                "landLevel": "三等地",
                "landType": "02",
                "isBaseLand": "是",
                "ownership": "",
                "landClass": null,
                "disputeReason": null,
                "landPurpose": "种植业",
                "contractStartDate": null,
                "contractYear": null,
                "contractEndDate": null
            },
            {
                "id": 2715,
                "contractorCode": "230184206200000001",
                "graphCode": "4974.00-42600.50",
                "landCode": "02974",
                "landName": "房东南北垄",
                "contractArea": 3.97,
                "measurementMu": 3.97,
                "eastTo": "宁学彬",
                "westTo": "王占江2",
                "southTo": "道路",
                "northTo": "道路",
                "landLevel": "一等地",
                "landType": "02",
                "isBaseLand": "是",
                "ownership": "",
                "landClass": null,
                "disputeReason": null,
                "landPurpose": "种植业",
                "contractStartDate": null,
                "contractYear": null,
                "contractEndDate": null
            },
            {
                "id": 2716,
                "contractorCode": "230184206200000001",
                "graphCode": "4974.00-42599.50",
                "landCode": "03046",
                "landName": "坝下稻地",
                "contractArea": 0.39,
                "measurementMu": 0.39,
                "eastTo": "沟渠",
                "westTo": "沟渠",
                "southTo": "道路",
                "northTo": "沟渠",
                "landLevel": "一等地",
                "landType": "01",
                "isBaseLand": "否",
                "ownership": "",
                "landClass": null,
                "disputeReason": null,
                "landPurpose": "种植业",
                "contractStartDate": null,
                "contractYear": null,
                "contractEndDate": null
            },
            {
                "id": 2717,
                "contractorCode": "230184206200000001",
                "graphCode": "4974.50-42600.00",
                "landCode": "02373",
                "landName": "豪北大地",
                "contractArea": 1.64,
                "measurementMu": 1.64,
                "eastTo": "道路",
                "westTo": "道路",
                "southTo": "杜才",
                "northTo": "刘长清",
                "landLevel": "二等地",
                "landType": "02",
                "isBaseLand": "是",
                "ownership": "",
                "landClass": null,
                "disputeReason": null,
                "landPurpose": "种植业",
                "contractStartDate": null,
                "contractYear": null,
                "contractEndDate": null
            },
            {
                "id": 2718,
                "contractorCode": "230184206200000001",
                "graphCode": "4975.00-42600.00",
                "landCode": "02040",
                "landName": "岗上",
                "contractArea": 0.12,
                "measurementMu": 0.97,
                "eastTo": "王富",
                "westTo": "沟渠",
                "southTo": "刘长山",
                "northTo": "朱和",
                "landLevel": "一等地",
                "landType": "02",
                "isBaseLand": "是",
                "ownership": "",
                "landClass": null,
                "disputeReason": null,
                "landPurpose": "种植业",
                "contractStartDate": null,
                "contractYear": null,
                "contractEndDate": null
            }
        ],
        "peasant": {
            "id": 1,
            "contractorCode": "230184206200000001",
            "contractorName": "周玉忠",
            "contractorID": "232103196211031736",
            "contractorIDType": "01",
            "contractorAge": 53,
            "contractorSex": "男",
            "contractorBirth": "1962-11-03",
            "contractorTel": "13234990099",
            "cityCode": "230184",
            "townCode": "206",
            "countryCode": "200",
            "groupName": "民意乡九合村八里屯",
            "contractorZipcode": "150207",
            "contractorhouseholdType": "农业户口",
            "contractorType": "01",
            "contractId": null,
            "contractorNation": "汉族",
            "rightId": null,
            "attestor": null,
            "attestMechanism": null,
            "attestDate": null,
            "attestNo": null,
            "landPurpose": "种植业",
            "rightGetWay": "家庭承包",
            "contractStartDate": 883584000000,
            "contractYear": 30,
            "contractEndDate": 1830182400000,
            "getLandPersonCount": null,
            "familyPersonCount": null,
            "surveyDate": null,
            "surveyMemo": null
        }
    }
}		</p>
	</div>
</body>
</html>