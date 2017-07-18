var myApp = sy.getModule('myApp',[]);
myApp.controller('MyController', function($scope,$http) {
	$scope.user = {};
	
    sy.openLoad();
	$http.post(sy.path + "/m/home/userInfo", {}).success(function(data) {
		if(data == 'sessionOut'){
			location.href = sy.path + '/m/login';
		}else if(data.code == 0){
			$scope.user = data.user;
		}
		sy.closeLoad();
	}).error(function(data, status) {
		sy.closeLoad();
	});
	
	$scope.editHeadImg = function(){
		$("#img").trigger("click");
	}
  
	$scope.editNickName = function(){
		var value = prompt("昵称：", "");
		if(value != null && value != ''){
			if(value.length > 20){
				sy.alert("昵称长度过长");
				return;
			}
			$scope.updateUser(2,{id:$scope.user.id,nickName:value});
		}
	}
	
	$scope.editSex = function(){
		$scope.updateUser(3,{id:$scope.user.id,sex:$("#sex").val()});
	}
	
	$scope.updateUser = function(type,user){
		sy.openLoad();
		$http.post(sy.path + "/m/home/updateUser", user).success(function(data) {
			sy.closeLoad();
			if(data == 'sessionOut'){
				location.href = sy.path + '/m/login';
			}else if(data.code == 0){
				if(type == 1){
					$scope.user.headImg = user.headImg;
				}else if(type == 2){
					$scope.user.nickName = user.nickName;
				}else if(type == 3){
					$scope.user.sex = user.sex;
				}
			}
		}).error(function(data, status) {
			sy.closeLoad();
		});
	}
	
	$scope.toAddress=function(){
		location.href = sy.path+'/m/addr';
	}
});

//回调
function callback(id){
	sy.closeLoad();
	var appElement = document.querySelector('[ng-controller=MyController]');
	var $scope = angular.element(appElement).scope(); 
	$scope.updateUser(1,{id:$scope.user.id,headImg:id});
}

$(function(){
	$("#img").bind("change",function(){
		if($(this).val() == '')return;
		sy.openLoad();
		$("#form1").submit();
	});
	
})