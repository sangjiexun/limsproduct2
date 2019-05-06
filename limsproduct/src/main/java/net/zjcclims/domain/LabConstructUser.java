package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabConstructUsers", query = "select myLabConstructUser from LabConstructUser myLabConstructUser"),
		@NamedQuery(name = "findLabConstructUserById", query = "select myLabConstructUser from LabConstructUser myLabConstructUser where myLabConstructUser.id = ?1"),
		@NamedQuery(name = "findLabConstructUserByInfo", query = "select myLabConstructUser from LabConstructUser myLabConstructUser where myLabConstructUser.info = ?1"),
		@NamedQuery(name = "findLabConstructUserByPrimaryKey", query = "select myLabConstructUser from LabConstructUser myLabConstructUser where myLabConstructUser.id = ?1"),
		@NamedQuery(name = "findLabConstructUserByResponsibilityContent", query = "select myLabConstructUser from LabConstructUser myLabConstructUser where myLabConstructUser.responsibilityContent = ?1"),
		@NamedQuery(name = "findLabConstructUserByResponsibilityContentContaining", query = "select myLabConstructUser from LabConstructUser myLabConstructUser where myLabConstructUser.responsibilityContent like ?1") })
@Table(name = "lab_construct_user")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabConstructUser")
public class LabConstructUser implements Serializable {
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
	 */

	@Column(name = "responsibility_content")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String responsibilityContent;
	/**
	 */

	@Column(name = "cname")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String cname;
	/**
	 */

	@Column(name = "sex")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String sex;
	/**
	 */

	@Column(name = "age")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer age;
	/**
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

	@Column(name = "info", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String info;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_construct_app_id", referencedColumnName = "id") })
	@XmlTransient
	LabConstructApp labConstructApp;

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

	/**ProjectFeeList
	 *
	 */
	public void setResponsibilityContent(String responsibilityContent) {
		this.responsibilityContent = responsibilityContent;
	}

	/**
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
	 */
	public void setLabConstructApp(LabConstructApp labConstructApp) {
		this.labConstructApp = labConstructApp;
	}

	/**
	 */
	public LabConstructApp getLabConstructApp() {
		return labConstructApp;
	}
	
	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 */
	public LabConstructUser() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabConstructUser that) {
		setId(that.getId());
		setResponsibilityContent(that.getResponsibilityContent());
		setInfo(that.getInfo());
		setLabConstructApp(that.getLabConstructApp());
		setCname(that.getCname());
		setSex(that.getSex());
		setAge(that.getAge());
		setPosition(that.getPosition());
		setJobTitle(that.getJobTitle());
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
		if (!(obj instanceof LabConstructUser))
			return false;
		LabConstructUser equalCheck = (LabConstructUser) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
