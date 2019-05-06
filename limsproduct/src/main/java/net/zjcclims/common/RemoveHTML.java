package net.zjcclims.common;

import java.util.regex.Pattern;

public class RemoveHTML {
        public static String Html2Text(String content){
            // <p>段落替换为换行
            content = content.replaceAll("&lt;p .*?&gt;", "\r\n");
            // <br><br/>替换为换行
            content = content.replaceAll("&lt;br\\s*/?&gt;", "\r\n");
            // 去掉其它的<>之间的东西
            content = content.replaceAll("\\&lt;.*?&gt;", "");
            // 还原HTML
            // content = HTMLDecoder.decode(content);
            return content;
        }

}
