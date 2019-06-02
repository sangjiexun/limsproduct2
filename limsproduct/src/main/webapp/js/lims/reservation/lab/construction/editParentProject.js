var contextPath = $("meta[name='contextPath']").attr("content");

layui.use(['form'], function() {
	var layer = layui.layer //弹层
	var form = layui.form
	//父项目编辑表单初始赋值
	form.val('parentproject', {
		// "ppname": "默认显示当前父项目名称",
		// "budget": "100"
	});
    form.on('submit(submitParentProject)', function(data){
        console.log('提交')
        console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
        console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
		var data = JSON.stringify(data.field);
		$.ajax({
            url: contextPath + '/lims/api/labConstruction/submitParentProject',
            data: data,
            async: false,
            type: "POST",
            contentType: "application/json;charset=UTF-8",
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
        // return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    form.on('submit(saveParentProject)', function(data){
        console.log('保存')
        console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
        console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
        var data = JSON.stringify(data.field);
        $.ajax({
            url: contextPath + '/lims/api/labConstruction/saveParentProject',
            data: data,
            async: false,
            type: "POST",
            contentType: "application/json;charset=UTF-8",
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