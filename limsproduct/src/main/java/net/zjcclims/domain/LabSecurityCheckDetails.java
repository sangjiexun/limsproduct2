package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabSecurityCheckDetailss", query = "select myLabSecurityCheckDetails from LabSecurityCheckDetails myLabSecurityCheckDetails"),
		@NamedQuery(name = "findLabSecurityCheckDetailsById", query = "select myLabSecurityCheckDetails from LabSecurityCheckDetails myLabSecurityCheckDetails where myLabSecurityCheckDetails.id = ?1"),
		@NamedQuery(name = "findLabSecurityCheckDetailsByPrimaryKey", query = "select myLabSecurityCheckDetails from LabSecurityCheckDetails myLabSecurityCheckDetails where myLabSecurityCheckDetails.id = ?1"),
		@NamedQuery(name = "findLabSecurityCheckDetailsByResult", query = "select myLabSecurityCheckDetails from LabSecurityCheckDetails myLabSecurityCheckDetails where myLabSecurityCheckDetails.result = ?1"),
		@NamedQuery(name = "findLabSecurityCheckDetailsByResultContaining", query = "select myLabSecurityCheckDetails from LabSecurityCheckDetails myLabSecurityCheckDetails where myLabSecurityCheckDetails.result like ?1") })
@Table(name = "lab_security_check_details")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabSecurityCheckDetails")
public class LabSecurityCheckDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵ���Ұ�ȫ��������
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
    Integer id;
	/**
	 * ��ϣ�1��������ϣ�2���������ã�3��
	 * 
	 */

	@Column(name = "result", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String result;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "security_check_id", referencedColumnName = "id") })
	@XmlTransient
	LabSecurityCheck labSecurityCheck;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "s_dictionary_id", referencedColumnName = "id") })
	@XmlTransient
	SDictionary SDictionary;

	/**
	 * ʵ���Ұ�ȫ��������
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵ���Ұ�ȫ��������
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ��ϣ�1��������ϣ�2���������ã�3��
	 * 
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * ��ϣ�1��������ϣ�2���������ã�3��
	 * 
	 */
	public String getResult() {
		return this.result;
	}

	/**
	 */
	public void setLabSecurityCheck(LabSecurityCheck labSecurityCheck) {
		this.labSecurityCheck = labSecurityCheck;
	}

	/**
	 */
	@JsonIgnore
	public LabSecurityCheck getLabSecurityCheck() {
		return labSecurityCheck;
	}

	/**
	 */
	public void setSDictionary(SDictionary SDictionary) {
		this.SDictionary = SDictionary;
	}

	/**
	 */
	@JsonIgnore
	public SDictionary getSDictionary() {
		return SDictionary;
	}

	/**
	 */
	public LabSecurityCheckDetails() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabSecurityCheckDetails that) {
		setId(that.getId());
		setResult(that.getResult());
		setLabSecurityCheck(that.getLabSecurityCheck());
		setSDictionary(that.getSDictionary());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("result=[").append(result).append("] ");

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
		if (!(obj instanceof LabSecurityCheckDetails))
			return false;
		LabSecurityCheckDetails equalCheck = (LabSecurityCheckDetails) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
