var myApp = sy.getModule('myApp',['ngRoute','infinite-scroll']);
myApp.config(['$routeProvider',function ($routeProvider) {  
    $routeProvider  
        .when('/1', {  
            templateUrl: 'pt',  
            controller: 'ptCtl'  
        })  
        .when('/2', {
            templateUrl: 'sj',  
            controller: 'sjCtl'  
        })  
        .otherwise({
            redirectTo: '/1'
        });  
}]); 

myApp.controller('ptCtl', function($scope,$http) {
	  $(".pt").addClass("on");
	  $(".sj").removeClass("on");
	  $scope.list = [];
	  $scope.busy = false;
	  $scope.page = 1;
	  $scope.limit = 10;
	  $scope.hasMore = true;
	  
	  $scope.nextPage = function() {
		  if ($scope.busy || !$scope.hasMore) return;
		  $scope.busy = true;
	      sy.openLoad();
	      $http.post(sy.path + "/m/home/myCoupons",{
	    	  		type:1,page:$scope.page,limit:$scope.limit
	      		})
				.success(function (data) {
					sy.closeLoad();
					if(data == "sessionOut")location.href = sy.path + "/m/login";
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
});

myApp.controller('sjCtl', function($scope,$http) {
	  $(".sj").addClass("on");
	  $(".pt").removeClass("on");
	  $scope.list = [];
	  $scope.busy = false;
	  $scope.page = 1;
	  $scope.limit = 10;
	  $scope.hasMore = true;
	  
	  $scope.nextPage = function() {
		  if ($scope.busy || !$scope.hasMore) return;
		  $scope.busy = true;
	      sy.openLoad();
	      $http.post(sy.path + "/m/home/myCoupons",{
	    	  		type:2,page:$scope.page,limit:$scope.limit
	      		})
				.success(function (data) {
					sy.closeLoad();
					if(data == "sessionOut")location.href = sy.path + "/m/login";
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
});