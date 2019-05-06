<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<head>
    <meta name="decorator" content="iframe"/>
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->

    <script type="text/javascript">
        function saveEditForm(){
            if($("#roomName").val()=="" || $("#roomName").val()==null){
                alert("请填写名称");
            }else if($("#academyNumber").val()=="" || $("#academyNumber").val()==null){
                alert("请选择所属部门");
            }else if($("#preCapacityRangeId").val()=="" || $("#preCapacityRangeId").val()==null){
                alert("请选择容量");
            }else if($("#roomType").val()=="" || $("#roomType").val()==null){
                alert("请选择房间布局类型");
            }else if($("#preSoftwareId").val()=="" || $("#preSoftwareId").val()==null){
                alert("请选择软件");
            }else {
                $.ajax({
                    url:"${pageContext.request.contextPath}/lims/preCourse/savePreLabRoom",
                    type: 'POST',
                    data: $('#subForm').serialize(),
                    complete:function(data){
                        window.parent.location.reload();
                        window.close();
                    }
                })
            }
        }
    </script>

</head>

<body>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <form:form id="subForm" name="subForm" action="" method="POST" modelAttribute="preLabRoom">
                        <div class="new-classroom">
                            <form:hidden path="id"/>
                            <fieldset>
                                <label><spring:message code="all.trainingRoom.labroom"/>名称：<font color="red">*</font></label>
                                <form:input path="roomName" id="roomName" />
                            </fieldset>
                            <fieldset>
                                <label>所属部门：<font color="red">*</font></label>
                                <form:select id="academyNumber" path="schoolAcademy.academyNumber" class="chzn-select">
                                    <form:option value="${schoolAcademy.academyNumber}" selected="selected">${schoolAcademy.academyName}</form:option>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>容量：<font color="red">*</font></label>
                                <form:select id="preCapacityRangeId" path="preCapacityRange.id" class="chzn-select">
                                    <c:forEach items="${preCapacityRanges}" var="c">
                                        <c:if test="${preLabRoom.preCapacityRange != null && preLabRoom.preCapacityRange.id == c.id}">
                                            <form:option value="${c.id}"
                                                         selected="selected">${c.capaType}(${c.capaRange}人)</form:option>
                                        </c:if>
                                        <c:if test="${preLabRoom.preCapacityRange == null || preLabRoom.preCapacityRange.id != c.id}">
                                            <form:option value="${c.id}">${c.capaType}(${c.capaRange}人)</form:option>
                                        </c:if>
                                    </c:forEach>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>房间布局类型：<font color="red">*</font></label>
                                <form:select id="roomType" path="preRoomType.id" class="chzn-select">
                                    <form:options items="${preRoomTypes}" itemLabel="roomType" itemValue="id"/>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>软件：<font color="red">*</font></label>
                                <select id="preSoftwareId" name="preSoftwareId" class="chzn-select" multiple="multiple">
                                    <c:forEach items="${preSoftwares}" var="s">
                                        <c:forEach items="${preSoftwareAll}" var="curr">
                                            <c:if test="${curr.id eq s.id }">
                                                <option value="${curr.id}" selected="selected">${curr.name}</option>
                                            </c:if>
                                        </c:forEach>
                                        <option value="${s.id}">${s.name}</option>
                                    </c:forEach>
                                </select>
                            </fieldset>
                        </div>
                    </form:form>
                    <div class="moudle_footer">
                        <div class="submit_link">
                            <input class="btn btn-return" type="submit" value="保存" onclick="saveEditForm();">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
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
</body>
</html>

