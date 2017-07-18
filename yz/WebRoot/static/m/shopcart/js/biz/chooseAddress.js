var myApp = sy.getModule('myApp',[]);
myApp.controller('MyController', function($scope,$http) {
  $scope.list = [];
  
  $scope.select = {};
  
  sy.openLoad();
  $http.post(sy.path + "/m/addr/list", {}).success(function(data) {
	  sy.closeLoad();
	  	if(data == "sessionOut")location.href = sy.path+"/m/login";
		$.each(data,function(i,o){
			if(o.isDefault == 1){
				$scope.select = o;
			}
		});
		$scope.list = data;
	}).error(function(data, status) {
		sy.alert('数据请求失败');
		sy.closeLoad();
	});
  
  $scope.selectAddr = function(o){
	  $scope.select = o;
  }
  
  $scope.ok = function(){
	  if($scope.select.id == undefined){
		  sy.alert("请选择");
		  return;
	  }
	  if(sessionStorage.chooseType == 1 && sessionStorage.chooseId != undefined){
		  sy.openLoad();
		  var address = $scope.select.area +' '+ $scope.select.address;
		  $http.post(sy.path + "/m/lottery/logUpdate", 
				  {id:sessionStorage.chooseId,phone:$scope.select.phone,address:address,name:$scope.select.name}).success(function(data) {
			  sy.closeLoad();
			  	if(data == "sessionOut")location.href = sy.path+"/m/login";
			  	if(data.code == 0){
			  		sessionStorage.removeItem("chooseId");
			  		sessionStorage.removeItem("chooseType");
			  		location.href = sy.path + "/m/lottery/log";
			  	}else{
			  		sy.alert(data.msg);
			  	}
			}).error(function(data, status) {
				sy.closeLoad();
				sy.alert('数据请求失败');
			});
	  }else{
		  sessionStorage.defaultAddr = $scope.select.id;
		  if (/(iPhone|iPad|iPod)/i.test(navigator.userAgent)) {
	  	        window.location.href = sessionStorage.chooseId;
	  	    } else {
	  	    	window.history.go(-1); 
	  	    }
	  }
  }
  
  $scope.updateAddr = function(o){
	  location.href = sy.path + "/m/addr/"+o.id;
  }
});
