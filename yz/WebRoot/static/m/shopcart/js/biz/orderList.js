var myApp = sy.getModule('myApp', [ 'ngRoute', 'infinite-scroll' ]);
myApp.controller('orderCtl', function($scope, $http) {
	//tab类型
	$scope.state = 0;

	//分页
	$scope.list = [];
	$scope.busy = false;
	$scope.page = 1;
	$scope.limit = 10;
	$scope.hasMore = true;

	//批量付款价格
	$scope.batchPrice = 0;

	//批量选择
	$scope.selectOrders = function(order) {
		if (order.select == 1) {
			order.select = 0;
			$scope.batchPrice = $scope.batchPrice - parseInt(order.price);
		} else {
			order.select = 1;
			$scope.batchPrice = $scope.batchPrice + parseInt(order.price);
		}
	}

	//切换tab
	$scope.load = function(state) {
		if ($scope.state == state)
			return;
		$scope.state = state;

		$scope.hasBatch = false;
		$scope.batchPrice = 0;

		$scope.list = [];
		$scope.busy = false;
		$scope.page = 1;
		$scope.limit = 10;
		$scope.hasMore = true;

		$scope.nextPage();
	}

	//分页加载数据
	$scope.nextPage = function() {
		if ($scope.busy || !$scope.hasMore)
			return;
		$scope.busy = true;
		sy.openLoad();
		$http.post(sy.path + "/m/order/list", {
			state : $scope.state,
			page : $scope.page,
			limit : $scope.limit
		}).success(function(data) {
			sy.closeLoad();
			if(data == "sessionOut"){
				location.href = sy.path + '/m/login';
			}
			if (data.length < $scope.limit)
				$scope.hasMore = false;
			$.each(data, function(i, o) {
				$scope.list.push(o);
			});
			$scope.busy = false;
			$scope.page += 1;
		}).error(function(data, status) {
			sy.closeLoad();
			sy.alert('数据请求失败');
		});
	};

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
	
	$scope.batchPay = function(){
		var idArray = [];
		$.each($scope.list,function(i,o){
			if(o.select == 1){
				idArray.push(o.id);
			}
		});
		if(idArray.length > 0)
			location.href = sy.path + "/m/order/toPay/"+idArray.join(",");
	}
	
	$scope.toDetail = function(id){
		location.href = sy.path + "/m/order/"+id;
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
					$scope.hasBatch = false;
					$scope.batchPrice = 0;
	
					$scope.list = [];
					$scope.busy = false;
					$scope.page = 1;
					$scope.limit = 10;
					$scope.hasMore = true;
	
					$scope.nextPage();
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
