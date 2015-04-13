
angular.module("appmanage", ["ngRoute", "ngAnimate", "ngCookies",
	"mControllers", "mDirectives", "mFilters", "mRoutes", "mServices"])

.factory('CompanyData', function($cookieStore){
	// console.log($cookieStore.get("username"));
	if($cookieStore.get("username") == undefined){
		// window.location.href="/MeetingMng";
	}
	var company = {};
  company.companyName = "会管家科技责任有限公司";
  company.username = $cookieStore.get("username") || "test@mm.com";
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

