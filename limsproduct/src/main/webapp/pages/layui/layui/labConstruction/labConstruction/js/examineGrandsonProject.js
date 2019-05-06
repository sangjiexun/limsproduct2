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
});

//流程默认置底
$(document).ready(function() {
	$(".timeline_box .layui-timeline").scrollTop($(".timeline_box .layui-timeline")[0].scrollHeight);
});