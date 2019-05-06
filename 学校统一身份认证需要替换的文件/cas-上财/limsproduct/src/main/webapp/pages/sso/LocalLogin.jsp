<%@ page import="com.kingstar.sso.client.CurrentLoginUser" %>
<%@ page import="com.kingstar.sso.client.util.CommonUtil" %>
<%@page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!doctype html>
<html lang="en">
<head>
    <title>LocalLogin</title>
    <meta name="Generator" content="gvsun">
    <meta name="Author" content="lyyyyyy">
    <meta name="Keywords" content="">
    <meta name="decorator" content="none">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-sacale=1.0,user-scalable=no"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
</head>
<%
    /*
     * 须修改此处的变量
     * */
    String serverUri = "http://10.2.47.48";
    String appServerLoginUrl = serverUri + "/shufelims/pages/login.jsp";
    String appServerWelcomeUrl = serverUri + "/shufelims/pages/logout-redirect.jsp";
    /*
     * END
     * */

    String urlToRedirectTo = CommonUtil.getTargetURI(request, serverUri, appServerLoginUrl, appServerWelcomeUrl);

    HttpSession httpSession = request.getSession();
    String errorCode = request.getParameter("errorCode");

    String j_username = "";
    // 只用到了这个if里的代码 其他errorCode里的代码是sample里的没有改成自己项目里的，不知道能不能用
    if (errorCode == null || errorCode.equals("")) {
        // 认证服务器认证通过
        CurrentLoginUser currentLoginUser =
                (CurrentLoginUser) httpSession.getAttribute(CurrentLoginUser.CURRENT_LOGIN_USER_KEY);
        if (currentLoginUser == null) {
            response.sendRedirect(appServerLoginUrl + "?errorCode=000");
            return;
        } else {
            String username = currentLoginUser.getLoginUserAccount();
            // 赋值
            j_username = username;

			/*
			// TODO 写入本地Session
			*/


			/*
			// 跳转到最终访问页面
			*/
//            response.sendRedirect(urlToRedirectTo);
//            return;
        }
    } else if (errorCode.equals("000")) {
		/*
		// 认证服务器未认证
		// 返回登录页面，进行认证
		*/
        response.sendRedirect(appServerLoginUrl + "?errorCode=" + errorCode);
        return;
    } else if (errorCode.equals("999")) {
		/*
		// 认证服务器无效
		// 进行本地校验
		*/

        String username = (String) httpSession.getAttribute(CommonUtil.SESSSION_USERNAME_KEY);
        String password = (String) httpSession.getAttribute(CommonUtil.SESSSION_PASSWORD_KEY);

		/*
		// 根据 username获取用户信息，自行实现
		*/


		/*
		// 本地校验
		*/
        boolean isVerify = username.equals("admin") && password.equals("admin");
        if (isVerify) {
			/*
			// 写入API Session，以保证本地认证通过后，不会再被API 认证拦截
			*/
            CurrentLoginUser currentLoginUser = new CurrentLoginUser(username);
            httpSession.setAttribute(CurrentLoginUser.CURRENT_LOGIN_USER_KEY, currentLoginUser);

			/*
			// TODO 写入本地Session
			*/


			/*
			// 跳转到最终访问页面
			*/
            response.sendRedirect(urlToRedirectTo);
            return;
        } else {
			/*
			// 返回登录页面
			*/
            response.sendRedirect(appServerLoginUrl + "?errorCode=" + errorCode);
            return;
        }
    } else if (errorCode.equals("001") || errorCode.equals("002")) {
		/*
		// 认证服务器认证失败（用户不存在或密码错误）
		// 进行本地校验
		*/

        String username = (String) httpSession.getAttribute(CommonUtil.SESSSION_USERNAME_KEY);
        String password = (String) httpSession.getAttribute(CommonUtil.SESSSION_PASSWORD_KEY);

		/*
		// 根据 username获取用户信息，自行实现
		*/


		/*
		// 本地校验
		*/
        boolean isVerify = username.equals("admin") && password.equals("admin");
        if (isVerify) {
			/*
			// 写入API Session，以保证本地认证通过后，不会再被API 认证拦截
			*/
            CurrentLoginUser currentLoginUser = new CurrentLoginUser(username);
            httpSession.setAttribute(CurrentLoginUser.CURRENT_LOGIN_USER_KEY, currentLoginUser);

			/*
			// TODO 写入本地Session
			*/


			/*
			// 跳转到最终访问页面
			*/
            response.sendRedirect(urlToRedirectTo);
            return;
        } else {
			/*
			// 返回登录页面
			*/
            response.sendRedirect(appServerLoginUrl + "?errorCode=" + errorCode);
            return;
        }
    } else if (errorCode.equals("003")) {
		/*
		// 验证码错误
		// 返回登录页面
		*/
        response.sendRedirect(appServerLoginUrl + "?errorCode=" + errorCode);
        return;
    }
%>
<body style="display: none">
<form id="loginForm" name="loginForm" action='${pageContext.request.contextPath}/j_spring_security_check' method='POST'>
    <input type="text" name="j_username" value="<%=j_username%>"/>
    <input type="password" name="j_password" value="<%=j_username%>"/>
    <input type="submit" value="sumit">
</form>
<script type="text/javascript">
    // var a = document.getElementById("loginForm");
    // console.log(a)
    document.loginForm.submit();
</script>
</body>
</html>