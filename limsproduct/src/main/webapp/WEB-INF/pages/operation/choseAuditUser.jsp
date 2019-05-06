<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<head>
    <meta name="decorator" content="none"/>
    <meta name="contextPath" content="${pageContext.request.contextPath}"/>
    <link type="text/css" rel="stylesheet"
          href="${pageContext.request.contextPath}/bootstrap/css/plugins/bootstrap-table/bootstrap-table.min.css"/>
    <!-- 全局的引用 -->
    <script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <script type="text/javascript">
        //提交审核人数据
        function saveCheckUser() {
            /*document.check_user_form.action="<%--${pageContext.request.contextPath}--%>/operation/submitOperationItemLims?page=<%--${page}--%>";
            document.check_user_form.submit();*/
            $.ajax({
                url:'${pageContext.request.contextPath}/operation/submitOperationItemLims?page=${page}',
                type:'POST',
                data:$('#check_user_form').serialize(),
                success:function(){
                    parent.location.href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=${page}&status=1&orderBy=9";
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.closeAll('iframe');
                }
            });
        }
    </script>
</head>

<body>
<div class="iStyle_RightInner">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="site-box">
            <div class="site-content">
                <div class="content-box">
                    <form:form name="check_user_form" id="check_user_form" method="post" modelAttribute="operationItem">
                        <table>
                            <tr>
                                <td>指定审核人</td>
                                <td>
                                    <form:hidden path="id" id="lp_id"/>
                                    <form:select path="userByLpCheckUser.username" id="check_users" class="chzn-select">
                                        <form:option value="">请选择</form:option>
                                        <form:options items="${auditorList}" itemValue="username" itemLabel="cname"></form:options>
                                    </form:select>
                                </td>
                            </tr>
                        </table>
                        <div style="float: right;">
                            <div class="submit_link">
                                <input class="btn" id="save" type="button" onclick="saveCheckUser();" value="提交">
                            </div>
                        </div>
                    </form:form>
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

