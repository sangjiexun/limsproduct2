package net.gvsun.lims.dto.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/************************************************************
 * Descriptions：共享库-User的DTO对象
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class UserDTO implements Serializable {
    //用户的编号
    private String username;
    //用户的姓名
    private String cname;
    //用户的卡号
    private String cardno;
    //用户的学院
    private String academyName;
    //用户的学院
    private String academyNumber;
    //用户的身份
    private  String userRole;

    //用户辅组功能：是否选择：0：未选，1选择
    private  int selected;

    private List<AuthorityDTO> authorityDTOs;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public String getAcademyNumber() {
        return academyNumber;
    }

    public void setAcademyNumber(String academyNumber) {
        this.academyNumber = academyNumber;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public List<AuthorityDTO> getAuthorityDTOs() {
        return authorityDTOs;
    }

    public void setAuthorityDTOs(List<AuthorityDTO> authorityDTOs) {
        this.authorityDTOs = authorityDTOs;
    }
}
