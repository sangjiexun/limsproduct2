var contextPath = $("meta[name='contextPath']").attr("content");
var parentProject;
// function showDetail(initjson) {
    layui.use(['form'], function() {
        var form = layui.form;
        var layer = layui.layer;
        var parentProId = $('#parentProId').val();
        console.log(parent.layer.methodConfig);
        // console.log(initjson.parentProject);
        // $.ajax({
        //    url: contextPath + '/lims/api/labConstruction/detailParentProject?id='+data.id,
        //    // data: jsonData,
        //    async: false,
        //    type: "GET",
        //    contentType: "application/json;charset=UTF-8",
        // 	success:function (res) {
        //        //父项目详情表单初始赋值
        form.val('detailparentproject', {
            "dppname": "我是父项目名称",
            "dppfounder": "我是父项目的创建人",
            "dppfounderdepartment": "我是创建人所属部门",
            "dppfounderdate": "我是父项目创建时间",
            "dppbudget": "我是父项目经费预算"
        });
        //    },
        // 	error:function () {
        //        alert("后台出了点问题，请重试！");
        //        return false;
        //    }
        // })

    });
// }
