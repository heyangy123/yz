var myApp = sy.getModule('myApp',[]);
myApp.controller('MyController', function($scope,$http) {
	$scope.list = [];
	
    sy.openLoad();
	$http.post(sy.path + "/m/home/myTask", {}).success(function(data) {
		if(data == "sessionOut"){
			location.href = sy.path + "/m/login";
		}else{
			$scope.list = data;
		}
		sy.closeLoad();
	}).error(function(data, status) {
		sy.closeLoad();
	});
  
	$scope.goDetail = function(task){
		switch (task.taskId) {
		case '1001'://签到
			if(task.state != 1) $scope.sign();
			break;
		case '1002'://浏览商品
			location.href = sy.path+"/m/";
			break;
		case '1003'://购买商品
			location.href = sy.path+"/m/category";
			break;
		case '1004'://分享(仅支持APP)
			location.href = sy.path+"/m/saleCenter/salerStore";
			break;
		case '1005'://每日邀请
			location.href = sy.path+"/m/home/invite";
			break;
		default:
			break;
		}
	}
	
	$scope.sign = function(){
	    sy.openLoad();
		$http.post(sy.path + "/m/home/userSign", {}).success(function(data) {
			sy.closeLoad();
			if(data == "sessionOut"){
				location.href = sy.path+"/m/login";
			}else if(data.code == 0){
				location.reload();
			}else if(data.code == 2){
				sy.alert(data.msg);
			}else{
				sy.alert(data.msg);
			}
		}).error(function(data, status) {
			sy.closeLoad();
		});
	}
});
