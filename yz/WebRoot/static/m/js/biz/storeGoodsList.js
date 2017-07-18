var myApp = sy.getModule('myApp',['infinite-scroll']);

myApp.controller('StoreGoodsController', function($scope,$http) {
  var cate = $('#cate').val();
  var storeId = $('#storeId').val();
  var type = $('#type').val();
  $scope.sortType = 1;
  $scope.cateCode = cate;
  $scope.storeId = storeId;
  $scope.type=type;
  sessionStorage.removeItem("cateCode");
  sessionStorage.removeItem("keyword");
  
  
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
      $http.post(sy.path + "/m/store/storeGoods",{
    	  		areaCode:sy.areaCode,type:0,
    	  		page:$scope.page,limit:$scope.limit,
    	  		sortOrder:$scope.sortType,cateCode:$scope.cateCode,
    	  		storeId:$scope.storeId,type:$scope.type
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
