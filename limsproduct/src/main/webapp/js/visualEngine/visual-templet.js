var pageSize = 30;
$(function () {
    searchFunction = searchTemplets;
    linitFoot();
    searchTemplets();
    $("#add").click(function () {
        if ($("#new_templet").css("display") == "none") {
            $("#new_templet_id").val("");
            $("#new_templet_name").val("");
            $("#new_templet_authority").val("");
            $("#new_templet_editAuthority").val("");
            emptyProperties();
            $("#new_templet").css("display", "block")
        } else {
            $("#new_templet").css("display", "none")
        }
        });
});
function searchTemplets() {
    $.ajax({
        url: visualHost + "/getTempletList?currentPage=" + currpage + "&pageSize=" + pageSize,
        type: "GET",
        dataType: "json",
        success: function (data) {
            $("#table_manager").empty();
            $.each(data[0], function (index, item) {
                $("#table_manager").append("<tr>" +
                    "<td>" + item.id + "</td>" +
                    "<td>" + item.name + "</td>" +
                    "<td>" + item.authority + "</td>" +
                    "<td>" + item.editAuthority + "</td>" +
                    "<td>" + item.createdBy + "</td>" +
                    "<td>" + item.createTime + "</td>" +
                    "<td>" + item.upTime + "</td>" +
                    "<td>" + "<span onclick='editTemplet(this)'>编辑</span>"+"  "+"<span onclick='deleteTemplet(this)'>删除</span>" + "</td>" +
                    "</tr>")
            })
            $("#totalPages").text(data[2]);
            $("#totalRecords").text(data[1]);
            $("#goto").val(currpage);
        }
    });
}
function closeNewtemplet() {
    $.each($("#new_templet").find("input[value='取消']"), function (index, obj) {
        $(obj).click();
    });
    $("#new_templet").css("display", "none");
}
function submitNewTemplet() {
    var fd = new FormData();
    fd.append("id", $("#new_templet_id").val());
    fd.append("name", $("#new_templet_name").val());
    fd.append("createdBy", createrUser);
    fd.append("authority", $("#new_templet_authority").val());
    fd.append("editAuthority", $("#new_templet_editAuthority").val());
    for (var i = 1; i < 6; i++) {
        var array = new Array();
        $.each($("#properties" + i).children(), function (index, obj) {
            if ($(obj).attr("class") =="property"){
                array.push($(obj).attr("id").replace("property",""));
            }
        });
        fd.append("properties" + i, array);
        console.log(array);
    }
    $.ajax({
        url: visualHost + "/saveTemplet",
        type: "POST",
        dataType: "text",
        data:fd,
        contentType: false, 
        processData: false,
        success: function (data) {
            location.reload(true);
        }
    });
}
function deleteTemplet(obj) {
    $.ajax({
        url: visualHost + "/deleteTemplet?id=" + $($(obj).parent().parent().children().get(0)).text(),
        type: "GET",
        dataType: "text",
        success: function (data) {
            location.reload(true);
        }
    });
}
function editTemplet(obj) {
    $.ajax({
        url: visualHost + "/getTemplet?id=" + $(obj).parent().parent().children(":first-child").text(),
        type: "GET",
        dataType: "json",
        success: function (data) {
            $("#new_templet_id").val(data.id);
            $("#new_templet_name").val(data.name);
            $("#new_templet_authority").val(data.authority);
            $("#new_templet_editAuthority").val(data.editAuthority);
            emptyProperties();
            $.each(data.properties, function (index, property) {
                $("<div id='property" + property.id + "' class='property'><span>" + property.dataName + "</span><span><a>上移</a><a>下移</a><a>删除</a></span></div>").insertBefore($("#properties" + property.authority).children(":last-child"));
            });

        }
    });
    $("#new_templet").css("display", "block");
}
function emptyProperties() {
    $.each($("#new_templet_form").find(".property"), function (index, obj) {
        $(obj).remove();
    });
}
function openAddProperty(obj) {
    $(obj).css("display", "none").next().css("display", "block");
};
function closeAddProperty(obj) {
    $(obj).next().css("display", "none").parent().css("display", "none").prev().css("display", "block");
    $(obj).prev().prev().val("").prev().val("");
};
function autoSuggestProperty(obj) {
    $.ajax({
        url: visualHost + "/searchProperties?name=" + $(obj).val(),
        type: "GET",
        dataType: "json",
        success: function (data) {
            $(obj).next().next().next().next().empty();
            $.each(data, function (index, property) {
                $(obj).next().next().next().next().append("<div onclick='doAutoSuggest(this)' data='" + property.id + "' propertyName='" + property.dataName + "' class='auto_suggess_property_line'>" + property.dataName + "|" + property.dataType +"</div > ")
            });
            $(obj).next().next().next().next().css("display", "block");
        }
    })
}
function doAutoSuggest(obj) {
    $(obj).parent().css("display","none").parent().children(":first-child").val($(obj).attr("propertyName")).next().val($(obj).attr("data"));
}
function addProperty(obj) {
    if ($(obj).prev().val() == "") {
        alert("不能新增空配置！");
        return;
    }
    if ($("#property" + $(obj).prev().val()).length > 0) {
        alert("模板中已经包含此配置！");
    } else {
        $("<div id='property" + $(obj).prev().val() + "' class='property'><span>" + $(obj).prev().prev().val() + "</span><span><a>上移</a><a>下移</a><a>删除</a></span></div>").insertBefore($(obj).parent().parent());
        $(obj).prev().val("").prev().val("");
    }
    
}