package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllOperationOutlineCourses", query = "select myOperationOutlineCourse from OperationOutlineCourse myOperationOutlineCourse"),
		@NamedQuery(name = "findOperationOutlineCourseByCourseContent", query = "select myOperationOutlineCourse from OperationOutlineCourse myOperationOutlineCourse where myOperationOutlineCourse.courseContent = ?1"),
		@NamedQuery(name = "findOperationOutlineCourseById", query = "select myOperationOutlineCourse from OperationOutlineCourse myOperationOutlineCourse where myOperationOutlineCourse.id = ?1"),
		@NamedQuery(name = "findOperationOutlineCourseByPrimaryKey", query = "select myOperationOutlineCourse from OperationOutlineCourse myOperationOutlineCourse where myOperationOutlineCourse.id = ?1"),
		@NamedQuery(name = "findOperationOutlineCourseByWeek", query = "select myOperationOutlineCourse from OperationOutlineCourse myOperationOutlineCourse where myOperationOutlineCourse.week = ?1") })
@Table(name = "operation_outline_course")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "OperationOutlineCourse")
public class OperationOutlineCourse implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * �γ�����
	 * 
	 */

	@Column(name = "course_content", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String courseContent;
	/**
	 * �ܴ�
	 * 
	 */

	@Column(name = "week")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer week;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room_id", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "operation_outline_id", referencedColumnName = "id") })
	@XmlTransient
	OperationOutline operationOutline;
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "curriculum_nature", referencedColumnName = "id") })
	@XmlTransient
	CDictionary cDictionary;


	@Column(name = "course_hour")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Float courseHour;
	
	@Column(name = "course_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String courseTime;
	
	/**
	 * @return the courseHour
	 */
	public Float getCourseHour() {
		return courseHour;
	}

	/**
	 * @param courseHour the courseHour to set
	 */
	public void setCourseHour(Float courseHour) {
		this.courseHour = courseHour;
	}

	/**
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * �γ�����
	 * 
	 */
	public void setCourseContent(String courseContent) {
		this.courseContent = courseContent;
	}

	/**
	 * �γ�����
	 * 
	 */
	public String getCourseContent() {
		return this.courseContent;
	}

	/**
	 * �ܴ�
	 * 
	 */
	public void setWeek(Integer week) {
		this.week = week;
	}

	/**
	 * �ܴ�
	 * 
	 */
	public Integer getWeek() {
		return this.week;
	}
	
	public void setCourseTime(String courseTime) {
		this.courseTime = courseTime;
	}

	/**
	 * �ܴ�
	 * 
	 */
	public String getCourseTime() {
		return this.courseTime;
	}
	
	
	/**
	 */
	public CDictionary getcDictionary() {
		return cDictionary;
	}

	public void setcDictionary(CDictionary cDictionary) {
		this.cDictionary = cDictionary;
	}
	/**
	 */
	public void setLabRoom(LabRoom labRoom) {
		this.labRoom = labRoom;
	}


	/**
	 */
	@JsonIgnore
	public LabRoom getLabRoom() {
		return labRoom;
	}

	/**
	 */
	public void setOperationOutline(OperationOutline operationOutline) {
		this.operationOutline = operationOutline;
	}

	/**
	 */
	@JsonIgnore
	public OperationOutline getOperationOutline() {
		return operationOutline;
	}

	/**
	 */
	public OperationOutlineCourse() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(OperationOutlineCourse that) {
		setId(that.getId());
		setCourseContent(that.getCourseContent());
		setWeek(that.getWeek());
		setLabRoom(that.getLabRoom());
		setOperationOutline(that.getOperationOutline());
		setCourseHour(that.courseHour);
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("courseContent=[").append(courseContent).append("] ");
		buffer.append("week=[").append(week).append("] ");

		return buffer.toString();
	}

	/**
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((id == null) ? 0 : id.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof OperationOutlineCourse))
			return false;
		OperationOutlineCourse equalCheck = (OperationOutlineCourse) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
