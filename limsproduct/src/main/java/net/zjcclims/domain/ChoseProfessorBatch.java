package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllChoseProfessorBatchs", query = "select myChoseProfessorBatch from ChoseProfessorBatch myChoseProfessorBatch"),
		@NamedQuery(name = "findChoseProfessorBatchByBatchNumber", query = "select myChoseProfessorBatch from ChoseProfessorBatch myChoseProfessorBatch where myChoseProfessorBatch.batchNumber = ?1"),
		@NamedQuery(name = "findChoseProfessorBatchByEndTime", query = "select myChoseProfessorBatch from ChoseProfessorBatch myChoseProfessorBatch where myChoseProfessorBatch.endTime = ?1"),
		@NamedQuery(name = "findChoseProfessorBatchByEndTimeAfter", query = "select myChoseProfessorBatch from ChoseProfessorBatch myChoseProfessorBatch where myChoseProfessorBatch.endTime > ?1"),
		@NamedQuery(name = "findChoseProfessorBatchByEndTimeBefore", query = "select myChoseProfessorBatch from ChoseProfessorBatch myChoseProfessorBatch where myChoseProfessorBatch.endTime < ?1"),
		@NamedQuery(name = "findChoseProfessorBatchById", query = "select myChoseProfessorBatch from ChoseProfessorBatch myChoseProfessorBatch where myChoseProfessorBatch.id = ?1"),
		@NamedQuery(name = "findChoseProfessorBatchByPrimaryKey", query = "select myChoseProfessorBatch from ChoseProfessorBatch myChoseProfessorBatch where myChoseProfessorBatch.id = ?1"),
		@NamedQuery(name = "findChoseProfessorBatchByStartTime", query = "select myChoseProfessorBatch from ChoseProfessorBatch myChoseProfessorBatch where myChoseProfessorBatch.startTime = ?1"),
		@NamedQuery(name = "findChoseProfessorBatchByStartTimeAfter", query = "select myChoseProfessorBatch from ChoseProfessorBatch myChoseProfessorBatch where myChoseProfessorBatch.startTime > ?1"),
		@NamedQuery(name = "findChoseProfessorBatchByStartTimeBefore", query = "select myChoseProfessorBatch from ChoseProfessorBatch myChoseProfessorBatch where myChoseProfessorBatch.startTime < ?1") })
@Table(name = "chose_professor_batch")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ChoseProfessorBatch")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class ChoseProfessorBatch implements Serializable {
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
	 * �������
	 * 
	 */

	@Column(name = "batch_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer batchNumber;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "chose_record_id", referencedColumnName = "id") })
	@XmlTransient
	ChoseTheme choseTheme;
	/**
	 */
	@OneToMany(mappedBy = "choseProfessorBatch", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ChoseProfessorRecord> choseProfessorRecords;
	/**
	 */
	@OneToMany(mappedBy = "choseProfessorBatch", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ChoseDissertationRecord> choseDissertationRecords;
	/**
	 */
	public void setChoseDissertationRecords(Set<ChoseDissertationRecord> choseDissertationRecords) {
		this.choseDissertationRecords = choseDissertationRecords;
	}

	/**
	 */
	@JsonIgnore
	public Set<ChoseDissertationRecord> getChoseDissertationRecords() {
		if (choseDissertationRecords == null) {
			choseDissertationRecords = new java.util.LinkedHashSet<net.zjcclims.domain.ChoseDissertationRecord>();
		}
		return choseDissertationRecords;
	}
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
	 * �������
	 *
	 */
	public void setBatchNumber(Integer batchNumber) {
		this.batchNumber = batchNumber;
	}

	/**
	 * �������
	 *
	 */
	public Integer getBatchNumber() {
		return this.batchNumber;
	}

	/**
	 */
	public void setChoseTheme(ChoseTheme choseTheme) {
		this.choseTheme = choseTheme;
	}

	/**
	 */
	@JsonIgnore
	public ChoseTheme getChoseTheme() {
		return choseTheme;
	}

	/**
	 */
	public void setChoseProfessorRecords(Set<ChoseProfessorRecord> choseProfessorRecords) {
		this.choseProfessorRecords = choseProfessorRecords;
	}

	/**
	 */
	@JsonIgnore
	public Set<ChoseProfessorRecord> getChoseProfessorRecords() {
		if (choseProfessorRecords == null) {
			choseProfessorRecords = new java.util.LinkedHashSet<net.zjcclims.domain.ChoseProfessorRecord>();
		}
		return choseProfessorRecords;
	}

	/**
	 */
	public ChoseProfessorBatch() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ChoseProfessorBatch that) {
		setId(that.getId());
		setStartTime(that.getStartTime());
		setEndTime(that.getEndTime());
		setBatchNumber(that.getBatchNumber());
		setChoseTheme(that.getChoseTheme());
		setChoseProfessorRecords(new java.util.LinkedHashSet<net.zjcclims.domain.ChoseProfessorRecord>(that.getChoseProfessorRecords()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("startTime=[").append(startTime).append("] ");
		buffer.append("endTime=[").append(endTime).append("] ");
		buffer.append("batchNumber=[").append(batchNumber).append("] ");

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
		if (!(obj instanceof ChoseProfessorBatch))
			return false;
		ChoseProfessorBatch equalCheck = (ChoseProfessorBatch) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
