// services.js
var mServices = angular.module("mServices", [])

.service('sendMail', function($http){

})

.constant('PostCfg',{
  headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
  transformRequest: function(data) {
      return $.param(data);
  }
})