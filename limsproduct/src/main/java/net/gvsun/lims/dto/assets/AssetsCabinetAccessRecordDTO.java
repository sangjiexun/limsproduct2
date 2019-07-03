package net.gvsun.lims.dto.assets;
import java.io.Serializable;
import java.util.Date;

/************************************************************
 * Descriptions：物品柜
 *
 * @作者：吴奇臻
 * @时间：2019-03-31
 ************************************************************/
public class AssetsCabinetAccessRecordDTO implements Serializable{
    //1.ID
    private Integer id;
    //记录类型
    private String recordType;
    //发起日期
    private String date;
    //发起人
    private String username;
    //物品柜名称
    private String cabinetName;
    //数量
    private String quantity;
    //物品柜剩余数量
    private String amount;
    //所属学院
    private String schoolAcademy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCabinetName() {
        return cabinetName;
    }

    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSchoolAcademy() {
        return schoolAcademy;
    }

    public void setSchoolAcademy(String schoolAcademy) {
        this.schoolAcademy = schoolAcademy;
    }
}