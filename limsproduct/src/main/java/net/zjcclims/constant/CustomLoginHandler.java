package net.zjcclims.constant;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class CustomLoginHandler extends
        SavedRequestAwareAuthenticationSuccessHandler {

    @Value("${cms_url}")
    private String cmsUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {

        //这里可以追加开发人员自己的额外处理
        System.out
                .println("CustomLoginHandler.onAuthenticationSuccess() is called!");
        if(cmsUrl!=null&&!cmsUrl.contains("$")&&!cmsUrl.equals("")){
            javax.servlet.http.HttpSession session = request.getSession(true);
            Object attribute = session.getAttribute("username");
            response.sendRedirect(cmsUrl+attribute);
        }else{
//            super.onAuthenticationSuccess(request, response, authentication);
            response.sendRedirect(request.getContextPath()+super.getDefaultTargetUrl());
        }
    }

}