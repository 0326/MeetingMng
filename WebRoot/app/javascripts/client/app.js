
angular.module("appclient", ["ngRoute", "ngCookies",
	"mControllers", "mDirectives", "mFilters", "mRoutes", "mServices"])


.config(function($routeProvider) {
	$routeProvider
	//首页
	.when('/', {
		templateUrl: 'app/templates/client/home.html',
		controller: 'homeCtrl',
		resolve:{
			listType:function($route){
				return $route.current.params.listType || 0;
			}
		}
	})
	//会议相关
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
			meetingId:function($route){
				return $route.current.params.mid;
			},
		}
	}).when('/meeting-contact', {
		templateUrl: 'app/templates/client/meetingcontact.html',
		controller: 'meetingcontactCtrl',
		resolve:{
			meetingId:function($route){
				return $route.current.params.mid;
			},
		}
	}).when('/meeting-discuss', {
		templateUrl: 'app/templates/client/meetingdiscuss.html',
		controller: 'meetingdiscussCtrl',
		resolve:{
			meetingId:function($route){
				return $route.current.params.mid;
			},
		}
	}).when('/meeting-history', {
		templateUrl: 'app/templates/client/meetinghistory.html',
		controller: 'meetinghistoryCtrl',
		resolve:{
			listType:function($route){
				return $route.current.params.listType || 0;
			}
		}
	})
	.when('/meeting-qrcode', {
		templateUrl: 'app/templates/client/meetingqrcode.html',
		controller: 'meetingqrcodeCtrl',
		resolve:{
			listType:function($route){
				return $route.current.params.listType || 0;
			}
		}
	})
	//会议话题相关
	.when('/topic-comment', {
		templateUrl: 'app/templates/client/topiccomment.html',
		controller: 'topicCommentCtrl',
		resolve:{
			pid:function($route){
				return {'topicId': $route.current.params.tid,
								'meetingId': $route.current.params.mid};
			},
		}
	})
	// 联系人模块
	.when('/contact-list-company', {
		templateUrl: 'app/templates/client/contactlistcompany.html',
		controller: 'contactListCompanyCtrl',
		resolve:{
			topicId:function($route){
				// return $route.current.params.tid;
			},
		}
	})
	.when('/contact-list-mine', {
		templateUrl: 'app/templates/client/contactlistmine.html',
		controller: 'contactListMineCtrl',
		resolve:{
			topicId:function($route){
				// return $route.current.params.tid;
			},
		}
	})
  //我的消息
	.when('/message', {
		templateUrl: 'app/templates/client/message.html',
		controller: 'messageCtrl',
		resolve:{
			msgId:function($route){
				return $route.current.params.msgId;
			},
		}
	})
	//个人设置相关
	.when('/profile', {
		templateUrl: 'app/templates/client/profile.html',
		controller: 'profileCtrl'
	})

	.otherwise({
		// redirectTo: '/'
	});

})

