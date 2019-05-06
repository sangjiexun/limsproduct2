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
		@NamedQuery(name = "findAllProjectAcceptanceApplications", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByActualBenefits", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.actualBenefits = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByAppDate", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.appDate = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByAppDateAfter", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.appDate > ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByAppDateBefore", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.appDate < ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByConstructCondition", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.constructCondition = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByCourseAmount", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.courseAmount = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByCourseName", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.courseName = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByCourseNameContaining", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.courseName like ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByExpectCompleteDate", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.expectCompleteDate = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByExpectCompleteDateAfter", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.expectCompleteDate > ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByExpectCompleteDateBefore", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.expectCompleteDate < ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByExpectLabItem", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.expectLabItem = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationById", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.id = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByMajorAmount", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.majorAmount = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByMajorName", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.majorName = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByMajorNameContaining", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.majorName like ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByOpenLabItem", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.openLabItem = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByOriginalLabroomAdd", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.originalLabroomAdd = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByOriginalLabroomAddContaining", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.originalLabroomAdd like ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByOriginalLabroomArea", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.originalLabroomArea = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByOriginalLabroomItem", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.originalLabroomItem = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByOriginalLabroomValue", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.originalLabroomValue = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByPrimaryKey", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.id = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByProjectExpectedBenefits", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.projectExpectedBenefits = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByProjectSituation", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.projectSituation = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByProjectStartDate", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.projectStartDate = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByProjectStartDateAfter", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.projectStartDate > ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByProjectStartDateBefore", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.projectStartDate < ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByRealityCompleteDate", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.realityCompleteDate = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByRealityCompleteDateAfter", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.realityCompleteDate > ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByRealityCompleteDateBefore", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.realityCompleteDate < ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByRealityLabItem", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.realityLabItem = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByTargetLabroomAdd", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.targetLabroomAdd = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByTargetLabroomAddContaining", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.targetLabroomAdd like ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByTargetLabroomArea", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.targetLabroomArea = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByTargetLabroomItem", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.targetLabroomItem = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByProjectName", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.projectName = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByProjectNameContaining", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.projectName like ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByOtherFee", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.otherFee = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByFeeAmount", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.feeAmount = ?1"),
		@NamedQuery(name = "findProjectAcceptanceApplicationByTargetLabroomValue", query = "select myProjectAcceptanceApplication from ProjectAcceptanceApplication myProjectAcceptanceApplication where myProjectAcceptanceApplication.targetLabroomValue = ?1") })
@Table(name = "project_acceptance_application")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "ProjectAcceptanceApplication")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class ProjectAcceptanceApplication implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ʵ���ҽ�����Ŀ�����������
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	
	/**
	 * 项目名称
	 * 
	 */

	@Column(name = "project_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String projectName;
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
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "project_start_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar projectStartDate;
	/**
	 * Ԥ�����ʱ��
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "expect_complete_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar expectCompleteDate;
	/**
	 * ʵ�����ʱ��
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "reality_complete_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar realityCompleteDate;
	/**
	 * ԭʵ�������
	 * 
	 */

	@Column(name = "original_labroom_area", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal originalLabroomArea;
	/**
	 * ԭʵ���ҵص�
	 * 
	 */

	@Column(name = "original_labroom_add")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String originalLabroomAdd;
	/**
	 * ԭʵ�����豸��ֵ
	 * 
	 */

	@Column(name = "original_labroom_value", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal originalLabroomValue;
	/**
	 * ԭʵ���ҿ���ʵ����Ŀ��
	 * 
	 */

	@Column(name = "original_labroom_item")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer originalLabroomItem;
	/**
	 * ���ɺ�ʵ�������
	 * 
	 */

	@Column(name = "target_labroom_area", scale = 2, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal targetLabroomArea;
	/**
	 * ���ɺ�ʵ���ҵص�
	 * 
	 */

	@Column(name = "target_labroom_add")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String targetLabroomAdd;
	/**
	 * ���ɺ�ʵ�����豸��ֵ
	 * 
	 */

	@Column(name = "target_labroom_value", precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal targetLabroomValue;
	/**
	 * ���ɺ�ʵ���ҿ���ʵ����Ŀ��
	 * 
	 */

	@Column(name = "target_labroom_item")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer targetLabroomItem;
	/**
	 * ����רҵ��
	 * 
	 */

	@Column(name = "major_amount")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer majorAmount;
	/**
	 * רҵ���
	 * 
	 */

	@Column(name = "major_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String majorName;
	/**
	 * ����γ���
	 * 
	 */

	@Column(name = "course_amount")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer courseAmount;
	/**
	 * �γ����
	 * 
	 */

	@Column(name = "course_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String courseName;
	/**
	 * �ɿ���ʵ����Ŀ��
	 * 
	 */

	@Column(name = "expect_lab_item")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer expectLabItem;
	/**
	 * ʵ�ʿ���ʵ����Ŀ��
	 * 
	 */

	@Column(name = "reality_lab_item")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer realityLabItem;
	/**
	 * ����������
	 * 
	 */

	@Column(name = "construct_condition", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String constructCondition;
	/**
	 */

	@Column(name = "open_lab_item")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer openLabItem;
	/**
	 * ����ſ�
	 * 
	 */

	@Column(name = "project_situation", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String projectSituation;
	/**
	 * ����Ԥ��Ч��
	 * 
	 */

	@Column(name = "project_expected_benefits", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String projectExpectedBenefits;
	/**
	 * ʵ��Ч��
	 * 
	 */

	@Column(name = "actual_benefits", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String actualBenefits;
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
	ProjectStartedReport projectStartedReport;
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
	@OneToMany(mappedBy = "projectAcceptanceApplication", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectAcceptCompletionItem> projectAcceptCompletionItems;
	/**
	 */
	@OneToMany(mappedBy = "projectAcceptanceApplication", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectAcceptDevice> projectAcceptDevices;
	/**
	 */
	@OneToMany(mappedBy = "projectAcceptanceApplication", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectAcceptUser> projectAcceptUsers;
	/**
	 */
	@OneToMany(mappedBy = "projectAcceptanceApplication", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<ProjectAcceptFeeList> projectAcceptFeeLists;

	/**
	 */
	@OneToMany(mappedBy = "projectAcceptanceApplicationByProjectAcceptAppItem", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<CommonDocument> commonDocumentsForProjectAcceptAppItem;
	/**
	 */
	@OneToMany(mappedBy = "projectAcceptanceApplicationByProjectAcceptAppEstablish", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<CommonDocument> commonDocumentsForProjectAcceptAppGuide;
	/**
	 */
	@OneToMany(mappedBy = "projectAcceptanceApplicationByProjectAcceptAppEstablish", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<CommonDocument> commonDocumentsForProjectAcceptAppEstablish;
	/**
	 */
	@OneToMany(mappedBy = "projectAcceptanceApplicationByProjectAcceptAppEstablish", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<CommonDocument> commonDocumentsForProjectAcceptAppSummarize;
	/**
	 */
	@OneToMany(mappedBy = "projectAcceptanceApplicationByProjectAcceptAppEstablish", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<CommonDocument> commonDocumentsForProjectAcceptAppTest;

	/**
	 * ʵ���ҽ�����Ŀ�����������
	 *
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ʵ���ҽ�����Ŀ�����������
	 *
	 */
	public Integer getId() {
		return this.id;
	}
	/**
	 * 项目名称
	 *
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * 项目名称
	 *
	 */
	public String getProjectName() {
		return this.projectName;
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
	 * ����ʱ��
	 *
	 */
	public void setProjectStartDate(Calendar projectStartDate) {
		this.projectStartDate = projectStartDate;
	}

	/**
	 * ����ʱ��
	 *
	 */
	public Calendar getProjectStartDate() {
		return this.projectStartDate;
	}

	/**
	 * Ԥ�����ʱ��
	 *
	 */
	public void setExpectCompleteDate(Calendar expectCompleteDate) {
		this.expectCompleteDate = expectCompleteDate;
	}

	/**
	 * Ԥ�����ʱ��
	 *
	 */
	public Calendar getExpectCompleteDate() {
		return this.expectCompleteDate;
	}

	/**
	 * ʵ�����ʱ��
	 *
	 */
	public void setRealityCompleteDate(Calendar realityCompleteDate) {
		this.realityCompleteDate = realityCompleteDate;
	}

	/**
	 * ʵ�����ʱ��
	 *
	 */
	public Calendar getRealityCompleteDate() {
		return this.realityCompleteDate;
	}

	/**
	 * ԭʵ�������
	 *
	 */
	public void setOriginalLabroomArea(BigDecimal originalLabroomArea) {
		this.originalLabroomArea = originalLabroomArea;
	}

	/**
	 * ԭʵ�������
	 *
	 */
	public BigDecimal getOriginalLabroomArea() {
		return this.originalLabroomArea;
	}

	/**
	 * ԭʵ���ҵص�
	 *
	 */
	public void setOriginalLabroomAdd(String originalLabroomAdd) {
		this.originalLabroomAdd = originalLabroomAdd;
	}

	/**
	 * ԭʵ���ҵص�
	 *
	 */
	public String getOriginalLabroomAdd() {
		return this.originalLabroomAdd;
	}

	/**
	 * ԭʵ�����豸��ֵ
	 *
	 */
	public void setOriginalLabroomValue(BigDecimal originalLabroomValue) {
		this.originalLabroomValue = originalLabroomValue;
	}

	/**
	 * ԭʵ�����豸��ֵ
	 *
	 */
	public BigDecimal getOriginalLabroomValue() {
		return this.originalLabroomValue;
	}

	/**
	 * ԭʵ���ҿ���ʵ����Ŀ��
	 *
	 */
	public void setOriginalLabroomItem(Integer originalLabroomItem) {
		this.originalLabroomItem = originalLabroomItem;
	}

	/**
	 * ԭʵ���ҿ���ʵ����Ŀ��
	 *
	 */
	public Integer getOriginalLabroomItem() {
		return this.originalLabroomItem;
	}

	/**
	 * ���ɺ�ʵ�������
	 *
	 */
	public void setTargetLabroomArea(BigDecimal targetLabroomArea) {
		this.targetLabroomArea = targetLabroomArea;
	}

	/**
	 * ���ɺ�ʵ�������
	 *
	 */
	public BigDecimal getTargetLabroomArea() {
		return this.targetLabroomArea;
	}

	/**
	 * ���ɺ�ʵ���ҵص�
	 *
	 */
	public void setTargetLabroomAdd(String targetLabroomAdd) {
		this.targetLabroomAdd = targetLabroomAdd;
	}

	/**
	 * ���ɺ�ʵ���ҵص�
	 *
	 */
	public String getTargetLabroomAdd() {
		return this.targetLabroomAdd;
	}

	/**
	 * ���ɺ�ʵ�����豸��ֵ
	 *
	 */
	public void setTargetLabroomValue(BigDecimal targetLabroomValue) {
		this.targetLabroomValue = targetLabroomValue;
	}

	/**
	 * ���ɺ�ʵ�����豸��ֵ
	 *
	 */
	public BigDecimal getTargetLabroomValue() {
		return this.targetLabroomValue;
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
	 * ���ɺ�ʵ���ҿ���ʵ����Ŀ��
	 *
	 */
	public void setTargetLabroomItem(Integer targetLabroomItem) {
		this.targetLabroomItem = targetLabroomItem;
	}

	/**
	 * ���ɺ�ʵ���ҿ���ʵ����Ŀ��
	 *
	 */
	public Integer getTargetLabroomItem() {
		return this.targetLabroomItem;
	}

	/**
	 * ����רҵ��
	 *
	 */
	public void setMajorAmount(Integer majorAmount) {
		this.majorAmount = majorAmount;
	}

	/**
	 * ����רҵ��
	 *
	 */
	public Integer getMajorAmount() {
		return this.majorAmount;
	}

	/**
	 * רҵ���
	 *
	 */
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	/**
	 * רҵ���
	 *
	 */
	public String getMajorName() {
		return this.majorName;
	}

	/**
	 * ����γ���
	 *
	 */
	public void setCourseAmount(Integer courseAmount) {
		this.courseAmount = courseAmount;
	}

	/**
	 * ����γ���
	 *
	 */
	public Integer getCourseAmount() {
		return this.courseAmount;
	}

	/**
	 * �γ����
	 *
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * �γ����
	 *
	 */
	public String getCourseName() {
		return this.courseName;
	}

	/**
	 * �ɿ���ʵ����Ŀ��
	 *
	 */
	public void setExpectLabItem(Integer expectLabItem) {
		this.expectLabItem = expectLabItem;
	}

	/**
	 * �ɿ���ʵ����Ŀ��
	 *
	 */
	public Integer getExpectLabItem() {
		return this.expectLabItem;
	}

	/**
	 * ʵ�ʿ���ʵ����Ŀ��
	 *
	 */
	public void setRealityLabItem(Integer realityLabItem) {
		this.realityLabItem = realityLabItem;
	}

	/**
	 * ʵ�ʿ���ʵ����Ŀ��
	 *
	 */
	public Integer getRealityLabItem() {
		return this.realityLabItem;
	}

	/**
	 * ����������
	 *
	 */
	public void setConstructCondition(String constructCondition) {
		this.constructCondition = constructCondition;
	}

	/**
	 * ����������
	 *
	 */
	public String getConstructCondition() {
		return this.constructCondition;
	}

	/**
	 */
	public void setOpenLabItem(Integer openLabItem) {
		this.openLabItem = openLabItem;
	}

	/**
	 */
	public Integer getOpenLabItem() {
		return this.openLabItem;
	}

	/**
	 * ����ſ�
	 *
	 */
	public void setProjectSituation(String projectSituation) {
		this.projectSituation = projectSituation;
	}

	/**
	 * ����ſ�
	 *
	 */
	public String getProjectSituation() {
		return this.projectSituation;
	}

	/**
	 * ����Ԥ��Ч��
	 *
	 */
	public void setProjectExpectedBenefits(String projectExpectedBenefits) {
		this.projectExpectedBenefits = projectExpectedBenefits;
	}

	/**
	 * ����Ԥ��Ч��
	 *
	 */
	public String getProjectExpectedBenefits() {
		return this.projectExpectedBenefits;
	}

	/**
	 * ʵ��Ч��
	 *
	 */
	public void setActualBenefits(String actualBenefits) {
		this.actualBenefits = actualBenefits;
	}

	/**
	 * ʵ��Ч��
	 *
	 */
	public String getActualBenefits() {
		return this.actualBenefits;
	}

	/**
	 */
	public void setProjectStartedReport(ProjectStartedReport projectStartedReport) {
		this.projectStartedReport = projectStartedReport;
	}

	/**
	 */
	public ProjectStartedReport getProjectStartedReport() {
		return projectStartedReport;
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
	public void setProjectAcceptCompletionItems(Set<ProjectAcceptCompletionItem> projectAcceptCompletionItems) {
		this.projectAcceptCompletionItems = projectAcceptCompletionItems;
	}

	/**
	 */
	public Set<ProjectAcceptCompletionItem> getProjectAcceptCompletionItems() {
		if (projectAcceptCompletionItems == null) {
			projectAcceptCompletionItems = new java.util.LinkedHashSet<ProjectAcceptCompletionItem>();
		}
		return projectAcceptCompletionItems;
	}

	/**
	 */
	public void setProjectAcceptDevices(Set<ProjectAcceptDevice> projectAcceptDevices) {
		this.projectAcceptDevices = projectAcceptDevices;
	}

	/**
	 */
	public Set<ProjectAcceptDevice> getProjectAcceptDevices() {
		if (projectAcceptDevices == null) {
			projectAcceptDevices = new java.util.LinkedHashSet<ProjectAcceptDevice>();
		}
		return projectAcceptDevices;
	}

	/**
	 */
	public void setProjectAcceptUsers(Set<ProjectAcceptUser> projectAcceptUsers) {
		this.projectAcceptUsers = projectAcceptUsers;
	}

	/**
	 */
	public Set<ProjectAcceptUser> getProjectAcceptUsers() {
		if (projectAcceptUsers == null) {
			projectAcceptUsers = new java.util.LinkedHashSet<ProjectAcceptUser>();
		}
		return projectAcceptUsers;
	}

	/**
	 */
	public void setProjectAcceptFeeLists(Set<ProjectAcceptFeeList> projectAcceptFeeLists) {
		this.projectAcceptFeeLists = projectAcceptFeeLists;
	}

	/**
	 */
	public Set<ProjectAcceptFeeList> getProjectAcceptFeeLists() {
		if (projectAcceptFeeLists == null) {
			projectAcceptFeeLists = new java.util.LinkedHashSet<ProjectAcceptFeeList>();
		}
		return projectAcceptFeeLists;
	}

	/**
	 */
	public void setCommonDocumentsForProjectAcceptAppItem(Set<CommonDocument> commonDocumentsForProjectAcceptAppItem) {
		this.commonDocumentsForProjectAcceptAppItem = commonDocumentsForProjectAcceptAppItem;
	}

	/**
	 */
	public Set<CommonDocument> getCommonDocumentsForProjectAcceptAppItem() {
		if (commonDocumentsForProjectAcceptAppItem == null) {
			commonDocumentsForProjectAcceptAppItem = new java.util.LinkedHashSet<CommonDocument>();
		}
		return commonDocumentsForProjectAcceptAppItem;
	}

	/**
	 */
	public void setCommonDocumentsForProjectAcceptAppGuide(Set<CommonDocument> commonDocumentsForProjectAcceptAppGuide) {
		this.commonDocumentsForProjectAcceptAppGuide = commonDocumentsForProjectAcceptAppGuide;
	}

	/**
	 */
	public Set<CommonDocument> getCommonDocumentsForProjectAcceptAppGuide() {
		if (commonDocumentsForProjectAcceptAppGuide == null) {
			commonDocumentsForProjectAcceptAppGuide = new java.util.LinkedHashSet<CommonDocument>();
		}
		return commonDocumentsForProjectAcceptAppGuide;
	}

	/**
	 */
	public void setCommonDocumentsForProjectAcceptAppEstablish(Set<CommonDocument> commonDocumentsForProjectAcceptAppEstablish) {
		this.commonDocumentsForProjectAcceptAppEstablish = commonDocumentsForProjectAcceptAppEstablish;
	}

	/**
	 */
	public Set<CommonDocument> getCommonDocumentsForProjectAcceptAppEstablish() {
		if (commonDocumentsForProjectAcceptAppEstablish == null) {
			commonDocumentsForProjectAcceptAppEstablish = new java.util.LinkedHashSet<CommonDocument>();
		}
		return commonDocumentsForProjectAcceptAppEstablish;
	}

	/**
	 */
	public void setCommonDocumentsForProjectAcceptAppSummarize(Set<CommonDocument> commonDocumentsForProjectAcceptAppSummarize) {
		this.commonDocumentsForProjectAcceptAppSummarize = commonDocumentsForProjectAcceptAppSummarize;
	}

	/**
	 */
	public Set<CommonDocument> getCommonDocumentsForProjectAcceptAppSummarize() {
		if (commonDocumentsForProjectAcceptAppSummarize == null) {
			commonDocumentsForProjectAcceptAppSummarize = new java.util.LinkedHashSet<CommonDocument>();
		}
		return commonDocumentsForProjectAcceptAppSummarize;
	}

	/**
	 */
	public void setCommonDocumentsForProjectAcceptAppTest(Set<CommonDocument> commonDocumentsForProjectAcceptAppTest) {
		this.commonDocumentsForProjectAcceptAppTest = commonDocumentsForProjectAcceptAppTest;
	}

	/**
	 */
	public Set<CommonDocument> getCommonDocumentsForProjectAcceptAppTest() {
		if (commonDocumentsForProjectAcceptAppTest == null) {
			commonDocumentsForProjectAcceptAppTest = new java.util.LinkedHashSet<CommonDocument>();
		}
		return commonDocumentsForProjectAcceptAppTest;
	}

	/**
	 */
	public ProjectAcceptanceApplication() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(ProjectAcceptanceApplication that) {
		setId(that.getId());
		setProjectName(that.getProjectName());
		setAppDate(that.getAppDate());
		setProjectStartDate(that.getProjectStartDate());
		setExpectCompleteDate(that.getExpectCompleteDate());
		setRealityCompleteDate(that.getRealityCompleteDate());
		setOriginalLabroomArea(that.getOriginalLabroomArea());
		setOriginalLabroomAdd(that.getOriginalLabroomAdd());
		setOriginalLabroomValue(that.getOriginalLabroomValue());
		setOriginalLabroomItem(that.getOriginalLabroomItem());
		setTargetLabroomArea(that.getTargetLabroomArea());
		setTargetLabroomAdd(that.getTargetLabroomAdd());
		setTargetLabroomValue(that.getTargetLabroomValue());
		setTargetLabroomItem(that.getTargetLabroomItem());
		setMajorAmount(that.getMajorAmount());
		setMajorName(that.getMajorName());
		setCourseAmount(that.getCourseAmount());
		setCourseName(that.getCourseName());
		setExpectLabItem(that.getExpectLabItem());
		setRealityLabItem(that.getRealityLabItem());
		setConstructCondition(that.getConstructCondition());
		setOpenLabItem(that.getOpenLabItem());
		setProjectSituation(that.getProjectSituation());
		setProjectExpectedBenefits(that.getProjectExpectedBenefits());
		setActualBenefits(that.getActualBenefits());
		setFeeAmount(that.getFeeAmount());
		setOtherFee(that.getOtherFee());
		setProjectStartedReport(that.getProjectStartedReport());
		setSchoolAcademy(that.getSchoolAcademy());
		setCProjectSource(that.getCProjectSource());
		setProjectAcceptCompletionItems(new java.util.LinkedHashSet<ProjectAcceptCompletionItem>(that.getProjectAcceptCompletionItems()));
		setProjectAcceptDevices(new java.util.LinkedHashSet<ProjectAcceptDevice>(that.getProjectAcceptDevices()));
		setProjectAcceptUsers(new java.util.LinkedHashSet<ProjectAcceptUser>(that.getProjectAcceptUsers()));
		setProjectAcceptFeeLists(new java.util.LinkedHashSet<ProjectAcceptFeeList>(that.getProjectAcceptFeeLists()));
		setCommonDocumentsForProjectAcceptAppItem(new java.util.LinkedHashSet<CommonDocument>(that.getCommonDocumentsForProjectAcceptAppItem()));
		setCommonDocumentsForProjectAcceptAppGuide(new java.util.LinkedHashSet<CommonDocument>(that.getCommonDocumentsForProjectAcceptAppGuide()));
		setCommonDocumentsForProjectAcceptAppEstablish(new java.util.LinkedHashSet<CommonDocument>(that.getCommonDocumentsForProjectAcceptAppEstablish()));
		setCommonDocumentsForProjectAcceptAppSummarize(new java.util.LinkedHashSet<CommonDocument>(that.getCommonDocumentsForProjectAcceptAppSummarize()));
		setCommonDocumentsForProjectAcceptAppTest(new java.util.LinkedHashSet<CommonDocument>(that.getCommonDocumentsForProjectAcceptAppTest()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("projectName=[").append(projectName).append("] ");
		buffer.append("appDate=[").append(appDate).append("] ");
		buffer.append("projectStartDate=[").append(projectStartDate).append("] ");
		buffer.append("expectCompleteDate=[").append(expectCompleteDate).append("] ");
		buffer.append("realityCompleteDate=[").append(realityCompleteDate).append("] ");
		buffer.append("originalLabroomArea=[").append(originalLabroomArea).append("] ");
		buffer.append("originalLabroomAdd=[").append(originalLabroomAdd).append("] ");
		buffer.append("originalLabroomValue=[").append(originalLabroomValue).append("] ");
		buffer.append("originalLabroomItem=[").append(originalLabroomItem).append("] ");
		buffer.append("targetLabroomArea=[").append(targetLabroomArea).append("] ");
		buffer.append("targetLabroomAdd=[").append(targetLabroomAdd).append("] ");
		buffer.append("targetLabroomValue=[").append(targetLabroomValue).append("] ");
		buffer.append("targetLabroomItem=[").append(targetLabroomItem).append("] ");
		buffer.append("majorAmount=[").append(majorAmount).append("] ");
		buffer.append("majorName=[").append(majorName).append("] ");
		buffer.append("courseAmount=[").append(courseAmount).append("] ");
		buffer.append("courseName=[").append(courseName).append("] ");
		buffer.append("expectLabItem=[").append(expectLabItem).append("] ");
		buffer.append("realityLabItem=[").append(realityLabItem).append("] ");
		buffer.append("constructCondition=[").append(constructCondition).append("] ");
		buffer.append("openLabItem=[").append(openLabItem).append("] ");
		buffer.append("projectSituation=[").append(projectSituation).append("] ");
		buffer.append("projectExpectedBenefits=[").append(projectExpectedBenefits).append("] ");
		buffer.append("actualBenefits=[").append(actualBenefits).append("] ");
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
		if (!(obj instanceof ProjectAcceptanceApplication))
			return false;
		ProjectAcceptanceApplication equalCheck = (ProjectAcceptanceApplication) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
