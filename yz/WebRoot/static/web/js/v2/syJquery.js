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

layer.use('extend/layer.ext.js', function(){
    layer.ext = function(){
        layer.prompt({})
    };
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
sy.getCookie = function(sName) {
    var aCookie = document.cookie.split("; ");
    var lastMatch = null;
    for (var i = 0; i < aCookie.length; i++) {
        var aCrumb = aCookie[i].split("=");
        if (sName == aCrumb[0]) {
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
    if(days==undefined) days =30;
    expires.setTime(expires.getTime() + days*24*3600000);    
    path = path==undefined||path=='' ? "" : ";path=" + path;   
    _expires = (typeof hours) == "string" ? "" : ";expires=" + expires.toUTCString();   
    document.cookie = name + "=" + value + _expires + path;   
}   
sy.delCookie = function(name){//为了删除指定名称的cookie，可以将其过期时间设定为一个过去的时间
   var date = new Date();
   date.setTime(date.getTime() - 10000);
   document.cookie = name + "=a; expires=" + date.toGMTString();
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
//	layer.closeAll();
//	layer.load(1, {shade: [0.5,'#000']});
};

sy.closeLoad = function() {
//	layer.closeAll();
};

sy.alert = function(msg,yes){
	var opt = {content: msg,offset:['40%','40%']};
	if(yes){
		opt.yes = yes;
	}
	layer.open(opt);
};

sy.tips = function(msg, anchor, color){
	layer.tips(msg, anchor, {tips: [1, color]});
};

sy.msg = function(msg){
	layer.msg(msg);
};

sy.confirm = function(msg,yes,no,cancel,ok,title){
	layer.open({
	    content: msg,
	    title:title||'信息',
	    btn: [(ok || '确认'), (cancel || '取消')],
	    shadeClose: false,
	    yes: function(index){
	        if(yes)yes();
	    }, 
	    cancel: function(index){
	    	if(no)no();
	    }
	});
};

sy.prompt = function(options, func){
	//仿系统prompt
	layer.prompt = function(options, yes){
	    options = options || {};
	    if(typeof options === 'function') yes = options;
	    var prompt, content = options.formType == 2 ? '<textarea class="layui-layer-input">'+ (options.value||'') +'</textarea>' : function(){
	        return '<input type="'+ (options.formType == 1 ? 'password' : 'text') +'" class="layui-layer-input" value="'+ (options.value||'') +'">';
	    }();
	    return layer.open($.extend({
	        btn: ['&#x786E;&#x5B9A;','&#x53D6;&#x6D88;'],
	        content: content,
	        skin: 'layui-layer-prompt',
	        success: function(layero){
	            prompt = layero.find('.layui-layer-input');
	            prompt.focus();
	        }, yes: function(index){
	            var value = prompt.val();
	            if(value === ''){
	                prompt.focus();
	            } else if(value.length > (options.maxlength||500)) {
	                layer.tips('&#x6700;&#x591A;&#x8F93;&#x5165;'+ (options.maxlength || 500) +'&#x4E2A;&#x5B57;&#x6570;', prompt, {tips: 1});
	            } else {
	                yes && yes(value, index, prompt);
	            }
	            
	        }
	    }, options));
	};
	layer.prompt(options, func);
};

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
		alert("由于您长时间没有操作,请重新登录！");
		location.reload();
    }else {
    	try {
			if(XMLHttpRequest.responseText != undefined){
				alert(XMLHttpRequest.responseText);
			}else{
				alert("操作异常，请联系管理员！");
			}
		} catch (e) {
			alert(XMLHttpRequest.responseText);
		}
    }
});

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
sy.post = function(opt){
	var options = {type:'post',dataType:'json',async:true};
	$.extend(options,opt);
	$.ajax(options);
}
sy.getModule = function(name,params){
	var module = angular.module(name,params);
	
	module.run(function($rootScope) {
		$rootScope.go = function(href){
			location.href = href;
		}
		$rootScope.getSrc = function(id,w,h){
			if(id){
				if(w){
					return sy.path + '/download?id='+id + '&w='+w+'&h='+h;
				}else{
					return sy.path + '/download?id='+id;
				}
			}
		}
		$rootScope.left = function(str,length,after){
			if(str){
				if(str.length>=length){
					return str.substring(0,length)+after;
				}
				return str;
			}
			return '';
		}
	});
	
	module.factory('httpInterceptor', [ '$q', '$injector',function($q, $injector) {  
	        var httpInterceptor = {  
        		'request':function(config) {
	                return config;  
	            }, 
	            'requestError':function(config) {  
	                return $q.reject(config);
	            }, 	
	            'response':function(response) {
	            	if(response.data && response.data == "sessionOut"){
	            		sy.alert("请先登录！");
						location.href = sy.path + "/web/login";
	            	}else{
	            		return response;
	            	}
	            },
	            'responseError' : function(response) { 
	            }   
	        }  
	        return httpInterceptor;  
	    }   
	]);
	
	module.config(function($httpProvider) {
		$httpProvider.interceptors.push('httpInterceptor'); 
		
	    $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
	    $httpProvider.defaults.headers.put['X-Requested-With'] = 'XMLHttpRequest';
	    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
	    $httpProvider.defaults.headers.post['X-Requested-With'] = 'XMLHttpRequest';
	 
	    $httpProvider.defaults.transformRequest = [function(data) {
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
	
	module.directive('onFinishRenderFilters', function ($timeout) {
	    return {
		        restrict: 'A',
		        link: function(scope, element, attr) {
		            if (scope.$last === true) {
		                $timeout(function() {
		                    scope.$emit('ngRepeatFinished',attr.onFinishRenderFilters);
		                });
		            }
		        }
		    };
	});

	module.directive('page', function ($timeout) {
	    return{
	        replace:true,
	        scope:{
	            page : '=pageObject',
	            query : '=pageFunction',
	            scrolltop:'=pageScrolltop'
	        },
	        controller : function ($scope,$element) {
	            $scope.createHtml = function () {
	                var maxPage =  Math.ceil($scope.page.total / $scope.page.limit) ;
	                var pageNo = $scope.page.page;
	                var str = '<ul class="ul-center">' ;
	                if(maxPage > 10){
	                    if(pageNo > 3){//minPage + 2
	                        str += '<li class="item prev"><a class="number p"></a></li>' ;
	                        str += '<li class="item"><a class="number pfirst" >...</a></li>';
	                    }
	                    for(var i= pageNo <=2?1:pageNo -2 ;i<= (pageNo >= maxPage-2?maxPage:pageNo +2) ;i++ ){
	                        if(i == 1){
	                            if(pageNo == 1){
	                            	str += '<li class="item prev prev-disabled"><a></a></li>' ;
	                                str += '<li class="item cur"><a>'+i+'</a></li>' ;
	                            }else{
	                            	str += '<li class="item prev"><a class="number p"></a></li>' ;
	                            	str += '<li class="item"><a class="num number">'+i+'</a></li>';
	                            }
	                        }else if(i == maxPage){
	                            if(pageNo == maxPage){
	                            	str += '<li class="item cur"><a>'+i+'</a></li>' ;
	                            	str += '<li class="item next next-disabled"><a></a></li>';
	                            }else{
	                            	str += '<li class="item"><a class="num number">'+i+'</a></li>';
	                                str += '<li class="item next"><a class="number next" style="margin-left:20px;" ></a></li>';
	                            }
	                        }else{
	                            if(pageNo == i){
	                            	str += '<li class="item cur"><a>'+i+'</a></li>' ;
	                            }else{
	                            	str += '<li class="item"><a class="num number">'+i+'</a></li>';
	                            }
	                        }
	                    }
	                    if(pageNo < maxPage - 2){
	                        str += '<li class="item"><a class="number plast" >...</a></li>';
	                        str += '<li class="item next"><a class="number next"></a></li>';
	                    }
	                }else{
	                    for(var i = 1; i <= maxPage ; i++){
	                        if(i == 1){
	                            if(pageNo == 1){
	                                str += '<li class="item prev prev-disabled"><a></a></li>';
	                                str += '<li class="item cur"><a>1</a></li>';
	                                if(maxPage == 1){
	                                	str += '<li class="item next next-disabled"><a></a></li>';
	                                }
	                            }else{
	                            	str += '<li class="item prev"><a class="number p"></a></li>';
	                            	str += '<li class="item"><a class="num number">1</a></li>';
	                            }
	                        }else if(i == maxPage){
	                            if(pageNo == maxPage){
	                                str += '<li class="item cur"><a>'+i+'</a></li>';
	                                str += '<li class="item next next-disabled"><a></a></li>';
	                            }else{
	                                str += '<li class="item"><a class="num number">'+i+'</a></li>';
	                                str += '<li class="item next"><a class="number next" style="margin-left:20px;" ></a></li>';
	                            }
	                        }else{
	                            if(pageNo == i){
	                                str += '<li class="item cur"><a>'+i+'</a></li>';
	                            }else{
	                                str += '<li class="item"><a class="num number">'+i+'</a></li>';
	                            }
	                        }
	                    }
	                }
	                str += '</ul>';
	                $element.html(str);
	                $scope.bindEvent();
	            };
	            $scope.bindEvent = function () {
	                $element.find('.number').on('click', function () {
	                	var text = $(this).html();
	                    var pageNo = $scope.page.page;
	                    var query = false;
	                    if($(this).hasClass("p")){
	                        $scope.page.page = pageNo - 1 ;
	                        query = true;
	                    }else if($(this).hasClass("next")){
	                        $scope.page.page = pageNo + 1 ;
	                        query = true;
	                    }else if($(this).hasClass("plast")){
	                    	var maxPage =  Math.ceil($scope.page.total / $scope.page.limit) ;
	                        $scope.page.page = maxPage;
	                        query = true;
	                    }else if($(this).hasClass("pfirst")){
	                        $scope.page.page = 1;
	                        query = true;
	                    }else{
	                		var go = parseInt(text);
	                		if(!isNaN(go)){
	                			$scope.page.page = go;
	                    		query = true;
	                		}
	                    }
	                    if(query){
	                    	$scope.query();
	                        $scope.createHtml();
	                    }
	                    if($scope.scrolltop)
	                    	$('body,html').animate({scrollTop:$scope.scrolltop},1000);
	                    else
	                    	$('body,html').animate({scrollTop:0},1000);
	                });
	            }
	            $scope.createHtml();
	            $scope.$watch('page.total', function () {
	                $scope.createHtml();
	            })
	        }
	    }
	});
	return module;
};

//保留两位小数
sy.remain2num = function(momey) {
	var pos = (momey || '0.00').lastIndexOf('.');
	if (pos == -1) {
		return momey + '.00';
	} else {
		var rst = momey.substring(0, pos);
		var poi = momey.length - pos;
		switch (poi) {
			case 1 :
				return rst + '00';
			case 2 :
				return momey + '0';
			default :
				return momey.substring(0, pos + 3);
		}
	}
};

sy.showWxQrPay = function(money,qr,ok,cancel){
	_cancel = function(){
		$(".wxpay-alert").remove();
	}
	_ok = function(){
		$(".wxpay-alert").remove();
	}
	if(typeof cancel == 'function'){
		_cancel = function(){
			cancel();
			$(".weixin-pay").remove();
		}
	}
	if(typeof ok == 'function'){
		_ok = function(){
			ok();
			$(".wxpay-alert").remove();
		}
	}
	
	$(".wxpay-alert").remove();
	var html = "";
    html += "<div class=\"wxpay-alert\">";
    html += "    <div class=\"main\">";
    html += "        <div class=\"title\"><a class=\"a-close\" onclick=\"_cancel()\"></a>支付<em>"+money+"</em>元</div>";
    html += "        <div class=\"pic\">";
    html += "            <span class=\"s-t\">微信扫码支付</span>";
    html += "            <div class=\"s-pic\">";
    html += "                <img src=\""+qr+"\" width=\"156\" height=\"156\"/>";
    html += "            </div>";
    html += "        </div>";
    html += "        <div class=\"bottom\"><a onclick=\"_ok()\">完成支付请点击&gt;</a></div>";
    html += "    </div>";
    html += "</div>";
	$(document.body).append(html);
}