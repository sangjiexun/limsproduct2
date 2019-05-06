package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonIgnore;

import net.zjcclims.domain.LabRoom;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllSoftwares", query = "select mySoftware from Software mySoftware"),
		@NamedQuery(name = "findSoftwareByBuyType", query = "select mySoftware from Software mySoftware where mySoftware.buyType = ?1"),
		@NamedQuery(name = "findSoftwareByCode", query = "select mySoftware from Software mySoftware where mySoftware.code = ?1"),
		@NamedQuery(name = "findSoftwareByCodeContaining", query = "select mySoftware from Software mySoftware where mySoftware.code like ?1"),
		@NamedQuery(name = "findSoftwareByEdition", query = "select mySoftware from Software mySoftware where mySoftware.edition = ?1"),
		@NamedQuery(name = "findSoftwareByEditionContaining", query = "select mySoftware from Software mySoftware where mySoftware.edition like ?1"),
		@NamedQuery(name = "findSoftwareBySupplierTel", query = "select mySoftware from Software mySoftware where mySoftware.supplierTel = ?1"),
		@NamedQuery(name = "findSoftwareById", query = "select mySoftware from Software mySoftware where mySoftware.id = ?1"),
		@NamedQuery(name = "findSoftwareByName", query = "select mySoftware from Software mySoftware where mySoftware.name = ?1"),
		@NamedQuery(name = "findSoftwareByNameContaining", query = "select mySoftware from Software mySoftware where mySoftware.name like ?1"),
		@NamedQuery(name = "findSoftwareByNo", query = "select mySoftware from Software mySoftware where mySoftware.no = ?1"),
		@NamedQuery(name = "findSoftwareByNoContaining", query = "select mySoftware from Software mySoftware where mySoftware.no like ?1"),
		@NamedQuery(name = "findSoftwareByPrice", query = "select mySoftware from Software mySoftware where mySoftware.price = ?1"),
		@NamedQuery(name = "findSoftwareByPrimaryKey", query = "select mySoftware from Software mySoftware where mySoftware.id = ?1"),
		@NamedQuery(name = "findSoftwareByProperty", query = "select mySoftware from Software mySoftware where mySoftware.property = ?1"),
		@NamedQuery(name = "findSoftwareByFramework", query = "select mySoftware from Software mySoftware where mySoftware.framework = ?1"),
		@NamedQuery(name = "findSoftwareByFrameworkContaining", query = "select mySoftware from Software mySoftware where mySoftware.framework = ?1"),
		@NamedQuery(name = "findSoftwareBySupplier", query = "select mySoftware from Software mySoftware where mySoftware.supplier = ?1"),
		@NamedQuery(name = "findSoftwareBySupplierContaining", query = "select mySoftware from Software mySoftware where mySoftware.supplier = ?1"),
		@NamedQuery(name = "findSoftwareByInstallInstruction", query = "select mySoftware from Software mySoftware where mySoftware.installInstruction = ?1"),
		@NamedQuery(name = "findSoftwareByInstallInstructionContaining", query = "select mySoftware from Software mySoftware where mySoftware.installInstruction = ?1"),
		@NamedQuery(name = "findSoftwareByUseInstruction", query = "select mySoftware from Software mySoftware where mySoftware.useInstruction = ?1"),
		@NamedQuery(name = "findSoftwareByUseInstructionContaining", query = "select mySoftware from Software mySoftware where mySoftware.useInstruction = ?1"),
		@NamedQuery(name = "findSoftwareByOperationRequirement", query = "select mySoftware from Software mySoftware where mySoftware.operationRequirement = ?1"),
		@NamedQuery(name = "findSoftwareByOperationRequirementContaining", query = "select mySoftware from Software mySoftware where mySoftware.operationRequirement = ?1"),
		@NamedQuery(name = "findSoftwareByPropertyContaining", query = "select mySoftware from Software mySoftware where mySoftware.property like ?1"),
		@NamedQuery(name = "findSoftwareByPurchaseDate", query = "select mySoftware from Software mySoftware where mySoftware.purchaseDate = ?1"),
		@NamedQuery(name = "findSoftwareByPurchaseDateAfter", query = "select mySoftware from Software mySoftware where mySoftware.purchaseDate > ?1"),
		@NamedQuery(name = "findSoftwareByPurchaseDateBefore", query = "select mySoftware from Software mySoftware where mySoftware.purchaseDate < ?1"),
		@NamedQuery(name = "findSoftwareByPurchasePerson", query = "select mySoftware from Software mySoftware where mySoftware.purchasePerson = ?1"),
		@NamedQuery(name = "findSoftwareByPurchasePersonContaining", query = "select mySoftware from Software mySoftware where mySoftware.purchasePerson like ?1") })
@Table(name = "software")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "Software")
public class Software implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 * 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
	/**
	 * ������
	 * 
	 */

	@Column(name = "code", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String code;
	/**
	 * ������
	 * 
	 */

	@Column(name = "name", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String name;
	/**
	 * �������
	 * 
	 */

	@Column(name = "property", length = 100)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String property;
	
	@Column(name = "operation_requirement", length = 255)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String operationRequirement;
	
	@Column(name = "lab_room", length = 255)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String labRoom;	
	
	@Column(name = "install_instruction", length = 255)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String installInstruction;
	
	@Column(name = "use_instruction", length = 255)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String useInstruction;

	@Column(name = "framework", length = 255)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String framework;
	
	@Column(name = "supplier", length = 255)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String supplier;
	
	
	/**
	 * �۸�
	 * 
	 */

	@Column(name = "price", precision = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal price;
	/**
	 * �ɹ�����
	 * 
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "purchase_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar purchaseDate;
	/**
	 * ����ʱ�䣨�꣩
	 * 
	 */

	@Column(name = "supplier_tel")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String supplierTel;
	/**
	 * ��������
	 * 
	 */

	@Column(name = "purchase_person", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String purchasePerson;
	/**
	 * ʹ������
	 * 
	 */

	@Column(name = "edition", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String edition;
	/**
	 * ������
	 * 
	 */

	@Column(name = "no", length = 50)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String no;
	/**
	 * ��������
	 * 
	 */

	@Column(name = "buyType")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer buyType;
	
	@Column(name = "operation_item", length = 255)
    @Basic(fetch = FetchType.EAGER)
	@XmlElement
	String operationItem;
	
	@Column(name = "dongle")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer dongle;
	
	public void setDongle(Integer dongle) {
		this.dongle = dongle;
	}

	public Integer getDongle() {
		return this.dongle;
	}
	
	@Column(name = "has_cd")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer hasCd;
	
	public void setHasCd(Integer hasCd) {
		this.hasCd = hasCd;
	}

	public Integer getHasCd() {
		return this.hasCd;
	}

	@Column(name = "points")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer points;
	
	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getPoints() {
		return this.points;
	}

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "academy", referencedColumnName = "academy_number") })
	@XmlTransient
	SchoolAcademy schoolAcademy;
	
	@OneToMany(mappedBy = "software", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SoftwareItemRelated> softwareItemRelateds;

	/**
	 */
	@OneToMany(mappedBy = "software", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.SoftwareRoomRelated> softwareRoomRelateds;
	/**
	 */
	@OneToMany(mappedBy = "software", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.TimetableSoftwareRelated> timetableSoftwareRelateds;
	/**
	 */
	@ManyToMany(mappedBy = "softwares", fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.LabRoom> labRooms;
	
	/**
	 */
	@OneToMany(mappedBy = "software", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.CommonDocument> commonDocuments;
	
	

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
	 * id
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * id
	 * 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * ������
	 * 
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * ������
	 * 
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * ������
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ������
	 * 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * �������
	 * 
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	public String getProperty() {
		return this.property;
	}

	public void setOperationRequirement(String operationRequirement) {
		this.operationRequirement = operationRequirement;
	}

	public String getOperationRequirement() {
		return this.operationRequirement;
	}
	
	public void setLabRoom(String labRoom) {
		this.labRoom = labRoom;
	}

	public String getLabRoom() {
		return this.labRoom;
	}
	
	public void setInstallInstruction(String installInstruction) {
		this.installInstruction = installInstruction;
	}

	public String getInstallInstruction() {
		return this.installInstruction;
	}
	
	public void setUseInstruction(String useInstruction) {
		this.useInstruction = useInstruction;
	}

	public String getUseInstruction() {
		return this.useInstruction;
	}
	public void setFramework(String framework) {
		this.framework = framework;
	}

	public String getFramework() {
		return this.framework;
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
	
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSupplier() {
		return this.supplier;
	}

	/**
	 * �۸�
	 * 
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * �۸�
	 * 
	 */
	public BigDecimal getPrice() {
		return this.price;
	}

	/**
	 * �ɹ�����
	 * 
	 */
	public void setPurchaseDate(Calendar purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
	 * �ɹ�����
	 * 
	 */
	public Calendar getPurchaseDate() {
		return this.purchaseDate;
	}

	/**
	 * ����ʱ�䣨�꣩
	 * 
	 */
	public void setSupplierTel(String supplierTel) {
		this.supplierTel = supplierTel;
	}

	public String getSupplierTel() {
		return this.supplierTel;
	}
	/**
	 * ��������
	 * 
	 */
	public void setPurchasePerson(String purchasePerson) {
		this.purchasePerson = purchasePerson;
	}

	/**
	 * �ɹ�������
	 * 
	 */
	public String getPurchasePerson() {
		return this.purchasePerson;
	}

	/**
	 * ʹ������
	 * 
	 */
	public void setEdition(String edition) {
		this.edition = edition;
	}

	/**
	 * ����汾
	 * 
	 */
	public String getEdition() {
		return this.edition;
	}

	/**
	 * ������
	 * 
	 */
	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * ������
	 * 
	 */
	public String getNo() {
		return this.no;
	}

	/**
	 * ��������
	 * 
	 */
	public void setBuyType(Integer buyType) {
		this.buyType = buyType;
	}

	/**
	 * ��������
	 * 
	 */
	public Integer getBuyType() {
		return this.buyType;
	}
	
	public void setOperationItem(String operationItem) {
	this.operationItem = operationItem;
	}
	
	public String getOperationItem() {
	return this.operationItem;
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
	
	public void setSoftwareItemRelateds(Set<SoftwareItemRelated> softwareItemRelateds) {
		this.softwareItemRelateds = softwareItemRelateds;
	}

	@JsonIgnore
	public Set<SoftwareItemRelated> getSoftwareItemRelateds() {
		if (softwareItemRelateds == null) {
			softwareItemRelateds = new java.util.LinkedHashSet<net.zjcclims.domain.SoftwareItemRelated>();
		}
		return softwareItemRelateds;
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
	public void setTimetableSoftwareRelateds(Set<TimetableSoftwareRelated> timetableSoftwareRelateds) {
		this.timetableSoftwareRelateds = timetableSoftwareRelateds;
	}

	/**
	 */
	@JsonIgnore
	public Set<TimetableSoftwareRelated> getTimetableSoftwareRelateds() {
		if (timetableSoftwareRelateds == null) {
			timetableSoftwareRelateds = new java.util.LinkedHashSet<net.zjcclims.domain.TimetableSoftwareRelated>();
		}
		return timetableSoftwareRelateds;
	}

	/**
	 */
	public Software() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(Software that) {
		setId(that.getId());
		setCode(that.getCode());
		setName(that.getName());
		setProperty(that.getProperty());
		setOperationRequirement(that.getOperationRequirement());
		setFramework(that.getFramework());
		setSupplier(that.getSupplier());
		setInstallInstruction(that.getInstallInstruction());
		setUseInstruction(that.getUseInstruction());
		setPrice(that.getPrice());
		setLabRoom(that.getLabRoom());
		setPurchaseDate(that.getPurchaseDate());
		setSupplierTel(that.getSupplierTel());
		setPurchasePerson(that.getPurchasePerson());
		setEdition(that.getEdition());
		setNo(that.getNo());
		setBuyType(that.getBuyType());
		setSchoolAcademy(that.getSchoolAcademy());
		setSoftwareItemRelateds(new java.util.LinkedHashSet<net.zjcclims.domain.SoftwareItemRelated>(that.getSoftwareItemRelateds()));
		setCommonDocuments(new java.util.LinkedHashSet<net.zjcclims.domain.CommonDocument>(that.getCommonDocuments()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("code=[").append(code).append("] ");
		buffer.append("name=[").append(name).append("] ");
		buffer.append("property=[").append(property).append("] ");
		buffer.append("operationRequirement=[").append(operationRequirement).append("] ");
		buffer.append("framework=[").append(framework).append("] ");
		buffer.append("supplier=[").append(supplier).append("] ");
		buffer.append("installInstruction=[").append(installInstruction).append("] ");
		buffer.append("useInstruction=[").append(useInstruction).append("] ");
		buffer.append("price=[").append(price).append("] ");
		buffer.append("labRoom=[").append(labRoom).append("] ");
		buffer.append("purchaseDate=[").append(purchaseDate).append("] ");
		buffer.append("supplierTel=[").append(supplierTel).append("] ");
		buffer.append("purchasePerson=[").append(purchasePerson).append("] ");
		buffer.append("edition=[").append(edition).append("] ");
		buffer.append("no=[").append(no).append("] ");
		buffer.append("buyType=[").append(buyType).append("] ");

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
		if (!(obj instanceof Software))
			return false;
		Software equalCheck = (Software) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
