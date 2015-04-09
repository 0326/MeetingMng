var mApp = angular.module("appstatic", ["ngRoute", "ngAnimate", "ngCookies",
	"mControllers", "mDirectives", "mFilters", "mRoutes", "mServices"])

mApp.config(function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl: 'app/templates/static/home.html',
		controller: 'homeCtrl'
	}).when('/regist', {
		templateUrl: 'app/templates/static/regist.html',
		controller: 'registCtrl'
	}).otherwise({
		redirectTo: '/'
	});

})

