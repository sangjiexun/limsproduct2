package net.gvsun.lims.vo.OpenProjectRelatedReports;

import java.io.Serializable;

public class ReceiptOfLowValueConsumablesVO implements Serializable {
    //日期
    private String Time;
    //用途
    private String usage;
    //低值易耗品名称和规格
    private String NameAndSpecifications;
    //领用数量
    private String lendingNum;
    //回收数量
    private String returnNum;
    //使用情况
    private String useSituation;
    //领用人
    private String lendingUser;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getNameAndSpecifications() {
        return NameAndSpecifications;
    }

    public void setNameAndSpecifications(String nameAndSpecifications) {
        NameAndSpecifications = nameAndSpecifications;
    }

    public String getLendingNum() {
        return lendingNum;
    }

    public void setLendingNum(String lendingNum) {
        this.lendingNum = lendingNum;
    }

    public String getReturnNum() {
        return returnNum;
    }

    public void setReturnNum(String returnNum) {
        this.returnNum = returnNum;
    }

    public String getUseSituation() {
        return useSituation;
    }

    public void setUseSituation(String useSituation) {
        this.useSituation = useSituation;
    }

    public String getLendingUser() {
        return lendingUser;
    }

    public void setLendingUser(String lendingUser) {
        this.lendingUser = lendingUser;
    }
}
