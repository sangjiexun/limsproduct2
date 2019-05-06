package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllProjectAcceptUsers", query = "select myProjectAcceptUser from ProjectAcceptUser myProjectAcceptUser"),
		@NamedQuery(name = "findProjectAcceptUserByAge", query = "select myProjectAcceptUser from ProjectAcceptUser myProjectAcceptUser where myProjectAcceptUser.age = ?1"),
		@NamedQuery(name = "findProjectAcceptUserByCname", query = "select myProjectAcceptUser from ProjectAcceptUser myProjectAcceptUser where myProjectAcceptUser.cname = ?1"),
		@NamedQuery(name = "findProjectAcceptUserByCnameContaining", query = "select myProjectAcceptUser from ProjectAcceptUser myProjectAcceptUser where myProjectAcceptUser.cname like ?1"),
		@NamedQuery(name = "findProjectAcceptUserById", query = "select myProjectAcceptUser from ProjectAcceptUser myProjectAcceptUser where myProjectAcceptUser.id = ?1"),
		@NamedQuery(name = "findProjectAcceptUserByInfo", query = "select myProjectAcceptUser from ProjectAcceptUser myProjectAcceptUser where myProjectAcceptUser.info = ?1"),
		@NamedQuery(name = "findProjectAcceptUserByJobTitle", query = "select myProjectAcceptUser from ProjectAcceptUser myProjectAcceptUser where myProjectAcceptUser.jobTitle = ?1"),
		@NamedQuery(name = "findProjectAcceptUserByJobTitleContaining", query = "select myProjectAcceptUser from ProjectAcceptUser myProjectAcceptUser where myProjectAcceptUser.jobTitle like ?1"),
		@NamedQuery(name = "findProjectAcceptUserByPosition", query = "select myProjectAcceptUser from ProjectAcceptUser myProjectAcceptUser where myProjectAcceptUser.position = ?1"),
		@NamedQuery(name = "findProjectAcceptUserByPositionContaining", query = "select myProjectAcceptUser from ProjectAcceptUser myProjectAcceptUser where myProjectAcceptUser.position like ?1"),
		@NamedQuery(name = "findProjectAcceptUserByPrimaryKey", query = "select myProjectAcceptUser from ProjectAcceptUser myProjectAcceptUser where myProjectAcceptUser.id = ?1"),
		@NamedQuery(name = "findProjectAcceptUserByResponsibilityContent", query = "select myProjectAcceptUser from ProjectAcceptUser myProjectAcceptUser where myProjectAcceptUser.responsibilityContent = ?1"),
		@NamedQuery(name = "findProjectAcceptUserByResponsibilityContentContaining", query = "select myProjectAcceptUser from ProjectAcceptUser myProjectAcceptUser where myProjectAcceptUser.responsibilityContent like ?1"),
		@NamedQuery(name = "findProjectAcceptUserBySex", query = "select myProjectAcceptUser from ProjectAcceptUser myProjectAcceptUser where myProjectAcceptUser.sex = ?1"),
		@NamedQuery(name = "findProjectAcceptUserBySexContaining", query = "select myProjectAcceptUser from ProjectAcceptUser myProjectAcceptUser where myProjectAcceptUser.sex like ?1") })
@Table(name = "project_accept_user")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectAcceptUser")
public class ProjectAcceptUser implements Serializable {
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
	@JoinColumns({ @JoinColumn(name = "project_accept_id", referencedColumnName = "id") })
	@XmlTransient
	ProjectAcceptanceApplication projectAcceptanceApplication;

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
	public void setProjectAcceptanceApplication(ProjectAcceptanceApplication projectAcceptanceApplication) {
		this.projectAcceptanceApplication = projectAcceptanceApplication;
	}

	/**
	 */
	public ProjectAcceptanceApplication getProjectAcceptanceApplication() {
		return projectAcceptanceApplication;
	}

	/**
	 */
	public ProjectAcceptUser() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectAcceptUser that) {
		setId(that.getId());
		setResponsibilityContent(that.getResponsibilityContent());
		setInfo(that.getInfo());
		setCname(that.getCname());
		setSex(that.getSex());
		setAge(that.getAge());
		setPosition(that.getPosition());
		setJobTitle(that.getJobTitle());
		setProjectAcceptanceApplication(that.getProjectAcceptanceApplication());
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
		if (!(obj instanceof ProjectAcceptUser))
			return false;
		ProjectAcceptUser equalCheck = (ProjectAcceptUser) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
