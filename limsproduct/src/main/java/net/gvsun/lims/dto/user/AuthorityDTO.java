package net.gvsun.lims.dto.user;

import java.io.Serializable;
import java.util.Date;

/************************************************************
 * Descriptions：共享库-SchoolTerm的DTO对象
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class AuthorityDTO implements Serializable {
    //schoolCourse的编号
    private int id;
    //教学班编号
    private String authorityName;
    //学期开始时间
    private String cname;
    //学期结束时间
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
