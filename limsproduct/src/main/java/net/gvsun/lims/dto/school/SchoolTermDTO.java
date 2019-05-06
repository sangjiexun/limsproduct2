package net.gvsun.lims.dto.school;

import java.io.Serializable;
import java.util.Date;

/************************************************************
 * Descriptions：共享库-SchoolTerm的DTO对象
 *
 * @作者：魏诚
 * @时间：2018-09-04
 ************************************************************/
public class SchoolTermDTO implements Serializable {
    //schoolCourse的编号
    private int termId;
    //教学班编号
    private String termName;
    //学期开始时间
    private Date termStart;
    //学期结束时间
    private Date termEnd;
    //学年
    private String year;
    //学期编码：上半学期1，下半学期2
    private  int termCode;

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Date getTermStart() {
        return termStart;
    }

    public void setTermStart(Date termStart) {
        this.termStart = termStart;
    }

    public Date getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(Date termEnd) {
        this.termEnd = termEnd;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getTermCode() {
        return termCode;
    }

    public void setTermCode(int termCode) {
        this.termCode = termCode;
    }
}
