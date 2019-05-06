var contextPath = $("meta[name='contextPath']").attr("content");
layui.use(['form', 'laypage', 'layer', 'table', 'element'], function() {
	var form = layui.form
	//孙项目审核表单初始赋值
	form.val('examinegrandsonproject', {
		"dgpname": "我是孙项目名称",
		"dgpsonname": "我是孙项目所属的子项目名称",
		"dgpparentname": "我是孙项目所属的父项目名称",
		"dgpfounder": "我是孙项目的创建人",
		"dgpfounderdepartment": "我是创建人所属部门",
		"dgpfounderdate": "我是孙项目创建时间",
		"dgpbudget": "我是孙项目经费预算",
		"dgpdate": "我是孙项目实施时间",
		"rcbudget": "我是相关合同的招标纪要实际金额",
		"aafbudget": "我是验收申请表的实际金额"
	});
    form.on('submit(submitAudit)', function(data){
        console.log('审核')
        // console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        // console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
        // console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
        var grandSonId = $('#grandSonId').val();
        var auditResult = data.field.gpexamine;
        var remark = data.field.remark;
        // var data = JSON.stringify(data.field);
        // return false;
        $.ajax({
            url: contextPath + '/lims/api/labConstruction/saveProjectAudit?id='+grandSonId+'&auditResult='+auditResult+'&remark='+remark,
            // data: data,
            async: false,
            type: "GET",
            // contentType: "application/json;charset=UTF-8",
            success:function (res) {
                console.log(res);
                var index=parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
                parent.location.reload();
            },
            error:function(){
                alert("后台出了点问题，请重试！");
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            }
        });
    });
});

//流程默认置底
$(document).ready(function() {
	$(".timeline_box .layui-timeline").scrollTop($(".timeline_box .layui-timeline")[0].scrollHeight);
});