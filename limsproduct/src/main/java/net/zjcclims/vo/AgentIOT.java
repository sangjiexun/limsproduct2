package net.zjcclims.vo;

import java.io.Serializable;

/**
 * Description 发送iot参数
 * @author 陈乐为 2019年6月11日
 */
public class AgentIOT implements Serializable {
    String id;
    String cardno;
    String username;
    String cname;
    String starttime;
    String endtime;
    Integer isAdmin;
    Integer deviceindex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getDeviceindex() {
        return deviceindex;
    }

    public void setDeviceindex(Integer deviceindex) {
        this.deviceindex = deviceindex;
    }

}