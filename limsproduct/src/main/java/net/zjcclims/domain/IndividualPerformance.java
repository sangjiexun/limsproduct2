package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.math.BigDecimal;

import java.util.Calendar;

import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllIndividualPerformances", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance"),
		@NamedQuery(name = "findIndividualPerformanceByAdditionNum", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.additionNum = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByAidAmount", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.aidAmount = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByAuditStatus", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.auditStatus = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByAwardsDepartment", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.awardsDepartment = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByAwardsDepartmentContaining", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.awardsDepartment like ?1"),
		@NamedQuery(name = "findIndividualPerformanceByBookConcern", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.bookConcern = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByBookConcernContaining", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.bookConcern like ?1"),
		@NamedQuery(name = "findIndividualPerformanceByClassHour", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.classHour = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByCreateDate", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.createDate = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByCreater", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.creater = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByCreaterContaining", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.creater like ?1"),
		@NamedQuery(name = "findIndividualPerformanceByCredit", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.credit = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByEndTime", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.endTime = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByFileUrl", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.fileUrl = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByFileUrlContaining", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.fileUrl like ?1"),
		@NamedQuery(name = "findIndividualPerformanceById", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.id = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByMemo", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.memo = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByOtherTeacher", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.otherTeacher = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByOtherTeacherContaining", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.otherTeacher like ?1"),
		@NamedQuery(name = "findIndividualPerformanceByPositional", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.positional = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByPositionalContaining", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.positional like ?1"),
		@NamedQuery(name = "findIndividualPerformanceByPrimaryKey", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.id = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByPublicDate", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.publicDate = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByPublicDateAfter", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.publicDate > ?1"),
		@NamedQuery(name = "findIndividualPerformanceByPublicDateBefore", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.publicDate < ?1"),
		@NamedQuery(name = "findIndividualPerformanceByPublicationType", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.publicationType = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByPublicationTypeContaining", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.publicationType like ?1"),
		@NamedQuery(name = "findIndividualPerformanceByResTeacher", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.resTeacher = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByResTeacherContaining", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.resTeacher like ?1"),
		@NamedQuery(name = "findIndividualPerformanceBySchoolAcademy", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.schoolAcademy = ?1"),
		@NamedQuery(name = "findIndividualPerformanceBySchoolAcademyContaining", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.schoolAcademy like ?1"),
		@NamedQuery(name = "findIndividualPerformanceBySchoolTerm", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.schoolTerm = ?1"),
		@NamedQuery(name = "findIndividualPerformanceBySchoolTermContaining", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.schoolTerm like ?1"),
		@NamedQuery(name = "findIndividualPerformanceByStartTime", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.startTime = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByStatus", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.status = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByTheme", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.theme = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByThemeAddition", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.themeAddition = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByThemeAdditionContaining", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.themeAddition like ?1"),
		@NamedQuery(name = "findIndividualPerformanceByType", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.type = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByYearCode", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.yearCode = ?1"),
		@NamedQuery(name = "findIndividualPerformanceByYearCodeContaining", query = "select myIndividualPerformance from IndividualPerformance myIndividualPerformance where myIndividualPerformance.yearCode like ?1") })
@Table(name = "individual_performance")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "IndividualPerformance")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class IndividualPerformance implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ��������
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ģ�飨1�γ̽���ɹ���2���¿������3��ѧ�ɹ����������4����Ŀ���⼰�걨�����5����Ӫ���6��ȳ���̲ģ�7�����ѧ��������8�����ѧ������9��ʦ���δ����񽱣�10�������ྺ���񽱣�11������ѧ��ʦ��12�̸Ŀ����걨�����⣬13���ר���������㣬14������⣬15ʦ��������Ŀ�걨�����⣬16��ȷ������ģ�17������ļ��18������������Ƚ���19ȫУ��ѡ���ڿ������20���ר�����������21�����ۺ��ԡ������ʵ���걨�ˣ�22����ʵ���һ��Ŀ��
	 * 
	 */

	@Column(name = "type")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer type;
	/**
	 * ��ȣ�2018-2019��
	 * 
	 */

	@Column(name = "year_code", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String yearCode;
	/**
	 * ѧ�ڣ�2018-2019-1��
	 * 
	 */

	@Column(name = "school_term", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String schoolTerm;
	/**
	 * ����ϵ��
	 * 
	 */

	@Column(name = "school_academy", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String schoolAcademy;
	/**
	 * �����ʦ������
	 * 
	 */

	@Column(name = "res_teacher", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String resTeacher;
	/**
	 * ������ʦ������(��ѡ)
	 * 
	 */

	@Column(name = "other_teacher")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String otherTeacher;
	/**
	 * �佱���Ż�λ
	 * 
	 */

	@Column(name = "awards_department", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String awardsDepartment;
	/**
	 * ����
	 * 
	 */

	@Column(name = "theme")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String theme;
	/**
	 * ���⸽������
	 * 
	 */

	@Column(name = "theme_addition", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String themeAddition;
	/**
	 * ֤����ļ��ϴ�·��
	 * 
	 */

	@Column(name = "file_url")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String fileUrl;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar startTime;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar endTime;
	/**
	 * �������
	 * 
	 */

	@Column(name = "aid_amount", scale = 2, precision = 10)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal aidAmount;
	/**
	 * ����
	 * 
	 */

	@Column(name = "addition_num")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer additionNum;
	/**
	 * ְ��
	 * 
	 */

	@Column(name = "positional", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String positional;
	/**
	 * ��������
	 * 
	 */

	@Column(name = "publication_type", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String publicationType;
	/**
	 * ����������
	 * 
	 */

	@Column(name = "book_concern", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String bookConcern;
	/**
	 * ����
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "public_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar publicDate;
	/**
	 * ѧʱ
	 * 
	 */

	@Column(name = "class_hour")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer classHour;
	/**
	 * ѧ��
	 * 
	 */

	@Column(name = "credit", scale = 1, precision = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal credit;
	/**
	 * �Ƿ�(1�ǣ�0��)
	 * 
	 */

	@Column(name = "status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer status;
	/**
	 * ������
	 * 
	 */

	@Column(name = "creater", length = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String creater;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createDate;
	/**
	 * ��ע
	 * 
	 */

	@Column(name = "memo")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String memo;
	/**
	 * ״̬��0�༭��1����ˣ�2���ͨ����3��˾ܾ���
	 * 
	 */

	@Column(name = "audit_status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer auditStatus;

	@OneToMany(mappedBy = "individualPerformance", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<CommonDocument> commonDocuments;
	/**
	 * ��������
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ��������
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	public java.util.Set<CommonDocument> getCommonDocuments() {
		if (commonDocuments == null) {
			commonDocuments = new java.util.LinkedHashSet<CommonDocument>();
		}
		return commonDocuments;
	}

	public void setCommonDocuments(java.util.Set<CommonDocument> commonDocuments) {
		this.commonDocuments = commonDocuments;
	}
	/**
	 * ģ�飨1�γ̽���ɹ���2���¿������3��ѧ�ɹ����������4����Ŀ���⼰�걨�����5����Ӫ���6��ȳ���̲ģ�7�����ѧ��������8�����ѧ������9��ʦ���δ����񽱣�10�������ྺ���񽱣�11������ѧ��ʦ��12�̸Ŀ����걨�����⣬13���ר���������㣬14������⣬15ʦ��������Ŀ�걨�����⣬16��ȷ������ģ�17������ļ��18������������Ƚ���19ȫУ��ѡ���ڿ������20���ר�����������21�����ۺ��ԡ������ʵ���걨�ˣ�22����ʵ���һ��Ŀ��
	 * 
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * ģ�飨1�γ̽���ɹ���2���¿������3��ѧ�ɹ����������4����Ŀ���⼰�걨�����5����Ӫ���6��ȳ���̲ģ�7�����ѧ��������8�����ѧ������9��ʦ���δ����񽱣�10�������ྺ���񽱣�11������ѧ��ʦ��12�̸Ŀ����걨�����⣬13���ר���������㣬14������⣬15ʦ��������Ŀ�걨�����⣬16��ȷ������ģ�17������ļ��18������������Ƚ���19ȫУ��ѡ���ڿ������20���ר�����������21�����ۺ��ԡ������ʵ���걨�ˣ�22����ʵ���һ��Ŀ��
	 * 
	 */
	public Integer getType() {
		return this.type;
	}

	/**
	 * ��ȣ�2018-2019��
	 * 
	 */
	public void setYearCode(String yearCode) {
		this.yearCode = yearCode;
	}

	/**
	 * ��ȣ�2018-2019��
	 * 
	 */
	public String getYearCode() {
		return this.yearCode;
	}

	/**
	 * ѧ�ڣ�2018-2019-1��
	 * 
	 */
	public void setSchoolTerm(String schoolTerm) {
		this.schoolTerm = schoolTerm;
	}

	/**
	 * ѧ�ڣ�2018-2019-1��
	 * 
	 */
	public String getSchoolTerm() {
		return this.schoolTerm;
	}

	/**
	 * ����ϵ��
	 * 
	 */
	public void setSchoolAcademy(String schoolAcademy) {
		this.schoolAcademy = schoolAcademy;
	}

	/**
	 * ����ϵ��
	 * 
	 */
	public String getSchoolAcademy() {
		return this.schoolAcademy;
	}

	/**
	 * �����ʦ������
	 * 
	 */
	public void setResTeacher(String resTeacher) {
		this.resTeacher = resTeacher;
	}

	/**
	 * �����ʦ������
	 * 
	 */
	public String getResTeacher() {
		return this.resTeacher;
	}

	/**
	 * ������ʦ������(��ѡ)
	 * 
	 */
	public void setOtherTeacher(String otherTeacher) {
		this.otherTeacher = otherTeacher;
	}

	/**
	 * ������ʦ������(��ѡ)
	 * 
	 */
	public String getOtherTeacher() {
		return this.otherTeacher;
	}

	/**
	 * �佱���Ż�λ
	 * 
	 */
	public void setAwardsDepartment(String awardsDepartment) {
		this.awardsDepartment = awardsDepartment;
	}

	/**
	 * �佱���Ż�λ
	 * 
	 */
	public String getAwardsDepartment() {
		return this.awardsDepartment;
	}

	/**
	 * ����
	 * 
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * ����
	 * 
	 */
	public String getTheme() {
		return this.theme;
	}

	/**
	 * ���⸽������
	 * 
	 */
	public void setThemeAddition(String themeAddition) {
		this.themeAddition = themeAddition;
	}

	/**
	 * ���⸽������
	 * 
	 */
	public String getThemeAddition() {
		return this.themeAddition;
	}

	/**
	 * ֤����ļ��ϴ�·��
	 * 
	 */
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	/**
	 * ֤����ļ��ϴ�·��
	 * 
	 */
	public String getFileUrl() {
		return this.fileUrl;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public Calendar getStartTime() {
		return this.startTime;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public Calendar getEndTime() {
		return this.endTime;
	}

	/**
	 * �������
	 * 
	 */
	public void setAidAmount(BigDecimal aidAmount) {
		this.aidAmount = aidAmount;
	}

	/**
	 * �������
	 * 
	 */
	public BigDecimal getAidAmount() {
		return this.aidAmount;
	}

	/**
	 * ����
	 * 
	 */
	public void setAdditionNum(Integer additionNum) {
		this.additionNum = additionNum;
	}

	/**
	 * ����
	 * 
	 */
	public Integer getAdditionNum() {
		return this.additionNum;
	}

	/**
	 * ְ��
	 * 
	 */
	public void setPositional(String positional) {
		this.positional = positional;
	}

	/**
	 * ְ��
	 * 
	 */
	public String getPositional() {
		return this.positional;
	}

	/**
	 * ��������
	 * 
	 */
	public void setPublicationType(String publicationType) {
		this.publicationType = publicationType;
	}

	/**
	 * ��������
	 * 
	 */
	public String getPublicationType() {
		return this.publicationType;
	}

	/**
	 * ����������
	 * 
	 */
	public void setBookConcern(String bookConcern) {
		this.bookConcern = bookConcern;
	}

	/**
	 * ����������
	 * 
	 */
	public String getBookConcern() {
		return this.bookConcern;
	}

	/**
	 * ����
	 * 
	 */
	public void setPublicDate(Calendar publicDate) {
		this.publicDate = publicDate;
	}

	/**
	 * ����
	 * 
	 */
	public Calendar getPublicDate() {
		return this.publicDate;
	}

	/**
	 * ѧʱ
	 * 
	 */
	public void setClassHour(Integer classHour) {
		this.classHour = classHour;
	}

	/**
	 * ѧʱ
	 * 
	 */
	public Integer getClassHour() {
		return this.classHour;
	}

	/**
	 * ѧ��
	 * 
	 */
	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	/**
	 * ѧ��
	 * 
	 */
	public BigDecimal getCredit() {
		return this.credit;
	}

	/**
	 * �Ƿ�(1�ǣ�0��)
	 * 
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * �Ƿ�(1�ǣ�0��)
	 * 
	 */
	public Integer getStatus() {
		return this.status;
	}

	/**
	 * ������
	 * 
	 */
	public void setCreater(String creater) {
		this.creater = creater;
	}

	/**
	 * ������
	 * 
	 */
	public String getCreater() {
		return this.creater;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public void setCreateDate(Calendar createDate) {
		this.createDate = createDate;
	}

	/**
	 * ����ʱ��
	 * 
	 */
	public Calendar getCreateDate() {
		return this.createDate;
	}

	/**
	 * ��ע
	 * 
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * ��ע
	 * 
	 */
	public String getMemo() {
		return this.memo;
	}

	/**
	 * ״̬��0�༭��1����ˣ�2���ͨ����3��˾ܾ���
	 * 
	 */
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * ״̬��0�༭��1����ˣ�2���ͨ����3��˾ܾ���
	 * 
	 */
	public Integer getAuditStatus() {
		return this.auditStatus;
	}

	/**
	 */
	public IndividualPerformance() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(IndividualPerformance that) {
		setId(that.getId());
		setType(that.getType());
		setYearCode(that.getYearCode());
		setSchoolTerm(that.getSchoolTerm());
		setSchoolAcademy(that.getSchoolAcademy());
		setResTeacher(that.getResTeacher());
		setOtherTeacher(that.getOtherTeacher());
		setAwardsDepartment(that.getAwardsDepartment());
		setTheme(that.getTheme());
		setThemeAddition(that.getThemeAddition());
		setFileUrl(that.getFileUrl());
		setStartTime(that.getStartTime());
		setEndTime(that.getEndTime());
		setAidAmount(that.getAidAmount());
		setAdditionNum(that.getAdditionNum());
		setPositional(that.getPositional());
		setPublicationType(that.getPublicationType());
		setBookConcern(that.getBookConcern());
		setPublicDate(that.getPublicDate());
		setClassHour(that.getClassHour());
		setCredit(that.getCredit());
		setStatus(that.getStatus());
		setCreater(that.getCreater());
		setCreateDate(that.getCreateDate());
		setMemo(that.getMemo());
		setAuditStatus(that.getAuditStatus());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("type=[").append(type).append("] ");
		buffer.append("yearCode=[").append(yearCode).append("] ");
		buffer.append("schoolTerm=[").append(schoolTerm).append("] ");
		buffer.append("schoolAcademy=[").append(schoolAcademy).append("] ");
		buffer.append("resTeacher=[").append(resTeacher).append("] ");
		buffer.append("otherTeacher=[").append(otherTeacher).append("] ");
		buffer.append("awardsDepartment=[").append(awardsDepartment).append("] ");
		buffer.append("theme=[").append(theme).append("] ");
		buffer.append("themeAddition=[").append(themeAddition).append("] ");
		buffer.append("fileUrl=[").append(fileUrl).append("] ");
		buffer.append("startTime=[").append(startTime).append("] ");
		buffer.append("endTime=[").append(endTime).append("] ");
		buffer.append("aidAmount=[").append(aidAmount).append("] ");
		buffer.append("additionNum=[").append(additionNum).append("] ");
		buffer.append("positional=[").append(positional).append("] ");
		buffer.append("publicationType=[").append(publicationType).append("] ");
		buffer.append("bookConcern=[").append(bookConcern).append("] ");
		buffer.append("publicDate=[").append(publicDate).append("] ");
		buffer.append("classHour=[").append(classHour).append("] ");
		buffer.append("credit=[").append(credit).append("] ");
		buffer.append("status=[").append(status).append("] ");
		buffer.append("creater=[").append(creater).append("] ");
		buffer.append("createDate=[").append(createDate).append("] ");
		buffer.append("memo=[").append(memo).append("] ");
		buffer.append("auditStatus=[").append(auditStatus).append("] ");

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
		if (!(obj instanceof IndividualPerformance))
			return false;
		IndividualPerformance equalCheck = (IndividualPerformance) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
