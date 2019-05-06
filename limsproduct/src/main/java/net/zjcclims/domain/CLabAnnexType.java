package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllCLabAnnexTypes", query = "select myCLabAnnexType from CLabAnnexType myCLabAnnexType"),
		@NamedQuery(name = "findCLabAnnexTypeById", query = "select myCLabAnnexType from CLabAnnexType myCLabAnnexType where myCLabAnnexType.id = ?1"),
		@NamedQuery(name = "findCLabAnnexTypeByName", query = "select myCLabAnnexType from CLabAnnexType myCLabAnnexType where myCLabAnnexType.name = ?1"),
		@NamedQuery(name = "findCLabAnnexTypeByNameContaining", query = "select myCLabAnnexType from CLabAnnexType myCLabAnnexType where myCLabAnnexType.name like ?1"),
		@NamedQuery(name = "findCLabAnnexTypeByPrimaryKey", query = "select myCLabAnnexType from CLabAnnexType myCLabAnnexType where myCLabAnnexType.id = ?1") })
@Table(name = "c_lab_annex_type")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "CLabAnnexType")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class CLabAnnexType implements Serializable {
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
	 * ʵ�������
	 * 
	 */

	@Column(name = "name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String name;


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
	 * ʵ�������
	 *
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ʵ�������
	 *
	 */
	public String getName() {
		return this.name;
	}


	/**
	 */
	public CLabAnnexType() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(CLabAnnexType that) {
		setId(that.getId());
		setName(that.getName());
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
		if (!(obj instanceof CLabAnnexType))
			return false;
		CLabAnnexType equalCheck = (CLabAnnexType) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
