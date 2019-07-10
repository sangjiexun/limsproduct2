package net.zjcclims.aop;


import net.zjcclims.datasource.DataSourceHolder;
import net.zjcclims.util.CookieUtil;
import net.zjcclims.web.common.PConfig;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;


/*********************************************************************
 * Description:动态数据源拦截类
 * @author: lay
 * @date :2019/1/14
 * @return:
 **********************************************************************/
@Aspect
@Component
public class RoutingAspect {
    @Pointcut("execution(* net..*.*(..))")

    private void anyMethod(){}//定义一个切入点

    @Before("anyMethod()")
    public void dataSourceChange() {
        //获取RequestAttributes
        Cookie cookie = CookieUtil.getCookieByName("dataResource");
        if (cookie!=null) {
            String data = cookie.getValue();
            PConfig.PROJECT_NAME = data;
            DataSourceHolder.clearDataSource();
            DataSourceHolder.setDataSource(data);
        }
    }

}
