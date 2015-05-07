
$(document).ready(function(){
	fixFooter();
	fixHeader();
	asideMenuController();
})

function fixFooter () {
	var wh = $(window).height(),
		  bh = $("body").height();
	
	if(bh < wh){
		$("html").css({"height":"100%"});
		$("body").css({"position":"relative","height":"100%"});
		$("#footer").css({"position":"fixed","bottom":"0","width":"100%"});
		// console.log("xx")
	}
}

function fixHeader (){
	$("#header").delegate("li","mouseover",function(){
		$(this).addClass("current");
	});
	$("#header").delegate("li","mouseout",function(){
		$(this).removeClass("current");
	});

	$("#header li").removeClass("selected");
	var navname = location.pathname.split("/")[1];
	$("#nav-"+navname).addClass("selected");

}

function asideMenuController(){
	$(".aside-menu").delegate("li","mouseover",function(){
		$(this).addClass("current");
	});
	$(".aside-menu").delegate("li","mouseout",function(){
		$(this).removeClass("current");
	});
	$(".aside-menu").delegate("li","click",function(){
		$(".aside-menu li").removeClass("selected");
		$(this).addClass("selected");
	});
	$(".aside-menu").delegate("a","click",function(e){
		e.preventDefault();
	});
}