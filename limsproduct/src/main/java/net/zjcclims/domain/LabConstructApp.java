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
		@NamedQuery(name = "findAllLabConstructApps", query = "select myLabConstructApp from LabConstructApp myLabConstructApp"),
		@NamedQuery(name = "findLabConstructAppByAppDate", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.appDate = ?1"),
		@NamedQuery(name = "findLabConstructAppByAppDateAfter", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.appDate > ?1"),
		@NamedQuery(name = "findLabConstructAppByAppDateBefore", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.appDate < ?1"),
		@NamedQuery(name = "findLabConstructAppByAppropriationBudget", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.appropriationBudget = ?1"),
		@NamedQuery(name = "findLabConstructAppByApprovalAppendix", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.approvalAppendix = ?1"),
		@NamedQuery(name = "findLabConstructAppByApprovalAppendixContaining", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.approvalAppendix like ?1"),
		@NamedQuery(name = "findLabConstructAppByConstructBasis", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.constructBasis = ?1"),
		@NamedQuery(name = "findLabConstructAppByConstructBasisContaining", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.constructBasis like ?1"),
		@NamedQuery(name = "findLabConstructAppByEquipmentDetail", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.equipmentDetail = ?1"),
		@NamedQuery(name = "findLabConstructAppByExpectedResult", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.expectedResult = ?1"),
		@NamedQuery(name = "findLabConstructAppByExpectedResultContaining", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.expectedResult like ?1"),
		@NamedQuery(name = "findLabConstructAppById", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.id = ?1"),
		@NamedQuery(name = "findLabConstructAppByLabConstructAppCode", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.labConstructAppCode = ?1"),
		@NamedQuery(name = "findLabConstructAppByLabConstructAppCodeContaining", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.labConstructAppCode like ?1"),
		@NamedQuery(name = "findLabConstructAppByOpenLabItem", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.openLabItem = ?1"),
		@NamedQuery(name = "findLabConstructAppByOtherAppendix", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.otherAppendix = ?1"),
		@NamedQuery(name = "findLabConstructAppByOtherAppendixContaining", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.otherAppendix like ?1"),
		@NamedQuery(name = "findLabConstructAppByParticipant", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.participant = ?1"),
		@NamedQuery(name = "findLabConstructAppByPartyId", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.partyId = ?1"),
		@NamedQuery(name = "findLabConstructAppByPrimaryKey", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.id = ?1"),
		@NamedQuery(name = "findLabConstructAppByPrimaryObjective", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.primaryObjective = ?1"),
		@NamedQuery(name = "findLabConstructAppByPrimaryObjectiveContaining", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.primaryObjective like ?1"),
		@NamedQuery(name = "findLabConstructAppByProjectBasis", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.projectBasis = ?1"),
		@NamedQuery(name = "findLabConstructAppByProjectBasisContaining", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.projectBasis like ?1"),
		@NamedQuery(name = "findLabConstructAppByProjectName", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.projectName = ?1"),
		@NamedQuery(name = "findLabConstructAppByProjectNameContaining", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.projectName like ?1"),
		@NamedQuery(name = "findLabConstructAppBySpecialInnovation", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.specialInnovation = ?1"),
		@NamedQuery(name = "findLabConstructAppByPlanSchedule", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.planSchedule = ?1"),
		@NamedQuery(name = "findLabConstructAppByMajorAmount", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.majorAmount = ?1"),
		@NamedQuery(name = "findLabConstructAppByCourseAmount", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.courseAmount = ?1"),
		@NamedQuery(name = "findLabConstructAppByFeeAmount", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.feeAmount = ?1"),
		@NamedQuery(name = "findLabConstructAppByOtherFee", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.otherFee = ?1"),
		@NamedQuery(name = "findLabConstructAppByMajorName", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.majorName = ?1"),
		@NamedQuery(name = "findLabConstructAppByMajorNameContaining", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.majorName like ?1"),
		@NamedQuery(name = "findLabConstructAppByPurposeName", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.purposeName = ?1"),
		@NamedQuery(name = "findLabConstructAppByPurposeNameContaining", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.purposeName like ?1"),
		@NamedQuery(name = "findLabConstructAppByCourseName", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.courseName = ?1"),
		@NamedQuery(name = "findLabConstructAppByCourseNameContaining", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.courseName like ?1"),
		@NamedQuery(name = "findLabConstructAppBySpecialInnovationContaining", query = "select myLabConstructApp from LabConstructApp myLabConstructApp where myLabConstructApp.specialInnovation like ?1") })
@Table(name = "lab_construct_app")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabConstructApp")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class LabConstructApp implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ����
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	/**
	 * ������
	 * 
	 */

	@Column(name = "lab_construct_app_code", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String labConstructAppCode;
	/**
	 * ��Ŀ���
	 * 
	 */

	@Column(name = "project_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String projectName;
	/**
	 * ����ϵ��
	 * 
	 */

	@Column(name = "party_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer partyId;
	/**
	 * �걨ʱ��
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "app_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar appDate;
	/**
	 */

	@Column(name = "purpose_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String purposeName;
	/**
	 */

	@Column(name = "major_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String majorName;
	/**
	 */

	@Column(name = "course_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String courseName;
	/**
	 * �μ���Ա
	 * 
	 */

	@Column(name = "participant")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer participant;
	/**
	 * ��ҪĿ��
	 * 
	 */
	@Column(name = "primary_objective", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String primaryObjective;
	/**
	 * ��ɫ����
	 * 
	 */
	@Column(name = "special_innovation", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String specialInnovation;
	/**
	 * ��������
	 * 
	 */

	
	@Column(name = "project_basis", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String projectBasis;
	/**
	 * ����Ļ�
	 * 
	 */

	
	@Column(name = "construct_basis", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String constructBasis;
	/**
	 * Ԥ�ڳɹ�
	 * 
	 */

	
	@Column(name = "expected_result", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String expectedResult;
	/**
	 * ����Ԥ��
	 * 
	 */

	@Column(name = "appropriation_budget")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer appropriationBudget;
	/**
	 * �豸��ϸ��
	 * 
	 */

	@Column(name = "equipment_detail")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer equipmentDetail;
	/**
	 * ���ɺ��ܿ����ʵ�飨ʵѵ����Ŀ
	 * 
	 */

	@Column(name = "open_lab_item")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer openLabItem;
	/**
	 * ������ϴ�
	 * 
	 */

	@Column(name = "other_appendix")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String otherAppendix;
	/**
	 * ��˸����ϴ�
	 * 
	 */

	@Column(name = "approval_appendix")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String approvalAppendix;
	
	/**
	 * 计划进度
	 * 
	 */

	@Column(name = "plan_schedule", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String planSchedule;
	
	/**
	 */

	@Column(name = "course_amount")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer courseAmount;
	/**
	 */

	@Column(name = "major_amount")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer majorAmount;
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
	@JoinColumns({ @JoinColumn(name = "proposer_id", referencedColumnName = "username") })
	@XmlTransient
	User user;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_purpose_id", referencedColumnName = "id") })
	@XmlTransient
	CProjectPurpose CProjectPurpose;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_source_id", referencedColumnName = "id") })
	@XmlTransient
	CProjectSource CProjectSource;
	/**
	 */
/*	@OneToMany(mappedBy = "labConstructApp", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectAcceptanceApplication> projectAcceptanceApplications;*/
	/**
	 */
	@OneToMany(mappedBy = "labConstructApp", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectStartedReport> projectStartedReports;
	/**
	 */
	@OneToMany(mappedBy = "labConstructApp", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<LabConstructAppApproval> labConstructAppApprovals;
	/**
	 */
	@OneToMany(mappedBy = "labConstructApp", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectDevice> projectDevices;
	/**
	 */
	@OneToMany(mappedBy = "labConstructApp", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ConstructionProject> constructionProjects;
	/**
	 */
	@OneToMany(mappedBy = "labConstructApp", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectCompletionItem> projectCompletionItems;
	/**
	 */
	@OneToMany(mappedBy = "labConstructApp", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<LabConstructUser> labConstructUsers;

	/**
	 */
	@OneToMany(mappedBy = "labConstructApp", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectFeeList> projectFeeLists;

	/**
	 */
	@OneToMany(mappedBy = "labConstructApp", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectAppMajor> projectAppMajors;
	/**
	 */
	@OneToMany(mappedBy = "labConstructApp", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectPurpose> projectPurposes;
	/**
	 */
	@OneToMany(mappedBy = "labConstructApp", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectAppCourse> projectAppCourses;

	/**
	 */
	@OneToMany(mappedBy = "labConstructApp", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<CommonDocument> commonDocuments;
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
	 * ������
	 *
	 */
	public void setLabConstructAppCode(String labConstructAppCode) {
		this.labConstructAppCode = labConstructAppCode;
	}

	/**
	 * ������
	 *
	 */
	public String getLabConstructAppCode() {
		return this.labConstructAppCode;
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
	 * ����ϵ��
	 *
	 */
	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
	}

	/**
	 * ����ϵ��
	 *
	 */
	public Integer getPartyId() {
		return this.partyId;
	}

	/**
	 * �걨ʱ��
	 *
	 */
	public void setAppDate(Calendar appDate) {
		this.appDate = appDate;
	}

	/**
	 * �걨ʱ��
	 *
	 */
	public Calendar getAppDate() {
		return this.appDate;
	}
	/**
	 */
	public void setPurposeName(String purposeName) {
		this.purposeName = purposeName;
	}

	/**
	 */
	public String getPurposeName() {
		return this.purposeName;
	}

	/**
	 */
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	/**
	 */
	public String getMajorName() {
		return this.majorName;
	}

	/**
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 */
	public String getCourseName() {
		return this.courseName;
	}
	/**
	 * �μ���Ա
	 *
	 */
	public void setParticipant(Integer participant) {
		this.participant = participant;
	}

	/**
	 * �μ���Ա
	 *
	 */
	public Integer getParticipant() {
		return this.participant;
	}

	/**
	 * ��ҪĿ��
	 *
	 */
	public void setPrimaryObjective(String primaryObjective) {
		this.primaryObjective = primaryObjective;
	}

	/**
	 * ��ҪĿ��
	 *
	 */
	public String getPrimaryObjective() {
		return this.primaryObjective;
	}

	/**
	 * ��ɫ����
	 *
	 */
	public void setSpecialInnovation(String specialInnovation) {
		this.specialInnovation = specialInnovation;
	}

	/**
	 * ��ɫ����
	 *
	 */
	public String getSpecialInnovation() {
		return this.specialInnovation;
	}

	/**
	 * ��������
	 *
	 */
	public void setProjectBasis(String projectBasis) {
		this.projectBasis = projectBasis;
	}

	/**
	 * ��������
	 *
	 */
	public String getProjectBasis() {
		return this.projectBasis;
	}

	/**
	 * ����Ļ�
	 *
	 */
	public void setConstructBasis(String constructBasis) {
		this.constructBasis = constructBasis;
	}

	/**
	 * ����Ļ�
	 *
	 */
	public String getConstructBasis() {
		return this.constructBasis;
	}

	/**
	 * Ԥ�ڳɹ�
	 *
	 */
	public void setExpectedResult(String expectedResult) {
		this.expectedResult = expectedResult;
	}

	/**
	 * Ԥ�ڳɹ�
	 *
	 */
	public String getExpectedResult() {
		return this.expectedResult;
	}

	/**
	 * ����Ԥ��
	 *
	 */
	public void setAppropriationBudget(Integer appropriationBudget) {
		this.appropriationBudget = appropriationBudget;
	}

	/**
	 * ����Ԥ��
	 *
	 */
	public Integer getAppropriationBudget() {
		return this.appropriationBudget;
	}

	/**
	 * �豸��ϸ��
	 *
	 */
	public void setEquipmentDetail(Integer equipmentDetail) {
		this.equipmentDetail = equipmentDetail;
	}

	/**
	 * �豸��ϸ��
	 *
	 */
	public Integer getEquipmentDetail() {
		return this.equipmentDetail;
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
	 * ������ϴ�
	 *
	 */
	public void setOtherAppendix(String otherAppendix) {
		this.otherAppendix = otherAppendix;
	}

	/**
	 * ������ϴ�
	 *
	 */
	public String getOtherAppendix() {
		return this.otherAppendix;
	}

	/**
	 * ��˸����ϴ�
	 *
	 */
	public void setApprovalAppendix(String approvalAppendix) {
		this.approvalAppendix = approvalAppendix;
	}

	/**
	 * ��˸����ϴ�
	 *
	 */
	public String getApprovalAppendix() {
		return this.approvalAppendix;
	}

	/**
	 * 计划进度
	 *
	 */
	public void setPlanSchedule(String planSchedule) {
		this.planSchedule = planSchedule;
	}

	/**
	 * 计划进度
	 *
	 */
	public String getPlanSchedule() {
		return this.planSchedule;
	}

	/**
	 */
	public void setCourseAmount(Integer courseAmount) {
		this.courseAmount = courseAmount;
	}

	/**
	 */
	public Integer getCourseAmount() {
		return this.courseAmount;
	}

	/**
	 */
	public void setMajorAmount(Integer majorAmount) {
		this.majorAmount = majorAmount;
	}

	/**
	 */
	public Integer getMajorAmount() {
		return this.majorAmount;
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
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 */
	public User getUser() {
		return user;
	}

	/**
	 */
	public void setCProjectPurpose(CProjectPurpose CProjectPurpose) {
		this.CProjectPurpose = CProjectPurpose;
	}

	/**
	 */
	public CProjectPurpose getCProjectPurpose() {
		return CProjectPurpose;
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
/*	public void setProjectAcceptanceApplications(Set<ProjectAcceptanceApplication> projectAcceptanceApplications) {
		this.projectAcceptanceApplications = projectAcceptanceApplications;
	}

	*//**
	 *//*
	public Set<ProjectAcceptanceApplication> getProjectAcceptanceApplications() {
		if (projectAcceptanceApplications == null) {
			projectAcceptanceApplications = new java.util.LinkedHashSet<ProjectAcceptanceApplication>();
		}
		return projectAcceptanceApplications;
	}*/

	/**
	 */
	public void setProjectStartedReports(Set<ProjectStartedReport> projectStartedReports) {
		this.projectStartedReports = projectStartedReports;
	}

	/**
	 */
	public Set<ProjectStartedReport> getProjectStartedReports() {
		if (projectStartedReports == null) {
			projectStartedReports = new java.util.LinkedHashSet<ProjectStartedReport>();
		}
		return projectStartedReports;
	}

	/**
	 */
	public void setLabConstructAppApprovals(Set<LabConstructAppApproval> labConstructAppApprovals) {
		this.labConstructAppApprovals = labConstructAppApprovals;
	}

	/**
	 */
	public Set<LabConstructAppApproval> getLabConstructAppApprovals() {
		if (labConstructAppApprovals == null) {
			labConstructAppApprovals = new java.util.LinkedHashSet<LabConstructAppApproval>();
		}
		return labConstructAppApprovals;
	}

	/**
	 */
	public void setProjectDevices(Set<ProjectDevice> projectDevices) {
		this.projectDevices = projectDevices;
	}

	/**
	 */
	public Set<ProjectDevice> getProjectDevices() {
		if (projectDevices == null) {
			projectDevices = new java.util.LinkedHashSet<ProjectDevice>();
		}
		return projectDevices;
	}

	/**
	 */
	public void setConstructionProjects(Set<ConstructionProject> constructionProjects) {
		this.constructionProjects = constructionProjects;
	}

	/**
	 */
	public Set<ConstructionProject> getConstructionProjects() {
		if (constructionProjects == null) {
			constructionProjects = new java.util.LinkedHashSet<ConstructionProject>();
		}
		return constructionProjects;
	}

	/**
	 */
	public void setProjectCompletionItems(Set<ProjectCompletionItem> projectCompletionItems) {
		this.projectCompletionItems = projectCompletionItems;
	}

	/**
	 */
	public Set<ProjectCompletionItem> getProjectCompletionItems() {
		if (projectCompletionItems == null) {
			projectCompletionItems = new java.util.LinkedHashSet<ProjectCompletionItem>();
		}
		return projectCompletionItems;
	}

	/**
	 */
	public void setLabConstructUsers(Set<LabConstructUser> labConstructUsers) {
		this.labConstructUsers = labConstructUsers;
	}

	/**
	 */
	public Set<LabConstructUser> getLabConstructUsers() {
		if (labConstructUsers == null) {
			labConstructUsers = new java.util.LinkedHashSet<LabConstructUser>();
		}
		return labConstructUsers;
	}

	/**
	 */
	public void setProjectFeeLists(Set<ProjectFeeList> projectFeeLists) {
		this.projectFeeLists = projectFeeLists;
	}

	/**
	 */
	public Set<ProjectFeeList> getProjectFeeLists() {
		if (projectFeeLists == null) {
			projectFeeLists = new java.util.LinkedHashSet<ProjectFeeList>();
		}
		return projectFeeLists;
	}

	/**
	 */
	public void setProjectAppMajors(Set<ProjectAppMajor> projectAppMajors) {
		this.projectAppMajors = projectAppMajors;
	}

	/**
	 */
	public Set<ProjectAppMajor> getProjectAppMajors() {
		if (projectAppMajors == null) {
			projectAppMajors = new java.util.LinkedHashSet<ProjectAppMajor>();
		}
		return projectAppMajors;
	}

	/**
	 */
	public void setProjectPurposes(Set<ProjectPurpose> projectPurposes) {
		this.projectPurposes = projectPurposes;
	}

	/**
	 */
	public Set<ProjectPurpose> getProjectPurposes() {
		if (projectPurposes == null) {
			projectPurposes = new java.util.LinkedHashSet<ProjectPurpose>();
		}
		return projectPurposes;
	}

	/**
	 */
	public void setProjectAppCourses(Set<ProjectAppCourse> projectAppCourses) {
		this.projectAppCourses = projectAppCourses;
	}

	/**
	 */
	public Set<ProjectAppCourse> getProjectAppCourses() {
		if (projectAppCourses == null) {
			projectAppCourses = new java.util.LinkedHashSet<ProjectAppCourse>();
		}
		return projectAppCourses;
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
	public LabConstructApp() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabConstructApp that) {
		setId(that.getId());
		setLabConstructAppCode(that.getLabConstructAppCode());
		setProjectName(that.getProjectName());
		setPartyId(that.getPartyId());
		setAppDate(that.getAppDate());
		setPurposeName(that.getPurposeName());
		setMajorName(that.getMajorName());
		setCourseName(that.getCourseName());
		setParticipant(that.getParticipant());
		setPrimaryObjective(that.getPrimaryObjective());
		setSpecialInnovation(that.getSpecialInnovation());
		setProjectBasis(that.getProjectBasis());
		setConstructBasis(that.getConstructBasis());
		setExpectedResult(that.getExpectedResult());
		setAppropriationBudget(that.getAppropriationBudget());
		setEquipmentDetail(that.getEquipmentDetail());
		setOpenLabItem(that.getOpenLabItem());
		setOtherAppendix(that.getOtherAppendix());
		setApprovalAppendix(that.getApprovalAppendix());
		setPlanSchedule(that.getPlanSchedule());
		setCourseAmount(that.getCourseAmount());
		setMajorAmount(that.getMajorAmount());
		setFeeAmount(that.getFeeAmount());
		setOtherFee(that.getOtherFee());
		setUser(that.getUser());
		setCProjectPurpose(that.getCProjectPurpose());
		setCProjectSource(that.getCProjectSource());
		//setProjectAcceptanceApplications(new java.util.LinkedHashSet<ProjectAcceptanceApplication>(that.getProjectAcceptanceApplications()));
		setProjectStartedReports(new java.util.LinkedHashSet<ProjectStartedReport>(that.getProjectStartedReports()));
		setLabConstructAppApprovals(new java.util.LinkedHashSet<LabConstructAppApproval>(that.getLabConstructAppApprovals()));
		setProjectDevices(new java.util.LinkedHashSet<ProjectDevice>(that.getProjectDevices()));
		setConstructionProjects(new java.util.LinkedHashSet<ConstructionProject>(that.getConstructionProjects()));
		setProjectCompletionItems(new java.util.LinkedHashSet<ProjectCompletionItem>(that.getProjectCompletionItems()));
		setLabConstructUsers(new java.util.LinkedHashSet<LabConstructUser>(that.getLabConstructUsers()));
		setProjectFeeLists(new java.util.LinkedHashSet<ProjectFeeList>(that.getProjectFeeLists()));
		setProjectAppMajors(new java.util.LinkedHashSet<ProjectAppMajor>(that.getProjectAppMajors()));
		setProjectPurposes(new java.util.LinkedHashSet<ProjectPurpose>(that.getProjectPurposes()));
		setProjectAppCourses(new java.util.LinkedHashSet<ProjectAppCourse>(that.getProjectAppCourses()));
		setCommonDocuments(new java.util.LinkedHashSet<CommonDocument>(that.getCommonDocuments()));

	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("labConstructAppCode=[").append(labConstructAppCode).append("] ");
		buffer.append("projectName=[").append(projectName).append("] ");
		buffer.append("partyId=[").append(partyId).append("] ");
		buffer.append("appDate=[").append(appDate).append("] ");
		buffer.append("purposeName=[").append(purposeName).append("] ");
		buffer.append("majorName=[").append(majorName).append("] ");
		buffer.append("courseName=[").append(courseName).append("] ");
		buffer.append("participant=[").append(participant).append("] ");
		buffer.append("primaryObjective=[").append(primaryObjective).append("] ");
		buffer.append("specialInnovation=[").append(specialInnovation).append("] ");
		buffer.append("projectBasis=[").append(projectBasis).append("] ");
		buffer.append("constructBasis=[").append(constructBasis).append("] ");
		buffer.append("expectedResult=[").append(expectedResult).append("] ");
		buffer.append("appropriationBudget=[").append(appropriationBudget).append("] ");
		buffer.append("equipmentDetail=[").append(equipmentDetail).append("] ");
		buffer.append("planSchedule=[").append(planSchedule).append("] ");
		buffer.append("openLabItem=[").append(openLabItem).append("] ");
		buffer.append("otherAppendix=[").append(otherAppendix).append("] ");
		buffer.append("approvalAppendix=[").append(approvalAppendix).append("] ");
		buffer.append("planSchedule=[").append(planSchedule).append("] ");
		buffer.append("courseAmount=[").append(courseAmount).append("] ");
		buffer.append("majorAmount=[").append(majorAmount).append("] ");
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
		if (!(obj instanceof LabConstructApp))
			return false;
		LabConstructApp equalCheck = (LabConstructApp) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
