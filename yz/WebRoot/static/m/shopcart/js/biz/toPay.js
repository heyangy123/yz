var myApp = sy.getModule('myApp',[]);

//$.request;获取url参数
var getRequestParam = function(paras) {
	var url = location.href;
	var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
	var paraObj = {};
	for (i = 0; j = paraString[i]; i++) {
		paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j
				.indexOf("=") + 1, j.length)
	}
	;
	if (!paras)
		return paraObj;
	var returnValue = paraObj[paras.toLowerCase()];
	return returnValue;
};
var openCode = getRequestParam("code");
if(isWeixin()){
	if(!openCode||openCode==''){
		openCode = sessionStorage.openCode;
	}else{
		sessionStorage.openCode = openCode;
	}
	if(!openCode||openCode==''){
		window.location.href = codeURL;
	}
}

myApp.controller('MyController', function($scope,$http) {
	  $scope.isWeixin = sy.isWeixin;
	  $scope.data = {};
	  $scope.data.needToPay = 0;
	  $scope.couponValue = sessionStorage.couponValue;
	  $scope.couponId = sessionStorage.couponId;
	  $scope.useMoney = 0;
	  $scope.payType = 1;
	  
	  if($scope.isWeixin()){
		  $scope.payType = 3;
	  }
	  
	  sy.openLoad();
	  $http.post(sy.path + "/m/order/beforePay", {id:id}).success(function(data) {
		  	sy.closeLoad();
		  	if(data == "sessionOut")location.href = sy.path+"/m/login";
		  	if(data.code == 0){
	  			$scope.data = data.data;
	  			if($scope.couponValue){
		  			  sessionStorage.removeItem("couponValue");
		  			  sessionStorage.removeItem("couponId");
		  			  if($scope.couponValue >= $scope.data.online)
		  				  $scope.data.needToPay = 0;
		  			  else
		  				  $scope.data.needToPay = $scope.data.online - $scope.couponValue;
		  		  }else{
		  			  $scope.data.needToPay = $scope.data.online;
		  		  }
		  	}else{
		  		alert(data.msg);
		  		history.go(-1);
		  	}
		}).error(function(data, status) {
			sy.closeLoad();
			sy.alert('数据请求失败');
		});
	  
	  $scope.selectCoupon = function(){
		  location.href = sy.path + "/m/order/myCoupon";
	  }
	  
	  $scope.selectMoney = function(){
		  if($scope.useMoney == 1){
			  $scope.useMoney = 0;
			  if($scope.couponValue){
				  if($scope.couponValue >= $scope.data.online)
					  $scope.data.needToPay = 0;
				  else
					  $scope.data.needToPay = $scope.data.online - $scope.couponValue;
			  }else{
				  $scope.data.needToPay = $scope.data.online;
			  }
		  }else{
			  $scope.useMoney = 1;
			  if($scope.data.balance >= $scope.data.needToPay){
				  $scope.data.needToPay = 0;
			  }else{
				  $scope.data.needToPay = $scope.data.needToPay - $scope.data.balance;
			  }
		  }
	  }
	  
	  $scope.selectType = function(type){
		  $scope.payType = type;
	  }
	  
	$scope.onlinePay = function(data){
		if($scope.payType == 3){
			//微信支付
			  
			function onBridgeReady(){
			 
				   WeixinJSBridge.invoke(
				       'getBrandWCPayRequest', {
				           "appId" : data.appId,     //公众号名称，由商户传入     
				           "timeStamp":data.timeStamp,         //时间戳，自1970年以来的秒数     
				           "nonceStr" : data.nonceStr, //随机串     
				           "package" : "prepay_id="+data.totalId,     
				           "signType" : "MD5",         //微信签名方式：     
				           "paySign" : data.sign //微信签名
				       },
				       function(res){     
				    	   
				           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
				        	   window.location.href = sy.path + "/m/order";
				           }else if(res.err_msg.indexOf('fail')!=-1){
				        	   $http.post(sy.path + "/m/order/wxCodePay", {id:id,userMoney:$scope.useMoney,cashId:$scope.couponId,type:$scope.payType,openCode:openCode}).success(function(ret) {
					   			  	sy.closeLoad();
					   			  	if(ret == "sessionOut")location.href = sy.path+"/m/login";
					   			  	if(ret.code == 0){
					   			  		$('#choosePay').after('<div style="width:80%;80%;padding-bottom:200px;" ><img src="'+sy.path + '/qr.do?msg='+ret.totalId+'" ></div>');
					   			  	}else{
					   			  		alert(ret.msg);
					   			  	}
					   			}).error(function(data, status) {
					   				sy.closeLoad();
					   				sy.alert('数据请求失败');
					   			});
					           }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
				       }
				   ); 
			    }
				if (typeof WeixinJSBridge == "undefined"){
				   if( document.addEventListener ){
				       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
				   }else if (document.attachEvent){
				       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
				       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
				   }
				}else{
				   onBridgeReady();
				}
		}else{
			$("#content").val(data.totalId);
			$("#form").submit();
		}
	};
	  
	  $scope.pay = function(){
		  sy.openLoad();
		  $http.post(sy.path + "/m/order/pay", {id:id,userMoney:$scope.useMoney,cashId:$scope.couponId,type:$scope.payType,openCode:openCode}).success(function(data) {
			  	sy.closeLoad();
			  	if(data == "sessionOut")location.href = sy.path+"/m/login";
			  	if(data.code == 0){
			  		if(data.price > 0){
			  			$scope.onlinePay(data);
			  		}else{
			  			location.href = sy.path + "/m/order";
			  		}
			  	}else{
			  		alert(data.msg);
			  	}
			}).error(function(data, status) {
				sy.closeLoad();
				sy.alert('数据请求失败');
			});
	  }
});
