package net.zjcclims.web.aop;


import net.zjcclims.util.CookieUtil;
import net.zjcclims.web.common.PConfig;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.io.*;
import java.util.Properties;


/*********************************************************************
 * Description:动态数据源拦截类
 * @author: lay
 * @date :2019/1/14
 * @return:
 **********************************************************************/
@Aspect
@Component
public class RoutingAspect {
    @Autowired
    PConfig pConfig;
    @Pointcut("!execution(* net.zjcclims.web.common.*.*(..)) &&execution(* net..*.*(..))")
    private void anyMethod(){}//定义一个切入点



    @Before("anyMethod()")
    public void dataSourceChange() {
        //获取RequestAttributes
        Cookie cookie = CookieUtil.getCookieByName("dataResource");
        if (cookie!=null) {
            try {
                String data = cookie.getValue();
                DataSourceHolder.clearDataSource();
                DataSourceHolder.setDataSource(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            DataSourceHolder.clearDataSource();
            DataSourceHolder.setDataSource(pConfig.PROJECT_NAME);
        }
    }

}
