package net.gvsun.lims.dto.labroom;

import java.io.Serializable;
import java.util.Date;

/************************************************************
 * Descriptions：共享库-LabRoom的DTO对象
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class LabRoomDTO implements Serializable {
    //LabRoom的主键
    private int labRoomId;
    //LabRoom的编号
    private int labRoomNumber;
    //LabRoom的名称
    private String labRoomName;
    //LabRoom的地址
    private String labRoomAddress;
    //LabRoom的学院
    private String academyName;
    //LabRoom的学院
    private String academyNumber;
    //LabRoom的预约并发数
    private int reservationNumber;
    //LabRoom是否可用
    private int isUsed;
    //LabRoom是否可预约
    private int labRoomReservation;

    public int getLabRoomId() {
        return labRoomId;
    }

    public void setLabRoomId(int labRoomId) {
        this.labRoomId = labRoomId;
    }

    public int getLabRoomNumber() {
        return labRoomNumber;
    }

    public void setLabRoomNumber(int labRoomNumber) {
        this.labRoomNumber = labRoomNumber;
    }

    public String getLabRoomName() {
        return labRoomName;
    }

    public void setLabRoomName(String labRoomName) {
        this.labRoomName = labRoomName;
    }

    public String getLabRoomAddress() {
        return labRoomAddress;
    }

    public void setLabRoomAddress(String labRoomAddress) {
        this.labRoomAddress = labRoomAddress;
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

    public int getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public int getLabRoomReservation() {
        return labRoomReservation;
    }

    public void setLabRoomReservation(int labRoomReservation) {
        this.labRoomReservation = labRoomReservation;
    }
}
