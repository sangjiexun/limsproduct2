package net.zjcclims.web.api;

import net.gvsun.lims.dto.common.PConfigDTO;
import net.zjcclims.domain.User;
import net.zjcclims.service.common.ShareService;
import net.zjcclims.util.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 77947 on 2018/11/30.
 */
@RequestMapping("/shareApi")
@Controller("shareApi")
public class shareApi {
    @Value("${directoryEngineHost}")
    private  String directoryEngineHost;
    @Value("${resourceContainerHost}")
    private String resourceContainerHost;
    @Value("${visualHost}")
    private String visualHost;
    @Autowired
    ShareService shareService;
    @Autowired
    private HttpServletRequest request;

    @ResponseBody
    @RequestMapping("/getHosts")
    public Map getHosts(){
        Map<String,String> results = new HashMap<String,String>();
        results.put("directoryEngineHost",directoryEngineHost);
        results.put("resourceContainerHost",resourceContainerHost);
        results.put("visualHost",visualHost);
        return results;
    }

    @ResponseBody
    @RequestMapping("/getAuthorization")
    public String getAuthorization(HttpServletResponse res){
        Map<String,Object> data = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PConfigDTO pConfigDTO = shareService.getCurrentDataSourceConfiguration();
        User user = shareService.getUserDetail();
        if(user!=null){
            if(user.getUsername()==null){
                data.put("username","");
            }else{
                data.put("cname",user.getCname());
                data.put("username",user.getUsername());
                data.put("selected_role",request.getSession().getAttribute("selected_role"));
                data.put("auditServerUrl",pConfigDTO.auditServerUrl);
                data.put("authTimetableType",pConfigDTO.authTimetableType);
                data.put("proj_name", pConfigDTO.PROJECT_NAME);
                data.put("project", pConfigDTO.siteEnName);
                data.put("limsUrl", pConfigDTO.limsUrl);
                data.put("academy_number", request.getSession().getAttribute("selected_academy"));
            }
            data.put("siteName","实验室管理");
            data.put("uniCode","GvSun");
            //Authorization authorization = AuthorizationUtil.getAuthorization(shareService.getUser().getUsername(),data);

            /*if(authorization.getRequestState().equals("success")){
                String returnStr = authorization.getJwtToken();
                return returnStr;
            }*/
            String[] jwt = JWTUtil.parseJwt(data);
            return jwt[1];
            //return authorization.getErrorCode();
        }else{
            return"notAllowed";
        }
    }
}
