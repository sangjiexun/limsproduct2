<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>

    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->

    <script type="text/javascript">
        //提交表单
        function submitForm(){
            $.ajax({
                url:"${pageContext.request.contextPath}/labRoom/saveLabRoom?type=3&page=${page}",
                type:'POST',
                data:$('#submitForm').serialize(),
                async:false,
                error:function (request){
                    alert('请求错误!');
                },
                success:function(){
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.close(index);
                }
            });
        }
    </script>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.meeting.management"/></a></li>
            <c:if test="${flag!=1 }"><li class="end"><a href="javascript:void(0)">新建</a></li></c:if>
            <c:if test="${flag==1 }">
                <li class="end"><a href="javascript:void(0)">编辑</a></li>
            </c:if>
        </ul>
    </div>
</div>

<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="title">
                        <c:if test="${flag!=1 }"><div id="title">新建工作室</div></c:if>
                        <c:if test="${flag==1 }"><div id="title">编辑工作室</div></c:if>
                        <a class="btn btn-return" onclick="window.history.go(-1)">返回</a>
                    </div>
                    <form:form id="submitForm" name="MeetingRoom_form" method="POST" modelAttribute="labRoom">
                        <div class="new-classroom">
                            <fieldset>
                                <form:hidden path="id"/>
                                <label>会议室编号：</label>
                                <form:input path="labRoomNumber" class="easyui-validatebox" required="true"/>
                            </fieldset>

                            <fieldset>
                                <label>会议室名称：</label>
                                <form:input path="labRoomName" class="easyui-validatebox" required="true"/>
                            </fieldset>

                            <fieldset>
                                <label>会议室地点：</label>
                                <form:input path="labRoomAddress" class="easyui-validatebox" required="true"/>
                            </fieldset>

                            <fieldset>
                                <label>会议室英文名称</label>
                                <form:input path="labRoomEnName" class="easyui-validatebox" required="true"/>
                            </fieldset>
                            <fieldset>
                                <label>使用面积</label>
                                <form:input path="labRoomArea" class="easyui-validatebox" required="true"/>
                            </fieldset>
                            <fieldset>
                                <label>会议室容量</label>
                                <form:input path="labRoomCapacity" class="easyui-validatebox" required="true"/>
                            </fieldset>
                            <fieldset>
                                <label>管理机构</label>
                                <form:input path="labRoomManagerAgencies" class="easyui-validatebox" required="true"/>
                            </fieldset>
                            <%--<c:if test="${labRoomId==0}">
                            <fieldset>
                                <label>会议室管理员</label>
                                <select id="labRoomAdmin" name="labRoomAdmin" class="chzn-select">
                                    <option value="">请选择</option>
                                   <c:forEach items="${userList}" var="curr">
                                       <option value="${curr.username}">${curr.cname}</option>
                                   </c:forEach>
                                </select>
                            </fieldset>
                            </c:if>--%>
                            <fieldset>
                            <label>有无多媒体：</label>
                            <form:select id="CDictionaryByIsMultimedia" path="CDictionaryByIsMultimedia.id" class="chzn-select">
                                <form:option value="">请选择</form:option>
                                <form:options items="${isMultimedia}" itemLabel="CName" itemValue="id"/>
                            </form:select>
                            </fieldset>
                        </div>
                        <div class="moudle_footer">
                            <div class="submit_link">
                                <input class="btn" id="save" type="button" onclick="submitForm();" value="保存">
                                <input class="btn btn-return" type="button" value="取消" onclick="window.history.go(-1)">
                            </div>
                        </div>
                    </form:form>

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
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
