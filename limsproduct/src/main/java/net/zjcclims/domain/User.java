package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllUsers", query = "select myUser from User myUser"),
		@NamedQuery(name = "findUserByAttendanceTime", query = "select myUser from User myUser where myUser.attendanceTime = ?1"),
		@NamedQuery(name = "findUserByAttendanceTimeContaining", query = "select myUser from User myUser where myUser.attendanceTime like ?1"),
		@NamedQuery(name = "findUserByCardno", query = "select myUser from User myUser where myUser.cardno = ?1"),
		@NamedQuery(name = "findUserByCardnoContaining", query = "select myUser from User myUser where myUser.cardno like ?1"),
		@NamedQuery(name = "findUserByCname", query = "select myUser from User myUser where myUser.cname = ?1"),
		@NamedQuery(name = "findUserByCnameContaining", query = "select myUser from User myUser where myUser.cname like ?1"),
		@NamedQuery(name = "findUserByCreatedAt", query = "select myUser from User myUser where myUser.createdAt = ?1"),
		@NamedQuery(name = "findUserByCreatedAtAfter", query = "select myUser from User myUser where myUser.createdAt > ?1"),
		@NamedQuery(name = "findUserByCreatedAtBefore", query = "select myUser from User myUser where myUser.createdAt < ?1"),
		@NamedQuery(name = "findUserByEmail", query = "select myUser from User myUser where myUser.email = ?1"),
		@NamedQuery(name = "findUserByEmailContaining", query = "select myUser from User myUser where myUser.email like ?1"),
		@NamedQuery(name = "findUserByEnabled", query = "select myUser from User myUser where myUser.enabled = ?1"),
		@NamedQuery(name = "findUserByEnrollmentStatus", query = "select myUser from User myUser where myUser.enrollmentStatus = ?1"),
		@NamedQuery(name = "findUserByGrade", query = "select myUser from User myUser where myUser.grade = ?1"),
		@NamedQuery(name = "findUserByGradeContaining", query = "select myUser from User myUser where myUser.grade like ?1"),
		@NamedQuery(name = "findUserByIfEnrollment", query = "select myUser from User myUser where myUser.ifEnrollment = ?1"),
		@NamedQuery(name = "findUserByIfEnrollmentContaining", query = "select myUser from User myUser where myUser.ifEnrollment like ?1"),
		@NamedQuery(name = "findUserByLastLogin", query = "select myUser from User myUser where myUser.lastLogin = ?1"),
		@NamedQuery(name = "findUserByMajorNumber", query = "select myUser from User myUser where myUser.majorNumber = ?1"),
		@NamedQuery(name = "findUserByMajorNumberContaining", query = "select myUser from User myUser where myUser.majorNumber like ?1"),
		@NamedQuery(name = "findUserByPassword", query = "select myUser from User myUser where myUser.password = ?1"),
		@NamedQuery(name = "findUserByPasswordContaining", query = "select myUser from User myUser where myUser.password like ?1"),
		@NamedQuery(name = "findUserByPrimaryKey", query = "select myUser from User myUser where myUser.username = ?1"),
		@NamedQuery(name = "findUserByTeacherNumber", query = "select myUser from User myUser where myUser.teacherNumber = ?1"),
		@NamedQuery(name = "findUserByTelephone", query = "select myUser from User myUser where myUser.telephone = ?1"),
		@NamedQuery(name = "findUserByTelephoneContaining", query = "select myUser from User myUser where myUser.telephone like ?1"),
		@NamedQuery(name = "findUserByUpdatedAt", query = "select myUser from User myUser where myUser.updatedAt = ?1"),
		@NamedQuery(name = "findUserByUpdatedAtAfter", query = "select myUser from User myUser where myUser.updatedAt > ?1"),
		@NamedQuery(name = "findUserByUpdatedAtBefore", query = "select myUser from User myUser where myUser.updatedAt < ?1"),
		@NamedQuery(name = "findUserByUserRole", query = "select myUser from User myUser where myUser.userRole = ?1"),
		@NamedQuery(name = "findUserByUserRoleContaining", query = "select myUser from User myUser where myUser.userRole like ?1"),
		@NamedQuery(name = "findUserByUserSexy", query = "select myUser from User myUser where myUser.userSexy = ?1"),
		@NamedQuery(name = "findUserByUserSexyContaining", query = "select myUser from User myUser where myUser.userSexy like ?1"),
		@NamedQuery(name = "findUserByUserStatus", query = "select myUser from User myUser where myUser.userStatus = ?1"),
		@NamedQuery(name = "findUserByUserStatusContaining", query = "select myUser from User myUser where myUser.userStatus like ?1"),
		@NamedQuery(name = "findUserByUserType", query = "select myUser from User myUser where myUser.userType = ?1"),
		@NamedQuery(name = "findUserByUsername", query = "select myUser from User myUser where myUser.username = ?1"),
		@NamedQuery(name = "findUserByUsernameContaining", query = "select myUser from User myUser where myUser.username like ?1") })
@Table(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "User")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ѧ��/����
	 *
	 */

	@Column(name = "username", length = 40, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	String username;
	/**
	 * ����
	 *
	 */

	@Column(name = "cardno")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String cardno;
	/**
	 * ����
	 *
	 */

	@Column(name = "cname", length = 100, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String cname;
	/**
	 * ����
	 *
	 */

	@Column(name = "password", length = 120, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String password;
	/**
	 * �Ա�
	 *
	 */

	@Column(name = "user_sexy", length = 10)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String userSexy;
	/**
	 * �Ƿ���У
	 *
	 */

	@Column(name = "user_status", length = 10)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String userStatus;
	/**
	 * ����Ա����
	 *
	 */

	@Column(name = "teacher_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer teacherNumber;
	/**
	 * רҵ����
	 *
	 */

	@Column(name = "major_number", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String majorNumber;
	/**
	 * �û���ɫ��0��ѧ��1�ǽ�ʦ��
	 *
	 */

	@Column(name = "user_role", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String userRole;

	/**
	 * ����¼ʱ��
	 *
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_login")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar lastLogin;
	/**
	 * ����ʱ��
	 *
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "created_at")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createdAt;
	/**
	 * ����ʱ��
	 *
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "updated_at")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar updatedAt;
	/**
	 * ��ϵ�绰
	 *
	 */

	@Column(name = "telephone", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String telephone;
	/**
	 * �ʼ�
	 *
	 */

	@Column(name = "email", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String email;
	/**
	 * �Ƿ����
	 *
	 */

	@Column(name = "enabled")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Boolean enabled;
	/**
	 * ѧ��״̬
	 *
	 */

	@Column(name = "enrollment_status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer enrollmentStatus;

	@Column(name = "credit_score")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer creditScore;
	/**
	 * �Ƿ��ڼ�
	 *
	 */

	@Column(name = "if_enrollment", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String ifEnrollment;
	/**
	 * ѧ�����
	 *
	 */

	@Column(name = "user_type")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer userType;
	/**
	 * ��ѧ���
	 *
	 */

	@Column(name = "attendance_time", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String attendanceTime;
	/**
	 * �����꼶
	 *
	 */

	@Column(name = "grade", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String grade;


	//QQ
	@Column(name = "qq", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String qq;
	/**
	 */
	@Column(name = "teaching_department")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String teachingDepartment;


	public String getTeachingDepartment() {
		return teachingDepartment;
	}

	public void setTeachingDepartment(String teachingDepartment) {
		this.teachingDepartment = teachingDepartment;
	}

	/**
	 * 英文名
	 *
	 */
	@Column(name = "en_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String eNname;
	public String geteNname() {
		return eNname;
	}

	public void seteNname(String eNname) {
		this.eNname = eNname;
	}

	/**
	 * 登录账号对应的域账号
	 *
	 */
	@Column(name = "domain_account")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String domainAccount;

	public String getDomainAccount() {
		return domainAccount;
	}

	public void setDomainAccount(String domainAccount) {
		this.domainAccount = domainAccount;
	}

	/**
	 */

	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ChoseProfessorRecord> choseProfessorRecords;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ChoseProfessor> choseProfessors;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ChoseDissertationRecord> choseDissertationRecords;

	public Set<ChoseProfessorRecord> getChoseProfessorRecords() {
		return choseProfessorRecords;
	}

	public void setChoseProfessorRecords(Set<ChoseProfessorRecord> choseProfessorRecords) {
		this.choseProfessorRecords = choseProfessorRecords;
	}

	public Set<ChoseProfessor> getChoseProfessors() {
		return choseProfessors;
	}

	public void setChoseProfessors(Set<ChoseProfessor> choseProfessors) {
		this.choseProfessors = choseProfessors;
	}

	public Set<ChoseDissertationRecord> getChoseDissertationRecords() {
		return choseDissertationRecords;
	}

	public void setChoseDissertationRecords(Set<ChoseDissertationRecord> choseDissertationRecords) {
		this.choseDissertationRecords = choseDissertationRecords;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 */
	/*public void setCreditOptions(Set<CreditOption> CreditOptions) {
		this.CreditOptions = CreditOptions;
	}

	*//**
	 *//*
	@JsonIgnore
	public Set<CreditOption> getCreditOptions() {
		if (CreditOptions == null) {
			CreditOptions = new java.util.LinkedHashSet<net.zjcclims.domain.CreditOption>();
		}
		return CreditOptions;
	}*/
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "academy_number", referencedColumnName = "academy_number") })
	@XmlTransient
	SchoolAcademy schoolAcademy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "classes_number", referencedColumnName = "class_number") })
	@XmlTransient
	SchoolClasses schoolClasses;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "major_direction", referencedColumnName = "id") })
    @XmlTransient
    CMajorDirection CMajorDirection;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "research_id", referencedColumnName = "id") })
	@XmlTransient
	ResearchProject researchProject;

	/**
	 */
	@OneToMany(mappedBy = "userByCreatedBy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableAttendance> timetableAttendancesForUserNumber;
	/**
	 */
	@OneToMany(mappedBy = "userByCenterManager", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabCenter> labCentersForCenterManager;
	/**
	 */
	@OneToMany(mappedBy = "userByCreatedBy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabCenter> labCentersForCreatedBy;
	/**
	 */
	@OneToMany(mappedBy = "userByUpdatedBy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabCenter> labCentersForUpdatedBy;
	/**
	 */
	@OneToMany(mappedBy = "userByKeepUser", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolDevice> schoolDevicesForUserNumber;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableTutorRelated> timetableTutorRelateds;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableGroupStudents> timetableGroupStudentses;
	/**
	 */
	@OneToMany(mappedBy = "userByKeepUser", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolDevice> schoolDevicesForKeepUser;
	/**
	 */
	@OneToMany(mappedBy = "userByCreatedBy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableAttendance> timetableAttendancesForCreatedBy;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomAdmin> labRoomAdmins;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabConstructApp> labConstructApps;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabConstructAppApproval> labConstructAppApprovals;


	@OneToMany(mappedBy = "userByTeacherNumber", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolCourseStudent> schoolCourseStudentsForTeacherNumber;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDevice> labRoomDevices;
	/**
	 */
	@OneToMany(mappedBy = "userByCreatedBy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolCourse> schoolCoursesForCreatedBy;

	/**
	 */
	@OneToMany(mappedBy = "userByUpdatedBy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolCourse> schoolCoursesForUpdatedBy;
	/**
	 */
	@OneToMany(mappedBy = "userByLpTeacherAssistantId", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItemsForLpTeacherAssistantId;
	/**
	 */
	@OneToMany(mappedBy = "userByLpTeacherSpeakerId", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItemsForLpTeacherSpeakerId;

	@OneToMany(mappedBy = "userByLpCheckUser", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItemsForLpCheckUser;

	@OneToMany(mappedBy = "userByLpCreateUser", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> operationItemsForLpCreateUser;

	/**
	 */
	@OneToMany(mappedBy = "userByTeacher", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolCourse> schoolCoursesForTeacher;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.UserCard> userCards;

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceTraining> labRoomDeviceTrainings;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDevicePermitUsers> labRoomDevicePermitUserses;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceTrainingPeople> labRoomDeviceTrainingPeoples;

	/**
	 */
	@OneToMany(mappedBy = "userByStudentNumber", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolCourseStudent> schoolCourseStudentsForStudentNumber;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableTeacherRelated> timetableTeacherRelateds;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.CreditOption> CreditOptions;
	/**
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_authority", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "username", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "authority_id", referencedColumnName = "id", nullable = false, updatable = false) })
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.Authority> authorities;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableSelfCourse> timetableSelfCourses;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoom> labRooms;

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableCourseStudent> timetableCourseStudents;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SchoolCourseDetail> schoolCourseDetails;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabReservation> labReservations;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabReservationAudit> labReservationAudits;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabReservationTimeTableStudent> labReservationTimeTableStudents;
	//新增
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabConstructionProjectAudit> labConstructionProjectAudits;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabConstructionPurchaseAudit> labConstructionPurchaseAudits;
	/**
	 *//*
	@ManyToMany(fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabConstructionProject> labConstructionProjects;*/
	/**
	 */
	@ManyToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)//2015-10-09 更改  原来是 @OneToMany
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.MLabConstructionProjectUser> MLabConstructionProjectUsers;

	/**
	 */
	@OneToMany(mappedBy = "userByKeeper", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)//2015-10-09 更改  原来是 userByApplicant
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabConstructionPurchase> labConstructionPurchasesForKeeper;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabConstructionProject> labConstructionProjects_1;
	/**
	 */
	@OneToMany(mappedBy = "userByCreatedBy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabConstructionProject> labConstructionProjects;


	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabConstructionFundingAudit> labConstructionFundingAudits;
	/**
	 */
	@OneToMany(mappedBy = "userByApplicant", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabConstructionPurchase> labConstructionPurchasesForApplicant;


	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.CommonDocument> commonDocument;
	/**
	 */
	@OneToMany(mappedBy = "userByReserveUser", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceReservation> labRoomDeviceReservationsForReserveUser;
	/**
	 */
	@OneToMany(mappedBy = "userByReserveUser", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceReservation> labRoomDeviceReservationsForTeacher;

	/**
	 */
	@OneToMany(mappedBy = "userByCreatedBy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SystemBuild> systemBuildsForCreatedBy;
	/**
	 */
	@OneToMany(mappedBy = "userByCreatedBy", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SystemBuild> systemBuildsForUpdatedBy;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceReservationResult> labRoomDeviceReservationResults;
	/**
	 */
	@ManyToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabReservation> labReservations_1;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.WkFolder> folders;

	@ManyToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationOutline> operationOutlineUser;
	/**
	 * 一对多（该用户发布的通知公告）
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TMessage> sendMessages;


	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationOutline> operationOutlines;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.NDeviceAuditRecord> NDeviceAuditRecords;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.NDevicePurchaseDetail> NDevicePurchaseDetails;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.NDevicePurchase> NDevicePurchases;

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.AssetApp> assetApps;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.AssetReceive> assetReceives;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.AssetCabinetWarehouseAccess> assetCabinetWarehouseAccesses;

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.AssetAdjustRecord> assetAdjustRecords;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.AssetAppAudit> assetAppAudits;
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.AssetReceiveAudit> assetReceiveAudits;

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceRepair> labRoomDeviceRepairs;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorker> labWorkers;

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomLendingResult> labRoomLendingResults;

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabWorkerTraining> labWorkerTrainings;

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomPermitUser> labRoomPermitUsers;

	/**
	 */
	@OneToMany(mappedBy = "userByDean", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomStationReservation> labRoomStationReservationsForTeacher;
	/**
	 */
	@OneToMany(mappedBy = "userByDean", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomStationReservation> labRoomStationReservationsForDean;

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomStationReservationResult> labRoomStationReservationResults;

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperItemAudit> operItemAudits;

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SoftwareReserve> softwareReserves;

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SoftwareReserveAudit> softwareReserveAudits;

	/**
	 */
	@OneToMany(mappedBy = "userByDepartmentHead", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceLending> labRoomDeviceLendingsForDepartmentHead;
	/**
	 */
	@OneToMany(mappedBy = "userByDepartmentHead", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceLending> labRoomDeviceLendingsForTeacher;
	/**
	 */
	@OneToMany(mappedBy = "userByDepartmentHead", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDeviceLending> labRoomDeviceLendingsForLendingUser;

	/**
	 */
	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableAppointmentChangeAduit> timetableAppointmentChangeAduits;

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.WorkerOption> workerOptions;

	public Set<LabReservation> getLabReservationForTeacher() {
		if(labReservationForTeacher == null){
			labReservationForTeacher = new HashSet<>();
		}
		return labReservationForTeacher;
	}

	public void setLabReservationForTeacher(Set<LabReservation> labReservationForTeacher) {
		this.labReservationForTeacher = labReservationForTeacher;
	}

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabReservation> labReservationForTeacher;

	/**
	 */
	public void setTimetableAppointmentChangeAduits(Set<TimetableAppointmentChangeAduit> timetableAppointmentChangeAduits) {
		this.timetableAppointmentChangeAduits = timetableAppointmentChangeAduits;
	}

	/**
	 */
	@JsonIgnore
	public Set<TimetableAppointmentChangeAduit> getTimetableAppointmentChangeAduits() {
		if (timetableAppointmentChangeAduits == null) {
			timetableAppointmentChangeAduits = new java.util.LinkedHashSet<net.zjcclims.domain.TimetableAppointmentChangeAduit>();
		}
		return timetableAppointmentChangeAduits;
	}

	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableAppointmentChange> timetableAppointmentChanges;
	public java.util.Set<net.zjcclims.domain.TimetableAppointmentChange> getTimetableAppointmentChanges() {
		return timetableAppointmentChanges;
	}

	public void setTimetableAppointmentChanges(
			java.util.Set<net.zjcclims.domain.TimetableAppointmentChange> timetableAppointmentChanges) {
		this.timetableAppointmentChanges = timetableAppointmentChanges;
	}

	public ChoseUser getChoseUser() {
		return choseUser;
	}

	public void setChoseUser(ChoseUser choseUser) {
		this.choseUser = choseUser;
	}

	@OneToOne(mappedBy = "user", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	net.zjcclims.domain.ChoseUser choseUser;

	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<SchoolCourseInfo> schoolCourseInfos;


	public Set<SchoolCourseInfo> getSchoolCourseInfos() {
		return schoolCourseInfos;
	}

	public void setSchoolCourseInfos(Set<SchoolCourseInfo> schoolCourseInfos) {
		this.schoolCourseInfos = schoolCourseInfos;
	}
	/**
	 */

	public void setLabRoomDeviceLendingsForDepartmentHead(Set<LabRoomDeviceLending> labRoomDeviceLendingsForDepartmentHead) {
		this.labRoomDeviceLendingsForDepartmentHead = labRoomDeviceLendingsForDepartmentHead;
	}

	public java.util.Set<net.zjcclims.domain.OperationOutline> getOperationOutlineUser() {
		return operationOutlineUser;
	}

	public void setOperationOutlineUser(
			java.util.Set<net.zjcclims.domain.OperationOutline> operationOutlineUser) {
		this.operationOutlineUser = operationOutlineUser;
	}

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.RoutineInspection> routineInspections;

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SpecialExamination> specialExaminations;

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabSecurityCheck> labSecurityChecks;

	/**
	 */
	@OneToMany(mappedBy = "reviewUser", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SpecialExamination> specialExaminationsforreviewUser;

	/**
	 */
	@OneToMany(mappedBy = "updateUser", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItem> updateUserOperationItems;

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ItemOpenTeacher> itemByOpenTeachers;

	/**
	 */
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.ItemLabUsers> itemByLabUsers;

	public Set<RoutineInspection> getRoutineInspections() {
		return routineInspections;
	}

	public void setRoutineInspections(Set<RoutineInspection> routineInspections) {
		this.routineInspections = routineInspections;
	}

	public Set<SpecialExamination> getSpecialExaminations() {
		return specialExaminations;
	}

	public void setSpecialExaminations(Set<SpecialExamination> specialExaminations) {
		this.specialExaminations = specialExaminations;
	}

	public Set<LabSecurityCheck> getLabSecurityChecks() {
		return labSecurityChecks;
	}

	public void setLabSecurityChecks(Set<LabSecurityCheck> labSecurityChecks) {
		this.labSecurityChecks = labSecurityChecks;
	}

	public Set<SpecialExamination> getSpecialExaminationsforreviewUser() {
		return specialExaminationsforreviewUser;
	}

	public void setSpecialExaminationsforreviewUser(Set<SpecialExamination> specialExaminationsforreviewUser) {
		this.specialExaminationsforreviewUser = specialExaminationsforreviewUser;
	}


	/**
	 */
	@JsonIgnore
	public Set<LabRoomDeviceLending> getLabRoomDeviceLendingsForDepartmentHead() {
		if (labRoomDeviceLendingsForDepartmentHead == null) {
			labRoomDeviceLendingsForDepartmentHead = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDeviceLending>();
		}
		return labRoomDeviceLendingsForDepartmentHead;
	}

	/**
	 */
	public void setLabRoomDeviceLendingsForTeacher(Set<LabRoomDeviceLending> labRoomDeviceLendingsForTeacher) {
		this.labRoomDeviceLendingsForTeacher = labRoomDeviceLendingsForTeacher;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDeviceLending> getLabRoomDeviceLendingsForTeacher() {
		if (labRoomDeviceLendingsForTeacher == null) {
			labRoomDeviceLendingsForTeacher = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDeviceLending>();
		}
		return labRoomDeviceLendingsForTeacher;
	}

	/**
	 */
	public void setLabRoomDeviceLendingsForLendingUser(Set<LabRoomDeviceLending> labRoomDeviceLendingsForLendingUser) {
		this.labRoomDeviceLendingsForLendingUser = labRoomDeviceLendingsForLendingUser;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDeviceLending> getLabRoomDeviceLendingsForLendingUser() {
		if (labRoomDeviceLendingsForLendingUser == null) {
			labRoomDeviceLendingsForLendingUser = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDeviceLending>();
		}
		return labRoomDeviceLendingsForLendingUser;
	}
	public java.util.Set<net.zjcclims.domain.SoftwareReserveAudit> getSoftwareReserveAudits() {
		return softwareReserveAudits;
	}

	public void setSoftwareReserveAudits(
			java.util.Set<net.zjcclims.domain.SoftwareReserveAudit> softwareReserveAudits) {
		this.softwareReserveAudits = softwareReserveAudits;
	}

	public java.util.Set<net.zjcclims.domain.SoftwareReserve> getSoftwareReserves() {
		return softwareReserves;
	}

	public void setSoftwareReserves(
			java.util.Set<net.zjcclims.domain.SoftwareReserve> softwareReserves) {
		this.softwareReserves = softwareReserves;
	}

	public java.util.Set<net.zjcclims.domain.OperItemAudit> getOperItemAudits() {
		return operItemAudits;
	}

	public void setOperItemAudits(
			java.util.Set<net.zjcclims.domain.OperItemAudit> operItemAudits) {
		this.operItemAudits = operItemAudits;
	}

	/**
	 */
	public void setLabRoomStationReservationsForDean(Set<LabRoomStationReservation> labRoomStationReservationsForDean) {
		this.labRoomStationReservationsForDean = labRoomStationReservationsForDean;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomStationReservation> getLabRoomStationReservationsForDean() {
		if (labRoomStationReservationsForDean == null) {
			labRoomStationReservationsForDean = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomStationReservation>();
		}
		return labRoomStationReservationsForDean;
	}
	/**
	 */
	public void setLabRoomStationReservationsForTeacher(Set<LabRoomStationReservation> labRoomStationReservationsForTeacher) {
		this.labRoomStationReservationsForTeacher = labRoomStationReservationsForTeacher;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomStationReservation> getLabRoomStationReservationsForTeacher() {
		if (labRoomStationReservationsForTeacher == null) {
			labRoomStationReservationsForTeacher = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomStationReservation>();
		}
		return labRoomStationReservationsForTeacher;
	}
	/**
	 */
	public void setLabRoomStationReservationResults(Set<LabRoomStationReservationResult> labRoomStationReservationResults) {
		this.labRoomStationReservationResults = labRoomStationReservationResults;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomStationReservationResult> getLabRoomStationReservationResults() {
		if (labRoomStationReservationResults == null) {
			labRoomStationReservationResults = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomStationReservationResult>();
		}
		return labRoomStationReservationResults;
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
	@OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
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

	/**
	 */
	public void setOperationOutlines(Set<OperationOutline> operationOutlines) {
		this.operationOutlines = operationOutlines;
	}

	/**
	 */
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
	public void setAssetApps(Set<AssetApp> assetApps) {
		this.assetApps = assetApps;
	}

	/**
	 */
	@JsonIgnore
	public Set<AssetApp> getAssetApps() {
		if (assetApps == null) {
			assetApps = new java.util.LinkedHashSet<net.zjcclims.domain.AssetApp>();
		}
		return assetApps;
	}

	/**
	 */
	public void setAssetReceives(Set<AssetReceive> assetReceives) {
		this.assetReceives = assetReceives;
	}

	/**
	 */
	@JsonIgnore
	public Set<AssetReceive> getAssetReceives() {
		if (assetReceives == null) {
			assetReceives = new java.util.LinkedHashSet<net.zjcclims.domain.AssetReceive>();
		}
		return assetReceives;
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
	public void setAssetAdjustRecords(Set<AssetAdjustRecord> assetAdjustRecords) {
		this.assetAdjustRecords = assetAdjustRecords;
	}

	/**
	 */
	@JsonIgnore
	public Set<AssetAdjustRecord> getAssetAdjustRecords() {
		if (assetAdjustRecords == null) {
			assetAdjustRecords = new java.util.LinkedHashSet<net.zjcclims.domain.AssetAdjustRecord>();
		}
		return assetAdjustRecords;
	}

	/**
	 */
	public void setAssetAppAudits(Set<AssetAppAudit> assetAppAudits) {
		this.assetAppAudits = assetAppAudits;
	}

	/**
	 */
	@JsonIgnore
	public Set<AssetAppAudit> getAssetAppAudits() {
		if (assetAppAudits == null) {
			assetAppAudits = new java.util.LinkedHashSet<net.zjcclims.domain.AssetAppAudit>();
		}
		return assetAppAudits;
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


	@JsonIgnore
	public java.util.Set<net.zjcclims.domain.LabReservation> getLabReservations() {
		return labReservations;
	}

	public void setLabReservations(
			java.util.Set<net.zjcclims.domain.LabReservation> labReservations) {
		this.labReservations = labReservations;
	}

	@JsonIgnore
	public java.util.Set<net.zjcclims.domain.LabRoom> getLabRooms() {
		return labRooms;
	}

	public void setLabRooms(java.util.Set<net.zjcclims.domain.LabRoom> labRooms) {
		this.labRooms = labRooms;
	}

	@JsonIgnore
	public java.util.Set<net.zjcclims.domain.CreditOption> getCreditOptions() {
		return CreditOptions;
	}

	public void setCreditOptions(java.util.Set<net.zjcclims.domain.CreditOption> creditOptions) {
		this.CreditOptions = creditOptions;
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
	public java.util.Set<net.zjcclims.domain.LabReservationTimeTableStudent> getLabReservationTimeTableStudents() {
		return labReservationTimeTableStudents;
	}

	public void setLabReservationTimeTableStudents(
			java.util.Set<net.zjcclims.domain.LabReservationTimeTableStudent> labReservationTimeTableStudents) {
		this.labReservationTimeTableStudents = labReservationTimeTableStudents;
	}

	/**
	 */
	public void setCMajorDirection(CMajorDirection CMajorDirection) {
		this.CMajorDirection = CMajorDirection;
	}

	/**
	 */
	@JsonIgnore
	public CMajorDirection getCMajorDirection() {
		return CMajorDirection;
	}


	/**
	 */
	@OneToMany(mappedBy = "userByTutor", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.PreTimetableAppointment> preTimetableAppointmentsForTutor;
	/**
	 */
	@OneToMany(mappedBy = "userByTutor", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.PreTimetableAppointment> preTimetableAppointmentsForTeacher;

	/**
	 */
	public void setPreTimetableAppointmentsForTutor(Set<PreTimetableAppointment> preTimetableAppointmentsForTutor) {
		this.preTimetableAppointmentsForTutor = preTimetableAppointmentsForTutor;
	}

	/**
	 */
	@JsonIgnore
	public Set<PreTimetableAppointment> getPreTimetableAppointmentsForTutor() {
		if (preTimetableAppointmentsForTutor == null) {
			preTimetableAppointmentsForTutor = new java.util.LinkedHashSet<net.zjcclims.domain.PreTimetableAppointment>();
		}
		return preTimetableAppointmentsForTutor;
	}

	/**
	 */
	public void setPreTimetableAppointmentsForTeacher(Set<PreTimetableAppointment> preTimetableAppointmentsForTeacher) {
		this.preTimetableAppointmentsForTeacher = preTimetableAppointmentsForTeacher;
	}

	/**
	 */
	@JsonIgnore
	public Set<PreTimetableAppointment> getPreTimetableAppointmentsForTeacher() {
		if (preTimetableAppointmentsForTeacher == null) {
			preTimetableAppointmentsForTeacher = new java.util.LinkedHashSet<net.zjcclims.domain.PreTimetableAppointment>();
		}
		return preTimetableAppointmentsForTeacher;
	}




	/**
	 * ѧ��/����
	 *
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * ѧ��/����
	 *
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * ����
	 *
	 */
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	/**
	 * ����
	 *
	 */
	public String getCardno() {
		return this.cardno;
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
	 * ����
	 *
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * ����
	 *
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * �Ա�
	 *
	 */
	public void setUserSexy(String userSexy) {
		this.userSexy = userSexy;
	}

	/**
	 * �Ա�
	 *
	 */
	public String getUserSexy() {
		return this.userSexy;
	}

	/**
	 * �Ƿ���У
	 *
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	/**
	 * �Ƿ���У
	 *
	 */
	public String getUserStatus() {
		return this.userStatus;
	}

	/**
	 * ����Ա����
	 *
	 */
	public void setTeacherNumber(Integer teacherNumber) {
		this.teacherNumber = teacherNumber;
	}

	/**
	 * ����Ա����
	 *
	 */
	public Integer getTeacherNumber() {
		return this.teacherNumber;
	}

	/**
	 * רҵ����
	 *
	 */
	public void setMajorNumber(String majorNumber) {
		this.majorNumber = majorNumber;
	}

	/**
	 * רҵ����
	 *
	 */
	public String getMajorNumber() {
		return this.majorNumber;
	}

	/**
	 * �û���ɫ��0��ѧ��1�ǽ�ʦ��
	 *
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	/**
	 * �û���ɫ��0��ѧ��1�ǽ�ʦ��
	 *
	 */
	public String getUserRole() {
		return this.userRole;
	}

	/**
	 * ����¼ʱ��
	 *
	 */
	public void setLastLogin(Calendar lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * ����¼ʱ��
	 *
	 */
	public Calendar getLastLogin() {
		return this.lastLogin;
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

	public Integer getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(Integer creditScore) {
		this.creditScore = creditScore;
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
	 * ��ϵ�绰
	 *
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * ��ϵ�绰
	 *
	 */
	public String getTelephone() {
		return this.telephone;
	}

	/**
	 * �ʼ�
	 *
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * �ʼ�
	 *
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * �Ƿ����
	 *
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * �Ƿ����
	 *
	 */
	public Boolean getEnabled() {
		return this.enabled;
	}

	/**
	 * ѧ��״̬
	 *
	 */
	public void setEnrollmentStatus(Integer enrollmentStatus) {
		this.enrollmentStatus = enrollmentStatus;
	}

	/**
	 * ѧ��״̬
	 *
	 */
	public Integer getEnrollmentStatus() {
		return this.enrollmentStatus;
	}

	/**
	 * �Ƿ��ڼ�
	 *
	 */
	public void setIfEnrollment(String ifEnrollment) {
		this.ifEnrollment = ifEnrollment;
	}

	/**
	 * �Ƿ��ڼ�
	 *
	 */
	public String getIfEnrollment() {
		return this.ifEnrollment;
	}

	/**
	 * ѧ�����
	 *
	 */
	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	/**
	 * ѧ�����
	 *
	 */
	public Integer getUserType() {
		return this.userType;
	}

	/**
	 * ��ѧ���
	 *
	 */
	public void setAttendanceTime(String attendanceTime) {
		this.attendanceTime = attendanceTime;
	}

	/**
	 * ��ѧ���
	 *
	 */
	public String getAttendanceTime() {
		return this.attendanceTime;
	}

	/**
	 * �����꼶
	 *
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**
	 * �����꼶
	 *
	 */
	public String getGrade() {
		return this.grade;
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
	public void setResearchProject(ResearchProject researchProject) {
		this.researchProject = researchProject;
	}

	/**
	 */
	@JsonIgnore
	public ResearchProject getResearchProject() {
		return researchProject;
	}

	/**
	 */
	public void setTimetableAttendancesForUserNumber(Set<TimetableAttendance> timetableAttendancesForUserNumber) {
		this.timetableAttendancesForUserNumber = timetableAttendancesForUserNumber;
	}

	/**
	 */
	@JsonIgnore
	public Set<TimetableAttendance> getTimetableAttendancesForUserNumber() {
		if (timetableAttendancesForUserNumber == null) {
			timetableAttendancesForUserNumber = new java.util.LinkedHashSet<net.zjcclims.domain.TimetableAttendance>();
		}
		return timetableAttendancesForUserNumber;
	}

	/**
	 */
	public void setLabCentersForCenterManager(Set<LabCenter> labCentersForCenterManager) {
		this.labCentersForCenterManager = labCentersForCenterManager;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabCenter> getLabCentersForCenterManager() {
		if (labCentersForCenterManager == null) {
			labCentersForCenterManager = new java.util.LinkedHashSet<net.zjcclims.domain.LabCenter>();
		}
		return labCentersForCenterManager;
	}

	/**
	 */
	public void setLabCentersForCreatedBy(Set<LabCenter> labCentersForCreatedBy) {
		this.labCentersForCreatedBy = labCentersForCreatedBy;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabCenter> getLabCentersForCreatedBy() {
		if (labCentersForCreatedBy == null) {
			labCentersForCreatedBy = new java.util.LinkedHashSet<net.zjcclims.domain.LabCenter>();
		}
		return labCentersForCreatedBy;
	}

	/**
	 */
	public void setLabCentersForUpdatedBy(Set<LabCenter> labCentersForUpdatedBy) {
		this.labCentersForUpdatedBy = labCentersForUpdatedBy;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabCenter> getLabCentersForUpdatedBy() {
		if (labCentersForUpdatedBy == null) {
			labCentersForUpdatedBy = new java.util.LinkedHashSet<net.zjcclims.domain.LabCenter>();
		}
		return labCentersForUpdatedBy;
	}

	/**
	 */
	public void setSchoolDevicesForUserNumber(Set<SchoolDevice> schoolDevicesForUserNumber) {
		this.schoolDevicesForUserNumber = schoolDevicesForUserNumber;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolDevice> getSchoolDevicesForUserNumber() {
		if (schoolDevicesForUserNumber == null) {
			schoolDevicesForUserNumber = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolDevice>();
		}
		return schoolDevicesForUserNumber;
	}

	/**
	 */
	public void setTimetableTutorRelateds(Set<TimetableTutorRelated> timetableTutorRelateds) {
		this.timetableTutorRelateds = timetableTutorRelateds;
	}

	/**
	 */
	@JsonIgnore
	public Set<TimetableTutorRelated> getTimetableTutorRelateds() {
		if (timetableTutorRelateds == null) {
			timetableTutorRelateds = new java.util.LinkedHashSet<net.zjcclims.domain.TimetableTutorRelated>();
		}
		return timetableTutorRelateds;
	}

	/**
	 */
	public void setTimetableGroupStudentses(Set<TimetableGroupStudents> timetableGroupStudentses) {
		this.timetableGroupStudentses = timetableGroupStudentses;
	}

	/**
	 */
	@JsonIgnore
	public Set<TimetableGroupStudents> getTimetableGroupStudentses() {
		if (timetableGroupStudentses == null) {
			timetableGroupStudentses = new java.util.LinkedHashSet<net.zjcclims.domain.TimetableGroupStudents>();
		}
		return timetableGroupStudentses;
	}

	/**
	 */
	public void setSchoolDevicesForKeepUser(Set<SchoolDevice> schoolDevicesForKeepUser) {
		this.schoolDevicesForKeepUser = schoolDevicesForKeepUser;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolDevice> getSchoolDevicesForKeepUser() {
		if (schoolDevicesForKeepUser == null) {
			schoolDevicesForKeepUser = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolDevice>();
		}
		return schoolDevicesForKeepUser;
	}

	public java.util.Set<net.zjcclims.domain.OperationItem> getOperationItemsForLpCreateUser() {
		return operationItemsForLpCreateUser;
	}

	public void setOperationItemsForLpCreateUser(
			java.util.Set<net.zjcclims.domain.OperationItem> operationItemsForLpCreateUser) {
		this.operationItemsForLpCreateUser = operationItemsForLpCreateUser;
	}

	/**
	 */
	public void setTimetableAttendancesForCreatedBy(Set<TimetableAttendance> timetableAttendancesForCreatedBy) {
		this.timetableAttendancesForCreatedBy = timetableAttendancesForCreatedBy;
	}

	/**
	 */
	@JsonIgnore
	public Set<TimetableAttendance> getTimetableAttendancesForCreatedBy() {
		if (timetableAttendancesForCreatedBy == null) {
			timetableAttendancesForCreatedBy = new java.util.LinkedHashSet<net.zjcclims.domain.TimetableAttendance>();
		}
		return timetableAttendancesForCreatedBy;
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
	public void setSchoolCourseStudentsForTeacherNumber(Set<SchoolCourseStudent> schoolCourseStudentsForTeacherNumber) {
		this.schoolCourseStudentsForTeacherNumber = schoolCourseStudentsForTeacherNumber;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolCourseStudent> getSchoolCourseStudentsForTeacherNumber() {
		if (schoolCourseStudentsForTeacherNumber == null) {
			schoolCourseStudentsForTeacherNumber = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourseStudent>();
		}
		return schoolCourseStudentsForTeacherNumber;
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
	public void setSchoolCoursesForCreatedBy(Set<SchoolCourse> schoolCoursesForCreatedBy) {
		this.schoolCoursesForCreatedBy = schoolCoursesForCreatedBy;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolCourse> getSchoolCoursesForCreatedBy() {
		if (schoolCoursesForCreatedBy == null) {
			schoolCoursesForCreatedBy = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourse>();
		}
		return schoolCoursesForCreatedBy;
	}

	/**
	 */
	public void setSchoolCoursesForUpdatedBy(Set<SchoolCourse> schoolCoursesForUpdatedBy) {
		this.schoolCoursesForUpdatedBy = schoolCoursesForUpdatedBy;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolCourse> getSchoolCoursesForUpdatedBy() {
		if (schoolCoursesForUpdatedBy == null) {
			schoolCoursesForUpdatedBy = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourse>();
		}
		return schoolCoursesForUpdatedBy;
	}

	/**
	 */
	public void setOperationItemsForLpTeacherAssistantId(Set<OperationItem> operationItemsForLpTeacherAssistantId) {
		this.operationItemsForLpTeacherAssistantId = operationItemsForLpTeacherAssistantId;
	}

	public java.util.Set<net.zjcclims.domain.OperationItem> getOperationItemsForLpCheckUser() {
		return operationItemsForLpCheckUser;
	}

	public void setOperationItemsForLpCheckUser(
			java.util.Set<net.zjcclims.domain.OperationItem> operationItemsForLpCheckUser) {
		this.operationItemsForLpCheckUser = operationItemsForLpCheckUser;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationItem> getOperationItemsForLpTeacherAssistantId() {
		if (operationItemsForLpTeacherAssistantId == null) {
			operationItemsForLpTeacherAssistantId = new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>();
		}
		return operationItemsForLpTeacherAssistantId;
	}

	/**
	 */
	public void setOperationItemsForLpTeacherSpeakerId(Set<OperationItem> operationItemsForLpTeacherSpeakerId) {
		this.operationItemsForLpTeacherSpeakerId = operationItemsForLpTeacherSpeakerId;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationItem> getOperationItemsForLpTeacherSpeakerId() {
		if (operationItemsForLpTeacherSpeakerId == null) {
			operationItemsForLpTeacherSpeakerId = new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>();
		}
		return operationItemsForLpTeacherSpeakerId;
	}

	/**
	 */
	public void setSchoolCoursesForTeacher(Set<SchoolCourse> schoolCoursesForTeacher) {
		this.schoolCoursesForTeacher = schoolCoursesForTeacher;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolCourse> getSchoolCoursesForTeacher() {
		if (schoolCoursesForTeacher == null) {
			schoolCoursesForTeacher = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourse>();
		}
		return schoolCoursesForTeacher;
	}

	/**
	 */
	public void setUserCards(Set<UserCard> userCards) {
		this.userCards = userCards;
	}

	/**
	 */
	@JsonIgnore
	public Set<UserCard> getUserCards() {
		if (userCards == null) {
			userCards = new java.util.LinkedHashSet<net.zjcclims.domain.UserCard>();
		}
		return userCards;
	}

	/**
	 */
	public void setSchoolCourseStudentsForStudentNumber(Set<SchoolCourseStudent> schoolCourseStudentsForStudentNumber) {
		this.schoolCourseStudentsForStudentNumber = schoolCourseStudentsForStudentNumber;
	}

	/**
	 */
	@JsonIgnore
	public Set<SchoolCourseStudent> getSchoolCourseStudentsForStudentNumber() {
		if (schoolCourseStudentsForStudentNumber == null) {
			schoolCourseStudentsForStudentNumber = new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourseStudent>();
		}
		return schoolCourseStudentsForStudentNumber;
	}

	/**
	 */
	public void setTimetableTeacherRelateds(Set<TimetableTeacherRelated> timetableTeacherRelateds) {
		this.timetableTeacherRelateds = timetableTeacherRelateds;
	}

	/**
	 */
	@JsonIgnore
	public Set<TimetableTeacherRelated> getTimetableTeacherRelateds() {
		if (timetableTeacherRelateds == null) {
			timetableTeacherRelateds = new java.util.LinkedHashSet<net.zjcclims.domain.TimetableTeacherRelated>();
		}
		return timetableTeacherRelateds;
	}

	/**
	 */
	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	/**
	 */
	@JsonIgnore
	public Set<Authority> getAuthorities() {
		if (authorities == null) {
			authorities = new java.util.LinkedHashSet<net.zjcclims.domain.Authority>();
		}
		return authorities;
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

	public SchoolClasses getSchoolClasses() {
		return schoolClasses;
	}

	public void setSchoolClasses(SchoolClasses schoolClasses) {
		this.schoolClasses = schoolClasses;
	}

	/**
	 */
	public void setTimetableCourseStudents(Set<TimetableCourseStudent> timetableCourseStudents) {
		this.timetableCourseStudents = timetableCourseStudents;
	}

	/**
	 */
	@JsonIgnore
	public Set<TimetableCourseStudent> getTimetableCourseStudents() {
		if (timetableCourseStudents == null) {
			timetableCourseStudents = new java.util.LinkedHashSet<net.zjcclims.domain.TimetableCourseStudent>();
		}
		return timetableCourseStudents;
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
	 *//*
	public void setLabConstructionProjects(Set<LabConstructionProject> labConstructionProjects) {
		this.labConstructionProjects = labConstructionProjects;
	}

	*//**
	 *//*
	@JsonIgnore
	public Set<LabConstructionProject> getLabConstructionProjects() {
		if (labConstructionProjects == null) {
			labConstructionProjects = new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionProject>();
		}
		return labConstructionProjects;
	}*/

	/**
	 */
	public void setMLabConstructionProjectUsers(Set<MLabConstructionProjectUser> MLabConstructionProjectUsers) {
		this.MLabConstructionProjectUsers = MLabConstructionProjectUsers;
	}

	/**
	 */
	@JsonIgnore
	public Set<MLabConstructionProjectUser> getMLabConstructionProjectUsers() {
		if (MLabConstructionProjectUsers == null) {
			MLabConstructionProjectUsers = new java.util.LinkedHashSet<net.zjcclims.domain.MLabConstructionProjectUser>();
		}
		return MLabConstructionProjectUsers;
	}
	/**
	 */
	public void setLabConstructionPurchasesForKeeper(Set<LabConstructionPurchase> labConstructionPurchasesForKeeper) {
		this.labConstructionPurchasesForKeeper = labConstructionPurchasesForKeeper;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabConstructionPurchase> getLabConstructionPurchasesForKeeper() {
		if (labConstructionPurchasesForKeeper == null) {
			labConstructionPurchasesForKeeper = new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionPurchase>();
		}
		return labConstructionPurchasesForKeeper;
	}

	/**
	 */
	public void setLabConstructionProjects_1(Set<LabConstructionProject> labConstructionProjects_1) {
		this.labConstructionProjects_1 = labConstructionProjects_1;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabConstructionProject> getLabConstructionProjects_1() {
		if (labConstructionProjects_1 == null) {
			labConstructionProjects_1 = new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionProject>();
		}
		return labConstructionProjects_1;
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
	public void setLabConstructionPurchasesForApplicant(Set<LabConstructionPurchase> labConstructionPurchasesForApplicant) {
		this.labConstructionPurchasesForApplicant = labConstructionPurchasesForApplicant;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabConstructionPurchase> getLabConstructionPurchasesForApplicant() {
		if (labConstructionPurchasesForApplicant == null) {
			labConstructionPurchasesForApplicant = new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionPurchase>();
		}
		return labConstructionPurchasesForApplicant;
	}



	@JsonIgnore
	public java.util.Set<net.zjcclims.domain.CommonDocument> getCommonDocument() {
		return commonDocument;
	}

	public void setCommonDocument(
			java.util.Set<net.zjcclims.domain.CommonDocument> commonDocument) {
		this.commonDocument = commonDocument;
	}

	public java.util.Set<net.zjcclims.domain.LabRoomDeviceTraining> getLabRoomDeviceTrainings() {
		return labRoomDeviceTrainings;
	}

	public void setLabRoomDeviceTrainings(
			java.util.Set<net.zjcclims.domain.LabRoomDeviceTraining> labRoomDeviceTrainings) {
		this.labRoomDeviceTrainings = labRoomDeviceTrainings;
	}

	public java.util.Set<net.zjcclims.domain.LabRoomDevicePermitUsers> getLabRoomDevicePermitUserses() {
		return labRoomDevicePermitUserses;
	}

	public void setLabRoomDevicePermitUserses(
			java.util.Set<net.zjcclims.domain.LabRoomDevicePermitUsers> labRoomDevicePermitUserses) {
		this.labRoomDevicePermitUserses = labRoomDevicePermitUserses;
	}

	public java.util.Set<net.zjcclims.domain.LabRoomDeviceTrainingPeople> getLabRoomDeviceTrainingPeoples() {
		return labRoomDeviceTrainingPeoples;
	}

	public void setLabRoomDeviceTrainingPeoples(
			java.util.Set<net.zjcclims.domain.LabRoomDeviceTrainingPeople> labRoomDeviceTrainingPeoples) {
		this.labRoomDeviceTrainingPeoples = labRoomDeviceTrainingPeoples;
	}

	public java.util.Set<net.zjcclims.domain.LabConstructionProject> getLabConstructionProjects() {
		return labConstructionProjects;
	}

	public void setLabConstructionProjects(
			java.util.Set<net.zjcclims.domain.LabConstructionProject> labConstructionProjects) {
		this.labConstructionProjects = labConstructionProjects;
	}

	public java.util.Set<net.zjcclims.domain.LabRoomDeviceReservation> getLabRoomDeviceReservationsForReserveUser() {
		return labRoomDeviceReservationsForReserveUser;
	}

	public void setLabRoomDeviceReservationsForReserveUser(
			java.util.Set<net.zjcclims.domain.LabRoomDeviceReservation> labRoomDeviceReservationsForReserveUser) {
		this.labRoomDeviceReservationsForReserveUser = labRoomDeviceReservationsForReserveUser;
	}

	public java.util.Set<net.zjcclims.domain.LabRoomDeviceReservation> getLabRoomDeviceReservationsForTeacher() {
		return labRoomDeviceReservationsForTeacher;
	}

	public void setLabRoomDeviceReservationsForTeacher(
			java.util.Set<net.zjcclims.domain.LabRoomDeviceReservation> labRoomDeviceReservationsForTeacher) {
		this.labRoomDeviceReservationsForTeacher = labRoomDeviceReservationsForTeacher;
	}


	public java.util.Set<net.zjcclims.domain.SystemBuild> getSystemBuildsForCreatedBy() {
		return systemBuildsForCreatedBy;
	}

	public void setSystemBuildsForCreatedBy(
			java.util.Set<net.zjcclims.domain.SystemBuild> systemBuildsForCreatedBy) {
		this.systemBuildsForCreatedBy = systemBuildsForCreatedBy;
	}

	public java.util.Set<net.zjcclims.domain.SystemBuild> getSystemBuildsForUpdatedBy() {
		return systemBuildsForUpdatedBy;
	}

	public void setSystemBuildsForUpdatedBy(
			java.util.Set<net.zjcclims.domain.SystemBuild> systemBuildsForUpdatedBy) {
		this.systemBuildsForUpdatedBy = systemBuildsForUpdatedBy;
	}

	public java.util.Set<net.zjcclims.domain.LabRoomDeviceReservationResult> getLabRoomDeviceReservationResults() {
		return labRoomDeviceReservationResults;
	}

	public void setLabRoomDeviceReservationResults(
			java.util.Set<net.zjcclims.domain.LabRoomDeviceReservationResult> labRoomDeviceReservationResults) {
		this.labRoomDeviceReservationResults = labRoomDeviceReservationResults;
	}

	public java.util.Set<net.zjcclims.domain.LabReservation> getLabReservations_1() {
		return labReservations_1;
	}

	public void setLabReservations_1(
			java.util.Set<net.zjcclims.domain.LabReservation> labReservations_1) {
		this.labReservations_1 = labReservations_1;
	}

	public java.util.Set<net.zjcclims.domain.WkFolder> getFolders() {
		return folders;
	}

	public void setFolders(java.util.Set<net.zjcclims.domain.WkFolder> folders) {
		this.folders = folders;
	}



	public java.util.Set<net.zjcclims.domain.TMessage> getSendMessages() {
		return sendMessages;
	}

	public void setSendMessages(
			java.util.Set<net.zjcclims.domain.TMessage> sendMessages) {
		this.sendMessages = sendMessages;
	}

	/**
	 */
	public void setLabRoomDeviceRepairs(Set<LabRoomDeviceRepair> labRoomDeviceRepairs) {
		this.labRoomDeviceRepairs = labRoomDeviceRepairs;
	}

	/**
	 */
	@JsonIgnore
	public Set<LabRoomDeviceRepair> getLabRoomDeviceRepairs() {
		if (labRoomDeviceRepairs == null) {
			labRoomDeviceRepairs = new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDeviceRepair>();
		}
		return labRoomDeviceRepairs;
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
	/**
	 */
	public void setNDeviceAuditRecords(Set<NDeviceAuditRecord> NDeviceAuditRecords) {
		this.NDeviceAuditRecords = NDeviceAuditRecords;
	}

	/**
	 */
	@JsonIgnore
	public Set<NDeviceAuditRecord> getNDeviceAuditRecords() {
		if (NDeviceAuditRecords == null) {
			NDeviceAuditRecords = new java.util.LinkedHashSet<net.zjcclims.domain.NDeviceAuditRecord>();
		}
		return NDeviceAuditRecords;
	}

	/**
	 */
	public void setNDevicePurchaseDetails(Set<NDevicePurchaseDetail> NDevicePurchaseDetails) {
		this.NDevicePurchaseDetails = NDevicePurchaseDetails;
	}

	/**
	 */
	@JsonIgnore
	public Set<NDevicePurchaseDetail> getNDevicePurchaseDetails() {
		if (NDevicePurchaseDetails == null) {
			NDevicePurchaseDetails = new java.util.LinkedHashSet<net.zjcclims.domain.NDevicePurchaseDetail>();
		}
		return NDevicePurchaseDetails;
	}

	/**
	 */
	public void setNDevicePurchases(Set<NDevicePurchase> NDevicePurchases) {
		this.NDevicePurchases = NDevicePurchases;
	}

	/**
	 */
	@JsonIgnore
	public Set<NDevicePurchase> getNDevicePurchases() {
		if (NDevicePurchases == null) {
			NDevicePurchases = new java.util.LinkedHashSet<net.zjcclims.domain.NDevicePurchase>();
		}
		return NDevicePurchases;
	}
	public void setLabConstructApps(Set<LabConstructApp> labConstructApps) {
		this.labConstructApps = labConstructApps;
	}

	/**
	 */
	public Set<LabConstructApp> getLabConstructApps() {
		if (labConstructApps == null) {
			labConstructApps = new java.util.LinkedHashSet<LabConstructApp>();
		}
		return labConstructApps;
	}

	public void setLabConstructAppApprovals(Set<LabConstructAppApproval> labConstructAppApprovals) {
		this.labConstructAppApprovals = labConstructAppApprovals;
	}

	/**
	 */
	public Set<LabConstructAppApproval> getLabConstructAppApprovals() {
		if (labConstructAppApprovals == null) {
			labConstructAppApprovals = new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructAppApproval>();
		}
		return labConstructAppApprovals;
	}

	public Set<OperationItem> getUpdateUserOperationItems() {
		if (updateUserOperationItems == null) {
			updateUserOperationItems = new java.util.LinkedHashSet<>();
		}
		return updateUserOperationItems;
	}

	public void setUpdateUserOperationItems(Set<OperationItem> updateUserOperationItems) {
		this.updateUserOperationItems = updateUserOperationItems;
	}

	public Set<ItemOpenTeacher> getItemByOpenTeachers() {
		return itemByOpenTeachers;
	}

	public void setItemByOpenTeachers(Set<ItemOpenTeacher> itemByOpenTeachers) {
		this.itemByOpenTeachers = itemByOpenTeachers;
	}

	public Set<ItemLabUsers> getItemByLabUsers() {
		return itemByLabUsers;
	}

	public void setItemByLabUsers(Set<ItemLabUsers> itemByLabUsers) {
		this.itemByLabUsers = itemByLabUsers;
	}

	/**
	 */
	public User() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(User that) {
		setUsername(that.getUsername());
		setCardno(that.getCardno());
		setCname(that.getCname());
		setPassword(that.getPassword());
		setUserSexy(that.getUserSexy());
		setUserStatus(that.getUserStatus());
		setTeacherNumber(that.getTeacherNumber());
		setMajorNumber(that.getMajorNumber());
		setUserRole(that.getUserRole());
		setLastLogin(that.getLastLogin());
		setCreatedAt(that.getCreatedAt());
		setUpdatedAt(that.getUpdatedAt());
		setTelephone(that.getTelephone());
		setEmail(that.getEmail());
		setEnabled(that.getEnabled());
		setEnrollmentStatus(that.getEnrollmentStatus());
		setIfEnrollment(that.getIfEnrollment());
		setUserType(that.getUserType());
		setAttendanceTime(that.getAttendanceTime());
		setGrade(that.getGrade());
		setSchoolAcademy(that.getSchoolAcademy());
		seteNname(that.geteNname());
		setTimetableAttendancesForUserNumber(new java.util.LinkedHashSet<net.zjcclims.domain.TimetableAttendance>(that.getTimetableAttendancesForUserNumber()));
		setLabCentersForCenterManager(new java.util.LinkedHashSet<net.zjcclims.domain.LabCenter>(that.getLabCentersForCenterManager()));
		setLabCentersForCreatedBy(new java.util.LinkedHashSet<net.zjcclims.domain.LabCenter>(that.getLabCentersForCreatedBy()));
		setLabCentersForUpdatedBy(new java.util.LinkedHashSet<net.zjcclims.domain.LabCenter>(that.getLabCentersForUpdatedBy()));
		setSchoolDevicesForUserNumber(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolDevice>(that.getSchoolDevicesForUserNumber()));
		setTimetableTutorRelateds(new java.util.LinkedHashSet<net.zjcclims.domain.TimetableTutorRelated>(that.getTimetableTutorRelateds()));
		setTimetableGroupStudentses(new java.util.LinkedHashSet<net.zjcclims.domain.TimetableGroupStudents>(that.getTimetableGroupStudentses()));
		setSchoolDevicesForKeepUser(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolDevice>(that.getSchoolDevicesForKeepUser()));
		setTimetableAttendancesForCreatedBy(new java.util.LinkedHashSet<net.zjcclims.domain.TimetableAttendance>(that.getTimetableAttendancesForCreatedBy()));
		setLabRoomAdmins(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomAdmin>(that.getLabRoomAdmins()));
		setSchoolCourseStudentsForTeacherNumber(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourseStudent>(that.getSchoolCourseStudentsForTeacherNumber()));
		setLabRoomDevices(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>(that.getLabRoomDevices()));
		setSchoolCoursesForCreatedBy(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourse>(that.getSchoolCoursesForCreatedBy()));
		setSchoolCoursesForUpdatedBy(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourse>(that.getSchoolCoursesForUpdatedBy()));
		setOperationItemsForLpTeacherAssistantId(new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>(that.getOperationItemsForLpTeacherAssistantId()));
		setOperationItemsForLpTeacherSpeakerId(new java.util.LinkedHashSet<net.zjcclims.domain.OperationItem>(that.getOperationItemsForLpTeacherSpeakerId()));
		setSchoolCoursesForTeacher(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourse>(that.getSchoolCoursesForTeacher()));
		setUserCards(new java.util.LinkedHashSet<net.zjcclims.domain.UserCard>(that.getUserCards()));
		setSchoolCourseStudentsForStudentNumber(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourseStudent>(that.getSchoolCourseStudentsForStudentNumber()));
		setTimetableTeacherRelateds(new java.util.LinkedHashSet<net.zjcclims.domain.TimetableTeacherRelated>(that.getTimetableTeacherRelateds()));
		setAuthorities(new java.util.LinkedHashSet<net.zjcclims.domain.Authority>(that.getAuthorities()));
		setTimetableSelfCourses(new java.util.LinkedHashSet<net.zjcclims.domain.TimetableSelfCourse>(that.getTimetableSelfCourses()));
		setTimetableCourseStudents(new java.util.LinkedHashSet<net.zjcclims.domain.TimetableCourseStudent>(that.getTimetableCourseStudents()));
		setSchoolCourseDetails(new java.util.LinkedHashSet<net.zjcclims.domain.SchoolCourseDetail>(that.getSchoolCourseDetails()));
		//新增
		setLabConstructionProjectAudits(new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionProjectAudit>(that.getLabConstructionProjectAudits()));
		setLabConstructionPurchaseAudits(new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionPurchaseAudit>(that.getLabConstructionPurchaseAudits()));
//		setLabConstructionProjects(new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionProject>(that.getLabConstructionProjects()));
		setMLabConstructionProjectUsers(new java.util.LinkedHashSet<net.zjcclims.domain.MLabConstructionProjectUser>(that.getMLabConstructionProjectUsers()));
		setLabConstructionPurchasesForKeeper(new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionPurchase>(that.getLabConstructionPurchasesForKeeper()));
		setLabConstructionProjects_1(new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionProject>(that.getLabConstructionProjects_1()));
		setLabConstructionFundingAudits(new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionFundingAudit>(that.getLabConstructionFundingAudits()));
		setLabConstructionPurchasesForApplicant(new java.util.LinkedHashSet<net.zjcclims.domain.LabConstructionPurchase>(that.getLabConstructionPurchasesForApplicant()));
		setCommonDocument(that.getCommonDocument());
		setOperationOutlines(new java.util.LinkedHashSet<net.zjcclims.domain.OperationOutline>(that.getOperationOutlines()));
		setLabWorkerTrainings(new java.util.LinkedHashSet<net.zjcclims.domain.LabWorkerTraining>(that.getLabWorkerTrainings()));
		setLabRoomPermitUsers(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomPermitUser>(that.getLabRoomPermitUsers()));
		setLabRoomDeviceLendingsForDepartmentHead(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDeviceLending>(that.getLabRoomDeviceLendingsForDepartmentHead()));
		setLabRoomDeviceLendingsForTeacher(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDeviceLending>(that.getLabRoomDeviceLendingsForTeacher()));
		setLabRoomDeviceLendingsForLendingUser(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDeviceLending>(that.getLabRoomDeviceLendingsForLendingUser()));
		setUpdateUserOperationItems(new LinkedHashSet<>(that.getUpdateUserOperationItems()));
		setItemByOpenTeachers(new LinkedHashSet<>(that.getItemByOpenTeachers()));
		setItemByLabUsers(new LinkedHashSet<>(that.getItemByLabUsers()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("username=[").append(username).append("] ");
		buffer.append("cardno=[").append(cardno).append("] ");
		buffer.append("cname=[").append(cname).append("] ");
		buffer.append("password=[").append(password).append("] ");
		buffer.append("userSexy=[").append(userSexy).append("] ");
		buffer.append("userStatus=[").append(userStatus).append("] ");
		buffer.append("teacherNumber=[").append(teacherNumber).append("] ");
		buffer.append("majorNumber=[").append(majorNumber).append("] ");
		buffer.append("userRole=[").append(userRole).append("] ");
		buffer.append("lastLogin=[").append(lastLogin).append("] ");
		buffer.append("createdAt=[").append(createdAt).append("] ");
		buffer.append("updatedAt=[").append(updatedAt).append("] ");
		buffer.append("telephone=[").append(telephone).append("] ");
		buffer.append("email=[").append(email).append("] ");
		buffer.append("enabled=[").append(enabled).append("] ");
		buffer.append("enrollmentStatus=[").append(enrollmentStatus).append("] ");
		buffer.append("ifEnrollment=[").append(ifEnrollment).append("] ");
		buffer.append("userType=[").append(userType).append("] ");
		buffer.append("attendanceTime=[").append(attendanceTime).append("] ");
		buffer.append("grade=[").append(grade).append("] ");
		buffer.append("eNname=[").append(eNname).append("] ");

		return buffer.toString();
	}

	/**
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((username == null) ? 0 : username.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof User))
			return false;
		User equalCheck = (User) obj;
		if ((username == null && equalCheck.username != null) || (username != null && equalCheck.username == null))
			return false;
		if (username != null && !username.equals(equalCheck.username))
			return false;
		return true;
	}
}
