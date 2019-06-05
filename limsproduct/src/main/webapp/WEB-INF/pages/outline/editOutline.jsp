<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html >
<head>
    <meta name="decorator" content="iframe"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Calendar.js"></script>

    <!-- 下拉框的样式 -->
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link type="text/css" rel="stylesheet"  href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <link href="${pageContext.request.contextPath}/static_limsproduct/css/global_static.css" rel="stylesheet" type="text/css">
    <!-- 文件上传的样式和js -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>
    <script type="text/javascript">
        $(function(){
            $("#Pop_content").window({
                top: ($(window).width() - 800) * 0.5 ,
                left: ($(window).width() - 1000) * 0.5
            })
            $(".listTable").css('height',600);
        })
        function newwindor(){
            $("#Pop_content").show();
            //获取当前屏幕的绝对位置
            var topPos = window.pageYOffset;
            //使得弹出框在屏幕顶端可见
            $('#Pop_content').window({left:"0px", top:topPos+"px"});
            $("#Pop_content").window('open');

            var nameop ="";
            $.ajax({
                url:"${pageContext.request.contextPath}/outline/saveGenerateperations",
                data:{nameop:nameop},
                type:"POST",
                success:function(data){
                    $("#npo").html("");
                    $("#npo").append(data);
                }
            });
        }
        function addproject(){
            $("#projectitrms").attr("value","");

            var  projectitems="";
            var c=document.getElementById("Pop_content").getElementsByTagName("input");
            for(var i=0;i<c.length;i++){
                if(c[i].type=="checkbox" && c[i].checked){
                    projectitems+=c[i].value+",";
                }
            }

            $.post('${pageContext.request.contextPath}/operation/getuserprojectitems',{projectitems:projectitems},function(data){  //serialize()序列化
                $("#ds").after(data);
            });

            $("#projectitrms").attr("value",projectitems);
            alert("添加成功！");
            $('#Pop_content').window('close');
        }


        $(function(){
            $("#userSubmit").click(function(){
                var nameop = $("#nameop").val();
                $.ajax({
                    url:"${pageContext.request.contextPath}/outline/saveGenerateperations",
                    data:{nameop:nameop},
                    type:"POST",
                    success:function(data){
                        $("#npo").html("");
                        $("#npo").append(data);
                    }
                });
            });
        });
        function sunb(){

            var jie=[];
            $($("#commencementnaturemap option:selected")).each(function(){
                jie.push(this.value);
            });
            var sss=[];
            $($("#schoolMajorsa option:selected")).each(function(){
                sss.push(this.value);
            });
            var nameop = $("#labOutlineName").val();
            if(nameop!=null){
                return true;
            }else  if(sss.length>0){
                return true;
            }else    if(sss.length<0) {
                alert("请选择面向专业！");
                return false;
            }else  if(jie.length>0){
                return true;
            }else{
                alert("请选择课程性质！");
                return false;
            }
        }
        function del(s){
            $("#"+s+"").remove();
            var d=   $("#projectitrms").val();
            var a= d.replace(s+",","");
            $("#projectitrms").attr("value","");
            $("#projectitrms").attr("value",a);
        }
    </script>
    <script type="text/javascript">
        function uploadDocument(){

            //获取当前屏幕的绝对位置
            var topPos = window.pageYOffset;
            //使得弹出框在屏幕顶端可见
            $('#searchFile').window({left:"0px", top:topPos+"px"});
            $('#searchFile').window('open');

            $('#file_upload').uploadify({
                'formData':{id:1},    //传值
                'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',
                'uploader':'${pageContext.request.contextPath}/operation/uploaddnolinedocment;jsessionid=<%=session.getId()%>',		//提交的controller和要在火狐下使用必须要加的id
                buttonText:'选择文件',
                'onUploadSuccess' : function(file, data, response) {

                    $("#doc").append(data);
                },
                onQueueComplete : function(data) {
                    var ss="";

                    $("tr[id*='s_']").each(function(){
                        var eval1=$(this).attr("id");
                        var str1= eval1.substr(eval1.indexOf("_")+1 ,eval1.lenght);
                        var vals1=str1+"_"+$(this).attr("value");

                        ss+=str1+",";
                    });
                    $("#docment").attr("value",ss);
                    $('#searchFile').window('close');
                }
            });
        }

        function delectuploaddocment(s){
            if(confirm( '你真的要删除吗？ ')==false){return   false;}else{
                $.post('${pageContext.request.contextPath}/operation/delectdnolinedocment?idkey='+s+'',function(data){  //serialize()序列化
                    if(data=="ok"){
                        $("#s_"+s+"").empty();
                    }
                });
            }
        }
    </script>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.gif" />
    <style>
        .content-box fieldset {
            margin: 0 0 20px 0;
            background: #fff;
            border: none;
            border-radius: 0px;
            padding: 0px;
            left: 1%;
        }
        .content-box legend {
            padding: 0;
            display: block;
            width: 100%;
            position: relative;
            font-size: 14px;
            color: #7c99e5;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .introduce-box {
            width: 98% !important;
            position: relative;
        }
        .content-box table {
            margin: 0px;
            width: 100%;
            left:0;
        }
        fieldset th, fieldset td {

            text-align: left;
            border: 1px solid #ccc!important;
        }
        .content-box table tbody tr:nth-child(odd) td {
            background: none!important;
        }
        .introduce-box table td>input,
        .introduce-box table select{
            height:26px;
            margin: 0;
            box-sizing: border-box;
            box-shadow: none;
            width: 200px;
        }
        /*.introduce-box table select,*/
        /*.chzn-container{*/
            /*width:200px!important;*/
        /*}*/
        /*.introduce-box table input{*/
            /*width:100%!important;*/
        /*}*/
        .introduce-box table textarea{
            resize:none;
            box-shadow: none;
            box-sizing: border-box;
            margin: 0;
        }
        .introduce-box textarea {
            width: 100%;
        }
        .introduce-box input[type=button]{
            /*position: absolute;*/
            right: 0;
            /*top: 0;*/
        }
        .content-box td {
            padding: 15px 8px;
        }
        table td{
            text-align: unset!important;
        }
    </style>
</head>
<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.practicalTeaching.arrange"/></a></li>
            <li class="end"><a href="javascript:void(0)">新建大纲</a></li>
        </ul>
    </div>
</div>
<div class="right-content">
    <div id="TabbedPanels1" class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="title">
                    <div id="title"> 新建大纲	 </div>
                    <a class="btn-new" onclick="window.history.go(-1)">返回</a>
                </div>

                <div class="content-box" style="padding: 20px 10px;overflow: hidden;">
                    <form:form action="${pageContext.request.contextPath}/outline/saveOutline"
                               method="post" modelAttribute="outline" onsubmit="return sunb();">
                        <fieldset class="introduce-box tool-box1">
                            <legend>实验室基本信息<input type="hidden" value="" id="xsd"></legend>
                            <table id="listTable" width="50%" cellpadding="0" cellspacing="0" class="tablesorter">
                                <tr>
                                    <form:input type="hidden" path="id"></form:input>
                                    <th class="label">大纲名称<font color=red>*</font></th>
                                    <td class="label"><form:input path="labOutlineName" required="true" /></td>
                                    <th class="label">英文名称</th>
                                    <td class="label"><form:input path="enName" /> </td>
                                </tr>
                                <tr>
                                    <th class="label"><input type="hidden" id="docment" name="docment">课程名称</th>
                                    <td class="label">
                                        <form:select items="${courseInfoMap}" path="schoolCourseInfoByClassId.courseNumber" class="chzn-select"></form:select>
                                    </td>
                                    <th class="label">课程学分</th>
                                    <td class="label">
                                        <form:select path="cOperationOutlineCredit.id" items="${outlineCreditMap}"></form:select>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="label">开课学院</th>
                                    <td class="label">
                                        <form:select path="schoolAcademy.academyNumber" items="${academys}" class="chzn-select" required="true">
                                    </form:select>
                                    </td>
                                    <th class="label">课程性质<font color=red>*</font></th>
                                    <td class="label">
                                        <select id="commencementnaturemap" name="commencementnaturemap" class="chzn-select" multiple="multiple"  required="true">
                                            <c:forEach items="${commencementnaturemap}" var="s">
                                                <c:forEach items="${property}" var="curr">
                                                    <c:if test="${curr.id eq s.id }">
                                                        <option value="${s.id}" selected="selected">${s.CName}</option>
                                                    </c:if>
                                                </c:forEach>
                                                <option value="${s.id}">${s.CName}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="label">后续课程</th>
                                    <td class="label"><form:select
                                            path="schoolCourseInfoByFollowUpCourses.courseNumber"
                                            class="chzn-select">
                                        <option value="" selected="selected">无</option>
                                        <form:options items="${courseInfoMap}" />
                                    </form:select>
                                    </td>
                                    <th class="label">先修课程</th>
                                    <td class="label"><form:select
                                            path="schoolCourseInfoByFirstCourses.courseNumber" class="chzn-select">
                                        <option value="" selected="selected">无</option>
                                        <form:options items="${courseInfoMap}" />
                                    </form:select>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="label">任课教师/课程负责人<font color=red>*</font></th>
                                    <td class="label">
                                        <select id="operationOutlineTeacher" name="operationOutlineTeacher" class="chzn-select" multiple="multiple">
                                            <option value="-1">其他</option>
                                            <c:forEach items="${allTeachers}" var="d">
                                                <c:forEach items="${operationUser}" var="curr">
                                                    <c:if test="${d.username eq curr.username}">
                                                        <option value="${d.username}" selected="selected">${d.cname}</option>
                                                    </c:if>
                                                </c:forEach>
                                                <option value="${d.username}">${d.cname}</option>
                                            </c:forEach>
                                        </select>
                                        <form:input type="hidden" path="extraTeacher"  style="display: none" id="extraTeacher"></form:input>
                                    </td>
                                    <th class="label">面向专业<font color=red>*</font></th>
                                    <td class="label">
                                        <select id="schoolMajorsa" name="schoolMajorsa" class="chzn-select" multiple="multiple"  required="true">
                                            <c:forEach items="${schoolmajer}" var="d">
                                                <c:forEach items="${majorsEdit}" var="curr">
                                                    <c:if test="${d.majorNumber eq curr.majorNumber}">
                                                        <option value="${d.majorNumber}" selected="selected">${d.majorName}</option>
                                                    </c:if>
                                                </c:forEach>
                                                <option value="${d.majorNumber}">${d.majorName}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="label">使用教材</th>
                                    <td colspan="3"><form:textarea path="useMaterials" /></td>
                                </tr>
                            </table>
                        </fieldset>

                        <fieldset class="introduce-box">
                            <legend>课程简介及建议<input type="hidden" value="" id="xsd"></legend>
                            <table id="listTable" width="50%" cellpadding="0">
                                <tbody>
                                <tr><td colspan="3">课程简介<font color="red">*</font></td></tr>
                                <tr><td><form:textarea path="courseDescription" required="true" cols="104" /></textarea></td></tr>
                                <tr><td colspan="3">选课建议<font color="red">*</font></td></tr>
                                <tr><td><form:textarea path="coursesAdvice" required="true" cols="104" /></textarea></td></tr>
                                </tbody>
                            </table>
                        </fieldset>
                        <fieldset class="introduce-box">
                            <legend>课程任务和教学目标<input type="hidden" value="" id="xsd"></legend>
                            <table id="listTable" width="50%" cellpadding="0">
                                <tbody>
                                <tr>
                                    <td colspan="3">任务和教学目标<font color="red">*</font></td></tr>
                                <tr><td><form:textarea
                                        path="outlineCourseTeachingTarget" required="true" cols="104" />
                                </td>
                                </tr>
                                </tbody>
                            </table>
                        </fieldset>
                        <fieldset class="introduce-box">
                            <legend>课程基本内容及要求<input type="hidden" value="" id="xsd"></legend>
                            <table id="listTable" width="100%" cellpadding="0" cellspacing="0" class="tablesorter">
                                <tr><td colspan="3">课程基本内容及要求</td></tr>
                                <tr><td colspan="3">（一）课程基本内容</td></tr>
                                <tr><td colspan="10"><form:textarea path="basicContentCourse" cols="104" /></td></tr>
                                <tr><td colspan="3">（二）课程基本要求</td></tr>
                                <tr><td colspan="10"><form:textarea path="basicRequirementsCourse" cols="104" /></td></tr>
                            </table>
                        </fieldset>
                        <fieldset class="introduce-box">
                            <legend>作业及成绩评定<input type="hidden" value="" id="xsd"></legend>
                            <table>
                                <tr><td colspan="3">作业、考核成绩及成绩评定</td></tr>
                                <tr><td colspan="10"><form:textarea path="assResultsPerEvaluation" cols="104" /></td></tr>
                                <tr><td>课程任务和教学目标<font color="red">*</font></td></tr>
                                <tr><td><form:textarea path="outlineCourseTeachingTargetOver" cols="104" required="true" /></td></tr>
                            </table>
                        </fieldset>
                        <fieldset class="introduce-box">
                            <legend>文档名称<input type="hidden" value="" id="xsd"></legend>
                            <input type="button" onclick="uploadDocument()" value="上传附件"/>
                            <table>
                                <tr >
                                    <td>文档名称</td>
                                    <td>操作</td>
                                </tr>
                            </table>
                            <table>
                                <tbody id="doc"></tbody>
                            </table>
                        </fieldset>
                        <fieldset class="introduce-box">
                            <legend>项目名称<input type="hidden" value="" id="xsd"></legend>
                            <input id="u1436_input" type="button"
                                   onclick="newwindor();" value="添加">
                            <table>
                                <tr id="ds">
                                    <td>实验项目编号</td>
                                    <td>实验项目名称</td>
                                    <td>实验类型</td>
                                    <td>实验时数</td>
                                    <td>每组人数</td>
                                    <td>实验室</td>
                                    <td>备注（必做/选做）</td>
                                </tr>
                                <c:forEach items="${outline.operationItems}" var="s">
                                    <tr>
                                        <td>${s.lpCodeCustom}</td>
                                        <td>${s.lpName}</td>
                                        <td>${s.CDictionaryByLpCategoryMain.CName}</td>
                                        <td>${s.lpDepartmentHours}</td>
                                        <td>${s.lpStudentNumberGroup}</td>
                                        <td><c:forEach items="${s.labRooms}" var="a">${a.labRoomName}/</c:forEach></td>
                                        <td></td>
                                    </tr>
                                </c:forEach>
                            </table>
                            </table>
                        </fieldset>
                        <tr><td colspan="5"></td></tr>
                        <tr>
                            <input type="hidden" id="projectitrms" name="projectitrms" />
                            <td></td>
                            <td><input class="btn-big"  style="position: relative; float: right; margin-right: 1%;" type="submit" value="提交">
                            </td>
                            <td></td>
                            <td colspan="3"></td>
                        </tr>
                    </form:form>
                </div>
                <!-- </div> -->
                <!-- 下拉框的js -->
                <script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
                        type="text/javascript"></script>
                <script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
                        type="text/javascript" charset="utf-8"></script>
                <script type="text/javascript">
                    var config = {
                        '.chzn-select'           : {},
                        '.chzn-select-deselect'  : {allow_single_deselect:true},
                        '.chzn-select-no-single' : {disable_search_threshold:10},
                        '.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},
                        '.chzn-select-width'     : {width:"95%"}
                    }
                    for (var selector in config) {
                        $(selector).chosen(config[selector]);
                    }
                </script>

                <div id="Pop_content" class="easyui-window" closed="true"
                     modal="true" minimizable="true" title="添加实验项目"
                     style="width:800px;padding:10px;top: 50px">
                    <div class="tool-box">
                        <table >
                            <tr>
                                <td>实验项目名称:<input type="text" id="nameop" /></td>
                                <td></td>
                                <td>
                                    <input class="savebutton" id="saveContent" type="button" onclick="addproject();" value="添加" />
                                    <input type="button" id="userSubmit" name="userSubmit" value="查找" /></td>
                            </tr>
                        </table>
                    </div>

                    <div class="content-box">
                        <form:form id="clauseForm">
                            <table >
                                <tr>
                                    <thead>
                                    <tr id="s">
                                        <th width="10%">操作</th>
                                        <th width="15%">实验项目编号</th>
                                        <th width="65%">实验项目名称</th>
                                        <th width="10%">实验项目安排</th>
                                    </tr>
                                    </thead>
                                    <div>
                                        <tbody id="npo">
                                        <c:forEach items="${operationItem }" var="s">
                                            <tr>
                                                <td><input type="checkbox" value="${s.id }">
                                                </td>
                                                <td align="left">${s.itemNumber}</td>
                                                <td align="left">${s.itemName}</td>
                                                <td align="left">${s.COperationItemArrange.name}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </div>
                            </table>
                        </form:form>
                    </div>

                </div></table>

                <div id="searchFile" class="easyui-window" title="上传附件" closed="true" iconCls="icon-add" style="width:400px;height:200px">
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
</body>
<!-------------列表结束----------->
</html>