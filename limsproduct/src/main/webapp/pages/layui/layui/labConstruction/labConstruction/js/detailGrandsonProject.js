layui.use(['form'], function() {
	var form = layui.form
	//孙项目详情表单初始赋值
	form.val('detailgrandsonproject', {
		"dgpname": "我是孙项目名称",
		"dgpsonname": "我是孙项目所属的子项目名称",
		"dgpparentname": "我是孙项目所属的父项目名称",
		"dgpprocess": "显示当前进度（例：2019-01-03【上传人】文件上传成功，正在审核...）",
		"dgpfounder": "我是孙项目的创建人",
		"dgpfounderdepartment": "我是创建人所属部门",
		"dgpfounderdate": "我是孙项目创建时间",
		"dgpbudget": "我是孙项目经费预算",
		"dgpdate": "我是孙项目实施时间",
		"rcbudget": "我是相关合同的招标纪要实际金额",
		"aafbudget": "我是验收申请表的实际金额",
		"dgpsurplus": "我是'父项目预算'与'实际金额之和'的结余"
	});
});