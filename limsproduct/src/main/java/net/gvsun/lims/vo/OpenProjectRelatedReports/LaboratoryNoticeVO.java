package net.gvsun.lims.vo.OpenProjectRelatedReports;

import java.io.Serializable;
import java.util.List;

/**
 * Description 实验通知单对象、教学记录单、实验开出情况统计表共用VO
 * @author Hezhaoyi
 * 2019-5-16
 */
public class LaboratoryNoticeVO implements Serializable {
    //学科
    private String subject;
    //实验课题
    private String itemName;
    //演示或分组（实验类型）
    private String itemCategory;
    //实验时间
    private String itemTime;
    //授课教师
    private String teacher;
    //相关信息
    private List<Object[]> InformationList;
    //仪器及药品
    private String deviceAndAsset;
    //学期
    private String term;
    //年级
    private String grade;
    //表头
    private String title;
    //实验班级
    private String classes;
    //学生数
    private Integer studentNum;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemTime() {
        return itemTime;
    }

    public void setItemTime(String itemTime) {
        this.itemTime = itemTime;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public List<Object[]> getInformationList() {
        return InformationList;
    }

    public void setInformationList(List<Object[]> informationList) {
        InformationList = informationList;
    }

    public String getDeviceAndAsset() {
        return deviceAndAsset;
    }

    public void setDeviceAndAsset(String deviceAndAsset) {
        this.deviceAndAsset = deviceAndAsset;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }
}
