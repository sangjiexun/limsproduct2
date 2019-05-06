package net.zjcclims.constant;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import static net.zjcclims.constant.CustomLoginFilter.Constants.LOGIN_USER_KEY;


public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
    //Constants.jsp
    public interface Constants {
        // CAS根地址
        String CAS_BASE_PATH = "https://cas.ycit.edu.cn/cas/";
        //cas.ycit.edu.cn/cas

        // 业务系统需要显式使用的端口配置，包括80端口，如果不需要配置显式端口，则配置空字符串""即可
        String CLIENT_SYSTEM_EXPLICIT_PORT = "";

        // CAS票据验证地址
        String CAS_VALIDATE_URL = CAS_BASE_PATH + "serviceValidate";

        // CAS登录地址
        String CAS_LOGIN_URL = CAS_BASE_PATH + "login";

        // CAS注销地址
        String CAS_LOGOUT_URL = CAS_BASE_PATH + "logout";

        //登录成功默认跳转地址 //没用到
        String DEF_TARGET_URI = "";

        // 业务系统认证集成改造之后的登录URI
        String SSO_LOGIN_URI = "pages/sso/login.jsp";

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

    @Override
    @SuppressWarnings("unchecked")
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        javax.servlet.http.HttpSession session = request.getSession(true);
        String username="";
        Object attribute = session.getAttribute("username");
        //没有登录就跳转到cas认证页面
        if (attribute == null) {
            try {
                //response.sendRedirect(request.getContextPath()+"/home");
                //cas地址
                String loginUrl = CasUtils.getLoginUrl(request);
                response.sendRedirect(loginUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            username = attribute.toString();
        }
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, username);
        // authentication.WebAuthenticationDetails实例到details中
        setDetails(request, authRequest);
        // 通过AuthenticationManager:ProviderManager完成认证任务
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    //LoginUser.jsp
    public static class LoginUser {

        public static final String CAS_PREFIX = "cas:";
        public static final String LOGIN_SUCCESS_KEY = CAS_PREFIX
                + "authenticationSuccess";
        public static final String ACCOUNT_KEY = CAS_PREFIX + "user";
        public static final String ATTRIBUTES_KEY = CAS_PREFIX + "attributes";

        private String account;

        private String ssoAccount;
        private String deptName;
        private String idCard;
        private String studentNo;
        private String remark;
        private String localAccount;
        private String tel;
        private String dicOrgId;
        private String nick;
        private String email;
        private String staffNo;
        private String name;
        private String deptCode;
        private String dn;
        private String mobile;
        private String typeCode;
        private String type;
        private String typeName;

        public LoginUser(String loginUserXmlStr) {
            if (StringUtils.isEmpty(loginUserXmlStr))
                return;
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                    .newInstance();
            InputStream in = null;
            try {
                DocumentBuilder docBuilder = docBuilderFactory
                        .newDocumentBuilder();
                in = IOUtils.toInputStream(loginUserXmlStr, Constants.UTF_8);
                Document rootDoc = docBuilder.parse(in);
                NodeList successNodeList = rootDoc
                        .getElementsByTagName(LOGIN_SUCCESS_KEY);
                if (successNodeList.getLength() > 0) {
                    Node successNode = successNodeList.item(0);
                    Document successDocument = successNode.getOwnerDocument();
                    NodeList accountNodeList = successDocument
                            .getElementsByTagName(ACCOUNT_KEY);
                    if (accountNodeList != null
                            && accountNodeList.getLength() > 0) {
                        Node accountNode = accountNodeList.item(0);
                        Node accountText = accountNode.getFirstChild();
                        this.account = accountText.getNodeValue();
                    }
                    NodeList attrsNodeList = successDocument
                            .getElementsByTagName(ATTRIBUTES_KEY);
                    if (attrsNodeList.getLength() > 0) {
                        Node attrsNode = attrsNodeList.item(0);
                        if (attrsNode.hasChildNodes()) {
                            Document attrsDoc = attrsNode.getOwnerDocument();
                            Field[] fields = getClass().getDeclaredFields();
                            for (Field field : fields) {
                                String fieldName = field.getName();
                                String attrTagName = CAS_PREFIX + fieldName;
                                NodeList attrNodeList = attrsDoc
                                        .getElementsByTagName(attrTagName);
                                if (attrNodeList.getLength() > 0) {
                                    Node attrNode = attrNodeList.item(0);
                                    Node attrText = attrNode.getFirstChild();
                                    field.set(this, attrText.getNodeValue()
                                            .trim());
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                // 解析用户信息失败！
                e.printStackTrace();
            } finally {
                IOUtils.closeQuietly(in);
            }
        }

        public String getAccount() {
            return account;
        }

        public String getSsoAccount() {
            return ssoAccount;
        }

        public String getDeptName() {
            return deptName;
        }

        public String getIdCard() {
            return idCard;
        }

        public String getStudentNo() {
            return studentNo;
        }

        public String getRemark() {
            return remark;
        }

        public String getLocalAccount() {
            return localAccount;
        }

        public String getTel() {
            return tel;
        }

        public String getDicOrgId() {
            return dicOrgId;
        }

        public String getNick() {
            return nick;
        }

        public String getEmail() {
            return email;
        }

        public String getStaffNo() {
            return staffNo;
        }

        public String getName() {
            return name;
        }

        public String getDeptCode() {
            return deptCode;
        }

        public String getDn() {
            return dn;
        }

        public String getMobile() {
            return mobile;
        }

        public String getTypeCode() {
            return typeCode;
        }

        public void setTypeCode(String typeCode) {
            this.typeCode = typeCode;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public boolean isLogin() {
            return account != null && account.length() != 0;
        }

        @Override
        public String toString() {
            return "LoginUser{" +
                    "account='" + account + '\'' +
                    ", ssoAccount='" + ssoAccount + '\'' +
                    ", deptName='" + deptName + '\'' +
                    ", idCard='" + idCard + '\'' +
                    ", studentNo='" + studentNo + '\'' +
                    ", remark='" + remark + '\'' +
                    ", localAccount='" + localAccount + '\'' +
                    ", tel='" + tel + '\'' +
                    ", dicOrgId='" + dicOrgId + '\'' +
                    ", nick='" + nick + '\'' +
                    ", email='" + email + '\'' +
                    ", staffNo='" + staffNo + '\'' +
                    ", name='" + name + '\'' +
                    ", deptCode='" + deptCode + '\'' +
                    ", dn='" + dn + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", typeCode='" + typeCode + '\'' +
                    ", type='" + type + '\'' +
                    ", typeName='" + typeName + '\'' +
                    '}';
        }
    }
    //CommonUtils.jsp
    public static abstract class StringUtils {

        public static boolean isEmpty(CharSequence cs) {
            return cs == null || cs.length() == 0;
        }

        public static byte[] getBytesUtf8(final String string) {
            return getBytes(string, Constants.UTF_8);
        }

        public static byte[] getBytes(final String string, final Charset charset) {
            if (string == null) {
                return null;
            }
            return string.getBytes(charset);
        }

        public static String newStringUtf8(final byte[] bytes) {
            return newString(bytes, Constants.UTF_8);
        }

        public static String newString(final byte[] bytes, final Charset charset) {
            return bytes == null ? null : new String(bytes, charset);
        }
    }

    public static abstract class IOUtils {

        private static final int EOF = -1;

        private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

        public static void close(URLConnection conn) {
            if (conn instanceof HttpURLConnection) {
                ((HttpURLConnection) conn).disconnect();
            }
        }

        public static void closeQuietly(InputStream input) {
            closeQuietly((Closeable) input);
        }

        public static void closeQuietly(Closeable closeable) {
            try {
                if (closeable != null) {
                    closeable.close();
                }
            } catch (IOException ioe) {
                // ignore
            }
        }

        public static String toString(InputStream input, Charset encoding) throws IOException {
            StringWriter sw = new StringWriter();
            copy(input, sw, encoding);
            return sw.toString();
        }

        public static void copy(InputStream input, Writer output, Charset encoding) throws IOException {
            encoding = encoding == null ? Charset.defaultCharset() : encoding;
            InputStreamReader in = new InputStreamReader(input, encoding);
            copy(in, output);
        }

        public static int copy(Reader input, Writer output) throws IOException {
            long count = copyLarge(input, output);
            if (count > Integer.MAX_VALUE) {
                return -1;
            }
            return (int) count;
        }

        public static long copyLarge(Reader input, Writer output) throws IOException {
            return copyLarge(input, output, new char[DEFAULT_BUFFER_SIZE]);
        }

        public static long copyLarge(Reader input, Writer output, char[] buffer) throws IOException {
            long count = 0;
            int n;
            while (EOF != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
                count += n;
            }
            return count;
        }

        public static InputStream toInputStream(String input, Charset encoding) {
            return new ByteArrayInputStream(input.getBytes(encoding));
        }

    }

    public static abstract class Base64Utils {

        private static final byte[] ENC_TAB_BYTES = new byte[64];

        static {
            int j = 0;
            for (byte i = 'A'; i <= 'Z'; i++) {
                ENC_TAB_BYTES[j] = i;
                j++;
            }
            for (byte i = 'a'; i <= 'z'; i++) {
                ENC_TAB_BYTES[j] = i;
                j++;
            }
            for (byte i = '0'; i <= '9'; i++) {
                ENC_TAB_BYTES[j] = i;
                j++;
            }
            ENC_TAB_BYTES[j++] = '+';
            ENC_TAB_BYTES[j] = '/';
        }

        public static String encodeBase64Str(String str) {
            byte[] bytes = StringUtils.getBytesUtf8(str);
            return encodeBase64Str(bytes);
        }

        public static String encodeBase64Str(byte[] bytes) {
            byte[] base64Bytes = encodeBase64(bytes);
            return StringUtils.newStringUtf8(base64Bytes);
        }

        public static byte[] encodeBase64(byte[] bytes) {
            byte[] base64Bytes;
            int modulus = bytes.length % 3;
            if (modulus == 0) {
                base64Bytes = new byte[(4 * bytes.length) / 3];
            } else {
                base64Bytes = new byte[4 * ((bytes.length / 3) + 1)];
            }
            int dataLength = (bytes.length - modulus);
            int a1;
            int a2;
            int a3;
            for (int i = 0, j = 0; i < dataLength; i += 3, j += 4) {
                a1 = bytes[i] & 0xff;
                a2 = bytes[i + 1] & 0xff;
                a3 = bytes[i + 2] & 0xff;
                base64Bytes[j] = ENC_TAB_BYTES[(a1 >>> 2) & 0x3f];
                base64Bytes[j + 1] = ENC_TAB_BYTES[((a1 << 4) | (a2 >>> 4)) & 0x3f];
                base64Bytes[j + 2] = ENC_TAB_BYTES[((a2 << 2) | (a3 >>> 6)) & 0x3f];
                base64Bytes[j + 3] = ENC_TAB_BYTES[a3 & 0x3f];
            }
            int b1;
            int b2;
            int b3;
            int d1;
            int d2;
            switch (modulus) {
                case 0:
                    break;
                case 1:
                    d1 = bytes[bytes.length - 1] & 0xff;
                    b1 = (d1 >>> 2) & 0x3f;
                    b2 = (d1 << 4) & 0x3f;
                    base64Bytes[base64Bytes.length - 4] = ENC_TAB_BYTES[b1];
                    base64Bytes[base64Bytes.length - 3] = ENC_TAB_BYTES[b2];
                    base64Bytes[base64Bytes.length - 2] = '=';
                    base64Bytes[base64Bytes.length - 1] = '=';
                    break;
                case 2:
                    d1 = bytes[bytes.length - 2] & 0xff;
                    d2 = bytes[bytes.length - 1] & 0xff;
                    b1 = (d1 >>> 2) & 0x3f;
                    b2 = ((d1 << 4) | (d2 >>> 4)) & 0x3f;
                    b3 = (d2 << 2) & 0x3f;
                    base64Bytes[base64Bytes.length - 4] = ENC_TAB_BYTES[b1];
                    base64Bytes[base64Bytes.length - 3] = ENC_TAB_BYTES[b2];
                    base64Bytes[base64Bytes.length - 2] = ENC_TAB_BYTES[b3];
                    base64Bytes[base64Bytes.length - 1] = '=';
                    break;
            }
            return base64Bytes;
        }

        private static final byte[] DEC_TAB_BYTES = new byte[128];

        static {
            for (int i = 0; i < 128; i++) {
                DEC_TAB_BYTES[i] = (byte) -1;
            }
            for (int i = 'A'; i <= 'Z'; i++) {
                DEC_TAB_BYTES[i] = (byte) (i - 'A');
            }
            for (int i = 'a'; i <= 'z'; i++) {
                DEC_TAB_BYTES[i] = (byte) (i - 'a' + 26);
            }
            for (int i = '0'; i <= '9'; i++) {
                DEC_TAB_BYTES[i] = (byte) (i - '0' + 52);
            }
            DEC_TAB_BYTES['+'] = 62;
            DEC_TAB_BYTES['/'] = 63;
        }

        public static String decodeBase64Str(String data) {
            byte[] base64Bytes = StringUtils.getBytesUtf8(data);
            return decodeBase64Str(base64Bytes);
        }

        public static String decodeBase64Str(byte[] base64Bytes) {
            byte[] bytes = decodeBase64(base64Bytes);
            return StringUtils.newStringUtf8(bytes);
        }

        public static byte[] decodeBase64(byte[] base64Bytes) {
            byte[] bytes;
            byte b1;
            byte b2;
            byte b3;
            byte b4;
            base64Bytes = discardNonBase64Bytes(base64Bytes);
            if (base64Bytes[base64Bytes.length - 2] == '=') {
                bytes = new byte[(((base64Bytes.length / 4) - 1) * 3) + 1];
            } else if (base64Bytes[base64Bytes.length - 1] == '=') {
                bytes = new byte[(((base64Bytes.length / 4) - 1) * 3) + 2];
            } else {
                bytes = new byte[((base64Bytes.length / 4) * 3)];
            }
            for (int i = 0, j = 0; i < (base64Bytes.length - 4); i += 4, j += 3) {
                b1 = DEC_TAB_BYTES[base64Bytes[i]];
                b2 = DEC_TAB_BYTES[base64Bytes[i + 1]];
                b3 = DEC_TAB_BYTES[base64Bytes[i + 2]];
                b4 = DEC_TAB_BYTES[base64Bytes[i + 3]];
                bytes[j] = (byte) ((b1 << 2) | (b2 >> 4));
                bytes[j + 1] = (byte) ((b2 << 4) | (b3 >> 2));
                bytes[j + 2] = (byte) ((b3 << 6) | b4);
            }
            if (base64Bytes[base64Bytes.length - 2] == '=') {
                b1 = DEC_TAB_BYTES[base64Bytes[base64Bytes.length - 4]];
                b2 = DEC_TAB_BYTES[base64Bytes[base64Bytes.length - 3]];
                bytes[bytes.length - 1] = (byte) ((b1 << 2) | (b2 >> 4));
            } else if (base64Bytes[base64Bytes.length - 1] == '=') {
                b1 = DEC_TAB_BYTES[base64Bytes[base64Bytes.length - 4]];
                b2 = DEC_TAB_BYTES[base64Bytes[base64Bytes.length - 3]];
                b3 = DEC_TAB_BYTES[base64Bytes[base64Bytes.length - 2]];
                bytes[bytes.length - 2] = (byte) ((b1 << 2) | (b2 >> 4));
                bytes[bytes.length - 1] = (byte) ((b2 << 4) | (b3 >> 2));
            } else {
                b1 = DEC_TAB_BYTES[base64Bytes[base64Bytes.length - 4]];
                b2 = DEC_TAB_BYTES[base64Bytes[base64Bytes.length - 3]];
                b3 = DEC_TAB_BYTES[base64Bytes[base64Bytes.length - 2]];
                b4 = DEC_TAB_BYTES[base64Bytes[base64Bytes.length - 1]];
                bytes[bytes.length - 3] = (byte) ((b1 << 2) | (b2 >> 4));
                bytes[bytes.length - 2] = (byte) ((b2 << 4) | (b3 >> 2));
                bytes[bytes.length - 1] = (byte) ((b3 << 6) | b4);
            }
            return bytes;
        }

        private static byte[] discardNonBase64Bytes(byte[] datas) {
            byte[] temp = new byte[datas.length];
            int bytesCopied = 0;
            for (byte data : datas) {
                if (isValidBase64Byte(data)) {
                    temp[bytesCopied++] = data;
                }
            }
            byte[] newData = new byte[bytesCopied];
            System.arraycopy(temp, 0, newData, 0, bytesCopied);
            return newData;
        }

        private static boolean isValidBase64Byte(byte b) {
            if (b == '=') {
                return true;
            } else if ((b < 0) || (b >= 128)) {
                return false;
            } else if (DEC_TAB_BYTES[b] == -1) {
                return false;
            }
            return true;
        }
    }

    public static abstract class BooleanUtils {

        public static boolean toBoolean(final String str) {
            return toBooleanObject(str) == Boolean.TRUE;
        }

        public static Boolean toBooleanObject(final String str) {
            if (str.equals("true")) {
                return Boolean.TRUE;
            }
            switch (str.length()) {
                case 1: {
                    final char ch0 = str.charAt(0);
                    if (ch0 == 'y' || ch0 == 'Y' || ch0 == 't' || ch0 == 'T') {
                        return Boolean.TRUE;
                    }
                    if (ch0 == 'n' || ch0 == 'N' || ch0 == 'f' || ch0 == 'F') {
                        return Boolean.FALSE;
                    }
                    break;
                }
                case 2: {
                    final char ch0 = str.charAt(0);
                    final char ch1 = str.charAt(1);
                    if ((ch0 == 'o' || ch0 == 'O') && (ch1 == 'n' || ch1 == 'N')) {
                        return Boolean.TRUE;
                    }
                    if ((ch0 == 'n' || ch0 == 'N') && (ch1 == 'o' || ch1 == 'O')) {
                        return Boolean.FALSE;
                    }
                    break;
                }
                case 3: {
                    final char ch0 = str.charAt(0);
                    final char ch1 = str.charAt(1);
                    final char ch2 = str.charAt(2);
                    if ((ch0 == 'y' || ch0 == 'Y') && (ch1 == 'e' || ch1 == 'E') && (ch2 == 's' || ch2 == 'S')) {
                        return Boolean.TRUE;
                    }
                    if ((ch0 == 'o' || ch0 == 'O') && (ch1 == 'f' || ch1 == 'F') && (ch2 == 'f' || ch2 == 'F')) {
                        return Boolean.FALSE;
                    }
                    break;
                }
                case 4: {
                    final char ch0 = str.charAt(0);
                    final char ch1 = str.charAt(1);
                    final char ch2 = str.charAt(2);
                    final char ch3 = str.charAt(3);
                    if ((ch0 == 't' || ch0 == 'T') && (ch1 == 'r' || ch1 == 'R')
                            && (ch2 == 'u' || ch2 == 'U') && (ch3 == 'e' || ch3 == 'E')) {
                        return Boolean.TRUE;
                    }
                    break;
                }
                case 5: {
                    final char ch0 = str.charAt(0);
                    final char ch1 = str.charAt(1);
                    final char ch2 = str.charAt(2);
                    final char ch3 = str.charAt(3);
                    final char ch4 = str.charAt(4);
                    if ((ch0 == 'f' || ch0 == 'F') && (ch1 == 'a' || ch1 == 'A')
                            && (ch2 == 'l' || ch2 == 'L') && (ch3 == 's' || ch3 == 'S') && (ch4 == 'e' || ch4 == 'E')) {
                        return Boolean.FALSE;
                    }
                    break;
                }
                default:
                    break;
            }
            return null;
        }
    }

    public static abstract class HttpRequestUtils {

        public static String doGet(String urlStr) throws IOException {
            URL url = new URL(urlStr);
            InputStream in = null;
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.connect();
                in = conn.getInputStream();
                return IOUtils.toString(in, Constants.UTF_8);
            } finally {
                IOUtils.close(conn);
                IOUtils.closeQuietly(in);
            }
        }

    }

    //CasUtils.jsp
    public static abstract class CasUtils {

        /** 判断是否已经登录过 */
        public static boolean isLogin(HttpSession session) {
            Object isLogin = session.getAttribute(Constants.LOGIN_KEY);
            return BooleanUtils.toBoolean(String.valueOf(isLogin));
        }

        public static String getBasePath(HttpServletRequest request) {
            String scheme = request.getScheme();
            String serverName = request.getServerName();
            int serverPort = request.getServerPort();
            String contextPath1 = request.getRequestURI();
            String contextPath = request.getContextPath();

            // 判断是否配置了显式端口
            boolean explicit_port = Constants.CLIENT_SYSTEM_EXPLICIT_PORT != null
                    && !Constants.CLIENT_SYSTEM_EXPLICIT_PORT.isEmpty();

            if (explicit_port) {
                try {
                    serverPort = Integer
                            .parseInt(Constants.CLIENT_SYSTEM_EXPLICIT_PORT);
                } catch (Exception e) {

                    // 异常时赋值，方便双方排查问题
                    serverPort = 19000;
                }

                String url = scheme + "://" + serverName + ":" + serverPort
                        + contextPath + "/";
                return url;

            } else {
                if ((serverPort == 80) || (serverPort == 443)) {

                    String url = scheme + "://" + serverName + contextPath
                            + "/";
                    return url;
                } else {
                    String url = scheme + "://" + serverName + ":" + serverPort
                            + contextPath + "/";
                    return url;
                }
            }

        }

        /** 获取TargetUrl */
        public static String getTargetUrl(HttpServletRequest request) {
            String basePath = getBasePath(request);

            // 获取请求中的targetUrl
            String targetUrl = request.getParameter(Constants.TARGET_URL_KEY);

            if (StringUtils.isEmpty(targetUrl)) {
                // 若不存在，则使用默认页面作为targetUrl
                targetUrl = basePath + Constants.DEF_TARGET_URI;
            } else {
                // 判断target是否编码
                if (targetUrl.startsWith(Constants.BASE64_PREFIX)) {
                    targetUrl = targetUrl.substring(Constants.BASE64_PREFIX
                            .length());
                    targetUrl = Base64Utils.decodeBase64Str(targetUrl);
                }
            }
            return targetUrl;
        }

        /** 判断票据是否存在 */
        public static boolean hasTicket(HttpServletRequest request) {
            Object ticket = request.getParameter(Constants.TICKET_KEY);
            return ticket != null
                    && !StringUtils.isEmpty(String.valueOf(ticket));
        }

        public static String getURLEncodeServiceUrl(HttpServletRequest request)
                throws UnsupportedEncodingException {

            // 编码成系统可识别的加密串
            String targetUrl = getTargetUrl(request);
            String base64TargetUrl = Base64Utils.encodeBase64Str(targetUrl);

            String serviceUrlRoot = getBasePath(request)
                    + Constants.SSO_LOGIN_URI;

            String serviceUrl = serviceUrlRoot + "?" + Constants.TARGET_URL_KEY
                    + "=" + Constants.BASE64_PREFIX + base64TargetUrl;
            return URLEncoder.encode(serviceUrl, Constants.UTF_8_STR);
        }

        /** 获取Cas登录Url 登录成功后返回票据 */
        public static String getLoginUrl(HttpServletRequest request)
                throws UnsupportedEncodingException {
            String encodeServiceUrl = getURLEncodeServiceUrl(request);

            return Constants.CAS_LOGIN_URL + "?" + Constants.SERVICE_KEY + "="
                    + encodeServiceUrl;
        }

        /** 获取校验票据Url */
        public static String getServiceValidateUrl(HttpServletRequest request)
                throws UnsupportedEncodingException {

            String encodeServiceUrl = getURLEncodeServiceUrl(request);
            Object ticket = request.getParameter(Constants.TICKET_KEY);

            return Constants.CAS_VALIDATE_URL + "?" + Constants.TICKET_KEY
                    + "=" + ticket + "&" + Constants.SERVICE_KEY + "="
                    + encodeServiceUrl;
        }

        public static LoginUser getLoginUser(HttpServletRequest request)
                throws IOException {
            String serviceValidateUrl = getServiceValidateUrl(request);

            // System.out.println("serviceValidateUrl + " + serviceValidateUrl);

            String casUserInfoXml = HttpRequestUtils.doGet(serviceValidateUrl);

            // System.out.println("casUserInfoXml + " + casUserInfoXml);
            return new LoginUser(casUserInfoXml);
        }

        /** 获取登出地址 */
        public static String getLogoutUrl(HttpServletRequest request)
                throws UnsupportedEncodingException {
            // 获取本次请求的根Path
            String loginUrlRoot = getBasePath(request)
                    + Constants.SSO_LOGIN_URI;
            String encodeLoginUrlRoot = URLEncoder.encode(loginUrlRoot,
                    Constants.UTF_8_STR);

            return Constants.CAS_LOGOUT_URL + "?" + Constants.SERVICE_KEY + "="
                    + encodeLoginUrlRoot;
        }

        /** 写入单页面登录判断标志 */
        public static void login(LoginUser loginUser, HttpSession session) {
            session.setAttribute(Constants.LOGIN_KEY, true);
            session.setAttribute(LOGIN_USER_KEY, loginUser);
        }

        /** 移出单页面登录判断标志 */
        public static void logout(HttpSession session) {
            session.removeAttribute(Constants.LOGIN_KEY);
            session.removeAttribute(LOGIN_USER_KEY);
        }

    }
    //login.jsp
    public boolean doLogin(LoginUser loginUser, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext application = session.getServletContext();
        // 如果使用了Spring可以用下面的方法获取spring的context对象
        // WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(application);
        // 如果需要使用SpringMVC上下文、可以用下面的方法获取springMVC的context对象
        // WebApplicationContext mvcContext = RequestContextUtils.getWebApplicationContext(request);
        return true;
    }
}