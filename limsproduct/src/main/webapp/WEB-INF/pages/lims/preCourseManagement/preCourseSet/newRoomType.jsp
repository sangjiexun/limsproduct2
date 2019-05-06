<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<head>
    <meta name="decorator" content="iframe"/>

    <script type="text/javascript">
        function saveEditForm() {
            if($("#roomType").val()=="" || $("#roomType").val()==null){
                alert("请填写布局类型名称");
            } else {
                $.ajax({
                    url: "${pageContext.request.contextPath}/lims/preCourse/savePreRoomType",
                    type: 'POST',
                    data: $('#myForm').serialize(),
                    complete: function (data) {
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
        <div class="TabbedPanelsTabGroup-box">
            <div class="TabbedPanelsContent">
                <form:form id="myForm" action="" method="POST" modelAttribute="preRoomType">
                    <table class="tab_lab">
                        <tr>
                            <form:hidden path="id"/>
                            <th>布局类型名称<font color="red">*</font></th>
                            <td>
                                <form:input path="roomType" id="roomType"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4" style="text-align:right;">
                                <input class="btn btn-new" type="button" value="提交" onclick="saveEditForm();"/>
                            </td>
                        </tr>
                    </table>
                </form:form>


            </div>
        </div>
    </div>
</div>

</body>
</html>

