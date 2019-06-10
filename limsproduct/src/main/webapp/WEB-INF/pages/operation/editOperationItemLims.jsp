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
        //2015-09-10新增，为了让新建页面中有添加材料和仪器设备
        function addMaterialRecord() {
            $("#lpmrId").val(null);
            $("#lpmr_name").val("");
            $("#lpmr_category").val("");
            $("#lpmr_model").val("");
            $("#lpmr_unit").val("");
            $("#lpmr_quantity").val("");
            $("#lpmr_amount").val("");
            $("#lpmr_remark").val("");

            $("#editMaterialRecord").show();
            //获取当前屏幕的绝对位置
            var topPos = window.pageYOffset;
            //使得弹出框在屏幕顶端可见
            $('#editMaterialRecord').window({left:"100px", top:topPos+"px"});

            $("#editMaterialRecord").window('open');
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
                    $("#assetId").val(data.assetId);
                    $("#assetId").trigger("liszt:updated");
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
        // 保存材料添加
        function saveMaterialRecord() {
            document.form_material.action="${pageContext.request.contextPath}/openOperationItem/saveItemAssets?itemId=${itemId}&page=${page}&type=2";
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
            if ($("#lpCodeCustom").val()=="" || $("#lpCodeCustom").val() == null) {
                alert("请填写项目编号！");
                return false;
            }
            if($("#systemSubject12").val()=="" || $("#systemSubject12").val()==null){
                alert("请选择所属学科！");
                return false;
            }else if($("#schoolCourseInfo").val()=="" || $("#schoolCourseInfo").val()==null){
                alert("请选择所属课程！");
                return false;
            }else{
                document.edit_form.submit();
            }
        };
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
                    <form:form name="edit_form" action="${pageContext.request.contextPath}/operation/saveOperationItemLims?page=${page}" method="POST" modelAttribute="operationItem">
                        <div class="new-classroom">
                            <fieldset>
                                <form:hidden path="id"/>
                                <form:hidden path="CDictionaryByLpStatusCheck.id"/>
                                <form:hidden path="userByLpCheckUser.username"/>
                                <label>实验名称<font color="red">*</font> ：</label>
                                <form:input path="lpName" id="lpName" class="easyui-validatebox" required="true"/>
                            </fieldset>
                            <fieldset>
                                <label>实验编号<font color="red">*</font>：</label>
                                <form:input path="lpCodeCustom" id="lpCodeCustom" class="easyui-validatebox"/>
                            </fieldset>
                            <fieldset>
                                <label>所属学期<font color="red">*</font>：</label>
                                <form:select path="schoolTerm.id" id="schoolTermId" required="true">
                                    <form:options items="${schoolTerms}" itemLabel="termName" itemValue="id"/>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>所属学科<font color="red">*</font>：</label>
                                <form:select path="systemSubject12.SNumber" id="systemSubject12" class="chzn-select">
                                    <form:option value="">请选择</form:option>
                                    <c:forEach items="${subjects}" var="s">
                                        <form:option value="${s.SNumber}">[${s.SNumber}]${s.SName}</form:option>
                                    </c:forEach>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>所属实验室：</label>
                                <select id="labRoomId" name="labRoomId" class="chzn-select" multiple="multiple" >
                                    <c:forEach items="${labRooms}" var="d">
                                        <c:forEach items="${labRoomAll}" var="curr">
                                            <c:if test="${curr.id eq d.id }">
                                                <option value="${d.id}" selected="selected">${d.labRoomName}</option>
                                            </c:if>
                                        </c:forEach>
                                        <option value="${d.id}">${d.labRoomName}</option>
                                    </c:forEach>
                                </select>
                            </fieldset>
                            <fieldset>
                                <label>实验学时数<font color="red">*</font>：（完成该项目所需实际学时）</label>
                                <form:input path="lpDepartmentHours" id="lpDepartmentHours" class="easyui-validatebox" required="true"
                                            onkeyup="value=value.replace(/[^\d]/g,'') " maxlength="4"
                                            placeholder="请输入大于零的整数"
                                />
                            </fieldset>
                            <fieldset>
                                <label>实验总学时：（实验项目对应课程的实验总学时，包括课内、课外）</label>
                                <form:input path="lpDepartmentHoursTotal" id="lpDepartmentHoursTotal" class="easyui-validatebox"
                                            onkeyup="value=value.replace(/[^\d]/g,'') " maxlength="4"
                                            placeholder="请输入大于零的整数"
                                />
                            </fieldset>
                            <fieldset>
                                <label>课程总学时：（实验项目对应课程总学时）</label>
                                <form:input path="lpCourseHoursTotal" id="lpCourseHoursTotal" class="easyui-validatebox"
                                            onkeyup="value=value.replace(/[^\d]/g,'') "  maxlength="4"
                                            placeholder="请输入大于零的整数"
                                />
                            </fieldset>
                            <fieldset>
                                <label>实验者人数<font color="red">*</font>:(该实验项目一学年内上课人数：一人做两次实验按一人计算）</label>
                                <form:input path="lpStudentNumber" id="lpStudentNumber" class="easyui-validatebox" required="true"
                                            onkeyup="value=value.replace(/[^\d]/g,'') "  maxlength="6"
                                            placeholder="请输入大于零的整数"
                                />
                            </fieldset>
                            <fieldset>
                                <label>实验组数：（该实验室可开出本实验项目的设备人数）</label>
                                <form:input path="lpSetNumber" id="lpSetNumber" class="easyui-validatebox"
                                            onkeyup="value=value.replace(/[^\d]/g,'') "   maxlength="3"
                                            placeholder="请输入大于零的整数"
                                />
                            </fieldset>
                            <fieldset>
                                <label>每组人数<font color="red">*</font>：（教学实验项目中在每套仪器设备上完成本实验项目的人数）</label>
                                <form:input path="lpStudentNumberGroup" id="lpStudentNumberGroup" class="easyui-validatebox" required="true"
                                            onkeyup="value=value.replace(/[^\d]/g,'') " maxlength="2"
                                            placeholder="请输入大于零的整数"
                                />
                            </fieldset>
                            <fieldset style="width:21%">
                                <label>实验类别<font color="red">*</font>：</label>
                                <form:select path="CDictionaryByLpCategoryMain.id" id="CDictionaryByLpCategoryMain" required="true">
                                    <form:option value="">- - - -请选择- - - -</form:option>
                                    <form:options items="${labProjectMain}" itemLabel="CName" itemValue="id"/>
                                </form:select>
                            </fieldset>
                            <fieldset style="width:21%">
                                <label>实验类型<font color="red">*</font>：</label>
                                <form:select path="CDictionaryByLpCategoryApp.id" id="CDictionaryByLpCategoryApp" required="true">
                                    <form:option value="">- - - -请选择- - - -</form:option>
                                    <form:options items="${labProjectApp}" itemLabel="CName" itemValue="id"/>
                                </form:select>
                            </fieldset>
                            <fieldset style="width:21%">
                                <label>实验性质<font color="red">*</font>：</label>
                                <form:select path="CDictionaryByLpCategoryNature.id" id="CDictionaryByLpCategoryNature" required="true">
                                    <form:option value="">- - - -请选择- - - -</form:option>
                                    <form:options items="${labProjectNature}" itemLabel="CName" itemValue="id"/>
                                </form:select>
                            </fieldset>
                            <fieldset style="width:21%">
                                <label>实验者类型<font color="red">*</font>：</label>
                                <form:select path="CDictionaryByLpCategoryStudent.id" id="CDictionaryByLpCategoryStudent" required="true">
                                    <form:option value="">- - - -请选择- - - -</form:option>
                                    <form:options items="${labProjectStudent}" itemLabel="CName" itemValue="id"/>
                                </form:select>
                            </fieldset>
                            <fieldset style="width: 21%;">
                                <label>变动状态：</label>
                                <form:select path="CDictionaryByLpStatusChange.id" id="CDictionaryByLpStatusChange">
                                    <form:option value="">- - - -请选择- - - -</form:option>
                                    <form:options items="${labProjectChange}" itemLabel="CName" itemValue="id"/>
                                </form:select>
                            </fieldset>
                            <fieldset style="width: 21%;">
                                <label >开放实验：</label>
                                <form:select path="CDictionaryByLpCategoryPublic.id" id="CDictionaryByLpCategoryPublic">
                                    <form:option value="">- - - -请选择- - - -</form:option>
                                    <form:options items="${labProjectPublic}" itemLabel="CName" itemValue="id"/>
                                </form:select>
                            </fieldset>
                            <fieldset style="width: 21%;">
                                <label>获奖等级：</label>
                                <form:select path="CDictionaryByLpCategoryRewardLevel.id" id="CDictionaryByLpCategoryRewardLevel">
                                    <form:option value="">- - - -请选择- - - -</form:option>
                                    <form:options items="${labProjectRewardLevel}" itemLabel="CName" itemValue="id"/>
                                </form:select>
                            </fieldset>
                            <fieldset style="width: 21%;">
                                <label>实验要求：</label>
                                <form:select path="CDictionaryByLpCategoryRequire.id" id="CDictionaryByLpCategoryRequire">
                                    <form:option value="">- - - -请选择- - - -</form:option>
                                    <form:options items="${labProjectRequire}" itemLabel="CName" itemValue="id"/>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>所属专业：</label>
                                <form:select path="schoolMajor.majorNumber" id="systemMajor12" class="chzn-select">
                                    <form:option value="">- - - -请选择- - - -</form:option>
                                    <c:forEach items="${majors}" var="m">
                                        <form:option value="${m.majorNumber}">[${m.majorNumber}]${m.majorName}</form:option>
                                    </c:forEach>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>所属课程<font color="red">*</font>：</label>
                                <form:select path="schoolCourseInfo.courseNumber" id="schoolCourseInfo" class="chzn-select">
                                    <form:option value="">加载中...</form:option>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>主讲教师：</label>
                                <form:select path="userByLpTeacherSpeakerId.username" id="teacher_speaker" class="chzn-select">
                                    <form:option value="${operationItem.userByLpTeacherSpeakerId.username }">${operationItem.userByLpTeacherSpeakerId.cname }</form:option>
                                    <c:forEach items="${users}" var="user">
                                        <form:option value="${user.username}">[${user.username}]${user.cname}</form:option>
                                    </c:forEach>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>辅导教师：</label>
                                <form:select path="userByLpTeacherAssistantId.username" id="teacher_assistant" class="chzn-select" >
                                    <form:option value="${operationItem.userByLpTeacherAssistantId.username }">${operationItem.userByLpTeacherAssistantId.cname }</form:option>
                                    <c:forEach items="${users}" var="user">
                                        <form:option value="${user.username}">[${user.username}]${user.cname}</form:option>
                                    </c:forEach>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>指导书名称：</label>
                                <form:input path="lpGuideBookTitle" id="lpGuideBookTitle" class="easyui-validatebox"/>
                            </fieldset>
                            <fieldset>
                                <label>编者：</label>
                                <form:input path="lpGuideBookAuthor" id="lpGuideBookAuthor" class="easyui-validatebox"/>
                            </fieldset>
                            <fieldset>
                                <label>考核方法：</label>
                                <form:input path="lpAssessmentMethods" id="lpAssessmentMethods" class="easyui-validatebox"/>
                            </fieldset>
                            <%--<fieldset>
                                <label>实验指导书：</label>
                                <form:select path="CDictionaryByLpCategoryGuideBook.id" id="CDictionaryByLpCategoryGuideBook">
                                    <form:option value="">- - - -请选择- - - -</form:option>
                                    <form:options items="${labProjectGuideBook}" itemLabel="CName" itemValue="id"/>
                                </form:select>
                            </fieldset>--%>
                            <fieldset>
                                <label>计划开课周次：</label>
                                <form:select path="planWeek" id="planWeek" class="chzn-select" multiple="true">
                                    <c:forEach begin="1" end="18" step="1" var="curr">
                                        <c:set var="isWeek" value="0"></c:set>
                                        <c:forTokens items="${operationItem.planWeek}" delims="," var="currWeek">
                                            <c:if test="${currWeek eq curr}">
                                                <c:set var="isWeek" value="1"></c:set>
                                            </c:if>
                                        </c:forTokens>
                                        <c:if test="${isWeek eq 1}">
                                            <form:option value="${curr}" selected="selected">第${curr}周</form:option>
                                        </c:if>
                                        <c:if test="${isWeek eq 0}">
                                            <form:option value="${curr}">第${curr}周</form:option>
                                        </c:if>
                                    </c:forEach>
                                </form:select>
                            </fieldset>
                            <fieldset>
                                <label>项目简介：</label>
                                <form:textarea path="lpIntroduction" id="lpIntroduction" cssStyle=" height:150px;"/>
                            </fieldset>
                            <fieldset>
                                <label>课前准备：</label>
                                <form:textarea path="lpPreparation" id="lpPreparation" cssStyle=" height:150px;"/>
                            </fieldset>
                            <!-- 实验项目基本信息提交后出现的东西 -->
                            <c:if test="${itemId eq -1}">
                                <label style="padding: 2%; float: right;">
                                    <font color=red>实验项目保存后，可对实验材料、设备进行编辑。</font>
                                </label>
                            </c:if>
                            <c:if test="${itemId != -1 && operationItem.CDictionaryByLpStatusCheck.CNumber == 1}"><!-- 草稿状态 -->
                            <fieldset style="float:none; width: 96%;">
                                <label>材料使用</label>
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
                                                        <th>数量</th>
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
                                                            <td>${curr.amount}<div id="unit${i.count}" style="display: inline"></div></td>
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
                                <a class="btn btn-return" type="button" href="${pageContext.request.contextPath}/operation/listOperationItemLims?currpage=1&status=1&orderBy=9">返回</a>
                                <input class="btn btn-return" type="submit" value="保存" onclick="saveEditForm();return false;">
                            </div>
                        </div>
                    </form:form>
<%--                    <!-- 添加材料弹框开始 -->--%>
<%--                    <div id="editMaterialRecord" class="easyui-window" closed="true" modal="true" minimizable="true" title="材料使用记录" style="width: 600px;height: 500px;padding: 20px">--%>
<%--                        <div class="content-box">--%>
<%--                            <form:form name="form_material" method="post" modelAttribute="operationItemMaterialRecord" >--%>
<%--                                <table class="color_tb">--%>
<%--                                    <tr>--%>
<%--                                        <td>材料名称</td><td><form:input path="lpmrName" id="lpmr_name" /><form:hidden path="lpmrId" /><form:hidden path="operationItem.id" id="item_id" value="${operationItem.id}"/></td>--%>
<%--                                    </tr>--%>
<%--                                    <tr>--%>
<%--                                        <td>材料类型</td>--%>
<%--                                        <td>--%>
<%--                                            <form:select path="CDictionary.id" id="lpmr_category">--%>
<%--                                                <form:options items="${categoryMaterialRecordMain}" itemLabel="CName" itemValue="id"/>--%>
<%--                                            </form:select>--%>
<%--                                        </td>--%>
<%--                                    </tr>--%>
<%--                                    <tr>--%>
<%--                                        <td>型号/规格</td><td><form:input path="lpmrModel" id="lpmr_model" /></td>--%>
<%--                                    </tr>--%>
<%--                                    <tr>--%>
<%--                                        <td>单位</td><td><form:input path="lpmrUnit" id="lpmr_unit" /></td>--%>
<%--                                    </tr>--%>
<%--                                    <tr>--%>
<%--                                        <td>数量</td><td><form:input path="lpmrQuantity" id="lpmr_quantity" /></td>--%>
<%--                                    </tr>--%>
<%--                                    <tr>--%>
<%--                                        <td>金额</td><td><form:input path="lpmrAmount" id="lpmr_amount" /></td>--%>
<%--                                    </tr>--%>
<%--                                    <tr>--%>
<%--                                        <td>备注</td><td><form:textarea path="lpmrRemark" id="lpmr_remark" /></td>--%>
<%--                                    </tr>--%>
<%--                                </table>--%>
<%--                                <div class="moudle_footer">--%>
<%--                                    <div class="submit_link">--%>
<%--                                        <input class="btn" id="save" type="button" onclick="saveMaterialRecord();" value="确定">--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                            </form:form>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                    <!-- 添加材料弹框结束 -->--%>
                    <!-- 添加耗材弹框开始 -->
                    <div id="editMaterialRecord" class="easyui-window" closed="true" modal="true" minimizable="true" title="添加耗材" style="width: 600px;height: 500px;padding: 20px">
                        <div class="content-box">
                            <form:form name="form_material" method="post" modelAttribute="itemAssets" >
                                <table class="color_tb">
                                    <tr>
                                        <td>耗材</td>
                                        <td>
                                            <form:hidden path="id" id="itemAssetsId"/>
                                            <form:select path="asset.id" id="assetId" onChange="getSpec(this)" class="chzn-select">
                                                <form:option value="" label="请选择"/>
                                                <c:forEach items="${assets}" var="curr">
                                                    <form:option value="${curr.id}">${curr.chName}[${curr.specifications}]</form:option>
                                                </c:forEach>
                                            </form:select>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>数量</td><td><form:input path="amount" id="amount" /><label id="unit"></label></td>
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
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
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
        //$("#unit").html(unit);
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
