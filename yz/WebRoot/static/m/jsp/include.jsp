<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="wap-font-scale" content="no">
<meta http-equiv="Pragma" content="no-cache">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,minimum-scale=1.0, user-scalable=0" />
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=56fcf9dd3597d584d13c3dd5ae63c40a"></script>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=d3894bebfcae22426384fa82997f04d9"></script>
<script type="text/javascript">
var sy = sy || {};
sy.path = "${pageContext.request.scheme}" + "://" + "${pageContext.request.serverName}" + ":" + "${pageContext.request.serverPort}" + "${pageContext.request.contextPath}";
sy.contextPath = "${pageContext.request.contextPath}";
if(sy.contextPath == "")sy.contextPath = "/";
</script>
<link rel="apple-touch-icon" href="${path}/static/m/images/favicon.png"/>
<link rel="shortcut icon" type="image/x-icon" href="${path}/static/m/images/favicon.png" media="screen">
<%-- 引入jQuery --%>
<script src="${path}/static/m/js/jquery-1.9.1.js" type="text/javascript" charset="utf-8"></script>

<%-- 引入angular --%>
<script src="http://apps.bdimg.com/libs/angular.js/1.3.9/angular.js"></script>
<script src="http://apps.bdimg.com/libs/angular.js/1.3.9/angular-animate.js"></script>
<script src="http://apps.bdimg.com/libs/angular.js/1.3.9/angular-route.js"></script>
<script src="http://apps.bdimg.com/libs/angular.js/1.3.9/angular-sanitize.js"></script>
<script src="${path}/static/m/js/ng-infinite-scroll.js"></script>

<%-- web弹层组件 --%>
<script type="text/javascript" src="${path}/static/m/js/layer.m/layer.m.js" ></script>
<%-- 引入jquery扩展 --%>
<script src="${path}/static/m/js/syJquery.js" type="text/javascript" charset="utf-8"></script>

<%-- 引入UT工具扩展 --%>
<script src="${path}/static/m/js/ut.js" type="text/javascript" charset="utf-8"></script>
<%-- <script src="${path}/static/m/js/auto.js" type="text/javascript" charset="utf-8"></script> --%>
<script type="text/javascript">
sy.areaCode = sy.getUrlParam('city');
if(sy.areaCode!=null){
	sessionStorage.areaCode = sy.areaCode;
}
sy.areaCode = sessionStorage.areaCode;
sy.areaName = sessionStorage.areaName;
$(function(){
	if(sy.areaCode != undefined && sy.areaName == undefined ){
		$.ajax({
			url:sy.path + "/m/i/getCityName",
			data:{code:sy.areaCode},
			dataType:'json',
			type:'post',
			async:false,
			success: function(result) {
				sy.closeLoad();
		    	if (result.code == 0) {
		    		var msg = result.msg.split(",");
		    		sessionStorage.areaCode = msg[0];
		    		sessionStorage.areaName = msg[1];
		    		
		    		location.reload();
				}else{
					sy.alert("定位失败");
				}
			}
		});
	}else if(sy.areaCode == undefined || sy.areaName == undefined){
		sy.openLoad();
		//var myCity = new BMap.LocalCity();
		//myCity.get(initLocation);
		AMap.service(["AMap.CitySearch"], function() {
			var citysearch = new AMap.CitySearch();
			citysearch.getLocalCity(initAMapLocation);
		});
	}
	
	$("body").append("<div class=\"w640\"><div class=\"home\" onclick=\"location.href='"+sy.path+"/m/';\"></div></div>");
	
	if(location.href.indexOf("/login") == -1 && location.href.indexOf("/regist") == -1 && location.href.indexOf("/forgetPwd") == -1){
		sessionStorage.lastUnloginUrl = location.href;
	}
});

//高德定位
function initAMapLocation(status, result){
	if(status === 'complete' && result.info === 'OK'){
		if(result && result.city && result.bounds) {
			var cityName = result.city;
			//var citybounds = result.bounds;
			if(cityName.substring(cityName.length,cityName.length-1) == "市"){
				cityName = cityName.substring(0,cityName.length-1);
			}
			
			$.ajax({
				url:sy.path + "/m/i/cityMatch",
				data:{name:cityName},
				dataType:'json',
				type:'post',
				async:false,
				success:  function(result) {
					sy.closeLoad();
			    	if (result.code == 0) {
			    		var msg = result.msg.split(",");
			    		sessionStorage.areaCode = msg[0];
			    		sessionStorage.areaName = msg[1];
			    		location.reload();
					}else{
						sy.alert("定位失败");
					}
				}
			});
	 
		}
	}
}


//定位
function initLocation(result){
	var cityName = result.name;
	if(cityName.substring(cityName.length,cityName.length-1) == "市"){
		cityName = cityName.substring(0,cityName.length-1);
	}
	
	$.ajax({
		url:sy.path + "/m/i/cityMatch",
		data:{name:cityName},
		dataType:'json',
		type:'post',
		async:false,
		success:  function(result) {
			sy.closeLoad();
	    	if (result.code == 0) {
	    		var msg = result.msg.split(",");
	    		sessionStorage.areaCode = msg[0];
	    		sessionStorage.areaName = msg[1];
	    		
	    		location.reload();
			}else{
				sy.alert("定位失败");
			}
		}
	});
}
</script>
<script type="text/javascript">
$(function(){
	//html root的字体计算应该放在最前面，这样计算就不会有误差了/
	var clientWidth = document.documentElement ? document.documentElement.clientWidth : document.body.clientWidth;
	if (clientWidth > 640) clientWidth = 640;
	document.documentElement.style.fontSize = clientWidth * 1 / 16 + "px";
	return clientWidth * 1 / 16;
});
</script>
<style>
.home{width:2.5rem;height:2.5rem;position:fixed;bottom:80px;right:25px;z-index:9999;background:url(${path}/static/m/images/home.png) no-repeat 0 0;background-size: cover;}
</style>
<link rel="stylesheet" type="text/css" href="${path}/static/m/css/share.css" />