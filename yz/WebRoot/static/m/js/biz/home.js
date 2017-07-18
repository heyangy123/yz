var myApp = sy.getModule('myApp',[]);
myApp.controller('MyController', function($scope,$http) {
	$scope.hasLogin = false;
	$scope.user = {};
	
    sy.openLoad();
	$http.post(sy.path + "/m/home/userInfo", {}).success(function(data) {
		if(data.code == 0){
			$scope.user = data.user;
			$scope.hasLogin = true;
		}
		sy.closeLoad();
	}).error(function(data, status) {
		sy.closeLoad();
	});
  
	$scope.sign = function(){
		if(!$scope.hasLogin){
			location.href = sy.path+"/m/login";
		}else if($scope.user.isSign == 0){
		    sy.openLoad();
			$http.post(sy.path + "/m/home/userSign", {}).success(function(data) {
				if(data == "sessionOut"){
					location.href = sy.path+"/m/login";
				}else if(data.code == 0){
					$scope.user.isSign = 1;
					$scope.user.credit = $scope.user.credit + parseInt(data.msg);
				}else if(data.code == 2){
					$scope.user.isSign = 1;
					sy.alert(data.msg);
				}else{
					sy.alert(data.msg);
				}
				sy.closeLoad();
			}).error(function(data, status) {
				sy.closeLoad();
			});
		}
	}
});
