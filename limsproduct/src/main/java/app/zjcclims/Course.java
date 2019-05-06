package app.zjcclims;

public class Course {
	private String courseNumber;
	private String courseName;
	private String teacher;
	private String labroom;
	
	public Course(String courseNumber,String courseName,String teacher,String labroom){
		this.courseNumber=courseNumber;
		this.courseName=courseName;
		this.teacher=teacher;
		this.labroom=labroom;
	}
	public Course(String courseNumber,String courseName){
		this.courseNumber=courseNumber;
		this.courseName = courseName;
	}
	public String getCourseNumber(){
		return courseNumber;
	}
	public String getCourseName(){
		return courseName;
	}
	public String getTeacher(){
		return teacher;
	}
	public String getLabroom(){
		return labroom;
	}
}
