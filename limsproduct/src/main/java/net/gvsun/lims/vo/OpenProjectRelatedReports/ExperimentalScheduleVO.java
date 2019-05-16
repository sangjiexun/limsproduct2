package net.gvsun.lims.vo.OpenProjectRelatedReports;

import java.io.Serializable;

/**
 * Description 实验计划表报表对象
 *  @author Hezhaoyi
 *  2019-5-16
 */
public class ExperimentalScheduleVO implements Serializable {
    //编号
    private Integer id;
    //实验内容
    private String itemName;
    //器材-实验物资
    private String itemAssets;
    //器材-实验设备
    private String itemDecvices;
    //实验类型
    private String itemCategory;
    //计划时间
    private String planTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemAssets() {
        return itemAssets;
    }

    public void setItemAssets(String itemAssets) {
        this.itemAssets = itemAssets;
    }

    public String getItemDecvices() {
        return itemDecvices;
    }

    public void setItemDecvices(String itemDecvices) {
        this.itemDecvices = itemDecvices;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }
}
