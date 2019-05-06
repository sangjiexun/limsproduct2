package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllConstructionProjects", query = "select myConstructionProject from ConstructionProject myConstructionProject"),
		@NamedQuery(name = "findConstructionProjectById", query = "select myConstructionProject from ConstructionProject myConstructionProject where myConstructionProject.id = ?1"),
		@NamedQuery(name = "findConstructionProjectByPrimaryKey", query = "select myConstructionProject from ConstructionProject myConstructionProject where myConstructionProject.id = ?1") })
@Table(name = "construction_project")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ConstructionProject")
public class ConstructionProject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ������Ŀ�б�
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	Integer id;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_name_id", referencedColumnName = "id") })
	@XmlTransient
	LabConstructApp labConstructApp;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_status", referencedColumnName = "id") })
	@XmlTransient
	CProjectStatus CProjectStatus;

	/**
	 * ������Ŀ�б�
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ������Ŀ�б�
	 * 
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
	public void setCProjectStatus(CProjectStatus CProjectStatus) {
		this.CProjectStatus = CProjectStatus;
	}

	/**
	 */
	public CProjectStatus getCProjectStatus() {
		return CProjectStatus;
	}

	/**
	 */
	public ConstructionProject() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ConstructionProject that) {
		setId(that.getId());
		setLabConstructApp(that.getLabConstructApp());
		setCProjectStatus(that.getCProjectStatus());
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
		if (!(obj instanceof ConstructionProject))
			return false;
		ConstructionProject equalCheck = (ConstructionProject) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
