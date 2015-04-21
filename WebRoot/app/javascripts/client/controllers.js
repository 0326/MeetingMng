// controllers.js
var mControllers = angular.module("mControllers", [])

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


.controller("meetingnewCtrl", function($scope, CompanyData) {

})

.controller("meetingeditCtrl", function($scope, CompanyData) {

})

.controller("meetingdetailCtrl", function($scope, meeting) {
  $scope.meeting = {
      title:'会管家周会',
      date:'每周五 下午4:00',
      addr:'软件学院502教室',
      id:'345346546'
    };
})

.controller("meetingcontactCtrl", function($scope, CompanyData) {

})

.controller("meetingdiscussCtrl", function($scope, CompanyData) {

})

.controller("meetinghistoryCtrl", function($scope, CompanyData) {

})

.controller("profileCtrl", function($scope, CompanyData, userService) {
   $scope.company = CompanyData;
   $scope.userLogout = function(){
    userService.logout({
      username: $scope.company.username
    });
   }
})





