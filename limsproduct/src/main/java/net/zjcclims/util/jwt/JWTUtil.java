package net.zjcclims.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {
    private static final String secret = "mySecret";
    private static final String header = "Authorization";
    private static final String head = "Bearer";
    private static final Long expiration = 60000l;
    /*********************************************************************
     * Description:解析jwt
     * @author: lzw
     * @date :2018/10/30
     **********************************************************************/
    public static Map<String,String> testJwt(HttpServletRequest request){
        String token = request.getHeader(header);
        Map<String,String> returnMap = new HashMap<>();
        returnMap.put("state","fail");
        if (token != null) {
            // parse the token.
            Claims message = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace(head, ""))
                    .getBody();

            if (message != null) {
                returnMap.put("state","success");
                for(String key : message.keySet()){
                    returnMap.put(key,message.get(key).toString());
                }
            }
        }
        return returnMap;
    }

    /*********************************************************************
     * Description:生成jwt token
     * @author: lzw
     * @date :2018/10/30
     * @return:String[] String[0]为responseHeader的key String[1]为value
     **********************************************************************/
    public static String[] parseJwt(Map<String,Object> messages){
        JwtBuilder jb = new DefaultJwtBuilder();
        Date now = new Date();
        Date afterDate = new Date(now .getTime() + expiration);//10秒后的时间
        jb.setClaims(messages).setExpiration(afterDate).signWith(SignatureAlgorithm.HS512, secret);
        String token = jb.compact();
        String[] s = {header,head + token};
        return s;
    }
}
