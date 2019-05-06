package net.zjcclims.constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ly.uap.client.authentication.AttributePrincipal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    @SuppressWarnings("unchecked")
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();
        String username = principal.getName();
//    	System.out.println(username);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, username);
        //authentication.WebAuthenticationDetails实例到details中
        setDetails(request, authRequest);
        //通过AuthenticationManager:ProviderManager完成认证任务
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}