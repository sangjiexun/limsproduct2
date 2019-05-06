<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>
    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <!-- 文件上传的样式和js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>
    <style>
        .chzn-container{
            width: 100%!important;
        }
        .popup_box table th,
        .popup_box table td{
            width:16.6%;
        }
    </style>
    <script type="text/javascript">
        //分页跳转
        function targetUrl(url) {
            document.queryForm.action = url;
            document.queryForm.submit();
        }
        function cancel() {
            window.parent.location.reload();

        }
        function upload(type){

            //获取当前屏幕的绝对位置
            var topPos = window.pageYOffset;
            //使得弹出框在屏幕顶端可见
            $('#searchFile').window({
                top:topPos+"px",
            });
            $("#searchFile").window('open');

            $('#file_upload').uploadify({
                'formData':{id:'${individualPerformance.id}',type:type},    //传值
                'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',
                'uploader':'${pageContext.request.contextPath}/performance/performanceUpload;jsessionid=<%=session.getId()%>',		//提交的controller和要在火狐下使用必须要加的id
                buttonText:'选择文件',
                onQueueComplete : function(data) {//当队列中的所有文件全部完成上传时触发
                    //当上传玩所有文件的时候关闭对话框并且转到显示界面
                    $('#searchFile').window('close');
                    window.location.href="${pageContext.request.contextPath}/performance/editPerformance?type=16&id="+${individualPerformance.id};
                }
            });

        }
        function save() {
            $.ajax({
                url:'${pageContext.request.contextPath}/performance/savePerformance?type='+${type},
                type:'POST',
                data:$('#queryForm').serialize(),
                error:function (request){
                    alert('请求错误!');
                },
                success:function(){
                    parent.location.href="${pageContext.request.contextPath}/performance/listPerformance?currpage=1&type="+${type};
                    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                    parent.layer.closeAll(index);
                }
            });
        }
    </script>
</head>

<body>


<div class="right-content">
    <div class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <form:form id="queryForm" action="${pageContext.request.contextPath}/performance/savePerformance?type=16"  method="post" modelAttribute="individualPerformance" >
                    <form:hidden path="id"/>
                    <table class="tab_lab">
                        <table class="tab_lab">
                            <tr>
                                <th>论文标题</th>
                                <td colspan="3">
                                    <form:input  path="theme" type="text" placeholder="请输入"/>
                                </td>
                                <th>年度</th>
                                <td>
                                    <form:select  path="yearCode" class="chzn-select">
                                        <form:options  items="${schoolYear}" itemLabel="yearCode" itemValue="yearCode" />
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <th>发表期刊名称</th>
                                <td colspan="3">
                                    <form:input path="bookConcern" type="text" placeholder="请输入"/>
                                </td>
                                <th>刊号</th>
                                <td>
                                    <form:input path="publicationType" type="text" placeholder="请输入"/>
                                </td>
                            </tr>
                            <tr>
                                <th>发表日期</th>
                                <td>
                                    <form:input path="publicDate" type="datetime" class="easyui-datebox" />
                                </td>
                                <th>收录情况</th>
                                <td>
                                    <form:input  path="positional" type="text" placeholder="请输入"/>
                                </td>
                                <th>论文类别</th>
                                <td>
                                    <form:select  path="themeAddition" class="chzn-select">
                                        <option value="">请选择</option>
                                        <option value="A类" selected="selected">A类</option>
                                        <option value="会议收录">会议收录</option>
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <th>是否教改论文</th>
                                <td>
                                    <label class="label_td">
                                        <form:radiobutton path="status" value="1" name="thesis"/>是
                                    </label>
                                    <label class="label_td">
                                        <form:radiobutton path="status" value="0" name="thesis"/>否
                                    </label>
                                </td>
                                <th>教师姓名</th>
                                <td>
                                    <form:select  path="otherTeacher" class="chzn-select">
                                        <form:option value="">请选择</form:option>
                                        <c:forEach items="${teacher}" var="curr">
                                            <form:option value="${curr.cname}/${curr.username}">${curr.cname}/${curr.username}</form:option>
                                        </c:forEach>
                                    </form:select>
                                </td>
                                <th>所属系部</th>
                                <td>
                                    <form:select  path="schoolAcademy" class="chzn-select">
                                        <form:option value="" label ="--请选择--"/>
                                        <form:options  items="${schoolAcademy}" itemLabel="academyName" itemValue="academyName" />
                                    </form:select>
                                </td>
                            </tr>
                            <tr>
                                <th>备注</th>
                                <td colspan="5">
                                    <form:textarea path="memo" rows="3" placeholder="请输入"></form:textarea>
                                </td>
                            </tr>
                            <tr>
                                <th >证书或相关文件扫描上传</th>
                                <td>
                                    <input type="button" onclick="upload(2)" value="上传附件"/>
                                    <c:forEach items="${individualPerformance.commonDocuments}" var="d">
                                        <c:if test="${d.type==2}">
                                            ${d.documentName}
                                        </c:if>
                                    </c:forEach>
                                </td>
                            </tr>
                        </table>
                        <div class="popup_btns">
                            <input type="button" onclick="save()" class="btn" value="确定"/>
                            <input type="button" onclick="cancel()"  class="btn cancel-submit" value="取消"/>
                        </div>
                        </form:form>
                        <div id="searchFile" class="easyui-window" title="上传文件" closed="true" iconCls="icon-add" style="width:400px;height:200px">
                            <form id="form_file" method="post" enctype="multipart/form-data">
                                <table  border="0" align="center" cellpadding="0" cellspacing="0">
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
</div>
</div>
<!--弹出框end-->
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var config = {
        '.chzn-select'           : {search_contains:true},
        '.chzn-select-deselect'  : {allow_single_deselect:true},
        '.chzn-select-no-single' : {disable_search_threshold:10},
        '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
        '.chzn-select-width'     : {width:"100%"}
    }
    for (var selector in config) {
        $(selector).chosen(config[selector]);
    }
</script>
<!-- 下拉框的js -->
<script src="${pageContext.request.contextPath}/js/popup.js" type="text/javascript"></script>
</body>
</html>





