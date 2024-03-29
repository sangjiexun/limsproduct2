<%@ page language="java" import="java.nio.charset.Charset"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%!
    public interface Constants {
        // CAS根地址
        String CAS_BASE_PATH = "https://cas.ycit.edu.cn/cas/";

        // 业务系统需要显式使用的端口配置，包括80端口，如果不需要配置显式端口，则配置空字符串""即可
        String CLIENT_SYSTEM_EXPLICIT_PORT = "";

        // CAS票据验证地址
        String CAS_VALIDATE_URL = CAS_BASE_PATH + "serviceValidate";

        // CAS登录地址
        String CAS_LOGIN_URL = CAS_BASE_PATH + "login";

        // CAS注销地址
        String CAS_LOGOUT_URL = CAS_BASE_PATH + "logout";

        //登录成功默认跳转地址
        String DEF_TARGET_URI = "";//"pages/sso/index.jsp";//"pages/sso/index.jsp";//"sso/index.jsp";

        // 业务系统认证集成改造之后的登录URI
        String SSO_LOGIN_URI = "pages/sso/login.jsp";//"pages/sso/login.jsp";//"sso/login.jsp";

        // REQUEST中获取需要跳转URL的KEY
        String TARGET_URL_KEY = "targetUrl";

        // SESSION中判断是否登录的KEY
        String LOGIN_KEY = "isSupwisdomCasLogin";
        String LOGIN_USER_KEY = "supwisdomCasLoginUser";

        // REQUEST中获取票据的KEY
        String TICKET_KEY = "ticket";

        // CAS Server验证成功后需跳转客户端Url的Key
        String SERVICE_KEY = "service";

        // BASE64编码的前缀
        String BASE64_PREFIX = "%7Bbase64%7D";//"{base64}";

        // 默认编码字符串格式
        String UTF_8_STR = "UTF-8";

        //默认编码
        Charset UTF_8 = Charset.forName(UTF_8_STR);
    }
%>