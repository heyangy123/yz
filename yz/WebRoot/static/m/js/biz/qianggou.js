var myApp = sy.getModule('myApp',['infinite-scroll']);

myApp.directive('onFinishRenderFilters', function ($timeout) {
    return {
        restrict: 'A',
        link: function(scope,element,attr) {
            if (scope.$last === true) {
            	var finishFunc=scope.$parent[attr.onFinishRenderFilters];
            	if(finishFunc)
            	{
            		finishFunc();
            	}
            }
        }
    };
});

myApp.controller('MyController', function($scope,$http) {
  $scope.list = [];
  $scope.titles = [];
  $scope.type = "";
  $scope.firstType = "";
  $scope.seconds = 0;
  $scope.minutes = 0;
  $scope.timer;
  
  $scope.time = {h1:0,h2:0,m1:0,m2:0,s1:0,s2:0};
  
  $scope.busy = true;
  $scope.page = 1;
  $scope.limit = 10;
  $scope.hasMore = true;
  
  //请求抢购信息
  sy.openLoad();
  $http.post(sy.path + "/m/i/quickShopping",{areaCode:sy.areaCode})
	.success(function (data) {
		sy.closeLoad();
		if(data.title.length > 0){
			$scope.seconds = data.seconds;
			$scope.minutes = data.minutes;
			$scope.type = data.title[0];
			$scope.firstType = data.title[0];
			$scope.timer = setInterval(function(){$scope.$apply($scope.setTimer);},1000);

			$scope.busy = false;
			$scope.nextPage();
		}
		$scope.titles = data.title;
  }).error(function(data, status) {
  	sy.closeLoad();
	});
  
  $scope.complete=function(){
	  new Swiper('#navSlider',{
			pagination: '',
			createPagination: false,
			paginationClickable: false,
			slidesPerView: 'auto'
		});
  }
  
  //tab选择
  $scope.selectType = function(title){
	  if($scope.type == title)return;
	  $scope.type = title;
	  
	  $scope.busy = false;
	  $scope.page = 1;
	  $scope.limit = 10;
	  $scope.hasMore = true;
	  $scope.list = [];
	  $scope.nextPage();
  }
  
  //计算倒计时
  $scope.setTimer = function() {
		var time = 0;
		--$scope.seconds;
		
		if($scope.firstType == $scope.type){
			if ($scope.seconds > 0) {// 未开始
				time = formatTime($scope.seconds);
			} else {
				var s = $scope.minutes * 60 + $scope.seconds;
				time = formatTime(s);
			}
		}else{
			var s = $scope.seconds + ($scope.type-$scope.firstType)*60*60;
			time = formatTime(s);
		}
		
		$scope.time = {
				h1:time.substring(0,1),
				h2:time.substring(1,2),
				m1:time.substring(3,4),
				m2:time.substring(4,5),
				s1:time.substring(6,7),
				s2:time.substring(7,8)
		}
	}
  
  $scope.nextPage = function() {
	  if ($scope.busy || !$scope.hasMore || sy.areaCode == undefined) return;
	  $scope.busy = true;
      sy.openLoad();
      $http.post(sy.path + "/m/i/quickGoodsList",{
    	  		type:$scope.type,areaCode:sy.areaCode,page:$scope.page,limit:$scope.limit
      		})
			.success(function (data) {
				sy.closeLoad();
				$.each(data,function(i,o){
					$scope.list.push(o);
				});
				if(data.length < $scope.limit)$scope.hasMore = false;
				$scope.busy = false;
				$scope.page += 1;
	        }).error(function(data, status) {
	        	sy.closeLoad();
			});
  };
  
  $scope.goDetail = function(goods){
	  if(goods.total > 0 && $scope.seconds<0 && $scope.firstType == $scope.type)
		  location.href = sy.path + "/m/goods/"+goods.id;
  }
  
  $scope.parseInt = function(num){
	  return parseInt(num);
  }
  
});

function formatTime(s){
    // 计算
    var h=0,i=0;
    if(s>60){
        i=parseInt(s/60);
        s=parseInt(s%60);
        if(i > 60) {
            h=parseInt(i/60);
            i = parseInt(i%60);
        }
    }
    // 补零
    var zero=function(v){
        return (v>>0)<10?"0"+v:v;
    };
    return [zero(h),zero(i),zero(s)].join(":");
};
