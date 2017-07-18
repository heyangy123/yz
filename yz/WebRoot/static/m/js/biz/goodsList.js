var myApp = sy.getModule('myApp',['infinite-scroll']);
myApp.controller('GoodsController', function($scope,$http) {
  $scope.sortType = 1;
  $scope.cateCode = $("#cateCode").val();
  $scope.keyword = $("#keyword").val();
  
  $scope.list = [];
  
  $scope.clickSort = function(i){
	  if($scope.sortType == i && i!=3){
		  return;
	  }
	  
	  if($scope.sortType == 3 && i == 3){
		  $scope.sortType = 4;
	  }else{
		  $scope.sortType = i;
	  }
	  
	  $scope.busy = false;
	  $scope.page = 1;
	  $scope.limit = 10;
	  $scope.hasMore = true;
	  
	  $scope.list = [];
	  $scope.nextPage();
  }
  
  $scope.busy = false;
  $scope.page = 1;
  $scope.limit = 10;
  $scope.hasMore = true;
  
  $scope.nextPage = function() {
	  if ($scope.busy || !$scope.hasMore || sy.areaCode == undefined) return;
	  $scope.busy = true;
      sy.openLoad();
      $http.post(sy.path + "/m/goods/goodsList",{
    	  		areaCode:sy.areaCode,type:0,
    	  		page:$scope.page,limit:$scope.limit,
    	  		sortType:$scope.sortType,cateCode:$scope.cateCode,
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
	  location.href = sy.path + "/m/goods/"+id;
  }
});
