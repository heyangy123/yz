var myApp = sy.getModule('myApp',['ngRoute','infinite-scroll']);
myApp.config(['$routeProvider',function ($routeProvider) {  
    $routeProvider  
        .when('/1', {  
            templateUrl: 'tz',  
            controller: 'tzCtl'  
        })  
        .when('/2', {
            templateUrl: 'gg',  
            controller: 'ggCtl'  
        })  
        .otherwise({
            redirectTo: '/1'
        });  
}]); 

myApp.controller('tzCtl', function($scope,$http) {
	  $(".tz").addClass("on");
	  $(".gg").removeClass("on");
	  $scope.list = [];
	  $scope.busy = false;
	  $scope.page = 1;
	  $scope.limit = 10;
	  $scope.hasMore = true;
	  
	  $scope.nextPage = function() {
		  if ($scope.busy || !$scope.hasMore || sy.areaCode == undefined) return;
		  $scope.busy = true;
	      sy.openLoad();
	      $http.post(sy.path + "/m/notify/notifyList",{
	    	  		type:1,page:$scope.page,limit:$scope.limit
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
});

myApp.controller('ggCtl', function($scope,$http) {
	  $(".gg").addClass("on");
	  $(".tz").removeClass("on");
	  $scope.list = [];
	  $scope.busy = false;
	  $scope.page = 1;
	  $scope.limit = 10;
	  $scope.hasMore = true;
	  
	  $scope.nextPage = function() {
		  if ($scope.busy || !$scope.hasMore || sy.areaCode == undefined) return;
		  $scope.busy = true;
	      sy.openLoad();
	      $http.post(sy.path + "/m/notify/notifyList",{
	    	  		type:2,page:$scope.page,limit:$scope.limit
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
});