<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<style>
a:link {
	font-size: 12px;
	color: #000000;
	text-decoration: none;
}

a:visited {
	font-size: 12px;
	color: #000000;
	text-decoration: none;
}

a:hover {
	font-size: 12px;
	color: #000000;
	text-decoration: underline;
}

.article-detail {
	width: 90%;
	margin-left: auto;
	margin-right: auto;
}
</style>

</head>
<body>
	<div class="pin">
		<div class="article-detail">
			<div class="article-content" style="margin-top:15px">
				<input id="account" name="account" readonly value="${id}"
					style="border:0px;width:100%" />
			</div>
			<div style="margin-top:30px;">
				<div id='copy' data-clipboard-text='${id }'
					style="border:solid thin #999;font-size:14px;padding:6px 12px;background-color:#ccc;cursor:pointer;margin-left:auto;margin-right:auto;width:30px"
					onclick="alert('已复制到剪贴板');">复制</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="./base/js/copy/ZeroClipboard.min.js"></script>
	<script type="text/javascript">
		if ((navigator.userAgent.indexOf('MSIE') >= 0)
				&& (navigator.userAgent.indexOf('Opera') < 0)) {
				document.getElementById("copy").onclick = function copy(){
				var t = document.getElementById("account");
				t.select();
				window.clipboardData.setData('text', t.createTextRange().text);
				alert('已复制到剪贴板');
			};
		} else {
			new ZeroClipboard(document.getElementById('copy'));
		}
	</script>
</body>
</html>
