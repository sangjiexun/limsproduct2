package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllProjectAppCourses", query = "select myProjectAppCourse from ProjectAppCourse myProjectAppCourse"),
		@NamedQuery(name = "findProjectAppCourseById", query = "select myProjectAppCourse from ProjectAppCourse myProjectAppCourse where myProjectAppCourse.id = ?1"),
		@NamedQuery(name = "findProjectAppCourseByInfo", query = "select myProjectAppCourse from ProjectAppCourse myProjectAppCourse where myProjectAppCourse.info = ?1"),
		@NamedQuery(name = "findProjectAppCourseByInfoContaining", query = "select myProjectAppCourse from ProjectAppCourse myProjectAppCourse where myProjectAppCourse.info like ?1"),
		@NamedQuery(name = "findProjectAppCourseByPrimaryKey", query = "select myProjectAppCourse from ProjectAppCourse myProjectAppCourse where myProjectAppCourse.id = ?1") })
@Table(name = "project_app_course")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectAppCourse")
public class ProjectAppCourse implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	Integer id;
	/**
	 */

	@Column(name = "info", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String info;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "course_no", referencedColumnName = "course_no") })
	@XmlTransient
	SchoolCourse schoolCourse;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_construct_app_id", referencedColumnName = "id") })
	@XmlTransient
	LabConstructApp labConstructApp;

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
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 */
	public String getInfo() {
		return this.info;
	}

	/**
	 */
	public void setSchoolCourse(SchoolCourse schoolCourse) {
		this.schoolCourse = schoolCourse;
	}

	/**
	 */
	public SchoolCourse getSchoolCourse() {
		return schoolCourse;
	}

	/**
	 */
	public void setLabConstructApp(LabConstructApp labConstructApp) {
		this.labConstructApp = labConstructApp;
	}

	/**
	 */
	public LabConstructApp getLabConstructApp() {
		return labConstructApp;
	}

	/**
	 */
	public ProjectAppCourse() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectAppCourse that) {
		setId(that.getId());
		setInfo(that.getInfo());
		setSchoolCourse(that.getSchoolCourse());
		setLabConstructApp(that.getLabConstructApp());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("info=[").append(info).append("] ");

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
		if (!(obj instanceof ProjectAppCourse))
			return false;
		ProjectAppCourse equalCheck = (ProjectAppCourse) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
