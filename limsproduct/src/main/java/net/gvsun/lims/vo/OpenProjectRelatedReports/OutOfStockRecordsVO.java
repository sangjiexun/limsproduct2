package net.gvsun.lims.vo.OpenProjectRelatedReports;
import java.io.Serializable;

/**
 * Description 领用出库对象-低值易耗品领用登记单/耗材领用登记单共用VO
 * @author Hezhaoyi
 * 2019-5-16
 */
public class OutOfStockRecordsVO implements Serializable {
    //日期
    private String time;
    //用途
    private String usage;
    //低值易耗品名称和规格
    private String nameAndSpecifications;
    //领用数量
    private String lendingNum;
    //回收数量
    private String returnNum;
    //使用情况
    private String useSituation;
    //领用人
    private String lendingUser;
    //剩余库存量
    private Integer remainQuantity;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getNameAndSpecifications() {
        return nameAndSpecifications;
    }

    public void setNameAndSpecifications(String nameAndSpecifications) {
        this.nameAndSpecifications = nameAndSpecifications;
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

    public Integer getRemainQuantity() {
        return remainQuantity;
    }

    public void setRemainQuantity(Integer remainQuantity) {
        this.remainQuantity = remainQuantity;
    }
}
