package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllChoseDissertationRecords", query = "select myChoseDissertationRecord from ChoseDissertationRecord myChoseDissertationRecord"),
		@NamedQuery(name = "findChoseDissertationRecordByAduitResult", query = "select myChoseDissertationRecord from ChoseDissertationRecord myChoseDissertationRecord where myChoseDissertationRecord.aduitResult = ?1"),
		@NamedQuery(name = "findChoseDissertationRecordByCurrAduit", query = "select myChoseDissertationRecord from ChoseDissertationRecord myChoseDissertationRecord where myChoseDissertationRecord.currAduit = ?1"),
		@NamedQuery(name = "findChoseDissertationRecordById", query = "select myChoseDissertationRecord from ChoseDissertationRecord myChoseDissertationRecord where myChoseDissertationRecord.id = ?1"),
		@NamedQuery(name = "findChoseDissertationRecordByPrimaryKey", query = "select myChoseDissertationRecord from ChoseDissertationRecord myChoseDissertationRecord where myChoseDissertationRecord.id = ?1") })
@Table(name = "chose_dissertation_record")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ChoseDissertationRecord")
public class ChoseDissertationRecord implements Serializable {
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
	 * ��ʦ���״̬1ͬ��2��ͬ��
	 * 
	 */

	@Column(name = "aduit_result")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer aduitResult;
	
	@Column(name = "t_username")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String tUsername;
	/**
	 * �����״̬0δ���1�����2��˽���
	 * 
	 */

	@Column(name = "curr_aduit")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer currAduit;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "dissertation_id", referencedColumnName = "id") })
	@XmlTransient
	ChoseDissertation choseDissertation;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "student", referencedColumnName = "username") })
	@XmlTransient
	User user;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "batch_id", referencedColumnName = "id") })
	@XmlTransient
	ChoseProfessorBatch choseProfessorBatch;

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
	public void setTUsername(String tUsername) {
		this.tUsername = tUsername;
	}

	/**
	 */
	public String getTUsername() {
		return this.tUsername;
	}

	/**
	 * ��ʦ���״̬1ͬ��2��ͬ��
	 * 
	 */
	public void setAduitResult(Integer aduitResult) {
		this.aduitResult = aduitResult;
	}

	/**
	 * ��ʦ���״̬1ͬ��2��ͬ��
	 * 
	 */
	public Integer getAduitResult() {
		return this.aduitResult;
	}

	/**
	 * �����״̬0δ���1�����2��˽���
	 * 
	 */
	public void setCurrAduit(Integer currAduit) {
		this.currAduit = currAduit;
	}

	/**
	 * �����״̬0δ���1�����2��˽���
	 * 
	 */
	public Integer getCurrAduit() {
		return this.currAduit;
	}

	/**
	 */
	public void setChoseDissertation(ChoseDissertation choseDissertation) {
		this.choseDissertation = choseDissertation;
	}

	/**
	 */
	@JsonIgnore
	public ChoseDissertation getChoseDissertation() {
		return choseDissertation;
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
	public void setChoseProfessorBatch(ChoseProfessorBatch choseProfessorBatch) {
		this.choseProfessorBatch = choseProfessorBatch;
	}

	/**
	 */
	@JsonIgnore
	public ChoseProfessorBatch getChoseProfessorBatch() {
		return choseProfessorBatch;
	}

	/**
	 */
	public ChoseDissertationRecord() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ChoseDissertationRecord that) {
		setId(that.getId());
		setAduitResult(that.getAduitResult());
		setCurrAduit(that.getCurrAduit());
		setChoseDissertation(that.getChoseDissertation());
		setUser(that.getUser());
		setChoseProfessorBatch(that.getChoseProfessorBatch());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("aduitResult=[").append(aduitResult).append("] ");
		buffer.append("currAduit=[").append(currAduit).append("] ");

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
		if (!(obj instanceof ChoseDissertationRecord))
			return false;
		ChoseDissertationRecord equalCheck = (ChoseDissertationRecord) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
