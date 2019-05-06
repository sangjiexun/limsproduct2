package net.zjcclims.constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
    @Value("${access-token-uri}")
    private String accessTokenUri;

    @Value("${user-authorization-uri}")
    private String userAuthorizationUri;

    @Value("${application-host}")
    private String applicationHost;

    @Value("${user-info-uri}")
    private String userInfoUri;

    @Value("${client_id}")
    private String clientId;

    @Value("${secret_key}")
    private String secretKey;

    @Value("${scope}")
    private String scope;

    @SuppressWarnings("unchecked")
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        javax.servlet.http.HttpSession session = request.getSession(true);
        Object attribute = session.getAttribute("username");
        String username;
        if (attribute == null) {
            try {
                response.sendRedirect(request.getContextPath()+"/home");
//                response.sendRedirect(request.getContextPath()+"/index.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            username = attribute.toString();
        }
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,username);
        // authentication.WebAuthenticationDetails实例到details中
        setDetails(request, authRequest);
        // 通过AuthenticationManager:ProviderManager完成认证任务
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}