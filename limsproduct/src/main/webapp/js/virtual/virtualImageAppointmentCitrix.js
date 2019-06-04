//分页跳转
function targetUrl(url) {
    document.form.action = url;
    document.form.submit();
}
function saveReservation(){
    /*//判断预约时间是否为当前时间的十分钟后
    var now=new Date();
    var start=new Date($("#startTime").val());
    var num = (start.getTime()-now.getTime())/(1000*60);
    if(num<10){
        alert("预约时间需为当前时间的十分钟后");
        return false;
    }
    //判断预约时长是否超过两小时
    var end=new Date($("#endTime").val());
    var long = (end.getTime()-start.getTime())/(1000*60*60);
    if(long<0){
        alert("结束时间需大于开始时间");
        return false;
    }
    if(long>2){
        alert("预约时长不可超过两小时");
        return false;
    }*/
    //判断当前时间段是否可用
    $.ajax({
        type: "POST",
        url: "../virtual/checkImageCitrix",
        data:{'VirtualImage': $("#VirtualImage").val(),'startTime': $("#startTime").val(),'remarks':$("#remarks").val()},
        dataType: 'text',
        success: function (data) {
            if (data == "used") {
                alert("预约的镜像已无可用账号");
            } else if (data == "booked") {
                alert("您在该时间段已预约有镜像");
            } else if (data == "fail") {
                alert("预约出错");
            } else {
                //保存预约记录
                $.ajax({
                    type: "POST",
                    url: "../virtual/saveVirtualImageReservationCitrix",
                    data: {
                        'VirtualImage': $("#VirtualImage").val(),
                        'startTime': $("#startTime").val(),
                        'endTime': $("#endTime").val(),
                        'remarks': $("#remarks").val(),
                        'imageAccount': data,
                    },
                    dataType: 'text',
                    success: function (data1) {
                        if (data1 == "success") {
                            alert("预约成功");
                            window.location.href = "../virtual/virtualImageReservation?currpage=1"
                        } else if (data1 == "interfaceSuccess") {
                            alert("接口错误");
                            window.location.href = "../virtual/virtualImageReservation?currpage=1"
                        } else if (data1 == "dataSuccess") {
                            alert("该镜像桌面已被占用");
                            window.location.href = "../virtual/virtualImageReservation?currpage=1"
                        } else if (data1 == "idSuccess") {
                            alert("未知错误");
                            window.location.href = "../virtual/virtualImageReservation?currpage=1"
                        } else {
                            alert("其他错误");
                            window.location.href = "../virtual/virtualImageReservation?currpage=1"
                        }
                    }
                });
            }
        }
    });

}

function layerListVirtualImageReservation(imageId){
    var index = layer.open({
        type: 2,
        title: '查看虚拟镜像预约记录',
        maxmin: true,
        shadeClose: true,
        area : ['700px' , '350px'],
        content: "../virtual/layerListVirtualImageReservation?imageId="+imageId+"&currpage=1",
    });
    //layer.full(index);
}

function updateImage() {
    $.ajax({
        type: "POST",
        url: "../virtual/updateImageCitrix",
        dataType: 'text',
        success: function (data) {
            if ("success"==data){
                alert("更新成功!");
                window.location.reload();
            }else {
                alert("更新失败!");
            }
        }
    });
}