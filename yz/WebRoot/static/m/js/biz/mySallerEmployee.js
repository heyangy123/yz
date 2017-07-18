var factory = sy.getModule('sallerEmp',['infinite-scroll']);
	// 创建后台数据交互工厂（分页）
	factory.factory('employee', function ($http) {
		var storeId = sessionStorage.storeId;
	    var Demo = function () {
	        this.items = [];
	        this.superior=[];
	        this.busy = false;
	        this.after = '';
	        this.page = 1;
	        this.limit=10;
	        this.isend=false;
	    };
	    
	    Demo.prototype.nextPage = function () {
	        if(this.busy || this.isend) return;
	        this.busy = true;
	        sy.openLoad();
	        $http.post(sy.path+'/m/saleCenter/mySallerEmp',{
				page:this.page,limit:this.limit,id:storeId
			}).success(function (data) {
				if(String(data).indexOf('sessionOut')!=-1){
					location.href=sy.path+"/m/login";
					return;
				}
	            var items = data;
	            if(items){
	            	for (var i = 0; i < items.list.length; i++) {
		                this.items.push(items.list[i]);
		            }
	            	if(this.page==1) this.superior = items.superior;
	            	if(items.list.length<this.limit) this.isend=true;
	            }else{
	            	this.isend=true;
	            }
	            
	            this.busy = false;
	            this.page += 1;
	            sy.closeLoad();
	        }.bind(this)).error(function(data, status) {
	        	sy.closeLoad();
			});
	    };
	    return Demo;
	});
	
	// 申明页面控制类、
	factory.controller('sallerEmpController',['$http','$scope','employee',
	   	function ($http,$scope, employee) {
		     $scope.storeId = sessionStorage.storeId;
		     $scope.storeName = sessionStorage.storeName;
		     $scope.logo = sessionStorage.logo;
		     $scope.employee = new employee();
		     $scope.shareStore = function(id){
		    	 sessionStorage.storeId = $scope.storeId;
		    	 sessionStorage.storeName = $scope.storeName;
		    	 sessionStorage.logo = $scope.logo;
		    	 location.href = sy.path+'/m/store/'+id;
		     }
		}
	]);