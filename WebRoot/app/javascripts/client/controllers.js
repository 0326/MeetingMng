// client controllers.js
var mControllers = angular.module("mControllers", [])

.controller("layoutCtrl", function($scope, $rootScope, $window, userService) {
  $scope.client = {};
  
  userService
  .getInfo()
  .then(function(data){
    $scope.client = data;
    // $rootScope.client = data;
    $scope.$broadcast('userProfileBroadcast', $scope.client);
    $("#loading").fadeOut("normal",function(){
        $("#layout").fadeIn("normal",function(){
          $('.off-canvas-wrap').foundation('offcanvas', 'show', 'move-right');
        });
        $window.MMComet.initialize($scope.client.cellphone);
        console.log("websocket init ok");
    });
  })

  $scope.$on('userProfileChange',function(event, client){
    // console.log('this is in sidemenuCtrl:', client);
    $scope.client = client;
    $scope.$broadcast('userProfileBroadcast', client);
  });
  
  $scope.logout = function(){
    userService.logout(userService.profiles);
  }
})

.controller("homeCtrl", function($scope, meetingService, userService, listType) {
  $scope.client = userService.profiles;
  $scope.meetinglist = [];
  
  $scope.paginationConf = {
      currentPage: 1,
      totalItems: 16,
      itemsPerPage: 10,
      pagesLength: 15,
      perPageOptions: [10, 20, 30, 40, 50],
      rememberPerPage: 'perPageItems',
      onChange: function(){
        // console.log(this.currentPage);
      }
  };

  meetingService
   .getAll($scope.client.cellphone,1,listType)
   .then(function(meetinglist){
      $scope.meetinglist = meetinglist;
   });


  $scope.onDelete = function(meetingId){
    meetingService.delete(meetingId);
  }
  //用户如果在此刷新浏览器，需要重新加载数据
  $scope.$on("userProfileBroadcast",function(event, client){
    $scope.client = client;
     meetingService
     .getAll($scope.client.cellphone,1,listType)
     .then(function(meetinglist){
        $scope.meetinglist = meetinglist;
     });
  });
  
})


.controller("meetingnewCtrl", function($scope, meetingService, userService) {
  $scope.meeting = {
    meetingCreatorId: userService.profiles.cellphone,
    meetingFrequency: 1
  };
  $scope.submitAddForm = function(isValid){
    $scope.meeting.meetingStartTime = $("#starttime").val();
    $scope.meeting.meetingPredictFinishTime = $("#endtime").val();
    console.log("$scope.meeting");
    meetingService.add($scope.meeting);
  }
})

.controller("meetingeditCtrl", function($scope) {

})

.controller("meetingdetailCtrl", function($scope, meetingService,meetingId) {
  $scope.meeting = {'meetingId': meetingId};
  meetingService
  .findByMeetingId(meetingId)
  .then(function(data){
    // console.log(data);
    $scope.meeting = $.parseJSON(data.meeting);
  });


  $scope.onUpdate = function(){
    $("#detailBox").addClass("hide");
    $("#updateBox").removeClass("hide");
  }
  $scope.submitUpdateForm = function(isValid){
    meetingService.update($scope.meeting);
  }
  $scope.cancelUpdate = function(){
    $("#detailBox").removeClass("hide");
    $("#updateBox").addClass("hide");
  }

})

//会议联系人管理
.controller("meetingcontactCtrl", function($scope, userService, meetingId, organizerService, contactService) {
  $scope.meeting = {'meetingId': meetingId};  //会议
  $scope.organizerList = null;                //办会人员列表
  $scope.companyUsers = null;                 //公司联系人名单
  $scope.myContactors = null;                 //我的联系人名单
  $scope.addorganizerList = null;             //要添加的办会人员名单
  var init = function(){
    //获取办会者列表
    organizerService
    .findOrganizer(userService.profiles.cellphone,$scope.meeting.meetingId)
    .then(function(data){
      $scope.organizerList = data;
    });
    //获取添加办会人员列表
    contactService
    .findCompanyContactForOrganizer(userService.profiles.cellphone, $scope.meeting.meetingId)
    .then(function(data){
      $scope.addorganizerList = data;
    });  
    //对比要添加的联系人和
    function stateCallback(){

    }
  }
  init();
  //用户如果在此刷新浏览器，需要重新加载数据
  $scope.$on("userProfileBroadcast",function(event, client){
    init();
  });
})

.controller("meetingdiscussCtrl", function($scope, userService, topicService, meetingId) {
  $scope.meeting = {'meetingId': meetingId};
  $scope.topicList = [];

  var init = function(){
    $scope.newTopic = {
      'meetingId': $scope.meeting.meetingId,
      'creatorId': userService.profiles.cellphone
    }

    topicService
    .findTopicList($scope.meeting.meetingId,userService.profiles.cellphone)
    .then(function(data){
      $scope.topicList = data;
    });  
  }
  init();
  
  $scope.onCreate = function (){
    topicService.create($scope.newTopic);
  }

  //用户如果在此刷新浏览器，需要重新加载数据
  $scope.$on("userProfileBroadcast",function(event, client){
    init();
  });

})

//话题评论页面
.controller("topicCommentCtrl", function($scope, userService, topicService, topicId) {
  $scope.topic = {'topicId': topicId};
  $scope.commentList = [];

  var init = function(){
    topicService
    .findCommentByTopicId($scope.meeting.meetingId,userService.profiles.cellphone)
    .then(function(data){
      $scope.topicList = data;
    });  
  }
  init();
  
  $scope.onCreate = function (){
    topicService.create($scope.newTopic);
  }

  //用户如果在此刷新浏览器，需要重新加载数据
  $scope.$on("userProfileBroadcast",function(event, client){
    init();
  });

})


.controller("meetinghistoryCtrl", function($scope) {

})


//联系人模块
.controller("contactListCompanyCtrl", function($scope, userService, contactService) {
  $scope.userList = [];

  var init = function(){
    contactService
    .findCompanyContact(userService.profiles.cellphone)
    .then(function(data){
      $scope.userList = data;
    });  
  }
  init();
  
  //用户如果在此刷新浏览器，需要重新加载数据
  $scope.$on("userProfileBroadcast",function(event, client){
    init();
  });

})

.controller("contactListMineCtrl", function($scope, userService, contactService) {
  $scope.userList = [];

  var init = function(){
    contactService
    .findCompanyContact(userService.profiles.cellphone)
    .then(function(data){
      $scope.userList = data;
    });  
  }
  init();
  
  //用户如果在此刷新浏览器，需要重新加载数据
  $scope.$on("userProfileBroadcast",function(event, client){
    init();
  });

})


.controller("profileCtrl", function($scope, userService) {
   $scope.client =userService.profiles;

   $scope.modifyInfo = function(){
      // console.log($scope.client.avatarUrl);
      $scope.client.avatarUrl = $("#avatarUrl").val()
      userService.updateInfo($scope.client);
      $scope.$emit('userProfileChange',$scope.client);
   }
})
