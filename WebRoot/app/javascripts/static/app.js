angular.module("appstatic", ["ngRoute", "ngAnimate", "ngCookies",
	"mControllers", "mDirectives", "mFilters", "mRoutes", "mServices"])

.config(function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl: 'app/templates/static/home.html',
		controller: 'homeCtrl'
	}).when('/regist', {
		templateUrl: 'app/templates/static/regist.html',
		controller: 'registCtrl'
	}).when('/callemail', {
		templateUrl: 'app/templates/static/callemail.html',
		controller: 'callemailCtrl'
	}).when('/activition', {
		templateUrl: 'app/templates/static/activition.html',
		// resolve:{},
		controller: 'activitionCtrl'
	}).otherwise({
		redirectTo: '/'
	});

})

