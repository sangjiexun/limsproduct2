package net.zjcclims.vo;

import java.io.Serializable;

/**
 * Description 封装查询参数
 * @author 陈乐为 2019年4月16日
 */
public class QueryParamsVO implements Serializable {
    // 学期id
    int term_id;
    // 校区编号
    String campus_no;
    // 学院编号
    String academy_number;
    // 中心id
    int center_id;
    // 实验室id
    int lab_id;
    // 教师工号/学生学号等
    String user_name;
    // 开始日期
    String start_date;
    // 结束日期
    String end_date;
    // 综合查询
    String query_params;
    // 标志位
    int type;
    // 分页参数
    int curr_page;
    int page_size;

    public int getTerm_id() {
        return term_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    public String getCampus_no() {
        return campus_no;
    }

    public void setCampus_no(String campus_no) {
        this.campus_no = campus_no;
    }

    public String getAcademy_number() {
        return academy_number;
    }

    public void setAcademy_number(String academy_number) {
        this.academy_number = academy_number;
    }

    public int getCenter_id() {
        return center_id;
    }

    public void setCenter_id(int center_id) {
        this.center_id = center_id;
    }

    public int getLab_id() {
        return lab_id;
    }

    public void setLab_id(int lab_id) {
        this.lab_id = lab_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getQuery_params() {
        return query_params;
    }

    public void setQuery_params(String query_params) {
        this.query_params = query_params;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCurr_page() {
        return curr_page;
    }

    public void setCurr_page(int curr_page) {
        this.curr_page = curr_page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }
}