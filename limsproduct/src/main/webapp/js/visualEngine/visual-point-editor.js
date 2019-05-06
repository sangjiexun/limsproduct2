var toolType;
var pointsId;
var pointsPosition;
var pointsName;
var test;
var layerTime = 0;
var editingPoint = -1;
var editingPointHead = -1;
var hasMorePointsImg = false;
function initPointImageUrls(urls) {
    for(var i = 0;i<urls.length;i++){
        initPointImageUrl(urls[i]);
    }
}
function initPointImageUrl(url) {
    hasMorePointsImg = true;
    if($("#editor_tool_points").children(":last-child").children().length == 4){
        $("#editor_tool_points").append("<div class='editor_tool_point_line'></div>");
    }
    $("#editor_tool_points").children(":last-child").append("<div class='editor_tool_point'  onclick='changePoint(this)'><img src='"+url+"'></div>");
}
function initDictionaryEdit() {
    $("<div id='visual_point_editor_layer' class='editor_Layer' onclick='showLayers(this)'>图层选择</div>").insertBefore($("#point_manager"));
    $("<div id='visual_point_editor' class='editor_Button' onclick='showTools(this)'>编辑</div>").insertBefore($("#point_manager"));
    $("<div id='editor_tool' class='editor_tool'><div id='tool1' class='tool_unit' onclick='setPoint(this,1)'><img class='tool_img' src='"+iconUrl+"/editor_point.png'/></div><div id='tool2' class='tool_unit' onclick='setPoint(this,2)'><img class='tool_img' src='"+iconUrl+"/editor_line.png'/></div><div id='tool3' class='tool_unit'  onclick='setPoint(this,3)'><img class='tool_img' src='"+iconUrl+"/editor_polygon.png'/></div><div id='tool4' onclick='setPoint(this,4)' class='tool_unit'><img class='tool_img' src='"+iconUrl+"/editor_select.png'/></div></div>").insertAfter($("#visual_point_editor"));
    $("<div id='editor_tool_line' class='editor_tool_line'><div id='tool_undo' class='tool_unit_line'onclick='undo();'><img class='tool_img_line' src='"+iconUrl+"/editor_undo.png'/></div><div id='tool_visual' class='tool_unit_line'onclick='visual(this);'><img class='tool_img_line' src='"+iconUrl+"/editor_visual.png'/></div><div id='tool_accept' class='tool_unit_line'onclick='sendPointsInfo();'><img class='tool_img_line' src='"+iconUrl+"/editor_accept.png'/></div><div id='tool_undo' class='tool_unit_line'onclick='cancelTool();'><img class='tool_img_line' src='"+iconUrl+"/editor_cancel.png'/></div></div>").insertAfter($("#visual_point_editor"));
    $("<div id='editor_tool_points' class='editor_tool_points'><div class='editor_tool_point_line'></div></div>").insertAfter($("#visual_point_editor"));
    $("<div id='editor_confirm_box' class='editor_confirm_box'><div class='confirm_input'><span>标记名:</span><input id='editor_confirm_name' /></div><div class='editor_confirm'onclick='confirmPoint();event.stopPropagation();'>确定</div><div class='editor_cancel' onclick=''>取消</div><div class='editor_delete' onclick='deletePoint()'>删除</div><div class='editor_add'>预留</div><div class='editor_delete_all' onclick='deleteAll()'>删除实体</div></div>)").insertAfter($("#editor_tool"));
    var pointManager = document.getElementById("point_manager");
    pointManager.addEventListener("click", function (ev) {
        var oEvent = ev || event;
        if (toolType != null) {
            var fixedXPercent = (oEvent.offsetX / pointManager.offsetWidth).toFixed(4);
            var fixedYPercent = (oEvent.offsetY / pointManager.offsetHeight).toFixed(4);
            if (toolType == 1 || toolType == 2 || toolType == 3) {
                pointsPosition[pointsPosition.length - 2] = fixedXPercent;
                pointsPosition[pointsPosition.length - 1] = fixedYPercent;
            }
            if (toolType == 1) {
                $("#managerNew").css("left", fixedXPercent * 100 + "%").css("top", fixedYPercent * 100 + "%").css("display", "block");
                $("#editor_confirm_box").css("display", "block").css("left", fixedXPercent * 100 + "%").css("top", fixedYPercent * 100 + "%").css("display", "block");
            } else if (toolType == 2) {
                $($("#managerNew").children().get(name.length - 1)).css("left", fixedXPercent * 100 + "%").css("top", fixedYPercent * 100 + "%").css("display","");
                var brush = linkPath(pointsPosition);
                brush.clearRect(0, 0, resolutionW, resolutionH);
                drawLine(brush, lineColor);
                $("#editor_confirm_box").css("display", "block").css("left", fixedXPercent * 100 + "%").css("top", fixedYPercent * 100 + "%").css("display", "block");
            } else if (toolType == 3) {
                $($("#managerNew").children().get(name.length - 1)).css("left", fixedXPercent * 100 + "%").css("top", fixedYPercent * 100 + "%").css("display","");
                var brush = linkPath(pointsPosition);
                brush.closePath();
                brush.clearRect(0, 0, resolutionW, resolutionH);
                drawPolygon(brush, "#79A1F7", "rgba(178,200,247,30%)");
                confirmPoint();
            } else if (toolType == 4) {
                if (moveInState&&pointType.get(windowInfoId.replace("check",""))=="3") {
                    if (editingPoint != -1 && editingPointHead != windowInfoId.replace("check", "")) {
                        if (!confirm("检测到您可能未保存刚刚的修改，是否确认取消刚刚的修改？")) {
                            return;
                        }
                    } 
                    $.each($("#manager" + windowInfoId.replace("check", "")).children(), function (index, child) {
                         $(child).css("display", "");
                    });
                    closeWindow($("#info_div").find(".setting_title input"));
                    onTagClick($("#" + windowInfoId));
                }else if (editingPoint != -1) {
                    var index = pointsId.lastIndexOf(parseInt(editingPoint));
                    if (pointType.get(editingPointHead) == 1) {
                        $($("#manager" + editingPointHead)).css("left", fixedXPercent * 100 + "%").css("top", fixedYPercent * 100 + "%")
                    } else {
                        $($("#manager" + editingPointHead).children().get(index)).css("left", fixedXPercent * 100 + "%").css("top", fixedYPercent * 100 + "%")

                    }
                        $("#editor_confirm_box").css("left", fixedXPercent * 100 + "%").css("top", fixedYPercent * 100 + "%")
                        pointsPosition[index * 2] = fixedXPercent;
                        pointsPosition[index * 2 + 1] = fixedYPercent;
                        var brush = linkPath(pointsPosition, $("#canvas" + editingPointHead).children().get(0));
                        brushs.set(editingPointHead, brush);
                        brush.clearRect(0, 0, resolutionW, resolutionH);
                        if (pointType.get(editingPointHead) == 2) {
                            drawLine(brush, lineColor);
                        }
                        if (pointType.get(editingPointHead) == 3) {
                        brush.closePath();
                        drawPolygon(brush, "#79A1F7", "rgba(178,200,247,30%)");
                    }
                }
            }
        }

    });

    pointManager.addEventListener("dblclick", function (ev) {
        setPoint(test,1)
        var oEvent = ev || event;
        var fixedXPercent = (oEvent.offsetX / pointManager.offsetWidth).toFixed(4);
        var fixedYPercent = (oEvent.offsetY / pointManager.offsetHeight).toFixed(4);
        pointsPosition[pointsPosition.length - 2] = fixedXPercent;
        pointsPosition[pointsPosition.length - 1] = fixedYPercent;
        $("#managerNew").css("left", fixedXPercent * 100 + "%").css("top", fixedYPercent * 100 + "%").css("display", "block");
        $("#editor_confirm_box").css("display", "block").css("left", fixedXPercent * 100 + "%").css("top", fixedYPercent * 100 + "%").css("display", "block");
    });
}
function confirmPoint() {
    if (toolType != 4) {
        pointsName[pointsName.length - 1] = $("#editor_confirm_name").val();
        if (toolType != 1) {
            //预留
            pointsPosition.push(-1);
            pointsPosition.push(-1);
            $("#editor_confirm_box").css("display", "none");
            $("#editor_confirm_name").val("");
            $("#managerNew").append("<div class='tag_container' style='display:none;'><div type='canvas'onclick='event.stopPropagation();' class='tag_div'>" + pointsName.length + "</div></div>");
            if (toolType == 3 && pointsName.length == 3) {
                $("#managerNew").css("display", "none");
            }
            pointsName.push("");
        } else {
            sendPointsInfo(false);
        }
    } else {
        var index = pointsId.lastIndexOf(parseInt(editingPoint));
        pointsName[index] = $("#editor_confirm_name").val();
        $("#editor_confirm_name").val("");
        $("#editor_confirm_box").css("display", "none");
        editingPoint = -1;
    }
   
}
function sendPointsInfo(flag) {
    if (flag) {
        confirmPoint();
    }
    if (pointsPosition == null || toolType == null) {
        return;
    }
    if (toolType != 1 && toolType != 4) {
        if (pointsPosition[pointsPosition.length - 1] != -1) {
            confirmPoint();
        }
        pointsPosition.pop();
        pointsPosition.pop();
        pointsName.pop();
    }
    var fd = new FormData();
    if(toolType ==1){
        fd.append("picUrl",$("#managerNew").children("img").attr("src"));
    }
    fd.append("ids", pointsId);
    fd.append("points", pointsPosition);
    fd.append("createdBy", createrUser);
    fd.append("name", pointsName);
    if (toolType > 3) {
        fd.append("type", pointType.get(editingPointHead));
    } else {
        fd.append("type", toolType);
    }
    fd.append("dictionaryId", dictionaryId);
    var url = visualHost + "/savePoint";
    $.ajax({
        url: url,
        type: "POST",
        data: fd,
        dataType: "json",
        contentType: false,
        processData: false,
        success: function (data) {
            if(toolType !=4){
                if(toolType!=1){
                    $.each($("#managerNew").children(),function (index,child) {
                        $(child).children().attr("data",data[index]).attr("id","check"+data[index]).attr("headpoint",data[0]).
                        attr("onclick","onTagClick(this);event.stopPropagation();").attr("onmouseover","setShowWindowFlag(this,event,1,true,\"show\")").
                        attr("onmouseout","setShowWindowFlag(this,event,-1,true,\"show\")");
                    });
                    $("#canvasNew").attr("id","canvas"+data[0]);
                    var brush = linkPath(pointsPosition, $("#canvas"+data[0]).children().get(0));
                    if(toolType==3){
                        brush.closePath();
                    }
                    brushs.set(data[0],brush);
                }else{
                    $("#managerNew").children().attr("data",data[0]).
                    attr("onclick","onTagClick(this);event.stopPropagation();").attr("onmouseover","setShowWindowFlag(this,event,1,true,\"show\")").
                    attr("onmouseout","setShowWindowFlag(this,event,-1,true,\"show\")");
                }
                $("#managerNew").attr("id","manager"+data[0]);
                pointType.set(data[0],toolType);
                cancelTool(true);
            }else{
                location.reload();
            }

        }
    });
}
function showTools(obj) {
    $(obj).next().next().animate({ width: 'toggle' }, 350).next().slideToggle();
    if(hasMorePointsImg){
        $(obj).next().animate({ width: 'toggle' }, 350);
    }
}
function showLayers(obj) {
    if(layerTime==0){
        $("<div id='visual_layer' class='editor_layer'>" +
            "<div id='layer1' class='tool_unit'><p class='layer_p'>资源地图</p></div>" +
            "<div id='layer2' class='tool_unit'><p class='layer_p'>绩效地图</p></div>" +
            "<div id='layer3' class='tool_unit'><p class='layer_p'>知识地图</p></div>" +
            "<div id='layer4' class='tool_unit'><p class='layer_p'>安全地图</p></div>" +
            "</div>").insertAfter($("#visual_point_editor_layer"));
    }
    // $("<div id='visual_layer' class='editor_layer'><div id='layer1' class='tool_unit'><p>资源地图</p></div></div>").insertAfter($("#visual_point_editor_layer"));
    layerTime=1;
    $(obj).next().slideToggle();
    // $("#visual_point_editor_layer").append("<div id='visual_layer' class='editor_tool'><div id='layer1' class='tool_unit'><p>资源地图</p></div></div>");
    // $.ajax({
    //     url: visualHost + "/getLayers?dictionaryId=" + dictionaryId,
    //     dataType: "json",
    //     type: "get",
    //     success: function (data) {
    //
    //     }
    // })
}
function cancelTool(saved) {
    if(saved == null || !saved){
        if (!confirm("您刚刚的操作未保存，是否确认放弃所有更改！")) {
            return;
        }
        if (toolType == 4) {
            location.reload();
        }
    }
    if (toolType != null) {
        $("#tool" + toolType).css("box-shadow", "");
        $("#canvasNew").remove();
        $("#managerNew").remove();  
        $("#editor_confirm_box").css("display", "none")
    }
    toolType = null;
    if ($("#tool_visual").css("box-shadow") != "none") {
        visual($("#tool_visual"));
    }
    editingPointHead = -1;
    editingPoint = -1;
}
function setPoint(obj, flag) {
    if (toolType == flag) {
        $(obj).css("box-shadow","");
        $("#canvasNew").remove();
        $("#managerNew").remove();
        $("#editor_confirm_box").css("display", "none")
        if ($("#tool_visual").css("box-shadow") != "none") {
            visual($("#tool_visual"));
        }
        if (toolType == 4) {
            location.reload();
        }
        toolType = null;
    } else{
        if (toolType != null) {
            $("#tool" + toolType).css("box-shadow", "");
            $("#canvasNew").remove();
            $("#managerNew").remove();
            $("#editor_confirm_box").css("display", "none");
        }
        if (flag == 4) {
          
        } else {
            pointsId = new Array();
            if (flag != 1) {
                $(container).append("<div  id='canvasNew' class='canvas_div' >" +
                    "<canvas class='canvas_div' width='" + resolutionW + "' height='" + resolutionH + "'></canvas>"
                    + "</div>")
                $("#point_manager").append("<div id='managerNew' class='point_manager'><div class='tag_container' style='display: none;'><div type='canvas'onclick='event.stopPropagation();' class='tag_div'>0</div></div></div>");
                $("#editor_confirm_box").find(".editor_cancel").attr("onclick", "undo();event.stopPropagation();");
            } else {
                $("#point_manager").append("<div onclick='event.stopPropagation();' style='display:none' id='managerNew' class='tag_container'><img src='"+iconUrl+"/point.png' class='tag_img'></div>");
                $("#editor_confirm_box").find(".editor_cancel").attr("onclick", "cancelTool();event.stopPropagation();");
            }
            pointsPosition = new Array();
            pointsPosition.push(-1);
            pointsPosition.push(-1);
            pointsName = new Array();
            pointsName.push("");
        }
        $(obj).css("box-shadow", "inset 0 0 5px 5px rgba(134, 251, 215, 0.70)");
        toolType = flag;
    }
    
}
function linkPath(array, canvas) {
    var brush;
    if (canvas == null) {
        brush = $("#canvasNew").children().get(0).getContext('2d');
    } else {
        brush = canvas.getContext('2d');
    }
    brush.beginPath();
    for (var i = 0; i < array.length; i += 2) {
        var fixedX = array[i] * resolutionW;
        var fixedY = array[i+1] * resolutionH;
        if (i == 0) {
            brush.moveTo(fixedX, fixedY);
        } else {
            brush.lineTo(fixedX, fixedY);
        }
       
    }
    return brush;
}
function undo() {
    if (toolType == 4) {
        alert("选择工具暂时不能撤销");
        return;
    }
    if (pointsPosition == null || toolType==null || pointsPosition.length == 2) {
        return;
    }
    var flag = true;
    if (pointsPosition[pointsPosition.length - 1] != -1) {
        flag = false;
    }
    pointsPosition.pop();
    pointsPosition.pop();
    pointsName.pop();
    $("#managerNew").children(":last-child").remove();
    if (toolType == 2) {
        var brush = linkPath(pointsPosition);
        brush.clearRect(0, 0, resolutionW, resolutionH);
        drawLine(brush, lineColor);
    } else {
        pointsPosition.pop();
        pointsPosition.pop();
        pointsName.pop();
        $("#managerNew").children(":last-child").remove();
        var brush = linkPath(pointsPosition);
        brush.clearRect(0, 0, resolutionW, resolutionH);
        brush.closePath();
        drawPolygon(brush, "#79A1F7", "rgba(178,200,247,30%)");
        flag = false;
        if (pointsPosition.length <= 4) {
            $("#managerNew").css("display", "block");
        }
    }
    if (!flag) {
        confirmPoint();
    } else {
        $("#editor_confirm_box").css("left", $("#managerNew").children(":last-child").css("left")).css("top", $("#managerNew").children(":last-child").css("top"));
        $("#editor_confirm_box").css("display", "block");
    }


}
function visual(btn) {
    if ($(btn).css("box-shadow") == "none") {
        $(btn).css("box-shadow", "inset 0 0 5px 5px rgba(110, 180, 250,0.70)");
        $.each($("#point_manager").children(), function (index, obj) {
            if ($(obj).attr("id") != "managerNew" && $(obj).attr("id") != "manager" + editingPointHead) {
                $(obj).css("display", "none");
            }
        })
        $.each($(container).children(), function (index, obj) {
            if ($(obj).attr("id")!=null && $(obj).attr("id").indexOf("canvas") != -1 && $(obj).attr("id") != "canvasNew" && $(obj).attr("id") != "canvas" + editingPointHead) {
                $(obj).css("display", "none");
            }
        })
        showInfo = false;
    } else {
        $(btn).css("box-shadow", "none");
        $.each($("#point_manager").children(), function (index, obj) {
            $(obj).css("display", "");
        })
        $.each($(container).children(), function (index, obj) {
            if ($(obj).attr("id")!=null && $(obj).attr("id").indexOf("canvas") != -1) {
                $(obj).css("display", "");
            }
        })
        showInfo = true;
    }
    
}
function deletePoint() {
    if (toolType != 4) {
        undo();
    } else {
        var index = pointsId.lastIndexOf(parseInt(editingPoint));
        $($("#manager" + editingPointHead).children().get(index)).remove();
        $("#editor_confirm_box").css("display", "none");
        for (var i = index; i < pointsId.length; i++) {
            $($("#manager" + editingPointHead).children().get(i)).children(":first-child").text(i);
        }
        pointsPosition.splice(index * 2, 2);
        if (pointsId.length > 1) {
            pointsId.splice(index, 1);
        }
        pointsName.splice(index, 1);
        if(pointType.get(editingPoint)!=1){
            var brush = linkPath(pointsPosition, $("#canvas" + editingPointHead).children().get(0));
            brushs.set(editingPointHead, brush);
            brush.clearRect(0, 0, resolutionW, resolutionH);
        }
        if ($("#manager" + editingPointHead).children().length > 0) {
            windowInfoId = $($("#manager" + editingPointHead).children().get(i)).children(":first-child").attr("id");
        }
        if (pointType.get(editingPointHead) == 2) {
            drawLine(brush, lineColor);
        } else if (pointType.get(editingPointHead) == 3) {
            drawPolygon(brush, "#79A1F7", "rgba(178,200,247,30%)");
        }
    }
}
function editInfo(obj) {
    showWindowFlag = 2;
    $.ajax({
        url: visualHost + "/getTempletAndDataInfo?pointId=" + $(obj).parent().parent().find("span[data='id']").text(),
        type: "Get",
        dataType: "json",
        success: function (data) {
            $(obj).css("display", "none").next().css("display", "block").parent().children(".info_line").remove();
            $("<div>选择模版:<select id='templetSelecter'onchange='changeTemplet(this)' ></select></div>").insertAfter($(obj).parent().children(":first-child"));
            // $.each(data[0], function (id, name) {
            //     $("#templetSelecter").append("<option value='" + id + "'>" + name + "</option>");
            // });
            optionCheck(data);
            $("#templetSelecter").val(data[1].id);
            setInfoInput(data[1], $(obj));
            getDataInfoList($("#templetSelecter"), $(obj).parent().parent().find("span[data='id']").text())
        }
    });

}
function setInfoInput(templet, obj) {
    $("<div class='info_line templet_info'>标记可<span style='color:red;'>显示</span>最低权限:" + templet.authority + "<br/>标记可<span style='color:red;'>编辑</span>最低权限:" + templet.editAuthority + "</div>").insertBefore($(obj));
    $.each(templet.properties, function (index, property) {
        if (property.dataType == "input") {
            $("<div class='info_line' data='" + property.id + "'>" + property.dataName + ":<input class='theData'/></div>").insertBefore($(obj));
        }else if(property.dataType=="resource"){
            $("<div class='info_line' data='" + property.id + "'>" + property.dataName + ":<input type='button' onclick='getDirectId(this,\"resource\")' value='设置'/><input type='hidden' class='theData' value='"+dictionaryId+"'/></div>").insertBefore($(obj));
        }else if(property.dataType=="resourceFile"){
            $("<div class='info_line' data='" + property.id + "'>" + property.dataName + ":<input type='button' onclick='getDirectId(this,\"resourceFile\")' value='设置'/><input type='hidden' class='theData' value=''/></div>").insertBefore($(obj));
        }else if(property.dataType=="url"){
            $("<div class='info_line' data='" + property.id + "'>" + property.dataName + ":<br/>链接名:<input /><br/>链接地址:<input /><input type='hidden' class='theData'/></div>").insertBefore($(obj));
        }else{
            checkDataTypeEdit(property,obj);
        }
    });

}
function changeTemplet(obj) {
    $.ajax({
        url: visualHost + "/getTemplet?id=" + $(obj).val(),
        type: "Get",
        dataType: "json",
        success: function (data) {
            $(obj).parent().parent().children(".info_line").remove();
            $(obj).parent().parent().children(".info_title").text(data.name);
            setInfoInput(data, $(obj).parent().next());
            getDataInfoList($(obj), $(obj).parent().parent().parent().find("span[data='id']").text())
        }
    });
}
function getDataInfoList(obj, pointId) {
    $.ajax({
        url: visualHost + "/getDataInfoList?&pointId=" + pointId,
        type: "Get",
        dataType: "json",
        success: function (data) {
            $.each(data, function (index, value) {
                $(obj).parent().parent().children("div[data='" + index + "']").children(".theData").val(value);
            });
        }
    });
}
function cancelTempletDataChange() {
    $("#info_content").empty();
    if (setInfoBox != null) {
        closeWindow($("#info_div").children(".setting_title").children(":first-child"));
    }
}
function saveTempletDataInfo(obj) {
    var fd = new FormData()
    fd.append("pointId",$(obj).parent().parent().parent().find("span[data='id']").text());
    fd.append("templetId",$("#templetSelecter").val());
    fd.append("createdBy", createrUser);
    var properties = new Array();
    var dataValue = new Array();
    $.each($(obj).parent().parent().children(".info_line"), function (index, obj) {
        if ($(obj).attr("data") != null) {
            properties.push($(obj).attr("data"))
            if ($(obj).children(".theData").val() != null) {
                dataValue.push($(obj).children(".theData").val());
            } else {
                dataValue.push("");
            }
        }
    });
    fd.append("dataValue", dataValue);
    fd.append("propertyId", properties);
    $.ajax({
        url: visualHost + "/saveDataInfo",
        type: "POST",
        dataType: "text",
        data: fd,
        contentType: false,
        processData: false,
        success: function (data) {
            cancelTempletDataChange();
        }
    });
}
function changePoint(obj) {
    if(toolType ==1){
        $("#managerNew").children("img").attr("src",$(obj).children("img").attr("src"));
    }
}
function deleteAll(){
    if(pointType.get(editingPointHead)!= 1){
        pointsPosition = new Array();
        pointsName = new Array();
        for(var i =0;i<pointsId.length;i++){
            if(pointsId[i] !=-1){
                var j = pointsId[i];
                pointsId = new Array();
                pointsId.push(j);
            }
        }
        $("#canvas"+editingPointHead).remove();
        $("#manager"+editingPointHead).remove();
    }else{
        deletePoint();
    }
    sendPointsInfo();
}
function getDirectId(obj,rType){
    $("#visualMessageContent").attr("data",$(obj).parent().attr("data"));
    var diID=dictionaryId;
    if($(obj).parent().children(".theData").val()!=null && $(obj).parent().children(".theData").val()!=""){
        diID =$(obj).parent().children(".theData").val();
    }
    getDirectoryPathInfoById({
        directoryId:diID,
        type:1,
        success:function (direct) {
            $("#visualMessageContent").empty();
            $("#visualMessageContent").append("<div class='selectors' style='display: inline-block;margin-right: 10px;'></div>")
            $("#visualMessageContent").append("<input type=\"button\" onclick='confirmDirectSelect(\""+rType+"\");' style='margin-right: 10px;' value=\"确定\"/><input type=\"button\" onclick='closeVisualMessage();' value=\"取消\"/><br/>")
            var names = direct.nameList.split("/");
            var dids = direct.idList.split("/");
            for(var i = 0;i<names.length;i++){
                if(i==0){
                    $("#visualMessageContent").children(".selectors").append("<input type='hidden' value='"+dids[0]+"'/>");
                }else{
                    $("#visualMessageContent").children(".selectors").append("<select onchange='onResourceOptionsSelect(this,\""+rType+"\")'></select><input type='hidden' value='"+dids[i]+"'/>");
                    getResourceOptions($("#visualMessageContent").children(".selectors").children(":last-child").prev());
                }
            }
            $("#visualMessageContent").children(".selectors").append("<select onchange='onResourceOptionsSelect(this,\""+rType+"\")'></select><input type='hidden' value='noSelect'/>");
            getResourceOptions($("#visualMessageContent").children(".selectors").children(":last-child").prev());
            $("#visualMessageContent").append("<div id='selectedTable' class='resource_file_select_table'>" +
                "<table>" +
                "<thead>" +
                "<th width='40%'>文件名</th><th width='40%'>文件详情</th><th width='20%'>操作</th>" +
                "</thead><tbody></tbody>" +
                "</table>" +
                "</div>" +
                "<div id='allFilesTable' class='resource_file_select_table'>" +
                "<table>" +
                "<thead>" +
                "<th width='40%'>文件名</th><th width='40%'>文件详情</th><th width='20%'>操作</th>" +
                "</thead><tbody></tbody>" +
                "</table>" +
                "</div>");
            setResourceFileTable();
            $("#visualMessage").css("display","block");
        }
    });
}
function setResourceFileTable() {
    var table = $("#visualMessageContent").find("#allFilesTable").find("tbody");
    $(table).empty();
    getFilesByDirectoryID(null,$("#visualMessageContent").children(".selectors").children(":last-child").prev().prev().val(),function (data) {
        var array=new Array();
        $.each(data,function (index,file) {
            array.push(file.fileCreater);
        });
        getCname(array);
        $.each(data,function (index,file) {
            $(table).append("<tr data='"+file.id+"'><td><a src='"+file.url+"?filename="+subFileName(file.fileName)+"'>"+file.fileName+"</a></td><td>于"+file.upTime+"由"+cnames[index]+"上传</td><td><input type='button' value='添加'/></td></tr>")
        })
    })
}
function getResourceOptions(obj) {
    getDirectoryChildren({
        parentId:$(obj).prev().val(),
        type:1,
        success:function (children) {
            $(obj).append("<option value='noSelect'>请选择</option>");
            $.each(children.directorys,function (index,child) {
                $(obj).append("<option value="+child.directoryId+">"+child.directoryName+"</option>");
            });
            $(obj).val($(obj).next().val());
        }
    });
}
function onResourceOptionsSelect(obj,type){
    $(obj).next().val($(obj).val());
    while($(obj).next().next().length>0){
        $(obj).next().next().remove();
    }
    if($(obj).val() != "noSelect"){
        $("#visualMessageContent").children(".selectors").append("<select onchange='onResourceOptionsSelect(this)'></select><input type='hidden' value='noSelect'/>");
        getResourceOptions($("#visualMessageContent").children(".selectors").children(":last-child").prev());
    }
    if(type=="resourceFile"){
        setResourceFileTable();
    }
}
function confirmDirectSelect(type) {
    if(type=="resource"){
        $(".info_line[data='"+$("#visualMessageContent").attr("data")+"']").children(".theData").val($("#visualMessageContent").children(".selectors").children(":last-child").prev().prev().val());
        closeVisualMessage();
    }else if(type=="resourceFile"){

    }
}

