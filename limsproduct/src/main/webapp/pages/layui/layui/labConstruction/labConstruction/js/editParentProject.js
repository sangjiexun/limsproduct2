layui.use(['form'], function() {
	var form = layui.form
	//父项目编辑表单初始赋值
	form.val('parentproject', {
		"ppname": "默认显示当前父项目名称",
		"ppbudget": "100"
	});
});