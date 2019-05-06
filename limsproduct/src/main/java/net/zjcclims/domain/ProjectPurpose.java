package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllProjectPurposes", query = "select myProjectPurpose from ProjectPurpose myProjectPurpose"),
		@NamedQuery(name = "findProjectPurposeById", query = "select myProjectPurpose from ProjectPurpose myProjectPurpose where myProjectPurpose.id = ?1"),
		@NamedQuery(name = "findProjectPurposeByInfo", query = "select myProjectPurpose from ProjectPurpose myProjectPurpose where myProjectPurpose.info = ?1"),
		@NamedQuery(name = "findProjectPurposeByLabConstructAppId", query = "select myProjectPurpose from ProjectPurpose myProjectPurpose where myProjectPurpose.labConstructAppId = ?1"),
		@NamedQuery(name = "findProjectPurposeByPrimaryKey", query = "select myProjectPurpose from ProjectPurpose myProjectPurpose where myProjectPurpose.id = ?1"),
		@NamedQuery(name = "findProjectPurposeByProjectPurposeId", query = "select myProjectPurpose from ProjectPurpose myProjectPurpose where myProjectPurpose.projectPurposeId = ?1") })
@Table(name = "project_purpose")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "limsproduct/net/zjcclims/domain", name = "ProjectPurpose")
public class ProjectPurpose implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id")
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	Integer id;
	/**
	 */

	@Column(name = "lab_construct_app_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer labConstructAppId;
	/**
	 */

	@Column(name = "project_purpose_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer projectPurposeId;
	/**
	 */

	@Column(name = "info", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String info;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_purpose_id", referencedColumnName = "id", insertable = false, updatable = false) })
	@XmlTransient
	CProjectPurpose CProjectPurpose;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_construct_app_id", referencedColumnName = "id", insertable = false, updatable = false) })
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
	public void setLabConstructAppId(Integer labConstructAppId) {
		this.labConstructAppId = labConstructAppId;
	}

	/**
	 */
	public Integer getLabConstructAppId() {
		return this.labConstructAppId;
	}

	/**
	 */
	public void setProjectPurposeId(Integer projectPurposeId) {
		this.projectPurposeId = projectPurposeId;
	}

	/**
	 */
	public Integer getProjectPurposeId() {
		return this.projectPurposeId;
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
	public void setCProjectPurpose(CProjectPurpose CProjectPurpose) {
		this.CProjectPurpose = CProjectPurpose;
	}

	/**
	 */
	public CProjectPurpose getCProjectPurpose() {
		return CProjectPurpose;
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
	public ProjectPurpose() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectPurpose that) {
		setId(that.getId());
		setLabConstructAppId(that.getLabConstructAppId());
		setProjectPurposeId(that.getProjectPurposeId());
		setInfo(that.getInfo());
		setCProjectPurpose(that.getCProjectPurpose());
		setLabConstructApp(that.getLabConstructApp());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("labConstructAppId=[").append(labConstructAppId).append("] ");
		buffer.append("projectPurposeId=[").append(projectPurposeId).append("] ");
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
		if (!(obj instanceof ProjectPurpose))
			return false;
		ProjectPurpose equalCheck = (ProjectPurpose) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
