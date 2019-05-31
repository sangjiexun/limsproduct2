package net.gvsun.lims.dto.assets;
import java.io.Serializable;

/************************************************************
 * Descriptions：物品柜具体信息
 *
 * @作者：吴奇臻
 * @时间：2019-03-31
 ************************************************************/
public class AssetsCabinetWareHouseDTO implements Serializable{
    //1.柜门ID
    private Integer id;
    //1.柜门编号
    private String wareHouseCode;
    //2.柜门名称
    private String wareHouseName;
    //2.物品柜编号
    private String cabinetCode;
    //2.物品柜id
    private Integer cabinetId;
    //3.主要物品类别
    private String goodsCategory;
    //4.剩余库存容量
    private String capacity;
    //5.对应物资剩余容量
    private String quantity;



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

    public String getWareHouseCode() {
        return wareHouseCode;
    }

    public void setWareHouseCode(String wareHouseCode) {
        this.wareHouseCode = wareHouseCode;
    }

    public String getWareHouseName() {
        return wareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        this.wareHouseName = wareHouseName;
    }

    public Integer getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(Integer cabinetId) {
        this.cabinetId = cabinetId;
    }
}