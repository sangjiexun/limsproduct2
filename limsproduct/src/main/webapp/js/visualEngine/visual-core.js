var showWindowFlag = 0;
var resolutionW = 1000;
var resolutionH = 1000;
var isPosintionGet = false;
var windowInfoId;
var setInfoBox;
var parentX;
var parentY;
var beginDraw = false;
var closeInfoWindow;
var visualHost;
var showInfo = true;
var currpage = 1;
var searchFunction;
var createrUser;
// var iconUrl=$("#contextPath").val()+"/images/visualEngine";
function initVisualCore(username) {
    createrUser = username;
    console.log("Welcome to use the Visualization Engine @@ Author:林志威 @@ Version:1.0.2");
}
function initInfoBox(obj) {
    $("<div id='info_div' class='info_div' onmouseover ='setShowWindowFlag(this,event,1)' onmouseout='setShowWindowFlag(this,event,-1)'><div class='setting_title'>标记信息<input type='button' value='×'onclick='closeWindow(this)' /></div ><div id='info_content' class='info_content'></div></div>").insertAfter($(obj));
}
function setShowWindowFlag(obj, ev, flag,isFix,site) {
    showWindowFlag += flag;
    if (showWindowFlag < 0) {
        showWindowFlag = 0;
    }
    setTimeout(function () {
        if (showWindowFlag > 0) {
            if ($(obj).attr("id")!=null && $(obj).attr("id").indexOf("info")==-1) {
                if ($(obj).attr("id") != windowInfoId) {
                    isPosintionGet = false;
                    if (site == "show" && windowInfoId != null) {
                        var id = $("#" + windowInfoId).attr("headPoint");
                        brushs.get(id).clearRect(0, 0, resolutionW, resolutionH);
                        if (pointType.get(id) == 2) {
                            drawLine(brushs.get(id), lineColor)
                        } else if (pointType.get(id) == 3) {
                            drawPolygon(brushs.get(id), "#79A1F7", "rgba(178,200,247,30%)");
                        }
                    }
                    windowInfoId = $(obj).attr("id");
                }
            }
            openInfoBox(obj, ev, isFix);
        } else {
            if (closeInfoWindow != null) {
                closeInfoWindow(obj);
            }
            if (windowInfoId != null && windowInfoId.indexOf("check")!=-1) {
                var id = $("#" + windowInfoId).attr("headPoint");
                brushs.get(id).clearRect(0, 0, resolutionW, resolutionH);
                if (pointType.get(id) == 2) {
                    drawLine(brushs.get(id), lineColor)
                } else if (pointType.get(id) == 3) {
                    drawPolygon(brushs.get(id), "#79A1F7", "rgba(178,200,247,30%)");
                }
                moveInId = null;
                moveInState = false;
            }
            $("#info_div").css("display", "none");
            isPosintionGet = false;
        }
    }, 50);
}
function closeWindow(obj) {
    if (closeInfoWindow != null) {
        closeInfoWindow(obj);
    }
    $(obj).parent().parent().css("display", "none");
    setTimeout(function () {
        if (windowInfoId != null && windowInfoId.indexOf("check") != -1) {
            var id = $("#" + windowInfoId).attr("headPoint");
            brushs.get(id).clearRect(0, 0, resolutionW, resolutionH);
            if (pointType.get(id) == 2) {
                drawLine(brushs.get(id), lineColor)
            } else if (pointType.get(id) == 3) {
                drawPolygon(brushs.get(id), "#79A1F7", "rgba(178,200,247,30%)");
            }
            moveInId = null;
            moveInState = false;
        }
        showWindowFlag = 0;
        isPosintionGet = false;
        windowInfoId = null;
    }, 50);
}
function openInfoBox(obj, ev, isFix) {
    if (!isPosintionGet) {
        $("#info_content").empty();
        if (setInfoBox != null) {
            setInfoBox($("#info_content"), $(obj));
        }
        var oEvent = ev || event;
        var fixY = 0;
        var fixX = 0;
        var basicX = 0;
        var basicY;
        if (isFix == null || isFix == true) {
            basicX= obj.offsetWidth + obj.parentNode.offsetLeft - 10;
            basicY = obj.parentNode.offsetTop
        } else {
           basicX = oEvent.offsetX;
           basicY = oEvent.offsetY;
        }
        var infoWinHight = document.getElementById("info_div").offsetHeight
        if (oEvent.clientY + infoWinHight > document.body.clientHeight) {
            fixY = document.body.clientHeight - (oEvent.clientY +infoWinHight);
        }
        if (document.body.offsetWidth - oEvent.clientX < 320) {
            fixX = 300 + obj.offsetWidth;
        }
        $("#info_div").css("margin-left", basicX - fixX);
        $("#info_div").css("margin-top", basicY - fixY - 20);
        isPosintionGet = true;
        $("#info_div").css("display", "block");
    }
}
function overrideSetInfoBoxFunction(func) {
    setInfoBox = func;
}
function drawLine(brush, color) {
    //brush.lineCap = "round";
    brush.lineJoin = 'round'
    brush.strokeStyle = color;
    brush.lineWidth = 4;
    brush.stroke();
}
function drawPolygon(brush, lineColor, fillColor) {
    brush.strokeStyle = lineColor;
    brush.fillStyle = fillColor;
    brush.lineJoin = 'bevel';
    brush.fill();
    brush.stroke();
}
function getVisualHost() {
    return visualHost;
}
function formartInfos(title, array) {
    var basic = "<div class='info_package'><div class='info_title'>" + title + "</div>";
    for (var i = 0; i < array.length; i += 2) {
        basic = basic + "<div class='info_line'>" + array[i] + ":" + array[i + 1] + "</div>";
    }
    basic += "</div>";
    return basic;

}
function firstPage() {
    currpage = 1;
    searchFunction();
}
function prePage() {
    if (currpage == 1) return;
    currpage = currpage - 1;
    searchFunction()
}
function nexPage() {
    var totalPages = parseInt($("#totalPages").text());
    if (currpage == totalPages) return;
    currpage = currpage + 1;
    searchFunction()
}
function lasPage() {
    currpage = parseInt($("#totalPages").text());
    searchFunction()
}
function thisPage(e) {
    if (e != null) {
        if (e != 13) {
            return
        }
    }
    var thispage = $("#goto").val();
    var totalPages = parseInt($("#totalPages").text());
    if (testThisPage(thispage, totalPages)) {
        currpage = parseInt(thispage);
        searchFunction()
    }
}
function testThisPage(thisPage, totalPages) {
    var reg = /^[0-9]*$/;
    if (!reg.test(thisPage)) {
        alert("您输入的页码不是数字！");
        return false
    }
    if (thisPage < 1) {
        alert("您输入的页码小于1");
        return false
    }
    if (thisPage > totalPages) {
        alert("您输入的页码大于总页数");
        return false
    }
    return true
}
function linitFoot() {
    $("#foot").append("<div class=\"page\"><div class=\"page_info\"><span id='totalRecords'></span>条记录 • 共<span id='totalPages'></span>页</div><a class=\"page_btn\" onclick='firstPage()'>首页</a><a class=\"page_btn\"onclick='prePage()'>上一页</a><div class=\"page_select\"><span>第</span><input id='goto' type=\"text\" onkeypress='thisPage(event.keyCode||event.which)'/><span>页</span></div><a class=\"page_btn\" onclick='thisPage()'>跳转</a><a class=\"page_btn\" onclick='nexPage()'>下一页</a><a class=\"page_btn\" onclick='lasPage()'>末页</a></div>")

}
function getBackgroundByDirectoryId(dataJson) {
    getAuthorization({
        success:function (authorization) {
            $.ajax({
                url:visualHost+"/getBackgroundByDirectoryId?directId="+dataJson.directId,
                type: "GET",
                dataType: "json",
                headers:{
                    Authorization:authorization
                },
                success:function (data) {
                    if(dataJson.success!=null){
                        dataJson.success(data);
                    }
                }
            })
        }
    })
}