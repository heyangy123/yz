var myApp = sy.getModule('myApp',['ngRoute','infinite-scroll']);
myApp.config(['$routeProvider',function ($routeProvider) {  
    $routeProvider  
        .when('/1', {  
            templateUrl: 'goods',
            controller: 'goodsCtl'
        })  
        .when('/2', {
            templateUrl: 'store',
            controller: 'storeCtl'
        })  
        .otherwise({
            redirectTo: '/1'
        });  
}]); 

myApp.controller('goodsCtl', function($scope,$http) {
	  $("#goodsTab").addClass("on");
	  $("#storeTab").removeClass("on");
	  $scope.list = [];
	  $scope.busy = false;
	  $scope.page = 1;
	  $scope.limit = 10;
	  $scope.hasMore = true;
	  
	  $scope.nextPage = function() {
		  if ($scope.busy || !$scope.hasMore) return;
		  $scope.busy = true;
	      sy.openLoad();
	      $http.post(sy.path + "/m/home/goodsCollectList",{
	    	  		page:$scope.page,limit:$scope.limit
	      		})
				.success(function (data) {
					sy.closeLoad();
					if(data == "sessionOut"){
						location.href=sy.path + "/m/login";
						return;
					}
					if(data.length < $scope.limit)$scope.hasMore = false;
					$.each(data,function(i,o){
						$scope.list.push(o);
					});
					$scope.busy = false;
					$scope.page += 1;
		        }).error(function(data, status) {
		        	sy.closeLoad();
				});
	  };
	  
	  $scope.goDetail = function(id){
		  location.href = sy.path+"/m/goods/"+id;
	  }
});

myApp.controller('storeCtl', function($scope,$http) {
	  $("#storeTab").addClass("on");
	  $("#goodsTab").removeClass("on");
	  $scope.list = [];
	  $scope.busy = false;
	  $scope.page = 1;
	  $scope.limit = 10;
	  $scope.hasMore = true;
	  
	  $scope.nextPage = function() {
		  if ($scope.busy || !$scope.hasMore) return;
		  $scope.busy = true;
	      sy.openLoad();
		$http.post(sy.path + "/m/home/storeCollectList", {
			page : $scope.page,
			limit : $scope.limit
		}).success(function(data) {
			sy.closeLoad();
			if (data == "sessionOut") {
				location.href = sy.path + "/m/login";
				return;
			}
			if (data.length < $scope.limit)
				$scope.hasMore = false;
			$.each(data, function(i, o) {
				$scope.list.push(o);
			});
			$scope.busy = false;
			$scope.page += 1;
		}).error(function(data, status) {
			sy.closeLoad();
		});
		
		  $scope.goDetail = function(id){
			  location.href = sy.path+"/m/store/"+id;
		  }
	};
});