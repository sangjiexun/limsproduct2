package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllSchoolAcademys", query = "select mySchoolAcademy from SchoolAcademy mySchoolAcademy"),
		@NamedQuery(name = "findSchoolAcademyByAcademyName", query = "select mySchoolAcademy from SchoolAcademy mySchoolAcademy where mySchoolAcademy.academyName = ?1"),
		@NamedQuery(name = "findSchoolAcademyByAcademyNameContaining", query = "select mySchoolAcademy from SchoolAcademy mySchoolAcademy where mySchoolAcademy.academyName like ?1"),
		@NamedQuery(name = "findSchoolAcademyByAcademyNumber", query = "select mySchoolAcademy from SchoolAcademy mySchoolAcademy where mySchoolAcademy.academyNumber = ?1"),
		@NamedQuery(name = "findSchoolAcademyByAcademyNumberContaining", query = "select mySchoolAcademy from SchoolAcademy mySchoolAcademy where mySchoolAcademy.academyNumber like ?1"),
		@NamedQuery(name = "findSchoolAcademyByAcademyType", query = "select mySchoolAcademy from SchoolAcademy mySchoolAcademy where mySchoolAcademy.academyType = ?1"),
		@NamedQuery(name = "findSchoolAcademyByAcademyTypeContaining", query = "select mySchoolAcademy from SchoolAcademy mySchoolAcademy where mySchoolAcademy.academyType like ?1"),
		@NamedQuery(name = "findSchoolAcademyByCreatedAt", query = "select mySchoolAcademy from SchoolAcademy mySchoolAcademy where mySchoolAcademy.createdAt = ?1"),
		@NamedQuery(name = "findSchoolAcademyByIsVaild", query = "select mySchoolAcademy from SchoolAcademy mySchoolAcademy where mySchoolAcademy.isVaild = ?1"),
		@NamedQuery(name = "findSchoolAcademyByPrimaryKey", query = "select mySchoolAcademy from SchoolAcademy mySchoolAcademy where mySchoolAcademy.academyNumber = ?1"),
		@NamedQuery(name = "findSchoolAcademyByUpdatedAt", query = "select mySchoolAcademy from SchoolAcademy mySchoolAcademy where mySchoolAcademy.updatedAt = ?1") })
@Table(name = "school_academy")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SchoolAcademy")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class SchoolAcademy implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ѧԺ����
	 * 
	 */

	@Column(name = "academy_number", length = 40, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	String academyNumber;
	/**
	 * ѧԺ���
	 * 
	 */

	@Column(name = "academy_name", length = 150)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String academyName;
	/**
	 */

	@Column(name = "is_vaild")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Boolean isVaild;
	/**
	 * ѧԺ����
	 * 
	 */

	@Column(name = "academy_type", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String academyType;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createdAt;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar updatedAt;

	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoom> labRooms;

	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorkRoom> labWorkRooms;
	
	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolCourseDetail> schoolCourseDetails;
	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolCourseStudent> schoolCourseStudents;
	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabCenter> labCenters;
	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolDevice> schoolDevices;
	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.User> users;
	/**
	 */
	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.Software> softwares;
	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.AssetAppRecord> assetAppRecords;
	
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableSelfCourse> timetableSelfCourses;
	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolCourse> schoolCourses;

	//新增
	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabConstructionProject> labConstructionProjects;
	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabConstructionPurchase> labConstructionPurchases;

	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationOutline> operationOutlines;
	
	
	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ReportRate> rReportRates;
	
	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ReportParameter> reportParameters;

	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorker> labWorkers;

	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectAcceptanceApplication> projectAcceptanceApplications;

	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectStartedReport> projectStartedReports;

	public Set<LabRoom> getOpenLabRooms() {
		if (openLabRooms == null) {
			openLabRooms = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoom>();
		}
		return openLabRooms;
	}

	public void setOpenLabRooms(Set<LabRoom> openLabRooms) {
		this.openLabRooms = openLabRooms;
	}

    /**
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "lab_open_academy", joinColumns = { @JoinColumn(name = "academy_number", referencedColumnName = "academy_number", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "lab_room_id", referencedColumnName = "id", nullable = false, updatable = false) })
    @XmlElement(name = "", namespace = "")
    java.util.Set<net.zjcclims.domain.LabRoom> openLabRooms;

    public Set<LabRoomDevice> getOpenLabRoomDevices() {
        if (openLabRoomDevices == null) {
            openLabRoomDevices = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>();
        }
        return openLabRoomDevices;
    }

    public void setOpenLabRoomDevices(Set<LabRoomDevice> openLabRoomDevices) {
        this.openLabRoomDevices = openLabRoomDevices;
    }

    /**
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "device_open_academy", joinColumns = { @JoinColumn(name = "academy_number", referencedColumnName = "academy_number", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "device_id", referencedColumnName = "id", nullable = false, updatable = false) })
    @XmlElement(name = "", namespace = "")
    java.util.Set<net.zjcclims.domain.LabRoomDevice> openLabRoomDevices;

	/**
	 */
	@ManyToMany(fetch = FetchType.LAZY)

	@JoinTable(name = "lab_construction_son_project_academies", joinColumns = {
			@JoinColumn(name = "academy_number", referencedColumnName = "academy_number", nullable = false, updatable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "son_project_id", referencedColumnName = "id", nullable = false, updatable = false)})
	@XmlElement(name = "", namespace = "")
	Set<LabConstructionSonProject> labConstructionSonProjects;

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

	public java.util.Set<net.zjcclims.domain.ReportParameter> getReportParameters() {
		return reportParameters;
	}

	public void setReportParameters(
			java.util.Set<net.zjcclims.domain.ReportParameter> reportParameters) {
		this.reportParameters = reportParameters;
	}

	public java.util.Set<net.zjcclims.domain.ReportRate> getrReportRates() {
		return rReportRates;
	}

	public void setrReportRates(
			java.util.Set<net.zjcclims.domain.ReportRate> rReportRates) {
		this.rReportRates = rReportRates;
	}

	/**
	 */
	public void setLabWorkRooms(Set<LabWorkRoom> labWorkRooms) {
		this.labWorkRooms = labWorkRooms;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabWorkRoom> getLabWorkRooms() {
		if (labWorkRooms == null) {
			labWorkRooms = new java.util.LinkedHashSet<net.zjcclims.domain.LabWorkRoom>();
		}
		return labWorkRooms;
	}
	
	/**
	 */
	public void setLabRooms(Set<LabRoom> labRooms) {
		this.labRooms = labRooms;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoom> getLabRooms() {
		if (labRooms == null) {
			labRooms = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoom>();
		}
		return labRooms;
	}

	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.PreTimetableAppointment> preTimetableAppointments;
	/**
	 */
	@OneToMany(mappedBy = "schoolAcademy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.PreLabRoom> preLabRooms;

	/**
	 */
	public void setPreTimetableAppointments(Set<PreTimetableAppointment> preTimetableAppointments) {
		this.preTimetableAppointments = preTimetableAppointments;
	}

	/**
	 */
	@JsonIgnore
	public Set<PreTimetableAppointment> getPreTimetableAppointments() {
		if (preTimetableAppointments == null) {
			preTimetableAppointments = new java.util.LinkedHashSet<net.zjcclims.domain.PreTimetableAppointment>();
		}
		return preTimetableAppointments;
	}

	/**
	 */
	public void setPreLabRooms(Set<PreLabRoom> preLabRooms) {
		this.preLabRooms = preLabRooms;
	}

	/**
	 */
	@JsonIgnore
	public Set<PreLabRoom> getPreLabRooms() {
		if (preLabRooms == null) {
			preLabRooms = new java.util.LinkedHashSet<net.zjcclims.domain.PreLabRoom>();
		}
		return preLabRooms;
	}

	/**
	 * ѧԺ����
	 * 
	 */
	public void setAcademyNumber(String academyNumber) {
		this.academyNumber = academyNumber;
	}

	/**
	 * ѧԺ����
	 * 
	 */
	public String getAcademyNumber() {
		return this.academyNumber;
	}

	/**
	 * ѧԺ���
	 * 
	 */
	public void setAcademyName(String academyName) {
		this.academyName = academyName;
	}

	/**
	 * ѧԺ���
	 * 
	 */
	public String getAcademyName() {
		return this.academyName;
	}

	/**
	 */
	public void setIsVaild(Boolean isVaild) {
		this.isVaild = isVaild;
	}

	/**
	 */
	public Boolean getIsVaild() {
		return this.isVaild;
	}

	/**
	 * ѧԺ����
	 * 
	 */
	public void setAcademyType(String academyType) {
		this.academyType = academyType;
	}

	/**
	 * ѧԺ����
	 * 
	 */
	public String getAcademyType() {
		return this.academyType;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public Calendar getCreatedAt() {
		return this.createdAt;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public Calendar getUpdatedAt() {
		return this.updatedAt;
	}

	/**
	 */
	public void setSchoolCourseDetails(Set<SchoolCourseDetail> schoolCourseDetails) {
		this.schoolCourseDetails = schoolCourseDetails;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolCourseDetail> getSchoolCourseDetails() {
		if (schoolCourseDetails == null) {
			schoolCourseDetails = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourseDetail>();
		}
		return schoolCourseDetails;
	}

	/**
	 */
	public void setSchoolCourseStudents(Set<SchoolCourseStudent> schoolCourseStudents) {
		this.schoolCourseStudents = schoolCourseStudents;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolCourseStudent> getSchoolCourseStudents() {
		if (schoolCourseStudents == null) {
			schoolCourseStudents = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourseStudent>();
		}
		return schoolCourseStudents;
	}

	/**
	 */
	public void setLabCenters(Set<LabCenter> labCenters) {
		this.labCenters = labCenters;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabCenter> getLabCenters() {
		if (labCenters == null) {
			labCenters = new java.util.LinkedHashSet<net.zjcclims.domain.LabCenter>();
		}
		return labCenters;
	}

	/**
	 */
	public void setSchoolDevices(Set<SchoolDevice> schoolDevices) {
		this.schoolDevices = schoolDevices;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolDevice> getSchoolDevices() {
		if (schoolDevices == null) {
			schoolDevices = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolDevice>();
		}
		return schoolDevices;
	}

	/**
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/**
	 */
	@JsonIgnore
	public Set<User> getUsers() {
		if (users == null) {
			users = new java.util.LinkedHashSet<net.zjcclims.domain.User>();
		}
		return users;
	}

	/**
	 */
	public void setTimetableSelfCourses(Set<TimetableSelfCourse> timetableSelfCourses) {
		this.timetableSelfCourses = timetableSelfCourses;
	}

	/**
	 */
	@JsonIgnore
	public Set<TimetableSelfCourse> getTimetableSelfCourses() {
		if (timetableSelfCourses == null) {
			timetableSelfCourses = new java.util.LinkedHashSet<net.zjcclims.domain.TimetableSelfCourse>();
		}
		return timetableSelfCourses;
	}

	/**
	 */
	public void setSchoolCourses(Set<SchoolCourse> schoolCourses) {
		this.schoolCourses = schoolCourses;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolCourse> getSchoolCourses() {
		if (schoolCourses == null) {
			schoolCourses = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourse>();
		}
		return schoolCourses;
	}
	
	//新增
	/**
	 */
	public void setLabConstructionProjects(Set<LabConstructionProject> labConstructionProjects) {
		this.labConstructionProjects = labConstructionProjects;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabConstructionProject> getLabConstructionProjects() {
		if (labConstructionProjects == null) {
			labConstructionProjects = new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionProject>();
		}
		return labConstructionProjects;
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
	public Set<LabWorker> getLabWorkers() {
		return labWorkers;
	}

	public void setLabWorkers(Set<LabWorker> labWorkers) {
		this.labWorkers = labWorkers;
	}

	/**
	 */
	public SchoolAcademy() {
	}

	public Set<LabConstructionSonProject> getLabConstructionSonProjects() {
		return labConstructionSonProjects;
	}

	public void setLabConstructionSonProjects(Set<LabConstructionSonProject> labConstructionSonProjects) {
		this.labConstructionSonProjects = labConstructionSonProjects;
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SchoolAcademy that) {
		setAcademyNumber(that.getAcademyNumber());
		setAcademyName(that.getAcademyName());
		setIsVaild(that.getIsVaild());
		setAcademyType(that.getAcademyType());
		setCreatedAt(that.getCreatedAt());
		setUpdatedAt(that.getUpdatedAt());
		setLabRooms(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoom>(that.getLabRooms()));
		setSchoolCourseDetails(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourseDetail>(that.getSchoolCourseDetails()));
		setSchoolCourseStudents(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourseStudent>(that.getSchoolCourseStudents()));
		setLabCenters(new java.util.LinkedHashSet<net.zjcclims.domain.LabCenter>(that.getLabCenters()));
		setSchoolDevices(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolDevice>(that.getSchoolDevices()));
		setUsers(new java.util.LinkedHashSet<net.zjcclims.domain.User>(that.getUsers()));
		setLabWorkers(new java.util.LinkedHashSet<net.zjcclims.domain.LabWorker>(that.getLabWorkers()));
		setTimetableSelfCourses(new java.util.LinkedHashSet<net.zjcclims.domain.TimetableSelfCourse>(that.getTimetableSelfCourses()));
		setSchoolCourses(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourse>(that.getSchoolCourses()));
		//新增
		setOperationOutlines(new java.util.LinkedHashSet<net.zjcclims.domain.OperationOutline>(that.getOperationOutlines()));
		setLabConstructionProjects(new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionProject>(that.getLabConstructionProjects()));
		setLabConstructionPurchases(new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionPurchase>(that.getLabConstructionPurchases()));

		setLabConstructionSonProjects(that.getLabConstructionSonProjects());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("academyNumber=[").append(academyNumber).append("] ");
		buffer.append("academyName=[").append(academyName).append("] ");
		buffer.append("isVaild=[").append(isVaild).append("] ");
		buffer.append("academyType=[").append(academyType).append("] ");
		buffer.append("createdAt=[").append(createdAt).append("] ");
		buffer.append("updatedAt=[").append(updatedAt).append("] ");

		return buffer.toString();
	}

	/**
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((academyNumber == null) ? 0 : academyNumber.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof SchoolAcademy))
			return false;
		SchoolAcademy equalCheck = (SchoolAcademy) obj;
		if ((academyNumber == null && equalCheck.academyNumber != null) || (academyNumber != null && equalCheck.academyNumber == null))
			return false;
		if (academyNumber != null && !academyNumber.equals(equalCheck.academyNumber))
			return false;
		return true;
	}
}
