package net.gvsun.lims.dto.common;

import java.io.Serializable;
import java.util.List;

/**
 * 用于存放页面按钮显示的VO
 * Created by huanghao on 2018/8/20.
 */
public class SelectDTO implements Serializable {
    private List results;

    public List getResults() {
        return results;
    }

    public void setResults(List results) {
        this.results = results;
    }
}
