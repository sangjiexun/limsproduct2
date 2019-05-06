package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Calendar;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllEvaluationSets", query = "select myEvaluationSet from EvaluationSet myEvaluationSet"),
		@NamedQuery(name = "findEvaluationSetByEndTime", query = "select myEvaluationSet from EvaluationSet myEvaluationSet where myEvaluationSet.endTime = ?1"),
		@NamedQuery(name = "findEvaluationSetById", query = "select myEvaluationSet from EvaluationSet myEvaluationSet where myEvaluationSet.id = ?1"),
		@NamedQuery(name = "findEvaluationSetByMemo", query = "select myEvaluationSet from EvaluationSet myEvaluationSet where myEvaluationSet.memo = ?1"),
		@NamedQuery(name = "findEvaluationSetByMemoContaining", query = "select myEvaluationSet from EvaluationSet myEvaluationSet where myEvaluationSet.memo like ?1"),
		@NamedQuery(name = "findEvaluationSetByPrimaryKey", query = "select myEvaluationSet from EvaluationSet myEvaluationSet where myEvaluationSet.id = ?1"),
		@NamedQuery(name = "findEvaluationSetByStartTime", query = "select myEvaluationSet from EvaluationSet myEvaluationSet where myEvaluationSet.startTime = ?1"),
		@NamedQuery(name = "findEvaluationSetByStatus", query = "select myEvaluationSet from EvaluationSet myEvaluationSet where myEvaluationSet.status = ?1"),
		@NamedQuery(name = "findEvaluationSetByTermId", query = "select myEvaluationSet from EvaluationSet myEvaluationSet where myEvaluationSet.termId = ?1") })
@Table(name = "evaluation_set")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "EvaluationSet")
public class EvaluationSet implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ����
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ѧ��id
	 * 
	 */

	@Column(name = "term_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer termId;
	/**
	 * ��ʼʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar startTime;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar endTime;
	/**
	 * ״̬��0δ�ύ��1���ύ��
	 * 
	 */

	@Column(name = "status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer status;
	/**
	 * ��ע
	 * 
	 */

	@Column(name = "memo")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String memo;

	/**
	 * ����
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ����
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ѧ��id
	 * 
	 */
	public void setTermId(Integer termId) {
		this.termId = termId;
	}

	/**
	 * ѧ��id
	 * 
	 */
	public Integer getTermId() {
		return this.termId;
	}

	/**
	 * ��ʼʱ��
	 * 
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	/**
	 * ��ʼʱ��
	 * 
	 */
	public Calendar getStartTime() {
		return this.startTime;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public Calendar getEndTime() {
		return this.endTime;
	}

	/**
	 * ״̬��0δ�ύ��1���ύ��
	 * 
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * ״̬��0δ�ύ��1���ύ��
	 * 
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * ��ע
	 * 
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * ��ע
	 * 
	 */
	public String getMemo() {
		return this.memo;
	}

	/**
	 */
	public EvaluationSet() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(EvaluationSet that) {
		setId(that.getId());
		setTermId(that.getTermId());
		setStartTime(that.getStartTime());
		setEndTime(that.getEndTime());
		setStatus(that.getStatus());
		setMemo(that.getMemo());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("termId=[").append(termId).append("] ");
		buffer.append("startTime=[").append(startTime).append("] ");
		buffer.append("endTime=[").append(endTime).append("] ");
		buffer.append("status=[").append(status).append("] ");
		buffer.append("memo=[").append(memo).append("] ");

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
		if (!(obj instanceof EvaluationSet))
			return false;
		EvaluationSet equalCheck = (EvaluationSet) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
