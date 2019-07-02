<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp" />

<head>
    <meta name="decorator" content="iframe" />
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/iconFont.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
    <!-- 下拉的样式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
    <!-- 下拉的样式结束 -->
    <!-- 弹窗 -->
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/layer-v2.2/layer/extend/layer.ext.js" type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
    <script type="text/javascript">
        // 分页
        function targetUrl(url) {
            document.form.action = url;
            document.form.submit();
        }
        // 全选/取消全选
        function checkAll() {
            if($("#check_all").attr("checked")) {
                $(":checkbox").attr("checked", true);
            }else {
                $(":checkbox").attr("checked", false);
            }
        }

        // 提交授权名单
        function submitAuthorized(lab_id) {
            var startDate = $("#startDate").val();
            var endDate = $("#endDate").val();
            var startTime = $("#startTime").val();
            var endTime = $("#endTime").val();
            if(startDate==null||startDate==""){
                alert("请选择开始日期")
                return false;
            }
            else if(endDate==null||endDate==""){
                alert("请选择结束日期")
                return false;
            }
            else if(startTime==null||startTime==""){
                alert("请选择开始时间")
                return false;
            }
            else if(endTime==null||endTime==""){
                alert("请选择结束时间")
                return false;
            }

            var array = new Array();
            var flag=false; //判断是否一个未选
            $("input[name='items']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox
                if ($(this).is(':checked')) { //判断是否选中
                    flag = true; //只要有一个被选择 设置为 true
                }
            })
            if (flag) {
                $("input[name='items']:checkbox").each(function() { //遍历所有的name为selectFlag的 checkbox
                    if ($(this).is(':checked')) { //判断是否选中
                        array.push($(this).val()); //将选中的值 添加到 array中
                    }
                })
                $.ajax({
                    url:'${pageContext.request.contextPath}/labRoom/saveLabRoomAuthorized?roomId='+ lab_id +'&array='+array ,
                    data: {'startDate':startDate,'endDate':endDate,'startTime':startTime,'endTime':endTime},
                    type:'POST',
                    success:function(){
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        parent.layer.close(index);
                    }
                });
            }else {
                alert("请至少选择一条记录");
                return false;
            }

        }

        // 查询
        function searchAll() {
            document.queryForm.submit();
        }
        // 取消查询
        function cancelSearch() {
            window.location.href="${pageContext.request.contextPath}/labRoom/layerAddLabRoomAuthorized?currpage=1&lab_id=${lab_id}";
        }
    </script>
</head>
<body>
<!-- 导航栏 -->
<div id="TabbedPanels1" class="TabbedPanels">
    <div class="TabbedPanelsContentGroup">
        <div class="TabbedPanelsContent0">
            <!-- 查询、导出、打印 -->
            <div class="content-box">
                <div class="tool-box" style="overflow: initial;">
                    <form name="queryForm" method="Post" action="${pageContext.request.contextPath}/labRoom/layerAddLabRoomAuthorized?currpage=1&lab_id=${lab_id}">
                        <ul>
                            <li><input name="search" placeholder='请输入姓名或学号' />
                            </li>
                            <li><input type="button" onclick="searchAll(); return false;" value="查询">
                                <input class="cancel-submit" type="button" onclick="cancelSearch(); return false;" value="取消查询">
                            </li>
                        </ul>
                    </form>
                </div>
            </div>

            <div class="content-box">
                <form name="form" action="${pageContext.request.contextPath}/labRoom/saveLabRoomAuthorized?roomId=${labRoom.id}&type=${type}" method="post" onsubmit="return checkAuthorized()">
                    <div style="float:left;">
                        <span class="f14" style="float:left;">授权时间设置&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开始日期：</span>
                        <input id="startDate" name="startDate" class="Wdate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:100px; margin-top: 1px;" readonly />
                    </div>
                    <div style="float:left;">
                        <span class="f14" style="float:left;">结束日期：</span>
                        <input id="endDate" name="endDate" class="Wdate" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:100px; margin-top: 1px;" readonly />
                    </div>
                    <div style="float:left;">
                        <span class="f14" style="float:left;">开始时间：</span>
                        <input id="startTime" name="startTime" class="Wdate" type="text" onclick="WdatePicker({dateFmt:'HH:mm'})" style="width:100px; margin-top: 1px;" readonly />
                    </div>
                    <div style="float:left;">
                        <span class="f14" style="float:left;">结束时间：</span>
                        <input id="endTime" name="endTime" class="Wdate" type="text" onclick="WdatePicker({dateFmt:'HH:mm'})" style="width:100px; margin-top: 1px;" readonly />
                    </div>
                    <input class="search r btn btn-new" type="button" onclick="submitAuthorized('${lab_id}');return false;" value="添加" />
                </form>
                <table class="tb" id="my_show">
                    <thead>
                    <tr>
                        <th><input id="check_all" type="checkbox" onclick="checkAll();"/></th>
                        <th>序号</th>
                        <th>用户工号</th>
                        <th>用户姓名</th>
                        <th>学院</th>
                        <th>用户身份</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${userList}" var="current" varStatus="i">
                        <tr>
                            <td><input id="check_${current.username}" name="items" type="checkbox" value="${current.username}"/></td>
                            <td>${i.count}</td>
                            <td>${current.username}</td>
                            <td>${current.cname}</td>
                            <td>${current.schoolAcademy.academyName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${current.userRole==0}">
                                        学生
                                    </c:when>
                                    <c:otherwise>
                                        教师
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <!-- 分页导航 -->
                <div class="page">
                    总记录:<strong>${totalRecords}</strong>条&nbsp;
                    总页数:<strong>${pageModel.totalPage}</strong>页 <input type="hidden" value="${pageModel.lastPage}" id="totalpage" />&nbsp;
                    <a onclick=targetUrl("${pageContext.request.contextPath}/labRoom/layerAddLabRoomAuthorized?lab_id=${lab_id}&currpage=${pageModel.firstPage}") target="_self"> 首页</a>
                    <a onclick=targetUrl("${pageContext.request.contextPath}/labRoom/layerAddLabRoomAuthorized?lab_id=${lab_id}&currpage=${pageModel.previousPage}")  target="_self">上一页 </a>
                    第<select onchange="javascript:window.location.href = this.options[this.selectedIndex].value;">
                    <option value="${pageContext.request.contextPath}/labRoom/layerAddLabRoomAuthorized?lab_id=${lab_id}&currpage=${page}">${page}</option>
                    <c:forEach begin="${pageModel.firstPage}" end="${pageModel.lastPage}" step="1" varStatus="j" var="current">
                        <c:if test="${j.index!=page}">
                            <option value="${pageContext.request.contextPath}/labRoom/layerAddLabRoomAuthorized?lab_id=${lab_id}&currpage=${j.index}">${j.index}</option>
                        </c:if>
                    </c:forEach></select>页
                    <a onclick=targetUrl("${pageContext.request.contextPath}/labRoom/layerAddLabRoomAuthorized?lab_id=${lab_id}&currpage=${pageModel.nextPage}")  target="_self">下一页 </a>
                    <a onclick=targetUrl("${pageContext.request.contextPath}/labRoom/layerAddLabRoomAuthorized?lab_id=${lab_id}&currpage=${pageModel.lastPage}") target="_self">末页 </a>&nbsp;
                </div>
            </div>
        </div>
    </div>
    <!-- 下拉框的js -->
    <script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
        var config = {
            '.chzn-select'           : {search_contains:true},
            '.chzn-select-deselect'  : {allow_single_deselect:true},
            '.chzn-select-no-single' : {disable_search_threshold:10},
            '.chzn-select-no-results': {no_results_text:'Oops, nothing found!'},
            '.chzn-select-width'     : {width:"95%"}
        }
        for (var selector in config) {
            $(selector).chosen(config[selector]);
        }
    </script>
    <!-- 下拉框的js -->
</body>
</html>

