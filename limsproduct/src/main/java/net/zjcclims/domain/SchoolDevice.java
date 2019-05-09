package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
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
		@NamedQuery(name = "findAllSchoolDevices", query = "select mySchoolDevice from SchoolDevice mySchoolDevice"),
		@NamedQuery(name = "findSchoolDeviceByAcademyMemo", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.academyMemo = ?1"),
		@NamedQuery(name = "findSchoolDeviceByAcademyMemoContaining", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.academyMemo like ?1"),
		@NamedQuery(name = "findSchoolDeviceByCreatedAt", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.createdAt = ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceAccountedDate", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceAccountedDate = ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceAccountedDateAfter", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceAccountedDate > ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceAccountedDateBefore", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceAccountedDate < ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceAddress", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceAddress = ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceAddressContaining", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceAddress like ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceBuyDate", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceBuyDate = ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceBuyDateAfter", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceBuyDate > ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceBuyDateBefore", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceBuyDate < ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceCountry", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceCountry = ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceCountryContaining", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceCountry like ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceEnName", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceEnName = ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceEnNameContaining", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceEnName like ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceFormat", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceFormat = ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceFormatContaining", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceFormat like ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceName", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceName = ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceNameContaining", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceName like ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceNumber", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceNumber = ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceNumberContaining", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceNumber like ?1"),
		@NamedQuery(name = "findSchoolDeviceByDevicePattern", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.devicePattern = ?1"),
		@NamedQuery(name = "findSchoolDeviceByDevicePatternContaining", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.devicePattern like ?1"),
		@NamedQuery(name = "findSchoolDeviceByDevicePrice", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.devicePrice = ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceStatus", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceStatus = ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceStatusContaining", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceStatus like ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceSupplier", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceSupplier = ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceSupplierContaining", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceSupplier like ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceUseDirection", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceUseDirection = ?1"),
		@NamedQuery(name = "findSchoolDeviceByDeviceUseDirectionContaining", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceUseDirection like ?1"),
		@NamedQuery(name = "findSchoolDeviceById", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.id = ?1"),
		@NamedQuery(name = "findSchoolDeviceByManufacturer", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.manufacturer = ?1"),
		@NamedQuery(name = "findSchoolDeviceByManufacturerContaining", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.manufacturer like ?1"),
		@NamedQuery(name = "findSchoolDeviceByPrimaryKey", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.deviceNumber = ?1"),
		@NamedQuery(name = "findSchoolDeviceByProjectSource", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.projectSource = ?1"),
		@NamedQuery(name = "findSchoolDeviceByProjectSourceContaining", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.projectSource like ?1"),
		@NamedQuery(name = "findSchoolDeviceBySystemRoom", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.systemRoom = ?1"),
		@NamedQuery(name = "findSchoolDeviceBySystemRoomContaining", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.systemRoom like ?1"),
		@NamedQuery(name = "findSchoolDeviceByProjectCode", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.projectCode = ?1"),
		@NamedQuery(name = "findSchoolDeviceByProjectCodeContaining", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.projectCode like ?1"),
		@NamedQuery(name = "findSchoolDeviceBySupplyPhone", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.supplyPhone = ?1"),
		@NamedQuery(name = "findSchoolDeviceBySupplyPhoneContaining", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.supplyPhone like ?1"),
		@NamedQuery(name = "findSchoolDeviceByUpdatedAt", query = "select mySchoolDevice from SchoolDevice mySchoolDevice where mySchoolDevice.updatedAt = ?1") })
@Table(name = "school_device")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "SchoolDevice")
@XmlRootElement(namespace = "zjcclims/net/zjcclims/domain")
public class SchoolDevice implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * �������
	 * 
	 */

	@Column(name = "device_number", length = 40, nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@XmlElement
	String deviceNumber;
	/**
	 * �������
	 * 
	 */

	@Column(name = "device_name", length = 200)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String deviceName;
	/**
	 * ����Ӣ�����
	 * 
	 */

	@Column(name = "device_en_name", length = 200)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String deviceEnName;
	/**
	 * �����ͺ�
	 * 
	 */

	@Column(name = "device_pattern", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String devicePattern;
	/**
	 * �������
	 * 
	 */

	@Column(name = "device_format", length = 1000)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String deviceFormat;
	/**
	 * ʹ�÷���
	 * 
	 */

	@Column(name = "device_use_direction", length = 100)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String deviceUseDirection;
	/**
	 * �豸��������
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "device_buy_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar deviceBuyDate;
	/**
	 * ��ŵص�
	 * 
	 */

	@Column(name = "device_address", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String deviceAddress;
	/**
	 * ���
	 * 
	 */

	@Column(name = "device_country", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String deviceCountry;
	/**
	 * �豸�۸�
	 * 
	 */

	@Column(name = "device_price", scale = 2, precision = 20)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal devicePrice;
	/**
	 * �豸��������
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "device_accounted_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar deviceAccountedDate;
	/**
	 * �豸��Ӧ��
	 * 
	 */

	@Column(name = "device_supplier", length = 200)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String deviceSupplier;
	/**
	 * ������
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar createdAt;
	/**
	 * ������
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar updatedAt;
	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer id;
	/**
	 * ��Ŀ��Դ
	 * 
	 */

	@Column(name = "project_source", length = 200)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String projectSource;
	/**
	 * ����
	 * 
	 */

	@Column(name = "manufacturer", length = 200)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String manufacturer;
	/**
	 * Ժϵ��ע
	 * 
	 */

	@Column(name = "academy_memo", length = 200)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String academyMemo;
	/**
	 * �豸״̬
	 * 
	 */

	@Column(name = "device_status", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String deviceStatus;
	
	@Column(name = "sn")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String sn;
	
	/**
	 * 资产类别
	 */
	@Column(name = "category_main", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String categoryMain;
	
	/**
	 * 资产类型
	 */
	@Column(name = "category_type", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String categoryType;
	
	@Column(name = "inner_same")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer innerSame;
	
	@Column(name = "inner_device_name")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String innerDeviceName;
	
	@Column(name = "use_hours", scale = 2, precision = 11)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal useHours ;
	
	@Column(name = "use_count")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer useCount;
	
	/**
	 */

	@Column(name = "system_room", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String systemRoom;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "system_build", referencedColumnName = "build_number") })
	@XmlTransient
	SystemBuild systemBuild;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "department_number", referencedColumnName = "academy_number") })
	@XmlTransient
	SchoolAcademy schoolAcademy;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "keep_user", referencedColumnName = "username") })
	@XmlTransient
	User userByKeepUser;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "user_number", referencedColumnName = "username") })
	@XmlTransient
	User userByUserNumber;
	/**
	 */
	@OneToMany(mappedBy = "schoolDevice", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoomDevice> labRoomDevices;
	
	@OneToMany(mappedBy = "schoolDevice", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.OperationItemDevice> operationItemDevices;

	/**
	 * �������
	 * 
	 */
	/**
	 * ¹©Ó¦µ¥Î»ÁªÏµ·½Ê½
	 * 
	 */

	@Column(name = "supply_phone", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String supplyPhone;
	/**
	 * ÏîÄ¿±àºÅ
	 * 
	 */

	@Column(name = "project_code", length = 40)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String projectCode;

	@Column(name = "device_brand", length = 255)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String deviceBrand;

	@Column(name = "memo", length = 255)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String memo;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "device_flag", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByDeviceFlag;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "use_status", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByUseStatus;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "device_source", referencedColumnName = "id") })
	@XmlTransient
	CDictionary CDictionaryByDeviceSource;
	
	/**
	 * ¹©Ó¦µ¥Î»ÁªÏµ·½Ê½
	 * 
	 */
	public void setSupplyPhone(String supplyPhone) {
		this.supplyPhone = supplyPhone;
	}

	/**
	 * ¹©Ó¦µ¥Î»ÁªÏµ·½Ê½
	 * 
	 */
	public String getSupplyPhone() {
		return this.supplyPhone;
	}

	/**
	 * ÏîÄ¿±àºÅ
	 * 
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	/**
	 * ÏîÄ¿±àºÅ
	 * 
	 */
	public String getProjectCode() {
		return this.projectCode;
	}

	/**
	 */
	public void setCDictionaryByDeviceFlag(CDictionary CDictionaryByDeviceFlag) {
		this.CDictionaryByDeviceFlag = CDictionaryByDeviceFlag;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByDeviceFlag() {
		return CDictionaryByDeviceFlag;
	}

	/**
	 */
	public void setCDictionaryByUseStatus(CDictionary CDictionaryByUseStatus) {
		this.CDictionaryByUseStatus = CDictionaryByUseStatus;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByUseStatus() {
		return CDictionaryByUseStatus;
	}

	/**
	 */
	public void setCDictionaryByDeviceSource(CDictionary CDictionaryByDeviceSource) {
		this.CDictionaryByDeviceSource = CDictionaryByDeviceSource;
	}

	/**
	 */
	@JsonIgnore
	public CDictionary getCDictionaryByDeviceSource() {
		return CDictionaryByDeviceSource;
	}
	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	/**
	 * �������
	 * 
	 */
	public String getDeviceNumber() {
		return this.deviceNumber;
	}
	
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
	
	public String getCategoryMain() {
		return categoryMain;
	}

	public void setCategoryMain(String categoryMain) {
		this.categoryMain = categoryMain;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	/**
	 * �������
	 * 
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * �������
	 * 
	 */
	public String getDeviceName() {
		return this.deviceName;
	}

	/**
	 * ����Ӣ�����
	 * 
	 */
	public void setDeviceEnName(String deviceEnName) {
		this.deviceEnName = deviceEnName;
	}

	/**
	 * ����Ӣ�����
	 * 
	 */
	public String getDeviceEnName() {
		return this.deviceEnName;
	}

	/**
	 * �����ͺ�
	 * 
	 */
	public void setDevicePattern(String devicePattern) {
		this.devicePattern = devicePattern;
	}

	/**
	 * �����ͺ�
	 * 
	 */
	public String getDevicePattern() {
		return this.devicePattern;
	}

	/**
	 * �������
	 * 
	 */
	public void setDeviceFormat(String deviceFormat) {
		this.deviceFormat = deviceFormat;
	}

	/**
	 * �������
	 * 
	 */
	public String getDeviceFormat() {
		return this.deviceFormat;
	}

	/**
	 * ʹ�÷���
	 * 
	 */
	public void setDeviceUseDirection(String deviceUseDirection) {
		this.deviceUseDirection = deviceUseDirection;
	}

	/**
	 * ʹ�÷���
	 * 
	 */
	public String getDeviceUseDirection() {
		return this.deviceUseDirection;
	}

	/**
	 * �豸��������
	 * 
	 */
	public void setDeviceBuyDate(Calendar deviceBuyDate) {
		this.deviceBuyDate = deviceBuyDate;
	}

	/**
	 * �豸��������
	 * 
	 */
	public Calendar getDeviceBuyDate() {
		return this.deviceBuyDate;
	}

	/**
	 * ��ŵص�
	 * 
	 */
	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
	}

	/**
	 * ��ŵص�
	 * 
	 */
	public String getDeviceAddress() {
		return this.deviceAddress;
	}

	/**
	 * ���
	 * 
	 */
	public void setDeviceCountry(String deviceCountry) {
		this.deviceCountry = deviceCountry;
	}

	/**
	 * ���
	 * 
	 */
	public String getDeviceCountry() {
		return this.deviceCountry;
	}

	/**
	 * �豸�۸�
	 * 
	 */
	public void setDevicePrice(BigDecimal devicePrice) {
		this.devicePrice = devicePrice;
	}

	/**
	 * �豸�۸�
	 * 
	 */
	public BigDecimal getDevicePrice() {
		return this.devicePrice;
	}

	/**
	 * �豸��������
	 * 
	 */
	public void setDeviceAccountedDate(Calendar deviceAccountedDate) {
		this.deviceAccountedDate = deviceAccountedDate;
	}

	/**
	 * �豸��������
	 * 
	 */
	public Calendar getDeviceAccountedDate() {
		return this.deviceAccountedDate;
	}

	/**
	 * �豸��Ӧ��
	 * 
	 */
	public void setDeviceSupplier(String deviceSupplier) {
		this.deviceSupplier = deviceSupplier;
	}

	/**
	 * �豸��Ӧ��
	 * 
	 */
	public String getDeviceSupplier() {
		return this.deviceSupplier;
	}

	/**
	 * ������
	 * 
	 */
	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * ������
	 * 
	 */
	public Calendar getCreatedAt() {
		return this.createdAt;
	}

	/**
	 * ������
	 * 
	 */
	public void setUpdatedAt(Calendar updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * ������
	 * 
	 */
	public Calendar getUpdatedAt() {
		return this.updatedAt;
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
	 * ��Ŀ��Դ
	 * 
	 */
	public void setProjectSource(String projectSource) {
		this.projectSource = projectSource;
	}

	/**
	 * ��Ŀ��Դ
	 * 
	 */
	public String getProjectSource() {
		return this.projectSource;
	}

	/**
	 * ����
	 * 
	 */
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	/**
	 * ����
	 * 
	 */
	public String getManufacturer() {
		return this.manufacturer;
	}

	/**
	 * Ժϵ��ע
	 * 
	 */
	public void setAcademyMemo(String academyMemo) {
		this.academyMemo = academyMemo;
	}

	/**
	 * Ժϵ��ע
	 * 
	 */
	public String getAcademyMemo() {
		return this.academyMemo;
	}

	/**
	 * �豸״̬
	 * 
	 */
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	/**
	 * �豸״̬
	 * 
	 */
	public String getDeviceStatus() {
		return this.deviceStatus;
	}
	public Integer getInnerSame() {
		return innerSame;
	}

	public void setInnerSame(Integer innerSame) {
		this.innerSame = innerSame;
	}

	public String getDeviceBrand() {
		return deviceBrand;
	}

	public void setDeviceBrand(String deviceBrand) {
		this.deviceBrand = deviceBrand;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getInnerDeviceName() {
		return innerDeviceName;
	}

	public void setInnerDeviceName(String innerDeviceName) {
		this.innerDeviceName = innerDeviceName;
	}

	public BigDecimal getUseHours() {
		return useHours;
	}

	public void setUseHours(BigDecimal useHours) {
		this.useHours = useHours;
	}

	public Integer getUseCount() {
		return useCount;
	}

	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}
	
	/**
	 */
	public void setSystemRoom(String systemRoom) {
		this.systemRoom = systemRoom;
	}

	/**
	 */
	public String getSystemRoom() {
		return this.systemRoom;
	}

	/**
	 */
	public void setSystemBuild(SystemBuild systemBuild) {
		this.systemBuild = systemBuild;
	}

	/**
	 */
	@JsonIgnore
	public SystemBuild getSystemBuild() {
		return systemBuild;
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
	public void setUserByKeepUser(User userByKeepUser) {
		this.userByKeepUser = userByKeepUser;
	}

	/**
	 */
	@JsonIgnore
	public User getUserByKeepUser() {
		return userByKeepUser;
	}

	/**
	 */
	public void setUserByUserNumber(User userByUserNumber) {
		this.userByUserNumber = userByUserNumber;
	}

	/**
	 */
	@JsonIgnore
	public User getUserByUserNumber() {
		return userByUserNumber;
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
	
	public void setOperationItemDevices(Set<OperationItemDevice> operationItemDevices) {
		this.operationItemDevices = operationItemDevices;
	}

	/**
	 */
	@JsonIgnore
	public Set<OperationItemDevice> getOperationItemDevices() {
		if (operationItemDevices == null) {
			operationItemDevices = new java.util.LinkedHashSet<net.zjcclims.domain.OperationItemDevice>();
		}
		return operationItemDevices;
	}

	/**
	 */
	public SchoolDevice() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(SchoolDevice that) {
		setDeviceNumber(that.getDeviceNumber());
		setDeviceName(that.getDeviceName());
		setDeviceEnName(that.getDeviceEnName());
		setSn(that.getSn());
		setCategoryMain(that.getCategoryMain());
		setCategoryType(that.getCategoryType());
		setDevicePattern(that.getDevicePattern());
		setDeviceFormat(that.getDeviceFormat());
		setDeviceUseDirection(that.getDeviceUseDirection());
		setDeviceBuyDate(that.getDeviceBuyDate());
		setDeviceAddress(that.getDeviceAddress());
		setDeviceCountry(that.getDeviceCountry());
		setDevicePrice(that.getDevicePrice());
		setDeviceAccountedDate(that.getDeviceAccountedDate());
		setDeviceSupplier(that.getDeviceSupplier());
		setCreatedAt(that.getCreatedAt());
		setUpdatedAt(that.getUpdatedAt());
		setId(that.getId());
		setProjectSource(that.getProjectSource());
		setManufacturer(that.getManufacturer());
		setAcademyMemo(that.getAcademyMemo());
		setDeviceStatus(that.getDeviceStatus());
		setSystemRoom(that.getSystemRoom());
		setSystemBuild(that.getSystemBuild());
		setSchoolAcademy(that.getSchoolAcademy());
		setUserByKeepUser(that.getUserByKeepUser());
		setUserByUserNumber(that.getUserByUserNumber());
		setLabRoomDevices(new java.util.LinkedHashSet<net.zjcclims.domain.LabRoomDevice>(that.getLabRoomDevices()));
		setSupplyPhone(that.getSupplyPhone());
		setProjectCode(that.getProjectCode());
		setCDictionaryByDeviceFlag(that.getCDictionaryByDeviceFlag());
		setCDictionaryByUseStatus(that.getCDictionaryByUseStatus());
		setCDictionaryByDeviceSource(that.getCDictionaryByDeviceSource());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("deviceNumber=[").append(deviceNumber).append("] ");
		buffer.append("deviceName=[").append(deviceName).append("] ");
		buffer.append("deviceEnName=[").append(deviceEnName).append("] ");
		buffer.append("devicePattern=[").append(devicePattern).append("] ");
		buffer.append("deviceFormat=[").append(deviceFormat).append("] ");
		buffer.append("deviceUseDirection=[").append(deviceUseDirection).append("] ");
		buffer.append("deviceBuyDate=[").append(deviceBuyDate).append("] ");
		buffer.append("deviceAddress=[").append(deviceAddress).append("] ");
		buffer.append("deviceCountry=[").append(deviceCountry).append("] ");
		buffer.append("devicePrice=[").append(devicePrice).append("] ");
		buffer.append("deviceAccountedDate=[").append(deviceAccountedDate).append("] ");
		buffer.append("deviceSupplier=[").append(deviceSupplier).append("] ");
		buffer.append("createdAt=[").append(createdAt).append("] ");
		buffer.append("updatedAt=[").append(updatedAt).append("] ");
		buffer.append("id=[").append(id).append("] ");
		buffer.append("sn=[").append(sn).append("] ");
		buffer.append("categoryMain=[").append(categoryMain).append("] ");
		buffer.append("categoryType=[").append(categoryType).append("] ");
		buffer.append("projectSource=[").append(projectSource).append("] ");
		buffer.append("manufacturer=[").append(manufacturer).append("] ");
		buffer.append("academyMemo=[").append(academyMemo).append("] ");
		buffer.append("deviceStatus=[").append(deviceStatus).append("] ");
		buffer.append("systemRoom=[").append(systemRoom).append("] ");
		buffer.append("supplyPhone=[").append(supplyPhone).append("] ");
		buffer.append("projectCode=[").append(projectCode).append("] ");

		return buffer.toString();
	}

	/**
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((deviceNumber == null) ? 0 : deviceNumber.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof SchoolDevice))
			return false;
		SchoolDevice equalCheck = (SchoolDevice) obj;
		if ((deviceNumber == null && equalCheck.deviceNumber != null) || (deviceNumber != null && equalCheck.deviceNumber == null))
			return false;
		if (deviceNumber != null && !deviceNumber.equals(equalCheck.deviceNumber))
			return false;
		return true;
	}
}
