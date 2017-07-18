var myApp = sy.getModule('myApp', []);
myApp.controller('GoodsController', function($scope, $http) {
	$scope.list = [];
	$scope.md5 = md5;
	$scope.from = from;
	
	if(isPc()){
		location.href = sy.path + "/web/goods/share/"+md5+"/"+from;
	}

	sy.openLoad();
	$http.post(sy.path + "/m/goods/goodsShareList", {
		md5:$scope.md5
	}).success(function(data) {
		sy.closeLoad();
		$scope.list = data;
	}).error(function(data, status) {
		sy.closeLoad();
	});

	$scope.goDetail = function(id) {
		location.href = sy.path + "/m/goods/" + id + "?u="+$scope.from;
	}
});
