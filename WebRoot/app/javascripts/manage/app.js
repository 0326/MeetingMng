
angular.module("appmanage", ["ngRoute", "ngCookies",
	"mControllers", "mDirectives", "mFilters", "mRoutes", "mServices"])

// .provider('userProvider', function($http, $q, $cookieStore){
// 	var _provider = {};
// 	_provider.getInfo = function(username){
//     var d = $q.defer();
//     $http.get("/MeetingMng/api/v1/companyManagerGetInfo?username="+$cookieStore.get('username'))
//     .success(function(data, status){
//       service.profiles = data.user;
//       d.resolve(data.user);
//     });
//     return d.promise;
//   }

//   return _provider;
// })

.config(function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl: 'app/templates/manage/home.html',
		resolve:{

		},
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
	}).when('/stuffdetail', {
		templateUrl: 'app/templates/manage/stuffdetail.html',
		controller: 'stuffdetailCtrl',
		resolve:{
			cellphone:function($route, $http){
				return $route.current.params.cellphone;
			},
		}
	})

	.when('/profile', {
		templateUrl: 'app/templates/manage/profile.html',
		controller: 'profileCtrl'
	}).otherwise({
		redirectTo: '/'
	});

})

