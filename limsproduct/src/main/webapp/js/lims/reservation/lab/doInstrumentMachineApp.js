//定义的变量
var is_table;
//下面的 jsonData 接收者
var data;
//date need
var searchDates = $("#searchDate").val();
var RoundTripController = {
	SearchRequest: {
		"searchDate": searchDates,
	},
	Language: "CN",
};
var ConditionController = RoundTripController;
//date need over
var dateline = new dateLine();
$('.form_date').datetimepicker({
	language: 'zh-CN',
	weekStart: 1,
	todayBtn: 1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 2,
	startDate: new Date(),
	forceParse: 0,
	format: 'yyyy-mm-dd'
}).on('changeDate', function(e) {
	$("#time_list").empty();
	$("#time_list").hide();
	var dateStr = dateline.getDateString(e.date);
	// console.log(dateStr);
	$("#searchDate").val(dateStr)
	setCookie("searchDate", document.getElementById("searchDate").value);
	// var myData = {
	//     "searchData": $("#searchDate").val(),
	//     "insUid": $("#insUid").val()
	// }
	// document.getElementById("searchDate").value = data.searchDate;
	changeDays();
	initdate();
	// zanshi over

});

// 日期滑块生成操作
function initdate() {
	var currdate = new Date($("#searchDate").val().replace(/-/g, "/"));
	var enddate = new Date(dateline.addDays(currdate, 7));
	var i = 0;

	while (currdate.valueOf() < enddate) {
		var week;
		var date1;
		week = dateline.weekcn[currdate.getDay()];
		date1 = dateline.getDateString(currdate).substring(5);
		var tmpDate = dateline.getDateString(currdate);
		var owli = $("#flightlistnav").find("li[class=navcols]").eq(i);
		var $a = owli.find("a");

		var div = owli.find("div");
		div.html("<em value=" + tmpDate + ">" + date1 + "</em><em>&nbsp;(" + week + ")</em>");
		if (i == 0) {
			div.attr("class", "f_l_nb");
		}

		currdate.setUTCDate(currdate.getUTCDate() + 1);
		i++;
	}
}

$("#lowlistnav").on("click", ".navcols a", function() {
	var dateStr = $(this).find("em").eq(0).attr("value");
	$("#searchDate").val(dateStr)
	$(this).find("div").addClass("f_l_nb").removeClass("f_l_na");
	$(this).parent().siblings().find("a").find("div").removeClass("f_l_nb").addClass("f_l_na")
	// var $href = $(this).attr("data-href");
	setCookie("searchDate", document.getElementById("searchDate").value);
	// 切换日期 清空orderDiv重新加载
	changeDays();
})

var date1 = getCookie("searchDate");
if (date1 == null || date1 == "") {
	date1 = new Date();
} else {
	date1 = new Date(date1);
}


var startH = 0;
var startM = 0;
var endH = 24;
var endM = 0;
date1.setHours(startH, startM)
var date2 = new Date();
date2.setHours(endH, endM);

var interval = $("#timeLineInterval").val() * 60;
$(document).ready(function(e) {
	setCookie("searchDate", "");
	initdate();
	changeDays();
});

function isOtherExist(before, after) {
	for (var i = before + 1; i < after; i++) {
		var obj = document.getElementById('timeDiv' + i);
		if (obj.className != "timeDiv timeChosen bggreen" && obj.className != "timeDiv bggrey") {
			return true;
		}
	}
	return false;
}

function getTime(insUid) {
	// var searchDateChosen = $("#searchDate").val();
	var divNum = parseInt((24 - 0) * 60 / interval);
	for (var i = 0; i < divNum; i++) {
		var obj5 = document.getElementById('timeDiv' + i);
		if (obj5.className == "timeDiv timeChosen bggreen") {
			var time = obj5.firstElementChild.innerText;
			var $span1 = $("<span>" + time + "</span>");
			var $i = $("<i class='fa fa-times r close' title='关闭'></i>");
			var $selectTime = $("<input name='selectTime' type='hidden' value='" + time + "'/>");
			var $div = $("<div></div>");
			$div.append($selectTime);
			$div.append($span1);
			$div.append($i);
			var $divNew = $("<div class='course_select sample' id='d" + i + "'></div>");
			$divNew.append($div);
			$(".public_message").find(".sample:last").after($divNew);
		}
	}
	// $.ajax({
	//     type: 'post',
	//     async: false,
	//     url: '../instrument/getBeginAndEndTime?insUid=' + insUid + '&searchDate=' + searchDateChosen,
	//     data: $('form').serialize(),
	//     success: function (data) {
	//         $("#beginAndEnd").val(data);
	//     }
	// });
}

Date.prototype.DateAdd = function(strInterval, Number) {
	var dtTmp = this;
	switch (strInterval) {
		case 's':
			return new Date(Date.parse(dtTmp) + (1000 * Number));
		case 'n':
			return new Date(Date.parse(dtTmp) + (60000 * Number));
		case 'h':
			return new Date(Date.parse(dtTmp) + (3600000 * Number));
		case 'd':
			return new Date(Date.parse(dtTmp) + (86400000 * Number));
		case 'w':
			return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));
		case 'q':
			return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number * 3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(),
				dtTmp.getSeconds());
		case 'm':
			return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(),
				dtTmp.getSeconds());
		case 'y':
			return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(),
				dtTmp.getSeconds());
	}
}

function nextWeek() {

	var time = document.getElementById("searchDate").value;
	var tmp = new Date(time).DateAdd('h', -8).DateAdd('w', 1).Format("yyyy-MM-dd");
	document.getElementById("searchDate").value = tmp;
	setCookie("searchDate", document.getElementById("searchDate").value);
	changeDays();
}

function changeDays() {
	// ajax获取该设备的机时预约记录
	$('#orderDiv').empty();
	// var date1 = getCookie("searchDate");
	// if (date1 == null || date1 == "") {
	//     date1 = new Date();
	// } else {
	//     date1 = new Date(date1);
	// }
	changeDaysSuccess([])
	$("#time_list").empty();
	$("#time_list").hide();
}

function changeDaysSuccess(jsonData) {
	data = jsonData;
	//开始小时
	var startH = 0;
	//开始分钟
	var startM = 0;
	//结束小时
	var endH = 24;
	//结束分钟
	var endM = 0;
	//开始的时间（年月日时分）
	date1.setHours(startH, startM)
	var date2 = new Date();
	//结束的时间（年月日时分）
	date2.setHours(endH, endM);
	is_table = new iStyle_TimeDiv(date1, date2, interval);
	var thisUser = document.getElementById("usernames").value;
	//把已经预约的时间块上色
	is_table.load(data, date1, thisUser);

	//总的时间块
	var divNum = parseInt((24 - 0) * 60 / interval);

	//全部被不能用
	for (var i = 0; i < divNum; i++) {
		// console.log("全部被不能用")
		var obj = document.getElementById('timeDiv' + i);
		if (obj.className == "timeDiv bggrey") {
			document.getElementById('timeDiv' + i).className = "timeDiv bgred";
		}
	}
	//获取当前时间
	var chooseDate = $("#searchDate").val();
	var sta = new Date().format("yyyy-MM-dd hh:mm");
	//这里使得本设备管理员，超级管理员，在当前时间之后，除过有人预约的模块以外，都可以预约
	for (var i = 0; i < divNum; i++) {
		var temp = new Date(chooseDate).DateAdd('h', -8).DateAdd('n', i * interval).format("yyyy-MM-dd hh:mm");
		if (CompareDate(temp, sta)) {
			var obj = document.getElementById('timeDiv' + i);
			if (obj.className == "timeDiv bgred") {
				obj.className = "timeDiv bggrey";
			}
		}
	}

	$(".timeDiv.bggrey").click(function() {
		//系统名称
		//这个flag用于点击色块完成后执行别的操作，作用 为了防止多次调用同一个方法
		//得到当前div的方位
		var thisId = parseInt($(this).attr('id').substring(7));
		//点击的点之前有没有绿，有true，没有false;
		var flagGreenFormer = false;
		for (var i = 0; i < thisId; i++) {
			var obj = document.getElementById('timeDiv' + i);
			if (obj.className == "timeDiv timeChosen bggreen") {
				flagGreenFormer = true;
			}
		}
		//点后有没有绿
		var flagGreenAfter = false;
		for (var i = thisId + 1; i < divNum; i++) {
			var obj = document.getElementById('timeDiv' + i);
			if (obj.className == "timeDiv timeChosen bggreen") {
				flagGreenAfter = true;
			}
		}
		var flag = true;
		if (true) { //之后需判冲
			//点了绿色
			if ($(this).hasClass("timeChosen")) {
				//将此之后的所有绿色变灰色
				for (var i = thisId + 1; i < divNum; i++) {
					var obj = document.getElementById('timeDiv' + i);
					if (obj.className == "timeDiv timeChosen bggreen") {
						document.getElementById('timeDiv' + i).className = "timeDiv bggrey";
					}
				}
				//绿变灰
				if (!flagGreenFormer && !flagGreenAfter) {
					document.getElementById('timeDiv' + thisId).className = "timeDiv bggrey";
				}
			}
			//不是绿色
			else {
				//两点之间变绿
				if (flagGreenFormer) {
					for (var i = 0; i < thisId; i++) {
						var obj = document.getElementById('timeDiv' + i);
						if (obj.className == "timeDiv timeChosen bggreen") {
							if (isOtherExist(i, thisId)) {
								flag = false;
								alert("不能跨时间段预约！");
								break;
							} else {
								for (var j = i + 1; j < thisId; j++)
									document.getElementById('timeDiv' + j).className = "timeDiv timeChosen bggreen";
							}
						}
					}
				}
				if (flagGreenFormer && flagGreenAfter) {
					//后面的点之后都变回来
					for (var i = thisId + 1; i < divNum; i++) {
						var obj = document.getElementById('timeDiv' + i);
						if (obj.className == "timeDiv timeChosen bggreen") {

							document.getElementById('timeDiv' + i).className = "timeDiv bggrey";
						}
					}
				}
				if (!flagGreenFormer && flagGreenAfter) {
					var count = 0;
					for (var i = 0; i < divNum; i++) {
						var obj2 = document.getElementById('timeDiv' + i);
						if (obj2.className == "timeDiv timeChosen bggreen") {
							count = i;
							break;
						}
					}
					if (isOtherExist(thisId, count)) {
						flag = false;
						alert("不能跨时间段预约！");
					} else {


						for (var i = thisId + 1; i < divNum; i++) {
							var obj = document.getElementById('timeDiv' + i);
							if (obj.className == "timeDiv timeChosen bggreen") {
								break;
							}
							if (obj.className == "timeDiv bggrey") {
								document.getElementById('timeDiv' + i).className = "timeDiv timeChosen bggreen";
							}


						}
					}

				}
				if (flagGreenFormer && flagGreenAfter) {
					for (var i = 0; i < thisId; i++) {
						var obj = document.getElementById('timeDiv' + i);
						if (obj.className == "timeDiv timeChosen bggreen") {
							document.getElementById('timeDiv' + i).className = "timeDiv bggrey";
						}

					}
				}

				if (flag) {
					$(this).removeClass("bggrey").addClass("timeChosen bggreen");
				}
			}
		}
		var counter = 0;
		for (var i = 0; i < divNum; i++) {
			var obj5 = document.getElementById('timeDiv' + i);
			if (obj5.className == "timeDiv timeChosen bggreen") {
				counter++;
			}
		}
		//后续操作
		$("#duration").html((counter * $("#timeLineInterval").val()).toFixed(2) + "小时");
		cacularCharge();
	});
}


//其他方法

//JS操作cookies方法!
//写cookies
function setCookie(name, value) {
	var Days = 30;
	var exp = new Date();
	exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
	document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}

//读取cookies
function getCookie(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}

//比较日期大小
function CompareDate(d1, d2) {
	return ((new Date(d1.replace(/-/g, "\/"))) >= (new Date(d2.replace(/-/g, "\/"))));
}

//点击色块后计算预计费用
function cacularCharge() {
	// var pUid = "";
	// pUid = $("#pUid").val();
	// var config = $("#configUid").val();
	// var times = document.getElementsByClassName("timeDiv timeChosen bggreen");
	// var begin = times[0].firstElementChild.innerText;
	// var end = times[times.length - 1].firstElementChild.innerText;
	// var searchDates = $("#searchDate").val();

	// console.log("myData点击了色快");
	$("#time_list").show();
	$("#time_list").empty();
	var divNum = parseInt((24 - 0) * 60 / interval);
	var num = 0;
	for (var i = 0; i < divNum; i++) {
		var obj5 = document.getElementById('timeDiv' + i);
		if (obj5.className == "timeDiv timeChosen bggreen") {
			num++;
			var time = obj5.firstElementChild.innerText;
			var $span1 = $("<div>" + time + "</div>");
			$("#time_list").append($span1)
		}
	}
	if(num==0){
		$("#time_list").hide();
	}
}
