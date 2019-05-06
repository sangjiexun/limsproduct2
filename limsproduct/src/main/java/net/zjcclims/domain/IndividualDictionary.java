package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Calendar;

import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllIndividualDictionarys", query = "select myIndividualDictionary from IndividualDictionary myIndividualDictionary"),
		@NamedQuery(name = "findIndividualDictionaryByAuditStatus", query = "select myIndividualDictionary from IndividualDictionary myIndividualDictionary where myIndividualDictionary.auditStatus = ?1"),
		@NamedQuery(name = "findIndividualDictionaryByCreateDate", query = "select myIndividualDictionary from IndividualDictionary myIndividualDictionary where myIndividualDictionary.createDate = ?1"),
		@NamedQuery(name = "findIndividualDictionaryByCreater", query = "select myIndividualDictionary from IndividualDictionary myIndividualDictionary where myIndividualDictionary.creater = ?1"),
		@NamedQuery(name = "findIndividualDictionaryByCreaterContaining", query = "select myIndividualDictionary from IndividualDictionary myIndividualDictionary where myIndividualDictionary.creater like ?1"),
		@NamedQuery(name = "findIndividualDictionaryById", query = "select myIndividualDictionary from IndividualDictionary myIndividualDictionary where myIndividualDictionary.id = ?1"),
		@NamedQuery(name = "findIndividualDictionaryByName", query = "select myIndividualDictionary from IndividualDictionary myIndividualDictionary where myIndividualDictionary.name = ?1"),
		@NamedQuery(name = "findIndividualDictionaryByNameContaining", query = "select myIndividualDictionary from IndividualDictionary myIndividualDictionary where myIndividualDictionary.name like ?1"),
		@NamedQuery(name = "findIndividualDictionaryByPrimaryKey", query = "select myIndividualDictionary from IndividualDictionary myIndividualDictionary where myIndividualDictionary.id = ?1"),
		@NamedQuery(name = "findIndividualDictionaryByType", query = "select myIndividualDictionary from IndividualDictionary myIndividualDictionary where myIndividualDictionary.type = ?1") })
@Table(name = "individual_dictionary")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "IndividualDictionary")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class IndividualDictionary implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ��������
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ģ�飨1�佱���Ż�λ��2�������ͣ�3�������ͣ�4�񽱵ȼ���5�񽱼���6������ѧ��λ��7�Ƽ���ר������8�������
	 * 
	 */

	@Column(name = "type", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer type;
	/**
	 * ����
	 * 
	 */

	@Column(name = "name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String name;
	/**
	 * ������
	 * 
	 */

	@Column(name = "creater", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String creater;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createDate;
	/**
	 * ״̬��0�༭��1����ˣ�2���ͨ����3��˾ܾ���
	 * 
	 */

	@Column(name = "audit_status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer auditStatus;

	/**
	 * ��������
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ��������
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ģ�飨1�佱���Ż�λ��2�������ͣ�3�������ͣ�4�񽱵ȼ���5�񽱼���6������ѧ��λ��7�Ƽ���ר������8�������
	 * 
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * ģ�飨1�佱���Ż�λ��2�������ͣ�3�������ͣ�4�񽱵ȼ���5�񽱼���6������ѧ��λ��7�Ƽ���ר������8�������
	 * 
	 */
	public Integer getType() {
		return this.type;
	}

	/**
	 * ����
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ����
	 * 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * ������
	 * 
	 */
	public void setCreater(String creater) {
		this.creater = creater;
	}

	/**
	 * ������
	 * 
	 */
	public String getCreater() {
		return this.creater;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public Calendar getCreateDate() {
		return this.createDate;
	}

	/**
	 * ״̬��0�༭��1����ˣ�2���ͨ����3��˾ܾ���
	 * 
	 */
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * ״̬��0�༭��1����ˣ�2���ͨ����3��˾ܾ���
	 * 
	 */
	public Integer getAuditStatus() {
		return this.auditStatus;
	}

	/**
	 */
	public IndividualDictionary() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(IndividualDictionary that) {
		setId(that.getId());
		setType(that.getType());
		setName(that.getName());
		setCreater(that.getCreater());
		setCreateDate(that.getCreateDate());
		setAuditStatus(that.getAuditStatus());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("type=[").append(type).append("] ");
		buffer.append("name=[").append(name).append("] ");
		buffer.append("creater=[").append(creater).append("] ");
		buffer.append("createDate=[").append(createDate).append("] ");
		buffer.append("auditStatus=[").append(auditStatus).append("] ");

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
		if (!(obj instanceof IndividualDictionary))
			return false;
		IndividualDictionary equalCheck = (IndividualDictionary) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
