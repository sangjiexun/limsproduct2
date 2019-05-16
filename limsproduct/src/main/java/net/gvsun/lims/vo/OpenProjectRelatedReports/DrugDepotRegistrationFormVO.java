package net.gvsun.lims.vo.OpenProjectRelatedReports;

import java.io.Serializable;

/**
 *Desciption 药品出库登记表对象
 * @author Hezhaoyi
 * 2019-5-16
 */
public class DrugDepotRegistrationFormVO implements Serializable {
    //日期
    private String Time;
    //药品名称
    private String drugName;
    //规格
    private String specification;
    //单位
    private String unit;
    //数量
    private Integer number;
    //出库人
    private String user;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
