var factory = sy.getModule('storeCategory',[]);
	// 创建后台数据交互工厂（分页）
	factory.factory('category', function ($http) {
	    var Demo = function () {
	        this.items = {};
	    };
	        
        Demo.prototype.query = function (id) {
        	this.items={};
            sy.openLoad();
	        $http.post(sy.path+'/m/storeCategory/cateList',{
				storeId:id
			}).success(function (data) {
	            this.items = data;
	            sy.closeLoad();
	        }.bind(this)).error(function(data, status) {
	        	sy.closeLoad();
			});
        };
	    return Demo;
	});
	
	
	// 申明页面控制类、
	factory.controller('cateController',['$scope','category',
	   	function ($scope, category) {
			var storeId= $('#storeId').val();
		     $scope.category = new category();
		     $scope.category.query(storeId);
		     $scope.storeId = storeId;
		}
	]);
	
	