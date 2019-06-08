package net.zjcclims.domain;

import java.io.Serializable;

import java.lang.StringBuilder;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.xml.bind.annotation.*;

import javax.persistence.*;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllAssetCabinetWarehouseAccesss", query = "select myAssetCabinetWarehouseAccess from AssetCabinetWarehouseAccess myAssetCabinetWarehouseAccess"),
		@NamedQuery(name = "findAssetCabinetWarehouseAccessByCabinetQuantity", query = "select myAssetCabinetWarehouseAccess from AssetCabinetWarehouseAccess myAssetCabinetWarehouseAccess where myAssetCabinetWarehouseAccess.cabinetQuantity = ?1"),
		@NamedQuery(name = "findAssetCabinetWarehouseAccessByCreateDate", query = "select myAssetCabinetWarehouseAccess from AssetCabinetWarehouseAccess myAssetCabinetWarehouseAccess where myAssetCabinetWarehouseAccess.createDate = ?1"),
		@NamedQuery(name = "findAssetCabinetWarehouseAccessById", query = "select myAssetCabinetWarehouseAccess from AssetCabinetWarehouseAccess myAssetCabinetWarehouseAccess where myAssetCabinetWarehouseAccess.id = ?1"),
		@NamedQuery(name = "findAssetCabinetWarehouseAccessByMem", query = "select myAssetCabinetWarehouseAccess from AssetCabinetWarehouseAccess myAssetCabinetWarehouseAccess where myAssetCabinetWarehouseAccess.mem = ?1"),
		@NamedQuery(name = "findAssetCabinetWarehouseAccessByMemContaining", query = "select myAssetCabinetWarehouseAccess from AssetCabinetWarehouseAccess myAssetCabinetWarehouseAccess where myAssetCabinetWarehouseAccess.mem like ?1"),
		@NamedQuery(name = "findAssetCabinetWarehouseAccessByPrimaryKey", query = "select myAssetCabinetWarehouseAccess from AssetCabinetWarehouseAccess myAssetCabinetWarehouseAccess where myAssetCabinetWarehouseAccess.id = ?1"),
		@NamedQuery(name = "findAssetCabinetWarehouseAccessByStatus", query = "select myAssetCabinetWarehouseAccess from AssetCabinetWarehouseAccess myAssetCabinetWarehouseAccess where myAssetCabinetWarehouseAccess.status = ?1") })
@Table(name = "asset_cabinet_warehouse_access")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "AssetCabinetWarehouseAccess")
public class AssetCabinetWarehouseAccess implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;
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
	 * ���⻹�����1���0����
	 * 
	 */

	@Column(name = "status")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer status;
	
	@Column(name = "flag")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer flag;

	@Column(name = "asset_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer assetId;

	@Column(name = "app_record_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer appRecordId;


	@Column(name = "warehouse_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer warehouseId;


	@Column(name = "type")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String type;
	/**
	 * ���ʽ������
	 * 
	 */

	@Column(name = "cabinet_quantity")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer cabinetQuantity;

	@Column(name = "remain_quantity")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer remainQuantity;
	/**
	 * ����
	 * 
	 */

	@Column(name = "manager")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String manager;

	@Column(name = "mem")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String mem;
	/**
	 * ����ʱ��
	 * 
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "stock_date")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Calendar stockDate;
	
	/**
	 * ���ʵ���
	 * 
	 */

	@Column(name = "unit_price", scale = 10, precision = 16)
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	BigDecimal unitPrice;
	
	

	@Column(name = "if_public")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer ifPublic;

	/**
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "lab_id", referencedColumnName = "id") })
	@XmlTransient
	LabRoom labRoom;
	/**
	 */

	
	/**
	 */
	@OneToMany(mappedBy = "assetCabinetWarehouseAccess", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@XmlElement(name = "", namespace = "")
	java.util.Set<net.zjcclims.domain.AssetCabinetWarehouseAccessRecord> assetCabinetWarehouseAccessRecords;

	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "operation_item_id", referencedColumnName = "id") })
	@XmlTransient
	OperationItem operationItem;
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

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getRemainQuantity() {
		return remainQuantity;
	}

	public void setRemainQuantity(Integer remainQuantity) {
		this.remainQuantity = remainQuantity;
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
	 * ���⻹�����1���0����
	 * 
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/**
	 */
	public void setOperationItem(OperationItem operationItem) {
		this.operationItem = operationItem;
	}

	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	public Integer getAppRecordId() {
		return appRecordId;
	}

	public void setAppRecordId(Integer appRecordId) {
		this.appRecordId = appRecordId;
	}

	/**
	 */
	@JsonIgnore
	public OperationItem getOperationItem() {
		return operationItem;
	}

	/**
	 * ���⻹�����1���0����
	 * 
	 */
	public Integer getStatus() {
		return this.status;
	}

	public Integer getCabinetQuantity() {
		return cabinetQuantity;
	}

	public void setCabinetQuantity(Integer cabinetQuantity) {
		this.cabinetQuantity = cabinetQuantity;
	}

	/**
	 * ����
	 * 
	 */
	public void setMem(String mem) {
		this.mem = mem;
	}

	/**
	 * ����
	 * 
	 */
	public String getMem() {
		return this.mem;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
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



	public Calendar getStockDate() {
		return stockDate;
	}

	public void setStockDate(Calendar stockDate) {
		this.stockDate = stockDate;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getIfPublic() {
		return ifPublic;
	}

	public void setIfPublic(Integer ifPublic) {
		this.ifPublic = ifPublic;
	}
	
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	/**
	 */
	public void setAssetCabinetWarehouseAccessRecords(Set<AssetCabinetWarehouseAccessRecord> assetCabinetWarehouseAccessRecords) {
		this.assetCabinetWarehouseAccessRecords = assetCabinetWarehouseAccessRecords;
	}

	/**
	 */
	@JsonIgnore
	public Set<AssetCabinetWarehouseAccessRecord> getAssetCabinetWarehouseAccessRecords() {
		if (assetCabinetWarehouseAccessRecords == null) {
			assetCabinetWarehouseAccessRecords = new java.util.LinkedHashSet<net.zjcclims.domain.AssetCabinetWarehouseAccessRecord>();
		}
		return assetCabinetWarehouseAccessRecords;
	}

	/**
	 */
	public AssetCabinetWarehouseAccess() {
	}
	

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(AssetCabinetWarehouseAccess that) {
		setId(that.getId());
		setCreateDate(that.getCreateDate());
		setStatus(that.getStatus());
		setCabinetQuantity(that.getCabinetQuantity());
		setMem(that.getMem());
		setOperationItem(that.getOperationItem());
		setLabRoom(that.getLabRoom());
		setAssetCabinetWarehouseAccessRecords(new java.util.LinkedHashSet<net.zjcclims.domain.AssetCabinetWarehouseAccessRecord>(that.getAssetCabinetWarehouseAccessRecords()));
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("createDate=[").append(createDate).append("] ");
		buffer.append("status=[").append(status).append("] ");
		buffer.append("cabinetQuantity=[").append(cabinetQuantity).append("] ");
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
		if (!(obj instanceof AssetCabinetWarehouseAccess))
			return false;
		AssetCabinetWarehouseAccess equalCheck = (AssetCabinetWarehouseAccess) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
