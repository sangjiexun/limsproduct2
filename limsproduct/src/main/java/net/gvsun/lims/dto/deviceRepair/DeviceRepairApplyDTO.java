package net.gvsun.lims.dto.deviceRepair;

import net.gvsun.lims.dto.common.BaseActionAuthDTO;

public class DeviceRepairApplyDTO {

    // id
    private Integer id;

    // 设备名称
    private String deviceName;

    // 设备编号
    private String deviceNumber;

    // 报修地点
    private String labAddress;

    // 创建人
    private String creator;

    // 报修时间
    private String createDate;

    // 报修描述
    private String content;

    // 当前状态
    private Integer status;

    // 报修人
    private String reportUser;

    // 预计金额
    private String expectAmount;

    // 权限控制
    private BaseActionAuthDTO baseActionAuthDTO;

    // 设备价格
    private String devicePrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getLabAddress() {
        return labAddress;
    }

    public void setLabAddress(String labAddress) {
        this.labAddress = labAddress;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReportUser() {
        return reportUser;
    }

    public void setReportUser(String reportUser) {
        this.reportUser = reportUser;
    }

    public String getExpectAmount() {
        return expectAmount;
    }

    public void setExpectAmount(String expectAmount) {
        this.expectAmount = expectAmount;
    }

    public BaseActionAuthDTO getBaseActionAuthDTO() {
        return baseActionAuthDTO;
    }

    public void setBaseActionAuthDTO(BaseActionAuthDTO baseActionAuthDTO) {
        this.baseActionAuthDTO = baseActionAuthDTO;
    }

    public String getDevicePrice() {
        return devicePrice;
    }

    public void setDevicePrice(String devicePrice) {
        this.devicePrice = devicePrice;
    }
}
