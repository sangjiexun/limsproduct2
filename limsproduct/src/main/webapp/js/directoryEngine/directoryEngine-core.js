//version:1.0.2
var directoryEngineHost;
var resourceContainerHost;
var visualHost;
var apiGateWayHost;
var getAuthorizationUrl="../shareApi/getAuthorization";
var getHostsUrl="../shareApi/getHosts";
function initDirectoryEngine(jsonData){
    if(jsonData!=null){
        if(jsonData.getAuthorizationUrl!=null){
            getAuthorizationUrl=jsonData.getAuthorizationUrl;
        }
        if(jsonData.getHostsUrl!=null){
            if(jsonData.getHostsUrl=='notNeed'){
                getHostsUrl=null;
            }else{
                getHostsUrl=jsonData.getHostsUrl;
            }
        }
    }
    if(getHostsUrl!=null){
        $.ajax({
            url:getHostsUrl,
            type:"get",
            dataType:"json",
            async:false,
            success:function (data) {
                $.each(data,function (key,value) {
                    eval(key+"='"+value+"';");
                });
            }
        });
    }
}
function getDirectoryId(dataJson) {
    if(dataJson.directoryCds!=null){
        alert("api发生更新，您使用的是旧的api")
        return;
    }
    $.ajax({
        url:directoryEngineHost+"/getDirectoryId?directoryNames="+dataJson.directoryNames+"&type="+dataJson.type,
        type:"GET",
        dataType:"text",
        async:false,
        success:function (data) {
            if(dataJson.success!=null){
                dataJson.success(data);
            }
        }
    })
}
function getDirectoryChildren(dataJson) {
    var url = directoryEngineHost+"/getDirectoryChildren?parentId="+dataJson.parentId+"&type="+dataJson.type;
    if(dataJson.currentPage!=null){
        url += "&currentPage="+dataJson.currentPage;
    }
    if(dataJson.pageSize!=null){
        url+="&pageSize="+dataJson.pageSize;
    }
    if(dataJson.sortType != null){
        url+=  "&sortType="+dataJson.sortType;
    }
    $.ajax({
        url:url,
        type:"GET",
        dataType:"json",
        success:function (data) {
            if(dataJson.success!=null){
                dataJson.success(data);
            }
        }
    })
}
function setUpTime(dataJson) {
    $.ajax({
        url:directoryEngineHost+"/setUpTime?directoryId="+dataJson.directoryId+"&type="+dataJson.type,
        type:"GET",
        dataType:"text",
        success:function (data) {
            if(dataJson.success!=null){
                dataJson.success(data);
            }
        }
    })
}
function getDirectoryPathInfoById(dataJson) {
    $.ajax({
        url:directoryEngineHost+"/getDirectoryPathInfoById?directoryId="+dataJson.directoryId+"&type="+dataJson.type,
        type:"GET",
        dataType:"json",
        success:function (data) {
            if(dataJson.success!=null){
                dataJson.success(data);
            }
        }
    })
}
function changeDirectorySort(dataJson) {
    $.ajax({
        url:directoryEngineHost+"/changeDirectorySort?directories="+dataJson.directories+"&type="+dataJson.type+"&currentPage="+dataJson.currentPage+"&pageSize="+dataJson.pageSize,
        type:"GET",
        dataType:"text",
        success:function (data) {
            if(dataJson.success!=null){
                dataJson.success(data);
            }
        }
    })
}
function newDirectory(dataJson){
    $.ajax({
        url:directoryEngineHost+"/newDirectory?directoryId="+dataJson.directoryId+"&type="+dataJson.type,
        type:"GET",
        dataType:"json",
        success:function (data) {
            if(dataJson.success!=null){
                dataJson.success(data);
            }
        }
    });
}
function renameDirectory(dataJson) {
    $.ajax({
        url:directoryEngineHost+"/renameDirectory?directoryId="+dataJson.directoryId+"&type="+dataJson.type+"&directoryName="+dataJson.directoryName,
        type:"GET",
        dataType:"json",
        success:function (data) {
            if(dataJson.success!=null){
                dataJson.success(data);
            }
        }
    });
}
function deleteDirectory(dataJson) {
    $.ajax({
        url:directoryEngineHost+"/deleteDirectory?directoryId="+dataJson.directoryId+"&type="+dataJson.type,
        type:"GET",
        dataType:"json",
        success:function (data) {
            if(dataJson.success!=null){
                dataJson.success(data);
            }
        }
    });
}
function getDirectoryIds(dataJson) {
    if(dataJson.directoryCds!=null){
        alert("api发生更新，您使用的是旧的api")
        return;
    }
    $.ajax({
        url:directoryEngineHost+"/getDirectoryIds?directoryNamesList="+dataJson.directoryNamesList+"&type="+dataJson.type,
        type:"GET",
        dataType:"text",
        async:false,
        success:function (data) {
            if(dataJson.success!=null){
                dataJson.success(data);
            }
        }
    })
}
function getAuthorization(jsonData) {
    $.ajax({
        type: "Get",
        async:(jsonData.async==null?true:jsonData.async),
        url: getAuthorizationUrl,
        success: function (data) {
            jsonData.success(data);
        }
    });
}