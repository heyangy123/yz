var myApp = sy.getModule('myApp',[]);

myApp.controller('MyController', function($scope,$http) {
  $scope.fid = "";
  $scope.order = {};
  $scope.order.id = id;
  
  sy.openLoad();
  $http.post(sy.path + "/m/order/findById", {id:$scope.order.id}).success(function(data) {
	  	sy.closeLoad();
	  	if(data == "sessionOut")location.href = sy.path+"/m/login";
	  	if(data.code == 0){
  			$scope.order = data.order;
	  	}else{
	  		sy.alert(data.msg);
	  	}
	}).error(function(data, status) {
		sy.closeLoad();
		sy.alert('数据请求失败');
	});
  

	$scope.kuaidi = function(order){
		var url = "http://m.kuaidi100.com/index_all.html?type="+encodeURI(order.press.name)+"&postid="+encodeURI(order.press.code);
		location.href = sy.path + "/www?url="+escape(url)+"&type=1";
	}
  
	$scope.toComment = function(order){
		location.href = sy.path + "/m/order/comment/"+order.id;
	}
	
	$scope.toPay = function(order){
		location.href = sy.path + "/m/order/toPay/"+order.id;
	}
	
	$scope.toGoodsDetail = function(goods){
		location.href=sy.path + '/m/goods/'+goods.goodsId;
	}
	
	$scope.updateOrder = function(order,type){
		var msg = "";// 1:取消 2:申请退款 3:删除 4:确认收货
		switch (type) {
		case 1:
			msg = "确定要取消该订单吗？"
			break;
		case 2:
			msg = "确定要申请退款吗？"
			break;
		case 3:
			msg = "确定要删除该订单吗？"
			break;		
		case 4:
			msg = "确定要确认收货吗？"
			break;		
		default:
			break;
		}
		sy.confirm(msg,function(){
			var info = "";
			if(type == 2){
				i = prompt("请填写退款原因：", "");
				if (i != null && i != "") {
					if(i.length>100){
						alert("内容过长");
						return;
					}
					info = i;
				}else{
					return;
				}
			}
			sy.openLoad();
			$http.post(sy.path + "/m/order/update", {
				type:type,
				id:order.id,
				info:info
			}).success(function(data) {
				sy.closeLoad();
				if(data == "sessionOut"){
					location.href = sy.path + '/m/login';
				}else if(data.code == 0){
					if(type == 3){
						history.go(-1);
					}else{
						location.reload();
					}
				}else{
					sy.alert(data.msg);
				}
			}).error(function(data, status) {
				sy.closeLoad();
				sy.alert('数据请求失败');
			});
		});
	}
});
