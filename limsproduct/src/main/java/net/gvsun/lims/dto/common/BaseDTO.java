package net.gvsun.lims.dto.common;

import java.io.Serializable;
import java.util.List;

/**
 * 用于存放页面按钮显示的VO
 * Created by huanghao on 2018/8/20.
 */
public class BaseDTO implements Serializable {
    private List rows;
    private long total;

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
