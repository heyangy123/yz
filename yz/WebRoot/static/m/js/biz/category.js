var myApp = sy.getModule('myApp',[]);
myApp.controller('CateController', function($scope,$http) {
  $scope.parentList = [];
  $scope.nowIndex = 0;
  $scope.sonData = {};
  
  sy.openLoad();
  $http.post(sy.path + "/m/category/list",{})
		.success(function (data) {
			$scope.parentList = data;
			if(data.length>0){
				$scope.getSons(data[0].code,0,true);
			}
			
            sy.closeLoad();
        }).error(function(data, status) {
        	sy.closeLoad();
		});
  
  $scope.getSons = function(code,index,init){
	  if(!init && index == $scope.nowIndex)return;
	  $scope.nowIndex = index;
	  
	  if(sy.data[code]){
		  $scope.sonData = sy.data[code];
		  return;
	  }
	  
	  sy.openLoad();
	  $http.post(sy.path + "/m/category/list",{parent:code,areaCode:sy.areaCode})
		.success(function (data) {
			$scope.sonData = data;
			sy.data[code] = data;//缓存数据
			sy.closeLoad();
      }).error(function(data, status) {
      	sy.closeLoad();
		});
  }
  
  $scope.goCateAct = function(id){
	  location.href = sy.path + "/m/cateAct/"+id;
  }
  
  $scope.goGoodsList = function(code){
	  location.href = sy.path + "/m/goods?cateCode="+code;
  }
});
