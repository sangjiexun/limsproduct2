
package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabConstructionGrandsonProjects", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject"),
		@NamedQuery(name = "findLabConstructionGrandsonProjectByAcceptanceaActualAmount", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject where myLabConstructionGrandsonProject.acceptanceaActualAmount = ?1"),
		@NamedQuery(name = "findLabConstructionGrandsonProjectByBudget", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject where myLabConstructionGrandsonProject.budget = ?1"),
		@NamedQuery(name = "findLabConstructionGrandsonProjectByCreateTime", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject where myLabConstructionGrandsonProject.createTime = ?1"),
		@NamedQuery(name = "findLabConstructionGrandsonProjectByCreateTimeAfter", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject where myLabConstructionGrandsonProject.createTime > ?1"),
		@NamedQuery(name = "findLabConstructionGrandsonProjectByCreateTimeBefore", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject where myLabConstructionGrandsonProject.createTime < ?1"),
		@NamedQuery(name = "findLabConstructionGrandsonProjectByCreateUser", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject where myLabConstructionGrandsonProject.createUser = ?1"),
		@NamedQuery(name = "findLabConstructionGrandsonProjectByCreateUserContaining", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject where myLabConstructionGrandsonProject.createUser like ?1"),
		@NamedQuery(name = "findLabConstructionGrandsonProjectById", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject where myLabConstructionGrandsonProject.id = ?1"),
		@NamedQuery(name = "findLabConstructionGrandsonProjectByPrimaryKey", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject where myLabConstructionGrandsonProject.id = ?1"),
		@NamedQuery(name = "findLabConstructionGrandsonProjectByProjectImplementTime", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject where myLabConstructionGrandsonProject.projectImplementTime = ?1"),
		@NamedQuery(name = "findLabConstructionGrandsonProjectByProjectImplementTimeAfter", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject where myLabConstructionGrandsonProject.projectImplementTime > ?1"),
		@NamedQuery(name = "findLabConstructionGrandsonProjectByProjectImplementTimeBefore", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject where myLabConstructionGrandsonProject.projectImplementTime < ?1"),
		@NamedQuery(name = "findLabConstructionGrandsonProjectByProjectName", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject where myLabConstructionGrandsonProject.projectName = ?1"),
		@NamedQuery(name = "findLabConstructionGrandsonProjectByProjectNameContaining", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject where myLabConstructionGrandsonProject.projectName like ?1"),
		@NamedQuery(name = "findLabConstructionGrandsonProjectByStage", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject where myLabConstructionGrandsonProject.stage = ?1"),
		@NamedQuery(name = "findLabConstructionGrandsonProjectByStatus", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject where myLabConstructionGrandsonProject.status = ?1"),
		@NamedQuery(name = "findLabConstructionGrandsonProjectByTenderActualAmount", query = "select myLabConstructionGrandsonProject from LabConstructionGrandsonProject myLabConstructionGrandsonProject where myLabConstructionGrandsonProject.tenderActualAmount = ?1") })

@Table(name = "lab_construction_grandson_project")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabConstructionGrandsonProject")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class LabConstructionGrandsonProject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	* ��ʵ����Ŀ����Ŀ������
	* 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	/**
	* ��Ŀ����
	* 
	 */

	@Column(name = "project_name")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	String projectName;
	/**
	* ��ǰ�׶Σ�1-����ĵ���2-��֤˵����3-�ɹ��ļ���4-��غ�ͬ��5-���������
	* 
	 */

	@Column(name = "stage")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	Integer stage;
	/**
	* ��ǰ�׶ε�״̬��0-�ϴ��У�1-���ͨ����2-��˾ܾ���3-����У�
	* 
	 */

	@Column(name = "status")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	Integer status;
	/**
	* ������
	* 
	 */

	@Column(name = "create_user")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	String createUser;
	/**
	* ����ʱ��
	* 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "create_time")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	Calendar createTime;
	/**
	* Ԥ��
	* 
	 */

	@Column(name = "budget")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	BigDecimal budget;
	/**
	* ��Ŀʵʩʱ��
	* 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "project_implement_time")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	Calendar projectImplementTime;
	/**
	* �б�ʵ�ʽ��
	* 
	 */

	@Column(name = "tender_actual_amount")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	BigDecimal tenderActualAmount;
	/**
	* ����ʵ�ʽ��
	* 
	 */

	@Column(name = "acceptancea_actual_amount")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	BigDecimal acceptanceaActualAmount;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "son_project_id", referencedColumnName = "id") })
	@XmlTransient
	LabConstructionSonProject labConstructionSonProject;
	/**
	 */
	@OneToMany(mappedBy = "labConstructionGrandsonProject", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)

	@XmlElement(name = "", namespace = "")
	Set<LabConstructionProjectAuditNew> labConstructionProjectAuditNews;
	/**
	 */
	@OneToMany(mappedBy = "labConstructionGrandsonProject", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)

	@XmlElement(name = "", namespace = "")
	Set<LabConstructionProjectDocument> labConstructionProjectDocuments;

	/**
	 */
	@Column(name = "submitted")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	Boolean submitted;

	/**
	* ��ʵ����Ŀ����Ŀ������
	* 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	* ��ʵ����Ŀ����Ŀ������
	* 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	* ��Ŀ����
	* 
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	* ��Ŀ����
	* 
	 */
	public String getProjectName() {
		return this.projectName;
	}

	/**
	* ��ǰ�׶Σ�1-����ĵ���2-��֤˵����3-�ɹ��ļ���4-��غ�ͬ��5-���������
	* 
	 */
	public void setStage(Integer stage) {
		this.stage = stage;
	}

	/**
	* ��ǰ�׶Σ�1-����ĵ���2-��֤˵����3-�ɹ��ļ���4-��غ�ͬ��5-���������
	* 
	 */
	public Integer getStage() {
		return this.stage;
	}

	/**
	* ��ǰ�׶ε�״̬��0-�ϴ��У�1-���ͨ����2-��˾ܾ���3-����У�
	* 
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	* ��ǰ�׶ε�״̬��0-�ϴ��У�1-���ͨ����2-��˾ܾ���3-����У�
	* 
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	* ������
	* 
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	* ������
	* 
	 */
	public String getCreateUser() {
		return this.createUser;
	}

	/**
	* ����ʱ��
	* 
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	/**
	* ����ʱ��
	* 
	 */
	public Calendar getCreateTime() {
		return this.createTime;
	}

	/**
	* Ԥ��
	* 
	 */
	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	/**
	* Ԥ��
	* 
	 */
	public BigDecimal getBudget() {
		return this.budget;
	}

	/**
	* ��Ŀʵʩʱ��
	* 
	 */
	public void setProjectImplementTime(Calendar projectImplementTime) {
		this.projectImplementTime = projectImplementTime;
	}

	/**
	* ��Ŀʵʩʱ��
	* 
	 */
	public Calendar getProjectImplementTime() {
		return this.projectImplementTime;
	}

	/**
	* �б�ʵ�ʽ��
	* 
	 */
	public void setTenderActualAmount(BigDecimal tenderActualAmount) {
		this.tenderActualAmount = tenderActualAmount;
	}

	/**
	* �б�ʵ�ʽ��
	* 
	 */
	public BigDecimal getTenderActualAmount() {
		return this.tenderActualAmount;
	}

	/**
	* ����ʵ�ʽ��
	* 
	 */
	public void setAcceptanceaActualAmount(BigDecimal acceptanceaActualAmount) {
		this.acceptanceaActualAmount = acceptanceaActualAmount;
	}

	/**
	* ����ʵ�ʽ��
	* 
	 */
	public BigDecimal getAcceptanceaActualAmount() {
		return this.acceptanceaActualAmount;
	}

	/**
	 */
	public void setLabConstructionSonProject(LabConstructionSonProject labConstructionSonProject) {
		this.labConstructionSonProject = labConstructionSonProject;
	}

	/**
	 */
	@JsonIgnore
	public LabConstructionSonProject getLabConstructionSonProject() {
		return labConstructionSonProject;
	}

	/**
	 */
	public void setLabConstructionProjectAuditNews(Set<LabConstructionProjectAuditNew> labConstructionProjectAuditNews) {
		this.labConstructionProjectAuditNews = labConstructionProjectAuditNews;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabConstructionProjectAuditNew> getLabConstructionProjectAuditNews() {
		if (labConstructionProjectAuditNews == null) {
			labConstructionProjectAuditNews = new LinkedHashSet<LabConstructionProjectAuditNew>();
		}
		return labConstructionProjectAuditNews;
	}

	/**
	 */
	public void setLabConstructionProjectDocuments(Set<LabConstructionProjectDocument> labConstructionProjectDocuments) {
		this.labConstructionProjectDocuments = labConstructionProjectDocuments;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabConstructionProjectDocument> getLabConstructionProjectDocuments() {
		if (labConstructionProjectDocuments == null) {
			labConstructionProjectDocuments = new LinkedHashSet<LabConstructionProjectDocument>();
		}
		return labConstructionProjectDocuments;
	}

	public Boolean getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Boolean submitted) {
		this.submitted = submitted;
	}

	/**
	 */
	public LabConstructionGrandsonProject() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabConstructionGrandsonProject that) {
		setId(that.getId());
		setProjectName(that.getProjectName());
		setStage(that.getStage());
		setStatus(that.getStatus());
		setCreateUser(that.getCreateUser());
		setCreateTime(that.getCreateTime());
		setBudget(that.getBudget());
		setProjectImplementTime(that.getProjectImplementTime());
		setTenderActualAmount(that.getTenderActualAmount());
		setAcceptanceaActualAmount(that.getAcceptanceaActualAmount());
		setLabConstructionSonProject(that.getLabConstructionSonProject());
		setLabConstructionProjectAuditNews(new LinkedHashSet<LabConstructionProjectAuditNew>(that.getLabConstructionProjectAuditNews()));
		setLabConstructionProjectDocuments(new LinkedHashSet<LabConstructionProjectDocument>(that.getLabConstructionProjectDocuments()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("projectName=[").append(projectName).append("] ");
		buffer.append("stage=[").append(stage).append("] ");
		buffer.append("status=[").append(status).append("] ");
		buffer.append("createUser=[").append(createUser).append("] ");
		buffer.append("createTime=[").append(createTime).append("] ");
		buffer.append("budget=[").append(budget).append("] ");
		buffer.append("projectImplementTime=[").append(projectImplementTime).append("] ");
		buffer.append("tenderActualAmount=[").append(tenderActualAmount).append("] ");
		buffer.append("acceptanceaActualAmount=[").append(acceptanceaActualAmount).append("] ");

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
		if (!(obj instanceof LabConstructionGrandsonProject))
			return false;
		LabConstructionGrandsonProject equalCheck = (LabConstructionGrandsonProject) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
