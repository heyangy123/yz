<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html ng-app="myApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="${keyWords}">
 	<%@ include file="/static/web/jsp/include.jsp"%>
	<title>分享</title>

<link href="${path}/base/css/style.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${path}/static/web/js/biz/share.js"></script>
<script type="text/javascript">
var id = "${id}";
</script>
</head>
  
<body ng-controller="ShareController">
<div class="wrap">
 <div class="addresstext"><a ng-bind="game.place"></a>&nbsp&nbsp&nbsp<a ng-bind="game.createTime"></a></div>
 <div style="clear:both"></div>
 <div class="logo">
   <div class="logo1"><img class="image_radius" ng-src="{{getSrc(organizer.img,200,200)}}" width="100%" height="100%"><br/><a ng-bind="organizer.name"></a></div>
   <div class="logo2"><img class="image_radius" ng-src="{{getSrc(accpeter.img,200,200)}}" width="100%" height="100%"><br/><a ng-bind="accpeter.name"></a></div>
 </div>
</div>
</body>
</html>
