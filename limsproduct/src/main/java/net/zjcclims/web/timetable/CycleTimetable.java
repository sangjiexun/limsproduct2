package net.zjcclims.web.timetable;


public class CycleTimetable {
  private Integer labRoomId;
  private Integer operationItem;
  private Integer week;
  private Integer weekday;
  private Integer startClass;
  private Integer endClass;
  private Integer groupId;
  public Integer getGroupId() {
	return groupId;
}
public void setGroupId(Integer groupId) {
	this.groupId = groupId;
}
public Integer getLabRoomId() {
	return labRoomId;
}
public void setLabRoomId(Integer labRoomId) {
	this.labRoomId = labRoomId;
}
public Integer getOperationItem() {
	return operationItem;
}
public void setOperationItem(Integer operationItem) {
	this.operationItem = operationItem;
}
public Integer getWeek() {
	return week;
}
public void setWeek(Integer week) {
	this.week = week;
}
public Integer getWeekday() {
	return weekday;
}
public void setWeekday(Integer weekday) {
	this.weekday = weekday;
}
public Integer getStartClass() {
	return startClass;
}
public void setStartClass(Integer startClass) {
	this.startClass = startClass;
}
public Integer getEndClass() {
	return endClass;
}
public void setEndClass(Integer endClass) {
	this.endClass = endClass;
}
public String getTeacher() {
	return teacher;
}
public void setTeacher(String teacher) {
	this.teacher = teacher;
}
private String teacher;
private String tutor;
public String getTutor() {
	return tutor;
}
public void setTutor(String tutor) {
	this.tutor = tutor;
}
}
