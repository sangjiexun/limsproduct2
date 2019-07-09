<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>
    <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.css" rel="stylesheet"/>
    <!-- 全局的引用 -->
    <script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- bootstrap的引用 -->
    <link href="${pageContext.request.contextPath}/bootstrap/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" >
    <script src="${pageContext.request.contextPath}/bootstrap/bootstrap-select/js/bootstrap-select.js"></script>

    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <script type="text/javascript">
        // 提交批量设置管理员
        function subMultiple() {
            var type_code = $("#type_code").val();
            var labtwo = $("#labtwo").val();
            var peopletwo = $("#peopletwo").val();
            if($("#type_code").val()==''){
                alert("请选择管理员类别！");
            }else if($("#labtwo").val()=='' || $("#labtwo").val()==null) {
                alert("请至少选择一个实验室！");
            }else if($("#peopletwo").val()=='' || $("#peopletwo").val()==null) {
                alert("请至少选择一个管理员！");
            }
            $.ajax({
                url:"${pageContext.request.contextPath}/labRoom/saveMultipleManager",// 保存批量添加的管理员
                type:"POST",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                data:{"type_code":type_code,"labtwo":labtwo,"peopletwo":peopletwo},
                dataType: 'text',
                // async:false,
                success:function(data){
                    alert(data);
                }
            });
        }
    </script>
    <style>
        .btn {
            line-height: 23px;
            border-radius: 3px;
            font-size: 12px;
            padding: 0 7px;
            outline:none!important;
        }

        .btn:hover,
        .btn:focus {
            color:#fff;
            opacity: 0.8;
        }
        .row {
            margin-top: 10px;
            margin-bottom: 10px;
        }
        .row input[type=button] {
            margin: 0 0 5px;
        }
        .form-control {
            height: auto;
            box-shadow: none;
        }
        .dropdown .btn{
            margin:auto;
        }
        .btn-outline-primary{
            color:#409eff!important;
            background: #fff!important;
            border:1px solid #409eff!important;
        }
        .bootstrap-select .dropdown-menu .selected a span.text,
        .bootstrap-select.show-tick .dropdown-menu .selected span {
            color:#fff;
        }
    </style>
    <style>
        .modal-content,
        .modal-footer{
            border: none;
        }
        @media (min-width: 350px) {
            .col-md-2 {
                float: left;
                -webkit-box-flex: 0;
                -ms-flex: 0 0 12%;
                flex: 0 0 12%;
                max-width: 12%;
            }
            .col-md-5 {
                float: left;
                -webkit-box-flex: 0;
                -ms-flex: 0 0 44%;
                flex: 0 0 44%;
                max-width: 44%;
            }
        }
    </style>
</head>

<body>
<!--批量设置管理员弹出框-->
<%--<div class="modal " id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">--%>
<%--    <div class="modal-dialog" role="document">--%>
<%--        <div class="modal-content">--%>
            <div class="modal-body" style="height: auto;">
                <div class="row">
                    <div class="col-md-12" style="width: 95%;">
                        <select id="type_code" name="type_code" class="form-control selectpicker" title="请选择管理员类别">
                            <option value="">请选择</option>
                            <option value="1">实验室管理员</option>
                            <option value="2">物联管理员</option>
                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-5">
                        <select id="labone" class="form-control selectpicker show-tick" data-first-option="false" data-live-search="true" style="height: 200px;" multiple title="请选择实验室">
                            <c:forEach items="${labRoomList}" var="curr">
                                <option value="${curr.id}">${curr.labRoomName}[${curr.labRoomNumber}]</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2 text-center">
                        <input class="btn btn-outline-primary btn-sm" type="button" id="labright" value=">>"><br>
                        <input class="btn btn-outline-primary btn-sm" type="button" id="lableft" value="<<">
                    </div>
                    <div class="col-md-5">
                        <select id="labtwo" class="form-control show-tick" multiple="multiple" style="height: 200px !important; display: block !important;">

                        </select>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-md-5">
                        <select id="peopleone" class="form-control selectpicker show-tick" data-first-option="false" data-live-search="true" style="height: 200px;" multiple title="请选择人员">
                            <c:forEach items="${userList}" var="curr">
                                <option value="${curr.username}">${curr.cname}[${curr.username}]</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2 text-center">
                        <input class="btn btn-outline-primary btn-sm" type="button" id="peopleright" value=">>"><br>
                        <input class="btn btn-outline-primary btn-sm" type="button" id="peopleleft" value="<<">
                    </div>
                    <div class="col-md-5">
                        <select id="peopletwo" class="form-control show-tick" multiple="multiple" style="height: 200px !important; display: block !important;">

                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-sm" data-dismiss="modal" onclick="subMultiple();">提交</button>
<%--                <button type="button" class="btn btn-default btn-sm" data-dismiss="modal">关闭</button>--%>
            </div>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<script type="text/javascript">
    $("#labright").click(function() { //向右删除下拉框的值   向左增加下拉框的值 的点击事件
        $("#labone option:selected").appendTo("#labtwo");
        $('.selectpicker').selectpicker('refresh');
    });
    $("#lableft").click(function() { //向左删除下拉框的值   向右增加下拉框的值  的点击事件
        $("#labtwo option:selected").appendTo("#labone");
        $('.selectpicker').selectpicker('refresh');
    });
    $("#peopleright").click(function() { //向右删除下拉框的值   向左增加下拉框的值 的点击事件
        $("#peopleone option:selected").appendTo("#peopletwo");
        $('.selectpicker').selectpicker('refresh');
    });
    $("#peopleleft").click(function() { //向左删除下拉框的值   向右增加下拉框的值  的点击事件
        $("#peopletwo option:selected").appendTo("#peopleone");
        $('.selectpicker').selectpicker('refresh');
    });
</script>
</body>
</html>
