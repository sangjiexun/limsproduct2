package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllOperationItems", query = "select myOperationItem from OperationItem myOperationItem"),
		@NamedQuery(name = "findOperationItemById", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.id = ?1"),
		@NamedQuery(name = "findOperationItemByLpAssessmentMethods", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpAssessmentMethods = ?1"),
		@NamedQuery(name = "findOperationItemByLpAssessmentMethodsContaining", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpAssessmentMethods like ?1"),
		@NamedQuery(name = "findOperationItemByLpCodeCustom", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpCodeCustom = ?1"),
		@NamedQuery(name = "findOperationItemByLpCodeCustomContaining", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpCodeCustom like ?1"),
		@NamedQuery(name = "findOperationItemByLpCollege", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpCollege = ?1"),
		@NamedQuery(name = "findOperationItemByLpCollegeContaining", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpCollege like ?1"),
		@NamedQuery(name = "findOperationItemByLpConfigMaterial", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpConfigMaterial = ?1"),
		@NamedQuery(name = "findOperationItemByLpCourseHoursTotal", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpCourseHoursTotal = ?1"),
		@NamedQuery(name = "findOperationItemByLpCycleNumber", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpCycleNumber = ?1"),
		@NamedQuery(name = "findOperationItemByLpCycleNumberContaining", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpCycleNumber like ?1"),
		@NamedQuery(name = "findOperationItemByLpDepartmentHours", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpDepartmentHours = ?1"),
		@NamedQuery(name = "findOperationItemByLpDepartmentHoursTotal", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpDepartmentHoursTotal = ?1"),
		@NamedQuery(name = "findOperationItemByLpGuideBookAuthor", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpGuideBookAuthor = ?1"),
		@NamedQuery(name = "findOperationItemByLpGuideBookAuthorContaining", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpGuideBookAuthor like ?1"),
		@NamedQuery(name = "findOperationItemByLpGuideBookTitle", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpGuideBookTitle = ?1"),
		@NamedQuery(name = "findOperationItemByLpGuideBookTitleContaining", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpGuideBookTitle like ?1"),
		@NamedQuery(name = "findOperationItemByLpIntroduction", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpIntroduction = ?1"),
		@NamedQuery(name = "findOperationItemByLpMajorFit", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpMajorFit = ?1"),
		@NamedQuery(name = "findOperationItemByLpMajorFitContaining", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpMajorFit like ?1"),
		@NamedQuery(name = "findOperationItemByLpName", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpName = ?1"),
		@NamedQuery(name = "findOperationItemByLpNameContaining", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpName like ?1"),
		@NamedQuery(name = "findOperationItemByLpPreparation", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpPreparation = ?1"),
		@NamedQuery(name = "findOperationItemByLpPurposes", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpPurposes = ?1"),
		@NamedQuery(name = "findOperationItemByLpScore", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpScore = ?1"),
		@NamedQuery(name = "findOperationItemByLpSetNumber", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpSetNumber = ?1"),
		@NamedQuery(name = "findOperationItemByLpSetNumberContaining", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpSetNumber like ?1"),
		@NamedQuery(name = "findOperationItemByLpStudentNumber", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpStudentNumber = ?1"),
		@NamedQuery(name = "findOperationItemByLpStudentNumberGroup", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpStudentNumberGroup = ?1"),
		@NamedQuery(name = "findOperationItemByLpStudentNumberGroupContaining", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpStudentNumberGroup like ?1"),
		@NamedQuery(name = "findOperationItemByLpYearsPeopleNumberPlan", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.lpYearsPeopleNumberPlan = ?1"),
		@NamedQuery(name = "findOperationItemByPrimaryKey", query = "select myOperationItem from OperationItem myOperationItem where myOperationItem.id = ?1") })
@Table(name = "operation_item")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "OperationItem")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class OperationItem implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵ����Ŀ��
	 *
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ��Ŀ���
	 *
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createdAt;

	/**
	 * ��Ŀ���
	 *
	 */


	@Column(name = "lp_code_custom")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String lpCodeCustom;

	/**
	 * ��Ŀ���
	 *
	 */
	@Column(name = "lp_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String lpName;
	/**
	 */
	@Column(name = "lp_college")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String lpCollege;
	/**
	 * ����רҵ
	 *
	 */

	@Column(name = "lp_major_fit")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String lpMajorFit;
	/**
	 * ָ�������
	 *
	 */

	@Column(name = "lp_guide_book_title")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String lpGuideBookTitle;
	/**
	 * ����
	 *
	 */

	@Column(name = "lp_guide_book_author")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String lpGuideBookAuthor;
	/**
	 * ���˷���
	 *
	 */

	@Column(name = "lp_assessment_methods")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String lpAssessmentMethods;
	/**
	 * ʵ��ѧʱ
	 *
	 */

	@Column(name = "lp_department_hours")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer lpDepartmentHours;
	/**
	 * ʵ����ѧʱ
	 *
	 */

	@Column(name = "lp_department_hours_total")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer lpDepartmentHoursTotal;
	/**
	 * �γ���ѧʱ
	 *
	 */

	@Column(name = "lp_course_hours_total")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer lpCourseHoursTotal;
	/**
	 * �ƻ�ѧ��������
	 *
	 */

	@Column(name = "lp_years_people_number_plan")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer lpYearsPeopleNumberPlan;
	/**
	 * ʵ��������
	 *
	 */

	@Column(name = "lp_student_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer lpStudentNumber;
	/**
	 * ʵ������
	 *
	 */

	@Column(name = "lp_set_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String lpSetNumber;
	/**
	 * ÿ������
	 *
	 */

	@Column(name = "lp_student_number_group")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String lpStudentNumberGroup;
	/**
	 * ѭ������
	 *
	 */

	@Column(name = "lp_cycle_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String lpCycleNumber;
	/**
	 * ѧ��
	 *
	 */

	@Column(name = "lp_score", scale = 2, precision = 12)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal lpScore;
	/**
	 * ʵ��Ŀ��
	 *
	 */

	@Column(name = "lp_purposes", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String lpPurposes;
	/**
	 * ʵ����Ŀ���
	 *
	 */

	@Column(name = "lp_introduction", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String lpIntroduction;
	/**
	 * ��ǰ׼��
	 *
	 */

	@Column(name = "lp_preparation", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String lpPreparation;
	/**
	 * ����
	 *
	 */

	@Column(name = "lp_config_material", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String lpConfigMaterial;

	@Column(name = "software")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String software;


	@Column(name = "audit_stage")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer auditStage;

	@Column(name = "plan_week")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String planWeek;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_category_main", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLpCategoryMain;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_term", referencedColumnName = "id") })
	@XmlTransient
	SchoolTerm schoolTerm;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_major", referencedColumnName = "major_number") })
	@XmlTransient
	SchoolMajor schoolMajor;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_category_require", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLpCategoryRequire;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_category_app", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLpCategoryApp;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_subject", referencedColumnName = "s_number") })
	@XmlTransient
	SystemSubject12 systemSubject12;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_lab_id", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_teacher_speaker_id", referencedColumnName = "username") })
	@XmlTransient
	User userByLpTeacherSpeakerId;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_category_student", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLpCategoryStudent;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_category_guide_book", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLpCategoryGuideBook;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_category_public", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLpCategoryPublic;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_status_check", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLpStatusCheck;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_status_change", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLpStatusChange;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_category_nature", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLpCategoryNature;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_course_number", referencedColumnName = "course_number") })
	@XmlTransient
	SchoolCourseInfo schoolCourseInfo;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_category_reward_level", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByLpCategoryRewardLevel;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_teacher_assistant_id", referencedColumnName = "username") })
	@XmlTransient
	User userByLpTeacherAssistantId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_check_user", referencedColumnName = "username") })
	@XmlTransient
	User userByLpCheckUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lp_create_user", referencedColumnName = "username") })
	@XmlTransient
	User userByLpCreateUser;

	/**
	 */
	@OneToMany(mappedBy = "operationItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<TimetableItemRelated> timetableItemRelateds;
	/**
	 */
	@OneToMany(mappedBy = "operationItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<TimetableBatchItems> timetableBatchItemses;

	@OneToMany(mappedBy = "operationItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<OperationItemMaterialRecord> operationItemMaterialRecords;

	@OneToMany(mappedBy = "operationItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<OperationItemDevice> operationItemDevices;

	@OneToMany(mappedBy = "operationItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<AssetCabinetWarehouseAccess> assetCabinetWarehouseAccesses;

	@OneToMany(mappedBy = "operationItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<AssetReceive> assetReceives;

	@OneToMany(mappedBy = "operationItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<AssetApp> assetApps;

	//新增多对多
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "m_operation_item_lab_room", joinColumns = { @JoinColumn(name = "operation_item", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "lab_room", referencedColumnName = "id", nullable = false, updatable = false) })
	@XmlElement(name = "", namespace = "")
	Set<LabRoom> labRooms;
   /**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "operation_outline_id", referencedColumnName = "id") })
	@XmlTransient
	OperationOutline operationOutline;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_type", referencedColumnName = "id") })
	@XmlTransient
	LabCenter labCenter;

	/**
	 */
	@OneToMany(mappedBy = "operationItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<CommonDocument> commonDocuments;

	@OneToMany(mappedBy = "operationItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<SoftwareItemRelated> softwareItemRelateds;


	/**
	 */
	@OneToMany(mappedBy = "operationItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<OperItemAudit> operItemAudits;

	/**
	 * 开发人职称
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "title", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByTitle;

	/**
	 * 最小单元
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "min_unit", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByMinUnit;

	/**
	 * 开放学期
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "open_term", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByOpenTerm;

	/**
	 * 开放周次
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "open_weeks", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByOpenWeeks;

	/**
	 * 实验原理
	 */
	@Column(name = "item_theory", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String itemTheory;

	/**
	 * 实验方法
	 */
	@Column(name = "item_method", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String itemMethod;

	/**
	 * 实验结果及分析
	 */
	@Column(name = "item_result", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String itemResult;

	/**
	 * 实验注意事项
	 */
	@Column(name = "item_attention", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String itemAttention;

	/**
	 * 思考题
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "item_question", referencedColumnName = "id") })
	@XmlTransient
	CommonDocument itemQuestionDocument;

	/**
	 * 实验结果形式
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "item_result_type", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByItemResultType;

	/**
	 * 开放年级
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "open_grade", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByOpenGrade;

	/**
	 * 实验经费预算
	 */
	@Column(name = "item_budget")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal itemBudget;

	/**
	 * 审核通过时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "audit_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar auditTime;

	/**
	 * 最后修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar updateTime;

	/**
	 * 最后修改人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "update_user", referencedColumnName = "username") })
	@XmlTransient
	User updateUser;

	/**
	 * 实验开放教师（多对多）
	 */
	@OneToMany(mappedBy = "operationItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<ItemOpenTeacher> openTeachers;

	/**
	 * 相关实验室人员（多对多）
	 */
	@OneToMany(mappedBy = "operationItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<ItemLabUsers> labUsers;

	/**
     * 项目计划
	 */
	@OneToMany(mappedBy = "operationItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<ItemPlan> itemPlans;

	/**
	 * 项目计划
	 */
	@OneToMany(mappedBy = "operationItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	Set<ItemAssets> itemAssets;

    /**
     * 实验目标，要求
     */
    @Column(name = "item_aim")
    @Basic(fetch = FetchType.EAGER)
    @Lob
    @XmlElement
    String itemAim;



	public Set<OperItemAudit> getOperItemAudits() {
		return operItemAudits;
	}

	public void setOperItemAudits(
			Set<OperItemAudit> operItemAudits) {
		this.operItemAudits = operItemAudits;
	}

	@JsonIgnore
	public Set<OperationItemDevice> getOperationItemDevices() {
		return operationItemDevices;
	}

	public void setOperationItemDevices(
			Set<OperationItemDevice> operationItemDevices) {
		this.operationItemDevices = operationItemDevices;
	}

	/**
	 * ʵ����Ŀ��
	 *
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵ����Ŀ��
	 *
	 */
	public Integer getId() {
		return this.id;
	}
	/**
	 * ʵ����Ŀ��
	 *
	 */
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
	 * ��Ŀ���
	 *
	 */
	public void setLpCodeCustom(String lpCodeCustom) {
		this.lpCodeCustom = lpCodeCustom;
	}

	/**
	 * ��Ŀ���
	 *
	 */
	public String getLpCodeCustom() {
		return this.lpCodeCustom;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	public String getSoftware() {
		return this.software;
	}

	/**
	 * ��Ŀ���
	 *
	 */
	public void setLpName(String lpName) {
		this.lpName = lpName;
	}

	/**
	 * ��Ŀ���
	 *
	 */
	public String getLpName() {
		return this.lpName;
	}

	/**
	 */
	public void setLpCollege(String lpCollege) {
		this.lpCollege = lpCollege;
	}

	/**
	 */
	public String getLpCollege() {
		return this.lpCollege;
	}

	/**
	 * ����רҵ
	 *
	 */
	public void setLpMajorFit(String lpMajorFit) {
		this.lpMajorFit = lpMajorFit;
	}

	/**
	 * ����רҵ
	 *
	 */
	public String getLpMajorFit() {
		return this.lpMajorFit;
	}

	/**
	 * ָ�������
	 *
	 */
	public void setLpGuideBookTitle(String lpGuideBookTitle) {
		this.lpGuideBookTitle = lpGuideBookTitle;
	}

	/**
	 * ָ�������
	 *
	 */
	public String getLpGuideBookTitle() {
		return this.lpGuideBookTitle;
	}

	/**
	 * ����
	 *
	 */
	public void setLpGuideBookAuthor(String lpGuideBookAuthor) {
		this.lpGuideBookAuthor = lpGuideBookAuthor;
	}

	/**
	 * ����
	 *
	 */
	public String getLpGuideBookAuthor() {
		return this.lpGuideBookAuthor;
	}

	/**
	 * ���˷���
	 *
	 */
	public void setLpAssessmentMethods(String lpAssessmentMethods) {
		this.lpAssessmentMethods = lpAssessmentMethods;
	}

	/**
	 * ���˷���
	 *
	 */
	public String getLpAssessmentMethods() {
		return this.lpAssessmentMethods;
	}

	/**
	 * ʵ��ѧʱ
	 *
	 */
	public void setLpDepartmentHours(Integer lpDepartmentHours) {
		this.lpDepartmentHours = lpDepartmentHours;
	}

	/**
	 * ʵ��ѧʱ
	 *
	 */
	public Integer getLpDepartmentHours() {
		return this.lpDepartmentHours;
	}

	/**
	 * ʵ����ѧʱ
	 *
	 */
	public void setLpDepartmentHoursTotal(Integer lpDepartmentHoursTotal) {
		this.lpDepartmentHoursTotal = lpDepartmentHoursTotal;
	}

	/**
	 * ʵ����ѧʱ
	 *
	 */
	public Integer getLpDepartmentHoursTotal() {
		return this.lpDepartmentHoursTotal;
	}

	/**
	 * �γ���ѧʱ
	 *
	 */
	public void setLpCourseHoursTotal(Integer lpCourseHoursTotal) {
		this.lpCourseHoursTotal = lpCourseHoursTotal;
	}

	/**
	 * �γ���ѧʱ
	 *
	 */
	public Integer getLpCourseHoursTotal() {
		return this.lpCourseHoursTotal;
	}

	/**
	 * �ƻ�ѧ��������
	 *
	 */
	public void setLpYearsPeopleNumberPlan(Integer lpYearsPeopleNumberPlan) {
		this.lpYearsPeopleNumberPlan = lpYearsPeopleNumberPlan;
	}

	/**
	 * �ƻ�ѧ��������
	 *
	 */
	public Integer getLpYearsPeopleNumberPlan() {
		return this.lpYearsPeopleNumberPlan;
	}

	/**
	 * ʵ��������
	 *
	 */
	public void setLpStudentNumber(Integer lpStudentNumber) {
		this.lpStudentNumber = lpStudentNumber;
	}

	/**
	 * ʵ��������
	 *
	 */
	public Integer getLpStudentNumber() {
		return this.lpStudentNumber;
	}

	/**
	 * ʵ������
	 *
	 */
	public void setLpSetNumber(String lpSetNumber) {
		this.lpSetNumber = lpSetNumber;
	}

	/**
	 * ʵ������
	 *
	 */
	public String getLpSetNumber() {
		return this.lpSetNumber;
	}

	/**
	 * ÿ������
	 *
	 */
	public void setLpStudentNumberGroup(String lpStudentNumberGroup) {
		this.lpStudentNumberGroup = lpStudentNumberGroup;
	}

	/**
	 * ÿ������
	 *
	 */
	public String getLpStudentNumberGroup() {
		return this.lpStudentNumberGroup;
	}

	/**
	 * ѭ������
	 *
	 */
	public void setLpCycleNumber(String lpCycleNumber) {
		this.lpCycleNumber = lpCycleNumber;
	}

	/**
	 * ѭ������
	 *
	 */
	public String getLpCycleNumber() {
		return this.lpCycleNumber;
	}

	/**
	 * ѧ��
	 *
	 */
	public void setLpScore(BigDecimal lpScore) {
		this.lpScore = lpScore;
	}

	/**
	 * ѧ��
	 *
	 */
	public BigDecimal getLpScore() {
		return this.lpScore;
	}

	/**
	 * ʵ��Ŀ��
	 *
	 */
	public void setLpPurposes(String lpPurposes) {
		this.lpPurposes = lpPurposes;
	}

	/**
	 * ʵ��Ŀ��
	 *
	 */
	public String getLpPurposes() {
		return this.lpPurposes;
	}

	/**
	 * ʵ����Ŀ���
	 *
	 */
	public void setLpIntroduction(String lpIntroduction) {
		this.lpIntroduction = lpIntroduction;
	}

	/**
	 * ʵ����Ŀ���
	 *
	 */
	public String getLpIntroduction() {
		return this.lpIntroduction;
	}

	/**
	 * ��ǰ׼��
	 *
	 */
	public void setLpPreparation(String lpPreparation) {
		this.lpPreparation = lpPreparation;
	}

	/**
	 * ��ǰ׼��
	 *
	 */
	public String getLpPreparation() {
		return this.lpPreparation;
	}

	/**
	 * ����
	 *
	 */
	public void setLpConfigMaterial(String lpConfigMaterial) {
		this.lpConfigMaterial = lpConfigMaterial;
	}

	/**
	 * ����
	 *
	 */
	public String getLpConfigMaterial() {
		return this.lpConfigMaterial;
	}

	/**
	 * @return the planWeek
	 */
	public String getPlanWeek() {
		return planWeek;
	}

	/**
	 * @param planWeek the planWeek to set
	 */
	public void setPlanWeek(String planWeek) {
		this.planWeek = planWeek;
	}

	/**
	 */
	public void setCDictionaryByLpCategoryMain(CDictionary CDictionaryByLpCategoryMain) {
		this.CDictionaryByLpCategoryMain = CDictionaryByLpCategoryMain;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByLpCategoryMain() {
		return CDictionaryByLpCategoryMain;
	}

	/**
	 */
	public void setSchoolTerm(SchoolTerm schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	/**
	 */
	@JsonIgnore
	public SchoolTerm getSchoolTerm() {
		return schoolTerm;
	}

	/**
	 */
	public void setSchoolMajor(SchoolMajor schoolMajor) {
		this.schoolMajor = schoolMajor;
	}

	/**
	 */
	@JsonIgnore
	public SchoolMajor getSchoolMajor() {
		return schoolMajor;
	}

	/**
	 */
	public void setCDictionaryByLpCategoryRequire(CDictionary CDictionaryByLpCategoryRequire) {
		this.CDictionaryByLpCategoryRequire = CDictionaryByLpCategoryRequire;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByLpCategoryRequire() {
		return CDictionaryByLpCategoryRequire;
	}

	/**
	 */
	public void setCDictionaryByLpCategoryApp(CDictionary CDictionaryByLpCategoryApp) {
		this.CDictionaryByLpCategoryApp = CDictionaryByLpCategoryApp;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByLpCategoryApp() {
		return CDictionaryByLpCategoryApp;
	}

	/**
	 */
	public void setSystemSubject12(SystemSubject12 systemSubject12) {
		this.systemSubject12 = systemSubject12;
	}

	/**
	 */
	@JsonIgnore
	public SystemSubject12 getSystemSubject12() {
		return systemSubject12;
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

	public User getUserByLpCreateUser() {
		return userByLpCreateUser;
	}

	public void setUserByLpCreateUser(User userByLpCreateUser) {
		this.userByLpCreateUser = userByLpCreateUser;
	}

	/**
	 */
	public void setUserByLpTeacherSpeakerId(User userByLpTeacherSpeakerId) {
		this.userByLpTeacherSpeakerId = userByLpTeacherSpeakerId;
	}

	/**
	 */
	@JsonIgnore
	public User getUserByLpTeacherSpeakerId() {
		return userByLpTeacherSpeakerId;
	}

	public User getUserByLpCheckUser() {
		return userByLpCheckUser;
	}

	public void setUserByLpCheckUser(User userByLpCheckUser) {
		this.userByLpCheckUser = userByLpCheckUser;
	}

	/**
	 */
	public void setCDictionaryByLpCategoryStudent(CDictionary CDictionaryByLpCategoryStudent) {
		this.CDictionaryByLpCategoryStudent = CDictionaryByLpCategoryStudent;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByLpCategoryStudent() {
		return CDictionaryByLpCategoryStudent;
	}

	/**
	 */
	public void setCDictionaryByLpCategoryGuideBook(CDictionary CDictionaryByLpCategoryGuideBook) {
		this.CDictionaryByLpCategoryGuideBook = CDictionaryByLpCategoryGuideBook;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByLpCategoryGuideBook() {
		return CDictionaryByLpCategoryGuideBook;
	}

	/**
	 */
	public void setCDictionaryByLpCategoryPublic(CDictionary CDictionaryByLpCategoryPublic) {
		this.CDictionaryByLpCategoryPublic = CDictionaryByLpCategoryPublic;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByLpCategoryPublic() {
		return CDictionaryByLpCategoryPublic;
	}

	/**
	 */
	public void setCDictionaryByLpStatusCheck(CDictionary CDictionaryByLpStatusCheck) {
		this.CDictionaryByLpStatusCheck = CDictionaryByLpStatusCheck;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByLpStatusCheck() {
		return CDictionaryByLpStatusCheck;
	}

	/**
	 */
	public void setCDictionaryByLpStatusChange(CDictionary CDictionaryByLpStatusChange) {
		this.CDictionaryByLpStatusChange = CDictionaryByLpStatusChange;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByLpStatusChange() {
		return CDictionaryByLpStatusChange;
	}

	/**
	 */
	public void setCDictionaryByLpCategoryNature(CDictionary CDictionaryByLpCategoryNature) {
		this.CDictionaryByLpCategoryNature = CDictionaryByLpCategoryNature;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByLpCategoryNature() {
		return CDictionaryByLpCategoryNature;
	}

	/**
	 */
	public void setSchoolCourseInfo(SchoolCourseInfo schoolCourseInfo) {
		this.schoolCourseInfo = schoolCourseInfo;
	}

	/**
	 */
	@JsonIgnore
	public SchoolCourseInfo getSchoolCourseInfo() {
		return schoolCourseInfo;
	}

	/**
	 */
	public void setCDictionaryByLpCategoryRewardLevel(CDictionary CDictionaryByLpCategoryRewardLevel) {
		this.CDictionaryByLpCategoryRewardLevel = CDictionaryByLpCategoryRewardLevel;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByLpCategoryRewardLevel() {
		return CDictionaryByLpCategoryRewardLevel;
	}

	/**
	 */
	public void setUserByLpTeacherAssistantId(User userByLpTeacherAssistantId) {
		this.userByLpTeacherAssistantId = userByLpTeacherAssistantId;
	}

	/**
	 */
	@JsonIgnore
	public User getUserByLpTeacherAssistantId() {
		return userByLpTeacherAssistantId;
	}

	/**
	 */
	public void setTimetableItemRelateds(Set<TimetableItemRelated> timetableItemRelateds) {
		this.timetableItemRelateds = timetableItemRelateds;
	}

	/**
	 */
	@JsonIgnore
	public Set<TimetableItemRelated> getTimetableItemRelateds() {
		if (timetableItemRelateds == null) {
			timetableItemRelateds = new LinkedHashSet<TimetableItemRelated>();
		}
		return timetableItemRelateds;
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
			assetCabinetWarehouseAccesses = new LinkedHashSet<AssetCabinetWarehouseAccess>();
		}
		return assetCabinetWarehouseAccesses;
	}

	public Set<CommonDocument> getCommonDocuments() {
		if (commonDocuments == null) {
			commonDocuments = new LinkedHashSet<CommonDocument>();
		}
		return commonDocuments;
	}

	public void setCommonDocuments(Set<CommonDocument> commonDocuments) {
		this.commonDocuments = commonDocuments;
	}

	public void setSoftwareItemRelateds(Set<SoftwareItemRelated> softwareItemRelateds) {
		this.softwareItemRelateds = softwareItemRelateds;
	}

	@JsonIgnore
	public Set<SoftwareItemRelated> getSoftwareItemRelateds() {
		if (softwareItemRelateds == null) {
			softwareItemRelateds = new LinkedHashSet<SoftwareItemRelated>();
		}
		return softwareItemRelateds;
	}

	/**
	 */
	public void setAssetReceive(Set<AssetReceive> assetReceives) {
		this.assetReceives = assetReceives;
	}

	/**
	 */
	@JsonIgnore
	public Set<AssetReceive> getAssetReceives() {
		if (assetReceives == null) {
			assetReceives = new LinkedHashSet<AssetReceive>();
		}
		return assetReceives;
	}

	/**
	 */
	public void setAssetApp(Set<AssetApp> assetApps) {
		this.assetApps = assetApps;
	}

	/**
	 */
	@JsonIgnore
	public Set<AssetApp> getAssetApps() {
		if (assetApps == null) {
			assetApps = new LinkedHashSet<AssetApp>();
		}
		return assetApps;
	}

	/**
	 */
	public void setTimetableBatchItemses(Set<TimetableBatchItems> timetableBatchItemses) {
		this.timetableBatchItemses = timetableBatchItemses;
	}

	/**
	 */
	@JsonIgnore
	public Set<TimetableBatchItems> getTimetableBatchItemses() {
		if (timetableBatchItemses == null) {
			timetableBatchItemses = new LinkedHashSet<TimetableBatchItems>();
		}
		return timetableBatchItemses;
	}

	public Set<OperationItemMaterialRecord> getOperationItemMaterialRecords() {
		return operationItemMaterialRecords;
	}

	public void setOperationItemMaterialRecords(
			Set<OperationItemMaterialRecord> operationItemMaterialRecords) {
		this.operationItemMaterialRecords = operationItemMaterialRecords;
	}


	//新增多对多
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
			labRooms = new LinkedHashSet<LabRoom>();
		}
		return labRooms;
	}
	/**
	 */
	/**
	 */
	public void setOperationOutline(OperationOutline operationOutline) {
		this.operationOutline = operationOutline;
	}

	/**
	 */
	@JsonIgnore
	public OperationOutline getOperationOutline() {
		return operationOutline;
	}

	public void setLabCenter(LabCenter labCenter) {
		this.labCenter = labCenter;
	}

	/**
	 */
	@JsonIgnore
	public LabCenter getLabCenter() {
		return labCenter;
	}

	public OperationItem() {
	}

	public CDictionary getCDictionaryByTitle() {
		return CDictionaryByTitle;
	}

	public void setCDictionaryByTitle(CDictionary CDictionaryByTitle) {
		this.CDictionaryByTitle = CDictionaryByTitle;
	}

	public CDictionary getCDictionaryByMinUnit() {
		return CDictionaryByMinUnit;
	}

	public void setCDictionaryByMinUnit(CDictionary CDictionaryByMinUnit) {
		this.CDictionaryByMinUnit = CDictionaryByMinUnit;
	}

	public CDictionary getCDictionaryByOpenTerm() {
		return CDictionaryByOpenTerm;
	}

	public void setCDictionaryByOpenTerm(CDictionary CDictionaryByOpenTerm) {
		this.CDictionaryByOpenTerm = CDictionaryByOpenTerm;
	}

	public CDictionary getCDictionaryByOpenWeeks() {
		return CDictionaryByOpenWeeks;
	}

	public void setCDictionaryByOpenWeeks(CDictionary CDictionaryByOpenWeeks) {
		this.CDictionaryByOpenWeeks = CDictionaryByOpenWeeks;
	}

	public String getItemTheory() {
		return itemTheory;
	}

	public void setItemTheory(String itemTheory) {
		this.itemTheory = itemTheory;
	}

	public String getItemMethod() {
		return itemMethod;
	}

	public void setItemMethod(String itemMethod) {
		this.itemMethod = itemMethod;
	}

	public String getItemResult() {
		return itemResult;
	}

	public void setItemResult(String itemResult) {
		this.itemResult = itemResult;
	}

	public String getItemAttention() {
		return itemAttention;
	}

	public void setItemAttention(String itemAttention) {
		this.itemAttention = itemAttention;
	}

	public CommonDocument getItemQuestionDocument() {
		return itemQuestionDocument;
	}

	public void setItemQuestionDocument(CommonDocument itemQuestionDocument) {
		this.itemQuestionDocument = itemQuestionDocument;
	}

	public CDictionary getCDictionaryByItemResultType() {
		return CDictionaryByItemResultType;
	}

	public void setCDictionaryByItemResultType(CDictionary CDictionaryByItemResultType) {
		this.CDictionaryByItemResultType = CDictionaryByItemResultType;
	}

	public CDictionary getCDictionaryByOpenGrade() {
		return CDictionaryByOpenGrade;
	}

	public void setCDictionaryByOpenGrade(CDictionary CDictionaryByOpenGrade) {
		this.CDictionaryByOpenGrade = CDictionaryByOpenGrade;
	}

	public BigDecimal getItemBudget() {
		return itemBudget;
	}

	public void setItemBudget(BigDecimal itemBudget) {
		this.itemBudget = itemBudget;
	}

	public Calendar getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Calendar auditTime) {
		this.auditTime = auditTime;
	}

	public Calendar getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Calendar updateTime) {
		this.updateTime = updateTime;
	}

	public User getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
	}

	public Set<ItemOpenTeacher> getOpenTeachers() {
		if(openTeachers == null){
			openTeachers = new LinkedHashSet<>();
		}
		return openTeachers;
	}

	public void setOpenTeachers(Set<ItemOpenTeacher> openTeachers) {
		this.openTeachers = openTeachers;
	}

	public Set<ItemLabUsers> getLabUsers() {
		if(labUsers == null){
			labUsers = new LinkedHashSet<>();
		}
		return labUsers;
	}

	public void setLabUsers(Set<ItemLabUsers> labUsers) {
		this.labUsers = labUsers;
	}

	public Set<ItemPlan> getItemPlans() {
		return itemPlans;
	}

	public void setItemPlans(Set<ItemPlan> itemPlans) {
		if(itemPlans == null){
			itemPlans = new LinkedHashSet<>();
		}
		this.itemPlans = itemPlans;
	}

	public Set<ItemAssets> getItemAssets() {
		return itemAssets;
	}

	public void setItemAssets(Set<ItemAssets> itemAssets) {
		this.itemAssets = itemAssets;
	}

    public String getItemAim() {
        return itemAim;
    }

    public void setItemAim(String itemAim) {
        this.itemAim = itemAim;
    }

    /**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(OperationItem that) {
//		setId(that.getId());
		setCreatedAt(that.getCreatedAt());
		setLpCodeCustom(that.getLpCodeCustom());
		setLpName(that.getLpName());
		setLpCollege(that.getLpCollege());
		setLpMajorFit(that.getLpMajorFit());
		setLpGuideBookTitle(that.getLpGuideBookTitle());
		setLpGuideBookAuthor(that.getLpGuideBookAuthor());
		setLpAssessmentMethods(that.getLpAssessmentMethods());
		setLpDepartmentHours(that.getLpDepartmentHours());
		setLpDepartmentHoursTotal(that.getLpDepartmentHoursTotal());
		setLpCourseHoursTotal(that.getLpCourseHoursTotal());
		setLpYearsPeopleNumberPlan(that.getLpYearsPeopleNumberPlan());
		setLpStudentNumber(that.getLpStudentNumber());
		setLpSetNumber(that.getLpSetNumber());
		setLpStudentNumberGroup(that.getLpStudentNumberGroup());
		setLpCycleNumber(that.getLpCycleNumber());
		setLpScore(that.getLpScore());
		setLpPurposes(that.getLpPurposes());
		setLpIntroduction(that.getLpIntroduction());
		setLpPreparation(that.getLpPreparation());
		setLpConfigMaterial(that.getLpConfigMaterial());
		setCDictionaryByLpCategoryMain(that.getCDictionaryByLpCategoryMain());
		setSchoolTerm(that.getSchoolTerm());
		setSchoolMajor(that.getSchoolMajor());
		setCDictionaryByLpCategoryRequire(that.getCDictionaryByLpCategoryRequire());
		setCDictionaryByLpCategoryApp(that.getCDictionaryByLpCategoryApp());
		setSystemSubject12(that.getSystemSubject12());
		setLabRoom(that.getLabRoom());
		setUserByLpTeacherSpeakerId(that.getUserByLpTeacherSpeakerId());
		setCDictionaryByLpCategoryStudent(that.getCDictionaryByLpCategoryStudent());
		setCDictionaryByLpCategoryGuideBook(that.getCDictionaryByLpCategoryGuideBook());
		setCDictionaryByLpCategoryPublic(that.getCDictionaryByLpCategoryPublic());
		setCDictionaryByLpStatusCheck(that.getCDictionaryByLpStatusCheck());
		setCDictionaryByLpStatusChange(that.getCDictionaryByLpStatusChange());
		setCDictionaryByLpCategoryNature(that.getCDictionaryByLpCategoryNature());
		setSchoolCourseInfo(that.getSchoolCourseInfo());
		setCDictionaryByLpCategoryRewardLevel(that.getCDictionaryByLpCategoryRewardLevel());
		setUserByLpTeacherAssistantId(that.getUserByLpTeacherAssistantId());
		setUserByLpCheckUser(that.getUserByLpCheckUser());
		setUserByLpCreateUser(that.getUserByLpCreateUser());
		setLabCenter(that.getLabCenter());
		//新增多对多
		setLabRooms(new LinkedHashSet<LabRoom>(that.getLabRooms()));
//		setTimetableItemRelateds(new java.util.LinkedHashSet<net.zjcclims.domain.TimetableItemRelated>(that.getTimetableItemRelateds()));
//		setTimetableBatchItemses(new java.util.LinkedHashSet<net.zjcclims.domain.TimetableBatchItems>(that.getTimetableBatchItemses()));
		setSoftwareItemRelateds(new LinkedHashSet<SoftwareItemRelated>(that.getSoftwareItemRelateds()));

		setUpdateUser(that.getUpdateUser());
		setItemResult(that.getItemResult());
		setItemBudget(that.getItemBudget());
		setAuditTime(that.getAuditTime());
		setUpdateTime(that.getUpdateTime());
		setItemTheory(that.getItemTheory());
		setItemMethod(that.getItemMethod());
		setOpenTeachers(new LinkedHashSet<>(that.getOpenTeachers()));
		setLabUsers(new LinkedHashSet<>(that.getLabUsers()));
		setItemAttention(that.getItemAttention());
		setItemPlans(new LinkedHashSet<>(that.getItemPlans()));
		setCDictionaryByItemResultType(that.getCDictionaryByItemResultType());
		setCDictionaryByOpenGrade(that.getCDictionaryByOpenGrade());
		setItemQuestionDocument(that.getItemQuestionDocument());
		setCDictionaryByOpenTerm(that.getCDictionaryByOpenTerm());
		setCDictionaryByMinUnit(that.getCDictionaryByMinUnit());
		setCDictionaryByOpenWeeks(that.getCDictionaryByOpenWeeks());
		setCDictionaryByTitle(that.getCDictionaryByTitle());
		setItemAssets(new LinkedHashSet<>(that.getItemAssets()));
		setItemAim(that.getItemAim());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("lpCodeCustom=[").append(lpCodeCustom).append("] ");
		buffer.append("lpName=[").append(lpName).append("] ");
		buffer.append("lpCollege=[").append(lpCollege).append("] ");
		buffer.append("lpMajorFit=[").append(lpMajorFit).append("] ");
		buffer.append("lpGuideBookTitle=[").append(lpGuideBookTitle).append("] ");
		buffer.append("lpGuideBookAuthor=[").append(lpGuideBookAuthor).append("] ");
		buffer.append("lpAssessmentMethods=[").append(lpAssessmentMethods).append("] ");
		buffer.append("lpDepartmentHours=[").append(lpDepartmentHours).append("] ");
		buffer.append("lpDepartmentHoursTotal=[").append(lpDepartmentHoursTotal).append("] ");
		buffer.append("lpCourseHoursTotal=[").append(lpCourseHoursTotal).append("] ");
		buffer.append("lpYearsPeopleNumberPlan=[").append(lpYearsPeopleNumberPlan).append("] ");
		buffer.append("lpStudentNumber=[").append(lpStudentNumber).append("] ");
		buffer.append("lpSetNumber=[").append(lpSetNumber).append("] ");
		buffer.append("lpStudentNumberGroup=[").append(lpStudentNumberGroup).append("] ");
		buffer.append("lpCycleNumber=[").append(lpCycleNumber).append("] ");
		buffer.append("lpScore=[").append(lpScore).append("] ");
		buffer.append("lpPurposes=[").append(lpPurposes).append("] ");
		buffer.append("lpIntroduction=[").append(lpIntroduction).append("] ");
		buffer.append("lpPreparation=[").append(lpPreparation).append("] ");
		buffer.append("lpConfigMaterial=[").append(lpConfigMaterial).append("] ");

		buffer.append("CDictionaryByTitle=[").append(CDictionaryByTitle).append("] ");
		buffer.append("CDictionaryByMinUnit=[").append(CDictionaryByMinUnit).append("] ");
		buffer.append("CDictionaryByOpenTerm=[").append(CDictionaryByOpenTerm).append("] ");
		buffer.append("CDictionaryByOpenWeeks=[").append(CDictionaryByOpenWeeks).append("] ");
		buffer.append("itemTheory=[").append(itemTheory).append("] ");
		buffer.append("itemMethod=[").append(itemMethod).append("] ");
		buffer.append("itemResult=[").append(itemResult).append("] ");
		buffer.append("itemAttention=[").append(itemAttention).append("] ");
		buffer.append("itemQuestionDocument=[").append(itemQuestionDocument).append("] ");
		buffer.append("CDictionaryByItemResultType=[").append(CDictionaryByItemResultType).append("] ");
		buffer.append("CDictionaryByOpenGrade=[").append(CDictionaryByOpenGrade).append("] ");
		buffer.append("itemBudget=[").append(itemBudget).append("] ");
		buffer.append("auditTime=[").append(auditTime).append("] ");
		buffer.append("updateTime=[").append(updateTime).append("] ");
		buffer.append("updateUser=[").append(updateUser).append("] ");
		buffer.append("openTeachers=[").append(openTeachers).append("] ");
		buffer.append("labUsers=[").append(labUsers).append("] ");
		buffer.append("itemPlans=[").append(itemPlans).append("] ");

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
		if (!(obj instanceof OperationItem))
			return false;
		OperationItem equalCheck = (OperationItem) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}

	public Integer getAuditStage() {
		return auditStage;
	}

	public void setAuditStage(Integer auditStage) {
		this.auditStage = auditStage;
	}
}
