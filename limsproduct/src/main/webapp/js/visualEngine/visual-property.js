var pageSize = 30;
$(function () {
    searchFunction = searchProperties;
    linitFoot();
    searchProperties();
    $("#add").click(function () {
        if ($("#new_property").css("display") == "none") {
            $("#new_property_dataSort").val("");
            $("#new_property_dataUrl").val("");
            $("#new_property_dataType").val("");
            $("#new_property_dataName").val("");
            $("#new_property_id").val("");
            $("#new_property").css("display", "block")
        } else {
            $("#new_property").css("display", "none")
        }
        });
});
function searchProperties() {
    $.ajax({
        url: visualHost + "/getPropertyList?currentPage=" + currpage + "&pageSize=" + pageSize,
        type: "GET",
        dataType: "json",
        success: function (data) {
            $("#table_manager").empty();
            $.each(data[0], function (index, item) {
                $("#table_manager").append("<tr>" +
                    "<td>" + item.id + "</td>" +
                    "<td>" + (item.individualId == null ? "无个性化" : ("个性化配置于" + "<a href='http://www.baidu.com'>" + (item.individualType == 0 ? "页面" : "标记") + item.individualId + "</a>")) + "</td>" +
                    "<td>" + item.dataName + "</td>" +
                    "<td>" + item.dataType + "</td>" +
                    "<td>" + (item.dataUrl == null ? "" : item.dataUrl) + "</td>" +
                    "<td>" + (item.dataSort == null ? "" : item.dataSort) + "</td>" +
                    "<td>" + "<span onclick='editProperty(this)'>编辑</span>"+"  "+"<span onclick='deleteProperty(this)'>删除</span>" + "</td>" +
                    "</tr>")
            })
            $("#totalPages").text(data[2]);
            $("#totalRecords").text(data[1]);
            $("#goto").val(currpage);
        }
    });
}
function closeNewProperty() {
    $("#new_property").css("display", "none")
}
function submitNewProperty() {
    $.ajax({
        url: visualHost + "/saveProperty?" + $("#new_property_form").serialize(),
        type: "GET",
        dataType: "text",
        success: function (data) {
            location.reload(true);
        }
    });
}
function deleteProperty(obj) {
    $.ajax({
        url: visualHost + "/deleteProperty?id=" + $($(obj).parent().parent().children().get(0)).text(),
        type: "GET",
        dataType: "text",
        success: function (data) {
            location.reload(true);
        }
    });
}
function editProperty(obj) {
    $("#new_property_dataSort").val(parseInt($(obj).parent().prev().text()));
    $("#new_property_dataUrl").val($(obj).parent().prev().prev().text());
    $("#new_property_dataType").val($(obj).parent().prev().prev().prev().text());
    $("#new_property_dataName").val($(obj).parent().prev().prev().prev().prev().text());
    $("#new_property_id").val($(obj).parent().prev().prev().prev().prev().prev().prev().text());
    $("#new_property").css("display", "block");
}
