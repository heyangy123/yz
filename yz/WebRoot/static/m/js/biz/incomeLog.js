var myApp = sy.getModule('myApp',['infinite-scroll']);
myApp.controller('MyController', function($scope,$http) {
  $scope.list = [];
  $scope.money = 0;
  $scope.busy = false;
  $scope.page = 1;
  $scope.limit = 10;
  $scope.hasMore = true;
  
  $scope.nextPage = function() {
	  if ($scope.busy || !$scope.hasMore) return;
	  $scope.busy = true;
      sy.openLoad();
      $http.post(sy.path + "/m/saleCenter/myIncomeLog", {
			page : $scope.page,
			limit : $scope.limit
		}).success(function(data) {
			if(data == "sessionOut")location.href = sy.path + "/m/login";
			if (data.list.length < $scope.limit){
				$scope.hasMore = false;
			}
			if($scope.page == 1)$scope.money = data.money;
			$.each(data.list, function(i, o) {
				$scope.list.push(o);
			});
			$scope.busy = false;
			$scope.page += 1;
			sy.closeLoad();
		}).error(function(data, status) {
			sy.closeLoad();
		});
  };
});
