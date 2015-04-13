
angular.module("appclient", ["ngRoute", "ngCookies",
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
		templateUrl: 'app/templates/client/home.html',
		controller: 'homeCtrl'
	}).when('/meeting-new', {
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
	}).when('/meeting-history', {
		templateUrl: 'app/templates/client/meetinghistory.html',
		controller: 'meetinghistoryCtrl'
	}).when('/profile', {
		templateUrl: 'app/templates/client/profile.html',
		controller: 'profileCtrl'
	}).otherwise({
		redirectTo: '/'
	});

})

