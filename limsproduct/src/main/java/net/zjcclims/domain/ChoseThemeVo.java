package net.zjcclims.domain;

import java.io.Serializable;
import java.util.Calendar;

/**
 */


public class ChoseThemeVo implements Serializable {
	

	Integer id;
	

	
	Integer studentNumber;
	
	
	Calendar startTime;
	
	Calendar endTime;
	
	
	Integer state;
	
	
	Integer type;
	
	String tittle;
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTittle() {
		return tittle;
	}

	public void setTittle(String tittle) {
		this.tittle = tittle;
	}

	
	Integer theYear;
	

	
	Integer batchNumber;
	

	
	Integer advanceDay;
	
	Integer teacherNumber;
	
	Integer maxStudent;

	
	Calendar expectDeadline;
	
	Calendar expectStartline;
	
	Calendar lastBatchEndTime;
	int isOverCurrTime;
	public int getIsOverCurrTime() {
		return isOverCurrTime;
	}

	public void setIsOverCurrTime(int isOverCurrTime) {
		this.isOverCurrTime = isOverCurrTime;
	}
	public Calendar getLastBatchEndTime() {
		return lastBatchEndTime;
	}

	public void setLastBatchEndTime(Calendar lastBatchEndTime) {
		this.lastBatchEndTime = lastBatchEndTime;
	}
	public Calendar getExpectStartline() {
		return expectStartline;
	}

	public void setExpectStartline(Calendar expectStartline) {
		this.expectStartline = expectStartline;
	}

	public Calendar getExpectDeadline() {
		return expectDeadline;
	}

	public void setExpectDeadline(Calendar expectDeadline) {
		this.expectDeadline = expectDeadline;
	}

	

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Integer getId() {
		return this.id;
	}

	
	public void setStudentNumber(Integer studentNumber) {
		this.studentNumber = studentNumber;
	}

	
	public Integer getStudentNumber() {
		return this.studentNumber;
	}

	
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	
	public Calendar getStartTime() {
		return this.startTime;
	}

	
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	
	public Calendar getEndTime() {
		return this.endTime;
	}

	
	public void setState(Integer state) {
		this.state = state;
	}

	
	public Integer getState() {
		return this.state;
	}

	
	public void setTheYear(Integer theYear) {
		this.theYear = theYear;
	}

	
	public Integer getTheYear() {
		return this.theYear;
	}

	
	public void setBatchNumber(Integer batchNumber) {
		this.batchNumber = batchNumber;
	}

	
	public Integer getBatchNumber() {
		return this.batchNumber;
	}

	
	public void setAdvanceDay(Integer advanceDay) {
		this.advanceDay = advanceDay;
	}

	/**
	 * ��ǰ��������
	 * 
	 */
	public Integer getAdvanceDay() {
		return this.advanceDay;
	}

	/**
	 * ��ʦ����
	 * 
	 */
	public void setTeacherNumber(Integer teacherNumber) {
		this.teacherNumber = teacherNumber;
	}

	/**
	 * ��ʦ����
	 * 
	 */
	public Integer getTeacherNumber() {
		return this.teacherNumber;
	}

	/**
	 * ��ʦ������ѧ������
	 * 
	 */
	public void setMaxStudent(Integer maxStudent) {
		this.maxStudent = maxStudent;
	}

	/**
	 * ��ʦ������ѧ������
	 * 
	 */
	public Integer getMaxStudent() {
		return this.maxStudent;
	}

	
	/**
	 */
	public ChoseThemeVo() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("studentNumber=[").append(studentNumber).append("] ");
		buffer.append("startTime=[").append(startTime).append("] ");
		buffer.append("endTime=[").append(endTime).append("] ");
		buffer.append("state=[").append(state).append("] ");
		buffer.append("theYear=[").append(theYear).append("] ");
		buffer.append("batchNumber=[").append(batchNumber).append("] ");
		buffer.append("advanceDay=[").append(advanceDay).append("] ");
		buffer.append("teacherNumber=[").append(teacherNumber).append("] ");
		buffer.append("maxStudent=[").append(maxStudent).append("] ");

		return buffer.toString();
	}

	
}
