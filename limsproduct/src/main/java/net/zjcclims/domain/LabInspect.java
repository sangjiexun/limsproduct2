package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabInspects", query = "select myLabInspect from LabInspect myLabInspect"),
		@NamedQuery(name = "findLabInspectById", query = "select myLabInspect from LabInspect myLabInspect where myLabInspect.id = ?1"),
		@NamedQuery(name = "findLabInspectByPrimaryKey", query = "select myLabInspect from LabInspect myLabInspect where myLabInspect.id = ?1"),
		@NamedQuery(name = "findLabInspectByStandardName", query = "select myLabInspect from LabInspect myLabInspect where myLabInspect.standardName = ?1"),
		@NamedQuery(name = "findLabInspectByStandardNameContaining", query = "select myLabInspect from LabInspect myLabInspect where myLabInspect.standardName like ?1") })
@Table(name = "lab_inspect")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabInspect")
public class LabInspect implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ��׼���
	 * 
	 */

	@Column(name = "standard_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String standardName;

	/**
	 */
	@OneToMany(mappedBy = "labInspect", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<net.zjcclims.domain.MInspectSetting> mInspectSettings;

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
	 * ��׼���
	 * 
	 */
	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	/**
	 * ��׼���
	 * 
	 */
	public String getStandardName() {
		return this.standardName;
	}

	/**
	 */
	public void setMInspectSettings(Set<MInspectSetting> mInspectSettings) {
		this.mInspectSettings = mInspectSettings;
	}

	/**
	 */
	@JsonIgnore
	public Set<MInspectSetting> getMInspectSettings() {
		if (mInspectSettings == null) {
			mInspectSettings = new java.util.LinkedHashSet<net.zjcclims.domain.MInspectSetting>();
		}
		return mInspectSettings;
	}

	/**
	 */
	public LabInspect() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabInspect that) {
		setId(that.getId());
		setStandardName(that.getStandardName());
		setMInspectSettings(new java.util.LinkedHashSet<net.zjcclims.domain.MInspectSetting>(that.getMInspectSettings()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("standardName=[").append(standardName).append("] ");

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
		if (!(obj instanceof LabInspect))
			return false;
		LabInspect equalCheck = (LabInspect) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
