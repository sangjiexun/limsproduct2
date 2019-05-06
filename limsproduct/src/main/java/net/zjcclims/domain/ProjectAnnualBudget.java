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
		@NamedQuery(name = "findAllProjectAnnualBudgets", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget"),
		@NamedQuery(name = "findProjectAnnualBudgetByAcademyName", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.academyName = ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByAcademyNameContaining", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.academyName like ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByAppendix", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.appendix = ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByAppendixContaining", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.appendix like ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByConstructYear", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.constructYear = ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByConstructYearContaining", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.constructYear like ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetById", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.id = ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByManager", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.manager = ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByManagerContaining", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.manager like ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByPrimaryKey", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.id = ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByProjectFunds", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.projectFunds = ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByProjectFundsContaining", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.projectFunds like ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByProjectName", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.projectName = ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByProjectNameContaining", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.projectName like ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByProjectProceed", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.projectProceed = ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByProjectProceedContaining", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.projectProceed like ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByProjectSource", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.projectSource = ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByProjectSourceContaining", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.projectSource like ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByStatus", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.status = ?1"),
		@NamedQuery(name = "findProjectAnnualBudgetByStatusContaining", query = "select myProjectAnnualBudget from ProjectAnnualBudget myProjectAnnualBudget where myProjectAnnualBudget.status like ?1") })
@Table(name = "project_annual_budget")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectAnnualBudget")
public class ProjectAnnualBudget implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
    Integer id;
	/**
	 * ����ϵ��
	 * 
	 */

	@Column(name = "academy_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String academyName;
	/**
	 * ��Ŀ���
	 * 
	 */

	@Column(name = "project_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String projectName;
	/**
	 * ��Ŀ��Դ
	 * 
	 */

	@Column(name = "project_source")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String projectSource;
	/**
	 * �������
	 * 
	 */

	@Column(name = "construct_year")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String constructYear;
	/**
	 * ��Ŀ����
	 * 
	 */

	@Column(name = "project_funds")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String projectFunds;
	/**
	 * ��Ŀ������
	 * 
	 */

	@Column(name = "manager")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String manager;
	/**
	 * ��Ŀ��չ
	 * 
	 */

	@Column(name = "project_proceed")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String projectProceed;
	/**
	 * ��������ʱ��
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "dead_lines")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Calendar deadLines;
	/**
	 * ״̬
	 * 
	 */

	@Column(name = "status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String status;
	/**
	 * ����
	 * 
	 */

	@Column(name = "appendix")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    String appendix;
	
	@OneToMany(mappedBy = "projectAnnualBudget", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<CommonDocument> commonDocuments;

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
	 * ����ϵ��
	 * 
	 */
	public void setAcademyName(String academyName) {
		this.academyName = academyName;
	}

	/**
	 * ����ϵ��
	 * 
	 */
	public String getAcademyName() {
		return this.academyName;
	}

	/**
	 * ��Ŀ���
	 * 
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * ��Ŀ���
	 * 
	 */
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * ��Ŀ��Դ
	 * 
	 */
	public void setProjectSource(String projectSource) {
		this.projectSource = projectSource;
	}

	/**
	 * ��Ŀ��Դ
	 * 
	 */
	public String getProjectSource() {
		return this.projectSource;
	}

	/**
	 * �������
	 * 
	 */
	public void setConstructYear(String constructYear) {
		this.constructYear = constructYear;
	}

	/**
	 * �������
	 * 
	 */
	public String getConstructYear() {
		return this.constructYear;
	}

	/**
	 * ��Ŀ����
	 * 
	 */
	public void setProjectFunds(String projectFunds) {
		this.projectFunds = projectFunds;
	}

	/**
	 * ��Ŀ����
	 * 
	 */
	public String getProjectFunds() {
		return this.projectFunds;
	}

	/**
	 * ��Ŀ������
	 * 
	 */
	public void setManager(String manager) {
		this.manager = manager;
	}

	/**
	 * ��Ŀ������
	 * 
	 */
	public String getManager() {
		return this.manager;
	}

	/**
	 * ��Ŀ��չ
	 * 
	 */
	public void setProjectProceed(String projectProceed) {
		this.projectProceed = projectProceed;
	}

	/**
	 * ��Ŀ��չ
	 * 
	 */
	public String getProjectProceed() {
		return this.projectProceed;
	}

	/**
	 * ��������ʱ��
	 * 
	 */
	public void setDeadLines(Calendar deadLines) {
		this.deadLines = deadLines;
	}

	/**
	 * ��������ʱ��
	 * 
	 */
	public Calendar getDeadLines() {
		return this.deadLines;
	}

	/**
	 * ״̬
	 * 
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * ״̬
	 * 
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * ����
	 * 
	 */
	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}

	/**
	 * ����
	 * 
	 */
	public String getAppendix() {
		return this.appendix;
	}
	
	public java.util.Set<CommonDocument> getCommonDocuments() {
		return commonDocuments;
	}

	public void setCommonDocuments(
			java.util.Set<CommonDocument> commonDocuments) {
		this.commonDocuments = commonDocuments;
	}

	/**
	 */
	public ProjectAnnualBudget() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectAnnualBudget that) {
		setId(that.getId());
		setAcademyName(that.getAcademyName());
		setProjectName(that.getProjectName());
		setProjectSource(that.getProjectSource());
		setConstructYear(that.getConstructYear());
		setProjectFunds(that.getProjectFunds());
		setManager(that.getManager());
		setProjectProceed(that.getProjectProceed());
		setDeadLines(that.getDeadLines());
		setStatus(that.getStatus());
		setAppendix(that.getAppendix());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("academyName=[").append(academyName).append("] ");
		buffer.append("projectName=[").append(projectName).append("] ");
		buffer.append("projectSource=[").append(projectSource).append("] ");
		buffer.append("constructYear=[").append(constructYear).append("] ");
		buffer.append("projectFunds=[").append(projectFunds).append("] ");
		buffer.append("manager=[").append(manager).append("] ");
		buffer.append("projectProceed=[").append(projectProceed).append("] ");
		buffer.append("deadLines=[").append(deadLines).append("] ");
		buffer.append("status=[").append(status).append("] ");
		buffer.append("appendix=[").append(appendix).append("] ");

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
		if (!(obj instanceof ProjectAnnualBudget))
			return false;
		ProjectAnnualBudget equalCheck = (ProjectAnnualBudget) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
