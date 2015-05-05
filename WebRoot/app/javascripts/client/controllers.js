// client controllers.js
var mControllers = angular.module("mControllers", [])

.controller("layoutCtrl", function($scope, userService) {
  $scope.client = {};
  
  userService
  .getInfo()
  .then(function(data){
    $scope.client = data;
    $scope.$broadcast('userProfileBroadcast', $scope.client);
    $("#loading").fadeOut("normal",function(){
        $("#layout").fadeIn();
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

.controller("homeCtrl", function($scope, meetingService, userService) {
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
        console.log(this.currentPage);
      }
  };


  meetingService
  .getAll($scope.client.cellphone)
  .then(function(data){
    if(data.code == 0){
      $scope.meetinglist = $.parseJSON(data.meetinglist);
    }
    // console.log(data);
  });
  $scope.$on("userProfileBroadcast",function(event, client){
    $scope.client = client;
     meetingService
     .getAll($scope.client.cellphone)
     .then(function(data){
        if(data.code == 0){
          $scope.meetinglist = $.parseJSON(data.meetinglist);
        }
        // console.log(data);
     });
  });
  
})


.controller("meetingnewCtrl", function($scope, meetingService, userService) {
  $scope.meeting = {
    meetingCreatorId: userService.profiles.cellphone
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
  $scope.meeting = {};
  meetingService
  .findByMeetingId(meetingId)
  .then(function(data){
    // console.log(data);
    $scope.meeting = $.parseJSON(data.meeting);
  });
})

.controller("meetingcontactCtrl", function($scope) {
  var vm = $scope.vm = {};
  vm.items = [{id:1,name:"hello",followers:"followers",birthday:"2014",income:"2000"},
  {id:1,name:"hello",followers:"followers",birthday:"2014",income:"2000"}];
  vm.checkAll = function(checked) {
    angular.forEach(vm.items, function(item) {
      item.$checked = checked;
    });
  };
  vm.selection = function() {
    return _.where(vm.items, {$checked: true});
  };
  // 供页面中使用的函数
  vm.age = function(birthday) {
    return moment().diff(birthday, 'years');
  };


})

.controller("meetingdiscussCtrl", function($scope) {

})

.controller("meetinghistoryCtrl", function($scope) {

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





