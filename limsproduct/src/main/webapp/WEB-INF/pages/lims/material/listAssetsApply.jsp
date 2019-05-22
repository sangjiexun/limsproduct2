<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<fmt:setBundle basename="bundles.lab-resources"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf8">
	<title>物资申购列表</title>
	<meta name="decorator" content="timetable"/>
	<meta name="contextPath" content="${pageContext.request.contextPath}"/>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css" media="all">
</head>

<body>
<input id="contextPath" value="${pageContext.request.contextPath}" style="display: none;"/>

<div class="layui-layout layui-layout-admin">
	<div class="layui-main">
		<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
			<legend>物资申购列表</legend>
		</fieldset>
		<button data-method="newAssetsApply" class="layui-btn layui-btn-xs layui-btn-normal apply_btn">新建物资申购</button>
		<blockquote class="layui-elem-quote">
				<div class="layui-inline">
					<input class="layui-input" id="keywords" autocomplete="off" placeholder="请输入关键字"/>
				</div>
			<button class="layui-btn search_btn" data-method="search">搜索</button>
			<div class="layui-form" style="display:inline-block">
				<label class="layui-form-label">物资类别</label>
				<div class="layui-input-block">
					<select name="kind" id="kind"  lay-filter="kind">
						<option value=""></option>
					</select>
				</div>
			</div>
		</blockquote>
		<table class="layui-hide add_progress" id="assetsApplyList" lay-filter="assetsApplyList"></table>
		<script type="text/html" id="parentbar">
			{{#  if(d.status==0){ }}
			<a class="layui-btn layui-btn-xs" lay-event="submit">提交</a>
			<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
			<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
			<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
			{{# }  else if(d.status==1 && d.auditFlag==0 && d.curAuditLevel=='OPEARTIONSECURITYMANAGEMENT'){ }}
			<a class="layui-btn layui-btn-xs" lay-event="detail">待运行与安全管理科审核</a>
			{{# }  else if(d.status==1 && d.auditFlag==0 && d.curAuditLevel=='EXCENTERDIRECTOR'){ }}
			<a class="layui-btn layui-btn-xs" lay-event="detail">待实验中心主任审核</a>
			{{# }  else if(d.status==1 && d.auditFlag==1){ }}
			<a class="layui-btn layui-btn-xs" lay-event="detail">审核</a>
			{{# }  else if(d.status==2 && d.appFlag==1){ }}
			<a class="layui-btn layui-btn-xs" lay-event="inbound">发起入库</a>
			{{# }  else if(d.status==2 && d.appFlag==0){ }}
			<a class="layui-btn layui-btn-xs" lay-event="detail">待发起入库</a>
			{{# }  else if(d.status==3){ }}
			<a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
			{{# }  else if(d.status==4){ }}
			<a class="layui-btn layui-btn-xs" lay-event="detail">已发起入库</a>
			{{#  } }}
		</script>
		<script type="text/html" id="zh">
			{{#  if(d.status==0){ }}
			未提交
			{{# }else if(d.status==1){ }}
			提交审核中
			{{# }  else if(d.status==2){ }}
			审核通过
			{{# }  else if(d.status==3){ }}
			审核拒绝
			{{# }  else if(d.status==4){ }}
			已发起入库
			{{#  } }}
		</script>
	</div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/layui/layui.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lims/material/listAssetsApply.js" ></script>
</body>

</html>