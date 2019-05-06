package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllProjectAppMajors", query = "select myProjectAppMajor from ProjectAppMajor myProjectAppMajor"),
		@NamedQuery(name = "findProjectAppMajorById", query = "select myProjectAppMajor from ProjectAppMajor myProjectAppMajor where myProjectAppMajor.id = ?1"),
		@NamedQuery(name = "findProjectAppMajorByPrimaryKey", query = "select myProjectAppMajor from ProjectAppMajor myProjectAppMajor where myProjectAppMajor.id = ?1") })
@Table(name = "project_app_major")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectAppMajor")
public class ProjectAppMajor implements Serializable {
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_construct_id", referencedColumnName = "id") })
	@XmlTransient
	LabConstructApp labConstructApp;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "major_no", referencedColumnName = "major_number") })
	@XmlTransient
	SchoolMajor schoolMajor;

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
	public void setSchoolMajor(SchoolMajor schoolMajor) {
		this.schoolMajor = schoolMajor;
	}

	/**
	 */
	public SchoolMajor getSchoolMajor() {
		return schoolMajor;
	}

	/**
	 */
	public ProjectAppMajor() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectAppMajor that) {
		setId(that.getId());
		setLabConstructApp(that.getLabConstructApp());
		setSchoolMajor(that.getSchoolMajor());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");

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
		if (!(obj instanceof ProjectAppMajor))
			return false;
		ProjectAppMajor equalCheck = (ProjectAppMajor) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
