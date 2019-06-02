var contextPath = $("meta[name='contextPath']").attr("content");

layui.use(['form', 'laydate'], function() {
	var form = layui.form,
		laydate = layui.laydate

	//子项目预算结算时间
	laydate.render({
		elem: '#budgetBalanceTime'
	});

	//子项目实施时间
	laydate.render({
		elem: '#projectImplementTime',
        trigger: 'click'
	});

    form.on('submit(submitSonProject)', function(data){
        console.log('提交')
        console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
        console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
        var data = JSON.stringify(data.field);
        console.log(data)
        // return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        $.ajax({
            url: contextPath + '/lims/api/labConstruction/submitSonProject',
            data: data,
            async: false,
            type: "POST",
            contentType: "application/json;charset=UTF-8",
            success:function (res) {
                console.log(res);
                if (res == "over") {
                    alert('项目总预算超额，请重新填写！');
                    return false;
                } else {
                    var index=parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.location.reload();
                }
            },
            error:function(){
                alert("后台出了点问题，请重试！");
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            }
        });
        // return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })

    form.on('submit(saveSonProject)', function(data){
        console.log('保存')
        console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
        console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}
        var data = JSON.stringify(data.field);
        console.log(data);
        // return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        $.ajax({
            url: contextPath + '/lims/api/labConstruction/saveSonProject',
            data: data,
            async: false,
            type: "POST",
            contentType: "application/json;charset=UTF-8",
            success:function (res) {
                console.log(res);
                if (res == "over") {
                    alert('项目总预算超额，请重新填写！');
                    return false;
                } else {
                    var index=parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.location.reload();
                }
            },
            error:function(){
                alert("后台出了点问题，请重试！");
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            }
        });
    });

	//子项目表单表单初始赋值
	form.val('sonproject', {
		// "projectName": ""
		// "budget": "100"
	});
});