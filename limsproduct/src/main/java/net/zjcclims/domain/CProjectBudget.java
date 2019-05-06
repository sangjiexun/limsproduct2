package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllCProjectBudgets", query = "select myCProjectBudget from CProjectBudget myCProjectBudget"),
		@NamedQuery(name = "findCProjectBudgetById", query = "select myCProjectBudget from CProjectBudget myCProjectBudget where myCProjectBudget.id = ?1"),
		@NamedQuery(name = "findCProjectBudgetByName", query = "select myCProjectBudget from CProjectBudget myCProjectBudget where myCProjectBudget.name = ?1"),
		@NamedQuery(name = "findCProjectBudgetByNameContaining", query = "select myCProjectBudget from CProjectBudget myCProjectBudget where myCProjectBudget.name like ?1"),
		@NamedQuery(name = "findCProjectBudgetByPrimaryKey", query = "select myCProjectBudget from CProjectBudget myCProjectBudget where myCProjectBudget.id = ?1") })
@Table(name = "c_project_budget")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "CProjectBudget")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class CProjectBudget implements Serializable {
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
	@OneToMany(mappedBy = "CProjectBudget", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectBudget> projectBudgets;

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
	public void setProjectBudgets(Set<ProjectBudget> projectBudgets) {
		this.projectBudgets = projectBudgets;
	}

	/**
	 */
	public Set<ProjectBudget> getProjectBudgets() {
		if (projectBudgets == null) {
			projectBudgets = new java.util.LinkedHashSet<ProjectBudget>();
		}
		return projectBudgets;
	}

	/**
	 */
	public CProjectBudget() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(CProjectBudget that) {
		setId(that.getId());
		setName(that.getName());
		setProjectBudgets(new java.util.LinkedHashSet<ProjectBudget>(that.getProjectBudgets()));
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
		if (!(obj instanceof CProjectBudget))
			return false;
		CProjectBudget equalCheck = (CProjectBudget) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
