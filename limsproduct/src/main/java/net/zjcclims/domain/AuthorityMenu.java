package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllAuthorityMenus", query = "select myAuthorityMenu from AuthorityMenu myAuthorityMenu"),
		@NamedQuery(name = "findAuthorityMenuById", query = "select myAuthorityMenu from AuthorityMenu myAuthorityMenu where myAuthorityMenu.id = ?1"),
		@NamedQuery(name = "findAuthorityMenuByPrimaryKey", query = "select myAuthorityMenu from AuthorityMenu myAuthorityMenu where myAuthorityMenu.id = ?1") })
@Table(name = "authority_menu")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "AuthorityMenu")
public class AuthorityMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ��ɫĿ¼Ȩ�ޱ�
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "menu_id", referencedColumnName = "id") })
	@XmlTransient
	SystemMenu systemMenu;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "authority_id", referencedColumnName = "id") })
	@XmlTransient
	Authority authority;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "realm_id", referencedColumnName = "id") })
	@XmlTransient
	Realm realm;

	@Column(name = "academy_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String academyNumber;

	public String getAcademyNumber() {
		return academyNumber;
	}

	public void setAcademyNumber(String academyNumber) {
		this.academyNumber = academyNumber;
	}

	/**
	 * ��ɫĿ¼Ȩ�ޱ�
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ��ɫĿ¼Ȩ�ޱ�
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	public SystemMenu getSystemMenu() {
		return systemMenu;
	}

	public void setSystemMenu(SystemMenu systemMenu) {
		this.systemMenu = systemMenu;
	}

	/**
	 */
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	/**
	 */
	@JsonIgnore
	public Authority getAuthority() {
		return authority;
	}

	/**
	 */
	public void setRealm(Realm realm) {
		this.realm = realm;
	}

	/**
	 */
	@JsonIgnore
	public Realm getRealm() {
		return realm;
	}

	/**
	 */
	public AuthorityMenu() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(AuthorityMenu that) {
		setId(that.getId());
		setSystemMenu(that.getSystemMenu());
		setAuthority(that.getAuthority());
		setRealm(that.getRealm());
		setAcademyNumber(that.getAcademyNumber());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("academyNumber=[").append(academyNumber).append("] ");

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
		if (!(obj instanceof AuthorityMenu))
			return false;
		AuthorityMenu equalCheck = (AuthorityMenu) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
