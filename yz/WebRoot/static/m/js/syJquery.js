var sy = sy || {};
sy.data = sy.data || {};// 用于存放临时的数据或者对象

/**
 * 屏蔽右键
 * @requires jQuery
 */
$(document).bind('contextmenu', function() {
	// return false;
});

/**
 * 禁止复制
 * @requires jQuery
 */
$(document).bind('selectstart', function() {
	// return false;
});

sy.ns = function() {
	var o = {}, d;
	for (var i = 0; i < arguments.length; i++) {
		d = arguments[i].split(".");
		o = window[d[0]] = window[d[0]] || {};
		for (var k = 0; k < d.slice(1).length; k++) {
			o = o[d[k + 1]] = o[d[k + 1]] || {};
		}
	}
	return o;
};

sy.serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (this['value'] != undefined && this['value'].length > 0) {// 如果表单项的值非空，才进行序列化操作
			if (o[this['name']]) {
				o[this['name']] = o[this['name']] + "," + this['value'];
			} else {
				o[this['name']] = this['value'];
			}
		}
	});
	return o;
};

sy.formatString = function(str) {
	for (var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

sy.stringToList = function(value) {
	if (value != undefined && value != '') {
		var values = [];
		var t = value.split(',');
		for (var i = 0; i < t.length; i++) {
			values.push('' + t[i]);/* 避免他将ID当成数字 */
		}
		return values;
	} else {
		return [];
	}
};

sy.jsonToString = function(o) {
	var r = [];
	if (typeof o == "string")
		return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
	if (typeof o == "object") {
		if (!o.sort) {
			for ( var i in o)
				r.push(i + ":" + sy.jsonToString(o[i]));
			if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {
				r.push("toString:" + o.toString.toString());
			}
			r = "{" + r.join() + "}";
		} else {
			for (var i = 0; i < o.length; i++)
				r.push(sy.jsonToString(o[i]));
			r = "[" + r.join() + "]";
		}
		return r;
	}
	return o.toString();
};
sy.stringToJSON = function(obj){   
    return eval('(' + obj + ')');   
}
sy.trim = function(str) {
	return str.replace(/(^\s*)|(\s*$)/g, '');
};
sy.ltrim = function(str) {
	return str.replace(/(^\s*)/g, '');
};
sy.rtrim = function(str) {
	return str.replace(/(\s*$)/g, '');
};
sy.startWith = function(source, str) {
	var reg = new RegExp("^" + str);
	return reg.test(source);
};
sy.endWith = function(source, str) {
	var reg = new RegExp(str + "$");
	return reg.test(source);
};

//获取url中的参数
sy.getUrlParam = function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
sy.isWeixin = function(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    } else {
        return false;
    }
}
sy.getCookie = function(name) {
    var aCookie = document.cookie.split("; ");
    var lastMatch = null;
    for (var i = 0; i < aCookie.length; i++) {
        var aCrumb = aCookie[i].split("=");
        if (name == aCrumb[0]) {
            lastMatch = aCrumb;
        }
    }
    if (lastMatch) {
        var v = lastMatch[1];
        if (v === undefined) return v;
        return unescape(v);
    }
    return null;
}
sy.setCookie = function(name,value,days,path){   
    var name = escape(name);   
    var value = escape(value);   
    var expires = new Date();   
    expires.setTime(expires.getTime() + days*24*3600000);   
    path = path == "" ? "" : ";path=" + path;   
    _expires = (typeof hours) == "string" ? "" : ";expires=" + expires.toGMTString();   
    document.cookie = name + "=" + value + _expires + path;   
}   
sy.delCookie = function(name,path){//为了删除指定名称的cookie，可以将其过期时间设定为一个过去的时间
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var value = sy.getCookie(name);
	path = path == "" ? "" : ";path=" + path;   
	if (value != null) document.cookie = name + "=" + value + ";expires=" + exp.toGMTString() + path;
}

/**IE下解决new Date(str)
 * */	
sy.newDateAndTime = function(dateStr){
	if (!dateStr) return new Date();
	var ds = dateStr.split(" ")[0].split("-");
	var ts = dateStr.split(" ")[1].split(":");
	var r = new Date();
	r.setFullYear(ds[0],ds[1] - 1, ds[2]);
	r.setHours(ts[0], ts[1], ts[2], 0);
	return r;
};

sy.openLoad = function(){
	layer.closeAll();
	layer.open({
	    type: 2,
	    shadeClose:false
	});
}
sy.closeLoad = function(){layer.closeAll();}

sy.alert = function(msg){
//	layer.open({
//	    content: msg,
//	    time: 2
//	});
	alert(msg);
}

sy.confirm = function(msg,yes,no){
//	layer.open({
//	    content: msg,
//	    btn: ['确认', '取消'],
//	    shadeClose: false,
//	    yes: function(){
//	        if(yes)yes();
//	    }, no: function(){
//	    	if(no)no();
//	    }
//	});
	if(confirm(msg)){
		yes();
	}else{
		if(no)no();
	}
}


//日期格式化
Date.prototype.Format = function(fmt){
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
};

$.ajaxSetup({type : 'POST'});
$(document).ajaxError(function(event,xhr,settings){
	layer.closeAll();
    if(xhr.responseText=="sessionOut"){
		alert("请先登录！");
		location.href = sy.path + "/m/login";
    }else {
    	try {
			if(XMLHttpRequest.responseText != undefined){
				alert(XMLHttpRequest.responseText);
			}else{
				 
			}
		} catch (e) {
			alert(XMLHttpRequest.responseText);
		}
    }
});

sy.getModule = function(name,params){
	var module = angular.module(name,params);
	module.config(function($httpProvider) {
	    $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
	    $httpProvider.defaults.headers.put['X-Requested-With'] = 'XMLHttpRequest';
	    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
	    $httpProvider.defaults.headers.post['X-Requested-With'] = 'XMLHttpRequest';
	 
	    // Override $http service's default transformRequest
	    $httpProvider.defaults.transformRequest = [function(data) {
	        /**
	         * The workhorse; converts an object to x-www-form-urlencoded serialization.
	         * @param {Object} obj
	         * @return {String}
	         */
	        var param = function(obj) {
	            var query = '';
	            var name, value, fullSubName, subName, subValue, innerObj, i;
	 
	            for (name in obj) {
	                value = obj[name];
	 
	                if (value instanceof Array) {
	                    for (i = 0; i < value.length; ++i) {
	                        subValue = value[i];
	                        fullSubName = name + '[' + i + ']';
	                        innerObj = {};
	                        innerObj[fullSubName] = subValue;
	                        query += param(innerObj) + '&';
	                    }
	                } else if (value instanceof Object) {
	                    for (subName in value) {
	                        subValue = value[subName];
	                        fullSubName = name + '[' + subName + ']';
	                        innerObj = {};
	                        innerObj[fullSubName] = subValue;
	                        query += param(innerObj) + '&';
	                    }
	                } else if (value !== undefined && value !== null) {
	                    query += encodeURIComponent(name) + '='
	                            + encodeURIComponent(value) + '&';
	                }
	            }
	 
	            return query.length ? query.substr(0, query.length - 1) : query;
	        };
	 
	        return angular.isObject(data) && String(data) !== '[object File]'
	                ? param(data)
	                : data;
	    }];
	});
	
	return module;
}

function isWeixin(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    } else {
        return false;
    }
}

function isIphone(){
	var sUserAgent = navigator.userAgent.toLowerCase();
	return sUserAgent.match(/iphone/i) == "iphone";
}

function isAndroid(){
	var sUserAgent = navigator.userAgent.toLowerCase();
	return sUserAgent.match(/android/i) == "android" || sUserAgent.match(/Linux/i) == "Linux";
}

function isPc() {
	//平台、设备和操作系统  
    var system ={  
        win : false,  
        mac : false,  
        xll : false  
    };  
    //检测平台  
    var p = navigator.platform;  
    /**var sUserAgent = navigator.userAgent.toLowerCase(); 
    alert(sUserAgent);*/  
    system.win = p.indexOf("Win") == 0;  
    system.mac = p.indexOf("Mac") == 0;  
//    system.x11 = (p == "X11") || (p.indexOf("Linux") == 0);  
    if(system.win||system.mac||system.xll){
        return true;
    }
    return false;
}
