<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html>
<head>
    <meta name="decorator" content="iframe"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

    <!-- 文件上传的样式和js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>

    <script type="text/javascript">
        //上传图片
        function uploadSystemFloorPic(campusNumber) {

            var date = "<input type='button' onclick=\"saveDocument('"+campusNumber+"');\" value='上传' />";
            $("#num").html("");
            $("#num").append(date);

            //获取当前屏幕的绝对位置
            var topPos = window.pageYOffset;
            //使得弹出框在屏幕顶端可见
            $('#searchFile').window({top: window.pageYOffset + 100});
            $("#searchFile").window('open');

            /*$('#file_upload').uploadify({
                'formData': {campusNumber: campusNumber},    //传值
                'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',
                'uploader': '${pageContext.request.contextPath}/visualization/uploadSystemCampusPic',		//提交的controller和要在火狐下使用必须要加的id
                buttonText: '选择文件',
                onQueueComplete: function (data) {//当队列中的所有文件全部完成上传时触发
                    //当上传玩所有文件的时候关闭对话框并且转到显示界面
                    $('#searchFile').window('close');
                    window.location.href = "${pageContext.request.contextPath}/visualization/systemCampusList";
                }
            });*/

        }

        //保存图片
        function saveDocument(campusNumber){
            var formData = new FormData();
            formData.append("myfile",document.getElementById("file_upload").files[0]);
            $.ajax({
                url:'${pageContext.request.contextPath}/visualization/uploadSystemCampusPic?campusNumber='+campusNumber,
                type:'POST',
                async:false,
                cache:false,
                contentType:false,
                processData:false,
                dataType:'text',
                data:formData,
                success:function(){
                    window.location.href = "${pageContext.request.contextPath}/visualization/systemCampusList";
                }
            });
        }

        //查看图片
        function showImage(image) {
            document.getElementById("img").src = image.src;
            //获取当前屏幕的绝对位置
            var topPos = window.pageYOffset;
            //使得弹出框在屏幕顶端可见
            $('#showImage').window({
                top: topPos + "px",
            });
            $('#showImage').window('open');
        }

    </script>


    <style type="text/css">
        .right-box a {
            color: #333;
            font-size: 12px;
        }

        .right-box select {
            width: 250px;
            overflow: scroll;
        }

        .select-action a {
            color: #333;
            text-decoration: none;
        }

    </style>
</head>
<body>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="title">
                        <div id="title">校区图片管理</div>
                    </div>
                    <c:forEach items="${systemCampuses}" var="s">
                        <fieldset class="introduce-box" style="width:96% !important;">
                            <label>${s.campusName}: </label><input type="button" onclick="uploadSystemFloorPic('${s.campusNumber}')" value="上传图片"/>
                            <ul class="img-box">
                                <li>
                                    <c:if test="${s.picUrl != null}">
                                        <img src="${pageContext.request.contextPath}/${s.picUrl}" border="0"
                                             width="200px" height="150px" onclick="showImage(this);">
                                        <a class="btn btn-common"
                                           href="${pageContext.request.contextPath}/visualization/deleteSystemCampusPic?campusNumber=${s.campusNumber}">删除</a>
                                    </c:if>
                                </li>
                            </ul>
                        </fieldset>
                    </c:forEach>
                    <div id="searchFile" class="easyui-window" title="上传图片" closed="true" iconCls="icon-add"
                         style="width:400px;height:200px">
                        <form id="form_file" name="form_file" method="post" enctype="multipart/form-data">
                            <table border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>
                                        <div id="queue"></div>
                                        <input id="file_upload" name="file_upload" type="file" multiple="true">
                                        <a id="num" name="num"></a>
                                </tr>
                            </table>
                        </form>
                    </div>
                    <div id="showImage" class="easyui-window" title="查看图片" closed="true" iconCls="icon-add"
                         style="width:800px;height:500px">
                        <center>
                            <img id="img" src="" width="800" height="500" border="0">
                        </center>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>