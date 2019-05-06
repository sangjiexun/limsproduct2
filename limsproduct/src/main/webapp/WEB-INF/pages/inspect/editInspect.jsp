<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html >
<head>
    <meta name="decorator" content="iframe"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>

    <!-- 文件上传的样式和js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>
    <script type="text/javascript">
        $(function(){
            $("#Pop_content").window({
                top: ($(window).width() - 800) * 0.5 ,
                left: ($(window).width() - 1000) * 0.5
            })
            $(".listTable").css('height',600);
        })
        $(function(){
            $("#userSubmit").click(function(){
                var nameop = $("#nameop").val();
                $.ajax({
                    url:"${pageContext.request.contextPath}/inspect/saveInspect",
                    data:{nameop:nameop},
                    type:"POST",
                    success:function(data){
                        $("#npo").html("");
                        $("#npo").append(data);
                    }
                });
            });
        });
    </script>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif" />
    <style>
        .content-box fieldset {
            margin: 0 0 20px 0;
            background: #fff;
            border: none;
            border-radius: 0px;
            padding: 0px;
            left: 1%;
        }
        .content-box legend {
            padding: 0;
            display: block;
            width: 100%;
            position: relative;
            font-size: 14px;
            color: #7c99e5;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .content-box table {
            margin: 0px;
            width: 100%;
            left:0;
        }
        fieldset th, fieldset td {

            text-align: left;
            border: 1px solid #ccc!important;
        }
        .content-box table tbody tr:nth-child(odd) td {
            background: none!important;
        }
        .introduce-box table td>input,
        .introduce-box table select{
            height:26px;
            margin: 0;
            box-sizing: border-box;
            box-shadow: none;
            width: 200px;
        }
        .introduce-box table select,
        .chzn-container{
            width:200px!important;
        }
        .introduce-box table input{
            width:100%!important;
        }
        .introduce-box table textarea{
            resize:none;
            box-shadow: none;
            box-sizing: border-box;
            margin: 0;
        }
        .introduce-box textarea {
            width: 100%;
        }
        .introduce-box input[type=button]{
            /*position: absolute;*/
            right: 0;
            /*top: 0;*/
        }
        .content-box td {
            padding: 15px 8px;
        }
    </style>
</head>
<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.inspect.item"/></a></li>
            <li class="end"><a href="javascript:void(0)">编辑项目</a></li>
        </ul>
    </div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <!-- 标题 -->
                    <div class="title">
                        编辑项目
                    </div>

                    <!-- 表单 -->
                    <form:form action="${pageContext.request.contextPath}/inspect/saveLabInspect?idKey=${inspect.id}" method="POST" modelAttribute="inspect">
                        <div class="new-classroom">
                            <fieldset>
                                <form:hidden path="id"/>
                                <label>项目名称：</label>
                                <form:input path="standardName"/>
                            </fieldset>
                            <fieldest>
                                <div class="submit_link">
                                    <input class="btn" id="save" type="submit" value="保存">
                                    <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
                                </div>
                            </fieldest>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<!-------------列表结束----------->
</html>