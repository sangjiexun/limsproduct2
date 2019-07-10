package net.zjcclims.web.aop;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2015/11/25.
 */
@Component
public class DataSourceHolder {

    //线程本地环境
    private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();
    //设置数据源
    public static void setDataSource(String customerType) {
        dataSources.set(customerType);
    }
    //获取数据源
    public static String getDataSource() {
        String d =  (String) dataSources.get();
        return d;
    }
    //清除数据源
    public static void clearDataSource() {
        dataSources.remove();
    }
}
