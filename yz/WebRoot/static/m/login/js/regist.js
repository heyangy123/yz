var myApp = sy.getModule('myApp',[]);
myApp.controller('MyController', function($scope,$http) {
	$scope.account = "";
	$scope.code = "";
	$scope.password = "";
	$scope.pwd = "";
	$scope.inviteCode = "";
	
	$scope.sendEnable = true;
	
	$scope.sendMsg = function(){
		if($scope.sendEnable){
			if(!UT.isPhone($scope.account)){
				sy.alert("请输入正确的手机号");
				return;
			}
			
			sy.openLoad();
			$http.post(sy.path + "/m/regist/getMobileMsg",{phone:$scope.account,type:1})
				.success(function (data) {
					sy.closeLoad();
					if(data.code == 0){
						$scope.msgBtn("#msg");
						$scope.code = data.msg;
					}else{
						sy.alert(data.msg);
					}
				})
				.error(function(data, status) {
			      	sy.alert('数据请求失败');
			      	sy.closeLoad();
				});
		}
	}
	
	$scope.wait = 60;
	$scope.msgBtn = function(o) {
		if ($scope.wait == 0) {
			$(o).removeClass("yzm_send");
			$scope.sendEnable = true;
			$(o).html("发送验证码");
			$scope.wait = 60;
		} else {
			$(o).addClass("yzm_send");
			$scope.sendEnable = false;
			$(o).html($scope.wait+"秒后重新获取");
			$scope.wait--;
			setTimeout(function() {
				$scope.msgBtn(o);
			}, 1000)
		}
	}
  
	$scope.regist = function(){
		if(!UT.isPhone($scope.account)){
			sy.alert("请输入正确的手机号");
			return;
		}
		if($scope.code == ''){
			sy.alert("请输入短信验证码");
			return;
		}
		if($scope.password == ''){
			sy.alert("请输入密码");
			return;
		}
		if($scope.password != $scope.pwd){
			sy.alert("密码输入不一致");
			return;
		}
		
		if(!$(".h_checkbox").is(".current_app")){
			sy.alert("请确认您已阅读软件许可协议");
			return;
		}
		
		sy.openLoad();
		$http.post(sy.path + "/m/regist/register", {
			phone : $scope.account,
			password : $scope.password,
			code:$scope.code,
			inviteCode:$scope.inviteCode
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
					location.href = sy.path + "/m/";
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
