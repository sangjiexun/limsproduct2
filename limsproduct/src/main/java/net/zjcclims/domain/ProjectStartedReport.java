package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllProjectStartedReports", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport"),
		@NamedQuery(name = "findProjectStartedReportByEquipmentList", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.equipmentList = ?1"),
		@NamedQuery(name = "findProjectStartedReportByFeeApp", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.feeApp = ?1"),
		@NamedQuery(name = "findProjectStartedReportByFeeApprovalDetail", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.feeApprovalDetail = ?1"),
		@NamedQuery(name = "findProjectStartedReportByFeeCode", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.feeCode = ?1"),
		@NamedQuery(name = "findProjectStartedReportByFeeCodeContaining", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.feeCode like ?1"),
		@NamedQuery(name = "findProjectStartedReportById", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.id = ?1"),
		@NamedQuery(name = "findProjectStartedReportByLabAddress", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.labAddress = ?1"),
		@NamedQuery(name = "findProjectStartedReportByLabAddressContaining", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.labAddress like ?1"),
		@NamedQuery(name = "findProjectStartedReportByLabArea", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.labArea = ?1"),
		@NamedQuery(name = "findProjectStartedReportByOpenLabItem", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.openLabItem = ?1"),
		@NamedQuery(name = "findProjectStartedReportByPrimaryKey", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.id = ?1"),
		@NamedQuery(name = "findProjectStartedReportByProjectName", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.projectName = ?1"),
		@NamedQuery(name = "findProjectStartedReportByProjectNameContaining", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.projectName like ?1"),
		@NamedQuery(name = "findProjectStartedReportByStartDate", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.startDate = ?1"),
		@NamedQuery(name = "findProjectStartedReportByStartDateAfter", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.startDate > ?1"),
		@NamedQuery(name = "findProjectStartedReportByOtherFee", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.otherFee = ?1"),
		@NamedQuery(name = "findProjectStartedReportByFeeAmount", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.feeAmount = ?1"),
		@NamedQuery(name = "findProjectStartedReportByStartDateBefore", query = "select myProjectStartedReport from ProjectStartedReport myProjectStartedReport where myProjectStartedReport.startDate < ?1") })
@Table(name = "project_started_report")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectStartedReport")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class ProjectStartedReport implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵ���ҽ�����Ŀ��������
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	/**
	 * ��Ŀ���
	 * 
	 */

	@Column(name = "project_name", length = 100)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String projectName;
	/**
	 * ʵѵ�ҵ�ַ
	 * 
	 */

	@Column(name = "lab_address")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String labAddress;
	/**
	 * ʵѵ�����
	 * 
	 */

	@Column(name = "lab_area", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal labArea;
	/**
	 * �걨����
	 * 
	 */

	@Column(name = "fee_app", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal feeApp;
	/**
	 * ���ѱ��
	 * 
	 */

	@Column(name = "fee_code", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String feeCode;
	/**
	 * ��������
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "start_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar startDate;
	/**
	 * ���ɺ��ܿ����ʵ�飨ʵѵ����Ŀ
	 * 
	 */

	@Column(name = "open_lab_item")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer openLabItem;
	/**
	 * �豸��ϸ��
	 * 
	 */

	@Column(name = "equipment_list")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer equipmentList;
	/**
	 * ������ϸ
	 * 
	 */

	@Column(name = "fee_approval_detail")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer feeApprovalDetail;
	
	/**
	 * 经费金额
	 * 
	 */

	@Column(name = "fee_amount", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal feeAmount;
	/**
	 * 其他经费来源及金额
	 * 
	 */

	@Column(name = "other_fee", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal otherFee;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_id", referencedColumnName = "id") })
	@XmlTransient
	LabConstructApp labConstructApp;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "academy_number", referencedColumnName = "academy_number") })
	@XmlTransient
	SchoolAcademy schoolAcademy;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_source_id", referencedColumnName = "id") })
	@XmlTransient
	CProjectSource CProjectSource;
	/**
	 */
	@OneToMany(mappedBy = "projectStartedReport", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectAcceptanceApplication> projectAcceptanceApplications;
	/**
	 */
	@OneToMany(mappedBy = "projectStartedReport", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectStartUser> projectStartUsers;
	/**
	 */
	@OneToMany(mappedBy = "projectStartedReport", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectStartCompletionItem> projectStartCompletionItems;
	/**
	 */
	@OneToMany(mappedBy = "projectStartedReport", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectStartFeeList> projectStartFeeLists;
	/**
	 */
	@OneToMany(mappedBy = "projectStartedReport", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectStartDevice> projectStartDevices;

	/**
	 */
	@OneToMany(mappedBy = "projectStartedReportByProjectStartReportApproval", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<CommonDocument> commonDocumentsForProjectStartReportApproval;
	/**
	 */
	@OneToMany(mappedBy = "projectStartedReportByProjectStartReportApproval", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<CommonDocument> commonDocumentsForProjectStartReportDiscussion;


	/**
	 * ʵ���ҽ�����Ŀ��������
	 *
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵ���ҽ�����Ŀ��������
	 *
	 */
	public Integer getId() {
		return this.id;
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
	 * ʵѵ�ҵ�ַ
	 *
	 */
	public void setLabAddress(String labAddress) {
		this.labAddress = labAddress;
	}

	/**
	 * ʵѵ�ҵ�ַ
	 *
	 */
	public String getLabAddress() {
		return this.labAddress;
	}

	/**
	 * ʵѵ�����
	 *
	 */
	public void setLabArea(BigDecimal labArea) {
		this.labArea = labArea;
	}

	/**
	 * ʵѵ�����
	 *
	 */
	public BigDecimal getLabArea() {
		return this.labArea;
	}

	/**
	 * �걨����
	 *
	 */
	public void setFeeApp(BigDecimal feeApp) {
		this.feeApp = feeApp;
	}

	/**
	 * �걨����
	 *
	 */
	public BigDecimal getFeeApp() {
		return this.feeApp;
	}

	/**
	 * ���ѱ��
	 *
	 */
	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	/**
	 * ���ѱ��
	 *
	 */
	public String getFeeCode() {
		return this.feeCode;
	}

	/**
	 * ��������
	 *
	 */
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	/**
	 * ��������
	 *
	 */
	public Calendar getStartDate() {
		return this.startDate;
	}

	/**
	 * ���ɺ��ܿ����ʵ�飨ʵѵ����Ŀ
	 *
	 */
	public void setOpenLabItem(Integer openLabItem) {
		this.openLabItem = openLabItem;
	}

	/**
	 * ���ɺ��ܿ����ʵ�飨ʵѵ����Ŀ
	 *
	 */
	public Integer getOpenLabItem() {
		return this.openLabItem;
	}

	/**
	 * �豸��ϸ��
	 *
	 */
	public void setEquipmentList(Integer equipmentList) {
		this.equipmentList = equipmentList;
	}

	/**
	 * �豸��ϸ��
	 *
	 */
	public Integer getEquipmentList() {
		return this.equipmentList;
	}

	/**
	 * ������ϸ
	 *
	 */
	public void setFeeApprovalDetail(Integer feeApprovalDetail) {
		this.feeApprovalDetail = feeApprovalDetail;
	}

	/**
	 * ������ϸ
	 *
	 */
	public Integer getFeeApprovalDetail() {
		return this.feeApprovalDetail;
	}
	/**
	 * 经费金额
	 *
	 */
	public void setFeeAmount(BigDecimal feeAmount) {
		this.feeAmount = feeAmount;
	}

	/**
	 * 经费金额
	 *
	 */
	public BigDecimal getFeeAmount() {
		return this.feeAmount;
	}

	/**
	 * 其他经费来源及金额
	 *
	 */
	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	/**
	 * 其他经费来源及金额
	 *
	 */
	public BigDecimal getOtherFee() {
		return this.otherFee;
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

	/**
	 */
	public void setSchoolAcademy(SchoolAcademy schoolAcademy) {
		this.schoolAcademy = schoolAcademy;
	}

	/**
	 */
	public SchoolAcademy getSchoolAcademy() {
		return schoolAcademy;
	}

	/**
	 */
	public void setCProjectSource(CProjectSource CProjectSource) {
		this.CProjectSource = CProjectSource;
	}

	/**
	 */
	public CProjectSource getCProjectSource() {
		return CProjectSource;
	}

	/**
	 */
	public void setProjectAcceptanceApplications(Set<ProjectAcceptanceApplication> projectAcceptanceApplications) {
		this.projectAcceptanceApplications = projectAcceptanceApplications;
	}

	/**
	 */
	public Set<ProjectAcceptanceApplication> getProjectAcceptanceApplications() {
		if (projectAcceptanceApplications == null) {
			projectAcceptanceApplications = new java.util.LinkedHashSet<ProjectAcceptanceApplication>();
		}
		return projectAcceptanceApplications;
	}

	/**
	 */
	public void setProjectStartUsers(Set<ProjectStartUser> projectStartUsers) {
		this.projectStartUsers = projectStartUsers;
	}

	/**
	 */
	public Set<ProjectStartUser> getProjectStartUsers() {
		if (projectStartUsers == null) {
			projectStartUsers = new java.util.LinkedHashSet<ProjectStartUser>();
		}
		return projectStartUsers;
	}

	/**
	 */
	public void setProjectStartCompletionItems(Set<ProjectStartCompletionItem> projectStartCompletionItems) {
		this.projectStartCompletionItems = projectStartCompletionItems;
	}

	/**
	 */
	public Set<ProjectStartCompletionItem> getProjectStartCompletionItems() {
		if (projectStartCompletionItems == null) {
			projectStartCompletionItems = new java.util.LinkedHashSet<ProjectStartCompletionItem>();
		}
		return projectStartCompletionItems;
	}

	/**
	 */
	public void setProjectStartFeeLists(Set<ProjectStartFeeList> projectStartFeeLists) {
		this.projectStartFeeLists = projectStartFeeLists;
	}

	/**
	 */
	public Set<ProjectStartFeeList> getProjectStartFeeLists() {
		if (projectStartFeeLists == null) {
			projectStartFeeLists = new java.util.LinkedHashSet<ProjectStartFeeList>();
		}
		return projectStartFeeLists;
	}

	/**
	 */
	public void setProjectStartDevices(Set<ProjectStartDevice> projectStartDevices) {
		this.projectStartDevices = projectStartDevices;
	}

	/**
	 */
	public Set<ProjectStartDevice> getProjectStartDevices() {
		if (projectStartDevices == null) {
			projectStartDevices = new java.util.LinkedHashSet<ProjectStartDevice>();
		}
		return projectStartDevices;
	}

	/**
	 */
	public void setCommonDocumentsForProjectStartReportApproval(Set<CommonDocument> commonDocumentsForProjectStartReportApproval) {
		this.commonDocumentsForProjectStartReportApproval = commonDocumentsForProjectStartReportApproval;
	}

	/**
	 */
	public Set<CommonDocument> getCommonDocumentsForProjectStartReportApproval() {
		if (commonDocumentsForProjectStartReportApproval == null) {
			commonDocumentsForProjectStartReportApproval = new java.util.LinkedHashSet<CommonDocument>();
		}
		return commonDocumentsForProjectStartReportApproval;
	}

	/**
	 */
	public void setCommonDocumentsForProjectStartReportDiscussion(Set<CommonDocument> commonDocumentsForProjectStartReportDiscussion) {
		this.commonDocumentsForProjectStartReportDiscussion = commonDocumentsForProjectStartReportDiscussion;
	}

	/**
	 */
	public Set<CommonDocument> getCommonDocumentsForProjectStartReportDiscussion() {
		if (commonDocumentsForProjectStartReportDiscussion == null) {
			commonDocumentsForProjectStartReportDiscussion = new java.util.LinkedHashSet<CommonDocument>();
		}
		return commonDocumentsForProjectStartReportDiscussion;
	}

	/**
	 */
	public ProjectStartedReport() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectStartedReport that) {
		setId(that.getId());
		setProjectName(that.getProjectName());
		setLabAddress(that.getLabAddress());
		setLabArea(that.getLabArea());
		setFeeApp(that.getFeeApp());
		setFeeCode(that.getFeeCode());
		setStartDate(that.getStartDate());
		setOpenLabItem(that.getOpenLabItem());
		setEquipmentList(that.getEquipmentList());
		setFeeApprovalDetail(that.getFeeApprovalDetail());
		setFeeAmount(that.getFeeAmount());
		setOtherFee(that.getOtherFee());
		setLabConstructApp(that.getLabConstructApp());
		setSchoolAcademy(that.getSchoolAcademy());
		setCProjectSource(that.getCProjectSource());
		setProjectAcceptanceApplications(new java.util.LinkedHashSet<ProjectAcceptanceApplication>(that.getProjectAcceptanceApplications()));
		setProjectStartUsers(new java.util.LinkedHashSet<ProjectStartUser>(that.getProjectStartUsers()));
		setProjectStartCompletionItems(new java.util.LinkedHashSet<ProjectStartCompletionItem>(that.getProjectStartCompletionItems()));
		setProjectStartFeeLists(new java.util.LinkedHashSet<ProjectStartFeeList>(that.getProjectStartFeeLists()));
		setProjectStartDevices(new java.util.LinkedHashSet<ProjectStartDevice>(that.getProjectStartDevices()));
		setCommonDocumentsForProjectStartReportApproval(new java.util.LinkedHashSet<CommonDocument>(that.getCommonDocumentsForProjectStartReportApproval()));
		setCommonDocumentsForProjectStartReportDiscussion(new java.util.LinkedHashSet<CommonDocument>(that.getCommonDocumentsForProjectStartReportDiscussion()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("projectName=[").append(projectName).append("] ");
		buffer.append("labAddress=[").append(labAddress).append("] ");
		buffer.append("labArea=[").append(labArea).append("] ");
		buffer.append("feeApp=[").append(feeApp).append("] ");
		buffer.append("feeCode=[").append(feeCode).append("] ");
		buffer.append("startDate=[").append(startDate).append("] ");
		buffer.append("openLabItem=[").append(openLabItem).append("] ");
		buffer.append("equipmentList=[").append(equipmentList).append("] ");
		buffer.append("feeApprovalDetail=[").append(feeApprovalDetail).append("] ");
		buffer.append("feeAmount=[").append(feeAmount).append("] ");
		buffer.append("otherFee=[").append(otherFee).append("] ");

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
		if (!(obj instanceof ProjectStartedReport))
			return false;
		ProjectStartedReport equalCheck = (ProjectStartedReport) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
