var phoneWidth = parseInt(window.screen.width);
var phoneHeight = parseInt(window.screen.height);
var phoneScale = phoneWidth / 640;
var ua = navigator.userAgent;
var Agents = new Array("Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod", "Blackberry"); 
var flag = 1; 
for (var v = 0; v < Agents.length; v++) { 
	if (ua.indexOf(Agents[v]) > 0) { flag = 0; break; } 
} 
//if(flag == 1){alert("对不起,请使用手机端设备浏览！");window.location.href="http://www.xxx.com";}

if (/Android (\d+\.\d+)/.test(ua)) {
	var version = parseFloat(RegExp.$1);
	// andriod 2.3
	if (version > 2.3) {
		document.write('<meta name="viewport" content="width=640, minimum-scale = ' + phoneScale + ', maximum-scale = ' + phoneScale + ', target-densitydpi=device-dpi">');
		// andriod 2.3以上
	} else {
		document.write('<meta name="viewport" content="width=640, target-densitydpi=device-dpi">');
	}
	// 其他系统
} else {
	document.write('<meta name="viewport" content="width=640, user-scalable=no, target-densitydpi=device-dpi">');
}