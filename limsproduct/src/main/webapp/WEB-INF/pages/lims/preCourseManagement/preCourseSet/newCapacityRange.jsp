<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<head>
    <meta name="decorator" content="iframe"/>

    <script type="text/javascript">
        var re = /^[1-9]+[0-9]*]*$/;
        function saveEditForm() {
            if($("#capaType").val()=="" || $("#capaType").val()==null){
                alert("请填写容量类型");
            } else if($("#min").val()=="" || $("#min").val()==null ||
                $("#max").val()=="" || $("#max").val()==null){
                alert("请填写容量范围");
            } else if (!re.test($("#min").val()) || !re.test($("#max").val())) {
                $("#min").val("");
                $("#max").val("");
                alert("请输入正整数");
            } else {
                $.ajax({
                    url: "${pageContext.request.contextPath}/lims/preCourse/savePreCapacityRange",
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
                <form:form id="myForm" action="" method="POST" modelAttribute="preCapacityRange">
                    <table class="tab_lab">
                        <tr>
                            <form:hidden path="id"/>
                            <th> 容量类型<font color="red">*</font></th>
                            <td>
                                <form:input path="capaType" id="capaType"/>
                            </td>
                        </tr>
                        <tr>
                            <th> 容量范围<font color="red">*</font></th>
                            <td>
                                <input name="min" id="min" value="${min}"/>至
                                <input name="max" id="max" value="${max}"/>人
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

