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
		@NamedQuery(name = "findAllSoftwareItemRelateds", query = "select mySoftwareItemRelated from SoftwareItemRelated mySoftwareItemRelated"),
		@NamedQuery(name = "findSoftwareItemRelatedById", query = "select mySoftwareItemRelated from SoftwareItemRelated mySoftwareItemRelated where mySoftwareItemRelated.id = ?1"),
		@NamedQuery(name = "findSoftwareItemRelatedByPrimaryKey", query = "select mySoftwareItemRelated from SoftwareItemRelated mySoftwareItemRelated where mySoftwareItemRelated.id = ?1") })
@Table(name = "software_item_related")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SoftwareItemRelated")
public class SoftwareItemRelated implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ��Ŀ-�������
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "software", referencedColumnName = "id") })
	@XmlTransient
	Software software;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "operation_item", referencedColumnName = "id") })
	@XmlTransient
	OperationItem operationItem;

	/**
	 * ��Ŀ-�������
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ��Ŀ-�������
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 */
	public void setSoftware(Software software) {
		this.software = software;
	}

	/**
	 */
	@JsonIgnore
	public Software getSoftware() {
		return software;
	}

	/**
	 */
	public void setOperationItem(OperationItem operationItem) {
		this.operationItem = operationItem;
	}

	/**
	 */
	@JsonIgnore
	public OperationItem getOperationItem() {
		return operationItem;
	}

	/**
	 */
	public SoftwareItemRelated() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SoftwareItemRelated that) {
		setId(that.getId());
		setSoftware(that.getSoftware());
		setOperationItem(that.getOperationItem());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");

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
		if (!(obj instanceof SoftwareItemRelated))
			return false;
		SoftwareItemRelated equalCheck = (SoftwareItemRelated) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
