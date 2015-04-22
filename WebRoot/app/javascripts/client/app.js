
angular.module("appclient", ["ngRoute", "ngCookies",
	"mControllers", "mDirectives", "mFilters", "mRoutes", "mServices"])


.config(function($routeProvider) {
	$routeProvider
	.when('/', {
		templateUrl: 'app/templates/client/home.html',
		controller: 'homeCtrl'
	})

	.when('/meeting-new', {
		templateUrl: 'app/templates/client/meetingnew.html',
		controller: 'meetingnewCtrl'
	}).when('/meeting-edit', {
		templateUrl: 'app/templates/client/meetingedit.html',
		controller: 'meetingeditCtrl'
	}).when('/meeting-detail', {
		templateUrl: 'app/templates/client/meetingdetail.html',
		controller: 'meetingdetailCtrl',
		resolve:{
			meeting:function($route, $http){
				return $route.current.params.mid;
			}
		}
	}).when('/meeting-contact', {
		templateUrl: 'app/templates/client/meetingcontact.html',
		controller: 'meetingcontactCtrl'
	}).when('/meeting-discuss', {
		templateUrl: 'app/templates/client/meetingdiscuss.html',
		controller: 'meetingdiscussCtrl'
	}).when('/meeting-history', {
		templateUrl: 'app/templates/client/meetinghistory.html',
		controller: 'meetinghistoryCtrl'
	})

	.when('/profile', {
		templateUrl: 'app/templates/client/profile.html',
		controller: 'profileCtrl'
	})

	.otherwise({
		redirectTo: '/'
	});

})

