var myApp = sy.getModule('myApp',[]);

myApp.controller('MyController', function($scope,$http) {
	  $scope.list = [];
	  $scope.address = {};
	  
	  $scope.params = params;
	  
	  /*********配送方式begin**********/
	  
	  $scope.isExpressShow = false;//是否显示配送方式弹出层
	  $scope.expressOrder = {};//配送方式弹出层数据
	  $scope.showExpressSelect = function(order){//显示弹出层
		  $scope.expressOrder = order;
		  $scope.isExpressShow = true;
	  }
	  $scope.selectExpress = function(expressOrder,exp){//选择快递
		  $.each($scope.list,function(i,o){
			  if(o.storeId == $scope.expressOrder.storeId){
				  o.nowExpress = exp;
			  }
		  });
		  $scope.isExpressShow = false;
	  }
	  /*********配送方式end**********/
	  
	  /*********支付方式begin**********/
	  
	  $scope.isPayTypeShow = false;//是否显示支付方式弹出层
	  $scope.payTypeOrder = {};//支付方式弹出层数据
	  $scope.showPayTypeSelect = function(order){//显示弹出层
		  $scope.payTypeOrder = order;
		  $scope.isPayTypeShow = true;
	  }
	  $scope.selectPayType = function(payTypeOrder,payType){//选择支付方式
		  $.each($scope.list,function(i,o){
			  if(o.storeId == $scope.payTypeOrder.storeId){
				  o.nowPayType = payType;
			  }
		  });
		  $scope.isPayTypeShow = false;
	  }
	  /*********支付方式end**********/
	  
	  /*********配送时间begin**********/
	  
	  $scope.isTimeShow = false;//是否显示配送时间弹出层
	  $scope.timeOrder = {};//配送时间弹出层数据
	  $scope.showTimeSelect = function(order){//显示弹出层
		  $scope.timeOrder = order;
		  $scope.isTimeShow = true;
	  }
	  
	  $scope.timeOk = function(){//选择配送时间
		  if($scope.timeOrder.nowDay == undefined || $scope.timeOrder.nowHour == undefined){
			  $scope.timeOrder.nowDay = $scope.timeOrder.time.day[0];
			  $scope.timeOrder.nowHour = $scope.timeOrder.time.time[0];
		  }
		  
		  $.each($scope.list,function(i,o){
			  if(o.storeId == $scope.timeOrder.storeId){
				  o.nowTime = $scope.timeOrder.nowDay+" "+$scope.timeOrder.nowHour.beginTime + "~"+$scope.timeOrder.nowHour.endTime;
			  }
		  });
		  $scope.isTimeShow = false;
	  }
	  /*********配送时间end**********/
	  
	  //商家总额
	  $scope.getOrderTotal = function(order){
		  return parseFloat(order.total) + order.nowExpress.price;
	  }
	  
	  //总订单金额
	  $scope.getTotal = function(){
		  var total = 0;
		  $.each($scope.list,function(i,o){
			  total += parseFloat(o.total) + o.nowExpress.price;
	  	    });
		  return total;
	  }
	  
	  //立即购买
	  quickBuy = function(id,num,fid){
		  sy.openLoad();
		  $http.post(sy.path + "/m/order/quickBuy", {id:id,num:num,fid:fid}).success(function(data) {
			  	sy.closeLoad();
			  	if(data == "sessionOut")location.href = sy.path+"/m/login";
			  	if(data.code == 0){
			  		data.data.nowExpress = data.data.express[0];
			  		data.data.nowPayType = data.data.payType[0];
			  		$scope.list[0] = data.data;
			  		
			  		$scope.getTotal();
			  	}else{
			  		alert(data.msg);
			  		history.go(-1);
			  	}
			}).error(function(data, status) {
				sy.closeLoad();
				sy.alert('数据请求失败');
			});
	  }
	  
	  //购物车下单
	  cartBuy = function(ids){
		  sy.openLoad();
		  $http.post(sy.path + "/m/order/cartBuy", {ids:ids}).success(function(data) {
			  	sy.closeLoad();
			  	if(data == "sessionOut")location.href = sy.path+"/m/login";
			  	if(data.code == 0){
			  		$scope.list = data.data;
			  	    $scope.total = data.total;
			  	    
			  	    $.each($scope.list,function(i,o){
			  	    	o.nowExpress = o.express[0];
			  	    	o.nowPayType = o.payType[0];
			  	    });
			  	    
			  	    $scope.getTotal();
			  	}else{
			  		alert(data.msg);
			  		history.go(-1);
			  	}
			}).error(function(data, status) {
				sy.closeLoad();
				sy.alert('数据请求失败');
			});
	  }
	  
	  //获取地址
	  getAddress = function(){
		  var defaultAddr = "";
		  if(sessionStorage.defaultAddr != undefined){
			  defaultAddr = sessionStorage.defaultAddr;
		  }
		  
		  $http.post(sy.path + "/m/addr/defaultAddress", {id:defaultAddr}).success(function(data) {
			  	sy.closeLoad();
			  	if(data == "sessionOut")location.href = sy.path+"/m/login";
			  	if(data.code == 0){
			  		$scope.address = data.data;
			  	}
			}).error(function(data, status) {
				sy.closeLoad();
			});
	  }
	  
	  //初始化
	  if($scope.params.mode != undefined){
		  if($scope.params.mode == 1){
			  quickBuy($scope.params.id,$scope.params.num,$scope.params.fid);
		  }else{
			  cartBuy($scope.params.ids);
		  }
		  getAddress();
	  }
	  
	  //选择地址
	  $scope.chooseAddr = function(){
		 sessionStorage.chooseId = location.href;
    	 sessionStorage.chooseType = 2;
    	 location.href = sy.path + '/m/addr/choose';
     }
	  
	  //提交订单
	  $scope.submitOrder = function(){
		  if($scope.address.id == undefined){
			  sy.alert("请选择收货地址");
			  return;
		  }
		  var addressId = $scope.address.id;
		  
		  var flag = true;
		  $.each($scope.list,function(i,o){
	  	    	if(o.isTime == 1){
	  	    		if(o.nowTime == undefined){
	  	    			sy.alert("请选择配送时间");
	  	    			flag = false;
	  	    			return flag;
	  	    		}
	  	    	}
	  	    });
		  if(!flag)return;
		  
		  if($scope.params.mode == 1){//立即购买
			  var o = $scope.list[0];
			  sy.openLoad();
			  $http.post(sy.path + "/m/order/addSingleOrder",
			  {payType:o.nowPayType,goodsId:o.goods[0].goodsId,num:o.goods[0].num,addressId:addressId,
				  ticketId:o.coupon.id,info:o.info,expressId:o.nowExpress.id,fid:o.goods[0].sns,time:o.nowTime}
			  ).success(function(data) {
				  	sy.closeLoad();
				  	if(data == "sessionOut")location.href = sy.path+"/m/login";
				  	if(data.code == 0){
				  		if(data.data.online == 0){
				  			location.href = sy.path + '/m/order';
				  		}else{
				  			location.href = sy.path + "/m/order/toPay/"+data.data.totalId;
				  		}
				  	}else{
				  		sy.alert(data.msg);
				  	}
				}).error(function(data, status) {
					sy.closeLoad();
				});
		  }else{
				var list = [];
				
				$.each($scope.list,function(i,o){
					var cart = [];
					$.each(o.goods,function(j,g){
						cart.push(g.id);
					});
					
					list.push({
						cart:cart,
						storeId:o.storeId,
						payType:o.nowPayType,
						ticketId:o.coupon.id||"",
						info:o.info||"",
						expressId:o.nowExpress.id||"",
						time:o.nowTime||""
					});
		  	    });
				var data = sy.jsonToString(list);
				
			    sy.openLoad();
			    $http.post(sy.path + "/m/order/addOrderByCart",
					  {addressId:addressId,data:data}
			    ).success(function(data) {
				  	sy.closeLoad();
				  	if(data == "sessionOut")location.href = sy.path+"/m/login";
				  	if(data.code == 0){
				  		if(data.data.online == 0){
				  			location.href = sy.path + '/m/order';
				  		}else{
				  			location.href = sy.path + "/m/order/toPay/"+data.data.totalId;
				  		}
				  	}else{
				  		sy.alert(data.msg);
				  	}
				}).error(function(data, status) {
					sy.closeLoad();
				});
		  }
	  }
	  

});
