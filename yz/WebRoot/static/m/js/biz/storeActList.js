var myApp = sy.getModule('myApp',[]);

myApp.controller('storeActController', function($scope,$http) {
  $scope.storeId = sessionStorage.storeId;
  
  $scope.list = [];

  
  $scope.query = function() {
      sy.openLoad();
      $http.post(sy.path + "/m/store/storeAct",{
    	  		storeId:$scope.storeId
      		})
			.success(function (data) {
				$.each(data,function(i,o){
					$scope.list.push(o);
				});
	            sy.closeLoad();
	        }).error(function(data, status) {
	        	sy.closeLoad();
			});
  };
  $scope.query();
  $scope.goDetail = function(id){
	  location.href = sy.path + "/m/goods/"+id;
  }
});
