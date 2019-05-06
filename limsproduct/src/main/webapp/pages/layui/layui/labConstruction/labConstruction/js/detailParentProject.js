layui.use(['form'], function() {
	var form = layui.form
	//孙项目详情表单初始赋值
	form.val('detailparentproject', {
		"dppname": "我是父项目名称",
		"dppfounder": "我是父项目的创建人",
		"dppfounderdepartment": "我是创建人所属部门",
		"dppfounderdate": "我是父项目创建时间",
		"dppbudget": "我是父项目经费预算"
	});
});