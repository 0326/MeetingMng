// filters.js
var mFilters = angular.module("mFilters", [])

//日期过滤器，传入[频率,时间戳]
.filter('meetingDate', function($filter){
	return function(obj){
		var freq = obj[0];
		var time = obj[1]; 
		var result = "";
		if(freq == 0){
			result = $filter('date')(time,'mm月dd日 hh:mm');
		}
		else if(freq == 1){ 
			result = "每天 " + $filter('date')(time,'hh:mm');
		}
		else if(freq == 2){ 
			result="每周 " + $filter('date')(time,'EEEE, hh:mm'); 
		}
		else if(freq == 3){ 
			result="每月 " + $filter('date')(time,'dd日 hh:mm'); 
		}

		return result;
	}
})