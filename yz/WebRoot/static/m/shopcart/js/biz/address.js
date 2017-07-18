var myApp = sy.getModule('myApp',[]);
myApp.controller('MyController', function($scope,$http) {
  $scope.addr = {};
  $scope.addr.id = id;
  $scope.addr.isDefault = 1;
  
  if($scope.addr.id != ""){
	  sy.openLoad();
	  $http.post(sy.path + "/m/addr/findById", {id:$scope.addr.id}).success(function(data) {
		  	sy.closeLoad();
		  	if(data == "sessionOut")location.href = sy.path+"/m/login";
		  	$scope.addr = data;
		  	showLocation($scope.addr.area);
		}).error(function(data, status) {
			sy.closeLoad();
			sy.alert('数据请求失败');
		});
  } else{
	  showLocation();
  }
  
  $scope.save = function(){
	  if($.trim($scope.addr.name).length == 0 || $.trim($scope.addr.area).length == 0 
			  || $.trim($scope.addr.address).length == 0 || $.trim($scope.addr.phone).length == 0
			  || $.trim($scope.addr.zipCode).length == 0){
		  return;
	  }
	  
	  if($.trim($scope.addr.name).length > 15){
		  sy.alert("收货人名称过长");
		  return;
	  }
	  
	  if($.trim($scope.addr.address).length > 100){
		  sy.alert("详细地址过长");
		  return;
	  }
	  
	  if(!UT.isPhone($scope.addr.phone)){
		  sy.alert("请输入有效的手机号");
		  return;
	  }
	  
	  if(!UT.isZipCode($scope.addr.zipCode)){
		  sy.alert("请输入有效的邮编");
		  return;
	  }
	  
	  sy.openLoad();
	  $http.post(sy.path + "/m/addr/save", $scope.addr).success(function(data) {
		  	sy.closeLoad();
		  	if(data == "sessionOut")location.href = sy.path+"/m/login";
		  	if(data.code == 0){
		  		if (/(iPhone|iPad|iPod)/i.test(navigator.userAgent)) {
		  	        window.location.href = window.document.referrer;
		  	    } else {
		  	    	window.history.go(-1); 
		  	    }
		  	}else{
		  		sy.alert(data.msg);
		  	}
		}).error(function(data, status) {
			sy.alert('数据请求失败');
			sy.closeLoad();
		});
  }
  
  $scope.del = function(){
	  if($scope.addr.id){
		  sy.confirm('确定要删除吗?',function(){
		  sy.openLoad();
		  $http.post(sy.path + "/m/addr/del", {id:$scope.addr.id}).success(function(data) {
			  	sy.closeLoad();
			  	if(data == "sessionOut")location.href = sy.path+"/m/login";
			  	if(data.code == 0){
			  		if (/(iPhone|iPad|iPod)/i.test(navigator.userAgent)) {
			  	        window.location.href = window.document.referrer;
			  	    } else {
			  	    	window.history.go(-1); 
			  	    }
			  	}else{
			  		sy.alert(data.msg);
			  	}
			}).error(function(data, status) {
				sy.alert('数据请求失败');
				sy.closeLoad();
			});
		  });
	  }
  }
  
  $scope.setDefault = function(){
	  if($scope.addr.isDefault == 1) {
		  $scope.addr.isDefault = 0;
	  }
	  else{
		  $scope.addr.isDefault = 1;
	  }
  }
});


function showLocation(area) {
	var appElement = document.querySelector('[ng-controller=MyController]');
	var $scope = angular.element(appElement).scope(); 
	var loc = new Location();
	var title = [ '省份', '地级市', '市、县、区' ];
	if(area != undefined){
		var arr = area.split("-");
		title = arr;
	}
	
	$.each(title, function(k, v) {
		title[k] = '<option value="">' + v + '</option>';
	})
	$('#loc_province').append(title[0]);
	$('#loc_city').append(title[1]);
	$('#loc_town').append(title[2]);
	$('#loc_province').change(function() {
		title = [ '<option value="">省份</option>', '<option value="">地级市</option>', '<option value="">市、县、区</option>' ];
		$scope.addr.area = "";
		$('#loc_city').empty();
		$('#loc_city').append(title[1]);
		loc.fillOption('loc_city', '0,' + $('#loc_province').val());
		$('#loc_town').empty();
		$('#loc_town').append(title[2]);
	})
	$('#loc_city').change(
			function() {
				$scope.addr.area = "";
				$('#loc_town').empty();
				$('#loc_town').append(title[2]);
				loc.fillOption('loc_town', '0,' + $('#loc_province').val()
						+ ',' + $('#loc_city').val());
			})

	$('#loc_town').change(function() {
		var province = $("#loc_province option:selected").text();
		var city = $("#loc_city option:selected").text();
		var town = $("#loc_town option:selected").text();
		
		$scope.addr.area = province+"-"+city+"-"+town;
	})

//	if (province) {
//		loc.fillOption('loc_province', '0', province);
//		if (city) {
//			loc.fillOption('loc_city', '0,' + province, city);
//			if (town) {
//				loc.fillOption('loc_town', '0,' + province + ',' + city, town);
//			}
//		}
//	} else {
		loc.fillOption('loc_province', '0');
//	}

}