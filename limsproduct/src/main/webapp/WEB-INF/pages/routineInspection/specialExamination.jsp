<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
    <meta name="decorator" content="iframe"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>
    <link href="${pageContext.request.contextPath}/css/tCourseSite/global.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/tCourseSite/reset.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/css/tCourseSite/lib.css" rel="stylesheet" type="text/css">

    <!-- 下拉框的样式 -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css"/>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css"/>
    <!-- 下拉的样式结束 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
    <link href="${pageContext.request.contextPath}/static_limsproduct/css/global_static.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        textarea {
            height: 240px;
        }
    </style>
    <script>
        //翻页
        //首页
        function first(url) {
            document.queryForm.action = url;
            document.queryForm.submit();
        }

        //末页
        function last(url) {
            document.queryForm.action = url;
            document.queryForm.submit();
        }

        //上一页
        function previous(url) {
            var page =${page};
            if (page == 1) {
                page = 1;
            } else {
                page = page - 1;
            }
            document.queryForm.action = url + page;
            document.queryForm.submit();
        }

        //下一页
        function next(url) {
            var totalPage =${pageModel.totalPage};
            var page =${page};
            if (page >= totalPage) {
                page = totalPage;
            } else {
                page = page + 1
            }
            if (totalPage == 0) {
                page = 1;
            }
            document.queryForm.action = url + page;
            document.queryForm.submit();
        }

        function cancel() {
            window.location.href = "${pageContext.request.contextPath}/specialExamination?currpage=1";
        }

        //新建-提交
        function subNew(types) {
            if ($("#newCheckContent").val() == "" || $("#newCheckContent").val() == null) {
                alert("检查内容未填写！")
            } else {
                if ($("#file_upload").val() == "" || $("#file_upload").val() == null) {
                    alert("附件未上传！")
                } else {
                    if ($("#checkItem").val() == "" || $("#checkItem").val() == null) {
                        alert("请选择检查项！")
                    }else if($("#creationDate").val()=="" || $("#creationDate").val()==null) {
                        alert("请填写检查时间");
                    }
                    else {
                        document.queryForm2.action = "${pageContext.request.contextPath}/saveSpecialExamination";
                        document.queryForm2.submit();
                    }
                }
            }
        }

        //新建
        function newSpecialExamination() {
            $("#newDiv").show();
        }

        //查看
        function examine(sEId) {
            window.location.href = "${pageContext.request.contextPath}/examineSpecialExamination?sEId=" + sEId;
        }

        //复查
        function reviewExamine(sEId) {
            window.location.href = "${pageContext.request.contextPath}/reviewExamineSpecialExamination?sEId=" + sEId;
        }

        //提交
        function sub(igId) {
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/ajaxGetRecord",
                data: {id: igId},
                dataType: "json",
                success: function (data) {
                    $("#riIdSub").val(igId);
                    $("#riTimeSub").val(decodeURI(data.riTime));
                    $("#riCenterNameSub").val(decodeURI(data.riCenterName));
                    $("#riRoomNameSub").val(decodeURI(data.riRoomName));
                    $("#riCnamSub").val(decodeURI(data.riCnam));
                    $("#riCheckContentSub").val(decodeURI(data.riCheckContent));
                    $("#urlSub").attr("src", "${pageContext.request.contextPath}" + decodeURI(data.url))
                    $("#newDiv").hide();
                    $("#examineDiv").hide();
                    $("#subDiv").show();
                },
                error: function () {
                    alert("查询附件信息出错！");
                }
            });
        }

        $(document).ready(function () {

            $.ajax({
                url: "${pageContext.request.contextPath}/getCheckItems",
                dataType: "json",
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                type: "POST",
                data: {checkType: $("#checkType").val()},
                complete: function (result) {
                    var obj = eval(result.responseText);
                    var result2;

                    $("#checkItem").empty();


                    for (var i = 0, j = 1; i < obj.length; i++, j++) {
                        result2 = result2 + "<option value='" + j + obj[i].value + "'>" + j + obj[i].value + "</option>";
                    }
                    $("#checkItem").append(result2);
                    $("#checkItem").trigger("liszt:updated");
                    result2 = "";
                }
            });

            $("#checkType").change(
                function () {

                    $.ajax({
                        url: "${pageContext.request.contextPath}/getCheckItems",
                        dataType: "json",
                        contentType: "application/x-www-form-urlencoded; charset=utf-8",
                        type: "POST",
                        data: {checkType: $("#checkType").val()},
                        complete: function (result) {
                            var obj = eval(result.responseText);
                            var result2;

                            $("#checkItem").empty();

                            for (var i = 0, j = 1; i < obj.length; i++, j++) {
                                result2 = result2 + "<option  value='" + j + obj[i].value + "'>" + j + obj[i].value + "</option>";


                            }
                            $("#checkItem").append(result2);
                            $("#checkItem").trigger("liszt:updated");
                            result2 = "";
                        }
                    });
                    $("#checkItem").trigger("liszt:updated");
                });
        });

    </script>

    <script type="text/javascript">
        // 保存（表单提交）
        function saveDocument(nameListId, type) {
            var name = "";
            if (type == 201) {
                name = "experimentalQuidesNameList";
            } else if (type == 202) {
                name = "experimentalImagesNameList";
            } else if (type == 203) {
                name = "experimentalVideosNameList";
            } else if (type == 204) {
                name = "experimentalToolsNameList";
            }
            var cur = "";
            var data = "";
            if ($("#file_upload_ori").val() != 0) {
                var formData = new FormData($("#uploadForm")[0]);
                $.ajax({
                    url: "${pageContext.request.contextPath}/tcoursesite/fileUploadWithOri?type=" + type,
                    type: 'POST',
                    data: formData,
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (returndata) {
                        alert("上传成功");
                        $("#searchFileOri").hide();
                    },
                    error: function (returndata) {
                        alert("上传失败");
                    }
                });
            } else {
                alert("请选择文件");
            }
        }
    </script>

</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.lab.safety.inspect"/></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.special.inspection"/></a></li>
        </ul>
    </div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <ul class="TabbedPanelsTabGroup">
            <li class="TabbedPanelsTab1 selected" id="s1">
                <a href="javascript:void(0)"><spring:message code="left.special.inspection"/></a>
            </li>
            <sec:authorize ifAnyGranted="ROLE_OPEARTIONSECURITYMANAGEMENT,ROLE_SUPERADMIN">
                <input class="btn btn-new" type="button" value="新建" onclick="newSpecialExamination();"/>
            </sec:authorize>
        </ul>
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="tool-box">
                        <form:form name="queryForm"
                                   action="${pageContext.request.contextPath}/specialExamination?currpage=1"
                                   method="post" modelAttribute="specialExamination">
                            <ul>
                            <li>
                                实验中心:
                                <form:select class="chzn-select" path="labAnnex.id" id="labAnnexId" name="labAnnex.id"
                                             style="width:100px">
                                    <form:option value="">实验中心</form:option>
                                    <c:forEach items="${labAnnexs}" var="current">
                                        <form:option value="${current.id}">${current.labName} </form:option>
                                    </c:forEach>
                                </form:select>
                            </li>
                            <li>
                                学期:
                                <form:select class="chzn-select" path="schoolTerm.id" id="schoolTerm.id"
                                             name="schoolTerm.id" style="width:180px">
                                    <form:option value="">学期</form:option>
                                    <c:forEach items="${terms}" var="current">
                                        <form:option value="${current.id}">${current.termName} </form:option>
                                    </c:forEach>
                                </form:select>
                            </li>
                            <li>
                                <input type="submit" value="查询"/>
                                <input class="cancel-submit" type="button" value="取消" onclick="cancel();"/>
                                <%--<sec:authorize ifAnyGranted="ROLE_OPEARTIONSECURITYMANAGEMENT,ROLE_SUPERADMIN">--%>
                                    <%--<input type="button" value="新建" onclick="newSpecialExamination();"/>--%>
                                <%--</sec:authorize>--%>
                            </li>
                            </ul>
                        </form:form>
                    </div>
                </div>
                <div class="content-box">
                    <table class="tb" id="my_show">
                        <thead style="center-content">
                        <tr>
                            <th>学期</th>
                            <th>学院</th>
                            <th>实验中心</th>
                            <th>检查人</th>
                            <th>参加检查人员</th>
                            <th>检查时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${specialExaminations}" var="curr">
                            <tr>
                                <td>${curr.schoolTerm.termName}</td>
                                <td>${curr.labAnnex.labCenter.schoolAcademy.academyName}</td>
                                <td>${curr.labAnnex.labName}</td>
                                <td>${curr.user.cname}</td>
                                <td>${curr.participant}</td>
                                <td><fmt:formatDate value="${curr.creationDate.time}"
                                                    pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>
                                    <%--<input type="button" value="2查看" onclick="examine(${curr.id});"/>--%>
                                        <a href="javascript:void(0)" onclick="examine(${curr.id});">查看</a>
                                    <sec:authorize ifAnyGranted="ROLE_OPEARTIONSECURITYMANAGEMENT,ROLE_SUPERADMIN">
                                        <%--<input type="button" value="复查" onclick="reviewExamine(${curr.id});"/>--%>
                                        <a href="javascript:void(0)" onclick="reviewExamine(${curr.id});">复查</a>
                                    </sec:authorize>
                                    <a href="${pageContext.request.contextPath}/casualInspection/exportExamineSpecialExamination?specialExaminationId=${curr.id}">导出</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>


                    <!-- 分页开始 -->
                    <div class="page">
                        ${totalRecords}条记录,共${pageModel.totalPage}页
                        <a href="javascript:void(0)"
                           onclick="first('${pageContext.request.contextPath}/specialExamination?currpage=1')"
                           target="_self">首页</a>
                        <a href="javascript:void(0)"
                           onclick="previous('${pageContext.request.contextPath}/specialExamination?currpage=')"
                           target="_self">上一页</a>
                        第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                        <option value="${pageContext.request.contextPath}/specialExamination?currpage=${page}">${page}</option>
                        <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j"
                                   var="current">
                            <c:if test="${j.index!=page}">
                                <option value="${pageContext.request.contextPath}/specialExamination?currpage=${j.index}">${j.index}</option>
                            </c:if>
                        </c:forEach></select>页
                        <a href="javascript:void(0)"
                           onclick="next('${pageContext.request.contextPath}/specialExamination?currpage=')"
                           target="_self">下一页</a>
                        <a href="javascript:void(0)"
                           onclick="last('${pageContext.request.contextPath}/specialExamination?currpage=${pageModel.totalPage}')"
                           target="_self">末页</a>
                    </div>
                    <!-- 分页结束 -->
                </div>
                <br/>

                <div id="newDiv" style="display: none;width:97%;height: 400px;padding: 20px;border:1px solid #000;">
                    <table border="0" cellpadding="5" cellspacing="100" height="30" width="100%">
                        <tbody>
                        <form:form name="queryForm2" action="" method="post" modelAttribute="specialExamination"
                                   enctype="multipart/form-data">
                        <tr>
                            <td>
                                时间：${termName}
                            </td>
                            <td> 实验中心：
                                <form:select class="chzn-select" path="labAnnex.id" id="labAnnexId" name="labAnnex.id"
                                             style="width:200px">
                                    <c:forEach items="${labAnnexs}" var="current">
                                        <form:option value="${current.id}">${current.labName} </form:option>
                                    </c:forEach>
                                </form:select></td>
                            <td>
                                检查人：${user.cname}
                            </td>
                            <td>
                                检查时间：<input class="Wdate" id="creationDate" name="creationDate" type="text"
                                            value="<fmt:formatDate value="${creationDate.time}" pattern="yyyy-MM-dd"/>"
                                onclick="WdatePicker({maxDate:'%y-%M-{%d}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" />

                            </td>
                        </tr>
                        <tr>
                            <td>检查类型：</td>
                            <td>
                                <select class="chzn-select" path="checkType" id="checkType" name="checkType"
                                        style="width:150px">
                                    <c:forEach items="${checkTypes}" var="current" varStatus="status">
                                        <option value="${status.index+1}">${current} </option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                检查项：
                                <select class="chzn-select" path="checkItem" id="checkItem" multiple="multiple"
                                        name="checkItem" style="width:200px">
                                </select>

                            </td>
                            <td>
                                参加检查人员：
                                <select class="chzn-select" path="participant" id="participant" multiple="multiple"
                                        name="participant" style="width:200px">
                                    <c:forEach items="${participant}" var="current" varStatus="status">
                                        <option value="${current.value}">${current.key}${current.value}</option>
                                    </c:forEach>
                                </select>

                            </td>

                        </tr>
                        <tr>
                            <td colspan="2">
                                <br/>
                                <div style="float:left"> 检查内容及发现问题：</div>
                                <div style=" float:left"><textarea id="newCheckContent" rows="20" cols="60"
                                                                   path="checkContent" name="checkContent"></textarea>
                                </div>
                            </td>
                            <td>
                                <br/>
                                <div style="float:left"> 整改：</div>
                                <div style=" float:left"><textarea id="rectification" rows="20" cols="60"
                                                                   path="rectification" name="rectification"></textarea>
                                </div>
                            </td>
                            <td style="vertical-align:left;">

                                <input id="file_upload" name="file_upload" type="file" multiple="true">
                            </td>
                        </tr>
                    </table>
                    <table>
                        <tbody id="doc"></tbody>
                    </table>


                    </td>
                    </tr>
                    <tr>
                        <td>
                        </td>
                        <td>
                        </td>
                        <td>
                        </td>
                        <td>
                            <br>
                            <input type="button" value="提交" onclick="subNew()"/>
                        </td>

                    </tr>
                    </form:form>
                    </tbody>
                    </table>
                    <div>
                    </div>
                </div>
                <!-- 原生方法 -->
                <div id="searchFileOri" class="window_box hide fix zx2  " title="请选择" closed="true" iconCls="icon-add"
                     style="width:400px;height:200px;">
                    <span class="close_icon f16 abs b1 br3 bs5 poi h20 w20 lh20">x</span>
                    <form id="uploadForm" name="form_file_ori" method="post" enctype="multipart/form-data">
                        <table border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <div id="queue">
                                        <input id="file_upload_ori" name="file_upload_ori" type="file" multiple="true"
                                               value="0">
                                        <a href="javascript:void(0);"
                                           onclick="saveDocument('experimentalQuidesList',201)">上传</a>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div id="searchFile" class="easyui-window" title="上传附件" closed="true" iconCls="icon-add"
                     style="width:400px;height:200px">
                    <form id="form_file" method="post" enctype="multipart/form-data">
                        <table border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <div id="queue"></div>
                                    <input id="file_upload" name="file_upload" type="file" multiple="true">
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript"
        charset="utf-8"></script>
<script type="text/javascript">
    var config = {
        '.chzn-select': {search_contains: true},
        '.chzn-select-deselect': {allow_single_deselect: true},
        '.chzn-select-no-single': {disable_search_threshold: 10},
        '.chzn-select-no-results': {no_results_text: '选项, 没有发现!'},
        '.chzn-select-width': {width: "95%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
    $("#checkItem_chzn").css("width", "200px")
</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/tCourseSite/global.js"></script>

</body>
</html>
