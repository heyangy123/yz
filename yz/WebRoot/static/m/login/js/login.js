var myApp = sy.getModule('myApp',[]);
myApp.controller('MyController', function($scope,$http) {
	$scope.account = "";
	$scope.password = "";
  
	$scope.login = function(){
		if(!UT.isPhone($scope.account)){
			sy.alert("请输入正确的手机号");
			return;
		}
		if($scope.password == ''){
			sy.alert("请输入密码");
			return;
		}
		
		sy.openLoad();
		$http.post(sy.path + "/m/login/checkLogin", {
			account : $scope.account,
			password : $scope.password
		}).success(function(data) {
			sy.closeLoad();
			if (data.code == 0) {
				sy.setCookie("account",$scope.account,100,sy.contextPath);
				sy.setCookie("pwd",hex_md5($scope.password),100,sy.contextPath);
				
				if(sessionStorage.lastUnloginUrl != undefined){
					var url = sessionStorage.lastUnloginUrl;
					sessionStorage.removeItem("lastUnloginUrl");
					location.href = url;
				}else{
					location.href = sy.path + '/m/home';
				}
			} else {
				sy.alert(data.msg);
			}
		}).error(function(data, status) {
			sy.alert('数据请求失败');
			sy.closeLoad();
		});
	}
});
