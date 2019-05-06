package net.gvsun.lims.dto.common;

import java.io.Serializable;
import java.util.List;

/**
 * 用于存放页面按钮显示的VO
 * Created by huanghao on 2018/8/20.
 */
public class MessageDTO implements Serializable {
    private String type;
    private String text;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
