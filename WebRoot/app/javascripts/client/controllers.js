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
    $scope.meeting.meetingStartTime = $("#starttime").val();
    $scope.meeting.meetingPredictFinishTime = $("#endtime").val();
    console.log("$scope.meeting")
    meetingService.add($scope.meeting);
  }
})

.controller("meetingeditCtrl", function($scope) {

})

.controller("meetingdetailCtrl", function($scope, meeting) {

})

.controller("meetingcontactCtrl", function($scope) {

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





