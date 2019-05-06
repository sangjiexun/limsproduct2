package net.gvsun.lims.dto.common;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 用于存放页面按钮显示的VO
 * Created by huanghao on 2018/8/20.
 */
public class JsonValueDTO implements Serializable {
    public String id;
    public String text;
    public Integer status;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
