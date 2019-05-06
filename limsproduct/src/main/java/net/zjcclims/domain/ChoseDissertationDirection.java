package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllChoseDissertationDirections", query = "select myChoseDissertationDirection from ChoseDissertationDirection myChoseDissertationDirection"),
		@NamedQuery(name = "findChoseDissertationDirectionById", query = "select myChoseDissertationDirection from ChoseDissertationDirection myChoseDissertationDirection where myChoseDissertationDirection.id = ?1"),
		@NamedQuery(name = "findChoseDissertationDirectionByName", query = "select myChoseDissertationDirection from ChoseDissertationDirection myChoseDissertationDirection where myChoseDissertationDirection.name = ?1"),
		@NamedQuery(name = "findChoseDissertationDirectionByNameContaining", query = "select myChoseDissertationDirection from ChoseDissertationDirection myChoseDissertationDirection where myChoseDissertationDirection.name like ?1"),
		@NamedQuery(name = "findChoseDissertationDirectionByPrimaryKey", query = "select myChoseDissertationDirection from ChoseDissertationDirection myChoseDissertationDirection where myChoseDissertationDirection.id = ?1") })
@Table(name = "chose_dissertation_direction")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ChoseDissertationDirection")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class ChoseDissertationDirection implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
    Integer id;
	/**
	 * �о�����
	 * 
	 */

	@Column(name = "name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String name;

	/**
	 */
	/*@OneToMany(mappedBy = "choseDissertationDirection", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ChoseDissertation> choseDissertations;*/

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
	 * �о�����
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * �о�����
	 * 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 */
	/*public void setChoseDissertations(Set<ChoseDissertation> choseDissertations) {
		this.choseDissertations = choseDissertations;
	}

	*//**
	 *//*
	@JsonIgnore
	public Set<ChoseDissertation> getChoseDissertations() {
		if (choseDissertations == null) {
			choseDissertations = new java.util.LinkedHashSet<net.zjcclims.domain.ChoseDissertation>();
		}
		return choseDissertations;
	}*/

	/**
	 */
	public ChoseDissertationDirection() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ChoseDissertationDirection that) {
		setId(that.getId());
		setName(that.getName());
		//setChoseDissertations(new java.util.LinkedHashSet<net.zjcclims.domain.ChoseDissertation>(that.getChoseDissertations()));
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
		if (!(obj instanceof ChoseDissertationDirection))
			return false;
		ChoseDissertationDirection equalCheck = (ChoseDissertationDirection) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
