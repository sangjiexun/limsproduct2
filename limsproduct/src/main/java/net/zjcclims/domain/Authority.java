package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Set;

import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;

import javax.persistence.*;


@Entity
@NamedQueries({
		@NamedQuery(name = "findAllAuthoritys", query = "select myAuthority from Authority myAuthority"),
		@NamedQuery(name = "findAuthorityByAuthorityName", query = "select myAuthority from Authority myAuthority where myAuthority.authorityName = ?1"),
		@NamedQuery(name = "findAuthorityByAuthorityNameContaining", query = "select myAuthority from Authority myAuthority where myAuthority.authorityName like ?1"),
		@NamedQuery(name = "findAuthorityByCname", query = "select myAuthority from Authority myAuthority where myAuthority.cname = ?1"),
		@NamedQuery(name = "findAuthorityByCnameContaining", query = "select myAuthority from Authority myAuthority where myAuthority.cname like ?1"),
		@NamedQuery(name = "findAuthorityById", query = "select myAuthority from Authority myAuthority where myAuthority.id = ?1"),
		@NamedQuery(name = "findAuthorityByPrimaryKey", query = "select myAuthority from Authority myAuthority where myAuthority.id = ?1"),
		@NamedQuery(name = "findAuthorityByType", query = "select myAuthority from Authority myAuthority where myAuthority.type = ?1") })
@Table(name = "authority")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "Authority")
public class Authority implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	Integer id;

	@Column(name = "authority_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String authorityName;

	@Column(name = "cname")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String cname;
	/**
	 *
	 * 
	 */

	@Column(name = "type")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer type;

	@Column(name = "manage_table")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String manageTable;

	public String getManageTable() {
		return manageTable;
	}

	public void setManageTable(String manageTable) {
		this.manageTable = manageTable;
	}

	/**
	 */
	@ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.User> users;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	public String getAuthorityName() {
		return this.authorityName;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCname() {
		return this.cname;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}

	/**
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/**
	 */
	@JsonIgnore
	public Set<User> getUsers() {
		if (users == null) {
			users = new java.util.LinkedHashSet<net.zjcclims.domain.User>();
		}
		return users;
	}


	/**
	 */
	public Authority() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(Authority that) {
		setId(that.getId());
		setAuthorityName(that.getAuthorityName());
		setCname(that.getCname());
		setType(that.getType());
		setUsers(new java.util.LinkedHashSet<net.zjcclims.domain.User>(that.getUsers()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("authorityName=[").append(authorityName).append("] ");
		buffer.append("cname=[").append(cname).append("] ");
		buffer.append("type=[").append(type).append("] ");

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
		if (!(obj instanceof Authority))
			return false;
		Authority equalCheck = (Authority) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
