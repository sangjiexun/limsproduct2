package net.gvsun.lims.dto.deviceRepair;

import java.io.Serializable;
import java.math.BigDecimal;

public class SchoolDeviceRepairDTO implements Serializable {

    // 设备id
    private Integer id;

    // 设备编号
    private String deviceNumber;

    // 设备名称
    private String deviceName;

    // 设备型号
    private String devicePattern;

    // 设备金额
    private BigDecimal devicePrice;

    // 设备实验室
    private Integer labAddress;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDevicePattern() {
        return devicePattern;
    }

    public void setDevicePattern(String devicePattern) {
        this.devicePattern = devicePattern;
    }

    public BigDecimal getDevicePrice() {
        return devicePrice;
    }

    public void setDevicePrice(BigDecimal devicePrice) {
        this.devicePrice = devicePrice;
    }

    public Integer getLabAddress() {
        return labAddress;
    }

    public void setLabAddress(Integer labAddress) {
        this.labAddress = labAddress;
    }
}
