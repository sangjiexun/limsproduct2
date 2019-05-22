package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllAssetApps", query = "select myAssetApp from AssetApp myAssetApp"),
		@NamedQuery(name = "findAssetAppByAppDate", query = "select myAssetApp from AssetApp myAssetApp where myAssetApp.appDate = ?1"),
		@NamedQuery(name = "findAssetAppByAppDateAfter", query = "select myAssetApp from AssetApp myAssetApp where myAssetApp.appDate > ?1"),
		@NamedQuery(name = "findAssetAppByAppDateBefore", query = "select myAssetApp from AssetApp myAssetApp where myAssetApp.appDate < ?1"),
		@NamedQuery(name = "findAssetAppByAppNo", query = "select myAssetApp from AssetApp myAssetApp where myAssetApp.appNo = ?1"),
		@NamedQuery(name = "findAssetAppByAppNoContaining", query = "select myAssetApp from AssetApp myAssetApp where myAssetApp.appNo like ?1"),
		@NamedQuery(name = "findAssetAppByAppType", query = "select myAssetApp from AssetApp myAssetApp where myAssetApp.appType = ?1"),
		@NamedQuery(name = "findAssetAppByAssetAuditStatus", query = "select myAssetApp from AssetApp myAssetApp where myAssetApp.assetAuditStatus = ?1"),
		@NamedQuery(name = "findAssetAppByAssetStatu", query = "select myAssetApp from AssetApp myAssetApp where myAssetApp.assetStatu = ?1"),
		@NamedQuery(name = "findAssetAppById", query = "select myAssetApp from AssetApp myAssetApp where myAssetApp.id = ?1"),
		@NamedQuery(name = "findAssetAppByMem", query = "select myAssetApp from AssetApp myAssetApp where myAssetApp.mem = ?1"),
		@NamedQuery(name = "findAssetAppByPrimaryKey", query = "select myAssetApp from AssetApp myAssetApp where myAssetApp.id = ?1"),
		@NamedQuery(name = "findAssetAppByProjectName", query = "select myAssetApp from AssetApp myAssetApp where myAssetApp.projectName = ?1"),
		@NamedQuery(name = "findAssetAppByProjectNameContaining", query = "select myAssetApp from AssetApp myAssetApp where myAssetApp.projectName like ?1"),
		@NamedQuery(name = "findAssetAppBySaveSubmit", query = "select myAssetApp from AssetApp myAssetApp where myAssetApp.saveSubmit = ?1") })
@Table(name = "asset_app")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "AssetApp")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class AssetApp implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ����_����ǰ�Ṻ������
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ���뵥��� �Զ���� ��ʽΪ����ǰ����+XXXX
	 * 
	 */

	@Column(name = "app_no")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String appNo;
	/**
	 * ����ʱ�� Ĭ�ϵ�ǰ����
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "app_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar appDate;
	/**
	 * ����״̬��1����or2δ����
	 * 
	 */

	@Column(name = "asset_statu")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer assetStatu;
	
	/**
	 * ����״̬��1����or2δ����
	 * 
	 */

	@Column(name = "stock_status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer stockStatus; 

	/**
	 * ���״̬ 1���ͨ�� 2���� 3����� 4δ���
	 * 
	 */

	@Column(name = "asset_audit_status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer assetAuditStatus;
	/**
	 * ���������ͣ���ʦ��ʵ���ң�ѧԺ��
	 * 
	 */

	@Column(name = "app_type")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer appType;
	/**
	 * ����
	 * 
	 */

	@Column(name = "save_submit")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer saveSubmit;
	/**
	 * ʵ��������
	 * 
	 */

	@Column(name = "`project_ name`")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String projectName;
	
	@Column(name = "type")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer type;

	@Column(name = "price")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Double price;

	@Column(name = "category_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer categoryId;

	@Column(name = "begin_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Date beginDate;

	@Column(name = "end_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Date endDate;

	@Column(name = "academy_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String academyNumber;

	@Column(name = "center_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer centerId;

	@Column(name = "cur_audit_level")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String curAuditLevel;

	/**
	 * ��ע
	 * 
	 */

	@Column(name = "mem", columnDefinition = "TEXT")
	@Basic(fetch = FetchType.EAGER)
	@Lob
	@XmlElement
	String mem;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "app_user", referencedColumnName = "username") })
	@XmlTransient
	User user;
	
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "fisrt_audit_user", referencedColumnName = "username") })
	@XmlTransient
	User userByFirstAuditUser;
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "second_audit_user", referencedColumnName = "username") })
	@XmlTransient
	User userBySecondAuditUser;

	/**
	 */
	@OneToMany(mappedBy = "assetApp", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.AssetAppAudit> assetAppAudits;
	/**
	 */
	@OneToMany(mappedBy = "assetApp", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.AssetAppRecord> assetAppRecords;
	
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "operation_item_id", referencedColumnName = "id") })
	@XmlTransient
	OperationItem operationItem;

	@Column(name = "course_no")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String courseNo;

	@Column(name = "reject_reason")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String rejectReason;
	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	/**
	 * ����_����ǰ�Ṻ������
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * ����_����ǰ�Ṻ������
	 * 
	 */
	public Integer getId() {
		return this.id;
	}


	/**
	 * ���뵥��� �Զ���� ��ʽΪ����ǰ����+XXXX
	 * 
	 */
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	/**
	 * ���뵥��� �Զ���� ��ʽΪ����ǰ����+XXXX
	 * 
	 */
	public String getAppNo() {
		return this.appNo;
	}

	/**
	 * ����ʱ�� Ĭ�ϵ�ǰ����
	 * 
	 */
	public void setAppDate(Calendar appDate) {
		this.appDate = appDate;
	}

	/**
	 * ����ʱ�� Ĭ�ϵ�ǰ����
	 * 
	 */
	public Calendar getAppDate() {
		return this.appDate;
	}

	/**
	 * ����״̬��1����or2δ����
	 * 
	 */
	public void setAssetStatu(Integer assetStatu) {
		this.assetStatu = assetStatu;
	}

	/**
	 * ����״̬��1����or2δ����
	 * 
	 */
	public Integer getAssetStatu() {
		return this.assetStatu;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * ���״̬ 1���ͨ�� 2���� 3����� 4δ���
	 * 
	 */
	public void setAssetAuditStatus(Integer assetAuditStatus) {
		this.assetAuditStatus = assetAuditStatus;
	}

	/**
	 * ���״̬ 1���ͨ�� 2���� 3����� 4δ���
	 * 
	 */
	public Integer getAssetAuditStatus() {
		return this.assetAuditStatus;
	}

	/**
	 * ���������ͣ���ʦ��ʵ���ң�ѧԺ��
	 * 
	 */
	public void setAppType(Integer appType) {
		this.appType = appType;
	}

	/**
	 * ���������ͣ���ʦ��ʵ���ң�ѧԺ��
	 * 
	 */
	public Integer getAppType() {
		return this.appType;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getAcademyNumber() {
		return academyNumber;
	}

	public void setAcademyNumber(String academyNumber) {
		this.academyNumber = academyNumber;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getCenterId() {
		return centerId;
	}

	public void setCenterId(Integer centerId) {
		this.centerId = centerId;
	}

	public String getCurAuditLevel() {
		return curAuditLevel;
	}

	public void setCurAuditLevel(String curAuditLevel) {
		this.curAuditLevel = curAuditLevel;
	}

	/**
	 * ����
	 * 
	 */
	public void setSaveSubmit(Integer saveSubmit) {
		this.saveSubmit = saveSubmit;
	}

	/**
	 * ����
	 * 
	 */
	public Integer getSaveSubmit() {
		return this.saveSubmit;
	}

	/**
	 * ʵ��������
	 * 
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * ʵ��������
	 * 
	 */
	public String getProjectName() {
		return this.projectName;
	}

	/**
	 * ��ע
	 * 
	 */
	public void setMem(String mem) {
		this.mem = mem;
	}

	/**
	 * ��ע
	 * 
	 */
	public String getMem() {
		return this.mem;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Integer getStockStatus() {
		return stockStatus;
	}

	public void setStockStatus(Integer stockStatus) {
		this.stockStatus = stockStatus;
	}

	/**
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 */
	@JsonIgnore
	public User getUser() {
		return user;
	}
	
	
	/**
	 */
	public void setUserByFirstAudtitUser(User userByFirstAuditUser) {
		this.userByFirstAuditUser = userByFirstAuditUser;
	}

	/**
	 */
	@JsonIgnore
	public User getUserByFirstAudtitUser() {
		return userByFirstAuditUser;
	}
	
	/**
	 */
	public void setUserBySecondAudtitUser(User userBySecondAuditUser) {
		this.userBySecondAuditUser = userBySecondAuditUser;
	}

	/**
	 */
	@JsonIgnore
	public User getUserBySecondAudtitUser() {
		return userBySecondAuditUser;
	}
	
	
	/**
	 */
	public void setOperationItem(OperationItem operationItem) {
		this.operationItem = operationItem;
	}

	/**
	 */
	@JsonIgnore
	public OperationItem getOperationItem() {
		return operationItem;
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
	public AssetApp() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(AssetApp that) {
		setId(that.getId());
		setAppNo(that.getAppNo());
		setAppDate(that.getAppDate());
		setAssetStatu(that.getAssetStatu());
		setAssetAuditStatus(that.getAssetAuditStatus());
		setAppType(that.getAppType());
		setSaveSubmit(that.getSaveSubmit());
		setProjectName(that.getProjectName());
		setMem(that.getMem());
		setUser(that.getUser());
		setOperationItem(that.getOperationItem());
		setAssetAppAudits(new java.util.LinkedHashSet<net.zjcclims.domain.AssetAppAudit>(that.getAssetAppAudits()));
		setAssetAppRecords(new java.util.LinkedHashSet<net.zjcclims.domain.AssetAppRecord>(that.getAssetAppRecords()));
		setCourseNo(that.getCourseNo());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("appNo=[").append(appNo).append("] ");
		buffer.append("appDate=[").append(appDate).append("] ");
		buffer.append("assetStatu=[").append(assetStatu).append("] ");
		buffer.append("assetAuditStatus=[").append(assetAuditStatus).append("] ");
		buffer.append("appType=[").append(appType).append("] ");
		buffer.append("saveSubmit=[").append(saveSubmit).append("] ");
		buffer.append("projectName=[").append(projectName).append("] ");
		buffer.append("mem=[").append(mem).append("] ");

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
		if (!(obj instanceof AssetApp))
			return false;
		AssetApp equalCheck = (AssetApp) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
