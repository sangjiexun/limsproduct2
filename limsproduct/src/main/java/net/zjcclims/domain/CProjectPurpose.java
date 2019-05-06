package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllCProjectPurposes", query = "select myCProjectPurpose from CProjectPurpose myCProjectPurpose"),
		@NamedQuery(name = "findCProjectPurposeById", query = "select myCProjectPurpose from CProjectPurpose myCProjectPurpose where myCProjectPurpose.id = ?1"),
		@NamedQuery(name = "findCProjectPurposeByName", query = "select myCProjectPurpose from CProjectPurpose myCProjectPurpose where myCProjectPurpose.name = ?1"),
		@NamedQuery(name = "findCProjectPurposeByNameContaining", query = "select myCProjectPurpose from CProjectPurpose myCProjectPurpose where myCProjectPurpose.name like ?1"),
		@NamedQuery(name = "findCProjectPurposeByPrimaryKey", query = "select myCProjectPurpose from CProjectPurpose myCProjectPurpose where myCProjectPurpose.id = ?1") })
@Table( name = "c_project_purpose")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "limsproduct/net/zjcclims/domain", name = "CProjectPurpose")
@XmlRootElement(namespace = "limsproduct/net/zjcclims/domain")
public class CProjectPurpose implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	/**
	 */

	@Column(name = "name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String name;

	/**
	 */
	@OneToMany(mappedBy = "CProjectPurpose", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<LabConstructApp> labConstructApps;
	/**
	 */
	@OneToMany(mappedBy = "CProjectPurpose", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectPurpose> projectPurposes;

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
	public void setName(String name) {
		this.name = name;
	}

	/**
	 */
	public String getName() {
		return this.name;
	}

	/**
	 */
	public void setLabConstructApps(Set<LabConstructApp> labConstructApps) {
		this.labConstructApps = labConstructApps;
	}

	/**
	 */
	public Set<LabConstructApp> getLabConstructApps() {
		if (labConstructApps == null) {
			labConstructApps = new java.util.LinkedHashSet<LabConstructApp>();
		}
		return labConstructApps;
	}
	/**
	 */
	public void setProjectPurposes(Set<ProjectPurpose> projectPurposes) {
		this.projectPurposes = projectPurposes;
	}

	/**
	 */
	public Set<ProjectPurpose> getProjectPurposes() {
		if (projectPurposes == null) {
			projectPurposes = new java.util.LinkedHashSet<ProjectPurpose>();
		}
		return projectPurposes;
	}
	/**
	 */
	public CProjectPurpose() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(CProjectPurpose that) {
		setId(that.getId());
		setName(that.getName());
		setLabConstructApps(new java.util.LinkedHashSet<LabConstructApp>(that.getLabConstructApps()));
		setProjectPurposes(new java.util.LinkedHashSet<ProjectPurpose>(that.getProjectPurposes()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("name=[").append(name).append("] ");

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
		if (!(obj instanceof CProjectPurpose))
			return false;
		CProjectPurpose equalCheck = (CProjectPurpose) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
