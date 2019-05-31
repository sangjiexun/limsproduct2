//分页跳转
function targetUrl(url) {
    document.form.action = url;
    document.form.submit();
}
function saveReservation(){
    //判断预约时间是否为当前时间的十分钟后
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
    }
    //判断当前时间段是否可用
    $.ajax({
        type: "POST",
        url: "../virtual/checkImage",
        data:{'VirtualImage': $("#VirtualImage").val(),'startTime': $("#startTime").val(),'endTime': $("#endTime").val(),'remarks':$("#remarks").val()},
        dataType: 'text',
        success: function (data) {
            if(data=="success") {
                //保存预约记录
                $.ajax({
                    type: "POST",
                    url: "../virtual/saveVirtualImageReservation",
                    data:{'VirtualImage': $("#VirtualImage").val(),'startTime': $("#startTime").val(),'endTime': $("#endTime").val(),'remarks':$("#remarks").val()},
                    dataType: 'text',
                    success: function (data) {
                        if(data=="success"){
                            alert("预约成功");
                            window.location.href = "../virtual/virtualImageReservation?currpage=1"
                        }else if(data=="interfaceSuccess"){
                            alert("接口错误");
                            window.location.href = "../virtual/virtualImageReservation?currpage=1"
                        }else if(data=="dataSuccess"){
                            alert("该镜像桌面已被占用");
                            window.location.href = "../virtual/virtualImageReservation?currpage=1"
                        }else if(data=="idSuccess"){
                            alert("未知错误");
                            window.location.href = "../virtual/virtualImageReservation?currpage=1"
                        }else{
                            alert("其他错误");
                            window.location.href = "../virtual/virtualImageReservation?currpage=1"
                        }
                    }
                });
            }else if (data=="used") {
                alert("预约的镜像已被占用");
            }else if(data=="booked"){
                alert("您在该时间段已预约有镜像");
            }else if(data=="fail"){
                alert("预约出错");
            }else{
                alert("预约失败")
            }
        }
    });

}

function VirtualLogin(id) {
    url="../virtual/virtualLogin?virtualImageReservationid="+id;
    window.open(url);
}

/*
function testLogin() {
    var x;
   $.ajax({
       type:"POST",
       url:"https://10.2.39.50/Citrix/GVSUNWeb/Authentication/GetAuthMethods",
       /!*beforeSend: function(xhr) {
           xhr.setRequestHeader("X-Citrix-IsUsingHTTPS","No");
       },*!/
       headers:{
           "Content-Type":"application/x-www-form-urlencoded",
           "X-Citrix-IsUsingHTTPS":"No"
       },
       async:false,
       success:function (data) {
           console.log(data);
           x=document.cookie;
           console.log(x);
           $.ajax({
               type:"POST",
               url:"https://10.2.39.50/Citrix/GVSUNWeb/ExplicitAuth/Login",
               /!*beforeSend: function(xhr) {
                   xhr.setRequestHeader("X-Citrix-IsUsingHTTPS","No");
               },*!/
               headers:{
                   "Content-Type":"application/x-www-form-urlencoded",
                   "X-Citrix-IsUsingHTTPS":"No",
                   "Csrf-Token":"x"
               },
               async:false,
               success:function (data) {
                   console.log(data);
                   x=document.cookie;
                   console.log(x);
               }
           });
           $.ajax({
               type:"POST",
               url:"https://10.2.39.50/Citrix/GVSUNWeb/ExplicitAuth/LoginAttempt",
               /!*beforeSend: function(xhr) {
                   xhr.setRequestHeader("X-Citrix-IsUsingHTTPS","No");
               },*!/
               headers:{
                   "Content-Type":"application/x-www-form-urlencoded",
                   "X-Citrix-IsUsingHTTPS":"No",
                   "Csrf-Token":"x"
               },
               async:false,
               success:function (data) {
                   console.log(data);
                   x=document.cookie;
                   console.log(x);
               }
           });
       }
   });


}*/