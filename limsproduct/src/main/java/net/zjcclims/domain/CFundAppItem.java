package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllCFundAppItems", query = "select myCFundAppItem from CFundAppItem myCFundAppItem"),
		@NamedQuery(name = "findCFundAppItemById", query = "select myCFundAppItem from CFundAppItem myCFundAppItem where myCFundAppItem.id = ?1"),
		@NamedQuery(name = "findCFundAppItemByName", query = "select myCFundAppItem from CFundAppItem myCFundAppItem where myCFundAppItem.name = ?1"),
		@NamedQuery(name = "findCFundAppItemByNameContaining", query = "select myCFundAppItem from CFundAppItem myCFundAppItem where myCFundAppItem.name like ?1"),
		@NamedQuery(name = "findCFundAppItemByPrimaryKey", query = "select myCFundAppItem from CFundAppItem myCFundAppItem where myCFundAppItem.id = ?1") })
@Table(name = "c_fund_app_item")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "CFundAppItem")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class CFundAppItem implements Serializable {
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
	 * ������������
	 * 
	 */

	@Column(name = "name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String name;

	/**
	 */
	@OneToMany(mappedBy = "CFundAppItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectDeviceList> projectDeviceLists;

	/**
	 */
	@OneToMany(mappedBy = "CFundAppItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectFeeList> projectFeeLists;

	/**
	 */
	@OneToMany(mappedBy = "CFundAppItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectAcceptFeeList> projectAcceptFeeLists;
	/**
	 */
	@OneToMany(mappedBy = "CFundAppItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectStartFeeList> projectStartFeeLists;

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
	 * ������������
	 *
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ������������
	 *
	 */
	public String getName() {
		return this.name;
	}

	/**
	 */
	public void setProjectDeviceLists(Set<ProjectDeviceList> projectDeviceLists) {
		this.projectDeviceLists = projectDeviceLists;
	}

	/**
	 */
	public Set<ProjectDeviceList> getProjectDeviceLists() {
		if (projectDeviceLists == null) {
			projectDeviceLists = new java.util.LinkedHashSet<ProjectDeviceList>();
		}
		return projectDeviceLists;
	}

	/**
	 */
	public void setProjectFeeLists(Set<ProjectFeeList> projectFeeLists) {
		this.projectFeeLists = projectFeeLists;
	}

	/**
	 */
	public Set<ProjectFeeList> getProjectFeeLists() {
		if (projectFeeLists == null) {
			projectFeeLists = new java.util.LinkedHashSet<ProjectFeeList>();
		}
		return projectFeeLists;
	}

	/**
	 */
	public void setProjectAcceptFeeLists(Set<ProjectAcceptFeeList> projectAcceptFeeLists) {
		this.projectAcceptFeeLists = projectAcceptFeeLists;
	}

	/**
	 */
	public Set<ProjectAcceptFeeList> getProjectAcceptFeeLists() {
		if (projectAcceptFeeLists == null) {
			projectAcceptFeeLists = new java.util.LinkedHashSet<ProjectAcceptFeeList>();
		}
		return projectAcceptFeeLists;
	}

	/**
	 */
	public void setProjectStartFeeLists(Set<ProjectStartFeeList> projectStartFeeLists) {
		this.projectStartFeeLists = projectStartFeeLists;
	}

	/**
	 */
	public Set<ProjectStartFeeList> getProjectStartFeeLists() {
		if (projectStartFeeLists == null) {
			projectStartFeeLists = new java.util.LinkedHashSet<ProjectStartFeeList>();
		}
		return projectStartFeeLists;
	}
	/**
	 */
	public CFundAppItem() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(CFundAppItem that) {
		setId(that.getId());
		setName(that.getName());
		setProjectDeviceLists(new java.util.LinkedHashSet<ProjectDeviceList>(that.getProjectDeviceLists()));
		setProjectFeeLists(new java.util.LinkedHashSet<ProjectFeeList>(that.getProjectFeeLists()));
		setProjectAcceptFeeLists(new java.util.LinkedHashSet<ProjectAcceptFeeList>(that.getProjectAcceptFeeLists()));
		setProjectStartFeeLists(new java.util.LinkedHashSet<ProjectStartFeeList>(that.getProjectStartFeeLists()));
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
		if (!(obj instanceof CFundAppItem))
			return false;
		CFundAppItem equalCheck = (CFundAppItem) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
