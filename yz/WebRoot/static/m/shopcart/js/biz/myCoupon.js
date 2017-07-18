var myApp = sy.getModule('myApp',['infinite-scroll']);

myApp.controller('ptCtl', function($scope,$http) {
	  $scope.list = [];
	  $scope.busy = false;
	  $scope.page = 1;
	  $scope.limit = 100;
	  $scope.hasMore = true;
	  
	  $scope.nextPage = function() {
		  if ($scope.busy || !$scope.hasMore) return;
		  $scope.busy = true;
	      sy.openLoad();
	      $http.post(sy.path + "/m/home/myCoupons",{
	    	  		type:1,source:"shopcart",page:$scope.page,limit:$scope.limit
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
		        	sy.alert('数据请求失败');
				});
	  };
	  
	  $scope.select = function(t){
		  sessionStorage.couponValue = t.value;
		  sessionStorage.couponId = t.id;
		  
		  if (/(iPhone|iPad|iPod)/i.test(navigator.userAgent)) {
	  	        window.location.href = window.document.referrer;
	  	    } else {
	  	    	window.history.go(-1); 
	  	    }
	  }
});
