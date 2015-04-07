
angular.module("appmanage", ["ngRoute", "ngAnimate", 
	"mControllers", "mDirectives", "mFilters", "mRoutes", "mServices"])

.factory('CompanyData', function(){
	var company = {};

  company.companyName = "软酷管家科技有限公司";
  company.username = "1833559609@qq.com";
  company.avatarUrl = "app/images/userimg.jpg";
  // console.log(company);
  return company;
})

.config(function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl: 'app/templates/manage/home.html',
		controller: 'homeCtrl'
	}).when('/departmanage', {
		templateUrl: 'app/templates/manage/departmanage.html',
		controller: 'departmanageCtrl'
	}).when('/meetingcreate', {
		templateUrl: 'app/templates/manage/meetingcreate.html',
		controller: 'meetingcreateCtrl'
	}).when('/meetinglist', {
		templateUrl: 'app/templates/manage/meetinglist.html',
		controller: 'meetinglistCtrl'
	}).when('/statmeeting', {
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
	}).when('/profile', {
		templateUrl: 'app/templates/manage/profile.html',
		controller: 'profileCtrl'
	}).otherwise({
		redirectTo: '/'
	});

})
