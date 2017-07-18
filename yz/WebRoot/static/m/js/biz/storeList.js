var myApp = sy.getModule('myApp',['infinite-scroll']);
myApp.controller('myController', function($scope,$http) {
  $scope.keyword = $("#keyword").val();
  
  $scope.list = [];
  
  $scope.busy = false;
  $scope.page = 1;
  $scope.limit = 10;
  $scope.hasMore = true;
  
  $scope.nextPage = function() {
	  if ($scope.busy || !$scope.hasMore || sy.areaCode == undefined) return;
	  $scope.busy = true;
      sy.openLoad();
      $http.post(sy.path + "/m/store/list",{
    	  		areaCode:sy.areaCode,
    	  		page:$scope.page,limit:$scope.limit,
    	  		keyword:$scope.keyword
      		})
			.success(function (data) {
				if(data.length < $scope.limit)$scope.hasMore = false;
				$.each(data,function(i,o){
					$scope.list.push(o);
				});
				$scope.busy = false;
				$scope.page += 1;
	            sy.closeLoad();
	        }).error(function(data, status) {
	        	sy.closeLoad();
			});
  };
  
  $scope.goDetail = function(id){
	  location.href = sy.path + "/m/store/"+id;
  }
});
