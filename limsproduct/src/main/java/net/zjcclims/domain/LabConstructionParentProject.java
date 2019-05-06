
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
		@NamedQuery(name = "findAllLabConstructionParentProjects", query = "select myLabConstructionParentProject from LabConstructionParentProject myLabConstructionParentProject"),
		@NamedQuery(name = "findLabConstructionParentProjectByBudget", query = "select myLabConstructionParentProject from LabConstructionParentProject myLabConstructionParentProject where myLabConstructionParentProject.budget = ?1"),
		@NamedQuery(name = "findLabConstructionParentProjectByCreateTime", query = "select myLabConstructionParentProject from LabConstructionParentProject myLabConstructionParentProject where myLabConstructionParentProject.createTime = ?1"),
		@NamedQuery(name = "findLabConstructionParentProjectByCreateTimeAfter", query = "select myLabConstructionParentProject from LabConstructionParentProject myLabConstructionParentProject where myLabConstructionParentProject.createTime > ?1"),
		@NamedQuery(name = "findLabConstructionParentProjectByCreateTimeBefore", query = "select myLabConstructionParentProject from LabConstructionParentProject myLabConstructionParentProject where myLabConstructionParentProject.createTime < ?1"),
		@NamedQuery(name = "findLabConstructionParentProjectByCreateUser", query = "select myLabConstructionParentProject from LabConstructionParentProject myLabConstructionParentProject where myLabConstructionParentProject.createUser = ?1"),
		@NamedQuery(name = "findLabConstructionParentProjectByCreateUserContaining", query = "select myLabConstructionParentProject from LabConstructionParentProject myLabConstructionParentProject where myLabConstructionParentProject.createUser like ?1"),
		@NamedQuery(name = "findLabConstructionParentProjectById", query = "select myLabConstructionParentProject from LabConstructionParentProject myLabConstructionParentProject where myLabConstructionParentProject.id = ?1"),
		@NamedQuery(name = "findLabConstructionParentProjectByPrimaryKey", query = "select myLabConstructionParentProject from LabConstructionParentProject myLabConstructionParentProject where myLabConstructionParentProject.id = ?1"),
		@NamedQuery(name = "findLabConstructionParentProjectByProjectName", query = "select myLabConstructionParentProject from LabConstructionParentProject myLabConstructionParentProject where myLabConstructionParentProject.projectName = ?1"),
		@NamedQuery(name = "findLabConstructionParentProjectByProjectNameContaining", query = "select myLabConstructionParentProject from LabConstructionParentProject myLabConstructionParentProject where myLabConstructionParentProject.projectName like ?1"),
		@NamedQuery(name = "findLabConstructionParentProjectBySubmitted", query = "select myLabConstructionParentProject from LabConstructionParentProject myLabConstructionParentProject where myLabConstructionParentProject.submitted = ?1") })

@Table(name = "lab_construction_parent_project")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabConstructionParentProject")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class LabConstructionParentProject implements Serializable {
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
	* �Ƿ����ύ
	* 
	 */

	@Column(name = "submitted")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	Boolean submitted;

	/**
	 */
	@OneToMany(mappedBy = "labConstructionParentProject", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)

	@XmlElement(name = "", namespace = "")
	Set<LabConstructionSonProject> labConstructionSonProjects;

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
	public void setLabConstructionSonProjects(Set<LabConstructionSonProject> labConstructionSonProjects) {
		this.labConstructionSonProjects = labConstructionSonProjects;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabConstructionSonProject> getLabConstructionSonProjects() {
		if (labConstructionSonProjects == null) {
			labConstructionSonProjects = new LinkedHashSet<LabConstructionSonProject>();
		}
		return labConstructionSonProjects;
	}

	/**
	 */
	public LabConstructionParentProject() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabConstructionParentProject that) {
		setId(that.getId());
		setProjectName(that.getProjectName());
		setCreateUser(that.getCreateUser());
		setCreateTime(that.getCreateTime());
		setBudget(that.getBudget());
		setSubmitted(that.getSubmitted());
		setLabConstructionSonProjects(new LinkedHashSet<LabConstructionSonProject>(that.getLabConstructionSonProjects()));
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
		if (!(obj instanceof LabConstructionParentProject))
			return false;
		LabConstructionParentProject equalCheck = (LabConstructionParentProject) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
