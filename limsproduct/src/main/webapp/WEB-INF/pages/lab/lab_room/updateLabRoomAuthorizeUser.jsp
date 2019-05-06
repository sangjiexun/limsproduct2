<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<html >
<head>
    <meta name="decorator" content="iframe"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href="${pageContext.request.contextPath}/css/iconFont.css" rel="stylesheet">

    <style type="text/css">
        .select-box{overlow:hidden;}
        .left-box,.right-box{float:left;
            margin-right:15px;}
        .right-box a{color:#333;
            font-size:12px;
        }
        .right-box{width:250px;
            border:1px solid #333;}
        .right-box select{width:250px;
            overflow:scroll;}
        .select-action a{color:#333;
            text-decoration:none;}
        .chzn-container{width: 200px;}
        .chzn-container ,#build_chzn{width:100%;}
        .chzn-container{width:100% !important;}

    </style>
    <script type="text/javascript">

    </script>
</head>
<body>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="title">
                        <div id="title">修改授权管理名单</div>

                    </div>
                    <form:form action="${pageContext.request.contextPath}/labRoom/saveLabRoomAuthorized?roomId=${labRoomAdmin.labRoom.id}&type=${type}" method="POST" modelAttribute="labRoomAdmin" >
                        <div class="new-classroom">
                            <fieldset>
                                <form:hidden path="id" />
                                <label>人员名称/编号：：</label>
                                <form:select id="username" path="user.username" class="chzn-select">
                                    <c:forEach items="${userList}" var="curr">
                                        <form:option value="${curr.username}">${curr.cname }${curr.username}</form:option>
                                    </c:forEach>
                                </form:select>
                                </td>
                            </fieldset>
                            <fieldset>
                                <label>开始日期：</label>
                                <input id="startDate" name="startDate" class="Wdate" type="text" value="<fmt:formatDate value='${labRoomAdmin.startDate.time}' pattern='yyyy-MM-dd'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:100px; margin-top: 1px;" readonly />
                            </fieldset>
                            <fieldset>
                                <label>结束日期：</label>
                                <input id="endDate" name="endDate" class="Wdate" type="text" value="<fmt:formatDate value='${labRoomAdmin.endDate.time}' pattern='yyyy-MM-dd'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:100px; margin-top: 1px;" readonly />
                            </fieldset>
                            <fieldset>
                                <label>开始时间</label>
                                <input id="startTime" name="startTime" class="Wdate" type="text" value="<fmt:formatDate value='${labRoomAdmin.startTime.time}' pattern='HH:mm'/>"  onclick="WdatePicker({dateFmt:'HH:mm'})" style="width:100px; margin-top: 1px;" readonly />
                            </fieldset>
                            <fieldset>
                                <label>结束时间：</label>
                                <input id="endTime" name="endTime" class="Wdate" type="text" value="<fmt:formatDate value='${labRoomAdmin.endTime.time}' pattern='HH:mm'/>"  onclick="WdatePicker({dateFmt:'HH:mm'})" style="width:100px; margin-top: 1px;" readonly />
                            </fieldset>

                        </div>
                        <div class="moudle_footer">
                            <div class="submit_link">
                                <input class="btn" id="save" type="submit" value="确定">
                                <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
                            </div>
                        </div>
                        </table>

                        <!-- 下拉框的js -->
                        <script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
                        <script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
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

                    </form:form>



                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
