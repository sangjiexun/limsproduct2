var moveInId;
var moveInState = false;
var brushs;
var pointType;
var container;
var dictionaryId;
var lineColor = "black";
function initVisualPoints(visualContainer, dictionaryID) {
    container = visualContainer;
    dictionaryId = dictionaryID;
    $("<div id='point_manager' class='point_manager'></div>").insertBefore($(container).children(":first-child"));
    $("body").append("<div id='visualMessage' style='display:none;'  class=\"popup_fix\"><div id='visualMessageSize' class=\"popup\" style='width: 80%;height: 80%;'><div id='visualMessageTitle' class=\"popup_tit\">可视化信息展示<input type=\"button\" value=\"×\"  onclick='closeVisualMessage();' title=\"关闭\"/></div><div id='visualMessageContent' class=\"popup_content1\" style='overflow:auto;height: 100%'></div></div></div>")
    getPoints();
}
function getPoints() {
    brushs = new Map();
    pointType = new Map();
    initInfoBox($("#point_manager"));
    overrideSetInfoBoxFunction(function (infoWin, obj) {
        $.ajax({
            url: visualHost + "/getNodeInfo?id=" + $(obj).attr("data") + "&type=1",
            dataType: "json",
            type: "get",
            success: function (data) {
                if (data.templetName != null) {
                    array = new Array();
                    $.each(data.dataInfoVOList, function (index, dataInfo) {
                        if (dataInfo.dataValue != "") {
                            if(dataInfo.dataType=="input"){
                                array.push(dataInfo.propertyName, dataInfo.dataValue);
                            }
                            else if(dataInfo.dataType=="resource"){
                                array.push(dataInfo.propertyName,"<a onclick='openVisualMessage("+dataInfo.dataValue+",\"resource\")'>查看</a>")
                            }else{
                                checkDataTypeInfo(array,dataInfo);
                            }
                        }
                    });
                    $("#info_content").append(formartInfos(data.templetName, array));
                }
                else {
                    array = new Array();
                    $("#info_content").append(formartInfos("无数据", array));
                }
                $("#info_content").children(":last-child").append("<div class='info_edit' onclick='editInfo(this)'>编辑</div>");
                $("#info_content").children(":last-child").append("<div style='display:none;'><div class='info_edit' onclick='saveTempletDataInfo(this)'>保存</div><div class='info_edit' style='background-color:darkred;' onclick='cancelTempletDataChange()'>取消</div></div>");
                var array = new Array();
                if (data.id != null) {
                    array.push("标记ID", "<span data='id'>" + data.id + "</span>");
                }
                if (data.name != null) {
                    array.push("标记名称", data.name);
                }
                if (data.createdBy != null) {
                    array.push("创建者", data.createdBy);
                }
                if (data.createTime != null) {
                    array.push("创建时间", data.createTime);
                }
                if (data.upTime != null) {
                    array.push("最后一次更新时间", data.upTime);
                }
                $("#info_content").append(formartInfos("基础信息", array));
                if ($(obj).attr("type") == "canvas") {
                    var headid = $(obj).attr("headPoint");
                    moveInState = true;
                    moveInId = headid;
                    if (pointType.get(headid) == 2) {
                        drawLine(brushs.get(headid), "red")
                    } else if (pointType.get(headid) == 3) {
                        drawPolygon(brushs.get(headid), "#79A1F7", "rgba(44,97,212,70%)");
                    }
                }
            }
        });
    });
     $.ajax({
            url: visualHost + "/getNodes?dictionaryId=" + dictionaryId + "&childDictionary=false",
            type: "get",
            dataType: "json",
            success: function (data) {
                $.each(data.points, function (index, value) {

                    if (value.type == "1") {
                        // point function-------------------------------------------------------------------------------
                        $("#point_manager").append("<div id='manager" + value.id + "' class='tag_container' style = 'left:" + value.xCoordinate * 100 +
                            "%;top:" + value.yCoordinate * 100 + "%;'><img data='" + value.id + "' onclick='onTagClick(this);event.stopPropagation();'  onmouseover='setShowWindowFlag(this,event,1,true,&quot;show&quot;)' onmouseout='setShowWindowFlag(this,event,-1,true,&quot;show&quot;)' src='"+value.picUrl+"' class='tag_img'/></div>")
                        // $("#manager" + value.id).css('left',value.xCoordinate * 100+'%');
                    } else {
                        $("<div  id='canvas" + value.id + "' class='canvas_div' >" +
                            "<canvas class='canvas_div' width='" + resolutionW + "' height='" + resolutionH + "'></canvas>"
                            + "</div>").insertAfter($("#point_manager"));
                        var canvas = $("#canvas" + value.id).children().get(0);
                        var brush = canvas.getContext('2d');
                        // line function---------------------------------------------------------------------------------
                        brush.beginPath();
                        $("#point_manager").append("<div id='manager" + value.id + "' class='point_manager'></div>")
                        for (var i = 0; i < value.path.length; i++) {
                            var fixedX = value.path[i].xCoordinate;
                            var fixedY = value.path[i].yCoordinate;
                            if (i == 0) {
                                brush.moveTo(fixedX * resolutionW, fixedY * resolutionH);
                            } else {
                                brush.lineTo(fixedX * resolutionW, fixedY * resolutionH);
                            }
                            $("#manager" + value.id).append("<div class='tag_container' style = 'left:" + fixedX * 100 +
                                "%;top:" + fixedY * 100 + "%;" + (value.type == '2' ? '' : 'display:none;') + "'><div type='canvas' id='check" + value.path[i].id + "' data='" + value.path[i].id + "' headPoint='" + value.id + "' onclick='onTagClick(this);event.stopPropagation();'  onmouseover='setShowWindowFlag(this,event,1,true,&quot;show&quot;)'onmousemove='event.stopPropagation();' onmouseout='setShowWindowFlag(this,event,-1,true,&quot;show&quot;)' class='tag_div'>" + i + "</div></div>");
                        }
                        if (value.type == "2") {
                            drawLine(brush, lineColor);
                            //drawPolygon(brush, "#79A1F7", "rgba(178,200,247,30%)");

                        } else if (value.type == "3") {
                            if($("#manager" + value.id).children().length<=2){
                                $("#manager" + value.id).children("div").css("display","");
                            }
                            // polygon---------------------------------------------------------------------------------------
                            brush.closePath();
                            drawPolygon(brush, "#79A1F7", "rgba(178,200,247,30%)");
                        }
                        brushs.set(value.id, brush);
                    }
                    pointType.set(value.id, value.type);
                });
            }
        });   

    var pointManager = document.getElementById("point_manager");
    pointManager.addEventListener("mousemove", function (ev) {
        if (!showInfo) {
            return;
        }
        var oEvent = ev || event;
        //console.log(oEvent.offsetX+"|"+ oEvent.offsetY);
        var fixedX = (oEvent.offsetX / pointManager.offsetWidth) * resolutionW;
        var fixedY = (oEvent.offsetY / pointManager.offsetHeight) * resolutionH;
        //var checkflag = flase;
        for (var b of brushs) {
            var k = b[0];
            var v = b[1];
            if (pointType.get(k) == 3) {
                if (v.isPointInPath(fixedX, fixedY)) {
                    if (!moveInState) {
                        moveInState = true;
                        moveInId = k;
                        if (pointType.get(k) == 2) {
                            //drawLine(v, "red")
                        } else if (pointType.get(k) == 3) {
                            drawPolygon(v, "#79A1F7", "rgba(44,97,212,70%)");
                            setShowWindowFlag($("#check" + k), event, 1, false, "show");
                            return;
                        }
                    } else if (moveInId != null && moveInId == k) {
                        return;
                    }
                    else if (moveInId != null && moveInId != k) {
                        brushs.get(moveInId).clearRect(0, 0, resolutionW, resolutionH);
                        if (pointType.get(moveInId) == 2) {
                            drawLine(brushs.get(moveInId), lineColor)
                        } else if (pointType.get(moveInId) == 3) {
                            drawPolygon(brushs.get(moveInId), "#79A1F7", "rgba(178,200,247,30%)");
                            setShowWindowFlag($("#check" + k), event, -1, false, "show");
                            return;
                        }
                        if (pointType.get(k) == 2) {
                            moveInId = k;
                            drawLine(v, "red")
                        } else if (pointType.get(k) == 3) {
                            moveInId = k;
                            drawPolygon(v, "#79A1F7", "rgba(44,97,212,70%)");
                        }

                    }
                } else if (moveInId == k && moveInState) {
                    moveInState = false;
                    v.clearRect(0, 0, resolutionW, resolutionH);
                    if (pointType.get(k) == 2) {
                        drawLine(v, lineColor)
                    } else if (pointType.get(k) == 3) {
                        drawPolygon(v, "#79A1F7", "rgba(178,200,247,30%)");
                        setShowWindowFlag($("#manager" + k).find(".headPoint"), event, -1, false, "show");
                        return;
                    }
                    moveInId = null;
                }
            }

        }


    });


}
function onTagClick(obj) {
    var getPath = false;
    if (toolType == 4) {
        if ($(obj).attr("headpoint") != null) {
            if (editingPointHead != -1 && editingPointHead != $(obj).attr("headpoint")) {
                if (!confirm("检测到您可能未保存刚刚的修改，是否确认取消刚刚的修改？")) {
                    return;
                }
            }
            if (editingPointHead == -1 || editingPointHead != $(obj).attr("headpoint")) {
                editingPoint = $(obj).attr("data");
                editingPointHead = $(obj).attr("headpoint");
                visual($("#tool_visual"));
                getPath = true;
            } else if (editingPointHead == $(obj).attr("headpoint")) {
                editingPoint = $(obj).attr("data");
            }
        } else {
            if (editingPointHead != -1 && editingPointHead != $(obj).attr("data")) {
                if (!confirm("检测到您可能未保存刚刚的修改，是否确认取消刚刚的修改？")) {
                    return;
                }
            }
            if (editingPointHead == -1 || editingPointHead != $(obj).attr("data")) {
                editingPoint = $(obj).attr("data");
                editingPointHead = editingPoint;
                visual($("#tool_visual"));
                getPath = true;
            }
        }
        if (getPath) {
            $.ajax({
                url: visualHost + "/getPathByPointId?id=" + editingPointHead,
                type: "GET",
                dataType: "json",
                async: false,
                success: function (data) {
                    pointsId = new Array();
                    pointsPosition = new Array();
                    pointsName = new Array();
                    $.each(data, function (index, value) {
                        pointsId.push(value.id);
                        pointsName.push(value.name);
                        pointsPosition.push(value.xCoordinate);
                        pointsPosition.push(value.yCoordinate);
                    });
                }
            });
        }
        var index = pointsId.lastIndexOf(parseInt(editingPoint));
        $("#editor_confirm_name").val(pointsName[index]);
        $("#editor_confirm_box").css("left", $(obj).parent().css("left")).css("top", $(obj).parent().css("top")).css("display", "block").find(".editor_cancel").attr("onclick", "cancelTool();");
        $("#editor_editor_box").css("display","block");
    }
}
function closeVisualMessage() {
    $("#visualMessage").css("display","none");
}
function openVisualMessage(data,type){
    if(type=="resource"){
        $("#visualMessageContent").empty().append("<div id='tempResource'></div>");
        getDirectoryPathInfoById({
            directoryId:data,
            type:1,
            success:function (direct) {
                initResourceContainer({
                    content:$("#tempResource"),
                    basicNames:direct.nameList,
                    cnameUrl:"../teach/resource/getCnames",
                    searchUserUrl:"../teach/resource/searchUsers"
                });
                $("#visualMessage").css("display","block");
            }
        });
    }
}