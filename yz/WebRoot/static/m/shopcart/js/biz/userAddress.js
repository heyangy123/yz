var myApp = sy.getModule('myApp',[]);

myApp.controller('MyController', function($scope,$http) {
  $scope.list = [];
  sy.openLoad();
  $http.post(sy.path + "/m/addr/list", {}).success(function(data) {
	  sy.closeLoad();
	  	if(data == "sessionOut")location.href = sy.path+"/m/login";
		$.each(data,function(i,o){
			if(o.isDefault == 1){
				$scope.select = o;
			}
		});
		$scope.list = data;
	}).error(function(data, status) {
		sy.alert('数据请求失败');
		sy.closeLoad();
	});
  
  $scope.updateAddr = function(o){
	  location.href = sy.path + "/m/addr/"+o.id;
  }
});
