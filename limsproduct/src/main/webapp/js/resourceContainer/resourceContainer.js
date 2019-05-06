var siteName;
var beforeUpLoad;
var afterUpLoad;
var beforeMkdir;
var afterMkdir;
var beforeMkfiletabel;
var afterMkfiletabel;
var sortType;
var getCnameUrl;
var cnames;
var fileBoxCurrentPage = 1;
var fileBoxPageSize = 6;
var selectFileid;
var allowUload;
var beforeNewDirectory;
var afterNewDirectory;
var searchUsernameUrl;
var dirTool = false;
var beforeRenameDir;
var afterRenameDir;
var afterDeletDir;
$(function() {
    console.log("Welcome to use the ResourceContainer plug-in @@ Author:林志威 @@ Version:1.5.1")
});
function initResourceContainer(initJson) {
    $(initJson.content).addClass("line_sum");
    if (initJson.cnameUrl != null) {
        getCnameUrl = initJson.cnameUrl
    }
    if (initJson.searchUserUrl != null) {
        searchUsernameUrl = initJson.searchUserUrl
    }
    if (initJson.beforeMkdir != null) {
        beforeMkdir = initJson.beforeMkdir
    }
    if (initJson.afterMkdir != null) {
        afterMkdir = initJson.afterMkdir
    }
    if (initJson.beforeMkfiletabel != null) {
        beforeMkfiletabel = initJson.beforeMkfiletabel
    }
    if (initJson.beforeNewDirectory != null) {
        beforeNewDirectory = initJson.beforeNewDirectory
    }
    if (initJson.afterMkfiletabel != null) {
        afterMkfiletabel = initJson.afterMkfiletabel
    }
    if (initJson.afterNewDirectory != null) {
        afterNewDirectory = initJson.afterNewDirectory
    }
    if (initJson.beforeRenameDir != null) {
        beforeRenameDir = initJson.beforeRenameDir
    }
    if (initJson.afterRenameDir != null) {
        afterRenameDir = initJson.afterRenameDir
    }
    if (initJson.afterDeletDir != null) {
        afterDeletDir = initJson.afterDeletDir
    }
    if (initJson.dirTool != null) {
        dirTool = initJson.dirTool
    }
    $("<div class='page' "+((initJson.pageSize!=null && initJson.currentPage!=null)?"":"style='display:none;'")+"><div class=\"page_info\" pageSize='"+(initJson.pageSize!=null?initJson.pageSize:20)+"' currentPage='"+(initJson.currentPage=null?initJson.currentPage:1)+"'><span id='totalRecords'></span>条记录 • 共<span id='totalPages'></span>页</div><a class=\"page_btn\" onclick='firstPage(null,this)'>首页</a><a class=\"page_btn\"onclick='prePage(this)'>上一页</a><div class=\"page_select\"><span>第</span><input id='goto' type=\"text\" onkeypress='thisPage(event.keyCode||event.which,this)' value='1'/><span>页</span></div><a class=\"page_btn\" onclick='thisPage(null,this)'>跳转</a><a class=\"page_btn\" onclick='nexPage(this)'>下一页</a><a class=\"page_btn\" onclick='lasPage(this)'>末页</a></div>").insertAfter($(initJson.content));
    sortType = initJson.sortType;
    if(dirTool){
        $(initJson.content).append("<div class='dir_tool_sum'><button class=\"fa \" title=\"新建子目录\" onclick='createNewDirectory(this)'>✚</button></div>")
    }
    $("<div class='tool_box'><label>文件夹名称<input id=\"searchTagTitle\" type=\"text\" onKeyPress=\"doSearch(event.keyCode||event.which,this)\"/></label><label>文件名称<input id=\"searchFileName\" type=\"text\" onKeyPress=\"doSearch(event.keyCode||event.which,this)\"/></label><label>上传时间从<input type=\"date\" id=\"searchStartTime\" onKeyPress=\"doSearch(event.keyCode||event.which,this)\"/>到<input type=\"date\" id=\"searchEndTime\" onKeyPress=\"doSearch(event.keyCode||event.which,this)\"/></label><label class=\"submit_person\">上传人<input id=\"searchCreaterUserName\" type='hidden'/><input id=\"searchCreaterUserCname\" type='hidden'/><input id=\"searchCreaterUserNameShow\" type=\"text\" oninput='searchUsers(this)' onKeyPress=\"doSearch(event.keyCode||event.which,this)\"/><input type=\"button\" value=\"×\" onclick='clearInput(this)' title=\"点击不选择上传人\"/><div id='selectUser' style='display:none;overflow-y: auto; overflow-x:hidden;position: absolute;left:0px;top:32px;width: 200px;height: 300px;z-index:100;border: 1px solid #e4e5e7;background-color: white;'></div></label><input type=\"button\" value=\"查询\" onclick=\"firstPage(null,$(this).parent().next().next().children(':first-child').next())\"/><input style='margin-left: 10px;' type=\"button\" value=\"取消\" onclick=\"location.reload(true)\"/></div>").insertBefore($(initJson.content));
    getDirectoryId({
        directoryNames:initJson.basicNames,
        type:1,
        success:function(data){
            $(initJson.content).attr("data",data);
            getTagContext($(initJson.content),data,initJson.currentPage,initJson.pageSize);
        }
    });
}
function initUploadWindow(initJson) {
    allowUload=true;
    if (initJson.cnameUrl != null) {
        getCnameUrl = initJson.cnameUrl
    }
    if(initJson.beforeUpLoad !=null){
        beforeUpLoad=initJson.beforeUpLoad;
    }
    if(initJson.afterUpLoad !=null){
        afterUpLoad=initJson.afterUpLoad;
    }
    $("#upLoadWindow").append("<div class=\"popup_fix\"><div class=\"popup\"><div class=\"popup_tit\">上传文件<input type=\"button\" value=\"×\"  onclick='closeopenUploadWindow()' title=\"关闭\"/></div><div class=\"popup_content1\"><div class=\"popup_line\"><label>目标目录</label><input id='directoryId' name='directoryId' type=\"hidden\"/><input id='fileTag1' name='fileTag1' type=\"hidden\"/><input name='username'  type=\"hidden\" value='" + initJson.username + "'/><input name='siteName' id='uploadSiteName'  type=\"hidden\"/><input id='publicState' name='publicState'  type=\"hidden\" value='0'/><input id='uploadTitle' name='targetTitle' type=\"text\" readonly='readonly'/></div><div class=\"popup_line\" style='margin-top: 10px;'><label>共享状态:</label><input type='checkbox' checked='checked' onclick='publicAllSite(this)'/>全网共享<input type='checkbox' checked='checked' style='margin-left: 20px;' onclick='publicMySite(this)'/>本站共享</div><div ondrop=\"fileCardDrop(event)\"ondragover=\"allowDrop(event)\" class=\"popup_line\" id='noneFileBoxSelect'><div><label>上传文件</label><input type=\"file\" name='file' id=\"browsefile\" style=\"visibility:hidden;\" onchange=\"filepath.value=this.value\"/><input type=\"textfield\" id=\"filepath\" placeholder=\"未选择任何文件\" readonly=\"readonly\"/><input type=\"button\" id=\"filebutton\" value=\"✚ 添加文件\" onclick=\"browsefile.click()\"/></div><div class=\"progress_bar\"><div id=\"uploadBar\" style='width: 0%;'><label id=\"upPercent\">0%</label></div></div></div><div class=\"popup_line\" style='display: none; overflow-y: auto; overflow-x: hidden;height:170px;' id='doFileBoxSelect'></div><div class=\"popup_btn\"><input type=\"button\" onclick='doUpload()' value=\"上传\"/><input type=\"button\" onclick='closeopenUploadWindow()' value=\"取消\"/></div></div></div>");
    $("<div id='fileBoxSelector' method=\"post\"  class=\"file_box\"><div class=\"fb_tit\">文件选择器</div><div class=\"fb_tool\"><form id='fileBoxForm'>" + "<label>文件名称<input class='res' style='width:130px;' id='fileBoxSearchFileName' name=\"fileName\" type=\"text\" onKeyPress=\"doFileBoxSearch(event.keyCode||event.which)\"/></label><label class=\"submit_person\">上传人<input class='res' name=\"creater\" type='hidden'/><input class='res'  id='fileBoxSearchCname' type='hidden'/><input  class='res' style='width:140px;' type=\"text\" oninput='searchUsers(this)' onKeyPress=\"doFileBoxSearch(event.keyCode||event.which)\"/><input type=\"button\" value=\"×\" onclick='clearInput(this)' title=\"点击不选择上传人\"/><div  style='display:none;overflow-y: auto; overflow-x:hidden;position: absolute;right: 40px;top: 45px;width: 200px;height: 150px;z-index:100;border: 1px solid #e4e5e7;background-color: white;'></div></label><label>上传时间从<input class='res' style='width:150px;' type=\"date\" name=\"startTime\" onKeyPress=\"doFileBoxSearch(event.keyCode||event.which)\"/>到<input class='res' style='width:150px;' type=\"date\" name=\"endTime\" onKeyPress=\"doFileBoxSearch(event.keyCode||event.which)\"/></label><input style='float:right;margin-left: 10px;' onclick='clearFileSelectBox()' type=\"button\" value=\"取消\"/><input style='float:right;' type=\"button\" value=\"查询\" onclick=\"firstPage(function() { fileBoxCurrentPage=1;searchFileBox();})\"/></form></div><div class=\"fb_content\"><div id = 'fbCardTable' class=\"fb_content_scroll scrollbar\"></div></div><div class=\"page fb_page\"><a class=\"page_btn\" onclick='firstPage(function() { fileBoxCurrentPage=1;searchFileBox();})'>首页</a><a class=\"page_btn\" onclick='fileBoxPrevPage()'>上一页</a><a class=\"page_btn\" onclick='fileBoxNexPage()'>下一页</a><a class=\"page_btn\" onclick=\"fileBoxLastPage()\">末页</a><div><div class=\"page_info\"><span id='fileBoxTotalRecords'></span>条记录 • 共<span id='fileBoxTotalPage'></span>页</div><div class=\"page_select\"><span>第</span><input id='fileBoxGoTO' onKeyPress=\"fileBoxThisPage(event.keyCode||event.which)\" style='color:#62a8d1;' type=\"text\" /><span>页</span></div><a class=\"page_btn\" onclick='fileBoxThisPage()'>跳转</a></div></div></div></div>").insertAfter($("#upLoadWindow"));
    searchFileBox();
    $("body").append("<div id='messageBox' style='display:none;'  class=\"popup_fix\"><div id='messageSize' class=\"popup\"><div id='messageTitle' class=\"popup_tit\"></div><div id='messageContent' class=\"popup_content1\"></div></div></div>")
}
function getTag(obj) {
    $(obj).append("<p>正在获取目录下的数据...</p>");
    getTagContext($(obj),$(obj).attr("id").substring(1),null,null);
    $($(obj).prev().children().get(0)).attr("state", "get").click()
}
function getTagContext(obj,directoryId,currentPage,pageSize) {
    getDirectoryChildren({
        parentId:directoryId,
        type:1,
        sortType:sortType,
        currentPage:currentPage,
        pageSize:pageSize,
        success:function (data) {
            if($(obj).text()=="搜索中..."){
                $(obj).empty();
            }
            setTimeout(function () {
                $(obj).children("p").remove();
            },500);
            if($(obj).hasClass("line_sum")){
                $(obj).next().children(".page_info").children(":first-child").text(data.totalRecords).next().text(data.totalPages);
            }
            $.each(data.directorys,function (index,directory) {
                mkdir($(obj),directory.directoryName,directory.directoryId);
            });
        }
    });
    getFilesByDirectoryID($(obj),directoryId);
}
function getFilesByDirectoryID(obj,directoryId,successFunc) {
    $.ajax({
        url:resourceContainerHost+"/getFilesByDirectoryID?directoryId="+directoryId,
        type:"Get",
        dataType:"json",
        success:function (data) {
            if(successFunc!=null){
                successFunc(data);
            }else{
                if($(obj).text()=="搜索中..."){
                    $(obj).empty();
                }
                var array=new Array();
                $.each(data,function (index,file) {
                    array.push(file.fileCreater);
                });
                getCname(array);
                $.each(data,function (index,file) {
                    mkfiletable($(obj),file.fileName,file.id,file.upTime,cnames[index]);
                });
            }
        }
    });
}
function mkdir(obj, dirName, dirID, isGot) {
    if ($(obj).find("#t" + dirID).length > 0) {
        return
    }
    if (beforeMkdir != null) {
        var doMk = beforeMkdir(obj, dirName, dirID, isGot);
        if (doMk == false) {
            return
        }
    }
    var tagtitle = dirName;
    var flagObj = $(obj).hasClass("line_sum")?$(obj).prev():$(obj).parents(".line_sum").prev();
    var flag = $(flagObj).find("#searchTagTitle").val();
    if (flag != null) dirName = linlight(dirName, flag, "lightBlue");
    var dirString = "<div class=\"line_box\"><div class=\"main_line head_line" + (isGot == "get" ? " head_line_select": "") + "\"> <label onclick=\"slidLable(this)\" class=\"fa fa-folder-o\" state='" + (isGot == null ? "": isGot) + "'><span>" + dirName + "</span></label>"+(allowUload?"<div class='inline_tool'>"+(sortType==null?"<button class=\"fa \" title=\"上移\" onclick='moveUp(this)'>↑</button><button class=\"fa \" title=\"下移\" onclick='moveDown(this)'>↓</button>":"")+(dirTool?"<button class=\"fa \" title=\"修改目录名\" onclick='changeDirectoryName(this)'>✎</button><button class=\"fa \" title=\"新建子目录\" onclick='createNewDirectory(this)'>✚</button><button  class=\"fa delete_dir\" title=\"删除目录\" onclick='deletDir(this)'></button>":"")+"<button class=\"fa fa-upload\" title=\"上传资源\" onclick='openUploadWindow(this)'></button></div>":"")+"</div><div id=\"t" + dirID + "\" tagTitle='" + tagtitle + "' class=\"sub_line_box_limit\"></div></div>";
    if($(obj).children(".sub_line_tab").length>0){
        $(dirString).insertBefore($(obj).children(".sub_line_tab"));
    }else{
        $(obj).append(dirString);
    }
    if (afterMkdir != null) {
        afterMkdir(obj, dirName, dirID, isGot)
    }
}
function mkfiletable(obj, filename, fileId, uptime, creater) {
    if (beforeMkfiletabel != null) {
        var doMk = beforeMkfiletabel(obj, filename, fileId, uptime, creater);
        if (doMk == false) {
            return
        }
    }
    var flagObj = $(obj).hasClass("line_sum")?$(obj).prev():$(obj).parents(".line_sum").prev();
    var flag = $(flagObj).find("#searchFileName").val();
    if (!$(obj).children(".sub_line_tab").length > 0) {
        $(obj).append("<div class=\"sub_line_tab\"><table><thead><th>附件名称</th><th>详细信息</th></thead><tbody></tbody></table></div>")
    }
    $(obj).find(".sub_line_tab table tbody").append("<tr><td><a onclick='downLoadFile("+fileId+")'>" + linlight(filename, flag, "orange") + "</a></td><td>于" + uptime.substring(0, uptime.length - 2) + "由" + linlight(creater, $(flagObj).find("#searchCreaterUserCname").val(), "orange") + "上传</td></tr>");
    if (afterMkfiletabel != null) {
        afterMkfiletabel(obj, filename, fileId, uptime, creater)
    }
}
function slidLable(obj) {
    if ($(obj).attr("state") == "get") {
        $(obj).parent().siblings(".sub_line_box_limit").slideToggle();
        var theClass = $(obj).parent().attr("class");
        if (theClass.indexOf(" head_line_select") == -1) {
            theClass += " head_line_select"
        } else {
            theClass = theClass.replace(" head_line_select", "")
        }
        $(obj).parent().attr("class", theClass)
    } else if ($(obj).attr("state") == "loading") {
        alert("该目录下文件过多，正在加载，请稍后！")
    } else {
        $(obj).attr("state", "loading");
        getTag($(obj).parent().next())
    }
}
function subFileName(fileName) {
    if (fileName.length > 35) {
        var fixTag = fileName.split(".")[fileName.split(".").length - 1];
        var fixFilename1 = fileName.substr(0, 10);
        var fixFilename2 = fileName.substr((fileName.lastIndexOf(".") - 10), 11);
        return fixFilename1 + "..." + fixFilename2 + fixTag
    } else {
        return fileName
    }
}
function linLightLine(obj, color) {
    $(obj).css("background-color", color)
}
function linlight(str, flag, color) {
    if(flag == null){
        return str;
    }
    var flags = flag.split(" ");
    for (var i = 0; i < flags.length; i++) {
        str = str.replace(flags[i], "<span style='background-color: " + color + "'>" + flags[i] + "</span>")
    }
    return str
}
function mkfbcard(fileid, filename, fileurl, uptime, creater, tag) {
    var nameFlag = $("#fileBoxSearchFileName").val();
    var cnameFlag = $("#fileBoxSearchCname").val();
    var flagClass = "";
    if (fileid == selectFileid) {
        flagClass = " file_select"
    }
    if (tag == "") {
        tag = "回收站"
    }
    $("#fbCardTable").append("<div id='filecard" + fileid + "' ondragstart='fileCardDrag(event)' draggable=\"true\" data='" + fileid + "' class=\"fb_card" + flagClass + "\"><table><tr><th>文件名称：</th><td><a onclick='downLoadFile("+fileid+")'>" + linlight(filename, nameFlag, "orange") + "</a></td></tr><tr><th>文件目录：</th><td class='directory_name'>" + tag + "</td></tr><tr><th>文件信息：</th><td>于" + uptime + "由" + linlight(creater, cnameFlag, "orange") + "上传</td></tr><tr><td colspan=\"2\"><div type='button' class=\"file_btn\" title=\"选择文件\" onclick='selectFile(this)'></div></td></tr></table></div>")
}
function doFileBoxSearch(e) {
    if (e == 13) {
        fileBoxCurrentPage = 1;
        searchFileBox()
    }
}
function getCname(array) {
    if(getCnameUrl==null){
        cnames = array;
        return;
    }
    $.ajax({
        url: getCnameUrl + "?usernames=" + array,
        dataType: "json",
        type: "GET",
        async: false,
        success: function(data) {
            cnames = data
        }
    })
}
function searchFileBox() {
    $.ajax({
        url: resourceContainerHost + "/searchFileSelect?currentPage=" + fileBoxCurrentPage + "&pageSize=" + fileBoxPageSize + "&username=" +$("#fileTag1").next().val()+"&siteName="+siteName+"&"+$("#fileBoxForm").serialize(),
        type: "GET",
        dataType: "json",
        success: function(data) {
            $("#fileBoxTotalRecords").text(data.totalRecord);
            $("#fileBoxTotalPage").text(data.totalPage);
            $("#fileBoxGoTO").val(fileBoxCurrentPage);
            getCname(data.usernames);
            $("#fbCardTable").empty();
            $.each(data.resourceFiles,
                function(index, value) {
                    mkfbcard(value.id, value.fileName, value.url, value.upTime, cnames[index], value.directoryId)
                    if(value.directoryId !="回收站"){
                        getDirectoryPathInfoById({
                            directoryId:value.directoryId,
                            type:1,
                            success:function (direct) {
                                var tagFlag = $("#fileBoxSearchTag").val();
                                $("#filecard"+value.id).find(".directory_name").html(linlight(direct.nameList, tagFlag, "lightblue"))
                            }
                        });
                    }
                })
        }
    })
}
function fileBoxNexPage() {
    var totalPage = parseInt($("#fileBoxTotalPage").text());
    if (fileBoxCurrentPage < totalPage) {
        fileBoxCurrentPage += 1;
        searchFileBox()
    }
}
function fileBoxPrevPage() {
    if (fileBoxCurrentPage > 1) {
        fileBoxCurrentPage -= 1;
        searchFileBox()
    }
}
function fileBoxLastPage() {
    fileBoxCurrentPage = parseInt($('#fileBoxTotalPage').text());
    searchFileBox()
}
function fileBoxThisPage(e) {
    if (e != null) {
        if (e != 13) {
            return
        }
    }
    var thispage = parseInt($("#fileBoxGoTO").val());
    var totalPage = parseInt($("#fileBoxTotalPage").text());
    if (testThisPage(thispage, totalPage)) {
        fileBoxCurrentPage = thispage;
        searchFileBox()
    }
}
function selectFile(obj) {
    $(obj).parent().parent().parent().parent().parent().siblings(".fb_card").removeClass("file_select");
    $("#noneFileBoxSelect").css("display", "none");
    $("#doFileBoxSelect").empty();
    selectFileid = $(obj).parent().parent().parent().parent().parent(".fb_card").addClass("file_select").attr("data");
    $(obj).parent().parent().parent().parent().parent(".fb_card").clone().addClass("file_select_left").appendTo("#doFileBoxSelect");
    $("#doFileBoxSelect").css("display", "block");
    $("#doFileBoxSelect").children().removeAttr("id").find(".file_btn").attr("title", "取消选择").attr("onclick", "clearSelect(this)")
}
function clearSelect(obj) {
    $("#noneFileBoxSelect").css("display", "block");
    $("#doFileBoxSelect").css("display", "none");
    $.each($("#fbCardTable").children(),
        function(index, obj) {
            $(obj).removeClass("file_select")
        });
    selectFileid = null
}
function clearFileSelectBox() {
    $("#fileBoxForm").find(".res").val("");
    fileBoxCurrentPage = 1;
    searchFileBox()
}
function setDownloadHref(obj, fileId,renameObj) {
    getFile({
        fileId:fileId,
        success: function(data) {
            $(obj).attr("href", data.url + "?filename=" + subFileName(data.fileName));
            if(renameObj!=null){
                $(renameObj).text(data.fileName);
            }
        }
    })
}
function fileCardDrag(ev) {
    ev.dataTransfer.setData("CardId", ev.target.id)
}
function fileCardDrop(ev) {
    ev.preventDefault();
    selectFile($("#" + ev.dataTransfer.getData("CardId")).find(".file_btn"))
}
function allowDrop(ev) {
    ev.preventDefault()
}
function openUploadWindow(obj) {
    obj = $(obj).parent();
    var directoryId= $(obj).parent().next().attr("id").substring(1);
    getDirectoryPathInfoById({
        directoryId:directoryId,
        type:1,
        success:function (data) {
            $("#uploadTitle").val(data.nameList);
            $("#uploadSiteName").val(data.nameList.split("/")[0]);
            $("#directoryId").val(directoryId);
            $("#upLoadWindow").css("display", "block");
            $("#fileBoxSelector").css("display", "block")
        }
    });

}
function openUploadWindowByPath( pathTitle,versionFlag) {
    if(versionFlag!=null){
        alert("api发生更新，您使用的是旧的api");
        return;
    }
    getDirectoryId({
        directoryNames:pathTitle,
        type:1,
        success:function (data) {
            $("#uploadTitle").val(pathTitle);
            $("#uploadSiteName").val(pathTitle.split("/")[0]);
            $("#directoryId").val(data);
            $("#upLoadWindow").css("display", "block");
            $("#fileBoxSelector").css("display", "block")
        }
    });
}
function closeopenUploadWindow() {
    $("#filepath").val("");
    $("#browsefile").val("");
    $("#upLoadWindow").css("display", "none");
    $("#fileBoxSelector").css("display", "none");
    $("#messageBox").css("display", "none")
}
function doUpload() {
    var uploadtype=$("#type").val();
    if($("#publicState").parent().next().children().get(1).checked){
        $("#publicState").val(2);
    }else if($("#publicState").parent().next().children().get(2).checked){
        $("#publicState").val(1);
    }else{
        $("#publicState").val(0);
    }
    if ($("#noneFileBoxSelect").css("display") == "none") {
        doApply($("#doFileBoxSelect").children().attr("data"));
        return
    }
    var filename = $("#filepath").val();
    if (filename == "") {
        alert("请选择上传文件");
        return false
    }
    if (filename.lastIndexOf('.') == -1) {
        alert("不支持无后缀名的文件！");
        return false
    }
    var type = filename.substring(filename.lastIndexOf('.') + 1);
    var firstcheck = /^[0-9a-zA-Z]+$/;
    var imageregx = /(.jpg|.JPG|.gif|.GIF|.PNG|.png|.JEPG|.jepg)$/;
    var videoregx = /(.mp4|.avi|.wmv|.rmvb|.MP4|.AVI|.WMV|.RMVB)$/;
    if (!firstcheck.test(type)) {
        alert("文件后缀异常！");
        return false
    }
    if(uploadtype==1 || uploadtype==203){
        if (videoregx.test(filename)){
            $("#fileTag1").val("video")
        }else{
            alert("视频格式错误");
            $("#filepath").val("");
            $("#browsefile").val("");
            $("#upLoadWindow").css("display", "none");
            $("#fileBoxSelector").css("display", "none");
            $("#messageBox").css("display", "none")
        }
    }else if(uploadtype==2 || uploadtype==202){
        if (imageregx.test(filename)){
            $("#fileTag1").val("image")
        }else{
            alert("图片格式错误");
            $("#filepath").val("");
            $("#browsefile").val("");
            $("#upLoadWindow").css("display", "none");
            $("#fileBoxSelector").css("display", "none");
            $("#messageBox").css("display", "none")
        }
    }else{
        if (imageregx.test(filename)) {
            $("#fileTag1").val("image")
        } else if (videoregx.test(filename)) {
            $("#fileTag1").val("video")
        } else {
            $("#fileTag1").val("file")
        }
    }
    /*if(uploadtype==undefined) {
     if (imageregx.test(filename)) {
     $("#fileTag1").val("image")
     } else if (videoregx.test(filename)) {
     $("#fileTag1").val("video")
     } else {
     $("#fileTag1").val("file")
     }
     }*/
    var filesize = document.getElementById("browsefile").files[0].size;
    var name = filename.split("\\")[filename.split("\\").length - 1];
    $.ajax({
        url: resourceContainerHost + "/checkFile?filename=" + name + "&size=" + filesize,
        type: "GET",
        dataType: "json",
        success: function(data) {
            if (data.nofile == "nofile") {
                uLoad()
            } else {
                $("#messageSize").css("width", "1200px");
                $("#messageTitle").empty();
                $("#messageTitle").append("<span style='color: #fff;font-size:14px;'>文件服务器发现可能一致的文件，是否直接采用？</span><input type=\"button\" value=\"×\"  onclick='closeopenMessageBox()' title=\"关闭\"/>");
                $("#messageContent").empty();
                $("#messageContent").append("<div id='messageTable' style='overflow-y: auto;overflow-x: hidden;width: 100%;'><table width='100%'><thead><th width='30%'>文件名</th><th width='30%'>文件目录</th><th width='30%'>详细信息</th><th width='10%'>操作</th></th></thead><tbody id='duplicateFiles'></tbody></table></div>");
                getCname(data[0]);
                $.each(data[1],
                    function(index, file) {
                        $("#duplicateFiles").append("<tr onmouseenter='linLightLine(this,\"#ACD6FF\")' onmouseleave='linLightLine(this,\"white\")' style='height: 35px;'><td>" + file.fileName + "</td><td id='checkFile"+file.id+"'>" + file.directoryId + "</td><td>于" + file.upTime.substring(0, file.upTime.length - 2) + "由" + cnames[index] + "上传</td><td><input type='button' style='background-color: #fff;border:1px solid #62a8d1;color: #62a8d1;font-size:14px;letter-spacing:2px;border-radius:3px;' value='采用' onclick='doApply(" + file.id + ")'/></td></tr>");
                        if(file.directoryId!="回收站"){
                            getDirectoryPathInfoById({
                                directoryId:file.directoryId,
                                type:1,
                                success:function (direct) {
                                    $("#checkFile"+file.id).text(direct.nameList);
                                }
                            });
                        }
                    });
                $("#messageContent").append("<input type='button' style='background-color: #62a8d1;border:1px solid #62a8d1;color: #fff;font-size:14px;letter-spacing:2px;border-radius:3px;float: right;margin-top: 20px;' onclick='notApply()' value='不采用'/>");
                $("#messageBox").css("display", "block");
                var tableHeight = parseInt($("#messageTable").height());
                var height = tableHeight + 140;
                if (height > 600) {
                    height = 600;
                    $("#messageTable").css("height", "460px")
                }
                $("#messageSize").css("height", height + "px")
            }
        },
        error: function() {
            alert("网络连接异常！")
        }
    })
}
function notApply() {
    $("#messageBox").css("display", "none");
    uLoad()
}
function doApply(fileId) {
    var form = new FormData(document.getElementById("upLoadWindow"));
    if($("#noneFileBoxSelect").css("display")=="none"){
        form.append("fileName",$("#doFileBoxSelect").find("tbody").children(":first-child").find("a").text());
    }else{
        form.append("fileName",form.get("file").name);
    }
    if (beforeUpLoad != null) {
        var bfu = beforeUpLoad(form, fileId);
        if(bfu!==null && !bfu){
            return;
        }
    }
    $.ajax({
        url: resourceContainerHost + "/applyFile?fileId=" + fileId + "&" + $("#upLoadWindow").serialize(),
        type: "GET",
        success: function() {
            $("#upLoadWindow").css("display", "none");
            $("#fileBoxSelector").css("display", "none");
            $("#messageBox").css("display", "none")
            setUpTime({
                directoryId:$("#directoryId").val(),
                type:1
            });
            if (afterUpLoad != null) {
                afterUpLoad(form, "applysuccess", fileId)
            } else {
                alert("上传成功！");
                location.reload(true)
            }
        },
        error: function() {
            if (afterUpLoad != null) {
                afterUpLoad(form, "applyerror")
            }
            alert("网络异常！")
        }
    })
}
function uLoad() {
    var form = new FormData(document.getElementById("upLoadWindow"));
    form.append("fileName",form.get("file").name);
    if (beforeUpLoad != null) {
        var bfu = beforeUpLoad(form);
        if(bfu!==null && !bfu){
            return;
        }
    }
    var url = resourceContainerHost + "/uploadFileNew";
    $.ajax({
        url: url,
        type: "POST",
        data: form,
        processData: false,
        contentType: false,
        success: function(data) {
            $("#upLoadWindow").css("display", "none");
            $("#fileBoxSelector").css("display", "none");
            $("#messageBox").css("display", "none")
            setUpTime({
                directoryId:$("#directoryId").val(),
                type:1
            });
            if (afterUpLoad != null) {
                afterUpLoad(form, "success", data)
            } else {
                alert("上传成功！");
                location.reload(true)
            }
        },
        error: function(data, error, obj) {
            if (afterUpLoad != null) {
                afterUpLoad(form, "error")
            }
            alert("网络连接异常");
            setTimeout(function() {
                    document.getElementById("uploadBar").style.width = "0%";
                    $("#upPercent").text("0%")
                },
                50)
        },
        xhr: function() {
            var myXhr = $.ajaxSettings.xhr();
            if (myXhr.upload) {
                myXhr.upload.addEventListener('progress',
                    function(e) {
                        var percent = parseInt(e.loaded / e.total * 100);
                        document.getElementById("uploadBar").style.width = percent + "%";
                        $("#upPercent").text(percent + "%")
                    },
                    false)
            }
            return myXhr
        }
    })
}
function closeopenMessageBox() {
    $("#messageBox").css("display", "none")
}
function publicAllSite(obj) {
    if(obj.checked){
        $(obj).parent().children().get(2).checked=true;
    }
}
function publicMySite(obj) {
    if(!obj.checked){
        $(obj).parent().children().get(1).checked=false;
    }
}
function moveDown(obj){
    obj = $(obj).parent();
    var index = $(obj).parent().parent();
    if(index.index() != length-1&&index.next().attr("class")!="sub_line_tab"){
        index.next().after(index);
    }
    saveDirSort(index);
}
function moveUp(obj){
    obj = $(obj).parent();
    var index = $(obj).parent().parent();
    if(index.index() != 0){
        index.prev().before(index);
    }
    saveDirSort(index);
}
function saveDirSort(obj){
    var array = new Array();
    $.each($(obj).parent().children(),function (index,dir) {
        if($(dir).attr("class")=="line_box"){
            array.push($.trim($(dir).children(".sub_line_box_limit").attr("id")).substring(1));
        }
    });
    var current = 1;
    var pagesize = -1;
    if($(obj).parent().hasClass("line_sum")){
        if($(obj).parent().next().hasClass("page")){
            pagesize = $(foot).find(".page_info").attr("pageSize");
            current = $(foot).find(".page_info").attr("currentPage");
        }
    }
    changeDirectorySort({
        directories:array.join("/"),
        type:1,
        currentPage:current,
        pageSize:pagesize
    });
}
function firstPage(func,obj) {
    if (func != null) {
        func()
    } else {
        $(obj).next().next().children("input").val(1);
        searchFile($(obj).parent().prev())
    }
}
function prePage(obj) {
    var currpage = parseInt($(obj).next().children("input").val());
    if (currpage == 1) return;
    $(obj).next().children("input").val(currpage-1);
    searchFile($(obj).parent().prev())
}
function nexPage(obj) {
    var totalPages = parseInt($(obj).parent().children(".page_info").children(":first-child").next().text());
    var currpage = parseInt($(obj).prev().prev().children("input").val());
    if (currpage == totalPages) return;
    currpage = currpage + 1;
    $(obj).prev().prev().children("input").val(currpage);
    searchFile($(obj).parent().prev())
}
function lasPage(obj) {
    var totalPages = parseInt($(obj).parent().children(".page_info").children(":first-child").next().text());
    $(obj).prev().prev().prev().children("input").val(totalPages);
    searchFile($(obj).parent().prev())
}
function thisPage(e,obj) {
    if (e != null) {
        if (e != 13) {
            return
        }
    }else{
        obj = $(obj).prev().children("input");
    }
    var thispage = parseInt($(obj).val());
    var totalPages = parseInt($(obj).parent().children(".page_info").children(":first-child").next().text());
    if (testThisPage(thispage, totalPages)) {
        searchFile($(obj).parent().parent().prev())
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
function searchFile(obj) {
    if($(obj).text()=="搜索中..."){
        alert("正在努力搜索中");
        return;
    }
    $(obj).empty();
    $(obj).text("搜索中...");
    var currentPage = $(obj).next().find("#goto").val();
    var pageSize = $(obj).next().children().attr("pageSize");
    var directoryKey = $(obj).prev().children(":first-child").children("input").val();
    var fileKey = $(obj).prev().children(":first-child").next().children("input").val();
    var startTime = $(obj).prev().children(":first-child").next().next().children("#searchStartTime").val();
    var endTime = $(obj).prev().children(":first-child").next().next().children("#searchEndTime").val();
    var username = $(obj).prev().children(":first-child").next().next().next().children("#searchCreaterUserName").val();
    if(directoryKey==""&&fileKey==""&&startTime==""&&endTime==""&&username==""){
        getTagContext($(obj),$(obj).attr("data"),currentPage,pageSize);
    }else{
        $(obj).next().css("display","block");
        var url = directoryEngineHost+"/search?currentPage="+currentPage+"&pageSize="+pageSize+"&type="+1+"&directoryId="+$(obj).attr("data")+"&directoryKey="+directoryKey+"&fileKey="+fileKey+"&startTime="+startTime+"&endTime="+endTime+"&username="+username;
        $.ajax({
            url:url,
            type:"GET",
            dataType:"json",
            success:function (data) {
                $(obj).empty();
                $(obj).next().find("#totalRecords").text(data.totalRecords);
                $(obj).next().find("#totalPages").text(data.totalPage);
                getCname(data.usernames);
                $.each(data.search,function (index,searchVO) {
                    if(searchVO.fileName==null){
                        mkdir($(obj),searchVO.directoryName,searchVO.directoryId);
                    }else{
                        mkdir($(obj),searchVO.directoryName,searchVO.directoryId,"get");
                        $(obj).find("#t"+searchVO.directoryId).css("display","block");
                        mkfiletable($(obj).find("#t"+searchVO.directoryId),searchVO.fileName,searchVO.fileId,searchVO.upTime,cnames[index]);
                    }
                })
            }
        });
    }
}
function createNewDirectory(obj) {
    var direcID;
    var reObj = $(obj).parent().parent();
    if($(reObj).hasClass("main_line")){
        direcID=$(reObj).next().attr("id");
        direcID = direcID.substring(1);
        if(!$(reObj).hasClass("head_line_select")){
            $(obj).parent().prev().click();
        }
        reObj=$(reObj).next();
    }else{
        direcID=$(reObj).attr("data");
    }
    if (beforeNewDirectory !=null){
        beforeNewDirectory(direcID,$(reObj));
    }
    newDirectory({
        directoryId:direcID,
        type:1,
        success:function (data) {
            if ($(reObj).find("#t" + data.directoryId).length > 0) {
                alert("已经存在未命名子目录，请命名后再新建");
                if (afterNewDirectory !=null){
                    afterNewDirectory(data.directoryId,data.directoryName,"directAlreadyHave");
                }
            }else{
                mkdir($(reObj),data.directoryName,data.directoryId);
                if (afterNewDirectory !=null){
                    afterNewDirectory(data.directoryId,data.directoryName,"success");
                }
            }
        }
    })
}
function changeDirectoryName(obj) {
    var reobj = $(obj).parent().prev();
    $(reobj).append("<input style='color: black' value='"+$(reobj).children("span").text()+"' onclick='event.stopPropagation();'/><input style='color: black' type='button' value='确定' onclick='renameTheDirectory(this);event.stopPropagation();'/><input style='color: black' type='button' value='取消' onclick='cancelRename(this);event.stopPropagation();' oldData='"+$(reobj).children("span").text()+"'/>").children("span").remove();
}
function cancelRename(obj) {
    $("<span>"+$(obj).attr("oldData")+"</span>").insertAfter($(obj));
    $(obj).prev().remove();
    $(obj).prev().remove();
    $(obj).remove();
}
function renameTheDirectory(obj){
    if($(obj).attr("connecting")=="true"){
        alert("正在改名中，请稍后！");
        return;
    }
    $(obj).attr("connecting","true");
    var info = $(obj).parent().parent().next();
    var dirId = $(info).attr("id");
    dirId = dirId.substring(1);
    if(beforeRenameDir!=null){
        beforeRenameDir($(obj).prev().val())
    }
    renameDirectory({
        directoryId:dirId,
        directoryName:$(obj).prev().val(),
        type:1,
        success:function (data) {
            if(data.state=="fail"){
                alert("重命名失败，请检查是否存在同名文件或联系管理员！")
            }else{
                $(obj).prev().remove();
                $(obj).next().remove();
                $("<span>"+data.directoryName+"</span>").insertAfter($(obj));
                $(obj).parent().parent().next().attr("tagtitle",data.directoryName);
                $(obj).remove();
            }
            if(afterRenameDir!=null){
                $(obj).attr("connecting","false");
                afterRenameDir(data.state,data.directoryName,$(obj).parent().parent().next())
            }
        }
    });
}
function doSearch(e,obj) {
    if (e == 13)
        $(obj).parent().parent().children(":last-child").prev().click();
}
function searchUsers(obj) {
    if(searchUsernameUrl!=null){
        $.ajax({
            url: searchUsernameUrl + "?username=" + $(obj).val(),
            type: 'POST',
            dataType: 'json',
            success: function(data) {
                $(obj).next().next().css("display", "block");
                $(obj).next().next().empty();
                $.each(data,
                    function(key, val) {
                        $(obj).next().next().append("<div style='font-size:18px;text-align:center;color:black;width: 100%;border: 1px solid #cfcfcf;background-color:white;' onmouseenter='this.style.backgroundColor=\"rgb(200,200,200)\"' onmouseleave='this.style.backgroundColor=\"white\"' onclick='fixInput(this,\"" + key + "\",\"" + val + "\")'>" + key + "|" + val + "</div>")
                    })
            }
        })
    }
    else{
        $(obj).prev().val($(obj).val()).prev().val($(obj).val());
    }

}
function fixInput(obj, username, cname) {
    $(obj).parent().prev().prev().val(username + "|" + cname);
    $(obj).parent().prev().prev().prev().prev().val(username);
    $(obj).parent().prev().prev().prev().val(cname);
    $(obj).parent().css("display", "none")
}
function clearInput(obj) {
    $(obj).prev().prev().prev().val("");
    $(obj).prev().val("");
    $(obj).prev().prev().val("");
    $(obj).next().css("display", "none")
}
function deletDir(obj) {
    var direcID = $(obj).parent().parent().next().attr("id");
    direcID = direcID.replace("t","");
    deleteDirectory({
        directoryId:direcID,
        type:1,
        success:function (data) {
            if(data.state=="success"){
                if(afterDeletDir!=null){
                    afterDeletDir("success");
                }
                $(obj).parent().parent().parent().remove();
            }else{
                alert("出现异常，请联系管理员！")
            }
        }
    });
}
function getFile(jsonData) {
    getAuthorization({
        async:jsonData.async,
        success:function(authorization){
            $.ajax({
                url:resourceContainerHost+"/getFileById?fileId="+jsonData.fileId,
                type:"Get",
                dataType:"json",
                async:(jsonData.async==null?true:jsonData.async),
                headers:{
                    Authorization:authorization
                },
                success:function (data) {
                    switch(data.url){
                        case "NoFileUrl":
                            if(jsonData.error!=null)
                                jsonData.error();
                            break;
                        case "AuthorizationOutOfTime":
                            if(jsonData.authorizationOutOfTime!=null)
                                jsonData.authorizationOutOfTime();
                            break;
                        case "NotAllowedRequest":
                            if(jsonData.notAllowedRequest!=null)
                                jsonData.notAllowedRequest();
                            break;
                        default:
                            if(jsonData.success!=null)
                                jsonData.success(data);
                            break;
                    }
                }
            })
        }
    });
}
function getFiles(jsonData) {
    getAuthorization({
        async:jsonData.async,
        success:function(authorization){
            $.ajax({
                url:resourceContainerHost+"/getFilesByIds?fileIds="+jsonData.fileIds,
                type:"Get",
                dataType:"json",
                async:(jsonData.async==null?true:jsonData.async),
                headers:{
                    Authorization:authorization
                },
                success:function (data) {
                    switch(data[0].url){
                        case "NoFileUrl":
                            if(jsonData.error!=null)
                                jsonData.error();
                            break;
                        case "AuthorizationOutOfTime":
                            if(jsonData.authorizationOutOfTime!=null)
                                jsonData.authorizationOutOfTime();
                            break;
                        case "NotAllowedRequest":
                            if(jsonData.notAllowedRequest!=null)
                                jsonData.notAllowedRequest();
                            break;
                        default:
                            if(jsonData.success!=null)
                                jsonData.success(data);
                            break;
                    }
                }
            })
        }
    });
}
function downLoadFile(fileId) {
    getFile({
        fileId:fileId,
        success:function (data) {
            location.href=data.url+"&filename="+subFileName(data.fileName);
        }
    })
}
function previewFile(fileId) {
    var url="";
    getFile({
        fileId:fileId,
        async:false,
        success:function (data) {
            url=data.url;
        }
    })
    window.open(url,"_blank");
}
function setSrc(fileId,obj) {
    getFile({
        fileId:fileId,
        success:function (data) {
            $(obj).attr("src",data.url);
        }
    })
}