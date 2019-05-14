package net.gvsun.lims.dto.assets;
import java.io.Serializable;

/************************************************************
 * Descriptions：物资入库DTO
 *
 * @作者：chenwenmin
 * @时间：2019-02-28
 ************************************************************/
public class AssetsInStorageDTO implements Serializable{
    //1.ID
    private Integer id;
    //物资申领ID
    private Integer applyId;
    //入库人
    private String username;
    //申购人
    private String applicantUserName;
    //2.入库日期
    private String date;
    //3.入库编号
    private String batchNumber;
    //4.学院
    private String academyNumber;
    //5.部门
    private String department;
    //6.物品分类
    private String goodsCategory;
    //7.总价
    private String totalPrice;
    //8.发票号
    private String invoiceNumber;
    //9.发票图片
    private String invoiceImageUrl;
    //10.入库单图片
    private String goDownEntry;
    //11.备注
    private String remarks;
    //12.物品柜
    private String cabinet;
    //13.状态
    private String status;
    //14.file
    private String file;
    //15.当前审核层级
    private String curAuditLevel;
    //15.审核标志位
    private Integer auditFlag;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApplicantUserName() {
        return applicantUserName;
    }

    public void setApplicantUserName(String applicantUserName) {
        this.applicantUserName = applicantUserName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
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

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
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

    public String getInvoiceImageUrl() {
        return invoiceImageUrl;
    }

    public void setInvoiceImageUrl(String invoiceImageUrl) {
        this.invoiceImageUrl = invoiceImageUrl;
    }

    public String getGoDownEntry() {
        return goDownEntry;
    }

    public void setGoDownEntry(String goDownEntry) {
        this.goDownEntry = goDownEntry;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getCurAuditLevel() {
        return curAuditLevel;
    }

    public void setCurAuditLevel(String curAuditLevel) {
        this.curAuditLevel = curAuditLevel;
    }

    public Integer getAuditFlag() {
        return auditFlag;
    }

    public void setAuditFlag(Integer auditFlag) {
        this.auditFlag = auditFlag;
    }
}