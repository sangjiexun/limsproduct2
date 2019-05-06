package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

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
		@NamedQuery(name = "findLabRoomByLabRoomWorker", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomWorker = ?1"),
		@NamedQuery(name = "findAllLabRooms", query = "select myLabRoom from LabRoom myLabRoom"),
		@NamedQuery(name = "findLabRoomById", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.id = ?1"),
		@NamedQuery(name = "findLabRoomByIsUsed", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.isUsed = ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomActive", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomActive = ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomAddress", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomAddress = ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomAddressContaining", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomAddress like ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomArea", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomArea = ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomCapacity", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomCapacity = ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomEnName", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomEnName = ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomEnNameContaining", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomEnName like ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomIntroduction", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomIntroduction = ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomManagerAgencies", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomManagerAgencies = ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomManagerAgenciesContaining", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomManagerAgencies like ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomName", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomName = ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomNameContaining", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomName like ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomNumber", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomNumber = ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomNumberContaining", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomNumber like ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomPrizeInformation", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomPrizeInformation = ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomRegulations", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomRegulations = ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomReservation", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomReservation = ?1"),
		@NamedQuery(name = "findLabRoomByLabRoomTimeCreate", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoomTimeCreate = ?1"),
		@NamedQuery(name = "findLabRoomByLabRoonAbbreviation", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoonAbbreviation = ?1"),
		@NamedQuery(name = "findLabRoomByLabRoonAbbreviationContaining", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.labRoonAbbreviation like ?1"),
		@NamedQuery(name = "findLabRoomByPrimaryKey", query = "select myLabRoom from LabRoom myLabRoom where myLabRoom.id = ?1") })
@Table(name = "lab_room")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabRoom")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class LabRoom implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵ���ҷ��ұ�
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ʵ���ұ��
	 * 
	 */

	@Column(name = "lab_room_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String labRoomNumber;
	/**
	 * ʵ�������
	 * 
	 */

	@Column(name = "lab_room_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String labRoomName;
	/**
	 * ʵ����Ӣ�����
	 * 
	 */

	@Column(name = "lab_room_phone")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String labRoomPhone;

	/**
	 * 排序字段
	 *
	 */

	@Column(name = "sort", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String sort;

	@Column(name = "lab_room_en_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String labRoomEnName;
	/**
	 * ʵ���Ҽ��
	 * 
	 */

	@Column(name = "lab_roon_abbreviation")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String labRoonAbbreviation;

	/**
	 * ʵ���ҵص�
	 * 
	 */

	@Column(name = "lab_room_address", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String labRoomAddress;
	
	/**
	 * ʹ�����
	 * 
	 */

	@Column(name = "lab_room_area", scale = 2, precision = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal labRoomArea;

	/**
	 * ʵ��������
	 *
	 */
	@Column(name = "lab_room_capacity")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer labRoomCapacity;


	/**
	 * 可同时预约该实验室的并发次数（人数）
	 */
	@Column(name = "reservation_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer reservationNumber;

	@Column(name = "is_special")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer isSpecial;

	@Column(name = "is_open")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer isOpen;

	/**
	 * �����
	 * 
	 */

	@Column(name = "lab_room_manager_agencies")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String labRoomManagerAgencies;

	@Column(name = "lab_category")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer labCategory;

	@Column(name = "open_inweekend")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
    Integer openInweekend;
	@Column(name = "start_hour", precision = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal startHour;

	@Column(name = "end_hour", precision = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal endHour;

    public BigDecimal getStartHour() {
        return startHour;
    }

    public void setStartHour(BigDecimal startHour) {
        this.startHour = startHour;
    }

    public BigDecimal getEndHour() {
        return endHour;
    }

    public void setEndHour(BigDecimal endHour) {
        this.endHour = endHour;
    }

    public BigDecimal getStartHourInweekend() {
        return startHourInweekend;
    }

    public void setStartHourInweekend(BigDecimal startHourInweekend) {
        this.startHourInweekend = startHourInweekend;
    }

    public BigDecimal getEndHourInweekend() {
        return endHourInweekend;
    }

    public void setEndHourInweekend(BigDecimal endHourInweekend) {
        this.endHourInweekend = endHourInweekend;
    }

    @Column(name = "start_hour_inweekend")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal startHourInweekend;

	@Column(name = "end_hour_inweekend", precision = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal endHourInweekend;
	
	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabroomTimetableRegister> labroomTimetableRegisters;

	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectDevice> projectDevices;

	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<VirtualImage> virtualImages;

	/**
	 */
	public void setLabroomTimetableRegisters(Set<LabroomTimetableRegister> labroomTimetableRegisters) {
		this.labroomTimetableRegisters = labroomTimetableRegisters;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabroomTimetableRegister> getLabroomTimetableRegisters() {
		if (labroomTimetableRegisters == null) {
			labroomTimetableRegisters = new java.util.LinkedHashSet<net.zjcclims.domain.LabroomTimetableRegister>();
		}
		return labroomTimetableRegisters;
	}

	/**
	 * ����ѧ��
	 *
	 */

	/**
	 * ��ϵ��ʽ
	 *
	 */
	public void setLabRoomPhone(String labRoomPhone) {
		this.labRoomPhone = labRoomPhone;
	}

	/**
	 * ��ϵ��ʽ
	 *
	 */
	public String getLabRoomPhone() {
		return this.labRoomPhone;
	}


	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "system_room", referencedColumnName = "room_number") })
	@XmlTransient
	SystemRoom systemRoom;

	/**
	 * �Ƿ���ã���� )
	 * 
	 */

	@Column(name = "lab_room_active")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer labRoomActive;
	/**
	 * �Ƿ��ԤԼ�����
	 * 
	 */

	@Column(name = "lab_room_reservation")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer labRoomReservation;
	/**
	 * ʵ���Ҽ��
	 * 
	 */

	@Column(name = "lab_room_introduction", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String labRoomIntroduction;
	/**
	 * �����ƶ�
	 * 
	 */

	@Column(name = "lab_room_regulations", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String labRoomRegulations;
	/**
	 * ����Ϣ
	 * 
	 */

	@Column(name = "lab_room_prize_information", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String labRoomPrizeInformation;

	/**
	 */

	@Column(name = "is_used")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer isUsed;
	/**
	 * ʵ���Ҵ���ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lab_room_time_create")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar labRoomTimeCreate;

	/**
	 */
	@Column(name = "lab_room_level")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer labRoomLevel;
	
	/**
	 * ʵ���Ҽ��
	 * 
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_center_id", referencedColumnName = "id") })
	@XmlTransient
	LabCenter labCenter;
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_annex_id", referencedColumnName = "id") })
	@XmlTransient
	LabAnnex labAnnex;
	/**
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room_type", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLabRoom;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "creater_user", referencedColumnName = "username") })
	@XmlTransient
	User user;

	@Column(name = "major_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String majorName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({@JoinColumn(name="lab_room_subject", referencedColumnName="s_number")})
	@XmlTransient
	SystemSubject12 systemSubject12;

	/**
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "inter_lab_software", joinColumns = { @JoinColumn(name = "lab_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "software_id", referencedColumnName = "id", nullable = false, updatable = false) })
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.Software> softwares;

	public Set<SchoolAcademy> getOpenSchoolAcademies() {
		if (openSchoolAcademies == null) {
			openSchoolAcademies = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolAcademy>();
		}
		return openSchoolAcademies;
	}

	public void setOpenSchoolAcademies(Set<SchoolAcademy> openSchoolAcademies) {
		this.openSchoolAcademies = openSchoolAcademies;
	}

	/**
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "lab_open_academy", joinColumns = { @JoinColumn(name = "lab_room_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "academy_number", referencedColumnName = "academy_number", nullable = false, updatable = false) })
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolAcademy> openSchoolAcademies;
	/**
	 * ¿ÉÔ¤Ô¼¹¤Î»Êý
	 * 
	 */

	@Column(name = "lab_room_worker")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer labRoomWorker;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_base_id", referencedColumnName = "id") })
	@XmlTransient
	LabAnnex labBase;

	/**
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room_sort", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLabRoomSort;

	/**
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "is_multimedia", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByIsMultimedia;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "school_academy", referencedColumnName = "academy_number") })
	@XmlTransient
	SchoolAcademy schoolAcademy;

	/**
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_room_classification", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLabRoomClassification;
	
	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SoftwareRoomRelated> softwareRoomRelateds;

	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableLabRelated> timetableLabRelateds;
	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDevice> labRoomDevices;
	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomAdmin> labRoomAdmins;
	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomAgent> labRoomAgents;
	/**
	 */
	/*@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItems;*///原来的一对多
	//新增多对多
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "m_operation_item_lab_room", joinColumns = { @JoinColumn(name = "lab_room", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "operation_item", referencedColumnName = "id", nullable = false, updatable = false) })
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItems;
	
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabReservation> labReservations;
	
	//2015.10.13新增
	
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.CommonDocument> commonDocuments;
	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.CommonVideo> commonVideos;
	
	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.AssetCabinet> assetCabinets;
	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.AssetReceiveAudit> assetReceiveAudits;
	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.AssetAppRecord> assetAppRecords;
	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.AssetCabinetWarehouseAccess> assetCabinetWarehouseAccesses;

	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomLending> labRoomLendings;
	
	@Column(name = "lab_room_attentions", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String labRoomAttentions;
	/**
	 * 是否需要审核
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "is_audit", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByIsAudit;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "build_number", referencedColumnName = "build_number") })
	@XmlTransient
	SystemBuild systemBuild;

	public SystemBuild getSystemBuild() {
		return systemBuild;
	}

	public void setSystemBuild(SystemBuild systemBuild) {
		this.systemBuild = systemBuild;
	}

	@Column(name = "floor_no", length = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer floorNo;

	public Integer getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(Integer floorNo) {
		this.floorNo = floorNo;
	}
	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorker> labWorkers;
	
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ReportRate> reportRates;
	
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SoftwareReserve> softwareReserves;
	
	public java.util.Set<net.zjcclims.domain.SoftwareReserve> getSoftwareReserves() {
		return softwareReserves;
	}

	public void setSoftwareReserves(
			java.util.Set<net.zjcclims.domain.SoftwareReserve> softwareReserves) {
		this.softwareReserves = softwareReserves;
	}

	public java.util.Set<net.zjcclims.domain.ReportRate> getReportRates() {
		return reportRates;
	}

	public void setReportRates(
			java.util.Set<net.zjcclims.domain.ReportRate> reportRates) {
		this.reportRates = reportRates;
	}

	/**
	 */
	public void setLabWorkers(Set<LabWorker> labWorkers) {
		this.labWorkers = labWorkers;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabWorker> getLabWorkers() {
		if (labWorkers == null) {
			labWorkers = new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>();
		}
		return labWorkers;
	}
	public void setCDictionaryByIsAudit(CDictionary CDictionaryByIsAudit) {
		this.CDictionaryByIsAudit = CDictionaryByIsAudit;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByIsAudit() {
		return CDictionaryByIsAudit;
	}
	
	/**
	 * 是否需要导师审核
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "teacher_audit", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByTeacherAudit;
	/**
	 */
	public void setCDictionaryByTeacherAudit(CDictionary CDictionaryByTeacherAudit) {
		this.CDictionaryByTeacherAudit = CDictionaryByTeacherAudit;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByTeacherAudit() {
		return CDictionaryByTeacherAudit;
	}
	
	/**
	 * 是否需要实训室管理员审核
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_manager_audit", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLabManagerAudit;
	/**
	 */
	public void setCDictionaryByLabManagerAudit(CDictionary CDictionaryByLabManagerAudit) {
		this.CDictionaryByLabManagerAudit = CDictionaryByLabManagerAudit;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByLabManagerAudit() {
		return CDictionaryByLabManagerAudit;
	}
	
	/**
	 * 准入形式
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "train_type", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByTrainType;
	/**
	 */
	public void setCDictionaryByTrainType(CDictionary CDictionaryByTrainType) {
		this.CDictionaryByTrainType = CDictionaryByTrainType;
	}

	/**
	 */
	public CDictionary getCDictionaryByTrainType() {
		return CDictionaryByTrainType;
	}
	
	/**
	 * 是否需要安全准入
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "allow_security_access", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByAllowSecurityAccess;
	
	/**
	 */
	public void setCDictionaryByAllowSecurityAccess(CDictionary CDictionaryByAllowSecurityAccess) {
		this.CDictionaryByAllowSecurityAccess = CDictionaryByAllowSecurityAccess;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByAllowSecurityAccess() {
		return CDictionaryByAllowSecurityAccess;
	}
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "allow_lending", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByAllowLending;
	
	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomPermitUser> labRoomPermitUsers;
	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomTraining> labRoomTrainings;
	
	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableAppointmentChange> timetableAppointmentChanges;
	
	
	public java.util.Set<net.zjcclims.domain.TimetableAppointmentChange> getTimetableAppointmentChanges() {
		return timetableAppointmentChanges;
	}

	public void setTimetableAppointmentChanges(
			java.util.Set<net.zjcclims.domain.TimetableAppointmentChange> timetableAppointmentChanges) {
		this.timetableAppointmentChanges = timetableAppointmentChanges;
	}
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Column(name = "dean")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer dean;
	
	@Column(name = "training_center_director")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer trainingCenterDirector;
	
	@Column(name = "training_department_dirrector")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer trainingDepartmentDirrector;


	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SpecialExamination> specialExaminations;

	public Set<SpecialExamination> getSpecialExaminations() {
		return specialExaminations;
	}

	public void setSpecialExaminations(Set<SpecialExamination> specialExaminations) {
		this.specialExaminations = specialExaminations;
	}

	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.RoutineInspection> routineInspections;

	public Set<RoutineInspection> getRoutineInspections() {
		return routineInspections;
	}

	public void setRoutineInspections(Set<RoutineInspection> routineInspections) {
		this.routineInspections = routineInspections;
	}


	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabSecurityCheck> labSecurityChecks;

	public Set<LabSecurityCheck> getLabSecurityChecks() {
		return labSecurityChecks;
	}

	public void setLabSecurityChecks(Set<LabSecurityCheck> labSecurityChecks) {
		this.labSecurityChecks = labSecurityChecks;
	}



	public Integer getDean() {
		return dean;
	}

	public void setDean(Integer dean) {
		this.dean = dean;
	}

	public Integer getTrainingCenterDirector() {
		return trainingCenterDirector;
	}

	public void setTrainingCenterDirector(Integer trainingCenterDirector) {
		this.trainingCenterDirector = trainingCenterDirector;
	}

	public Integer getTrainingDepartmentDirrector() {
		return trainingDepartmentDirrector;
	}

	public void setTrainingDepartmentDirrector(Integer trainingDepartmentDirrector) {
		this.trainingDepartmentDirrector = trainingDepartmentDirrector;
	}

	/**
	 */
	public void setLabRoomTrainings(Set<LabRoomTraining> labRoomTrainings) {
		this.labRoomTrainings = labRoomTrainings;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomTraining> getLabRoomTrainings() {
		if (labRoomTrainings == null) {
			labRoomTrainings = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomTraining>();
		}
		return labRoomTrainings;
	}
	/**
	 */
	public void setLabRoomPermitUsers(Set<LabRoomPermitUser> labRoomPermitUsers) {
		this.labRoomPermitUsers = labRoomPermitUsers;
	}


	/**
	 */
	@JsonIgnore
	public Set<LabRoomPermitUser> getLabRoomPermitUsers() {
		if (labRoomPermitUsers == null) {
			labRoomPermitUsers = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomPermitUser>();
		}
		return labRoomPermitUsers;
	}
	/**
	 */
	public void setCDictionaryByAllowLending(CDictionary CDictionaryByAllowLending) {
		this.CDictionaryByAllowLending = CDictionaryByAllowLending;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByAllowLending() {
		return CDictionaryByAllowLending;
	}
	
	@JsonIgnore
	public java.util.Set<net.zjcclims.domain.LabReservation> getLabReservations() {
		return labReservations;
	}

	public void setLabReservations(
			java.util.Set<net.zjcclims.domain.LabReservation> labReservations) {
		this.labReservations = labReservations;
	}
	
	@JsonIgnore
	public SystemSubject12 getSystemSubject12() {
		return systemSubject12;
	}

	public void setSystemSubject12(SystemSubject12 systemSubject12) {
		this.systemSubject12 = systemSubject12;
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
	public void setSoftwares(Set<Software> softwares) {
		this.softwares = softwares;
	}

	/**
	 */
	@JsonIgnore
	public Set<Software> getSoftwares() {
		if (softwares == null) {
			softwares = new java.util.LinkedHashSet<net.zjcclims.domain.Software>();
		}
		return softwares;
	}
	
	/**
	 */
	public void setLabRoomLendings(Set<LabRoomLending> labRoomLendings) {
		this.labRoomLendings = labRoomLendings;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomLending> getLabRoomLendings() {
		if (labRoomLendings == null) {
			labRoomLendings = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomLending>();
		}
		return labRoomLendings;
	}
	
	/**
	 */
	public void setCDictionaryByLabRoom(CDictionary CDictionaryByLabRoom) {
		this.CDictionaryByLabRoom = CDictionaryByLabRoom;
	}
	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByLabRoom() {
		return CDictionaryByLabRoom;
	}
	public Integer getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(Integer isSpecial) {
		this.isSpecial = isSpecial;
	}

	public Integer getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Integer isOpen) {
		this.isOpen = isOpen;
	}

//	/**
//	 */
//	@JsonIgnore
//	public CDictionary getCDictionaryByLabRoom() {
//		return CDictionaryByLabRoom;
//	}
	
	/**
	 */
	@OneToMany(mappedBy = "labRoom", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomStationReservation> labRoomStationReservations;
	
	/**
	 */
	public void setLabRoomStationReservations(Set<LabRoomStationReservation> labRoomStationReservations) {
		this.labRoomStationReservations = labRoomStationReservations;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomStationReservation> getLabRoomStationReservations() {
		if (labRoomStationReservations == null) {
			labRoomStationReservations = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomStationReservation>();
		}
		return labRoomStationReservations;
	}
	
	public SystemRoom getSystemRoom() {
		return systemRoom;
	}

	public void setSystemRoom(SystemRoom systemRoom) {
		this.systemRoom = systemRoom;
	}


	/**
	 * 是否校企共建：1是、0或null否
	 */
	@Column(name = "is_school_enterprise_cooperation")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer isSchoolEnterpriseCooperation;

	/**
	 * 是否生产性实训室：1是、0或null否
	 */
	@Column(name = "is_roductivity")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer isRoductivity;

	/**
	 * 是否仿真实训室：1是、0或null否
	 */
	@Column(name = "is_simulation")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer isSimulation;

	public Integer getIsSchoolEnterpriseCooperation() {
		return isSchoolEnterpriseCooperation;
	}

	public void setIsSchoolEnterpriseCooperation(Integer isSchoolEnterpriseCooperation) {
		this.isSchoolEnterpriseCooperation = isSchoolEnterpriseCooperation;
	}

	public Integer getIsRoductivity() {
		return isRoductivity;
	}

	public void setIsRoductivity(Integer isRoductivity) {
		this.isRoductivity = isRoductivity;
	}

	public Integer getIsSimulation() {
		return isSimulation;
	}

	public void setIsSimulation(Integer isSimulation) {
		this.isSimulation = isSimulation;
	}


	/**
	 * ʵ���ҷ��ұ�
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵ���ҷ��ұ�
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ʵ���ұ��
	 * 
	 */
	public void setLabRoomNumber(String labRoomNumber) {
		this.labRoomNumber = labRoomNumber;
	}

	/**
	 * ʵ���ұ��
	 * 
	 */
	public String getLabRoomNumber() {
		return this.labRoomNumber;
	}

	/**
	 * ʵ�������
	 * 
	 */
	public void setLabRoomName(String labRoomName) {
		this.labRoomName = labRoomName;
	}

	/**
	 * ʵ�������
	 * 
	 */
	public String getLabRoomName() {
		return this.labRoomName;
	}

	/**
	 * ʵ����Ӣ�����
	 * 
	 */
	public void setLabRoomEnName(String labRoomEnName) {
		this.labRoomEnName = labRoomEnName;
	}

	/**
	 * ʵ����Ӣ�����
	 * 
	 */
	public String getLabRoomEnName() {
		return this.labRoomEnName;
	}

	/**
	 * ʵ���Ҽ��
	 * 
	 */
	public void setLabRoonAbbreviation(String labRoonAbbreviation) {
		this.labRoonAbbreviation = labRoonAbbreviation;
	}

	/**
	 * ʵ���Ҽ��
	 * 
	 */
	public String getLabRoonAbbreviation() {
		return this.labRoonAbbreviation;
	}

	/**
	 * ʵ���ҵص�
	 * 
	 */
	public void setLabRoomAddress(String labRoomAddress) {
		this.labRoomAddress = labRoomAddress;
	}

	/**
	 * ʵ���ҵص�
	 * 
	 */
	public String getLabRoomAddress() {
		return this.labRoomAddress;
	}

	/**
	 * ʹ�����
	 * 
	 */
	public void setLabRoomArea(BigDecimal labRoomArea) {
		this.labRoomArea = labRoomArea;
	}

	/**
	 * ʹ�����
	 * 
	 */
	public BigDecimal getLabRoomArea() {
		return this.labRoomArea;
	}

	/**
	 * ʵ��������
	 * 
	 */
	public void setLabRoomCapacity(Integer labRoomCapacity) {
		this.labRoomCapacity = labRoomCapacity;
	}

	/**
	 * ʵ��������
	 * 
	 */
	public Integer getLabRoomCapacity() {
		return this.labRoomCapacity;
	}
	
	public Integer getReservationNumber() {
		return reservationNumber;
	}

	public void setReservationNumber(Integer reservationNumber) {
		this.reservationNumber = reservationNumber;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	/**
	 */
	public String getMajorName() {
		return this.majorName;
	}

	/**
	 * �����
	 * 
	 */
	public void setLabRoomManagerAgencies(String labRoomManagerAgencies) {
		this.labRoomManagerAgencies = labRoomManagerAgencies;
	}

	/**
	 * �����
	 * 
	 */
	public String getLabRoomManagerAgencies() {
		return this.labRoomManagerAgencies;
	}

	/**
	 * �Ƿ���ã���� )
	 * 
	 */
	public void setLabRoomActive(Integer labRoomActive) {
		this.labRoomActive = labRoomActive;
	}

	/**
	 * �Ƿ���ã���� )
	 * 
	 */
	public Integer getLabRoomActive() {
		return this.labRoomActive;
	}

	/**
	 * �Ƿ��ԤԼ�����
	 * 
	 */
	public void setLabRoomReservation(Integer labRoomReservation) {
		this.labRoomReservation = labRoomReservation;
	}

	/**
	 * �Ƿ��ԤԼ�����
	 * 
	 */
	public Integer getLabRoomReservation() {
		return this.labRoomReservation;
	}

	/**
	 * ʵ���Ҽ��
	 * 
	 */
	public void setLabRoomIntroduction(String labRoomIntroduction) {
		this.labRoomIntroduction = labRoomIntroduction;
	}

	/**
	 * ʵ���Ҽ��
	 * 
	 */
	public String getLabRoomIntroduction() {
		return this.labRoomIntroduction;
	}

	/**
	 * �����ƶ�
	 * 
	 */
	public void setLabRoomRegulations(String labRoomRegulations) {
		this.labRoomRegulations = labRoomRegulations;
	}

	/**
	 * �����ƶ�
	 * 
	 */
	public String getLabRoomRegulations() {
		return this.labRoomRegulations;
	}

	/**
	 * ����Ϣ
	 * 
	 */
	public void setLabRoomPrizeInformation(String labRoomPrizeInformation) {
		this.labRoomPrizeInformation = labRoomPrizeInformation;
	}

	/**
	 * ����Ϣ
	 * 
	 */
	public String getLabRoomPrizeInformation() {
		return this.labRoomPrizeInformation;
	}

	/**
	 */
	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	/**
	 */
	public Integer getIsUsed() {
		return this.isUsed;
	}

	public Integer getLabCategory() {
		return labCategory;
	}

	public void setLabCategory(Integer labCategory) {
		this.labCategory = labCategory;
	}

	/**
	 * ʵ���Ҵ���ʱ��
	 * 
	 */
	public void setLabRoomTimeCreate(Calendar labRoomTimeCreate) {
		this.labRoomTimeCreate = labRoomTimeCreate;
	}

	/**
	 * ʵ���Ҵ���ʱ��
	 * 
	 */
	public Calendar getLabRoomTimeCreate() {
		return this.labRoomTimeCreate;
	}

	/**
	 */
	public void setLabCenter(LabCenter labCenter) {
		this.labCenter = labCenter;
	}

	/**
	 */
	@JsonIgnore
	public LabCenter getLabCenter() {
		return labCenter;
	}

	/**
	 */
	public void setTimetableLabRelateds(Set<TimetableLabRelated> timetableLabRelateds) {
		this.timetableLabRelateds = timetableLabRelateds;
	}

	/**
	 */
	@JsonIgnore
	public Set<TimetableLabRelated> getTimetableLabRelateds() {
		if (timetableLabRelateds == null) {
			timetableLabRelateds = new java.util.LinkedHashSet<net.zjcclims.domain.TimetableLabRelated>();
		}
		return timetableLabRelateds;
	}

	/**
	 */
	public void setLabRoomDevices(Set<LabRoomDevice> labRoomDevices) {
		this.labRoomDevices = labRoomDevices;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDevice> getLabRoomDevices() {
		if (labRoomDevices == null) {
			labRoomDevices = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>();
		}
		return labRoomDevices;
	}

	/**
	 */
	public void setLabRoomAdmins(Set<LabRoomAdmin> labRoomAdmins) {
		this.labRoomAdmins = labRoomAdmins;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomAdmin> getLabRoomAdmins() {
		if (labRoomAdmins == null) {
			labRoomAdmins = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomAdmin>();
		}
		return labRoomAdmins;
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
	public void setSoftwareRoomRelateds(Set<SoftwareRoomRelated> softwareRoomRelateds) {
		this.softwareRoomRelateds = softwareRoomRelateds;
	}

	/**
	 */
	@JsonIgnore
	public Set<SoftwareRoomRelated> getSoftwareRoomRelateds() {
		if (softwareRoomRelateds == null) {
			softwareRoomRelateds = new java.util.LinkedHashSet<net.zjcclims.domain.SoftwareRoomRelated>();
		}
		return softwareRoomRelateds;
	}

	/**
	 */
	/*public void setOperationItems(Set<OperationItem> operationItems) {
		this.operationItems = operationItems;
	}*/

	/**
	 */
	/*@JsonIgnore
	public Set<OperationItem> getOperationItems() {
		if (operationItems == null) {
			operationItems = new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>();
		}
		return operationItems;
	}*///原来的一对多
	
	//新增多对多
	public void setOperationItems(Set<OperationItem> operationItems) {
		this.operationItems = operationItems;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationItem> getOperationItems() {
		if (operationItems == null) {
			operationItems = new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>();
		}
		return operationItems;
	}
	
	
	//2015.10.13新增
	
	/**
	 */
	public void setCommonDocuments(Set<CommonDocument> commonDocuments) {
		this.commonDocuments = commonDocuments;
	}

	/**
	 */
	@JsonIgnore
	public Set<CommonDocument> getCommonDocuments() {
		if (commonDocuments == null) {
			commonDocuments = new java.util.LinkedHashSet<net.zjcclims.domain.CommonDocument>();
		}
		return commonDocuments;
	}
	
	
	/**
	 */
	public void setCommonVideos(Set<CommonVideo> commonVideos) {
		this.commonVideos = commonVideos;
	}

	/**
	 */
	@JsonIgnore
	public Set<CommonVideo> getCommonVideos() {
		if (commonVideos == null) {
			commonVideos = new java.util.LinkedHashSet<net.zjcclims.domain.CommonVideo>();
		}
		return commonVideos;
	}
	
	public String getLabRoomAttentions() {
		return labRoomAttentions;
	}

	public void setLabRoomAttentions(String labRoomAttentions) {
		this.labRoomAttentions = labRoomAttentions;
	}
	
	/**
	 */
	public void setAssetCabinets(Set<AssetCabinet> assetCabinets) {
		this.assetCabinets = assetCabinets;
	}

	/**
	 */
	@JsonIgnore
	public Set<AssetCabinet> getAssetCabinets() {
		if (assetCabinets == null) {
			assetCabinets = new java.util.LinkedHashSet<net.zjcclims.domain.AssetCabinet>();
		}
		return assetCabinets;
	}
	/**
	 * ¿ÉÔ¤Ô¼¹¤Î»Êý
	 * 
	 */
	public void setLabRoomWorker(Integer labRoomWorker) {
		this.labRoomWorker = labRoomWorker;
	}

	/**
	 * ¿ÉÔ¤Ô¼¹¤Î»Êý
	 * 
	 */
	public Integer getLabRoomWorker() {
		return this.labRoomWorker;
	}

	public void setLabBase(LabAnnex labBase) {
		this.labBase = labBase;
	}

	/**
	 */
	@JsonIgnore
	public LabAnnex getLabBase() {
		return labBase;
	}

	/**
	 */
	public void setCDictionaryByLabRoomClassification(CDictionary CDictionaryByLabRoomClassification) {
		this.CDictionaryByLabRoomClassification = CDictionaryByLabRoomClassification;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByLabRoomClassification() {
		return CDictionaryByLabRoomClassification;
	}

	/**
	 */
	public void setCDictionaryByIsMultimedia(CDictionary CDictionaryByIsMultimedia) {
		this.CDictionaryByIsMultimedia = CDictionaryByIsMultimedia;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByIsMultimedia() {
		return CDictionaryByIsMultimedia;
	}

	/**
	 */
	public void setSchoolAcademy(SchoolAcademy schoolAcademy) {
		this.schoolAcademy = schoolAcademy;
	}

	/**
	 */
	@JsonIgnore
	public SchoolAcademy getSchoolAcademy() {
		return schoolAcademy;
	}

	/**
	 */
	public void setCDictionaryByLabRoomSort(CDictionary CDictionaryByLabRoomSort) {
		this.CDictionaryByLabRoomSort = CDictionaryByLabRoomSort;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByLabRoomSort() {
		return CDictionaryByLabRoomSort;
	}

	/**
	 */
	public void setAssetReceiveAudits(Set<AssetReceiveAudit> assetReceiveAudits) {
		this.assetReceiveAudits = assetReceiveAudits;
	}

	/**
	 */
	@JsonIgnore
	public Set<AssetReceiveAudit> getAssetReceiveAudits() {
		if (assetReceiveAudits == null) {
			assetReceiveAudits = new java.util.LinkedHashSet<net.zjcclims.domain.AssetReceiveAudit>();
		}
		return assetReceiveAudits;
	}

	/**
	 */
	public void setAssetAppRecords(Set<AssetAppRecord> assetAppRecords) {
		this.assetAppRecords = assetAppRecords;
	}

	/**
	 */
	@JsonIgnore
	public Set<AssetAppRecord> getAssetAppRecords() {
		if (assetAppRecords == null) {
			assetAppRecords = new java.util.LinkedHashSet<net.zjcclims.domain.AssetAppRecord>();
		}
		return assetAppRecords;
	}

	/**
	 */
	public void setAssetCabinetWarehouseAccesses(Set<AssetCabinetWarehouseAccess> assetCabinetWarehouseAccesses) {
		this.assetCabinetWarehouseAccesses = assetCabinetWarehouseAccesses;
	}

	/**
	 */
	@JsonIgnore
	public Set<AssetCabinetWarehouseAccess> getAssetCabinetWarehouseAccesses() {
		if (assetCabinetWarehouseAccesses == null) {
			assetCabinetWarehouseAccesses = new java.util.LinkedHashSet<net.zjcclims.domain.AssetCabinetWarehouseAccess>();
		}
		return assetCabinetWarehouseAccesses;
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

	public Set<VirtualImage> getVirtualImages() {
		return virtualImages;
	}

	public void setVirtualImages(Set<VirtualImage> virtualImages) {
		this.virtualImages = virtualImages;
	}

    public Integer getOpenInweekend() {
        return openInweekend;
    }

    public void setOpenInweekend(Integer openInweekend) {
        this.openInweekend = openInweekend;
    }

    /**
	 */
	public Integer getLabRoomLevel() {
		return labRoomLevel;
	}

	public void setLabRoomLevel(Integer labRoomLevel) {
		this.labRoomLevel = labRoomLevel;
	}
	/**
	 */
	public LabRoom() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabRoom that) {
		setSchoolAcademy(that.getSchoolAcademy());
		setCDictionaryByLabRoomSort(that.getCDictionaryByLabRoomSort());
		setCDictionaryByIsMultimedia(that.getCDictionaryByIsMultimedia());
		setCDictionaryByLabRoomClassification(that.getCDictionaryByLabRoomClassification());
		setLabBase(that.getLabBase());
		setLabRoomWorker(that.getLabRoomWorker());
		setId(that.getId());
		setLabRoomNumber(that.getLabRoomNumber());
		setLabRoomName(that.getLabRoomName());
		setLabRoomEnName(that.getLabRoomEnName());
		setLabRoonAbbreviation(that.getLabRoonAbbreviation());
		setLabRoomAddress(that.getLabRoomAddress());
		setLabRoomArea(that.getLabRoomArea());
		setLabRoomCapacity(that.getLabRoomCapacity());
		setReservationNumber(that.getReservationNumber());
		setLabRoomManagerAgencies(that.getLabRoomManagerAgencies());
		setSystemRoom(that.getSystemRoom());
		setLabRoomActive(that.getLabRoomActive());
		setLabRoomReservation(that.getLabRoomReservation());
		setLabRoomIntroduction(that.getLabRoomIntroduction());
		setLabRoomRegulations(that.getLabRoomRegulations());
		setLabRoomPrizeInformation(that.getLabRoomPrizeInformation());
		setIsUsed(that.getIsUsed());
		setLabRoomTimeCreate(that.getLabRoomTimeCreate());
		setLabCenter(that.getLabCenter());
		setIsSchoolEnterpriseCooperation(that.getIsSchoolEnterpriseCooperation());
		setIsRoductivity(that.getIsRoductivity());
		setIsSimulation(that.getIsSimulation());
		setIsOpen(that.getIsOpen());
		setIsSpecial(that.getIsSpecial());
		setIsUsed(that.getIsUsed());
		setTimetableLabRelateds(new java.util.LinkedHashSet<net.zjcclims.domain.TimetableLabRelated>(that.getTimetableLabRelateds()));
		setLabRoomDevices(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>(that.getLabRoomDevices()));
		setLabRoomAdmins(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomAdmin>(that.getLabRoomAdmins()));
		setLabRoomAgents(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomAgent>(that.getLabRoomAgents()));
//		setOperationItems(new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>(that.getOperationItems()));
		//新增多对多，与原来的一对多一样的 
		setOperationItems(new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>(that.getOperationItems()));
//		setCDictionaryByLabRoomActive(that.getCDictionaryByLabRoomActive());
		setCDictionaryByLabRoom(that.getCDictionaryByLabRoom());
		//2015.10.13新增
		setCommonDocuments(new java.util.LinkedHashSet<net.zjcclims.domain.CommonDocument>(that.getCommonDocuments()));
		setCommonVideos(new java.util.LinkedHashSet<net.zjcclims.domain.CommonVideo>(that.getCommonVideos()));
		setLabWorkers(new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>(that.getLabWorkers()));
		setSoftwares(new java.util.LinkedHashSet<net.zjcclims.domain.Software>(that.getSoftwares()));
		setLabRoomPermitUsers(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomPermitUser>(that.getLabRoomPermitUsers()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();
		buffer.append("labRoomWorker=[").append(labRoomWorker).append("] ");
		buffer.append("id=[").append(id).append("] ");
		buffer.append("labRoomNumber=[").append(labRoomNumber).append("] ");
		buffer.append("labRoomName=[").append(labRoomName).append("] ");
		buffer.append("labRoomEnName=[").append(labRoomEnName).append("] ");
		//buffer.append("systemRoom=[").append(systemRoom).append("] ");
		buffer.append("labRoonAbbreviation=[").append(labRoonAbbreviation).append("] ");
		buffer.append("labRoomAddress=[").append(labRoomAddress).append("] ");
		buffer.append("labRoomArea=[").append(labRoomArea).append("] ");
		buffer.append("labRoomCapacity=[").append(labRoomCapacity).append("] ");
		buffer.append("reservationNumber=[").append(reservationNumber).append("] ");
		buffer.append("labRoomManagerAgencies=[").append(labRoomManagerAgencies).append("] ");
		buffer.append("labRoomActive=[").append(labRoomActive).append("] ");
		buffer.append("labRoomReservation=[").append(labRoomReservation).append("] ");
		buffer.append("labRoomIntroduction=[").append(labRoomIntroduction).append("] ");
		buffer.append("labRoomRegulations=[").append(labRoomRegulations).append("] ");
		buffer.append("labRoomPrizeInformation=[").append(labRoomPrizeInformation).append("] ");
		buffer.append("isUsed=[").append(isUsed).append("] ");
		buffer.append("labRoomTimeCreate=[").append(labRoomTimeCreate).append("] ");
		buffer.append("labCategory=[").append(labCategory).append("] ");
		buffer.append("isSchoolEnterpriseCooperation=[").append(isSchoolEnterpriseCooperation).append("] ");
		buffer.append("isRoductivity=[").append(isRoductivity).append("] ");
		buffer.append("isSimulation=[").append(isSimulation).append("] ");
		buffer.append("isOpen=[").append(isOpen).append("] ");
		buffer.append("isSpecial=[").append(isSpecial).append("] ");

		return buffer.toString();
	}

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		if (!(obj instanceof LabRoom))
			return false;
		LabRoom equalCheck = (LabRoom) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
