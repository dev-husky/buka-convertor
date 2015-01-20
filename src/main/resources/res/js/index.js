var currImgChap = -1;
var currImgPage = -1;
var currImg = "picImg";

var prevImgChap = -1;
var prevImgPage = -1;
var prevImg = "picImg2";

var nextImgChap = -1;
var nextImgPage = -1;
var nextImg = "picImg3";

var windowHeight = $(window).height();
var windowWidth = $(window).width();


$(function() {
	init();
	
	$("#prevButton").click(prev);
	$("#nextButton").click(next);
	$("#menuButton").click(showMenu);
	$("#menuBackground").click(hideMenu);
	
	//	$("#menuDialog").click(function(e) {
//		e.stopPropagation();
//	});
	
	$(window).resize(function() {
		windowHeight = $(window).height();
		windowWidth = $(window).width();
		resizeBrowser();
		resizePic(currImg);
		resizePic(nextImg);
		resizePic(prevImg);
	});
	
	showMainContainer();
});

function init() {
	$("title").append(chapOrder.name);
	$(".cover").attr("src", chapOrder.logo);
	$(".name").append(chapOrder.name);
	$(".author").append("作　　者：" + chapOrder.author);
	$(".last-updated").append("最后更新：" + chapOrder.lastuptimeex);
	$(".intro").append("简　　介：" + chapOrder.intro);
	
	if (chapOrder.episodeCount > 0) {
		$(".episode").append("<div class=\"index-tab\">连载（话）</div>")
	}
	if (chapOrder.chapterCount > 0) {
		$(".chapter").append("<div class=\"index-tab\">单行本（卷）</div>")
	}
	if (chapOrder.legendCount > 0) {
		$(".legend").append("<div class=\"index-tab\">番外篇</div>")
	}
	for (var i in chapOrder.links) {
		var browserEntryHtml = "<div class=\"browser-entry\">"
			+ "<a href=\"javascript:showPicBrowser(" + chapOrder.links[i].pageIndex  + ");\">" + chapOrder.links[i].title
			+ "</a></div>";
		if (chapOrder.links[i].type == 0) {
			$(".episode").append(browserEntryHtml);
		}
		if (chapOrder.links[i].type == 1) {
			$(".chapter").append(browserEntryHtml);
		}
		if (chapOrder.links[i].type == 2) {
			$(".legend").append(browserEntryHtml);
		}
	}
}

function showMainContainer() {
	//$("body").css("background-color", "#ffffff");
	$("#picBrowser").hide();
	$("#mainContainer").show();
	resizeBrowser();
}

function showPicBrowser(chap) {
	currImg = "picImg";
	prevImg = "picImg2";
	nextImg = "picImg3";
	$("#" + currImg).css({"z-index":"-1", "width":"100px", "height":"100px"});
	$("#" + prevImg).css({"z-index":"-1", "width":"100px", "height":"100px"});
	$("#" + nextImg).css({"z-index":"-1", "width":"100px", "height":"100px"});
	
	$("#mainContainer").hide();
	hideMenu();
	$("#picBrowser").show();
	resizeBrowser();
	
	$("#" + currImg).load(function() {
		resizePic(currImg);
		$("#" + currImg).css("z-index", "1");
		
		$("#picImg").load(function() {
			resizePic("picImg");
		});
		$("#picImg2").load(function() {
			resizePic("picImg2");
		});
		$("#picImg3").load(function() {
			resizePic("picImg3");
		});
		
		setPrevImg();
		setNextImg();
	});
	currImgChap = chap;
	currImgPage = 0;
	setCurrImg();
	
	
	
}

function setCurrImg() {
	$("#" + currImg).attr("src", "pics/" + pageIndex[currImgChap].cid + "/" + pageIndex[currImgChap].picNames[currImgPage]);
}

function setPrevImg() {
	if (currImgPage > 0) { 
		// 当前页不是第一页，上一页为当前章节的上一页
		prevImgChap = currImgChap;
		prevImgPage = currImgPage - 1;
	} else {
		if (currImgChap > 0) { 
			// 当前页是第一页，且当前章节不是第一章，上一页为前一章的最后一页
			prevImgChap = currImgChap - 1;
			prevImgPage = pageIndex[prevImgChap].picNames.length - 1;
		} else {
			// 当前页是第一页，且当前章节是第一章，没有上一页
			prevImgChap = -1;
			prevImgPage = -1;
		}
	}
	if (prevImgChap > -1 && prevImgPage > -1) {
		$("#" + prevImg).attr("src", "pics/" + pageIndex[prevImgChap].cid + "/" + pageIndex[prevImgChap].picNames[prevImgPage]);
	}
}

function setNextImg() {
	if (currImgPage < pageIndex[currImgChap].picNames.length - 1) {
		// 当前页不是最后一页，下一页为当前章的下一页
		nextImgChap = currImgChap;
		nextImgPage = currImgPage + 1;
	} else {
		if (currImgChap < pageIndex.length - 1) {
			// 当前页是最后一页，且当前章节不是最后一章，下一页为下一章的第一页
			nextImgChap = currImgChap + 1;
			nextImgPage = 0;
		} else {
			// 当前页是最后一页，且当前章节是最后一章，没有下一页
			nextImgChap = -1;
			nextImgPage = -1;
		}
	}
	if (nextImgChap > -1 && nextImgPage > -1) {
		$("#" + nextImg).attr("src", "pics/" + pageIndex[nextImgChap].cid + "/" + pageIndex[nextImgChap].picNames[nextImgPage]);
	}
}

function prev() {
	if (prevImgChap > -1 && prevImgPage > -1) {
		// 隐藏当前页
		$("#" + currImg).css("z-index", "-1");
		// 显示上一页
		$("#" + prevImg).css("z-index", "1");
		// 当前页变为下一页
		nextImgChap = currImgChap;
		nextImgPage = currImgPage;
		// 原先的上一页变为当前页
		currImgChap = prevImgChap;
		currImgPage = prevImgPage;
		
		// 重新分配img容器
		var tmp = nextImg;
		// 当前页变为下一页
		nextImg = currImg;
		// 原先的上一页变为当前页
		currImg = prevImg;
		// 原先的下一页容器空出来用于放新的上一页
		prevImg = tmp;
		
		setPrevImg();
	} else {
		alert("已经是第一页啦！");
	}
}

function next() {
	if (nextImgChap > -1 && nextImgPage > -1) {
		// 隐藏当前页
		$("#" + currImg).css("z-index", "-1");
		// 显示下一页
		$("#" + nextImg).css("z-index", "1");
		// 当前页变为上一页
		prevImgChap = currImgChap;
		prevImgPage = currImgPage;
		// 原先的下一页变为当前页
		currImgChap = nextImgChap;
		currImgPage = nextImgPage;
		
		// 重新分配img容器
		var tmp = prevImg;
		// 当前页变为上一页
		prevImg = currImg;
		// 原先的下一页变为当前页
		currImg = nextImg;
		// 原先的上一页容器空出来用于放新的下一页
		nextImg = tmp;
		
		setNextImg();
	} else {
		alert("已经是最后一页啦！");
	}
}

function prevChap() {
	if (currImgChap > 0) {
		currImgChap = currImgChap - 1;
		// 重新分配img容器
		var tmp = prevImg;
		prevImg = currImg;
		currImg = nextImg;
		nextImg = tmp;
		showPicBrowser(currImgChap);
	} else {
		alert("已经是第一章啦！");
	}
}

function nextChap() {
	if (currImgChap < pageIndex.length - 1) {
		currImgChap = currImgChap + 1;
		// 重新分配img容器
		var tmp = nextImg;
		nextImg = currImg;
		currImg = prevImg;
		prevImg = tmp;
		showPicBrowser(currImgChap++);
	} else {
		alert("已经是最后一章啦！");
	}
}

function back() {
	showMainContainer();
}

function showMenu() {
	$("#menuBackground").show();
	$("#menuDialog").show();
	resizeBrowser();
	$("#currChapInfo").empty();
	var chapInfo = getChapInfo(pageIndex[currImgChap].cid);
	var chapInfoStr;
	if (chapInfo.title == chapInfo.idx.toString()) {
		if (chapInfo.type == 0) {
			chapInfoStr = "第 " + chapInfo.title + " 话";
		} else if (chapInfo.type == 1) {
			chapInfoStr = "第 " + chapInfo.title + " 卷";
		} else if (chapInfo.type == 2) {
			chapInfoStr = "番外 " + chapInfo.title + " 话";
		}
	} else {
		chapInfoStr = chapInfo.title;
	}
	chapInfoStr += "　" + (currImgPage + 1) + "/" + pageIndex[currImgChap].picNames.length
	$("#currChapInfo").append(chapInfoStr);
}
function hideMenu() {
	$("#menuDialog").hide();
	$("#menuBackground").hide();
}


function resizePic(imgId) {
	var img = $("#" + imgId);
	var naturalHeight = img[0].naturalHeight;
	var naturalWidth = img[0].naturalWidth;
	
	var height = windowHeight;
	var width = parseInt(height * naturalWidth / naturalHeight);
	if (width > windowWidth) {
		width = windowWidth;
		height = parseInt(width * naturalHeight / naturalWidth);
	}

	var marginTop = 0;
	var marginLeft = 0;
	var controlButtonHeight = height;
	if (windowHeight > height) {
		marginTop = parseInt((windowHeight - height) / 2);
		controlButtonHeight = windowHeight;
	}
	if (windowWidth > width) {
		marginLeft = parseInt((windowWidth - width) / 2);
	}
	
	img.css({"width":width + "px", "height":height + "px", "margin-top":marginTop + "px", "margin-left":marginLeft + "px"});
	
}

function resizeBrowser() {
	if ($("#mainContainer").height() < windowHeight) {
		$("#mainContainer").css("height", windowHeight + "px");
	}
	$(".info-right").css("width", ($(".info").width() - $(".info-left").width()) + "px");
	$(".pic-background").css({"width":windowWidth + "px", "height":windowHeight + "px"});
	$(".pic-control-button").css("height", windowHeight + "px");
	//$(".pic").css({"width":windowWidth + "px", "height":windowHeight + "px"});
	$(".menu-background").css({"width":windowWidth + "px", "height":windowHeight + "px"});
	$(".menu-dialog").css({
		"margin-left":(windowWidth - $(".menu-dialog").width()) / 2 + "px", 
		"margin-top":(windowHeight - $(".menu-dialog").height()) / 2 + "px"
	});
}

function getChapInfo(cid) {
	for (var i in chapOrder.links) {
		if (chapOrder.links[i].cid == cid) {
			return chapOrder.links[i];
		}
	}
}