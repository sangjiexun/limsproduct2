package net.gvsun.lims.dto.suppliesInStorage;
import javax.swing.plaf.PanelUI;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
/************************************************************
 * Descriptions：申购
 *
 * @作者：chenwenmin
 * @时间：2019-02-28
 ************************************************************/
public class suppliesInStorageDTO implements Serializable{
    private Integer id;
    private String date;
    private int batchNumber;
    private String academyNumber;
    private String department;
    private String sellerFactory ;
    private String goodsCategory;
    private String goodsName;
    private String typeSpecification;
    private String unit;
    private String quantity;
    private String unitPrice;
    private String money;
    private String itemPrice;
    private String totalPrice;
    private String invoiceNumber;
    private String invoiceImage;
    private String saleBillImage;
    private String remarks;
    private String StorageWay;
    private String purchaseNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getAcademyNumber() {
        return academyNumber;
    }

    public void setAcademyNumber(String academyNumber) {
        this.academyNumber = academyNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSellerFactory() {
        return sellerFactory;
    }

    public void setSellerFactory(String sellerFactory) {
        this.sellerFactory = sellerFactory;
    }

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getTypeSpecification() {
        return typeSpecification;
    }

    public void setTypeSpecification(String typeSpecification) {
        this.typeSpecification = typeSpecification;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceImage() {
        return invoiceImage;
    }

    public void setInvoiceImage(String invoiceImage) {
        this.invoiceImage = invoiceImage;
    }

    public String getSaleBillImage() {
        return saleBillImage;
    }

    public void setSaleBillImage(String saleBillImage) {
        this.saleBillImage = saleBillImage;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStorageWay() {
        return StorageWay;
    }

    public void setStorageWay(String storageWay) {
        StorageWay = storageWay;
    }

    public String getPurchaseNumber() {
        return purchaseNumber;
    }

    public void setPurchaseNumber(String purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }
}