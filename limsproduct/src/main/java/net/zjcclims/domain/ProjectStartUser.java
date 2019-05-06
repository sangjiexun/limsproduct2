package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllProjectStartUsers", query = "select myProjectStartUser from ProjectStartUser myProjectStartUser"),
		@NamedQuery(name = "findProjectStartUserByAge", query = "select myProjectStartUser from ProjectStartUser myProjectStartUser where myProjectStartUser.age = ?1"),
		@NamedQuery(name = "findProjectStartUserByCname", query = "select myProjectStartUser from ProjectStartUser myProjectStartUser where myProjectStartUser.cname = ?1"),
		@NamedQuery(name = "findProjectStartUserByCnameContaining", query = "select myProjectStartUser from ProjectStartUser myProjectStartUser where myProjectStartUser.cname like ?1"),
		@NamedQuery(name = "findProjectStartUserById", query = "select myProjectStartUser from ProjectStartUser myProjectStartUser where myProjectStartUser.id = ?1"),
		@NamedQuery(name = "findProjectStartUserByInfo", query = "select myProjectStartUser from ProjectStartUser myProjectStartUser where myProjectStartUser.info = ?1"),
		@NamedQuery(name = "findProjectStartUserByJobTitle", query = "select myProjectStartUser from ProjectStartUser myProjectStartUser where myProjectStartUser.jobTitle = ?1"),
		@NamedQuery(name = "findProjectStartUserByJobTitleContaining", query = "select myProjectStartUser from ProjectStartUser myProjectStartUser where myProjectStartUser.jobTitle like ?1"),
		@NamedQuery(name = "findProjectStartUserByPosition", query = "select myProjectStartUser from ProjectStartUser myProjectStartUser where myProjectStartUser.position = ?1"),
		@NamedQuery(name = "findProjectStartUserByPositionContaining", query = "select myProjectStartUser from ProjectStartUser myProjectStartUser where myProjectStartUser.position like ?1"),
		@NamedQuery(name = "findProjectStartUserByPrimaryKey", query = "select myProjectStartUser from ProjectStartUser myProjectStartUser where myProjectStartUser.id = ?1"),
		@NamedQuery(name = "findProjectStartUserByResponsibilityContent", query = "select myProjectStartUser from ProjectStartUser myProjectStartUser where myProjectStartUser.responsibilityContent = ?1"),
		@NamedQuery(name = "findProjectStartUserByResponsibilityContentContaining", query = "select myProjectStartUser from ProjectStartUser myProjectStartUser where myProjectStartUser.responsibilityContent like ?1"),
		@NamedQuery(name = "findProjectStartUserBySex", query = "select myProjectStartUser from ProjectStartUser myProjectStartUser where myProjectStartUser.sex = ?1"),
		@NamedQuery(name = "findProjectStartUserBySexContaining", query = "select myProjectStartUser from ProjectStartUser myProjectStartUser where myProjectStartUser.sex like ?1") })
@Table(name = "project_start_user")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectStartUser")
public class ProjectStartUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	Integer id;
	/**
	 * ��������
	 * 
	 */

	@Column(name = "responsibility_content")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String responsibilityContent;
	/**
	 */

	@Column(name = "info", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String info;
	/**
	 * ����
	 * 
	 */

	@Column(name = "cname")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String cname;
	/**
	 * �Ա�
	 * 
	 */

	@Column(name = "sex")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String sex;
	/**
	 * ����
	 * 
	 */

	@Column(name = "age")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer age;
	/**
	 * ְ��
	 * 
	 */

	@Column(name = "position")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String position;
	/**
	 */

	@Column(name = "job_title")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String jobTitle;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_start_report_id", referencedColumnName = "id") })
	@XmlTransient
	ProjectStartedReport projectStartedReport;

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
	 * ��������
	 * 
	 */
	public void setResponsibilityContent(String responsibilityContent) {
		this.responsibilityContent = responsibilityContent;
	}

	/**
	 * ��������
	 * 
	 */
	public String getResponsibilityContent() {
		return this.responsibilityContent;
	}

	/**
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 */
	public String getInfo() {
		return this.info;
	}

	/**
	 * ����
	 * 
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}

	/**
	 * ����
	 * 
	 */
	public String getCname() {
		return this.cname;
	}

	/**
	 * �Ա�
	 * 
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * �Ա�
	 * 
	 */
	public String getSex() {
		return this.sex;
	}

	/**
	 * ����
	 * 
	 */
	public void setAge(Integer age) {
		this.age = age;
	}

	/**
	 * ����
	 * 
	 */
	public Integer getAge() {
		return this.age;
	}

	/**
	 * ְ��
	 * 
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * ְ��
	 * 
	 */
	public String getPosition() {
		return this.position;
	}

	/**
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 */
	public String getJobTitle() {
		return this.jobTitle;
	}

	/**
	 */
	public void setProjectStartedReport(ProjectStartedReport projectStartedReport) {
		this.projectStartedReport = projectStartedReport;
	}

	/**
	 */
	public ProjectStartedReport getProjectStartedReport() {
		return projectStartedReport;
	}

	/**
	 */
	public ProjectStartUser() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectStartUser that) {
		setId(that.getId());
		setResponsibilityContent(that.getResponsibilityContent());
		setInfo(that.getInfo());
		setCname(that.getCname());
		setSex(that.getSex());
		setAge(that.getAge());
		setPosition(that.getPosition());
		setJobTitle(that.getJobTitle());
		setProjectStartedReport(that.getProjectStartedReport());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("responsibilityContent=[").append(responsibilityContent).append("] ");
		buffer.append("info=[").append(info).append("] ");
		buffer.append("cname=[").append(cname).append("] ");
		buffer.append("sex=[").append(sex).append("] ");
		buffer.append("age=[").append(age).append("] ");
		buffer.append("position=[").append(position).append("] ");
		buffer.append("jobTitle=[").append(jobTitle).append("] ");

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
		if (!(obj instanceof ProjectStartUser))
			return false;
		ProjectStartUser equalCheck = (ProjectStartUser) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
