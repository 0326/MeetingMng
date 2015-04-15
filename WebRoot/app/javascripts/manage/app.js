
angular.module("appmanage", ["ngRoute", "ngAnimate", "ngCookies",
	"mControllers", "mDirectives", "mFilters", "mRoutes", "mServices"])

.factory('CompanyData', function($cookieStore, userService){
	var  _company = {
		username: $cookieStore.get('username'),
		avatarUrl: 'app/images/userimg.jpg'
	};
	var service = {};

	userService.getInfo(_company.username)
	.then(function(data){
	  _company = data;
	  if(_company.avatarUrl == null){
	    _company.avatarUrl = 'app/images/userimg.jpg';
	  }
	});
	
	service = {
		getAll: function(){return _company;},
		getUsername: function(){return _company.username;},
		setAvatarUrl: function(a){_company.avatarUrl = a;},
		setCellphone: function(a){_company.cellphone = a;},
		setEmail: function(a){_company.email = a;},
		setName: function(a){_company.name = a;},
		setOfficeLocation: function(a){_company.officeLocation = a;},
		setOfficePhone: function(a){_company.officePhone = a;},
		setSex: function(a){_company.sex = a;}
	};

	return service;
})

.config(function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl: 'app/templates/manage/home.html',
		// resolve:{
		// 	CompanyData:function(CompanyData){
		// 		return CompanyData;
		// 	}
		// },
		controller: 'homeCtrl'
	})
	
	.when('/departmanage', {
		templateUrl: 'app/templates/manage/departmanage.html',
		controller: 'departmanageCtrl'
	})
	
	.when('/meetingcreate', {
		templateUrl: 'app/templates/manage/meetingcreate.html',
		controller: 'meetingcreateCtrl'
	}).when('/meetinglist', {
		templateUrl: 'app/templates/manage/meetinglist.html',
		controller: 'meetinglistCtrl'
	})
	
	.when('/statmeeting', {
		templateUrl: 'app/templates/manage/statmeeting.html',
		controller: 'statmeetingCtrl'
	}).when('/statstuff', {
		templateUrl: 'app/templates/manage/statstuff.html',
		controller: 'statstuffCtrl'
	}).when('/stuffio', {
		templateUrl: 'app/templates/manage/stuffio.html',
		controller: 'stuffioCtrl'
	}).when('/stufflist', {
		templateUrl: 'app/templates/manage/stufflist.html',
		controller: 'stufflistCtrl'
	})

	.when('/profile', {
		templateUrl: 'app/templates/manage/profile.html',
		controller: 'profileCtrl'
	}).otherwise({
		redirectTo: '/'
	});

})

