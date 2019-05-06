package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllChoseProfessorRecords", query = "select myChoseProfessorRecord from ChoseProfessorRecord myChoseProfessorRecord"),
		@NamedQuery(name = "findChoseProfessorRecordByAduitResult", query = "select myChoseProfessorRecord from ChoseProfessorRecord myChoseProfessorRecord where myChoseProfessorRecord.aduitResult = ?1"),
		@NamedQuery(name = "findChoseProfessorRecordByCurrAduit", query = "select myChoseProfessorRecord from ChoseProfessorRecord myChoseProfessorRecord where myChoseProfessorRecord.currAduit = ?1"),
		@NamedQuery(name = "findChoseProfessorRecordById", query = "select myChoseProfessorRecord from ChoseProfessorRecord myChoseProfessorRecord where myChoseProfessorRecord.id = ?1"),
		@NamedQuery(name = "findChoseProfessorRecordByPrimaryKey", query = "select myChoseProfessorRecord from ChoseProfessorRecord myChoseProfessorRecord where myChoseProfessorRecord.id = ?1") })
@Table(name = "chose_professor_record")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ChoseProfessorRecord")
public class ChoseProfessorRecord implements Serializable {
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
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "professor_id", referencedColumnName = "id") })
	@XmlTransient
	ChoseProfessor choseProfessor;

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
	public void setTUsername(String tUsername) {
		this.tUsername = tUsername;
	}

	/**
	 * ��ʦ���״̬1ͬ��2��ͬ��
	 * 
	 */
	public String getTUsername() {
		return this.tUsername;
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
	public void setChoseProfessor(ChoseProfessor choseProfessor) {
		this.choseProfessor = choseProfessor;
	}

	/**
	 */
	@JsonIgnore
	public ChoseProfessor getChoseProfessor() {
		return choseProfessor;
	}

	/**
	 */
	public ChoseProfessorRecord() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ChoseProfessorRecord that) {
		setId(that.getId());
		setAduitResult(that.getAduitResult());
		setCurrAduit(that.getCurrAduit());
		setUser(that.getUser());
		setChoseProfessorBatch(that.getChoseProfessorBatch());
		setChoseProfessor(that.getChoseProfessor());
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
		if (!(obj instanceof ChoseProfessorRecord))
			return false;
		ChoseProfessorRecord equalCheck = (ChoseProfessorRecord) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
