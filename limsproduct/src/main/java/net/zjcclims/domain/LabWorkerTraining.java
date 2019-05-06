package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabWorkerTrainings", query = "select myLabWorkerTraining from LabWorkerTraining myLabWorkerTraining"),
		@NamedQuery(name = "findLabWorkerTrainingByAnnex", query = "select myLabWorkerTraining from LabWorkerTraining myLabWorkerTraining where myLabWorkerTraining.annex = ?1"),
		@NamedQuery(name = "findLabWorkerTrainingByAnnexContaining", query = "select myLabWorkerTraining from LabWorkerTraining myLabWorkerTraining where myLabWorkerTraining.annex like ?1"),
		@NamedQuery(name = "findLabWorkerTrainingByBeginTime", query = "select myLabWorkerTraining from LabWorkerTraining myLabWorkerTraining where myLabWorkerTraining.beginTime = ?1"),
		@NamedQuery(name = "findLabWorkerTrainingByEndTime", query = "select myLabWorkerTraining from LabWorkerTraining myLabWorkerTraining where myLabWorkerTraining.endTime = ?1"),
		@NamedQuery(name = "findLabWorkerTrainingById", query = "select myLabWorkerTraining from LabWorkerTraining myLabWorkerTraining where myLabWorkerTraining.id = ?1"),
		@NamedQuery(name = "findLabWorkerTrainingByLearnContent", query = "select myLabWorkerTraining from LabWorkerTraining myLabWorkerTraining where myLabWorkerTraining.learnContent = ?1"),
		@NamedQuery(name = "findLabWorkerTrainingByLearnContentContaining", query = "select myLabWorkerTraining from LabWorkerTraining myLabWorkerTraining where myLabWorkerTraining.learnContent like ?1"),
		@NamedQuery(name = "findLabWorkerTrainingByOrganizer", query = "select myLabWorkerTraining from LabWorkerTraining myLabWorkerTraining where myLabWorkerTraining.organizer = ?1"),
		@NamedQuery(name = "findLabWorkerTrainingByOrganizerContaining", query = "select myLabWorkerTraining from LabWorkerTraining myLabWorkerTraining where myLabWorkerTraining.organizer like ?1"),
		@NamedQuery(name = "findLabWorkerTrainingByPrimaryKey", query = "select myLabWorkerTraining from LabWorkerTraining myLabWorkerTraining where myLabWorkerTraining.id = ?1"),
		@NamedQuery(name = "findLabWorkerTrainingByScore", query = "select myLabWorkerTraining from LabWorkerTraining myLabWorkerTraining where myLabWorkerTraining.score = ?1") })
@Table(name = "lab_worker_training")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabWorkerTraining")
public class LabWorkerTraining implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵѵ�Ҷ�����ѵ���޵Ǽ�
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ��ʼʱ��
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "begin_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar beginTime;
	
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
	 * �ɼ�
	 * 
	 */

	@Column(name = "score", scale = 2, precision = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal score;
	/**
	 * ���쵥λ
	 * 
	 */

	@Column(name = "organizer", length = 100)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String organizer;
	/**
	 * ѧϰ����
	 * 
	 */

	@Column(name = "learn_content")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String learnContent;
	/**
	 * �ϴ�����
	 * 
	 */

	@Column(name = "annex")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String annex;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "training_type", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionary;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "username", referencedColumnName = "username") })
	@XmlTransient
	User user;
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_worker", referencedColumnName = "id") })
	@XmlTransient
	LabWorker labWorker;

	
	/**
	 */
	@OneToMany(mappedBy = "labWorkerTraining", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.CommonDocument> commonDocuments;
	
	/**
	 */
	public void setCommonDocuments(Set<CommonDocument> commonDocuments) {
		this.commonDocuments = commonDocuments;
	}

	/**
	 */
	@JsonIgnore
	public Set<CommonDocument> getCommonDocuments() {
		if (commonDocuments == null) {
			commonDocuments = new java.util.LinkedHashSet<net.zjcclims.domain.CommonDocument>();
		}
		return commonDocuments;
	}
	/**
	 * ʵѵ�Ҷ�����ѵ���޵Ǽ�
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵѵ�Ҷ�����ѵ���޵Ǽ�
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ��ʼʱ��
	 * 
	 */
	public void setBeginTime(Calendar beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * ��ʼʱ��
	 * 
	 */
	public Calendar getBeginTime() {
		return this.beginTime;
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
	 * �ɼ�
	 * 
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}

	/**
	 * �ɼ�
	 * 
	 */
	public BigDecimal getScore() {
		return this.score;
	}

	/**
	 * ���쵥λ
	 * 
	 */
	public void setOrganizer(String organizer) {
		this.organizer = organizer;
	}

	/**
	 * ���쵥λ
	 * 
	 */
	public String getOrganizer() {
		return this.organizer;
	}

	/**
	 * ѧϰ����
	 * 
	 */
	public void setLearnContent(String learnContent) {
		this.learnContent = learnContent;
	}

	/**
	 * ѧϰ����
	 * 
	 */
	public String getLearnContent() {
		return this.learnContent;
	}

	/**
	 * �ϴ�����
	 * 
	 */
	public void setAnnex(String annex) {
		this.annex = annex;
	}

	/**
	 * �ϴ�����
	 * 
	 */
	public String getAnnex() {
		return this.annex;
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
	public void setLabWorker(LabWorker labWorker) {
		this.labWorker = labWorker;
	}

	/**
	 */
	@JsonIgnore
	public LabWorker getLabWorker() {
		return labWorker;
	}

	/**
	 */
	public LabWorkerTraining() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabWorkerTraining that) {
		setId(that.getId());
		setBeginTime(that.getBeginTime());
		setEndTime(that.getEndTime());
		setScore(that.getScore());
		setOrganizer(that.getOrganizer());
		setLearnContent(that.getLearnContent());
		setAnnex(that.getAnnex());
		setCDictionary(that.getCDictionary());
		setUser(that.getUser());
		setLabWorker(that.getLabWorker());
		setCommonDocuments(new java.util.LinkedHashSet<net.zjcclims.domain.CommonDocument>(that.getCommonDocuments()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("beginTime=[").append(beginTime).append("] ");
		buffer.append("endTime=[").append(endTime).append("] ");
		buffer.append("score=[").append(score).append("] ");
		buffer.append("organizer=[").append(organizer).append("] ");
		buffer.append("learnContent=[").append(learnContent).append("] ");
		buffer.append("annex=[").append(annex).append("] ");

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
		if (!(obj instanceof LabWorkerTraining))
			return false;
		LabWorkerTraining equalCheck = (LabWorkerTraining) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
