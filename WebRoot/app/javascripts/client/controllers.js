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

.controller("homeCtrl", function($scope, meetingService) {
  $scope.meetinglist = [];
   meetingService
   .getAll()
   .then(function(data){
      if(data.code == 0){
        $scope.meetinglist = $.parseJSON(data.meetings);
      }
      // console.log(data);
   });
})


.controller("meetingnewCtrl", function($scope, meetingService, userService) {
  $scope.meeting = {
    meetingCreatorId: userService.profiles.cellphone
  };
  $scope.submitAddForm = function(isValid){
    meetingService.add($scope.meeting);
  }
})

.controller("meetingeditCtrl", function($scope) {

})

.controller("meetingdetailCtrl", function($scope, meeting) {
  $scope.meeting = {
      title:'会管家周会',
      date:'每周五 下午4:00',
      addr:'软件学院502教室',
      id:'345346546'
    };
})

.controller("meetingcontactCtrl", function($scope) {

})

.controller("meetingdiscussCtrl", function($scope) {

})

.controller("meetinghistoryCtrl", function($scope) {

})

.controller("profileCtrl", function($scope, userService) {
   $scope.client =userService.profiles;
   
})





