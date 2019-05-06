/**
 * Created by vickers on 2018/11/15.
 */
function checkDataTypeInfo(array,dataInfo) {
    // var array = new Array();
    // $.each(data.dataInfoVOList, function (index, dataInfo) {
    // //检查数据类型
    // if (dataInfo.dataValue != "") {
    //     // array.push(checkDataType(dataInfo));
    //     if(dataInfo.dataType=="input"){
    //         array.push(dataInfo.propertyName, dataInfo.dataValue);
    //     }
    //     else if(dataInfo.dataType=="resource"){
    //         array.push(dataInfo.propertyName,"<a onclick='openVisualMessage("+dataInfo.dataValue+",\"resource\")'>查看</a>")
    //     }
    //     else
            if(dataInfo.dataType=="buildData" || dataInfo.dataType=="roomData" || dataInfo.dataType=="deviceData"){
                console.log(dataInfo);
                $.ajax({
                    url: $("#contextPath").val() + "/getInstallationNameById?id=" + dataInfo.dataValue + "&type=" + dataInfo.dataType,
                    type: "GET",
                    dataType: "text",
                    async:false,
                    success: function (data) {
                        array.push(dataInfo.propertyName, data);
                        console.log(array)
                    }
                });

            }
    // }
    // });
    return array;
}
function checkDataTypeEdit(property,obj) {
    // $.each(templet.properties, function (index, property) {
    //     if (property.dataType == "input") {
    //         $("<div class='info_line' data='" + property.id + "'>" + property.dataName + ":<input class='theData'/></div>").insertBefore($(obj));
    //     }else if(property.dataType=="resource"){
    //         $("<div class='info_line' data='" + property.id + "'>" + property.dataName + ":<input type='button' onclick='getDirectId(this,\"resource\")' value='设置'/><input type='hidden' class='theData' value='"+dictionaryId+"'/></div>").insertBefore($(obj));
    //     }else if(property.dataType=="resourceFile"){
    //         $("<div class='info_line' data='" + property.id + "'>" + property.dataName + ":<input type='button' onclick='getDirectId(this,\"resourceFile\")' value='设置'/><input type='hidden' class='theData' value=''/></div>").insertBefore($(obj));
    //     }else if(property.dataType=="url"){
    //         $("<div class='info_line' data='" + property.id + "'>" + property.dataName + ":<br/>链接名:<input /><br/>链接地址:<input /><input type='hidden' class='theData'/></div>").insertBefore($(obj));
    //     }else
    if(property.dataType=="buildData" || property.dataType=="roomData" || property.dataType=="deviceData"){
        $("<div class='info_line' data='" + property.id + "'>" + property.dataName + ":<select class='theData' id='dynamic_data_select_"+ property.id +"'><option value='-1' selected='selected'>请选择</option></select></div>").insertBefore($(obj));
        var directoryName = $('#directoryName').val()
        var rt= /(.+)?(?:\(|（)(.+)(?=\)|）)/.exec(directoryName);
        var arr=directoryName.split("/");
        console.log(rt[2]);
        console.log(property.dataType);
        $.ajax({
            url: $("#contextPath").val() + "/getDynamicDataById?id=" + rt[2] + "&type=" + property.dataType,
            type: "Get",
            dataType: "json",
            success: function (data) {
                console.log(data);
                $.each(data, function (i) {
                    $("#dynamic_data_select_"+ property.id).append("<option value='" + data[i].id +    "'>" + data[i].id + "/" + data[i].name + "</option>");
                });
            }
        });
        // var data = [
        //     {'id':'111','name':'第一个'},
        //     {'id':'2','name':'第二个'},
        // ]

    }
    // });
}

function optionCheck(data) {
    $.each(data[0], function (id, name) {
        var type = $('#visual_type').val();
        if(name.indexOf('教学平台')== -1){
            if(type=='campus'){
                if(name!='房间信息'&&name!='设备信息'){
                    $("#templetSelecter").append("<option value='" + id + "'>" + name + "</option>");
                }
            }else if(type=='build'){
                if(name!='楼宇信息'&&name!='设备信息'){
                    $("#templetSelecter").append("<option value='" + id + "'>" + name + "</option>");
                }
            }else if(type=='room'){
                if(name!='楼宇信息'&&name!='房间信息'){
                    $("#templetSelecter").append("<option value='" + id + "'>" + name + "</option>");
                }
            }
        }
    });
}