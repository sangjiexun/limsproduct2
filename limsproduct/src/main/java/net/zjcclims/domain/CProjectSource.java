package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllCProjectSources", query = "select myCProjectSource from CProjectSource myCProjectSource"),
		@NamedQuery(name = "findCProjectSourceById", query = "select myCProjectSource from CProjectSource myCProjectSource where myCProjectSource.id = ?1"),
		@NamedQuery(name = "findCProjectSourceByName", query = "select myCProjectSource from CProjectSource myCProjectSource where myCProjectSource.name = ?1"),
		@NamedQuery(name = "findCProjectSourceByNameContaining", query = "select myCProjectSource from CProjectSource myCProjectSource where myCProjectSource.name like ?1"),
		@NamedQuery(name = "findCProjectSourceByPrimaryKey", query = "select myCProjectSource from CProjectSource myCProjectSource where myCProjectSource.id = ?1") })
@Table(name = "c_project_source")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "CProjectSource")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class CProjectSource implements Serializable {
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
	@OneToMany(mappedBy = "CProjectSource", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectAcceptanceApplication> projectAcceptanceApplications;
	/**
	 */
	@OneToMany(mappedBy = "CProjectSource", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<LabConstructApp> labConstructApps;
	/**
	 */
	@OneToMany(mappedBy = "CProjectSource", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectStartedReport> projectStartedReports;

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
	public void setProjectAcceptanceApplications(Set<ProjectAcceptanceApplication> projectAcceptanceApplications) {
		this.projectAcceptanceApplications = projectAcceptanceApplications;
	}

	/**
	 */
	public Set<ProjectAcceptanceApplication> getProjectAcceptanceApplications() {
		if (projectAcceptanceApplications == null) {
			projectAcceptanceApplications = new java.util.LinkedHashSet<ProjectAcceptanceApplication>();
		}
		return projectAcceptanceApplications;
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
	public void setProjectStartedReports(Set<ProjectStartedReport> projectStartedReports) {
		this.projectStartedReports = projectStartedReports;
	}

	/**
	 */
	public Set<ProjectStartedReport> getProjectStartedReports() {
		if (projectStartedReports == null) {
			projectStartedReports = new java.util.LinkedHashSet<ProjectStartedReport>();
		}
		return projectStartedReports;
	}

	/**
	 */
	public CProjectSource() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(CProjectSource that) {
		setId(that.getId());
		setName(that.getName());
		setProjectAcceptanceApplications(new java.util.LinkedHashSet<ProjectAcceptanceApplication>(that.getProjectAcceptanceApplications()));
		setLabConstructApps(new java.util.LinkedHashSet<LabConstructApp>(that.getLabConstructApps()));
		setProjectStartedReports(new java.util.LinkedHashSet<ProjectStartedReport>(that.getProjectStartedReports()));
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
		if (!(obj instanceof CProjectSource))
			return false;
		CProjectSource equalCheck = (CProjectSource) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
