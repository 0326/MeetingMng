// controllers.js
var mControllers = angular.module("mControllers", [])


.controller("homeCtrl", function($scope) {
  $scope.user = {
    username: "example@gmail.com",
    password: "password",
    autoLogin: false
  }
  $scope.getFormData = function(){
    console.log($scope.user);
  }
})

.controller("stufflistCtrl", function($scope) {
  $scope.stuffs = [
    {name: "Bob",depart:"技术部"},
    {name: "Lili",depart:"市场部"},
    {name: "Perl",depart:"技术部"},
    {name: "Python",depart:"技术部"}];

  $scope.getFormData = function(){
    console.log($scope.user);
  }
})