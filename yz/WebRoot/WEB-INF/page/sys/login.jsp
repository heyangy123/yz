<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<title>${sysName}登录</title>
<%@ include file="/base/jsp/include.jsp"%>
<script type="text/javascript">
  	//防止嵌套页面
	if (window.top != window) {
		window.top.location = window.location;
	}
</script>
<link rel="stylesheet" href="${path}/base/css/login.css" type="text/css">
<link rel="stylesheet" href="${path}/base/css/rotate.css" type="text/css">
<script src="${path}/base/js/cloud.js" type="text/javascript" charset="utf-8"></script>
<script language="javascript">
var sys = '${sys}';
$(function(){	
	// 请求上次存储的Cookies
	var account = getCookie(sys+"_account");
	
	//设定sys的cookie
	var temp = getCookie('sys');
	if (temp != sys){
		delCookie('sys');
		setCookie('sys',sys,100,"/");
	}
	
	if (account!=null) {
		$("#account").val(account);
        // 密码框赋值
        $("#pwd").val(getCookie(sys+"_pwd"));
        // 并设置勾选记住密码
        $("#remember").attr("checked","checked");
	}
	
	$('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
	$(window).resize(function(){  
		$('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
	});
	
	$('form :input').keyup(function(event) {
		if (event.keyCode == 13) {
			loginFun();
		}
	});
});  

var loginFun = function() {
	if($("#account").val() == '' || $("#pwd").val() == ''){
		alert("账号或密码不能为空");
		return;
	}
	
	var $form = $('form');
	if ($form.length == 1) {
		$.post(sy.contextPath + '/manager/'+sys+'/login', $form.serialize(), function(result) {
			if (result.code == 0) {
				if(document.getElementById("remember").checked){
					setCookie(sys+"_account",$("#account").val(),100,"/");
					setCookie(sys+"_pwd",$("#pwd").val(),100,"/");
				}else{
					delCookie(sys+"_account");
					delCookie(sys+"_pwd");
				}
				location.replace(sy.contextPath + '/manager/'+sys);
			} else {
				alert(result.msg);
			}
		}, 'json');
	}
};
</script> 
</head>
<body style="">
    <div id="mainBody">
      <div id="cloud1" class="cloud"></div>
      <div id="cloud2" class="cloud"></div>
    </div>
	<div class="logintop">
		<span>欢迎登录${sysName}后台管理界面平台</span>
		<ul>
		</ul>
	</div>
	<div class="loginbody">
		<span class="systemlogo"></span>
		<div class="loginbox">
			<ul>
			<form method="post" id="form">
				<li><input id="account" name="account" type="text" class="loginuser" placeholder="请输入账号" value="admin"/></li>
				<li><input id="pwd" name="pwd" type="password" class="loginpwd" placeholder="请输入密码" value="1"/></li>
				</form>
				<li><input type="button" class="loginbtn" value="登录" onclick="loginFun()" />
					<label><input type="checkbox" checked="checked" id="remember"/>记住密码</label>
				</li>
			</ul>
		</div>
	</div>
	<div class="loginbm"></div>
</body>
</html>
