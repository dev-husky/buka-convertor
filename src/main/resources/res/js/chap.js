var currP = 0;
var currImg = "pic-img";
var currImgPre = "pic-img-2";

//var windowHeight = $(window).height() - 20;
//var windowWidth = $(window).width() - 20;

var viewHeight = 960;
var viewWidth = 960;

function prev() {
	if (currP > 0) {
		currP--;
		showPic();
	} else {
		parent.prevChap();
	}
}

function next() {
	if (currP < pages.length - 1) {
		currP++;
		showPic();
	} else {
		parent.nextChap();
	}
}

function showPic() {
	$("#" + currImgPre).attr("src", pages[currP]);
}

function resizePic(imgId) {
	var img = $("#" + imgId);
	var naturalHeight = img[0].naturalHeight;
	var naturalWidth = img[0].naturalWidth;
	
	var height = viewHeight;
	var width = parseInt(height * naturalWidth / naturalHeight);
	if (width > viewWidth) {
		width = viewWidth;
		height = parseInt(width * naturalHeight / naturalWidth);
	}
	var marginTop = 0;
	var controlButtonHeight = height + 20;
	if ($(window).height() > height) {
		marginTop = parseInt(($(window).height() - height) / 2);
		controlButtonHeight = $(window).height();
	}
	
	img.css("height", height + "px");
	img.css("width", width + "px");
	img.css("margin-top", marginTop + "px");
	$(".pic-control-button").css("height", controlButtonHeight + "px");
	
	$("html").animate({scrollTop: $("body").offset().top}, 1);
}

function menu() {
	parent.showMenu();
}

function switchPic() {
	resizePic(currImg);
	$("#" + currImgPre).hide();
	$("#" + currImg).fadeIn();
}

$(function() {
	$("#prev-button").click(prev);
	$("#menu-button").click(menu);
	$("#next-button").click(next);
	
	$("#pic-img").load(function() {
		currImg = "pic-img";
		currImgPre = "pic-img-2";
		switchPic();
	});
	$("#pic-img-2").load(function() {
		currImg = "pic-img-2";
		currImgPre = "pic-img";
		switchPic();
	});
	$(window).resize(function() {
		resizePic(currImg);
	});
	
	
	showPic();
	
});