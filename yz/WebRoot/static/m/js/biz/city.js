var city = sy.getModule('city',[]);
city.controller('CityController', function($scope,$http) {
  $scope.hotList = [];
  $scope.pinyinList = [];
  $scope.area = {};
  
  sy.openLoad();
  $http.post(sy.path + "/m/i/cityList",{})
		.success(function (data) {
			$scope.hotList = data.hotList;
			$scope.pinyinList = data.pinyinList;
            sy.closeLoad();
        }).error(function(data, status) {
        	sy.closeLoad();
		});
  
  $scope.setLocation = function(city){
	  sessionStorage.areaCode = city.code;
	  sessionStorage.areaName = city.name;
	  location.href = sy.path + "/m/";
  }
});

var myCity = new BMap.LocalCity();
myCity.get(initLocation);
function initLocation(result){
	var cityName = result.name;
	if(cityName.substring(cityName.length,cityName.length-1) == "市"){
		cityName = cityName.substring(0,cityName.length-1);
	}
	
	$.post(sy.path + "/m/i/cityMatch", {name:cityName}, function(result) {
		sy.closeLoad();
    	if (result.code == 0) {
    		var msg = result.msg.split(",");
    		var appElement = document.querySelector('[ng-controller=CityController]');
    		var $scope = angular.element(appElement).scope(); 
    		$scope.area.name = msg[1];
    		$scope.area.code = msg[0];
    		$scope.$apply();
		}else{
			sy.alert("定位失败");
		}
	}, 'json');
}