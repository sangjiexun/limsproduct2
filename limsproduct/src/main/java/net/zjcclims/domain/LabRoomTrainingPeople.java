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
		@NamedQuery(name = "findAllLabRoomTrainingPeoples", query = "select myLabRoomTrainingPeople from LabRoomTrainingPeople myLabRoomTrainingPeople"),
		@NamedQuery(name = "findLabRoomTrainingPeopleByEMail", query = "select myLabRoomTrainingPeople from LabRoomTrainingPeople myLabRoomTrainingPeople where myLabRoomTrainingPeople.EMail = ?1"),
		@NamedQuery(name = "findLabRoomTrainingPeopleByEMailContaining", query = "select myLabRoomTrainingPeople from LabRoomTrainingPeople myLabRoomTrainingPeople where myLabRoomTrainingPeople.EMail like ?1"),
		@NamedQuery(name = "findLabRoomTrainingPeopleById", query = "select myLabRoomTrainingPeople from LabRoomTrainingPeople myLabRoomTrainingPeople where myLabRoomTrainingPeople.id = ?1"),
		@NamedQuery(name = "findLabRoomTrainingPeopleByMessageFlag", query = "select myLabRoomTrainingPeople from LabRoomTrainingPeople myLabRoomTrainingPeople where myLabRoomTrainingPeople.messageFlag = ?1"),
		@NamedQuery(name = "findLabRoomTrainingPeopleByPrimaryKey", query = "select myLabRoomTrainingPeople from LabRoomTrainingPeople myLabRoomTrainingPeople where myLabRoomTrainingPeople.id = ?1"),
		@NamedQuery(name = "findLabRoomTrainingPeopleByTelephone", query = "select myLabRoomTrainingPeople from LabRoomTrainingPeople myLabRoomTrainingPeople where myLabRoomTrainingPeople.telephone = ?1"),
		@NamedQuery(name = "findLabRoomTrainingPeopleByTelephoneContaining", query = "select myLabRoomTrainingPeople from LabRoomTrainingPeople myLabRoomTrainingPeople where myLabRoomTrainingPeople.telephone like ?1") })
@Table(name = "lab_room_training_people")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabRoomTrainingPeople")
public class LabRoomTrainingPeople implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵѵ��ԤԼ��ѵ���
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ��ϵ�绰
	 * 
	 */

	@Column(name = "telephone", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String telephone;
	/**
	 * �ʼ�
	 * 
	 */

	@Column(name = "e_mail", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String EMail;
	/**
	 * ��ѵ�б仯ʱѧ���յ���֪ͨ��Ϣ���ݱ�־��0 ��  1 ʱ��仯 2 ��ѵ��ȡ��
	 * 
	 */

	@Column(name = "message_flag")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer messageFlag;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "student", referencedColumnName = "username") })
	@XmlTransient
	User user;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "result", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionary;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "train", referencedColumnName = "id") })
	@XmlTransient
	LabRoomTraining labRoomTraining;

	/**
	 * ʵѵ��ԤԼ��ѵ���
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵѵ��ԤԼ��ѵ���
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ��ϵ�绰
	 * 
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * ��ϵ�绰
	 * 
	 */
	public String getTelephone() {
		return this.telephone;
	}

	/**
	 * �ʼ�
	 * 
	 */
	public void setEMail(String EMail) {
		this.EMail = EMail;
	}

	/**
	 * �ʼ�
	 * 
	 */
	public String getEMail() {
		return this.EMail;
	}

	/**
	 * ��ѵ�б仯ʱѧ���յ���֪ͨ��Ϣ���ݱ�־��0 ��  1 ʱ��仯 2 ��ѵ��ȡ��
	 * 
	 */
	public void setMessageFlag(Integer messageFlag) {
		this.messageFlag = messageFlag;
	}

	/**
	 * ��ѵ�б仯ʱѧ���յ���֪ͨ��Ϣ���ݱ�־��0 ��  1 ʱ��仯 2 ��ѵ��ȡ��
	 * 
	 */
	public Integer getMessageFlag() {
		return this.messageFlag;
	}

	/**
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 */
	@JsonIgnore
	public User getUser() {
		return user;
	}

	/**
	 */
	public void setCDictionary(CDictionary CDictionary) {
		this.CDictionary = CDictionary;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionary() {
		return CDictionary;
	}

	/**
	 */
	public void setLabRoomTraining(LabRoomTraining labRoomTraining) {
		this.labRoomTraining = labRoomTraining;
	}

	/**
	 */
	@JsonIgnore
	public LabRoomTraining getLabRoomTraining() {
		return labRoomTraining;
	}

	/**
	 */
	public LabRoomTrainingPeople() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabRoomTrainingPeople that) {
		setId(that.getId());
		setTelephone(that.getTelephone());
		setEMail(that.getEMail());
		setMessageFlag(that.getMessageFlag());
		setUser(that.getUser());
		setCDictionary(that.getCDictionary());
		setLabRoomTraining(that.getLabRoomTraining());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("telephone=[").append(telephone).append("] ");
		buffer.append("EMail=[").append(EMail).append("] ");
		buffer.append("messageFlag=[").append(messageFlag).append("] ");

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
		if (!(obj instanceof LabRoomTrainingPeople))
			return false;
		LabRoomTrainingPeople equalCheck = (LabRoomTrainingPeople) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
