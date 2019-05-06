
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
		@NamedQuery(name = "findAllLabConstructionSonProjects", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject"),
		@NamedQuery(name = "findLabConstructionSonProjectByBudget", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject where myLabConstructionSonProject.budget = ?1"),
		@NamedQuery(name = "findLabConstructionSonProjectByBudgetBalanceTime", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject where myLabConstructionSonProject.budgetBalanceTime = ?1"),
		@NamedQuery(name = "findLabConstructionSonProjectByBudgetBalanceTimeAfter", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject where myLabConstructionSonProject.budgetBalanceTime > ?1"),
		@NamedQuery(name = "findLabConstructionSonProjectByBudgetBalanceTimeBefore", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject where myLabConstructionSonProject.budgetBalanceTime < ?1"),
		@NamedQuery(name = "findLabConstructionSonProjectByCreateTime", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject where myLabConstructionSonProject.createTime = ?1"),
		@NamedQuery(name = "findLabConstructionSonProjectByCreateTimeAfter", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject where myLabConstructionSonProject.createTime > ?1"),
		@NamedQuery(name = "findLabConstructionSonProjectByCreateTimeBefore", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject where myLabConstructionSonProject.createTime < ?1"),
		@NamedQuery(name = "findLabConstructionSonProjectByCreateUser", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject where myLabConstructionSonProject.createUser = ?1"),
		@NamedQuery(name = "findLabConstructionSonProjectByCreateUserContaining", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject where myLabConstructionSonProject.createUser like ?1"),
		@NamedQuery(name = "findLabConstructionSonProjectById", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject where myLabConstructionSonProject.id = ?1"),
		@NamedQuery(name = "findLabConstructionSonProjectByPrimaryKey", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject where myLabConstructionSonProject.id = ?1"),
		@NamedQuery(name = "findLabConstructionSonProjectByProjectImplementTime", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject where myLabConstructionSonProject.projectImplementTime = ?1"),
		@NamedQuery(name = "findLabConstructionSonProjectByProjectImplementTimeAfter", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject where myLabConstructionSonProject.projectImplementTime > ?1"),
		@NamedQuery(name = "findLabConstructionSonProjectByProjectImplementTimeBefore", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject where myLabConstructionSonProject.projectImplementTime < ?1"),
		@NamedQuery(name = "findLabConstructionSonProjectByProjectName", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject where myLabConstructionSonProject.projectName = ?1"),
		@NamedQuery(name = "findLabConstructionSonProjectByProjectNameContaining", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject where myLabConstructionSonProject.projectName like ?1"),
		@NamedQuery(name = "findLabConstructionSonProjectBySubmitted", query = "select myLabConstructionSonProject from LabConstructionSonProject myLabConstructionSonProject where myLabConstructionSonProject.submitted = ?1") })

@Table(name = "lab_construction_son_project")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabConstructionSonProject")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class LabConstructionSonProject implements Serializable {
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
	* Ԥ�����ʱ��
	* 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "budget_balance_time")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	Calendar budgetBalanceTime;
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
	* �Ƿ����ύ
	* 
	 */

	@Column(name = "submitted")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	Boolean submitted;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "parent_project_id", referencedColumnName = "id") })
	@XmlTransient
	LabConstructionParentProject labConstructionParentProject;
	/**
	 */
	@ManyToMany(fetch = FetchType.LAZY)

	@JoinTable(name = "lab_construction_son_project_academies", joinColumns = {
			@JoinColumn(name = "son_project_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "academy_number", referencedColumnName = "academy_number", nullable = false, updatable = false) })
	@XmlElement(name = "", namespace = "")
	Set<SchoolAcademy> schoolAcademies;
	/**
	 */
	@OneToMany(mappedBy = "labConstructionSonProject", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)

	@XmlElement(name = "", namespace = "")
	Set<LabConstructionGrandsonProject> labConstructionGrandsonProjects;

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
	* Ԥ�����ʱ��
	* 
	 */
	public void setBudgetBalanceTime(Calendar budgetBalanceTime) {
		this.budgetBalanceTime = budgetBalanceTime;
	}

	/**
	* Ԥ�����ʱ��
	* 
	 */
	public Calendar getBudgetBalanceTime() {
		return this.budgetBalanceTime;
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
	* �Ƿ����ύ
	* 
	 */
	public void setSubmitted(Boolean submitted) {
		this.submitted = submitted;
	}

	/**
	* �Ƿ����ύ
	* 
	 */
	public Boolean getSubmitted() {
		return this.submitted;
	}

	/**
	 */
	public void setLabConstructionParentProject(LabConstructionParentProject labConstructionParentProject) {
		this.labConstructionParentProject = labConstructionParentProject;
	}

	/**
	 */
	@JsonIgnore
	public LabConstructionParentProject getLabConstructionParentProject() {
		return labConstructionParentProject;
	}

	/**
	 */
	public void setSchoolAcademies(Set<SchoolAcademy> schoolAcademies) {
		this.schoolAcademies = schoolAcademies;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolAcademy> getSchoolAcademies() {
		if (schoolAcademies == null) {
			schoolAcademies = new LinkedHashSet<SchoolAcademy>();
		}
		return schoolAcademies;
	}

	/**
	 */
	public void setLabConstructionGrandsonProjects(Set<LabConstructionGrandsonProject> labConstructionGrandsonProjects) {
		this.labConstructionGrandsonProjects = labConstructionGrandsonProjects;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabConstructionGrandsonProject> getLabConstructionGrandsonProjects() {
		if (labConstructionGrandsonProjects == null) {
			labConstructionGrandsonProjects = new LinkedHashSet<LabConstructionGrandsonProject>();
		}
		return labConstructionGrandsonProjects;
	}

	/**
	 */
	public LabConstructionSonProject() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabConstructionSonProject that) {
		setId(that.getId());
		setProjectName(that.getProjectName());
		setCreateUser(that.getCreateUser());
		setCreateTime(that.getCreateTime());
		setBudget(that.getBudget());
		setBudgetBalanceTime(that.getBudgetBalanceTime());
		setProjectImplementTime(that.getProjectImplementTime());
		setSubmitted(that.getSubmitted());
		setLabConstructionParentProject(that.getLabConstructionParentProject());
		setSchoolAcademies(new LinkedHashSet<SchoolAcademy>(that.getSchoolAcademies()));
		setLabConstructionGrandsonProjects(new LinkedHashSet<LabConstructionGrandsonProject>(that.getLabConstructionGrandsonProjects()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("projectName=[").append(projectName).append("] ");
		buffer.append("createUser=[").append(createUser).append("] ");
		buffer.append("createTime=[").append(createTime).append("] ");
		buffer.append("budget=[").append(budget).append("] ");
		buffer.append("budgetBalanceTime=[").append(budgetBalanceTime).append("] ");
		buffer.append("projectImplementTime=[").append(projectImplementTime).append("] ");
		buffer.append("submitted=[").append(submitted).append("] ");

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
		if (!(obj instanceof LabConstructionSonProject))
			return false;
		LabConstructionSonProject equalCheck = (LabConstructionSonProject) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
