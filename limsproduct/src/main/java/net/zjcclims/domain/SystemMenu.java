package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllSystemMenus", query = "select mySystemMenu from SystemMenu mySystemMenu"),
		@NamedQuery(name = "findSystemMenuById", query = "select mySystemMenu from SystemMenu mySystemMenu where mySystemMenu.id = ?1"),
		@NamedQuery(name = "findSystemMenuByProjectName", query = "select mySystemMenu from SystemMenu mySystemMenu where mySystemMenu.projectName = ?1"),
		@NamedQuery(name = "findSystemMenuByProjectNameContaining", query = "select mySystemMenu from SystemMenu mySystemMenu where mySystemMenu.projectName like ?1"),
		@NamedQuery(name = "findSystemMenuByName", query = "select mySystemMenu from SystemMenu mySystemMenu where mySystemMenu.name = ?1"),
		@NamedQuery(name = "findSystemMenuByNameContaining", query = "select mySystemMenu from SystemMenu mySystemMenu where mySystemMenu.name like ?1"),
		@NamedQuery(name = "findSystemMenuByPrimaryKey", query = "select mySystemMenu from SystemMenu mySystemMenu where mySystemMenu.id = ?1") })
@Table(name = "system_menu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SystemMenu")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class SystemMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ���˵���Ŀ��
	 *
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	Integer id;
	/**
	 * ���
	 *
	 */

	@Column(name = "name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String name;
	/**
	 * ����
	 *
	 */

	@Column(name = "project_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String projectName;

	@Column(name = "order_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer orderNumber;

	@Column(name = "identification")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String identification;

	@Column(name = "is_used")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer isUsed;

	@Column(name = "hyperlink")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String hyperlink;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "parent_id", referencedColumnName = "id") })
	@XmlTransient
    SystemMenu parentMenu;
	/**
	 */
	@OneToMany(mappedBy = "systemMenu", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<AuthorityMenu> authorityMenus;
	/**
	 */
	@OneToMany(mappedBy = "parentMenu", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	@OrderBy("id")//对set集合进行排序,根据id进行排序
	Set<SystemMenu> childMenus;

	/**
	 * ���˵���Ŀ��
	 *
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ���˵���Ŀ��
	 *
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ���
	 *
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ���
	 *
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * ����
	 *
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * ����
	 *
	 */
	public String getProjectName() {
		return this.projectName;
	}

	public SystemMenu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(SystemMenu parentMenu) {
		this.parentMenu = parentMenu;
	}

	/**
	 */
	public void setAuthorityMenus(Set<AuthorityMenu> authorityMenus) {
		this.authorityMenus = authorityMenus;
	}

	/**
	 */
	@JsonIgnore
	public Set<AuthorityMenu> getAuthorityMenus() {
		if (authorityMenus == null) {
			authorityMenus = new LinkedHashSet<AuthorityMenu>();
		}
		return authorityMenus;
	}

	public Set<SystemMenu> getChildMenus() {
		if (childMenus == null) {
			childMenus = new LinkedHashSet<SystemMenu>();
		}
		return childMenus;
	}

	public void setChildMenus(Set<SystemMenu> childMenus) {
		this.childMenus = childMenus;
	}

	public Integer getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public Integer getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	public String getHyperlink() {
		return hyperlink;
	}

	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink;
	}

	/**
	 */
	public SystemMenu() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SystemMenu that) {
		setName(that.getName());
		setId(that.getId());
		setHyperlink(that.getHyperlink());
		setParentMenu(that.getParentMenu());
		setProjectName(that.getProjectName());
		setIsUsed(that.getIsUsed());
		setOrderNumber(that.getOrderNumber());
		setChildMenus(that.getChildMenus());
		setAuthorityMenus(that.getAuthorityMenus());
		setIdentification(that.getIdentification());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("name=[").append(name).append("] ");
		buffer.append("projectName=[").append(projectName).append("] ");
		buffer.append("orderNumber=[").append(orderNumber).append("] ");
		buffer.append("identification=[").append(identification).append("] ");
		buffer.append("isUsed=[").append(isUsed).append("] ");
		buffer.append("hyperlink=[").append(hyperlink).append("] ");
		buffer.append("parentMenu=[").append(parentMenu).append("] ");

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
		if (!(obj instanceof SystemMenu))
			return false;
		SystemMenu equalCheck = (SystemMenu) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
