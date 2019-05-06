package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Set;


/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllCDictionarys", query = "select myCDictionary from CDictionary myCDictionary"),
		@NamedQuery(name = "findCDictionaryByCCategory", query = "select myCDictionary from CDictionary myCDictionary where myCDictionary.CCategory = ?1"),
		@NamedQuery(name = "findCDictionaryByCCategoryContaining", query = "select myCDictionary from CDictionary myCDictionary where myCDictionary.CCategory like ?1"),
		@NamedQuery(name = "findCDictionaryByCName", query = "select myCDictionary from CDictionary myCDictionary where myCDictionary.CName = ?1"),
		@NamedQuery(name = "findCDictionaryByCNameContaining", query = "select myCDictionary from CDictionary myCDictionary where myCDictionary.CName like ?1"),
		@NamedQuery(name = "findCDictionaryByCNumber", query = "select myCDictionary from CDictionary myCDictionary where myCDictionary.CNumber = ?1"),
		@NamedQuery(name = "findCDictionaryByCNumberContaining", query = "select myCDictionary from CDictionary myCDictionary where myCDictionary.CNumber like ?1"),
		@NamedQuery(name = "findCDictionaryByEnabled", query = "select myCDictionary from CDictionary myCDictionary where myCDictionary.enabled = ?1"),
		@NamedQuery(name = "findCDictionaryById", query = "select myCDictionary from CDictionary myCDictionary where myCDictionary.id = ?1"),
		@NamedQuery(name = "findCDictionaryByPrimaryKey", query = "select myCDictionary from CDictionary myCDictionary where myCDictionary.id = ?1") })
@Table(name = "c_dictionary")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "CDictionary")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class CDictionary implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	Integer id;
	/**
	 * ���
	 * 
	 */

	@Column(name = "c_number", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String CNumber;
	/**
	 * ��������
	 * 
	 */

	@Column(name = "c_category", length = 100)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String CCategory;
	/**
	 * ���
	 * 
	 */

	@Column(name = "c_name", length = 100)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String CName;
	/**
	 */

	@Column(name = "enabled")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Boolean enabled;

	/**
	 */
	@OneToMany(mappedBy = "CTrainingStatus", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceTraining> labRoomDeviceTrainings;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByCostReason", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.NDevicePurchase> NDevicePurchasesForCostType;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByCostReason", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.NDevicePurchase> NDevicePurchasesForCostReason;
	

	/**
	 */
	@OneToMany(mappedBy = "CDictionary", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomAgent> labRoomAgents;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByTeacherAudit", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDevice> labRoomDevicesForManagerAudit;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByCourseStatus", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolCourse> schoolCoursesForCourseStatus;
	
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByTeacherAudit", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDevice> labRoomDevicesForTrainType;

	/**
	 */
	@OneToMany(mappedBy = "CDictionary", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceTrainingPeople> labRoomDeviceTrainingPeoples;

	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByStudentType", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolCourse> schoolCoursesForStudentType;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByTeacherAudit", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDevice> labRoomDevicesForAllowLending;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByTeacherAudit", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDevice> labRoomDevicesForAppointmentType;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByTeacherAudit", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDevice> labRoomDevicesForAllowSecurityAccess;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByTeacherAudit", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDevice> labRoomDevicesForSecurityAccessType;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByTeacherAudit", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDevice> labRoomDevicesForDeviceType;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByTeacherAudit", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDevice> labRoomDevicesForDeviceStatus;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByTeacherAudit", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDevice> labRoomDevicesForDeviceCharge;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByTeacherAudit", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDevice> labRoomDevicesForTeacherAudit;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLabManagerAudit", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoom> labRoomsForLabRoomSort;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLabManagerAudit", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoom> labRoomsForLabRoomClassification;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLabManagerAudit", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoom> labRoomsForIsMultimedia;
	/**
	 */
	/**
	 *
	 */
	@OneToMany(mappedBy = "CDictionaryByLabManagerAudit", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoom> labRoomDForLabManagerAudit;
	
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByObject", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableAppointment> timetableAppointmentsForApplication;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByObject", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableAppointment> timetableAppointmentsForObject;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByObject", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableAppointment> timetableAppointmentsForTidySituation;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByObject", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableAppointment> timetableAppointmentsForEquipmentSituation;
	

	public void setLabRoomDForLabManagerAudit(
			java.util.Set<net.zjcclims.domain.LabRoom> labRoomDForLabManagerAudit) {
		this.labRoomDForLabManagerAudit = labRoomDForLabManagerAudit;
	}
	@JsonIgnore
	public Set<LabRoom> getLabRoomDForLabManagerAudit() {
		if (labRoomDForLabManagerAudit == null) {
			labRoomDForLabManagerAudit = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoom>();
		}
		return labRoomDForLabManagerAudit;
	}
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLpCategoryMain", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItemsForLpStatusCheck;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByTeacherAudit", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDevice> labRoomDevicesForAllowAppointment;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLpCategoryMain", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItemsForLpCategoryPublic;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLpCategoryMain", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItemsForLpStatusChange;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLwMainWork", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorker> labWorkersForLwCategoryStaff;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLpCategoryMain", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItemsForLpCategoryRewardLevel;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLwMainWork", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorker> labWorkersForLwForeignLanguageLevel;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLwMainWork", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorker> labWorkersForLwDegree;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLpCategoryMain", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItemsForLpCategoryApp;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLwMainWork", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorker> labWorkersForLwBookLevel;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLpCategoryMain", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItemsForLpCategoryGuideBook;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLpCategoryMain", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItemsForLpCategoryStudent;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLpCategoryMain", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItemsForLpCategoryNature;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLwMainWork", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorker> labWorkersForLwMainWork;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLwMainWork", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorker> labWorkersForLwPaperLevel;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLwMainWork", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorker> labWorkersForLwSpecialtyDuty;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByTeacherAudit", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDevice> labRoomDevicesForIsAudit;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLwMainWork", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorker> labWorkersForLwSubject;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLpCategoryMain", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItemsForLpCategoryRequire;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLwMainWork", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorker> labWorkersForLwForeignLanguage;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLwMainWork", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorker> labWorkersForLwAcademicDegree;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLwMainWork", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorker> labWorkersForLwReward;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLwMainWork", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorker> labWorkersForLwEmployment;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLpCategoryMain", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItemsForLpCategoryMain;
	
	@OneToMany(mappedBy = "CDictionary", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItemMaterialRecord> operationItemMaterialRecords;
	
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLabRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoom> labRoomsForLabRoom;
	
	
	@OneToMany(mappedBy = "CDictionary", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabReservationTimeTable> labReservationTimeTables;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByActivityCategory", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabReservation> labReservationsForActivityCategory;
	/**
	 */
	@OneToMany(mappedBy = "CDictionary", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabReservationAudit> labReservationAudits;
	
	@OneToMany(mappedBy = "CDictionaryByActivityCategory", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabReservation> labReservationsForLabReservetYpe;
	
	/**
	 */
	@OneToMany(mappedBy = "CDictionary", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomLendingResult> labRoomLendingResults;
	
	//新增
	
	/**
	 */
	@OneToMany(mappedBy = "CDictionary", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabConstructionProjectAudit> labConstructionProjectAudits;
	/**
	 */
	@OneToMany(mappedBy = "CDictionary", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabConstructionPurchaseAudit> labConstructionPurchaseAudits;
	/**
	 */
	@OneToMany(mappedBy = "CDictionary", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabConstructionPurchase> labConstructionPurchases;
	/**
	 */
	@OneToMany(mappedBy = "CDictionary", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabConstructionFundingAudit> labConstructionFundingAudits;


	/**
	 */
	@OneToMany(mappedBy = "CDictionary", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomStationReservation> LabRoomStationReservations;
	

	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLabAnnex", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabAnnex> labAnnexesForLabAnnex;
	/**
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "operation_outline_ccourse_property", joinColumns = { @JoinColumn(name = "c_outline_property_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "operation_outline_id", referencedColumnName = "id", nullable = false, updatable = false) })
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationOutline> operationOutlines;


	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByStatusId", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceRepair> labRoomDeviceRepairsForStatusId;
	
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByFailureChoice", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceRepair> labRoomDeviceRepairsForFailureChoice;
	
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByPartitionId", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceRepair> labRoomDeviceRepairsForPartitionId;
	
	/**
	 */
	@OneToMany(mappedBy = "CDictionary", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceLendingResult> labRoomDeviceLendingResults;
	
	/**
	 */
	@OneToMany(mappedBy = "CDictionary", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceLending> labRoomDeviceLendings;
	
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByLwMainWork", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorker> labWorkersForLwCategoryExpert;
	
	/**
	 */
	@OneToMany(mappedBy = "CDictionary", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorkerTraining> labWorkerTrainings;
	
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByDeviceFlag", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolDevice> schoolDevicesForDeviceFlag;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByDeviceFlag", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolDevice> schoolDevicesForDeviceSource;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByDeviceFlag", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolDevice> schoolDevicesForUseStatus;
	
	/**
	 * Description 实训室借用-借用类型
	 */
	@OneToMany(mappedBy = "CDictionaryByLendingType", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomLending> labRoomLendingType;
	
	/**
	 * Description 实训室借用-借用类型
	 */
	@OneToMany(mappedBy = "CDictionaryByLendingType", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabReservation> labLendingType;
	
	
	public void setLabRoomLendingType(Set<LabRoomLending> labRoomLendingType) {
		this.labRoomLendingType = labRoomLendingType;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomLending> getLabRoomLendingType() {
		if (labRoomLendingType == null) {
			labRoomLendingType = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomLending>();
		}
		return labRoomLendingType;
	}
	/**
	 * Description 实训室借用-使用人类型
	 */
	@OneToMany(mappedBy = "CDictionaryByLendingUserType", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomLending> lendingUserType;
	
	/**
	 * Description 实训室借用-使用人类型
	 */
	@OneToMany(mappedBy = "CDictionaryByLendingUserType", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabReservation> labLendingUserType;
	
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByObject", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabroomTimetableRegister> labroomTimetableRegistersForEquipmentSituation;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByObject", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabroomTimetableRegister> labroomTimetableRegistersForApplication;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByObject", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabroomTimetableRegister> labroomTimetableRegistersForObject;
	/**
	 */
	@OneToMany(mappedBy = "CDictionaryByObject", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabroomTimetableRegister> labroomTimetableRegistersForTidySituation;
	
	
	public void setLendingUserType(Set<LabRoomLending> lendingUserType) {
		this.lendingUserType = lendingUserType;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomLending> getLendingUserType() {
		if (lendingUserType == null) {
			lendingUserType = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomLending>();
		}
		return lendingUserType;
	}
	
	/**
	 */
	public void setSchoolDevicesForDeviceFlag(Set<SchoolDevice> schoolDevicesForDeviceFlag) {
		this.schoolDevicesForDeviceFlag = schoolDevicesForDeviceFlag;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolDevice> getSchoolDevicesForDeviceFlag() {
		if (schoolDevicesForDeviceFlag == null) {
			schoolDevicesForDeviceFlag = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolDevice>();
		}
		return schoolDevicesForDeviceFlag;
	}

	/**
	 */
	public void setSchoolDevicesForDeviceSource(Set<SchoolDevice> schoolDevicesForDeviceSource) {
		this.schoolDevicesForDeviceSource = schoolDevicesForDeviceSource;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolDevice> getSchoolDevicesForDeviceSource() {
		if (schoolDevicesForDeviceSource == null) {
			schoolDevicesForDeviceSource = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolDevice>();
		}
		return schoolDevicesForDeviceSource;
	}

	/**
	 */
	public void setSchoolDevicesForUseStatus(Set<SchoolDevice> schoolDevicesForUseStatus) {
		this.schoolDevicesForUseStatus = schoolDevicesForUseStatus;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolDevice> getSchoolDevicesForUseStatus() {
		if (schoolDevicesForUseStatus == null) {
			schoolDevicesForUseStatus = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolDevice>();
		}
		return schoolDevicesForUseStatus;
	}
	/**
	 */
	public void setLabWorkerTrainings(Set<LabWorkerTraining> labWorkerTrainings) {
		this.labWorkerTrainings = labWorkerTrainings;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabWorkerTraining> getLabWorkerTrainings() {
		if (labWorkerTrainings == null) {
			labWorkerTrainings = new java.util.LinkedHashSet<net.zjcclims.domain.LabWorkerTraining>();
		}
		return labWorkerTrainings;
	}
	/**
	 */
	
	public void setLabWorkersForLwCategoryExpert(Set<LabWorker> labWorkersForLwCategoryExpert) {
		this.labWorkersForLwCategoryExpert = labWorkersForLwCategoryExpert;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabWorker> getLabWorkersForLwCategoryExpert() {
		if (labWorkersForLwCategoryExpert == null) {
			labWorkersForLwCategoryExpert = new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>();
		}
		return labWorkersForLwCategoryExpert;
	}

	@JsonIgnore
	public java.util.Set<net.zjcclims.domain.LabReservationTimeTable> getLabReservationTimeTables() {
		return labReservationTimeTables;
	}

	public void setLabReservationTimeTables(
			java.util.Set<net.zjcclims.domain.LabReservationTimeTable> labReservationTimeTables) {
		this.labReservationTimeTables = labReservationTimeTables;
	}
	
	/**
	 */
	public void setLabRoomsForLabRoom(Set<LabRoom> labRoomsForLabRoom) {
		this.labRoomsForLabRoom = labRoomsForLabRoom;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoom> getLabRoomsForLabRoom() {
		if (labRoomsForLabRoom == null) {
			labRoomsForLabRoom = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoom>();
		}
		return labRoomsForLabRoom;
	}

	@JsonIgnore
	public java.util.Set<net.zjcclims.domain.LabReservation> getLabReservationsForActivityCategory() {
		return labReservationsForActivityCategory;
	}

	public void setLabReservationsForActivityCategory(
			java.util.Set<net.zjcclims.domain.LabReservation> labReservationsForActivityCategory) {
		this.labReservationsForActivityCategory = labReservationsForActivityCategory;
	}

	@JsonIgnore
	public java.util.Set<net.zjcclims.domain.LabReservationAudit> getLabReservationAudits() {
		return labReservationAudits;
	}

	public void setLabReservationAudits(
			java.util.Set<net.zjcclims.domain.LabReservationAudit> labReservationAudits) {
		this.labReservationAudits = labReservationAudits;
	}

	@JsonIgnore
	public java.util.Set<net.zjcclims.domain.LabReservation> getLabReservationsForLabReservetYpe() {
		return labReservationsForLabReservetYpe;
	}

	public void setLabReservationsForLabReservetYpe(
			java.util.Set<net.zjcclims.domain.LabReservation> labReservationsForLabReservetYpe) {
		this.labReservationsForLabReservetYpe = labReservationsForLabReservetYpe;
	}

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
	 * ���
	 * 
	 */
	public void setCNumber(String CNumber) {
		this.CNumber = CNumber;
	}

	/**
	 * ���
	 * 
	 */
	public String getCNumber() {
		return this.CNumber;
	}

	/**
	 * ��������
	 * 
	 */
	public void setCCategory(String CCategory) {
		this.CCategory = CCategory;
	}

	/**
	 * ��������
	 * 
	 */
	public String getCCategory() {
		return this.CCategory;
	}

	/**
	 * ���
	 * 
	 */
	public void setCName(String CName) {
		this.CName = CName;
	}

	public void setLabRoomLendingResults(Set<LabRoomLendingResult> labRoomLendingResults) {
		this.labRoomLendingResults = labRoomLendingResults;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomLendingResult> getLabRoomLendingResults() {
		if (labRoomLendingResults == null) {
			labRoomLendingResults = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomLendingResult>();
		}
		return labRoomLendingResults;
	}
	
	/**
	 * ���
	 * 
	 */
	public String getCName() {
		return this.CName;
	}

	/**
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 */
	public Boolean getEnabled() {
		return this.enabled;
	}

	/**
	 */
	public void setLabRoomAgents(Set<LabRoomAgent> labRoomAgents) {
		this.labRoomAgents = labRoomAgents;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomAgent> getLabRoomAgents() {
		if (labRoomAgents == null) {
			labRoomAgents = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomAgent>();
		}
		return labRoomAgents;
	}

	/**
	 */
	public void setLabRoomDevicesForManagerAudit(Set<LabRoomDevice> labRoomDevicesForManagerAudit) {
		this.labRoomDevicesForManagerAudit = labRoomDevicesForManagerAudit;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDevice> getLabRoomDevicesForManagerAudit() {
		if (labRoomDevicesForManagerAudit == null) {
			labRoomDevicesForManagerAudit = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>();
		}
		return labRoomDevicesForManagerAudit;
	}

	/**
	 */
	public void setSchoolCoursesForCourseStatus(Set<SchoolCourse> schoolCoursesForCourseStatus) {
		this.schoolCoursesForCourseStatus = schoolCoursesForCourseStatus;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolCourse> getSchoolCoursesForCourseStatus() {
		if (schoolCoursesForCourseStatus == null) {
			schoolCoursesForCourseStatus = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourse>();
		}
		return schoolCoursesForCourseStatus;
	}

	/**
	 */
	public void setSchoolCoursesForStudentType(Set<SchoolCourse> schoolCoursesForStudentType) {
		this.schoolCoursesForStudentType = schoolCoursesForStudentType;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolCourse> getSchoolCoursesForStudentType() {
		if (schoolCoursesForStudentType == null) {
			schoolCoursesForStudentType = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourse>();
		}
		return schoolCoursesForStudentType;
	}

	/**
	 */
	public void setLabRoomDevicesForAllowLending(Set<LabRoomDevice> labRoomDevicesForAllowLending) {
		this.labRoomDevicesForAllowLending = labRoomDevicesForAllowLending;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDevice> getLabRoomDevicesForAllowLending() {
		if (labRoomDevicesForAllowLending == null) {
			labRoomDevicesForAllowLending = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>();
		}
		return labRoomDevicesForAllowLending;
	}

	/**
	 */
	public void setLabRoomDevicesForAppointmentType(Set<LabRoomDevice> labRoomDevicesForAppointmentType) {
		this.labRoomDevicesForAppointmentType = labRoomDevicesForAppointmentType;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDevice> getLabRoomDevicesForAppointmentType() {
		if (labRoomDevicesForAppointmentType == null) {
			labRoomDevicesForAppointmentType = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>();
		}
		return labRoomDevicesForAppointmentType;
	}

	/**
	 */
	public void setLabRoomDevicesForAllowSecurityAccess(Set<LabRoomDevice> labRoomDevicesForAllowSecurityAccess) {
		this.labRoomDevicesForAllowSecurityAccess = labRoomDevicesForAllowSecurityAccess;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDevice> getLabRoomDevicesForAllowSecurityAccess() {
		if (labRoomDevicesForAllowSecurityAccess == null) {
			labRoomDevicesForAllowSecurityAccess = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>();
		}
		return labRoomDevicesForAllowSecurityAccess;
	}

	/**
	 */
	public void setLabRoomDevicesForSecurityAccessType(Set<LabRoomDevice> labRoomDevicesForSecurityAccessType) {
		this.labRoomDevicesForSecurityAccessType = labRoomDevicesForSecurityAccessType;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDevice> getLabRoomDevicesForSecurityAccessType() {
		if (labRoomDevicesForSecurityAccessType == null) {
			labRoomDevicesForSecurityAccessType = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>();
		}
		return labRoomDevicesForSecurityAccessType;
	}

	/**
	 */
	public void setLabRoomDevicesForDeviceType(Set<LabRoomDevice> labRoomDevicesForDeviceType) {
		this.labRoomDevicesForDeviceType = labRoomDevicesForDeviceType;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDevice> getLabRoomDevicesForDeviceType() {
		if (labRoomDevicesForDeviceType == null) {
			labRoomDevicesForDeviceType = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>();
		}
		return labRoomDevicesForDeviceType;
	}

	/**
	 */
	public void setLabRoomDevicesForDeviceStatus(Set<LabRoomDevice> labRoomDevicesForDeviceStatus) {
		this.labRoomDevicesForDeviceStatus = labRoomDevicesForDeviceStatus;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDevice> getLabRoomDevicesForDeviceStatus() {
		if (labRoomDevicesForDeviceStatus == null) {
			labRoomDevicesForDeviceStatus = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>();
		}
		return labRoomDevicesForDeviceStatus;
	}

	/**
	 */
	public void setLabRoomDevicesForDeviceCharge(Set<LabRoomDevice> labRoomDevicesForDeviceCharge) {
		this.labRoomDevicesForDeviceCharge = labRoomDevicesForDeviceCharge;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDevice> getLabRoomDevicesForDeviceCharge() {
		if (labRoomDevicesForDeviceCharge == null) {
			labRoomDevicesForDeviceCharge = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>();
		}
		return labRoomDevicesForDeviceCharge;
	}

	/**
	 */
	public void setLabRoomDevicesForTeacherAudit(Set<LabRoomDevice> labRoomDevicesForTeacherAudit) {
		this.labRoomDevicesForTeacherAudit = labRoomDevicesForTeacherAudit;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDevice> getLabRoomDevicesForTeacherAudit() {
		if (labRoomDevicesForTeacherAudit == null) {
			labRoomDevicesForTeacherAudit = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>();
		}
		return labRoomDevicesForTeacherAudit;
	}

	/**
	 */
	public void setOperationItemsForLpStatusCheck(Set<OperationItem> operationItemsForLpStatusCheck) {
		this.operationItemsForLpStatusCheck = operationItemsForLpStatusCheck;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationItem> getOperationItemsForLpStatusCheck() {
		if (operationItemsForLpStatusCheck == null) {
			operationItemsForLpStatusCheck = new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>();
		}
		return operationItemsForLpStatusCheck;
	}

	/**
	 */
	public void setLabRoomDevicesForAllowAppointment(Set<LabRoomDevice> labRoomDevicesForAllowAppointment) {
		this.labRoomDevicesForAllowAppointment = labRoomDevicesForAllowAppointment;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDevice> getLabRoomDevicesForAllowAppointment() {
		if (labRoomDevicesForAllowAppointment == null) {
			labRoomDevicesForAllowAppointment = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>();
		}
		return labRoomDevicesForAllowAppointment;
	}

	/**
	 */
	public void setOperationItemsForLpCategoryPublic(Set<OperationItem> operationItemsForLpCategoryPublic) {
		this.operationItemsForLpCategoryPublic = operationItemsForLpCategoryPublic;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationItem> getOperationItemsForLpCategoryPublic() {
		if (operationItemsForLpCategoryPublic == null) {
			operationItemsForLpCategoryPublic = new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>();
		}
		return operationItemsForLpCategoryPublic;
	}

	/**
	 */
	public void setOperationItemsForLpStatusChange(Set<OperationItem> operationItemsForLpStatusChange) {
		this.operationItemsForLpStatusChange = operationItemsForLpStatusChange;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationItem> getOperationItemsForLpStatusChange() {
		if (operationItemsForLpStatusChange == null) {
			operationItemsForLpStatusChange = new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>();
		}
		return operationItemsForLpStatusChange;
	}

	/**
	 */
	public void setLabWorkersForLwCategoryStaff(Set<LabWorker> labWorkersForLwCategoryStaff) {
		this.labWorkersForLwCategoryStaff = labWorkersForLwCategoryStaff;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabWorker> getLabWorkersForLwCategoryStaff() {
		if (labWorkersForLwCategoryStaff == null) {
			labWorkersForLwCategoryStaff = new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>();
		}
		return labWorkersForLwCategoryStaff;
	}

	/**
	 */
	public void setOperationItemsForLpCategoryRewardLevel(Set<OperationItem> operationItemsForLpCategoryRewardLevel) {
		this.operationItemsForLpCategoryRewardLevel = operationItemsForLpCategoryRewardLevel;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationItem> getOperationItemsForLpCategoryRewardLevel() {
		if (operationItemsForLpCategoryRewardLevel == null) {
			operationItemsForLpCategoryRewardLevel = new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>();
		}
		return operationItemsForLpCategoryRewardLevel;
	}

	/**
	 */
	public void setLabWorkersForLwForeignLanguageLevel(Set<LabWorker> labWorkersForLwForeignLanguageLevel) {
		this.labWorkersForLwForeignLanguageLevel = labWorkersForLwForeignLanguageLevel;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabWorker> getLabWorkersForLwForeignLanguageLevel() {
		if (labWorkersForLwForeignLanguageLevel == null) {
			labWorkersForLwForeignLanguageLevel = new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>();
		}
		return labWorkersForLwForeignLanguageLevel;
	}

	/**
	 */
	public void setLabWorkersForLwDegree(Set<LabWorker> labWorkersForLwDegree) {
		this.labWorkersForLwDegree = labWorkersForLwDegree;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabWorker> getLabWorkersForLwDegree() {
		if (labWorkersForLwDegree == null) {
			labWorkersForLwDegree = new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>();
		}
		return labWorkersForLwDegree;
	}

	/**
	 */
	public void setOperationItemsForLpCategoryApp(Set<OperationItem> operationItemsForLpCategoryApp) {
		this.operationItemsForLpCategoryApp = operationItemsForLpCategoryApp;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationItem> getOperationItemsForLpCategoryApp() {
		if (operationItemsForLpCategoryApp == null) {
			operationItemsForLpCategoryApp = new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>();
		}
		return operationItemsForLpCategoryApp;
	}

	/**
	 */
	public void setLabWorkersForLwBookLevel(Set<LabWorker> labWorkersForLwBookLevel) {
		this.labWorkersForLwBookLevel = labWorkersForLwBookLevel;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabWorker> getLabWorkersForLwBookLevel() {
		if (labWorkersForLwBookLevel == null) {
			labWorkersForLwBookLevel = new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>();
		}
		return labWorkersForLwBookLevel;
	}

	/**
	 */
	public void setOperationItemsForLpCategoryGuideBook(Set<OperationItem> operationItemsForLpCategoryGuideBook) {
		this.operationItemsForLpCategoryGuideBook = operationItemsForLpCategoryGuideBook;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationItem> getOperationItemsForLpCategoryGuideBook() {
		if (operationItemsForLpCategoryGuideBook == null) {
			operationItemsForLpCategoryGuideBook = new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>();
		}
		return operationItemsForLpCategoryGuideBook;
	}

	/**
	 */
	public void setOperationItemsForLpCategoryStudent(Set<OperationItem> operationItemsForLpCategoryStudent) {
		this.operationItemsForLpCategoryStudent = operationItemsForLpCategoryStudent;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationItem> getOperationItemsForLpCategoryStudent() {
		if (operationItemsForLpCategoryStudent == null) {
			operationItemsForLpCategoryStudent = new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>();
		}
		return operationItemsForLpCategoryStudent;
	}

	/**
	 */
	public void setOperationItemsForLpCategoryNature(Set<OperationItem> operationItemsForLpCategoryNature) {
		this.operationItemsForLpCategoryNature = operationItemsForLpCategoryNature;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationItem> getOperationItemsForLpCategoryNature() {
		if (operationItemsForLpCategoryNature == null) {
			operationItemsForLpCategoryNature = new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>();
		}
		return operationItemsForLpCategoryNature;
	}

	/**
	 */
	public void setLabWorkersForLwMainWork(Set<LabWorker> labWorkersForLwMainWork) {
		this.labWorkersForLwMainWork = labWorkersForLwMainWork;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabWorker> getLabWorkersForLwMainWork() {
		if (labWorkersForLwMainWork == null) {
			labWorkersForLwMainWork = new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>();
		}
		return labWorkersForLwMainWork;
	}

	/**
	 */
	public void setLabWorkersForLwPaperLevel(Set<LabWorker> labWorkersForLwPaperLevel) {
		this.labWorkersForLwPaperLevel = labWorkersForLwPaperLevel;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabWorker> getLabWorkersForLwPaperLevel() {
		if (labWorkersForLwPaperLevel == null) {
			labWorkersForLwPaperLevel = new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>();
		}
		return labWorkersForLwPaperLevel;
	}

	/**
	 */
	public void setLabWorkersForLwSpecialtyDuty(Set<LabWorker> labWorkersForLwSpecialtyDuty) {
		this.labWorkersForLwSpecialtyDuty = labWorkersForLwSpecialtyDuty;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabWorker> getLabWorkersForLwSpecialtyDuty() {
		if (labWorkersForLwSpecialtyDuty == null) {
			labWorkersForLwSpecialtyDuty = new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>();
		}
		return labWorkersForLwSpecialtyDuty;
	}

	/**
	 */
	public void setLabRoomDevicesForIsAudit(Set<LabRoomDevice> labRoomDevicesForIsAudit) {
		this.labRoomDevicesForIsAudit = labRoomDevicesForIsAudit;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDevice> getLabRoomDevicesForIsAudit() {
		if (labRoomDevicesForIsAudit == null) {
			labRoomDevicesForIsAudit = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>();
		}
		return labRoomDevicesForIsAudit;
	}

	/**
	 */
	public void setLabWorkersForLwSubject(Set<LabWorker> labWorkersForLwSubject) {
		this.labWorkersForLwSubject = labWorkersForLwSubject;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabWorker> getLabWorkersForLwSubject() {
		if (labWorkersForLwSubject == null) {
			labWorkersForLwSubject = new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>();
		}
		return labWorkersForLwSubject;
	}

	/**
	 */
	public void setOperationItemsForLpCategoryRequire(Set<OperationItem> operationItemsForLpCategoryRequire) {
		this.operationItemsForLpCategoryRequire = operationItemsForLpCategoryRequire;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationItem> getOperationItemsForLpCategoryRequire() {
		if (operationItemsForLpCategoryRequire == null) {
			operationItemsForLpCategoryRequire = new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>();
		}
		return operationItemsForLpCategoryRequire;
	}

	/**
	 */
	public void setLabWorkersForLwForeignLanguage(Set<LabWorker> labWorkersForLwForeignLanguage) {
		this.labWorkersForLwForeignLanguage = labWorkersForLwForeignLanguage;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabWorker> getLabWorkersForLwForeignLanguage() {
		if (labWorkersForLwForeignLanguage == null) {
			labWorkersForLwForeignLanguage = new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>();
		}
		return labWorkersForLwForeignLanguage;
	}

	/**
	 */
	public void setLabWorkersForLwAcademicDegree(Set<LabWorker> labWorkersForLwAcademicDegree) {
		this.labWorkersForLwAcademicDegree = labWorkersForLwAcademicDegree;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabWorker> getLabWorkersForLwAcademicDegree() {
		if (labWorkersForLwAcademicDegree == null) {
			labWorkersForLwAcademicDegree = new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>();
		}
		return labWorkersForLwAcademicDegree;
	}
	
	@JsonIgnore
	public java.util.Set<net.zjcclims.domain.OperationItemMaterialRecord> getOperationItemMaterialRecords() {
		return operationItemMaterialRecords;
	}

	public void setOperationItemMaterialRecords(
			java.util.Set<net.zjcclims.domain.OperationItemMaterialRecord> operationItemMaterialRecords) {
		this.operationItemMaterialRecords = operationItemMaterialRecords;
	}

	/**
	 */
	public void setLabWorkersForLwReward(Set<LabWorker> labWorkersForLwReward) {
		this.labWorkersForLwReward = labWorkersForLwReward;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabWorker> getLabWorkersForLwReward() {
		if (labWorkersForLwReward == null) {
			labWorkersForLwReward = new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>();
		}
		return labWorkersForLwReward;
	}

	/**
	 */
	public void setLabWorkersForLwEmployment(Set<LabWorker> labWorkersForLwEmployment) {
		this.labWorkersForLwEmployment = labWorkersForLwEmployment;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabWorker> getLabWorkersForLwEmployment() {
		if (labWorkersForLwEmployment == null) {
			labWorkersForLwEmployment = new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>();
		}
		return labWorkersForLwEmployment;
	}

	/**
	 */
	public void setOperationItemsForLpCategoryMain(Set<OperationItem> operationItemsForLpCategoryMain) {
		this.operationItemsForLpCategoryMain = operationItemsForLpCategoryMain;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationItem> getOperationItemsForLpCategoryMain() {
		if (operationItemsForLpCategoryMain == null) {
			operationItemsForLpCategoryMain = new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>();
		}
		return operationItemsForLpCategoryMain;
	}
	//新增
	
	/**
	 */
	public void setLabConstructionProjectAudits(Set<LabConstructionProjectAudit> labConstructionProjectAudits) {
		this.labConstructionProjectAudits = labConstructionProjectAudits;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabConstructionProjectAudit> getLabConstructionProjectAudits() {
		if (labConstructionProjectAudits == null) {
			labConstructionProjectAudits = new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionProjectAudit>();
		}
		return labConstructionProjectAudits;
	}

	/**
	 */
	public void setLabConstructionPurchaseAudits(Set<LabConstructionPurchaseAudit> labConstructionPurchaseAudits) {
		this.labConstructionPurchaseAudits = labConstructionPurchaseAudits;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabConstructionPurchaseAudit> getLabConstructionPurchaseAudits() {
		if (labConstructionPurchaseAudits == null) {
			labConstructionPurchaseAudits = new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionPurchaseAudit>();
		}
		return labConstructionPurchaseAudits;
	}

	/**
	 */
	public void setLabConstructionPurchases(Set<LabConstructionPurchase> labConstructionPurchases) {
		this.labConstructionPurchases = labConstructionPurchases;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabConstructionPurchase> getLabConstructionPurchases() {
		if (labConstructionPurchases == null) {
			labConstructionPurchases = new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionPurchase>();
		}
		return labConstructionPurchases;
	}

	/**
	 */
	public void setLabConstructionFundingAudits(Set<LabConstructionFundingAudit> labConstructionFundingAudits) {
		this.labConstructionFundingAudits = labConstructionFundingAudits;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabConstructionFundingAudit> getLabConstructionFundingAudits() {
		if (labConstructionFundingAudits == null) {
			labConstructionFundingAudits = new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionFundingAudit>();
		}
		return labConstructionFundingAudits;
	}

	/**
	 */
	public void setLabRoomsForLabRoomSort(Set<LabRoom> labRoomsForLabRoomSort) {
		this.labRoomsForLabRoomSort = labRoomsForLabRoomSort;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoom> getLabRoomsForLabRoomSort() {
		if (labRoomsForLabRoomSort == null) {
			labRoomsForLabRoomSort = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoom>();
		}
		return labRoomsForLabRoomSort;
	}

	/**
	 */
	public void setLabRoomsForLabRoomClassification(Set<LabRoom> labRoomsForLabRoomClassification) {
		this.labRoomsForLabRoomClassification = labRoomsForLabRoomClassification;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoom> getLabRoomsForLabRoomClassification() {
		if (labRoomsForLabRoomClassification == null) {
			labRoomsForLabRoomClassification = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoom>();
		}
		return labRoomsForLabRoomClassification;
	}

		/**
	 */
	public void setLabRoomsForIsMultimedia(Set<LabRoom> labRoomsForIsMultimedia) {
		this.labRoomsForIsMultimedia = labRoomsForIsMultimedia;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoom> getLabRoomsForIsMultimedia() {
		if (labRoomsForIsMultimedia == null) {
			labRoomsForIsMultimedia = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoom>();
		}
		return labRoomsForIsMultimedia;
	}


	public void setLabRoomStationReservations(Set<LabRoomStationReservation> LabRoomStationReservations) {
		this.LabRoomStationReservations = LabRoomStationReservations;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomStationReservation> getLabRoomStationReservations() {
		if (LabRoomStationReservations == null) {
			LabRoomStationReservations = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomStationReservation>();
		}
		return LabRoomStationReservations;
	}
	
	/**
	 */
	public void setOperationOutlines(Set<OperationOutline> operationOutlines) {
		this.operationOutlines = operationOutlines;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationOutline> getOperationOutlines() {
		if (operationOutlines == null) {
			operationOutlines = new java.util.LinkedHashSet<net.zjcclims.domain.OperationOutline>();
		}
		return operationOutlines;
	}


	public Set<LabRoomDeviceRepair> getLabRoomDeviceRepairsForStatusId() {
		if (labRoomDeviceRepairsForStatusId == null) {
			labRoomDeviceRepairsForStatusId = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDeviceRepair>();
		}
		return labRoomDeviceRepairsForStatusId;
	}

	public void setLabRoomDeviceRepairsForStatusId(Set<LabRoomDeviceRepair> labRoomDeviceRepairsForStatusId) {
		this.labRoomDeviceRepairsForStatusId = labRoomDeviceRepairsForStatusId;
	}

	public Set<LabRoomDeviceRepair> getLabRoomDeviceRepairsForFailureChoice() {
		if (labRoomDeviceRepairsForFailureChoice == null) {
			labRoomDeviceRepairsForFailureChoice = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDeviceRepair>();
		}
		return labRoomDeviceRepairsForFailureChoice;
	}

	public void setLabRoomDeviceRepairsForFailureChoice(Set<LabRoomDeviceRepair> labRoomDeviceRepairsForFailureChoice) {
		this.labRoomDeviceRepairsForFailureChoice = labRoomDeviceRepairsForFailureChoice;
	}

	public Set<LabRoomDeviceRepair> getLabRoomDeviceRepairsForPartitionId() {
		if (labRoomDeviceRepairsForPartitionId == null) {
			labRoomDeviceRepairsForPartitionId = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDeviceRepair>();
		}
		return labRoomDeviceRepairsForPartitionId;
	}

	public void setLabRoomDeviceRepairsForPartitionId(Set<LabRoomDeviceRepair> labRoomDeviceRepairsForPartitionId) {
		this.labRoomDeviceRepairsForPartitionId = labRoomDeviceRepairsForPartitionId;
	}

	public Set<LabAnnex> getLabAnnexesForLabAnnex() {
		if (labAnnexesForLabAnnex == null) {
			labAnnexesForLabAnnex = new java.util.LinkedHashSet<net.zjcclims.domain.LabAnnex>();
		}
		return labAnnexesForLabAnnex;
	}

	public void setLabAnnexesForLabAnnex(Set<LabAnnex> labAnnexesForLabAnnex) {
		this.labAnnexesForLabAnnex = labAnnexesForLabAnnex;
	}
	/**
	 */
	public void setNDevicePurchasesForCostType(Set<NDevicePurchase> NDevicePurchasesForCostType) {
		this.NDevicePurchasesForCostType = NDevicePurchasesForCostType;
	}

	/**
	 */
	@JsonIgnore
	public Set<NDevicePurchase> getNDevicePurchasesForCostType() {
		if (NDevicePurchasesForCostType == null) {
			NDevicePurchasesForCostType = new java.util.LinkedHashSet<net.zjcclims.domain.NDevicePurchase>();
		}
		return NDevicePurchasesForCostType;
	}

	/**
	 */
	public void setNDevicePurchasesForCostReason(Set<NDevicePurchase> NDevicePurchasesForCostReason) {
		this.NDevicePurchasesForCostReason = NDevicePurchasesForCostReason;
	}

	/**
	 */
	@JsonIgnore
	public Set<NDevicePurchase> getNDevicePurchasesForCostReason() {
		if (NDevicePurchasesForCostReason == null) {
			NDevicePurchasesForCostReason = new java.util.LinkedHashSet<net.zjcclims.domain.NDevicePurchase>();
		}
		return NDevicePurchasesForCostReason;
	}


	
	
	/**
	 */
	/**
	 */
	public CDictionary() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(CDictionary that) {
		setId(that.getId());
		setCNumber(that.getCNumber());
		setCCategory(that.getCCategory());
		setCName(that.getCName());
		setEnabled(that.getEnabled());
		setSchoolDevicesForDeviceFlag(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolDevice>(that.getSchoolDevicesForDeviceFlag()));
		setSchoolDevicesForDeviceSource(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolDevice>(that.getSchoolDevicesForDeviceSource()));
		setSchoolDevicesForUseStatus(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolDevice>(that.getSchoolDevicesForUseStatus()));
		setLabRoomsForLabRoomSort(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoom>(that.getLabRoomsForLabRoomSort()));
		setLabRoomsForLabRoomClassification(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoom>(that.getLabRoomsForLabRoomClassification()));
		setLabRoomsForIsMultimedia(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoom>(that.getLabRoomsForIsMultimedia()));
		setLabRoomAgents(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomAgent>(that.getLabRoomAgents()));
		setLabRoomDevicesForManagerAudit(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>(that.getLabRoomDevicesForManagerAudit()));
		setSchoolCoursesForCourseStatus(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourse>(that.getSchoolCoursesForCourseStatus()));
		setSchoolCoursesForStudentType(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourse>(that.getSchoolCoursesForStudentType()));
		setLabRoomDevicesForAllowLending(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>(that.getLabRoomDevicesForAllowLending()));
		setLabRoomDevicesForAppointmentType(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>(that.getLabRoomDevicesForAppointmentType()));
		setLabRoomDevicesForAllowSecurityAccess(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>(that.getLabRoomDevicesForAllowSecurityAccess()));
		setLabRoomDevicesForSecurityAccessType(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>(that.getLabRoomDevicesForSecurityAccessType()));
		setLabRoomDevicesForDeviceType(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>(that.getLabRoomDevicesForDeviceType()));
		setLabRoomDevicesForDeviceStatus(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>(that.getLabRoomDevicesForDeviceStatus()));
		setLabRoomDevicesForDeviceCharge(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>(that.getLabRoomDevicesForDeviceCharge()));
		setLabRoomDevicesForTeacherAudit(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>(that.getLabRoomDevicesForTeacherAudit()));
		setOperationItemsForLpStatusCheck(new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>(that.getOperationItemsForLpStatusCheck()));
		setLabRoomDevicesForAllowAppointment(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>(that.getLabRoomDevicesForAllowAppointment()));
		setOperationItemsForLpCategoryPublic(new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>(that.getOperationItemsForLpCategoryPublic()));
		setOperationItemsForLpStatusChange(new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>(that.getOperationItemsForLpStatusChange()));
		setLabWorkersForLwCategoryStaff(new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>(that.getLabWorkersForLwCategoryStaff()));
		setOperationItemsForLpCategoryRewardLevel(new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>(that.getOperationItemsForLpCategoryRewardLevel()));
		setLabWorkersForLwForeignLanguageLevel(new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>(that.getLabWorkersForLwForeignLanguageLevel()));
		setLabWorkersForLwDegree(new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>(that.getLabWorkersForLwDegree()));
		setOperationItemsForLpCategoryApp(new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>(that.getOperationItemsForLpCategoryApp()));
		setLabWorkersForLwBookLevel(new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>(that.getLabWorkersForLwBookLevel()));
		setOperationItemsForLpCategoryGuideBook(new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>(that.getOperationItemsForLpCategoryGuideBook()));
		setOperationItemsForLpCategoryStudent(new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>(that.getOperationItemsForLpCategoryStudent()));
		setOperationItemsForLpCategoryNature(new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>(that.getOperationItemsForLpCategoryNature()));
		setLabWorkersForLwMainWork(new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>(that.getLabWorkersForLwMainWork()));
		setLabWorkersForLwPaperLevel(new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>(that.getLabWorkersForLwPaperLevel()));
		setLabWorkersForLwSpecialtyDuty(new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>(that.getLabWorkersForLwSpecialtyDuty()));
		setLabRoomDevicesForIsAudit(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>(that.getLabRoomDevicesForIsAudit()));
		setLabWorkersForLwSubject(new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>(that.getLabWorkersForLwSubject()));
		setOperationItemsForLpCategoryRequire(new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>(that.getOperationItemsForLpCategoryRequire()));
		setLabWorkersForLwForeignLanguage(new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>(that.getLabWorkersForLwForeignLanguage()));
		setLabWorkersForLwAcademicDegree(new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>(that.getLabWorkersForLwAcademicDegree()));
		setLabWorkersForLwReward(new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>(that.getLabWorkersForLwReward()));
		setLabWorkersForLwEmployment(new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>(that.getLabWorkersForLwEmployment()));
		setOperationItemsForLpCategoryMain(new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>(that.getOperationItemsForLpCategoryMain()));
		//新增
		setLabConstructionProjectAudits(new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionProjectAudit>(that.getLabConstructionProjectAudits()));
		setLabConstructionPurchaseAudits(new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionPurchaseAudit>(that.getLabConstructionPurchaseAudits()));
		setLabConstructionPurchases(new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionPurchase>(that.getLabConstructionPurchases()));
		setLabConstructionFundingAudits(new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionFundingAudit>(that.getLabConstructionFundingAudits()));		
		setLabWorkerTrainings(new java.util.LinkedHashSet<net.zjcclims.domain.LabWorkerTraining>(that.getLabWorkerTrainings()));setLabWorkersForLwCategoryExpert(new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>(that.getLabWorkersForLwCategoryExpert()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("CNumber=[").append(CNumber).append("] ");
		buffer.append("CCategory=[").append(CCategory).append("] ");
		buffer.append("CName=[").append(CName).append("] ");
		buffer.append("enabled=[").append(enabled).append("] ");

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
		if (!(obj instanceof CDictionary))
			return false;
		CDictionary equalCheck = (CDictionary) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
	public java.util.Set<net.zjcclims.domain.LabReservation> getLabLendingUserType() {
		return labLendingUserType;
	}
	public void setLabLendingUserType(
			java.util.Set<net.zjcclims.domain.LabReservation> labLendingUserType) {
		this.labLendingUserType = labLendingUserType;
	}
}
