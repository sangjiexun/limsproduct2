package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.LinkedHashSet;
import java.util.Set;

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
		@NamedQuery(name = "findAllPreCapacityRanges", query = "select myPreCapacityRange from PreCapacityRange myPreCapacityRange"),
		@NamedQuery(name = "findPreCapacityRangeByCapaRange", query = "select myPreCapacityRange from PreCapacityRange myPreCapacityRange where myPreCapacityRange.capaRange = ?1"),
		@NamedQuery(name = "findPreCapacityRangeByCapaRangeContaining", query = "select myPreCapacityRange from PreCapacityRange myPreCapacityRange where myPreCapacityRange.capaRange like ?1"),
		@NamedQuery(name = "findPreCapacityRangeByCapaType", query = "select myPreCapacityRange from PreCapacityRange myPreCapacityRange where myPreCapacityRange.capaType = ?1"),
		@NamedQuery(name = "findPreCapacityRangeByCapaTypeContaining", query = "select myPreCapacityRange from PreCapacityRange myPreCapacityRange where myPreCapacityRange.capaType like ?1"),
		@NamedQuery(name = "findPreCapacityRangeById", query = "select myPreCapacityRange from PreCapacityRange myPreCapacityRange where myPreCapacityRange.id = ?1"),
		@NamedQuery(name = "findPreCapacityRangeByPrimaryKey", query = "select myPreCapacityRange from PreCapacityRange myPreCapacityRange where myPreCapacityRange.id = ?1") })
@Table(name = "pre_capacity_range")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "PreCapacityRange")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class PreCapacityRange implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ???(??????Χ)
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ????????
	 * 
	 */

	@Column(name = "capa_type", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String capaType;
	/**
	 * ??????Χ
	 * 
	 */

	@Column(name = "capa_range", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String capaRange;

	/**
	 */
	@OneToMany(mappedBy = "preCapacityRange", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<PreLabRoom> preLabRooms;

	/**
	 * ???(??????Χ)
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ???(??????Χ)
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ????????
	 * 
	 */
	public void setCapaType(String capaType) {
		this.capaType = capaType;
	}

	/**
	 * ????????
	 * 
	 */
	public String getCapaType() {
		return this.capaType;
	}

	/**
	 * ??????Χ
	 * 
	 */
	public void setCapaRange(String capaRange) {
		this.capaRange = capaRange;
	}

	/**
	 * ??????Χ
	 * 
	 */
	public String getCapaRange() {
		return this.capaRange;
	}

	/**
	 */
	public void setPreLabRooms(Set<PreLabRoom> preLabRooms) {
		this.preLabRooms = preLabRooms;
	}

	/**
	 */
	@JsonIgnore
	public Set<PreLabRoom> getPreLabRooms() {
		if (preLabRooms == null) {
			preLabRooms = new LinkedHashSet<PreLabRoom>();
		}
		return preLabRooms;
	}

	/**
	 */
	public PreCapacityRange() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(PreCapacityRange that) {
		setId(that.getId());
		setCapaType(that.getCapaType());
		setCapaRange(that.getCapaRange());
		setPreLabRooms(new LinkedHashSet<PreLabRoom>(that.getPreLabRooms()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("capaType=[").append(capaType).append("] ");
		buffer.append("capaRange=[").append(capaRange).append("] ");

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
		if (!(obj instanceof PreCapacityRange))
			return false;
		PreCapacityRange equalCheck = (PreCapacityRange) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
