package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Calendar;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllCommonDocuments", query = "select myCommonDocument from CommonDocument myCommonDocument"),
		@NamedQuery(name = "findCommonDocumentByDocumentName", query = "select myCommonDocument from CommonDocument myCommonDocument where myCommonDocument.documentName = ?1"),
		@NamedQuery(name = "findCommonDocumentByDocumentNameContaining", query = "select myCommonDocument from CommonDocument myCommonDocument where myCommonDocument.documentName like ?1"),
		@NamedQuery(name = "findCommonDocumentByDocumentUrl", query = "select myCommonDocument from CommonDocument myCommonDocument where myCommonDocument.documentUrl = ?1"),
		@NamedQuery(name = "findCommonDocumentByDocumentUrlContaining", query = "select myCommonDocument from CommonDocument myCommonDocument where myCommonDocument.documentUrl like ?1"),
		@NamedQuery(name = "findCommonDocumentById", query = "select myCommonDocument from CommonDocument myCommonDocument where myCommonDocument.id = ?1"),
		@NamedQuery(name = "findCommonDocumentByLabRoom", query = "select myCommonDocument from CommonDocument myCommonDocument where myCommonDocument.labRoom = ?1"),
		@NamedQuery(name = "findCommonDocumentByLabRoomDevice", query = "select myCommonDocument from CommonDocument myCommonDocument where myCommonDocument.labRoomDevice = ?1"),
		@NamedQuery(name = "findCommonDocumentByLabWorkerTraining", query = "select myCommonDocument from CommonDocument myCommonDocument where myCommonDocument.labWorkerTraining = ?1"),
		@NamedQuery(name = "findCommonDocumentByLabRoomDeviceRepair", query = "select myCommonDocument from CommonDocument myCommonDocument where myCommonDocument.labRoomDeviceRepair = ?1"),
		@NamedQuery(name = "findCommonDocumentByPrimaryKey", query = "select myCommonDocument from CommonDocument myCommonDocument where myCommonDocument.id = ?1"),
		@NamedQuery(name = "findCommonDocumentByType", query = "select myCommonDocument from CommonDocument myCommonDocument where myCommonDocument.type = ?1") })
@Table(name = "common_document")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "CommonDocument")
public class CommonDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * �ĵ���
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ���ͣ�1��ʾͼƬ2��ʾ�ĵ���
	 * 
	 */

	@Column(name = "type")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer type;
	/**
	 * �ĵ����
	 * 
	 */

	@Column(name = "documentName")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String documentName;
	/**
	 * �ĵ�����·��
	 * 
	 */

	@Column(name = "documentUrl")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String documentUrl;
	
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room_device", referencedColumnName = "id") })
	@XmlTransient
	LabRoomDevice labRoomDevice;
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_worker_training", referencedColumnName = "id") })
	@XmlTransient
	LabWorkerTraining labWorkerTraining;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "software", referencedColumnName = "id") })
	@XmlTransient
	Software software;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room_device_repair", referencedColumnName = "id") })
	@XmlTransient
	LabRoomDeviceRepair labRoomDeviceRepair;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_annex", referencedColumnName = "id") })
	@XmlTransient
	LabAnnex labAnnex;
	//2015.10.14新增
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_construction_project", referencedColumnName = "id") })
	@XmlTransient
	LabConstructionProject labConstructionProject;
	
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_construction_acceptance", referencedColumnName = "id") })
	@XmlTransient
	LabConstructionAcceptance labConstructionAcceptance;
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "software_reserve", referencedColumnName = "id") })
	@XmlTransient
	SoftwareReserve softwareReserve;
	
	
	/**
	 * ���ͣ�1��ʾͼƬ2��ʾ�ĵ���
	 * 
	 */

	@Column(name = "flag")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer flag;
	
	
	@Column(name = "comments", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String comments;
	
	
	/**
	 * ��Ŀ���
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createdAt;

	@Column(name = "setting_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer settingId;

	public Integer getSettingId() {
		return settingId;
	}

	public void setSettingId(Integer settingId) {
		this.settingId = settingId;
	}
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "created_by", referencedColumnName = "username") })
	@XmlTransient
	User user;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "operation_online", referencedColumnName = "id") })
	@XmlTransient
	OperationOutline operationOutline;
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "item_id", referencedColumnName = "id") })
	@XmlTransient
	OperationItem operationItem;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_start_report_approval", referencedColumnName = "id") })
	@XmlTransient
	ProjectStartedReport projectStartedReportByProjectStartReportApproval;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_construct_app", referencedColumnName = "id") })
	@XmlTransient
	LabConstructApp labConstructApp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_annual_budget", referencedColumnName = "id") })
	@XmlTransient
	ProjectAnnualBudget projectAnnualBudget;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_accept_app_establish", referencedColumnName = "id") })
	@XmlTransient
	ProjectAcceptanceApplication projectAcceptanceApplicationByProjectAcceptAppEstablish;
	/**
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_accept_app_test", referencedColumnName = "id") })
	@XmlTransient
	ProjectAcceptanceApplication projectAcceptanceApplicationByProjectAcceptAppTest;
	/**
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_accept_app_guide", referencedColumnName = "id") })
	@XmlTransient
	ProjectAcceptanceApplication projectAcceptanceApplicationByProjectAcceptAppGuide;
	/**
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_accept_app_item", referencedColumnName = "id") })
	@XmlTransient
	ProjectAcceptanceApplication projectAcceptanceApplicationByProjectAcceptAppItem;
	/**
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_accept_app_summarize", referencedColumnName = "id") })
	@XmlTransient
	ProjectAcceptanceApplication projectAcceptanceApplicationByProjectAcceptAppSummarize;



	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "course_info", referencedColumnName = "course_number") })
	@XmlTransient
	SchoolCourseInfo schoolCourseInfo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "performance", referencedColumnName = "id") })
	@XmlTransient
	IndividualPerformance individualPerformance;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "routine_inspection_id", referencedColumnName = "id") })
	@XmlTransient
	RoutineInspection routineInspection;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "special_examination_id", referencedColumnName = "id") })
	@XmlTransient
	SpecialExamination specialExamination;

	@OneToOne(mappedBy = "commonDocument", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	LabConstructionProjectDocument labConstructionProjectDocument;

	@OneToOne(mappedBy = "itemQuestionDocument", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	OperationItem itemQuestion;

	public RoutineInspection getRoutineInspection() {
		return routineInspection;
	}

	public void setRoutineInspection(RoutineInspection routineInspection) {
		this.routineInspection = routineInspection;
	}

	public SpecialExamination getSpecialExamination() {
		return specialExamination;
	}

	public void setSpecialExamination(SpecialExamination specialExamination) {
		this.specialExamination = specialExamination;
	}




	public SchoolCourseInfo getSchoolCourseInfo() {
		return schoolCourseInfo;
	}

	public void setSchoolCourseInfo(SchoolCourseInfo schoolCourseInfo) {
		this.schoolCourseInfo = schoolCourseInfo;
	}

	public IndividualPerformance getIndividualPerformance() {
		return individualPerformance;
	}

	public void setIndividualPerformance(IndividualPerformance individualPerformance) {
		this.individualPerformance = individualPerformance;
	}
	/**
	 * �ĵ���
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}


	public ProjectAcceptanceApplication getProjectAcceptanceApplicationByProjectAcceptAppTest() {
		return projectAcceptanceApplicationByProjectAcceptAppTest;
	}

	public void setProjectAcceptanceApplicationByProjectAcceptAppTest(ProjectAcceptanceApplication projectAcceptanceApplicationByProjectAcceptAppTest) {
		this.projectAcceptanceApplicationByProjectAcceptAppTest = projectAcceptanceApplicationByProjectAcceptAppTest;
	}

	public ProjectAcceptanceApplication getProjectAcceptanceApplicationByProjectAcceptAppGuide() {
		return projectAcceptanceApplicationByProjectAcceptAppGuide;
	}

	public void setProjectAcceptanceApplicationByProjectAcceptAppGuide(ProjectAcceptanceApplication projectAcceptanceApplicationByProjectAcceptAppGuide) {
		this.projectAcceptanceApplicationByProjectAcceptAppGuide = projectAcceptanceApplicationByProjectAcceptAppGuide;
	}

	public ProjectAcceptanceApplication getProjectAcceptanceApplicationByProjectAcceptAppSummarize() {
		return projectAcceptanceApplicationByProjectAcceptAppSummarize;
	}

	public void setProjectAcceptanceApplicationByProjectAcceptAppSummarize(ProjectAcceptanceApplication projectAcceptanceApplicationByProjectAcceptAppSummarize) {
		this.projectAcceptanceApplicationByProjectAcceptAppSummarize = projectAcceptanceApplicationByProjectAcceptAppSummarize;
	}

	/**
	 * �ĵ���
	 * 
	 */

	/**
	 * ���ͣ�1��ʾͼƬ2��ʾ�ĵ���
	 * 
	 */
	public OperationOutline getOperationOutline() {
		return operationOutline;
	}

	public void setOperationOutline(OperationOutline operationOutline) {
		this.operationOutline = operationOutline;
	}
	
	/**
	 * ���ͣ�1��ʾͼƬ2��ʾ�ĵ���
	 * 
	 */
	public OperationItem getOperationItem() {
		return operationItem;
	}

	public void setOperationItem(OperationItem operationItem) {
		this.operationItem = operationItem;
	}

	/**
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * ���ͣ�1��ʾͼƬ2��ʾ�ĵ���
	 * 
	 */
	public Integer getType() {
		return this.type;
	}

	/**
	 * �ĵ����
	 * 
	 */
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	/**
	 * �ĵ����
	 * 
	 */
	public String getDocumentName() {
		return this.documentName;
	}

	/**
	 * �ĵ�����·��
	 * 
	 */
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}

	/**
	 * �ĵ�����·��
	 * 
	 */
	public String getDocumentUrl() {
		return this.documentUrl;
	}

	/**
	 */
	public void setLabRoom(LabRoom labRoom) {
		this.labRoom = labRoom;
	}

	/**
	 */
	@JsonIgnore
	public LabRoom getLabRoom() {
		return labRoom;
	}
	
	
	/**
	 */
	public void setLabRoomDevice(LabRoomDevice labRoomDevice) {
		this.labRoomDevice = labRoomDevice;
	}

	/**
	 */
	@JsonIgnore
	public LabRoomDevice getLabRoomDevice() {
		return labRoomDevice;
	}
	
	/**
	 */
	public void setLabWorkerTraining(LabWorkerTraining labWorkerTraining) {
		this.labWorkerTraining = labWorkerTraining;
	}

	/**
	 */
	@JsonIgnore
	public LabWorkerTraining getLabWorkerTraining() {
		return labWorkerTraining;
	}
	/**
	 */
	public void setSoftware(Software software) {
		this.software = software;
	}

	/**
	 */
	@JsonIgnore
	public Software getSoftware() {
		return software;
	}
	/**
	 */
	public void setLabRoomDeviceRepair(LabRoomDeviceRepair labRoomDeviceRepair) {
		this.labRoomDeviceRepair = labRoomDeviceRepair;
	}

	/**
	 */
	@JsonIgnore
	public LabRoomDeviceRepair getLabRoomDeviceRepair() {
		return labRoomDeviceRepair;
	}
	/**
	 */
	public void setLabAnnex(LabAnnex labAnnex) {
		this.labAnnex = labAnnex;
	}

	/**
	 */
	@JsonIgnore
	public LabAnnex getLabAnnex() {
		return labAnnex;
	}
	/**
	 */
	@JsonIgnore
	public LabConstructionProject getLabConstructionProject() {
		return labConstructionProject;
	}

	public void setLabConstructionProject(LabConstructionProject labConstructionProject) {
		this.labConstructionProject = labConstructionProject;
	}

	/**
	 */
	@JsonIgnore
	public LabConstructionAcceptance getLabConstructionAcceptance() {
		return labConstructionAcceptance;
	}

	public void setLabConstructionAcceptance(LabConstructionAcceptance labConstructionAcceptance) {
		this.labConstructionAcceptance = labConstructionAcceptance;
	}
	
	
	
	/**
	 */
	@JsonIgnore
	public SoftwareReserve getSoftwareReserve() {
		return softwareReserve;
	}

	public void setSoftwareReserve(SoftwareReserve softwareReserve) {
		this.softwareReserve = softwareReserve;
	}

	public ProjectAnnualBudget getProjectAnnualBudget() {
		return projectAnnualBudget;
	}

	public void setProjectAnnualBudget(ProjectAnnualBudget projectAnnualBudget) {
		this.projectAnnualBudget = projectAnnualBudget;
	}
	
	
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Calendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setProjectStartedReportByProjectStartReportApproval(ProjectStartedReport projectStartedReportByProjectStartReportApproval) {
		this.projectStartedReportByProjectStartReportApproval = projectStartedReportByProjectStartReportApproval;
	}

	/**
	 */
	public ProjectStartedReport getProjectStartedReportByProjectStartReportApproval() {
		return projectStartedReportByProjectStartReportApproval;
	}

	/**
	 */
	public void setProjectAcceptanceApplicationByProjectAcceptAppEstablish(ProjectAcceptanceApplication projectAcceptanceApplicationByProjectAcceptAppEstablish) {
		this.projectAcceptanceApplicationByProjectAcceptAppEstablish = projectAcceptanceApplicationByProjectAcceptAppEstablish;
	}

	/**
	 */
	public ProjectAcceptanceApplication getProjectAcceptanceApplicationByProjectAcceptAppEstablish() {
		return projectAcceptanceApplicationByProjectAcceptAppEstablish;
	}

	public LabConstructApp getLabConstructApp() {
		return labConstructApp;
	}

	public void setLabConstructApp(LabConstructApp labConstructApp) {
		this.labConstructApp = labConstructApp;
	}

	public void setProjectAcceptanceApplicationByProjectAcceptAppItem(ProjectAcceptanceApplication projectAcceptanceApplicationByProjectAcceptAppItem) {
		this.projectAcceptanceApplicationByProjectAcceptAppItem = projectAcceptanceApplicationByProjectAcceptAppItem;
	}

	/**
	 */
	public ProjectAcceptanceApplication getProjectAcceptanceApplicationByProjectAcceptAppItem() {
		return projectAcceptanceApplicationByProjectAcceptAppItem;
	}

	public LabConstructionProjectDocument getLabConstructionProjectDocument() {
		return labConstructionProjectDocument;
	}

	public void setLabConstructionProjectDocument(LabConstructionProjectDocument labConstructionProjectDocument) {
		this.labConstructionProjectDocument = labConstructionProjectDocument;
	}

	public OperationItem getItemQuestion() {
		return itemQuestion;
	}

	public void setItemQuestion(OperationItem itemQuestion) {
		this.itemQuestion = itemQuestion;
	}

	/**
	 */
	public CommonDocument() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.ProjectAcceptCompletionItem
	 *
	 */
	public void copy(CommonDocument that) {
		setId(that.getId());
		setType(that.getType());
		setDocumentName(that.getDocumentName());
		setDocumentUrl(that.getDocumentUrl());
		setLabRoom(that.getLabRoom());
		setLabRoomDevice(that.getLabRoomDevice());
		setLabConstructionProject(that.getLabConstructionProject());
		setLabConstructionAcceptance(that.getLabConstructionAcceptance());
		setComments(that.getComments());
		setCreatedAt(that.getCreatedAt());
		setUser(that.getUser());
		setFlag(that.getFlag());
		setLabWorkerTraining(that.getLabWorkerTraining());
		setSoftware(that.getSoftware());
		setLabRoomDeviceRepair(that.getLabRoomDeviceRepair());

		setLabConstructionProjectDocument(that.getLabConstructionProjectDocument());
		setItemQuestion(that.getItemQuestion());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("type=[").append(type).append("] ");
		buffer.append("documentName=[").append(documentName).append("] ");
		buffer.append("documentUrl=[").append(documentUrl).append("] ");
		buffer.append("labRoom=[").append(labRoom).append("] ");
		buffer.append("labRoomDevice=[").append(labRoomDevice).append("] ");
		buffer.append("labWorkerTraining=[").append(labWorkerTraining).append("] ");
		buffer.append("software=[").append(software).append("] ");
		buffer.append("labRoomDeviceRepair=[").append(labRoomDeviceRepair).append("] ");
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
		if (!(obj instanceof CommonDocument))
			return false;
		CommonDocument equalCheck = (CommonDocument) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
