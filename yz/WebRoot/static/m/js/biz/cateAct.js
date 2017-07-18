var myApp = sy.getModule('myApp',['infinite-scroll']);
myApp.controller('MyController', function($scope,$http) {
  $scope.list = [];
  $scope.act = {};
  $scope.id = id;
  
  $scope.busy = false;
  $scope.page = 1;
  $scope.limit = 10;
  $scope.hasMore = true;
  
  $scope.nextPage = function() {
	  if ($scope.busy || !$scope.hasMore || sy.areaCode == undefined) return;
	  $scope.busy = true;
      sy.openLoad();
      $http.post(sy.path + "/m/cateAct/findById",{
    	  		id:$scope.id,page:$scope.page,limit:$scope.limit
      		})
			.success(function (data) {
				sy.closeLoad();
				if($scope.page == 1){
					if(data.code != 0){
						alert(data.msg);
						location.href = sy.path+"/m/";
					}
					
					$scope.act = data.act;
				}
				$.each(data.list,function(i,o){
					$scope.list.push(o);
				});
				if(data.list.length < $scope.limit)$scope.hasMore = false;
				$scope.busy = false;
				$scope.page += 1;
	        }).error(function(data, status) {
	        	sy.closeLoad();
			});
  };
  
  $scope.goDetail = function(id){
	  location.href = sy.path + "/m/goods/"+id;
  }
});
