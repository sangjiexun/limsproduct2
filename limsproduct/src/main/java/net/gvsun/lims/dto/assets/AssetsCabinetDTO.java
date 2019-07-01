package net.gvsun.lims.dto.assets;
import java.io.Serializable;

/************************************************************
 * Descriptions：物品柜
 *
 * @作者：吴奇臻
 * @时间：2019-03-31
 ************************************************************/
public class AssetsCabinetDTO implements Serializable{
    //1.ID
    private Integer id;
    //2.物品柜编号
    private String cabinetCode;
    //2.物品柜名称
    private String cabinetName;
    //3.主要物品类别
    private String goodsCategory;
    //4.剩余库存容量
    private String capacity;
    //5.对应物资剩余容量
    private String quantity;
    //6.物联硬件类型
    private String hardwareType;
    //7.物联IP
    private String hardwareIp;
    //8.物联服务器id
    private String serverId;
    //9.物品柜类型
    private String type;
    //10.存放地点
    private String location;
    //11.创建人
    private String createUser;
    //11.学院
    private String schoolAcademy;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCabinetCode() {
        return cabinetCode;
    }

    public void setCabinetCode(String cabinetCode) {
        this.cabinetCode = cabinetCode;
    }

    public String getCabinetName() {
        return cabinetName;
    }

    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getHardwareType() {
        return hardwareType;
    }

    public void setHardwareType(String hardwareType) {
        this.hardwareType = hardwareType;
    }

    public String getHardwareIp() {
        return hardwareIp;
    }

    public void setHardwareIp(String hardwareIp) {
        this.hardwareIp = hardwareIp;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getSchoolAcademy() {
        return schoolAcademy;
    }

    public void setSchoolAcademy(String schoolAcademy) {
        this.schoolAcademy = schoolAcademy;
    }
}