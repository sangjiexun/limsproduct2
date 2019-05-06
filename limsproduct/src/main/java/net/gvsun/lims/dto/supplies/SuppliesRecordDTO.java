package net.gvsun.lims.dto.supplies;

public class SuppliesRecordDTO {
    //1、学院
    private String academy;
    //2、部门
    private String department;
    //3、物资类别
    private String suppliesCategory;
    //4、申领编号
    private String batchNumber;
    //5、年份
    private String year;
    //6、月份
    private String month;
    //7、按月物资金额
    private Float monthAmount ;
    //8、按年物资金额
    private Float yearAmount ;
    //9、时间
    private String time;


    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSuppliesCategory() {
        return suppliesCategory;
    }

    public void setSuppliesCategory(String suppliesCategory) {
        this.suppliesCategory = suppliesCategory;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Float getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(Float monthAmount) {
        this.monthAmount = monthAmount;
    }

    public Float getYearAmount() {
        return yearAmount;
    }

    public void setYearAmount(Float yearAmount) {
        this.yearAmount = yearAmount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
