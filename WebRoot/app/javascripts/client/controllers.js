// client controllers.js
var mControllers = angular.module("mControllers", [])

.controller("layoutCtrl", function($scope, $rootScope, $window, userService) {
  $scope.client = {};
  
  userService
  .getInfo()
  .then(function(data){
    if(data == "timeout"){
      //超时重新登录
      $window.location.href="/MeetingMng/";
    }
    $scope.client = data;
    // $rootScope.client = data;
    $scope.$broadcast('userProfileBroadcast', $scope.client);
    $("#loading").fadeOut("normal",function(){
        $("#layout").fadeIn("normal",function(){
          // $('.off-canvas-wrap').foundation('offcanvas', 'show', 'move-right');
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
   .getAll($scope.client.cellphone,0,listType)
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
     .getAll($scope.client.cellphone,0,listType)
     .then(function(meetinglist){
        $scope.meetinglist = meetinglist;
     });
  });
  
})


.controller("meetingnewCtrl", function($scope, meetingService, userService) {
  $scope.client = userService.profiles;
  $scope.meeting = {
    meetingFrequency: 1
  };
  $scope.submitAddForm = function(isValid){
    $scope.meeting.meetingCreatorId = $scope.client.cellphone;
    $scope.meeting.meetingStartTime = $("#starttime").val();
    $scope.meeting.meetingPredictFinishTime = $("#endtime").val();
    
    if($scope.meeting.meetingStartTime > $scope.meeting.meetingPredictFinishTime){
      alert("会议开始时间不能早于结束时间哦");
    }
    else if($scope.meeting.meetingStartTime < new Date().getTime()){
      alert("会议开始时间设置错误！");
    }
    else{
      meetingService.add($scope.meeting);
    }
    // console.log("$scope.meeting");
  }

  //用户如果在此刷新浏览器，需要重新加载数据
  $scope.$on("userProfileBroadcast",function(event, client){
    $scope.client = client;
  });


})

.controller("meetingeditCtrl", function($scope) {

})

.controller("meetingdetailCtrl", function($scope, meetingService, userService, meetingId) {
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
  //完成会议
  $scope.onFinish = function(){
    if(new Date().getTime() < $scope.meeting.meetingStartTime){
      alert("会议还未开始，现在不能完成！");
      return;
    }
    meetingService.finish($scope.meeting.meetingId, userService.profiles.cellphone);
  }
  //删除/取消会议
  $scope.onDelete = function(){
    meetingService.delete($scope.meeting.meetingId, userService.profiles.cellphone);
  }
  //会议签到
  $scope.onChecked = function(){

  }

})

//会议联系人管理
.controller("meetingcontactCtrl", function($scope, meetingService,
  userService, meetingId, organizerService, participatorService, contactService) {
  $scope.client = userService.profiles;
  $scope.meeting = {'meetingId': meetingId};  //会议
  $scope.organizerList = null;                //办会人员列表
  $scope.participatorList = null;             //参会人员列表
  $scope.companyUsers = null;                 //公司联系人名单
  $scope.myContactors = null;                 //我的联系人名单
  $scope.addorganizerList = null;             //要添加的办会人员名单
  $scope.addparticipatorList = null;          //要添加的参会人员名单
  var init = {
    all: function(){
      if(!$scope.client.cellphone) return;
      init.findMeeting();
      init.findOrganizer();
      init.findParticipator();
      init.findCompanyContactForOrganizer();
      init.findCompanyContactForParticipator();
    },
    findMeeting: function(){
      meetingService
      .findByMeetingId(meetingId)
      .then(function(data){
        // console.log(data);
        $scope.meeting = $.parseJSON(data.meeting);
        //会议处于活动状态才能修改联系人状态
        // if($scope.meeting.meetingState == 0){
        //   $(".contact-list").delegate('.info-group','click',function(){
        //      $(this).next().slideToggle("fast");
        //   });
        // }
        
      });
    },
    findOrganizer: function(){
      //获取办会者列表
      organizerService
      .findOrganizer($scope.client.cellphone,$scope.meeting.meetingId)
      .then(function(data){
        $scope.organizerList = data;
      });
    },
    findCompanyContactForOrganizer: function(){
      //获取添加办会人员列表
      contactService
      .findCompanyContactForOrganizer($scope.client.cellphone, $scope.meeting.meetingId)
      .then(function(data){
        $scope.addorganizerList = data;
      }); 
    },
    findParticipator: function(){
      //获取参会者列表
      participatorService
      .findParticipator($scope.client.cellphone,$scope.meeting.meetingId)
      .then(function(data){
        $scope.participatorList = data;
      });
    },
    findCompanyContactForParticipator: function(){
      //获取添加参会人员列表
      contactService
      .findCompanyContactForParticipator($scope.client.cellphone, $scope.meeting.meetingId)
      .then(function(data){
        $scope.addparticipatorList = data;
      });  
    },
  }

  init.all();

  $(".contact-list").delegate('.info-group','click',function(){
     $(this).next().slideToggle("fast");
  });

  $scope.onAddOrganizer = function(){
    var users = [];
    for(var i in $scope.addorganizerList){
      if($scope.addorganizerList[i].add) continue;
      if($(".li-user-add-false #o"+$scope.addorganizerList[i].cellphoneOfInfo)[0].checked){
        users.push($scope.addorganizerList[i].cellphoneOfInfo);
      }
    }
    organizerService
    .addOrganizer($scope.client.cellphone,$scope.meeting.meetingId,users)
    .then(function(data){
      if(data.code == 0){
        // alert("添加成功！");
        init.findOrganizer();
      }
      else if(data.code == -1){
        alert("您无此权限");
      }
      else if(data.code == -2){
        alert("客户端数据错误");
      }
      else if(data.code == -3){
        alert("服务器错误");
      }
      
    });
  }

  $scope.onAddParticipator = function(){
    var users = [];
    for(var i in $scope.addparticipatorList){
      if($scope.addparticipatorList[i].add) continue;
      if($(".li-user-add-false #p"+$scope.addparticipatorList[i].cellphoneOfInfo)[0].checked){
        users.push($scope.addparticipatorList[i].cellphoneOfInfo);
      }
    }
    participatorService
    .addParticipator($scope.client.cellphone,$scope.meeting.meetingId,users)
    .then(function(data){
      if(data.code == 0){
        // alert("添加成功！");
        init.findParticipator();
      }
      else if(data.code == -1){
        alert("您无此权限");
      }
      else if(data.code == -2){
        alert("客户端数据错误");
      }
      else if(data.code == -3){
        alert("服务器错误");
      }
      
    });
  }

  $scope.onDeleteOrganizer = function(operatedCellphone){
    var users = [operatedCellphone];
    organizerService
    .deleteOrganizer($scope.client.cellphone,$scope.meeting.meetingId,users)
    .then(function(data){
      if(data.code == 0){
        //删除成功，更新本地变量
        init.findOrganizer();
      }
      else if(data.code == -1){
        alert("您不能删除此人！")
      }
      else{
        alert("删除失败");
      }
    });
  }
  
  $scope.onDeleteParticipator = function(operatedCellphone){
    var users = [operatedCellphone];
    participatorService
    .deleteParticipator($scope.client.cellphone,$scope.meeting.meetingId,users)
    .then(function(data){
      if(data.code == 0){
        //删除成功，更新本地变量
        init.findParticipator();
      }
      else if(data.code == -1){
        alert("您不能删除此人！")
      }
      else{
        alert("删除失败");
      }
    });
  }
  
  $scope.onUpdateState = function(operatedCellphone,usertype){
    if(usertype == 'o'){
      organizerService
      .updateOrganizer($scope.client.cellphone,
        $scope.meeting.meetingId,
        operatedCellphone,
        state)
      .then(function(data){
        init.findOrganizer();
      });
    }
    else if(usertype == 'p'){
      participatorService
      .updateParticipator($scope.client.cellphone,
        $scope.meeting.meetingId,
        operatedCellphone,
        state)
      .then(function(data){
        init.findParticipator();
      });
    }
  }
  
  $scope.onSendInvite = function(operatedCellphone,usertype){
    if(usertype == 'o'){
      organizerService.inviteOrganizer($scope.client.cellphone,
        $scope.meeting.meetingId,
        operatedCellphone
        )
      .then(function(data){
        init.findOrganizer();
      });
    }
    else if(usertype == 'p'){
      participatorService.inviteParticipator($scope.client.cellphone,
        $scope.meeting.meetingId,
        operatedCellphone
        )
      .then(function(data){
        init.findParticipator();
      }); 
    }
  }
  
  $scope.signed = function(cellphone){
    if(new Date().getTime() < $scope.meeting.meetingStartTime){
      alert("会议开始了才能签到哦！");
      return;
    }
    participatorService
    .signed(cellphone,$scope.meeting.meetingId)
    .then(function(data){
      init.findParticipator();
    });
  
  }
  
  //用户如果在此刷新浏览器，需要重新加载数据
  $scope.$on("userProfileBroadcast",function(event, client){
    $scope.client = client;
    init.all();
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
    topicService
    .create($scope.newTopic)
    .then(function(data){
      init();
    });
  }

  //用户如果在此刷新浏览器，需要重新加载数据
  $scope.$on("userProfileBroadcast",function(event, client){
    init();
  });

})

//会议二维码扫描
// .controller("meetingdiscussCtrl", function($scope, userService, topicService, meetingId) {
  
// })
//话题评论页面
.controller("topicCommentCtrl", function($scope, userService, topicService, pid) {
  $scope.topic = {'topicId': pid.topicId};
  $scope.client = userService.profiles;
  $scope.commentList = [];
  $scope.newComment = {};

  var init = {
    all: function(){
      init.topic();
      init.commentList();
    },
    topic: function(){
      topicService
      .findTopicById($scope.topic.topicId)
      .then(function(data){
        $scope.topic = data;
      });
    },
    commentList: function(){
      topicService
      .findCommentByTopicId(
        pid.topicId,
        pid.meetingId,
        userService.profiles.cellphone)
      .then(function(data){
        $scope.commentList = data;
      });  
    }
  }
  init.all();
  
  $scope.onComment = function (){
    $scope.newComment = {
        'commentorId': $scope.client.cellphone,
        'topicId': pid.topicId,
        'meetingId': pid.meetingId,
        'content': $scope.newComment.content
      };
    topicService
    .comment($scope.newComment)
    .then(function(data){
      if(data.code == 0){
        // alert("评论成功！");
        init.commentList();
      }
      else{
        alert("创建失败");
      }
    });
  }

  $scope.onDelete = function(topicId){
    topicService
    .delete(pid.topicId,$scope.client.cellphone)
    .then(function(data){
      if(data.code == 0){
        // alert("评论成功！");
        init.commentList();
        window.location.href="/MeetingMng/u#/meeting-discuss?mid="+pid.meetingId;
      }
      else{
        // alert("创建失败");
      }
    });
  }

  //用户如果在此刷新浏览器，需要重新加载数据
  $scope.$on("userProfileBroadcast",function(event, client){
    $scope.client = client;
    init.all();
  });

})

//历史会议
.controller("meetinghistoryCtrl", function($scope, meetingService, userService, listType) {
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

//我的联系人/////////////////////////////////////////
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

//我的消息/////////////////////////////////////////
.controller("messageCtrl", function($scope, userService, messageService, isChecked) {
  $scope.msgList = [];
  $scope.currMsg = {};
  var init = function(){
    messageService
    .findMsgList(userService.profiles.cellphone,isChecked)
    .then(function(data){
      $scope.msgList = data;
      $scope.currMsg = $scope.msgList[0];
    });
  }
  init();
  
  if(isChecked == false){
    $(".contact-list").delegate('.info-group','click',function(){
       $(this).next().slideToggle("fast");
    });
  }
  
  $scope.onMeetingOperate = function(msgContent, msgId, state){
    var obj = $.parseJSON(msgContent);
    var msgType = obj.type;
    obj = $.parseJSON(obj.body);
    messageService
    .updateSate(msgId,userService.profiles.cellphone,obj.meetingId,state,msgType)
    .then(function(data){
      init();
    });
  }

  //用户如果在此刷新浏览器，需要重新加载数据
  $scope.$on("userProfileBroadcast",function(event, client){
    init();
  });

})

//个人信息
.controller("profileCtrl", function($scope, userService) {
   $scope.client =userService.profiles;

   $scope.modifyInfo = function(){
      // console.log($scope.client.avatarUrl);
      $scope.client.avatarUrl = $("#avatarUrl").val()
      userService.updateInfo($scope.client)
      .then(function(data){
        if(data.code == 0){
          // alert("修改成功！");
          $("#user-profile .pricing-table").removeClass("hide");
          $(".user-profile-form").addClass("hide");
        }
        else{
          alert("修改失败");
        }
      });
      $scope.$emit('userProfileChange',$scope.client);
   }

  $scope.onModify = function(){
    $("#user-profile .pricing-table").addClass("hide");
    $(".user-profile-form").removeClass("hide");
  }
  $scope.$on("userProfileBroadcast",function(event, client){
    $scope.client = client;
  });
})
