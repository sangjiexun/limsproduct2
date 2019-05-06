package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllEvaluationContents", query = "select myEvaluationContent from EvaluationContent myEvaluationContent"),
		@NamedQuery(name = "findEvaluationContentById", query = "select myEvaluationContent from EvaluationContent myEvaluationContent where myEvaluationContent.id = ?1"),
		@NamedQuery(name = "findEvaluationContentByMemo", query = "select myEvaluationContent from EvaluationContent myEvaluationContent where myEvaluationContent.memo = ?1"),
		@NamedQuery(name = "findEvaluationContentByMemoContaining", query = "select myEvaluationContent from EvaluationContent myEvaluationContent where myEvaluationContent.memo like ?1"),
		@NamedQuery(name = "findEvaluationContentByOptions", query = "select myEvaluationContent from EvaluationContent myEvaluationContent where myEvaluationContent.options = ?1"),
		@NamedQuery(name = "findEvaluationContentByOptionsContaining", query = "select myEvaluationContent from EvaluationContent myEvaluationContent where myEvaluationContent.options like ?1"),
		@NamedQuery(name = "findEvaluationContentByPrimaryKey", query = "select myEvaluationContent from EvaluationContent myEvaluationContent where myEvaluationContent.id = ?1"),
		@NamedQuery(name = "findEvaluationContentByScores", query = "select myEvaluationContent from EvaluationContent myEvaluationContent where myEvaluationContent.scores = ?1"),
		@NamedQuery(name = "findEvaluationContentByStatus", query = "select myEvaluationContent from EvaluationContent myEvaluationContent where myEvaluationContent.status = ?1") })
@Table(name = "evaluation_content")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "EvaluationContent")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class EvaluationContent implements Serializable {
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
	 * ѡ������
	 * 
	 */

	@Column(name = "options")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String options;
	/**
	 * �����ֵ
	 * 
	 */

	@Column(name = "scores")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer scores;
	/**
	 * ״̬��0δ���� 1�ѷ�����
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
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "term_id", referencedColumnName = "id") })
	@XmlTransient
	SchoolTerm schoolTerm;
	/**
	 */
	@OneToMany(mappedBy = "evaluationContent", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<net.zjcclims.domain.EvaluationResult> evaluationResults;

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
	 * ѡ������
	 * 
	 */
	public void setOptions(String options) {
		this.options = options;
	}

	/**
	 * ѡ������
	 * 
	 */
	public String getOptions() {
		return this.options;
	}

	/**
	 * �����ֵ
	 * 
	 */
	public void setScores(Integer scores) {
		this.scores = scores;
	}

	/**
	 * �����ֵ
	 * 
	 */
	public Integer getScores() {
		return this.scores;
	}

	/**
	 * ״̬��0δ���� 1�ѷ�����
	 * 
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * ״̬��0δ���� 1�ѷ�����
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
	public void setSchoolTerm(SchoolTerm schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	/**
	 */
	@JsonIgnore
	public SchoolTerm getSchoolTerm() {
		return schoolTerm;
	}

	/**
	 */
	public void setEvaluationResults(Set<EvaluationResult> evaluationResults) {
		this.evaluationResults = evaluationResults;
	}

	/**
	 */
	@JsonIgnore
	public Set<EvaluationResult> getEvaluationResults() {
		if (evaluationResults == null) {
			evaluationResults = new java.util.LinkedHashSet<net.zjcclims.domain.EvaluationResult>();
		}
		return evaluationResults;
	}

	/**
	 */
	public EvaluationContent() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(EvaluationContent that) {
		setId(that.getId());
		setOptions(that.getOptions());
		setScores(that.getScores());
		setStatus(that.getStatus());
		setMemo(that.getMemo());
		setSchoolTerm(that.getSchoolTerm());
		setEvaluationResults(new java.util.LinkedHashSet<net.zjcclims.domain.EvaluationResult>(that.getEvaluationResults()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("options=[").append(options).append("] ");
		buffer.append("scores=[").append(scores).append("] ");
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
		if (!(obj instanceof EvaluationContent))
			return false;
		EvaluationContent equalCheck = (EvaluationContent) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
