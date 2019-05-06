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
    //2.物品柜名称
    private String cabinetName;
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
}