package net.gvsun.lims.vo.preCourseManagement;

import java.io.Serializable;

/****************************************************************************
 * @描述： 预排课房间管理模块
 * @作者 ：张德冰
 * @时间： 2018-12-18
 ****************************************************************************/
public class PreLabRoomListVO implements Serializable {
    //编号
    private Integer Id;
    //实验室名称
    private String roomName;
    //容量
    private String capaRange;
    //房间布局类型
    private String roomType;
    //学院名称
    private String academyName;
    //是否有权限操作
    private Integer isAudit;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCapaRange() {
        return capaRange;
    }

    public void setCapaRange(String capaRange) {
        this.capaRange = capaRange;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public Integer getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Integer isAudit) {
        this.isAudit = isAudit;
    }

}
