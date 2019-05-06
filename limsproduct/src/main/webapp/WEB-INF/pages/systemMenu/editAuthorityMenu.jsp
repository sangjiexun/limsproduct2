<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.coursearrange-resources"/>
<head>
    <meta name="decorator" content="iframe"/>
    <link href="${pageContext.request.contextPath}/css/room/muchPress.css" rel="stylesheet" type="text/css"/>
    <!--直接排课  -->

    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->
</head>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.system.management"/></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.authority.management"/></a></li>
        </ul>
    </div>
</div>
<div class="iStyle_RightInner">

    <div class="right-content">
        <div id="TabbedPanels1" class="TabbedPanels">
            <div class="TabbedPanelsContentGroup">
                <div class="TabbedPanelsContent">
                    <div class="content-box">
                        <div class="title">${authority.cname}权限菜单编辑</div>
                        <form action="${pageContext.request.contextPath}/systemMenu/saveSystemMenu" method="post">
                            <label>选择一个学院：</label>
                            <select name="academyNumber" id="academyNumber" onchange="changeAcademy()" class="chzn-select">
                                <option value="-1">校级</option>
                                <c:forEach items="${schoolAcademies}" var="a" varStatus="i">
                                    <c:if test="${selAcno eq a.academyNumber}">
                                        <option value="${a.academyNumber}" selected>${a.academyName}[${a.academyNumber}]</option>
                                    </c:if>
                                    <c:if test="${selAcno ne a.academyNumber}">
                                        <option value="${a.academyNumber}">${a.academyName}[${a.academyNumber}]</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        <table>
                            <tr>
                            <c:forEach items="${allSystemMenuParent}" var="parentMenu" varStatus="i">
                                <th width="10%">
                                            <input type="checkbox" name="allSelected"
                                                   id="allSelected_${parentMenu.id}" value="${parentMenu.id}"
                                                   onclick="allselect(${parentMenu.id})"/>
                                            ${parentMenu.name}
                                </th>
                                    <td style="text-align: left">
                                        <c:forEach items="${allSystemMenuChild[parentMenu.identification]}" var="childMenu"
                                                   varStatus="j">
                                    <input type="checkbox" name="warningType_${parentMenu.id}" id="warningType_${childMenu.id}" value="${childMenu.id}"
                                           onclick="fatherselect(${childMenu.parentMenu.id})"/>
                                    ${childMenu.name}
                                            <c:if test="${j.count % 6 eq 0}"><p><br></p></c:if>
                                        </c:forEach>
                                    </td>
                            </tr>
                            </c:forEach>
                        </table>
                            <input type="hidden" value="${authority.authorityName}" name="authName"/>
                            <input type="submit" name="subBtn" id="subBtn" value="提交"/>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript"
        charset="utf-8"></script>
<script type="text/javascript">
    var config = {
        '.chzn-select': {search_contains: true},
        '.chzn-select-deselect': {allow_single_deselect: true},
        '.chzn-select-no-single': {disable_search_threshold: 10},
        '.chzn-select-no-results': {no_results_text: 'Oops, nothing found!'},
        '.chzn-select-width': {width: "95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }

</script>
<!-- 下拉框的js -->
<script>
    function allselect(id) {
        var obj = document.getElementById("allSelected_"+id);
        var tagNamesObj = document.getElementsByName("warningType_"+id);
        if (obj.checked) {
            for (i = 0; i < tagNamesObj.length; i++) {
                tagNamesObj[i].checked = true;
            }
        } else {
            for (i = 0; i < tagNamesObj.length; i++) {
                tagNamesObj[i].checked = false;
            }
        }
    }

    function fatherselect(id) {
        var tagNamesObj = document.getElementsByName("warningType_"+id);
        var obj = document.getElementById("allSelected_"+id);
        var flag = true;
        for (i = 0; i < tagNamesObj.length; i++) {
            if (tagNamesObj[i].checked) {
                obj.checked = true;
                flag = false;
            }
        }
        if(flag){
            obj.checked = false;
        }
    }

    function changeAcademy(){
        var number = $("#academyNumber").val();
        window.location.href="${pageContext.request.contextPath}/systemMenu/editAuthorityMenu?name=${authority.authorityName}&academyNumber="+number;
    }

    $(document).ready(function () {
        var r = ${authorityMenuParent};
        for (var i = 0; i < r.length; i++) {
            var obj = document.getElementById("allSelected_"+r[i]);
            obj.checked = true;
        }
        var r1 = ${authorityMenuChild};
        for (var i = 0; i < r1.length; i++) {
            var obj = document.getElementById("warningType_"+r1[i]);
            obj.checked = true;
        }
    })
</script>

