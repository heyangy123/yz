var myApp = sy.getModule('myApp',[]);

myApp.controller('ShareController', function($scope,$http,$sce) {
	  
	  $http.post(sy.path + "/share/getContent",{
		  id:id
		}).success(function (data) {
			$scope.game = data.game;
			$scope.organizer = data.organizer;
			$scope.accpeter = data.accpeter;
      });
	  
});


