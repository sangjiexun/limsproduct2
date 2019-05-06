<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
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
    <style>
        table input[type="number"]{
            width:38px;
        }
        .tab_normal td .combo input[type="text"]{
            /*width:80px!important;*/
        }
    </style>
</head>

<body>
<div class="navigation">
    <div id="navigation">
        <ul>
            <li><a href="javascript:void(0)"><spring:message code="left.system.management"/></a></li>
            <li class="end"><a href="javascript:void(0)"><spring:message code="left.global.setting"/></a></li>
        </ul>
    </div>
</div>
<div class="right-content">
    <div class="TabbedPanels">
        <div class="TabbedPanelsContentGroup">
            <div class="TabbedPanelsContent">
                <div class="content-box">
                    <div class="title">
                        <div id="title">开放设置</div>
                    </div>
                    <table class="tab_normal">
                        <thead>
                        <tr>
                            <th>已设置的开放日期</th>
                            <th>周次</th>
                            <th>时间</th>
                            <th>等级</th>
                            <th>最小提前预约时间</th>
                            <th>最大提前预约时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            <tr class="tab_td_nowrap">
                                <td>
                                    <input type="text" class="easyui-datebox" placeholder="请选择日期"/>
                                    至
                                    <input type="text" class="easyui-datebox" placeholder="请选择日期"/>
                                </td>
                                <td>
                                    <select class="chzn-select" multiple>
                                        <option value="">1</option>
                                        <option value="">2</option>
                                        <option value="">3</option>
                                        <option value="">4</option>
                                        <option value="">5</option>
                                        <option value="">6</option>
                                        <option value="">7</option>
                                    </select>
                                </td>
                                <td>
                                    <input class="easyui-timespinner" data-options="showSeconds:true"/>
                                    至
                                    <input class="easyui-timespinner" data-options="showSeconds:true"/>
                                </td>
                                <td>
                                    <select class="chzn-select">
                                        <option value="" selected>全部</option>
                                        <option value="">其他</option>
                                    </select>
                                </td>
                                <td>
                                    <input type="number"/>小时
                                </td>
                                <td>
                                    <input type="number"/>小时
                                </td>
                                <td>
                                    <a href="javascript:void(0);">保存</a>
                                </td>
                            </tr>
                            <tr>
                                <td>2018-10-10至2018-10-22</td>
                                <td>周1/2/3/4/5/6/7</td>
                                <td>00:00:00至23:59:59</td>
                                <td>全部</td>
                                <td>1小时</td>
                                <td>45小时</td>
                                <td>
                                    <a href="javascript:void(0);">删除</a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <!-- 分页[s] -->
                    <div class="page" >
                        ${totalRecords}条记录 &nbsp; 共${pageModel.totalPage}页 &nbsp;
                        <a href="javascript:void(0)" onclick="" target="_self">首页</a>
                        <a href="javascript:void(0)" onclick="" target="_self">上一页</a>
                        <input type="hidden" value="${pageModel.lastPage}" id="totalpage"/>&nbsp;
                        <input type="hidden" value="${currpage}" id="currpage"/>&nbsp;
                        第
                        <select>
                            <option value="">${currpage}</option>
                        </select>页&nbsp;
                        <a href="javascript:void(0)" onclick="" target="_self">下一页</a>
                        <a href="javascript:void(0)" onclick="" target="_self">末页</a>
                    </div>
                    <!-- 分页[e] -->
                </div>
                <div class="content-box" style="margin:20px 0 0;">
                    <div class="title">
                        <div id="title">禁用时间段设置</div>
                    </div>
                    <table class="tab_normal">
                        <thead>
                        <tr>
                            <th>已设置的不可预约日期</th>
                            <th>周次</th>
                            <th>时间</th>
                            <th>备注</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="tab_td_nowrap">
                            <td>
                                <input type="text" class="easyui-datebox" placeholder="请选择日期"/>
                                至
                                <input type="text" class="easyui-datebox" placeholder="请选择日期"/>
                            </td>
                            <td>
                                <select class="chzn-select" multiple>
                                    <option value="">1</option>
                                    <option value="">2</option>
                                    <option value="">3</option>
                                    <option value="">4</option>
                                    <option value="">5</option>
                                    <option value="">6</option>
                                    <option value="">7</option>
                                </select>
                            </td>
                            <td>
                                <input class="easyui-timespinner" data-options="showSeconds:true"/>
                                至
                                <input class="easyui-timespinner" data-options="showSeconds:true"/>
                            </td>
                            <td>
                                <input type="text" placeholder="请输入备注"/>
                            </td>
                            <td>
                                <a href="javascript:void(0);">保存</a>
                            </td>
                        </tr>
                        <tr>
                            <td>2018-10-10至2018-10-22</td>
                            <td>周1/2/3/4/5/6/7</td>
                            <td>00:00:00至23:59:59</td>
                            <td>显示备注信息</td>
                            <td>
                                <a href="javascript:void(0);">删除</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <!-- 分页[s] -->
                    <div class="page" >
                        ${totalRecords}条记录 &nbsp; 共${pageModel.totalPage}页 &nbsp;
                        <a href="javascript:void(0)" onclick="" target="_self">首页</a>
                        <a href="javascript:void(0)" onclick="" target="_self">上一页</a>
                        <input type="hidden" value="${pageModel.lastPage}" id="totalpage"/>&nbsp;
                        <input type="hidden" value="${currpage}" id="currpage"/>&nbsp;
                        第
                        <select>
                            <option value="">${currpage}</option>
                        </select>页&nbsp;
                        <a href="javascript:void(0)" onclick="" target="_self">下一页</a>
                        <a href="javascript:void(0)" onclick="" target="_self">末页</a>
                    </div>
                    <!-- 分页[e] -->
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
<script>
    $(function () {
        $('.easyui-timespinner').timespinner({
            value: '00:00',
            min: '00:00',
            max: '23:59',
            editable: true,
            required: true,
            showSeconds: true
        });
    });
</script>
</body>
</html>
