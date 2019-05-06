<%@ taglib prefix="sec"
           uri="http://www.springframework.org/security/tags"%>
<%@page language="java" isELIgnored="false"
        contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />
<fmt:setBundle basename="bundles.lab-resources" />
<html>
<head>
    <meta name="decorator" content="iframe" />
    <link href="${pageContext.request.contextPath}/css/iconFont.css"
          rel="stylesheet">
    <!-- 下拉框的样式 -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <link rel="stylesheet"
          href="https://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script
            src="${pageContext.request.contextPath}/js/cmsSystem/plugins/layer/layer.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.cookie.js"></script>



    <!-- 下拉的样式结束 -->
    <script type="text/javascript">
        $(document).ready(
            //设置
            function (){
                $('[data-id]').each(function(i,e){
                    $(e).on('click',function(){
                        layer.open({
                            type: 2,
                            title: '添加',
                            maxmin: true,
                            shadeClose: true,
                            area : ['700px' , '350px'],
                            content: '${pageContext.request.contextPath }/labRoom/addAgent?id='+$(e).attr('data-id')
                        })
                    });
                });
            });

        /**
         添加镜像至虚拟实验室
         */
        function addVirtualImage(){
            $("#addVirtualImage").show();
            $("#addVirtualImage").window('open');

        }

        function addLabRoomSoft(labNum,VirtualImageId){
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/virtual/addVirtualImage",
                data:{'LabRoomNumber': labNum,'VirtualImageId': VirtualImageId},
                dataType: 'text',
                success: function () {
                        alert("添加成功");
                }
            });
        }

    </script>
    <script type="text/javascript">

        //首页
        function first(page){
            var name=document.getElementById("deviceName").value;
            var number=document.getElementById("deviceNumber").value;
            //var maxDeviceNumber=document.getElementById("maxDeviceNumber").value;
            var deviceAddress=document.getElementById("deviceAddress").value;
            $.ajax({
                url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceByNameAndNumber?name="+name+"&number="+number+"&deviceAddress="+deviceAddress+"&page="+page,

                type:"POST",
                success:function(data){//AJAX查询成功
                    document.getElementById("body").innerHTML=data;

                }
            });
        }
        //上一页
        function previous(page){
            if(page==1){
                page=1;
            }else{
                page=page-1;
            }
            var name=document.getElementById("deviceName").value;
            var number=document.getElementById("deviceNumber").value;
            //var maxDeviceNumber=document.getElementById("maxDeviceNumber").value;
            var deviceAddress=document.getElementById("deviceAddress").value;
            $.ajax({
                url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceByNameAndNumber?name="+name+"&number="+number+"&deviceAddress="+deviceAddress+"&page="+page,

                type:"POST",
                success:function(data){//AJAX查询成功
                    document.getElementById("body").innerHTML=data;

                }
            });
        }
        //下一页
        function next(page,totalPage){
            if(page>=totalPage){
                page=totalPage;
            }else{
                page=page+1
            }
            var name=document.getElementById("deviceName").value;
            var number=document.getElementById("deviceNumber").value;
            //var maxDeviceNumber=document.getElementById("maxDeviceNumber").value;
            var deviceAddress=document.getElementById("deviceAddress").value;
            $.ajax({
                url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceByNameAndNumber?name="+name+"&number="+number+"&deviceAddress="+deviceAddress+"&page="+page,

                type:"POST",
                success:function(data){//AJAX查询成功
                    document.getElementById("body").innerHTML=data;

                }
            });
        }
        //末页
        function last(page){
            var name=document.getElementById("deviceName").value;
            var number=document.getElementById("deviceNumber").value;
            //var maxDeviceNumber=document.getElementById("maxDeviceNumber").value;
            var deviceAddress=document.getElementById("deviceAddress").value;
            $.ajax({
                url:"${pageContext.request.contextPath}/labRoom/findSchoolDeviceByNameAndNumber?name="+name+"&number="+number+"&deviceAddress="+deviceAddress+"&page="+page,

                type:"POST",
                success:function(data){//AJAX查询成功
                    document.getElementById("body").innerHTML=data;

                }
            });
        }
    </script>
    <script type="text/javascript">
        //首页
        function firstPage(page,typeId){
            var cname=document.getElementById("cname").value;
            var username=document.getElementById("username").value;
            $.ajax({
                url:"${pageContext.request.contextPath}/labRoom/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&roomId=${labRoom.id}&page="+page+"&typeId="+typeId,
                type:"POST",
                success:function(data){//AJAX查询成功
                    if(typeId==1){
                        document.getElementById("user_body").innerHTML=data;
                    }
                    if(typeId==2){
                        document.getElementById("user_body2").innerHTML=data;
                    }

                }
            });
        }
        //上一页
        function previousPage(page,typeId){
            if(page==1){
                page=1;
            }else{
                page=page-1;
            }
            var cname=document.getElementById("cname").value;
            var username=document.getElementById("username").value;
            $.ajax({
                url:"${pageContext.request.contextPath}/labRoom/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&roomId=${labRoom.id}&page="+page+"&typeId="+typeId,
                type:"POST",
                success:function(data){//AJAX查询成功
                    if(typeId==1){
                        document.getElementById("user_body").innerHTML=data;
                    }
                    if(typeId==2){
                        document.getElementById("user_body2").innerHTML=data;
                    }

                }
            });
        }
        //下一页
        function nextPage(page,totalPage,typeId){
            if(page>=totalPage){
                page=totalPage;
            }else{
                page=page+1
            }
            var cname=document.getElementById("cname").value;
            var username=document.getElementById("username").value;
            $.ajax({
                url:"${pageContext.request.contextPath}/labRoom/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&roomId=${labRoom.id}&page="+page+"&typeId="+typeId,
                type:"POST",
                success:function(data){//AJAX查询成功
                    if(typeId==1){
                        document.getElementById("user_body").innerHTML=data;
                    }
                    if(typeId==2){
                        document.getElementById("user_body2").innerHTML=data;
                    }
                }
            });
        }
        //末页
        function lastPage(page,typeId){
            var cname=document.getElementById("cname").value;
            var username=document.getElementById("username").value;
            $.ajax({
                url:"${pageContext.request.contextPath}/labRoom/findUserByCnameAndUsername?cname="+cname+"&username="+username+"&roomId=${labRoom.id}&page="+page+"&typeId="+typeId,
                type:"POST",
                success:function(data){//AJAX查询成功
                    if(typeId==1){
                        document.getElementById("user_body").innerHTML=data;
                    }
                    if(typeId==2){
                        document.getElementById("user_body2").innerHTML=data;
                    }

                }
            });
        }
    </script>
    <script type="text/javascript">

        function closeMyWindow(){
            var index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    </script>

    <style>
        td {
            word-wrap: break-word
        }
        .btn {
            padding: 2px 12px;
        }
    </style>
    <script type="text/javascript">
        function changeOps() {
            var hardware_id = $("#hardware_id").val();
            if(hardware_id==548) {
                $("#doorind").css("display", "table-row");
            }else {
                $("#doorind").css("display", "none");
            }
        }
    </script>
</head>
<body>
<!-- 虚拟实验室信息开始 -->
<div class="tit-box">
    <a class="btn btn-new" onclick="closeMyWindow()">返回虚拟实验室管理页</a>
</div>
<!-- 左侧栏目开始 -->
<div class="edit-left" style="width:98%">


    <div class="edit-content-box" style="overflow:initial;">
        <div id="myTabContent" class="tab-content">
                <!-- 虚拟镜像开始 -->
                <div class="edit-content-box">
                    <div class="title">
                        <div id="title">编号${labNum}虚拟实验室的镜像列表</div>
                        <%--<c:if test="${flag==true}">--%>
                            <a class="btn btn-new" href="javascript:void(0)"
                               onclick="addVirtualImage();">添加镜像</a>
                        <%--</c:if>--%>
                    </div>
                    <div class="edit-content">
                        <table class="tb" id="my_show">
                            <thead>
                            <tr>
                                <th>镜像编号</th>
                                <th>镜像名称</th>
                                <th>硬件链接</th>
                                <th>图片编号</th>
                                <th>供应商</th>
                                <th>备注</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${LabRoomVirtualImageList}" var="curr">
                                    <tr>
                                        <td>${curr.id}</td>
                                        <td>${curr.name}</td>
                                        <td>${curr.hardwareSet}</td>
                                        <td>${curr.imageCode}</td>
                                        <td>${curr.provider}</td>
                                        <td>${curr.setNote}</td>
                                        <td><a  href="${pageContext.request.contextPath}/virtual/deleteVirtualImage?roomNum=${labNum}&ImageId=${curr.id}"
                                                onclick="return confirm('确认删除吗？')">删除</a>
                                        </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <!-- 添加镜像 -->
                    <div id="addVirtualImage" class="easyui-window " title="添加镜像至虚拟实验室"
                         align="left" title="" modal="true" maximizable="false"
                         collapsible="false" closed="true" minimizable="false"
                         style="width: 500px; height: 300px;">
                        <table>
                            <tr>
                                <td>虚拟镜像名称</td>
                            </tr>

                                <c:forEach items="${virtualImageList}" var="m" >
                            <tr>
                                    <td>${m.name}</td>
                                <td><a onclick='addLabRoomSoft(${labNum},${m.id})' >添加</a></td>
                            </tr>
                                </c:forEach>
                        </table>
                    </div>
                </div>
            </div>

                    </div>
                </div>
            </div>
                    <!-- 分页模块
<div class="page" >
    ${pageModel.totalRecords}条记录,共${pageModel.totalPage}页
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=1')" target="_self">首页</a>
	<a href="javascript:void(0)" onclick="targetUrl('${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=${pageModel.previousPage}')" target="_self">上一页</a>
	第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
	<option value="${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=${pageModel.currpage}">${pageModel.currpage}</option>
	<c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
    <c:if test="${j.index!=pageModel.currpage}">
    <option value="${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=${j.index}">${j.index}</option>
    </c:if>
    </c:forEach></select>页
	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=${pageModel.nextPage}')" target="_self">下一页</a>
 	<a href="javascript:void(0)"  onclick="targetUrl('${pageContext.request.contextPath}/device/listMachineRoomDeviceUsage?currpage=${pageModel.lastPage}')" target="_self">末页</a>
</div>
<!-- 分页模块 -->
                </div>

            </div>
        </div>
    </div>


</div>
<!-- 左侧栏目结束 -->



<!-- 下拉框的js -->
<script
        src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
        type="text/javascript" charset="utf-8"></script>
<script
        src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
        type="text/javascript" charset="utf-8"></script>
<!-- <script
    src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script> -->
<!-- <script
    src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->
<script type="text/javascript">
    var config = {
        '.chzn-select': {search_contains : true},
        '.chzn-select-deselect'  : {allow_single_deselect:true},
        '.chzn-select-no-single' : {disable_search_threshold:10},
        '.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
        '.chzn-select-width'     : {width:"95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->
<script type="text/javascript">
    $(".btn-edit").click(function(){
        $(this).parent().next().slideUp(); //原信息隐藏
        $(this).parent().next().next().children(".edit-edit").slideDown();//修改信息显示
        $(this).hide();//修改按钮隐藏
        $(this).parent().next().next().children(".edit-action").show();	//返回保存按钮显示
    });
    $(".btn-return").click(function(){
        $(this).parent().hide();//返回保存按钮隐藏
        $(this).parent().parent().prev().prev().children(".btn-edit").show();//修改按钮显示
        $(this).parent().prev().slideUp();//修改信息隐藏
        $(this).parent().parent().prev().slideDown();//原始信息显示
        0
    });
</script>
<script  type="text/javascript">
    $(function () {
        $('#myTab li').click(function() {
            var index = $(this).index();
            $(this).addClass('active');
            $(this).siblings().removeClass('active');
            $('#p'+(index+1)).siblings().removeClass('active');
            $('#p'+(index+1)).addClass('active in');
            $.cookie("active",index+1,{"path":"/", expires:30})
        });
        var cookie_active = $.cookie("active");
        if(cookie_active!=null){
            $('#myTab li').siblings().removeClass('active');
            $('#myTab'+(cookie_active)).addClass('active');
            $('#p'+(cookie_active)).siblings().removeClass('active');
            $('#p'+(cookie_active)).addClass('active in');
        }
    });
</script>
</body>
</html>