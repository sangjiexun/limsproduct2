<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>

    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <script type="text/javascript">
        function getComment(){
            var id = document.getElementById("settingId").value;
            document.queryform.action="${pageContext.request.contextPath}/inspect/newInspectGrading?roomId=0&idKey="+id;
            document.queryform.submit();
        }
    </script>
</head>
<body>

<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)">评价管理</a></li>
            <li class="end"><c:if test="${isEdit}"><a href="javascript:void(0)">编辑</a></c:if><c:if test="${!isEdit}"><a href="javascript:void(0)">新建</a></c:if></li>
        </ul>
    </div>
</div>

<!-- 内容栏开始 -->
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <!-- 标题 -->
                    <div class="title">
                        <div id="title">添加评价</div>
                    </div>

                    <!-- 表单 -->
                    <form:form name="queryform" action="${pageContext.request.contextPath}/inspect/saveInspectGrading?"
                               method="POST" modelAttribute="commonDocument" enctype="multipart/form-data">
                        <div class="new-classroom">
                            <fieldset>
                                <form:hidden path="user.username" value="${username}" />
                                <form:hidden path="type" value="1" />
                                    <%-- <input type="hidden" name="batchId" value="${batch.id}" /> --%>

                                <fieldset>
                                    <label style="width:auto">批次：</label>
                                    <form:select path="settingId" name="batchId" id="settingId" onchange="getComment()">
                                        <option value="${lets.id }" selected="selected">${lets.title }</option>
                                        <c:forEach items="${labEvaluationTimeSetting}" var="l">
                                            <option value="${l.id }" >${l.title }</option>
                                        </c:forEach>
                                    </form:select>
                                </fieldset>
                                <fieldset>
                                    <label style="width:auto">检查名称：</label>
                                    <select  id="comment" name="comment">
                                        <c:forEach items="${comment}" var="c">
                                            <option value="${c.id}">${c.comment }</option>
                                        </c:forEach>
                                    </select>
                                </fieldset>

                                <label>实验室名称：</label>
                                <form:select  id="labRoom" path="labRoom.id">
                                    <c:if test="${room != null && room.id != null}">
                                        <option selected="selected" value="${room.id }">${room.labRoomName}</option>
                                    </c:if>
                                    <c:forEach items="${listLabRooms}" var="current">
                                        <option value="${current.id }">${current.labRoomName}</option>
                                    </c:forEach>
                                </form:select>
                                <label>实验室评价意见(可不填)：</label>
                                <form:input path="comments"/>
                                <%--<input name="createdAt" type="hidden"  value="<fmt:formatDate value='${date.time}' pattern='yyyy-MM-dd HH:mm:ss'/>" />--%>
                            </fieldset>
                            <fieldset>
                                <label>上传图片：</label>
                                <input name="file" id="file" type="file" multiple="true" capture="camera">
                            </fieldset>

                            <fieldset style="width:88%">

                                <c:forEach items="${standards}" var="standard">
                                    <c:set var="ids" value="${ids},${standard.id }"></c:set>
                                    <label style="width:88%"><c:out value="${standard.standardName}"/></label>
                                    <select id="standard${standard.id}" name="standard${standard.id}">
                                        <option value="0">不合格</option>
                                        <option value="1">差</option>
                                        <option value="2">较差</option>
                                        <option value="3">正常</option>
                                        <option value="4">良好</option>
                                        <option value="5">优秀</option>
                                    </select>
                                </c:forEach>
                            </fieldset>

                            <div class="submit_link">
                                <input class="btn" id="save" type="button" value="上传" onclick="upload()" />

                                <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)" />
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

<script type="text/javascript">
    function upload(){
        var save=document.getElementById("save");
        var file=document.getElementById("file").value;
        var ids="${ids}";
        ids=ids.substring(1,ids.length);
        var id=new Array();
        id=ids.split(",");
        var sum=0;
        for(var i=0;i<id.length;i++){
            sum=sum+parseInt($("#standard"+id[i]).val());
        }
        if(sum>0){
            if(file.length<1){
                alert('请上传图片');
                return false;
            }
            else
            {
                document.queryform.submit();
            }
        }
        else
        {
            alert("所有指标均不合格,请重新评价！");
            return false;
        }
    }
</script>
</body>
</html>
