package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllCProjectStatuss", query = "select myCProjectStatus from CProjectStatus myCProjectStatus"),
		@NamedQuery(name = "findCProjectStatusById", query = "select myCProjectStatus from CProjectStatus myCProjectStatus where myCProjectStatus.id = ?1"),
		@NamedQuery(name = "findCProjectStatusByName", query = "select myCProjectStatus from CProjectStatus myCProjectStatus where myCProjectStatus.name = ?1"),
		@NamedQuery(name = "findCProjectStatusByNameContaining", query = "select myCProjectStatus from CProjectStatus myCProjectStatus where myCProjectStatus.name like ?1"),
		@NamedQuery(name = "findCProjectStatusByPrimaryKey", query = "select myCProjectStatus from CProjectStatus myCProjectStatus where myCProjectStatus.id = ?1") })
@Table(name = "c_project_status")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "CProjectStatus")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class CProjectStatus implements Serializable {
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

	@Column(name = "name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String name;

	/**
	 */
	@OneToMany(mappedBy = "CProjectStatus", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ConstructionProject> constructionProjects;

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
	public void setConstructionProjects(Set<ConstructionProject> constructionProjects) {
		this.constructionProjects = constructionProjects;
	}

	/**
	 */
	public Set<ConstructionProject> getConstructionProjects() {
		if (constructionProjects == null) {
			constructionProjects = new java.util.LinkedHashSet<ConstructionProject>();
		}
		return constructionProjects;
	}

	/**
	 */
	public CProjectStatus() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(CProjectStatus that) {
		setId(that.getId());
		setName(that.getName());
		setConstructionProjects(new java.util.LinkedHashSet<ConstructionProject>(that.getConstructionProjects()));
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
		if (!(obj instanceof CProjectStatus))
			return false;
		CProjectStatus equalCheck = (CProjectStatus) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
