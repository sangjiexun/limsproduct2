package net.zjcclims.util;

import com.google.inject.internal.Lists;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**************************************************************************
 * Description: HttpClient工具类
 *
 * @author:lixueteng
 * @date:2017/12/7 0007
 **************************************************************************/
public class HttpClientUtil {
    private static CookieStore store;
    static HttpClient httpClient1 = new DefaultHttpClient();

    /**************************************************************************
     * Description: 发送http请求 根据传过来的url返回对应的stream
     *
     * @author:lixueteng
     * @date:2017/12/7 0007
     **************************************************************************/
    public static InputStream getStreamWithUrl(String url) throws IOException {
        HttpGet httpGet1 = new HttpGet(url);
        HttpResponse httpResponse1 = httpClient1.execute(httpGet1);
        InputStream input = httpResponse1.getEntity().getContent();
        return input;
    }

    /**
     * 发送get请求，key-value参数
     * @param url
     * @param param
     * @return
     */
    public static String doGet(String url, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 发送get请求
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     *发送post请求，map参数
     * @param url
     * @param param
     * @return
     */
    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    public static String doPost(String url) {
        return doPost(url, (Map<String, String>) null);
    }

    /**
     * 发送post请求。json参数
     * @param url
     * @param json
     * @return
     */
    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    /**
     *发送post请求，map参数
     * @param url
     * @param param
     * @return
     */
    public static String doPost(String url, String[] param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                httpPost.addHeader(param[0], param[1]);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    /*************************************************************************************
     * Description:post请求自定义header、param
     *
     * @author: 杨新蔚
     * @date: 2019/5/28
     *************************************************************************************/
    public static String postWithoutCookie(String url,Map<String,String> params,Map<String,String> headers) throws URISyntaxException, IOException, ClassNotFoundException {
        // 创建Http Post请求
        HttpPost httpPost = new HttpPost(url);
        // 创建参数列表
        if (headers != null) {
            for(Map.Entry<String,String> en:headers.entrySet()){
                httpPost.addHeader(new BasicHeader(en.getKey(), en.getValue()));
            }
        }
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);

        //获取cookies信息
        store = client.getCookieStore();
        List<Cookie> cookieList = store.getCookies();
        for (Cookie cookie : cookieList){
            String name =cookie.getName();
            String value = cookie.getValue();
            System.out.println("访问接口成功，cookie name = "+name+", cookie value = "+value);
        }
        return result;
    }

    /*************************************************************************************
     * Description:post请求自定义header、param(用于citrix虚拟化接口)
     *
     * @author: 杨新蔚
     * @date: 2019/5/28
     *************************************************************************************/
    public static String postWithCookie(String url,Map<String,String> params,Map<String,String> headers) throws URISyntaxException, IOException, ClassNotFoundException {
        // 创建Http Post请求
        HttpPost httpPost = new HttpPost(url);
        // 创建header中的参数
        if (headers != null) {
            for(Map.Entry<String,String> en:headers.entrySet()){
                httpPost.addHeader(new BasicHeader(en.getKey(), en.getValue()));
            }
        }
        //获取cookies信息
        List<Cookie> cookieList = store.getCookies();
        for (Cookie cookie : cookieList){
            if ("CsrfToken".equals(cookie.getName())){
                httpPost.addHeader(new BasicHeader("Csrf-Token", cookie.getValue()));
            }
        }
        //创建body中的参数
        if (params != null) {
            List<NameValuePair> paramList = new ArrayList<>();
            for (String key : params.keySet()) {
                paramList.add(new BasicNameValuePair(key, params.get(key)));
            }
            // 模拟表单
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,"utf-8");
            httpPost.setEntity(entity);
        }
        DefaultHttpClient client = new DefaultHttpClient();
        //设置cookies信息
        client.setCookieStore(store);
        HttpResponse response = client.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        store=client.getCookieStore();
        /*//循环标志位，判断是否有新的cookie生成，有则写入
        int j=0;
        CookieStore storeNew = client.getCookieStore();
        List<Cookie> cookieListNew = storeNew.getCookies();
        for (int i=0;i<cookieListNew.size();i++){
            for(Cookie cookie : cookieList){
                if (cookie.getName().equals(cookieListNew.get(i).getName())){
                    j++;
                }
            }
            if (j<cookieList.size()){
                cookieList.add(cookieListNew.get(i));
            }
            j=0;
        }*/
        return result;
    }

    /*************************************************************************************
     * Description:get请求自定义header、param(用于citrix虚拟化接口)
     *
     * @author: 杨新蔚
     * @date: 2019/5/28
     *************************************************************************************/
    public static String getWithCookie(String url,Map<String,String> params,Map<String,String> headers) throws URISyntaxException, IOException, ClassNotFoundException {
        StringBuffer  stringBuffer= new StringBuffer("?");
        //获取cookies信息
        List<Cookie> cookieList = store.getCookies();
        for (Cookie cookie : cookieList){
            if ("CsrfToken".equals(cookie.getName())){
                stringBuffer.append("CsrfToken=").append(cookie.getValue()).append("&");
            }
        }
        if (params != null) {
            for (String key : params.keySet()) {
                stringBuffer.append(key).append("=").append(URLEncoder.encode(params.get(key))).append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length()-1);
        }

        // 创建Http Get请求
        HttpGet httpGet = new HttpGet(url+stringBuffer);
        // 创建header中的参数
        if (headers != null) {
            for(Map.Entry<String,String> en:headers.entrySet()){
                httpGet.addHeader(new BasicHeader(en.getKey(), en.getValue()));
            }
        }
        //获取cookies信息
        for (Cookie cookie : cookieList){
            if ("CsrfToken".equals(cookie.getName())){
                httpGet.addHeader(new BasicHeader("Csrf-Token", cookie.getValue()));
            }
        }

        //创建body中的参数

        DefaultHttpClient client = new DefaultHttpClient();
        //设置cookies信息
        client.setCookieStore(store);
        HttpResponse response = client.execute(httpGet);
        String result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        store=client.getCookieStore();

        return result;
    }



}
