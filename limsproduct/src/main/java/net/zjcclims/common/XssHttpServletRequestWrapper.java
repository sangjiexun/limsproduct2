package net.zjcclims.common;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {  
    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }
    public String[] getParameterValues(String parameter) {
      String[] values = super.getParameterValues(parameter);
      if (values==null)  {
                  return null;
          }
      int count = values.length;
      String[] encodedValues = new String[count];
      for (int i = 0; i < count; i++) {
                 encodedValues[i] = cleanXSS(values[i]);
       }
      return encodedValues;
    }
    public String getParameter(String parameter) {
          String value = super.getParameter(parameter);
          if (value == null) {
                 return null;
                  }
          return cleanXSS(value);
    }
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null)
            return null;
        return cleanXSS(value);
    }
    private String cleanXSS(String value) {
                //在这里增加或替换过滤规则
        value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;"); //影响到ueditor的标签
        // 影响courseNo传值了！
        //value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        value = value.replaceAll("'", "&#39;");
        value = value.replaceAll("eval\\((.*)\\)", "");
        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        value = value.replaceAll("=", "");
        //value = value.replaceAll("+", "");
        value = value.replaceAll("script", "");
        value = value.replaceAll("select", "");
        //value = value.replaceAll("*", "");
        value = value.replaceAll("%", "");
        //value = value.replaceAll("@", "");2018.4.18隐 会影响保存个人信息的邮件
        value = value.replaceAll("insert", "");
        value = value.replaceAll("delete", "");
        value = value.replaceAll("update", "");
        value = value.replaceAll("like'", "");
        value = value.replaceAll("#", "");
        value = value.replaceAll("where", "");
        return value;
    }

} 