// filters.js
var mFilters = angular.module("mFilters", [])

//日期过滤器，传入[频率,时间戳]
.filter('meetingDate', function($filter){
	return function(obj){
		var freq = obj[0];
		var time = obj[1]; 
		var result = "";
<<<<<<< HEAD
		if(freq == 1){
			result = $filter('date')(time,'mm月dd日 HH:mm');
		}
		else if(freq == 2){ 
			result = "每天 " + $filter('date')(time,'HH:mm');
		}
		else if(freq == 3){ 
			result="每周" + $filter('date')(time,'EEEE HH:mm')
				.replace('Monday','一')
				.replace('Tuesday','二') 
				.replace('Wednesday','三') 
				.replace('Thursday','四') 
				.replace('Friday','五') 
				.replace('Saturday','六') 
				.replace('Sunday','日'); 
		}
		else if(freq == 4){ 
			result="每月" + $filter('date')(time,'dd日 HH:mm'); 
=======
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
>>>>>>> 78f7660e22b8ea2b4a3d2ef8dcac8e488c585ad2
		}

		return result;
	}
})