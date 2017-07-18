<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta property="qc:admins" content="2020335717547736375" />
<link rel="apple-touch-icon" href="${path}/static/web/pic/favicon.png"/>
<link rel="shortcut icon" type="image/x-icon" href="${path}/static/web/pic/favicon.png" media="screen">
<script type="text/javascript">
var sy = sy || {};
sy.path = "${pageContext.request.scheme}" + "://" + "${pageContext.request.serverName}" + ":" + "${pageContext.request.serverPort}" + "${pageContext.request.contextPath}";
sy.contextPath = "${pageContext.request.contextPath}";
if(sy.contextPath == "")sy.contextPath = "/";
sy.siteName = "篮球约战";
</script>

<%-- 引入jQuery --%>
<script type="text/javascript" src="${path}/static/web/js/v2/jquery-1.9.1.js"></script>

<%-- 引入angular --%>
<script type="text/javascript" src="${path}/static/web/js/angular/angular.js"></script>
<script type="text/javascript" src="${path}/static/web/js/angular/angular-route.js"></script>
<script type="text/javascript" src="${path}/static/web/js/angular/angular-sanitize.js"></script>

<%-- 引入angular滚动分页插件 --%>
<script type="text/javascript" src="${path}/static/web/js/v2/ng-infinite-scroll.js"></script>

<%-- web弹层组件 --%>
<script type="text/javascript" src="${path}/static/web/js/layer-v2.2/layer.js" ></script>
<script type="text/javascript" src="${path}/static/web/js/layer-v2.2/extend/layer.ext.js" ></script>

<%-- 引入jquery扩展 --%>
<script type="text/javascript" src="${path}/static/web/js/v2/syJquery.js"></script>

<%-- 引入UT工具扩展 --%>
<script type="text/javascript" src="${path}/static/web/js/v2/ut.js"></script>

<%-- 分享提示 --%>
<script type="text/javascript" src="${path}/static/web/js/v2/ConstantStr.js" ></script>

