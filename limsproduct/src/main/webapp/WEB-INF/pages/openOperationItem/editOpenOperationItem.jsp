N<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="decorator" content="iframe"/>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
    <!-- 下拉框的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <script type="text/javascript">
        $(document).ready(function () {
            <c:set var="openTeachers" value=""/>
            <c:forEach items="${operationItem.openTeachers}" var="tu">
            <c:set var="openTeachers" value="${openTeachers},${tu.user.username}"/>
            </c:forEach>

            <c:set var="labUsers" value=""/>
            <c:forEach items="${operationItem.labUsers}" var="lu">
            <c:set var="labUsers" value="${labUsers},${lu.user.username}"/>
            </c:forEach>

            var openTeachers = "${openTeachers}";
            var labUsers = "${labUsers}";
            var academies = "${operationItem.lpCollege}";
            var Majors = "${operationItem.lpMajorFit}";
            var teacher = openTeachers.split(",");
            for (var i = 0; i < teacher.length; i++) {
                $("#teachers option[value='"+teacher[i]+"']").attr("selected","selected");
            }
            $("#teachers").chosen();
            $("#teachers").trigger('liszt:updated');
            var labUser = labUsers.split(",");
            for (var i = 0; i < labUser.length; i++) {
                $("#relatedLabUsers option[value='"+labUser[i]+"']").attr("selected","selected");
            }
            $("#relatedLabUsers").chosen();
            $("#relatedLabUsers").trigger('liszt:updated');
            var academy = academies.split(",");
            for (var i = 0; i < academy.length; i++) {
                $("#lpCollege option[value='"+academy[i]+"']").attr("selected","selected");
            }
            $("#lpCollege").chosen();
            $("#lpCollege").trigger('liszt:updated');
        });
        // 绑定组和个人关联
        $(function () {
            changeGroup();
            $("#CDictionaryByMinUnit").change(function () {
                changeGroup();
            });
            // $("#lpSetNumber").change(function () {
            //     $("#lpStudentNumberGroup").val(Math.ceil($("#lpStudentNumber").val()/$("#lpSetNumber").val()));
            // });
        });

        function changeGroup() {
            var minUnitId = "${minUnit.id}";
            var chooseId = $("#CDictionaryByMinUnit").val();
            if(chooseId - minUnitId == 0) {
                $("#group").show();
            }else{
                $("#group").hide();
            }
        }

        //2015-09-10新增，为了让新建页面中有添加材料和仪器设备
        function addMaterialRecord() {

            $("#editMaterialRecord").show();
            //获取当前屏幕的绝对位置
            var topPos = window.pageYOffset;
            //使得弹出框在屏幕顶端可见
            $('#editMaterialRecord').window({left:"100px", top:topPos+"px"});

            $("#editMaterialRecord").window('open');
        }
        // 保存材料添加
        function saveMaterialRecord() {
            document.form_material.action="${pageContext.request.contextPath}/openOperationItem/saveItemAssets?itemId=${itemId}&page=${page}";
            if($("#asset.id").val() == ""){
                alert("【耗材】选择耗材");
                return false;
            }
            if(isNaN($("#amount").val())){
                alert("【数量】填写数字");
                return false;
            }
            document.form_material.submit();
        }

        function editMaterialRecord(itemAssetId) {
            $.ajax({
                type:"GET",
                url:"${pageContext.request.contextPath}/openOperationItem/getItemAsset",
                data:{itemAssetId:itemAssetId},
                dataType:"json",
                success:function(data){
                    $("#itemAssetsId").val(itemAssetId);
                    $("#assetId").val(data.assetId);
                    $("#assetId").trigger("liszt:updated");
                    $('#assetId').chosen();
                    $("#amount").val(data.amount);
                    getSpec(document.getElementById("assetId"));

                    $("#editMaterialRecord").show();
                    //获取当前屏幕的绝对位置
                    var topPos = window.pageYOffset;
                    //使得弹出框在屏幕顶端可见
                    $('#editMaterialRecord').window({left:"100px", top:topPos+"px"});
                    $("#editMaterialRecord").window('open');
                }
            });
        }

        function addDevices() {
            var index = layer.open({
                type: 2,
                title: '添加项目设备',
                maxmin: true,
                shadeClose: true,
                area : ['700px' , '350px'],
                content: '${pageContext.request.contextPath }/operation/listItemDevices?itemId=${itemId}&currpage=1'
            });
            layer.full(index);
        }

        //全部选择或不选
        function checkAll() {
            if($("#check_all").attr("checked")) {
                $(":checkbox").attr("checked", true);
            }else {
                $(":checkbox").attr("checked", false);
            }
        }
        //保存实验项目设备
        function submitForm() {
            var flag = false;  //是否有checkbox被选中
            var category = $("#device_category").val();
            var ids = "";
            $("input[name='itemDevice']:checked").each(function(){
                ids += $(this).val()+",";
                flag = true;
            });

            if(flag) {
                if(ids.length > 0) {
                    ids = ids.substring(0, ids.length-1);  //去掉最后的逗号
                }
                if(category=="") {
                    alert("请选择类型！");
                    return false;
                }
                document.device_form.action="${pageContext.request.contextPath}/operation/saveItemDeviceLims?itemId=${operationItem.id}&category="+category+"&ids="+ids+"&status=1&page=${page}";
                document.device_form.submit();
            }
            else
            {
                alert("至少选择一个设备！");
            }
        }
        $(document).ready(function(){
            // 获取课程
            $.ajax({
                type: "POST",
                url:"${pageContext.request.contextPath}/operation/getAllCourseInfos",
                success:function(data){
                    $("#schoolCourseInfo").html(data.courseValue);
                    $("#schoolCourseInfo").append('<option value="${operationItem.schoolCourseInfo.courseNumber}" selected="selected">[${operationItem.schoolCourseInfo.courseNumber}]${operationItem.schoolCourseInfo.courseName}</option>');
                    $("#schoolCourseInfo").trigger("liszt:updated");
                }
            });
            if(${flagId==10}){
                alert("项目卡片保存成功！")
            };
        });
        //保存新建/编辑 的实验室项目卡
        function saveEditForm(){
            // if($("#systemSubject12").val()=="" || $("#systemSubject12").val()==null){
            //     alert("请选择所属学科！")
            // }else if($("#schoolCourseInfo").val()=="" || $("#schoolCourseInfo").val()==null){
            //     alert("请选择所属课程！")
            // }else{
            //     document.edit_form.submit();
            // }
                document.edit_form.submit();
        }
    </script>
    <style>
        fieldset label {
            display: block;
            float: left;
            font-weight: bold;
            height: 25px;
            line-height: 25px;
            margin: -5px 0 5px;
            padding-left: 10px;
            text-shadow: 0 1px 0 #fff;
            text-transform: uppercase;
            width: 90%;
        }
    </style>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.practicalTeaching.arrange"/></a></li>
            <li><a href="javascript:void(0)"><spring:message code="left.item.operation"/></a></li>
            <li class="end"><c:if test="${itemId ne -1}"><a href="javascript:void(0)">编辑</a></c:if><c:if test="${itemId eq -1}"><a href="javascript:void(0)">新建</a></c:if></li>
        </ul>
    </div>
</div>

<!-- 内容栏开始 -->
<div class="right-content">
    <div class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <!-- 标题 -->
                    <div class="title">
                        <div id="title"><c:if test="${itemId ne -1}">编辑</c:if><c:if test="${itemId eq -1}">新建</c:if>实验项目</div>
                    </div>

                    <!-- 表单 -->
                    <form:form name="edit_form" action="${pageContext.request.contextPath}/openOperationItem/saveOpenOperationItem?page=${page}" method="POST" modelAttribute="operationItem">
                        <div class="new-classroom">
                            <fieldset>
                                <form:hidden path="id"/>
                                <form:hidden path="CDictionaryByLpStatusCheck.id"/>
                                <form:hidden path="userByLpCheckUser.username"/>
                                <label>实验名称<font color="red">*</font> ：</label>
                                <form:input path="lpName" id="lpName" class="easyui-validatebox" required="true"/>
                            </fieldset>
                            <fieldset>
                                <label>实验性质<font color="red">*</font>：</label>
                                <form:select path="CDictionaryByLpCategoryNature.id" id="CDictionaryByLpCategoryNature" required="true">
                                    <form:option value="">- - - -请选择- - - -</form:option>
                                    <form:options items="${labProjectNature}" itemLabel="CName" itemValue="id"/>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>实验项目开发人：${creator.cname}</label>
                            </fieldset>
                            <fieldset>
                                <label>院系：${creator.schoolAcademy.academyName}</label>
                            </fieldset>
                            <fieldset style="height: 76px;">
                                <%--<label>实验项目开发人：${creator.cname}</label>--%>
                                <label>职称：</label>
                                <form:select path="CDictionaryByTitle.id" id="CDictionaryByTitle" required="true">
                                    <form:option value="">- - - -请选择- - - -</form:option>
                                    <form:options items="${titles}" itemLabel="CName" itemValue="id"/>
                                </form:select>

                                    <%--<label>院系：${creator.schoolAcademy.academyName}</label>--%>
                            </fieldset>
                            <fieldset>
                                <label>实验者总人数<font color="red">*</font>:(该实验项目一学年内上课人数：一人做两次实验按一人计算）</label>
                                <form:input path="lpStudentNumber" id="lpStudentNumber" class="easyui-validatebox" required="true"
                                            onkeyup="value=value.replace(/[^\d]/g,'') "  maxlength="6"
                                            placeholder="请输入大于零的整数"
                                />
                            </fieldset>
                            <fieldset>
                                <label>实验最小单位： </label>
                                    <form:select path="CDictionaryByMinUnit.id" id="CDictionaryByMinUnit" required="true">
                                        <form:option value="">- - - -请选择- - - -</form:option>
                                        <form:options items="${minUnits}" itemLabel="CName" itemValue="id"/>
                                    </form:select>

                                <div id="group">
                                    <label>实验组数：
                                    </label>
                                    <form:input path="lpSetNumber" id="lpSetNumber" class="easyui-validatebox"
                                                onkeyup="value=value.replace(/[^\d]/g,'') "   maxlength="3"
                                                placeholder="请输入大于零的整数"
                                    />
                                    <label>每组人数<font color="red">*</font>：
                                    </label>
                                    <form:input path="lpStudentNumberGroup" id="lpStudentNumberGroup" class="easyui-validatebox" required="true"
                                                onkeyup="value=value.replace(/[^\d]/g,'') " maxlength="2"
                                                placeholder="请输入大于零的整数"
                                    />
                                </div>
                            </fieldset>
                            <fieldset>
                                <label>实验类型<font color="red">*</font>：</label>
                                <form:select path="CDictionaryByLpCategoryApp.id" id="CDictionaryByLpCategoryApp" required="true">
                                    <form:option value="">- - - -请选择- - - -</form:option>
                                    <form:options items="${labProjectApp}" itemLabel="CName" itemValue="id"/>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>开放教师：</label>
                                <select name="teachers" id="teachers" class="chzn-select" multiple>
                                    <c:forEach items="${users}" varStatus="i" var="user">
                                        <option value="${user.username}">${user.cname}</option>
                                    </c:forEach>
                                </select>
                            </fieldset>
                            <fieldset>
                                <label>相关实验室人员：</label>
                                <select name="relatedLabUsers" id="relatedLabUsers" class="chzn-select" multiple>
                                    <c:forEach items="${users}" varStatus="i" var="user">
                                        <option value="${user.username}">${user.cname}</option>
                                    </c:forEach>
                                </select>
                            </fieldset>
                            <fieldset style="height: 71px;">
                                <%--<div>--%>
                                <label>开放学院：</label>
                                <select name="lpCollege" id="lpCollege" class="chzn-select" multiple>
                                    <c:forEach items="${academies}" varStatus="i" var="academy">
                                        <option value="${academy.academyNumber}">${academy.academyName}[${academy.academyNumber}]</option>
                                    </c:forEach>
                                </select>
                                <%--</div>--%>
                                <%--<div>--%>
                                <%--<label>开放专业：</label>--%>
                                <%--<form:select path="lpMajorFit" id="lpMajorFit" class="chzn-select" multiple="true">--%>
                                    <%--<c:forEach items="${chooseMajors}" varStatus="j" var="m">--%>
                                        <%--<form:option value="${m.majorNumber}" selected="selected">[${m.majorNumber}]${m.majorName}</form:option>--%>
                                    <%--</c:forEach>--%>
                                    <%--<c:forEach items="${majors}" varStatus="i" var="m">--%>
                                        <%--<form:option value="${m.majorNumber}">[${m.majorNumber}]${m.majorName}</form:option>--%>
                                    <%--</c:forEach>--%>
                                <%--</form:select>--%>
                                <%--</div>--%>
                                <%--<div>--%>
                                <%--<label>开放年级：</label>--%>
                                <%--<form:select path="CDictionaryByOpenGrade.id" id="CDictionaryByOpenGrade" required="true">--%>
                                    <%--<form:options items="${grades}" itemLabel="CName" itemValue="id"/>--%>
                                <%--</form:select>--%>
                                <%--</div>--%>
                                <%--<div>--%>
                                <%--<label>开放学期：</label>--%>
                                <%--<form:select path="CDictionaryByOpenTerm.id" id="CDictionaryByOpenTerm" required="true">--%>
                                    <%--<form:options items="${terms}" itemLabel="CName" itemValue="id"/>--%>
                                <%--</form:select>--%>
                                <%--</div>--%>
                            </fieldset>
                            <fieldset>
                                <label>开放专业：</label>
                                <form:select path="lpMajorFit" id="lpMajorFit" class="chzn-select" multiple="true">
                                    <c:forEach items="${chooseMajors}" varStatus="j" var="m">
                                        <form:option value="${m.majorNumber}" selected="selected">[${m.majorNumber}]${m.majorName}</form:option>
                                    </c:forEach>
                                    <c:forEach items="${majors}" varStatus="i" var="m">
                                        <form:option value="${m.majorNumber}">[${m.majorNumber}]${m.majorName}</form:option>
                                    </c:forEach>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>开放年级：</label>
                                <form:select path="CDictionaryByOpenGrade.id" id="CDictionaryByOpenGrade" required="true">
                                    <form:options items="${grades}" itemLabel="CName" itemValue="id"/>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>开放学期：</label>
                                <form:select path="CDictionaryByOpenTerm.id" id="CDictionaryByOpenTerm" required="true">
                                    <form:options items="${terms}" itemLabel="CName" itemValue="id"/>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>实验隶属课程<font color="red">*</font>：</label>
                                <form:select path="schoolCourseInfo.courseNumber" id="schoolCourseInfo" class="chzn-select">
                                    <form:option value="">加载中...</form:option>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>实验隶属大纲<font color="red">*</font>：</label>
                                <form:select path="operationOutline.id" id="operationOutline" required="true" class="chzn-select">
                                    <form:option value="">- - - -请选择- - - -</form:option>
                                    <form:options items="${operationOutlines}" itemLabel="labOutlineName" itemValue="id"/>
                                </form:select>
                            </fieldset>
                                <fieldset>
                                    <label>实验目标、要求：</label>
                                    <form:textarea path="itemAim" id="itemAim" cssStyle=" height:150px;"/>
                                </fieldset>
                            <fieldset>
                                <label>实验原理：</label>
                                <form:textarea path="itemTheory" id="itemTheory" cssStyle=" height:150px;"/>
                            </fieldset>
                            <fieldset>
                                <label>实验方法：</label>
                                <form:textarea path="itemMethod" id="itemMethod" cssStyle=" height:150px;"/>
                            </fieldset>
                            <fieldset>
                                <label>实验结果及分析：</label>
                                <form:textarea path="itemResult" id="itemResult" cssStyle=" height:150px;"/>
                            </fieldset>
                            <fieldset>
                                <label>注意事项：</label>
                                <form:textarea path="itemAttention" id="itemAttention" cssStyle=" height:150px;"/>
                            </fieldset>
                            <fieldset style="width: 21%;">
                                <label>实验结果形式：</label>
                                <form:select path="CDictionaryByItemResultType.id" id="CDictionaryByItemResultType">
                                    <form:option value="">- - - -请选择- - - -</form:option>
                                    <form:options items="${itemResultTypes}" itemLabel="CName" itemValue="id"/>
                                </form:select>
                            </fieldset>
                            <fieldset style="width: 21%;">
                                <label>实验预算：</label>
                                <form:input path="itemBudget" id="itemBudget" class="easyui-validatebox" required="true"
                                            onkeyup="if(isNaN(value))execCommand('undo')" maxlength="6"
                                            placeholder="请输入大于零的数"
                                />
                            </fieldset>
                            <fieldset>
                                <label>计划周次*：</label>
                                <form:input path="planWeek" id="planWeek" required="true" />
                            </fieldset>
<%--                            <fieldset>--%>
<%--                                <label>计划周次：</label>--%>
<%--                                <form:select path="planWeek" id="planWeek">--%>
<%--                                    <option value="">- - - -请选择- - - -</option>--%>
<%--                                <c:forEach  var="i" begin="1" end="30" varStatus="status">--%>
<%--                                    <option value="第${status.index}周">第${status.index}周</option>--%>
<%--                                </c:forEach>--%>
<%--                                </form:select>--%>
<%--                            </fieldset>--%>
                            <!-- 实验项目基本信息提交后出现的东西 -->
                            <c:if test="${itemId eq -1}">
                                <label style="padding: 2%; float: right;">
                                    <font color=red>实验项目保存后，可对思考题、实验材料、设备进行编辑。</font>
                                </label>
                            </c:if>
                            <c:if test="${itemId != -1 && operationItem.CDictionaryByLpStatusCheck.CNumber == 1}"><!-- 草稿状态 -->
                            <fieldset>
                                <label>思考题：
                                    <c:if test="${operationItem.itemQuestionDocument != null}">
                                    <a onclick="javascript:void(0)" href="${pageContext.request.contextPath}/${operationItem.itemQuestionDocument.documentUrl}">
                                        ${operationItem.itemQuestionDocument.documentName} 点击下载
                                        </a>
                                    </c:if>
                                    <input type="button" value="上传" onclick="addQuestion();"/></label>
                            </fieldset>
                            <fieldset style="float:none; width: 96%;">
                                <label>实验物资</label>
                                <div class="TabbedPanels">
                                    <div class="TabbedPanelsContentGroup">
                                        <div class="TabbedPanelsContent">
                                            <input type="button" onclick="addMaterialRecord();" value="添加" />
                                            <div class="content-box">
                                                <table>
                                                    <thead>
                                                    <tr>
                                                        <th>序号</th>
                                                        <th>物资名称</th>
                                                        <th>物资类型</th>
                                                        <th>型号/规格</th>
<%--                                                        <th>单位</th>--%>
                                                        <th>数量</th>
<%--                                                        <th>金额（元）</th>--%>
                                                        <th>操作</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach items="${operationItem.itemAssets}" var="curr" varStatus="i">
                                                        <tr>
                                                            <td>${i.count}</td>
                                                            <td>${curr.asset.chName}</td>
                                                            <td>${materialKindMap[curr.asset.category]}</td>
                                                            <td>${curr.asset.specifications}</td>
<%--                                                            <td>${curr.asset.unit}</td>--%>
                                                            <td>${curr.amount}</td>
<%--                                                            <td>${curr.lpmrAmount}</td>--%>
                                                            <td>
                                                                <a href="javascript:void(0)" onclick="editMaterialRecord(${curr.id});">编辑</a>
                                                                <a href="${pageContext.request.contextPath}/openOperationItem/deleteItemAsset?itemAssetId=${curr.id}&itemId=${itemId}&page=${page}" onclick="return confirm('确认要删除吗？')">删除</a>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                            <fieldset style="width:96%; float: none;">
                                <!-- 设备仪器开始  -->
                                <label>仪器设备</label>
                                <div class="TabbedPanels">
                                    <div class="TabbedPanelsContentGroup">
                                        <div class="TabbedPanelsContent">
                                            <input type="button" onclick="addDevices();" value="批量添加"/>
                                            <div class="content-box">
                                                <table>
                                                    <thead>
                                                    <tr>
                                                        <th>序号</th>
                                                        <th>设备编号</th>
                                                        <th>设备名称</th>
                                                        <th>所属<spring:message code="all.trainingRoom.labroom"/></th>
                                                        <th>规格</th>
                                                        <th>型号</th>
                                                        <th>单价</th>
                                                        <th>操作</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach items="${listItemDevice}" var="curr" varStatus="i">
                                                        <tr>
                                                            <td>${i.count}</td>
                                                            <td>${curr.labRoomDevice.schoolDevice.deviceNumber}</td>
                                                            <td>${curr.labRoomDevice.schoolDevice.deviceName}</td>
                                                            <td>${curr.labRoomDevice.labRoom.labRoomName}</td>
                                                            <td>${curr.labRoomDevice.schoolDevice.deviceFormat}</td>
                                                            <td>${curr.labRoomDevice.schoolDevice.devicePattern}</td>
                                                            <td>${curr.labRoomDevice.schoolDevice.devicePrice}</td>
                                                            <td>
                                                                <a href="${pageContext.request.contextPath}/operation/deleteItemDeviceLims?itemDeviceId=${curr.id}&itemId=${operationItem.id}&status=1&page=${page}" onclick="return confirm('确认要删除吗？')">删除</a>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                            </c:if>
                            <!-- 设备仪器结束  -->
                        </div>
                        <div class="moudle_footer">
                            <div class="submit_link">
                                <a class="btn btn-return" type="button" href="${pageContext.request.contextPath}/openOperationItem/listOpenOperationItem?currpage=1&status=1&orderBy=9">返回</a>
                                <input class="btn btn-big" type="submit" value="保存" onclick="saveEditForm();">
                            </div>
                        </div>
                    </form:form>
                    <!-- 添加耗材弹框开始 -->
                    <div id="editMaterialRecord" class="easyui-window" closed="true" modal="true" minimizable="true" title="添加耗材" style="width: 600px;height: 500px;padding: 20px">
                        <div class="content-box">
                            <form:form name="form_material" method="post" modelAttribute="itemAssets" >
                                <table class="color_tb">
                                    <tr>
                                        <td>耗材</td>
                                        <td>
<%--                                            <input name="id" id="id" type="hidden" value="${itemAssets.id}"/>--%>
                                            <form:hidden path="id" id="itemAssetsId"/>
                                            <form:select path="asset.id" id="assetId" class="chzn-select">
                                                <form:option value="" label="请选择"/>
                                                <c:forEach items="${assets}" var="curr">
                                                    <form:option value="${curr.id}">${curr.chName}[${curr.specifications}]</form:option>
                                                </c:forEach>
                                            </form:select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>数量</td><td><form:input path="amount" id="amount" /></td>
                                    </tr>
                                </table>
                                <div class="moudle_footer">
                                    <div class="submit_link">
                                        <input class="btn" id="save" type="button" onclick="saveMaterialRecord();" value="确定">
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                    <!-- 添加材料弹框结束 -->

                    <!-- 添加思考题弹框结束 -->
                    <div id="searchFile" class="easyui-window" title="上传思考题" closed="true" iconCls="icon-add" style="width:500px;height:300px">
                        <form id="form_file_ori" name="form_file_ori" method="post"
                              enctype="multipart/form-data">
                            <table  border="0" align="center" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td>
                                        <div id="queue"></div> <input id="file_upload_ori"name="file_upload_ori" type="file" multiple="true">
                                        <input type="button" onclick="saveQuestion()" value="上传" />
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                    <!-- 添加思考题弹框结束 -->

                    <!-- 下拉框的js -->editOpenOperationItem
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
    function addQuestion() {
        var topPos=window.pageYOffset;
        $("#searchFile").window({left: "100px", top: topPos + "px"});
        $("#searchFile").window('open');
    }

    function saveQuestion() {
        var formData = new FormData();
        formData.append("myfile", document.getElementById("file_upload_ori").files[0]);
        //将要所有要添加的数据传给后台处理
        $.ajax({
            url:"${pageContext.request.contextPath}/openOperationItem/uploadQuestion?id=${itemId}",
            type:"POST",
            async:false,
            cache: false,
            contentType: false,
            processData: false,
            dataType: 'text',
            data:formData,
            success:function(saveResult)
            {
                alert(saveResult);
                window.location.reload();
            },
            error:function () {
                alert("请求错误");
            }
        });
    }

    function getSpec(osel){
        var content = osel.options[osel.selectedIndex].text;

        var x,y;
        for(var i = 0; i<content.length; i++){
            if(content[i] == '['){
                x=i;
            }
            if(content[i] == ']'){
                y=i;
                break;
            }
        }
        var unit  = content.substring(x,y+1);
        unit = unit.replace(/[^a-z^A-Z]/g,'');
        $("#unit").html(unit);
    }

    function getSimpleSpec(unit) {
        unit = unit.replace(/[^a-z^A-Z]/g,'');
        return unit;
    }

    for (var i = 1; i <= ${fn:length(operationItem.itemAssets)}; i++) {
        var old = $("#unit" + i).html();
        $("#unit" + i).html(getSimpleSpec(old));
    }
</script>
</body>
</html>
