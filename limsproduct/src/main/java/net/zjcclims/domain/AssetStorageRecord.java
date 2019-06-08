package net.zjcclims.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Calendar;


@Entity
@Table(name = "asset_storage_record")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "AssetStorageRecord")
public class AssetStorageRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@XmlElement
	Integer id;

	@Column(name = "store_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer storeId;

	@Column(name = "asset_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer assetId;

	@Column(name = "lab_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer labId;

	@Column(name = "quantity")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String quantity;

	@Column(name = "price")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Double price;

	@Column(name = "supplier")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String supplier;

	@Column(name = "cabinet_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer cabinetId;

	@Column(name = "total_price")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Double totalPrice;

	@Column(name = "invoice_number")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String invoiceNumber;

	@Column(name = "info")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	String info;

	@Column(name = "warehouse_id")
	@Basic(fetch = FetchType.EAGER)
	@XmlElement
	Integer warehouseId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	public Integer getLabId() {
		return labId;
	}

	public void setLabId(Integer labId) {
		this.labId = labId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public Integer getCabinetId() {
		return cabinetId;
	}

	public void setCabinetId(Integer cabinetId) {
		this.cabinetId = cabinetId;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Integer warehouseId) {
		this.warehouseId = warehouseId;
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
		if (!(obj instanceof AssetStorageRecord))
			return false;
		AssetStorageRecord equalCheck = (AssetStorageRecord) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
