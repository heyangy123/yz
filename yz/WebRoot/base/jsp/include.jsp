<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="path" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" content="ie=edge"/> 
<script type="text/javascript">
var sy = sy || {};
sy.contextPath = '${path}';
sy.basePath = "${pageContext.request.scheme}" + "://" + "${pageContext.request.serverName}" + ":" + "${pageContext.request.serverPort}" + "${pageContext.request.contextPath}"+"/";
var projectPath = sy.basePath;
sy.roleCode = '${sessionScope.sessionUser.roleCode}';
sy.areaCode = '${sessionScope.sessionUser.areaCode}';
sy.system = '${sessionScope.sessionUser.sys}';
sy.pixel_0 = '${path}/base/css/images/pixel_0.gif';//0像素的背景，一般用于占位
</script>

<%-- 引入jQuery --%>
<script src="${path}/base/js/jquery-1.9.1.js" type="text/javascript" charset="utf-8"></script>

<%-- 引入jquery扩展 --%>
<script src="${path}/base/js/syJquery.js" type="text/javascript" charset="utf-8"></script>

<%-- 引入javascript扩展 --%>
<script src="${path}/base/js/syJavascript.js" type="text/javascript" charset="utf-8"></script>

<%-- 引入jquery自动补全
<link href="${path}/base/js/jquery-autocomplete/jquery.autocomplete.css" type="text/css" rel="stylesheet" />
<script src="${path}/base/js/jquery-autocomplete/jquery.autocomplete.js" type="text/javascript" charset="utf-8"></script>
<script src="${path}/base/js/jquery-autocomplete/autocomplete.js" type="text/javascript" charset="utf-8"></script>
 --%>
 
<%-- 引入my97日期时间控件 
<script type="text/javascript" src="${path}/base/js/My97DatePicker/WdatePicker.js" charset="utf-8"></script>
--%>

<%-- 引入colorpicker --%>
<script type="text/javascript" src="${path}/base/js/colorPicker/colors.js" charset="utf-8"></script>
<script type="text/javascript" src="${path}/base/js/colorPicker/jqColorPicker.js" charset="utf-8"></script>


<%-- 引入kindeditor控件 --%>
<script src="${path}/base/js/kindeditor-4.1.10/kindeditor-all.js" type="text/javascript" charset="utf-8"></script>

<%-- 引入uploadify --%>
<link href="${path}/base/js/uploadify/uploadify.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${path}/base/js/uploadify/swfobject.js"></script>
<script type="text/javascript" src="${path}/base/js/uploadify/jquery.uploadify.js"></script>

<%-- 引入EasyUI --%>
<script type="text/javascript">
var skin = getCookie("easyuiTheme");
if (!skin) {
	skin = 'metro-blue';
}
document.write('<link href="' + sy.basePath + '/base/js/jquery-easyui-1.4.2/themes/' + skin + '/easyui.css" rel="stylesheet" type="text/css" id="easyuiTheme"/>');
</script>
<link rel="stylesheet" href="${path}/base/js/jquery-easyui-1.4.2/themes/icon.css" type="text/css">
<script type="text/javascript" src="${path}/base/js/jquery-easyui-1.4.2/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${path}/base/js/jquery-easyui-1.4.2/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
 
<%-- web弹层组件 --%>
<script type="text/javascript" src="${path}/base/js/layer-1.9.2/layer.js" ></script>
<script type="text/javascript" src="${path}/base/js/layer-1.9.2/extend/layer.ext.js" ></script>

<%-- 引入easyui扩展 --%>
<script src="${path}/base/js/syEasyUI.js" type="text/javascript" charset="utf-8"></script>
<script src="${path}/base/js/syValidatebox.js" type="text/javascript" charset="utf-8"></script>

<%-- 引入扩展图标 --%>
<link rel="stylesheet" href="${path}/base/css/syIcon.css" type="text/css">

<%-- 引入自定义样式 --%>
<link rel="stylesheet" href="${path}/base/css/syCss.css" type="text/css">

<%-- 引入ui扩展 --%>
<script src="${path}/base/js/core.ui.js" type="text/javascript" charset="utf-8"></script>
<%-- 引入图片裁剪--%>
<link rel="stylesheet" href="${path}/base/js/tapmodo-Jcrop-1902fbc/css/jquery.Jcrop.css" type="text/css">
<script type="text/javascript" src="${path}/base/js/tapmodo-Jcrop-1902fbc/js/jquery.Jcrop.js"></script>
<script type="text/javascript" src="${path}/base/js/tapmodo-Jcrop-1902fbc/js/jquery.Jcrop.min.js"></script>
<%-- 浏览大图 --%>
<link rel="stylesheet" href="${path}/base/js/img/img.css" type="text/css">
<script type="text/javascript" src="${path}/base/js/img/img.js"></script>

<script type="text/javascript" src="${path}/base/js/ut.js" ></script>

<script type="text/javascript">
layer.config({
	skin:'layer-ext-seaning',
	extend:'skin/seaning/style.css'
});
</script>