layui.use(['form', 'laydate'], function() {
	var form = layui.form,
		laydate = layui.laydate

	//子项目预算结算时间
	laydate.render({
		elem: '#spbudgetsettlement'
	});

	//子项目实施时间
	laydate.render({
		elem: '#spdate'
	});

	//子项目表单表单初始赋值
	form.val('sonproject', {
		"spname": "默认显示当前子项目名称"
	});
});