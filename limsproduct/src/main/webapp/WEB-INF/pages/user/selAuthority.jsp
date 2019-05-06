<%@page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<html>
<head>
	<meta name="decorator" content="iframe"/><style type="text/css">
		label{
			width: 86px;
			height:25px;
			display: inline-block;
		}
	</style>
	<script src="${pageContext.request.contextPath}/static/assets/js/jquery-1.11.1.min.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.easyui.zh.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jsUtils.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/plugins/layer-v2.2/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.form.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery_migrate.min.js"></script>
	<link href="${pageContext.request.contextPath}/static/plugins/uploadify/uploadify.css" rel="stylesheet" type="text/css">

	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/jstree/themes/default/style.min.css" />
	<script src="${pageContext.request.contextPath}/static/plugins/jstree/jstree.js"></script>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/ystep/css/ystep.css" />
	<script src="${pageContext.request.contextPath}/static/plugins/ystep/js/ystep.js"></script>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/plugins/icheck/css/all.css" />
	<script src="${pageContext.request.contextPath}/static/plugins/icheck/icheck.min.js"></script>

	<link href="${pageContext.request.contextPath}/static/override/override.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="page-content" style="padding: 20px;padding-bottom: 40x;padding-left: 28px;">
		<div class="skin-minimal">
			<c:forEach items="${allAuthoritys}" var="current" varStatus="i">
				<c:choose>
					<c:when test="${(i.count) % 4 == 0}">
						<c:if test="${'ROLE_'.concat(current.authorityName)==sessionScope.selected_role}">
							<input type="radio" name="auth" value="${current.authorityName }" checked="checked"> ${current.cname } &nbsp;&nbsp;
						</c:if>
						<c:if test="${'ROLE_'.concat(current.authorityName)!=sessionScope.selected_role}">
							<input type="radio" name="auth" value="${current.authorityName }"> ${current.cname } &nbsp;&nbsp;
						</c:if>
						<br />
					</c:when>
					<c:otherwise>
						<c:if test="${'ROLE_'.concat(current.authorityName)==sessionScope.selected_role}">
							<input type="radio" name="auth" value="${current.authorityName }" checked="checked"> ${current.cname } &nbsp;&nbsp;
						</c:if>
						<c:if test="${'ROLE_'.concat(current.authorityName)!=sessionScope.selected_role}">
							<input type="radio" name="auth" value="${current.authorityName }"> ${current.cname } &nbsp;&nbsp;
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:forEach>
           	<br>
           	<br>
			<a class="easyui-linkbutton" style="float: right;margin: 2px;" href="#" onclick="submitForm()" >保存</a>
		</div>

	</div>
	<script type="text/javascript">

		$(document).ready(function(){
			$('.skin-minimal input').iCheck({
				checkboxClass: 'icheckbox_minimal-green',
				radioClass: 'iradio_minimal',
				increaseArea: '20%'
			});
		});

		function closeIframe(){
			var index = parent.layer.getFrameIndex(window.name);
		    parent.layer.close(index);
			$('#submitForm').form('clear');
		}

		function submitForm(){

			var auth =  $('.skin-minimal input[name="auth"]:checked').val();

			if((auth == "" || auth == undefined || auth == null)){
				layer.msg('选择权限', {
				    time: 200
				});
			}else{
				$.ajax({
				    url:"${pageContext.request.contextPath}/common/changeUserRole?auth="+auth,
				    type: 'POST',
				    async:false,
				    success: function(data) {
			    		//parent.window.location.href='${pageContext.request.contextPath}/login';
                        var index = parent.layer.getFrameIndex(window.name);
                        window.parent.location.reload();
				    }
				});
			}
		}
	</script>

</body>
</html>