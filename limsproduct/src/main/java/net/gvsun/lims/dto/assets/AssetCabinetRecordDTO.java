package net.gvsun.lims.dto.assets;

import java.util.List;

public class AssetCabinetRecordDTO {
    //物资编号
    private String assetId;
    //cas号
    private String cas;
    //物资名称
    private String cname;
    //物资分类
    private String categoryCname;
    //计量单位
    private String unit;
    //物品规格
    private String specifications;
    //物品总数
    private String sum;
    //参考单价
    private Float price;
    //生产厂家
    private String factory;
    //功能描述
    private String function;
    //物资记录id
    private String id;
    //物品柜id
    private String cabinetId;
    //物品柜编号
    private String cabinetCode;
    //物品柜名称
    private String cabinetName;
    //物资数量
    private String stockNumber;
    //所属学院
    private String schoolAcademy;
    //物资与物品柜的关系
    List<AssetCabinetRecordDTO> assetCabinetRecordDTOList;

    public String getSchoolAcademy() {
        return schoolAcademy;
    }

    public void setSchoolAcademy(String schoolAcademy) {
        this.schoolAcademy = schoolAcademy;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getCas() {
        return cas;
    }

    public void setCas(String cas) {
        this.cas = cas;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCategoryCname() {
        return categoryCname;
    }

    public void setCategoryCname(String categoryCname) {
        this.categoryCname = categoryCname;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(String cabinetId) {
        this.cabinetId = cabinetId;
    }

    public String getCabinetCode() {
        return cabinetCode;
    }

    public void setCabinetCode(String cabinetCode) {
        this.cabinetCode = cabinetCode;
    }

    public String getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(String stockNumber) {
        this.stockNumber = stockNumber;
    }

    public String getCabinetName() {
        return cabinetName;
    }

    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }

    public List<AssetCabinetRecordDTO> getAssetCabinetRecordDTOList() {
        return assetCabinetRecordDTOList;
    }

    public void setAssetCabinetRecordDTOList(List<AssetCabinetRecordDTO> assetCabinetRecordDTOList) {
        this.assetCabinetRecordDTOList = assetCabinetRecordDTOList;
    }
}
