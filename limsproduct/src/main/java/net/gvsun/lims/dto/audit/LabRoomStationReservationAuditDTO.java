package net.gvsun.lims.dto.audit;

/**
 * 工位预约审核页面详情传参DTO
 */
public class LabRoomStationReservationAuditDTO {

    /**
     * 实验室名称
     */
    private String labRoomName;

    /**
     * 实验室编号
     */
    private String labRoomNumber;

    /**
     * 预约日期
     */
    private String reservationDate;

    /**
     * 预约时间
     */
    private String reservationTime;

    /**
     * 预约工位数
     */
    private Integer stationCount;

    /**
     * 申请人姓名
     */
    private String createUser;

    /**
     * 申请人用户名
     */
    private String createUsername;

    public String getLabRoomName() {
        return labRoomName;
    }

    public void setLabRoomName(String labRoomName) {
        this.labRoomName = labRoomName;
    }

    public String getLabRoomNumber() {
        return labRoomNumber;
    }

    public void setLabRoomNumber(String labRoomNumber) {
        this.labRoomNumber = labRoomNumber;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public Integer getStationCount() {
        return stationCount;
    }

    public void setStationCount(Integer stationCount) {
        this.stationCount = stationCount;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }
}
